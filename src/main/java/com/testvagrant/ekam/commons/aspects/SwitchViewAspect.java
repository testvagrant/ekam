package com.testvagrant.ekam.commons.aspects;

import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.annotations.SwitchView;
import com.testvagrant.ekam.commons.injectors.Injectors;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.LayoutInitiator.Page;
import static com.testvagrant.ekam.commons.LayoutInitiator.Screen;

public class SwitchViewAspect<T> implements MethodInterceptor {

  @SuppressWarnings("unchecked")
  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    if (Toggles.SWITCH_VIEW.isOff()) return methodInvocation.proceed();
    Method method = methodInvocation.getMethod();
    SwitchView annotation = method.getAnnotation(SwitchView.class);

    T view;

    Injectors injectors = annotation.useInjector();
    switch (injectors) {
      case WEB_PAGE_INJECTOR:
        view = (T) Page(annotation.view());
        break;

      default:
        view = (T) Screen(annotation.view());
        break;
    }

    return annotation
        .view()
        .getDeclaredMethod(method.getName(), method.getParameterTypes())
        .invoke(view, methodInvocation.getArguments());
  }
}
