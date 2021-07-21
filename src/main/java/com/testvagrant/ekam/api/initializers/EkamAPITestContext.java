package com.testvagrant.ekam.api.initializers;

import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.testContext.EkamTestDetails;

public class EkamAPITestContext {

  private final EkamTestDetails ekamTestDetails;

  public EkamAPITestContext(EkamTestDetails ekamTestDetails) {
    this.ekamTestDetails = ekamTestDetails;
  }

  public void init() {
    new InjectorCreator(ekamTestDetails).createApiInjector();
  }
}
