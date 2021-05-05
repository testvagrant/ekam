package com.testvagrant.ekam.commons.interceptors.web;

import com.testvagrant.ekam.commons.PageInitiator;
import com.testvagrant.ekam.reports.AllureAttachment;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotTaker {

    public void saveScreenshot() {
        PageInitiator.getInstance().captureScreenshot();
    }
}
