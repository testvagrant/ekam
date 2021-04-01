package com.testvagrant.ekam.db;

import com.testvagrant.ekam.testbase.EkamApiTest;
import com.testvagrant.ekam.testbase.EkamDBTest;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

public class DBTests extends EkamDBTest {

    @Inject AccountsDBClient accountsDBClient;

    @Test(groups = "db")
    public void accountsTest() {
        String userName = accountsDBClient.getUserName(1001);
        assertThat(userName).isEqualTo("ekam_user");
    }
}
