package com.testvagrant.ekam.mobile;

import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.models.EkamTest;

public class EkamMobileInjector {

  private final EkamTest ekamTest;

  public EkamMobileInjector(EkamTest ekamTest) {
    this.ekamTest = ekamTest;
  }

  public void createInjector(boolean enableWeb) {
    new InjectorCreator(ekamTest).createMobileInjector(enableWeb);
  }
}
