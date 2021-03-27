package com.testvagrant.ekam.api.assertions;

import org.apache.http.HttpStatus;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseApiAssertions {

    protected void assertThatStatusIsOK(Response response) {
        assertThat(response.code()).isEqualTo(HttpStatus.SC_OK);
    }
}
