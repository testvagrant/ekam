package com.testvagrant.ekam.internal.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.config.properties.ConfigPropertyLoader;

import java.util.Properties;

public class EkamConfigModule extends AbstractModule {

  @Override
  protected void configure() {
    Properties properties = new ConfigPropertyLoader().loadConfig();
    EkamConfig ekamConfig = new EkamConfig(properties);
    bind(EkamConfig.class).toInstance(ekamConfig);
  }
}
