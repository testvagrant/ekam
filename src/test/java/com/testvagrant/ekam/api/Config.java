package com.testvagrant.ekam.api;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class Config {

  @Inject
  @Named("host")
  private String host;

  public String getHost() {
    return host;
  }
}
