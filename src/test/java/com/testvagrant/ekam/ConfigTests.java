package com.testvagrant.ekam;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.name.Named;
import com.testvagrant.ekam.api.modules.PropertyModule;
import com.testvagrant.ekam.commons.SystemProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigTests extends BaseTest {
    @Test
    public void apiConfigTest() {
        Assert.assertEquals("http://localhost", initConfig().getHost());
    }

}
