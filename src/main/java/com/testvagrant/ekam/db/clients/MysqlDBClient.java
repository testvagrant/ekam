package com.testvagrant.ekam.db.clients;

import com.testvagrant.ekam.db.entities.DBType;

public abstract class MysqlDBClient extends DBClient {

  protected MysqlDBClient(String database) {
    this(database, DBType.MYSQL.getDbString());
  }

  protected MysqlDBClient(String database, String driverKey) {
    super(database, driverKey, DBType.MYSQL);
  }
}
