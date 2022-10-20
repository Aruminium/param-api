package com.example.wsbp.page;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;

public class ParentPage extends WebPage {
    @Override
    public void renderHead(IHeaderResponse response) {
        PackageResourceReference cssFile =
                new PackageResourceReference(ParentPage.class, "/css/main.css");
        CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);
        response.render(cssItem);

        PackageResourceReference cssFile2 =
                new PackageResourceReference(ParentPage.class, "/css/calendar.css");
        CssHeaderItem cssItem2 = CssHeaderItem.forReference(cssFile2);
        response.render(cssItem2);

        PackageResourceReference fontAwesomeCss =
                new PackageResourceReference(ParentPage.class, "/css/all.min.css");
        CssHeaderItem fontAwesomeItem = CssHeaderItem.forReference(fontAwesomeCss);
        response.render(fontAwesomeItem);

    }
}
