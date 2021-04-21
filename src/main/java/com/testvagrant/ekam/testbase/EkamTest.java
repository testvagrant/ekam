package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.WebModule;
import org.testng.annotations.Guice;

/** A single testbase to cover web/mobile/api tests for an application */
@Guice(
    modules = {
      PropertyModule.class,
      LocaleModule.class,
      SwitchViewModule.class,
      GrpcModule.class,
      WebModule.class,
      MobileModule.class
    })
public class EkamTest {}
