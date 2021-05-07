package com.testvagrant.ekam.commons;

import com.google.gson.reflect.TypeToken;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import com.testvagrant.ekam.atoms.web.WebPage;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import com.testvagrant.optimus.dashboard.App;
import com.testvagrant.optimus.dashboard.StepRecorder;
import com.testvagrant.optimus.dashboard.models.Step;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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

  public Path captureScreenshot() {
    OptimusRunTarget optimusRunTarget = getInjector().getInstance(OptimusRunTarget.class);
    return optimusRunTarget.captureScreenshot();
  }

  public void addStep(Step step) {
    StepRecorder stepRecorder = getInjector().getInstance(StepRecorder.class);
    stepRecorder.addStep(step);
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
    AppiumDriver<MobileElement> appiumDriver = activityInjector.getInstance(AppiumDriver.class);
    FieldDecorator fieldDecorator = new AppiumFieldDecorator(appiumDriver, Duration.ofSeconds(30));
    PageFactory.initElements(fieldDecorator, this);
    return activityInjector.getInstance(tActivity);
  }

  private Injector getInjector() {
    Injector webInjector = (Injector)
            Reporter.getCurrentTestResult().getAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector());
    Injector mobileInjector = (Injector)
            Reporter.getCurrentTestResult().getAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector());
    return Objects.isNull(webInjector)? mobileInjector: webInjector;
  }

  private Injector getInjector(Injectors injector) {
    return (Injector)
            Reporter.getCurrentTestResult().getAttribute(injector.getInjector());
  }
}
