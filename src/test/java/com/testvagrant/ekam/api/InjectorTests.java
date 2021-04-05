package com.testvagrant.ekam.api;

import com.testvagrant.ekam.api.httpclients.GrpcClient;
import com.testvagrant.ekam.api.httpclients.HttpClient;
import com.testvagrant.ekam.testbase.EkamApiTest;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectorTests extends EkamApiTest {
  @Inject private HttpClient httpClient;

  @Inject private GrpcClient grpcClient;

  @Test(groups = "unit")
  public void apiInjects() {
    assertThat(httpClient).isNotNull();
    assertThat(grpcClient).isNotNull();
  }
}
