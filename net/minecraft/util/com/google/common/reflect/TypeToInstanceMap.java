package net.minecraft.util.com.google.common.reflect;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.com.google.common.annotations.Beta;

@Beta
public interface TypeToInstanceMap<B> extends Map<TypeToken<? extends B>, B> {
  @Nullable
  <T extends B> T getInstance(Class<T> paramClass);
  
  @Nullable
  <T extends B> T putInstance(Class<T> paramClass, @Nullable T paramT);
  
  @Nullable
  <T extends B> T getInstance(TypeToken<T> paramTypeToken);
  
  @Nullable
  <T extends B> T putInstance(TypeToken<T> paramTypeToken, @Nullable T paramT);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\reflect\TypeToInstanceMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */