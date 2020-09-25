package com.avaje.ebean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheTuning {
  int maxSize() default 0;
  
  int maxIdleSecs() default 0;
  
  int maxSecsToLive() default 0;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\annotation\CacheTuning.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */