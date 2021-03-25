package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.locale.LocaleFinder;

public class LocaleModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LocaleFinder.class).asEagerSingleton();
    }
}
