package com.testvagrant.ekam.commons.locale;

import com.testvagrant.ekam.commons.cache.LocaleCache;

import static com.testvagrant.ekam.commons.cache.providers.LocaleProvider.localeProvider;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

@SuppressWarnings({"unchecked", "rawtypes"})
public class LocaleClient {

  private final LocaleCache localCache;

  public LocaleClient() {
    this.localCache = localeProvider();
  }

  public synchronized <T> T getLocale(String key, Class<T> tClass) {
    T t = (T) localCache.get(key, tClass);
    ekamLogger().info("[Locale Client] Found value {} for key {}", t, key);
    return t;
  }
}
