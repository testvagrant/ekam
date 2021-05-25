package com.testvagrant.ekam;

import com.google.inject.Inject;
import com.testvagrant.ekam.commons.modules.CacheModule;
import com.testvagrant.ekam.dataClients.LoginDataSetsClient;
import com.testvagrant.ekam.models.customerDetails.Credentials;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
@Guice(modules = {CacheModule.class})
public class SharedDataSetsTest {

  @Inject LoginDataSetsClient loginDataClient;

  @Test
  public void loadDataAndLock() {
    Credentials kycVerifiedUser7 = loginDataClient.getKycVerifiedUser(true);
    Assert.assertEquals(kycVerifiedUser7.getEmail(), "qa7testt@gmail.com");
    Assert.assertEquals(kycVerifiedUser7.getPassword(), "12345678");
    Credentials kycVerifiedUser6 = loginDataClient.getKycVerifiedUser(true);
    Assert.assertEquals(kycVerifiedUser6.getEmail(), "qa6testt@gmail.com");
    Assert.assertEquals(kycVerifiedUser6.getPassword(), "12345678");
    loginDataClient.release(kycVerifiedUser7);
    kycVerifiedUser7 = loginDataClient.getKycVerifiedUser();
    Assert.assertEquals(kycVerifiedUser7.getEmail(), "qa7testt@gmail.com");
  }

  @Test
  public void getDefaultDataWhenReachedMaxUsers() {
    Credentials kycVerifiedUser7 = loginDataClient.getKycVerifiedUser(true);
    Assert.assertEquals(kycVerifiedUser7.getEmail(), "qa7testt@gmail.com");
    Assert.assertEquals(kycVerifiedUser7.getPassword(), "12345678");
    Credentials kycVerifiedUser6 = loginDataClient.getKycVerifiedUser(true);
    Assert.assertEquals(kycVerifiedUser6.getEmail(), "qa6testt@gmail.com");
    Assert.assertEquals(kycVerifiedUser6.getPassword(), "12345678");
    Credentials defaultUser = loginDataClient.getKycVerifiedUser(true);
    Assert.assertEquals(defaultUser.getEmail(), "default@email.com");
    loginDataClient.release(kycVerifiedUser6);
    loginDataClient.release(kycVerifiedUser7);
    loginDataClient.release(defaultUser);
  }
}
