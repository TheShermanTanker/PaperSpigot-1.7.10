package org.fusesource.hawtjni.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JniClass {
  ClassFlag[] flags() default {};
  
  String conditional() default "";
  
  String name() default "";
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\hawtjni\runtime\JniClass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */