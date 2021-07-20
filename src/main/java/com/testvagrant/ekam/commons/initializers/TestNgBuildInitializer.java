package com.testvagrant.ekam.commons.initializers;

import com.testvagrant.ekam.commons.cache.DataStoreCache;
import com.testvagrant.ekam.commons.injectors.Injectors;
import com.testvagrant.ekam.config.models.DashboardConfig;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.models.dashboard.BuildOptions;
import com.testvagrant.ekam.dashboard.publishers.EkamReportPublisher;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;

public class TestNgBuildInitializer {

  private final DataStoreCache<Object> dataStoreCache;
  private final ThreadLocal<EkamTestNGBuildGenerator> testNGBuildGenerator = new ThreadLocal<>();

  public TestNgBuildInitializer() {
    dataStoreCache = dataStoreProvider();
    testNGBuildGenerator.set(new EkamTestNGBuildGenerator());
  }

  public void start(String target, boolean publishToDashboard) {
    if (!publishToDashboard) return;
    testNGBuildGenerator.get().startBuild();
    dataStoreProvider().put("buildGenerator", testNGBuildGenerator);
    dataStoreCache.put("target", target);
    dataStoreCache.put(Injectors.LOG_FOLDER.getInjector(), createLogFolder());
  }

  public void finish(DashboardConfig dashboard) {
    if (!dashboard.publishToDashboard()) return;
    testNGBuildGenerator.get().endBuild();
    testNGBuildGenerator.get().generate();
    generateReport(dashboard.getDashboardUrl());
  }

  public void generateReport(String ekamServerUrl) {
    new EkamReportPublisher(ekamServerUrl, getBuildOptions()).publish();
  }

  private BuildOptions getBuildOptions() {
    String target = (String) dataStoreCache.get("target").orElse("mobile");
    return new BuildOptions(target);
  }

  private String createLogFolder() {
    String runName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"));
    String folderName = String.format("logs/%s", runName);
    File logFolder = new File(folderName);
    if (!logFolder.exists()) {
      boolean mkdir = logFolder.mkdirs();
      if (!mkdir) {
        throw new RuntimeException(String.format("Folder: %s not created", folderName));
      }
    }

    return folderName;
  }
}
