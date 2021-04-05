package com.testvagrant.ekam.commons.mappers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.testvagrant.ekam.commons.file.FileExtension;
import com.testvagrant.ekam.commons.file.FileFinder;
import org.assertj.core.util.Files;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DataMapper {

  public <T> List<T> map(String fileName, Class<T> as) {
    FileFinder fileFinder = FileFinder.fileFinder();
    File file = fileFinder.find(fileName, FileExtension.JSON);
    Type listType = new TypeToken<ArrayList>() {}.getType();
    return new Gson().fromJson(Files.contentOf(file, Charset.defaultCharset()), listType);
  }
}
