package com.testvagrant.ekam.mobile;

import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.remote.ConfigLoader;
import com.testvagrant.ekam.commons.remote.models.CloudConfig;
import com.testvagrant.ekam.config.models.MobileConfig;
import com.testvagrant.ekam.devicemanager.BrowserStackDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.LocalDeviceManagerProvider;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.PCloudyDeviceManagerProvider;

import static com.testvagrant.ekam.commons.remote.constants.Hub.*;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class DeviceCacheDisposeFactory {

  public static void dispose(TargetDetails targetDetails, MobileConfig mobileConfig) {
    if (!mobileConfig.isRemote()) {
      ekamLogger().info("Releasing device {}", targetDetails);
      LocalDeviceManagerProvider.deviceManager().releaseDevice(targetDetails);
      return;
    }
    String hub = mobileConfig.getHub().toLowerCase();
    CloudConfig cloudConfig = new ConfigLoader().loadConfig(hub);
    releaseRemoteDevice(targetDetails, hub, cloudConfig);
  }

  private static void releaseRemoteDevice(
      TargetDetails targetDetails, String hub, CloudConfig cloudConfig) {
    ekamLogger().info("Releasing remote device on {}", hub);
    switch (hub) {
      case P_CLOUDY:
      case QUALITY_KIOSK:
        PCloudyDeviceManagerProvider.deviceManager(
                cloudConfig.getApiHost(), cloudConfig.getUsername(), cloudConfig.getAccessKey())
            .releaseDevice(targetDetails);
        break;
      case BROWSERSTACK:
        if(Toggles.BROWSER_STACK_CACHE_LOCK.isOn())
          BrowserStackDeviceManagerProvider.deviceManager(
                  cloudConfig.getUsername(), cloudConfig.getAccessKey())
              .releaseDevice(targetDetails);
        break;
      default:
        break;
    }
  }
}
