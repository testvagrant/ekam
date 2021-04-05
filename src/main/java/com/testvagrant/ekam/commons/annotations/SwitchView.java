package com.testvagrant.ekam.commons.annotations;

import com.testvagrant.ekam.commons.Injectors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwitchView {
  Class view();

  Injectors useInjector() default Injectors.MOBILE_PAGE_INJECTOR;
}
