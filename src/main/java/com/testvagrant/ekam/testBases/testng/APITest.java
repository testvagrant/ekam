package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.commons.injectors.EkamInjector;
import com.testvagrant.ekam.commons.models.EkamTest;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.testBases.EkamTestBase;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;

@Guice(modules = {EkamConfigModule.class, ApiHostsModule.class})
public class APITest extends EkamTestBase {

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
    EkamTest ekamTest = buildEkamTest(iTestResult);
    new EkamInjector(ekamTest).createApiInjector();
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
