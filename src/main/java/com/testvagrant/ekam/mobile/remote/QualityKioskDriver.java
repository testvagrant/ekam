package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.config.CloudConfig;
import com.testvagrant.ekam.commons.parsers.testfeed.MobileConfigParser;
import com.testvagrant.ekam.devicemanager.devicefinder.PCloudyDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.CapabilityMapper;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QualityKioskDriver {
  private DesiredCapabilities desiredCapabilities;
  private CloudConfig cloudConfig;
  private DeviceFilters deviceFilters;
  private MobileConfigParser mobileConfigParser;

  public QualityKioskDriver(MobileConfigParser mobileConfigParser) {
    this.mobileConfigParser = mobileConfigParser;
    this.cloudConfig = new ConfigLoader().loadConfig(mobileConfigParser.getMobileConfig().getHub());
    this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    this.deviceFilters = mobileConfigParser.getDeviceFilters();
  }

  public QualityKioskDriver(MobileConfigParser mobileConfigParser, CloudConfig cloudConfig) {
    this.cloudConfig = cloudConfig;
    this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    this.deviceFilters = mobileConfigParser.getDeviceFilters();
  }

  public Triple<URL, DesiredCapabilities, TargetDetails> buildRemoteMobileConfig() {
    TargetDetails target = findTarget();
    String appUrl = uploadApp();
    Map<String, Object> qualityKioskCaps =
        new CapabilityMapper().mapToQualityKioskCaps(appUrl, cloudConfig.getUsername(), cloudConfig.getAccessKey(), target) ;
    return Triple.of(getUrl(), mergeCaps(qualityKioskCaps), target);
  }

  private URL getUrl() {
    return RemoteUrlBuilder.build(cloudConfig);
  }

  private DesiredCapabilities mergeCaps(Map<String, Object> updatedCaps) {
    desiredCapabilities.merge(new DesiredCapabilities(updatedCaps));
    Map<String, Object> stringObjectMap = desiredCapabilities.asMap();
    HashMap<String, Object> stringObjectHashMap = new HashMap<>(stringObjectMap);
    stringObjectHashMap.remove("app");
    return new DesiredCapabilities(stringObjectHashMap);
  }

  private TargetDetails findTarget() {
    return new PCloudyDeviceFinder(
            mobileConfigParser.getPlatform().name().toLowerCase(),
            mobileConfigParser.getDeviceFilters(),
            cloudConfig.getApiHost(),
            cloudConfig.getUsername(),
            cloudConfig.getAccessKey())
        .findDevice();
  }

  public String uploadApp() {
    if (!mobileConfigParser.getMobileConfig().isRemote()) return desiredCapabilities.getCapability("app").toString();
    String app =
        (String)
            mobileConfigParser.getDesiredCapabilities().getCapability(MobileCapabilityType.APP);
    boolean isAppPresent = !Objects.isNull(app) && !app.isEmpty();
    if (!mobileConfigParser.getMobileConfig().isUploadApp() || !isAppPresent)  return desiredCapabilities.getCapability("app").toString();
    return RemoteDriverUploadFactory.uploadUrl(mobileConfigParser.getMobileConfig().getHub(), app);
  }
}
