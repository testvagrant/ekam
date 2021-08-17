package com.testvagrant.ekam.data;

import com.testvagrant.ekam.commons.cache.providers.DataSetsProvider;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;

public class CacheTestBase {

  @BeforeEach
  public void resetSingleton()
      throws SecurityException, NoSuchFieldException, IllegalArgumentException,
          IllegalAccessException {
    Field instance = DataSetsProvider.class.getDeclaredField("cacheProvider");
    instance.setAccessible(true);
    instance.set(null, null);
  }
}
