package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.injectors.EkamAPIInjector;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

/** TestBase for Ekam API Tests */
public class APITest extends TestNgTest {

  public APITest() {
    super("api");
  }

  /**
   * Executes before every test. Creates APIInjector by binding APIHosts and GRPC module
   *
   * @param iTestResult: TestNg ITestResult
   */
  @BeforeMethod(alwaysRun = true)
  public void initTest(ITestResult iTestResult) {
    initLogger(iTestResult);
    EkamTest ekamTest = buildEkamTest(iTestResult);
    new EkamAPIInjector(ekamTest, ekamConfig).create();
  }

  /**
   * Executes everytime after completion of a test. Updates dashboard build with Test details
   *
   * @param iTestResult:TestNg ITestResult
   */
  @AfterMethod(alwaysRun = true)
  public void ekamAPITearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
  }
}
