package com.testvagrant.ekam.commons.aspects;

import com.testvagrant.ekam.commons.annotations.AndroidSwitchView;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;

@SuppressWarnings("unchecked")
public class AndroidSwitchViewAspect implements MethodInterceptor {

  private AppiumDriver<MobileElement> appiumDriver;

  public AndroidSwitchViewAspect(AppiumDriver<MobileElement> appiumDriver) {
    this.appiumDriver = appiumDriver;
  }

  @Override
  public Object invoke(MethodInvocation methodInvocation) {
    try {
      if ((appiumDriver
          .getCapabilities()
          .getCapability("platform")
          .toString()
          .equalsIgnoreCase("android"))) {
        return methodInvocation.proceed();
      }

      Method method = methodInvocation.getMethod();
      AndroidSwitchView iosSwitchView = method.getAnnotation(AndroidSwitchView.class);

      return iosSwitchView
          .view()
          .getDeclaredMethod(method.getName(), method.getParameterTypes())
          .invoke(Screen(iosSwitchView.view()), methodInvocation.getArguments());
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable.getMessage());
    }
  }
}
