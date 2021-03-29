package com.testvagrant.ekam.atoms.web;

public class Textbox extends BaseElement {

  public void setText(CharSequence value) {
    super.getElement().sendKeys(value);
  }

  public void clear() {
    super.getElement().clear();
  }
}
