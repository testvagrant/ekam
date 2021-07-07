package com.testvagrant.ekam.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MobileStep {
  String keyword() default "";

  String persona() default "User";

  String platform() default "Mobile";

  String description();
}
