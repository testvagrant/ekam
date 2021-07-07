package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.parsers.testfeed.MobileConfigParser;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class RemoteMobileDriverFactory {

  public static Triple<URL, DesiredCapabilities, TargetDetails> getInstance(
      String hub, MobileConfigParser mobileConfigParser) {
    switch (hub) {
      case "browserstack":
        return new BrowserStackDriver(mobileConfigParser).buildRemoteMobileConfig();
      case "kobiton":
      case "saucelabs":
      default:
        throw new UnsupportedOperationException("Yet to support other remote clouds");
    }
  }
}
