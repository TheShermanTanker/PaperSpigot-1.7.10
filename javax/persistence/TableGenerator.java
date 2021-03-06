package javax.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableGenerator {
  String name();
  
  String table() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  String pkColumnName() default "";
  
  String valueColumnName() default "";
  
  String pkColumnValue() default "";
  
  int initialValue() default 0;
  
  int allocationSize() default 50;
  
  UniqueConstraint[] uniqueConstraints() default {};
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\javax\persistence\TableGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */