package com.avaje.ebean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface SqlSelect {
  String name() default "default";
  
  String tableAlias() default "";
  
  String query() default "";
  
  String extend() default "";
  
  String where() default "";
  
  String having() default "";
  
  String columnMapping() default "";
  
  boolean debug() default false;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\annotation\SqlSelect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */