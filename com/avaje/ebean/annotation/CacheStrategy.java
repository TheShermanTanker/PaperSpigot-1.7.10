package com.avaje.ebean.annotation;

import com.avaje.ebean.Query;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheStrategy {
  boolean useBeanCache() default true;
  
  String naturalKey() default "";
  
  boolean readOnly() default false;
  
  String warmingQuery() default "";
  
  Query.UseIndex useIndex() default Query.UseIndex.DEFAULT;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\annotation\CacheStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */