package com.testvagrant.ekam.atoms.web;

import com.testvagrant.ekam.commons.annotations.Screenshot;

public class Textbox extends BaseElement {

  public void setText(CharSequence value) {
    super.getElement().sendKeys(value);
  }

  public void clear() {
    super.getElement().clear();
  }
}
