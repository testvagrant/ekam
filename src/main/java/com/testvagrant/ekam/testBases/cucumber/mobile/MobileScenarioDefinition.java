package com.testvagrant.ekam.testBases.cucumber.mobile;

import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.injectors.EkamInjector;
import com.testvagrant.ekam.mobile.DeviceCacheDisposeFactory;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import com.testvagrant.ekam.testBases.cucumber.ScenarioDefinition;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.Scenario;

import java.util.Objects;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;

public class MobileScenarioDefinition extends ScenarioDefinition {

  public void setup(Scenario scenario) {
    EkamTest ekamTest = buildEkamTest(scenario);
    new EkamInjector(ekamTest).createMobileInjector(false);
  }

  public void tearDown() {
    MobileDriverDetails mobileDriverDetails =
        injectorsCache().getInjector().getInstance(MobileDriverDetails.class);
    AppiumDriver<MobileElement> driver = mobileDriverDetails.getDriver();

    if (driver != null) driver.quit();
    if (mobileDriverDetails.getService() != null) mobileDriverDetails.getService().stop();

    DeviceCacheDisposeFactory.dispose(
        Objects.requireNonNull(mobileDriverDetails).getTargetDetails(),
        injectorsCache().getInjector().getInstance(EkamConfig.class).getMobile());
  }
}
