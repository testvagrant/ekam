package com.testvagrant.ekam;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.testvagrant.ekam.api.modules.PropertyModule;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigEnvTests extends BaseTest {

    @Test
    public void apiConfigTestForEnv() {
        System.setProperty("apiConfig", "qaConfig");
        Assert.assertEquals("http://qa", initConfig().getHost());
    }
}
