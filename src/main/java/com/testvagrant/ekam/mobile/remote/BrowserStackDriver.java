package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.remote.ConfigLoader;
import com.testvagrant.ekam.commons.remote.RemoteUrlBuilder;
import com.testvagrant.ekam.commons.remote.models.CloudConfig;
import com.testvagrant.ekam.devicemanager.devicefinder.BrowserStackDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.CapabilityMapper;
import com.testvagrant.ekam.mobile.configparser.MobileConfigParser;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Map;
import java.util.Objects;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class BrowserStackDriver {

  private final DesiredCapabilities desiredCapabilities;
  private final CloudConfig cloudConfig;
  private final MobileConfigParser mobileConfigParser;

  public BrowserStackDriver(MobileConfigParser mobileConfigParser) {
    this.mobileConfigParser = mobileConfigParser;
    this.cloudConfig = new ConfigLoader().loadConfig("browserstack");
    this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
  }

  public Triple<URL, DesiredCapabilities, TargetDetails> buildRemoteMobileConfig() {
    TargetDetails target = findTarget();
    ekamLogger().info("Found remote target {}", target);
    String appUrl = uploadApp();
    URL url = RemoteUrlBuilder.build(cloudConfig);

    Map<String, Object> browserStackCaps =
        new CapabilityMapper().mapToBrowserStackCaps(appUrl, target);
    DesiredCapabilities updatedCapabilities =
        desiredCapabilities.merge(new DesiredCapabilities(browserStackCaps));
    ekamLogger().info("Building remote capabilities {}", updatedCapabilities);
    return Triple.of(url, updatedCapabilities, target);
  }

  private String uploadApp() {
    if (!mobileConfigParser.getMobileConfig().isRemote()) return "";
    String app =
        (String)
            mobileConfigParser.getDesiredCapabilities().getCapability(MobileCapabilityType.APP);
    boolean isAppPresent = !Objects.isNull(app) && !app.isEmpty();
    if (!mobileConfigParser.getMobileConfig().isUploadApp() || !isAppPresent) return "";
    return RemoteDriverUploadFactory.uploadUrl(mobileConfigParser.getMobileConfig().getHub(), app);
  }

  private TargetDetails findTarget() {
    return new BrowserStackDeviceFinder(
            mobileConfigParser.getPlatform(),
            mobileConfigParser.getDeviceFilters(),
            cloudConfig.getUsername(),
            cloudConfig.getAccessKey())
        .findDevice();
  }
}
