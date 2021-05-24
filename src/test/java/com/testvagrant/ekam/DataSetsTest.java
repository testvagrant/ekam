package com.testvagrant.ekam;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.ekam.commons.modules.CacheModule;
import com.testvagrant.ekam.dataClients.LoginDataClient;
import com.testvagrant.ekam.models.customerDetails.Credentials;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
@Guice(modules = {CacheModule.class})
public class DataSetsTest {

    @Inject
    LoginDataClient loginDataClient;

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


}
