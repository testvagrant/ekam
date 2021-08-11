package com.testvagrant.ekam.commons.remote;

import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.remote.exceptions.HubNotFoundException;
import com.testvagrant.ekam.commons.remote.models.CloudConfig;

import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigLoader {

  public CloudConfig loadConfig(String hub) {
    String file = String.format("cloud_config/%s.json", hub);
    InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(file);
    if (resourceAsStream == null) throw new HubNotFoundException(hub);
    CloudConfig cloudConfig =
        new GsonParser().deserialize(new InputStreamReader(resourceAsStream), CloudConfig.class);
    return cloudConfig.parseSystemProperties();
  }
}
