package com.testvagrant.ekam.web;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.MultiPlatformFinder;
import com.testvagrant.ekam.atoms.web.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.testvagrant.ekam.commons.LayoutInitiator.Page;

public class WebPage extends QueryFunctions {

  @Inject protected BrowserDriver browserDriver;
  @Inject protected WebDriver driver;

  protected Element element(By locator) {
    return new Element(driver, locator);
  }

  protected Textbox textbox(By locator) {
    return new Textbox(driver, locator);
  }

  protected Dropdown dropdown(By locator) {
    return new Dropdown(driver, locator);
  }

  protected MultiPlatformFinder finder(By findBy, By responsiveFindBy) {
    return MultiPlatformFinder.builder()
        .webFindBy(findBy)
        .responsiveFindBy(responsiveFindBy)
        .build();
  }

  protected ElementCollection elementCollection(By locator) {
    return new ElementCollection(driver, locator);
  }

  protected <T extends WebPage> T createInstance(Class<T> clazz) {
    return Page(clazz);
  }
}
