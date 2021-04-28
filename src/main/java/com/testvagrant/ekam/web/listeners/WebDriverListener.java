package com.testvagrant.ekam.web.listeners;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.PageInitiator;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.internal.Launcher;
import com.testvagrant.ekam.web.modules.OptimusRunTargetModule;
import com.testvagrant.optimus.core.remote.RemoteDriverManager;
import com.testvagrant.optimus.core.web.WebDriverManager;
import com.testvagrant.optimus.dashboard.OptimusTestNGBuildGenerator;
import com.testvagrant.optimus.dashboard.models.dashboard.Target;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class WebDriverListener implements ITestListener {

  @Override
  public void onTestStart(ITestResult result) {
    Reporter.log(String.format("Test %s has started", result.getName().toLowerCase()));
    addDivider();
    Injector driverInjector =
        result
            .getTestContext()
            .getSuite()
            .getParentInjector()
            .createChildInjector(new ModulesLibrary().webModules());
    WebDriver driver = driverInjector.getInstance(WebDriver.class);
    Injector childInjector = driverInjector.createChildInjector(new OptimusRunTargetModule(driver, result));
    result.setAttribute(Injectors.DRIVER_INJECTOR.getInjector(), driver);
    result.setAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector(), childInjector);
    childInjector.getInstance(Launcher.class).launch();
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    quit(result, "passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    PageInitiator.getInstance().captureScreenshot();
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
    OptimusTestNGBuildGenerator buildGenerator = (OptimusTestNGBuildGenerator) result.getTestContext().getSuite().getAttribute("buildGenerator");
    buildGenerator.addTestCase(result, status, Target.builder().build());
    if (SystemProperties.TARGET == com.testvagrant.ekam.commons.Target.REMOTE) {
      RemoteDriverManager.dispose();
    } else {
      WebDriverManager.dispose();
    }
    addDivider();
    Reporter.log(String.format("Test %s has ended", result.getName().toLowerCase()), true);
  }

  private void addDivider() {
    Reporter.log("==================================================", true);
  }
}
