package com.testvagrant.ekam.web.drivers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.config.CloudConfig;
import com.testvagrant.ekam.commons.parsers.testfeed.WebConfigParser;
import com.testvagrant.ekam.commons.platform.EkamSupportedPlatforms;
import com.testvagrant.ekam.commons.random.FindAny;
import com.testvagrant.ekam.commons.random.RepetitiveStringGenerator;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.config.models.WebConfig;
import com.testvagrant.ekam.drivers.models.BrowserConfig;
import com.testvagrant.ekam.drivers.models.RemoteBrowserConfig;
import com.testvagrant.ekam.drivers.web.LocalDriverManager;
import com.testvagrant.ekam.drivers.web.RemoteDriverManager;
import com.testvagrant.ekam.mobile.remote.ConfigLoader;
import com.testvagrant.ekam.mobile.remote.RemoteUrlBuilder;
import org.awaitility.Awaitility;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.List;

public class WebDriverProvider implements Provider<WebDriver> {

  private final List<String> randomBrowsers;
  @Inject private EkamConfig ekam;

  public WebDriverProvider() {
    randomBrowsers = generateBrowsers();
  }

  @Override
  public WebDriver get() {
    WebDriver webDriver = createDriver();
    launchSite(webDriver);
    return webDriver;
  }

  private WebDriver createDriver() {
    WebConfigParser webConfigParser = new WebConfigParser(ekam.getWeb());
    BrowserConfig browserConfig = webConfigParser.buildBrowserConfig();
    String browser = getBrowser();

    return ekam.getWeb().isRemote()
        ? new RemoteDriverManager(getRemoteBrowserConfig(browser, browserConfig)).launchDriver()
        : new LocalDriverManager(browser, browserConfig).launchDriver();
  }

  private String getBrowser() {
    WebConfig webConfig = ekam.getWeb();

    if (webConfig.isAny()) {
      String browser = FindAny.inList(randomBrowsers);
      webConfig.setTarget(browser.trim());
      return browser;
    }

    String browser = webConfig.getTarget().trim();
    return browser.equalsIgnoreCase("responsive")
        ? EkamSupportedPlatforms.CHROME.name().toLowerCase()
        : browser;
  }

  private void launchSite(WebDriver webDriver) {
    if (!ekam.getWeb().launchSite()) return;

    String url = ekam.getWeb().getLaunchUrl().trim();
    webDriver.get(url);

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

  private List<String> generateBrowsers() {
    RepetitiveStringGenerator repetitiveStringGenerator = new RepetitiveStringGenerator();
    return repetitiveStringGenerator.generate("chrome", "firefox", "msedge");
  }
}
