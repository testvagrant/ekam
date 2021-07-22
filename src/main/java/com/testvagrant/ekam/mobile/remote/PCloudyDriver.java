package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.remote.models.CloudConfig;
import com.testvagrant.ekam.devicemanager.devicefinder.PCloudyDeviceFinder;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.CapabilityMapper;
import com.testvagrant.ekam.mobile.configparser.MobileConfigParser;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.tuple.Triple;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PCloudyDriver {

  private final DesiredCapabilities desiredCapabilities;
  private final CloudConfig cloudConfig;
  private MobileConfigParser mobileConfigParser;

  public PCloudyDriver(MobileConfigParser mobileConfigParser) {
    this.mobileConfigParser = mobileConfigParser;
    this.cloudConfig = new ConfigLoader().loadConfig(mobileConfigParser.getMobileConfig().getHub());
    this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
  }

  public PCloudyDriver(MobileConfigParser mobileConfigParser, CloudConfig cloudConfig) {
    this.cloudConfig = cloudConfig;
    this.desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
  }

  public Triple<URL, DesiredCapabilities, TargetDetails> buildRemoteMobileConfig() {
    TargetDetails target = findTarget();
    String appUrl = uploadApp();
    Map<String, Object> pCloudyCaps =
        new CapabilityMapper()
            .mapToPCloudyCaps(
                appUrl, cloudConfig.getUsername(), cloudConfig.getAccessKey(), target);
    return Triple.of(getUrl(), mergeCaps(pCloudyCaps), target);
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
}
