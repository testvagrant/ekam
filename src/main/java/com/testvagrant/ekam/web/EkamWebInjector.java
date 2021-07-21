package com.testvagrant.ekam.web;

import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.models.EkamTest;

public class EkamWebInjector {

  private final EkamTest ekamTest;

  public EkamWebInjector(EkamTest ekamTest) {
    this.ekamTest = ekamTest;
  }

  public void createInjector(boolean enableMobile) {
    new InjectorCreator(ekamTest).createWebInjector(enableMobile);
  }
}
