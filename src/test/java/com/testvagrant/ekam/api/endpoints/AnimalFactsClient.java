package com.testvagrant.ekam.api.endpoints;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.api.httpclients.BaseClient;
import com.testvagrant.ekam.api.httpclients.HttpClient;
import com.testvagrant.ekam.api.models.CatFacts;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

public class AnimalFactsClient extends BaseClient {

    private AnimalFactsService animalFactsService;
    @Inject
    public AnimalFactsClient(HttpClient httpClient, @Named("catFactsHost") String baseUrl) {
        super(httpClient, baseUrl);
        animalFactsService = (AnimalFactsService) httpClient.getService(AnimalFactsService.class);
    }

    public <T> Response<T> getCatFacts() {
        Call<List<CatFacts>> responseCall = animalFactsService.catFacts();
        return httpClient.executeAsResponse(responseCall);
    }
}
