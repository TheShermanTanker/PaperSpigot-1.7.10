package com.avaje.ebean.annotation;

import com.avaje.ebean.TxIsolation;
import com.avaje.ebean.TxType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
  TxType type() default TxType.REQUIRED;
  
  TxIsolation isolation() default TxIsolation.DEFAULT;
  
  boolean readOnly() default false;
  
  String serverName() default "";
  
  Class<? extends Throwable>[] rollbackFor() default {};
  
  Class<? extends Throwable>[] noRollbackFor() default {};
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\annotation\Transactional.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */