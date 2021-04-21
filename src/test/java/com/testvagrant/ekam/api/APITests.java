package com.testvagrant.ekam.api;

import com.google.inject.Inject;
import com.testvagrant.ekam.api.assertions.AnimalFactsAssertions;
import com.testvagrant.ekam.api.endpoints.AnimalFactsClient;
import com.testvagrant.ekam.api.models.CatFacts;
import com.testvagrant.ekam.testbase.EkamApiTest;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.util.List;

public class APITests extends EkamApiTest {

  @Inject private AnimalFactsClient animalFactsClient;

  @Inject private AnimalFactsAssertions animalFactsAssertions;

  @Test(groups = "api")
  public void getAnimalFacts() {
    Response<List<CatFacts>> catFacts = animalFactsClient.getCatFacts();
    animalFactsAssertions.assertThatCatFactsAreAvailable(catFacts);
  }
}
