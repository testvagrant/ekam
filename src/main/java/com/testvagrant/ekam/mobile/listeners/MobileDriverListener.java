package com.testvagrant.ekam.mobile.listeners;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.ModulesLibrary;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Target;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import org.testng.*;

public class MobileDriverListener implements ITestListener, ISuiteListener {

  @Override
  public void onStart(ISuite suite) {
    Injector injector =
        suite.getParentInjector().createChildInjector(new ModulesLibrary().mobileModules());
    String logFolder = injector.getInstance(LogWriter.class).createLogFolder();
    suite.setAttribute(Injectors.LOG_FOLDER.getInjector(), logFolder);
  }

  @Override
  public void onTestStart(ITestResult result) {
    Reporter.log(String.format("Test %s has started", result.getName().toLowerCase()));
    addDivider();
    Injector driverInjector =
        result
            .getTestContext()
            .getSuite()
            .getParentInjector()
            .createChildInjector(new ModulesLibrary().mobileModules());

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
  public void onTestFailedWithTimeout(ITestResult result) {
    quit(result);
  }

  public void quit(ITestResult result) {
    if (SystemProperties.TARGET.equals(Target.OPTIMUS)) return;
    Injector driver = (Injector) result.getAttribute(Injectors.DRIVER_INJECTOR.getInjector());
    MobileDriverDetails driverInstance = driver.getInstance(MobileDriverDetails.class);
    addDivider();
    Reporter.log(String.format("Test %s has ended", result.getName().toLowerCase()));
    driverInstance.getMobileDriver().quit();
  }

  private void addDivider() {
    Reporter.log("==================================================");
  }

  private Module[] setupModules() {
    return new Module[] {
      new PropertyModule(), new LocaleModule(), new SwitchViewModule(), new MobileModule()
    };
  }
}
