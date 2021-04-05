package com.testvagrant.ekam.api.endpoints;

import com.testvagrant.ekam.api.models.CatFacts;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface AnimalFactsService {

  @GET("/facts")
  Call<List<CatFacts>> catFacts();
}
