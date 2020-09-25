package javax.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable {
  String name() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  JoinColumn[] joinColumns() default {};
  
  JoinColumn[] inverseJoinColumns() default {};
  
  UniqueConstraint[] uniqueConstraints() default {};
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\JoinTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */