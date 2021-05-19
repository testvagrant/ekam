package com.testvagrant.ekam.commons.cache;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.optimus.cache.DataCache;

import java.util.Objects;
import java.util.function.Predicate;

public class TestContextCache<Key, Value> extends DataCache<Key, Value> {

  private final LoadingCache<Key, Value> masterTestContextCache;
  private final LoadingCache<Key, Value> availableTestContextCache;
  private final LoadingCache<Key, Value> engagedTestContextCache;

  public TestContextCache() {
    masterTestContextCache = build(new TestContextCacheLoader());
    availableTestContextCache = build(new TestContextCacheLoader());
    engagedTestContextCache = build(new TestContextCacheLoader());
  }

  public synchronized void put(Key key, Value value) {
    masterTestContextCache.put(key, value);
    availableTestContextCache.put(key, value);
  }

  @Override
  public synchronized boolean isPresent(Predicate<Value> predicate) {
    return anyMatch(masterTestContextCache, predicate);
  }

  public synchronized boolean isPresent(Key key) {
    return anyMatch(masterTestContextCache, key);
  }

  public boolean isAvailable(Key key) {
    return anyMatch(availableTestContextCache, key);
  }

  public boolean isEngaged(Key key) {
    return anyMatch(engagedTestContextCache, key);
  }

  @Override
  public synchronized Value get(Predicate<Value> predicate) {
    throw new UnsupportedOperationException();
  }

  public synchronized Value get(Key key, boolean lockRecord) throws NoSuchKeyException {
    if (!isPresent(key)) {
      throw new NoSuchKeyException(String.format("%s is not found, please add it first", key));
    }
    Value value = getValue(availableTestContextCache, key);
    if (Objects.isNull(value))
      throw new NoSuchKeyException(
          String.format("%s is a shared key and is engaged, please release to access it", key));
    if (lockRecord) lock(key);
    return value;
  }

  public synchronized Value get(Key key) throws NoSuchKeyException {
    return get(key, false);
  }

  private Value getValue(LoadingCache<Key, Value> testContextCache, Key key) {
    return testContextCache.getIfPresent(key);
  }

  @Override
  protected synchronized void lock(Key key) {
    Value value = getValue(availableTestContextCache, key);
    engagedTestContextCache.put(key, value);
    availableTestContextCache.invalidate(key);
  }

  @Override
  public synchronized void release(Key key) {
    Value value = getValue(engagedTestContextCache, key);
    availableTestContextCache.put(key, value);
    engagedTestContextCache.invalidate(key);
  }

  @Override
  public long size() {
    return masterTestContextCache.size();
  }

  protected boolean anyMatch(LoadingCache<Key, Value> cache, Key key) {
    return cache.asMap().containsKey(key);
  }

  private class TestContextCacheLoader extends CacheLoader<Key, Value> {
    @Override
    public Value load(Key key) {
      return availableTestContextCache.getIfPresent(key);
    }
  }
}
