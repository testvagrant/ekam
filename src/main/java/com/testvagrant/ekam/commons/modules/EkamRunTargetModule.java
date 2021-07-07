package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.runcontext.EkamRunTarget;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;
import com.testvagrant.ekam.dashboard.StepRecorder;

public class EkamRunTargetModule extends AbstractModule {
  private final EkamTestContext ekamTestContext;
  private final EkamRunTarget ekamRunTarget;

  public EkamRunTargetModule(EkamRunTarget ekamRunTarget, EkamTestContext ekamTestContext) {
    this.ekamRunTarget = ekamRunTarget;
    this.ekamTestContext = ekamTestContext;
  }

  @Override
  protected void configure() {
    bind(EkamRunTarget.class).toInstance(ekamRunTarget);
    StepRecorder stepRecorder =
        new StepRecorder(ekamTestContext.getFeatureName(), ekamTestContext.getTestName());
    bind(StepRecorder.class).toInstance(stepRecorder);
  }
}
