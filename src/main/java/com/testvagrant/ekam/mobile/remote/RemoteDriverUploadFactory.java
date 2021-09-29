package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.remote.ConfigLoader;
import com.testvagrant.ekam.commons.remote.models.CloudConfig;
import com.testvagrant.ekam.devicemanager.remote.browserstack.BrowserStackUploadManager;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.PCloudyUploadManager;

import static com.testvagrant.ekam.commons.remote.constants.Hub.*;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class RemoteDriverUploadFactory {

  public static String uploadUrl(String hub, String appPath) {
    CloudConfig cloudConfig = new ConfigLoader().loadConfig(hub);
    ekamLogger().info("Uploading app to {}", hub);
    switch (hub.toLowerCase()) {
      case BROWSERSTACK:
        return BrowserStackUploadManager.getInstance(
                cloudConfig.getUsername(), cloudConfig.getAccessKey())
            .upload(appPath)
            .getAppUrl();
      case QUALITY_KIOSK:
      case P_CLOUDY:
        return PCloudyUploadManager.getInstance(
                cloudConfig.getApiHost(), cloudConfig.getUsername(), cloudConfig.getAccessKey())
            .upload(appPath)
            .getResult()
            .getFile();
      default:
        return "";
    }
  }
}
