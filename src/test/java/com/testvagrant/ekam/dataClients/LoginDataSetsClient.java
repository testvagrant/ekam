package com.testvagrant.ekam.dataClients;

import com.google.gson.internal.LinkedTreeMap;
import com.google.inject.Inject;
import com.testvagrant.ekam.commons.DataSetsClient;
import com.testvagrant.ekam.commons.cache.DataSetsCache;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.ekam.models.customerDetails.Credentials;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class LoginDataSetsClient extends DataSetsClient {

    @Inject
    public LoginDataSetsClient(DataSetsCache dataSetsCache) {
        super(dataSetsCache);
    }

    public Credentials getKycVerifiedUser() {
        return getKycVerifiedUser(false);
    }

    public Credentials getKycVerifiedUser(boolean lock) {
        return getUser("kycVerified", lock);
    }

    public Credentials getUser(String userKey) {
        return getUser(userKey, false);
    }

    public Credentials getUser(String userKey, boolean lock) {
        try {
            Credentials kycVerified = getValue(userKey, Credentials.class, lock);
            return kycVerified;
        } catch (NoSuchKeyException e) {
            return new Credentials();
        }
    }

    public void release(Credentials credentials) {
        super.release(credentialsPredicate(credentials));
    }


    private <T> Predicate<Map.Entry<String, LinkedTreeMap>> credentialsPredicate(Credentials credentials) {
        return entry -> entry.getValue().get("email").equals(credentials.getEmail());
    }
}
