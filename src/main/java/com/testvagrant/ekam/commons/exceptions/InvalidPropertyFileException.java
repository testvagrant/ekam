package com.testvagrant.ekam.commons.exceptions;

public class InvalidPropertyFileException extends RuntimeException {

  public InvalidPropertyFileException(String file) {
    super(String.format("Cannot load %s.properties", file));
  }
}
