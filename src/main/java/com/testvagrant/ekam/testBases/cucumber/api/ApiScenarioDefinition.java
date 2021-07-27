package com.testvagrant.ekam.testBases.cucumber.api;

import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.injectors.EkamInjector;
import com.testvagrant.ekam.testBases.cucumber.ScenarioDefinition;
import io.cucumber.java.Scenario;

public class ApiScenarioDefinition extends ScenarioDefinition {

  public void setup(Scenario scenario) {
    EkamTest ekamTest =
        EkamTest.builder()
            .scenario(scenario.getName())
            .feature(getFeatureFileNameFromScenarioId(scenario))
            .build();
    new EkamInjector(ekamTest).createApiInjector();
  }
}
