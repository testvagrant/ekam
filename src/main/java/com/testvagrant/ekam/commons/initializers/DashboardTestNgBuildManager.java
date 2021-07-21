package com.testvagrant.ekam.commons.initializers;

import com.testvagrant.ekam.commons.cache.DataStoreCache;
import com.testvagrant.ekam.commons.injectors.Injectors;
import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.models.dashboard.BuildOptions;
import com.testvagrant.ekam.dashboard.publishers.EkamReportPublisher;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    dataStoreProvider().put("buildGenerator", testNGBuildGenerator);
    dataStoreCache.put("target", target);
    dataStoreCache.put(Injectors.LOG_FOLDER.getInjector(), createLogFolder());
  }

  public void finish(String dashboardUrl) {
    testNGBuildGenerator.get().endBuild();
    testNGBuildGenerator.get().generate();
    String target = (String) dataStoreCache.get("target").orElse("mobile");
    new EkamReportPublisher(dashboardUrl, new BuildOptions(target)).publish();
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
