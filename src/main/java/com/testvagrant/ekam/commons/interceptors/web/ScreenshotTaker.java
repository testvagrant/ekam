package com.testvagrant.ekam.commons.interceptors.web;

import com.testvagrant.ekam.commons.PageInitiator;
import io.qameta.allure.Attachment;

public class ScreenshotTaker {

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        return PageInitiator.getInstance().captureScreenshot(true);
    }
}
