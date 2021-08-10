package com.testvagrant.ekam.testBases.cucumber;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import io.cucumber.java.Scenario;
import org.apache.commons.lang3.StringUtils;

public abstract class ScenarioDefinition {

  protected EkamConfig ekamConfig;

  public ScenarioDefinition() {
    Injector injector = Guice.createInjector(new EkamConfigModule());
    ekamConfig = injector.getInstance(EkamConfig.class);
  }

  protected EkamTest buildEkamTest(Scenario scenario) {
    return EkamTest.builder()
        .scenario(scenario.getName())
        .feature(StringUtils.capitalize(scenario.getName().replaceAll("\\s", "")))
        .build();
  }
}
