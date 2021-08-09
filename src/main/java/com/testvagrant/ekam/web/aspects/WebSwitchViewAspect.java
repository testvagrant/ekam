package com.testvagrant.ekam.web.aspects;

import com.testvagrant.ekam.web.annotations.WebSwitchView;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.LayoutInitiator.Page;

@SuppressWarnings({"unchecked", "rawtypes"})
public class WebSwitchViewAspect implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) {
    try {
      if (!System.getProperty("web.target").equalsIgnoreCase("responsive")) {
        return invocation.proceed();
      }

      Method method = invocation.getMethod();
      Class view = method.getAnnotation(WebSwitchView.class).view();

      return view.getDeclaredMethod(method.getName(), method.getParameterTypes())
          .invoke(Page(view), invocation.getArguments());
    } catch (Throwable throwable) {
      throw new RuntimeException(throwable.getMessage());
    }
  }
}
