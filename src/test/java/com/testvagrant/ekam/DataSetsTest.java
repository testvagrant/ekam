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
public class DataSetsTest {

  @Inject LoginDataSetsClient loginDataClient;

  @Test
  public void loadDataFromDataSets() {
    Credentials kycVerifiedUser = loginDataClient.getKycVerifiedUser();
    Assert.assertEquals(kycVerifiedUser.getEmail(), "qa6testt@gmail.com");
    Assert.assertEquals(kycVerifiedUser.getPassword(), "12345678");
  }

  @Test
  public void loadDefaultDataWhenKeyIsNotPresent() {
    Credentials kycVerifiedUser = loginDataClient.getUser("abracadabra");
    Assert.assertEquals(kycVerifiedUser.getEmail(), "default@email.com");
  }

  @Test
  public void loadDataWithoutKey() {
    Credentials kycVerifiedUser = loginDataClient.getUser("login_credentials_without_key.json");
    Assert.assertEquals(kycVerifiedUser.getEmail(), "nokeytest@gmail.com");
  }

  @Test
  public void loadDataWithoutArray() {
    Credentials kycVerifiedUser = loginDataClient.getUser("kycNotVerified");
    Assert.assertEquals(kycVerifiedUser.getEmail(), "qaNotVerifiedtestt@gmail.com");
  }
}
