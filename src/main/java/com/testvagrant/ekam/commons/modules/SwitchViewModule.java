package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.annotations.AndroidSwitchView;
import com.testvagrant.ekam.commons.annotations.IOSSwitchView;
import com.testvagrant.ekam.commons.annotations.WebSwitchView;
import com.testvagrant.ekam.commons.aspects.AndroidSwitchViewAspect;
import com.testvagrant.ekam.commons.aspects.IOSSwitchViewAspect;
import com.testvagrant.ekam.commons.aspects.WebSwitchViewAspect;

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
