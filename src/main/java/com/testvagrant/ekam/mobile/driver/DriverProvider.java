package com.testvagrant.ekam.mobile.driver;

import com.google.inject.Provider;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.logs.AppiumLogRecorder;
import com.testvagrant.optimus.core.appium.LocalDriverManager;
import com.testvagrant.optimus.core.models.CloudConfig;
import com.testvagrant.optimus.core.models.mobile.MobileDriverDetails;
import com.testvagrant.optimus.core.parser.TestFeedParser;
import com.testvagrant.optimus.core.remote.CloudConfigBuilder;
import com.testvagrant.optimus.core.remote.RemoteDriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Timer;

public class DriverProvider implements Provider<MobileDriverDetails> {

    protected ThreadLocal<MobileDriverDetails> mobileDriverDetailsThreadLocal = new ThreadLocal<>();

    public MobileDriverDetails setupMobileDriver() {
        MobileDriverDetails mobileDriverDetails;
        switch (SystemProperties.TARGET) {
            case REMOTE:
                mobileDriverDetails = createRemoteDriver();
                break;
            case OPTIMUS:
                throw new UnsupportedOperationException("Support for optimus coming soon!! Please use REMOTE / LOCAL as targets");
            default:
                mobileDriverDetails = new LocalDriverManager().createDriver();
                break;
        }
        mobileDriverDetailsThreadLocal.set(mobileDriverDetails);
        return mobileDriverDetailsThreadLocal.get();
    }

    private MobileDriverDetails createRemoteDriver() {
        TestFeedParser testFeedParser = new TestFeedParser(SystemProperties.TEST_FEED);
        DesiredCapabilities desiredCapabilities = testFeedParser.getDesiredCapabilities();
        CloudConfig build = new CloudConfigBuilder().build();
        AppiumDriver<MobileElement> mobileDriver = (AppiumDriver<MobileElement>) new RemoteDriverManager().createDriver(build, desiredCapabilities);
        return buildMobileDriverDetails(mobileDriver, desiredCapabilities);
    }

    private MobileDriverDetails buildMobileDriverDetails(AppiumDriver<MobileElement> mobileDriver, DesiredCapabilities desiredCapabilities) {
        MobileDriverDetails mobileDriverDetails = new MobileDriverDetails();
        mobileDriverDetails.setDriver(mobileDriver);
        mobileDriverDetails.setCapabilities(desiredCapabilities);
        return mobileDriverDetails;
    }

    @Override
    public MobileDriverDetails get() {
        return setupMobileDriver();
    }

    private void captureLogs() {
        if (Toggles.LOGS.isOff()) {
            System.out.println("Logs are turned off");
            return;
        }
        Timer timer = new Timer();
        timer.schedule(
                new AppiumLogRecorder(mobileDriverDetailsThreadLocal.get().getDriver()), 0, 3000);
    }
}
