package net.minecraft.util.com.google.common.base;

import javax.annotation.Nullable;
import net.minecraft.util.com.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface Function<F, T> {
  @Nullable
  T apply(@Nullable F paramF);
  
  boolean equals(@Nullable Object paramObject);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\base\Function.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */