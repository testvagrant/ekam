package com.testvagrant.ekam.testBases.testng;

import com.google.inject.Injector;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTestContext;
import com.testvagrant.ekam.internal.injectors.EkamWebInjector;
import com.testvagrant.ekam.web.ErrorCollector;
import org.openqa.selenium.WebDriver;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

/** TestBase for Ekam Web tests */
public class WebTest extends TestNgTest {

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
    initLogger(iTestResult);
    EkamTest ekamTest = buildEkamTest(iTestResult);
    new EkamWebInjector(ekamTest, ekamConfig).create();
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
    dispose();
  }

  private void dispose() {
    Injector injector = injectorsCache().getInjector();
    WebDriver webDriver = injector.getInstance(WebDriver.class);
    EkamTestContext context = injector.getInstance(EkamTestContext.class);
    if (ekamConfig.getWeb().isLogConsoleErrors())
      new ErrorCollector()
          .logConsoleMessages(context.getTestDirectory(), ekamConfig.getLogConfig());
    if (webDriver != null) webDriver.quit();
  }
}
