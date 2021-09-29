package com.testvagrant.ekam.mobile;

import com.testvagrant.ekam.commons.io.FileFinder;

import java.io.File;
import java.util.List;

import static com.testvagrant.ekam.commons.io.ResourcePaths.APP_DIR;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;
import static com.testvagrant.ekam.mobile.constants.MobilePlatform.ANDROID;
import static com.testvagrant.ekam.mobile.constants.MobilePlatform.IOS;

public class AppFinder {

  public static String getDefaultApp(String platform) {
    RuntimeException exception =
        new RuntimeException(String.format("Cannot find app in '%s'.", APP_DIR));
    FileFinder fileFinder = new FileFinder(APP_DIR, "");

    switch (platform.trim().toLowerCase()) {
      case IOS:
        List<File> iosFiles = fileFinder.findWithExtension(".ipa");
        List<File> iosSimFiles = fileFinder.findWithExtension(".app");
        iosFiles.addAll(iosSimFiles);
        return iosFiles.stream().findAny().orElseThrow(() -> {
          ekamLogger().warn("Cannot find {} app in {}", platform, APP_DIR);
          return exception;}
                ).getAbsolutePath();
      case ANDROID:
      default:
        List<File> androidFiles = fileFinder.findWithExtension(".apk");
        return androidFiles.stream().findAny().orElseThrow(() -> {
          ekamLogger().warn("Cannot find {} app in {}", platform, APP_DIR);
          return exception;
        }).getAbsolutePath();
    }
  }

  public static String findApp(String path) {
    return new FileFinder(APP_DIR, "").find(path).getAbsolutePath();
  }
}
