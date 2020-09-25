package com.avaje.ebean.validation.factory;

import java.lang.annotation.Annotation;

public interface ValidatorFactory {
  Validator create(Annotation paramAnnotation, Class<?> paramClass);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\ValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */