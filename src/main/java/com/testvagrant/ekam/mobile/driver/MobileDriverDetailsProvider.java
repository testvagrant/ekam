package com.testvagrant.ekam.mobile.driver;

import com.google.inject.Provider;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.logs.AppiumLogRecorder;
import com.testvagrant.optimus.core.mobile.MobileDriverManager;
import com.testvagrant.optimus.core.models.mobile.MobileDriverDetails;

import java.util.Timer;

public class MobileDriverDetailsProvider implements Provider<MobileDriverDetails> {

  protected ThreadLocal<MobileDriverDetails> mobileDriverDetailsThreadLocal = new ThreadLocal<>();

  public MobileDriverDetails setupMobileDriver() {
    MobileDriverDetails mobileDriverDetails = new MobileDriverManager().createDriverDetails();
    mobileDriverDetailsThreadLocal.set(mobileDriverDetails);
    return mobileDriverDetailsThreadLocal.get();
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
