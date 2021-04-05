package com.testvagrant.ekam.commons.exceptions;

public class UnReachableSiteException extends RuntimeException {

  public UnReachableSiteException(String url) {
    super(
        String.format(
            "Cannot reach site \"%s\" at this moment, quitting execution!! "
                + "Please check with your developer or try after sometime",
            url));
  }
}
