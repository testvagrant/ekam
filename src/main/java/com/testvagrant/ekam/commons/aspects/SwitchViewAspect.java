package com.testvagrant.ekam.commons.aspects;

import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.annotations.SwitchView;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.ActivityInitiator.Activity;

public class SwitchViewAspect implements MethodInterceptor {

  @SuppressWarnings("unchecked")
  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    if (Toggles.SWITCH_VIEW.isOff()) return methodInvocation.proceed();
    Method method = methodInvocation.getMethod();
    SwitchView annotation = method.getAnnotation(SwitchView.class);
    return annotation
        .view()
        .getDeclaredMethod(method.getName(), method.getParameterTypes())
        .invoke(Activity(annotation.view()), methodInvocation.getArguments());
  }
}
