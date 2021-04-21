package com.testvagrant.ekam.db.clients;

import com.testvagrant.ekam.db.DBConfig;
import com.testvagrant.ekam.db.entities.DBType;

public abstract class PostgresDBClient extends DBClient {

  protected PostgresDBClient(DBConfig dbConfig) {
    super(dbConfig, DBType.POSTGRES);
  }
}
