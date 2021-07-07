package com.testvagrant.ekam.commons.exceptions;

public class TestFeedTargetsNotFoundException extends RuntimeException {

  public TestFeedTargetsNotFoundException(String testFeedName) {
    super("No Targets found in testFeed: " + testFeedName);
  }
}
