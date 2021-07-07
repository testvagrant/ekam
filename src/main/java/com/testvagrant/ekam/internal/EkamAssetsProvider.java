package com.testvagrant.ekam.internal;

import com.testvagrant.ekam.commons.cache.DataStoreCache;
import com.testvagrant.ekam.commons.cache.providers.DataStoreProvider;

@SuppressWarnings("unchecked")
public class EkamAssetsProvider {
  private static DataStoreCache<Object> dataStore;
  private static EkamAssetsProvider ekamAssetsProvider;

  private EkamAssetsProvider() {}

  public static EkamAssetsProvider ekamAssets() {
    if (ekamAssetsProvider == null) {
      synchronized (DataStoreProvider.class) {
        if (ekamAssetsProvider == null) {
          ekamAssetsProvider = new EkamAssetsProvider();
          dataStore = ekamAssetsProvider.get();
        }
      }
    }
    return ekamAssetsProvider;
  }

  public <T> T get(String key) {
    return (T)
        dataStore.get(key).orElseThrow(() -> new RuntimeException("Key not found => " + key));
  }

  public <T> void put(String key, T value) {
    dataStore.put(key, value);
  }

  private DataStoreCache<Object> get() {
    return new DataStoreCache<>();
  }

  public static class EkamAssets {
    public static class Remote {
      public static final String TARGET_DETAILS = "target_details";
      public static final String APP_UPLOAD_URL = "app_upload_url";
    }
  }
}
