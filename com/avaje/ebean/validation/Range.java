package com.avaje.ebean.validation;

import com.avaje.ebean.validation.factory.RangeValidatorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory = RangeValidatorFactory.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
  long min() default -9223372036854775808L;
  
  long max() default 9223372036854775807L;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\Range.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */