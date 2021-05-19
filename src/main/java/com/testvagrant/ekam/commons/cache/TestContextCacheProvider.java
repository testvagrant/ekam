package com.testvagrant.ekam.commons.cache;

@SuppressWarnings("rawtypes")
public class TestContextCacheProvider {
  private static TestContextCache testContextCacheProvider;

  private TestContextCacheProvider() {}

  public static TestContextCache getInstance() {
    if (testContextCacheProvider == null) {
      synchronized (TestContextCacheProvider.class) {
        if (testContextCacheProvider == null) {
          testContextCacheProvider = new TestContextCacheProvider().get();
        }
      }
    }
    return testContextCacheProvider;
  }

  public TestContextCache get() {
    return new TestContextCache();
  }
}
