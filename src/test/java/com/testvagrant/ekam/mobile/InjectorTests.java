package com.testvagrant.ekam.mobile;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectorTests {

  @Inject private AppiumDriver<MobileElement> driver;
  @Inject private WebDriverWait wait;
  @Inject private FluentWait<AppiumDriver<MobileElement>> fluentWait;

  @Test(groups = "unit")
  public void mobileInjectsShouldNotBeNull() {
    assertThat(driver).isNotNull();
    assertThat(wait).isNotNull();
    assertThat(fluentWait).isNotNull();
  }
}
