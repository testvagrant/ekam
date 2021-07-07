package com.testvagrant.ekam.testBases;

import com.google.inject.Inject;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.initializers.TestNgBuildInitializer;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.StepRecorder;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;

@Guice(modules = {EkamConfigModule.class})
public class EkamTest {

  private final String target;
  @Inject TestNgBuildInitializer testNgBuildInitializer;

  @Inject private EkamConfig ekam;

  public EkamTest(String target) {
    this.target = target;
  }

  @BeforeSuite(alwaysRun = true)
  public void onStart() {
    testNgBuildInitializer.start(target, ekam.getDashboardConfig().publishToDashboard());
  }

  @AfterSuite(alwaysRun = true)
  public void onFinish() {
    testNgBuildInitializer.finish(ekam.getDashboardConfig());
  }

  protected void updateBuild(ITestResult result) {
    if (!ekam.getDashboardConfig().publishToDashboard()) return;
    EkamTestNGBuildGenerator buildGenerator = getBuildGenerator();
    buildGenerator.addTestCase(result, getTestStatus(result));
    StepRecorder stepRecorder =
        InjectorsCacheProvider.getInstance().get().getInstance(StepRecorder.class);
    stepRecorder.generateSteps();
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

  protected void addDivider() {
    Reporter.log("==================================================");
  }

  private EkamTestNGBuildGenerator getBuildGenerator() {
    return (EkamTestNGBuildGenerator)
        dataStoreProvider().get("buildGenerator").orElse(new EkamTestNGBuildGenerator());
  }
}
