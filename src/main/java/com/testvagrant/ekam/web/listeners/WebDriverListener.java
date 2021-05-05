package com.testvagrant.ekam.web.listeners;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.listeners.DriverListener;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.ekam.commons.modules.OptimusRunTargetModule;
import com.testvagrant.optimus.core.models.web.WebDriverDetails;
import com.testvagrant.optimus.core.web.WebDriverManager;
import com.testvagrant.optimus.dashboard.OptimusTestNGBuildGenerator;
import com.testvagrant.optimus.dashboard.StepRecorder;
import org.testng.*;

public class WebDriverListener extends DriverListener implements ITestListener {

  @Override
  public void onTestStart(ITestResult result) {
    Reporter.log(String.format("Test %s has started", result.getName().toLowerCase()));
    addDivider();

    Injector parentInjector = result.getTestContext().getSuite().getParentInjector();
    Injector driverInjector = parentInjector.createChildInjector(new ModulesLibrary().webModules());

    WebDriverDetails driverDetails = driverInjector.getInstance(WebDriverDetails.class);
    Injector childInjector =
        driverInjector.createChildInjector(
            new OptimusRunTargetModule(driverDetails.getDriver(), result, driverDetails.getTargetDetails()));
    result.setAttribute(Injectors.DRIVER_INJECTOR.getInjector(), childInjector);
    result.setAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector(), childInjector);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    quit(result, "passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    quit(result, "failed");
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    quit(result, "skipped");
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    quit(result, "passed");
  }

  @Override
  public void onStart(ITestContext context) {}

  @Override
  public void onFinish(ITestContext context) {}

  public void quit(ITestResult result, String status) {
    OptimusTestNGBuildGenerator buildGenerator =
        (OptimusTestNGBuildGenerator)
            result.getTestContext().getSuite().getAttribute("buildGenerator");
    buildGenerator.addTestCase(result, status);
    Injector runInjector = (Injector) result.getAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector());
    runInjector.getInstance(StepRecorder.class).generateSteps();
    WebDriverManager.dispose();
    addDivider();
    Reporter.log(String.format("Test %s has ended", result.getName().toLowerCase()), true);
  }

  private void addDivider() {
    Reporter.log("==================================================", true);
  }

}
