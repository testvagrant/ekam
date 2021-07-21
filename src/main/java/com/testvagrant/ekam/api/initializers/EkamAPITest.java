package com.testvagrant.ekam.api.initializers;

import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.models.EkamTest;

public class EkamAPITest {

  private final EkamTest ekamTest;

  public EkamAPITest(EkamTest ekamTest) {
    this.ekamTest = ekamTest;
  }

  public void init() {
    new InjectorCreator(ekamTest).injectApiAndStepRecorder();
  }
}
