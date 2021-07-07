package com.testvagrant.ekam.mobile;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.mobile.Element;
import com.testvagrant.ekam.atoms.mobile.ElementCollection;
import com.testvagrant.ekam.atoms.mobile.QueryFunctions;
import com.testvagrant.ekam.atoms.mobile.Textbox;
import com.testvagrant.ekam.atoms.mobile.android.AndroidDeviceDriver;
import com.testvagrant.ekam.atoms.mobile.ios.IOSDeviceDriver;
import com.testvagrant.ekam.reports.ReportLogger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;

// TODO:: Implement annotations and remove query functions
public abstract class MobileScreen extends QueryFunctions {

  @Inject protected AppiumDriver<MobileElement> driver;
  @Inject protected AndroidDeviceDriver androidDeviceDriver;
  @Inject protected IOSDeviceDriver iosDeviceDriver;

  protected Element element(By locator) {
    return new Element(driver, locator);
  }

  protected Textbox textbox(By locator) {
    return (Textbox) new Textbox(driver, locator);
  }

  protected ElementCollection elementCollection(By locator) {
    return new ElementCollection(driver, locator);
  }

  public void log(String message) {
    ReportLogger.log(message);
  }

  protected <T extends MobileScreen> T createInstance(Class<T> clazz) {
    return Screen(clazz);
  }
}
