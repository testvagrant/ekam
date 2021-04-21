package com.testvagrant.ekam.testbase;

import com.testvagrant.ekam.commons.modules.LocaleModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = {LocaleModule.class})
/** Entry point to DB tests */
@Test(groups = "db")
public class EkamDBTest {}
