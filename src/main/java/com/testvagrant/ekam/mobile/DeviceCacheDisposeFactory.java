package com.testvagrant.ekam.mobile;

import com.testvagrant.ekam.commons.remote.ConfigLoader;
import com.testvagrant.ekam.commons.remote.models.CloudConfig;
import com.testvagrant.ekam.config.models.MobileConfig;
import com.testvagrant.ekam.devicemanager.BrowserStackDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.LocalDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.PCloudyDeviceManagerProvider;

import static com.testvagrant.ekam.commons.remote.constants.Hub.P_CLOUDY;
import static com.testvagrant.ekam.commons.remote.constants.Hub.QUALITY_KIOSK;

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
      case P_CLOUDY:
      case QUALITY_KIOSK:
        PCloudyDeviceManagerProvider.deviceManager(
                cloudConfig.getApiHost(), cloudConfig.getUsername(), cloudConfig.getAccessKey())
            .releaseDevice(targetDetails);
        break;
      default:
        BrowserStackDeviceManagerProvider.deviceManager(
                cloudConfig.getUsername(), cloudConfig.getAccessKey())
            .releaseDevice(targetDetails);
        break;
    }
  }
}