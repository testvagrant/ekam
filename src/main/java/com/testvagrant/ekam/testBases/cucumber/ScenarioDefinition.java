package com.testvagrant.ekam.testBases.cucumber;

import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import io.cucumber.java.Scenario;
import org.apache.commons.lang3.StringUtils;

public class ScenarioDefinition {

  protected EkamTest buildEkamTest(Scenario scenario) {
    return EkamTest.builder()
        .scenario(scenario.getName())
        .feature(StringUtils.capitalize(scenario.getName().replaceAll("\\s", "")))
        .build();
  }
}
