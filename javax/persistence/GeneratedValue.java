package javax.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratedValue {
  GenerationType strategy() default GenerationType.AUTO;
  
  String generator() default "";
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\GeneratedValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */