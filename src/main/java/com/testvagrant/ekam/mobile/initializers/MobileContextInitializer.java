package com.testvagrant.ekam.mobile.initializers;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.factories.DeviceCacheDisposeFactory;
import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;
import com.testvagrant.ekam.config.models.EkamConfig;

import java.util.Objects;

public class MobileContextInitializer {

  private final EkamTestContext ekamTestContext;

  public MobileContextInitializer(EkamTestContext ekamTestContext) {
    this.ekamTestContext = ekamTestContext;
  }

  public void init() {
    new InjectorCreator(ekamTestContext).createMobileInjector();
  }

  public void dispose() {
    MobileDriverDetails mobileDriverDetails = null;
    Injector injector = getInjector();
    EkamConfig ekamConfig = injector.getInstance(EkamConfig.class);
    try {
      mobileDriverDetails = injector.getInstance(MobileDriverDetails.class);
      mobileDriverDetails.getDriver().quit();
      if (Objects.nonNull(mobileDriverDetails.getService())) {
        mobileDriverDetails.getService().stop();
      }
    } finally {
      assert mobileDriverDetails != null;
      DeviceCacheDisposeFactory.dispose(
          mobileDriverDetails.getTargetDetails(), ekamConfig.getMobile());
    }
  }

  private Injector getInjector() {
    return InjectorsCacheProvider.getInstance().get();
  }
}
