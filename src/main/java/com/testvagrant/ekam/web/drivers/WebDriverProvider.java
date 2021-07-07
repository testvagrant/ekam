package com.testvagrant.ekam.web.drivers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.config.CloudConfig;
import com.testvagrant.ekam.commons.parsers.testfeed.WebConfigParser;
import com.testvagrant.ekam.commons.random.FindOne;
import com.testvagrant.ekam.commons.random.RepetitiveStringGenerator;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.drivers.models.BrowserConfig;
import com.testvagrant.ekam.drivers.models.RemoteBrowserConfig;
import com.testvagrant.ekam.drivers.web.LocalDriverManager;
import com.testvagrant.ekam.drivers.web.RemoteDriverManager;
import com.testvagrant.ekam.mobile.remote.ConfigLoader;
import com.testvagrant.ekam.mobile.remote.RemoteUrlBuilder;
import com.testvagrant.ekam.web.WebLauncher;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.util.List;

public class WebDriverProvider implements Provider<WebDriver> {

  @Inject EkamConfig ekam;

  List<String> randomBrowsers;

  @Override
  public WebDriver get() {
    randomBrowsers = generateBrowsers();
    WebDriver webDriver = createDriver();
    launchSite(webDriver);
    return webDriver;
  }

  private WebDriver createDriver() {
    String browser = updateBrowserIfAny();
    if (ekam.getWeb().isRemote()) {
      return new RemoteDriverManager(getRemoteBrowserConfig(browser)).launchDriver();
    }
    return new LocalDriverManager(browser, getLocalBrowserConfig()).launchDriver();
  }

  private String updateBrowserIfAny() {
    String browser = ekam.getWeb().getTarget().trim();
    if (ekam.getWeb().isAny()) {
      browser = getRandomBrowser();
      ekam.getWeb().setTarget(browser.trim());
    }
    return browser;
  }

  private void launchSite(WebDriver webDriver) {
    if (!ekam.getWeb().launchSite()) return;
    new WebLauncher().launch(ekam.getWeb().getLaunchUrl(), webDriver);
  }

  private BrowserConfig getLocalBrowserConfig() {
    WebConfigParser webConfigParser = new WebConfigParser(ekam.getWeb());
    return webConfigParser.buildBrowserConfig();
  }

  private RemoteBrowserConfig getRemoteBrowserConfig(String browser) {
    WebConfigParser webConfigParser = new WebConfigParser(ekam.getWeb());
    BrowserConfig browserConfig = webConfigParser.buildBrowserConfig();
    CloudConfig cloudConfig = new ConfigLoader().loadConfig(ekam.getWeb().getHub());
    URL remoteUrl = RemoteUrlBuilder.build(cloudConfig);
    return RemoteBrowserConfig.builder()
        .browser(browser)
        .url(remoteUrl)
        .desiredCapabilities(browserConfig.getDesiredCapabilities())
        .build();
  }

  private String getRandomBrowser() {
    return FindOne.inList(randomBrowsers);
  }

  private List<String> generateBrowsers() {
    RepetitiveStringGenerator repetitiveStringGenerator = new RepetitiveStringGenerator();
    return repetitiveStringGenerator.generate("chrome", "firefox");
  }
}
