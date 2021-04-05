package com.testvagrant.ekam.web.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.clients.SiteClient;

public class SiteModule extends AbstractModule {

  @Override
  public void configure() {
    bind(SiteClient.class).asEagerSingleton();
  }
}
