package com.testvagrant.ekam;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.api.Config;

public class BaseTest {
  protected Config initConfig() {
    Injector injector = Guice.createInjector(new ApiHostsModule());
    return injector.getInstance(Config.class);
  }
}
