package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.function.TObjectFunction;
import net.minecraft.util.gnu.trove.procedure.TObjectObjectProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;

public interface TMap<K, V> extends Map<K, V> {
  V putIfAbsent(K paramK, V paramV);
  
  boolean forEachKey(TObjectProcedure<? super K> paramTObjectProcedure);
  
  boolean forEachValue(TObjectProcedure<? super V> paramTObjectProcedure);
  
  boolean forEachEntry(TObjectObjectProcedure<? super K, ? super V> paramTObjectObjectProcedure);
  
  boolean retainEntries(TObjectObjectProcedure<? super K, ? super V> paramTObjectObjectProcedure);
  
  void transformValues(TObjectFunction<V, V> paramTObjectFunction);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */