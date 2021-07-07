package com.testvagrant.ekam.commons.initializers;

import com.testvagrant.ekam.commons.cache.DataStoreCache;
import com.testvagrant.ekam.commons.injectors.Injectors;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.ekam.config.models.DashboardConfig;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.models.dashboard.BuildOptions;
import com.testvagrant.ekam.dashboard.publishers.EkamReportPublisher;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;

public class TestNgBuildInitializer {

  private final DataStoreCache<Object> dataStoreCache;

  public TestNgBuildInitializer() {
    dataStoreCache = dataStoreProvider();
  }

  public void start(String target, boolean publishToDashboard) {
    if (!publishToDashboard) return;
    EkamTestNGBuildGenerator testNGBuildGenerator = new EkamTestNGBuildGenerator();
    testNGBuildGenerator.startBuild();
    dataStoreProvider().put("buildGenerator", testNGBuildGenerator);
    dataStoreCache.put("target", target);
    String logFolder = new LogWriter().createLogFolder();
    dataStoreCache.put(Injectors.LOG_FOLDER.getInjector(), logFolder);
  }

  public void finish(DashboardConfig dashboard) {
    if (!dashboard.publishToDashboard()) return;
    EkamTestNGBuildGenerator testNGBuildGenerator =
        (EkamTestNGBuildGenerator)
            dataStoreCache.get("buildGenerator").orElse(getTestNgBuildGenerator());
    testNGBuildGenerator.endBuild();
    testNGBuildGenerator.generate();
    generateReport(dashboard.getDashboardUrl());
  }

  public void generateReport(String ekamServerUrl) {
    getBuildOptions();
    EkamReportPublisher ekamReportPublisher =
        new EkamReportPublisher(ekamServerUrl, getBuildOptions());
    ekamReportPublisher.publish();
  }

  private BuildOptions getBuildOptions() {
    String target = (String) dataStoreCache.get("target").orElse("mobile");
    return new BuildOptions(target);
  }

  private EkamTestNGBuildGenerator getTestNgBuildGenerator() {
    EkamTestNGBuildGenerator testNGBuildGenerator = new EkamTestNGBuildGenerator();
    testNGBuildGenerator.startBuild();
    return testNGBuildGenerator;
  }
}
