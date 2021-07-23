package com.testvagrant.ekam.testBases.testng;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.StepRecorder;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.reports.dashboard.DashboardTestNgBuildManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;
import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;

/** TestBase for all Ekam Testng tests */
@Guice(modules = {EkamConfigModule.class})
public class TestNgTest {

  private final String target;
  @Inject protected EkamConfig ekam;
  @Inject private DashboardTestNgBuildManager dashboardTestNgBuildManager;

  public TestNgTest(String target) {
    this.target = target;
  }

  /** Start Dashboard build if dashboard url specified in config */
  @BeforeSuite(alwaysRun = true)
  public void onStart() {
    if (ekam.getDashboardConfig().publishToDashboard()) {
      dashboardTestNgBuildManager.start(target);
    }
  }

  /** Finalize current build for dashboard if dashboard url specified in config */
  @AfterSuite(alwaysRun = true)
  public void onFinish() {
    if (ekam.getDashboardConfig().publishToDashboard()) {
      String dashboardUrl = ekam.getDashboardConfig().getDashboardUrl();
      dashboardTestNgBuildManager.finish(dashboardUrl);
    }
  }

  /**
   * Updates dashboard build details with Feature, Scenario and Step details
   *
   * @param result: Testng ITestResult
   */
  protected void updateBuild(ITestResult result) {
    if (ekam.getDashboardConfig().publishToDashboard()) {
      EkamTestNGBuildGenerator buildGenerator =
          (EkamTestNGBuildGenerator)
              dataStoreProvider().get("buildGenerator").orElse(new EkamTestNGBuildGenerator());
      buildGenerator.addTestCase(result, getTestStatus(result));
      Injector injector = injectorsCache().getInjector();
      injector.getInstance(StepRecorder.class).generateSteps();
    }
  }

  private String getTestStatus(ITestResult result) {
    switch (result.getStatus()) {
      case 1:
        return "passed";
      case 2:
        return "failed";
      default:
        return "skipped";
    }
  }

  protected EkamTest buildEkamTest(ITestResult testResult) {
    return EkamTest.builder()
        .feature(testResult.getMethod().getTestClass().getName())
        .scenario(testResult.getMethod().getMethodName())
        .build();
  }
}