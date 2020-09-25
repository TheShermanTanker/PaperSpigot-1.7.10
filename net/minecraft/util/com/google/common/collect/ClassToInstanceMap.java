package net.minecraft.util.com.google.common.collect;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.com.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface ClassToInstanceMap<B> extends Map<Class<? extends B>, B> {
  <T extends B> T getInstance(Class<T> paramClass);
  
  <T extends B> T putInstance(Class<T> paramClass, @Nullable T paramT);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ClassToInstanceMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */