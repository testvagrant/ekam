package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/** Entrypoint to GRPC Test */
@Guice(
    modules = {PropertyModule.class, LocaleModule.class, SwitchViewModule.class, GrpcModule.class})
@Test(groups = "grpc")
public class EkamGrpcTest {}
