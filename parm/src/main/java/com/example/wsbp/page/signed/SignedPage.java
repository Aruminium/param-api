package com.example.wsbp.page.signed;

import com.example.wsbp.MySession;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.infrastructure.service.user.IUserService;
import com.example.wsbp.page.ParentPage;
import com.example.wsbp.page.SignPage;
import com.example.wsbp.page.signed.ptj.PartTimeJobPage;
import com.example.wsbp.page.signed.ptj.common.ConfirmationLink;
import com.example.wsbp.page.usermanager.UserDeleteCompPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * ログイン後に動くクラス
 */

// ↓ どの役割のユーザであれば表示してよいか。
// セッションのgetRoleメソッドが USER であれば表示し、それ以外は表示しない。
@AuthorizeInstantiation(Roles.USER)
@MountPath("Signed")
public class SignedPage extends ParentPage {
    @SpringBean
    IUserService userService;

    public SignedPage() {
        User user = MySession.get().getUser();
        var userNameLabel = new Label("userName", user.getName());
        add(userNameLabel);
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

        //アルバイト機能
        var toPartTimeJobLink = new BookmarkablePageLink<>("toPartTimeJob", PartTimeJobPage.class);
        add(toPartTimeJobLink);
        var ptjDelete = new ConfirmationLink<>("userDelete", "全ての情報がなくなります。ユーザ削除しますか") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                userService.removeUser(MySession.get().getUser().getStudentNumber());
                setResponsePage(new UserDeleteCompPage());
            }
        };
        add(ptjDelete);

    }
}
