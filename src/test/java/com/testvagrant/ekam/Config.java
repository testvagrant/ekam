package com.testvagrant.ekam;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class Config {
    @Named("host")
    @Inject
    private String host;

    public String getHost() {
        return host;
    }
}
