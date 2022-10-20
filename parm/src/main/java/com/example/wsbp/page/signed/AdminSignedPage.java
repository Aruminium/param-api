package com.example.wsbp.page.signed;

import com.example.wsbp.MySession;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.page.ParentPage;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.wicketstuff.annotation.mount.MountPath;

@AuthorizeInstantiation(Roles.ADMIN)
@MountPath("admin-Signed")
public class AdminSignedPage extends ParentPage {
    User user = MySession.get().getUser();
}
