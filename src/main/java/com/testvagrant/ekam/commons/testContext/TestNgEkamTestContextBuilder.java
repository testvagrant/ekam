package com.testvagrant.ekam.commons.testContext;

import org.testng.ITestResult;

public class TestNgEkamTestContextBuilder {

  /** Builds EkamTestContext by parsing the ITestResult */
  public static EkamTestDetails buildTestContext(ITestResult testResult) {
    return EkamTestDetails.builder()
        .feature(testResult.getMethod().getTestClass().getName())
        .scenario(testResult.getMethod().getMethodName())
        .build();
  }
}
