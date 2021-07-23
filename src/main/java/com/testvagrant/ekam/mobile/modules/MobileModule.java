package com.testvagrant.ekam.mobile.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import com.testvagrant.ekam.mobile.providers.AppiumDriverProvider;
import com.testvagrant.ekam.mobile.providers.MobileDriverDetailsProvider;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MobileModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MobileDriverDetails.class)
        .toProvider(MobileDriverDetailsProvider.class)
        .asEagerSingleton();

    bind(new TypeLiteral<AppiumDriver<MobileElement>>() {})
        .toProvider(AppiumDriverProvider.class)
        .asEagerSingleton();
  }
}
