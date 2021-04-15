package com.testvagrant.ekam.db.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.db.DBConfig;
import com.testvagrant.ekam.db.configuration.DBConfiguration;
import com.testvagrant.ekam.db.entities.ConfigDetails;
import com.testvagrant.ekam.db.exceptions.InvalidConnectionException;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigManager {

  public DBConfig getConfiguration(ConfigDetails configDetails) throws InvalidConnectionException {
    return getConfiguration(configDetails.getConfigName());
  }

  public DBConfig getConfiguration(String configName) throws InvalidConnectionException {
    return getDBConfiguration(configName);
  }

  private DBConfig getDBConfiguration(String name) throws InvalidConnectionException {
    String env = SystemProperties.ENV;
    InputStream resourceAsStream =
        getClass().getClassLoader().getResourceAsStream("dbconfig/" + env + ".yaml");
    if (resourceAsStream == null)
      resourceAsStream =
          getClass().getClassLoader().getResourceAsStream("dbconfig/" + env + ".yml");
    if (resourceAsStream == null)
      throw new RuntimeException("Cannot find db property file for env " + env);
    Yaml yaml = new Yaml();
    HashMap<String, Object> parse =
        yaml.loadAs(new InputStreamReader(resourceAsStream), LinkedHashMap.class);
    Optional<Map.Entry<String, Object>> optionalEntry =
        parse.entrySet().stream()
            .filter(entry -> entry.getKey().toLowerCase().equals(name.toLowerCase()))
            .findFirst();

    if (optionalEntry.isPresent()) {
      Map.Entry<String, Object> stringObjectEntry = optionalEntry.get();
      Gson gson = new Gson();
      String json = gson.toJson(stringObjectEntry.getValue());
      return gson.fromJson(json, DBConfiguration.class);
    } else {
      throw new InvalidConnectionException(env, name);
    }
  }
}
