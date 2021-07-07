package com.testvagrant.ekam.commons.models.caps;

import com.testvagrant.ekam.commons.io.GsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class GeneralCapabilities {
  private String automationName;
  private String platformName;
  private String platformVersion;
  private String deviceName;
  private String app;
  private String otherApps;
  private String browserName;
  private Integer newCommandTimeout;
  private String language;
  private String locale;
  private String udid;
  private String orientation;
  private Boolean autoWebview;
  private Boolean noReset;
  private Boolean fullReset;
  private Boolean eventTimings;
  private Boolean enablePerformanceLogging;
  private Boolean printPageSourceOnFindFailure;
  private Boolean clearSystemFiles;

  public Map<String, Object> toDesiredCapabilities() {
    GsonParser gsonParser = new GsonParser();
    String caps = gsonParser.serialize(this);
    return gsonParser.deserializeFromString(caps, Map.class);
  }
}
