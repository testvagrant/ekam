package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.injectors.EkamMobileInjector;
import com.testvagrant.ekam.mobile.DeviceCacheDisposeFactory;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Objects;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

/** TestBase for Ekam Mobile Tests */
public class MobileTest extends TestNgTest {

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
    initLogger(iTestResult);
    EkamTest ekamTest = buildEkamTest(iTestResult);
    new EkamMobileInjector(ekamTest, ekamConfig).create();
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
    dispose();
  }

  private void dispose() {
    MobileDriverDetails mobileDriverDetails =
        injectorsCache().getInjector().getInstance(MobileDriverDetails.class);
    AppiumDriver<MobileElement> driver = mobileDriverDetails.getDriver();

    if (driver != null) driver.quit();
    if (mobileDriverDetails.getService() != null) mobileDriverDetails.getService().stop();

    DeviceCacheDisposeFactory.dispose(
        Objects.requireNonNull(mobileDriverDetails).getTargetDetails(), ekamConfig.getMobile());
  }
}
