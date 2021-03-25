package com.testvagrant.ekam.mobile.listeners;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.Target;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.Arrays;
import java.util.List;

public class MobileDriverListener implements ITestListener {


    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log(String.format("Test %s has started", result.getName().toLowerCase()));
        addDivider();
        Injector driverInjector = result.getTestContext().getSuite().getParentInjector().createChildInjector(new SwitchViewModule(), new MobileModule());
        result.getTestContext().setAttribute(Injectors.DRIVER_INJECTOR.getInjector(), driverInjector);
        result.getTestContext().setAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector(), driverInjector);
        MobileDriverDetails mobileDriverDetails = driverInjector.getInstance(MobileDriverDetails.class);
        result.getTestContext().setAttribute(Injectors.MOBILE_DRIVER.getInjector(), mobileDriverDetails);
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
    public void onTestFailedWithTimeout(ITestResult result) {
        quit(result);
    }


    public void quit(ITestResult result) {
        if(SystemProperties.TARGET.equals(Target.OPTIMUS)) return;
        Injector driver = (Injector) result.getTestContext().getAttribute(Injectors.DRIVER_INJECTOR.getInjector());
        MobileDriverDetails driverInstance = driver.getInstance(MobileDriverDetails.class);
        addDivider();
        Reporter.log(String.format("Test %s has ended",result.getName().toLowerCase()));
        driverInstance.getMobileDriver().quit();
    }

    private void addDivider() {
        Reporter.log("==================================================");
    }

    private List<Module> setupModules() {
        return Arrays.asList(
                new PropertyModule(),
                new LocaleModule(),
                new SwitchViewModule(),
                new MobileModule());
    }


}
