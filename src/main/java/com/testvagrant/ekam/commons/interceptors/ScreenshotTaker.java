package com.testvagrant.ekam.commons.interceptors;

import com.testvagrant.ekam.commons.LayoutInitiator;

public class ScreenshotTaker {

  public void saveScreenshot() {
    LayoutInitiator.getInstance().captureScreenshot();
  }
}
