package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.config.CloudConfig;
import com.testvagrant.ekam.commons.parsers.testfeed.MobileConfigParser;
import com.testvagrant.ekam.devicemanager.devicefinder.BrowserStackDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.CapabilityMapper;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class BrowserStackDriver {

  private DesiredCapabilities desiredCapabilities;
  private CloudConfig cloudConfig;
  private DeviceFilters deviceFilters;
  private MobileConfigParser mobileConfigParser;

  public BrowserStackDriver(MobileConfigParser mobileConfigParser) {
    this.mobileConfigParser = mobileConfigParser;
    this.cloudConfig = new ConfigLoader().loadConfig("browserstack");
    this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    this.deviceFilters = mobileConfigParser.getDeviceFilters();
  }

  public BrowserStackDriver(MobileConfigParser mobileConfigParser, CloudConfig cloudConfig) {
    this.cloudConfig = cloudConfig;
    this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    this.deviceFilters = mobileConfigParser.getDeviceFilters();
  }

  public Triple<URL, DesiredCapabilities, TargetDetails> buildRemoteMobileConfig() {
    TargetDetails target = findTarget();
    String appUrl = uploadApp();
    Map<String, Object> browserStackCaps =
        new CapabilityMapper().mapToBrowserStackCaps(appUrl, target);
    return Triple.of(getUrl(), mergeCaps(browserStackCaps), target);
  }

  private URL getUrl() {
    return RemoteUrlBuilder.build(cloudConfig);
  }

  private DesiredCapabilities mergeCaps(Map<String, Object> updatedCaps) {
    desiredCapabilities.merge(new DesiredCapabilities(updatedCaps));
    return desiredCapabilities;
  }

  private TargetDetails findTarget() {
    return new BrowserStackDeviceFinder(
            mobileConfigParser.getPlatform().name().toLowerCase(),
            mobileConfigParser.getDeviceFilters(),
            cloudConfig.getUsername(),
            cloudConfig.getAccessKey())
        .findDevice();
  }

  public String uploadApp() {
    if (!mobileConfigParser.getMobileConfig().isRemote()) return "";
    String app =
        (String)
            mobileConfigParser.getDesiredCapabilities().getCapability(MobileCapabilityType.APP);
    boolean isAppPresent = !Objects.isNull(app) && !app.isEmpty();
    if (!mobileConfigParser.getMobileConfig().isUploadApp() || !isAppPresent) return "";
    return RemoteDriverUploadFactory.uploadUrl(mobileConfigParser.getMobileConfig().getHub(), app);
  }
}
