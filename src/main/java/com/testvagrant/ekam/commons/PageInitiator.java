package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.atoms.base.BaseComponent;
import com.testvagrant.ekam.atoms.base.BasePage;
import org.testng.Reporter;

public class PageInitiator {

    //Sugar coated methods for readability

    public static <T extends BasePage> T getWebPage(Class<T> tPage) {
        return getPageInstance(tPage, Injectors.WEB_PAGE_INJECTOR);
    }

    public static <T extends BaseComponent> T getWebComponent(Class<T> tComponent) {
        return getPageInstance(tComponent, Injectors.WEB_PAGE_INJECTOR);
    }

    public static <T extends BaseComponent> T getMobileComponent(Class<T> tComponent) {
        return getPageInstance(tComponent, Injectors.MOBILE_PAGE_INJECTOR);
    }

    public static <T extends BaseComponent> T getActivity(Class<T> tActivity) {
        return getPageInstance(tActivity, Injectors.MOBILE_PAGE_INJECTOR);
    }

    public static <T extends BaseComponent> T getView(Class<T> tView) {
        return getPageInstance(tView, Injectors.MOBILE_PAGE_INJECTOR);
    }

    public static <T extends BasePage> T getPageInstance(Class<T> tClass, Injectors injectorType) {
        Injector pageInjector = (Injector) Reporter.getCurrentTestResult()
                .getAttribute(injectorType.getInjector());
        T page = pageInjector.getInstance(tClass);
        return (T) page.init(page);
    }


}
