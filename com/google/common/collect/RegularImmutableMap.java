/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.annotation.concurrent.Immutable;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class RegularImmutableMap<K, V>
/*     */   extends ImmutableMap<K, V>
/*     */ {
/*     */   private final transient LinkedEntry<K, V>[] entries;
/*     */   private final transient LinkedEntry<K, V>[] table;
/*     */   private final transient int mask;
/*     */   private final transient int keySetHashCode;
/*     */   private transient ImmutableSet<Map.Entry<K, V>> entrySet;
/*     */   private transient ImmutableSet<K> keySet;
/*     */   private transient ImmutableCollection<V> values;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   RegularImmutableMap(Map.Entry<?, ?>... immutableEntries) {
/*  49 */     int size = immutableEntries.length;
/*  50 */     this.entries = createEntryArray(size);
/*     */     
/*  52 */     int tableSize = chooseTableSize(size);
/*  53 */     this.table = createEntryArray(tableSize);
/*  54 */     this.mask = tableSize - 1;
/*     */     
/*  56 */     int keySetHashCodeMutable = 0;
/*  57 */     for (int entryIndex = 0; entryIndex < size; entryIndex++) {
/*     */ 
/*     */       
/*  60 */       Map.Entry<?, ?> entry = immutableEntries[entryIndex];
/*  61 */       K key = (K)entry.getKey();
/*  62 */       int keyHashCode = key.hashCode();
/*  63 */       keySetHashCodeMutable += keyHashCode;
/*  64 */       int tableIndex = Hashing.smear(keyHashCode) & this.mask;
/*  65 */       LinkedEntry<K, V> existing = this.table[tableIndex];
/*     */       
/*  67 */       LinkedEntry<K, V> linkedEntry = newLinkedEntry(key, (V)entry.getValue(), existing);
/*     */       
/*  69 */       this.table[tableIndex] = linkedEntry;
/*  70 */       this.entries[entryIndex] = linkedEntry;
/*  71 */       while (existing != null) {
/*  72 */         Preconditions.checkArgument(!key.equals(existing.getKey()), "duplicate key: %s", new Object[] { key });
/*  73 */         existing = existing.next();
/*     */       } 
/*     */     } 
/*  76 */     this.keySetHashCode = keySetHashCodeMutable;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int chooseTableSize(int size) {
/*  81 */     int tableSize = Integer.highestOneBit(size) << 1;
/*  82 */     Preconditions.checkArgument((tableSize > 0), "table too large: %s", new Object[] { Integer.valueOf(size) });
/*  83 */     return tableSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LinkedEntry<K, V>[] createEntryArray(int size) {
/*  93 */     return (LinkedEntry<K, V>[])new LinkedEntry[size];
/*     */   }
/*     */ 
/*     */   
/*     */   private static <K, V> LinkedEntry<K, V> newLinkedEntry(K key, V value, @Nullable LinkedEntry<K, V> next) {
/*  98 */     return (next == null) ? new TerminalEntry<K, V>(key, value) : new NonTerminalEntry<K, V>(key, value, next);
/*     */   }
/*     */   
/*     */   private static interface LinkedEntry<K, V>
/*     */     extends Map.Entry<K, V>
/*     */   {
/*     */     @Nullable
/*     */     LinkedEntry<K, V> next();
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   private static final class NonTerminalEntry<K, V>
/*     */     extends ImmutableEntry<K, V>
/*     */     implements LinkedEntry<K, V>
/*     */   {
/*     */     final RegularImmutableMap.LinkedEntry<K, V> next;
/*     */     
/*     */     NonTerminalEntry(K key, V value, RegularImmutableMap.LinkedEntry<K, V> next) {
/* 116 */       super(key, value);
/* 117 */       this.next = next;
/*     */     }
/*     */     
/*     */     public RegularImmutableMap.LinkedEntry<K, V> next() {
/* 121 */       return this.next;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   private static final class TerminalEntry<K, V>
/*     */     extends ImmutableEntry<K, V>
/*     */     implements LinkedEntry<K, V>
/*     */   {
/*     */     TerminalEntry(K key, V value) {
/* 134 */       super(key, value);
/*     */     }
/*     */     @Nullable
/*     */     public RegularImmutableMap.LinkedEntry<K, V> next() {
/* 138 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   public V get(@Nullable Object key) {
/* 143 */     if (key == null) {
/* 144 */       return null;
/*     */     }
/* 146 */     int index = Hashing.smear(key.hashCode()) & this.mask;
/* 147 */     LinkedEntry<K, V> entry = this.table[index];
/* 148 */     for (; entry != null; 
/* 149 */       entry = entry.next()) {
/* 150 */       K candidateKey = entry.getKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 158 */       if (key.equals(candidateKey)) {
/* 159 */         return entry.getValue();
/*     */       }
/*     */     } 
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 167 */     return this.entries.length;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 171 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsValue(@Nullable Object value) {
/* 175 */     if (value == null) {
/* 176 */       return false;
/*     */     }
/* 178 */     for (Map.Entry<K, V> entry : this.entries) {
/* 179 */       if (entry.getValue().equals(value)) {
/* 180 */         return true;
/*     */       }
/*     */     } 
/* 183 */     return false;
/*     */   }
/*     */   
/*     */   boolean isPartialView() {
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<Map.Entry<K, V>> entrySet() {
/* 193 */     ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
/* 194 */     return (es == null) ? (this.entrySet = new EntrySet<K, V>(this)) : es;
/*     */   }
/*     */   
/*     */   private static class EntrySet<K, V>
/*     */     extends ImmutableSet.ArrayImmutableSet<Map.Entry<K, V>> {
/*     */     final transient RegularImmutableMap<K, V> map;
/*     */     
/*     */     EntrySet(RegularImmutableMap<K, V> map) {
/* 202 */       super((Object[])map.entries);
/* 203 */       this.map = map;
/*     */     }
/*     */     
/*     */     public boolean contains(Object target) {
/* 207 */       if (target instanceof Map.Entry) {
/* 208 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)target;
/* 209 */         V mappedValue = this.map.get(entry.getKey());
/* 210 */         return (mappedValue != null && mappedValue.equals(entry.getValue()));
/*     */       } 
/* 212 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableSet<K> keySet() {
/* 219 */     ImmutableSet<K> ks = this.keySet;
/* 220 */     return (ks == null) ? (this.keySet = new KeySet<K, V>(this)) : ks;
/*     */   }
/*     */   
/*     */   private static class KeySet<K, V>
/*     */     extends ImmutableSet.TransformedImmutableSet<Map.Entry<K, V>, K>
/*     */   {
/*     */     final RegularImmutableMap<K, V> map;
/*     */     
/*     */     KeySet(RegularImmutableMap<K, V> map) {
/* 229 */       super((Map.Entry<K, V>[])map.entries, map.keySetHashCode);
/* 230 */       this.map = map;
/*     */     }
/*     */     
/*     */     K transform(Map.Entry<K, V> element) {
/* 234 */       return element.getKey();
/*     */     }
/*     */     
/*     */     public boolean contains(Object target) {
/* 238 */       return this.map.containsKey(target);
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 242 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableCollection<V> values() {
/* 249 */     ImmutableCollection<V> v = this.values;
/* 250 */     return (v == null) ? (this.values = new Values<V>(this)) : v;
/*     */   }
/*     */   
/*     */   private static class Values<V>
/*     */     extends ImmutableCollection<V> {
/*     */     final RegularImmutableMap<?, V> map;
/*     */     
/*     */     Values(RegularImmutableMap<?, V> map) {
/* 258 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 263 */       return this.map.entries.length;
/*     */     }
/*     */     
/*     */     public UnmodifiableIterator<V> iterator() {
/* 267 */       return new AbstractIndexedListIterator<V>(this.map.entries.length) {
/*     */           protected V get(int index) {
/* 269 */             return RegularImmutableMap.Values.this.map.entries[index].getValue();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean contains(Object target) {
/* 275 */       return this.map.containsValue(target);
/*     */     }
/*     */     
/*     */     boolean isPartialView() {
/* 279 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 284 */     StringBuilder result = Collections2.newStringBuilderForCollection(size()).append('{');
/*     */     
/* 286 */     Collections2.STANDARD_JOINER.appendTo(result, (Object[])this.entries);
/* 287 */     return result.append('}').toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\RegularImmutableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */