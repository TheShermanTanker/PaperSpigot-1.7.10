package net.minecraft.util.com.google.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Documented
@GwtCompatible
public @interface GwtIncompatible {
  String value();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\annotations\GwtIncompatible.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */