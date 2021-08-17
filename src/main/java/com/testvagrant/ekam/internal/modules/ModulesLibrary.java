package com.testvagrant.ekam.internal.modules;

import com.google.inject.Module;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.reports.modules.InterceptorModule;
import com.testvagrant.ekam.web.modules.WebModule;

import java.util.ArrayList;
import java.util.List;

public class ModulesLibrary {

  public List<Module> webModules() {
    List<Module> webModules = new ArrayList<>(baseModules());
    webModules.add(new WebModule());
    webModules.add(new SwitchViewModule());
    return webModules;
  }

  public List<Module> mobileModules() {
    List<Module> mobileModules = new ArrayList<>(baseModules());
    mobileModules.add(new MobileModule());
    mobileModules.add(new SwitchViewModule());
    return mobileModules;
  }

  public List<Module> baseModules() {
    List<Module> baseModules = new ArrayList<>();
    baseModules.add(new EkamConfigModule());
    baseModules.add(new InterceptorModule());
    return baseModules;
  }
}
