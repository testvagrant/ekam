package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.cache.DataSetsCache;
import com.testvagrant.ekam.commons.cache.DataSetsProvider;
import com.testvagrant.ekam.commons.cache.TestContextCache;
import com.testvagrant.ekam.commons.cache.TestContextCacheProvider;

public class CacheModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataSetsCache.class).toProvider(DataSetsProvider.class).asEagerSingleton();
    }
}
