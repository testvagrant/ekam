package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.HttpClientModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.web.modules.WebModule;
import org.testng.annotations.Guice;

/**
 * TestBase for web tests with API access
 */
@Guice(modules = {PropertyModule.class,
        LocaleModule.class,
        SwitchViewModule.class,
        HttpClientModule.class,
        GrpcModule.class,
        WebModule.class})
public class EkamWebApiTest {
}
