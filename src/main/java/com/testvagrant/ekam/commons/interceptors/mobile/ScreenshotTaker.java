package com.testvagrant.ekam.commons.interceptors.mobile;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotTaker {

  @Inject private AppiumDriver<MobileElement> driver;

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] saveScreenshot() {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }
}
