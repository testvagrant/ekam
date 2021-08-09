package com.testvagrant.ekam.testBases.cucumber.api;

import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.injectors.EkamAPIInjector;
import com.testvagrant.ekam.testBases.cucumber.ScenarioDefinition;
import io.cucumber.java.Scenario;

public class ApiScenarioDefinition extends ScenarioDefinition {

  public void setup(Scenario scenario) {
    EkamTest ekamTest = buildEkamTest(scenario);
    new EkamAPIInjector(ekamTest).create();
  }
}
