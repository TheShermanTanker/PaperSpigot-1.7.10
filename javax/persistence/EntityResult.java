package javax.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityResult {
  Class entityClass();
  
  FieldResult[] fields() default {};
  
  String discriminatorColumn() default "";
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\EntityResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */