package com.example.wsbp.page.usermanager;

import com.example.wsbp.page.ParentPage;
import com.example.wsbp.page.SignPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("UserMakerComp")
public class UserMakerCompPage extends ParentPage {

    public UserMakerCompPage(IModel<String> userNameModel) {
        var userNameLabel = new Label("userName", userNameModel);
        add(userNameLabel);

        var toHomeLink = new BookmarkablePageLink<>("toHome", SignPage.class);
        add(toHomeLink);
    }
}
