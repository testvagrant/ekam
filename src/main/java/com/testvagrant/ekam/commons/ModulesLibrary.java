package com.testvagrant.ekam.commons;

import com.google.inject.Module;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.api.modules.PropertyModule;
import com.testvagrant.ekam.commons.modules.InterceptorModule;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
import com.testvagrant.ekam.web.modules.WebModule;

import java.util.ArrayList;
import java.util.Arrays;
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
    return mobileModules;
  }

  public List<Module> apiModules() {
    List<Module> mobileModules = new ArrayList<>(baseModules());
    mobileModules.add(new PropertyModule());
    mobileModules.add(new GrpcModule());
    return mobileModules;
  }

  public List<Module> baseModules() {
    List<Module> baseModules = new ArrayList<>();
    baseModules.add(new LocaleModule());
    baseModules.add(new InterceptorModule());
    baseModules.add(new SwitchViewModule());
    return baseModules;
  }
}
