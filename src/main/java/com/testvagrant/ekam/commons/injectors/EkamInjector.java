package com.testvagrant.ekam.commons.injectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.models.EkamTest;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.commons.modules.StepRecorderModule;
import com.testvagrant.ekam.commons.path.PathBuilder;
import com.testvagrant.ekam.commons.runcontext.EkamTestContext;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.WebModule;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;
import static com.testvagrant.ekam.commons.io.FileUtilities.fileUtils;

public class EkamInjector {

  private final EkamTest ekamTest;

  public EkamInjector(EkamTest ekamTest) {
    this.ekamTest = ekamTest;
  }

  /** Creates API Injector binding APIHostsModule, StepRecorderModule */
  public void createApiInjector() {
    EkamTestContext testContext = buildEkamTestContext(null);
    createTargetJson(testContext);
    Injector baseInjector = Guice.createInjector(new ModulesLibrary().baseModules());
    injectApiAndStepRecorder(baseInjector, testContext);
  }

  /**
   * Create Mobile injector binding AppiumDriver, WebDriver(if enableWeb), ApiModule,
   * SwitchViewModule, StepRecorderModule
   *
   * @param enableWeb: Whether or not to bind WebInjector
   */
  public void createMobileInjector(boolean enableWeb) {
    Injector baseInjector = Guice.createInjector(new ModulesLibrary().mobileModules());
    MobileDriverDetails mobileDriverDetails = baseInjector.getInstance(MobileDriverDetails.class);
    EkamTestContext mobileContext = buildEkamTestContext(mobileDriverDetails.getTargetDetails());

    if (enableWeb) baseInjector = enableWeb(baseInjector, mobileContext);
    createTargetJson(mobileContext);
    createScreenShotDirectory(mobileContext.getTestFolder());
    injectApiAndStepRecorder(baseInjector, mobileContext);
  }

  /**
   * Create web injector binding WebDriver, AppiumDriver(if enableMobile), ApiModule,
   * SwitchViewModule, StepRecorderModule
   *
   * @param enableMobile: Whether or not to bind Mobile Injector
   */
  public void createWebInjector(boolean enableMobile) {
    Injector baseInjector = Guice.createInjector(new ModulesLibrary().webModules());
    WebDriver webDriver = baseInjector.getInstance(WebDriver.class);
    EkamTestContext webContext = buildEkamTestContext(buildTargetDetails(webDriver));

    if (enableMobile) baseInjector = enableMobile(baseInjector, webContext);
    createTargetJson(webContext);
    createScreenShotDirectory(webContext.getTestFolder());
    injectApiAndStepRecorder(baseInjector, webContext);
  }

  private void injectApiAndStepRecorder(Injector baseInjector, EkamTestContext testContext) {
    Injector childInjector =
        baseInjector.createChildInjector(
            new ApiHostsModule(), new GrpcModule(), new StepRecorderModule(testContext));

    injectorsCache().put(childInjector);
  }

  private Injector enableWeb(Injector baseInjector, EkamTestContext testContext) {
    Injector childInjector = baseInjector.createChildInjector(new WebModule());
    WebDriver webDriver = childInjector.getInstance(WebDriver.class);
    TargetDetails targetDetails = buildTargetDetails(webDriver);
    testContext.addTarget(targetDetails);
    return childInjector;
  }

  private Injector enableMobile(Injector baseInjector, EkamTestContext testContext) {
    Injector childInjector = baseInjector.createChildInjector(new MobileModule());
    MobileDriverDetails mobileDriverDetails = childInjector.getInstance(MobileDriverDetails.class);
    testContext.addTarget(mobileDriverDetails.getTargetDetails());
    return childInjector;
  }

  private EkamTestContext buildEkamTestContext(TargetDetails targetDetails) {
    return EkamTestContext.builder()
        .ekamTest(ekamTest)
        .build()
        .addTarget(targetDetails)
        .testPath(ekamTest.getFeature(), ekamTest.getScenario());
  }

  private TargetDetails buildTargetDetails(WebDriver webDriver) {
    Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();

    return TargetDetails.builder()
        .name(capabilities.getBrowserName())
        .platformVersion(capabilities.getVersion())
        .platform(EkamSupportedPlatforms.valueOf(capabilities.getBrowserName().toUpperCase()))
        .build();
  }

  private void createTargetJson(EkamTestContext testContext) {
    String serialize = new GsonParser().serialize(testContext.getTargets());
    File testFolder = new File(new PathBuilder(testContext.getTestFolder()).toString());

    if (!testFolder.exists()) {
      fileUtils().createDirectory(testFolder.getAbsolutePath());
    }

    String fileName = new PathBuilder(testContext.getTestFolder()).append("target.json").toString();
    fileUtils().writeFile(fileName, serialize);
  }

  private void createScreenShotDirectory(String testFolder) {
    String path = new PathBuilder(testFolder).append("screenshots").toString();
    fileUtils().createDirectory(path);
  }
}
