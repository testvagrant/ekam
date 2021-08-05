package com.testvagrant.ekam.db.clients;

import com.testvagrant.ekam.db.DBConfig;
import com.testvagrant.ekam.db.entities.DBType;
import com.testvagrant.ekam.db.mapper.ConfigManager;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public abstract class DBClient {

  private final Handle handle;

  protected DBClient(String database, String driverKey, DBType dbType) {
    DBConfig dbConfig = new ConfigManager().getConfiguration(driverKey);
    String url =
        String.format(
            "jdbc:%s://%s:%s/%s",
            dbType.getDbString(), dbConfig.getHost(), dbConfig.getPort(), database);
    DBI dbi = new DBI(url, dbConfig.getUsername(), dbConfig.getPassword());
    handle = dbi.open();
  }

  protected DBClient(String database, DBConfig dbConfig, DBType dbType) {
    String url =
            String.format(
                    "jdbc:%s://%s:%s/%s",
                    dbType.getDbString(), dbConfig.getHost(), dbConfig.getPort(), database);
    DBI dbi = new DBI(url, dbConfig.getUsername(), dbConfig.getPassword());
    handle = dbi.open();
  }

  protected Handle getHandle() {
    return handle;
  }

  public <T> T load(Class<T> dao) {
    return handle.attach(dao);
  }
}
