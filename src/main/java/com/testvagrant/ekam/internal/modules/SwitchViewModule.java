package com.testvagrant.ekam.internal.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.mobile.annotations.AndroidSwitchView;
import com.testvagrant.ekam.mobile.annotations.IOSSwitchView;
import com.testvagrant.ekam.mobile.aspects.AndroidSwitchViewAspect;
import com.testvagrant.ekam.mobile.aspects.IOSSwitchViewAspect;
import com.testvagrant.ekam.web.annotations.WebSwitchView;
import com.testvagrant.ekam.web.aspects.WebSwitchViewAspect;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class SwitchViewModule extends AbstractModule {

  @Override
  protected void configure() {
    bindInterceptor(any(), annotatedWith(AndroidSwitchView.class), new AndroidSwitchViewAspect());
    bindInterceptor(any(), annotatedWith(IOSSwitchView.class), new IOSSwitchViewAspect());
    bindInterceptor(any(), annotatedWith(WebSwitchView.class), new WebSwitchViewAspect());
  }
}
