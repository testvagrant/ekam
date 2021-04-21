package com.testvagrant.ekam.db.clients;

import com.testvagrant.ekam.db.DBConfig;
import com.testvagrant.ekam.db.entities.DBType;

public abstract class MysqlDBClient extends DBClient {

  protected MysqlDBClient(DBConfig dbConfig) {
    super(dbConfig, DBType.MYSQL);
  }
}
