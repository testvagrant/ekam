package com.testvagrant.ekam.api.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;
import com.testvagrant.ekam.config.models.ConfigKeys;

import java.io.File;
import java.util.Map;
import java.util.Optional;

import static com.testvagrant.ekam.commons.io.FileUtilities.fileUtils;

@SuppressWarnings("unchecked")
public class ApiHostsModule extends AbstractModule {

  @Override
  public void configure() {
    Map<String, String> envProps = loadApiTestFeed();
    Names.bindProperties(binder(), SystemPropertyParser.parse(envProps));
  }

  private Map<String, String> loadApiTestFeed() {
    String env = System.getProperty(ConfigKeys.Env.API_ENV, System.getProperty("env", ""));
    String fileName = System.getProperty(ConfigKeys.Api.HOSTS).replaceAll(".json", "").trim().concat(".json");

    Optional<File> file = fileUtils().findResource(fileName, env);
    if(file.isPresent()) {
      return new GsonParser().deserialize(file.get().getAbsolutePath(), Map.class);
    }
    throw new RuntimeException("Looks like have either not created hosts.json or have not set api.hosts in config.");
  }
}
