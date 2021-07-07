package com.testvagrant.ekam.testBases.testng;

import com.testvagrant.ekam.api.initializers.APIContextInitializer;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.commons.testContext.EkamTestContextConverter;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.testBases.EkamTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;

@Guice(modules = {EkamConfigModule.class, ApiHostsModule.class})
public class APITest extends EkamTest {

  public APITest() {
    super("api");
  }

  @BeforeMethod(alwaysRun = true)
  public void ekamAPISetup(ITestResult iTestResult) {
    new APIContextInitializer(EkamTestContextConverter.convert(iTestResult)).init();
  }

  @AfterMethod(alwaysRun = true)
  public void ekamAPITearDown(ITestResult iTestResult) {
    updateBuild(iTestResult);
  }
}
