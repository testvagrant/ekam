package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Textbox extends EkamMobileElement {

  @Inject
  public Textbox(AppiumDriver<MobileElement> driver) {
    super(driver);
  }

  public void setText(CharSequence value) {
    super.getElement().sendKeys(value);
  }

  public void clear() {
    super.getElement().clear();
  }
}
