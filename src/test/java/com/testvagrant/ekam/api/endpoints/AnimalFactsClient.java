package com.testvagrant.ekam.api.endpoints;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.api.models.CatFacts;
import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

public class AnimalFactsClient extends RetrofitBaseClient {

  private final AnimalFactsService animalFactsService;

  @Inject
  public AnimalFactsClient(RetrofitClient httpClient, @Named("catFactsHost") String baseUrl) {
    super(httpClient, baseUrl);
    animalFactsService = httpClient.getService(AnimalFactsService.class);
  }

  public Response<List<CatFacts>> getCatFacts() {
    Call<List<CatFacts>> responseCall = animalFactsService.catFacts();
    return httpClient.executeAsResponse(responseCall);
  }
}
