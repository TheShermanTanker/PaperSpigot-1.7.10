package org.bukkit.craftbukkit.libs.com.google.gson.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SerializedName {
  String value();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\annotations\SerializedName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */