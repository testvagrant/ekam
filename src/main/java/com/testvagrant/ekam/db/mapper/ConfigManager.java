package com.testvagrant.ekam.db.mapper;

import com.google.gson.Gson;
import com.testvagrant.ekam.commons.parsers.SystemPropertyParser;
import com.testvagrant.ekam.config.models.ConfigKeys;
import com.testvagrant.ekam.db.DBConfig;
import com.testvagrant.ekam.db.configuration.DBConfiguration;
import com.testvagrant.ekam.db.entities.ConfigDetails;
import com.testvagrant.ekam.db.exceptions.InvalidConnectionException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.testvagrant.ekam.commons.io.FileUtilities.fileUtils;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class ConfigManager {

  public DBConfig getConfiguration(ConfigDetails configDetails) {
    return getConfiguration(configDetails.getConfigName());
  }

  public DBConfig getConfiguration(String configName) {
    return getDBConfiguration(configName);
  }

  @SuppressWarnings("unchecked")
  private DBConfig getDBConfiguration(String name) {
    try {
      String env = System.getProperty(ConfigKeys.Env.DB_ENV, System.getProperty("env", ""));
      String fileName =
          System.getProperty(ConfigKeys.DB.DRIVERS).replaceAll(".yml", "").trim().concat(".yml");
      ekamLogger().info("Trying to load DB configuration from file  {}", fileName);
      Optional<File> file = fileUtils().findResource(fileName, env);
      if (!file.isPresent()) {
        ekamLogger().warn("Looks like have either not created drivers.yml or have not set db.drivers in config.");
        throw new RuntimeException(
            "Looks like have either not created drivers.yml or have not set db.drivers in config.");
      }
      LinkedHashMap<String, Object> parse =
          new Yaml().loadAs(new FileReader(file.get()), LinkedHashMap.class);
      ekamLogger().info("Loading DB configuration successfully.");
      Map.Entry<String, Object> stringObjectEntry =
          parse.entrySet().stream()
              .filter(entry -> entry.getKey().equalsIgnoreCase(name))
              .findFirst()
              .orElseThrow(() -> new InvalidConnectionException("drivers", name));

      Gson gson = new Gson();
      String json = gson.toJson(stringObjectEntry.getValue());
      DBConfiguration configuration = gson.fromJson(json, DBConfiguration.class);
      return updateConfiguration(configuration);
    } catch (Exception ex) {
      ekamLogger().error(ex.getMessage());
      throw new RuntimeException(ex.getMessage());
    }
  }

  private DBConfiguration updateConfiguration(DBConfiguration configuration) {
    configuration.setUsername(SystemPropertyParser.parse(configuration.getUsername()));
    configuration.setPassword(SystemPropertyParser.parse(configuration.getPassword()));
    configuration.setHost(SystemPropertyParser.parse(configuration.getHost()));
    configuration.setPort(SystemPropertyParser.parse(configuration.getPort()));
    return configuration;
  }
}
