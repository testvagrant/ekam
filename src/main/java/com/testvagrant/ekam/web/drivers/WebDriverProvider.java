package com.testvagrant.ekam.web.drivers;

import com.google.inject.Provider;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Target;
import com.testvagrant.optimus.core.models.CloudConfig;
import com.testvagrant.optimus.core.parser.WebTestFeedParser;
import com.testvagrant.optimus.core.remote.CloudConfigBuilder;
import com.testvagrant.optimus.core.remote.RemoteDriverManager;
import com.testvagrant.optimus.core.web.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverProvider implements Provider<WebDriver> {

    @Override
    public WebDriver get() {
        return createDriver();
    }

    private WebDriver createDriver() {
        return SystemProperties.TARGET == Target.REMOTE ?
                createRemoteDriver() :
                new WebDriverManager().createDriver();
    }

    private WebDriver createRemoteDriver() {
        WebTestFeedParser testFeedParser = new WebTestFeedParser(SystemProperties.TEST_FEED);
        DesiredCapabilities desiredCapabilities = testFeedParser.getDesiredCapabilities();
        CloudConfig build = new CloudConfigBuilder().build();
        return new RemoteDriverManager().createDriver(build, desiredCapabilities);
    }
}

