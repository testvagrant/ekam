package com.testvagrant.ekam.atoms.mobile.android;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.mobile.DeviceDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AndroidDeviceDriver extends DeviceDriver {

  @Inject
  public AndroidDeviceDriver(AppiumDriver<MobileElement> driver) {
    super(driver);
  }

  public void pressKey(AndroidKey key) {
    ((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent(key));
  }
}
