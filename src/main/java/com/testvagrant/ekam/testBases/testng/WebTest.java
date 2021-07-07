package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.commons.testContext.EkamTestContextConverter;
import com.testvagrant.ekam.testBases.EkamTest;
import com.testvagrant.ekam.web.initializer.WebContextInitializer;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class WebTest extends EkamTest {

  public WebTest() {
    super("web");
  }

  public WebTest(String webFeed) {
    this();
    System.setProperty("webFeed", webFeed);
  }

  @BeforeMethod(alwaysRun = true)
  public void ekamWebSetup(ITestResult iTestResult) {
    WebContextInitializer webContextInitializer =
        new WebContextInitializer(EkamTestContextConverter.convert(iTestResult));
    webContextInitializer.init();
  }

  @AfterMethod(alwaysRun = true)
  public void ekamWebTearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
    new WebContextInitializer(EkamTestContextConverter.convert(iTestResult)).dispose();
  }
}
