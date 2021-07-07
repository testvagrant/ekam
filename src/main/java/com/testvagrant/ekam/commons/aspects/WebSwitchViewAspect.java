package com.testvagrant.ekam.commons.aspects;

import com.testvagrant.ekam.commons.annotations.WebSwitchView;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.LayoutInitiator.Page;

@SuppressWarnings("unchecked")
public class WebSwitchViewAspect implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) {
    try {
      if (!System.getProperty("web.target").equalsIgnoreCase("responsive")) {
        return invocation.proceed();
      }

      Method method = invocation.getMethod();
      WebSwitchView switchView = method.getAnnotation(WebSwitchView.class);

      return switchView
          .view()
          .getDeclaredMethod(method.getName(), method.getParameterTypes())
          .invoke(Page(switchView.view()), invocation.getArguments());
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable.getMessage());
    }
  }
}
