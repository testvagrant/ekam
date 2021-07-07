package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.testvagrant.ekam.commons.annotations.AndroidSwitchView;
import com.testvagrant.ekam.commons.annotations.IOSSwitchView;
import com.testvagrant.ekam.commons.annotations.WebSwitchView;
import com.testvagrant.ekam.commons.aspects.AndroidSwitchViewAspect;
import com.testvagrant.ekam.commons.aspects.IOSSwitchViewAspect;
import com.testvagrant.ekam.commons.aspects.WebSwitchViewAspect;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class SwitchViewModule extends AbstractModule {
  @Inject(optional = true)
  AppiumDriver<MobileElement> mobileElementAppiumDriver;

  @Override
  protected void configure() {
    bindInterceptor(
        any(),
        annotatedWith(AndroidSwitchView.class),
        new AndroidSwitchViewAspect(mobileElementAppiumDriver));
    bindInterceptor(any(), annotatedWith(IOSSwitchView.class), new IOSSwitchViewAspect());
    bindInterceptor(any(), annotatedWith(WebSwitchView.class), new WebSwitchViewAspect());
  }
}
