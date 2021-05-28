package com.testvagrant.ekam.commons.listeners;

import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.cache.TestContextCacheProvider;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.ekam.commons.injectors.Injectors;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.ekam.commons.testContext.EkamTestContextConverter;
import com.testvagrant.optimus.dashboard.OptimusTestNGBuildGenerator;
import com.testvagrant.optimus.dashboard.StepRecorder;
import com.testvagrant.optimus.dashboard.models.dashboard.BuildOptions;
import com.testvagrant.optimus.dashboard.publishers.OptimusReportPublisher;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import java.util.List;

@SuppressWarnings("unchecked")
public class BuildListener implements ISuiteListener {

  private String target;

  public BuildListener(String target) {
    this.target = target;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void onStart(ISuite suite) {
    if(Toggles.PUBLISH_TO_DASHBOARD.isOff()) return;
    OptimusTestNGBuildGenerator testNGBuildGenerator = new OptimusTestNGBuildGenerator();
    testNGBuildGenerator.startBuild();
    TestContextCacheProvider.getInstance().put("buildGenerator", testNGBuildGenerator);
    TestContextCacheProvider.getInstance().put("target", target);
    String logFolder = new LogWriter().createLogFolder();
    TestContextCacheProvider.getInstance().put(Injectors.LOG_FOLDER.getInjector(), logFolder);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void onFinish(ISuite suite) {
    if(Toggles.PUBLISH_TO_DASHBOARD.isOff()) return;
    OptimusTestNGBuildGenerator testNGBuildGenerator = null;
    try {
      testNGBuildGenerator =
          (OptimusTestNGBuildGenerator)
              TestContextCacheProvider.getInstance().get("buildGenerator");
    } catch (NoSuchKeyException e) {
      testNGBuildGenerator = new OptimusTestNGBuildGenerator();
      testNGBuildGenerator.startBuild();
    }
    testNGBuildGenerator.endBuild();
    testNGBuildGenerator.generate();
    generateReport();
  }

  protected void updateBuild(ITestResult result, String status, Injectors injector) {
    if(Toggles.PUBLISH_TO_DASHBOARD.isOff()) return;
    OptimusTestNGBuildGenerator buildGenerator = getBuildGenerator();
    buildGenerator.addTestCase(result, status);
    StepRecorder stepRecorder =
        InjectorsCacheProvider.getInstance(
            injector.getInjector(EkamTestContextConverter.convert(result).hashCode()),
            StepRecorder.class);
    stepRecorder.generateSteps();
  }

  protected void addDivider() {
    Reporter.log("==================================================");
  }

  @SuppressWarnings("unchecked")
  private OptimusTestNGBuildGenerator getBuildGenerator() {
    try {
      return (OptimusTestNGBuildGenerator)
          TestContextCacheProvider.getInstance().get("buildGenerator");
    } catch (NoSuchKeyException e) {
      return new OptimusTestNGBuildGenerator();
    }
  }

  public void generateReport() {
    if(Toggles.PUBLISH_TO_DASHBOARD.isOff()) return;
    getBuildOptions();
    OptimusReportPublisher optimusReportPublisher =
            new OptimusReportPublisher(SystemProperties.EKAM_SERVER_URL, getBuildOptions());
    optimusReportPublisher.publish();
  }


  private BuildOptions getBuildOptions() {
    String target = "";
    try {
      target = (String) TestContextCacheProvider.getInstance().get("target");
    } catch (NoSuchKeyException e) {
      target = "mobile";
    }
    return new BuildOptions(target);
  }
}
