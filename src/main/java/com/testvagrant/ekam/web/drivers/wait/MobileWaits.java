package com.testvagrant.ekam.web.drivers.wait;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.annotations.WaitDuration;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MobileWaits implements Provider<WebDriverWait> {
    private WebDriverWait webDriverWait;


    @Inject
    public MobileWaits(MobileDriverDetails mobileDriverDetails, @WaitDuration String waitDuration) {
        webDriverWait = new WebDriverWait(mobileDriverDetails.getMobileDriver(),Long.parseLong(waitDuration));
    }

    @Override
    public WebDriverWait get() {
        return webDriverWait;
    }
}
