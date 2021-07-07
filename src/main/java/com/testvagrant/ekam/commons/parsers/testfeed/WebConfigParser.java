package com.testvagrant.ekam.commons.parsers.testfeed;

import com.testvagrant.ekam.commons.models.web.WebTestFeed;
import com.testvagrant.ekam.config.models.ConfigKeys;
import com.testvagrant.ekam.config.models.WebConfig;
import com.testvagrant.ekam.drivers.models.BrowserConfig;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;
import java.util.Map;

public class WebConfigParser extends TestConfigParser {
  private final WebTestFeed webTestFeed;
  private final WebConfig webConfig;

  public WebConfigParser(WebConfig webConfig) {
    this.webConfig = webConfig;
    String testFeedName = this.webConfig.getFeed();
    webTestFeed = getTestFeed(testFeedName);
  }

  public DesiredCapabilities getDesiredCapabilities() {
    Map<String, Object> capabilitiesMap = webTestFeed.getDesiredCapabilities();
    capabilitiesMap.put(CapabilityType.BROWSER_NAME, getBrowserName());
    return new DesiredCapabilities(capabilitiesMap);
  }

  public String getBrowserName() {
    return webConfig.getTarget();
  }

  public List<String> getArguments() {
    List<String> arguments = webTestFeed.getArguments();
    if (webConfig.isHeadless()) {
      arguments.add("--headless");
    }
    return arguments;
  }

  public List<String> getExtensions() {
    return webTestFeed.getExtensions();
  }

  public Map<String, Object> getExperimentalOptions() {
    return webTestFeed.getExperimentalOptions();
  }

  public Map<String, Object> getPreferences() {
    return webTestFeed.getPreferences();
  }

  public BrowserConfig buildBrowserConfig() {
    return BrowserConfig.builder()
        .desiredCapabilities(getDesiredCapabilities())
        .arguments(getArguments())
        .preferences(getPreferences())
        .arguments(getArguments())
        .experimentalOptions(getExperimentalOptions())
        .extensions(getExtensions())
        .build();
  }

  private WebTestFeed getTestFeed(String testFeedName) {
    if (testFeedName == null || testFeedName.isEmpty()) {
      return WebTestFeed.builder().build();
    }
    return loadFeed(testFeedName, System.getProperty(ConfigKeys.Env.WEB_ENV), WebTestFeed.class);
  }
}
