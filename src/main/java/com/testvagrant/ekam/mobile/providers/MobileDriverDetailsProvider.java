package com.testvagrant.ekam.mobile.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.devicemanager.devicefinder.LocalDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.drivers.mobile.MobileDriverManager;
import com.testvagrant.ekam.drivers.mobile.ServerManager;
import com.testvagrant.ekam.mobile.configparser.MobileConfigParser;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static com.testvagrant.ekam.mobile.remote.RemoteMobileDriverFactory.remoteMobileDriverFactory;

public class MobileDriverDetailsProvider implements Provider<MobileDriverDetails> {

  @Inject private EkamConfig ekam;

  @Override
  public MobileDriverDetails get() {
    MobileConfigParser mobileConfigParser = new MobileConfigParser(ekam.getMobile());
    return ekam.getMobile().isRemote()
        ? createRemoteDriver(mobileConfigParser)
        : createLocalDriver(mobileConfigParser);
  }

  private MobileDriverDetails createRemoteDriver(MobileConfigParser mobileConfigParser) {
    Triple<URL, DesiredCapabilities, TargetDetails> sessionDetails =
        remoteMobileDriverFactory(mobileConfigParser);
    URL remoteUrl = sessionDetails.getLeft();
    DesiredCapabilities desiredCapabilities = sessionDetails.getMiddle();
    TargetDetails targetDetails = sessionDetails.getRight();

    AppiumDriver<MobileElement> driver =
        new MobileDriverManager(remoteUrl, desiredCapabilities).createDriver();

    return MobileDriverDetails.builder()
        .driver(driver)
        .targetDetails(targetDetails)
        .capabilities(desiredCapabilities)
        .build();
  }

  private MobileDriverDetails createLocalDriver(MobileConfigParser mobileConfigParser) {
    TargetDetails availableDevice =
        new LocalDeviceFinder(
                mobileConfigParser.getPlatform(), mobileConfigParser.getDeviceFilters())
            .findDevice();

    DesiredCapabilities desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    DesiredCapabilities deviceCapabilities = new DesiredCapabilities(availableDevice.asMap());
    DesiredCapabilities capabilities = desiredCapabilities.merge(deviceCapabilities);

    AppiumDriverLocalService appiumDriverLocalService =
        new ServerManager().startService(mobileConfigParser.getServerArguments());
    AppiumDriver<MobileElement> driver =
        new MobileDriverManager(appiumDriverLocalService.getUrl(), capabilities).createDriver();

    return MobileDriverDetails.builder()
        .driver(driver)
        .targetDetails(availableDevice)
        .service(appiumDriverLocalService)
        .capabilities(capabilities)
        .build();
  }
}
