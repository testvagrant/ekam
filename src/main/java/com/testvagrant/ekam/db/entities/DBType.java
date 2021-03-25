package com.testvagrant.ekam.db.entities;

public enum DBType {
    POSTGRES("postgresql"),
    MYSQL("mysql"),
    DB2("as400");

    private String dbString;

    DBType(String dbString) {
        this.dbString = dbString;
    }

    public String getDbString() {
        return dbString;
    }
}
