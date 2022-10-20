package com.example.wsbp.page.usermanager;

import com.example.wsbp.page.ParentPage;
import com.example.wsbp.page.SignPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("UserDeleteComp")
public class UserDeleteCompPage extends ParentPage {
    public UserDeleteCompPage() {
        var toHomeLink = new BookmarkablePageLink<>("toHome", SignPage.class);
        add(toHomeLink);
    }
}
