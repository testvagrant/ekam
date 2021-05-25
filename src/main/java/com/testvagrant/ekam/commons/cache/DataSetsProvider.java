package com.testvagrant.ekam.commons.cache;

import com.google.gson.internal.LinkedTreeMap;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.optimus.commons.filehandlers.FileFinder;
import com.testvagrant.optimus.commons.filehandlers.GsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DataSetsProvider implements Provider<DataSetsCache> {

  public DataSetsCache load() {
    String dataSetsRootPath =
        Objects.requireNonNull(this.getClass().getClassLoader().getResource("dataSets")).getPath();
    String envPath = Paths.get(dataSetsRootPath, SystemProperties.ENV).toString();
    List<File> files = FileFinder.fileFinder(envPath).find(".json");
    DataSetsCache dataSetsCache = new DataSetsCache();
    files.forEach(
        file -> {
          try {
            transformAsMap(dataSetsCache, file);
          } catch (Exception e) {
            transformAsList(dataSetsCache, file);
          }
        });
    return dataSetsCache;
  }

  private <T> void transformAsMap(DataSetsCache dataSetsCache, File file)
      throws FileNotFoundException {
    Map<String, T> dataSetMap =
        GsonParser.toInstance().deserialize(new FileReader(file), Map.class);
    dataSetMap.forEach(
        (key, value) -> {
          String cacheKey = key.toLowerCase();
          if (value instanceof ArrayList) {
            List a = (List) value;
            IntStream.range(0, a.size())
                .forEach(
                    range -> {
                      dataSetsCache.put(cacheKey + "_" + range, (LinkedTreeMap) a.get(range));
                    });
          } else {
            dataSetsCache.put(cacheKey, (LinkedTreeMap) value);
          }
        });
  }

  private void transformAsList(DataSetsCache dataSetsCache, File file) {
    try {
      List<LinkedTreeMap> dataListMap =
          GsonParser.toInstance().deserialize(new FileReader(file), List.class);
      IntStream.range(0, dataListMap.size())
          .forEach(
              range -> {
                dataSetsCache.put(
                    file.getName().toLowerCase() + "_" + range, dataListMap.get(range));
              });
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    }
  }

  @Override
  public DataSetsCache get() {
    return load();
  }
}
