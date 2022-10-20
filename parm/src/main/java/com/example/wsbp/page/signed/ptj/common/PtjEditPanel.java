package com.example.wsbp.page.signed.ptj.common;

import com.example.wsbp.MySession;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.domain.models.ptj.value.FinishTime;
import com.example.wsbp.domain.models.ptj.value.StartTime;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.infrastructure.service.ISubjectService;
import com.example.wsbp.infrastructure.service.ptj.IPartTimeJobService;
import com.example.wsbp.infrastructure.service.ptj.IPartTimeJobUpdateService;
import com.example.wsbp.page.signed.ptj.PartTimeJobPage;
import com.example.wsbp.page.signed.ptj.PtjEditCompPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

@AuthorizeInstantiation(Roles.USER)
public class PtjEditPanel extends Panel {
    // TODO ドメイン知識流出や冗長な処理、可読性が低いことからリファクタリングする必要あり
    @SpringBean
    private ISubjectService subjectService;
    @SpringBean
    private IPartTimeJobUpdateService partTimeJobUpdateService;
    @SpringBean
    private IPartTimeJobService partTimeJobService;
    private final List<String> subjects = subjectService.find();
    private final User user = MySession.get().getUser();
    private final static List<String> dutiesSelection = Arrays.asList("実習用ワークシート作成", "課題提出確認カード作成", "eラーニング設定業務", "機器設定業務", "学生指導業務", "出席の集計業務", "課題の採点・集計業務", "質問対応", "授業準備");
    private final static List<Integer> startHourSelection = Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
    private final IModel<List<Integer>> startHourSelectionModel = Model.ofList(startHourSelection);
    private final IModel<List<Integer>> finishHourSelectionModel = Model.ofList(finishHourSelection);
    private final static List<Integer> finishHourSelection = Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
    private final static List<Integer> minutesSelection = Arrays.asList(0, 15, 30, 45);
    // 休憩時間の選択肢
    private final static List<Integer> breakTimeMinutesSelection = Arrays.asList(0, 15, 30, 45, 60);
    private final Model<Integer> selectStartHourModel = Model.of(startHourSelection.get(0));
    private final Model<Integer> selectFinishHourModel = Model.of(startHourSelection.get(0));
    private final Model<Integer> selectStartMinutesModel = Model.of(minutesSelection.get(0));
    private final Model<Integer> selectFinishMinutesModel = Model.of(minutesSelection.get(1));
    private final Model<Integer> selectBreakTimeMinutesModel = Model.of(breakTimeMinutesSelection.get(0));
    private final Model<Float> officeHourModel = Model.of(0.25F);
    private final Model<String> dutiesModel = Model.of(dutiesSelection.get(0));
    private final static float MAX_OFFICE_HOURS = 8;
    private final static float MIN_OFFICE_HOURS = 0.25F;
    private final FeedbackPanel dangerAlert = new FeedbackPanel("officeHoursFeedback");

    public PtjEditPanel(final String wicketId, final String date, final PartTimeJob originPtjRequest) {
        super(wicketId);
        Date ptjDate = Date.valueOf(date);
        Model<String> dateModel = Model.of(ptjDate.toString());
        Model<String> userTypeModel = Model.of(user.getUserType());
        Model<String> selectSubjectModel = Model.of(subjects.get(originPtjRequest.getSubjectId() - 1));
        Form<Void> ptjDetailForm = new Form<>("ptjDetailer");
        add(ptjDetailForm);
        var formButton = new Button("submit") {
            @Override
            public void onSubmit() {
                int id = originPtjRequest.getId();
                String studentNumber = user.getStudentNumber();
                int newSubjectId = subjectService.findForName(selectSubjectModel.getObject()).getId();
                Date date = Date.valueOf(dateModel.getObject());
                Time newStartTime = new StartTime(selectStartHourModel.getObject(), selectStartMinutesModel.getObject()).getTime();
                Time newFinishTime = new FinishTime(selectFinishHourModel.getObject(), selectFinishMinutesModel.getObject()).getTime();
                int newPtjBreakTimeMinutes = selectBreakTimeMinutesModel.getObject();
                float newOfficeHour = officeHourModel.getObject();
                String newDuties = dutiesModel.getObject();
                PartTimeJob newPtjRequest = new PartTimeJob(id, studentNumber, newSubjectId, date, newStartTime, newFinishTime, newPtjBreakTimeMinutes, newOfficeHour, newDuties);
                // IoC/DI した partTimeJobService のメソッドを呼び出す
                partTimeJobUpdateService.update(newPtjRequest);
                setResponsePage(new PtjEditCompPage("更新"));
            }
        };
        ptjDetailForm.add(formButton);
        dangerAlert.setVisible(false);
        dangerAlert.setOutputMarkupPlaceholderTag(true);
        dangerAlert.setOutputMarkupId(true);
        ptjDetailForm.add(dangerAlert);

        // 日付部分
        var dateField = new TextField<>("date", dateModel);
        dateField.setEnabled(false);
        ptjDetailForm.add(dateField);

        // ユーザタイプ
        var workingTypeField = new TextField<>("userType", userTypeModel);
        workingTypeField.setEnabled(false);
        ptjDetailForm.add(workingTypeField);

        //勤務対象
        //TODO: 今はサンプルデータが少ないのでドロップダウンからの選択でも構わないが、選択肢が増えたら、絞り込みに対応するなどしないと選択しづらい（参考になりそう：https://blog.kotorel.com/2014/01/test.html）
        var subjectChoice = new DropDownChoice<>("subjectChoice", selectSubjectModel, Model.ofList(subjects));
        ptjDetailForm.add(subjectChoice);

        //合計時間のモデル（表示用）
        officeHourModel.setObject(originPtjRequest.getOfficeHours());
        var officeHourFiled = new TextField<>("officeHour", officeHourModel);
        officeHourFiled.setOutputMarkupId(true);
        officeHourFiled.setEnabled(false);
        ptjDetailForm.add(officeHourFiled);

        //開始時間
        selectStartHourModel.setObject(originPtjRequest.getStartTime().getHour());
        var workingStartHoursField = new DropDownChoice<>("startHour", selectStartHourModel, startHourSelectionModel);
        workingStartHoursField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(workingStartHoursField);

        selectStartMinutesModel.setObject(originPtjRequest.getStartTime().getMinutes());
        var workingStartMinutesField = new DropDownChoice<>("startMinutes", selectStartMinutesModel, Model.ofList(minutesSelection));
        workingStartMinutesField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(workingStartMinutesField);

        //終了時間
        selectFinishHourModel.setObject(originPtjRequest.getFinishTime().getHour());
        var workingEndHoursField = new DropDownChoice<>("finishHour", selectFinishHourModel, finishHourSelectionModel);
        workingEndHoursField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(workingEndHoursField);

        selectFinishMinutesModel.setObject(originPtjRequest.getFinishTime().getMinutes());
        var workingEndMinutesField = new DropDownChoice<>("finishMinutes", selectFinishMinutesModel, Model.ofList(minutesSelection));
        workingEndMinutesField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(workingEndMinutesField);

        //休憩時間
        selectBreakTimeMinutesModel.setObject(originPtjRequest.getBreakTimeMinutes());
        var breakTimeField = new DropDownChoice<>("breakTime", selectBreakTimeMinutesModel, Model.ofList(breakTimeMinutesSelection));
        breakTimeField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(breakTimeField);

        // 勤務内容
        dutiesModel.setObject(originPtjRequest.getDuties());
        var workingContentField = new DropDownChoice<>("workingContent", dutiesModel, Model.ofList(dutiesSelection));
        ptjDetailForm.add(workingContentField);

        //フィードバック部分
        final FeedbackPanel label = new FeedbackPanel("feedBackMessage");
        label.setOutputMarkupId(true);
        ptjDetailForm.add(label);

        // 編集ボタンが押されたときに動く

        formButton.add(new Label("sendButtonLabel", "更新"));
        //戻るリンク
        var returnPartTimeJobPageLink = new BookmarkablePageLink<>("returnPartTimeJobPage", PartTimeJobPage.class);
        add(returnPartTimeJobPageLink);
    }

    // insertするとき
    public PtjEditPanel(final String wicketId, final String date) {
        super(wicketId);
        Date ptjDate = Date.valueOf(date);
        Model<String> dateModel = Model.of(ptjDate.toString());
        Model<String> userTypeModel = Model.of(user.getUserType());
        Model<String> selectSubjectModel = Model.of(subjects.get(0));
        Form<Void> ptjDetailForm = new Form<>("ptjDetailer");
        add(ptjDetailForm);
        var formButton = new Button("submit") {
            @Override
            public void onSubmit() {
                String studentNumber = user.getStudentNumber();
                int subjectId = subjectService.findForName(selectSubjectModel.getObject()).getId();
                Date date = Date.valueOf(dateModel.getObject());
                Time startTime = new StartTime(selectStartHourModel.getObject(), selectStartMinutesModel.getObject()).getTime();
                Time finishTime = new FinishTime(selectFinishHourModel.getObject(), selectFinishMinutesModel.getObject()).getTime();
                int ptjBreakTimeMinutes = selectBreakTimeMinutesModel.getObject();
                float officeHour = officeHourModel.getObject();
                String ptjContent = dutiesModel.getObject();
                partTimeJobUpdateService.insert(studentNumber, subjectId, date, startTime, finishTime, ptjBreakTimeMinutes, officeHour, ptjContent);
                setResponsePage(new PtjEditCompPage("登録"));
            }
        };
        formButton.setOutputMarkupPlaceholderTag(true);
        formButton.setOutputMarkupId(true);
        ptjDetailForm.add(formButton);

        dangerAlert.setVisible(false);
        dangerAlert.setOutputMarkupPlaceholderTag(true);
        dangerAlert.setOutputMarkupId(true);
        ptjDetailForm.add(dangerAlert);

        // 日付部分
        var dateField = new TextField<>("date", dateModel);
        dateField.setEnabled(false);
        ptjDetailForm.add(dateField);

        // ユーザタイプ
        var workingTypeField = new TextField<>("userType", userTypeModel);
        workingTypeField.setEnabled(false);
        ptjDetailForm.add(workingTypeField);

        //勤務対象
        //TODO: 今はサンプルデータが少ないのでドロップダウンからの選択でも構わないが、選択肢が増えたら、絞り込みに対応するなどしないと選択しづらい（参考になりそう：https://blog.kotorel.com/2014/01/test.html）
        var subjectChoice = new DropDownChoice<>("subjectChoice", selectSubjectModel, Model.ofList(subjects));
        ptjDetailForm.add(subjectChoice);

        //合計時間のモデル（表示用）
        var officeHourFiled = new TextField<>("officeHour", officeHourModel);
        officeHourFiled.setOutputMarkupId(true);
        officeHourFiled.setEnabled(false);
        ptjDetailForm.add(officeHourFiled);

        //開始時間
        var workingStartHoursField = new DropDownChoice<>("startHour", selectStartHourModel, startHourSelectionModel);
        workingStartHoursField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(workingStartHoursField);

        var workingStartMinutesField = new DropDownChoice<>("startMinutes", selectStartMinutesModel, Model.ofList(minutesSelection));
        workingStartMinutesField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(workingStartMinutesField);

        //終了時間
        var workingEndHoursField = new DropDownChoice<>("finishHour", selectFinishHourModel, finishHourSelectionModel);
        workingEndHoursField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(workingEndHoursField);

        var workingEndMinutesField = new DropDownChoice<>("finishMinutes", selectFinishMinutesModel, Model.ofList(minutesSelection));
        workingEndMinutesField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(workingEndMinutesField);

        //休憩時間
        var breakTimeField = new DropDownChoice<>("breakTime", selectBreakTimeMinutesModel, Model.ofList(breakTimeMinutesSelection));
        breakTimeField.add(onChangeHandler(officeHourFiled, formButton));
        ptjDetailForm.add(breakTimeField);

        // 勤務内容
        var workingContentField = new DropDownChoice<>("workingContent", dutiesModel, Model.ofList(dutiesSelection));
        ptjDetailForm.add(workingContentField);


        //編集ボタンが押されたときに動く
        formButton.add(new Label("sendButtonLabel", "登録"));

        //戻るリンク
        var returnPartTimeJobPageLink = new BookmarkablePageLink<>("returnPartTimeJobPage", PartTimeJobPage.class);
        add(returnPartTimeJobPageLink);
    }

    private float calcOfficeHour() {
        int startHour = selectStartHourModel.getObject();
        int startMinutes = selectStartMinutesModel.getObject();
        int finishHour = selectFinishHourModel.getObject();
        int finishMinutes = selectFinishMinutesModel.getObject();
        int breakTime = selectBreakTimeMinutesModel.getObject();
        StartTime startTime = new StartTime(startHour, startMinutes);
        FinishTime finishTime = new FinishTime(finishHour, finishMinutes);
        return (float) (finishTime.getTime().getTime() - startTime.getTime().getTime()) / 3600000 - (float) breakTime / 60;
    }

    private AjaxFormComponentUpdatingBehavior onChangeHandler(TextField<Float> id, Button button) {
        return new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                officeHourModel.setObject(calcOfficeHour());
                id.setModel(officeHourModel);
                if (officeHourModel.getObject() < MIN_OFFICE_HOURS) {
                    dangerAlert.error(String.format("%.2f時間未満の情報は入力できません", MIN_OFFICE_HOURS));
                    dangerAlert.setVisible(true);
                    button.setVisible(false);
                } else if (officeHourModel.getObject() > MAX_OFFICE_HOURS) {
                    dangerAlert.error("この日の合計勤務時間が8時間を超えようとしています。1日の合計勤務時間は8時間を超えてはいけません");
                    dangerAlert.setVisible(true);
                    button.setVisible(false);
                } else {
                    button.setVisible(true);
                    dangerAlert.setVisible(false);
                }
                ajaxRequestTarget.add(dangerAlert);
                ajaxRequestTarget.add(button);
                ajaxRequestTarget.add(id);
            }
        };
    }
}
