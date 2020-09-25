package org.bukkit.configuration.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DelegateDeserialization {
  Class<? extends ConfigurationSerializable> value();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\serialization\DelegateDeserialization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */