/*     */ package net.minecraft.util.gnu.trove.impl.sync;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.gnu.trove.function.TObjectFunction;
/*     */ import net.minecraft.util.gnu.trove.iterator.TCharObjectIterator;
/*     */ import net.minecraft.util.gnu.trove.map.TCharObjectMap;
/*     */ import net.minecraft.util.gnu.trove.procedure.TCharObjectProcedure;
/*     */ import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
/*     */ import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;
/*     */ import net.minecraft.util.gnu.trove.set.TCharSet;
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
/*     */ 
/*     */ public class TSynchronizedCharObjectMap<V>
/*     */   implements TCharObjectMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1978198479659022715L;
/*     */   private final TCharObjectMap<V> m;
/*     */   final Object mutex;
/*     */   
/*     */   public TSynchronizedCharObjectMap(TCharObjectMap<V> m) {
/*  61 */     if (m == null)
/*  62 */       throw new NullPointerException(); 
/*  63 */     this.m = m;
/*  64 */     this.mutex = this;
/*     */   }
/*     */   
/*     */   public TSynchronizedCharObjectMap(TCharObjectMap<V> m, Object mutex) {
/*  68 */     this.m = m;
/*  69 */     this.mutex = mutex;
/*     */   }
/*     */   
/*     */   public int size() {
/*  73 */     synchronized (this.mutex) { return this.m.size(); }
/*     */   
/*     */   } public boolean isEmpty() {
/*  76 */     synchronized (this.mutex) { return this.m.isEmpty(); }
/*     */   
/*     */   } public boolean containsKey(char key) {
/*  79 */     synchronized (this.mutex) { return this.m.containsKey(key); }
/*     */   
/*     */   } public boolean containsValue(Object value) {
/*  82 */     synchronized (this.mutex) { return this.m.containsValue(value); }
/*     */   
/*     */   } public V get(char key) {
/*  85 */     synchronized (this.mutex) { return (V)this.m.get(key); }
/*     */   
/*     */   }
/*     */   public V put(char key, V value) {
/*  89 */     synchronized (this.mutex) { return (V)this.m.put(key, value); }
/*     */   
/*     */   } public V remove(char key) {
/*  92 */     synchronized (this.mutex) { return (V)this.m.remove(key); }
/*     */   
/*     */   } public void putAll(Map<? extends Character, ? extends V> map) {
/*  95 */     synchronized (this.mutex) { this.m.putAll(map); }
/*     */   
/*     */   } public void putAll(TCharObjectMap<? extends V> map) {
/*  98 */     synchronized (this.mutex) { this.m.putAll(map); }
/*     */   
/*     */   } public void clear() {
/* 101 */     synchronized (this.mutex) { this.m.clear(); }
/*     */   
/*     */   }
/* 104 */   private transient TCharSet keySet = null;
/* 105 */   private transient Collection<V> values = null;
/*     */   
/*     */   public TCharSet keySet() {
/* 108 */     synchronized (this.mutex) {
/* 109 */       if (this.keySet == null)
/* 110 */         this.keySet = new TSynchronizedCharSet(this.m.keySet(), this.mutex); 
/* 111 */       return this.keySet;
/*     */     } 
/*     */   }
/*     */   public char[] keys() {
/* 115 */     synchronized (this.mutex) { return this.m.keys(); }
/*     */   
/*     */   } public char[] keys(char[] array) {
/* 118 */     synchronized (this.mutex) { return this.m.keys(array); }
/*     */   
/*     */   }
/*     */   public Collection<V> valueCollection() {
/* 122 */     synchronized (this.mutex) {
/* 123 */       if (this.values == null) {
/* 124 */         this.values = new SynchronizedCollection<V>(this.m.valueCollection(), this.mutex);
/*     */       }
/* 126 */       return this.values;
/*     */     } 
/*     */   }
/*     */   public Object[] values() {
/* 130 */     synchronized (this.mutex) { return this.m.values(); }
/*     */   
/*     */   } public V[] values(V[] array) {
/* 133 */     synchronized (this.mutex) { return (V[])this.m.values((Object[])array); }
/*     */   
/*     */   }
/*     */   public TCharObjectIterator<V> iterator() {
/* 137 */     return this.m.iterator();
/*     */   }
/*     */   
/*     */   public char getNoEntryKey() {
/* 141 */     return this.m.getNoEntryKey();
/*     */   }
/*     */   public V putIfAbsent(char key, V value) {
/* 144 */     synchronized (this.mutex) { return (V)this.m.putIfAbsent(key, value); }
/*     */   
/*     */   } public boolean forEachKey(TCharProcedure procedure) {
/* 147 */     synchronized (this.mutex) { return this.m.forEachKey(procedure); }
/*     */   
/*     */   } public boolean forEachValue(TObjectProcedure<? super V> procedure) {
/* 150 */     synchronized (this.mutex) { return this.m.forEachValue(procedure); }
/*     */   
/*     */   } public boolean forEachEntry(TCharObjectProcedure<? super V> procedure) {
/* 153 */     synchronized (this.mutex) { return this.m.forEachEntry(procedure); }
/*     */   
/*     */   } public void transformValues(TObjectFunction<V, V> function) {
/* 156 */     synchronized (this.mutex) { this.m.transformValues(function); }
/*     */   
/*     */   } public boolean retainEntries(TCharObjectProcedure<? super V> procedure) {
/* 159 */     synchronized (this.mutex) { return this.m.retainEntries(procedure); }
/*     */   
/*     */   }
/*     */   public boolean equals(Object o) {
/* 163 */     synchronized (this.mutex) { return this.m.equals(o); }
/*     */   
/*     */   } public int hashCode() {
/* 166 */     synchronized (this.mutex) { return this.m.hashCode(); }
/*     */   
/*     */   } public String toString() {
/* 169 */     synchronized (this.mutex) { return this.m.toString(); }
/*     */   
/*     */   } private void writeObject(ObjectOutputStream s) throws IOException {
/* 172 */     synchronized (this.mutex) { s.defaultWriteObject(); }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\impl\sync\TSynchronizedCharObjectMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */