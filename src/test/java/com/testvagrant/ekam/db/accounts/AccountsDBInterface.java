package com.testvagrant.ekam.db.accounts;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface AccountsDBInterface {
    @SqlQuery("SELECT username from accounts WHERE user_id = :userId")
    String getAccountName(@Bind("userId") Integer userId);
}
