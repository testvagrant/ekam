package com.testvagrant.ekam.commons.remote;

import com.testvagrant.ekam.commons.remote.models.CloudConfig;

import java.net.URL;

public class RemoteUrlBuilder {

  public static URL build(CloudConfig cloudConfig) {
    try {
      String urlString;
      if (!cloudConfig.getUrl().isEmpty()) {
        urlString = cloudConfig.getUrl();
      } else {
        urlString =
            String.format(
                "%s://%s:%s@%s/",
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
