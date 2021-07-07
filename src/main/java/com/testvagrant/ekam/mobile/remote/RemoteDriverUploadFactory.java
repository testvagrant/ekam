package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.config.CloudConfig;
import com.testvagrant.ekam.devicemanager.remote.browserstack.BrowserStackUploadManager;
import com.testvagrant.ekam.devicemanager.remote.browserstack.clients.responses.AppUploadResponse;

public class RemoteDriverUploadFactory {
  public static String uploadUrl(String hub, String appPath) {
    switch (hub.toLowerCase()) {
      case "browserstack":
        CloudConfig cloudConfig = new ConfigLoader().loadConfig(hub);
        AppUploadResponse upload =
            BrowserStackUploadManager.getInstance(
                    cloudConfig.getUsername(), cloudConfig.getAccessKey())
                .upload(appPath);
        return upload.getAppUrl();
      default:
        return "";
    }
  }
}
