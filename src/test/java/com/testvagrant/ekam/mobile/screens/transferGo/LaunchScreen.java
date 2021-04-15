package com.testvagrant.ekam.mobile.screens.transferGo;

import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import org.openqa.selenium.By;

public class LaunchScreen extends MobileScreen {

  private final By checkRates = queryById("authentication_explore");

  public TransferScreen checkRates() {
    element(checkRates).click();
    return createInstance(TransferScreen.class);
  }
}
