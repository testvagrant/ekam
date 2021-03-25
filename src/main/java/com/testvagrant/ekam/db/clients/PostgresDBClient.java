package com.testvagrant.ekam.db.clients;



import com.testvagrant.ekam.db.DBConfig;
import com.testvagrant.ekam.db.entities.DBType;

import java.sql.SQLException;


public abstract class PostgresDBClient extends DBClient {

    protected PostgresDBClient(DBConfig dbConfig) throws SQLException {
        super(dbConfig, DBType.POSTGRES);
    }
}
