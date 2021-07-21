package com.testvagrant.ekam.commons.interceptors;

import static com.testvagrant.ekam.commons.LayoutInitiator.layoutInitiator;

public class ScreenshotTaker {

  public void saveScreenshot() {
    layoutInitiator().captureScreenshot();
  }
}
