package com.testvagrant.ekam.mobile.driver;

import com.google.inject.Provider;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.logs.AppiumLogRecorder;
import com.testvagrant.optimusCloud.remote.CloudConfigBuilder;
import com.testvagrant.optimusCloud.remote.drivers.RemoteCloudDriver;
import com.testvagrant.optimusCloud.remote.models.CloudConfig;
import com.testvagrant.optimuscloud.dashboard.testng.builders.MobileDriverDetailsBuilder;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import com.testvagrant.optimuscloud.local.OptimusLocalDriver;
import com.testvagrant.optimuscloud.remote.OptimusCloudDriver;
import com.testvagrant.optimuscloud.utils.TestFeedToDesiredCapConverter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Timer;

public class DriverProvider implements Provider<MobileDriverDetails> {

  protected ThreadLocal<MobileDriverDetails> mobileDriverDetailsThreadLocal = new ThreadLocal<>();

  public MobileDriverDetails setupMobileDriver() {
    String testFeed = SystemProperties.TESTFEED;
    TestFeedToDesiredCapConverter testFeedToDesiredCapConverter =
        new TestFeedToDesiredCapConverter(testFeed);
    List<DesiredCapabilities> desiredCapabilities = testFeedToDesiredCapConverter.convert();
    MobileDriverDetails mobileDriverDetails = createDriver(desiredCapabilities);
    mobileDriverDetailsThreadLocal.set(mobileDriverDetails);
    captureLogs();
    return mobileDriverDetailsThreadLocal.get();
  }

  private MobileDriverDetails createDriver(List<DesiredCapabilities> desiredCapabilitiesList) {
    DesiredCapabilities desiredCapabilities = updateCapabilities(desiredCapabilitiesList.get(0));
    switch (SystemProperties.TARGET) {
      case LOCAL:
        return new OptimusLocalDriver().createDriver(desiredCapabilities);
      case OPTIMUS:
        return new OptimusCloudDriver().createDriver(desiredCapabilities);
      case REMOTE:
        String updatedAppPath =
            desiredCapabilities
                .getCapability("app")
                .toString()
                .replace(System.getProperty("user.dir") + "/app/", "");
        desiredCapabilities.setCapability("app", updatedAppPath);
        CloudConfig build = new CloudConfigBuilder().build();
        try {
          MobileDriver mobileDriver =
              new RemoteCloudDriver().buildRemoteCloudDriver(build, desiredCapabilities);
          return buildMobileDriverDetails(mobileDriver, desiredCapabilities);
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
    }
    throw new RuntimeException("Cannot create driver");
  }

  private MobileDriverDetails buildMobileDriverDetails(
      MobileDriver mobileDriver, DesiredCapabilities desiredCapabilities) {
    return new MobileDriverDetailsBuilder()
        .withMobileDriver(mobileDriver)
        .withDesiredCapabilities(desiredCapabilities)
        .build();
  }

  @Override
  public MobileDriverDetails get() {
    setupMobileDriver();
    return mobileDriverDetailsThreadLocal.get();
  }

  private DesiredCapabilities updateCapabilities(DesiredCapabilities desiredCapabilities) {
    if (desiredCapabilities.getCapability("automationName") != null
        && desiredCapabilities
            .getCapability("automationName")
            .toString()
            .equalsIgnoreCase("UIAutomator2")) {
      desiredCapabilities.setCapability("systemPort", findRandomOpenPortOnAllLocalInterfaces());
    }
    desiredCapabilities.setCapability("clearDeviceLogsOnStart", true);
    return desiredCapabilities;
  }

  private Integer findRandomOpenPortOnAllLocalInterfaces() {
    try (ServerSocket socket = new ServerSocket(0); ) {
      return socket.getLocalPort();

    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Cannot find open port");
  }

  private void captureLogs() {
    if (Toggles.LOGS.isOff()) {
      System.out.println("Logs are turned off");
      return;
    }
    Timer timer = new Timer();
    timer.schedule(
        new AppiumLogRecorder(
            (AppiumDriver<MobileElement>) mobileDriverDetailsThreadLocal.get().getMobileDriver()),
        0,
        3000);
  }
}
