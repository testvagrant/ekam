package com.testvagrant.ekam.testFeed;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = ApiHostsModule.class)
public class ApiTestFeedTest {

  @Inject(optional = true)
  @Named("host")
  private String host;

  @Test
  public void apiHostTest() {
    Assert.assertEquals(host, "abc");
  }
}
