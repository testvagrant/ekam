package com.testvagrant.ekam.api;

import com.testvagrant.ekam.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTests extends BaseTest {
  @Test
  public void apiConfigTest() {
    Assert.assertEquals("abc", initConfig().getHost());
  }
}
