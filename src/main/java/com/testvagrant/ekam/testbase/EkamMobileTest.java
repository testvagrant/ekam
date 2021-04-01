package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.HttpClientModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 * TestBase for mobile tests alone
 */
@Guice(modules = {})
@Test(groups = "mobile")
public class EkamMobileTest {
}
