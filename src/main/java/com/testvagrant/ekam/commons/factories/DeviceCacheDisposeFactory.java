package com.testvagrant.ekam.commons.factories;

import com.testvagrant.ekam.commons.config.CloudConfig;
import com.testvagrant.ekam.config.models.MobileConfig;
import com.testvagrant.ekam.devicemanager.BrowserStackDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.LocalDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.PCloudyDeviceManagerProvider;
import com.testvagrant.ekam.mobile.remote.ConfigLoader;

public class DeviceCacheDisposeFactory {

  public static void dispose(TargetDetails targetDetails, MobileConfig mobileConfig) {
    if (!mobileConfig.isRemote()) {
      LocalDeviceManagerProvider.deviceManager().releaseDevice(targetDetails);
      return;
    }
    String hub = mobileConfig.getHub().toLowerCase();
    CloudConfig cloudConfig = new ConfigLoader().loadConfig(hub);
    releaseRemoteDevice(targetDetails, hub, cloudConfig);
  }

  private static void releaseRemoteDevice(
      TargetDetails targetDetails, String hub, CloudConfig cloudConfig) {
    switch (hub) {
      case "pcloudy":
      case "qualitykiosk":
        PCloudyDeviceManagerProvider.deviceManager(cloudConfig.getApiHost(), cloudConfig.getUsername(), cloudConfig.getAccessKey())
                .releaseDevice(targetDetails);
      default:
        BrowserStackDeviceManagerProvider.deviceManager(
                cloudConfig.getUsername(), cloudConfig.getAccessKey())
            .releaseDevice(targetDetails);
    }
  }
}
