package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(
    modules = {PropertyModule.class, LocaleModule.class, SwitchViewModule.class, GrpcModule.class})
@Test(groups = "api")
/** Entry point to API tests with GRPC Support */
public class EkamApiTest {}
