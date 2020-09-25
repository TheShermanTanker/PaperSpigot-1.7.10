/*     */ package net.minecraft.util.gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.gnu.trove.TCollections;
/*     */ import net.minecraft.util.gnu.trove.TIntCollection;
/*     */ import net.minecraft.util.gnu.trove.function.TIntFunction;
/*     */ import net.minecraft.util.gnu.trove.iterator.TFloatIntIterator;
/*     */ import net.minecraft.util.gnu.trove.map.TFloatIntMap;
/*     */ import net.minecraft.util.gnu.trove.procedure.TFloatIntProcedure;
/*     */ import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
/*     */ import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
/*     */ import net.minecraft.util.gnu.trove.set.TFloatSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TUnmodifiableFloatIntMap
/*     */   implements TFloatIntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TFloatIntMap m;
/*     */   
/*     */   public TUnmodifiableFloatIntMap(TFloatIntMap m) {
/*  58 */     if (m == null)
/*  59 */       throw new NullPointerException(); 
/*  60 */     this.m = m;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.m.size(); }
/*  64 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  65 */   public boolean containsKey(float key) { return this.m.containsKey(key); }
/*  66 */   public boolean containsValue(int val) { return this.m.containsValue(val); } public int get(float key) {
/*  67 */     return this.m.get(key);
/*     */   }
/*  69 */   public int put(float key, int value) { throw new UnsupportedOperationException(); }
/*  70 */   public int remove(float key) { throw new UnsupportedOperationException(); }
/*  71 */   public void putAll(TFloatIntMap m) { throw new UnsupportedOperationException(); }
/*  72 */   public void putAll(Map<? extends Float, ? extends Integer> map) { throw new UnsupportedOperationException(); } public void clear() {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*  75 */   private transient TFloatSet keySet = null;
/*  76 */   private transient TIntCollection values = null;
/*     */   
/*     */   public TFloatSet keySet() {
/*  79 */     if (this.keySet == null)
/*  80 */       this.keySet = TCollections.unmodifiableSet(this.m.keySet()); 
/*  81 */     return this.keySet;
/*     */   }
/*  83 */   public float[] keys() { return this.m.keys(); } public float[] keys(float[] array) {
/*  84 */     return this.m.keys(array);
/*     */   }
/*     */   public TIntCollection valueCollection() {
/*  87 */     if (this.values == null)
/*  88 */       this.values = TCollections.unmodifiableCollection(this.m.valueCollection()); 
/*  89 */     return this.values;
/*     */   }
/*  91 */   public int[] values() { return this.m.values(); } public int[] values(int[] array) {
/*  92 */     return this.m.values(array);
/*     */   }
/*  94 */   public boolean equals(Object o) { return (o == this || this.m.equals(o)); }
/*  95 */   public int hashCode() { return this.m.hashCode(); }
/*  96 */   public String toString() { return this.m.toString(); }
/*  97 */   public float getNoEntryKey() { return this.m.getNoEntryKey(); } public int getNoEntryValue() {
/*  98 */     return this.m.getNoEntryValue();
/*     */   }
/*     */   public boolean forEachKey(TFloatProcedure procedure) {
/* 101 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   public boolean forEachValue(TIntProcedure procedure) {
/* 104 */     return this.m.forEachValue(procedure);
/*     */   }
/*     */   public boolean forEachEntry(TFloatIntProcedure procedure) {
/* 107 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TFloatIntIterator iterator() {
/* 111 */     return new TFloatIntIterator() {
/* 112 */         TFloatIntIterator iter = TUnmodifiableFloatIntMap.this.m.iterator();
/*     */         
/* 114 */         public float key() { return this.iter.key(); }
/* 115 */         public int value() { return this.iter.value(); }
/* 116 */         public void advance() { this.iter.advance(); }
/* 117 */         public boolean hasNext() { return this.iter.hasNext(); }
/* 118 */         public int setValue(int val) { throw new UnsupportedOperationException(); } public void remove() {
/* 119 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/* 123 */   public int putIfAbsent(float key, int value) { throw new UnsupportedOperationException(); }
/* 124 */   public void transformValues(TIntFunction function) { throw new UnsupportedOperationException(); }
/* 125 */   public boolean retainEntries(TFloatIntProcedure procedure) { throw new UnsupportedOperationException(); }
/* 126 */   public boolean increment(float key) { throw new UnsupportedOperationException(); }
/* 127 */   public boolean adjustValue(float key, int amount) { throw new UnsupportedOperationException(); } public int adjustOrPutValue(float key, int adjust_amount, int put_amount) {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\imp\\unmodifiable\TUnmodifiableFloatIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */