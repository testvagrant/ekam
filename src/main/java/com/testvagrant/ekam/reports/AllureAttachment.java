package com.testvagrant.ekam.reports;

import io.qameta.allure.Allure;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureAttachment {

    public void attachScreenshot(String name, Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            Allure.addAttachment(name, is);
        } catch (IOException e) {
            throw new RuntimeException(ExceptionUtils.getStackTrace(e));
        }
    }
}
