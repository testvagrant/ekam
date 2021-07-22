package com.testvagrant.ekam.mobile.driver;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppiumDriverProvider implements Provider<AppiumDriver<MobileElement>> {

  private final MobileDriverDetails mobileDriverDetails;

  @Inject
  public AppiumDriverProvider(MobileDriverDetails mobileDriverDetails) {
    this.mobileDriverDetails = mobileDriverDetails;
  }

  @Override
  public AppiumDriver<MobileElement> get() {
    return mobileDriverDetails.getDriver();
  }
}
