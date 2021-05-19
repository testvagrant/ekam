package com.testvagrant.ekam.commons.testContext;

import org.testng.ITestResult;

public class EkamTestContextConverter {

  // TESTNG Converter
  public static EkamTestContext convert(ITestResult testResult) {
    return EkamTestContext.builder()
        .featureName(testResult.getTestClass().getName())
        .testName(testResult.getName())
        .build();
  }
}
