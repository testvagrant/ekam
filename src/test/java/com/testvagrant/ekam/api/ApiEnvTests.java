package com.testvagrant.ekam.api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

@SetSystemProperty(key = "api.hosts", value = "hosts")
public class ApiEnvTests {

  private Config config;

  @BeforeEach
  protected void createInjector() {
    Injector injector = Guice.createInjector(new ApiHostsModule());
    config = injector.getInstance(Config.class);
  }

  @Test
  @SetSystemProperty(key = "env", value = "qa")
  public void shouldPickUpHostFromEnvWhenSpecified() {
    Assertions.assertEquals("qa", config.getHost());
  }

  @Test
  @SetSystemProperty(key = "api.env", value = "staging")
  @SetSystemProperty(key = "env", value = "qa")
  public void shouldPickUpHostFromAPiEnvWhenSpecified() {
    Assertions.assertEquals("staging", config.getHost());
  }

  @Test
  @Disabled
  @SetSystemProperty(key = "env", value = "")
  @SetSystemProperty(key = "api.hosts", value = "hosts")
  public void apiConfigTestForEnv() {
    Assertions.assertEquals("abc", config.getHost());
  }

  @Test
  @SetSystemProperty(key = "env", value = "")
  @SetSystemProperty(key = "api.hosts", value = "main_hosts")
  public void apiConfigTestForEnvFromSrcMainResource() {
    Assertions.assertEquals("abc", config.getHost());
  }

  @Test
  @SetSystemProperty(key = "api.env", value = "staging")
  @SetSystemProperty(key = "env", value = "qa")
  @SetSystemProperty(key = "api.hosts", value = "main_hosts")
  public void shouldPickUpHostFromAPiEnvWhenSpecifiedInSrcMainResource() {
    Assertions.assertEquals("staging", config.getHost());
  }
}
