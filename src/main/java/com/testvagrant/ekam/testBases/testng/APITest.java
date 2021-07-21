package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.api.initializers.EkamAPITestContext;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.commons.testContext.EkamTestDetails;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.testBases.EkamTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;

import static com.testvagrant.ekam.commons.testContext.TestNgEkamTestContextBuilder.buildTestContext;

@Guice(modules = {EkamConfigModule.class, ApiHostsModule.class})
public class APITest extends EkamTest {

  public APITest() {
    super("api");
  }

  /** Executes before every test. Creates APIInjector by binding APIHosts and GRPC module */
  @BeforeMethod(alwaysRun = true)
  public void initTest(ITestResult iTestResult) {
    EkamTestDetails testContext = buildTestContext(iTestResult);
    new EkamAPITestContext(testContext).init();
  }

  /** Executes everytime after completion of a test. Updates dashboard build with Test details */
  @AfterMethod(alwaysRun = true)
  public void ekamAPITearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
  }
}
