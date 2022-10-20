package com.example.wsbp.page.signed.ptj;

import com.example.wsbp.MySession;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.infrastructure.service.ISubjectService;
import com.example.wsbp.infrastructure.service.ptj.IPartTimeJobService;
import com.example.wsbp.infrastructure.service.ptj.IPartTimeJobUpdateService;
import com.example.wsbp.page.ParentPage;
import com.example.wsbp.page.SignPage;
import com.example.wsbp.page.signed.ptj.common.ConfirmationLink;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@AuthorizeInstantiation(Roles.USER)
@MountPath("View")
public class PtjView extends ParentPage {

    //まとめて削除する要素
    private final List<PartTimeJob> ptjRequestDeleteList = new ArrayList<>();
    @SpringBean
    private IPartTimeJobService partTimeJobService;
    @SpringBean
    private IPartTimeJobUpdateService partTimeJobUpdateService;
    @SpringBean
    private ISubjectService subjectService;
    private final List<String> subjects = subjectService.find();
    private final Model<Boolean> checkBoxModel = Model.of(false);

    public PtjView(PageParameters parameters) {
        //ログイン情報から取得
        User user = MySession.get().getUser();
        Date ptjDate = Date.valueOf((parameters.get("date").toString()));
        List<PartTimeJob> ptjRequestList = partTimeJobService.find(user.getStudentNumber(), ptjDate);
        IModel<List<PartTimeJob>> ptjRequestListModel = Model.ofList(ptjRequestList);
        add(new Label("date", Model.of(ptjDate)));
        var empty = new WebMarkupContainer("empty");
        empty.setVisible(ptjRequestList.isEmpty());
        empty.setOutputMarkupPlaceholderTag(true);
        add(empty);


        //追加リンク
        //遷移先のページにパラメーターを渡すには、パラメーターの分だけ「.add」する（URLでたまに見る「?xxx=yyy」の部分にあたる）
        BookmarkablePageLink<Object> ptjRequestPageLink = new BookmarkablePageLink<>("empty-add", PtjInsertPage.class, new PageParameters().add("date", ptjDate));
        empty.add(ptjRequestPageLink);
        var form = new Form<>("selectDeleteForm") {
            @Override
            public boolean isVisible() {
                return !ptjRequestList.isEmpty();
            }
        };

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

        var selectDeleteButton = new AjaxButton("selectDeleteButton", form) {
            //選択分削除
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                partTimeJobUpdateService.deletes(ptjRequestDeleteList);
                setResponsePage(new PtjView(new PageParameters().add("date", ptjDate)));
            }

            //初期化
            @Override
            protected void onInitialize() {
                super.onInitialize();
                this.setVisible(!ptjRequestDeleteList.isEmpty());
                this.setOutputMarkupId(true);
                this.setOutputMarkupPlaceholderTag(true);
            }

            //確認ダイアログ表示
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                AjaxCallListener ajaxCallListener = new AjaxCallListener();
                ajaxCallListener.onPrecondition("return confirm('" + ptjRequestDeleteList.size() + "件削除しますか');");
                attributes.getAjaxCallListeners().add(ajaxCallListener);
            }
        };

        var wmc = new WebMarkupContainer("wmc") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                setOutputMarkupId(true);
            }
        };
        form.add(wmc);

        //取得したデータを表示
        var ptjLV = new ListView<>("list", ptjRequestListModel) {

            @Override
            protected void populateItem(ListItem<PartTimeJob> listItem) {
                // List型のモデルから、 <li>...</li> ひとつ分に分けられたモデルを取り出す
                PartTimeJob ptjItem = listItem.getModelObject(); // 元々のListの n 番目の要素
                // インスタンスに入れ込まれたデータベースの検索結果を、列（＝フィールド変数）ごとにとりだして表示する
                // add する先が listItem になることに注意。
                var deleteCheckBox = new AjaxCheckBox("deleteCheckBox", checkBoxModel) {
                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        //チェック時に実行
                        if (getModelObject()) {
                            ptjRequestDeleteList.add(ptjItem);
                        } else {
                            ptjRequestDeleteList.remove(ptjItem);
                        }
                        target.add(selectDeleteButton.setVisible(!ptjRequestDeleteList.isEmpty()));

                    }
                };
                deleteCheckBox.setOutputMarkupId(true);
                listItem.add(deleteCheckBox);
                listItem.add(new Label("subject", subjects.get(ptjItem.getSubjectId() - 1)));
                listItem.add(new Label("startTime", ptjItem.getStartTime().getTime().toString()));
                listItem.add(new Label("finishTime", ptjItem.getFinishTime().getTime().toString()));
                listItem.add(new Label("breakTime", ptjItem.getBreakTimeMinutes()));
                listItem.add(new Label("officeHour", ptjItem.getOfficeHours()));
                listItem.add(new Label("duties", ptjItem.getDuties()));
                listItem.add(new BookmarkablePageLink<>("edit", PtjUpdatePage.class, new PageParameters().add("date", ptjDate).add("id", ptjItem.getId())));
                var ptjDelete = new ConfirmationLink<>("delete", "削除しますか") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        partTimeJobUpdateService.delete(ptjItem);

                        setResponsePage(new PtjView(new PageParameters().add("date", ptjDate)));
                    }
                };
                listItem.add(ptjDelete);
            }

        };
        wmc.add(ptjLV);
        var addButton = new BookmarkablePageLink<>("add", PtjInsertPage.class, new PageParameters().add("date", ptjDate));
        addButton.setVisible(!ptjRequestList.isEmpty());
        addButton.setOutputMarkupPlaceholderTag(true);
        add(addButton);
        //戻るリンク
        var returnPartTimeJobPageLink = new BookmarkablePageLink<>("returnPartTimeJobPage", PartTimeJobPage.class);
        add(returnPartTimeJobPageLink);

        //まとめて削除ボタン
        form.add(selectDeleteButton);
        add(form);
    }
}
