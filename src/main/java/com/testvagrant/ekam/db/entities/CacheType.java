package com.testvagrant.ekam.db.entities;

public enum CacheType {
  REDIS("redis");

  private final String cacheName;

  CacheType(String cacheName) {
    this.cacheName = cacheName;
  }

  public String getCacheName() {
    return cacheName;
  }
}
