package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.mobile.android.AndroidDeviceDriver;
import com.testvagrant.ekam.atoms.mobile.android.AndroidQueryFunctions;
import com.testvagrant.ekam.atoms.mobile.ios.IOSDeviceDriver;
import com.testvagrant.ekam.atoms.mobile.ios.IOSQueryFunctions;
import com.testvagrant.ekam.reports.ReportLogger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;

public abstract class MobileScreen implements AndroidQueryFunctions, IOSQueryFunctions {

  @Inject protected AppiumDriver<MobileElement> driver;

  @Inject protected AndroidDeviceDriver androidDeviceDriver;

  @Inject protected IOSDeviceDriver iosDeviceDriver;

  protected EkamMobileElement element(By locator) {
    return new EkamMobileElement(driver).locate(locator);
  }

  protected EkamMobileElementCollection elementCollection(By locator) {
    return new EkamMobileElementCollection(driver).locate(locator);
  }

  public void log(String message) {
    ReportLogger.log(message);
  }

  protected <T extends MobileScreen> T createInstance(Class<T> clazz) {
    return Screen(clazz);
  }
}
