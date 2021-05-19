package com.testvagrant.ekam.commons;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import com.testvagrant.ekam.atoms.web.WebPage;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.injectors.Injectors;
import com.testvagrant.ekam.commons.testContext.EkamTestContextConverter;
import com.testvagrant.optimus.core.models.mobile.MobileDriverDetails;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import com.testvagrant.optimus.dashboard.StepRecorder;
import com.testvagrant.optimus.dashboard.models.Step;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.testng.Reporter;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Objects;

public class LayoutInitiator {

    public static LayoutInitiator getInstance() {
        return new LayoutInitiator();
    }

    public static <Page extends WebPage> Page Page(Class<Page> tPage) {
        return getInstance().createWebLayout(tPage);
    }

    public static <Activity extends MobileScreen> Activity Screen(Class<Activity> tActivity) {
        return getInstance().createMobileLayout(tActivity);
    }

    public static <Client extends RetrofitBaseClient> Client Client(Class<Client> tClient) {
        return getInstance().createAPILayout(tClient);
    }

    public Path captureScreenshot() {
        OptimusRunTarget optimusRunTarget = getInjector().getInstance(OptimusRunTarget.class);
        return optimusRunTarget.captureScreenshot();
    }

    public Path captureScreenshot(Injectors injector) {
        OptimusRunTarget optimusRunTarget = getInjector(injector).getInstance(OptimusRunTarget.class);
        return optimusRunTarget.captureScreenshot();
    }

    public Path captureWebScreenshot(Injectors injector) {
        OptimusRunTarget optimusRunTarget = getInjector(injector).getInstance(OptimusRunTarget.class);
        return optimusRunTarget.captureWebScreenshot();
    }

    public Path captureMobileScreenshot(Injectors injector) {
        OptimusRunTarget optimusRunTarget = getInjector(injector).getInstance(OptimusRunTarget.class);
        return optimusRunTarget.captureMobileScreenshot();
    }

    public void addStep(Step step) {
        StepRecorder stepRecorder = getInjector().getInstance(StepRecorder.class);
        stepRecorder.addStep(step);
    }

    public void addStep(Step step, Injectors injector) {
        StepRecorder stepRecorder = null;
        try {
            stepRecorder = getInjector(injector).getInstance(StepRecorder.class);
            stepRecorder.addStep(step);
        } catch (ConfigurationException e) {
           // Ignore adding steps to optimus dashboard
        }
    }

    private <Page extends WebPage> Page createWebLayout(Class<Page> tPage) {
        Injector pageInjector = getInjector(Injectors.WEB_PAGE_INJECTOR);
        WebDriver webDriver = pageInjector.getInstance(WebDriver.class);
        Page instance = pageInjector.getInstance(tPage);
        PageFactory.initElements(webDriver, instance);
        return instance;
    }

    @SuppressWarnings("unchecked")
    private <Activity extends MobileScreen> Activity createMobileLayout(Class<Activity> tActivity) {
        Injector activityInjector = getInjector(Injectors.MOBILE_PAGE_INJECTOR);
        MobileDriverDetails mobileDriverDetails =
                activityInjector.getInstance(MobileDriverDetails.class);
        FieldDecorator fieldDecorator =
                new AppiumFieldDecorator(mobileDriverDetails.getDriver(), Duration.ofSeconds(30));
        PageFactory.initElements(fieldDecorator, this);
        return activityInjector.getInstance(tActivity);
    }

    private <Client extends RetrofitBaseClient> Client createAPILayout(Class<Client> tClient) {
        return getInjector(Injectors.API_INJECTOR).getInstance(tClient);
    }

    private Injector getInjector() {
        Injector webInjector = getInjector(Injectors.WEB_PAGE_INJECTOR);
        Injector mobileInjector = getInjector(Injectors.MOBILE_PAGE_INJECTOR);
        return Objects.isNull(webInjector) ? mobileInjector : webInjector;
    }

    private Injector getInjector(Injectors injector) {
        Injector currentTestContextInjector = InjectorsCacheProvider.getInstance()
                .get(
                        injector.getInjector(
                                EkamTestContextConverter.convert(Reporter.getCurrentTestResult()).hashCode()));
        return Objects.isNull(currentTestContextInjector)
                ? Reporter.getCurrentTestResult().getTestContext().getSuite().getParentInjector()
                : currentTestContextInjector;
    }
}
