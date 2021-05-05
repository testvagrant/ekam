package com.testvagrant.ekam.commons;

import com.google.inject.Module;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.WebModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModulesLibrary {

  public List<Module> webModules() {
    List<Module> webModules = new ArrayList<>(baseModules());
    webModules.add(new WebModule());
    return webModules;
  }

  public List<Module> mobileModules() {
    List<Module> mobileModules = new ArrayList<>(baseModules());
    mobileModules.add(new MobileModule());
    mobileModules.add(new SwitchViewModule());
    return mobileModules;
  }

  private List<Module> baseModules() {
    return Collections.singletonList(new LocaleModule());
  }
}
