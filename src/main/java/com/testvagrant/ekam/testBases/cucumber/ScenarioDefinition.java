package com.testvagrant.ekam.testBases.cucumber;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.modules.EkamConfigModule;
import io.cucumber.java.Scenario;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.testng.ITestResult;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

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

  protected void initLogger(Scenario scenario) {
    MDC.put("logFileName", scenario.getId());
    ekamLogger().info("Running test {}", scenario.getName());
  }
}
