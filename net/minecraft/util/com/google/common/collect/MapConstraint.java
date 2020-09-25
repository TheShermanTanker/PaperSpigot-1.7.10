package net.minecraft.util.com.google.common.collect;

import javax.annotation.Nullable;
import net.minecraft.util.com.google.common.annotations.Beta;
import net.minecraft.util.com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public interface MapConstraint<K, V> {
  void checkKeyValue(@Nullable K paramK, @Nullable V paramV);
  
  String toString();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\MapConstraint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */