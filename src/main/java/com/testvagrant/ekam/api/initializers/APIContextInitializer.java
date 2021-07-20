package com.testvagrant.ekam.api.initializers;

import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;

public class APIContextInitializer {

  private final EkamTestContext ekamTestContext;

  public APIContextInitializer(EkamTestContext ekamTestContext) {
    this.ekamTestContext = ekamTestContext;
  }

  public void init() {
    new InjectorCreator(ekamTestContext).createApiInjector();
  }
}
