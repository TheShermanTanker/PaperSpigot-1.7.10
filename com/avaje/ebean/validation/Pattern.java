package com.avaje.ebean.validation;

import com.avaje.ebean.validation.factory.PatternValidatorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory = PatternValidatorFactory.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pattern {
  String regex() default "";
  
  int flags() default 0;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\Pattern.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */