package com.testvagrant.ekam.testbase;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/** TestBase for mobile tests alone */
@Guice()
@Test(groups = "mobile")
public class EkamMobileTest {}
