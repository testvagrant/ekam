package com.testvagrant.ekam.commons.parsers.testfeed;

import io.appium.java_client.remote.MobileCapabilityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultCapabilitiesBuilder {

  public List<Map<String, Object>> defaultCapsList(String platform) {
    Map<String, Object> defaultCaps = new HashMap<>();
    defaultCaps.put(MobileCapabilityType.PLATFORM_NAME, platform);
    List<Map<String, Object>> capsList = new ArrayList<>();
    capsList.add(defaultCaps);
    return capsList;
  }
}
