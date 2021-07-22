package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.mobile.configparser.MobileConfigParser;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static com.testvagrant.ekam.commons.remote.constants.Hub.*;

public class RemoteMobileDriverFactory {

  public static Triple<URL, DesiredCapabilities, TargetDetails> remoteMobileDriverFactory(
      MobileConfigParser mobileConfigParser) {
    String hub = mobileConfigParser.getMobileConfig().getHub();
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
        throw new UnsupportedOperationException(String.format("'%s' not supported", hub));
    }
  }
}
