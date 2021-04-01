package com.testvagrant.ekam.api;

import com.testvagrant.ekam.api.httpclients.GrpcClient;
import com.testvagrant.ekam.api.httpclients.HttpClient;
import com.testvagrant.ekam.testbase.EkamApiTest;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

public class APIUnitTest extends EkamApiTest {
    @Inject
    HttpClient httpClient;

    @Inject
    GrpcClient grpcClient;

    @Test(groups = "unit")
    public void apiInjects() {
        assertThat(httpClient).isNotNull();
        assertThat(grpcClient).isNotNull();
    }
}
