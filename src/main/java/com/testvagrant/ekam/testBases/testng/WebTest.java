package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.commons.models.EkamTest;
import com.testvagrant.ekam.testBases.EkamTestBase;
import com.testvagrant.ekam.web.initializer.EkamWebTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class WebTest extends EkamTestBase {

  public WebTest() {
    super("web");
  }

  public WebTest(String webFeed) {
    this();
    System.setProperty("webFeed", webFeed);
  }

  /**
   * Executes before every test. Creates Web injector binding WebDriver, SwitchViewModule,
   * StepRecorderModule and APIModule
   *
   * @param iTestResult: TestNg ITestResult
   */
  @BeforeMethod(alwaysRun = true)
  public void ekamWebSetup(ITestResult iTestResult) {
    EkamTest ekamTest = buildEkamTest(iTestResult);
    new EkamWebTest(ekamTest).init(false);
  }

  /**
   * Executes everytime after completion of a test. Updates dashboard build with Test details
   * Performs WebDriver Teardown
   *
   * @param iTestResult: TestNg ITestResult
   */
  @AfterMethod(alwaysRun = true)
  public void ekamWebTearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
    new EkamWebTest(buildEkamTest(iTestResult)).dispose();
  }
}
