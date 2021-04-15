package com.testvagrant.ekam.atoms.mobile.ios;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.mobile.DeviceDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class IOSDeviceDriver extends DeviceDriver {

  @Inject
  public IOSDeviceDriver(AppiumDriver<MobileElement> driver) {
    super(driver);
  }
}
