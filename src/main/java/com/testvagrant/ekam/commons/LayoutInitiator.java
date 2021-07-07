package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.commons.runcontext.EkamRunTarget;
import com.testvagrant.ekam.dashboard.StepRecorder;
import com.testvagrant.ekam.dashboard.models.Step;
import com.testvagrant.ekam.mobile.MobileScreen;
import com.testvagrant.ekam.web.WebPage;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.nio.file.Path;
import java.time.Duration;

@SuppressWarnings("unchecked")
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

  public static <Client> Client Client(Class<Client> tClient) {
    return getInstance().createAPILayout(tClient);
  }

  public Path captureScreenshot() {
    EkamRunTarget ekamRunTarget = getInjector().getInstance(EkamRunTarget.class);
    return ekamRunTarget.captureScreenshot();
  }

  public Path captureWebScreenshot() {
    EkamRunTarget ekamRunTarget = getInjector().getInstance(EkamRunTarget.class);
    return ekamRunTarget.captureWebScreenshot();
  }

  public Path captureMobileScreenshot() {
    EkamRunTarget ekamRunTarget = getInjector().getInstance(EkamRunTarget.class);
    return ekamRunTarget.captureMobileScreenshot();
  }

  public void addStep(Step step) {
    StepRecorder stepRecorder = getInjector().getInstance(StepRecorder.class);
    stepRecorder.addStep(step);
  }

  private <Page extends WebPage> Page createWebLayout(Class<Page> tPage) {
    Injector pageInjector = getInjector();
    WebDriver webDriver = pageInjector.getInstance(WebDriver.class);
    Page instance = pageInjector.getInstance(tPage);
    PageFactory.initElements(webDriver, instance);
    return instance;
  }

  private <Activity extends MobileScreen> Activity createMobileLayout(Class<Activity> tActivity) {
    Injector activityInjector = getInjector();
    MobileDriverDetails mobileDriverDetails =
        activityInjector.getInstance(MobileDriverDetails.class);
    FieldDecorator fieldDecorator =
        new AppiumFieldDecorator(mobileDriverDetails.getDriver(), Duration.ofSeconds(30));
    PageFactory.initElements(fieldDecorator, this);
    return activityInjector.getInstance(tActivity);
  }

  private <Client> Client createAPILayout(Class<Client> tClient) {
    return getInjector().getInstance(tClient);
  }

  private Injector getInjector() {
    return InjectorsCacheProvider.getInstance().get();
  }
}
