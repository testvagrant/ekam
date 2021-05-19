package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import com.testvagrant.optimus.dashboard.StepRecorder;

public class OptimusRunTargetModule extends AbstractModule {
  private final EkamTestContext ekamTestContext;
  private final OptimusRunTarget optimusRunTarget;

  public OptimusRunTargetModule(
      OptimusRunTarget optimusRunTarget, EkamTestContext ekamTestContext) {
    this.optimusRunTarget = optimusRunTarget;
    this.ekamTestContext = ekamTestContext;
  }

  @Override
  protected void configure() {
    bind(OptimusRunTarget.class).toInstance(optimusRunTarget);
    StepRecorder stepRecorder =
        new StepRecorder(ekamTestContext.getFeatureName(), ekamTestContext.getTestName());
    bind(StepRecorder.class).toInstance(stepRecorder);
  }
}
