package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.config.CloudConfig;

import java.net.URL;

public class RemoteUrlBuilder {

  public static URL build(CloudConfig cloudConfig) {
    try {
      String urlString = "";
      if (!cloudConfig.getUrl().isEmpty()) {
        urlString = cloudConfig.getUrl();
      } else {
        urlString =
            String.format(
                "%s://%s:%s@%s/wd/hub",
                cloudConfig.getProtocol(),
                cloudConfig.getUsername(),
                cloudConfig.getAccessKey(),
                cloudConfig.getHub());
      }
      return new URL(urlString);
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}
