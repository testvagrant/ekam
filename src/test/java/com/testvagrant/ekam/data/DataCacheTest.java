package com.testvagrant.ekam.data;

import com.testvagrant.ekam.commons.cache.DataCache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DataCacheTest {

  DataCache<String> stringDataCache;
  DataCache<Object> objectDataCache;

  @BeforeEach
  public void setupDataCache() {
    stringDataCache = new DataCache<>();
    objectDataCache = new DataCache<>();
  }

  @AfterEach
  public void clearCache() {
    stringDataCache.invalidateAll();
    objectDataCache.invalidateAll();
  }

  @Test
  public void dataCacheShouldLoadStringValues() {
    stringDataCache.put("key", "value");
    Optional<String> key = stringDataCache.get("key");
    assertThat(key.isPresent()).isTrue();
    assertThat(key.get()).isEqualTo("value");
  }

  @Test
  public void dataCacheShouldLoadCustomObjectAsValues() {
    Object obj =
        new Object() {
          final String name = "objectName";
        };
    objectDataCache.put("objectKey", obj);
    Optional<Object> objectKey = objectDataCache.get("objectKey");
    assertThat(objectKey.isPresent()).isTrue();
    assertThat(objectKey.get()).isEqualTo(obj);
  }

  @Test
  public void dataCacheShouldLoadMapAsValues() {
    Map<String, String> dataMap = new HashMap<>();
    objectDataCache.put("mapKey", dataMap);
    Optional<Object> mapKey = objectDataCache.get("mapKey");
    assertThat(mapKey.isPresent()).isTrue();
    assertThat(mapKey.get()).isEqualTo(dataMap);
  }

  @Test
  public void dataCacheShouldReturnEmptyIfIsNotPresent() {
    stringDataCache.put("key", "value");
    Optional<String> invalidKey = stringDataCache.get("invalidKey");
    assertThat(invalidKey.isPresent()).isFalse();
  }
}
