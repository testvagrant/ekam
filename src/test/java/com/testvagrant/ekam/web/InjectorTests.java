package com.testvagrant.ekam.web;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectorTests {
  @Inject private WebDriver driver;
  @Inject private FluentWait<WebDriver> fluentWait;
  @Inject private WebDriverWait wait;

  @Test(groups = "unit")
  public void webInjectsShouldNotBeNull() {
    assertThat(driver).isNotNull();
    assertThat(wait).isNotNull();
    assertThat(fluentWait).isNotNull();
  }
}
