package com.example.wsbp.page.signed.ptj;

import com.example.wsbp.infrastructure.service.ISubjectService;
import com.example.wsbp.infrastructure.service.ptj.IPartTimeJobService;
import com.example.wsbp.page.ParentPage;
import com.example.wsbp.page.signed.ptj.common.PtjEditPanel;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * 学生がアルバイト内容を申請するために動くクラス
 */
@AuthorizeInstantiation(Roles.USER)
@MountPath("Request")
public class PtjInsertPage extends ParentPage {

    @SpringBean
    private IPartTimeJobService partTimeJobService;
    @SpringBean
    private ISubjectService subjectService;

    public PtjInsertPage(PageParameters parameters) {
        super();
        //ログイン情報から取得
        var editPanel = new PtjEditPanel("editPanel", parameters.get("date").toString());
        add(editPanel);
    }
}
