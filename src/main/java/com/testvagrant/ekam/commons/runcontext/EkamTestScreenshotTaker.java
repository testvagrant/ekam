package com.testvagrant.ekam.commons.runcontext;

import com.testvagrant.ekam.commons.path.PathBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;

public class EkamTestScreenshotTaker {

  private final EkamTestContext testContext;

  public EkamTestScreenshotTaker(EkamTestContext testContext) {
    this.testContext = testContext;
  }

  public Path captureScreenshot() {
    File file = takeScreenshotAsFile();
    Path destinationPath =
        new File(
                new PathBuilder(testContext.getTestFolder())
                    .append("screenshots")
                    .append(LocalDateTime.now() + ".png")
                    .toString())
            .toPath();
    try {
      Files.move(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException("Cannot move screenshot.\n" + e.getMessage());
    }
    return destinationPath;
  }

  private File takeScreenshotAsFile() {
    try {
      TakesScreenshot driver =
          (TakesScreenshot) injectorsCache().getInjector().getInstance(WebDriver.class);
      return driver.getScreenshotAs(OutputType.FILE);
    } catch (WebDriverException ex) {
      throw new RuntimeException("Failed to take screenshot." + ex.getMessage());
    }
  }
}
