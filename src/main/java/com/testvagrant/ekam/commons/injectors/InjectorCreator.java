package com.testvagrant.ekam.commons.injectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.commons.modules.EkamTestModule;
import com.testvagrant.ekam.commons.runcontext.EkamTestContext;
import com.testvagrant.ekam.commons.runcontext.EkamTestExecutionDetailsManager;
import com.testvagrant.ekam.commons.testContext.EkamTestDetails;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.WebModule;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;

public class InjectorCreator {

  private final EkamTestDetails ekamTestDetails;

  public InjectorCreator(EkamTestDetails ekamTestDetails) {
    this.ekamTestDetails = ekamTestDetails;
  }

  /** Creates API Injector with APIHosts and GRPC modules. Adds the injector to injector cache */
  public void createApiInjector() {
    EkamTestContext apiTestsEkamTestContext = buildEkamTestContext(null, null);
    EkamTestExecutionDetailsManager testContextDetailsManager =
        new EkamTestExecutionDetailsManager(apiTestsEkamTestContext);
    testContextDetailsManager.saveTargetDetails();

    Injector baseInjector = Guice.createInjector(new ModulesLibrary().apiModules());
    EkamTestModule ekamTestModule = new EkamTestModule(testContextDetailsManager, ekamTestDetails);
    Injector apiInjector = baseInjector.createChildInjector(ekamTestModule);
    injectorsCache().put(apiInjector);
  }

  /** Create mobile injector binding AppiumDriver, SwitchView and WebModule(if enabled) */
  public void createMobileInjector(boolean enableWeb) {
    // Create base injector with mobile modules
    Injector baseInjector = Guice.createInjector(new ModulesLibrary().mobileModules());
    MobileDriverDetails mobileDriverDetails = baseInjector.getInstance(MobileDriverDetails.class);
    EkamTestContext mobileContext =
        buildEkamTestContext(
            mobileDriverDetails.getDriver(), mobileDriverDetails.getTargetDetails());

    // Create screenshot directory and target.json for the current test
    EkamTestExecutionDetailsManager testContextDetailsManager =
        new EkamTestExecutionDetailsManager(mobileContext);
    testContextDetailsManager.createScreenShotDirectory();
    testContextDetailsManager.saveTargetDetails();

    // Bind web modules to base injector
    if (enableWeb) baseInjector = enableWeb(baseInjector, testContextDetailsManager);

    Injector mobileDriverInjector =
        baseInjector.createChildInjector(
            new EkamTestModule(testContextDetailsManager, ekamTestDetails));

    // Bind API injector to base injector and add to injector cache
    mobileDriverInjector = createApiInjector(mobileDriverInjector);
    injectorsCache().put(mobileDriverInjector);
  }

  /** Create web injector binding WebDriver, SwitchView and MobileModule(if enabled) */
  public void createWebInjector(boolean enableMobile) {
    // Create base injector with web modules
    Injector baseInjector = Guice.createInjector(new ModulesLibrary().webModules());
    WebDriver webDriver = baseInjector.getInstance(WebDriver.class);
    EkamTestContext webContext = buildEkamTestContext(webDriver, buildTargetDetails(webDriver));

    // Create screenshot directory and target.json for the current test
    EkamTestExecutionDetailsManager testContextDetailsManager =
        new EkamTestExecutionDetailsManager(webContext);
    testContextDetailsManager.createScreenShotDirectory();
    testContextDetailsManager.saveTargetDetails();

    // Bind mobile modules to base injector
    if (enableMobile) baseInjector = enableMobile(baseInjector, testContextDetailsManager);

    Injector webDriverInjector =
        baseInjector.createChildInjector(
            new EkamTestModule(testContextDetailsManager, ekamTestDetails));

    // Bind API injector to base injector and add to injector cache
    webDriverInjector = createApiInjector(webDriverInjector);
    injectorsCache().put(webDriverInjector);
  }

  public Injector createApiInjector(Injector baseInjector) {
    return baseInjector.createChildInjector(new ApiHostsModule(), new GrpcModule());
  }

  private Injector enableWeb(
      Injector baseInjector, EkamTestExecutionDetailsManager testContextDetailsManager) {
    Injector webInjector = baseInjector.createChildInjector(new WebModule());
    WebDriver webDriver = webInjector.getInstance(WebDriver.class);
    EkamTestContext webContext = buildEkamTestContext(webDriver, buildTargetDetails(webDriver));
    testContextDetailsManager.addWebContext(webContext);
    return webInjector;
  }

  private Injector enableMobile(
      Injector baseInjector, EkamTestExecutionDetailsManager optimusRunTarget) {
    Injector mobileInjector = baseInjector.createChildInjector(new MobileModule());
    MobileDriverDetails mobileDriverDetails = mobileInjector.getInstance(MobileDriverDetails.class);
    EkamTestContext mobileContext =
        buildEkamTestContext(
            mobileDriverDetails.getDriver(), mobileDriverDetails.getTargetDetails());
    optimusRunTarget.addMobileContext(mobileContext);
    return mobileInjector;
  }

  private EkamTestContext buildEkamTestContext(WebDriver webDriver, TargetDetails targetDetails) {
    return EkamTestContext.builder()
        .driver(webDriver)
        .build()
        .addTarget(targetDetails)
        .testPath(ekamTestDetails.getFeature(), ekamTestDetails.getScenario());
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
