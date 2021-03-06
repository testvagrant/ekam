package com.testvagrant.ekam.reports.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebStep {
  String keyword() default "";

  String persona() default "";

  String platform() default "Web";

  String description();
}
