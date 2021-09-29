package com.testvagrant.ekam.mobile.aspects;

import com.testvagrant.ekam.mobile.annotations.AndroidSwitchView;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

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
      ekamLogger().info("Switching to Android View");
      return iosSwitchView
          .getDeclaredMethod(method.getName(), method.getParameterTypes())
          .invoke(Screen(iosSwitchView), methodInvocation.getArguments());
    } catch (Throwable throwable) {
      ekamLogger().warn(throwable.getMessage());
      throw new RuntimeException(throwable.getMessage());
    }
  }
}
