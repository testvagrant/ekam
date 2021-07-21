package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.models.EkamTest;
import com.testvagrant.ekam.commons.runcontext.EkamTestContext;
import com.testvagrant.ekam.commons.runcontext.EkamTestScreenshotTaker;
import com.testvagrant.ekam.dashboard.StepRecorder;

public class StepRecorderModule extends AbstractModule {
  private final EkamTestContext testContext;

  public StepRecorderModule(EkamTestContext testContext) {
    this.testContext = testContext;
  }

  @Override
  protected void configure() {
    EkamTest ekamTest = testContext.getEkamTest();
    StepRecorder stepRecorder = new StepRecorder(ekamTest.getFeature(), ekamTest.getScenario());
    EkamTestScreenshotTaker scenarioTimeline = new EkamTestScreenshotTaker(testContext);

    bind(StepRecorder.class).toInstance(stepRecorder);
    bind(EkamTestScreenshotTaker.class).toInstance(scenarioTimeline);
  }
}
