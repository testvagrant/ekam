package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;

public class Textbox extends Element {

  @Inject
  public Textbox(WebDriver driver) {
    super(driver);
  }

  public void setText(CharSequence value) {
    super.getElement().sendKeys(value);
  }

  public void clear() {
    super.getElement().clear();
  }
}
