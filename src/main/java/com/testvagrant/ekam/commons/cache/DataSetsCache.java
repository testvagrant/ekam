package com.testvagrant.ekam.commons.cache;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.internal.LinkedTreeMap;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.optimus.cache.DataCache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public class DataSetsCache extends DataCache<String, LinkedTreeMap> {

  private final LoadingCache<String, LinkedTreeMap> masterTestContextCache;
  private final LoadingCache<String, LinkedTreeMap> availableTestContextCache;
  private final LoadingCache<String, LinkedTreeMap> engagedTestContextCache;

  public DataSetsCache() {
    masterTestContextCache = build(new TestContextCacheLoader());
    availableTestContextCache = build(new TestContextCacheLoader());
    engagedTestContextCache = build(new TestContextCacheLoader());
  }

  public synchronized void put(String key, LinkedTreeMap value) {
    masterTestContextCache.put(key, value);
    availableTestContextCache.put(key, value);
  }

  @Override
  public synchronized boolean isPresent(Predicate<LinkedTreeMap> predicate) {
    return anyMatch(masterTestContextCache, predicate);
  }

  public synchronized boolean isPresent(String key) {
    return anyMatch(masterTestContextCache, key);
  }

  public boolean isAvailable(String key) {
    return anyMatch(availableTestContextCache, key);
  }

  public boolean isEngaged(String key) {
    return anyMatch(engagedTestContextCache, key);
  }

  @Override
  public synchronized LinkedTreeMap get(Predicate<LinkedTreeMap> predicate) {
    throw new UnsupportedOperationException();
  }

  public synchronized Object get(String key, boolean lockRecord) throws NoSuchKeyException {
    if (!isPresent(key)) {
      throw new NoSuchKeyException(String.format("%s is not found, please add it first", key));
    }
    Object value = getObject(availableTestContextCache, key);
    if (Objects.isNull(value))
      throw new NoSuchKeyException(
          String.format("%s is a shared key and is engaged, please release to access it", key));
    if (lockRecord) lock(key);
    return value;
  }

  public synchronized Object get(String key) throws NoSuchKeyException {
    return get(key, false);
  }

  private LinkedTreeMap getObject(
      LoadingCache<String, LinkedTreeMap> testContextCache, String key) {
    String finalKey = getFinalKey(testContextCache, key);
    return testContextCache.getIfPresent(finalKey);
  }

  @Override
  protected synchronized void lock(String key) {
    String finalKey = getFinalKey(availableTestContextCache, key);
    LinkedTreeMap value = getObject(availableTestContextCache, finalKey);
    engagedTestContextCache.put(finalKey, value);
    availableTestContextCache.invalidate(finalKey);
  }

  @Override
  public synchronized void release(String key) {
    String finalKey = getFinalKey(engagedTestContextCache, key);
    LinkedTreeMap value = getObject(engagedTestContextCache, finalKey);
    availableTestContextCache.put(finalKey, value);
    engagedTestContextCache.invalidate(finalKey);
  }

  public synchronized void release(
      String key, Predicate<Map.Entry<String, LinkedTreeMap>> predicate) {
    String finalKey = getFinalKey(engagedTestContextCache, key, predicate);
    LinkedTreeMap value = getObject(engagedTestContextCache, finalKey);
    if (!finalKey.isEmpty()) {
      availableTestContextCache.put(finalKey, value);
      engagedTestContextCache.invalidate(finalKey);
    }
  }

  public synchronized void release(Predicate<Map.Entry<String, LinkedTreeMap>> predicate) {
    String finalKey = getFinalKey(engagedTestContextCache, predicate);
    LinkedTreeMap value = getObject(engagedTestContextCache, finalKey);
    if (!finalKey.isEmpty()) {
      availableTestContextCache.put(finalKey, value);
      engagedTestContextCache.invalidate(finalKey);
    }
  }

  @Override
  public long size() {
    return masterTestContextCache.size();
  }

  protected boolean anyMatch(LoadingCache<String, LinkedTreeMap> cache, String key) {
    boolean fullFledgedKey = cache.asMap().containsKey(key);
    if (fullFledgedKey) {
      return true;
    } else {
      return cache.asMap().keySet().stream().anyMatch(k -> k.startsWith(key));
    }
  }

  protected String getFinalKey(LoadingCache<String, LinkedTreeMap> cache, String key) {
    boolean fullFledgedKey = cache.asMap().containsKey(key);
    if (fullFledgedKey) {
      return key;
    } else {
      return cache.asMap().keySet().stream().filter(k -> k.startsWith(key)).findFirst().orElse(key);
    }
  }

  protected <T> String getFinalKey(
      LoadingCache<String, LinkedTreeMap> cache,
      String key,
      Predicate<Map.Entry<String, LinkedTreeMap>> predicate) {
    boolean fullFledgedKey = cache.asMap().containsKey(key);
    if (fullFledgedKey) {
      return key;
    } else {
      String finalKey = key;
      Optional<Map.Entry<String, LinkedTreeMap>> finalEntry =
          cache.asMap().entrySet().stream()
              .filter(entry -> entry.getKey().startsWith(finalKey))
              .filter(predicate)
              .findFirst();
      if (finalEntry.isPresent()) {
        key = finalEntry.get().getKey();
      }
      return key;
    }
  }

  protected <T> String getFinalKey(
      LoadingCache<String, LinkedTreeMap> cache,
      Predicate<Map.Entry<String, LinkedTreeMap>> predicate) {
    String finalKey = "";
    Optional<Map.Entry<String, LinkedTreeMap>> finalEntry =
        cache.asMap().entrySet().stream().filter(predicate).findFirst();
    if (finalEntry.isPresent()) {
      finalKey = finalEntry.get().getKey();
    }
    return finalKey;
  }

  private class TestContextCacheLoader extends CacheLoader<String, LinkedTreeMap> {
    @Override
    public LinkedTreeMap load(String key) {
      String finalKey =
          availableTestContextCache.asMap().keySet().stream()
              .filter(k -> k.startsWith(key))
              .findFirst()
              .orElse(key);
      return availableTestContextCache.getIfPresent(finalKey);
    }
  }
}
