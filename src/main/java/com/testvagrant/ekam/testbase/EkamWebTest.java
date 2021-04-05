package com.testvagrant.ekam.testbase;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/** TestBase for web tests alone */
@Guice(modules = {})
@Test(groups = "web")
public class EkamWebTest {}
