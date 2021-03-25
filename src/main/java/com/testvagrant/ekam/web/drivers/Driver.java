package com.testvagrant.ekam.web.drivers;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import java.util.List;

public interface Driver {

    void setup();

    WebDriver setupDriver();

    void terminateDriver();

    WebDriver getDriver();

    MutableCapabilities browserOptions();

    List<String> headlessArguments();
}
