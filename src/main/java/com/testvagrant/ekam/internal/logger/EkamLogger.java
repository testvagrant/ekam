package com.testvagrant.ekam.internal.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class EkamLogger {

  private final String filePath;

  public EkamLogger(String filePath) {
    this.filePath = filePath;
  }

  public void init() {
    ConfigurationBuilder<BuiltConfiguration> builder =
        ConfigurationBuilderFactory.newConfigurationBuilder();
    AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
    builder.add(console);

    AppenderComponentBuilder file = builder.newAppender("logToFile", "File");
    file.addAttribute("fileName", filePath);
    builder.add(file);

    LayoutComponentBuilder standard = builder.newLayout("PatternLayout");
    standard.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");
    console.add(standard);
    file.add(standard);

    RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.INFO);
    rootLogger.add(builder.newAppenderRef("stdout"));
    rootLogger.add(builder.newAppenderRef("logToFile"));
    builder.add(rootLogger);

    Configurator.reconfigure(builder.build());
  }
}
