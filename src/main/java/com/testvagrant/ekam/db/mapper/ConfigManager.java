package com.testvagrant.ekam.db.mapper;

import com.google.gson.Gson;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.db.DBConfig;
import com.testvagrant.ekam.db.configuration.DBConfiguration;
import com.testvagrant.ekam.db.entities.ConfigDetails;
import com.testvagrant.ekam.db.exceptions.InvalidConnectionException;
import com.testvagrant.optimus.commons.filehandlers.FileFinder;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.testvagrant.optimus.commons.filehandlers.ResourcePaths.MAIN_RESOURCES;
import static com.testvagrant.optimus.commons.filehandlers.ResourcePaths.TEST_RESOURCES;

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
      AtomicReference<String> configFilePath = new AtomicReference<>("");

      for (String path : validPaths) {
        File file = FileFinder.fileFinder(path).find(SystemProperties.DB_CONFIG, ".yaml");
        if (file != null && file.exists()) {
          configFilePath.set(file.getPath());
          break;
        }
      }

      if (configFilePath.get().isEmpty()) {
        throw new RuntimeException("Cannot find file " + name);
      }

      LinkedHashMap<String, Object> parse =
          new Yaml().loadAs(new FileReader(new File(configFilePath.get())), LinkedHashMap.class);

      Map.Entry<String, Object> stringObjectEntry =
          parse.entrySet().stream()
              .filter(entry -> entry.getKey().toLowerCase().equals(name.toLowerCase()))
              .findFirst()
              .orElseThrow(() -> new InvalidConnectionException(SystemProperties.DB_CONFIG, name));

      Gson gson = new Gson();
      String json = gson.toJson(stringObjectEntry.getValue());
      return gson.fromJson(json, DBConfiguration.class);
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  private static final String[] validPaths = new String[] {TEST_RESOURCES, MAIN_RESOURCES};
}
