package com.testvagrant.ekam.api.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.api.annotations.GrpcResponse;
import com.testvagrant.ekam.api.interceptors.GrpcResponseInterceptor;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class GrpcModule extends AbstractModule {

  @Override
  protected void configure() {
    GrpcResponseInterceptor grpcResponseInterceptor = new GrpcResponseInterceptor();
    bindInterceptor(any(), annotatedWith(GrpcResponse.class), grpcResponseInterceptor);
  }
}
