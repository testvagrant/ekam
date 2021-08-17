package com.testvagrant.ekam.commons.locale;

import com.testvagrant.ekam.commons.cache.LocaleCache;

import static com.testvagrant.ekam.commons.cache.providers.LocaleProvider.localeProvider;

@SuppressWarnings({"unchecked", "rawtypes"})
public class LocaleClient {

  private final LocaleCache localCache;

  public LocaleClient() {
    this.localCache = localeProvider();
  }

  public synchronized <T> T getLocale(String key, Class<T> tClass) {
    return (T) localCache.get(key, tClass);
  }
}
