package com.testvagrant.ekam.api.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;
import com.testvagrant.ekam.config.models.ConfigKeys;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.testvagrant.ekam.commons.io.FileUtilities.fileUtils;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

@SuppressWarnings("unchecked")
public class ApiHostsModule extends AbstractModule {

  @Override
  public void configure() {
    Map<String, String> envProps = loadApiTestFeed();
    Names.bindProperties(binder(), SystemPropertyParser.parse(envProps));
    ekamLogger().info("Binding hosts {} to Named properties", envProps);
  }

  private Map<String, String> loadApiTestFeed() {
    String env = System.getProperty(ConfigKeys.Env.API_ENV, System.getProperty("env", ""));
    String fileName =
        System.getProperty(ConfigKeys.Api.HOSTS).replaceAll(".json", "").trim().concat(".json");
    Optional<File> file = fileUtils().findResource(fileName, env);
    if (file.isPresent()) {
      ekamLogger().info("Loading API hosts file with name {}", fileName);
      return new GsonParser().deserialize(file.get().getAbsolutePath(), Map.class);
    }
    ekamLogger().info("No API hosts file available, returning empty map");
    return new HashMap<>();
  }
}
