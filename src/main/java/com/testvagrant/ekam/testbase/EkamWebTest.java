package com.testvagrant.ekam.testbase;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.HttpClientModule;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.internal.Launcher;
import com.testvagrant.ekam.web.modules.PageModule;
import com.testvagrant.ekam.web.modules.SiteModule;
import com.testvagrant.ekam.web.modules.WebModule;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * TestBase for web tests alone
 */
@Guice(modules = {})
@Test(groups = "web")
public class EkamWebTest {
}
