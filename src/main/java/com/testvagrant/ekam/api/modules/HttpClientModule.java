package com.testvagrant.ekam.api.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.api.httpclients.HttpClient;
import com.testvagrant.ekam.api.httpclients.RetrofitClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class HttpClientModule extends AbstractModule {
    @Override
    protected void configure() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        requestInjection(httpLoggingInterceptor);
        bind(HttpClient.class).to(RetrofitClient.class);
    }
}
