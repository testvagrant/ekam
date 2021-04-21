package com.testvagrant.ekam.mobile.listeners;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Target;
import com.testvagrant.optimus.core.appium.LocalDriverManager;
import com.testvagrant.optimus.core.models.mobile.MobileDriverDetails;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class MobileDriverListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log(String.format("Test %s has started", result.getName().toLowerCase()));
        addDivider();
        Injector parentInjector = result.getTestContext().getSuite().getParentInjector();
        Injector driverInjector =
                parentInjector.createChildInjector(new ModulesLibrary().mobileModules());

        MobileDriverDetails mobileDriverDetails = driverInjector.getInstance(MobileDriverDetails.class);
        result.setAttribute(Injectors.DRIVER_INJECTOR.getInjector(), driverInjector);
        result.setAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector(), driverInjector);
        result.setAttribute(Injectors.MOBILE_DRIVER.getInjector(), mobileDriverDetails);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        quit(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        quit(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        quit(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        quit(result);
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        quit(result);
    }

    public void quit(ITestResult result) {
        addDivider();
        Reporter.log(String.format("Test %s has ended", result.getName().toLowerCase()));
        Injector driver = (Injector) result.getAttribute(Injectors.DRIVER_INJECTOR.getInjector());
        MobileDriverDetails driverInstance = driver.getInstance(MobileDriverDetails.class);
        if (SystemProperties.TARGET == Target.REMOTE) {
            driverInstance.getDriver().quit();
        } else {
            LocalDriverManager.dispose(driverInstance);
        }
    }

    private void addDivider() {
        Reporter.log("==================================================");
    }
}
