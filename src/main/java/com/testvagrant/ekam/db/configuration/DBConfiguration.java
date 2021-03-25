package com.testvagrant.ekam.db.configuration;

import com.testvagrant.ekam.db.DBConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DBConfiguration implements DBConfig {
    private String host;
    private String port;
    private String username;
    private String password;
    private String database;
}
