package com.avaje.ebean.validation;

import com.avaje.ebean.validation.factory.LengthValidatorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory = LengthValidatorFactory.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
  int min() default 0;
  
  int max() default 2147483647;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\Length.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */