package com.testvagrant.ekam.commons.runcontext;

import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.path.PathBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Set;

public class EkamRunTarget {

  private final String logDirPath;
  private final String screenshotDirPath;
  private final EkamRunContext ekamRunContext;
  private EkamRunContext ekamWebContext;
  private EkamRunContext ekamMobileContext;

  public EkamRunTarget(EkamRunContext ekamRunContext) {
    this.ekamRunContext = ekamRunContext;
    this.ekamWebContext = ekamRunContext;
    this.ekamMobileContext = ekamRunContext;
    screenshotDirPath = createScreenShotDirectory(ekamRunContext);
    logDirPath = createLogsDirectory(ekamRunContext);
    saveTargetDetails();
  }

  public EkamRunTarget addWebContext(EkamRunContext optimusRunWebContext) {
    this.ekamWebContext = optimusRunWebContext;
    return this;
  }

  public EkamRunTarget addMobileContext(EkamRunContext optimusRunWebContext) {
    this.ekamMobileContext = optimusRunWebContext;
    return this;
  }

  public Path captureScreenshot() {
    return captureScreenshot(ekamRunContext);
  }

  public Path captureWebScreenshot() {
    return captureScreenshot(ekamWebContext);
  }

  public Path captureMobileScreenshot() {
    return captureScreenshot(ekamMobileContext);
  }

  private Path captureScreenshot(EkamRunContext ekamRunContext) {
    File file = takeScreenshotAsFile(ekamRunContext);
    Path destinationPath =
        new File(new PathBuilder(screenshotDirPath).append(LocalDateTime.now() + ".png").toString())
            .toPath();
    try {
      Files.move(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException("Cannot move screenshot.\n" + e.getMessage());
    }
    return destinationPath;
  }

  public void captureLogs() {
    try {
      Set<String> availableLogTypes;
      try {
        availableLogTypes = ekamRunContext.getWebDriver().manage().logs().getAvailableLogTypes();
      } catch (Exception e) {
        return;
      }
      availableLogTypes.stream()
          .parallel()
          .forEach(
              logType -> {
                LogEntries logEntries = ekamRunContext.getWebDriver().manage().logs().get(logType);
                FileWriter file = null;
                try {
                  file =
                      new FileWriter(
                          new PathBuilder(logDirPath).append(logType + ".json").toString());
                  file.write(String.valueOf(logEntries.toJson()));
                  file.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void saveTargetDetails() {
    String serialize = new GsonParser().serialize(ekamRunContext.getTargets());
    try {
      File testFolder = new File(new PathBuilder(ekamRunContext.getTestFolder()).toString());

      if (!testFolder.exists()) {
        throw new RuntimeException(testFolder.toString() + "doesn't exist");
      }

      FileWriter file =
          new FileWriter(
              new PathBuilder(ekamRunContext.getTestFolder()).append("target.json").toString());

      file.write(serialize);
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  private String createScreenShotDirectory(EkamRunContext ekamRunContext) {
    String screenshotDirPath =
        new PathBuilder(ekamRunContext.getTestFolder()).append("screenshots").toString();
    return createDirectory(screenshotDirPath);
  }

  private String createLogsDirectory(EkamRunContext ekamRunContext) {
    String logDirPath = new PathBuilder(ekamRunContext.getTestFolder()).append("logs").toString();
    return createDirectory(logDirPath);
  }

  private String createDirectory(String dirPath) {
    File file = new File(dirPath);
    if (file.exists() && file.isDirectory()) {
      try {
        FileUtils.deleteDirectory(file);
      } catch (Exception ex) {
        throw new RuntimeException(ex.getMessage());
      }
    }
    if (!file.exists()) {
      boolean directoryCreated = file.mkdirs();
      if (!directoryCreated) {
        throw new RuntimeException(file.getAbsolutePath() + " directory couldn't be created.");
      }
    }

    return file.getAbsolutePath();
  }

  private File takeScreenshotAsFile(EkamRunContext ekamRunContext) {
    try {
      return ((TakesScreenshot) ekamRunContext.getWebDriver()).getScreenshotAs(OutputType.FILE);
    } catch (WebDriverException ex) {
      throw new RuntimeException("Failed to take screenshot." + ex.getMessage());
    }
  }

  public EkamRunContext getEkamWebContext() {
    return ekamWebContext;
  }

  public EkamRunContext getEkamMobileContext() {
    return ekamMobileContext;
  }
}
