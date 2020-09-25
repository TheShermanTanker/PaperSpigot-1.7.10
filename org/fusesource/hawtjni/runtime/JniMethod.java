package org.fusesource.hawtjni.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JniMethod {
  String cast() default "";
  
  String accessor() default "";
  
  MethodFlag[] flags() default {};
  
  String copy() default "";
  
  String conditional() default "";
  
  JniArg[] callbackArgs() default {};
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\hawtjni\runtime\JniMethod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */