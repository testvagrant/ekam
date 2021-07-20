package com.testvagrant.ekam.commons.aspects;

import com.testvagrant.ekam.commons.annotations.AndroidSwitchView;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AndroidSwitchViewAspect implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation methodInvocation) {
    try {
      if (!System.getProperty("mobile.target").equalsIgnoreCase("android")) {
        return methodInvocation.proceed();
      }

      Method method = methodInvocation.getMethod();
      Class iosSwitchView = method.getAnnotation(AndroidSwitchView.class).view();

      return iosSwitchView
          .getDeclaredMethod(method.getName(), method.getParameterTypes())
          .invoke(Screen(iosSwitchView), methodInvocation.getArguments());
    } catch (Throwable throwable) {
      throw new RuntimeException(
          "Unable to switch view to Android.\nError Message:\n" + throwable.getMessage());
    }
  }
}
