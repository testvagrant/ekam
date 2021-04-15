package com.testvagrant.ekam.commons.logs;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LogWriter {

  private static final String ROOT_FOLDER = "logs";

  public String createLogFolder() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String runName = now.format(formatter).replace(":", "-").replaceAll(" ", "-").trim();
    String folderName = String.format("%s/%s", ROOT_FOLDER, runName);
    File f = new File(folderName);
    boolean mkdir = f.mkdirs();
    if (!mkdir) {
      throw new RuntimeException(String.format("%s not created", folderName));
    }
    return folderName;
  }

  public static void writeLog(String logFolder, String testName, List<String> output) {
    String logFile = String.format("%s/%s.log", logFolder, testName);
    try {
      FileUtils.writeLines(new File(logFile), Charset.defaultCharset().displayName(), output);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
