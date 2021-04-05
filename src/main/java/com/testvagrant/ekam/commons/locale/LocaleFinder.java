package com.testvagrant.ekam.commons.locale;

import com.google.gson.Gson;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.file.FileExtension;
import com.testvagrant.ekam.commons.file.FileFinder;
import com.testvagrant.ekam.commons.models.Locale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LocaleFinder implements Provider<LocaleFinder> {

  public static <T> T findLocale(String fileName, Class<T> tClass) {
    String localePath = LocaleFinder.class.getClassLoader().getResource("locale").getPath();
    return findFile(fileName, tClass, localePath);
  }

  public static <T> T findLocale(String path, String fileName, Class<T> tClass) {
    String localePath = LocaleFinder.class.getClassLoader().getResource("locale/" + path).getPath();
    return findFile(fileName, tClass, localePath);
  }

  private static <T> T findFile(String fileName, Class<T> tClass, String localePath) {
    FileFinder fileFinder = FileFinder.fileFinder(localePath);
    File file = fileFinder.find(fileName, FileExtension.JSON);
    try {
      Locale locale = new Gson().fromJson(new FileReader(file.getPath()), Locale.class);
      return locale.getLocale(tClass);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Cannot find locale");
  }

  @Override
  public LocaleFinder get() {
    return new LocaleFinder();
  }
}
