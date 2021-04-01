package com.testvagrant.ekam.commons.interceptors;

import com.google.inject.Inject;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class ScreenshotTaker {

    @Inject(optional = true)
    WebDriver webDriver;

    @Inject(optional = true)
    MobileDriverDetails mobileDriverDetails;


    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    private WebDriver getWebDriver() {
        WebDriver webDriver;
        if(Objects.isNull(this.webDriver)) {
            webDriver = this.mobileDriverDetails.getMobileDriver();
        } else {
            webDriver = this.webDriver;
        }
        return webDriver;
    }
}
