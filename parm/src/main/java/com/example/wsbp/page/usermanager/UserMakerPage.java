package com.example.wsbp.page.usermanager;

import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.infrastructure.service.user.IUserService;
import com.example.wsbp.page.ParentPage;
import com.example.wsbp.page.SignPage;
import com.example.wsbp.validation.StudentNumberValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.annotation.mount.MountPath;

import java.security.NoSuchAlgorithmException;


@MountPath("UserMaker")
public class UserMakerPage extends ParentPage {

    @SpringBean
    private IUserService userService;

    public UserMakerPage() {
        final Model<String> studentNumberModel = Model.of("");
        final Model<String> userNameModel = Model.of("");
        final Model<String> userPassModel = Model.of("");

        //配置したFormコンポーネントを匿名クラス化して処理を上書きする
        Form<Void> userInfoForm = new Form<>("userInfo") {
            @Override
            protected void onSubmit() {
                String studentNumber = studentNumberModel.getObject();
                String userName = userNameModel.getObject();
                String userPass = userPassModel.getObject();
                User user = new User(studentNumber, userName, userPass);
                // IoC/DI した userService のメソッドを呼び出す
                try {
                    userService.registerUser(user);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                setResponsePage(new UserMakerCompPage(userNameModel));
            }
        };
        add(userInfoForm);
        var dangerAlert = new FeedbackPanel("studentNumberFeedback");
        dangerAlert.setVisible(false);
        dangerAlert.setOutputMarkupPlaceholderTag(true);
        dangerAlert.setOutputMarkupId(true);
        userInfoForm.add(dangerAlert);

        var studentNumberField = new RequiredTextField<>("studentNumber", studentNumberModel);
        studentNumberField.add(StringValidator.exactLength(8));
        studentNumberField.add(new StudentNumberValidator(dangerAlert));
        userInfoForm.add(studentNumberField);

        var userNameField = new RequiredTextField<>("userName", userNameModel);
        userNameField.add(StringValidator.lengthBetween(1, 32));
        userInfoForm.add(userNameField);

        // 右辺の型引数 <String> を <> に省略して書いている
        var userPassField = new PasswordTextField("userPass", userPassModel);
        userPassField.add(StringValidator.lengthBetween(8, 32));
        userInfoForm.add(userPassField);

        var toHomeLink = new BookmarkablePageLink<>("toHome", SignPage.class);
        userInfoForm.add(toHomeLink);
    }

}
