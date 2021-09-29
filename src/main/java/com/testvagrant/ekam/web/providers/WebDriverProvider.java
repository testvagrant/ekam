package com.testvagrant.ekam.web.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.platform.EkamSupportedPlatforms;
import com.testvagrant.ekam.commons.random.FindAny;
import com.testvagrant.ekam.commons.random.RepetitiveStringGenerator;
import com.testvagrant.ekam.commons.remote.ConfigLoader;
import com.testvagrant.ekam.commons.remote.RemoteUrlBuilder;
import com.testvagrant.ekam.commons.remote.models.CloudConfig;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.config.models.WebConfig;
import com.testvagrant.ekam.drivers.models.BrowserConfig;
import com.testvagrant.ekam.drivers.models.RemoteBrowserConfig;
import com.testvagrant.ekam.drivers.web.LocalDriverManager;
import com.testvagrant.ekam.drivers.web.RemoteDriverManager;
import com.testvagrant.ekam.web.configparser.WebConfigParser;
import org.awaitility.Awaitility;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class WebDriverProvider implements Provider<WebDriver> {

  @Inject private EkamConfig ekam;

  @Override
  public WebDriver get() {
    BrowserConfig browserConfig = new WebConfigParser(ekam.getWeb()).buildBrowserConfig();
    String browser = getBrowser();

    WebDriver driver =
        ekam.getWeb().isRemote()
            ? new RemoteDriverManager(getRemoteBrowserConfig(browser, browserConfig)).launchDriver()
            : new LocalDriverManager(browser, browserConfig).launchDriver();

    launchSite(driver);
    return driver;
  }

  private String getBrowser() {
    WebConfig webConfig = ekam.getWeb();

    if (webConfig.isAny()) {
      List<String> randomBrowsers = generateBrowsers();
      String browser = FindAny.inList(randomBrowsers);
      webConfig.setTarget(browser.trim());
      return browser;
    }

    String browser = webConfig.getTarget().trim();
    return browser.equalsIgnoreCase("responsive")
        ? EkamSupportedPlatforms.CHROME.name().toLowerCase()
        : browser;
  }

  private RemoteBrowserConfig getRemoteBrowserConfig(String browser, BrowserConfig browserConfig) {
    CloudConfig cloudConfig = new ConfigLoader().loadConfig(ekam.getWeb().getHub());
    URL remoteUrl = RemoteUrlBuilder.build(cloudConfig);

    return RemoteBrowserConfig.builder()
        .browser(browser)
        .url(remoteUrl)
        .desiredCapabilities(browserConfig.getDesiredCapabilities())
        .experimentalOptions(browserConfig.getExperimentalOptions())
        .build();
  }

  private void launchSite(WebDriver webDriver) {
    if (!ekam.getWeb().launchSite()) return;

    String url = ekam.getWeb().getLaunchUrl().trim();
    webDriver.get(url);
    ekamLogger().info("Launched site {}", url);

    for (int retry = 0; retry < 3; retry++) {
      try {
        Awaitility.await()
            .atMost(Duration.ofMinutes(1))
            .until(() -> !webDriver.getTitle().isEmpty());
        break;
      } catch (Exception e) {
        webDriver.navigate().refresh();
      }
    }
  }

  private List<String> generateBrowsers() {
    RepetitiveStringGenerator repetitiveStringGenerator = new RepetitiveStringGenerator();
    return repetitiveStringGenerator.generate("chrome", "firefox", "msedge");
  }
}
