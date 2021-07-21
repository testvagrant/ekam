package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.commons.factories.DeviceCacheDisposeFactory;
import com.testvagrant.ekam.commons.injectors.EkamInjector;
import com.testvagrant.ekam.commons.models.EkamTest;
import com.testvagrant.ekam.commons.models.mobile.MobileDriverDetails;
import com.testvagrant.ekam.testBases.EkamTestBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Objects;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;

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
    new EkamInjector(ekamTest).createMobileInjector(false);
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
        Objects.requireNonNull(mobileDriverDetails).getTargetDetails(), ekam.getMobile());
  }
}
