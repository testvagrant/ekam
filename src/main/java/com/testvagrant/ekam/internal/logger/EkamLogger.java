package com.testvagrant.ekam.internal.logger;

import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.util.ArrayList;
import java.util.List;

public class EkamLogger {

  private final String filePath;
  private final ConfigurationBuilder<BuiltConfiguration> builder;

  public EkamLogger(String filePath) {
    this.filePath = filePath;
    builder = ConfigurationBuilderFactory.newConfigurationBuilder();
  }

  public void init(String level, List<String> logTypes) {
    ConfigurationBuilder<BuiltConfiguration> builder =
        ConfigurationBuilderFactory.newConfigurationBuilder();
    RootLoggerComponentBuilder rootLogger = builder.newRootLogger(level);

    List<AppenderComponentBuilder> appenders = new ArrayList<>();

    if (logTypes.contains("console")) appenders.add(buildConsoleAppender());
    if (logTypes.contains("file")) appenders.add(buildFileAppender());

    appenders.parallelStream().forEach(builder::add);
    appenders.parallelStream()
        .forEach(appender -> rootLogger.add(builder.newAppenderRef(appender.getName())));
    builder.add(rootLogger);

    Configurator.reconfigure(builder.build());
  }

  private AppenderComponentBuilder buildFileAppender() {
    AppenderComponentBuilder file = builder.newAppender("logToFile", "File");
    file.addAttribute("fileName", filePath);
    file.add(layoutBuilder(builder));
    return file;
  }

  private AppenderComponentBuilder buildConsoleAppender() {
    AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
    console.add(layoutBuilder(builder));
    return console;
  }

  private LayoutComponentBuilder layoutBuilder(ConfigurationBuilder<BuiltConfiguration> builder) {
    LayoutComponentBuilder standard = builder.newLayout("PatternLayout");
    standard.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");
    return standard;
  }
}
