package com.testvagrant.ekam.commons.logs;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.logging.LogEntries;

import java.util.TimerTask;
import java.util.stream.StreamSupport;

public class AppiumLogRecorder extends TimerTask {

    private MobileDriver mobileDriver;

    public AppiumLogRecorder(MobileDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
    }

    @Override
    public void run() {
        LogEntries logs = mobileDriver.manage().logs().get("server");
        StreamSupport.stream(logs.spliterator(), false).forEach(System.out::println);
    }
}
