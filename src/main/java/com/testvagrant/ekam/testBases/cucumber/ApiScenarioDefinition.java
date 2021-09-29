package com.testvagrant.ekam.testBases.cucumber;

import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.injectors.EkamAPIInjector;
import io.cucumber.java.Scenario;

public class ApiScenarioDefinition extends ScenarioDefinition {

  public void setup(Scenario scenario) {
    initLogger(scenario);
    EkamTest ekamTest = buildEkamTest(scenario);
    new EkamAPIInjector(ekamTest, ekamConfig).create();
  }
}
