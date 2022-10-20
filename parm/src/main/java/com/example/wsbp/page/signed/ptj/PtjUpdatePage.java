package com.example.wsbp.page.signed.ptj;

import com.example.wsbp.MySession;
import com.example.wsbp.domain.models.ptj.IPartTimeJobRepository;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.domain.models.user.User;
import com.example.wsbp.page.ParentPage;
import com.example.wsbp.page.signed.ptj.common.PtjEditPanel;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.sql.Date;

/**
 * 学生がアルバイト内容を申請するために動くクラス
 */
@AuthorizeInstantiation(Roles.USER)
@MountPath("Edit")
public class PtjUpdatePage extends ParentPage {
    @SpringBean
    IPartTimeJobRepository partTimeJobRepository;

    public PtjUpdatePage(PageParameters parameters) {
        int id = Integer.parseInt(parameters.get("id").toString());
        User user = MySession.get().getUser();
        Date ptjDate = Date.valueOf(parameters.get("date").toString());
        PartTimeJob originPtjRequest = partTimeJobRepository.find(id, user.getStudentNumber(), ptjDate);
        var editPanel = new PtjEditPanel("editPanel", parameters.get("date").toString(), originPtjRequest);
        add(editPanel);

    }
}
