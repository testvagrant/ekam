package com.testvagrant.ekam.db;

import com.testvagrant.ekam.db.entities.DBType;
import com.testvagrant.ekam.db.mapper.ConfigManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

@SetSystemProperty(key = "db.drivers", value = "drivers")
public class DBConfigManagerTest {

  @Test
  public void findDBConfig() {
    DBConfig configuration = new ConfigManager().getConfiguration(DBType.POSTGRES.getDbString());
    Assertions.assertEquals(configuration.getHost(), "a");
    Assertions.assertEquals(configuration.getPort(), "b");
    Assertions.assertEquals(configuration.getUsername(), "c");
    Assertions.assertEquals(configuration.getPassword(), "d");
  }

  @Test
  @SetSystemProperty(key = "env", value = "qa")
  public void findDBConfigFromEnvWhenSpecified() {
    DBConfig configuration = new ConfigManager().getConfiguration(DBType.POSTGRES.getDbString());
    Assertions.assertEquals(configuration.getHost(), "qa");
    Assertions.assertEquals(configuration.getPort(), "qa");
    Assertions.assertEquals(configuration.getUsername(), "qa");
    Assertions.assertEquals(configuration.getPassword(), "qa");
  }

  @Test
  @SetSystemProperty(key = "db.env", value = "staging")
  @SetSystemProperty(key = "env", value = "qa")
  public void findDBConfigFromDbEnvWhenSpecified() {
    DBConfig configuration = new ConfigManager().getConfiguration(DBType.POSTGRES.getDbString());
    Assertions.assertEquals(configuration.getHost(), "staging");
    Assertions.assertEquals(configuration.getPort(), "staging");
    Assertions.assertEquals(configuration.getUsername(), "staging");
    Assertions.assertEquals(configuration.getPassword(), "staging");
  }

  @Test
  public void findDBConfigWithCustomName() {
    DBConfig configuration = new ConfigManager().getConfiguration("ekam_pg");
    Assertions.assertEquals(configuration.getHost(), "e");
    Assertions.assertEquals(configuration.getPort(), "f");
    Assertions.assertEquals(configuration.getUsername(), "g");
    Assertions.assertEquals(configuration.getPassword(), "h");
  }
}
