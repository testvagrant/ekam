package com.testvagrant.ekam.commons.models.caps;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AndroidOnlyCapabilities extends GeneralCapabilities {
  private String appActivity;
  private String appPackage;
  private String appWaitActivity;
  private String appWaitPackage;
  private Integer appWaitDuration;
  private Integer deviceReadyTimeout;
  private Boolean allowTestPackages;
  private String androidCoverage;
  private String androidCoverageEndIntent;
  private Integer androidDeviceReadyTimeout;
  private Integer androidInstallTimeout;
  private String androidInstallPath;
  private Integer adbPort;
  private Integer systemPort;
  private String remoteAdbHost;
  private String androidDeviceSocket;
  private String avd;
  private Integer avdLaunchTimeout;
  private String avdReadyTimeout;
  private String avdArgs;
  private Boolean useKeystore;
  private String keystorePath;
  private String keystorePassword;
  private String keyAlias;
  private String keyPassword;
  private String chromedriverExecutable;
  private Boolean nativeWebScreenshot;
  private Boolean noSign;
  private Boolean disableAndroidWatchers;
  private Boolean dontStopAppOnReset;
  private Boolean resetKeyboard;
  private Boolean recreateChromeDriverSessions;
  private String intentFlags;
  private String chromeOptions;
  private String optionalIntentArguments;
  private String intentCategory;
  private Integer autoWebviewTimeout;
  private String androidScreenshotPath;
  private String intentAction;
  private Boolean ignoreUnimportantViews;
  private Boolean unicodeKeyboard;
  private Boolean autoGrantPermissions;
  private Boolean autoAcceptAlerts;
}
