package com.testvagrant.ekam.mobile.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.mobile.driver.DriverProvider;
import com.testvagrant.ekam.mobile.wait.MobileWaits;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MobileModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MobileDriverDetails.class).toProvider(DriverProvider.class).asEagerSingleton();
        bind(WebDriverWait.class).toProvider(MobileWaits.class).asEagerSingleton();
    }
}
