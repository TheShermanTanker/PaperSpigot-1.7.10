package com.avaje.ebean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedUpdate {
  String name();
  
  String update();
  
  boolean notifyCache() default true;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\annotation\NamedUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */