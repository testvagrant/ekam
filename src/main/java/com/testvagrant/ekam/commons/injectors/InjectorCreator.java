package com.testvagrant.ekam.commons.injectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.PropertyModule;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.modules.OptimusRunTargetModule;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.WebModule;
import com.testvagrant.optimus.core.models.TargetDetails;
import com.testvagrant.optimus.core.models.mobile.MobileDriverDetails;
import com.testvagrant.optimus.core.models.web.WebDriverDetails;
import com.testvagrant.optimus.core.screenshots.OptimusRunContext;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import org.openqa.selenium.WebDriver;

public class InjectorCreator {
  private Injector mobileDriverInjector, webDriverInjector;
  private final EkamTestContext ekamTestContext;

  public InjectorCreator(EkamTestContext ekamTestContext) {
    this.ekamTestContext = ekamTestContext;
  }

  public InjectorCreator createMobileInjector() {
    return this.createMobileInjector(false);
  }

  public InjectorCreator createWebInjector() {
    return this.createWebInjector(false);
  }

  public InjectorCreator createMobileInjector(boolean enableWeb) {
    Injector baseInjector = Guice.createInjector(new ModulesLibrary().mobileModules());

    MobileDriverDetails mobileDriverDetails = baseInjector.getInstance(MobileDriverDetails.class);
    OptimusRunContext mobileContext =
        getOptimusRunContext(
            mobileDriverDetails.getDriver(), mobileDriverDetails.getTargetDetails());
    OptimusRunTarget optimusRunTarget = new OptimusRunTarget(mobileContext);
    baseInjector = enableWeb(enableWeb, baseInjector, optimusRunTarget);
    mobileDriverInjector =
        baseInjector.createChildInjector(
            new OptimusRunTargetModule(optimusRunTarget, ekamTestContext));
    mobileDriverInjector = createApiInjector(mobileDriverInjector);
    updateInjectors(mobileDriverInjector);
    return this;
  }

  public InjectorCreator createWebInjector(boolean enableMobile) {
    Injector baseInjector = Guice.createInjector(new ModulesLibrary().webModules());
    WebDriverDetails webDriverDetails = baseInjector.getInstance(WebDriverDetails.class);
    OptimusRunContext mobileContext =
        getOptimusRunContext(webDriverDetails.getDriver(), webDriverDetails.getTargetDetails());
    OptimusRunTarget optimusRunTarget = new OptimusRunTarget(mobileContext);
    baseInjector = enableWeb(enableMobile, baseInjector, optimusRunTarget);
    webDriverInjector =
        baseInjector.createChildInjector(
            new OptimusRunTargetModule(optimusRunTarget, ekamTestContext));
    webDriverInjector = createApiInjector(webDriverInjector);
    updateInjectors(webDriverInjector);
    return this;
  }

  private void updateInjectors(Injector baseInjector) {
    InjectorsCacheProvider.getInstance()
        .put(
            Injectors.WEB_PAGE_INJECTOR.getInjector(String.valueOf(ekamTestContext.hashCode())),
            baseInjector);
    InjectorsCacheProvider.getInstance()
        .put(
            Injectors.MOBILE_PAGE_INJECTOR.getInjector(
                String.valueOf(ekamTestContext.hashCode())),
            baseInjector);
    InjectorsCacheProvider.getInstance()
            .put(
                    Injectors.API_INJECTOR.getInjector(
                            String.valueOf(ekamTestContext.hashCode())),
                    baseInjector);
  }

  public InjectorCreator createApiInjector() {
    OptimusRunContext mobileContext =
            getOptimusRunContext(
                    null, null);
    OptimusRunTarget optimusRunTarget = new OptimusRunTarget(mobileContext);
    Injector apiInjector = createApiInjector(Guice.createInjector(new ModulesLibrary().baseModules()));
    apiInjector = apiInjector.createChildInjector(new OptimusRunTargetModule(optimusRunTarget, ekamTestContext));
    InjectorsCacheProvider.getInstance()
            .put(
                    Injectors.API_INJECTOR.getInjector(String.valueOf(ekamTestContext.hashCode())),
                    apiInjector);
    return this;
  }

  public Injector createApiInjector(Injector baseInjector) {
    Injector apiInjector = baseInjector.createChildInjector(new PropertyModule(), new GrpcModule());
    return apiInjector;
  }


  public Injector getMobileDriverInjector() {
    return mobileDriverInjector;
  }

  public Injector getWebDriverInjector() {
    return webDriverInjector;
  }

  private Injector enableWeb(
      boolean enableWeb, Injector baseInjector, OptimusRunTarget optimusRunTarget) {
    if (enableWeb) {
      Injector webInjector = baseInjector.createChildInjector(new WebModule());
      WebDriverDetails webDriverDetails = webInjector.getInstance(WebDriverDetails.class);
      OptimusRunContext webContext =
          getOptimusRunContext(webDriverDetails.getDriver(), webDriverDetails.getTargetDetails());
      optimusRunTarget.addWebContext(webContext);
      return webInjector;
    }
    return baseInjector;
  }

  private Injector enableMobile(
      boolean enableMobile, Injector baseInjector, OptimusRunTarget optimusRunTarget) {
    if (enableMobile) {
      Injector mobileInjector = baseInjector.createChildInjector(new MobileModule());
      MobileDriverDetails mobileDriverDetails =
          mobileInjector.getInstance(MobileDriverDetails.class);
      OptimusRunContext mobileContext =
          getOptimusRunContext(
              mobileDriverDetails.getDriver(), mobileDriverDetails.getTargetDetails());
      optimusRunTarget.addMobileContext(mobileContext);
      return mobileInjector;
    }
    return baseInjector;
  }

  private OptimusRunContext getOptimusRunContext(WebDriver webDriver, TargetDetails targetDetails) {
    return OptimusRunContext.builder()
        .webDriver(webDriver)
        .build()
        .addTarget(targetDetails)
        .testPath(ekamTestContext.getFeatureName(), ekamTestContext.getTestName());
  }
}
