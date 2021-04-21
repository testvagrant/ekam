package com.testvagrant.ekam.api.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.parsers.PropertyParser;

import java.util.Properties;

public class PropertyModule extends AbstractModule {

  @Override
  public void configure() {
    Properties envProps = new PropertyParser().loadProperties(SystemProperties.API_CONFIG);
    Names.bindProperties(binder(), envProps);
  }
}
