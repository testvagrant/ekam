package com.testvagrant.ekam.internal.injectors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.commons.path.PathBuilder;
import com.testvagrant.ekam.config.models.EkamConfig;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTestContext;
import com.testvagrant.ekam.internal.logger.EkamLogger;
import com.testvagrant.ekam.internal.modules.EkamTestModule;
import com.testvagrant.ekam.internal.modules.LoggerModule;
import com.testvagrant.ekam.internal.modules.StepRecorderModule;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.testvagrant.ekam.commons.Toggles.LOGS;
import static com.testvagrant.ekam.commons.io.FileUtilities.fileUtils;
import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;

public class EkamInjector {

  protected final EkamTest ekamTest;
  protected final EkamTestContext testContext;
  private final EkamConfig ekamConfig;
  private Logger logger;

  public EkamInjector(EkamTest ekamTest, EkamConfig ekamConfig) {
    this.ekamTest = ekamTest;
    this.ekamConfig = ekamConfig;
    testContext = init();
  }

  protected Injector createInjector(List<Module> modules) {
    Injector injector = Guice.createInjector(new EkamTestModule(ekamTest));
    injectorsCache().put(injector);

    modules.addAll(
        Arrays.asList(
            new ApiHostsModule(),
            new GrpcModule(),
            new StepRecorderModule(testContext),
            new LoggerModule(logger)));
    injector = injector.createChildInjector(modules);
    injectorsCache().put(injector);
    return injector;
  }

  protected void createTargetJson() {
    String targets = new GsonParser().serialize(testContext.getTargets());
    File testFolder = new File(new PathBuilder(testContext.getTestDirectory()).toString());

    if (!testFolder.exists()) {
      fileUtils().createDirectory(testFolder.getAbsolutePath());
    }

    String fileName =
        new PathBuilder(testContext.getTestDirectory()).append("target.json").toString();
    fileUtils().writeFile(fileName, targets);
  }

  private EkamTestContext init() {
    String testDirectoryPath =
        ResourcePaths.getTestPath(ekamTest.getFeature(), ekamTest.getScenario());

    EkamTestContext context =
        EkamTestContext.builder().ekamTest(ekamTest).testDirectory(testDirectoryPath).build();

    File testDirectory = new File(String.valueOf(new PathBuilder(testDirectoryPath)));
    if (!testDirectory.exists()) fileUtils().createDirectory(testDirectory.getAbsolutePath());

    initLogger(testDirectoryPath);
    initScreenShotDirectory(testDirectoryPath);

    return context;
  }

  private void initScreenShotDirectory(String testDirectory) {
    String path = new PathBuilder(testDirectory).append("screenshots").toString();
    fileUtils().createDirectory(path);
  }

  private void initLogger(String testDirectory) {
    if (LOGS.isOn()) {
      String logDirectory = new PathBuilder(testDirectory).append("logs").toString();
      fileUtils().createDirectory(logDirectory);

      String logFilePath = new PathBuilder(logDirectory).append("logs.log").toString();
      logger = new EkamLogger(logFilePath, ekamConfig.getLogConfig()).init();
    }
  }
}
