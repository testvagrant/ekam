package com.testvagrant.ekam.mobile.initializers;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.factories.DeviceCacheDisposeFactory;
import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.commons.testContext.EkamTestDetails;
import com.testvagrant.ekam.config.models.EkamConfig;

import java.util.Objects;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;

public class EkamMobileTestContext {

  private final EkamTestDetails ekamTestDetails;

  public EkamMobileTestContext(EkamTestDetails ekamTestDetails) {
    this.ekamTestDetails = ekamTestDetails;
  }

  public void init() {
    new InjectorCreator(ekamTestDetails).createMobileInjector(false);
  }

  public void dispose() {
    MobileDriverDetails mobileDriverDetails = null;
    Injector injector = injectorsCache().getInjector();
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

}
