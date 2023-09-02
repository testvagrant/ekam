package com.testvagrant.ekam.testBases.cucumber;

import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.injectors.EkamMobileInjector;
import com.testvagrant.ekam.mobile.DeviceCacheDisposeFactory;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;

public class MobileScenarioDefinition extends ScenarioDefinition {

    public void setup(Scenario scenario) {
        initLogger(scenario);
        EkamTest ekamTest = buildEkamTest(scenario);
        new EkamMobileInjector(ekamTest, ekamConfig).create();
    }

    public void tearDown() {
        MobileDriverDetails mobileDriverDetails =
                injectorsCache().getInjector().getInstance(MobileDriverDetails.class);
        WebDriver driver = mobileDriverDetails.getDriver();

        if (driver != null) driver.quit();
        if (mobileDriverDetails.getService() != null) mobileDriverDetails.getService().stop();

        DeviceCacheDisposeFactory.dispose(
                Objects.requireNonNull(mobileDriverDetails).getTargetDetails(),
                injectorsCache().getInjector().getInstance(EkamConfig.class).getMobile());
    }
}
