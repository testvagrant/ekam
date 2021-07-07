package com.testvagrant.ekam.commons.exceptions;

public class InvalidFileException extends RuntimeException {

  public InvalidFileException(String file) {
    super(String.format("Cannot load %s.json", file));
  }
}
