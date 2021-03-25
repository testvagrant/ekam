package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.HttpClientModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import org.testng.annotations.Guice;

@Guice(modules = {PropertyModule.class,
        LocaleModule.class,
        SwitchViewModule.class,
        HttpClientModule.class,
        GrpcModule.class})
/**
 * Entry point to API tests with GRPC Support
 */
public class EkamApiTest {

}
