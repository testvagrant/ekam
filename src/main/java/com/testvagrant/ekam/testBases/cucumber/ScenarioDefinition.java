package com.testvagrant.ekam.testBases.cucumber;

import io.cucumber.java.Scenario;

public class ScenarioDefinition {

    protected String getFeatureFileNameFromScenarioId(Scenario scenario) {
        return scenario.getId().trim().replace(" ",".").replace("/",".").replace(":",".");
    }
}
