package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/** TestBase for mobile tests with API access */
@Guice(
    modules = {
      PropertyModule.class,
      LocaleModule.class,
      SwitchViewModule.class,
      GrpcModule.class,
      MobileModule.class
    })
@Test(groups = "mobile")
public class EkamMobileApiTest {}
