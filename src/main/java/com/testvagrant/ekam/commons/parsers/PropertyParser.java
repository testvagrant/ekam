package com.testvagrant.ekam.commons.parsers;

import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.exceptions.InvalidPropertyFileException;
import com.testvagrant.optimus.commons.filehandlers.FileFinder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import static com.testvagrant.optimus.commons.filehandlers.ResourcePaths.MAIN_RESOURCES;
import static com.testvagrant.optimus.commons.filehandlers.ResourcePaths.TEST_RESOURCES;

public class PropertyParser {

  public Properties loadProperties(String fileName) {
    try {
      AtomicReference<String> propertiesFilePath = new AtomicReference<>("");

      for (String path : validPaths) {
        File file = FileFinder.fileFinder(path).find(fileName, ".properties");
        if (file != null && file.exists()) {
          propertiesFilePath.set(file.getPath());
          break;
        }
      }

      if (propertiesFilePath.get().isEmpty()) {
        throw new InvalidPropertyFileException(fileName);
      }

      Properties envProps = new Properties();
      envProps.load(new FileReader(new File(propertiesFilePath.get())));
      return envProps;
    } catch (IOException | NullPointerException e) {
      throw new InvalidPropertyFileException(SystemProperties.WEBSITE_CONFIG);
    }
  }

  private static final String[] validPaths = new String[] {TEST_RESOURCES, MAIN_RESOURCES};
}
