package com.testvagrant.ekam.commons.injectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.commons.modules.EkamRunTargetModule;
import com.testvagrant.ekam.commons.runcontext.EkamRunContext;
import com.testvagrant.ekam.commons.runcontext.EkamRunTarget;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.WebModule;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class InjectorCreator {
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
    EkamRunContext mobileContext =
        getEkamRunContext(mobileDriverDetails.getDriver(), mobileDriverDetails.getTargetDetails());
    EkamRunTarget optimusRunTarget = new EkamRunTarget(mobileContext);
    baseInjector = enableWeb(enableWeb, baseInjector, optimusRunTarget);
    Injector mobileDriverInjector =
        baseInjector.createChildInjector(
            new EkamRunTargetModule(optimusRunTarget, ekamTestContext));
    mobileDriverInjector = createApiInjector(mobileDriverInjector);
    updateInjectors(mobileDriverInjector);
    return this;
  }

  public InjectorCreator createWebInjector(boolean enableMobile) {
    Injector baseInjector = Guice.createInjector(new ModulesLibrary().webModules());
    WebDriver webDriver = baseInjector.getInstance(WebDriver.class);
    EkamRunContext mobileContext = getEkamRunContext(webDriver, buildTargetDetails(webDriver));
    EkamRunTarget optimusRunTarget = new EkamRunTarget(mobileContext);
    baseInjector = enableWeb(enableMobile, baseInjector, optimusRunTarget);
    Injector webDriverInjector =
        baseInjector.createChildInjector(
            new EkamRunTargetModule(optimusRunTarget, ekamTestContext));
    webDriverInjector = createApiInjector(webDriverInjector);
    updateInjectors(webDriverInjector);
    return this;
  }

  private void updateInjectors(Injector baseInjector) {
    InjectorsCacheProvider.getInstance().put(baseInjector);
  }

  public InjectorCreator createApiInjector() {
    EkamRunContext mobileContext = getEkamRunContext(null, null);
    EkamRunTarget optimusRunTarget = new EkamRunTarget(mobileContext);
    Injector apiInjector =
        createApiInjector(Guice.createInjector(new ModulesLibrary().baseModules()));
    apiInjector =
        apiInjector.createChildInjector(new EkamRunTargetModule(optimusRunTarget, ekamTestContext));
    updateInjectors(apiInjector);
    return this;
  }

  public Injector createApiInjector(Injector baseInjector) {
    Injector apiInjector = baseInjector.createChildInjector(new ApiHostsModule(), new GrpcModule());
    return apiInjector;
  }

  private Injector enableWeb(
      boolean enableWeb, Injector baseInjector, EkamRunTarget optimusRunTarget) {
    if (enableWeb) {
      Injector webInjector = baseInjector.createChildInjector(new WebModule());
      WebDriver webDriver = webInjector.getInstance(WebDriver.class);
      EkamRunContext webContext = getEkamRunContext(webDriver, buildTargetDetails(webDriver));
      optimusRunTarget.addWebContext(webContext);
      return webInjector;
    }
    return baseInjector;
  }

  private Injector enableMobile(
      boolean enableMobile, Injector baseInjector, EkamRunTarget optimusRunTarget) {
    if (enableMobile) {
      Injector mobileInjector = baseInjector.createChildInjector(new MobileModule());
      MobileDriverDetails mobileDriverDetails =
          mobileInjector.getInstance(MobileDriverDetails.class);
      EkamRunContext mobileContext =
          getEkamRunContext(
              mobileDriverDetails.getDriver(), mobileDriverDetails.getTargetDetails());
      optimusRunTarget.addMobileContext(mobileContext);
      return mobileInjector;
    }
    return baseInjector;
  }

  private EkamRunContext getEkamRunContext(WebDriver webDriver, TargetDetails targetDetails) {
    return EkamRunContext.builder()
        .webDriver(webDriver)
        .build()
        .addTarget(targetDetails)
        .testPath(ekamTestContext.getFeatureName(), ekamTestContext.getTestName());
  }

  public TargetDetails buildTargetDetails(WebDriver webDriver) {
    Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();
    return TargetDetails.builder()
        .name(capabilities.getBrowserName())
        .platformVersion(capabilities.getVersion())
        .platform(EkamSupportedPlatforms.valueOf(capabilities.getBrowserName().toUpperCase()))
        .build();
  }
}
