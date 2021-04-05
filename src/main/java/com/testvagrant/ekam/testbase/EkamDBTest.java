package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = {PropertyModule.class, LocaleModule.class})
/** Entry point to DB tests */
@Test(groups = "db")
public class EkamDBTest {}
