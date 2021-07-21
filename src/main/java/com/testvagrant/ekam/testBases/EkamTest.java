package com.testvagrant.ekam.testBases;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.testvagrant.ekam.commons.initializers.DashboardTestNgBuildManager;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.StepRecorder;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;
import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;
import static java.util.Objects.requireNonNull;

@Guice(modules = {EkamConfigModule.class})
public class EkamTest {

  @Inject DashboardTestNgBuildManager dashboardTestNgBuildManager;
  @Inject private EkamConfig ekam;

  private final String target;
  private final boolean publishToDashboard;

  public EkamTest(String target) {
    this.target = target;
    publishToDashboard = requireNonNull(ekam).getDashboardConfig().publishToDashboard();
  }

  /** Start Dashboard build if dashboard url specified in config */
  @BeforeSuite(alwaysRun = true)
  public void onStart() {
    if (publishToDashboard) {
      dashboardTestNgBuildManager.start(target);
    }
  }

  /** Finalize current build for dashboard if dashboard url specified in config */
  @AfterSuite(alwaysRun = true)
  public void onFinish() {
    if (publishToDashboard) {
      String dashboardUrl = ekam.getDashboardConfig().getDashboardUrl();
      dashboardTestNgBuildManager.finish(dashboardUrl);
    }
  }

  /** Updates dashboard build details with Feature, Scenario and Step details */
  protected void updateBuild(ITestResult result) {
    if (publishToDashboard) {
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
}
