package com.testvagrant.ekam.db;

import com.testvagrant.ekam.testbase.EkamDBTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class DBTests extends EkamDBTest {

  @Inject AccountsDBClient accountsDBClient;

  @Test(groups = "db")
  public void accountsTest() {
    String userName = accountsDBClient.getUserName(1001);
    Assert.assertEquals(userName, "ekam_user");
  }
}
