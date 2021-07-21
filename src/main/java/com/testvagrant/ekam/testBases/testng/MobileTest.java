package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.commons.testContext.EkamTestDetails;
import com.testvagrant.ekam.mobile.initializers.EkamMobileTestContext;
import com.testvagrant.ekam.testBases.EkamTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.testvagrant.ekam.commons.testContext.TestNgEkamTestContextBuilder.buildTestContext;

public class MobileTest extends EkamTest {

  public MobileTest() {
    super("mobile");
  }

  public MobileTest(String mobileFeed) {
    this();
    System.setProperty("mobileFeed", mobileFeed);
  }

  /**
   * Executes before every test. Creates Mobile injector binding AppiumDriver and Switch view
   * Creates screenshot directory and target.json for the current test being executed
   */
  @BeforeMethod(alwaysRun = true)
  public void ekamMobileSetup(ITestResult iTestResult) {
    EkamTestDetails testContext = buildTestContext(iTestResult);
    new EkamMobileTestContext(testContext).init();
  }

  /**
   * Executes everytime after completion of a test. Updates dashboard build with Test details
   * Performs Appium Teardown
   */
  @AfterMethod(alwaysRun = true)
  public void ekamMobileTearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
    new EkamMobileTestContext(buildTestContext(iTestResult)).dispose();
  }
}
