package com.testvagrant.ekam.commons.listeners;

import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.injectors.Injectors;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;
import com.testvagrant.ekam.commons.testContext.EkamTestContextConverter;
import com.testvagrant.optimus.core.mobile.MobileDriverManager;
import com.testvagrant.optimus.core.web.WebDriverManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class EkamMobileWebDriverListener extends BuildListener implements ITestListener {

  public EkamMobileWebDriverListener() {
    super("mobile");
  }

  @Override
  public void onTestStart(ITestResult result) {
    Reporter.log(String.format("Test %s has started", result.getName().toLowerCase()));
    addDivider();
    EkamTestContext ekamTestContext = EkamTestContextConverter.convert(result);
    new InjectorCreator(ekamTestContext).createMobileInjector(true);
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
    quit(result, "failed");
  }

  @Override
  public void onStart(ITestContext context) {}

  @Override
  public void onFinish(ITestContext context) {}

  public void onTestFailedWithTimeout(ITestResult result) {
    quit(result, "failed");
  }

  public void quit(ITestResult result, String status) {
    updateBuild(result, status, Injectors.MOBILE_PAGE_INJECTOR);
    addDivider();
    Reporter.log(String.format("Test %s has ended", result.getName().toLowerCase()));
    MobileDriverManager.dispose();
    WebDriverManager.dispose();
  }
}
