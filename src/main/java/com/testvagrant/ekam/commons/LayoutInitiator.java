package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.dashboard.StepRecorder;
import com.testvagrant.ekam.dashboard.models.Step;
import com.testvagrant.ekam.internal.executiontimeline.EkamTestScreenshotTaker;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.nio.file.Path;
import java.time.Duration;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class LayoutInitiator {

  public static LayoutInitiator layoutInitiator() {
    return new LayoutInitiator();
  }

  public static <Page> Page Page(Class<Page> tPage) {
    return layoutInitiator().createWebLayout(tPage);
  }

  public static <Activity> Activity Screen(Class<Activity> tActivity) {
    return layoutInitiator().createMobileLayout(tActivity);
  }

  public static <Client> Client Client(Class<Client> tClient) {
    return layoutInitiator().createAPILayout(tClient);
  }

  public Path captureScreenshot() {
    EkamTestScreenshotTaker screenshotTaker =
        getInjector().getInstance(EkamTestScreenshotTaker.class);
    return screenshotTaker.captureScreenshot();
  }

  public void addStep(Step step) {
    StepRecorder stepRecorder = getInjector().getInstance(StepRecorder.class);
    stepRecorder.addStep(step);
  }

  private <Page> Page createWebLayout(Class<Page> tPage) {
    Injector pageInjector = getInjector();
    WebDriver webDriver = pageInjector.getInstance(WebDriver.class);
    Page instance = pageInjector.getInstance(tPage);
    PageFactory.initElements(webDriver, instance);
    return instance;
  }

  private <Activity> Activity createMobileLayout(Class<Activity> tActivity) {
    Injector activityInjector = getInjector();
    MobileDriverDetails mobileDriverDetails =
        activityInjector.getInstance(MobileDriverDetails.class);
    FieldDecorator fieldDecorator =
        new AppiumFieldDecorator(mobileDriverDetails.getDriver(), Duration.ofSeconds(30));
    PageFactory.initElements(fieldDecorator, this);
    Activity instance = activityInjector.getInstance(tActivity);
    return instance;
  }

  private <Client> Client createAPILayout(Class<Client> tClient) {
    Client instance = getInjector().getInstance(tClient);
    return instance;
  }

  private Injector getInjector() {
    return injectorsCache().getInjector();
  }
}
