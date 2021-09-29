package com.testvagrant.ekam.web.configparser;

import com.testvagrant.ekam.commons.parsers.TestConfigParser;
import com.testvagrant.ekam.config.models.ConfigKeys;
import com.testvagrant.ekam.config.models.WebConfig;
import com.testvagrant.ekam.drivers.models.BrowserConfig;
import com.testvagrant.ekam.web.models.WebTestFeed;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class WebConfigParser extends TestConfigParser {

  private final WebConfig webConfig;

  public WebConfigParser(WebConfig webConfig) {
    this.webConfig = webConfig;
  }

  public BrowserConfig buildBrowserConfig() {
    WebTestFeed webTestFeed = getTestFeed(webConfig.getFeed());
    ekamLogger().info("Parsing web feed {}", webTestFeed);
    DesiredCapabilities capabilities =
        new DesiredCapabilities(webTestFeed.getDesiredCapabilities());

    List<String> arguments = webTestFeed.getArguments();
    if (webConfig.isHeadless()) arguments.add("--headless");
    BrowserConfig browserConfig = BrowserConfig.builder()
            .desiredCapabilities(capabilities)
            .arguments(arguments)
            .preferences(webTestFeed.getPreferences())
            .experimentalOptions(webTestFeed.getExperimentalOptions())
            .extensions(webTestFeed.getExtensions())
            .build();
    return browserConfig;
  }

  private WebTestFeed getTestFeed(String testFeedName) {
    return testFeedName == null || testFeedName.isEmpty()
        ? WebTestFeed.builder().build()
        : loadFeed(testFeedName, System.getProperty(ConfigKeys.Env.WEB_ENV), WebTestFeed.class);
  }
}
