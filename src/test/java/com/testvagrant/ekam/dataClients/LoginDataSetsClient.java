package com.testvagrant.ekam.dataClients;

import com.google.inject.Inject;
import com.testvagrant.ekam.commons.DataSetsClient;
import com.testvagrant.ekam.commons.cache.DataSetsCache;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.ekam.models.customerDetails.Credentials;

import java.util.List;

public class LoginDataSetsClient extends DataSetsClient {

    @Inject
    public LoginDataSetsClient(DataSetsCache dataSetsCache) {
        super(dataSetsCache);
    }

    public Credentials getKycVerifiedUser() {
        return getUser("kycVerified");
    }

    public Credentials getUser(String userKey) {
        try {
            List<Credentials> kycVerified = getValue(userKey, Credentials.class);
            return findFirst(kycVerified);
        } catch (NoSuchKeyException e) {
            return new Credentials();
        }
    }
}
