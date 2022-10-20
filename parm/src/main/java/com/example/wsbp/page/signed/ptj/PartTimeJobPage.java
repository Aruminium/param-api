package com.example.wsbp.page.signed.ptj;

import com.example.wsbp.MySession;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.domain.models.request.PtjRequest;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.infrastructure.api.PtjRequestPdfAPI;
import com.example.wsbp.infrastructure.service.ISubjectService;
import com.example.wsbp.infrastructure.service.ptj.IPartTimeJobService;
import com.example.wsbp.page.ParentPage;
import com.example.wsbp.page.SignPage;
import com.example.wsbp.page.signed.SignedPage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * カレンダーが表示される画面
 */
@AuthorizeInstantiation(Roles.USER)
@MountPath("PartTimeJob")
public class PartTimeJobPage extends ParentPage {

    @SpringBean
    private IPartTimeJobService partTimeJobService;
    @SpringBean
    private ISubjectService subjectService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
    private String parameter = sdf.format(Calendar.getInstance().getTime());
    private final User user = MySession.get().getUser();
    private final List<String> subjects = subjectService.find();
    //    private List<PartTimeJob> ptjRequestList = partTimeJobService.find(user.getStudentNumber(), Date.valueOf(parameter), subjects.get(0));
    private final Model<String> selectSubjectModel = Model.of(subjects.get(0));
    private List<PartTimeJob> jobList = partTimeJobService.find(user.getStudentNumber(), Date.valueOf(parameter), selectSubjectModel.getObject());


    public PartTimeJobPage() {
        //表示中の年月（●年●月1日／月末だと計算がずれる可能性があるので日にちは1日に固定している）
        var displayingDateModel = Model.of(Calendar.getInstance());
        displayingDateModel.getObject().set(Calendar.DATE, 1);
        //年月の表示部分
        //Ajaxで更新する部分にはsetOutputMarkupId(true)を実行
        var calendarHeaderModel = Model.of(displayingDateModel.getObject().get(Calendar.YEAR) + "年 " + (displayingDateModel.getObject().get(Calendar.MONTH) + 1) + "月");
        var calendarHeaderLabel = new Label("calendarHeader", calendarHeaderModel) {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                setOutputMarkupId(true);
            }
        };
        add(calendarHeaderLabel);

        //カレンダーのデータ（日付情報）
        var calendarRowModel = Model.ofList(getCalendarRowData(displayingDateModel.getObject()));

        //カレンダーの、表示が変わる部分の親
        var calendarWMC = new WebMarkupContainer("calendar") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                setOutputMarkupId(true);
            }
        };
        add(calendarWMC);

        //カレンダーのデータを基に、ListViewとして出力
        //ListViewの中に更にListViewがあるという入れ子構造になっている
        var calendarRowLV = new ListView<>("calendarRow", calendarRowModel) {
            @Override
            protected void populateItem(ListItem<List<Calendar>> listItem) {
                var calendarRowData = listItem.getModel();

                var calendarColLV = new ListView<>("calendarCol", calendarRowData) {
                    @Override
                    protected void populateItem(ListItem<Calendar> listItem) {
                        var calendarItem = listItem.getModel().getObject();

                        //1桁の月・日の頭に0を付与した文字列（URLに使う用）
                        String monthOnURL = ("0" + (calendarItem.get(Calendar.MONTH) + 1));
                        monthOnURL = monthOnURL.substring(monthOnURL.length() - 2);
                        String dateOnURL = ("0" + (calendarItem.get(Calendar.DATE)));
                        dateOnURL = dateOnURL.substring(dateOnURL.length() - 2);
                        //yyyy-mm-ddの形
                        parameter = calendarItem.get(Calendar.YEAR) + "-" + monthOnURL + "-" + dateOnURL;

                        //ハイパーリンクの部分
                        var calendarLink = new BookmarkablePageLink<>("calendarLink", PtjView.class, new PageParameters().add("date", parameter));
                        //ハイパーリンクの中に日付を入れる
                        calendarLink.add(new Label("calendarDate", calendarItem.get(Calendar.DATE)));

                        listItem.add(calendarLink);

                        //先月・来月の日付の場合、tdタグにdisabledクラスを付与
                        if (calendarItem.get(Calendar.MONTH) != displayingDateModel.getObject().get(Calendar.MONTH)) {
                            listItem.add(new AttributeModifier("class", "disabled"));
                        }

                        //今日の日付の場合、tdタグにtodayクラスを付与
                        Calendar today = Calendar.getInstance();
                        if (calendarItem.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                                calendarItem.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                                calendarItem.get(Calendar.DATE) == today.get(Calendar.DATE)) {
                            listItem.add(new AttributeModifier("class", "today"));
                        }
                    }
                };

                listItem.add(calendarColLV);
            }
        };
        calendarWMC.add(calendarRowLV);

        var wmc = new WebMarkupContainer("wmc") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                setOutputMarkupId(true);
            }
        };
        add(wmc);
        ListView<PartTimeJob> listView = new ListView<>("list", jobList) {
            @Override
            protected void populateItem(ListItem<PartTimeJob> listItem) {
                PartTimeJob job = listItem.getModelObject();
                listItem.add(new Label("index", listItem.getIndex() + 1));
                listItem.add(new Label("date", job.getDate().toString()));
                listItem.add(new Label("startTime", job.getStartTime().getTime().toString()));
                listItem.add(new Label("finishTime", job.getFinishTime().getTime().toString()));
                listItem.add(new Label("breakTime", job.getBreakTimeMinutes()));
                listItem.add(new Label("officeHour", job.getOfficeHours()));
                listItem.add(new Label("duties", job.getDuties()));
                listItem.add(new AjaxEventBehavior("click") {
                    @Override
                    protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
                        setResponsePage(PtjUpdatePage.class, new PageParameters().add("date", job.getDate()).add("id", job.getId()));
                    }
                });
            }
        };
        wmc.add(listView);

        var downloadButton = new Link<>("downloadButton") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                this.setVisible(!jobList.isEmpty());
                this.setOutputMarkupId(true);
                this.setOutputMarkupPlaceholderTag(true);
            }

            @Override
            public void onClick() {
                PtjRequest ptjRequest = new PtjRequest(user, subjectService.findForName(selectSubjectModel.getObject()), jobList);
                try {
                    PtjRequestPdfAPI ptjRequestPdfAPI = new PtjRequestPdfAPI(ptjRequest);
                    ptjRequestPdfAPI.emptyDirectory();
                    IResourceStream stream = new FileResourceStream(ptjRequestPdfAPI.getFile());
                    ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(stream, ptjRequestPdfAPI.getFileName());
                    handler.setContentDisposition(ContentDisposition.ATTACHMENT);
                    handler.setCacheDuration(Duration.NONE);
                    getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        add(downloadButton);


        //月の切り替えボタン
        //displayingDateModelの中のCalendarインスタンスの月を足し引きしてから、●年●月の表示と、カレンダーのデータを再取得している
        add(new AjaxLink<Void>("calendarPrev") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                displayingDateModel.getObject().add(Calendar.MONTH, -1);
                calendarHeaderModel.setObject(displayingDateModel.getObject().get(Calendar.YEAR) + "年 " + (displayingDateModel.getObject().get(Calendar.MONTH) + 1) + "月");
                calendarRowModel.setObject(getCalendarRowData(displayingDateModel.getObject()));
                parameter = sdf.format(displayingDateModel.getObject().getTime());
                jobList = partTimeJobService.find(user.getStudentNumber(), Date.valueOf(parameter), selectSubjectModel.getObject());
                listView.setList(jobList);
                ajaxRequestTarget.add(calendarHeaderLabel);
                ajaxRequestTarget.add(calendarWMC);
                ajaxRequestTarget.add(downloadButton.setVisible(!jobList.isEmpty()));
                ajaxRequestTarget.add(wmc);
            }
        });
        add(new AjaxLink<Void>("calendarNext") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                displayingDateModel.getObject().add(Calendar.MONTH, 1);
                calendarHeaderModel.setObject(displayingDateModel.getObject().get(Calendar.YEAR) + "年 " + (displayingDateModel.getObject().get(Calendar.MONTH) + 1) + "月");
                calendarRowModel.setObject(getCalendarRowData(displayingDateModel.getObject()));
                parameter = sdf.format(displayingDateModel.getObject().getTime());
                jobList = partTimeJobService.find(user.getStudentNumber(), Date.valueOf(parameter), selectSubjectModel.getObject());
                listView.setList(jobList);
                ajaxRequestTarget.add(calendarHeaderLabel);
                ajaxRequestTarget.add(calendarWMC);
                ajaxRequestTarget.add(downloadButton.setVisible(!jobList.isEmpty()));
                ajaxRequestTarget.add(wmc);
            }
        });

        // ログアウトリンク
        Link<Void> signOutLink = new Link<>("signOut") {
            @Override
            public void onClick() {
                // セッションの破棄
                MySession.get().invalidate();
                // SignPageへ移動
                setResponsePage(SignPage.class);
            }
        };
        add(signOutLink);

        //戻るリンク
        var returnSignedPageLink = new BookmarkablePageLink<>("returnSignedPage", SignedPage.class);
        add(returnSignedPageLink);


        var subjectChoice = new DropDownChoice<>("subjectChoice", selectSubjectModel, Model.ofList(subjects));
        subjectChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                parameter = sdf.format(displayingDateModel.getObject().getTime());
                jobList = partTimeJobService.find(user.getStudentNumber(), Date.valueOf(parameter), selectSubjectModel.getObject());
                listView.setList(jobList);
                ajaxRequestTarget.add(downloadButton.setVisible(!jobList.isEmpty()));
                ajaxRequestTarget.add(wmc);
            }
        });
        add(subjectChoice);

    }

    /**
     * カレンダーの行・列のデータを、2次元Listとして返すメソッド。
     * 7個のCalendarインスタンス（日～土に相当）が入ったListが、必要な行数分入っている（最大6行）。
     * 例（2022年9月のCalendarインスタンスを渡した場合）：
     * [
     * [ 8/28,  8/29,  8/30,  8/31,  9/ 1,  9/ 2,  9/ 3],
     * [ 9/ 4,  9/ 5,  9/ 6,  9/ 7,  9/ 8,  9/ 9,  9/10],
     * [ 9/11,  9/12,  9/13,  9/14,  9/15,  9/16,  9/17],
     * [ 9/18,  9/19,  9/20,  9/21,  9/22,  9/23,  9/24],
     * [ 9/25,  9/26,  9/27,  9/28,  9/29,  9/30, 10/ 1],
     * ]
     *
     * @param date 対象となる年月のCalendarインスタンス（日にちは1日に設定されている事を想定。月末の日付の場合、ズレる可能性あり。）
     * @return カレンダーデータの2次元List
     */
    private List<List<Calendar>> getCalendarRowData(Calendar date) {
        //●日　の管理用
        int count = 0;
        //月の最初が何曜日？（0～6に変換）
        int startDayOfWeek = date.get(Calendar.DAY_OF_WEEK) - 1;
        //月の最終日を取得
        var endDateInstance = (Calendar) date.clone();
        endDateInstance.add(Calendar.MONTH, 1);
        endDateInstance.add(Calendar.DATE, -1);
        int endDate = endDateInstance.get(Calendar.DATE);
        //前の月の最終日を取得
        endDateInstance.set(Calendar.DATE, 0);
        int lastMonthEndDate = endDateInstance.get(Calendar.DATE);
        //カレンダーの必要な行数を計算
        int rows = (int) Math.ceil((float) (startDayOfWeek + endDate) / 7);

        //カレンダーのデータ（日付情報）の格納
        List<List<Calendar>> calendarRowData = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            List<Calendar> calendarColData = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                Calendar calendarData = (Calendar) date.clone();
                if (i == 0 && j < startDayOfWeek) {
                    //1行目・1日以前
                    calendarData.add(Calendar.MONTH, -1);
                    calendarData.set(Calendar.DATE, lastMonthEndDate - startDayOfWeek + j + 1);
                } else if (count >= endDate) {
                    //最終行の最終日以降
                    count++;
                    calendarData.add(Calendar.MONTH, 1);
                    calendarData.set(Calendar.DATE, count - endDate);
                } else {
                    //今月
                    count++;
                    calendarData.set(Calendar.DATE, count);
                }
                calendarColData.add(calendarData);
            }
            calendarRowData.add(calendarColData);
        }

        return calendarRowData;
    }
}
