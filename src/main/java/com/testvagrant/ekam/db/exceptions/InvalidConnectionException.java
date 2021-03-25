package com.testvagrant.ekam.db.exceptions;

public class InvalidConnectionException extends Exception {

    public InvalidConnectionException(String env, String name) {
        super(String.format("Cannot find a configuration entry for %s in %s"+".yaml"));
    }
}
