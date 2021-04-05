package com.testvagrant.ekam.commons;

import com.google.inject.Module;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.HttpClientModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.SiteModule;
import com.testvagrant.ekam.web.modules.WebModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModulesLibrary {

  public List<Module> baseModules() {
    return Arrays.asList(
        new SiteModule(), new PropertyModule(), new LocaleModule(), new SwitchViewModule());
  }

  public List<Module> webModules() {
    List<Module> webModules = new ArrayList<>(baseModules());
    webModules.add(new WebModule());
    return webModules;
  }

  public List<Module> mobileModules() {
    List<Module> mobileModules = new ArrayList<>(baseModules());
    mobileModules.add(new MobileModule());
    return mobileModules;
  }

  public List<Module> apiModules() {
    List<Module> apiModules = new ArrayList<>(baseModules());
    apiModules.add(new HttpClientModule());
    apiModules.add(new GrpcModule());
    return apiModules;
  }
}
