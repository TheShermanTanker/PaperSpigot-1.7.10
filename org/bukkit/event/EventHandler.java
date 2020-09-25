package org.bukkit.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
  EventPriority priority() default EventPriority.NORMAL;
  
  boolean ignoreCancelled() default false;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\EventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */