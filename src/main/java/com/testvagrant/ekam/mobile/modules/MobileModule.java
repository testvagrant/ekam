package com.testvagrant.ekam.mobile.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import com.testvagrant.ekam.mobile.providers.AppiumDriverProvider;
import com.testvagrant.ekam.mobile.providers.MobileDriverDetailsProvider;
import org.openqa.selenium.WebDriver;

public class MobileModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MobileDriverDetails.class)
                .toProvider(MobileDriverDetailsProvider.class)
                .asEagerSingleton();

        bind(new TypeLiteral<WebDriver>() {
        })
                .toProvider(AppiumDriverProvider.class)
                .asEagerSingleton();
    }
}
