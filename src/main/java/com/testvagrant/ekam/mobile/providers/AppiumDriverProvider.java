package com.testvagrant.ekam.mobile.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import org.openqa.selenium.WebDriver;

public class AppiumDriverProvider implements Provider<WebDriver> {

    private final MobileDriverDetails mobileDriverDetails;

    @Inject
    public AppiumDriverProvider(MobileDriverDetails mobileDriverDetails) {
        this.mobileDriverDetails = mobileDriverDetails;
    }

    @Override
    public WebDriver get() {
        return mobileDriverDetails.getDriver();
    }
}
