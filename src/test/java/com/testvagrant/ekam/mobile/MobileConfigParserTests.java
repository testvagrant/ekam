package com.testvagrant.ekam.mobile;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.commons.parsers.testfeed.MobileConfigParser;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.devicemanager.DeviceFiltersManager;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import java.util.Arrays;
import java.util.List;
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

  private List<TargetDetails> getTargetDetails() {
    TargetDetails iosEmulator =
        TargetDetails.builder()
            .platform(EkamSupportedPlatforms.IOS)
            .udid("D9FD7CCA-774A-4C1A-999D-FC767F86B018")
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