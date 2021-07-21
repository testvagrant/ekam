package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.runcontext.EkamTestExecutionDetailsManager;
import com.testvagrant.ekam.commons.testContext.EkamTestDetails;
import com.testvagrant.ekam.dashboard.StepRecorder;

public class EkamTestModule extends AbstractModule {
  private final EkamTestDetails ekamTestDetails;
  private final EkamTestExecutionDetailsManager ekamTestExecutionDetailsManager;

  public EkamTestModule(EkamTestExecutionDetailsManager ekamTestExecutionDetailsManager, EkamTestDetails ekamTestDetails) {
    this.ekamTestExecutionDetailsManager = ekamTestExecutionDetailsManager;
    this.ekamTestDetails = ekamTestDetails;
  }

  @Override
  protected void configure() {
    bind(EkamTestExecutionDetailsManager.class).toInstance(ekamTestExecutionDetailsManager);
    StepRecorder stepRecorder =
        new StepRecorder(ekamTestDetails.getFeature(), ekamTestDetails.getScenario());
    bind(StepRecorder.class).toInstance(stepRecorder);
  }
}
