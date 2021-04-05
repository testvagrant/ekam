package com.testvagrant.ekam.api.assertions;

import com.testvagrant.ekam.api.models.CatFacts;
import retrofit2.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalFactsAssertions extends BaseApiAssertions {

  public void assertThatCatFactsAreAvailable(Response<List<CatFacts>> catFactsResponse) {
    assertThatStatusIsOK(catFactsResponse);
    List<CatFacts> catFacts = catFactsResponse.body();
    assert catFacts != null;
    assertThat(catFacts.size()).isGreaterThan(0);
  }
}
