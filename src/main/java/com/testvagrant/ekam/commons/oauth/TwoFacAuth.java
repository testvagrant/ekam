package com.testvagrant.ekam.commons.oauth;

import org.jboss.aerogear.security.otp.Totp;

public class TwoFacAuth {
    public String getOtp(String secret) {
        Totp totp = new Totp(secret);
        return totp.now();
    }
}



