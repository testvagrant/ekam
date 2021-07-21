package com.testvagrant.ekam.mobile.initializers;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.factories.DeviceCacheDisposeFactory;
import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.models.EkamTest;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.config.models.EkamConfig;

import java.util.Objects;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;

public class EkamMobileTest {

  private final EkamTest ekamTest;

  public EkamMobileTest(EkamTest ekamTest) {
    this.ekamTest = ekamTest;
  }

  public void init(boolean enableWeb) {
    new InjectorCreator(ekamTest).createMobileInjector(enableWeb);
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
