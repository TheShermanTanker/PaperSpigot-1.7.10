package javax.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedNativeQuery {
  String name() default "";
  
  String query();
  
  QueryHint[] hints() default {};
  
  Class resultClass() default void.class;
  
  String resultSetMapping() default "";
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\NamedNativeQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */