package com.testvagrant.ekam.testBases.cucumber.web;

import com.google.inject.Injector;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.injectors.EkamInjector;
import com.testvagrant.ekam.testBases.cucumber.ScenarioDefinition;
import io.cucumber.java.Scenario;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;

public class WebScenarioDefinition extends ScenarioDefinition {

  public void setup(Scenario scenario) {
    EkamTest ekamTest =
        EkamTest.builder()
            .scenario(StringUtils.capitalize(scenario.getName().replaceAll("\\s", "")))
            .feature(getFeatureFileNameFromScenarioId(scenario))
            .build();
    new EkamInjector(ekamTest).createWebInjector(false);
  }

  public void tearDown() {
    Injector injector = injectorsCache().getInjector();
    WebDriver webDriver = injector.getInstance(WebDriver.class);
    if (webDriver != null) webDriver.quit();
  }
}
