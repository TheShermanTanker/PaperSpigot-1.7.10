package net.minecraft.util.com.google.common.cache;

import net.minecraft.util.com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface LongAddable {
  void increment();
  
  void add(long paramLong);
  
  long sum();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\cache\LongAddable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */