package com.testvagrant.ekam.db.clients;

import com.testvagrant.ekam.db.DBConfig;
import com.testvagrant.ekam.db.entities.DBType;
import com.testvagrant.ekam.db.mapper.ConfigManager;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.sql.SQLException;

public abstract class DBClient {

    private Handle handle;
    protected static ConfigManager configManager = new ConfigManager();

    protected DBClient(DBConfig dbConfig, DBType dbType) throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", dbType.getDbString(), dbConfig.getHost(), dbConfig.getPort(), dbConfig.getDatabase());
        DBI dbi = new DBI(url, dbConfig.getUsername(), dbConfig.getPassword());
        handle = dbi.open();
    }

    protected Handle getHandle() {
        return handle;
    }

    public <T> T load(Class<T> dao) {
        T daoObj = handle.attach(dao);
        return daoObj;
    }
}
