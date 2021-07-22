package com.testvagrant.ekam.mobile.models;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class IOSOnlyCapabilities extends GeneralCapabilities {
  private String waitForAppScript;
  private Integer interKeyDelay;
  private String safariInitialUrl;
  private String localizableStringsDir;
  private Boolean keepKeyChains;
  private String calendarFormat;
  private Integer screenshotWaitTimeout;
  private Boolean nativeWebTap;
  private Integer launchTimeout;
  private Boolean autoDismissAlerts;
  private Boolean safariOpenLinksInBackground;
  private String sendKeyStrategy;
  private Boolean safariAllowPopups;
  private Boolean locationServicesAuthorized;
  private String appName;
  private Boolean safariIgnoreFraudWarning;
  private Boolean autoAcceptAlerts;
  private Boolean showIOSLog;
  private String processArguments;
  private Boolean nativeInstrumentsLib;
  private String bundleId;
  private Integer webviewConnectRetries;
  private Boolean locationServicesEnabled;
}
