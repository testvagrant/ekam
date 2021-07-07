package com.testvagrant.ekam.web;

import org.awaitility.Awaitility;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class WebLauncher {

  public void launch(String url, WebDriver webDriver) {
    webDriver.get(url.trim());
    for (int retry = 0; retry < 3; retry++) {
      try {
        Awaitility.await()
            .atMost(Duration.ofMinutes(1))
            .until(() -> !webDriver.getTitle().isEmpty());
        break;
      } catch (Exception e) {
        webDriver.navigate().refresh();
      }
    }
  }
}
