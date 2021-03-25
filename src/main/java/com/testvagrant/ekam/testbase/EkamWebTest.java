package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.HttpClientModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.web.modules.PageModule;
import com.testvagrant.ekam.web.modules.SiteModule;
import com.testvagrant.ekam.web.modules.WebModule;
import org.testng.annotations.Guice;

/**
 * TestBase for web tests alone
 */
@Guice(modules = {PropertyModule.class, LocaleModule.class, HttpClientModule.class})
public class EkamWebTest {
}
