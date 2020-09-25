/*     */ package net.minecraft.util.gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.gnu.trove.TCollections;
/*     */ import net.minecraft.util.gnu.trove.TDoubleCollection;
/*     */ import net.minecraft.util.gnu.trove.function.TDoubleFunction;
/*     */ import net.minecraft.util.gnu.trove.iterator.TLongDoubleIterator;
/*     */ import net.minecraft.util.gnu.trove.map.TLongDoubleMap;
/*     */ import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
/*     */ import net.minecraft.util.gnu.trove.procedure.TLongDoubleProcedure;
/*     */ import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
/*     */ import net.minecraft.util.gnu.trove.set.TLongSet;
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
/*     */ public class TUnmodifiableLongDoubleMap
/*     */   implements TLongDoubleMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TLongDoubleMap m;
/*     */   
/*     */   public TUnmodifiableLongDoubleMap(TLongDoubleMap m) {
/*  58 */     if (m == null)
/*  59 */       throw new NullPointerException(); 
/*  60 */     this.m = m;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.m.size(); }
/*  64 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  65 */   public boolean containsKey(long key) { return this.m.containsKey(key); }
/*  66 */   public boolean containsValue(double val) { return this.m.containsValue(val); } public double get(long key) {
/*  67 */     return this.m.get(key);
/*     */   }
/*  69 */   public double put(long key, double value) { throw new UnsupportedOperationException(); }
/*  70 */   public double remove(long key) { throw new UnsupportedOperationException(); }
/*  71 */   public void putAll(TLongDoubleMap m) { throw new UnsupportedOperationException(); }
/*  72 */   public void putAll(Map<? extends Long, ? extends Double> map) { throw new UnsupportedOperationException(); } public void clear() {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*  75 */   private transient TLongSet keySet = null;
/*  76 */   private transient TDoubleCollection values = null;
/*     */   
/*     */   public TLongSet keySet() {
/*  79 */     if (this.keySet == null)
/*  80 */       this.keySet = TCollections.unmodifiableSet(this.m.keySet()); 
/*  81 */     return this.keySet;
/*     */   }
/*  83 */   public long[] keys() { return this.m.keys(); } public long[] keys(long[] array) {
/*  84 */     return this.m.keys(array);
/*     */   }
/*     */   public TDoubleCollection valueCollection() {
/*  87 */     if (this.values == null)
/*  88 */       this.values = TCollections.unmodifiableCollection(this.m.valueCollection()); 
/*  89 */     return this.values;
/*     */   }
/*  91 */   public double[] values() { return this.m.values(); } public double[] values(double[] array) {
/*  92 */     return this.m.values(array);
/*     */   }
/*  94 */   public boolean equals(Object o) { return (o == this || this.m.equals(o)); }
/*  95 */   public int hashCode() { return this.m.hashCode(); }
/*  96 */   public String toString() { return this.m.toString(); }
/*  97 */   public long getNoEntryKey() { return this.m.getNoEntryKey(); } public double getNoEntryValue() {
/*  98 */     return this.m.getNoEntryValue();
/*     */   }
/*     */   public boolean forEachKey(TLongProcedure procedure) {
/* 101 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   public boolean forEachValue(TDoubleProcedure procedure) {
/* 104 */     return this.m.forEachValue(procedure);
/*     */   }
/*     */   public boolean forEachEntry(TLongDoubleProcedure procedure) {
/* 107 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TLongDoubleIterator iterator() {
/* 111 */     return new TLongDoubleIterator() {
/* 112 */         TLongDoubleIterator iter = TUnmodifiableLongDoubleMap.this.m.iterator();
/*     */         
/* 114 */         public long key() { return this.iter.key(); }
/* 115 */         public double value() { return this.iter.value(); }
/* 116 */         public void advance() { this.iter.advance(); }
/* 117 */         public boolean hasNext() { return this.iter.hasNext(); }
/* 118 */         public double setValue(double val) { throw new UnsupportedOperationException(); } public void remove() {
/* 119 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/* 123 */   public double putIfAbsent(long key, double value) { throw new UnsupportedOperationException(); }
/* 124 */   public void transformValues(TDoubleFunction function) { throw new UnsupportedOperationException(); }
/* 125 */   public boolean retainEntries(TLongDoubleProcedure procedure) { throw new UnsupportedOperationException(); }
/* 126 */   public boolean increment(long key) { throw new UnsupportedOperationException(); }
/* 127 */   public boolean adjustValue(long key, double amount) { throw new UnsupportedOperationException(); } public double adjustOrPutValue(long key, double adjust_amount, double put_amount) {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\imp\\unmodifiable\TUnmodifiableLongDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */