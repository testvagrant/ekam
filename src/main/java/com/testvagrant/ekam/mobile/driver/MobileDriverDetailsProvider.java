package com.testvagrant.ekam.mobile.driver;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.commons.parsers.testfeed.MobileConfigParser;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.devicemanager.devicefinder.LocalDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.drivers.mobile.MobileDriverManager;
import com.testvagrant.ekam.drivers.mobile.ServerManager;
import com.testvagrant.ekam.mobile.remote.RemoteMobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class MobileDriverDetailsProvider implements Provider<MobileDriverDetails> {

  protected ThreadLocal<MobileDriverDetails> mobileDriverDetailsThreadLocal = new ThreadLocal<>();

  @Inject EkamConfig ekam;

  public MobileDriverDetails setupMobileDriver() {
    return ekam.getMobile().isRemote() ? createRemoteDriver() : createLocalDriver();
  }

  private MobileConfigParser getMobileConfigParser() {
    MobileConfigParser mobileConfigParser = new MobileConfigParser(ekam.getMobile());
    return mobileConfigParser;
  }

  private MobileDriverDetails createRemoteDriver() {
    Triple<URL, DesiredCapabilities, TargetDetails> sessionDetails =
        RemoteMobileDriverFactory.getInstance(ekam.getMobile().getHub(), getMobileConfigParser());
    AppiumDriver<MobileElement> driver =
        new MobileDriverManager(sessionDetails.getLeft(), sessionDetails.getMiddle())
            .createDriver();
    return MobileDriverDetails.builder()
        .driver(driver)
        .targetDetails(sessionDetails.getRight())
        .capabilities(sessionDetails.getMiddle())
        .build();
  }

  private MobileDriverDetails createLocalDriver() {
    MobileConfigParser mobileConfigParser = getMobileConfigParser();
    DesiredCapabilities desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    LocalDeviceFinder localDeviceFinder =
        new LocalDeviceFinder(
            mobileConfigParser.getPlatform().name().toLowerCase(),
            mobileConfigParser.getDeviceFilters());
    TargetDetails availableDevice = localDeviceFinder.findDevice();
    DesiredCapabilities capabilities =
        desiredCapabilities.merge(new DesiredCapabilities(availableDevice.asMap()));
    AppiumDriverLocalService appiumDriverLocalService =
        new ServerManager().startService(mobileConfigParser.getServerArgumentsMap());
    AppiumDriver<MobileElement> driver =
        new MobileDriverManager(appiumDriverLocalService.getUrl(), capabilities).createDriver();

    return MobileDriverDetails.builder()
        .driver(driver)
        .targetDetails(availableDevice)
        .service(appiumDriverLocalService)
        .capabilities(capabilities)
        .build();
  }

  @Override
  public MobileDriverDetails get() {
    return setupMobileDriver();
  }
}
