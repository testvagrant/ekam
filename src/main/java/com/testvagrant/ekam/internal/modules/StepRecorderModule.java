package com.testvagrant.ekam.internal.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.dashboard.StepRecorder;
import com.testvagrant.ekam.internal.executiontimeline.EkamTestScreenshotTaker;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTestContext;

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

    bind(EkamTestContext.class).toInstance(testContext);
    bind(StepRecorder.class).toInstance(stepRecorder);
    bind(EkamTestScreenshotTaker.class).toInstance(scenarioTimeline);
  }
}
