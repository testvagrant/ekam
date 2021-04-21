package com.testvagrant.ekam.web.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.annotations.Url;
import com.testvagrant.ekam.commons.annotations.WaitDuration;
import com.testvagrant.ekam.commons.clients.SiteClient;
import com.testvagrant.ekam.commons.parsers.PropertyParser;

import java.util.Properties;

public class SiteModule extends AbstractModule {

  @Override
  public void configure() {
    Properties envProps = new PropertyParser().loadProperties(SystemProperties.WEBSITE_CONFIG);
    Names.bindProperties(binder(), envProps);

    bind(Key.get(String.class, Url.class)).toInstance(envProps.getOrDefault("url", "").toString());
    bind(Key.get(String.class, WaitDuration.class))
        .toInstance(envProps.getOrDefault("wait", "30").toString());
    bind(SiteClient.class).asEagerSingleton();
  }
}
