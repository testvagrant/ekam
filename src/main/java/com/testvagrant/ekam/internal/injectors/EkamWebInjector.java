package com.testvagrant.ekam.internal.injectors;

import com.google.inject.Injector;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.devicemanager.models.EkamSupportedPlatforms;
import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.modules.ModulesLibrary;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class EkamWebInjector extends EkamInjector {

  public EkamWebInjector(EkamTest ekamTest, EkamConfig ekamConfig) {
    super(ekamTest, ekamConfig);
  }

  public Injector create() {
    Injector webInjector = createInjector(new ModulesLibrary().webModules());
    WebDriver webDriver = webInjector.getInstance(WebDriver.class);
    TargetDetails targetDetails = buildTargetDetails(webDriver);
    testContext.addTarget(targetDetails);
    createTargetJson();
    return webInjector;
  }

  private TargetDetails buildTargetDetails(WebDriver webDriver) {
    Capabilities capabilities = ((RemoteWebDriver) webDriver).getCapabilities();

    return TargetDetails.builder()
        .name(capabilities.getBrowserName())
        .platformVersion(capabilities.getBrowserVersion())
        .platform(EkamSupportedPlatforms.valueOf(capabilities.getBrowserName().toUpperCase()))
        .build();
  }
}
