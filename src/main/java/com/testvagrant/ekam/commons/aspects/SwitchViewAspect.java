package com.testvagrant.ekam.commons.aspects;

import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.annotations.SwitchView;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

import static com.testvagrant.ekam.commons.PageInitiator.getActivity;

public class SwitchViewAspect implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    if (Toggles.SWITCH_VIEW.isOff()) return methodInvocation.proceed();
    Method method = methodInvocation.getMethod();
    SwitchView annotation = method.getAnnotation(SwitchView.class);
    Object invoke =
        annotation
            .view()
            .getDeclaredMethod(method.getName(), method.getParameterTypes())
            .invoke(getActivity(annotation.view()), methodInvocation.getArguments());
    return invoke;
  }
}
