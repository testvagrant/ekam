package com.testvagrant.ekam.api.httpclients;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.testvagrant.ekam.api.httpclients.retrofit.CallTypeFinder;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class RetrofitClient implements HttpClient<Retrofit> {

    Retrofit retrofit;

    @Inject
    CallTypeFinder callTypeFinder;

    @Inject
    Logger logger;

    @Inject
    Gson gson;

    @Override
    public HttpClient<Retrofit> build(String baseUrl) {
        HttpLoggingInterceptor interceptor = getHttpLoggingInterceptor();
        OkHttpClient okHttpClient = getOKhttpBuilder(interceptor)
                .build();
         retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                 .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
         return this;
    }

    @Override
    public HttpClient<Retrofit> build(String baseUrl, Interceptor... interceptors) {
        HttpLoggingInterceptor interceptor = getHttpLoggingInterceptor();
        OkHttpClient okHttpClient = getOKhttpBuilder(interceptor)
                .addInterceptor(new AllureOkHttp3())
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        return this;
    }

    private OkHttpClient.Builder getOKhttpBuilder(Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Arrays.asList(interceptors).forEach(builder::addNetworkInterceptor);
        return builder;
    }

    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Override
    public <Type> Type execute(Call<Type> call) {
        Exception ex;
        Response<Type> execute = executeAsResponse(call);
        if(callTypeFinder.getType(call).equals(Response.class)) return (Type) execute;
        try {
            return execute.isSuccessful()? execute.body(): gson.fromJson(execute.errorBody().string(), mapErrorType(call));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot parse error %s", e.getMessage()));
        }
    }

    @Override
    public <Type> Type executeAsObj(Call<Type> call) {
        return execute(call);
    }

    @Override
    public <Type> Response<Type> executeAsResponse(Call<Type> call) {
        Exception ex;
        try {
            Response<Type> execute = call.execute();
            return execute;
        } catch (IOException e) {
            ex = e;
        }
        throw new RuntimeException(String.format("Cannot execute call %s due to %s ", call.request(), ex.getMessage()));
    }



    public <Service> Service getService(Class<Service> serviceClass) {
        return retrofit.create(serviceClass);
    }


    private <Type> Class<Type> mapErrorType(Call<Type> call) {
        return callTypeFinder.getType(call);
    }

}
