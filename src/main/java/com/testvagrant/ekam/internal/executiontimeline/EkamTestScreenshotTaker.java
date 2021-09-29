package com.testvagrant.ekam.internal.executiontimeline;

import com.testvagrant.ekam.commons.path.PathBuilder;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTestContext;
import com.testvagrant.ekam.mobile.models.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

/** Captures and save screenshots for the EkamTest specified */
public class EkamTestScreenshotTaker {

  private final EkamTestContext testContext;

  public EkamTestScreenshotTaker(EkamTestContext testContext) {
    this.testContext = testContext;
  }

  public Path captureScreenshot() {
    File file = takeScreenshotAsFile();
    Path destinationPath =
        new File(
                new PathBuilder(testContext.getTestDirectory())
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
      AppiumDriver<MobileElement> mobileDriver =
          injectorsCache().getInjector().getInstance(MobileDriverDetails.class).getDriver();

      // Convert Mobile/WebDriver to ScreenshotDriver
      TakesScreenshot driver =
          mobileDriver == null
              ? (TakesScreenshot) injectorsCache().getInjector().getInstance(WebDriver.class)
              : mobileDriver;

      return driver.getScreenshotAs(OutputType.FILE);
    } catch (WebDriverException ex) {
      ekamLogger().warn("Failed to take screenshot {}", ex.getMessage());
      throw new RuntimeException("Failed to take screenshot." + ex.getMessage());
    }
  }
}
