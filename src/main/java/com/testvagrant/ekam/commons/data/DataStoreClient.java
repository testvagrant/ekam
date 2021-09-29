package com.testvagrant.ekam.commons.data;

import com.testvagrant.ekam.commons.cache.DataStoreCache;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

@SuppressWarnings({"unchecked", "rawtypes"})
public class DataStoreClient {

  private final DataStoreCache dataStoreCache;

  public DataStoreClient() {
    this.dataStoreCache = dataStoreProvider();
  }

  public <T> T getValue(String key) {
    T t = (T) dataStoreCache.get(key);
    ekamLogger().info("[Data Store Client] Found value {} for key {}", t, key);
    return t;
  }
}
