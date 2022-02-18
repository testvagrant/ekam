package com.testvagrant.ekam.mobile.configparser;

import com.google.gson.reflect.TypeToken;
import com.testvagrant.ekam.commons.generators.PortGenerator;
import com.testvagrant.ekam.commons.parsers.TestConfigParser;
import com.testvagrant.ekam.commons.random.FindAny;
import com.testvagrant.ekam.commons.random.RepetitiveStringGenerator;
import com.testvagrant.ekam.config.models.MobileConfig;
import com.testvagrant.ekam.devicemanager.models.DeviceFilter;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.mobile.AppFinder;
import com.testvagrant.ekam.mobile.models.MobileTestFeed;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.flags.ServerArgument;
import org.apache.commons.exec.OS;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.*;

import static com.testvagrant.ekam.config.models.ConfigKeys.Env.MOBILE_ENV;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;
import static com.testvagrant.ekam.mobile.constants.MobilePlatform.ANDROID;
import static com.testvagrant.ekam.mobile.constants.MobilePlatform.IOS;

public class MobileConfigParser extends TestConfigParser {

  private final MobileTestFeed testFeed;
  private final MobileConfig mobileConfig;
  private String platform;
  private DesiredCapabilities desiredCapabilities;
  private DeviceFilters deviceFilters;
  private Map<ServerArgument, String> serverArguments;

  public MobileConfigParser(MobileConfig mobileConfig) {
    ekamLogger().info("Parsing Mobile Config {}", mobileConfig);
    this.mobileConfig = mobileConfig;
    setPlatform();
    this.testFeed = getTestFeed(mobileConfig.getFeed());
  }

  public String getPlatform() {
    if (platform == null) setPlatform();
    return platform;
  }

  public MobileConfig getMobileConfig() {
    return mobileConfig;
  }

  public Map<ServerArgument, String> getServerArguments() {
    if (serverArguments == null) setServerArguments();
    return serverArguments;
  }

  public DesiredCapabilities getDesiredCapabilities() {
    if (desiredCapabilities == null) setDesiredCapabilities();
    return desiredCapabilities;
  }

  public DeviceFilters getDeviceFilters() {
    if (deviceFilters == null) setDeviceFilters();
    return deviceFilters;
  }

  private void setDeviceFilters() {
    if (mobileConfig.isDeviceFiltersProvided()) {
      deviceFilters =
          loadFeed(
              mobileConfig.getDeviceFilters(), System.getProperty(MOBILE_ENV), DeviceFilters.class);
      return;
    }

    Map<String, Object> desiredCapabilities = getDesiredCapabilities().asMap();
    String udid = (String) desiredCapabilities.getOrDefault(MobileCapabilityType.UDID, "");
    String model = (String) desiredCapabilities.getOrDefault(MobileCapabilityType.DEVICE_NAME, "");
    String platformVersion =
        (String) desiredCapabilities.getOrDefault(MobileCapabilityType.PLATFORM_VERSION, "");

    DeviceFilter udidFilter =
        new DeviceFilter().toBuilder().include(Collections.singletonList(udid)).build();
    DeviceFilter modelFilter =
        new DeviceFilter().toBuilder().include(Collections.singletonList(model)).build();

    DeviceFilter platformVersionFilter =
        new DeviceFilter().toBuilder().include(Collections.singletonList(platformVersion)).build();

    deviceFilters =
        new DeviceFilters()
            .toBuilder()
                .model(modelFilter)
                .udid(udidFilter)
                .platformVersion(platformVersionFilter)
                .build();
    ekamLogger().info("Created device filters for {}", deviceFilters);
  }

  private void setServerArguments() {
    List<String> serverArgs =
        mobileConfig.isServerArgsProvided() ? loadServerArguments() : testFeed.getServerArguments();
    serverArguments = new ServerArgumentParser(serverArgs).getServerArguments();
    ekamLogger().info("Setting server arguments {}", serverArguments);
  }

  private void setDesiredCapabilities() {
    List<Map<String, Object>> capabilitiesList = testFeed.getDesiredCapabilities();
    RuntimeException exception = new RuntimeException("Cannot find desired capabilities");

    Map<String, Object> capabilities;

    if (mobileConfig.isAny() || capabilitiesList.size() == 1) {
      capabilities = capabilitiesList.stream().findAny().orElseThrow(() -> exception);
    } else {
      capabilities =
          capabilitiesList.stream()
              .filter(
                  desiredCaps ->
                      desiredCaps
                          .get(MobileCapabilityType.PLATFORM_NAME)
                          .toString()
                          .equalsIgnoreCase(platform))
              .findFirst()
              .orElseThrow(() -> exception);
    }

    Map<String, Object> updatedCapabilities = updateMandatoryCapabilities(testFeed.parseSystemProperty(capabilities));
    desiredCapabilities = new DesiredCapabilities(updatedCapabilities);
    ekamLogger().info("Setting desired capabilities {}", desiredCapabilities);
  }

  private Map<String, Object> updateMandatoryCapabilities(Map<String, Object> capabilities) {
    ekamLogger().info("Updating mandatory capabilities");
    capabilities.put(CapabilityType.PLATFORM_NAME, platform);
    ekamLogger().info("platform {}", platform);
    String app = (String) capabilities.getOrDefault(MobileCapabilityType.APP, "");
    if (!app.isEmpty()) {
      String appPath =
          Objects.isNull(mobileConfig.getFeed()) || mobileConfig.getFeed().isEmpty()
              ? AppFinder.getDefaultApp(platform)
              : app.contains(":") ? app : AppFinder.findApp(app);
      capabilities.put(MobileCapabilityType.APP, appPath);
      ekamLogger().info("platform {}", platform);
    }

    if (!mobileConfig.isRemote() && platform.equalsIgnoreCase(ANDROID)) {
      capabilities.put(
          AndroidMobileCapabilityType.SYSTEM_PORT,
          PortGenerator.randomOpenPortOnAllLocalInterfaces());
    }

    return capabilities;
  }

  private void setPlatform() {
    if (mobileConfig.isAny()) {
      List<String> randomPlatforms = generateRandomPlatforms();
      platform = FindAny.inList(randomPlatforms);
      ekamLogger().info("Platform is any, generating default platform as {}", platform);
      mobileConfig.setTarget(platform.trim());
    }
    platform = mobileConfig.getTarget().trim();
    ekamLogger().info("Platform is {}", platform);
  }

  private MobileTestFeed getTestFeed(String testFeed) {
    if (testFeed == null || testFeed.isEmpty()) {
      MobileTestFeed build = MobileTestFeed.builder().desiredCapabilities(generateDefaultCapabilities()).build();
      ekamLogger().info("mobile.feed is empty, generating a default feed {}", build);
      return build;
    }

    return loadFeed(testFeed, System.getProperty(MOBILE_ENV), MobileTestFeed.class);
  }

  private List<String> loadServerArguments() {
    return loadFeed(
        mobileConfig.getServerArgs(),
        System.getProperty(MOBILE_ENV),
        new TypeToken<List<String>>() {}.getType());
  }

  private List<String> generateRandomPlatforms() {
    RepetitiveStringGenerator repetitiveStringGenerator = new RepetitiveStringGenerator();
    return OS.isFamilyMac()
        ? repetitiveStringGenerator.generate(ANDROID, IOS)
        : repetitiveStringGenerator.generate(ANDROID);
  }

  private List<Map<String, Object>> generateDefaultCapabilities() {
    Map<String, Object> capabilities = new HashMap<>();
    capabilities.put(MobileCapabilityType.PLATFORM_NAME, platform);

    List<Map<String, Object>> capabilitiesList = new ArrayList<>();
    capabilitiesList.add(capabilities);
    return capabilitiesList;
  }
}
