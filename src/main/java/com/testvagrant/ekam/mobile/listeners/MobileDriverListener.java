package com.testvagrant.ekam.mobile.listeners;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.listeners.DriverListener;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.ekam.commons.modules.OptimusRunTargetModule;
import com.testvagrant.optimus.core.mobile.MobileDriverManager;
import com.testvagrant.optimus.core.models.mobile.MobileDriverDetails;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import com.testvagrant.optimus.dashboard.OptimusTestNGBuildGenerator;
import org.testng.*;

public class MobileDriverListener extends DriverListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log(String.format("Test %s has started", result.getName().toLowerCase()));
        addDivider();
        Injector parentInjector = result.getTestContext().getSuite().getParentInjector();
        Injector driverInjector =
                parentInjector.createChildInjector(new ModulesLibrary().mobileModules());

        MobileDriverDetails mobileDriverDetails = driverInjector.getInstance(MobileDriverDetails.class);
        Injector childInjector =
                driverInjector.createChildInjector(
                        new OptimusRunTargetModule(mobileDriverDetails.getDriver(), result, mobileDriverDetails.getTargetDetails()));
        result.setAttribute(Injectors.DRIVER_INJECTOR.getInjector(), childInjector);
        result.setAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector(), childInjector);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        quit(result, "passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        quit(result, "failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        quit(result, "skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        quit(result, "failed");
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        quit(result, "failed");
    }

    public void quit(ITestResult result, String status) {
        OptimusTestNGBuildGenerator buildGenerator =
                (OptimusTestNGBuildGenerator)
                        result.getTestContext().getSuite().getAttribute("buildGenerator");
        buildGenerator.addTestCase(result, status);
        addDivider();
        Reporter.log(String.format("Test %s has ended", result.getName().toLowerCase()));
        MobileDriverManager.dispose();
    }

    private void addDivider() {
        Reporter.log("==================================================");
    }

}
