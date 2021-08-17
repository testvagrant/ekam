package com.testvagrant.ekam.commons.data;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.testvagrant.ekam.commons.cache.DataSetsCache;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.commons.io.ResourcePaths;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.testvagrant.ekam.commons.cache.providers.DataSetsProvider.dataSetsProvider;
import static com.testvagrant.ekam.commons.constants.ResourcesConfigKeys.DATASETS;

@SuppressWarnings({"unchecked", "rawtypes"})
public class DataSetsClient {

  private final DataSetsCache dataSets;

  public DataSetsClient() {
    this.dataSets = dataSetsProvider();
  }

  public <T> T getValue(String key, Class<T> tClass) {
    if (tClass.getTypeName().toLowerCase().contains("list")) {
      try {
        return (T)
            this.dataSets
                .get(key.toLowerCase())
                .orElseThrow(() -> new RuntimeException(key + " not found"));
      } catch (Throwable throwable) {
        throw new RuntimeException(throwable);
      }
    }
    return getValue(key, tClass, false);
  }

  public <T> List<T> getListValue(String key, Class<T> tClass) {
    try {
      List<LinkedTreeMap> values =
          (List<LinkedTreeMap>)
              this.dataSets
                  .get(key.toLowerCase())
                  .orElseThrow(() -> new RuntimeException(key + " not found"));

      GsonParser gsonParser = new GsonParser();
      return (List<T>)
          values.stream()
              .map(value -> gsonParser.deserialize(value, tClass))
              .collect(Collectors.toList());

    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    }
  }

  public List<String> getListValue(String key) {
    return getValue(key, new TypeToken<List<String>>() {}.getType());
  }

  public <T> T getValue(String key, Type tType) {
    return getValue(key, tType, false);
  }

  public <T> T getValue(String key, Class<T> tClass, boolean lock) {
    String value = getJsonString(key, lock);
    return deserialize(value, tClass);
  }

  public <T> T getValue(String key, Type type, boolean lock) {
    String value = getJsonString(key, lock);
    return new Gson().fromJson(value, type);
  }

  public <T> T findFirst(List<T> dataList) {
    if (dataList.isEmpty()) return null;
    return dataList.stream().findFirst().orElse(null);
  }

  public <T> T findAny(List<T> dataList) {
    if (dataList.isEmpty()) return null;
    return dataList.stream().findAny().orElse(null);
  }

  public <T> List<T> findByPredicate(List<T> dataList, Predicate predicate) {
    if (dataList.isEmpty()) return null;
    return (List<T>) dataList.stream().filter(predicate).collect(Collectors.toList());
  }

  public void release(String key, Predicate<Map.Entry<String, LinkedTreeMap>> entryPredicate) {
    dataSets.release(key, entryPredicate);
  }

  public void release(Predicate<Map.Entry<String, LinkedTreeMap>> entryPredicate) {
    dataSets.release(entryPredicate);
  }

  private <T> String getJsonString(T type) {
    return new GsonParser().serialize(type);
  }

  private <T> String getJsonString(String key) {
    return getJsonString(key, false);
  }

  private <T> String getJsonString(String key, boolean lock) {
    try {
      T type =
          (T)
              dataSets
                  .get(key.toLowerCase(), lock)
                  .orElseThrow(
                      () -> {
                        String env =
                            System.getProperty(
                                DATASETS.DATASETS_ENV, System.getProperty("env", ""));

                        String envMessage =
                            Objects.isNull(env) || env.isEmpty()
                                ? ""
                                : String.format("for Env: '%s'", env);

                        String dataSetsDir = System.getProperty("datasets.dir");

                        String dataSetsDirMessage =
                            String.format(
                                "%s/%s",
                                ResourcePaths.TEST_RESOURCES,
                                Objects.isNull(dataSetsDir) || dataSetsDir.isEmpty()
                                    ? ""
                                    : dataSetsDir);
                        return new RuntimeException(
                            String.format(
                                "'%s' key not found in data_sets %s.\nFiles searched under directory: '%s'",
                                key, envMessage, dataSetsDirMessage));
                      });
      return new GsonParser().serialize(type);
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable);
    }
  }

  private <T> T deserialize(String value, Class<T> tClass) {
    return new GsonParser().deserializeFromString(value, tClass);
  }
}
