package com.testvagrant.ekam.api.httpclients;

import com.google.inject.Inject;
import okhttp3.Interceptor;
import retrofit2.Retrofit;

public class BaseClient {

  protected RetrofitClient httpClient;

  @Inject
  public BaseClient(RetrofitClient httpClient, String baseUrl) {
    this.httpClient = httpClient;
    this.httpClient.build(baseUrl);
  }

  public BaseClient(String baseUrl, Interceptor... interceptors) {
    this.httpClient.build(baseUrl, interceptors);
  }
}
