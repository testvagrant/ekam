package com.testvagrant.ekam.reports.dashboard;

import com.testvagrant.ekam.commons.cache.DataStoreCache;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.models.dashboard.BuildOptions;
import com.testvagrant.ekam.dashboard.publishers.EkamReportPublisher;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class DashboardTestNgBuildManager {

  private final DataStoreCache<Object> dataStoreCache;
  private final ThreadLocal<EkamTestNGBuildGenerator> testNGBuildGenerator = new ThreadLocal<>();

  public DashboardTestNgBuildManager() {
    dataStoreCache = dataStoreProvider();
    testNGBuildGenerator.set(new EkamTestNGBuildGenerator());
  }

  public void start(String target) {
    ekamLogger().info("Dashboard is enabled");
    ekamLogger().info("Starting a build on dashboard");
    testNGBuildGenerator.get().startBuild();
    dataStoreProvider().put("buildGenerator", testNGBuildGenerator.get());
    dataStoreCache.put("target", target);
  }

  public void finish(String dashboardUrl) {
    testNGBuildGenerator.get().endBuild();
    testNGBuildGenerator.get().generate();
    String target = (String) dataStoreCache.get("target").orElse("mobile");
    ekamLogger().info("Publishing results to dashboard...");
    new EkamReportPublisher(dashboardUrl, new BuildOptions(target)).publish();
  }
}
