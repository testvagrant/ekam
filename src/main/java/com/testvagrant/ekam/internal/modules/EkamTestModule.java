package com.testvagrant.ekam.internal.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;

public class EkamTestModule extends AbstractModule {

  private final EkamTest ekamTest;

  public EkamTestModule(EkamTest ekamTest) {
    this.ekamTest = ekamTest;
  }

  @Override
  protected void configure() {
    bind(EkamTest.class).toInstance(ekamTest);
  }
}
