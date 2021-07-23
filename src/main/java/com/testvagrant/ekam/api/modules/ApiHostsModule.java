package com.testvagrant.ekam.api.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;
import com.testvagrant.ekam.config.models.ConfigKeys;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.testvagrant.ekam.commons.io.ResourcePaths.TEST_RESOURCES;

@SuppressWarnings("unchecked")
public class ApiHostsModule extends AbstractModule {

  @Override
  public void configure() {
    Map<String, String> envProps = loadApiTestFeed();
    Names.bindProperties(binder(), SystemPropertyParser.parse(envProps));
  }

  private Map<String, String> loadApiTestFeed() {
    String env = System.getProperty(ConfigKeys.Env.API_ENV, System.getProperty("env", ""));
    String fileName = System.getProperty(ConfigKeys.Api.HOSTS).replaceAll(".json", "").trim();

    File file = new FileFinder(TEST_RESOURCES, env).find(fileName, ".json");
    return Objects.isNull(file)
        ? new HashMap<>()
        : new GsonParser().deserialize(file.getAbsolutePath(), Map.class);
  }
}
