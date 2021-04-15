package com.testvagrant.ekam.mobile.driver;

import com.google.inject.Provider;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.logs.AppiumLogRecorder;
import com.testvagrant.optimus.core.appium.DriverManager;
import com.testvagrant.optimus.core.model.MobileDriverDetails;
import com.testvagrant.optimus.core.parser.TestFeedParser;
import com.testvagrant.optimusCloud.remote.CloudConfigBuilder;
import com.testvagrant.optimusCloud.remote.drivers.RemoteCloudDriver;
import com.testvagrant.optimusCloud.remote.models.CloudConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.Timer;

public class DriverProvider implements Provider<MobileDriverDetails> {

  protected ThreadLocal<MobileDriverDetails> mobileDriverDetailsThreadLocal = new ThreadLocal<>();

  public MobileDriverDetails setupMobileDriver() {
    MobileDriverDetails mobileDriverDetails = new MobileDriverDetails();
    switch (SystemProperties.TARGET) {
      case REMOTE:
        mobileDriverDetails = createRemoteDriver(mobileDriverDetails);
        break;
      case OPTIMUS:
        throw new UnsupportedOperationException(
            "Support for optimus coming soon!! Please use REMOTE / LOCAL as targets");
      default:
        mobileDriverDetails = new DriverManager().createDriver();
        break;
    }
    mobileDriverDetailsThreadLocal.set(mobileDriverDetails);
    return mobileDriverDetailsThreadLocal.get();
  }

  private MobileDriverDetails createRemoteDriver(MobileDriverDetails mobileDriverDetails) {
    TestFeedParser testFeedParser = new TestFeedParser(SystemProperties.TESTFEED);
    DesiredCapabilities desiredCapabilities = testFeedParser.getDesiredCapabilities();
    CloudConfig build = new CloudConfigBuilder().build();
    try {
      AppiumDriver<MobileElement> mobileDriver =
          (AppiumDriver<MobileElement>)
              new RemoteCloudDriver().buildRemoteCloudDriver(build, desiredCapabilities);
      Thread.sleep(2000);
      mobileDriverDetails = buildMobileDriverDetails(mobileDriver, desiredCapabilities);
    } catch (MalformedURLException | InterruptedException e) {
      e.printStackTrace();
    }
    return mobileDriverDetails;
  }

  private MobileDriverDetails buildMobileDriverDetails(
      AppiumDriver<MobileElement> mobileDriver, DesiredCapabilities desiredCapabilities) {
    MobileDriverDetails mobileDriverDetails = new MobileDriverDetails();
    mobileDriverDetails.setDriver(mobileDriver);
    mobileDriverDetails.setCapabilities(desiredCapabilities);
    return mobileDriverDetails;
  }

  @Override
  public MobileDriverDetails get() {
    return setupMobileDriver();
  }

  private void captureLogs() {
    if (Toggles.LOGS.isOff()) {
      System.out.println("Logs are turned off");
      return;
    }
    Timer timer = new Timer();
    timer.schedule(
        new AppiumLogRecorder(mobileDriverDetailsThreadLocal.get().getDriver()), 0, 3000);
  }
}
