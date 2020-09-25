package net.minecraft.util.com.google.common.collect;

import net.minecraft.util.com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface Constraint<E> {
  E checkElement(E paramE);
  
  String toString();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\Constraint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */