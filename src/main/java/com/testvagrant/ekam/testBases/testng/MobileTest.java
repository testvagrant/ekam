package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.commons.models.EkamTest;
import com.testvagrant.ekam.mobile.initializers.EkamMobileTest;
import com.testvagrant.ekam.testBases.EkamTestBase;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class MobileTest extends EkamTestBase {

  public MobileTest() {
    super("mobile");
  }

  public MobileTest(String mobileFeed) {
    this();
    System.setProperty("mobileFeed", mobileFeed);
  }

  /**
   * Executes before every test. Creates Mobile injector binding AppiumDriver, SwitchViewModule,
   * StepRecorderModule and APIModule
   *
   * @param iTestResult: TestNg ITestResult
   */
  @BeforeMethod(alwaysRun = true)
  public void ekamMobileSetup(ITestResult iTestResult) {
    EkamTest ekamTest = buildEkamTest(iTestResult);
    new EkamMobileTest(ekamTest).init(false);
  }

  /**
   * Executes everytime after completion of a test. Updates dashboard build with Test details
   * Performs Appium Teardown
   *
   * @param iTestResult: TestNg ITestResult
   */
  @AfterMethod(alwaysRun = true)
  public void ekamMobileTearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
    new EkamMobileTest(buildEkamTest(iTestResult)).dispose();
  }
}
