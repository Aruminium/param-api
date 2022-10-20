package com.example.wsbp.page.signed.ptj;

import com.example.wsbp.page.ParentPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class PtjEditCompPage extends ParentPage {
    public PtjEditCompPage(final String edit){
        Label label = new Label("edit", edit);
        add(label);

        var toCalenderLink = new BookmarkablePageLink<>("toCalender", PartTimeJobPage.class);
        add(toCalenderLink);
    }
}
