package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.parsers.testfeed.MobileConfigParser;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static com.testvagrant.ekam.commons.constants.Hubs.*;

public class RemoteMobileDriverFactory {

  public static Triple<URL, DesiredCapabilities, TargetDetails> getInstance(
      String hub, MobileConfigParser mobileConfigParser) {
    switch (hub) {
      case BROWSERSTACK:
        return new BrowserStackDriver(mobileConfigParser).buildRemoteMobileConfig();
      case P_CLOUDY:
        return new PCloudyDriver(mobileConfigParser).buildRemoteMobileConfig();
      case QUALITY_KIOSK:
        return new QualityKioskDriver(mobileConfigParser).buildRemoteMobileConfig();
      case KOBITON:
      case SAUCE_LABS:
      default:
        throw new UnsupportedOperationException("Yet to support other remote clouds");
    }
  }
}
