package com.testvagrant.ekam.mobile.remote;

import com.testvagrant.ekam.commons.config.CloudConfig;
import com.testvagrant.ekam.commons.exceptions.HubNotFoundException;
import com.testvagrant.ekam.commons.io.GsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigLoader {

  public CloudConfig loadConfig(String hub) {
    String file = String.format("cloud_config/%s.json", hub);
    InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(file);
    if (resourceAsStream == null) throw new HubNotFoundException(hub);
    return new GsonParser().deserialize(new InputStreamReader(resourceAsStream), CloudConfig.class);
  }
}
