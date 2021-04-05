package com.testvagrant.ekam.commons.interceptors.web;

import com.google.inject.Inject;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotTaker {

  @Inject private WebDriver webDriver;

  @Attachment(value = "Screenshot", type = "image/png")
  public byte[] saveScreenshot() {
    return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
  }
}
