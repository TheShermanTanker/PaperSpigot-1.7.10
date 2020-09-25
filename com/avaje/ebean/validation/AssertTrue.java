package com.avaje.ebean.validation;

import com.avaje.ebean.validation.factory.AssertFalseValidatorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory = AssertFalseValidatorFactory.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertTrue {}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\AssertTrue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */