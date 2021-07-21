package com.testvagrant.ekam.commons.runcontext;

import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.path.PathBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

public class EkamTestExecutionDetailsManager {

  private String screenshotDirPath;
  private final EkamTestContext ekamTestContext;
  private EkamTestContext webContext;
  private EkamTestContext mobileContext;

  public EkamTestExecutionDetailsManager(EkamTestContext ekamTestContext) {
    this.ekamTestContext = ekamTestContext;
    this.webContext = ekamTestContext;
    this.mobileContext = ekamTestContext;
  }

  public void addWebContext(EkamTestContext ekamTestContext) {
    this.webContext = ekamTestContext;
  }

  public void addMobileContext(EkamTestContext ekamTestContext) {
    this.mobileContext = ekamTestContext;
  }

  public void captureScreenshot() {
    captureScreenshot(ekamTestContext);
  }

  public Path captureWebScreenshot() {
    return captureScreenshot(webContext);
  }

  public Path captureMobileScreenshot() {
    return captureScreenshot(mobileContext);
  }

  private Path captureScreenshot(EkamTestContext ekamTestContext) {
    File file = takeScreenshotAsFile(ekamTestContext);
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

  public void saveTargetDetails() {
    String serialize = new GsonParser().serialize(ekamTestContext.getTargets());
    serialize = serialize == null ? "" : serialize;
    try {
      File testFolder = new File(new PathBuilder(ekamTestContext.getTestFolder()).toString());

      if (!testFolder.exists()) {
        throw new RuntimeException(testFolder + "doesn't exist");
      }

      FileWriter file =
          new FileWriter(
              new PathBuilder(ekamTestContext.getTestFolder()).append("target.json").toString());

      file.write(serialize);
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  public void createScreenShotDirectory() {
    String path = new PathBuilder(ekamTestContext.getTestFolder()).append("screenshots").toString();
    this.screenshotDirPath = createDirectory(path);
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
        throw new RuntimeException(String.format("'%s' directory couldn't be created.", dirPath));
      }
    }

    return file.getAbsolutePath();
  }

  private File takeScreenshotAsFile(EkamTestContext ekamTestContext) {
    try {
      return ((TakesScreenshot) ekamTestContext.getDriver()).getScreenshotAs(OutputType.FILE);
    } catch (WebDriverException ex) {
      throw new RuntimeException("Failed to take screenshot." + ex.getMessage());
    }
  }
}
