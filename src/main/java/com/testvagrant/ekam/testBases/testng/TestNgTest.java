package com.testvagrant.ekam.testBases.testng;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.StepRecorder;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.modules.EkamConfigModule;
import com.testvagrant.ekam.reports.dashboard.DashboardTestNgBuildManager;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;
import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

/** TestBase for all Ekam Testng tests */
@Guice(modules = {EkamConfigModule.class})
public class TestNgTest {

  private final String target;
  @Inject protected EkamConfig ekamConfig;
  @Inject private DashboardTestNgBuildManager dashboardTestNgBuildManager;
  public TestNgTest(String target) {
    this.target = target;
  }

  /** Start Dashboard build if dashboard url specified in config */
  @BeforeSuite(alwaysRun = true)
  public void onStart() {
    ekamLogger().info("Setting up Ekam build...");
    if (ekamConfig.getDashboardConfig().publishToDashboard()) {
      dashboardTestNgBuildManager.start(target);
    }
  }

  /** Finalize current build for dashboard if dashboard url specified in config */
  @AfterSuite(alwaysRun = true)
  public void onFinish() {
    if (ekamConfig.getDashboardConfig().publishToDashboard()) {
      String dashboardUrl = ekamConfig.getDashboardConfig().getDashboardUrl();
      dashboardTestNgBuildManager.finish(dashboardUrl);
    }
    ekamLogger().info("Completed Ekam build");
  }

  /**
   * Updates dashboard build details with Feature, Scenario and Step details
   *
   * @param result: Testng ITestResult
   */
  protected void updateBuild(ITestResult result) {
    ekamLogger().info("Completed test {}", result.getMethod().getMethodName());
    if (ekamConfig.getDashboardConfig().publishToDashboard()) {
      EkamTestNGBuildGenerator buildGenerator =
          (EkamTestNGBuildGenerator)
              dataStoreProvider().get("buildGenerator").orElse(new EkamTestNGBuildGenerator());
      ekamLogger().info("Adding testcase {} to ekam build", result.getMethod().getMethodName());
      buildGenerator.addTestCase(result, getTestStatus(result));
    }
    Injector injector = injectorsCache().getInjector();
    injector.getInstance(StepRecorder.class).generateSteps();
  }

  private String getTestStatus(ITestResult result) {
    switch (result.getStatus()) {
      case 1:
        ekamLogger().info("Test {} has passed", result.getMethod().getMethodName());
        return "passed";
      case 2:
        ekamLogger().info("Test {} has failed", result.getMethod().getMethodName());
        return "failed";
      default:
        ekamLogger().info("Test {} has skipped", result.getMethod().getMethodName());
        return "skipped";
    }
  }

  protected EkamTest buildEkamTest(ITestResult testResult) {
    EkamTest ekamTest = EkamTest.builder()
            .feature(testResult.getMethod().getTestClass().getName())
            .scenario(testResult.getMethod().getMethodName())
            .build();
    ekamLogger().info("Building Ekam Test {}", ekamTest);
    return ekamTest;
  }

  protected void initLogger(ITestResult iTestResult) {
    MDC.put("logFileName", iTestResult.getMethod().getMethodName());
    ekamLogger().info("Running test {}", iTestResult.getMethod().getMethodName());
  }
}
