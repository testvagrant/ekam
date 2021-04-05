package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.annotations.SwitchView;
import com.testvagrant.ekam.commons.aspects.SwitchViewAspect;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class SwitchViewModule extends AbstractModule {

  @Override
  protected void configure() {
    bindInterceptor(any(), annotatedWith(SwitchView.class), new SwitchViewAspect());
  }
}
