/*      */ package net.minecraft.util.io.netty.util.internal.chmv8;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import sun.misc.Unsafe;
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
/*      */ public class ConcurrentHashMapV8<K, V>
/*      */   implements ConcurrentMap<K, V>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7249069246763182397L;
/*      */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   private static final int DEFAULT_CAPACITY = 16;
/*      */   static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
/*      */   private static final float LOAD_FACTOR = 0.75F;
/*      */   static final int TREEIFY_THRESHOLD = 8;
/*      */   static final int UNTREEIFY_THRESHOLD = 6;
/*      */   static final int MIN_TREEIFY_CAPACITY = 64;
/*      */   private static final int MIN_TRANSFER_STRIDE = 16;
/*      */   static final int MOVED = -1;
/*      */   static final int TREEBIN = -2;
/*      */   static final int RESERVED = -3;
/*      */   static final int HASH_BITS = 2147483647;
/*  591 */   static final int NCPU = Runtime.getRuntime().availableProcessors();
/*      */ 
/*      */   
/*  594 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", int.class), new ObjectStreamField("segmentShift", int.class) };
/*      */   
/*      */   volatile transient Node<K, V>[] table;
/*      */   private volatile transient Node<K, V>[] nextTable;
/*      */   private volatile transient long baseCount;
/*      */   private volatile transient int sizeCtl;
/*      */   private volatile transient int transferIndex;
/*      */   private volatile transient int transferOrigin;
/*      */   private volatile transient int cellsBusy;
/*      */   private volatile transient CounterCell[] counterCells;
/*      */   private transient KeySetView<K, V> keySet;
/*      */   private transient ValuesView<K, V> values;
/*      */   private transient EntrySetView<K, V> entrySet;
/*      */   
/*      */   static class Node<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final int hash;
/*      */     final K key;
/*      */     volatile V val;
/*      */     volatile Node<K, V> next;
/*      */     
/*      */     Node(int hash, K key, V val, Node<K, V> next) {
/*  617 */       this.hash = hash;
/*  618 */       this.key = key;
/*  619 */       this.val = val;
/*  620 */       this.next = next;
/*      */     }
/*      */     
/*  623 */     public final K getKey() { return this.key; }
/*  624 */     public final V getValue() { return this.val; }
/*  625 */     public final int hashCode() { return this.key.hashCode() ^ this.val.hashCode(); } public final String toString() {
/*  626 */       return (new StringBuilder()).append(this.key).append("=").append(this.val).toString();
/*      */     }
/*  628 */     public final V setValue(V value) { throw new UnsupportedOperationException(); } public final boolean equals(Object o) {
/*      */       Map.Entry<?, ?> e;
/*      */       Object k;
/*      */       Object v;
/*      */       Object u;
/*  633 */       return (o instanceof Map.Entry && (k = (e = (Map.Entry<?, ?>)o).getKey()) != null && (v = e.getValue()) != null && (k == this.key || k.equals(this.key)) && (v == (u = this.val) || v.equals(u)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Node<K, V> find(int h, Object k) {
/*  644 */       Node<K, V> e = this;
/*  645 */       if (k != null) {
/*      */         do {
/*      */           K ek;
/*  648 */           if (e.hash == h && ((ek = e.key) == k || (ek != null && k.equals(ek))))
/*      */           {
/*  650 */             return e; } 
/*  651 */         } while ((e = e.next) != null);
/*      */       }
/*  653 */       return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int spread(int h) {
/*  676 */     return (h ^ h >>> 16) & Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int tableSizeFor(int c) {
/*  684 */     int n = c - 1;
/*  685 */     n |= n >>> 1;
/*  686 */     n |= n >>> 2;
/*  687 */     n |= n >>> 4;
/*  688 */     n |= n >>> 8;
/*  689 */     n |= n >>> 16;
/*  690 */     return (n < 0) ? 1 : ((n >= 1073741824) ? 1073741824 : (n + 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Class<?> comparableClassFor(Object x) {
/*  698 */     if (x instanceof Comparable) {
/*      */       Class<?> c;
/*  700 */       if ((c = x.getClass()) == String.class)
/*  701 */         return c;  Type[] ts;
/*  702 */       if ((ts = c.getGenericInterfaces()) != null)
/*  703 */         for (int i = 0; i < ts.length; i++) {
/*  704 */           Type t; ParameterizedType p; Type[] as; if (t = ts[i] instanceof ParameterizedType && (p = (ParameterizedType)t).getRawType() == Comparable.class && (as = p.getActualTypeArguments()) != null && as.length == 1 && as[0] == c)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  709 */             return c;
/*      */           }
/*      */         }  
/*      */     } 
/*  713 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int compareComparables(Class<?> kc, Object k, Object x) {
/*  722 */     return (x == null || x.getClass() != kc) ? 0 : ((Comparable<Object>)k).compareTo(x);
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
/*      */ 
/*      */   
/*      */   static final <K, V> Node<K, V> tabAt(Node<K, V>[] tab, int i) {
/*  746 */     return (Node<K, V>)U.getObjectVolatile(tab, (i << ASHIFT) + ABASE);
/*      */   }
/*      */ 
/*      */   
/*      */   static final <K, V> boolean casTabAt(Node<K, V>[] tab, int i, Node<K, V> c, Node<K, V> v) {
/*  751 */     return U.compareAndSwapObject(tab, (i << ASHIFT) + ABASE, c, v);
/*      */   }
/*      */   
/*      */   static final <K, V> void setTabAt(Node<K, V>[] tab, int i, Node<K, V> v) {
/*  755 */     U.putObjectVolatile(tab, (i << ASHIFT) + ABASE, v);
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
/*      */   public ConcurrentHashMapV8() {}
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
/*      */   public ConcurrentHashMapV8(int initialCapacity) {
/*  833 */     if (initialCapacity < 0)
/*  834 */       throw new IllegalArgumentException(); 
/*  835 */     int cap = (initialCapacity >= 536870912) ? 1073741824 : tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1);
/*      */ 
/*      */     
/*  838 */     this.sizeCtl = cap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConcurrentHashMapV8(Map<? extends K, ? extends V> m) {
/*  847 */     this.sizeCtl = 16;
/*  848 */     putAll(m);
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
/*      */   public ConcurrentHashMapV8(int initialCapacity, float loadFactor) {
/*  867 */     this(initialCapacity, loadFactor, 1);
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
/*      */   
/*      */   public ConcurrentHashMapV8(int initialCapacity, float loadFactor, int concurrencyLevel) {
/*  890 */     if (loadFactor <= 0.0F || initialCapacity < 0 || concurrencyLevel <= 0)
/*  891 */       throw new IllegalArgumentException(); 
/*  892 */     if (initialCapacity < concurrencyLevel)
/*  893 */       initialCapacity = concurrencyLevel; 
/*  894 */     long size = (long)(1.0D + ((float)initialCapacity / loadFactor));
/*  895 */     int cap = (size >= 1073741824L) ? 1073741824 : tableSizeFor((int)size);
/*      */     
/*  897 */     this.sizeCtl = cap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  906 */     long n = sumCount();
/*  907 */     return (n < 0L) ? 0 : ((n > 2147483647L) ? Integer.MAX_VALUE : (int)n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  916 */     return (sumCount() <= 0L);
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
/*      */   public V get(Object key) {
/*  932 */     int h = spread(key.hashCode()); Node<K, V>[] tab; int n; Node<K, V> e;
/*  933 */     if ((tab = this.table) != null && (n = tab.length) > 0 && (e = tabAt(tab, n - 1 & h)) != null) {
/*      */       int eh;
/*  935 */       if ((eh = e.hash) == h) {
/*  936 */         K ek; if ((ek = e.key) == key || (ek != null && key.equals(ek))) {
/*  937 */           return e.val;
/*      */         }
/*  939 */       } else if (eh < 0) {
/*  940 */         Node<K, V> p; return ((p = e.find(h, key)) != null) ? p.val : null;
/*  941 */       }  while ((e = e.next) != null) {
/*  942 */         K ek; if (e.hash == h && ((ek = e.key) == key || (ek != null && key.equals(ek))))
/*      */         {
/*  944 */           return e.val; } 
/*      */       } 
/*      */     } 
/*  947 */     return null;
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
/*      */   public boolean containsKey(Object key) {
/*  960 */     return (get(key) != null);
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
/*      */   public boolean containsValue(Object value) {
/*  974 */     if (value == null)
/*  975 */       throw new NullPointerException(); 
/*      */     Node<K, V>[] t;
/*  977 */     if ((t = this.table) != null) {
/*  978 */       Traverser<K, V> it = new Traverser<K, V>(t, t.length, 0, t.length); Node<K, V> p;
/*  979 */       while ((p = it.advance()) != null) {
/*      */         V v;
/*  981 */         if ((v = p.val) == value || (v != null && value.equals(v)))
/*  982 */           return true; 
/*      */       } 
/*      */     } 
/*  985 */     return false;
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
/*      */   public V put(K key, V value) {
/* 1002 */     return putVal(key, value, false);
/*      */   }
/*      */ 
/*      */   
/*      */   final V putVal(K key, V value, boolean onlyIfAbsent) {
/* 1007 */     if (key == null || value == null) throw new NullPointerException(); 
/* 1008 */     int hash = spread(key.hashCode());
/* 1009 */     int binCount = 0;
/* 1010 */     Node<K, V>[] tab = this.table; while (true) {
/*      */       Node[] arrayOfNode; int n;
/* 1012 */       if (tab == null || (n = tab.length) == 0) {
/* 1013 */         arrayOfNode = (Node[])initTable(); continue;
/* 1014 */       }  int i; Node<K, V> f; if ((f = tabAt((Node<?, ?>[])arrayOfNode, i = n - 1 & hash)) == null) {
/* 1015 */         if (casTabAt((Node<?, ?>[])arrayOfNode, i, null, new Node<Object, Object>(hash, key, value, null)))
/*      */           break;  continue;
/*      */       } 
/*      */       int fh;
/* 1019 */       if ((fh = f.hash) == -1) {
/* 1020 */         arrayOfNode = (Node[])helpTransfer((Node<K, V>[])arrayOfNode, f); continue;
/*      */       } 
/* 1022 */       V oldVal = null;
/* 1023 */       synchronized (f) {
/* 1024 */         if (tabAt((Node<K, V>[])arrayOfNode, i) == f)
/* 1025 */           if (fh >= 0) {
/* 1026 */             binCount = 1;
/* 1027 */             for (Node<K, V> e = f;; binCount++) {
/*      */               K ek;
/* 1029 */               if (e.hash == hash && ((ek = e.key) == key || (ek != null && key.equals(ek)))) {
/*      */ 
/*      */                 
/* 1032 */                 oldVal = e.val;
/* 1033 */                 if (!onlyIfAbsent)
/* 1034 */                   e.val = value; 
/*      */                 break;
/*      */               } 
/* 1037 */               Node<K, V> pred = e;
/* 1038 */               if ((e = e.next) == null) {
/* 1039 */                 pred.next = new Node<K, V>(hash, key, value, null);
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1045 */           } else if (f instanceof TreeBin) {
/*      */             
/* 1047 */             binCount = 2; Node<K, V> p;
/* 1048 */             if ((p = ((TreeBin<K, V>)f).putTreeVal(hash, key, value)) != null) {
/*      */               
/* 1050 */               oldVal = p.val;
/* 1051 */               if (!onlyIfAbsent) {
/* 1052 */                 p.val = value;
/*      */               }
/*      */             } 
/*      */           }  
/*      */       } 
/* 1057 */       if (binCount != 0) {
/* 1058 */         if (binCount >= 8)
/* 1059 */           treeifyBin((Node<K, V>[])arrayOfNode, i); 
/* 1060 */         if (oldVal != null) {
/* 1061 */           return oldVal;
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 1066 */     addCount(1L, binCount);
/* 1067 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/* 1078 */     tryPresize(m.size());
/* 1079 */     for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
/* 1080 */       putVal(e.getKey(), e.getValue(), false);
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
/*      */   public V remove(Object key) {
/* 1093 */     return replaceNode(key, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final V replaceNode(Object key, V value, Object cv) {
/* 1102 */     int hash = spread(key.hashCode());
/* 1103 */     Node<K, V>[] tab = this.table; int n, i;
/*      */     Node<K, V> f;
/* 1105 */     while (tab != null && (n = tab.length) != 0 && (f = tabAt(tab, i = n - 1 & hash)) != null) {
/*      */       Node[] arrayOfNode;
/*      */       int fh;
/* 1108 */       if ((fh = f.hash) == -1) {
/* 1109 */         arrayOfNode = (Node[])helpTransfer(tab, f); continue;
/*      */       } 
/* 1111 */       V oldVal = null;
/* 1112 */       boolean validated = false;
/* 1113 */       synchronized (f) {
/* 1114 */         if (tabAt((Node<K, V>[])arrayOfNode, i) == f)
/* 1115 */           if (fh >= 0) {
/* 1116 */             validated = true;
/* 1117 */             Node<K, V> e = f, pred = null; do {
/*      */               K ek;
/* 1119 */               if (e.hash == hash && ((ek = e.key) == key || (ek != null && key.equals(ek)))) {
/*      */ 
/*      */                 
/* 1122 */                 V ev = e.val;
/* 1123 */                 if (cv == null || cv == ev || (ev != null && cv.equals(ev))) {
/*      */                   
/* 1125 */                   oldVal = ev;
/* 1126 */                   if (value != null) {
/* 1127 */                     e.val = value; break;
/* 1128 */                   }  if (pred != null) {
/* 1129 */                     pred.next = e.next; break;
/*      */                   } 
/* 1131 */                   setTabAt((Node<K, V>[])arrayOfNode, i, e.next);
/*      */                 } 
/*      */                 break;
/*      */               } 
/* 1135 */               pred = e;
/* 1136 */             } while ((e = e.next) != null);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1141 */             validated = true;
/* 1142 */             TreeBin<K, V> t = (TreeBin<K, V>)f;
/*      */             TreeNode<K, V> r, p;
/* 1144 */             if (f instanceof TreeBin && (r = t.root) != null && (p = r.findTreeNode(hash, key, (Class<?>)null)) != null) {
/*      */               
/* 1146 */               V pv = p.val;
/* 1147 */               if (cv == null || cv == pv || (pv != null && cv.equals(pv))) {
/*      */                 
/* 1149 */                 oldVal = pv;
/* 1150 */                 if (value != null) {
/* 1151 */                   p.val = value;
/* 1152 */                 } else if (t.removeTreeNode(p)) {
/* 1153 */                   setTabAt((Node<?, ?>[])arrayOfNode, i, untreeify(t.first));
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }  
/*      */       } 
/* 1159 */       if (validated) {
/* 1160 */         if (oldVal != null) {
/* 1161 */           if (value == null)
/* 1162 */             addCount(-1L, -1); 
/* 1163 */           return oldVal;
/*      */         } 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1169 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/* 1176 */     long delta = 0L;
/* 1177 */     int i = 0;
/* 1178 */     Node<K, V>[] tab = this.table;
/* 1179 */     while (tab != null && i < tab.length) {
/*      */       Node[] arrayOfNode;
/* 1181 */       Node<K, V> f = tabAt(tab, i);
/* 1182 */       if (f == null) {
/* 1183 */         i++; continue;
/* 1184 */       }  int fh; if ((fh = f.hash) == -1) {
/* 1185 */         arrayOfNode = (Node[])helpTransfer(tab, f);
/* 1186 */         i = 0;
/*      */         continue;
/*      */       } 
/* 1189 */       synchronized (f) {
/* 1190 */         if (tabAt((Node<K, V>[])arrayOfNode, i) == f) {
/* 1191 */           Node<K, V> p = (fh >= 0) ? f : ((f instanceof TreeBin) ? ((TreeBin)f).first : null);
/*      */ 
/*      */           
/* 1194 */           while (p != null) {
/* 1195 */             delta--;
/* 1196 */             p = p.next;
/*      */           } 
/* 1198 */           setTabAt((Node<?, ?>[])arrayOfNode, i++, null);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1203 */     if (delta != 0L) {
/* 1204 */       addCount(delta, -1);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public KeySetView<K, V> keySet() {
/*      */     KeySetView<K, V> ks;
/* 1227 */     return ((ks = this.keySet) != null) ? ks : (this.keySet = new KeySetView<K, V>(this, null));
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
/*      */   public Collection<V> values() {
/*      */     ValuesView<K, V> vs;
/* 1250 */     return ((vs = this.values) != null) ? vs : (this.values = new ValuesView<K, V>(this));
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
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/*      */     EntrySetView<K, V> es;
/* 1272 */     return ((es = this.entrySet) != null) ? es : (this.entrySet = new EntrySetView<K, V>(this));
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
/* 1283 */     int h = 0;
/*      */     Node<K, V>[] t;
/* 1285 */     if ((t = this.table) != null) {
/* 1286 */       Traverser<K, V> it = new Traverser<K, V>(t, t.length, 0, t.length); Node<K, V> p;
/* 1287 */       while ((p = it.advance()) != null)
/* 1288 */         h += p.key.hashCode() ^ p.val.hashCode(); 
/*      */     } 
/* 1290 */     return h;
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
/*      */   public String toString() {
/*      */     Node<K, V>[] t;
/* 1306 */     int f = ((t = this.table) == null) ? 0 : t.length;
/* 1307 */     Traverser<K, V> it = new Traverser<K, V>(t, f, 0, f);
/* 1308 */     StringBuilder sb = new StringBuilder();
/* 1309 */     sb.append('{');
/*      */     Node<K, V> p;
/* 1311 */     if ((p = it.advance()) != null) {
/*      */       while (true) {
/* 1313 */         K k = p.key;
/* 1314 */         V v = p.val;
/* 1315 */         sb.append((k == this) ? "(this Map)" : k);
/* 1316 */         sb.append('=');
/* 1317 */         sb.append((v == this) ? "(this Map)" : v);
/* 1318 */         if ((p = it.advance()) == null)
/*      */           break; 
/* 1320 */         sb.append(',').append(' ');
/*      */       } 
/*      */     }
/* 1323 */     return sb.append('}').toString();
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
/*      */   public boolean equals(Object o) {
/* 1337 */     if (o != this) {
/* 1338 */       if (!(o instanceof Map))
/* 1339 */         return false; 
/* 1340 */       Map<?, ?> m = (Map<?, ?>)o;
/*      */       Node<K, V>[] t;
/* 1342 */       int f = ((t = this.table) == null) ? 0 : t.length;
/* 1343 */       Traverser<K, V> it = new Traverser<K, V>(t, f, 0, f); Node<K, V> p;
/* 1344 */       while ((p = it.advance()) != null) {
/* 1345 */         V val = p.val;
/* 1346 */         Object v = m.get(p.key);
/* 1347 */         if (v == null || (v != val && !v.equals(val)))
/* 1348 */           return false; 
/*      */       } 
/* 1350 */       for (Map.Entry<?, ?> e : m.entrySet()) {
/*      */         Object mk; Object mv; Object v;
/* 1352 */         if ((mk = e.getKey()) == null || (mv = e.getValue()) == null || (v = get(mk)) == null || (mv != v && !mv.equals(v)))
/*      */         {
/*      */ 
/*      */           
/* 1356 */           return false; } 
/*      */       } 
/*      */     } 
/* 1359 */     return true;
/*      */   }
/*      */   
/*      */   static class Segment<K, V>
/*      */     extends ReentrantLock
/*      */     implements Serializable {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     final float loadFactor;
/*      */     
/*      */     Segment(float lf) {
/* 1369 */       this.loadFactor = lf;
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
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1385 */     int sshift = 0;
/* 1386 */     int ssize = 1;
/* 1387 */     while (ssize < 16) {
/* 1388 */       sshift++;
/* 1389 */       ssize <<= 1;
/*      */     } 
/* 1391 */     int segmentShift = 32 - sshift;
/* 1392 */     int segmentMask = ssize - 1;
/* 1393 */     Segment[] arrayOfSegment = new Segment[16];
/*      */     
/* 1395 */     for (int i = 0; i < arrayOfSegment.length; i++)
/* 1396 */       arrayOfSegment[i] = new Segment<Object, Object>(0.75F); 
/* 1397 */     s.putFields().put("segments", arrayOfSegment);
/* 1398 */     s.putFields().put("segmentShift", segmentShift);
/* 1399 */     s.putFields().put("segmentMask", segmentMask);
/* 1400 */     s.writeFields();
/*      */     
/*      */     Node<K, V>[] t;
/* 1403 */     if ((t = this.table) != null) {
/* 1404 */       Traverser<K, V> it = new Traverser<K, V>(t, t.length, 0, t.length); Node<K, V> p;
/* 1405 */       while ((p = it.advance()) != null) {
/* 1406 */         s.writeObject(p.key);
/* 1407 */         s.writeObject(p.val);
/*      */       } 
/*      */     } 
/* 1410 */     s.writeObject(null);
/* 1411 */     s.writeObject(null);
/* 1412 */     arrayOfSegment = null;
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
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1428 */     this.sizeCtl = -1;
/* 1429 */     s.defaultReadObject();
/* 1430 */     long size = 0L;
/* 1431 */     Node<K, V> p = null;
/*      */     while (true) {
/* 1433 */       K k = (K)s.readObject();
/* 1434 */       V v = (V)s.readObject();
/* 1435 */       if (k != null && v != null) {
/* 1436 */         p = new Node<K, V>(spread(k.hashCode()), k, v, p);
/* 1437 */         size++;
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/* 1442 */     if (size == 0L) {
/* 1443 */       this.sizeCtl = 0;
/*      */     } else {
/*      */       int n;
/* 1446 */       if (size >= 536870912L) {
/* 1447 */         n = 1073741824;
/*      */       } else {
/* 1449 */         int sz = (int)size;
/* 1450 */         n = tableSizeFor(sz + (sz >>> 1) + 1);
/*      */       } 
/*      */       
/* 1453 */       Node[] arrayOfNode = new Node[n];
/* 1454 */       int mask = n - 1;
/* 1455 */       long added = 0L;
/* 1456 */       while (p != null) {
/*      */         boolean insertAtFront;
/* 1458 */         Node<K, V> next = p.next;
/* 1459 */         int h = p.hash, j = h & mask; Node<K, V> first;
/* 1460 */         if ((first = tabAt((Node<?, ?>[])arrayOfNode, j)) == null) {
/* 1461 */           insertAtFront = true;
/*      */         } else {
/* 1463 */           K k = p.key;
/* 1464 */           if (first.hash < 0) {
/* 1465 */             TreeBin<K, V> t = (TreeBin<K, V>)first;
/* 1466 */             if (t.putTreeVal(h, k, p.val) == null)
/* 1467 */               added++; 
/* 1468 */             insertAtFront = false;
/*      */           } else {
/*      */             
/* 1471 */             int binCount = 0;
/* 1472 */             insertAtFront = true;
/*      */             Node<K, V> q;
/* 1474 */             for (q = first; q != null; q = q.next) {
/* 1475 */               K qk; if (q.hash == h && ((qk = q.key) == k || (qk != null && k.equals(qk)))) {
/*      */ 
/*      */                 
/* 1478 */                 insertAtFront = false;
/*      */                 break;
/*      */               } 
/* 1481 */               binCount++;
/*      */             } 
/* 1483 */             if (insertAtFront && binCount >= 8) {
/* 1484 */               insertAtFront = false;
/* 1485 */               added++;
/* 1486 */               p.next = first;
/* 1487 */               TreeNode<K, V> hd = null, tl = null;
/* 1488 */               for (q = p; q != null; q = q.next) {
/* 1489 */                 TreeNode<K, V> t = new TreeNode<K, V>(q.hash, q.key, q.val, null, null);
/*      */                 
/* 1491 */                 if ((t.prev = tl) == null) {
/* 1492 */                   hd = t;
/*      */                 } else {
/* 1494 */                   tl.next = t;
/* 1495 */                 }  tl = t;
/*      */               } 
/* 1497 */               setTabAt((Node<?, ?>[])arrayOfNode, j, new TreeBin<Object, Object>(hd));
/*      */             } 
/*      */           } 
/*      */         } 
/* 1501 */         if (insertAtFront) {
/* 1502 */           added++;
/* 1503 */           p.next = first;
/* 1504 */           setTabAt((Node<K, V>[])arrayOfNode, j, p);
/*      */         } 
/* 1506 */         p = next;
/*      */       } 
/* 1508 */       this.table = (Node<K, V>[])arrayOfNode;
/* 1509 */       this.sizeCtl = n - (n >>> 2);
/* 1510 */       this.baseCount = added;
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
/*      */   public V putIfAbsent(K key, V value) {
/* 1524 */     return putVal(key, value, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object key, Object value) {
/* 1533 */     if (key == null)
/* 1534 */       throw new NullPointerException(); 
/* 1535 */     return (value != null && replaceNode(key, null, value) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K key, V oldValue, V newValue) {
/* 1544 */     if (key == null || oldValue == null || newValue == null)
/* 1545 */       throw new NullPointerException(); 
/* 1546 */     return (replaceNode(key, newValue, oldValue) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(K key, V value) {
/* 1557 */     if (key == null || value == null)
/* 1558 */       throw new NullPointerException(); 
/* 1559 */     return replaceNode(key, value, null);
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
/*      */   public V getOrDefault(Object key, V defaultValue) {
/*      */     V v;
/* 1577 */     return ((v = get(key)) == null) ? defaultValue : v;
/*      */   }
/*      */   
/*      */   public void forEach(BiAction<? super K, ? super V> action) {
/* 1581 */     if (action == null) throw new NullPointerException(); 
/*      */     Node<K, V>[] t;
/* 1583 */     if ((t = this.table) != null) {
/* 1584 */       Traverser<K, V> it = new Traverser<K, V>(t, t.length, 0, t.length); Node<K, V> p;
/* 1585 */       while ((p = it.advance()) != null) {
/* 1586 */         action.apply(p.key, p.val);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void replaceAll(BiFun<? super K, ? super V, ? extends V> function) {
/* 1592 */     if (function == null) throw new NullPointerException(); 
/*      */     Node<K, V>[] t;
/* 1594 */     if ((t = this.table) != null) {
/* 1595 */       Traverser<K, V> it = new Traverser<K, V>(t, t.length, 0, t.length); Node<K, V> p;
/* 1596 */       label22: while ((p = it.advance()) != null) {
/* 1597 */         V oldValue = p.val;
/* 1598 */         K key = p.key; while (true) {
/* 1599 */           V newValue = function.apply(key, oldValue);
/* 1600 */           if (newValue == null)
/* 1601 */             throw new NullPointerException(); 
/* 1602 */           if (replaceNode(key, newValue, oldValue) == null) { if ((oldValue = get(key)) == null) {
/*      */               continue label22;
/*      */             }
/*      */             continue; }
/*      */           
/*      */           continue label22;
/*      */         } 
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(K key, Fun<? super K, ? extends V> mappingFunction) {
/* 1633 */     if (key == null || mappingFunction == null)
/* 1634 */       throw new NullPointerException(); 
/* 1635 */     int h = spread(key.hashCode());
/* 1636 */     V val = null;
/* 1637 */     int binCount = 0;
/* 1638 */     Node<K, V>[] tab = this.table; while (true) {
/*      */       Node[] arrayOfNode; int n;
/* 1640 */       if (tab == null || (n = tab.length) == 0) {
/* 1641 */         arrayOfNode = (Node[])initTable(); continue;
/* 1642 */       }  int i; Node<K, V> f; if ((f = tabAt((Node<?, ?>[])arrayOfNode, i = n - 1 & h)) == null) {
/* 1643 */         Node<K, V> r = new ReservationNode<K, V>();
/* 1644 */         synchronized (r) {
/* 1645 */           if (casTabAt((Node<K, V>[])arrayOfNode, i, null, r)) {
/* 1646 */             binCount = 1;
/* 1647 */             Node<K, V> node = null;
/*      */             try {
/* 1649 */               if ((val = mappingFunction.apply(key)) != null)
/* 1650 */                 node = new Node<K, V>(h, key, val, null); 
/*      */             } finally {
/* 1652 */               setTabAt((Node<K, V>[])arrayOfNode, i, node);
/*      */             } 
/*      */           } 
/*      */         } 
/* 1656 */         if (binCount != 0)
/*      */           break;  continue;
/*      */       }  int fh;
/* 1659 */       if ((fh = f.hash) == -1) {
/* 1660 */         arrayOfNode = (Node[])helpTransfer((Node<K, V>[])arrayOfNode, f); continue;
/*      */       } 
/* 1662 */       boolean added = false;
/* 1663 */       synchronized (f) {
/* 1664 */         if (tabAt((Node<K, V>[])arrayOfNode, i) == f) {
/* 1665 */           if (fh >= 0) {
/* 1666 */             binCount = 1;
/* 1667 */             for (Node<K, V> e = f;; binCount++) {
/*      */               K ek;
/* 1669 */               if (e.hash == h && ((ek = e.key) == key || (ek != null && key.equals(ek)))) {
/*      */ 
/*      */                 
/* 1672 */                 val = e.val;
/*      */                 break;
/*      */               } 
/* 1675 */               Node<K, V> pred = e;
/* 1676 */               if ((e = e.next) == null) {
/* 1677 */                 if ((val = mappingFunction.apply(key)) != null) {
/* 1678 */                   added = true;
/* 1679 */                   pred.next = new Node<K, V>(h, key, val, null);
/*      */                 } 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1685 */           } else if (f instanceof TreeBin) {
/* 1686 */             binCount = 2;
/* 1687 */             TreeBin<K, V> t = (TreeBin<K, V>)f;
/*      */             TreeNode<K, V> r, p;
/* 1689 */             if ((r = t.root) != null && (p = r.findTreeNode(h, key, (Class<?>)null)) != null) {
/*      */               
/* 1691 */               val = p.val;
/* 1692 */             } else if ((val = mappingFunction.apply(key)) != null) {
/* 1693 */               added = true;
/* 1694 */               t.putTreeVal(h, key, val);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/* 1699 */       if (binCount != 0) {
/* 1700 */         if (binCount >= 8)
/* 1701 */           treeifyBin((Node<K, V>[])arrayOfNode, i); 
/* 1702 */         if (!added) {
/* 1703 */           return val;
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 1708 */     if (val != null)
/* 1709 */       addCount(1L, binCount); 
/* 1710 */     return val;
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
/*      */ 
/*      */   
/*      */   public V computeIfPresent(K key, BiFun<? super K, ? super V, ? extends V> remappingFunction) {
/* 1734 */     if (key == null || remappingFunction == null)
/* 1735 */       throw new NullPointerException(); 
/* 1736 */     int h = spread(key.hashCode());
/* 1737 */     V val = null;
/* 1738 */     int delta = 0;
/* 1739 */     int binCount = 0;
/* 1740 */     Node<K, V>[] tab = this.table; while (true) {
/*      */       Node[] arrayOfNode; int n;
/* 1742 */       if (tab == null || (n = tab.length) == 0) {
/* 1743 */         arrayOfNode = (Node[])initTable(); continue;
/* 1744 */       }  int i; Node<K, V> f; if ((f = tabAt((Node<?, ?>[])arrayOfNode, i = n - 1 & h)) == null)
/*      */         break;  int fh;
/* 1746 */       if ((fh = f.hash) == -1) {
/* 1747 */         arrayOfNode = (Node[])helpTransfer((Node<K, V>[])arrayOfNode, f); continue;
/*      */       } 
/* 1749 */       synchronized (f) {
/* 1750 */         if (tabAt((Node<K, V>[])arrayOfNode, i) == f)
/* 1751 */           if (fh >= 0) {
/* 1752 */             binCount = 1;
/* 1753 */             for (Node<K, V> e = f, pred = null;; binCount++) {
/*      */               K ek;
/* 1755 */               if (e.hash == h && ((ek = e.key) == key || (ek != null && key.equals(ek)))) {
/*      */ 
/*      */                 
/* 1758 */                 val = remappingFunction.apply(key, e.val);
/* 1759 */                 if (val != null) {
/* 1760 */                   e.val = val; break;
/*      */                 } 
/* 1762 */                 delta = -1;
/* 1763 */                 Node<K, V> en = e.next;
/* 1764 */                 if (pred != null) {
/* 1765 */                   pred.next = en; break;
/*      */                 } 
/* 1767 */                 setTabAt((Node<K, V>[])arrayOfNode, i, en);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1771 */               pred = e;
/* 1772 */               if ((e = e.next) == null) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */           } else {
/* 1777 */             binCount = 2;
/* 1778 */             TreeBin<K, V> t = (TreeBin<K, V>)f;
/*      */             TreeNode<K, V> r, p;
/* 1780 */             if (f instanceof TreeBin && (r = t.root) != null && (p = r.findTreeNode(h, key, (Class<?>)null)) != null) {
/*      */               
/* 1782 */               val = remappingFunction.apply(key, p.val);
/* 1783 */               if (val != null) {
/* 1784 */                 p.val = val;
/*      */               } else {
/* 1786 */                 delta = -1;
/* 1787 */                 if (t.removeTreeNode(p)) {
/* 1788 */                   setTabAt((Node<?, ?>[])arrayOfNode, i, untreeify(t.first));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           }  
/*      */       } 
/* 1794 */       if (binCount != 0) {
/*      */         break;
/*      */       }
/*      */     } 
/* 1798 */     if (delta != 0)
/* 1799 */       addCount(delta, binCount); 
/* 1800 */     return val;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(K key, BiFun<? super K, ? super V, ? extends V> remappingFunction) {
/* 1825 */     if (key == null || remappingFunction == null)
/* 1826 */       throw new NullPointerException(); 
/* 1827 */     int h = spread(key.hashCode());
/* 1828 */     V val = null;
/* 1829 */     int delta = 0;
/* 1830 */     int binCount = 0;
/* 1831 */     Node<K, V>[] tab = this.table; while (true) {
/*      */       Node[] arrayOfNode; int n;
/* 1833 */       if (tab == null || (n = tab.length) == 0) {
/* 1834 */         arrayOfNode = (Node[])initTable(); continue;
/* 1835 */       }  int i; Node<K, V> f; if ((f = tabAt((Node<?, ?>[])arrayOfNode, i = n - 1 & h)) == null) {
/* 1836 */         Node<K, V> r = new ReservationNode<K, V>();
/* 1837 */         synchronized (r) {
/* 1838 */           if (casTabAt((Node<K, V>[])arrayOfNode, i, null, r)) {
/* 1839 */             binCount = 1;
/* 1840 */             Node<K, V> node = null;
/*      */             try {
/* 1842 */               if ((val = remappingFunction.apply(key, null)) != null) {
/* 1843 */                 delta = 1;
/* 1844 */                 node = new Node<K, V>(h, key, val, null);
/*      */               } 
/*      */             } finally {
/* 1847 */               setTabAt((Node<K, V>[])arrayOfNode, i, node);
/*      */             } 
/*      */           } 
/*      */         } 
/* 1851 */         if (binCount != 0)
/*      */           break;  continue;
/*      */       }  int fh;
/* 1854 */       if ((fh = f.hash) == -1) {
/* 1855 */         arrayOfNode = (Node[])helpTransfer((Node<K, V>[])arrayOfNode, f); continue;
/*      */       } 
/* 1857 */       synchronized (f) {
/* 1858 */         if (tabAt((Node<K, V>[])arrayOfNode, i) == f)
/* 1859 */           if (fh >= 0) {
/* 1860 */             binCount = 1;
/* 1861 */             for (Node<K, V> e = f, pred = null;; binCount++) {
/*      */               K ek;
/* 1863 */               if (e.hash == h && ((ek = e.key) == key || (ek != null && key.equals(ek)))) {
/*      */ 
/*      */                 
/* 1866 */                 val = remappingFunction.apply(key, e.val);
/* 1867 */                 if (val != null) {
/* 1868 */                   e.val = val; break;
/*      */                 } 
/* 1870 */                 delta = -1;
/* 1871 */                 Node<K, V> en = e.next;
/* 1872 */                 if (pred != null) {
/* 1873 */                   pred.next = en; break;
/*      */                 } 
/* 1875 */                 setTabAt((Node<K, V>[])arrayOfNode, i, en);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1879 */               pred = e;
/* 1880 */               if ((e = e.next) == null) {
/* 1881 */                 val = remappingFunction.apply(key, null);
/* 1882 */                 if (val != null) {
/* 1883 */                   delta = 1;
/* 1884 */                   pred.next = new Node<K, V>(h, key, val, null);
/*      */                 } 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1891 */           } else if (f instanceof TreeBin) {
/* 1892 */             TreeNode<K, V> p; binCount = 1;
/* 1893 */             TreeBin<K, V> t = (TreeBin<K, V>)f;
/*      */             TreeNode<K, V> r;
/* 1895 */             if ((r = t.root) != null) {
/* 1896 */               p = r.findTreeNode(h, key, (Class<?>)null);
/*      */             } else {
/* 1898 */               p = null;
/* 1899 */             }  V pv = (p == null) ? null : p.val;
/* 1900 */             val = remappingFunction.apply(key, pv);
/* 1901 */             if (val != null) {
/* 1902 */               if (p != null) {
/* 1903 */                 p.val = val;
/*      */               } else {
/* 1905 */                 delta = 1;
/* 1906 */                 t.putTreeVal(h, key, val);
/*      */               }
/*      */             
/* 1909 */             } else if (p != null) {
/* 1910 */               delta = -1;
/* 1911 */               if (t.removeTreeNode(p)) {
/* 1912 */                 setTabAt((Node<?, ?>[])arrayOfNode, i, untreeify(t.first));
/*      */               }
/*      */             } 
/*      */           }  
/*      */       } 
/* 1917 */       if (binCount != 0) {
/* 1918 */         if (binCount >= 8) {
/* 1919 */           treeifyBin((Node<K, V>[])arrayOfNode, i);
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 1924 */     if (delta != 0)
/* 1925 */       addCount(delta, binCount); 
/* 1926 */     return val;
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
/*      */ 
/*      */   
/*      */   public V merge(K key, V value, BiFun<? super V, ? super V, ? extends V> remappingFunction) {
/* 1950 */     if (key == null || value == null || remappingFunction == null)
/* 1951 */       throw new NullPointerException(); 
/* 1952 */     int h = spread(key.hashCode());
/* 1953 */     V val = null;
/* 1954 */     int delta = 0;
/* 1955 */     int binCount = 0;
/* 1956 */     Node<K, V>[] tab = this.table; while (true) {
/*      */       Node[] arrayOfNode; int n;
/* 1958 */       if (tab == null || (n = tab.length) == 0) {
/* 1959 */         arrayOfNode = (Node[])initTable(); continue;
/* 1960 */       }  int i; Node<K, V> f; if ((f = tabAt((Node<?, ?>[])arrayOfNode, i = n - 1 & h)) == null) {
/* 1961 */         if (casTabAt((Node<?, ?>[])arrayOfNode, i, null, new Node<Object, Object>(h, key, value, null))) {
/* 1962 */           delta = 1;
/* 1963 */           val = value; break;
/*      */         }  continue;
/*      */       } 
/*      */       int fh;
/* 1967 */       if ((fh = f.hash) == -1) {
/* 1968 */         arrayOfNode = (Node[])helpTransfer((Node<K, V>[])arrayOfNode, f); continue;
/*      */       } 
/* 1970 */       synchronized (f) {
/* 1971 */         if (tabAt((Node<K, V>[])arrayOfNode, i) == f)
/* 1972 */           if (fh >= 0) {
/* 1973 */             binCount = 1;
/* 1974 */             for (Node<K, V> e = f, pred = null;; binCount++) {
/*      */               K ek;
/* 1976 */               if (e.hash == h && ((ek = e.key) == key || (ek != null && key.equals(ek)))) {
/*      */ 
/*      */                 
/* 1979 */                 val = remappingFunction.apply(e.val, value);
/* 1980 */                 if (val != null) {
/* 1981 */                   e.val = val; break;
/*      */                 } 
/* 1983 */                 delta = -1;
/* 1984 */                 Node<K, V> en = e.next;
/* 1985 */                 if (pred != null) {
/* 1986 */                   pred.next = en; break;
/*      */                 } 
/* 1988 */                 setTabAt((Node<K, V>[])arrayOfNode, i, en);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1992 */               pred = e;
/* 1993 */               if ((e = e.next) == null) {
/* 1994 */                 delta = 1;
/* 1995 */                 val = value;
/* 1996 */                 pred.next = new Node<K, V>(h, key, val, null);
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 2002 */           } else if (f instanceof TreeBin) {
/* 2003 */             binCount = 2;
/* 2004 */             TreeBin<K, V> t = (TreeBin<K, V>)f;
/* 2005 */             TreeNode<K, V> r = t.root;
/* 2006 */             TreeNode<K, V> p = (r == null) ? null : r.findTreeNode(h, key, (Class<?>)null);
/*      */             
/* 2008 */             val = (p == null) ? value : remappingFunction.apply(p.val, value);
/*      */             
/* 2010 */             if (val != null) {
/* 2011 */               if (p != null) {
/* 2012 */                 p.val = val;
/*      */               } else {
/* 2014 */                 delta = 1;
/* 2015 */                 t.putTreeVal(h, key, val);
/*      */               }
/*      */             
/* 2018 */             } else if (p != null) {
/* 2019 */               delta = -1;
/* 2020 */               if (t.removeTreeNode(p)) {
/* 2021 */                 setTabAt((Node<?, ?>[])arrayOfNode, i, untreeify(t.first));
/*      */               }
/*      */             } 
/*      */           }  
/*      */       } 
/* 2026 */       if (binCount != 0) {
/* 2027 */         if (binCount >= 8) {
/* 2028 */           treeifyBin((Node<K, V>[])arrayOfNode, i);
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/* 2033 */     if (delta != 0)
/* 2034 */       addCount(delta, binCount); 
/* 2035 */     return val;
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
/*      */   @Deprecated
/*      */   public boolean contains(Object value) {
/* 2056 */     return containsValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<K> keys() {
/*      */     Node<K, V>[] t;
/* 2067 */     int f = ((t = this.table) == null) ? 0 : t.length;
/* 2068 */     return new KeyIterator<K, V>(t, f, 0, f, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<V> elements() {
/*      */     Node<K, V>[] t;
/* 2079 */     int f = ((t = this.table) == null) ? 0 : t.length;
/* 2080 */     return new ValueIterator<K, V>(t, f, 0, f, this);
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
/*      */   public long mappingCount() {
/* 2096 */     long n = sumCount();
/* 2097 */     return (n < 0L) ? 0L : n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> KeySetView<K, Boolean> newKeySet() {
/* 2108 */     return new KeySetView<K, Boolean>(new ConcurrentHashMapV8<K, Boolean>(), Boolean.TRUE);
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
/*      */   public static <K> KeySetView<K, Boolean> newKeySet(int initialCapacity) {
/* 2124 */     return new KeySetView<K, Boolean>(new ConcurrentHashMapV8<K, Boolean>(initialCapacity), Boolean.TRUE);
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
/*      */   public KeySetView<K, V> keySet(V mappedValue) {
/* 2140 */     if (mappedValue == null)
/* 2141 */       throw new NullPointerException(); 
/* 2142 */     return new KeySetView<K, V>(this, mappedValue);
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForwardingNode<K, V>
/*      */     extends Node<K, V>
/*      */   {
/*      */     final ConcurrentHashMapV8.Node<K, V>[] nextTable;
/*      */ 
/*      */     
/*      */     ForwardingNode(ConcurrentHashMapV8.Node<K, V>[] tab) {
/* 2153 */       super(-1, null, null, null);
/* 2154 */       this.nextTable = tab;
/*      */     }
/*      */ 
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
/* 2159 */       ConcurrentHashMapV8.Node<K, V>[] tab = this.nextTable; label27: while (true) {
/*      */         int n; ConcurrentHashMapV8.Node<K, V> e;
/* 2161 */         if (k == null || tab == null || (n = tab.length) == 0 || (e = ConcurrentHashMapV8.<K, V>tabAt(tab, n - 1 & h)) == null)
/*      */         {
/* 2163 */           return null; }  while (true) {
/*      */           int eh;
/*      */           K ek;
/* 2166 */           if ((eh = e.hash) == h && ((ek = e.key) == k || (ek != null && k.equals(ek))))
/*      */           {
/* 2168 */             return e; } 
/* 2169 */           if (eh < 0) {
/* 2170 */             if (e instanceof ForwardingNode) {
/* 2171 */               tab = ((ForwardingNode)e).nextTable;
/*      */               
/*      */               continue label27;
/*      */             } 
/* 2175 */             return e.find(h, k);
/*      */           } 
/* 2177 */           if ((e = e.next) == null)
/* 2178 */             return null; 
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReservationNode<K, V>
/*      */     extends Node<K, V>
/*      */   {
/*      */     ReservationNode() {
/* 2189 */       super(-3, null, null, null);
/*      */     }
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
/* 2193 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Node<K, V>[] initTable() {
/*      */     Node[] arrayOfNode;
/*      */     Node<K, V>[] tab;
/* 2204 */     while ((tab = this.table) == null || tab.length == 0) {
/* 2205 */       int sc; if ((sc = this.sizeCtl) < 0) {
/* 2206 */         Thread.yield(); continue;
/* 2207 */       }  if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
/*      */         try {
/* 2209 */           if ((tab = this.table) == null || tab.length == 0) {
/* 2210 */             int n = (sc > 0) ? sc : 16;
/*      */             
/* 2212 */             Node[] arrayOfNode1 = new Node[n];
/* 2213 */             this.table = (Node<K, V>[])(arrayOfNode = arrayOfNode1);
/* 2214 */             sc = n - (n >>> 2);
/*      */           } 
/*      */         } finally {
/* 2217 */           this.sizeCtl = sc;
/*      */         } 
/*      */         break;
/*      */       } 
/*      */     } 
/* 2222 */     return (Node<K, V>[])arrayOfNode;
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
/*      */   private final void addCount(long x, int check) {
/*      */     CounterCell[] as;
/*      */     long b;
/*      */     long s;
/* 2237 */     if ((as = this.counterCells) != null || !U.compareAndSwapLong(this, BASECOUNT, b = this.baseCount, s = b + x)) {
/*      */ 
/*      */       
/* 2240 */       boolean uncontended = true; CounterHashCode hc; int m; CounterCell a; long v;
/* 2241 */       if ((hc = threadCounterHashCode.get()) == null || as == null || (m = as.length - 1) < 0 || (a = as[m & hc.code]) == null || !(uncontended = U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2246 */         fullAddCount(x, hc, uncontended);
/*      */         return;
/*      */       } 
/* 2249 */       if (check <= 1)
/*      */         return; 
/* 2251 */       s = sumCount();
/*      */     } 
/* 2253 */     if (check >= 0) {
/*      */       Node<K, V>[] tab; int sc;
/* 2255 */       while (s >= (sc = this.sizeCtl) && (tab = this.table) != null && tab.length < 1073741824) {
/*      */         
/* 2257 */         if (sc < 0) {
/* 2258 */           Node<K, V>[] nt; if (sc == -1 || this.transferIndex <= this.transferOrigin || (nt = this.nextTable) == null) {
/*      */             break;
/*      */           }
/* 2261 */           if (U.compareAndSwapInt(this, SIZECTL, sc, sc - 1)) {
/* 2262 */             transfer(tab, nt);
/*      */           }
/* 2264 */         } else if (U.compareAndSwapInt(this, SIZECTL, sc, -2)) {
/* 2265 */           transfer(tab, null);
/* 2266 */         }  s = sumCount();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Node<K, V>[] helpTransfer(Node<K, V>[] tab, Node<K, V> f) {
/*      */     Node[] arrayOfNode;
/* 2276 */     if (f instanceof ForwardingNode && (arrayOfNode = ((ForwardingNode)f).nextTable) != null) {
/*      */       int sc;
/* 2278 */       if (arrayOfNode == this.nextTable && tab == this.table && this.transferIndex > this.transferOrigin && (sc = this.sizeCtl) < -1 && U.compareAndSwapInt(this, SIZECTL, sc, sc - 1))
/*      */       {
/*      */         
/* 2281 */         transfer(tab, (Node<K, V>[])arrayOfNode); } 
/* 2282 */       return (Node<K, V>[])arrayOfNode;
/*      */     } 
/* 2284 */     return this.table;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void tryPresize(int size) {
/* 2293 */     int c = (size >= 536870912) ? 1073741824 : tableSizeFor(size + (size >>> 1) + 1);
/*      */     
/*      */     int sc;
/* 2296 */     while ((sc = this.sizeCtl) >= 0) {
/* 2297 */       Node<K, V>[] tab = this.table; int n;
/* 2298 */       if (tab == null || (n = tab.length) == 0) {
/* 2299 */         n = (sc > c) ? sc : c;
/* 2300 */         if (U.compareAndSwapInt(this, SIZECTL, sc, -1))
/*      */           try {
/* 2302 */             if (this.table == tab) {
/*      */               
/* 2304 */               Node[] arrayOfNode = new Node[n];
/* 2305 */               this.table = (Node<K, V>[])arrayOfNode;
/* 2306 */               sc = n - (n >>> 2);
/*      */             } 
/*      */           } finally {
/* 2309 */             this.sizeCtl = sc;
/*      */           }  
/*      */         continue;
/*      */       } 
/* 2313 */       if (c <= sc || n >= 1073741824)
/*      */         break; 
/* 2315 */       if (tab == this.table && U.compareAndSwapInt(this, SIZECTL, sc, -2))
/*      */       {
/* 2317 */         transfer(tab, null);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void transfer(Node<K, V>[] tab, Node<K, V>[] nextTab) {
/*      */     Node[] arrayOfNode;
/* 2326 */     int n = tab.length; int stride;
/* 2327 */     if ((stride = (NCPU > 1) ? ((n >>> 3) / NCPU) : n) < 16)
/* 2328 */       stride = 16; 
/* 2329 */     if (nextTab == null) {
/*      */       
/*      */       try {
/* 2332 */         Node[] arrayOfNode1 = new Node[n << 1];
/* 2333 */         arrayOfNode = arrayOfNode1;
/* 2334 */       } catch (Throwable ex) {
/* 2335 */         this.sizeCtl = Integer.MAX_VALUE;
/*      */         return;
/*      */       } 
/* 2338 */       this.nextTable = (Node<K, V>[])arrayOfNode;
/* 2339 */       this.transferOrigin = n;
/* 2340 */       this.transferIndex = n;
/* 2341 */       ForwardingNode<K, V> rev = new ForwardingNode<K, V>(tab);
/* 2342 */       for (int k = n; k > 0; ) {
/* 2343 */         int nextk = (k > stride) ? (k - stride) : 0; int m;
/* 2344 */         for (m = nextk; m < k; m++)
/* 2345 */           arrayOfNode[m] = rev; 
/* 2346 */         for (m = n + nextk; m < n + k; m++)
/* 2347 */           arrayOfNode[m] = rev; 
/* 2348 */         U.putOrderedInt(this, TRANSFERORIGIN, k = nextk);
/*      */       } 
/*      */     } 
/* 2351 */     int nextn = arrayOfNode.length;
/* 2352 */     ForwardingNode<K, V> fwd = new ForwardingNode<K, V>((Node<K, V>[])arrayOfNode);
/* 2353 */     boolean advance = true;
/* 2354 */     boolean finishing = false;
/* 2355 */     int i = 0, bound = 0;
/*      */     while (true) {
/* 2357 */       while (advance) {
/* 2358 */         if (--i >= bound || finishing) {
/* 2359 */           advance = false; continue;
/* 2360 */         }  int nextIndex; if ((nextIndex = this.transferIndex) <= this.transferOrigin) {
/* 2361 */           i = -1;
/* 2362 */           advance = false; continue;
/*      */         }  int nextBound;
/* 2364 */         if (U.compareAndSwapInt(this, TRANSFERINDEX, nextIndex, nextBound = (nextIndex > stride) ? (nextIndex - stride) : 0)) {
/*      */ 
/*      */ 
/*      */           
/* 2368 */           bound = nextBound;
/* 2369 */           i = nextIndex - 1;
/* 2370 */           advance = false;
/*      */         } 
/*      */       } 
/* 2373 */       if (i < 0 || i >= n || i + n >= nextn) {
/* 2374 */         if (finishing) {
/* 2375 */           this.nextTable = null;
/* 2376 */           this.table = (Node<K, V>[])arrayOfNode;
/* 2377 */           this.sizeCtl = (n << 1) - (n >>> 1); return;
/*      */         }  int sc;
/*      */         do {
/*      */         
/* 2381 */         } while (!U.compareAndSwapInt(this, SIZECTL, sc = this.sizeCtl, ++sc));
/* 2382 */         if (sc != -1)
/*      */           return; 
/* 2384 */         finishing = advance = true;
/* 2385 */         i = n;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       Node<K, V> f;
/* 2390 */       if ((f = tabAt(tab, i)) == null) {
/* 2391 */         if (casTabAt(tab, i, null, fwd)) {
/* 2392 */           setTabAt((Node<?, ?>[])arrayOfNode, i, null);
/* 2393 */           setTabAt((Node<?, ?>[])arrayOfNode, i + n, null);
/* 2394 */           advance = true;
/*      */         }  continue;
/*      */       }  int fh;
/* 2397 */       if ((fh = f.hash) == -1) {
/* 2398 */         advance = true; continue;
/*      */       } 
/* 2400 */       synchronized (f) {
/* 2401 */         if (tabAt(tab, i) == f)
/*      */         {
/* 2403 */           if (fh >= 0) {
/* 2404 */             Node<K, V> ln, hn; int runBit = fh & n;
/* 2405 */             Node<K, V> lastRun = f; Node<K, V> p;
/* 2406 */             for (p = f.next; p != null; p = p.next) {
/* 2407 */               int b = p.hash & n;
/* 2408 */               if (b != runBit) {
/* 2409 */                 runBit = b;
/* 2410 */                 lastRun = p;
/*      */               } 
/*      */             } 
/* 2413 */             if (runBit == 0) {
/* 2414 */               ln = lastRun;
/* 2415 */               hn = null;
/*      */             } else {
/*      */               
/* 2418 */               hn = lastRun;
/* 2419 */               ln = null;
/*      */             } 
/* 2421 */             for (p = f; p != lastRun; p = p.next) {
/* 2422 */               int ph = p.hash; K pk = p.key; V pv = p.val;
/* 2423 */               if ((ph & n) == 0) {
/* 2424 */                 ln = new Node<K, V>(ph, pk, pv, ln);
/*      */               } else {
/* 2426 */                 hn = new Node<K, V>(ph, pk, pv, hn);
/*      */               } 
/* 2428 */             }  setTabAt((Node<K, V>[])arrayOfNode, i, ln);
/* 2429 */             setTabAt((Node<K, V>[])arrayOfNode, i + n, hn);
/* 2430 */             setTabAt(tab, i, fwd);
/* 2431 */             advance = true;
/*      */           }
/* 2433 */           else if (f instanceof TreeBin) {
/* 2434 */             TreeBin<K, V> t = (TreeBin<K, V>)f;
/* 2435 */             TreeNode<K, V> lo = null, loTail = null;
/* 2436 */             TreeNode<K, V> hi = null, hiTail = null;
/* 2437 */             int lc = 0, hc = 0;
/* 2438 */             for (Node<K, V> e = t.first; e != null; e = e.next) {
/* 2439 */               int h = e.hash;
/* 2440 */               TreeNode<K, V> p = new TreeNode<K, V>(h, e.key, e.val, null, null);
/*      */               
/* 2442 */               if ((h & n) == 0) {
/* 2443 */                 if ((p.prev = loTail) == null) {
/* 2444 */                   lo = p;
/*      */                 } else {
/* 2446 */                   loTail.next = p;
/* 2447 */                 }  loTail = p;
/* 2448 */                 lc++;
/*      */               } else {
/*      */                 
/* 2451 */                 if ((p.prev = hiTail) == null) {
/* 2452 */                   hi = p;
/*      */                 } else {
/* 2454 */                   hiTail.next = p;
/* 2455 */                 }  hiTail = p;
/* 2456 */                 hc++;
/*      */               } 
/*      */             } 
/* 2459 */             Node<K, V> ln = (lc <= 6) ? untreeify(lo) : ((hc != 0) ? new TreeBin<K, V>(lo) : t);
/*      */             
/* 2461 */             Node<K, V> hn = (hc <= 6) ? untreeify(hi) : ((lc != 0) ? new TreeBin<K, V>(hi) : t);
/*      */             
/* 2463 */             setTabAt((Node<K, V>[])arrayOfNode, i, ln);
/* 2464 */             setTabAt((Node<K, V>[])arrayOfNode, i + n, hn);
/* 2465 */             setTabAt(tab, i, fwd);
/* 2466 */             advance = true;
/*      */           } 
/*      */         }
/*      */       } 
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
/*      */   private final void treeifyBin(Node<K, V>[] tab, int index) {
/* 2482 */     if (tab != null) {
/* 2483 */       int n; if ((n = tab.length) < 64) {
/* 2484 */         int sc; if (tab == this.table && (sc = this.sizeCtl) >= 0 && U.compareAndSwapInt(this, SIZECTL, sc, -2))
/*      */         {
/* 2486 */           transfer(tab, null); } 
/*      */       } else {
/* 2488 */         Node<K, V> b; if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
/* 2489 */           synchronized (b) {
/* 2490 */             if (tabAt(tab, index) == b) {
/* 2491 */               TreeNode<K, V> hd = null, tl = null;
/* 2492 */               for (Node<K, V> e = b; e != null; e = e.next) {
/* 2493 */                 TreeNode<K, V> p = new TreeNode<K, V>(e.hash, e.key, e.val, null, null);
/*      */ 
/*      */                 
/* 2496 */                 if ((p.prev = tl) == null) {
/* 2497 */                   hd = p;
/*      */                 } else {
/* 2499 */                   tl.next = p;
/* 2500 */                 }  tl = p;
/*      */               } 
/* 2502 */               setTabAt(tab, index, new TreeBin<K, V>(hd));
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> Node<K, V> untreeify(Node<K, V> b) {
/* 2513 */     Node<K, V> hd = null, tl = null;
/* 2514 */     for (Node<K, V> q = b; q != null; q = q.next) {
/* 2515 */       Node<K, V> p = new Node<K, V>(q.hash, q.key, q.val, null);
/* 2516 */       if (tl == null) {
/* 2517 */         hd = p;
/*      */       } else {
/* 2519 */         tl.next = p;
/* 2520 */       }  tl = p;
/*      */     } 
/* 2522 */     return hd;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class TreeNode<K, V>
/*      */     extends Node<K, V>
/*      */   {
/*      */     TreeNode<K, V> parent;
/*      */     
/*      */     TreeNode<K, V> left;
/*      */     
/*      */     TreeNode<K, V> right;
/*      */     
/*      */     TreeNode<K, V> prev;
/*      */     boolean red;
/*      */     
/*      */     TreeNode(int hash, K key, V val, ConcurrentHashMapV8.Node<K, V> next, TreeNode<K, V> parent) {
/* 2539 */       super(hash, key, val, next);
/* 2540 */       this.parent = parent;
/*      */     }
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
/* 2544 */       return findTreeNode(h, k, (Class<?>)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final TreeNode<K, V> findTreeNode(int h, Object k, Class<?> kc) {
/* 2552 */       if (k != null) {
/* 2553 */         TreeNode<K, V> p = this;
/*      */         
/*      */         do {
/* 2556 */           TreeNode<K, V> pl = p.left, pr = p.right; int ph;
/* 2557 */           if ((ph = p.hash) > h)
/* 2558 */           { p = pl; }
/* 2559 */           else if (ph < h)
/* 2560 */           { p = pr; }
/* 2561 */           else { K pk; if ((pk = p.key) == k || (pk != null && k.equals(pk)))
/* 2562 */               return p; 
/* 2563 */             if (pl == null && pr == null)
/*      */               break;  int dir;
/* 2565 */             if ((kc != null || (kc = ConcurrentHashMapV8.comparableClassFor(k)) != null) && (dir = ConcurrentHashMapV8.compareComparables(kc, k, pk)) != 0)
/*      */             
/*      */             { 
/* 2568 */               p = (dir < 0) ? pl : pr; }
/* 2569 */             else if (pl == null)
/* 2570 */             { p = pr; }
/* 2571 */             else { TreeNode<K, V> q; if (pr == null || (q = pr.findTreeNode(h, k, kc)) == null)
/*      */               
/* 2573 */               { p = pl; }
/*      */               else
/* 2575 */               { return q; }  }  } 
/* 2576 */         } while (p != null);
/*      */       } 
/* 2578 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class TreeBin<K, V>
/*      */     extends Node<K, V>
/*      */   {
/*      */     ConcurrentHashMapV8.TreeNode<K, V> root;
/*      */     
/*      */     volatile ConcurrentHashMapV8.TreeNode<K, V> first;
/*      */     
/*      */     volatile Thread waiter;
/*      */     
/*      */     volatile int lockState;
/*      */     
/*      */     static final int WRITER = 1;
/*      */     
/*      */     static final int WAITER = 2;
/*      */     
/*      */     static final int READER = 4;
/*      */     
/*      */     private static final Unsafe U;
/*      */     
/*      */     private static final long LOCKSTATE;
/*      */     
/*      */     TreeBin(ConcurrentHashMapV8.TreeNode<K, V> b) {
/* 2605 */       super(-2, null, null, null);
/* 2606 */       this.first = b;
/* 2607 */       ConcurrentHashMapV8.TreeNode<K, V> r = null;
/* 2608 */       for (ConcurrentHashMapV8.TreeNode<K, V> x = b; x != null; x = next) {
/* 2609 */         ConcurrentHashMapV8.TreeNode<K, V> next = (ConcurrentHashMapV8.TreeNode<K, V>)x.next;
/* 2610 */         x.left = x.right = null;
/* 2611 */         if (r == null) {
/* 2612 */           x.parent = null;
/* 2613 */           x.red = false;
/* 2614 */           r = x;
/*      */         } else {
/*      */           
/* 2617 */           Object key = x.key;
/* 2618 */           int hash = x.hash;
/* 2619 */           Class<?> kc = null;
/* 2620 */           ConcurrentHashMapV8.TreeNode<K, V> p = r; while (true) {
/*      */             int dir, ph;
/* 2622 */             if ((ph = p.hash) > hash) {
/* 2623 */               dir = -1;
/* 2624 */             } else if (ph < hash) {
/* 2625 */               dir = 1;
/* 2626 */             } else if (kc != null || (kc = ConcurrentHashMapV8.comparableClassFor(key)) != null) {
/*      */               
/* 2628 */               dir = ConcurrentHashMapV8.compareComparables(kc, key, p.key);
/*      */             } else {
/* 2630 */               dir = 0;
/* 2631 */             }  ConcurrentHashMapV8.TreeNode<K, V> xp = p;
/* 2632 */             if ((p = (ConcurrentHashMapV8.TreeNode<K, V>)((dir <= 0) ? p.left : p.right)) == null) {
/* 2633 */               x.parent = xp;
/* 2634 */               if (dir <= 0) {
/* 2635 */                 xp.left = x;
/*      */               } else {
/* 2637 */                 xp.right = x;
/* 2638 */               }  r = balanceInsertion(r, x);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2644 */       this.root = r;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final void lockRoot() {
/* 2651 */       if (!U.compareAndSwapInt(this, LOCKSTATE, 0, 1)) {
/* 2652 */         contendedLock();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private final void unlockRoot() {
/* 2659 */       this.lockState = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final void contendedLock() {
/* 2666 */       boolean waiting = false; while (true) {
/*      */         int s;
/* 2668 */         while (((s = this.lockState) & 0x1) == 0) {
/* 2669 */           if (U.compareAndSwapInt(this, LOCKSTATE, s, 1)) {
/* 2670 */             if (waiting)
/* 2671 */               this.waiter = null; 
/*      */             return;
/*      */           } 
/*      */         } 
/* 2675 */         if ((s | 0x2) == 0) {
/* 2676 */           if (U.compareAndSwapInt(this, LOCKSTATE, s, s | 0x2)) {
/* 2677 */             waiting = true;
/* 2678 */             this.waiter = Thread.currentThread();
/*      */           }  continue;
/*      */         } 
/* 2681 */         if (waiting) {
/* 2682 */           LockSupport.park(this);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
/* 2692 */       if (k != null) {
/* 2693 */         for (ConcurrentHashMapV8.Node<K, V> e = this.first; e != null; e = e.next) {
/*      */           int s;
/* 2695 */           if (((s = this.lockState) & 0x3) != 0) {
/* 2696 */             K ek; if (e.hash == h && ((ek = e.key) == k || (ek != null && k.equals(ek))))
/*      */             {
/* 2698 */               return e;
/*      */             }
/* 2700 */           } else if (U.compareAndSwapInt(this, LOCKSTATE, s, s + 4)) {
/*      */             ConcurrentHashMapV8.TreeNode<K, V> p;
/*      */             try {
/*      */               ConcurrentHashMapV8.TreeNode<K, V> r;
/* 2704 */               p = ((r = this.root) == null) ? null : r.findTreeNode(h, k, (Class<?>)null);
/*      */             } finally {
/*      */               int ls;
/*      */               do {
/*      */               
/* 2709 */               } while (!U.compareAndSwapInt(this, LOCKSTATE, ls = this.lockState, ls - 4));
/*      */               
/*      */               Thread w;
/* 2712 */               if (ls == 6 && (w = this.waiter) != null)
/* 2713 */                 LockSupport.unpark(w); 
/*      */             } 
/* 2715 */             return p;
/*      */           } 
/*      */         } 
/*      */       }
/* 2719 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentHashMapV8.TreeNode<K, V> putTreeVal(int h, K k, V v) {
/* 2727 */       Class<?> kc = null;
/* 2728 */       ConcurrentHashMapV8.TreeNode<K, V> p = this.root; while (true) {
/*      */         int dir;
/* 2730 */         if (p == null) {
/* 2731 */           this.first = this.root = new ConcurrentHashMapV8.TreeNode<K, V>(h, k, v, null, null); break;
/*      */         } 
/*      */         int ph;
/* 2734 */         if ((ph = p.hash) > h)
/* 2735 */         { dir = -1; }
/* 2736 */         else if (ph < h)
/* 2737 */         { dir = 1; }
/* 2738 */         else { K pk; if ((pk = p.key) == k || (pk != null && k.equals(pk)))
/* 2739 */             return p; 
/* 2740 */           if ((kc == null && (kc = ConcurrentHashMapV8.comparableClassFor(k)) == null) || (dir = ConcurrentHashMapV8.compareComparables(kc, k, pk)) == 0)
/*      */           {
/*      */             
/* 2743 */             if (p.left == null)
/* 2744 */             { dir = 1; }
/* 2745 */             else { ConcurrentHashMapV8.TreeNode<K, V> pr; ConcurrentHashMapV8.TreeNode<K, V> q; if ((pr = p.right) == null || (q = pr.findTreeNode(h, k, kc)) == null)
/*      */               
/* 2747 */               { dir = -1; }
/*      */               else
/* 2749 */               { return q; }  }
/*      */              }  }
/* 2751 */          ConcurrentHashMapV8.TreeNode<K, V> xp = p;
/* 2752 */         if ((p = (ConcurrentHashMapV8.TreeNode<K, V>)((dir < 0) ? p.left : p.right)) == null) {
/* 2753 */           ConcurrentHashMapV8.TreeNode<K, V> f = this.first;
/* 2754 */           ConcurrentHashMapV8.TreeNode<K, V> x = new ConcurrentHashMapV8.TreeNode<K, V>(h, k, v, f, xp);
/* 2755 */           if (f != null)
/* 2756 */             f.prev = x; 
/* 2757 */           if (dir < 0) {
/* 2758 */             xp.left = x;
/*      */           } else {
/* 2760 */             xp.right = x;
/* 2761 */           }  if (!xp.red) {
/* 2762 */             x.red = true; break;
/*      */           } 
/* 2764 */           lockRoot();
/*      */           try {
/* 2766 */             this.root = balanceInsertion(this.root, x);
/*      */           } finally {
/* 2768 */             unlockRoot();
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 2774 */       assert checkInvariants(this.root);
/* 2775 */       return null;
/*      */     }
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
/*      */     final boolean removeTreeNode(ConcurrentHashMapV8.TreeNode<K, V> p) {
/* 2789 */       ConcurrentHashMapV8.TreeNode<K, V> next = (ConcurrentHashMapV8.TreeNode<K, V>)p.next;
/* 2790 */       ConcurrentHashMapV8.TreeNode<K, V> pred = p.prev;
/*      */       
/* 2792 */       if (pred == null) {
/* 2793 */         this.first = next;
/*      */       } else {
/* 2795 */         pred.next = next;
/* 2796 */       }  if (next != null)
/* 2797 */         next.prev = pred; 
/* 2798 */       if (this.first == null) {
/* 2799 */         this.root = null;
/* 2800 */         return true;
/*      */       }  ConcurrentHashMapV8.TreeNode<K, V> r, rl;
/* 2802 */       if ((r = this.root) == null || r.right == null || (rl = r.left) == null || rl.left == null)
/*      */       {
/* 2804 */         return true; } 
/* 2805 */       lockRoot();
/*      */       
/*      */       try {
/* 2808 */         ConcurrentHashMapV8.TreeNode<K, V> replacement, pl = p.left;
/* 2809 */         ConcurrentHashMapV8.TreeNode<K, V> pr = p.right;
/* 2810 */         if (pl != null && pr != null) {
/* 2811 */           ConcurrentHashMapV8.TreeNode<K, V> s = pr; ConcurrentHashMapV8.TreeNode<K, V> sl;
/* 2812 */           while ((sl = s.left) != null)
/* 2813 */             s = sl; 
/* 2814 */           boolean c = s.red; s.red = p.red; p.red = c;
/* 2815 */           ConcurrentHashMapV8.TreeNode<K, V> sr = s.right;
/* 2816 */           ConcurrentHashMapV8.TreeNode<K, V> pp = p.parent;
/* 2817 */           if (s == pr) {
/* 2818 */             p.parent = s;
/* 2819 */             s.right = p;
/*      */           } else {
/*      */             
/* 2822 */             ConcurrentHashMapV8.TreeNode<K, V> sp = s.parent;
/* 2823 */             if ((p.parent = sp) != null)
/* 2824 */               if (s == sp.left) {
/* 2825 */                 sp.left = p;
/*      */               } else {
/* 2827 */                 sp.right = p;
/*      */               }  
/* 2829 */             if ((s.right = pr) != null)
/* 2830 */               pr.parent = s; 
/*      */           } 
/* 2832 */           p.left = null;
/* 2833 */           if ((p.right = sr) != null)
/* 2834 */             sr.parent = p; 
/* 2835 */           if ((s.left = pl) != null)
/* 2836 */             pl.parent = s; 
/* 2837 */           if ((s.parent = pp) == null) {
/* 2838 */             r = s;
/* 2839 */           } else if (p == pp.left) {
/* 2840 */             pp.left = s;
/*      */           } else {
/* 2842 */             pp.right = s;
/* 2843 */           }  if (sr != null) {
/* 2844 */             replacement = sr;
/*      */           } else {
/* 2846 */             replacement = p;
/*      */           } 
/* 2848 */         } else if (pl != null) {
/* 2849 */           replacement = pl;
/* 2850 */         } else if (pr != null) {
/* 2851 */           replacement = pr;
/*      */         } else {
/* 2853 */           replacement = p;
/* 2854 */         }  if (replacement != p) {
/* 2855 */           ConcurrentHashMapV8.TreeNode<K, V> pp = replacement.parent = p.parent;
/* 2856 */           if (pp == null) {
/* 2857 */             r = replacement;
/* 2858 */           } else if (p == pp.left) {
/* 2859 */             pp.left = replacement;
/*      */           } else {
/* 2861 */             pp.right = replacement;
/* 2862 */           }  p.left = p.right = p.parent = null;
/*      */         } 
/*      */         
/* 2865 */         this.root = p.red ? r : balanceDeletion(r, replacement);
/*      */         
/* 2867 */         if (p == replacement) {
/*      */           ConcurrentHashMapV8.TreeNode<K, V> pp;
/* 2869 */           if ((pp = p.parent) != null) {
/* 2870 */             if (p == pp.left) {
/* 2871 */               pp.left = null;
/* 2872 */             } else if (p == pp.right) {
/* 2873 */               pp.right = null;
/* 2874 */             }  p.parent = null;
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 2878 */         unlockRoot();
/*      */       } 
/* 2880 */       assert checkInvariants(this.root);
/* 2881 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> ConcurrentHashMapV8.TreeNode<K, V> rotateLeft(ConcurrentHashMapV8.TreeNode<K, V> root, ConcurrentHashMapV8.TreeNode<K, V> p) {
/*      */       ConcurrentHashMapV8.TreeNode<K, V> r;
/* 2890 */       if (p != null && (r = p.right) != null) {
/* 2891 */         ConcurrentHashMapV8.TreeNode<K, V> rl; if ((rl = p.right = r.left) != null)
/* 2892 */           rl.parent = p;  ConcurrentHashMapV8.TreeNode<K, V> pp;
/* 2893 */         if ((pp = r.parent = p.parent) == null) {
/* 2894 */           (root = r).red = false;
/* 2895 */         } else if (pp.left == p) {
/* 2896 */           pp.left = r;
/*      */         } else {
/* 2898 */           pp.right = r;
/* 2899 */         }  r.left = p;
/* 2900 */         p.parent = r;
/*      */       } 
/* 2902 */       return root;
/*      */     }
/*      */ 
/*      */     
/*      */     static <K, V> ConcurrentHashMapV8.TreeNode<K, V> rotateRight(ConcurrentHashMapV8.TreeNode<K, V> root, ConcurrentHashMapV8.TreeNode<K, V> p) {
/*      */       ConcurrentHashMapV8.TreeNode<K, V> l;
/* 2908 */       if (p != null && (l = p.left) != null) {
/* 2909 */         ConcurrentHashMapV8.TreeNode<K, V> lr; if ((lr = p.left = l.right) != null)
/* 2910 */           lr.parent = p;  ConcurrentHashMapV8.TreeNode<K, V> pp;
/* 2911 */         if ((pp = l.parent = p.parent) == null) {
/* 2912 */           (root = l).red = false;
/* 2913 */         } else if (pp.right == p) {
/* 2914 */           pp.right = l;
/*      */         } else {
/* 2916 */           pp.left = l;
/* 2917 */         }  l.right = p;
/* 2918 */         p.parent = l;
/*      */       } 
/* 2920 */       return root;
/*      */     }
/*      */ 
/*      */     
/*      */     static <K, V> ConcurrentHashMapV8.TreeNode<K, V> balanceInsertion(ConcurrentHashMapV8.TreeNode<K, V> root, ConcurrentHashMapV8.TreeNode<K, V> x) {
/* 2925 */       x.red = true; while (true) {
/*      */         ConcurrentHashMapV8.TreeNode<K, V> xp;
/* 2927 */         if ((xp = x.parent) == null) {
/* 2928 */           x.red = false;
/* 2929 */           return x;
/*      */         }  ConcurrentHashMapV8.TreeNode<K, V> xpp;
/* 2931 */         if (!xp.red || (xpp = xp.parent) == null)
/* 2932 */           return root;  ConcurrentHashMapV8.TreeNode<K, V> xppl;
/* 2933 */         if (xp == (xppl = xpp.left)) {
/* 2934 */           ConcurrentHashMapV8.TreeNode<K, V> xppr; if ((xppr = xpp.right) != null && xppr.red) {
/* 2935 */             xppr.red = false;
/* 2936 */             xp.red = false;
/* 2937 */             xpp.red = true;
/* 2938 */             x = xpp;
/*      */             continue;
/*      */           } 
/* 2941 */           if (x == xp.right) {
/* 2942 */             root = rotateLeft(root, x = xp);
/* 2943 */             xpp = ((xp = x.parent) == null) ? null : xp.parent;
/*      */           } 
/* 2945 */           if (xp != null) {
/* 2946 */             xp.red = false;
/* 2947 */             if (xpp != null) {
/* 2948 */               xpp.red = true;
/* 2949 */               root = rotateRight(root, xpp);
/*      */             } 
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/* 2955 */         if (xppl != null && xppl.red) {
/* 2956 */           xppl.red = false;
/* 2957 */           xp.red = false;
/* 2958 */           xpp.red = true;
/* 2959 */           x = xpp;
/*      */           continue;
/*      */         } 
/* 2962 */         if (x == xp.left) {
/* 2963 */           root = rotateRight(root, x = xp);
/* 2964 */           xpp = ((xp = x.parent) == null) ? null : xp.parent;
/*      */         } 
/* 2966 */         if (xp != null) {
/* 2967 */           xp.red = false;
/* 2968 */           if (xpp != null) {
/* 2969 */             xpp.red = true;
/* 2970 */             root = rotateLeft(root, xpp);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> ConcurrentHashMapV8.TreeNode<K, V> balanceDeletion(ConcurrentHashMapV8.TreeNode<K, V> root, ConcurrentHashMapV8.TreeNode<K, V> x) {
/*      */       while (true) {
/* 2981 */         if (x == null || x == root)
/* 2982 */           return root;  ConcurrentHashMapV8.TreeNode<K, V> xp;
/* 2983 */         if ((xp = x.parent) == null) {
/* 2984 */           x.red = false;
/* 2985 */           return x;
/*      */         } 
/* 2987 */         if (x.red) {
/* 2988 */           x.red = false;
/* 2989 */           return root;
/*      */         }  ConcurrentHashMapV8.TreeNode<K, V> xpl;
/* 2991 */         if ((xpl = xp.left) == x) {
/* 2992 */           ConcurrentHashMapV8.TreeNode<K, V> xpr; if ((xpr = xp.right) != null && xpr.red) {
/* 2993 */             xpr.red = false;
/* 2994 */             xp.red = true;
/* 2995 */             root = rotateLeft(root, xp);
/* 2996 */             xpr = ((xp = x.parent) == null) ? null : xp.right;
/*      */           } 
/* 2998 */           if (xpr == null) {
/* 2999 */             x = xp; continue;
/*      */           } 
/* 3001 */           ConcurrentHashMapV8.TreeNode<K, V> treeNode1 = xpr.left, treeNode2 = xpr.right;
/* 3002 */           if ((treeNode2 == null || !treeNode2.red) && (treeNode1 == null || !treeNode1.red)) {
/*      */             
/* 3004 */             xpr.red = true;
/* 3005 */             x = xp;
/*      */             continue;
/*      */           } 
/* 3008 */           if (treeNode2 == null || !treeNode2.red) {
/* 3009 */             if (treeNode1 != null)
/* 3010 */               treeNode1.red = false; 
/* 3011 */             xpr.red = true;
/* 3012 */             root = rotateRight(root, xpr);
/* 3013 */             xpr = ((xp = x.parent) == null) ? null : xp.right;
/*      */           } 
/*      */           
/* 3016 */           if (xpr != null) {
/* 3017 */             xpr.red = (xp == null) ? false : xp.red;
/* 3018 */             if ((treeNode2 = xpr.right) != null)
/* 3019 */               treeNode2.red = false; 
/*      */           } 
/* 3021 */           if (xp != null) {
/* 3022 */             xp.red = false;
/* 3023 */             root = rotateLeft(root, xp);
/*      */           } 
/* 3025 */           x = root;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 3030 */         if (xpl != null && xpl.red) {
/* 3031 */           xpl.red = false;
/* 3032 */           xp.red = true;
/* 3033 */           root = rotateRight(root, xp);
/* 3034 */           xpl = ((xp = x.parent) == null) ? null : xp.left;
/*      */         } 
/* 3036 */         if (xpl == null) {
/* 3037 */           x = xp; continue;
/*      */         } 
/* 3039 */         ConcurrentHashMapV8.TreeNode<K, V> sl = xpl.left, sr = xpl.right;
/* 3040 */         if ((sl == null || !sl.red) && (sr == null || !sr.red)) {
/*      */           
/* 3042 */           xpl.red = true;
/* 3043 */           x = xp;
/*      */           continue;
/*      */         } 
/* 3046 */         if (sl == null || !sl.red) {
/* 3047 */           if (sr != null)
/* 3048 */             sr.red = false; 
/* 3049 */           xpl.red = true;
/* 3050 */           root = rotateLeft(root, xpl);
/* 3051 */           xpl = ((xp = x.parent) == null) ? null : xp.left;
/*      */         } 
/*      */         
/* 3054 */         if (xpl != null) {
/* 3055 */           xpl.red = (xp == null) ? false : xp.red;
/* 3056 */           if ((sl = xpl.left) != null)
/* 3057 */             sl.red = false; 
/*      */         } 
/* 3059 */         if (xp != null) {
/* 3060 */           xp.red = false;
/* 3061 */           root = rotateRight(root, xp);
/*      */         } 
/* 3063 */         x = root;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static <K, V> boolean checkInvariants(ConcurrentHashMapV8.TreeNode<K, V> t) {
/* 3074 */       ConcurrentHashMapV8.TreeNode<K, V> tp = t.parent, tl = t.left, tr = t.right;
/* 3075 */       ConcurrentHashMapV8.TreeNode<K, V> tb = t.prev, tn = (ConcurrentHashMapV8.TreeNode<K, V>)t.next;
/* 3076 */       if (tb != null && tb.next != t)
/* 3077 */         return false; 
/* 3078 */       if (tn != null && tn.prev != t)
/* 3079 */         return false; 
/* 3080 */       if (tp != null && t != tp.left && t != tp.right)
/* 3081 */         return false; 
/* 3082 */       if (tl != null && (tl.parent != t || tl.hash > t.hash))
/* 3083 */         return false; 
/* 3084 */       if (tr != null && (tr.parent != t || tr.hash < t.hash))
/* 3085 */         return false; 
/* 3086 */       if (t.red && tl != null && tl.red && tr != null && tr.red)
/* 3087 */         return false; 
/* 3088 */       if (tl != null && !checkInvariants(tl))
/* 3089 */         return false; 
/* 3090 */       if (tr != null && !checkInvariants(tr))
/* 3091 */         return false; 
/* 3092 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*      */       try {
/* 3099 */         U = ConcurrentHashMapV8.getUnsafe();
/* 3100 */         Class<?> k = TreeBin.class;
/* 3101 */         LOCKSTATE = U.objectFieldOffset(k.getDeclaredField("lockState"));
/*      */       }
/* 3103 */       catch (Exception e) {
/* 3104 */         throw new Error(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Traverser<K, V>
/*      */   {
/*      */     ConcurrentHashMapV8.Node<K, V>[] tab;
/*      */ 
/*      */ 
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> next;
/*      */ 
/*      */ 
/*      */     
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     int baseIndex;
/*      */ 
/*      */ 
/*      */     
/*      */     int baseLimit;
/*      */ 
/*      */ 
/*      */     
/*      */     final int baseSize;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Traverser(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit) {
/* 3141 */       this.tab = tab;
/* 3142 */       this.baseSize = size;
/* 3143 */       this.baseIndex = this.index = index;
/* 3144 */       this.baseLimit = limit;
/* 3145 */       this.next = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentHashMapV8.Node<K, V> advance() {
/*      */       ConcurrentHashMapV8.Node<K, V> e;
/* 3153 */       if ((e = this.next) != null) {
/* 3154 */         e = e.next;
/*      */       }
/*      */       while (true) {
/* 3157 */         if (e != null)
/* 3158 */           return this.next = e;  ConcurrentHashMapV8.Node<K, V>[] t; int n; int i;
/* 3159 */         if (this.baseIndex >= this.baseLimit || (t = this.tab) == null || (n = t.length) <= (i = this.index) || i < 0)
/*      */         {
/* 3161 */           return this.next = null; } 
/* 3162 */         if ((e = ConcurrentHashMapV8.<K, V>tabAt(t, this.index)) != null && e.hash < 0) {
/* 3163 */           if (e instanceof ConcurrentHashMapV8.ForwardingNode) {
/* 3164 */             this.tab = (ConcurrentHashMapV8.Node<K, V>[])((ConcurrentHashMapV8.ForwardingNode)e).nextTable;
/* 3165 */             e = null;
/*      */             continue;
/*      */           } 
/* 3168 */           if (e instanceof ConcurrentHashMapV8.TreeBin) {
/* 3169 */             e = ((ConcurrentHashMapV8.TreeBin)e).first;
/*      */           } else {
/* 3171 */             e = null;
/*      */           } 
/* 3173 */         }  if ((this.index += this.baseSize) >= n) {
/* 3174 */           this.index = ++this.baseIndex;
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class BaseIterator<K, V>
/*      */     extends Traverser<K, V>
/*      */   {
/*      */     final ConcurrentHashMapV8<K, V> map;
/*      */     ConcurrentHashMapV8.Node<K, V> lastReturned;
/*      */     
/*      */     BaseIterator(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit, ConcurrentHashMapV8<K, V> map) {
/* 3188 */       super(tab, size, index, limit);
/* 3189 */       this.map = map;
/* 3190 */       advance();
/*      */     }
/*      */     
/* 3193 */     public final boolean hasNext() { return (this.next != null); } public final boolean hasMoreElements() {
/* 3194 */       return (this.next != null);
/*      */     }
/*      */     public final void remove() {
/*      */       ConcurrentHashMapV8.Node<K, V> p;
/* 3198 */       if ((p = this.lastReturned) == null)
/* 3199 */         throw new IllegalStateException(); 
/* 3200 */       this.lastReturned = null;
/* 3201 */       this.map.replaceNode(p.key, null, null);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeyIterator<K, V>
/*      */     extends BaseIterator<K, V>
/*      */     implements Iterator<K>, Enumeration<K> {
/*      */     KeyIterator(ConcurrentHashMapV8.Node<K, V>[] tab, int index, int size, int limit, ConcurrentHashMapV8<K, V> map) {
/* 3209 */       super(tab, index, size, limit, map);
/*      */     }
/*      */     
/*      */     public final K next() {
/*      */       ConcurrentHashMapV8.Node<K, V> p;
/* 3214 */       if ((p = this.next) == null)
/* 3215 */         throw new NoSuchElementException(); 
/* 3216 */       K k = p.key;
/* 3217 */       this.lastReturned = p;
/* 3218 */       advance();
/* 3219 */       return k;
/*      */     }
/*      */     public final K nextElement() {
/* 3222 */       return next();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueIterator<K, V>
/*      */     extends BaseIterator<K, V> implements Iterator<V>, Enumeration<V> {
/*      */     ValueIterator(ConcurrentHashMapV8.Node<K, V>[] tab, int index, int size, int limit, ConcurrentHashMapV8<K, V> map) {
/* 3229 */       super(tab, index, size, limit, map);
/*      */     }
/*      */     
/*      */     public final V next() {
/*      */       ConcurrentHashMapV8.Node<K, V> p;
/* 3234 */       if ((p = this.next) == null)
/* 3235 */         throw new NoSuchElementException(); 
/* 3236 */       V v = p.val;
/* 3237 */       this.lastReturned = p;
/* 3238 */       advance();
/* 3239 */       return v;
/*      */     }
/*      */     public final V nextElement() {
/* 3242 */       return next();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntryIterator<K, V>
/*      */     extends BaseIterator<K, V> implements Iterator<Map.Entry<K, V>> {
/*      */     EntryIterator(ConcurrentHashMapV8.Node<K, V>[] tab, int index, int size, int limit, ConcurrentHashMapV8<K, V> map) {
/* 3249 */       super(tab, index, size, limit, map);
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> next() {
/*      */       ConcurrentHashMapV8.Node<K, V> p;
/* 3254 */       if ((p = this.next) == null)
/* 3255 */         throw new NoSuchElementException(); 
/* 3256 */       K k = p.key;
/* 3257 */       V v = p.val;
/* 3258 */       this.lastReturned = p;
/* 3259 */       advance();
/* 3260 */       return new ConcurrentHashMapV8.MapEntry<K, V>(k, v, this.map);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class MapEntry<K, V>
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final K key;
/*      */     V val;
/*      */     final ConcurrentHashMapV8<K, V> map;
/*      */     
/*      */     MapEntry(K key, V val, ConcurrentHashMapV8<K, V> map) {
/* 3272 */       this.key = key;
/* 3273 */       this.val = val;
/* 3274 */       this.map = map;
/*      */     }
/* 3276 */     public K getKey() { return this.key; }
/* 3277 */     public V getValue() { return this.val; }
/* 3278 */     public int hashCode() { return this.key.hashCode() ^ this.val.hashCode(); }
/* 3279 */     public String toString() { return (new StringBuilder()).append(this.key).append("=").append(this.val).toString(); } public boolean equals(Object o) {
/*      */       Map.Entry<?, ?> e;
/*      */       Object k;
/*      */       Object v;
/* 3283 */       return (o instanceof Map.Entry && (k = (e = (Map.Entry<?, ?>)o).getKey()) != null && (v = e.getValue()) != null && (k == this.key || k.equals(this.key)) && (v == this.val || v.equals(this.val)));
/*      */     }
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
/*      */     public V setValue(V value) {
/* 3299 */       if (value == null) throw new NullPointerException(); 
/* 3300 */       V v = this.val;
/* 3301 */       this.val = value;
/* 3302 */       this.map.put(this.key, value);
/* 3303 */       return v;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V>
/*      */     extends Traverser<K, V> implements ConcurrentHashMapSpliterator<K> {
/*      */     long est;
/*      */     
/*      */     KeySpliterator(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit, long est) {
/* 3312 */       super(tab, size, index, limit);
/* 3313 */       this.est = est;
/*      */     } public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<K> trySplit() {
/*      */       int i;
/*      */       int f;
/*      */       int h;
/* 3318 */       return ((h = (i = this.baseIndex) + (f = this.baseLimit) >>> 1) <= i) ? null : new KeySpliterator(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1L);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ConcurrentHashMapV8.Action<? super K> action) {
/* 3324 */       if (action == null) throw new NullPointerException();  ConcurrentHashMapV8.Node<K, V> p;
/* 3325 */       while ((p = advance()) != null)
/* 3326 */         action.apply(p.key); 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(ConcurrentHashMapV8.Action<? super K> action) {
/* 3330 */       if (action == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMapV8.Node<K, V> p;
/* 3332 */       if ((p = advance()) == null)
/* 3333 */         return false; 
/* 3334 */       action.apply(p.key);
/* 3335 */       return true;
/*      */     }
/*      */     public long estimateSize() {
/* 3338 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V>
/*      */     extends Traverser<K, V> implements ConcurrentHashMapSpliterator<V> {
/*      */     long est;
/*      */     
/*      */     ValueSpliterator(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit, long est) {
/* 3347 */       super(tab, size, index, limit);
/* 3348 */       this.est = est;
/*      */     } public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<V> trySplit() {
/*      */       int i;
/*      */       int f;
/*      */       int h;
/* 3353 */       return ((h = (i = this.baseIndex) + (f = this.baseLimit) >>> 1) <= i) ? null : new ValueSpliterator(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1L);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ConcurrentHashMapV8.Action<? super V> action) {
/* 3359 */       if (action == null) throw new NullPointerException();  ConcurrentHashMapV8.Node<K, V> p;
/* 3360 */       while ((p = advance()) != null)
/* 3361 */         action.apply(p.val); 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(ConcurrentHashMapV8.Action<? super V> action) {
/* 3365 */       if (action == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMapV8.Node<K, V> p;
/* 3367 */       if ((p = advance()) == null)
/* 3368 */         return false; 
/* 3369 */       action.apply(p.val);
/* 3370 */       return true;
/*      */     }
/*      */     public long estimateSize() {
/* 3373 */       return this.est;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V>
/*      */     extends Traverser<K, V> implements ConcurrentHashMapSpliterator<Map.Entry<K, V>> {
/*      */     final ConcurrentHashMapV8<K, V> map;
/*      */     long est;
/*      */     
/*      */     EntrySpliterator(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit, long est, ConcurrentHashMapV8<K, V> map) {
/* 3383 */       super(tab, size, index, limit);
/* 3384 */       this.map = map;
/* 3385 */       this.est = est;
/*      */     } public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<Map.Entry<K, V>> trySplit() {
/*      */       int i;
/*      */       int f;
/*      */       int h;
/* 3390 */       return ((h = (i = this.baseIndex) + (f = this.baseLimit) >>> 1) <= i) ? null : new EntrySpliterator(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1L, this.map);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> action) {
/* 3396 */       if (action == null) throw new NullPointerException();  ConcurrentHashMapV8.Node<K, V> p;
/* 3397 */       while ((p = advance()) != null)
/* 3398 */         action.apply(new ConcurrentHashMapV8.MapEntry<K, V>(p.key, p.val, this.map)); 
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> action) {
/* 3402 */       if (action == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMapV8.Node<K, V> p;
/* 3404 */       if ((p = advance()) == null)
/* 3405 */         return false; 
/* 3406 */       action.apply(new ConcurrentHashMapV8.MapEntry<K, V>(p.key, p.val, this.map));
/* 3407 */       return true;
/*      */     }
/*      */     public long estimateSize() {
/* 3410 */       return this.est;
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
/*      */   final int batchFor(long b) {
/*      */     long n;
/* 3426 */     if (b == Long.MAX_VALUE || (n = sumCount()) <= 1L || n < b)
/* 3427 */       return 0; 
/* 3428 */     int sp = ForkJoinPool.getCommonPoolParallelism() << 2;
/* 3429 */     return (b <= 0L || (n /= b) >= sp) ? sp : (int)n;
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
/*      */   public void forEach(long parallelismThreshold, BiAction<? super K, ? super V> action) {
/* 3442 */     if (action == null) throw new NullPointerException(); 
/* 3443 */     (new ForEachMappingTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, action)).invoke();
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
/*      */   public <U> void forEach(long parallelismThreshold, BiFun<? super K, ? super V, ? extends U> transformer, Action<? super U> action) {
/* 3463 */     if (transformer == null || action == null)
/* 3464 */       throw new NullPointerException(); 
/* 3465 */     (new ForEachTransformedMappingTask<Object, Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, transformer, action)).invoke();
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
/*      */   public <U> U search(long parallelismThreshold, BiFun<? super K, ? super V, ? extends U> searchFunction) {
/* 3487 */     if (searchFunction == null) throw new NullPointerException(); 
/* 3488 */     return (new SearchMappingsTask<Object, Object, U>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, searchFunction, new AtomicReference<U>())).invoke();
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
/*      */   
/*      */   public <U> U reduce(long parallelismThreshold, BiFun<? super K, ? super V, ? extends U> transformer, BiFun<? super U, ? super U, ? extends U> reducer) {
/* 3511 */     if (transformer == null || reducer == null)
/* 3512 */       throw new NullPointerException(); 
/* 3513 */     return (new MapReduceMappingsTask<Object, Object, U>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, reducer)).invoke();
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
/*      */ 
/*      */   
/*      */   public double reduceToDouble(long parallelismThreshold, ObjectByObjectToDouble<? super K, ? super V> transformer, double basis, DoubleByDoubleToDouble reducer) {
/* 3537 */     if (transformer == null || reducer == null)
/* 3538 */       throw new NullPointerException(); 
/* 3539 */     return (new MapReduceMappingsToDoubleTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().doubleValue();
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
/*      */ 
/*      */   
/*      */   public long reduceToLong(long parallelismThreshold, ObjectByObjectToLong<? super K, ? super V> transformer, long basis, LongByLongToLong reducer) {
/* 3563 */     if (transformer == null || reducer == null)
/* 3564 */       throw new NullPointerException(); 
/* 3565 */     return (new MapReduceMappingsToLongTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().longValue();
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
/*      */ 
/*      */   
/*      */   public int reduceToInt(long parallelismThreshold, ObjectByObjectToInt<? super K, ? super V> transformer, int basis, IntByIntToInt reducer) {
/* 3589 */     if (transformer == null || reducer == null)
/* 3590 */       throw new NullPointerException(); 
/* 3591 */     return (new MapReduceMappingsToIntTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().intValue();
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
/*      */   public void forEachKey(long parallelismThreshold, Action<? super K> action) {
/* 3606 */     if (action == null) throw new NullPointerException(); 
/* 3607 */     (new ForEachKeyTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, action)).invoke();
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
/*      */   public <U> void forEachKey(long parallelismThreshold, Fun<? super K, ? extends U> transformer, Action<? super U> action) {
/* 3627 */     if (transformer == null || action == null)
/* 3628 */       throw new NullPointerException(); 
/* 3629 */     (new ForEachTransformedKeyTask<Object, Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, transformer, action)).invoke();
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
/*      */   public <U> U searchKeys(long parallelismThreshold, Fun<? super K, ? extends U> searchFunction) {
/* 3651 */     if (searchFunction == null) throw new NullPointerException(); 
/* 3652 */     return (new SearchKeysTask<Object, Object, U>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, searchFunction, new AtomicReference<U>())).invoke();
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
/*      */   public K reduceKeys(long parallelismThreshold, BiFun<? super K, ? super K, ? extends K> reducer) {
/* 3670 */     if (reducer == null) throw new NullPointerException(); 
/* 3671 */     return (new ReduceKeysTask<K, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<K, ?>[])this.table, null, reducer)).invoke();
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
/*      */   
/*      */   public <U> U reduceKeys(long parallelismThreshold, Fun<? super K, ? extends U> transformer, BiFun<? super U, ? super U, ? extends U> reducer) {
/* 3694 */     if (transformer == null || reducer == null)
/* 3695 */       throw new NullPointerException(); 
/* 3696 */     return (new MapReduceKeysTask<Object, Object, U>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, reducer)).invoke();
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
/*      */ 
/*      */   
/*      */   public double reduceKeysToDouble(long parallelismThreshold, ObjectToDouble<? super K> transformer, double basis, DoubleByDoubleToDouble reducer) {
/* 3720 */     if (transformer == null || reducer == null)
/* 3721 */       throw new NullPointerException(); 
/* 3722 */     return (new MapReduceKeysToDoubleTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().doubleValue();
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
/*      */ 
/*      */   
/*      */   public long reduceKeysToLong(long parallelismThreshold, ObjectToLong<? super K> transformer, long basis, LongByLongToLong reducer) {
/* 3746 */     if (transformer == null || reducer == null)
/* 3747 */       throw new NullPointerException(); 
/* 3748 */     return (new MapReduceKeysToLongTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().longValue();
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
/*      */ 
/*      */   
/*      */   public int reduceKeysToInt(long parallelismThreshold, ObjectToInt<? super K> transformer, int basis, IntByIntToInt reducer) {
/* 3772 */     if (transformer == null || reducer == null)
/* 3773 */       throw new NullPointerException(); 
/* 3774 */     return (new MapReduceKeysToIntTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().intValue();
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
/*      */   public void forEachValue(long parallelismThreshold, Action<? super V> action) {
/* 3789 */     if (action == null)
/* 3790 */       throw new NullPointerException(); 
/* 3791 */     (new ForEachValueTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, action)).invoke();
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
/*      */   public <U> void forEachValue(long parallelismThreshold, Fun<? super V, ? extends U> transformer, Action<? super U> action) {
/* 3811 */     if (transformer == null || action == null)
/* 3812 */       throw new NullPointerException(); 
/* 3813 */     (new ForEachTransformedValueTask<Object, Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, transformer, action)).invoke();
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
/*      */   public <U> U searchValues(long parallelismThreshold, Fun<? super V, ? extends U> searchFunction) {
/* 3835 */     if (searchFunction == null) throw new NullPointerException(); 
/* 3836 */     return (new SearchValuesTask<Object, Object, U>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, searchFunction, new AtomicReference<U>())).invoke();
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
/*      */   public V reduceValues(long parallelismThreshold, BiFun<? super V, ? super V, ? extends V> reducer) {
/* 3853 */     if (reducer == null) throw new NullPointerException(); 
/* 3854 */     return (new ReduceValuesTask<Object, V>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, V>[])this.table, null, reducer)).invoke();
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
/*      */   
/*      */   public <U> U reduceValues(long parallelismThreshold, Fun<? super V, ? extends U> transformer, BiFun<? super U, ? super U, ? extends U> reducer) {
/* 3877 */     if (transformer == null || reducer == null)
/* 3878 */       throw new NullPointerException(); 
/* 3879 */     return (new MapReduceValuesTask<Object, Object, U>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, reducer)).invoke();
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
/*      */ 
/*      */   
/*      */   public double reduceValuesToDouble(long parallelismThreshold, ObjectToDouble<? super V> transformer, double basis, DoubleByDoubleToDouble reducer) {
/* 3903 */     if (transformer == null || reducer == null)
/* 3904 */       throw new NullPointerException(); 
/* 3905 */     return (new MapReduceValuesToDoubleTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().doubleValue();
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
/*      */ 
/*      */   
/*      */   public long reduceValuesToLong(long parallelismThreshold, ObjectToLong<? super V> transformer, long basis, LongByLongToLong reducer) {
/* 3929 */     if (transformer == null || reducer == null)
/* 3930 */       throw new NullPointerException(); 
/* 3931 */     return (new MapReduceValuesToLongTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().longValue();
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
/*      */ 
/*      */   
/*      */   public int reduceValuesToInt(long parallelismThreshold, ObjectToInt<? super V> transformer, int basis, IntByIntToInt reducer) {
/* 3955 */     if (transformer == null || reducer == null)
/* 3956 */       throw new NullPointerException(); 
/* 3957 */     return (new MapReduceValuesToIntTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().intValue();
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
/*      */   public void forEachEntry(long parallelismThreshold, Action<? super Map.Entry<K, V>> action) {
/* 3972 */     if (action == null) throw new NullPointerException(); 
/* 3973 */     (new ForEachEntryTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, action)).invoke();
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
/*      */   public <U> void forEachEntry(long parallelismThreshold, Fun<Map.Entry<K, V>, ? extends U> transformer, Action<? super U> action) {
/* 3992 */     if (transformer == null || action == null)
/* 3993 */       throw new NullPointerException(); 
/* 3994 */     (new ForEachTransformedEntryTask<Object, Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, transformer, action)).invoke();
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
/*      */   public <U> U searchEntries(long parallelismThreshold, Fun<Map.Entry<K, V>, ? extends U> searchFunction) {
/* 4016 */     if (searchFunction == null) throw new NullPointerException(); 
/* 4017 */     return (new SearchEntriesTask<Object, Object, U>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, searchFunction, new AtomicReference<U>())).invoke();
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
/*      */   public Map.Entry<K, V> reduceEntries(long parallelismThreshold, BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer) {
/* 4034 */     if (reducer == null) throw new NullPointerException(); 
/* 4035 */     return (new ReduceEntriesTask<K, V>(null, batchFor(parallelismThreshold), 0, 0, this.table, null, reducer)).invoke();
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
/*      */   
/*      */   public <U> U reduceEntries(long parallelismThreshold, Fun<Map.Entry<K, V>, ? extends U> transformer, BiFun<? super U, ? super U, ? extends U> reducer) {
/* 4058 */     if (transformer == null || reducer == null)
/* 4059 */       throw new NullPointerException(); 
/* 4060 */     return (new MapReduceEntriesTask<Object, Object, U>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, reducer)).invoke();
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
/*      */ 
/*      */   
/*      */   public double reduceEntriesToDouble(long parallelismThreshold, ObjectToDouble<Map.Entry<K, V>> transformer, double basis, DoubleByDoubleToDouble reducer) {
/* 4084 */     if (transformer == null || reducer == null)
/* 4085 */       throw new NullPointerException(); 
/* 4086 */     return (new MapReduceEntriesToDoubleTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().doubleValue();
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
/*      */ 
/*      */   
/*      */   public long reduceEntriesToLong(long parallelismThreshold, ObjectToLong<Map.Entry<K, V>> transformer, long basis, LongByLongToLong reducer) {
/* 4110 */     if (transformer == null || reducer == null)
/* 4111 */       throw new NullPointerException(); 
/* 4112 */     return (new MapReduceEntriesToLongTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().longValue();
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
/*      */ 
/*      */   
/*      */   public int reduceEntriesToInt(long parallelismThreshold, ObjectToInt<Map.Entry<K, V>> transformer, int basis, IntByIntToInt reducer) {
/* 4136 */     if (transformer == null || reducer == null)
/* 4137 */       throw new NullPointerException(); 
/* 4138 */     return (new MapReduceEntriesToIntTask<Object, Object>(null, batchFor(parallelismThreshold), 0, 0, (Node<?, ?>[])this.table, null, transformer, basis, reducer)).invoke().intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class CollectionView<K, V, E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7249069246763182397L;
/*      */     
/*      */     final ConcurrentHashMapV8<K, V> map;
/*      */     
/*      */     private static final String oomeMsg = "Required array size too large";
/*      */ 
/*      */     
/*      */     CollectionView(ConcurrentHashMapV8<K, V> map) {
/* 4153 */       this.map = map;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ConcurrentHashMapV8<K, V> getMap() {
/* 4160 */       return this.map;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 4166 */       this.map.clear();
/* 4167 */     } public final int size() { return this.map.size(); } public final boolean isEmpty() {
/* 4168 */       return this.map.isEmpty();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract Iterator<E> iterator();
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract boolean contains(Object param1Object);
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract boolean remove(Object param1Object);
/*      */ 
/*      */ 
/*      */     
/*      */     public final Object[] toArray() {
/* 4187 */       long sz = this.map.mappingCount();
/* 4188 */       if (sz > 2147483639L)
/* 4189 */         throw new OutOfMemoryError("Required array size too large"); 
/* 4190 */       int n = (int)sz;
/* 4191 */       Object[] r = new Object[n];
/* 4192 */       int i = 0;
/* 4193 */       for (E e : this) {
/* 4194 */         if (i == n) {
/* 4195 */           if (n >= 2147483639)
/* 4196 */             throw new OutOfMemoryError("Required array size too large"); 
/* 4197 */           if (n >= 1073741819) {
/* 4198 */             n = 2147483639;
/*      */           } else {
/* 4200 */             n += (n >>> 1) + 1;
/* 4201 */           }  r = Arrays.copyOf(r, n);
/*      */         } 
/* 4203 */         r[i++] = e;
/*      */       } 
/* 4205 */       return (i == n) ? r : Arrays.<Object>copyOf(r, i);
/*      */     }
/*      */ 
/*      */     
/*      */     public final <T> T[] toArray(T[] a) {
/* 4210 */       long sz = this.map.mappingCount();
/* 4211 */       if (sz > 2147483639L)
/* 4212 */         throw new OutOfMemoryError("Required array size too large"); 
/* 4213 */       int m = (int)sz;
/* 4214 */       T[] r = (a.length >= m) ? a : (T[])Array.newInstance(a.getClass().getComponentType(), m);
/*      */ 
/*      */       
/* 4217 */       int n = r.length;
/* 4218 */       int i = 0;
/* 4219 */       for (E e : this) {
/* 4220 */         if (i == n) {
/* 4221 */           if (n >= 2147483639)
/* 4222 */             throw new OutOfMemoryError("Required array size too large"); 
/* 4223 */           if (n >= 1073741819) {
/* 4224 */             n = 2147483639;
/*      */           } else {
/* 4226 */             n += (n >>> 1) + 1;
/* 4227 */           }  r = Arrays.copyOf(r, n);
/*      */         } 
/* 4229 */         r[i++] = (T)e;
/*      */       } 
/* 4231 */       if (a == r && i < n) {
/* 4232 */         r[i] = null;
/* 4233 */         return r;
/*      */       } 
/* 4235 */       return (i == n) ? r : Arrays.<T>copyOf(r, i);
/*      */     }
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
/*      */     public final String toString() {
/* 4250 */       StringBuilder sb = new StringBuilder();
/* 4251 */       sb.append('[');
/* 4252 */       Iterator<E> it = iterator();
/* 4253 */       if (it.hasNext()) {
/*      */         while (true) {
/* 4255 */           Object e = it.next();
/* 4256 */           sb.append((e == this) ? "(this Collection)" : e);
/* 4257 */           if (!it.hasNext())
/*      */             break; 
/* 4259 */           sb.append(',').append(' ');
/*      */         } 
/*      */       }
/* 4262 */       return sb.append(']').toString();
/*      */     }
/*      */     
/*      */     public final boolean containsAll(Collection<?> c) {
/* 4266 */       if (c != this)
/* 4267 */         for (Object e : c) {
/* 4268 */           if (e == null || !contains(e)) {
/* 4269 */             return false;
/*      */           }
/*      */         }  
/* 4272 */       return true;
/*      */     }
/*      */     
/*      */     public final boolean removeAll(Collection<?> c) {
/* 4276 */       boolean modified = false;
/* 4277 */       for (Iterator<E> it = iterator(); it.hasNext();) {
/* 4278 */         if (c.contains(it.next())) {
/* 4279 */           it.remove();
/* 4280 */           modified = true;
/*      */         } 
/*      */       } 
/* 4283 */       return modified;
/*      */     }
/*      */     
/*      */     public final boolean retainAll(Collection<?> c) {
/* 4287 */       boolean modified = false;
/* 4288 */       for (Iterator<E> it = iterator(); it.hasNext();) {
/* 4289 */         if (!c.contains(it.next())) {
/* 4290 */           it.remove();
/* 4291 */           modified = true;
/*      */         } 
/*      */       } 
/* 4294 */       return modified;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class KeySetView<K, V>
/*      */     extends CollectionView<K, V, K>
/*      */     implements Set<K>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7249069246763182397L;
/*      */ 
/*      */ 
/*      */     
/*      */     private final V value;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     KeySetView(ConcurrentHashMapV8<K, V> map, V value) {
/* 4315 */       super(map);
/* 4316 */       this.value = value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V getMappedValue() {
/* 4326 */       return this.value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 4332 */       return this.map.containsKey(o);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 4343 */       return (this.map.remove(o) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<K> iterator() {
/* 4350 */       ConcurrentHashMapV8<K, V> m = this.map; ConcurrentHashMapV8.Node<K, V>[] t;
/* 4351 */       int f = ((t = m.table) == null) ? 0 : t.length;
/* 4352 */       return new ConcurrentHashMapV8.KeyIterator<K, V>(t, f, 0, f, m);
/*      */     }
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
/*      */     public boolean add(K e) {
/*      */       V v;
/* 4367 */       if ((v = this.value) == null)
/* 4368 */         throw new UnsupportedOperationException(); 
/* 4369 */       return (this.map.putVal(e, v, true) == null);
/*      */     }
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
/*      */     public boolean addAll(Collection<? extends K> c) {
/* 4384 */       boolean added = false;
/*      */       V v;
/* 4386 */       if ((v = this.value) == null)
/* 4387 */         throw new UnsupportedOperationException(); 
/* 4388 */       for (K e : c) {
/* 4389 */         if (this.map.putVal(e, v, true) == null)
/* 4390 */           added = true; 
/*      */       } 
/* 4392 */       return added;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 4396 */       int h = 0;
/* 4397 */       for (K e : this)
/* 4398 */         h += e.hashCode(); 
/* 4399 */       return h;
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*      */       Set<?> c;
/* 4404 */       return (o instanceof Set && ((c = (Set)o) == this || (containsAll(c) && c.containsAll(this))));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<K> spliterator() {
/* 4411 */       ConcurrentHashMapV8<K, V> m = this.map;
/* 4412 */       long n = m.sumCount(); ConcurrentHashMapV8.Node<K, V>[] t;
/* 4413 */       int f = ((t = m.table) == null) ? 0 : t.length;
/* 4414 */       return new ConcurrentHashMapV8.KeySpliterator<K, V>(t, f, 0, f, (n < 0L) ? 0L : n);
/*      */     }
/*      */     
/*      */     public void forEach(ConcurrentHashMapV8.Action<? super K> action) {
/* 4418 */       if (action == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMapV8.Node<K, V>[] t;
/* 4420 */       if ((t = this.map.table) != null) {
/* 4421 */         ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<K, V>(t, t.length, 0, t.length); ConcurrentHashMapV8.Node<K, V> p;
/* 4422 */         while ((p = it.advance()) != null) {
/* 4423 */           action.apply(p.key);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ValuesView<K, V>
/*      */     extends CollectionView<K, V, V>
/*      */     implements Collection<V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     
/*      */     ValuesView(ConcurrentHashMapV8<K, V> map) {
/* 4436 */       super(map);
/*      */     } public final boolean contains(Object o) {
/* 4438 */       return this.map.containsValue(o);
/*      */     }
/*      */     
/*      */     public final boolean remove(Object o) {
/* 4442 */       if (o != null) {
/* 4443 */         for (Iterator<V> it = iterator(); it.hasNext();) {
/* 4444 */           if (o.equals(it.next())) {
/* 4445 */             it.remove();
/* 4446 */             return true;
/*      */           } 
/*      */         } 
/*      */       }
/* 4450 */       return false;
/*      */     }
/*      */     
/*      */     public final Iterator<V> iterator() {
/* 4454 */       ConcurrentHashMapV8<K, V> m = this.map;
/*      */       ConcurrentHashMapV8.Node<K, V>[] t;
/* 4456 */       int f = ((t = m.table) == null) ? 0 : t.length;
/* 4457 */       return new ConcurrentHashMapV8.ValueIterator<K, V>(t, f, 0, f, m);
/*      */     }
/*      */     
/*      */     public final boolean add(V e) {
/* 4461 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public final boolean addAll(Collection<? extends V> c) {
/* 4464 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<V> spliterator() {
/* 4469 */       ConcurrentHashMapV8<K, V> m = this.map;
/* 4470 */       long n = m.sumCount(); ConcurrentHashMapV8.Node<K, V>[] t;
/* 4471 */       int f = ((t = m.table) == null) ? 0 : t.length;
/* 4472 */       return new ConcurrentHashMapV8.ValueSpliterator<K, V>(t, f, 0, f, (n < 0L) ? 0L : n);
/*      */     }
/*      */     
/*      */     public void forEach(ConcurrentHashMapV8.Action<? super V> action) {
/* 4476 */       if (action == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMapV8.Node<K, V>[] t;
/* 4478 */       if ((t = this.map.table) != null) {
/* 4479 */         ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<K, V>(t, t.length, 0, t.length); ConcurrentHashMapV8.Node<K, V> p;
/* 4480 */         while ((p = it.advance()) != null) {
/* 4481 */           action.apply(p.val);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class EntrySetView<K, V>
/*      */     extends CollectionView<K, V, Map.Entry<K, V>>
/*      */     implements Set<Map.Entry<K, V>>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     
/*      */     EntrySetView(ConcurrentHashMapV8<K, V> map) {
/* 4494 */       super(map); } public boolean contains(Object o) { Map.Entry<?, ?> e;
/*      */       Object k;
/*      */       Object r;
/*      */       Object v;
/* 4498 */       return (o instanceof Map.Entry && (k = (e = (Map.Entry<?, ?>)o).getKey()) != null && (r = this.map.get(k)) != null && (v = e.getValue()) != null && (v == r || v.equals(r))); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*      */       Map.Entry<?, ?> e;
/*      */       Object k;
/*      */       Object v;
/* 4507 */       return (o instanceof Map.Entry && (k = (e = (Map.Entry<?, ?>)o).getKey()) != null && (v = e.getValue()) != null && this.map.remove(k, v));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/* 4517 */       ConcurrentHashMapV8<K, V> m = this.map;
/*      */       ConcurrentHashMapV8.Node<K, V>[] t;
/* 4519 */       int f = ((t = m.table) == null) ? 0 : t.length;
/* 4520 */       return new ConcurrentHashMapV8.EntryIterator<K, V>(t, f, 0, f, m);
/*      */     }
/*      */     
/*      */     public boolean add(Map.Entry<K, V> e) {
/* 4524 */       return (this.map.putVal(e.getKey(), e.getValue(), false) == null);
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Map.Entry<K, V>> c) {
/* 4528 */       boolean added = false;
/* 4529 */       for (Map.Entry<K, V> e : c) {
/* 4530 */         if (add(e))
/* 4531 */           added = true; 
/*      */       } 
/* 4533 */       return added;
/*      */     }
/*      */     
/*      */     public final int hashCode() {
/* 4537 */       int h = 0;
/*      */       ConcurrentHashMapV8.Node<K, V>[] t;
/* 4539 */       if ((t = this.map.table) != null) {
/* 4540 */         ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<K, V>(t, t.length, 0, t.length); ConcurrentHashMapV8.Node<K, V> p;
/* 4541 */         while ((p = it.advance()) != null) {
/* 4542 */           h += p.hashCode();
/*      */         }
/*      */       } 
/* 4545 */       return h;
/*      */     }
/*      */     
/*      */     public final boolean equals(Object o) {
/*      */       Set<?> c;
/* 4550 */       return (o instanceof Set && ((c = (Set)o) == this || (containsAll(c) && c.containsAll(this))));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<Map.Entry<K, V>> spliterator() {
/* 4557 */       ConcurrentHashMapV8<K, V> m = this.map;
/* 4558 */       long n = m.sumCount(); ConcurrentHashMapV8.Node<K, V>[] t;
/* 4559 */       int f = ((t = m.table) == null) ? 0 : t.length;
/* 4560 */       return new ConcurrentHashMapV8.EntrySpliterator<K, V>(t, f, 0, f, (n < 0L) ? 0L : n, m);
/*      */     }
/*      */     
/*      */     public void forEach(ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> action) {
/* 4564 */       if (action == null) throw new NullPointerException(); 
/*      */       ConcurrentHashMapV8.Node<K, V>[] t;
/* 4566 */       if ((t = this.map.table) != null) {
/* 4567 */         ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<K, V>(t, t.length, 0, t.length); ConcurrentHashMapV8.Node<K, V> p;
/* 4568 */         while ((p = it.advance()) != null) {
/* 4569 */           action.apply(new ConcurrentHashMapV8.MapEntry<K, V>(p.key, p.val, this.map));
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class BulkTask<K, V, R>
/*      */     extends CountedCompleter<R>
/*      */   {
/*      */     ConcurrentHashMapV8.Node<K, V>[] tab;
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> next;
/*      */     
/*      */     int index;
/*      */     
/*      */     int baseIndex;
/*      */     int baseLimit;
/*      */     final int baseSize;
/*      */     int batch;
/*      */     
/*      */     BulkTask(BulkTask<K, V, ?> par, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t) {
/* 4591 */       super(par);
/* 4592 */       this.batch = b;
/* 4593 */       this.index = this.baseIndex = i;
/* 4594 */       if ((this.tab = t) == null) {
/* 4595 */         this.baseSize = this.baseLimit = 0;
/* 4596 */       } else if (par == null) {
/* 4597 */         this.baseSize = this.baseLimit = t.length;
/*      */       } else {
/* 4599 */         this.baseLimit = f;
/* 4600 */         this.baseSize = par.baseSize;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentHashMapV8.Node<K, V> advance() {
/*      */       ConcurrentHashMapV8.Node<K, V> e;
/* 4609 */       if ((e = this.next) != null) {
/* 4610 */         e = e.next;
/*      */       }
/*      */       while (true) {
/* 4613 */         if (e != null)
/* 4614 */           return this.next = e;  ConcurrentHashMapV8.Node<K, V>[] t; int n; int i;
/* 4615 */         if (this.baseIndex >= this.baseLimit || (t = this.tab) == null || (n = t.length) <= (i = this.index) || i < 0)
/*      */         {
/* 4617 */           return this.next = null; } 
/* 4618 */         if ((e = ConcurrentHashMapV8.<K, V>tabAt(t, this.index)) != null && e.hash < 0) {
/* 4619 */           if (e instanceof ConcurrentHashMapV8.ForwardingNode) {
/* 4620 */             this.tab = (ConcurrentHashMapV8.Node<K, V>[])((ConcurrentHashMapV8.ForwardingNode)e).nextTable;
/* 4621 */             e = null;
/*      */             continue;
/*      */           } 
/* 4624 */           if (e instanceof ConcurrentHashMapV8.TreeBin) {
/* 4625 */             e = ((ConcurrentHashMapV8.TreeBin)e).first;
/*      */           } else {
/* 4627 */             e = null;
/*      */           } 
/* 4629 */         }  if ((this.index += this.baseSize) >= n) {
/* 4630 */           this.index = ++this.baseIndex;
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ForEachKeyTask<K, V>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Action<? super K> action;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ForEachKeyTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Action<? super K> action) {
/* 4649 */       super(p, b, i, f, t);
/* 4650 */       this.action = action;
/*      */     }
/*      */     public final void compute() {
/*      */       ConcurrentHashMapV8.Action<? super K> action;
/* 4654 */       if ((action = this.action) != null) {
/* 4655 */         int f; int h; for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4657 */           addToPendingCount(1);
/* 4658 */           (new ForEachKeyTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 4662 */         while ((p = advance()) != null)
/* 4663 */           action.apply(p.key); 
/* 4664 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachValueTask<K, V>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Action<? super V> action;
/*      */     
/*      */     ForEachValueTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Action<? super V> action) {
/* 4676 */       super(p, b, i, f, t);
/* 4677 */       this.action = action;
/*      */     }
/*      */     public final void compute() {
/*      */       ConcurrentHashMapV8.Action<? super V> action;
/* 4681 */       if ((action = this.action) != null) {
/* 4682 */         int f; int h; for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4684 */           addToPendingCount(1);
/* 4685 */           (new ForEachValueTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 4689 */         while ((p = advance()) != null)
/* 4690 */           action.apply(p.val); 
/* 4691 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachEntryTask<K, V>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> action;
/*      */     
/*      */     ForEachEntryTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> action) {
/* 4703 */       super(p, b, i, f, t);
/* 4704 */       this.action = action;
/*      */     }
/*      */     public final void compute() {
/*      */       ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> action;
/* 4708 */       if ((action = this.action) != null) {
/* 4709 */         int f; int h; for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4711 */           addToPendingCount(1);
/* 4712 */           (new ForEachEntryTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 4716 */         while ((p = advance()) != null)
/* 4717 */           action.apply(p); 
/* 4718 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachMappingTask<K, V>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiAction<? super K, ? super V> action;
/*      */     
/*      */     ForEachMappingTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.BiAction<? super K, ? super V> action) {
/* 4730 */       super(p, b, i, f, t);
/* 4731 */       this.action = action;
/*      */     }
/*      */     public final void compute() {
/*      */       ConcurrentHashMapV8.BiAction<? super K, ? super V> action;
/* 4735 */       if ((action = this.action) != null) {
/* 4736 */         int f; int h; for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4738 */           addToPendingCount(1);
/* 4739 */           (new ForEachMappingTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 4743 */         while ((p = advance()) != null)
/* 4744 */           action.apply(p.key, p.val); 
/* 4745 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachTransformedKeyTask<K, V, U>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.Action<? super U> action;
/*      */     
/*      */     ForEachTransformedKeyTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer, ConcurrentHashMapV8.Action<? super U> action) {
/* 4758 */       super(p, b, i, f, t);
/* 4759 */       this.transformer = transformer; this.action = action;
/*      */     }
/*      */     public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer;
/*      */       ConcurrentHashMapV8.Action<? super U> action;
/* 4764 */       if ((transformer = this.transformer) != null && (action = this.action) != null) {
/*      */         int f; int h;
/* 4766 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4768 */           addToPendingCount(1);
/* 4769 */           (new ForEachTransformedKeyTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 4773 */         while ((p = advance()) != null) {
/*      */           U u;
/* 4775 */           if ((u = transformer.apply(p.key)) != null)
/* 4776 */             action.apply(u); 
/*      */         } 
/* 4778 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachTransformedValueTask<K, V, U>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.Action<? super U> action;
/*      */     
/*      */     ForEachTransformedValueTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer, ConcurrentHashMapV8.Action<? super U> action) {
/* 4791 */       super(p, b, i, f, t);
/* 4792 */       this.transformer = transformer; this.action = action;
/*      */     }
/*      */     public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer;
/*      */       ConcurrentHashMapV8.Action<? super U> action;
/* 4797 */       if ((transformer = this.transformer) != null && (action = this.action) != null) {
/*      */         int f; int h;
/* 4799 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4801 */           addToPendingCount(1);
/* 4802 */           (new ForEachTransformedValueTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 4806 */         while ((p = advance()) != null) {
/*      */           U u;
/* 4808 */           if ((u = transformer.apply(p.val)) != null)
/* 4809 */             action.apply(u); 
/*      */         } 
/* 4811 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachTransformedEntryTask<K, V, U>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.Action<? super U> action;
/*      */     
/*      */     ForEachTransformedEntryTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> transformer, ConcurrentHashMapV8.Action<? super U> action) {
/* 4824 */       super(p, b, i, f, t);
/* 4825 */       this.transformer = transformer; this.action = action;
/*      */     }
/*      */     public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> transformer;
/*      */       ConcurrentHashMapV8.Action<? super U> action;
/* 4830 */       if ((transformer = this.transformer) != null && (action = this.action) != null) {
/*      */         int f; int h;
/* 4832 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4834 */           addToPendingCount(1);
/* 4835 */           (new ForEachTransformedEntryTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 4839 */         while ((p = advance()) != null) {
/*      */           U u;
/* 4841 */           if ((u = transformer.apply(p)) != null)
/* 4842 */             action.apply(u); 
/*      */         } 
/* 4844 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ForEachTransformedMappingTask<K, V, U>
/*      */     extends BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.Action<? super U> action;
/*      */     
/*      */     ForEachTransformedMappingTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer, ConcurrentHashMapV8.Action<? super U> action) {
/* 4858 */       super(p, b, i, f, t);
/* 4859 */       this.transformer = transformer; this.action = action;
/*      */     }
/*      */     public final void compute() {
/*      */       ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer;
/*      */       ConcurrentHashMapV8.Action<? super U> action;
/* 4864 */       if ((transformer = this.transformer) != null && (action = this.action) != null) {
/*      */         int f; int h;
/* 4866 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4868 */           addToPendingCount(1);
/* 4869 */           (new ForEachTransformedMappingTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 4873 */         while ((p = advance()) != null) {
/*      */           U u;
/* 4875 */           if ((u = transformer.apply(p.key, p.val)) != null)
/* 4876 */             action.apply(u); 
/*      */         } 
/* 4878 */         propagateCompletion();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SearchKeysTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super K, ? extends U> searchFunction;
/*      */     
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchKeysTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Fun<? super K, ? extends U> searchFunction, AtomicReference<U> result) {
/* 4892 */       super(p, b, i, f, t);
/* 4893 */       this.searchFunction = searchFunction; this.result = result;
/*      */     } public final U getRawResult() {
/* 4895 */       return this.result.get();
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<? super K, ? extends U> searchFunction;
/*      */       AtomicReference<U> result;
/* 4899 */       if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
/*      */         int f; int h;
/* 4901 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4903 */           if (result.get() != null)
/*      */             return; 
/* 4905 */           addToPendingCount(1);
/* 4906 */           (new SearchKeysTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 4910 */         while (result.get() == null) {
/*      */           ConcurrentHashMapV8.Node<K, V> p;
/*      */           
/* 4913 */           if ((p = advance()) == null) {
/* 4914 */             propagateCompletion(); break;
/*      */           } 
/*      */           U u;
/* 4917 */           if ((u = searchFunction.apply(p.key)) != null) {
/* 4918 */             if (result.compareAndSet(null, u)) {
/* 4919 */               quietlyCompleteRoot();
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SearchValuesTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super V, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchValuesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Fun<? super V, ? extends U> searchFunction, AtomicReference<U> result) {
/* 4936 */       super(p, b, i, f, t);
/* 4937 */       this.searchFunction = searchFunction; this.result = result;
/*      */     } public final U getRawResult() {
/* 4939 */       return this.result.get();
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<? super V, ? extends U> searchFunction;
/*      */       AtomicReference<U> result;
/* 4943 */       if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
/*      */         int f; int h;
/* 4945 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4947 */           if (result.get() != null)
/*      */             return; 
/* 4949 */           addToPendingCount(1);
/* 4950 */           (new SearchValuesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 4954 */         while (result.get() == null) {
/*      */           ConcurrentHashMapV8.Node<K, V> p;
/*      */           
/* 4957 */           if ((p = advance()) == null) {
/* 4958 */             propagateCompletion(); break;
/*      */           } 
/*      */           U u;
/* 4961 */           if ((u = searchFunction.apply(p.val)) != null) {
/* 4962 */             if (result.compareAndSet(null, u)) {
/* 4963 */               quietlyCompleteRoot();
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SearchEntriesTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchEntriesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> searchFunction, AtomicReference<U> result) {
/* 4980 */       super(p, b, i, f, t);
/* 4981 */       this.searchFunction = searchFunction; this.result = result;
/*      */     } public final U getRawResult() {
/* 4983 */       return this.result.get();
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> searchFunction;
/*      */       AtomicReference<U> result;
/* 4987 */       if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
/*      */         int f; int h;
/* 4989 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 4991 */           if (result.get() != null)
/*      */             return; 
/* 4993 */           addToPendingCount(1);
/* 4994 */           (new SearchEntriesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 4998 */         while (result.get() == null) {
/*      */           ConcurrentHashMapV8.Node<K, V> p;
/*      */           
/* 5001 */           if ((p = advance()) == null) {
/* 5002 */             propagateCompletion(); break;
/*      */           } 
/*      */           U u;
/* 5005 */           if ((u = searchFunction.apply(p)) != null) {
/* 5006 */             if (result.compareAndSet(null, u)) {
/* 5007 */               quietlyCompleteRoot();
/*      */             }
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SearchMappingsTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchMappingsTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> searchFunction, AtomicReference<U> result) {
/* 5024 */       super(p, b, i, f, t);
/* 5025 */       this.searchFunction = searchFunction; this.result = result;
/*      */     } public final U getRawResult() {
/* 5027 */       return this.result.get();
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> searchFunction;
/*      */       AtomicReference<U> result;
/* 5031 */       if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
/*      */         int f; int h;
/* 5033 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5035 */           if (result.get() != null)
/*      */             return; 
/* 5037 */           addToPendingCount(1);
/* 5038 */           (new SearchMappingsTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 5042 */         while (result.get() == null) {
/*      */           ConcurrentHashMapV8.Node<K, V> p;
/*      */           
/* 5045 */           if ((p = advance()) == null) {
/* 5046 */             propagateCompletion(); break;
/*      */           } 
/*      */           U u;
/* 5049 */           if ((u = searchFunction.apply(p.key, p.val)) != null) {
/* 5050 */             if (result.compareAndSet(null, u)) {
/* 5051 */               quietlyCompleteRoot();
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReduceKeysTask<K, V>
/*      */     extends BulkTask<K, V, K>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> reducer;
/*      */     K result;
/*      */     ReduceKeysTask<K, V> rights;
/*      */     ReduceKeysTask<K, V> nextRight;
/*      */     
/*      */     ReduceKeysTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ReduceKeysTask<K, V> nextRight, ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> reducer) {
/* 5069 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5070 */       this.reducer = reducer;
/*      */     } public final K getRawResult() {
/* 5072 */       return this.result;
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> reducer;
/* 5075 */       if ((reducer = this.reducer) != null) {
/* 5076 */         int f, h; for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5078 */           addToPendingCount(1);
/* 5079 */           (this.rights = new ReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 5083 */         K r = null; ConcurrentHashMapV8.Node<K, V> p;
/* 5084 */         while ((p = advance()) != null) {
/* 5085 */           K u = p.key;
/* 5086 */           r = (r == null) ? u : ((u == null) ? r : reducer.apply(r, u));
/*      */         } 
/* 5088 */         this.result = r;
/*      */         
/* 5090 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5092 */           ReduceKeysTask<K, V> t = (ReduceKeysTask)c;
/* 5093 */           ReduceKeysTask<K, V> s = t.rights;
/* 5094 */           while (s != null) {
/*      */             K sr;
/* 5096 */             if ((sr = s.result) != null) {
/* 5097 */               K tr; t.result = ((tr = t.result) == null) ? sr : reducer.apply(tr, sr);
/*      */             } 
/* 5099 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ReduceValuesTask<K, V>
/*      */     extends BulkTask<K, V, V>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> reducer;
/*      */     V result;
/*      */     ReduceValuesTask<K, V> rights;
/*      */     ReduceValuesTask<K, V> nextRight;
/*      */     
/*      */     ReduceValuesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ReduceValuesTask<K, V> nextRight, ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> reducer) {
/* 5116 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5117 */       this.reducer = reducer;
/*      */     } public final V getRawResult() {
/* 5119 */       return this.result;
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> reducer;
/* 5122 */       if ((reducer = this.reducer) != null) {
/* 5123 */         int f, h; for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5125 */           addToPendingCount(1);
/* 5126 */           (this.rights = new ReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 5130 */         V r = null; ConcurrentHashMapV8.Node<K, V> p;
/* 5131 */         while ((p = advance()) != null) {
/* 5132 */           V v = p.val;
/* 5133 */           r = (r == null) ? v : reducer.apply(r, v);
/*      */         } 
/* 5135 */         this.result = r;
/*      */         
/* 5137 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5139 */           ReduceValuesTask<K, V> t = (ReduceValuesTask)c;
/* 5140 */           ReduceValuesTask<K, V> s = t.rights;
/* 5141 */           while (s != null) {
/*      */             V sr;
/* 5143 */             if ((sr = s.result) != null) {
/* 5144 */               V tr; t.result = ((tr = t.result) == null) ? sr : reducer.apply(tr, sr);
/*      */             } 
/* 5146 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ReduceEntriesTask<K, V>
/*      */     extends BulkTask<K, V, Map.Entry<K, V>>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
/*      */     Map.Entry<K, V> result;
/*      */     ReduceEntriesTask<K, V> rights;
/*      */     ReduceEntriesTask<K, V> nextRight;
/*      */     
/*      */     ReduceEntriesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ReduceEntriesTask<K, V> nextRight, ConcurrentHashMapV8.BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer) {
/* 5163 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5164 */       this.reducer = reducer;
/*      */     } public final Map.Entry<K, V> getRawResult() {
/* 5166 */       return this.result;
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
/* 5169 */       if ((reducer = this.reducer) != null) {
/* 5170 */         int f, h; for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5172 */           addToPendingCount(1);
/* 5173 */           (this.rights = new ReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 5177 */         Map.Entry<K, V> r = null; ConcurrentHashMapV8.Node<K, V> p;
/* 5178 */         while ((p = advance()) != null)
/* 5179 */           r = (r == null) ? p : reducer.apply(r, p); 
/* 5180 */         this.result = r;
/*      */         
/* 5182 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5184 */           ReduceEntriesTask<K, V> t = (ReduceEntriesTask)c;
/* 5185 */           ReduceEntriesTask<K, V> s = t.rights;
/* 5186 */           while (s != null) {
/*      */             Map.Entry<K, V> sr;
/* 5188 */             if ((sr = s.result) != null) {
/* 5189 */               Map.Entry<K, V> tr; t.result = ((tr = t.result) == null) ? sr : reducer.apply(tr, sr);
/*      */             } 
/* 5191 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceKeysTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceKeysTask<K, V, U> rights;
/*      */     MapReduceKeysTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceKeysTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceKeysTask<K, V, U> nextRight, ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer, ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer) {
/* 5210 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5211 */       this.transformer = transformer;
/* 5212 */       this.reducer = reducer;
/*      */     } public final U getRawResult() {
/* 5214 */       return this.result;
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer;
/*      */       ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/* 5218 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         int f, h;
/* 5220 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5222 */           addToPendingCount(1);
/* 5223 */           (this.rights = new MapReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 5227 */         U r = null; ConcurrentHashMapV8.Node<K, V> p;
/* 5228 */         while ((p = advance()) != null) {
/*      */           U u;
/* 5230 */           if ((u = transformer.apply(p.key)) != null)
/* 5231 */             r = (r == null) ? u : reducer.apply(r, u); 
/*      */         } 
/* 5233 */         this.result = r;
/*      */         
/* 5235 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5237 */           MapReduceKeysTask<K, V, U> t = (MapReduceKeysTask)c;
/* 5238 */           MapReduceKeysTask<K, V, U> s = t.rights;
/* 5239 */           while (s != null) {
/*      */             U sr;
/* 5241 */             if ((sr = s.result) != null) {
/* 5242 */               U tr; t.result = ((tr = t.result) == null) ? sr : reducer.apply(tr, sr);
/*      */             } 
/* 5244 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceValuesTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceValuesTask<K, V, U> rights;
/*      */     MapReduceValuesTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceValuesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceValuesTask<K, V, U> nextRight, ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer, ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer) {
/* 5263 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5264 */       this.transformer = transformer;
/* 5265 */       this.reducer = reducer;
/*      */     } public final U getRawResult() {
/* 5267 */       return this.result;
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer;
/*      */       ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/* 5271 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         int f, h;
/* 5273 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5275 */           addToPendingCount(1);
/* 5276 */           (this.rights = new MapReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 5280 */         U r = null; ConcurrentHashMapV8.Node<K, V> p;
/* 5281 */         while ((p = advance()) != null) {
/*      */           U u;
/* 5283 */           if ((u = transformer.apply(p.val)) != null)
/* 5284 */             r = (r == null) ? u : reducer.apply(r, u); 
/*      */         } 
/* 5286 */         this.result = r;
/*      */         
/* 5288 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5290 */           MapReduceValuesTask<K, V, U> t = (MapReduceValuesTask)c;
/* 5291 */           MapReduceValuesTask<K, V, U> s = t.rights;
/* 5292 */           while (s != null) {
/*      */             U sr;
/* 5294 */             if ((sr = s.result) != null) {
/* 5295 */               U tr; t.result = ((tr = t.result) == null) ? sr : reducer.apply(tr, sr);
/*      */             } 
/* 5297 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceEntriesTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceEntriesTask<K, V, U> rights;
/*      */     MapReduceEntriesTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceEntriesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceEntriesTask<K, V, U> nextRight, ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> transformer, ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer) {
/* 5316 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5317 */       this.transformer = transformer;
/* 5318 */       this.reducer = reducer;
/*      */     } public final U getRawResult() {
/* 5320 */       return this.result;
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> transformer;
/*      */       ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/* 5324 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         int f, h;
/* 5326 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5328 */           addToPendingCount(1);
/* 5329 */           (this.rights = new MapReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 5333 */         U r = null; ConcurrentHashMapV8.Node<K, V> p;
/* 5334 */         while ((p = advance()) != null) {
/*      */           U u;
/* 5336 */           if ((u = transformer.apply(p)) != null)
/* 5337 */             r = (r == null) ? u : reducer.apply(r, u); 
/*      */         } 
/* 5339 */         this.result = r;
/*      */         
/* 5341 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5343 */           MapReduceEntriesTask<K, V, U> t = (MapReduceEntriesTask)c;
/* 5344 */           MapReduceEntriesTask<K, V, U> s = t.rights;
/* 5345 */           while (s != null) {
/*      */             U sr;
/* 5347 */             if ((sr = s.result) != null) {
/* 5348 */               U tr; t.result = ((tr = t.result) == null) ? sr : reducer.apply(tr, sr);
/*      */             } 
/* 5350 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceMappingsTask<K, V, U>
/*      */     extends BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceMappingsTask<K, V, U> rights;
/*      */     MapReduceMappingsTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceMappingsTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceMappingsTask<K, V, U> nextRight, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer, ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer) {
/* 5369 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5370 */       this.transformer = transformer;
/* 5371 */       this.reducer = reducer;
/*      */     } public final U getRawResult() {
/* 5373 */       return this.result;
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer;
/*      */       ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/* 5377 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         int f, h;
/* 5379 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5381 */           addToPendingCount(1);
/* 5382 */           (this.rights = new MapReduceMappingsTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer)).fork();
/*      */         } 
/*      */ 
/*      */         
/* 5386 */         U r = null; ConcurrentHashMapV8.Node<K, V> p;
/* 5387 */         while ((p = advance()) != null) {
/*      */           U u;
/* 5389 */           if ((u = transformer.apply(p.key, p.val)) != null)
/* 5390 */             r = (r == null) ? u : reducer.apply(r, u); 
/*      */         } 
/* 5392 */         this.result = r;
/*      */         
/* 5394 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5396 */           MapReduceMappingsTask<K, V, U> t = (MapReduceMappingsTask)c;
/* 5397 */           MapReduceMappingsTask<K, V, U> s = t.rights;
/* 5398 */           while (s != null) {
/*      */             U sr;
/* 5400 */             if ((sr = s.result) != null) {
/* 5401 */               U tr; t.result = ((tr = t.result) == null) ? sr : reducer.apply(tr, sr);
/*      */             } 
/* 5403 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceKeysToDoubleTask<K, V>
/*      */     extends BulkTask<K, V, Double>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToDouble<? super K> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/*      */     
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceKeysToDoubleTask<K, V> rights;
/*      */     MapReduceKeysToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToDoubleTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceKeysToDoubleTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToDouble<? super K> transformer, double basis, ConcurrentHashMapV8.DoubleByDoubleToDouble reducer) {
/* 5424 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5425 */       this.transformer = transformer;
/* 5426 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Double getRawResult() {
/* 5428 */       return Double.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToDouble<? super K> transformer;
/*      */       ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/* 5432 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5434 */         double r = this.basis; int f, h;
/* 5435 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5437 */           addToPendingCount(1);
/* 5438 */           (this.rights = new MapReduceKeysToDoubleTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5442 */         while ((p = advance()) != null)
/* 5443 */           r = reducer.apply(r, transformer.apply(p.key)); 
/* 5444 */         this.result = r;
/*      */         
/* 5446 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5448 */           MapReduceKeysToDoubleTask<K, V> t = (MapReduceKeysToDoubleTask)c;
/* 5449 */           MapReduceKeysToDoubleTask<K, V> s = t.rights;
/* 5450 */           while (s != null) {
/* 5451 */             t.result = reducer.apply(t.result, s.result);
/* 5452 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceValuesToDoubleTask<K, V>
/*      */     extends BulkTask<K, V, Double>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToDouble<? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/*      */     
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceValuesToDoubleTask<K, V> rights;
/*      */     MapReduceValuesToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToDoubleTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceValuesToDoubleTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToDouble<? super V> transformer, double basis, ConcurrentHashMapV8.DoubleByDoubleToDouble reducer) {
/* 5473 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5474 */       this.transformer = transformer;
/* 5475 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Double getRawResult() {
/* 5477 */       return Double.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToDouble<? super V> transformer;
/*      */       ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/* 5481 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5483 */         double r = this.basis; int f, h;
/* 5484 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5486 */           addToPendingCount(1);
/* 5487 */           (this.rights = new MapReduceValuesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5491 */         while ((p = advance()) != null)
/* 5492 */           r = reducer.apply(r, transformer.apply(p.val)); 
/* 5493 */         this.result = r;
/*      */         
/* 5495 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5497 */           MapReduceValuesToDoubleTask<K, V> t = (MapReduceValuesToDoubleTask)c;
/* 5498 */           MapReduceValuesToDoubleTask<K, V> s = t.rights;
/* 5499 */           while (s != null) {
/* 5500 */             t.result = reducer.apply(t.result, s.result);
/* 5501 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceEntriesToDoubleTask<K, V>
/*      */     extends BulkTask<K, V, Double>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToDouble<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/*      */     
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceEntriesToDoubleTask<K, V> rights;
/*      */     MapReduceEntriesToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToDoubleTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceEntriesToDoubleTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToDouble<Map.Entry<K, V>> transformer, double basis, ConcurrentHashMapV8.DoubleByDoubleToDouble reducer) {
/* 5522 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5523 */       this.transformer = transformer;
/* 5524 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Double getRawResult() {
/* 5526 */       return Double.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToDouble<Map.Entry<K, V>> transformer;
/*      */       ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/* 5530 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5532 */         double r = this.basis; int f, h;
/* 5533 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5535 */           addToPendingCount(1);
/* 5536 */           (this.rights = new MapReduceEntriesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5540 */         while ((p = advance()) != null)
/* 5541 */           r = reducer.apply(r, transformer.apply(p)); 
/* 5542 */         this.result = r;
/*      */         
/* 5544 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5546 */           MapReduceEntriesToDoubleTask<K, V> t = (MapReduceEntriesToDoubleTask)c;
/* 5547 */           MapReduceEntriesToDoubleTask<K, V> s = t.rights;
/* 5548 */           while (s != null) {
/* 5549 */             t.result = reducer.apply(t.result, s.result);
/* 5550 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceMappingsToDoubleTask<K, V>
/*      */     extends BulkTask<K, V, Double>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/*      */     
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceMappingsToDoubleTask<K, V> rights;
/*      */     MapReduceMappingsToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToDoubleTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceMappingsToDoubleTask<K, V> nextRight, ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> transformer, double basis, ConcurrentHashMapV8.DoubleByDoubleToDouble reducer) {
/* 5571 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5572 */       this.transformer = transformer;
/* 5573 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Double getRawResult() {
/* 5575 */       return Double.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> transformer;
/*      */       ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/* 5579 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5581 */         double r = this.basis; int f, h;
/* 5582 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5584 */           addToPendingCount(1);
/* 5585 */           (this.rights = new MapReduceMappingsToDoubleTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5589 */         while ((p = advance()) != null)
/* 5590 */           r = reducer.apply(r, transformer.apply(p.key, p.val)); 
/* 5591 */         this.result = r;
/*      */         
/* 5593 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5595 */           MapReduceMappingsToDoubleTask<K, V> t = (MapReduceMappingsToDoubleTask)c;
/* 5596 */           MapReduceMappingsToDoubleTask<K, V> s = t.rights;
/* 5597 */           while (s != null) {
/* 5598 */             t.result = reducer.apply(t.result, s.result);
/* 5599 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceKeysToLongTask<K, V>
/*      */     extends BulkTask<K, V, Long>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToLong<? super K> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.LongByLongToLong reducer;
/*      */     
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceKeysToLongTask<K, V> rights;
/*      */     MapReduceKeysToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToLongTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceKeysToLongTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToLong<? super K> transformer, long basis, ConcurrentHashMapV8.LongByLongToLong reducer) {
/* 5620 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5621 */       this.transformer = transformer;
/* 5622 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Long getRawResult() {
/* 5624 */       return Long.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToLong<? super K> transformer;
/*      */       ConcurrentHashMapV8.LongByLongToLong reducer;
/* 5628 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5630 */         long r = this.basis; int f, h;
/* 5631 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5633 */           addToPendingCount(1);
/* 5634 */           (this.rights = new MapReduceKeysToLongTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5638 */         while ((p = advance()) != null)
/* 5639 */           r = reducer.apply(r, transformer.apply(p.key)); 
/* 5640 */         this.result = r;
/*      */         
/* 5642 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5644 */           MapReduceKeysToLongTask<K, V> t = (MapReduceKeysToLongTask)c;
/* 5645 */           MapReduceKeysToLongTask<K, V> s = t.rights;
/* 5646 */           while (s != null) {
/* 5647 */             t.result = reducer.apply(t.result, s.result);
/* 5648 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceValuesToLongTask<K, V>
/*      */     extends BulkTask<K, V, Long>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToLong<? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.LongByLongToLong reducer;
/*      */     
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceValuesToLongTask<K, V> rights;
/*      */     MapReduceValuesToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToLongTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceValuesToLongTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToLong<? super V> transformer, long basis, ConcurrentHashMapV8.LongByLongToLong reducer) {
/* 5669 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5670 */       this.transformer = transformer;
/* 5671 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Long getRawResult() {
/* 5673 */       return Long.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToLong<? super V> transformer;
/*      */       ConcurrentHashMapV8.LongByLongToLong reducer;
/* 5677 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5679 */         long r = this.basis; int f, h;
/* 5680 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5682 */           addToPendingCount(1);
/* 5683 */           (this.rights = new MapReduceValuesToLongTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5687 */         while ((p = advance()) != null)
/* 5688 */           r = reducer.apply(r, transformer.apply(p.val)); 
/* 5689 */         this.result = r;
/*      */         
/* 5691 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5693 */           MapReduceValuesToLongTask<K, V> t = (MapReduceValuesToLongTask)c;
/* 5694 */           MapReduceValuesToLongTask<K, V> s = t.rights;
/* 5695 */           while (s != null) {
/* 5696 */             t.result = reducer.apply(t.result, s.result);
/* 5697 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceEntriesToLongTask<K, V>
/*      */     extends BulkTask<K, V, Long>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToLong<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.LongByLongToLong reducer;
/*      */     
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceEntriesToLongTask<K, V> rights;
/*      */     MapReduceEntriesToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToLongTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceEntriesToLongTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToLong<Map.Entry<K, V>> transformer, long basis, ConcurrentHashMapV8.LongByLongToLong reducer) {
/* 5718 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5719 */       this.transformer = transformer;
/* 5720 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Long getRawResult() {
/* 5722 */       return Long.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToLong<Map.Entry<K, V>> transformer;
/*      */       ConcurrentHashMapV8.LongByLongToLong reducer;
/* 5726 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5728 */         long r = this.basis; int f, h;
/* 5729 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5731 */           addToPendingCount(1);
/* 5732 */           (this.rights = new MapReduceEntriesToLongTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5736 */         while ((p = advance()) != null)
/* 5737 */           r = reducer.apply(r, transformer.apply(p)); 
/* 5738 */         this.result = r;
/*      */         
/* 5740 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5742 */           MapReduceEntriesToLongTask<K, V> t = (MapReduceEntriesToLongTask)c;
/* 5743 */           MapReduceEntriesToLongTask<K, V> s = t.rights;
/* 5744 */           while (s != null) {
/* 5745 */             t.result = reducer.apply(t.result, s.result);
/* 5746 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceMappingsToLongTask<K, V>
/*      */     extends BulkTask<K, V, Long>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.LongByLongToLong reducer;
/*      */     
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceMappingsToLongTask<K, V> rights;
/*      */     MapReduceMappingsToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToLongTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceMappingsToLongTask<K, V> nextRight, ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> transformer, long basis, ConcurrentHashMapV8.LongByLongToLong reducer) {
/* 5767 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5768 */       this.transformer = transformer;
/* 5769 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Long getRawResult() {
/* 5771 */       return Long.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> transformer;
/*      */       ConcurrentHashMapV8.LongByLongToLong reducer;
/* 5775 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5777 */         long r = this.basis; int f, h;
/* 5778 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5780 */           addToPendingCount(1);
/* 5781 */           (this.rights = new MapReduceMappingsToLongTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5785 */         while ((p = advance()) != null)
/* 5786 */           r = reducer.apply(r, transformer.apply(p.key, p.val)); 
/* 5787 */         this.result = r;
/*      */         
/* 5789 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5791 */           MapReduceMappingsToLongTask<K, V> t = (MapReduceMappingsToLongTask)c;
/* 5792 */           MapReduceMappingsToLongTask<K, V> s = t.rights;
/* 5793 */           while (s != null) {
/* 5794 */             t.result = reducer.apply(t.result, s.result);
/* 5795 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceKeysToIntTask<K, V>
/*      */     extends BulkTask<K, V, Integer>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToInt<? super K> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.IntByIntToInt reducer;
/*      */     
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceKeysToIntTask<K, V> rights;
/*      */     MapReduceKeysToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToIntTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceKeysToIntTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToInt<? super K> transformer, int basis, ConcurrentHashMapV8.IntByIntToInt reducer) {
/* 5816 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5817 */       this.transformer = transformer;
/* 5818 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Integer getRawResult() {
/* 5820 */       return Integer.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToInt<? super K> transformer;
/*      */       ConcurrentHashMapV8.IntByIntToInt reducer;
/* 5824 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5826 */         int r = this.basis; int f, h;
/* 5827 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5829 */           addToPendingCount(1);
/* 5830 */           (this.rights = new MapReduceKeysToIntTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5834 */         while ((p = advance()) != null)
/* 5835 */           r = reducer.apply(r, transformer.apply(p.key)); 
/* 5836 */         this.result = r;
/*      */         
/* 5838 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5840 */           MapReduceKeysToIntTask<K, V> t = (MapReduceKeysToIntTask)c;
/* 5841 */           MapReduceKeysToIntTask<K, V> s = t.rights;
/* 5842 */           while (s != null) {
/* 5843 */             t.result = reducer.apply(t.result, s.result);
/* 5844 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceValuesToIntTask<K, V>
/*      */     extends BulkTask<K, V, Integer>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToInt<? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.IntByIntToInt reducer;
/*      */     
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceValuesToIntTask<K, V> rights;
/*      */     MapReduceValuesToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToIntTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceValuesToIntTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToInt<? super V> transformer, int basis, ConcurrentHashMapV8.IntByIntToInt reducer) {
/* 5865 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5866 */       this.transformer = transformer;
/* 5867 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Integer getRawResult() {
/* 5869 */       return Integer.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToInt<? super V> transformer;
/*      */       ConcurrentHashMapV8.IntByIntToInt reducer;
/* 5873 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5875 */         int r = this.basis; int f, h;
/* 5876 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5878 */           addToPendingCount(1);
/* 5879 */           (this.rights = new MapReduceValuesToIntTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5883 */         while ((p = advance()) != null)
/* 5884 */           r = reducer.apply(r, transformer.apply(p.val)); 
/* 5885 */         this.result = r;
/*      */         
/* 5887 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5889 */           MapReduceValuesToIntTask<K, V> t = (MapReduceValuesToIntTask)c;
/* 5890 */           MapReduceValuesToIntTask<K, V> s = t.rights;
/* 5891 */           while (s != null) {
/* 5892 */             t.result = reducer.apply(t.result, s.result);
/* 5893 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceEntriesToIntTask<K, V>
/*      */     extends BulkTask<K, V, Integer>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToInt<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.IntByIntToInt reducer;
/*      */     
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceEntriesToIntTask<K, V> rights;
/*      */     MapReduceEntriesToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToIntTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceEntriesToIntTask<K, V> nextRight, ConcurrentHashMapV8.ObjectToInt<Map.Entry<K, V>> transformer, int basis, ConcurrentHashMapV8.IntByIntToInt reducer) {
/* 5914 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5915 */       this.transformer = transformer;
/* 5916 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Integer getRawResult() {
/* 5918 */       return Integer.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectToInt<Map.Entry<K, V>> transformer;
/*      */       ConcurrentHashMapV8.IntByIntToInt reducer;
/* 5922 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5924 */         int r = this.basis; int f, h;
/* 5925 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5927 */           addToPendingCount(1);
/* 5928 */           (this.rights = new MapReduceEntriesToIntTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5932 */         while ((p = advance()) != null)
/* 5933 */           r = reducer.apply(r, transformer.apply(p)); 
/* 5934 */         this.result = r;
/*      */         
/* 5936 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5938 */           MapReduceEntriesToIntTask<K, V> t = (MapReduceEntriesToIntTask)c;
/* 5939 */           MapReduceEntriesToIntTask<K, V> s = t.rights;
/* 5940 */           while (s != null) {
/* 5941 */             t.result = reducer.apply(t.result, s.result);
/* 5942 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MapReduceMappingsToIntTask<K, V>
/*      */     extends BulkTask<K, V, Integer>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.IntByIntToInt reducer;
/*      */     
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceMappingsToIntTask<K, V> rights;
/*      */     MapReduceMappingsToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToIntTask(ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, MapReduceMappingsToIntTask<K, V> nextRight, ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> transformer, int basis, ConcurrentHashMapV8.IntByIntToInt reducer) {
/* 5963 */       super(p, b, i, f, t); this.nextRight = nextRight;
/* 5964 */       this.transformer = transformer;
/* 5965 */       this.basis = basis; this.reducer = reducer;
/*      */     } public final Integer getRawResult() {
/* 5967 */       return Integer.valueOf(this.result);
/*      */     } public final void compute() {
/*      */       ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> transformer;
/*      */       ConcurrentHashMapV8.IntByIntToInt reducer;
/* 5971 */       if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
/*      */         
/* 5973 */         int r = this.basis; int f, h;
/* 5974 */         for (int i = this.baseIndex; this.batch > 0 && (h = (f = this.baseLimit) + i >>> 1) > i; ) {
/*      */           
/* 5976 */           addToPendingCount(1);
/* 5977 */           (this.rights = new MapReduceMappingsToIntTask(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer)).fork();
/*      */         } 
/*      */         
/*      */         ConcurrentHashMapV8.Node<K, V> p;
/* 5981 */         while ((p = advance()) != null)
/* 5982 */           r = reducer.apply(r, transformer.apply(p.key, p.val)); 
/* 5983 */         this.result = r;
/*      */         
/* 5985 */         for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
/*      */           
/* 5987 */           MapReduceMappingsToIntTask<K, V> t = (MapReduceMappingsToIntTask)c;
/* 5988 */           MapReduceMappingsToIntTask<K, V> s = t.rights;
/* 5989 */           while (s != null) {
/* 5990 */             t.result = reducer.apply(t.result, s.result);
/* 5991 */             s = t.rights = s.nextRight;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static final class CounterCell {
/*      */     volatile long p0;
/*      */     volatile long p1;
/*      */     volatile long p2;
/*      */     volatile long p3;
/*      */     volatile long p4;
/*      */     volatile long p5;
/*      */     volatile long p6;
/*      */     
/*      */     CounterCell(long x) {
/* 6008 */       this.value = x;
/*      */     }
/*      */     
/*      */     volatile long value;
/*      */     volatile long q0;
/*      */     volatile long q1;
/*      */     volatile long q2;
/*      */     volatile long q3;
/*      */     volatile long q4;
/*      */     volatile long q5;
/*      */     volatile long q6; }
/*      */   
/*      */   static final class CounterHashCode {
/*      */     int code;
/*      */   }
/* 6023 */   static final AtomicInteger counterHashCodeGenerator = new AtomicInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int SEED_INCREMENT = 1640531527;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 6034 */   static final ThreadLocal<CounterHashCode> threadCounterHashCode = new ThreadLocal<CounterHashCode>(); private static final Unsafe U; private static final long SIZECTL;
/*      */   private static final long TRANSFERINDEX;
/*      */   private static final long TRANSFERORIGIN;
/*      */   
/*      */   final long sumCount() {
/* 6039 */     CounterCell[] as = this.counterCells;
/* 6040 */     long sum = this.baseCount;
/* 6041 */     if (as != null)
/* 6042 */       for (int i = 0; i < as.length; i++) {
/* 6043 */         CounterCell a; if ((a = as[i]) != null) {
/* 6044 */           sum += a.value;
/*      */         }
/*      */       }  
/* 6047 */     return sum;
/*      */   }
/*      */   private static final long BASECOUNT; private static final long CELLSBUSY; private static final long CELLVALUE; private static final long ABASE;
/*      */   private static final int ASHIFT;
/*      */   
/*      */   private final void fullAddCount(long x, CounterHashCode hc, boolean wasUncontended) {
/*      */     int h;
/* 6054 */     if (hc == null) {
/* 6055 */       hc = new CounterHashCode();
/* 6056 */       int s = counterHashCodeGenerator.addAndGet(1640531527);
/* 6057 */       h = hc.code = (s == 0) ? 1 : s;
/* 6058 */       threadCounterHashCode.set(hc);
/*      */     } else {
/*      */       
/* 6061 */       h = hc.code;
/* 6062 */     }  boolean collide = false; while (true) {
/*      */       CounterCell[] as;
/*      */       int n;
/* 6065 */       if ((as = this.counterCells) != null && (n = as.length) > 0) {
/* 6066 */         CounterCell a; if ((a = as[n - 1 & h]) == null)
/* 6067 */         { if (this.cellsBusy == 0) {
/* 6068 */             CounterCell r = new CounterCell(x);
/* 6069 */             if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
/*      */               
/* 6071 */               boolean created = false; try {
/*      */                 CounterCell[] rs; int m;
/*      */                 int j;
/* 6074 */                 if ((rs = this.counterCells) != null && (m = rs.length) > 0 && rs[j = m - 1 & h] == null) {
/*      */ 
/*      */                   
/* 6077 */                   rs[j] = r;
/* 6078 */                   created = true;
/*      */                 } 
/*      */               } finally {
/* 6081 */                 this.cellsBusy = 0;
/*      */               } 
/* 6083 */               if (created)
/*      */                 break; 
/*      */               continue;
/*      */             } 
/*      */           } 
/* 6088 */           collide = false; }
/*      */         
/* 6090 */         else if (!wasUncontended)
/* 6091 */         { wasUncontended = true; }
/* 6092 */         else { long l; if (U.compareAndSwapLong(a, CELLVALUE, l = a.value, l + x))
/*      */             break; 
/* 6094 */           if (this.counterCells != as || n >= NCPU) {
/* 6095 */             collide = false;
/* 6096 */           } else if (!collide) {
/* 6097 */             collide = true;
/* 6098 */           } else if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
/*      */             
/*      */             try {
/* 6101 */               if (this.counterCells == as) {
/* 6102 */                 CounterCell[] rs = new CounterCell[n << 1];
/* 6103 */                 for (int i = 0; i < n; i++)
/* 6104 */                   rs[i] = as[i]; 
/* 6105 */                 this.counterCells = rs;
/*      */               } 
/*      */             } finally {
/* 6108 */               this.cellsBusy = 0;
/*      */             } 
/* 6110 */             collide = false; continue;
/*      */           }  }
/*      */         
/* 6113 */         h ^= h << 13;
/* 6114 */         h ^= h >>> 17;
/* 6115 */         h ^= h << 5; continue;
/*      */       } 
/* 6117 */       if (this.cellsBusy == 0 && this.counterCells == as && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
/*      */         
/* 6119 */         boolean init = false;
/*      */         try {
/* 6121 */           if (this.counterCells == as) {
/* 6122 */             CounterCell[] rs = new CounterCell[2];
/* 6123 */             rs[h & 0x1] = new CounterCell(x);
/* 6124 */             this.counterCells = rs;
/* 6125 */             init = true;
/*      */           } 
/*      */         } finally {
/* 6128 */           this.cellsBusy = 0;
/*      */         } 
/* 6130 */         if (init)
/*      */           break;  continue;
/*      */       }  long v;
/* 6133 */       if (U.compareAndSwapLong(this, BASECOUNT, v = this.baseCount, v + x))
/*      */         break; 
/*      */     } 
/* 6136 */     hc.code = h;
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
/*      */   static {
/*      */     try {
/* 6152 */       U = getUnsafe();
/* 6153 */       Class<?> k = ConcurrentHashMapV8.class;
/* 6154 */       SIZECTL = U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
/*      */       
/* 6156 */       TRANSFERINDEX = U.objectFieldOffset(k.getDeclaredField("transferIndex"));
/*      */       
/* 6158 */       TRANSFERORIGIN = U.objectFieldOffset(k.getDeclaredField("transferOrigin"));
/*      */       
/* 6160 */       BASECOUNT = U.objectFieldOffset(k.getDeclaredField("baseCount"));
/*      */       
/* 6162 */       CELLSBUSY = U.objectFieldOffset(k.getDeclaredField("cellsBusy"));
/*      */       
/* 6164 */       Class<?> ck = CounterCell.class;
/* 6165 */       CELLVALUE = U.objectFieldOffset(ck.getDeclaredField("value"));
/*      */       
/* 6167 */       Class<?> ak = Node[].class;
/* 6168 */       ABASE = U.arrayBaseOffset(ak);
/* 6169 */       int scale = U.arrayIndexScale(ak);
/* 6170 */       if ((scale & scale - 1) != 0)
/* 6171 */         throw new Error("data type scale not a power of two"); 
/* 6172 */       ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
/* 6173 */     } catch (Exception e) {
/* 6174 */       throw new Error(e);
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
/*      */   private static Unsafe getUnsafe() {
/*      */     try {
/* 6187 */       return Unsafe.getUnsafe();
/* 6188 */     } catch (SecurityException tryReflectionInstead) {
/*      */       try {
/* 6190 */         return AccessController.<Unsafe>doPrivileged(new PrivilegedExceptionAction<Unsafe>()
/*      */             {
/*      */               public Unsafe run() throws Exception {
/* 6193 */                 Class<Unsafe> k = Unsafe.class;
/* 6194 */                 for (Field f : k.getDeclaredFields()) {
/* 6195 */                   f.setAccessible(true);
/* 6196 */                   Object x = f.get(null);
/* 6197 */                   if (k.isInstance(x))
/* 6198 */                     return k.cast(x); 
/*      */                 } 
/* 6200 */                 throw new NoSuchFieldError("the Unsafe"); }
/*      */             });
/* 6202 */       } catch (PrivilegedActionException e) {
/* 6203 */         throw new RuntimeException("Could not initialize intrinsics", e.getCause());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface IntByIntToInt {
/*      */     int apply(int param1Int1, int param1Int2);
/*      */   }
/*      */   
/*      */   public static interface LongByLongToLong {
/*      */     long apply(long param1Long1, long param1Long2);
/*      */   }
/*      */   
/*      */   public static interface DoubleByDoubleToDouble {
/*      */     double apply(double param1Double1, double param1Double2);
/*      */   }
/*      */   
/*      */   public static interface ObjectByObjectToInt<A, B> {
/*      */     int apply(A param1A, B param1B);
/*      */   }
/*      */   
/*      */   public static interface ObjectByObjectToLong<A, B> {
/*      */     long apply(A param1A, B param1B);
/*      */   }
/*      */   
/*      */   public static interface ObjectByObjectToDouble<A, B> {
/*      */     double apply(A param1A, B param1B);
/*      */   }
/*      */   
/*      */   public static interface ObjectToInt<A> {
/*      */     int apply(A param1A);
/*      */   }
/*      */   
/*      */   public static interface ObjectToLong<A> {
/*      */     long apply(A param1A);
/*      */   }
/*      */   
/*      */   public static interface ObjectToDouble<A> {
/*      */     double apply(A param1A);
/*      */   }
/*      */   
/*      */   public static interface BiFun<A, B, T> {
/*      */     T apply(A param1A, B param1B);
/*      */   }
/*      */   
/*      */   public static interface Fun<A, T> {
/*      */     T apply(A param1A);
/*      */   }
/*      */   
/*      */   public static interface BiAction<A, B> {
/*      */     void apply(A param1A, B param1B);
/*      */   }
/*      */   
/*      */   public static interface Action<A> {
/*      */     void apply(A param1A);
/*      */   }
/*      */   
/*      */   public static interface ConcurrentHashMapSpliterator<T> {
/*      */     ConcurrentHashMapSpliterator<T> trySplit();
/*      */     
/*      */     long estimateSize();
/*      */     
/*      */     void forEachRemaining(ConcurrentHashMapV8.Action<? super T> param1Action);
/*      */     
/*      */     boolean tryAdvance(ConcurrentHashMapV8.Action<? super T> param1Action);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\chmv8\ConcurrentHashMapV8.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */