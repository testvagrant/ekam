package com.testvagrant.ekam.mobile;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.mobile.*;
import com.testvagrant.ekam.atoms.mobile.android.AndroidDeviceDriver;
import com.testvagrant.ekam.atoms.mobile.ios.IOSDeviceDriver;
import com.testvagrant.ekam.reports.allure.ReportLogger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;

public abstract class MobileScreen extends QueryFunctions {

  @Inject protected AppiumDriver<MobileElement> driver;

  @Inject protected AndroidDeviceDriver androidDeviceDriver;

  @Inject protected IOSDeviceDriver iosDeviceDriver;

  protected Element element(By locator) {
    return new Element(driver, locator);
  }

  protected Element element(Finder finder) {
    return new Element(driver, finder);
  }

  protected Textbox textbox(By locator) {
    return new Textbox(driver, locator);
  }

  protected Textbox textbox(Finder finder) {
    return new Textbox(driver, finder);
  }

  protected ElementCollection elementCollection(By locator) {
    return new ElementCollection(driver, locator);
  }

  protected ElementCollection elementCollection(Finder finder) {
    return new ElementCollection(driver, finder);
  }

  protected Finder finder(By androidFindBy, By iosFindBy) {
    return Finder.builder().androidFindBy(androidFindBy).iosFindBy(iosFindBy).build();
  }

  public void log(String message) {
    ReportLogger.log(message);
  }

  protected <T extends MobileScreen> T createInstance(Class<T> clazz) {
    return Screen(clazz);
  }
}
