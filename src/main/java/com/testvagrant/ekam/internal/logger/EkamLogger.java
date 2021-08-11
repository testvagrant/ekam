package com.testvagrant.ekam.internal.logger;

import com.testvagrant.ekam.config.models.LogConfig;
import org.apache.log4j.*;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EkamLogger {

  private final String filePath;
  private final LogConfig logConfig;

  public EkamLogger(String filePath, LogConfig logConfig) {
    this.filePath = filePath;
    this.logConfig = logConfig;
  }

  public Logger init() {
    String randomName = UUID.randomUUID().toString().replace("-", "");
    Logger logger = Logger.getLogger(randomName);

    List<WriterAppender> appenders = new ArrayList<>();
    List<String> logTypes = logConfig.getTypes();

    if (logTypes.contains("file")) appenders.add(buildRollingFileAppender(randomName));
    if (logTypes.contains("console")) appenders.add(buildConsoleAppender(randomName));
    logger.setAdditivity(false);
    appenders.forEach(logger::addAppender);
    return logger;
  }

  private RollingFileAppender buildRollingFileAppender(String name) {
    RollingFileAppender appender = new RollingFileAppender();

    String pattern =
        logConfig.getFileLogPattern().isEmpty()
            ? logConfig.getPattern()
            : logConfig.getFileLogPattern();

    String level =
        logConfig.getFileLogLevel().isEmpty()
            ? logConfig.getLogLevel()
            : logConfig.getFileLogLevel();

    appender.setName(name);
    appender.setLayout(new PatternLayout(pattern));
    appender.setFile(filePath);
    appender.setAppend(true);
    appender.setImmediateFlush(true);
    appender.setMaxFileSize(logConfig.getMaxFileSize());
    appender.setMaxBackupIndex(10);
    appender.activateOptions();
    appender.setThreshold(Level.toLevel(level));
    return appender;
  }

  private ConsoleAppender buildConsoleAppender(String name) {
    ConsoleAppender appender = new ConsoleAppender();
    String pattern =
        logConfig.getConsoleLogPattern().isEmpty()
            ? logConfig.getPattern()
            : logConfig.getConsoleLogPattern();

    String level =
        logConfig.getConsoleLogLevel().isEmpty()
            ? logConfig.getLogLevel()
            : logConfig.getConsoleLogLevel();

    appender.setName(name);
    appender.setWriter(new OutputStreamWriter(System.out));
    appender.setLayout(new PatternLayout(pattern));
    appender.setThreshold(Level.toLevel(level));
    return appender;
  }
}
