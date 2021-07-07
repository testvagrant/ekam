package com.testvagrant.ekam.commons.parsers.testfeed;

import com.testvagrant.ekam.commons.generators.PortGenerator;
import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.commons.models.caps.AndroidOnlyCapabilities;
import com.testvagrant.ekam.commons.models.caps.IOSOnlyCapabilities;
import com.testvagrant.ekam.commons.models.mobile.MobileTestFeed;
import com.testvagrant.ekam.commons.platform.EkamSupportedPlatforms;
import com.testvagrant.ekam.commons.random.FindOne;
import com.testvagrant.ekam.commons.random.RepetitiveStringGenerator;
import com.testvagrant.ekam.config.models.ConfigKeys;
import com.testvagrant.ekam.config.models.MobileConfig;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.mobile.AppFinder;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.flags.ServerArgument;
import lombok.Getter;
import org.apache.commons.exec.OS;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;
import java.util.Map;

@Getter
public class MobileConfigParser extends TestConfigParser {
  private final MobileTestFeed mobileTestFeed;
  private final String testFeedName;
  private final String platform;
  private final GsonParser gsonParser;
  private final List<Map<String, Object>> desiredCapabilitiesList;
  private final Map<String, Object> desiredCapabilities;
  private final MobileConfig mobileConfig;
  List<String> randomDevices;

  public MobileConfigParser(MobileConfig mobileConfig) {
    this.mobileConfig = mobileConfig;
    this.testFeedName = this.mobileConfig.getFeed();
    this.platform = updatePlatformIfAny();
    this.mobileTestFeed = getTestFeed(testFeedName);
    this.desiredCapabilitiesList = mobileTestFeed.getDesiredCapabilities();
    this.desiredCapabilities = getDesiredCapabilities(this.platform);
    this.gsonParser = new GsonParser();
    this.randomDevices = generateRandomDevices();
  }

  public EkamSupportedPlatforms getPlatform() {
    return EkamSupportedPlatforms.valueOf(String.valueOf(getPlatformValue().trim()).toUpperCase());
  }

  private String getPlatformValue() {
    return desiredCapabilities.get(MobileCapabilityType.PLATFORM_NAME).toString();
  }

  private Map<String, Object> getDesiredCapabilities(String platform) {
    if (desiredCapabilitiesList.isEmpty()) {
      throw new RuntimeException("Cannot find desired capabilities in " + testFeedName + ".json");
    }
    if (desiredCapabilitiesList.size() == 1) {
      return desiredCapabilitiesList.get(0);
    }

    if (mobileConfig.isAny() && desiredCapabilitiesList.size() == 1) {
      return desiredCapabilitiesList.get(0);
    }
    return desiredCapabilitiesList.stream()
        .filter(
            desiredCaps ->
                desiredCaps
                    .get(MobileCapabilityType.PLATFORM_NAME)
                    .toString()
                    .equalsIgnoreCase(platform))
        .findFirst()
        .orElseThrow(
            () ->
                new RuntimeException(
                    String.format(
                        "Cannot find desired caps for platform %s in testfeed %s",
                        platform, testFeedName)));
  }

  public Map<ServerArgument, String> getServerArgumentsMap() {
    return mobileConfig.isServerArgsProvided()
        ? new ServerArgumentParser(mobileConfig.getServerArgs()).getServerArgumentsMap()
        : new ServerArgumentParser(mobileTestFeed.getServerArguments()).getServerArgumentsMap();
  }

  public DesiredCapabilities getDesiredCapabilities() {
    Map<String, Object> generalCapabilities =
        getPlatform().equals(EkamSupportedPlatforms.IOS)
            ? getIOSCapabilities().toDesiredCapabilities()
            : getAndroidCapabilities().toDesiredCapabilities();

    Map<String, Object> mergedCapabilities = mergeDesiredCapabilities(generalCapabilities);
    Map<String, Object> desiredCapabilitiesMap = updateMandatoryDesiredCaps(mergedCapabilities);
    return new DesiredCapabilities(desiredCapabilitiesMap);
  }

  public DeviceFilters getDeviceFilters() {
    if (mobileConfig.isDeviceFiltersProvided()) {
      return new DeviceFiltersParser(mobileConfig.getDeviceFilters()).getDeviceFilters();
    }
    return new DeviceFilters();
  }

  private AndroidOnlyCapabilities getAndroidCapabilities() {
    return gsonParser.serialize(desiredCapabilities, AndroidOnlyCapabilities.class);
  }

  private IOSOnlyCapabilities getIOSCapabilities() {
    return gsonParser.serialize(desiredCapabilities, IOSOnlyCapabilities.class);
  }

  private Map<String, Object> mergeDesiredCapabilities(Map<String, Object> desiredCapabilitiesMap) {
    desiredCapabilities.entrySet().parallelStream()
        .forEach(
            entry -> {
              if (!desiredCapabilitiesMap.containsKey(entry.getKey())) {
                desiredCapabilitiesMap.put(entry.getKey(), entry.getValue());
              }
            });
    return desiredCapabilitiesMap;
  }

  private Map<String, Object> updateMandatoryDesiredCaps(
      Map<String, Object> desiredCapabilitiesMap) {
    desiredCapabilitiesMap.put(CapabilityType.PLATFORM_NAME, getPlatform().name().trim());

    String appPath = getAppPath();

    desiredCapabilitiesMap.put(MobileCapabilityType.APP, appPath);

    if (getPlatform().equals(EkamSupportedPlatforms.ANDROID)) {
      desiredCapabilitiesMap.put(
          AndroidMobileCapabilityType.SYSTEM_PORT,
          PortGenerator.aRandomOpenPortOnAllLocalInterfaces());
    }
    return desiredCapabilitiesMap;
  }

  private String getAppPath() {
    if (isMobileFeedEmpty(testFeedName))
      return AppFinder.getDefaultApp(platform)
          .orElseThrow(
              () -> new RuntimeException("Cannot find any app in app folder please add one"))
          .getAbsolutePath();
    String app = getAppCapability();
    return app.contains(":")
        ? app
        : new FileFinder(ResourcePaths.APP_DIR).find(app).getAbsolutePath();
  }

  private boolean isMobileFeedEmpty(String testFeedName) {
    return testFeedName == null || testFeedName.isEmpty();
  }

  private String getAppCapability() {
    return desiredCapabilities.getOrDefault("app", "").toString();
  }

  private MobileTestFeed getTestFeed(String testFeedName) {
    if (isMobileFeedEmpty(testFeedName)) {
      return MobileTestFeed.builder()
          .desiredCapabilities(new DefaultCapabilitiesBuilder().defaultCapsList(platform))
          .build();
    }
    return loadFeed(
        testFeedName, System.getProperty(ConfigKeys.Env.MOBILE_ENV), MobileTestFeed.class);
  }

  private String updatePlatformIfAny() {
    String device = mobileConfig.getTarget().trim();
    if (mobileConfig.isAny()) {
      device = getRandomDevice();
      mobileConfig.setTarget(device.trim());
    }
    return device;
  }

  private String getRandomDevice() {
    return FindOne.inList(randomDevices);
  }

  private List<String> generateRandomDevices() {
    RepetitiveStringGenerator repetitiveStringGenerator = new RepetitiveStringGenerator();
    if (OS.isFamilyMac()) {
      return repetitiveStringGenerator.generate("android", "ios");
    } else {
      return repetitiveStringGenerator.generate("android");
    }
  }
}
