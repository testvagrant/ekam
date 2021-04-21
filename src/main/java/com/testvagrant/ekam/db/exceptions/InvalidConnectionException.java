package com.testvagrant.ekam.db.exceptions;

public class InvalidConnectionException extends Exception {

  public InvalidConnectionException(String file, String name) {
    super(String.format("Cannot find a configuration entry for %s in %s", name, file));
  }
}
