package com.testvagrant.ekam.mobile;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.config.properties.ConfigPropertyLoader;
import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.internal.modules.EkamConfigModule;
import com.testvagrant.ekam.mobile.configparser.MobileConfigParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ClearSystemProperty(key = "env")
public class MobileConfigParserTests {

  @Test
  @SetSystemProperty(key = "mobile.feed", value = "udid_mobile_feed")
  public void shouldConsiderUdidInDesiredCapabilitiesIfSpecified() {
    Injector injector = Guice.createInjector(new EkamConfigModule());
    EkamConfig config = injector.getInstance(EkamConfig.class);
    MobileConfigParser configParser = new MobileConfigParser(config.getMobile());
    DeviceFilters deviceFilters = configParser.getDeviceFilters();

    String udid = "c6dbe58c21d23487e7ec19d894749494e8b26018";

    List<TargetDetails> targets = getTargetDetails();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager()
            .createDeviceFilters(EkamSupportedPlatforms.IOS.name(), deviceFilters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.get(0).getUdid(), udid);
  }

  @Test
  @SetSystemProperty(key = "mobile.feed", value = "udid_model_mobile_feed")
  public void shouldConsiderUdidAndModelInDesiredCapabilitiesIfSpecified() {
    Injector injector = Guice.createInjector(new EkamConfigModule());
    EkamConfig config = injector.getInstance(EkamConfig.class);
    MobileConfigParser configParser = new MobileConfigParser(config.getMobile());
    DeviceFilters deviceFilters = configParser.getDeviceFilters();

    String udid = "c6dbe58c21d23487e7ec19d894749494e8b26019";
    String name = "iphone";

    List<TargetDetails> targets = getTargetDetails();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager()
            .createDeviceFilters(EkamSupportedPlatforms.IOS.name(), deviceFilters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.get(0).getUdid(), udid);
    Assertions.assertEquals(matchingTargets.get(0).getName(), name);
  }

  @Test
  @SetSystemProperty(key = "mobile.feed", value = "ios_app_mobile_feed")
  public void shouldFindAppProvidedInTheTestFeed() {
    Injector injector = Guice.createInjector(new EkamConfigModule());
    EkamConfig config = injector.getInstance(EkamConfig.class);
    MobileConfigParser configParser = new MobileConfigParser(config.getMobile());
    DeviceFilters deviceFilters = configParser.getDeviceFilters();

    String udid = "c6dbe58c21d23487e7ec19d894749494e8b26017";
    String name = "iphone";

    List<TargetDetails> targets = getTargetDetails();

    Predicate<TargetDetails> predicate =
        new DeviceFiltersManager()
            .createDeviceFilters(EkamSupportedPlatforms.IOS.name(), deviceFilters);

    List<TargetDetails> matchingTargets =
        targets.parallelStream().filter(predicate).collect(Collectors.toList());

    Assertions.assertEquals(matchingTargets.get(0).getUdid(), udid);
    Assertions.assertEquals(matchingTargets.get(0).getName(), name);
  }

  @Test
  @SetSystemProperty(key = "mobile.feed", value = "env_mobile_feed")
  @SetSystemProperty(key = "app", value = "bs://blahhh.com")
  @SetSystemProperty(key="automationName",value="UiAutomator2")
  public void shouldConsiderTheDesiredCapabilitiesWhenSpecifiedViaEnvVariable(){
    Injector injector = Guice.createInjector(new EkamConfigModule());
    EkamConfig ekamConfig = injector.getInstance(EkamConfig.class);
    MobileConfigParser mobileConfigParser=new MobileConfigParser(ekamConfig.getMobile());
    DesiredCapabilities desiredCapabilities = mobileConfigParser.getDesiredCapabilities();
    Assertions.assertEquals("bs://blahhh.com", desiredCapabilities.getCapability("app"));
    Assertions.assertEquals("UiAutomator2",desiredCapabilities.getCapability("automationName"));
  }

  @Test
  @SetSystemProperty(key = "mobile.feed", value = "env_mobile_feed")
  @SetSystemProperty(key="automationName",value="UiAutomator2")
  @SetSystemProperty(key="app",value="")
  public void shouldNotConsiderAppIfAppIsEmptyInDesiredCapabilities(){
    Injector injector=Guice.createInjector(new EkamConfigModule());
    EkamConfig ekamConfig=injector.getInstance(EkamConfig.class);
    MobileConfigParser mobileConfigParser=new MobileConfigParser(ekamConfig.getMobile());
    DesiredCapabilities desiredCapabilities=mobileConfigParser.getDesiredCapabilities();
    Assertions.assertEquals("", desiredCapabilities.getCapability("app"));
  }

  @Test
  @SetSystemProperty(key = "mobile.feed", value = "env_mobile_feed")
  @SetSystemProperty(key = "app", value = "bs://blahhh.com")
  @SetSystemProperty(key="automationName",value="UiAutomator2")
  public void shouldConsiderTheIntegerTypeInDesiredCapabilities(){
    Injector injector=Guice.createInjector(new EkamConfigModule());
    EkamConfig ekamConfig=injector.getInstance(EkamConfig.class);
    MobileConfigParser mobileConfigParser=new MobileConfigParser(ekamConfig.getMobile());
    DesiredCapabilities desiredCapabilities=mobileConfigParser.getDesiredCapabilities();
    Assertions.assertEquals(desiredCapabilities.getCapability("newCommandTimeout").getClass(), Integer.class);
  }
  private List<TargetDetails> getTargetDetails() {
    TargetDetails iosEmulator =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.IOS)
            .udid("c6dbe58c21d23487e7ec19d894749494e8b26017")
            .name("iphone")
            .build();

    TargetDetails iosDevice =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.IOS)
            .udid("c6dbe58c21d23487e7ec19d894749494e8b26018")
            .build();

    TargetDetails iosDeviceWithModel =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.IOS)
            .udid("c6dbe58c21d23487e7ec19d894749494e8b26019")
            .name("iphone")
            .build();

    return Arrays.asList(iosEmulator, iosDevice, iosDeviceWithModel);
  }
}
