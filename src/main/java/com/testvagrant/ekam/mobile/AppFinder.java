package com.testvagrant.ekam.mobile;

import com.testvagrant.ekam.commons.io.FileFinder;
import com.testvagrant.ekam.commons.io.ResourcePaths;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class AppFinder {
  public static File getAppFile(String app) {
    File file = new FileFinder(ResourcePaths.APP_DIR, "").find(app);
    if (!file.exists()) throw new RuntimeException("Cannot find app " + app + "in app folder");
    return file;
  }

  public static Optional<File> getDefaultApp(String platform) {
    switch (platform.trim().toLowerCase()) {
      case "ios":
        List<File> iosFiles = new FileFinder(ResourcePaths.APP_DIR, "").findWithExtension(".ipa");
        return iosFiles.stream().findAny();
      default:
        List<File> androidFiles =
            new FileFinder(ResourcePaths.APP_DIR, "").findWithExtension(".apk");
        return androidFiles.stream().findAny();
    }
  }
}
