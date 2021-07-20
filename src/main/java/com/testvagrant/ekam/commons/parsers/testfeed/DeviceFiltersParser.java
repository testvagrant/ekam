package com.testvagrant.ekam.commons.parsers.testfeed;

import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.devicemanager.models.DeviceFilters;

import java.io.File;

public class DeviceFiltersParser {

  private final DeviceFilters deviceFilters;

  public DeviceFiltersParser(String deviceFiltersName) {
    File file = new FileFinder(ResourcePaths.TEST_RESOURCES, "").find(deviceFiltersName, ".json");
    deviceFilters = new GsonParser().deserialize(file.getPath(), DeviceFilters.class);
  }

  public DeviceFiltersParser(DeviceFilters deviceFilters) {
    this.deviceFilters = deviceFilters;
  }

  public DeviceFilters getDeviceFilters() {
    return deviceFilters;
  }
}
