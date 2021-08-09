package com.testvagrant.ekam.internal.injectors;

import com.google.inject.Injector;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.modules.ModulesLibrary;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;

public class EkamMobileInjector extends EkamInjector {
  public EkamMobileInjector(EkamTest ekamTest) {
    super(ekamTest);
  }

  public Injector create() {
    Injector mobileInjector = createInjector(new ModulesLibrary().mobileModules());
    MobileDriverDetails mobileDriverDetails = mobileInjector.getInstance(MobileDriverDetails.class);
    testContext.addTarget(mobileDriverDetails.getTargetDetails());
    createTargetJson();
    return mobileInjector;
  }
}
