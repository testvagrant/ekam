package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.HttpClientModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import org.testng.annotations.Guice;

/**
 * Entrypoint to GRPC Test
 */
@Guice(modules = {PropertyModule.class,
        LocaleModule.class,
        SwitchViewModule.class,
        GrpcModule.class})
public class EkamGrpcTest {
}
