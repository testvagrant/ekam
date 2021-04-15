package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.atoms.mobile.android.AndroidDeviceDriver;
import com.testvagrant.ekam.atoms.mobile.android.AndroidQueryFunctions;
import com.testvagrant.ekam.atoms.mobile.ios.IOSDeviceDriver;
import com.testvagrant.ekam.atoms.mobile.ios.IOSQueryFunctions;
import com.testvagrant.ekam.reports.ReportLogger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import static com.testvagrant.ekam.commons.ActivityInitiator.Activity;

public abstract class MobileScreen implements AndroidQueryFunctions, IOSQueryFunctions {

  @Inject
  @Named("persona")
  private String persona;

  @Inject protected AppiumDriver<MobileElement> driver;

  @Inject protected AndroidDeviceDriver androidDeviceDriver;

  @Inject protected IOSDeviceDriver iosDeviceDriver;

  protected EkamMobileElement element(By locator) {
    return new EkamMobileElement(driver).locate(locator);
  }

  protected Textbox textbox(By locator) {
    return (Textbox) new Textbox(driver).locate(locator);
  }

  public void log(String message) {
    ReportLogger.log(persona, message);
  }

  protected <T extends MobileScreen> T createInstance(Class<T> tClazz) {
    return Activity().getInstance(tClazz);
  }
}
