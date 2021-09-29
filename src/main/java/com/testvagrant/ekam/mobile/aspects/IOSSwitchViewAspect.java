package com.testvagrant.ekam.mobile.aspects;

import com.testvagrant.ekam.mobile.annotations.IOSSwitchView;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

@SuppressWarnings({"unchecked", "rawtypes"})
public class IOSSwitchViewAspect implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) {
    try {
      if (!System.getProperty("mobile.target").equalsIgnoreCase("ios")) {
        return invocation.proceed();
      }

      Method method = invocation.getMethod();
      Class iosSwitchView = method.getAnnotation(IOSSwitchView.class).view();
      ekamLogger().info("Switching to IOS View");
      return iosSwitchView
          .getDeclaredMethod(method.getName(), method.getParameterTypes())
          .invoke(Screen(iosSwitchView), invocation.getArguments());
    } catch (Throwable throwable) {
      ekamLogger().warn(throwable.getMessage());
      throw new RuntimeException(throwable.getMessage());
    }
  }
}
