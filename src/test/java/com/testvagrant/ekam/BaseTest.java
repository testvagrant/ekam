package com.testvagrant.ekam;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.testvagrant.ekam.api.modules.PropertyModule;

public class BaseTest {
    protected Config initConfig() {
        Injector injector = Guice.createInjector(new PropertyModule());
        return  injector.getInstance(Config.class);

    }
}
