package com.testvagrant.ekam.internal.injectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.commons.path.PathBuilder;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTestContext;
import com.testvagrant.ekam.internal.logger.EkamLogger;
import com.testvagrant.ekam.internal.modules.EkamTestModule;
import com.testvagrant.ekam.internal.modules.ModulesLibrary;
import com.testvagrant.ekam.internal.modules.StepRecorderModule;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.WebModule;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.testvagrant.ekam.commons.io.FileUtilities.fileUtils;
import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;

public class EkamInjector {

  private final EkamTest ekamTest;
  private final EkamTestContext testContext;

  public EkamInjector(EkamTest ekamTest) {
    this.ekamTest = ekamTest;
    testContext = initTestDirectory();
  }

  /** Creates API Injector binding APIHostsModule, StepRecorderModule */
  public void createApiInjector() {
    createInjector(new ModulesLibrary().baseModules());
    createTargetJson();
  }

  /**
   * Create Mobile injector binding AppiumDriver, WebDriver(if enableWeb), ApiModule,
   * SwitchViewModule, StepRecorderModule
   *
   * @param enableWeb - Enable web injector if true
   */
  public void createMobileInjector(boolean enableWeb) {
    Injector mobileInjector = createInjector(new ModulesLibrary().mobileModules());
    MobileDriverDetails mobileDriverDetails = mobileInjector.getInstance(MobileDriverDetails.class);
    testContext.addTarget(mobileDriverDetails.getTargetDetails());

    if (enableWeb) enableWeb(mobileInjector);
    createTargetJson();
  }

  /**
   * Create web injector binding WebDriver, AppiumDriver(if enableMobile), ApiModule,
   * SwitchViewModule, StepRecorderModule
   *
   * @param enableMobile - Enable mobile injector if true
   */
  public void createWebInjector(boolean enableMobile) {
    Injector webInjector = createInjector(new ModulesLibrary().webModules());
    WebDriver webDriver = webInjector.getInstance(WebDriver.class);
    TargetDetails targetDetails = buildTargetDetails(webDriver);
    testContext.addTarget(targetDetails);

    if (enableMobile) enableMobile(webInjector);
    createTargetJson();
  }

  /** Creates injector with all the required modules and the specified modules */
  private Injector createInjector(List<Module> modules) {
    modules.addAll(
        Arrays.asList(new ApiHostsModule(), new GrpcModule(), new StepRecorderModule(testContext)));

    String logFilePath =
        new PathBuilder(testContext.getTestDirectory()).append("logs").append("log.log").toString();
    EkamLogger logger = new EkamLogger(logFilePath);
    logger.init();

    Injector injector = Guice.createInjector(new EkamTestModule(ekamTest));
    injectorsCache().put(injector);
    injector = injector.createChildInjector(modules);
    injectorsCache().put(injector);
    return injector;
  }

  private void enableWeb(Injector baseInjector) {
    Injector childInjector = baseInjector.createChildInjector(new WebModule());
    injectorsCache().put(childInjector);

    WebDriver webDriver = childInjector.getInstance(WebDriver.class);
    TargetDetails targetDetails = buildTargetDetails(webDriver);
    testContext.addTarget(targetDetails);
  }

  private void enableMobile(Injector baseInjector) {
    Injector childInjector = baseInjector.createChildInjector(new MobileModule());
    injectorsCache().put(childInjector);

    MobileDriverDetails mobileDriverDetails = childInjector.getInstance(MobileDriverDetails.class);
    testContext.addTarget(mobileDriverDetails.getTargetDetails());
  }

  private TargetDetails buildTargetDetails(WebDriver webDriver) {
    Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();

    return TargetDetails.builder()
        .name(capabilities.getBrowserName())
        .platformVersion(capabilities.getVersion())
        .platform(EkamSupportedPlatforms.valueOf(capabilities.getBrowserName().toUpperCase()))
        .build();
  }

  private void createTargetJson() {
    String targets = new GsonParser().serialize(testContext.getTargets());
    File testFolder = new File(new PathBuilder(testContext.getTestDirectory()).toString());

    if (!testFolder.exists()) {
      fileUtils().createDirectory(testFolder.getAbsolutePath());
    }

    String fileName =
        new PathBuilder(testContext.getTestDirectory()).append("target.json").toString();
    fileUtils().writeFile(fileName, targets);
  }

  private EkamTestContext initTestDirectory() {
    String testDirectoryPath =
        ResourcePaths.getTestPath(ekamTest.getFeature(), ekamTest.getScenario());

    EkamTestContext context =
        EkamTestContext.builder()
            //
            .ekamTest(ekamTest)
            .testDirectory(testDirectoryPath)
            .build();

    File testDirectory = new File(String.valueOf(new PathBuilder(testDirectoryPath)));
    if (!testDirectory.exists()) {
      fileUtils().createDirectory(testDirectory.getAbsolutePath());
    }

    createLogsDirectory(testDirectoryPath);
    createScreenShotDirectory(testDirectoryPath);

    return context;
  }

  private void createScreenShotDirectory(String testFolder) {
    String path = new PathBuilder(testFolder).append("screenshots").toString();
    fileUtils().createDirectory(path);
  }

  private void createLogsDirectory(String testFolder) {
    String path = new PathBuilder(testFolder).append("logs").toString();
    fileUtils().createDirectory(path);
  }
}
