package com.testvagrant.ekam.api.httpclients;

import com.google.inject.Inject;
import okhttp3.Interceptor;

public class BaseClient {

    @Inject
    protected HttpClient httpClient;

    public BaseClient(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.httpClient.build(baseUrl);
    }

    public BaseClient(HttpClient httpClient, String baseUrl, Interceptor... interceptors) {
        this.httpClient = httpClient;
        this.httpClient.build(baseUrl, interceptors);
    }
}
