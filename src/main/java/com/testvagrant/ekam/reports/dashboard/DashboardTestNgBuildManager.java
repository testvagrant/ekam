package com.testvagrant.ekam.reports.dashboard;

import com.testvagrant.ekam.commons.cache.DataStoreCache;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.models.dashboard.BuildOptions;
import com.testvagrant.ekam.dashboard.publishers.EkamReportPublisher;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;

public class DashboardTestNgBuildManager {

  private final DataStoreCache<Object> dataStoreCache;
  private final ThreadLocal<EkamTestNGBuildGenerator> testNGBuildGenerator = new ThreadLocal<>();

  public DashboardTestNgBuildManager() {
    dataStoreCache = dataStoreProvider();
    testNGBuildGenerator.set(new EkamTestNGBuildGenerator());
  }

  public void start(String target) {
    testNGBuildGenerator.get().startBuild();
    dataStoreProvider().put("buildGenerator", testNGBuildGenerator.get());
    dataStoreCache.put("target", target);
  }

  public void finish(String dashboardUrl) {
    testNGBuildGenerator.get().endBuild();
    testNGBuildGenerator.get().generate();
    String target = (String) dataStoreCache.get("target").orElse("mobile");
    new EkamReportPublisher(dashboardUrl, new BuildOptions(target)).publish();
  }
}
