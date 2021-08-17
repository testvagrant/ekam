package com.testvagrant.ekam.commons.data;

import com.testvagrant.ekam.commons.cache.DataStoreCache;

import static com.testvagrant.ekam.commons.cache.providers.DataStoreProvider.dataStoreProvider;

@SuppressWarnings({"unchecked", "rawtypes"})
public class DataStoreClient {

  private final DataStoreCache dataStoreCache;

  public DataStoreClient() {
    this.dataStoreCache = dataStoreProvider();
  }

  public <T> T getValue(String key) {
    return (T) dataStoreCache.get(key);
  }
}
