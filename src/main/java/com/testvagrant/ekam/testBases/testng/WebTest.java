package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.commons.testContext.EkamTestDetails;
import com.testvagrant.ekam.testBases.EkamTest;
import com.testvagrant.ekam.web.initializer.EkamWebTestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.testvagrant.ekam.commons.testContext.TestNgEkamTestContextBuilder.buildTestContext;

public class WebTest extends EkamTest {

  public WebTest() {
    super("web");
  }

  public WebTest(String webFeed) {
    this();
    System.setProperty("webFeed", webFeed);
  }

  /**
   * Executes before every test. Creates Web injector binding WebDriver and SwitchView
   * Creates screenshot directory and target.json for the current test being executed
   */
  @BeforeMethod(alwaysRun = true)
  public void ekamWebSetup(ITestResult iTestResult) {
    EkamTestDetails ekamTestDetails = buildTestContext(iTestResult);
    new EkamWebTestContext(ekamTestDetails).init();
  }

  /**
   * Executes everytime after completion of a test. Updates dashboard build with Test details
   * Performs WebDriver Teardown
   */
  @AfterMethod(alwaysRun = true)
  public void ekamWebTearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
    new EkamWebTestContext(buildTestContext(iTestResult)).dispose();
  }
}
