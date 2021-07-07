package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.commons.testContext.EkamTestContextConverter;
import com.testvagrant.ekam.mobile.initializers.MobileContextInitializer;
import com.testvagrant.ekam.testBases.EkamTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class MobileTest extends EkamTest {

  public MobileTest() {
    super("mobile");
  }

  public MobileTest(String mobileFeed) {
    this();
    System.setProperty("mobileFeed", mobileFeed);
  }
  //
  //  @BeforeSuite(alwaysRun = true)
  //  public void hooksSetup() {
  //    new EkamExecutionHooks().apply();
  //  }

  @BeforeMethod(alwaysRun = true)
  public void ekamMobileSetup(ITestResult iTestResult) {
    new MobileContextInitializer(EkamTestContextConverter.convert(iTestResult)).init();
  }

  @AfterMethod(alwaysRun = true)
  public void ekamMobileTearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
    new MobileContextInitializer(EkamTestContextConverter.convert(iTestResult)).dispose();
  }
}
