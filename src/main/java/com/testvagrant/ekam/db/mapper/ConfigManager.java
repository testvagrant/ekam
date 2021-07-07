package com.testvagrant.ekam.db.mapper;

import com.google.gson.Gson;
import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.ResourcePaths;
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
      String fileName = System.getProperty(ConfigKeys.DB.DRIVERS).replaceAll(".yml", "").trim();

      File file = new FileFinder(ResourcePaths.TEST_RESOURCES, env).find(fileName, ".yml");
      if (!file.exists()) {
        return new DBConfiguration();
      }
      LinkedHashMap<String, Object> parse =
          new Yaml().loadAs(new FileReader(file), LinkedHashMap.class);

      Map.Entry<String, Object> stringObjectEntry =
          parse.entrySet().stream()
              .filter(entry -> entry.getKey().equalsIgnoreCase(name))
              .findFirst()
              .orElseThrow(() -> new InvalidConnectionException("drivers", name));

      Gson gson = new Gson();
      String json = gson.toJson(stringObjectEntry.getValue());
      return gson.fromJson(json, DBConfiguration.class);
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}
