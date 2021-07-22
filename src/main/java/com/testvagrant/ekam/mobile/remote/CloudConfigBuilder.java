package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.remote.models.CloudConfig;

public class CloudConfigBuilder {

  private final String userName;
  private final String accessKey;
  private final CloudConfig cloudConfig;

  public CloudConfigBuilder(String hub) {
    userName = System.getenv("username");
    accessKey = System.getenv("accessKey");
    cloudConfig = new ConfigLoader().loadConfig(hub);
    overrideCloudConfig(cloudConfig);
  }

  public CloudConfig build() {
    return cloudConfig;
  }

  private void overrideCloudConfig(CloudConfig cloudConfig) {
    if (userName != null) cloudConfig.setUsername(userName);
    if (accessKey != null) cloudConfig.setAccessKey(accessKey);
  }
}
