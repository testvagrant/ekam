package com.testvagrant.ekam.atoms.web;

import org.openqa.selenium.By;

public class Textbox extends Element {
  private Textbox(By locator) {
    super(locator);
  }

  public static Textbox Textbox(By locator) {
    return new Textbox(locator);
  }

  public void setText(CharSequence value) {
    super.getElement().sendKeys(value);
  }

  public void clear() {
    super.getElement().clear();
  }
}
