package com.testvagrant.ekam.commons.aspects;

import com.testvagrant.ekam.commons.annotations.IOSSwitchView;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;

@SuppressWarnings("unchecked")
public class IOSSwitchViewAspect implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) {
    try {
      if (!System.getProperty("mobile.target").equalsIgnoreCase("ios")) {
        return invocation.proceed();
      }

      Method method = invocation.getMethod();
      IOSSwitchView iosSwitchView = method.getAnnotation(IOSSwitchView.class);

      return iosSwitchView
          .view()
          .getDeclaredMethod(method.getName(), method.getParameterTypes())
          .invoke(Screen(iosSwitchView.view()), invocation.getArguments());
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable.getMessage());
    }
  }
}
