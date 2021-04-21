package com.testvagrant.ekam.api.assertions;

import org.apache.hc.core5.http.HttpStatus;
import org.testng.Assert;
import retrofit2.Response;

public class BaseApiAssertions {
  protected <T> void assertThatStatusIsOK(Response<T> response) {
    Assert.assertEquals(response.code(), HttpStatus.SC_OK);
  }
}
