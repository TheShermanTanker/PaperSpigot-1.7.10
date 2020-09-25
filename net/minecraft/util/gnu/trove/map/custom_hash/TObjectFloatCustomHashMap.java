/*      */ package net.minecraft.util.gnu.trove.map.custom_hash;
/*      */ 
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectOutput;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import net.minecraft.util.gnu.trove.TFloatCollection;
/*      */ import net.minecraft.util.gnu.trove.function.TFloatFunction;
/*      */ import net.minecraft.util.gnu.trove.impl.Constants;
/*      */ import net.minecraft.util.gnu.trove.impl.hash.TCustomObjectHash;
/*      */ import net.minecraft.util.gnu.trove.impl.hash.THash;
/*      */ import net.minecraft.util.gnu.trove.impl.hash.TObjectHash;
/*      */ import net.minecraft.util.gnu.trove.iterator.TFloatIterator;
/*      */ import net.minecraft.util.gnu.trove.iterator.TObjectFloatIterator;
/*      */ import net.minecraft.util.gnu.trove.iterator.hash.TObjectHashIterator;
/*      */ import net.minecraft.util.gnu.trove.map.TObjectFloatMap;
/*      */ import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
/*      */ import net.minecraft.util.gnu.trove.procedure.TObjectFloatProcedure;
/*      */ import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;
/*      */ import net.minecraft.util.gnu.trove.strategy.HashingStrategy;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TObjectFloatCustomHashMap<K>
/*      */   extends TCustomObjectHash<K>
/*      */   implements TObjectFloatMap<K>, Externalizable
/*      */ {
/*      */   static final long serialVersionUID = 1L;
/*      */   
/*   58 */   private final TObjectFloatProcedure<K> PUT_ALL_PROC = new TObjectFloatProcedure<K>() {
/*      */       public boolean execute(K key, float value) {
/*   60 */         TObjectFloatCustomHashMap.this.put(key, value);
/*   61 */         return true;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient float[] _values;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float no_entry_value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TObjectFloatCustomHashMap(HashingStrategy<? super K> strategy) {
/*   81 */     super(strategy);
/*   82 */     this.no_entry_value = Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TObjectFloatCustomHashMap(HashingStrategy<? super K> strategy, int initialCapacity) {
/*   96 */     super(strategy, initialCapacity);
/*      */     
/*   98 */     this.no_entry_value = Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TObjectFloatCustomHashMap(HashingStrategy<? super K> strategy, int initialCapacity, float loadFactor) {
/*  113 */     super(strategy, initialCapacity, loadFactor);
/*      */     
/*  115 */     this.no_entry_value = Constants.DEFAULT_FLOAT_NO_ENTRY_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TObjectFloatCustomHashMap(HashingStrategy<? super K> strategy, int initialCapacity, float loadFactor, float noEntryValue) {
/*  131 */     super(strategy, initialCapacity, loadFactor);
/*      */     
/*  133 */     this.no_entry_value = noEntryValue;
/*      */     
/*  135 */     if (this.no_entry_value != 0.0F) {
/*  136 */       Arrays.fill(this._values, this.no_entry_value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TObjectFloatCustomHashMap(HashingStrategy<? super K> strategy, TObjectFloatMap<? extends K> map) {
/*  150 */     this(strategy, map.size(), 0.5F, map.getNoEntryValue());
/*      */     
/*  152 */     if (map instanceof TObjectFloatCustomHashMap) {
/*  153 */       TObjectFloatCustomHashMap hashmap = (TObjectFloatCustomHashMap)map;
/*  154 */       this._loadFactor = hashmap._loadFactor;
/*  155 */       this.no_entry_value = hashmap.no_entry_value;
/*  156 */       this.strategy = hashmap.strategy;
/*      */       
/*  158 */       if (this.no_entry_value != 0.0F) {
/*  159 */         Arrays.fill(this._values, this.no_entry_value);
/*      */       }
/*  161 */       setUp((int)Math.ceil((10.0F / this._loadFactor)));
/*      */     } 
/*  163 */     putAll(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int setUp(int initialCapacity) {
/*  177 */     int capacity = super.setUp(initialCapacity);
/*  178 */     this._values = new float[capacity];
/*  179 */     return capacity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void rehash(int newCapacity) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _set : [Ljava/lang/Object;
/*      */     //   4: arraylength
/*      */     //   5: istore_2
/*      */     //   6: aload_0
/*      */     //   7: getfield _set : [Ljava/lang/Object;
/*      */     //   10: checkcast [Ljava/lang/Object;
/*      */     //   13: astore_3
/*      */     //   14: aload_0
/*      */     //   15: getfield _values : [F
/*      */     //   18: astore #4
/*      */     //   20: aload_0
/*      */     //   21: iload_1
/*      */     //   22: anewarray java/lang/Object
/*      */     //   25: putfield _set : [Ljava/lang/Object;
/*      */     //   28: aload_0
/*      */     //   29: getfield _set : [Ljava/lang/Object;
/*      */     //   32: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   35: invokestatic fill : ([Ljava/lang/Object;Ljava/lang/Object;)V
/*      */     //   38: aload_0
/*      */     //   39: iload_1
/*      */     //   40: newarray float
/*      */     //   42: putfield _values : [F
/*      */     //   45: aload_0
/*      */     //   46: getfield _values : [F
/*      */     //   49: aload_0
/*      */     //   50: getfield no_entry_value : F
/*      */     //   53: invokestatic fill : ([FF)V
/*      */     //   56: iload_2
/*      */     //   57: istore #5
/*      */     //   59: iload #5
/*      */     //   61: iinc #5, -1
/*      */     //   64: ifle -> 133
/*      */     //   67: aload_3
/*      */     //   68: iload #5
/*      */     //   70: aaload
/*      */     //   71: astore #6
/*      */     //   73: aload #6
/*      */     //   75: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   78: if_acmpeq -> 130
/*      */     //   81: aload #6
/*      */     //   83: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   86: if_acmpeq -> 130
/*      */     //   89: aload_0
/*      */     //   90: aload #6
/*      */     //   92: invokevirtual insertKey : (Ljava/lang/Object;)I
/*      */     //   95: istore #7
/*      */     //   97: iload #7
/*      */     //   99: ifge -> 118
/*      */     //   102: aload_0
/*      */     //   103: aload_0
/*      */     //   104: getfield _set : [Ljava/lang/Object;
/*      */     //   107: iload #7
/*      */     //   109: ineg
/*      */     //   110: iconst_1
/*      */     //   111: isub
/*      */     //   112: aaload
/*      */     //   113: aload #6
/*      */     //   115: invokevirtual throwObjectContractViolation : (Ljava/lang/Object;Ljava/lang/Object;)V
/*      */     //   118: aload_0
/*      */     //   119: getfield _values : [F
/*      */     //   122: iload #7
/*      */     //   124: aload #4
/*      */     //   126: iload #5
/*      */     //   128: faload
/*      */     //   129: fastore
/*      */     //   130: goto -> 59
/*      */     //   133: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #189	-> 0
/*      */     //   #192	-> 6
/*      */     //   #193	-> 14
/*      */     //   #195	-> 20
/*      */     //   #196	-> 28
/*      */     //   #197	-> 38
/*      */     //   #198	-> 45
/*      */     //   #200	-> 56
/*      */     //   #201	-> 67
/*      */     //   #202	-> 73
/*      */     //   #203	-> 89
/*      */     //   #204	-> 97
/*      */     //   #205	-> 102
/*      */     //   #207	-> 118
/*      */     //   #209	-> 130
/*      */     //   #210	-> 133
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   97	33	7	index	I
/*      */     //   73	57	6	o	Ljava/lang/Object;
/*      */     //   59	74	5	i	I
/*      */     //   0	134	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	134	1	newCapacity	I
/*      */     //   6	128	2	oldCapacity	I
/*      */     //   14	120	3	oldKeys	[Ljava/lang/Object;
/*      */     //   20	114	4	oldVals	[F
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   73	57	6	o	TK;
/*      */     //   0	134	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */     //   14	120	3	oldKeys	[TK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getNoEntryValue() {
/*  217 */     return this.no_entry_value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object key) {
/*  223 */     return contains(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsValue(float val) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _set : [Ljava/lang/Object;
/*      */     //   4: astore_2
/*      */     //   5: aload_0
/*      */     //   6: getfield _values : [F
/*      */     //   9: astore_3
/*      */     //   10: aload_3
/*      */     //   11: arraylength
/*      */     //   12: istore #4
/*      */     //   14: iload #4
/*      */     //   16: iinc #4, -1
/*      */     //   19: ifle -> 53
/*      */     //   22: aload_2
/*      */     //   23: iload #4
/*      */     //   25: aaload
/*      */     //   26: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   29: if_acmpeq -> 14
/*      */     //   32: aload_2
/*      */     //   33: iload #4
/*      */     //   35: aaload
/*      */     //   36: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   39: if_acmpeq -> 14
/*      */     //   42: fload_1
/*      */     //   43: aload_3
/*      */     //   44: iload #4
/*      */     //   46: faload
/*      */     //   47: fcmpl
/*      */     //   48: ifne -> 14
/*      */     //   51: iconst_1
/*      */     //   52: ireturn
/*      */     //   53: iconst_0
/*      */     //   54: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #229	-> 0
/*      */     //   #230	-> 5
/*      */     //   #232	-> 10
/*      */     //   #233	-> 22
/*      */     //   #234	-> 51
/*      */     //   #237	-> 53
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   14	39	4	i	I
/*      */     //   0	55	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	55	1	val	F
/*      */     //   5	50	2	keys	[Ljava/lang/Object;
/*      */     //   10	45	3	vals	[F
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	55	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float get(Object key) {
/*  243 */     int index = index(key);
/*  244 */     return (index < 0) ? this.no_entry_value : this._values[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float put(K key, float value) {
/*  252 */     int index = insertKey(key);
/*  253 */     return doPut(value, index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float putIfAbsent(K key, float value) {
/*  259 */     int index = insertKey(key);
/*  260 */     if (index < 0)
/*  261 */       return this._values[-index - 1]; 
/*  262 */     return doPut(value, index);
/*      */   }
/*      */ 
/*      */   
/*      */   private float doPut(float value, int index) {
/*  267 */     float previous = this.no_entry_value;
/*  268 */     boolean isNewMapping = true;
/*  269 */     if (index < 0) {
/*  270 */       index = -index - 1;
/*  271 */       previous = this._values[index];
/*  272 */       isNewMapping = false;
/*      */     } 
/*      */     
/*  275 */     this._values[index] = value;
/*      */     
/*  277 */     if (isNewMapping) {
/*  278 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*  280 */     return previous;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float remove(Object key) {
/*  286 */     float prev = this.no_entry_value;
/*  287 */     int index = index(key);
/*  288 */     if (index >= 0) {
/*  289 */       prev = this._values[index];
/*  290 */       removeAt(index);
/*      */     } 
/*  292 */     return prev;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeAt(int index) {
/*  304 */     this._values[index] = this.no_entry_value;
/*  305 */     super.removeAt(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Float> map) {
/*  313 */     Set<? extends Map.Entry<? extends K, ? extends Float>> set = map.entrySet();
/*  314 */     for (Map.Entry<? extends K, ? extends Float> entry : set) {
/*  315 */       put(entry.getKey(), ((Float)entry.getValue()).floatValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void putAll(TObjectFloatMap<? extends K> map) {
/*  322 */     map.forEachEntry(this.PUT_ALL_PROC);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokespecial clear : ()V
/*      */     //   4: aload_0
/*      */     //   5: getfield _set : [Ljava/lang/Object;
/*      */     //   8: iconst_0
/*      */     //   9: aload_0
/*      */     //   10: getfield _set : [Ljava/lang/Object;
/*      */     //   13: arraylength
/*      */     //   14: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   17: invokestatic fill : ([Ljava/lang/Object;IILjava/lang/Object;)V
/*      */     //   20: aload_0
/*      */     //   21: getfield _values : [F
/*      */     //   24: iconst_0
/*      */     //   25: aload_0
/*      */     //   26: getfield _values : [F
/*      */     //   29: arraylength
/*      */     //   30: aload_0
/*      */     //   31: getfield no_entry_value : F
/*      */     //   34: invokestatic fill : ([FIIF)V
/*      */     //   37: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #328	-> 0
/*      */     //   #329	-> 4
/*      */     //   #330	-> 20
/*      */     //   #331	-> 37
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	38	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	38	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<K> keySet() {
/*  338 */     return new KeyView();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] keys() {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual size : ()I
/*      */     //   4: anewarray java/lang/Object
/*      */     //   7: checkcast [Ljava/lang/Object;
/*      */     //   10: astore_1
/*      */     //   11: aload_0
/*      */     //   12: getfield _set : [Ljava/lang/Object;
/*      */     //   15: astore_2
/*      */     //   16: aload_2
/*      */     //   17: arraylength
/*      */     //   18: istore_3
/*      */     //   19: iconst_0
/*      */     //   20: istore #4
/*      */     //   22: iload_3
/*      */     //   23: iinc #3, -1
/*      */     //   26: ifle -> 60
/*      */     //   29: aload_2
/*      */     //   30: iload_3
/*      */     //   31: aaload
/*      */     //   32: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   35: if_acmpeq -> 22
/*      */     //   38: aload_2
/*      */     //   39: iload_3
/*      */     //   40: aaload
/*      */     //   41: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   44: if_acmpeq -> 22
/*      */     //   47: aload_1
/*      */     //   48: iload #4
/*      */     //   50: iinc #4, 1
/*      */     //   53: aload_2
/*      */     //   54: iload_3
/*      */     //   55: aaload
/*      */     //   56: aastore
/*      */     //   57: goto -> 22
/*      */     //   60: aload_1
/*      */     //   61: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #345	-> 0
/*      */     //   #346	-> 11
/*      */     //   #348	-> 16
/*      */     //   #349	-> 29
/*      */     //   #351	-> 47
/*      */     //   #354	-> 60
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   19	41	3	i	I
/*      */     //   22	38	4	j	I
/*      */     //   0	62	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   11	51	1	keys	[Ljava/lang/Object;
/*      */     //   16	46	2	k	[Ljava/lang/Object;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	62	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */     //   11	51	1	keys	[TK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K[] keys(K[] a) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual size : ()I
/*      */     //   4: istore_2
/*      */     //   5: aload_1
/*      */     //   6: arraylength
/*      */     //   7: iload_2
/*      */     //   8: if_icmpge -> 29
/*      */     //   11: aload_1
/*      */     //   12: invokevirtual getClass : ()Ljava/lang/Class;
/*      */     //   15: invokevirtual getComponentType : ()Ljava/lang/Class;
/*      */     //   18: iload_2
/*      */     //   19: invokestatic newInstance : (Ljava/lang/Class;I)Ljava/lang/Object;
/*      */     //   22: checkcast [Ljava/lang/Object;
/*      */     //   25: checkcast [Ljava/lang/Object;
/*      */     //   28: astore_1
/*      */     //   29: aload_0
/*      */     //   30: getfield _set : [Ljava/lang/Object;
/*      */     //   33: astore_3
/*      */     //   34: aload_3
/*      */     //   35: arraylength
/*      */     //   36: istore #4
/*      */     //   38: iconst_0
/*      */     //   39: istore #5
/*      */     //   41: iload #4
/*      */     //   43: iinc #4, -1
/*      */     //   46: ifle -> 83
/*      */     //   49: aload_3
/*      */     //   50: iload #4
/*      */     //   52: aaload
/*      */     //   53: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   56: if_acmpeq -> 41
/*      */     //   59: aload_3
/*      */     //   60: iload #4
/*      */     //   62: aaload
/*      */     //   63: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   66: if_acmpeq -> 41
/*      */     //   69: aload_1
/*      */     //   70: iload #5
/*      */     //   72: iinc #5, 1
/*      */     //   75: aload_3
/*      */     //   76: iload #4
/*      */     //   78: aaload
/*      */     //   79: aastore
/*      */     //   80: goto -> 41
/*      */     //   83: aload_1
/*      */     //   84: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #360	-> 0
/*      */     //   #361	-> 5
/*      */     //   #363	-> 11
/*      */     //   #367	-> 29
/*      */     //   #369	-> 34
/*      */     //   #370	-> 49
/*      */     //   #372	-> 69
/*      */     //   #375	-> 83
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   38	45	4	i	I
/*      */     //   41	42	5	j	I
/*      */     //   0	85	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	85	1	a	[Ljava/lang/Object;
/*      */     //   5	80	2	size	I
/*      */     //   34	51	3	k	[Ljava/lang/Object;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	85	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */     //   0	85	1	a	[TK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TFloatCollection valueCollection() {
/*  381 */     return new TFloatValueCollection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] values() {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual size : ()I
/*      */     //   4: newarray float
/*      */     //   6: astore_1
/*      */     //   7: aload_0
/*      */     //   8: getfield _values : [F
/*      */     //   11: astore_2
/*      */     //   12: aload_0
/*      */     //   13: getfield _set : [Ljava/lang/Object;
/*      */     //   16: astore_3
/*      */     //   17: aload_2
/*      */     //   18: arraylength
/*      */     //   19: istore #4
/*      */     //   21: iconst_0
/*      */     //   22: istore #5
/*      */     //   24: iload #4
/*      */     //   26: iinc #4, -1
/*      */     //   29: ifle -> 66
/*      */     //   32: aload_3
/*      */     //   33: iload #4
/*      */     //   35: aaload
/*      */     //   36: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   39: if_acmpeq -> 24
/*      */     //   42: aload_3
/*      */     //   43: iload #4
/*      */     //   45: aaload
/*      */     //   46: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   49: if_acmpeq -> 24
/*      */     //   52: aload_1
/*      */     //   53: iload #5
/*      */     //   55: iinc #5, 1
/*      */     //   58: aload_2
/*      */     //   59: iload #4
/*      */     //   61: faload
/*      */     //   62: fastore
/*      */     //   63: goto -> 24
/*      */     //   66: aload_1
/*      */     //   67: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #387	-> 0
/*      */     //   #388	-> 7
/*      */     //   #389	-> 12
/*      */     //   #391	-> 17
/*      */     //   #392	-> 32
/*      */     //   #393	-> 52
/*      */     //   #396	-> 66
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   21	45	4	i	I
/*      */     //   24	42	5	j	I
/*      */     //   0	68	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   7	61	1	vals	[F
/*      */     //   12	56	2	v	[F
/*      */     //   17	51	3	keys	[Ljava/lang/Object;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	68	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] values(float[] array) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual size : ()I
/*      */     //   4: istore_2
/*      */     //   5: aload_1
/*      */     //   6: arraylength
/*      */     //   7: iload_2
/*      */     //   8: if_icmpge -> 15
/*      */     //   11: iload_2
/*      */     //   12: newarray float
/*      */     //   14: astore_1
/*      */     //   15: aload_0
/*      */     //   16: getfield _values : [F
/*      */     //   19: astore_3
/*      */     //   20: aload_0
/*      */     //   21: getfield _set : [Ljava/lang/Object;
/*      */     //   24: astore #4
/*      */     //   26: aload_3
/*      */     //   27: arraylength
/*      */     //   28: istore #5
/*      */     //   30: iconst_0
/*      */     //   31: istore #6
/*      */     //   33: iload #5
/*      */     //   35: iinc #5, -1
/*      */     //   38: ifle -> 77
/*      */     //   41: aload #4
/*      */     //   43: iload #5
/*      */     //   45: aaload
/*      */     //   46: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   49: if_acmpeq -> 33
/*      */     //   52: aload #4
/*      */     //   54: iload #5
/*      */     //   56: aaload
/*      */     //   57: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   60: if_acmpeq -> 33
/*      */     //   63: aload_1
/*      */     //   64: iload #6
/*      */     //   66: iinc #6, 1
/*      */     //   69: aload_3
/*      */     //   70: iload #5
/*      */     //   72: faload
/*      */     //   73: fastore
/*      */     //   74: goto -> 33
/*      */     //   77: aload_1
/*      */     //   78: arraylength
/*      */     //   79: iload_2
/*      */     //   80: if_icmple -> 90
/*      */     //   83: aload_1
/*      */     //   84: iload_2
/*      */     //   85: aload_0
/*      */     //   86: getfield no_entry_value : F
/*      */     //   89: fastore
/*      */     //   90: aload_1
/*      */     //   91: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #402	-> 0
/*      */     //   #403	-> 5
/*      */     //   #404	-> 11
/*      */     //   #407	-> 15
/*      */     //   #408	-> 20
/*      */     //   #410	-> 26
/*      */     //   #411	-> 41
/*      */     //   #412	-> 63
/*      */     //   #415	-> 77
/*      */     //   #416	-> 83
/*      */     //   #418	-> 90
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   30	47	5	i	I
/*      */     //   33	44	6	j	I
/*      */     //   0	92	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	92	1	array	[F
/*      */     //   5	87	2	size	I
/*      */     //   20	72	3	v	[F
/*      */     //   26	66	4	keys	[Ljava/lang/Object;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	92	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TObjectFloatIterator<K> iterator() {
/*  426 */     return new TObjectFloatHashIterator<K>(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean increment(K key) {
/*  434 */     return adjustValue(key, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean adjustValue(K key, float amount) {
/*  440 */     int index = index(key);
/*  441 */     if (index < 0) {
/*  442 */       return false;
/*      */     }
/*  444 */     this._values[index] = this._values[index] + amount;
/*  445 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float adjustOrPutValue(K key, float adjust_amount, float put_amount) {
/*      */     float newValue;
/*      */     boolean isNewMapping;
/*  454 */     int index = insertKey(key);
/*      */ 
/*      */     
/*  457 */     if (index < 0) {
/*  458 */       index = -index - 1;
/*  459 */       newValue = this._values[index] = this._values[index] + adjust_amount;
/*  460 */       isNewMapping = false;
/*      */     } else {
/*  462 */       newValue = this._values[index] = put_amount;
/*  463 */       isNewMapping = true;
/*      */     } 
/*      */ 
/*      */     
/*  467 */     if (isNewMapping) {
/*  468 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*      */     
/*  471 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forEachKey(TObjectProcedure<? super K> procedure) {
/*  483 */     return forEach(procedure);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forEachValue(TFloatProcedure procedure) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _set : [Ljava/lang/Object;
/*      */     //   4: astore_2
/*      */     //   5: aload_0
/*      */     //   6: getfield _values : [F
/*      */     //   9: astore_3
/*      */     //   10: aload_3
/*      */     //   11: arraylength
/*      */     //   12: istore #4
/*      */     //   14: iload #4
/*      */     //   16: iinc #4, -1
/*      */     //   19: ifle -> 57
/*      */     //   22: aload_2
/*      */     //   23: iload #4
/*      */     //   25: aaload
/*      */     //   26: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   29: if_acmpeq -> 14
/*      */     //   32: aload_2
/*      */     //   33: iload #4
/*      */     //   35: aaload
/*      */     //   36: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   39: if_acmpeq -> 14
/*      */     //   42: aload_1
/*      */     //   43: aload_3
/*      */     //   44: iload #4
/*      */     //   46: faload
/*      */     //   47: invokeinterface execute : (F)Z
/*      */     //   52: ifne -> 14
/*      */     //   55: iconst_0
/*      */     //   56: ireturn
/*      */     //   57: iconst_1
/*      */     //   58: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #495	-> 0
/*      */     //   #496	-> 5
/*      */     //   #497	-> 10
/*      */     //   #498	-> 22
/*      */     //   #500	-> 55
/*      */     //   #503	-> 57
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   14	43	4	i	I
/*      */     //   0	59	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	59	1	procedure	Lnet/minecraft/util/gnu/trove/procedure/TFloatProcedure;
/*      */     //   5	54	2	keys	[Ljava/lang/Object;
/*      */     //   10	49	3	values	[F
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	59	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forEachEntry(TObjectFloatProcedure<? super K> procedure) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _set : [Ljava/lang/Object;
/*      */     //   4: astore_2
/*      */     //   5: aload_0
/*      */     //   6: getfield _values : [F
/*      */     //   9: astore_3
/*      */     //   10: aload_2
/*      */     //   11: arraylength
/*      */     //   12: istore #4
/*      */     //   14: iload #4
/*      */     //   16: iinc #4, -1
/*      */     //   19: ifle -> 61
/*      */     //   22: aload_2
/*      */     //   23: iload #4
/*      */     //   25: aaload
/*      */     //   26: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   29: if_acmpeq -> 14
/*      */     //   32: aload_2
/*      */     //   33: iload #4
/*      */     //   35: aaload
/*      */     //   36: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   39: if_acmpeq -> 14
/*      */     //   42: aload_1
/*      */     //   43: aload_2
/*      */     //   44: iload #4
/*      */     //   46: aaload
/*      */     //   47: aload_3
/*      */     //   48: iload #4
/*      */     //   50: faload
/*      */     //   51: invokeinterface execute : (Ljava/lang/Object;F)Z
/*      */     //   56: ifne -> 14
/*      */     //   59: iconst_0
/*      */     //   60: ireturn
/*      */     //   61: iconst_1
/*      */     //   62: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #517	-> 0
/*      */     //   #518	-> 5
/*      */     //   #519	-> 10
/*      */     //   #520	-> 22
/*      */     //   #523	-> 59
/*      */     //   #526	-> 61
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   14	47	4	i	I
/*      */     //   0	63	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	63	1	procedure	Lnet/minecraft/util/gnu/trove/procedure/TObjectFloatProcedure;
/*      */     //   5	58	2	keys	[Ljava/lang/Object;
/*      */     //   10	53	3	values	[F
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	63	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */     //   0	63	1	procedure	Lnet/minecraft/util/gnu/trove/procedure/TObjectFloatProcedure<-TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean retainEntries(TObjectFloatProcedure<? super K> procedure) {
/*      */     // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore_2
/*      */     //   2: aload_0
/*      */     //   3: getfield _set : [Ljava/lang/Object;
/*      */     //   6: checkcast [Ljava/lang/Object;
/*      */     //   9: astore_3
/*      */     //   10: aload_0
/*      */     //   11: getfield _values : [F
/*      */     //   14: astore #4
/*      */     //   16: aload_0
/*      */     //   17: invokevirtual tempDisableAutoCompaction : ()V
/*      */     //   20: aload_3
/*      */     //   21: arraylength
/*      */     //   22: istore #5
/*      */     //   24: iload #5
/*      */     //   26: iinc #5, -1
/*      */     //   29: ifle -> 81
/*      */     //   32: aload_3
/*      */     //   33: iload #5
/*      */     //   35: aaload
/*      */     //   36: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   39: if_acmpeq -> 24
/*      */     //   42: aload_3
/*      */     //   43: iload #5
/*      */     //   45: aaload
/*      */     //   46: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   49: if_acmpeq -> 24
/*      */     //   52: aload_1
/*      */     //   53: aload_3
/*      */     //   54: iload #5
/*      */     //   56: aaload
/*      */     //   57: aload #4
/*      */     //   59: iload #5
/*      */     //   61: faload
/*      */     //   62: invokeinterface execute : (Ljava/lang/Object;F)Z
/*      */     //   67: ifne -> 24
/*      */     //   70: aload_0
/*      */     //   71: iload #5
/*      */     //   73: invokevirtual removeAt : (I)V
/*      */     //   76: iconst_1
/*      */     //   77: istore_2
/*      */     //   78: goto -> 24
/*      */     //   81: aload_0
/*      */     //   82: iconst_1
/*      */     //   83: invokevirtual reenableAutoCompaction : (Z)V
/*      */     //   86: goto -> 99
/*      */     //   89: astore #6
/*      */     //   91: aload_0
/*      */     //   92: iconst_1
/*      */     //   93: invokevirtual reenableAutoCompaction : (Z)V
/*      */     //   96: aload #6
/*      */     //   98: athrow
/*      */     //   99: iload_2
/*      */     //   100: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #538	-> 0
/*      */     //   #540	-> 2
/*      */     //   #541	-> 10
/*      */     //   #544	-> 16
/*      */     //   #546	-> 20
/*      */     //   #547	-> 32
/*      */     //   #550	-> 70
/*      */     //   #551	-> 76
/*      */     //   #556	-> 81
/*      */     //   #557	-> 86
/*      */     //   #556	-> 89
/*      */     //   #559	-> 99
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   24	57	5	i	I
/*      */     //   0	101	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	101	1	procedure	Lnet/minecraft/util/gnu/trove/procedure/TObjectFloatProcedure;
/*      */     //   2	99	2	modified	Z
/*      */     //   10	91	3	keys	[Ljava/lang/Object;
/*      */     //   16	85	4	values	[F
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	101	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */     //   0	101	1	procedure	Lnet/minecraft/util/gnu/trove/procedure/TObjectFloatProcedure<-TK;>;
/*      */     //   10	91	3	keys	[TK;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   20	81	89	finally
/*      */     //   89	91	89	finally
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformValues(TFloatFunction function) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _set : [Ljava/lang/Object;
/*      */     //   4: astore_2
/*      */     //   5: aload_0
/*      */     //   6: getfield _values : [F
/*      */     //   9: astore_3
/*      */     //   10: aload_3
/*      */     //   11: arraylength
/*      */     //   12: istore #4
/*      */     //   14: iload #4
/*      */     //   16: iinc #4, -1
/*      */     //   19: ifle -> 56
/*      */     //   22: aload_2
/*      */     //   23: iload #4
/*      */     //   25: aaload
/*      */     //   26: ifnull -> 14
/*      */     //   29: aload_2
/*      */     //   30: iload #4
/*      */     //   32: aaload
/*      */     //   33: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   36: if_acmpeq -> 14
/*      */     //   39: aload_3
/*      */     //   40: iload #4
/*      */     //   42: aload_1
/*      */     //   43: aload_3
/*      */     //   44: iload #4
/*      */     //   46: faload
/*      */     //   47: invokeinterface execute : (F)F
/*      */     //   52: fastore
/*      */     //   53: goto -> 14
/*      */     //   56: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #569	-> 0
/*      */     //   #570	-> 5
/*      */     //   #571	-> 10
/*      */     //   #572	-> 22
/*      */     //   #573	-> 39
/*      */     //   #576	-> 56
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   14	42	4	i	I
/*      */     //   0	57	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	57	1	function	Lnet/minecraft/util/gnu/trove/function/TFloatFunction;
/*      */     //   5	52	2	keys	[Ljava/lang/Object;
/*      */     //   10	47	3	values	[F
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	57	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/*  589 */     if (!(other instanceof TObjectFloatMap)) {
/*  590 */       return false;
/*      */     }
/*  592 */     TObjectFloatMap that = (TObjectFloatMap)other;
/*  593 */     if (that.size() != size()) {
/*  594 */       return false;
/*      */     }
/*      */     try {
/*  597 */       TObjectFloatIterator<K> iter = iterator();
/*  598 */       while (iter.hasNext()) {
/*  599 */         iter.advance();
/*  600 */         Object key = iter.key();
/*  601 */         float value = iter.value();
/*  602 */         if (value == this.no_entry_value) {
/*  603 */           if (that.get(key) != that.getNoEntryValue() || !that.containsKey(key))
/*      */           {
/*      */             
/*  606 */             return false; } 
/*      */           continue;
/*      */         } 
/*  609 */         if (value != that.get(key)) {
/*  610 */           return false;
/*      */         }
/*      */       }
/*      */     
/*  614 */     } catch (ClassCastException ex) {}
/*      */ 
/*      */     
/*  617 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*      */     // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore_1
/*      */     //   2: aload_0
/*      */     //   3: getfield _set : [Ljava/lang/Object;
/*      */     //   6: astore_2
/*      */     //   7: aload_0
/*      */     //   8: getfield _values : [F
/*      */     //   11: astore_3
/*      */     //   12: aload_3
/*      */     //   13: arraylength
/*      */     //   14: istore #4
/*      */     //   16: iload #4
/*      */     //   18: iinc #4, -1
/*      */     //   21: ifle -> 76
/*      */     //   24: aload_2
/*      */     //   25: iload #4
/*      */     //   27: aaload
/*      */     //   28: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   31: if_acmpeq -> 16
/*      */     //   34: aload_2
/*      */     //   35: iload #4
/*      */     //   37: aaload
/*      */     //   38: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   41: if_acmpeq -> 16
/*      */     //   44: iload_1
/*      */     //   45: aload_3
/*      */     //   46: iload #4
/*      */     //   48: faload
/*      */     //   49: invokestatic hash : (F)I
/*      */     //   52: aload_2
/*      */     //   53: iload #4
/*      */     //   55: aaload
/*      */     //   56: ifnonnull -> 63
/*      */     //   59: iconst_0
/*      */     //   60: goto -> 70
/*      */     //   63: aload_2
/*      */     //   64: iload #4
/*      */     //   66: aaload
/*      */     //   67: invokevirtual hashCode : ()I
/*      */     //   70: ixor
/*      */     //   71: iadd
/*      */     //   72: istore_1
/*      */     //   73: goto -> 16
/*      */     //   76: iload_1
/*      */     //   77: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #623	-> 0
/*      */     //   #624	-> 2
/*      */     //   #625	-> 7
/*      */     //   #626	-> 12
/*      */     //   #627	-> 24
/*      */     //   #628	-> 44
/*      */     //   #632	-> 76
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   16	60	4	i	I
/*      */     //   0	78	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   2	76	1	hashcode	I
/*      */     //   7	71	2	keys	[Ljava/lang/Object;
/*      */     //   12	66	3	values	[F
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	78	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class KeyView
/*      */     extends MapBackedView<K>
/*      */   {
/*      */     public Iterator<K> iterator() {
/*  641 */       return (Iterator<K>)new TObjectHashIterator((TObjectHash)TObjectFloatCustomHashMap.this);
/*      */     }
/*      */     
/*      */     public boolean removeElement(K key) {
/*  645 */       return (TObjectFloatCustomHashMap.this.no_entry_value != TObjectFloatCustomHashMap.this.remove(key));
/*      */     }
/*      */     
/*      */     public boolean containsElement(K key) {
/*  649 */       return TObjectFloatCustomHashMap.this.contains(key);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapBackedView<E>
/*      */     extends AbstractSet<E>
/*      */     implements Set<E>, Iterable<E>
/*      */   {
/*      */     private MapBackedView() {}
/*      */ 
/*      */     
/*      */     public boolean contains(Object key) {
/*  663 */       return containsElement((E)key);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*  668 */       return removeElement((E)o);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  672 */       TObjectFloatCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public boolean add(E obj) {
/*  676 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int size() {
/*  680 */       return TObjectFloatCustomHashMap.this.size();
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/*  684 */       Object[] result = new Object[size()];
/*  685 */       Iterator<E> e = iterator();
/*  686 */       for (int i = 0; e.hasNext(); i++) {
/*  687 */         result[i] = e.next();
/*      */       }
/*  689 */       return result;
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] a) {
/*  693 */       int size = size();
/*  694 */       if (a.length < size)
/*      */       {
/*  696 */         a = (T[])Array.newInstance(a.getClass().getComponentType(), size);
/*      */       }
/*      */ 
/*      */       
/*  700 */       Iterator<E> it = iterator();
/*  701 */       T[] arrayOfT = a;
/*  702 */       for (int i = 0; i < size; i++) {
/*  703 */         arrayOfT[i] = (T)it.next();
/*      */       }
/*      */       
/*  706 */       if (a.length > size) {
/*  707 */         a[size] = null;
/*      */       }
/*      */       
/*  710 */       return a;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  714 */       return TObjectFloatCustomHashMap.this.isEmpty();
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends E> collection) {
/*  718 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> collection) {
/*  723 */       boolean changed = false;
/*  724 */       Iterator<E> i = iterator();
/*  725 */       while (i.hasNext()) {
/*  726 */         if (!collection.contains(i.next())) {
/*  727 */           i.remove();
/*  728 */           changed = true;
/*      */         } 
/*      */       } 
/*  731 */       return changed;
/*      */     }
/*      */     
/*      */     public abstract boolean removeElement(E param1E);
/*      */     
/*      */     public abstract boolean containsElement(E param1E); }
/*      */   
/*      */   class TFloatValueCollection implements TFloatCollection {
/*      */     public TFloatIterator iterator() {
/*  740 */       return new TObjectFloatValueHashIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public float getNoEntryValue() {
/*  745 */       return TObjectFloatCustomHashMap.this.no_entry_value;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  750 */       return TObjectFloatCustomHashMap.this._size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/*  755 */       return (0 == TObjectFloatCustomHashMap.this._size);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float entry) {
/*  760 */       return TObjectFloatCustomHashMap.this.containsValue(entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public float[] toArray() {
/*  765 */       return TObjectFloatCustomHashMap.this.values();
/*      */     }
/*      */ 
/*      */     
/*      */     public float[] toArray(float[] dest) {
/*  770 */       return TObjectFloatCustomHashMap.this.values(dest);
/*      */     }
/*      */     
/*      */     public boolean add(float entry) {
/*  774 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float entry) {
/*  779 */       float[] values = TObjectFloatCustomHashMap.this._values;
/*  780 */       Object[] set = TObjectFloatCustomHashMap.this._set;
/*      */       
/*  782 */       for (int i = values.length; i-- > 0;) {
/*  783 */         if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && entry == values[i]) {
/*  784 */           TObjectFloatCustomHashMap.this.removeAt(i);
/*  785 */           return true;
/*      */         } 
/*      */       } 
/*  788 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(Collection<?> collection) {
/*  793 */       for (Object element : collection) {
/*  794 */         if (element instanceof Float) {
/*  795 */           float ele = ((Float)element).floatValue();
/*  796 */           if (!TObjectFloatCustomHashMap.this.containsValue(ele))
/*  797 */             return false; 
/*      */           continue;
/*      */         } 
/*  800 */         return false;
/*      */       } 
/*      */       
/*  803 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(TFloatCollection collection) {
/*  808 */       TFloatIterator iter = collection.iterator();
/*  809 */       while (iter.hasNext()) {
/*  810 */         if (!TObjectFloatCustomHashMap.this.containsValue(iter.next())) {
/*  811 */           return false;
/*      */         }
/*      */       } 
/*  814 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsAll(float[] array) {
/*  819 */       for (float element : array) {
/*  820 */         if (!TObjectFloatCustomHashMap.this.containsValue(element)) {
/*  821 */           return false;
/*      */         }
/*      */       } 
/*  824 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends Float> collection) {
/*  829 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(TFloatCollection collection) {
/*  834 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addAll(float[] array) {
/*  839 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean retainAll(Collection<?> collection) {
/*  845 */       boolean modified = false;
/*  846 */       TFloatIterator iter = iterator();
/*  847 */       while (iter.hasNext()) {
/*  848 */         if (!collection.contains(Float.valueOf(iter.next()))) {
/*  849 */           iter.remove();
/*  850 */           modified = true;
/*      */         } 
/*      */       } 
/*  853 */       return modified;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(TFloatCollection collection) {
/*  858 */       if (this == collection) {
/*  859 */         return false;
/*      */       }
/*  861 */       boolean modified = false;
/*  862 */       TFloatIterator iter = iterator();
/*  863 */       while (iter.hasNext()) {
/*  864 */         if (!collection.contains(iter.next())) {
/*  865 */           iter.remove();
/*  866 */           modified = true;
/*      */         } 
/*      */       } 
/*  869 */       return modified;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean retainAll(float[] array) {
/*  874 */       boolean changed = false;
/*  875 */       Arrays.sort(array);
/*  876 */       float[] values = TObjectFloatCustomHashMap.this._values;
/*      */       
/*  878 */       Object[] set = TObjectFloatCustomHashMap.this._set;
/*  879 */       for (int i = set.length; i-- > 0;) {
/*  880 */         if (set[i] != TObjectHash.FREE && set[i] != TObjectHash.REMOVED && Arrays.binarySearch(array, values[i]) < 0) {
/*      */ 
/*      */           
/*  883 */           TObjectFloatCustomHashMap.this.removeAt(i);
/*  884 */           changed = true;
/*      */         } 
/*      */       } 
/*  887 */       return changed;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection<?> collection) {
/*  892 */       boolean changed = false;
/*  893 */       for (Object element : collection) {
/*  894 */         if (element instanceof Float) {
/*  895 */           float c = ((Float)element).floatValue();
/*  896 */           if (remove(c)) {
/*  897 */             changed = true;
/*      */           }
/*      */         } 
/*      */       } 
/*  901 */       return changed;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(TFloatCollection collection) {
/*  906 */       if (this == collection) {
/*  907 */         clear();
/*  908 */         return true;
/*      */       } 
/*  910 */       boolean changed = false;
/*  911 */       TFloatIterator iter = collection.iterator();
/*  912 */       while (iter.hasNext()) {
/*  913 */         float element = iter.next();
/*  914 */         if (remove(element)) {
/*  915 */           changed = true;
/*      */         }
/*      */       } 
/*  918 */       return changed;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeAll(float[] array) {
/*  923 */       boolean changed = false;
/*  924 */       for (int i = array.length; i-- > 0;) {
/*  925 */         if (remove(array[i])) {
/*  926 */           changed = true;
/*      */         }
/*      */       } 
/*  929 */       return changed;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  934 */       TObjectFloatCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean forEach(TFloatProcedure procedure) {
/*  939 */       return TObjectFloatCustomHashMap.this.forEachValue(procedure);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  945 */       final StringBuilder buf = new StringBuilder("{");
/*  946 */       TObjectFloatCustomHashMap.this.forEachValue(new TFloatProcedure() {
/*      */             private boolean first = true;
/*      */             
/*      */             public boolean execute(float value) {
/*  950 */               if (this.first) {
/*  951 */                 this.first = false;
/*      */               } else {
/*  953 */                 buf.append(", ");
/*      */               } 
/*      */               
/*  956 */               buf.append(value);
/*  957 */               return true;
/*      */             }
/*      */           });
/*  960 */       buf.append("}");
/*  961 */       return buf.toString();
/*      */     }
/*      */     
/*      */     class TObjectFloatValueHashIterator
/*      */       implements TFloatIterator
/*      */     {
/*  967 */       protected THash _hash = (THash)TObjectFloatCustomHashMap.this;
/*      */ 
/*      */ 
/*      */       
/*      */       protected int _expectedSize;
/*      */ 
/*      */ 
/*      */       
/*      */       protected int _index;
/*      */ 
/*      */ 
/*      */       
/*      */       TObjectFloatValueHashIterator() {
/*  980 */         this._expectedSize = this._hash.size();
/*  981 */         this._index = this._hash.capacity();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/*  986 */         return (nextIndex() >= 0);
/*      */       }
/*      */ 
/*      */       
/*      */       public float next() {
/*  991 */         moveToNextIndex();
/*  992 */         return TObjectFloatCustomHashMap.this._values[this._index];
/*      */       }
/*      */ 
/*      */       
/*      */       public void remove() {
/*  997 */         if (this._expectedSize != this._hash.size()) {
/*  998 */           throw new ConcurrentModificationException();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1004 */           this._hash.tempDisableAutoCompaction();
/* 1005 */           TObjectFloatCustomHashMap.this.removeAt(this._index);
/*      */         } finally {
/*      */           
/* 1008 */           this._hash.reenableAutoCompaction(false);
/*      */         } 
/*      */         
/* 1011 */         this._expectedSize--;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected final void moveToNextIndex() {
/* 1021 */         if ((this._index = nextIndex()) < 0) {
/* 1022 */           throw new NoSuchElementException();
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected final int nextIndex() {
/* 1037 */         if (this._expectedSize != this._hash.size()) {
/* 1038 */           throw new ConcurrentModificationException();
/*      */         }
/*      */         
/* 1041 */         Object[] set = TObjectFloatCustomHashMap.this._set;
/* 1042 */         int i = this._index;
/* 1043 */         while (i-- > 0 && (set[i] == TCustomObjectHash.FREE || set[i] == TCustomObjectHash.REMOVED));
/*      */ 
/*      */ 
/*      */         
/* 1047 */         return i;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class TObjectFloatHashIterator<K>
/*      */     extends TObjectHashIterator<K>
/*      */     implements TObjectFloatIterator<K>
/*      */   {
/*      */     private final TObjectFloatCustomHashMap<K> _map;
/*      */     
/*      */     public TObjectFloatHashIterator(TObjectFloatCustomHashMap<K> map) {
/* 1060 */       super((TObjectHash)map);
/* 1061 */       this._map = map;
/*      */     }
/*      */ 
/*      */     
/*      */     public void advance() {
/* 1066 */       moveToNextIndex();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public K key() {
/* 1072 */       return (K)this._map._set[this._index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float value() {
/* 1077 */       return this._map._values[this._index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float setValue(float val) {
/* 1082 */       float old = value();
/* 1083 */       this._map._values[this._index] = val;
/* 1084 */       return old;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeExternal(ObjectOutput out) throws IOException {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: iconst_0
/*      */     //   2: invokeinterface writeByte : (I)V
/*      */     //   7: aload_0
/*      */     //   8: aload_1
/*      */     //   9: invokespecial writeExternal : (Ljava/io/ObjectOutput;)V
/*      */     //   12: aload_1
/*      */     //   13: aload_0
/*      */     //   14: getfield strategy : Lnet/minecraft/util/gnu/trove/strategy/HashingStrategy;
/*      */     //   17: invokeinterface writeObject : (Ljava/lang/Object;)V
/*      */     //   22: aload_1
/*      */     //   23: aload_0
/*      */     //   24: getfield no_entry_value : F
/*      */     //   27: invokeinterface writeFloat : (F)V
/*      */     //   32: aload_1
/*      */     //   33: aload_0
/*      */     //   34: getfield _size : I
/*      */     //   37: invokeinterface writeInt : (I)V
/*      */     //   42: aload_0
/*      */     //   43: getfield _set : [Ljava/lang/Object;
/*      */     //   46: arraylength
/*      */     //   47: istore_2
/*      */     //   48: iload_2
/*      */     //   49: iinc #2, -1
/*      */     //   52: ifle -> 106
/*      */     //   55: aload_0
/*      */     //   56: getfield _set : [Ljava/lang/Object;
/*      */     //   59: iload_2
/*      */     //   60: aaload
/*      */     //   61: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.REMOVED : Ljava/lang/Object;
/*      */     //   64: if_acmpeq -> 48
/*      */     //   67: aload_0
/*      */     //   68: getfield _set : [Ljava/lang/Object;
/*      */     //   71: iload_2
/*      */     //   72: aaload
/*      */     //   73: getstatic net/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap.FREE : Ljava/lang/Object;
/*      */     //   76: if_acmpeq -> 48
/*      */     //   79: aload_1
/*      */     //   80: aload_0
/*      */     //   81: getfield _set : [Ljava/lang/Object;
/*      */     //   84: iload_2
/*      */     //   85: aaload
/*      */     //   86: invokeinterface writeObject : (Ljava/lang/Object;)V
/*      */     //   91: aload_1
/*      */     //   92: aload_0
/*      */     //   93: getfield _values : [F
/*      */     //   96: iload_2
/*      */     //   97: faload
/*      */     //   98: invokeinterface writeFloat : (F)V
/*      */     //   103: goto -> 48
/*      */     //   106: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1093	-> 0
/*      */     //   #1096	-> 7
/*      */     //   #1099	-> 12
/*      */     //   #1102	-> 22
/*      */     //   #1105	-> 32
/*      */     //   #1108	-> 42
/*      */     //   #1109	-> 55
/*      */     //   #1110	-> 79
/*      */     //   #1111	-> 91
/*      */     //   #1114	-> 106
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   48	58	2	i	I
/*      */     //   0	107	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap;
/*      */     //   0	107	1	out	Ljava/io/ObjectOutput;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	107	0	this	Lnet/minecraft/util/gnu/trove/map/custom_hash/TObjectFloatCustomHashMap<TK;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 1121 */     in.readByte();
/*      */ 
/*      */     
/* 1124 */     super.readExternal(in);
/*      */ 
/*      */     
/* 1127 */     this.strategy = (HashingStrategy)in.readObject();
/*      */ 
/*      */     
/* 1130 */     this.no_entry_value = in.readFloat();
/*      */ 
/*      */     
/* 1133 */     int size = in.readInt();
/* 1134 */     setUp(size);
/*      */ 
/*      */     
/* 1137 */     while (size-- > 0) {
/*      */       
/* 1139 */       K key = (K)in.readObject();
/* 1140 */       float val = in.readFloat();
/* 1141 */       put(key, val);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1148 */     final StringBuilder buf = new StringBuilder("{");
/* 1149 */     forEachEntry(new TObjectFloatProcedure<K>() { private boolean first = true;
/*      */           
/*      */           public boolean execute(K key, float value) {
/* 1152 */             if (this.first) { this.first = false; }
/* 1153 */             else { buf.append(","); }
/*      */             
/* 1155 */             buf.append(key).append("=").append(value);
/* 1156 */             return true;
/*      */           } }
/*      */       );
/* 1159 */     buf.append("}");
/* 1160 */     return buf.toString();
/*      */   }
/*      */   
/*      */   public TObjectFloatCustomHashMap() {}
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\custom_hash\TObjectFloatCustomHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */