package com.testvagrant.ekam.db;

import com.testvagrant.ekam.db.accounts.AccountsDBInterface;
import com.testvagrant.ekam.db.clients.DBClient;
import com.testvagrant.ekam.db.clients.PostgresDBClient;
import com.testvagrant.ekam.db.exceptions.InvalidConnectionException;

import java.sql.SQLException;

public class AccountsDBClient extends PostgresDBClient {

    AccountsDBInterface accountsDBInterface;

    protected AccountsDBClient() throws SQLException, InvalidConnectionException {
        super(DBClient.configManager.getConfiguration("ekam_pg"));
        accountsDBInterface = load(AccountsDBInterface.class);
    }

    public String getUserName(Integer userId) {
       return accountsDBInterface.getAccountName(userId);
    }
}
