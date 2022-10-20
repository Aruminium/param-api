package com.example.wsbp.page;

import com.example.wsbp.MySession;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.infrastructure.service.user.IUserService;
import com.example.wsbp.page.signed.AdminSignedPage;
import com.example.wsbp.page.signed.SignedPage;
import com.example.wsbp.page.usermanager.UserMakerPage;
import com.example.wsbp.validation.StudentNumberValidator;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.Objects;


@WicketSignInPage
@WicketHomePage
@MountPath("Sign")
public class SignPage extends ParentPage {

    // ServiceをIoC/DIする
    @SpringBean
    private IUserService userService;


    public SignPage() {
        Model<String> studentNumberModel = Model.of("");
        Model<String> userPassModel = Model.of("");

        var failedSign = new WebMarkupContainer("failedSign");
        failedSign.setVisible(false);
        failedSign.setOutputMarkupId(true);
        failedSign.setOutputMarkupPlaceholderTag(true);

        Form<Void> userInfoForm = new Form<>("userInfo") {
            @Override
            protected void onSubmit() {
                String studentNumber = studentNumberModel.getObject();
                String userPass = userPassModel.getObject();
                User user;
                try {
                    user = userService.loginUser(studentNumber, userPass);
                } catch (Exception e) {
                    failedSign.setVisible(true);
                    return;
                }
                MySession.get().sign(user);
                if (Objects.equals(MySession.get().getRoles().toString(), Roles.ADMIN))
                    setResponsePage(new AdminSignedPage());
                else setResponsePage(new SignedPage());
            }
        };
        add(userInfoForm);
        userInfoForm.add(failedSign);
        var dangerAlert = new FeedbackPanel("studentNumberFeedback");
        dangerAlert.setVisible(false);
        dangerAlert.setOutputMarkupPlaceholderTag(true);
        dangerAlert.setOutputMarkupId(true);
        userInfoForm.add(dangerAlert);

        var studentNumberField = new TextField<>("studentNumber", studentNumberModel);
        studentNumberField.add(StringValidator.exactLength(8));
        studentNumberField.add(new StudentNumberValidator(dangerAlert));
        userInfoForm.add(studentNumberField);

        var userPassField = new PasswordTextField("userPass", userPassModel);
        userPassField.add(StringValidator.lengthBetween(8, 32));
        userInfoForm.add(userPassField);


        var userMakerLink = new BookmarkablePageLink<>("userMaker", UserMakerPage.class);
        add(userMakerLink);
    }

}
