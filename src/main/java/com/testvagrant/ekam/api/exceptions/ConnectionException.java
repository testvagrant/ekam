package com.testvagrant.ekam.api.exceptions;

public class ConnectionException extends RuntimeException {

    public ConnectionException(Throwable cause) {
        super("Getting socket time out check your connection", cause);
    }
}
