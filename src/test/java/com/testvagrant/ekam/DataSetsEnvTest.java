package com.testvagrant.ekam;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.testvagrant.ekam.commons.modules.CacheModule;
import com.testvagrant.ekam.dataClients.LoginDataClient;
import com.testvagrant.ekam.models.customerDetails.Credentials;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
public class DataSetsEnvTest {

    @Test
    public void loadDataByEnv() {
        System.setProperty("env", "dev");
        Injector cacheInjector = com.google.inject.Guice.createInjector(new CacheModule());
        LoginDataClient loginDataClient = cacheInjector.getInstance(LoginDataClient.class);
        Credentials kycVerifiedUser = loginDataClient.getKycVerifiedUser();
        Assert.assertEquals(kycVerifiedUser.getEmail(), "dev6testt@gmail.com");
        Assert.assertEquals(kycVerifiedUser.getPassword(), "12345678");
    }

}
