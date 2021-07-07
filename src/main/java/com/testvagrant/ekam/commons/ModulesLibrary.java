package com.testvagrant.ekam.commons;

import com.google.inject.Module;
import com.testvagrant.ekam.api.modules.ApiHostsModule;
import com.testvagrant.ekam.api.modules.GrpcModule;
import com.testvagrant.ekam.commons.modules.InterceptorModule;
import com.testvagrant.ekam.commons.modules.SwitchViewModule;
import com.testvagrant.ekam.config.EkamConfigModule;
import com.testvagrant.ekam.mobile.modules.MobileModule;
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

  public List<Module> apiModules() {
    List<Module> mobileModules = new ArrayList<>(baseModules());
    mobileModules.add(new ApiHostsModule());
    mobileModules.add(new GrpcModule());
    return mobileModules;
  }

  public List<Module> baseModules() {
    List<Module> baseModules = new ArrayList<>();
    baseModules.add(new EkamConfigModule());
    baseModules.add(new InterceptorModule());
    return baseModules;
  }
}
