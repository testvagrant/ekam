package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.config.CloudConfig;
import com.testvagrant.ekam.devicemanager.remote.browserstack.BrowserStackUploadManager;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.PCloudyUploadManager;
import com.testvagrant.ekam.devicemanager.remote.pcloudy.clients.responses.PCloudyResponse;

public class RemoteDriverUploadFactory {
  public static String uploadUrl(String hub, String appPath) {
    CloudConfig cloudConfig = new ConfigLoader().loadConfig(hub);
    switch (hub.toLowerCase()) {
      case "browserstack":
        AppUploadResponse upload =
            BrowserStackUploadManager.getInstance(
                    cloudConfig.getUsername(), cloudConfig.getAccessKey())
                .upload(appPath);
        return upload.getAppUrl();
      case "qualitykiosk":
      case "pcloudy":
        PCloudyResponse pCloudyResponse = PCloudyUploadManager.getInstance(
                cloudConfig.getApiHost(), cloudConfig.getUsername(), cloudConfig.getAccessKey())
                .upload(appPath);
        return pCloudyResponse.getResult().getFile();
      default:
        return "";
    }
  }
}
