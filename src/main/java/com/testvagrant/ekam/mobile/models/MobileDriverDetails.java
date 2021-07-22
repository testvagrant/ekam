package com.testvagrant.ekam.mobile.models;

import com.testvagrant.ekam.devicemanager.models.TargetDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.*;
import org.openqa.selenium.Capabilities;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MobileDriverDetails {
  private AppiumDriver<MobileElement> driver;
  private Capabilities capabilities;
  private AppiumDriverLocalService service;
  private TargetDetails targetDetails;

  @Override
  public String toString() {
    return "{"
        + "\"driver\":"
        + driver
        + ", \"capabilities\":"
        + capabilities
        + ", \"service\":"
        + service
        + ", \"targetDetails\":"
        + targetDetails
        + "}}";
  }
}
