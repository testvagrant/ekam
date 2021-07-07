package com.testvagrant.ekam.db.clients;

import com.testvagrant.ekam.db.entities.DBType;

public abstract class PostgresDBClient extends DBClient {

  protected PostgresDBClient(String database, String driverKey) {
    super(database, driverKey, DBType.POSTGRES);
  }

  protected PostgresDBClient(String database) {
    this(database, DBType.POSTGRES.getDbString());
  }
}
