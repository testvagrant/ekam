package com.testvagrant.ekam.dataClients;

import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.testvagrant.ekam.commons.DataClient;
import com.testvagrant.ekam.commons.cache.DataSetsCache;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.ekam.models.customerDetails.Credentials;

import java.util.List;

public class LoginDataClient extends DataClient {

    @Inject
    DataSetsCache dataSetsCache;

    public Credentials getKycVerifiedUser() {
        return getUser("kycVerified");
    }

    public Credentials getUser(String userKey) {
        try {
            List<Credentials> kycVerified = unBoxAsList(dataSetsCache.get(userKey), Credentials.class);
            return findFirst(kycVerified);
        } catch (NoSuchKeyException e) {
            return new Credentials();
        }
    }
}
