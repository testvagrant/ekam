package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.remote.ConfigLoader;
import com.testvagrant.ekam.commons.remote.RemoteUrlBuilder;
import com.testvagrant.ekam.commons.remote.models.CloudConfig;
import com.testvagrant.ekam.devicemanager.devicefinder.PCloudyDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.mobile.configparser.MobileConfigParser;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QualityKioskDriver {
  private final DesiredCapabilities desiredCapabilities;
  private final CloudConfig cloudConfig;
  private final MobileConfigParser mobileConfigParser;

  public QualityKioskDriver(MobileConfigParser mobileConfigParser) {
    this.mobileConfigParser = mobileConfigParser;
    this.cloudConfig = new ConfigLoader().loadConfig(mobileConfigParser.getMobileConfig().getHub());
    this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
  }

  public Triple<URL, DesiredCapabilities, TargetDetails> buildRemoteMobileConfig() {
    TargetDetails target = findTarget();
    String appUrl = uploadApp();
    URL url = RemoteUrlBuilder.build(cloudConfig);
    Map<String, Object> qualityKioskCaps =
        mapToQualityKioskCaps(
            appUrl, cloudConfig.getUsername(), cloudConfig.getAccessKey(), target);
    return Triple.of(url, mergeCaps(qualityKioskCaps), target);
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
            mobileConfigParser.getPlatform(),
            mobileConfigParser.getDeviceFilters(),
            cloudConfig.getApiHost(),
            cloudConfig.getUsername(),
            cloudConfig.getAccessKey())
        .findDevice();
  }

  public String uploadApp() {
    if (!mobileConfigParser.getMobileConfig().isRemote())
      return desiredCapabilities.getCapability("app").toString();
    String app =
        (String)
            mobileConfigParser.getDesiredCapabilities().getCapability(MobileCapabilityType.APP);
    boolean isAppPresent = !Objects.isNull(app) && !app.isEmpty();
    if (!mobileConfigParser.getMobileConfig().isUploadApp() || !isAppPresent)
      return desiredCapabilities.getCapability("app").toString();
    return RemoteDriverUploadFactory.uploadUrl(mobileConfigParser.getMobileConfig().getHub(), app);
  }

  private Map<String, Object> mapToQualityKioskCaps(
      String appUrl, String userName, String accessKey, TargetDetails targetDetails) {
    Map<String, Object> pCloudyCaps = mapToQualityKioskCaps(userName, accessKey, targetDetails);
    if (!appUrl.isEmpty()) pCloudyCaps.put("Capability_ApplicationName", appUrl);
    return pCloudyCaps;
  }

  private Map<String, Object> mapToQualityKioskCaps(
      String userName, String accessKey, TargetDetails targetDetails) {
    Map<String, Object> capsMap = new HashMap<>();
    capsMap.put("Capability_Username", userName);
    capsMap.put("Capability_ApiKey", accessKey);
    capsMap.put("Capability_DeviceFullName", targetDetails.getName());
    return capsMap;
  }
}
