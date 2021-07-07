package com.testvagrant.ekam.commons.exceptions;

import org.apache.commons.lang3.StringUtils;

public class TestFeedNotFoundException extends RuntimeException {
  public TestFeedNotFoundException(String path, String[] paths) {
    super(
        String.format(
            "TestFeed %s not found in any of these paths:\n %s",
            path, StringUtils.join(paths, "\n")));
  }
}
