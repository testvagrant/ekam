package com.testvagrant.ekam.mobile.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.commons.path.PathBuilder;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.devicemanager.devicefinder.LocalDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.drivers.mobile.MobileDriverManager;
import com.testvagrant.ekam.drivers.mobile.ServerManager;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.mobile.configparser.MobileConfigParser;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;
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
    ekamLogger().info("Creating remote driver");
    Triple<URL, DesiredCapabilities, TargetDetails> sessionDetails =
        remoteMobileDriverFactory(mobileConfigParser);
    URL remoteUrl = sessionDetails.getLeft();
    DesiredCapabilities desiredCapabilities = sessionDetails.getMiddle();
    TargetDetails targetDetails = sessionDetails.getRight();

    AppiumDriver<MobileElement> driver =
        new MobileDriverManager(remoteUrl, desiredCapabilities).createDriver();

    return MobileDriverDetails.builder()
        .driver(driver)
        .targetDetails(targetDetails) // TODO: Create Target Details from driver object
        .capabilities(desiredCapabilities)
        .build();
  }

  private MobileDriverDetails createLocalDriver(MobileConfigParser mobileConfigParser) {
    ekamLogger().info("Creating local driver");
    TargetDetails availableDevice =
        new LocalDeviceFinder(
                mobileConfigParser.getPlatform(), mobileConfigParser.getDeviceFilters())
            .findDevice();
    ekamLogger().info("Found available device {}", availableDevice);
    DesiredCapabilities desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    DesiredCapabilities deviceCapabilities = new DesiredCapabilities(availableDevice.asMap());
    DesiredCapabilities capabilities = desiredCapabilities.merge(deviceCapabilities);

    EkamTest ekamTest = injectorsCache().getInjector().getInstance(EkamTest.class);
    String logFilePath =
        new PathBuilder(ResourcePaths.getTestPath(ekamTest.getFeature(), ekamTest.getScenario()))
            .append("logs")
            .append("appium.log")
            .toString();

    AppiumDriverLocalService appiumDriverLocalService =
        new ServerManager().startService(mobileConfigParser.getServerArguments(), logFilePath);
    AppiumDriver<MobileElement> driver =
        new MobileDriverManager(appiumDriverLocalService.getUrl(), capabilities).createDriver();

    ekamLogger().info("Created local driver");
    return MobileDriverDetails.builder()
        .driver(driver)
        .targetDetails(availableDevice)
        .service(appiumDriverLocalService)
        .capabilities(capabilities)
        .build();
  }
}
