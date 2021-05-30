package ru.roboforex.core;

import ru.roboforex.core.annotatons.Page;

public class WebPage extends WebObject {

    public WebPage() {
    }

    public String getUrlPart() {
        return this.getClass().getAnnotation(Page.class).url();
    }

    @Override
    public String getName() {
        return this.getClass().getAnnotation(Page.class).name();
    }

}

