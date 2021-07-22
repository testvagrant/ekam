package com.testvagrant.ekam.commons.remote.exceptions;

public class HubNotFoundException extends RuntimeException {

  public HubNotFoundException(String hub) {
    super(String.format("Hub %s is not found under cloud", hub));
  }
}
