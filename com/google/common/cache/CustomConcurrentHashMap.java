/*      */ package com.google.common.cache;
/*      */ 
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.base.Equivalence;
/*      */ import com.google.common.base.Equivalences;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Supplier;
/*      */ import com.google.common.base.Ticker;
/*      */ import com.google.common.collect.AbstractLinkedIterator;
/*      */ import com.google.common.collect.Iterators;
/*      */ import com.google.common.primitives.Ints;
/*      */ import com.google.common.util.concurrent.ExecutionError;
/*      */ import com.google.common.util.concurrent.UncheckedExecutionException;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.annotation.Nullable;
/*      */ import javax.annotation.concurrent.GuardedBy;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class CustomConcurrentHashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements ConcurrentMap<K, V>
/*      */ {
/*      */   static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   static final int MAX_SEGMENTS = 65536;
/*      */   static final int CONTAINS_VALUE_RETRIES = 3;
/*      */   static final int DRAIN_THRESHOLD = 63;
/*      */   static final int DRAIN_MAX = 16;
/*  137 */   private static final Logger logger = Logger.getLogger(CustomConcurrentHashMap.class.getName());
/*      */ 
/*      */ 
/*      */   
/*      */   final int segmentMask;
/*      */ 
/*      */ 
/*      */   
/*      */   final int segmentShift;
/*      */ 
/*      */ 
/*      */   
/*      */   final Segment<K, V>[] segments;
/*      */ 
/*      */ 
/*      */   
/*      */   final CacheLoader<? super K, V> loader;
/*      */ 
/*      */ 
/*      */   
/*      */   final int concurrencyLevel;
/*      */ 
/*      */ 
/*      */   
/*      */   final Equivalence<Object> keyEquivalence;
/*      */ 
/*      */ 
/*      */   
/*      */   final Equivalence<Object> valueEquivalence;
/*      */ 
/*      */ 
/*      */   
/*      */   final Strength keyStrength;
/*      */ 
/*      */ 
/*      */   
/*      */   final Strength valueStrength;
/*      */ 
/*      */ 
/*      */   
/*      */   final int maximumSize;
/*      */ 
/*      */ 
/*      */   
/*      */   final long expireAfterAccessNanos;
/*      */ 
/*      */ 
/*      */   
/*      */   final long expireAfterWriteNanos;
/*      */ 
/*      */   
/*      */   final Queue<RemovalNotification<K, V>> removalNotificationQueue;
/*      */ 
/*      */   
/*      */   final RemovalListener<K, V> removalListener;
/*      */ 
/*      */   
/*      */   final EntryFactory entryFactory;
/*      */ 
/*      */   
/*      */   final Ticker ticker;
/*      */ 
/*      */ 
/*      */   
/*      */   CustomConcurrentHashMap(CacheBuilder<? super K, ? super V> builder, Supplier<? extends AbstractCache.StatsCounter> statsCounterSupplier, CacheLoader<? super K, V> loader) {
/*  202 */     this.loader = (CacheLoader<? super K, V>)Preconditions.checkNotNull(loader);
/*      */     
/*  204 */     this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
/*      */     
/*  206 */     this.keyStrength = builder.getKeyStrength();
/*  207 */     this.valueStrength = builder.getValueStrength();
/*      */     
/*  209 */     this.keyEquivalence = builder.getKeyEquivalence();
/*  210 */     this.valueEquivalence = builder.getValueEquivalence();
/*      */     
/*  212 */     this.maximumSize = builder.maximumSize;
/*  213 */     this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
/*  214 */     this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
/*      */     
/*  216 */     this.entryFactory = EntryFactory.getFactory(this.keyStrength, expires(), evictsBySize());
/*  217 */     this.ticker = builder.getTicker();
/*      */     
/*  219 */     this.removalListener = builder.getRemovalListener();
/*  220 */     this.removalNotificationQueue = (this.removalListener == CacheBuilder.NullListener.INSTANCE) ? discardingQueue() : new ConcurrentLinkedQueue<RemovalNotification<K, V>>();
/*      */ 
/*      */ 
/*      */     
/*  224 */     int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
/*  225 */     if (evictsBySize()) {
/*  226 */       initialCapacity = Math.min(initialCapacity, this.maximumSize);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  232 */     int segmentShift = 0;
/*  233 */     int segmentCount = 1;
/*      */     
/*  235 */     while (segmentCount < this.concurrencyLevel && (!evictsBySize() || segmentCount * 2 <= this.maximumSize)) {
/*  236 */       segmentShift++;
/*  237 */       segmentCount <<= 1;
/*      */     } 
/*  239 */     this.segmentShift = 32 - segmentShift;
/*  240 */     this.segmentMask = segmentCount - 1;
/*      */     
/*  242 */     this.segments = newSegmentArray(segmentCount);
/*      */     
/*  244 */     int segmentCapacity = initialCapacity / segmentCount;
/*  245 */     if (segmentCapacity * segmentCount < initialCapacity) {
/*  246 */       segmentCapacity++;
/*      */     }
/*      */     
/*  249 */     int segmentSize = 1;
/*  250 */     while (segmentSize < segmentCapacity) {
/*  251 */       segmentSize <<= 1;
/*      */     }
/*      */     
/*  254 */     if (evictsBySize()) {
/*      */       
/*  256 */       int maximumSegmentSize = this.maximumSize / segmentCount + 1;
/*  257 */       int remainder = this.maximumSize % segmentCount;
/*  258 */       for (int i = 0; i < this.segments.length; i++) {
/*  259 */         if (i == remainder) {
/*  260 */           maximumSegmentSize--;
/*      */         }
/*  262 */         this.segments[i] = createSegment(segmentSize, maximumSegmentSize, (AbstractCache.StatsCounter)statsCounterSupplier.get());
/*      */       } 
/*      */     } else {
/*      */       
/*  266 */       for (int i = 0; i < this.segments.length; i++) {
/*  267 */         this.segments[i] = createSegment(segmentSize, -1, (AbstractCache.StatsCounter)statsCounterSupplier.get());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean evictsBySize() {
/*  274 */     return (this.maximumSize != -1);
/*      */   }
/*      */   
/*      */   boolean expires() {
/*  278 */     return (expiresAfterWrite() || expiresAfterAccess());
/*      */   }
/*      */   
/*      */   boolean expiresAfterWrite() {
/*  282 */     return (this.expireAfterWriteNanos > 0L);
/*      */   }
/*      */   
/*      */   boolean expiresAfterAccess() {
/*  286 */     return (this.expireAfterAccessNanos > 0L);
/*      */   }
/*      */   
/*      */   boolean usesKeyReferences() {
/*  290 */     return (this.keyStrength != Strength.STRONG);
/*      */   }
/*      */   
/*      */   boolean usesValueReferences() {
/*  294 */     return (this.valueStrength != Strength.STRONG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum Strength
/*      */   {
/*  303 */     STRONG
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> entry, V value)
/*      */       {
/*  307 */         return new CustomConcurrentHashMap.StrongValueReference<K, V>(value);
/*      */       }
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  312 */         return Equivalences.equals();
/*      */       }
/*      */     },
/*      */     
/*  316 */     SOFT
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> entry, V value)
/*      */       {
/*  320 */         return new CustomConcurrentHashMap.SoftValueReference<K, V>(segment.valueReferenceQueue, value, entry);
/*      */       }
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  325 */         return Equivalences.identity();
/*      */       }
/*      */     },
/*      */     
/*  329 */     WEAK
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> entry, V value)
/*      */       {
/*  333 */         return new CustomConcurrentHashMap.WeakValueReference<K, V>(segment.valueReferenceQueue, value, entry);
/*      */       }
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  338 */         return Equivalences.identity();
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> param1Segment, CustomConcurrentHashMap.ReferenceEntry<K, V> param1ReferenceEntry, V param1V);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract Equivalence<Object> defaultEquivalence();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum EntryFactory
/*      */   {
/*  360 */     STRONG
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  364 */         return new CustomConcurrentHashMap.StrongEntry<K, V>(key, hash, next);
/*      */       }
/*      */     },
/*  367 */     STRONG_EXPIRABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  371 */         return new CustomConcurrentHashMap.StrongExpirableEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  377 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  378 */         copyExpirableEntry(original, newEntry);
/*  379 */         return newEntry;
/*      */       }
/*      */     },
/*  382 */     STRONG_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  386 */         return new CustomConcurrentHashMap.StrongEvictableEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  392 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  393 */         copyEvictableEntry(original, newEntry);
/*  394 */         return newEntry;
/*      */       }
/*      */     },
/*  397 */     STRONG_EXPIRABLE_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  401 */         return new CustomConcurrentHashMap.StrongExpirableEvictableEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  407 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  408 */         copyExpirableEntry(original, newEntry);
/*  409 */         copyEvictableEntry(original, newEntry);
/*  410 */         return newEntry;
/*      */       }
/*      */     },
/*      */     
/*  414 */     SOFT
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  418 */         return new CustomConcurrentHashMap.SoftEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */     },
/*  421 */     SOFT_EXPIRABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  425 */         return new CustomConcurrentHashMap.SoftExpirableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  431 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  432 */         copyExpirableEntry(original, newEntry);
/*  433 */         return newEntry;
/*      */       }
/*      */     },
/*  436 */     SOFT_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  440 */         return new CustomConcurrentHashMap.SoftEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  446 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  447 */         copyEvictableEntry(original, newEntry);
/*  448 */         return newEntry;
/*      */       }
/*      */     },
/*  451 */     SOFT_EXPIRABLE_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  455 */         return new CustomConcurrentHashMap.SoftExpirableEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  461 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  462 */         copyExpirableEntry(original, newEntry);
/*  463 */         copyEvictableEntry(original, newEntry);
/*  464 */         return newEntry;
/*      */       }
/*      */     },
/*      */     
/*  468 */     WEAK
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  472 */         return new CustomConcurrentHashMap.WeakEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */     },
/*  475 */     WEAK_EXPIRABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  479 */         return new CustomConcurrentHashMap.WeakExpirableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  485 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  486 */         copyExpirableEntry(original, newEntry);
/*  487 */         return newEntry;
/*      */       }
/*      */     },
/*  490 */     WEAK_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  494 */         return new CustomConcurrentHashMap.WeakEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  500 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  501 */         copyEvictableEntry(original, newEntry);
/*  502 */         return newEntry;
/*      */       }
/*      */     },
/*  505 */     WEAK_EXPIRABLE_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  509 */         return new CustomConcurrentHashMap.WeakExpirableEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  515 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  516 */         copyExpirableEntry(original, newEntry);
/*  517 */         copyEvictableEntry(original, newEntry);
/*  518 */         return newEntry;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static final int EXPIRABLE_MASK = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     static final int EVICTABLE_MASK = 2;
/*      */ 
/*      */     
/*  532 */     static final EntryFactory[][] factories = new EntryFactory[][] { { STRONG, STRONG_EXPIRABLE, STRONG_EVICTABLE, STRONG_EXPIRABLE_EVICTABLE }, { SOFT, SOFT_EXPIRABLE, SOFT_EVICTABLE, SOFT_EXPIRABLE_EVICTABLE }, { WEAK, WEAK_EXPIRABLE, WEAK_EVICTABLE, WEAK_EXPIRABLE_EVICTABLE } };
/*      */ 
/*      */     
/*      */     static {
/*      */     
/*      */     }
/*      */     
/*      */     static EntryFactory getFactory(CustomConcurrentHashMap.Strength keyStrength, boolean expireAfterWrite, boolean evictsBySize) {
/*  540 */       int flags = (expireAfterWrite ? 1 : 0) | (evictsBySize ? 2 : 0);
/*  541 */       return factories[keyStrength.ordinal()][flags];
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  564 */       return newEntry(segment, original.getKey(), original.getHash(), newNext);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     <K, V> void copyExpirableEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry) {
/*  571 */       newEntry.setExpirationTime(original.getExpirationTime());
/*      */       
/*  573 */       CustomConcurrentHashMap.connectExpirables(original.getPreviousExpirable(), newEntry);
/*  574 */       CustomConcurrentHashMap.connectExpirables(newEntry, original.getNextExpirable());
/*      */       
/*  576 */       CustomConcurrentHashMap.nullifyExpirable(original);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     <K, V> void copyEvictableEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry) {
/*  583 */       CustomConcurrentHashMap.connectEvictables(original.getPreviousEvictable(), newEntry);
/*  584 */       CustomConcurrentHashMap.connectEvictables(newEntry, original.getNextEvictable());
/*      */       
/*  586 */       CustomConcurrentHashMap.nullifyEvictable(original);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> param1Segment, K param1K, int param1Int, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> param1ReferenceEntry);
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
/*  636 */   static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>()
/*      */     {
/*      */       public Object get() {
/*  639 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getEntry() {
/*  644 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public CustomConcurrentHashMap.ValueReference<Object, Object> copyFor(ReferenceQueue<Object> queue, CustomConcurrentHashMap.ReferenceEntry<Object, Object> entry) {
/*  650 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isComputingReference() {
/*  655 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object waitForValue() {
/*  660 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void notifyNewValue(Object newValue) {}
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> ValueReference<K, V> unset() {
/*  672 */     return (ValueReference)UNSET;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum NullEntry
/*      */     implements ReferenceEntry<Object, Object>
/*      */   {
/*  779 */     INSTANCE;
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<Object, Object> getValueReference() {
/*  783 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValueReference(CustomConcurrentHashMap.ValueReference<Object, Object> valueReference) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNext() {
/*  791 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/*  796 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getKey() {
/*  801 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getExpirationTime() {
/*  806 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setExpirationTime(long time) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNextExpirable() {
/*  814 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> next) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getPreviousExpirable() {
/*  822 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> previous) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNextEvictable() {
/*  830 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> next) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getPreviousEvictable() {
/*  838 */       return this;
/*      */     }
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> previous) {}
/*      */   }
/*      */   
/*      */   static abstract class AbstractReferenceEntry<K, V>
/*      */     implements ReferenceEntry<K, V>
/*      */   {
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> getValueReference() {
/*  848 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/*  853 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext() {
/*  858 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/*  863 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  868 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getExpirationTime() {
/*  873 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setExpirationTime(long time) {
/*  878 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() {
/*  883 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*  888 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/*  893 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/*  898 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/*  903 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*  908 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/*  913 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/*  918 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> ReferenceEntry<K, V> nullEntry() {
/*  924 */     return NullEntry.INSTANCE;
/*      */   }
/*      */   
/*  927 */   static final Queue<? extends Object> DISCARDING_QUEUE = new AbstractQueue()
/*      */     {
/*      */       public boolean offer(Object o) {
/*  930 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object peek() {
/*  935 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object poll() {
/*  940 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public int size() {
/*  945 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Object> iterator() {
/*  950 */         return (Iterator<Object>)Iterators.emptyIterator();
/*      */       }
/*      */     };
/*      */   
/*      */   Set<K> keySet;
/*      */   Collection<V> values;
/*      */   Set<Map.Entry<K, V>> entrySet;
/*      */   
/*      */   static <E> Queue<E> discardingQueue() {
/*  959 */     return (Queue)DISCARDING_QUEUE;
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
/*      */   static class StrongEntry<K, V>
/*      */     implements ReferenceEntry<K, V>
/*      */   {
/*      */     final K key;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int hash;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final CustomConcurrentHashMap.ReferenceEntry<K, V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile CustomConcurrentHashMap.ValueReference<K, V> valueReference;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     StrongEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/* 1045 */       this.valueReference = CustomConcurrentHashMap.unset(); this.key = key; this.hash = hash; this.next = next;
/*      */     }
/*      */     public K getKey() { return this.key; }
/*      */     public long getExpirationTime() { throw new UnsupportedOperationException(); }
/* 1049 */     public void setExpirationTime(long time) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { throw new UnsupportedOperationException(); } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { throw new UnsupportedOperationException(); }
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); }
/* 1054 */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { throw new UnsupportedOperationException(); } public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); } public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) { this.valueReference = valueReference; }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getHash() {
/* 1059 */       return this.hash;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext() {
/* 1064 */       return this.next;
/*      */     } }
/*      */   static final class StrongExpirableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V> { volatile long time; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable;
/*      */     
/* 1071 */     StrongExpirableEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1076 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1088 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1101 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; }
/*      */     public void setExpirationTime(long time) { this.time = time; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; }
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/* 1106 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1111 */       this.previousExpirable = previous;
/*      */     } }
/*      */   static final class StrongEvictableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V> { @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1118 */     StrongEvictableEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1123 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1136 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/*      */       return this.nextEvictable;
/*      */     } public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*      */       this.nextEvictable = next;
/*      */     } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/* 1141 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1146 */       this.previousEvictable = previous;
/*      */     } }
/*      */   static final class StrongExpirableEvictableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V> { volatile long time; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/* 1153 */     StrongExpirableEvictableEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1158 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1170 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1183 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1198 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1211 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; } public void setExpirationTime(long time) { this.time = time; } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { return this.previousExpirable; }
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { this.previousExpirable = previous; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { return this.nextEvictable; }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextEvictable = next; }
/* 1216 */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { return this.previousEvictable; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1221 */       this.previousEvictable = previous;
/*      */     } }
/*      */ 
/*      */   
/*      */   static class SoftEntry<K, V> extends SoftReference<K> implements ReferenceEntry<K, V> {
/*      */     final int hash;
/*      */     final CustomConcurrentHashMap.ReferenceEntry<K, V> next;
/*      */     volatile CustomConcurrentHashMap.ValueReference<K, V> valueReference;
/*      */     
/* 1230 */     SoftEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, queue);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1297 */       this.valueReference = CustomConcurrentHashMap.unset(); this.hash = hash; this.next = next; } public K getKey() { return get(); } public long getExpirationTime() { throw new UnsupportedOperationException(); } public void setExpirationTime(long time) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { throw new UnsupportedOperationException(); } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { throw new UnsupportedOperationException(); } public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); }
/* 1301 */     public CustomConcurrentHashMap.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 1306 */       this.valueReference = valueReference;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/* 1311 */       return this.hash;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext() {
/* 1316 */       return this.next;
/*      */     } }
/*      */   static final class SoftExpirableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V> { volatile long time;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable;
/*      */     
/* 1324 */     SoftExpirableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1329 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1341 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1354 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; }
/*      */     public void setExpirationTime(long time) { this.time = time; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; }
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/* 1359 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1364 */       this.previousExpirable = previous;
/*      */     } }
/*      */   
/*      */   static final class SoftEvictableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V> { @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1372 */     SoftEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1377 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1390 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/*      */       return this.nextEvictable;
/*      */     } public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*      */       this.nextEvictable = next;
/*      */     } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/* 1395 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1400 */       this.previousEvictable = previous;
/*      */     } }
/*      */   static final class SoftExpirableEvictableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V> { volatile long time; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1408 */     SoftExpirableEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1413 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1425 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1438 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1453 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1466 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; } public void setExpirationTime(long time) { this.time = time; } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { return this.previousExpirable; }
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { this.previousExpirable = previous; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { return this.nextEvictable; }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextEvictable = next; }
/* 1471 */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { return this.previousEvictable; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1476 */       this.previousEvictable = previous;
/*      */     } }
/*      */ 
/*      */   
/*      */   static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
/*      */     final int hash;
/*      */     final CustomConcurrentHashMap.ReferenceEntry<K, V> next;
/*      */     volatile CustomConcurrentHashMap.ValueReference<K, V> valueReference;
/*      */     
/* 1485 */     WeakEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, queue);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1553 */       this.valueReference = CustomConcurrentHashMap.unset(); this.hash = hash; this.next = next; } public K getKey() { return get(); } public long getExpirationTime() { throw new UnsupportedOperationException(); } public void setExpirationTime(long time) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { throw new UnsupportedOperationException(); } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { throw new UnsupportedOperationException(); } public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); }
/* 1557 */     public CustomConcurrentHashMap.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 1562 */       this.valueReference = valueReference;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/* 1567 */       return this.hash;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext() {
/* 1572 */       return this.next;
/*      */     } }
/*      */   static final class WeakExpirableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V> { volatile long time;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable;
/*      */     
/* 1580 */     WeakExpirableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1585 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1597 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1610 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; }
/*      */     public void setExpirationTime(long time) { this.time = time; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; }
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/* 1615 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1620 */       this.previousExpirable = previous;
/*      */     } }
/*      */   
/*      */   static final class WeakEvictableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V> { @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1628 */     WeakEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1633 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1646 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/*      */       return this.nextEvictable;
/*      */     } public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*      */       this.nextEvictable = next;
/*      */     } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/* 1651 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1656 */       this.previousEvictable = previous;
/*      */     } }
/*      */   static final class WeakExpirableEvictableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V> { volatile long time; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1664 */     WeakExpirableEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1669 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1681 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1694 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1709 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1722 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; } public void setExpirationTime(long time) { this.time = time; } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { return this.previousExpirable; }
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { this.previousExpirable = previous; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { return this.nextEvictable; }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextEvictable = next; }
/* 1727 */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { return this.previousEvictable; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1732 */       this.previousEvictable = previous;
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class WeakValueReference<K, V>
/*      */     extends WeakReference<V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final CustomConcurrentHashMap.ReferenceEntry<K, V> entry;
/*      */     
/*      */     WeakValueReference(ReferenceQueue<V> queue, V referent, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 1744 */       super(referent, queue);
/* 1745 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 1750 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public void notifyNewValue(V newValue) {
/* 1755 */       clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 1761 */       return new WeakValueReference(queue, get(), entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isComputingReference() {
/* 1766 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1771 */       return get();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SoftValueReference<K, V>
/*      */     extends SoftReference<V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final CustomConcurrentHashMap.ReferenceEntry<K, V> entry;
/*      */     
/*      */     SoftValueReference(ReferenceQueue<V> queue, V referent, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 1783 */       super(referent, queue);
/* 1784 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 1789 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public void notifyNewValue(V newValue) {
/* 1794 */       clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 1799 */       return new SoftValueReference(queue, get(), entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isComputingReference() {
/* 1804 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1809 */       return get();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class StrongValueReference<K, V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final V referent;
/*      */     
/*      */     StrongValueReference(V referent) {
/* 1820 */       this.referent = referent;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 1825 */       return this.referent;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 1830 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 1835 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isComputingReference() {
/* 1840 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1845 */       return get();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void notifyNewValue(V newValue) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int rehash(int h) {
/* 1864 */     h += h << 15 ^ 0xFFFFCD7D;
/* 1865 */     h ^= h >>> 10;
/* 1866 */     h += h << 3;
/* 1867 */     h ^= h >>> 6;
/* 1868 */     h += (h << 2) + (h << 14);
/* 1869 */     return h ^ h >>> 16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   @VisibleForTesting
/*      */   ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
/* 1878 */     return segmentFor(hash).newEntry(key, hash, next);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   @VisibleForTesting
/*      */   ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
/* 1887 */     int hash = original.getHash();
/* 1888 */     return segmentFor(hash).copyEntry(original, newNext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   @VisibleForTesting
/*      */   ValueReference<K, V> newValueReference(ReferenceEntry<K, V> entry, V value) {
/* 1897 */     int hash = entry.getHash();
/* 1898 */     return this.valueStrength.referenceValue(segmentFor(hash), entry, value);
/*      */   }
/*      */   
/*      */   int hash(Object key) {
/* 1902 */     int h = this.keyEquivalence.hash(key);
/* 1903 */     return rehash(h);
/*      */   }
/*      */   
/*      */   void reclaimValue(ValueReference<K, V> valueReference) {
/* 1907 */     ReferenceEntry<K, V> entry = valueReference.getEntry();
/* 1908 */     int hash = entry.getHash();
/* 1909 */     segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
/*      */   }
/*      */   
/*      */   void reclaimKey(ReferenceEntry<K, V> entry) {
/* 1913 */     int hash = entry.getHash();
/* 1914 */     segmentFor(hash).reclaimKey(entry, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   boolean isLive(ReferenceEntry<K, V> entry) {
/* 1923 */     return (segmentFor(entry.getHash()).getLiveValue(entry) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Segment<K, V> segmentFor(int hash) {
/* 1934 */     return this.segments[hash >>> this.segmentShift & this.segmentMask];
/*      */   }
/*      */ 
/*      */   
/*      */   Segment<K, V> createSegment(int initialCapacity, int maxSegmentSize, AbstractCache.StatsCounter statsCounter) {
/* 1939 */     return new Segment<K, V>(this, initialCapacity, maxSegmentSize, statsCounter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   V getLiveValue(ReferenceEntry<K, V> entry) {
/* 1948 */     if (entry.getKey() == null) {
/* 1949 */       return null;
/*      */     }
/* 1951 */     V value = (V)entry.getValueReference().get();
/* 1952 */     if (value == null) {
/* 1953 */       return null;
/*      */     }
/*      */     
/* 1956 */     if (expires() && isExpired(entry)) {
/* 1957 */       return null;
/*      */     }
/* 1959 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isExpired(ReferenceEntry<K, V> entry) {
/* 1968 */     return isExpired(entry, this.ticker.read());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isExpired(ReferenceEntry<K, V> entry, long now) {
/* 1976 */     return (now - entry.getExpirationTime() > 0L);
/*      */   }
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   static <K, V> void connectExpirables(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
/* 1981 */     previous.setNextExpirable(next);
/* 1982 */     next.setPreviousExpirable(previous);
/*      */   }
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   static <K, V> void nullifyExpirable(ReferenceEntry<K, V> nulled) {
/* 1987 */     ReferenceEntry<K, V> nullEntry = nullEntry();
/* 1988 */     nulled.setNextExpirable(nullEntry);
/* 1989 */     nulled.setPreviousExpirable(nullEntry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void processPendingNotifications() {
/*      */     RemovalNotification<K, V> notification;
/* 2001 */     while ((notification = this.removalNotificationQueue.poll()) != null) {
/*      */       try {
/* 2003 */         this.removalListener.onRemoval(notification);
/* 2004 */       } catch (Exception e) {
/* 2005 */         logger.log(Level.WARNING, "Exception thrown by removal listener", e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   static <K, V> void connectEvictables(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
/* 2013 */     previous.setNextEvictable(next);
/* 2014 */     next.setPreviousEvictable(previous);
/*      */   }
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   static <K, V> void nullifyEvictable(ReferenceEntry<K, V> nulled) {
/* 2019 */     ReferenceEntry<K, V> nullEntry = nullEntry();
/* 2020 */     nulled.setNextEvictable(nullEntry);
/* 2021 */     nulled.setPreviousEvictable(nullEntry);
/*      */   }
/*      */ 
/*      */   
/*      */   final Segment<K, V>[] newSegmentArray(int ssize) {
/* 2026 */     return (Segment<K, V>[])new Segment[ssize];
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
/*      */   static class Segment<K, V>
/*      */     extends ReentrantLock
/*      */   {
/*      */     final CustomConcurrentHashMap<K, V> map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile int count;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int modCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int threshold;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     volatile AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int maxSegmentSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ReferenceQueue<K> keyReferenceQueue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ReferenceQueue<V> valueReferenceQueue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Queue<CustomConcurrentHashMap.ReferenceEntry<K, V>> recencyQueue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2126 */     final AtomicInteger readCount = new AtomicInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     final Queue<CustomConcurrentHashMap.ReferenceEntry<K, V>> evictionQueue;
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     final Queue<CustomConcurrentHashMap.ReferenceEntry<K, V>> expirationQueue;
/*      */ 
/*      */ 
/*      */     
/*      */     final AbstractCache.StatsCounter statsCounter;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Segment(CustomConcurrentHashMap<K, V> map, int initialCapacity, int maxSegmentSize, AbstractCache.StatsCounter statsCounter) {
/* 2147 */       this.map = map;
/* 2148 */       this.maxSegmentSize = maxSegmentSize;
/* 2149 */       this.statsCounter = statsCounter;
/* 2150 */       initTable(newEntryArray(initialCapacity));
/*      */       
/* 2152 */       this.keyReferenceQueue = map.usesKeyReferences() ? new ReferenceQueue<K>() : null;
/*      */ 
/*      */       
/* 2155 */       this.valueReferenceQueue = map.usesValueReferences() ? new ReferenceQueue<V>() : null;
/*      */ 
/*      */       
/* 2158 */       this.recencyQueue = (map.evictsBySize() || map.expiresAfterAccess()) ? new ConcurrentLinkedQueue<CustomConcurrentHashMap.ReferenceEntry<K, V>>() : CustomConcurrentHashMap.<CustomConcurrentHashMap.ReferenceEntry<K, V>>discardingQueue();
/*      */ 
/*      */ 
/*      */       
/* 2162 */       this.evictionQueue = map.evictsBySize() ? new CustomConcurrentHashMap.EvictionQueue<K, V>() : CustomConcurrentHashMap.<CustomConcurrentHashMap.ReferenceEntry<K, V>>discardingQueue();
/*      */ 
/*      */ 
/*      */       
/* 2166 */       this.expirationQueue = map.expires() ? new CustomConcurrentHashMap.ExpirationQueue<K, V>() : CustomConcurrentHashMap.<CustomConcurrentHashMap.ReferenceEntry<K, V>>discardingQueue();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> newEntryArray(int size) {
/* 2172 */       return new AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>>(size);
/*      */     }
/*      */     
/*      */     void initTable(AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> newTable) {
/* 2176 */       this.threshold = newTable.length() * 3 / 4;
/* 2177 */       if (this.threshold == this.maxSegmentSize)
/*      */       {
/* 2179 */         this.threshold++;
/*      */       }
/* 2181 */       this.table = newTable;
/*      */     }
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/* 2186 */       return this.map.entryFactory.newEntry(this, key, hash, next);
/*      */     }
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/* 2191 */       CustomConcurrentHashMap.ValueReference<K, V> valueReference = original.getValueReference();
/* 2192 */       CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
/* 2193 */       newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, newEntry));
/* 2194 */       return newEntry;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void setValue(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, V value) {
/* 2202 */       CustomConcurrentHashMap.ValueReference<K, V> previous = entry.getValueReference();
/* 2203 */       CustomConcurrentHashMap.ValueReference<K, V> valueReference = this.map.valueStrength.referenceValue(this, entry, value);
/* 2204 */       entry.setValueReference(valueReference);
/* 2205 */       recordWrite(entry);
/* 2206 */       previous.notifyNewValue(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     V getOrCompute(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
/*      */       try {
/*      */         while (true) {
/* 2216 */           CustomConcurrentHashMap.ReferenceEntry<K, V> e = null;
/* 2217 */           if (this.count != 0) {
/* 2218 */             e = getEntry(key, hash);
/* 2219 */             if (e != null) {
/* 2220 */               V v = getLiveValue(e);
/* 2221 */               if (v != null) {
/* 2222 */                 recordRead(e);
/* 2223 */                 this.statsCounter.recordHit();
/* 2224 */                 return v;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2231 */           if (e == null || !e.getValueReference().isComputingReference()) {
/* 2232 */             boolean createNewEntry = true;
/* 2233 */             CustomConcurrentHashMap.ComputingValueReference<K, V> computingValueReference = null;
/* 2234 */             lock();
/*      */             try {
/* 2236 */               preWriteCleanup();
/*      */               
/* 2238 */               int newCount = this.count - 1;
/* 2239 */               AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2240 */               int index = hash & table.length() - 1;
/* 2241 */               CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */               
/* 2243 */               for (e = first; e != null; e = e.getNext()) {
/* 2244 */                 K entryKey = e.getKey();
/* 2245 */                 if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */                   
/* 2247 */                   CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2248 */                   if (valueReference.isComputingReference()) {
/* 2249 */                     createNewEntry = false; break;
/*      */                   } 
/* 2251 */                   V v = (V)e.getValueReference().get();
/* 2252 */                   if (v == null) {
/* 2253 */                     enqueueNotification(entryKey, hash, v, RemovalCause.COLLECTED);
/* 2254 */                   } else if (this.map.expires() && this.map.isExpired(e)) {
/*      */ 
/*      */                     
/* 2257 */                     enqueueNotification(entryKey, hash, v, RemovalCause.EXPIRED);
/*      */                   } else {
/* 2259 */                     recordLockedRead(e);
/* 2260 */                     this.statsCounter.recordHit();
/* 2261 */                     return v;
/*      */                   } 
/*      */ 
/*      */                   
/* 2265 */                   this.evictionQueue.remove(e);
/* 2266 */                   this.expirationQueue.remove(e);
/* 2267 */                   this.count = newCount;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */               
/* 2273 */               if (createNewEntry) {
/* 2274 */                 computingValueReference = new CustomConcurrentHashMap.ComputingValueReference<K, V>(loader);
/*      */                 
/* 2276 */                 if (e == null) {
/* 2277 */                   e = newEntry(key, hash, first);
/* 2278 */                   e.setValueReference(computingValueReference);
/* 2279 */                   table.set(index, e);
/*      */                 } else {
/* 2281 */                   e.setValueReference(computingValueReference);
/*      */                 } 
/*      */               } 
/*      */             } finally {
/* 2285 */               unlock();
/* 2286 */               postWriteCleanup();
/*      */             } 
/*      */             
/* 2289 */             if (createNewEntry)
/*      */             {
/* 2291 */               return compute(key, hash, e, computingValueReference);
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 2296 */           Preconditions.checkState(!Thread.holdsLock(e), "Recursive computation");
/*      */           
/* 2298 */           V value = (V)e.getValueReference().waitForValue();
/* 2299 */           if (value != null) {
/* 2300 */             recordRead(e);
/* 2301 */             this.statsCounter.recordConcurrentMiss();
/* 2302 */             return value;
/*      */           }
/*      */         
/*      */         } 
/*      */       } finally {
/*      */         
/* 2308 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     V compute(K key, int hash, CustomConcurrentHashMap.ReferenceEntry<K, V> e, CustomConcurrentHashMap.ComputingValueReference<K, V> computingValueReference) throws ExecutionException {
/* 2315 */       V value = null;
/* 2316 */       long start = System.nanoTime();
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2321 */         synchronized (e) {
/* 2322 */           value = computingValueReference.compute(key, hash);
/*      */         } 
/* 2324 */         long end = System.nanoTime();
/* 2325 */         this.statsCounter.recordLoadSuccess(end - start);
/*      */ 
/*      */         
/* 2328 */         V oldValue = put(key, hash, value, true);
/* 2329 */         if (oldValue != null)
/*      */         {
/* 2331 */           enqueueNotification(key, hash, value, RemovalCause.REPLACED);
/*      */         }
/* 2333 */         return value;
/*      */       } finally {
/* 2335 */         if (value == null) {
/* 2336 */           long end = System.nanoTime();
/* 2337 */           this.statsCounter.recordLoadException(end - start);
/* 2338 */           clearValue(key, hash, computingValueReference);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void tryDrainReferenceQueues() {
/* 2349 */       if (tryLock()) {
/*      */         try {
/* 2351 */           drainReferenceQueues();
/*      */         } finally {
/* 2353 */           unlock();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void drainReferenceQueues() {
/* 2364 */       if (this.map.usesKeyReferences()) {
/* 2365 */         drainKeyReferenceQueue();
/*      */       }
/* 2367 */       if (this.map.usesValueReferences()) {
/* 2368 */         drainValueReferenceQueue();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void drainKeyReferenceQueue() {
/* 2375 */       int i = 0; Reference<? extends K> ref;
/* 2376 */       while ((ref = this.keyReferenceQueue.poll()) != null) {
/*      */         
/* 2378 */         CustomConcurrentHashMap.ReferenceEntry<K, V> entry = (CustomConcurrentHashMap.ReferenceEntry)ref;
/* 2379 */         this.map.reclaimKey(entry);
/* 2380 */         if (++i == 16) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void drainValueReferenceQueue() {
/* 2389 */       int i = 0; Reference<? extends V> ref;
/* 2390 */       while ((ref = this.valueReferenceQueue.poll()) != null) {
/*      */         
/* 2392 */         CustomConcurrentHashMap.ValueReference<K, V> valueReference = (CustomConcurrentHashMap.ValueReference)ref;
/* 2393 */         this.map.reclaimValue(valueReference);
/* 2394 */         if (++i == 16) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clearReferenceQueues() {
/* 2404 */       if (this.map.usesKeyReferences()) {
/* 2405 */         clearKeyReferenceQueue();
/*      */       }
/* 2407 */       if (this.map.usesValueReferences()) {
/* 2408 */         clearValueReferenceQueue();
/*      */       }
/*      */     }
/*      */     
/*      */     void clearKeyReferenceQueue() {
/* 2413 */       while (this.keyReferenceQueue.poll() != null);
/*      */     }
/*      */     
/*      */     void clearValueReferenceQueue() {
/* 2417 */       while (this.valueReferenceQueue.poll() != null);
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
/*      */     void recordRead(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 2430 */       if (this.map.expiresAfterAccess()) {
/* 2431 */         recordExpirationTime(entry, this.map.expireAfterAccessNanos);
/*      */       }
/* 2433 */       this.recencyQueue.add(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void recordLockedRead(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 2445 */       this.evictionQueue.add(entry);
/* 2446 */       if (this.map.expiresAfterAccess()) {
/* 2447 */         recordExpirationTime(entry, this.map.expireAfterAccessNanos);
/* 2448 */         this.expirationQueue.add(entry);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void recordWrite(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 2459 */       drainRecencyQueue();
/* 2460 */       this.evictionQueue.add(entry);
/* 2461 */       if (this.map.expires()) {
/*      */ 
/*      */         
/* 2464 */         long expiration = this.map.expiresAfterAccess() ? this.map.expireAfterAccessNanos : this.map.expireAfterWriteNanos;
/*      */ 
/*      */         
/* 2467 */         recordExpirationTime(entry, expiration);
/* 2468 */         this.expirationQueue.add(entry);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void drainRecencyQueue() {
/*      */       CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2481 */       while ((e = this.recencyQueue.poll()) != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2486 */         if (this.evictionQueue.contains(e)) {
/* 2487 */           this.evictionQueue.add(e);
/*      */         }
/* 2489 */         if (this.map.expiresAfterAccess() && this.expirationQueue.contains(e)) {
/* 2490 */           this.expirationQueue.add(e);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void recordExpirationTime(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, long expirationNanos) {
/* 2499 */       entry.setExpirationTime(this.map.ticker.read() + expirationNanos);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void tryExpireEntries() {
/* 2506 */       if (tryLock()) {
/*      */         try {
/* 2508 */           expireEntries();
/*      */         } finally {
/* 2510 */           unlock();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void expireEntries() {
/* 2518 */       drainRecencyQueue();
/*      */       
/* 2520 */       if (this.expirationQueue.isEmpty()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 2525 */       long now = this.map.ticker.read();
/*      */       CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2527 */       while ((e = this.expirationQueue.peek()) != null && this.map.isExpired(e, now)) {
/* 2528 */         if (!removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
/* 2529 */           throw new AssertionError();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void enqueueNotification(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, RemovalCause cause) {
/* 2537 */       enqueueNotification(entry.getKey(), entry.getHash(), (V)entry.getValueReference().get(), cause);
/*      */     }
/*      */     
/*      */     void enqueueNotification(@Nullable K key, int hash, @Nullable V value, RemovalCause cause) {
/* 2541 */       if (cause.wasEvicted()) {
/* 2542 */         this.statsCounter.recordEviction();
/*      */       }
/* 2544 */       if (this.map.removalNotificationQueue != CustomConcurrentHashMap.DISCARDING_QUEUE) {
/* 2545 */         RemovalNotification<K, V> notification = new RemovalNotification<K, V>(key, value, cause);
/* 2546 */         this.map.removalNotificationQueue.offer(notification);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     boolean evictEntries() {
/* 2558 */       if (this.map.evictsBySize() && this.count >= this.maxSegmentSize) {
/* 2559 */         drainRecencyQueue();
/*      */         
/* 2561 */         CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.evictionQueue.remove();
/* 2562 */         if (!removeEntry(e, e.getHash(), RemovalCause.SIZE)) {
/* 2563 */           throw new AssertionError();
/*      */         }
/* 2565 */         return true;
/*      */       } 
/* 2567 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> getFirst(int hash) {
/* 2575 */       AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2576 */       return table.get(hash & table.length() - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry(Object key, int hash) {
/* 2582 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = getFirst(hash); e != null; e = e.getNext()) {
/* 2583 */         if (e.getHash() == hash) {
/*      */ 
/*      */ 
/*      */           
/* 2587 */           K entryKey = e.getKey();
/* 2588 */           if (entryKey == null) {
/* 2589 */             tryDrainReferenceQueues();
/*      */ 
/*      */           
/*      */           }
/* 2593 */           else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
/* 2594 */             return e;
/*      */           } 
/*      */         } 
/*      */       } 
/* 2598 */       return null;
/*      */     }
/*      */     
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> getLiveEntry(Object key, int hash) {
/* 2602 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = getEntry(key, hash);
/* 2603 */       if (e == null)
/* 2604 */         return null; 
/* 2605 */       if (this.map.expires() && this.map.isExpired(e)) {
/* 2606 */         tryExpireEntries();
/* 2607 */         return null;
/*      */       } 
/* 2609 */       return e;
/*      */     }
/*      */     
/*      */     V get(Object key, int hash) {
/*      */       try {
/* 2614 */         if (this.count != 0) {
/* 2615 */           CustomConcurrentHashMap.ReferenceEntry<K, V> e = getLiveEntry(key, hash);
/* 2616 */           if (e == null) {
/* 2617 */             return null;
/*      */           }
/*      */           
/* 2620 */           V value = (V)e.getValueReference().get();
/* 2621 */           if (value != null) {
/* 2622 */             recordRead(e);
/*      */           } else {
/* 2624 */             tryDrainReferenceQueues();
/*      */           } 
/* 2626 */           return value;
/*      */         } 
/* 2628 */         return null;
/*      */       } finally {
/* 2630 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean containsKey(Object key, int hash) {
/*      */       try {
/* 2636 */         if (this.count != 0) {
/* 2637 */           CustomConcurrentHashMap.ReferenceEntry<K, V> e = getLiveEntry(key, hash);
/* 2638 */           if (e == null) {
/* 2639 */             return false;
/*      */           }
/* 2641 */           return (e.getValueReference().get() != null);
/*      */         } 
/*      */         
/* 2644 */         return false;
/*      */       } finally {
/* 2646 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @VisibleForTesting
/*      */     boolean containsValue(Object value) {
/*      */       try {
/* 2657 */         if (this.count != 0) {
/* 2658 */           AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2659 */           int length = table.length();
/* 2660 */           for (int i = 0; i < length; i++) {
/* 2661 */             for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
/* 2662 */               V entryValue = getLiveValue(e);
/* 2663 */               if (entryValue != null)
/*      */               {
/*      */                 
/* 2666 */                 if (this.map.valueEquivalence.equivalent(value, entryValue)) {
/* 2667 */                   return true;
/*      */                 }
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 2673 */         return false;
/*      */       } finally {
/* 2675 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     V put(K key, int hash, V value, boolean onlyIfAbsent) {
/* 2680 */       lock();
/*      */       try {
/* 2682 */         preWriteCleanup();
/*      */         
/* 2684 */         int newCount = this.count + 1;
/* 2685 */         if (newCount > this.threshold) {
/* 2686 */           expand();
/* 2687 */           newCount = this.count + 1;
/*      */         } 
/*      */         
/* 2690 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2691 */         int index = hash & table.length() - 1;
/* 2692 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */ 
/*      */         
/* 2695 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2696 */           K entryKey = e.getKey();
/* 2697 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */ 
/*      */ 
/*      */             
/* 2701 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2702 */             V entryValue = valueReference.get();
/*      */             
/* 2704 */             if (entryValue == null) {
/* 2705 */               this.modCount++;
/* 2706 */               setValue(e, value);
/* 2707 */               if (!valueReference.isComputingReference()) {
/* 2708 */                 enqueueNotification(key, hash, entryValue, RemovalCause.COLLECTED);
/* 2709 */                 newCount = this.count;
/* 2710 */               } else if (evictEntries()) {
/* 2711 */                 newCount = this.count + 1;
/*      */               } 
/* 2713 */               this.count = newCount;
/* 2714 */               return null;
/* 2715 */             }  if (onlyIfAbsent) {
/*      */ 
/*      */ 
/*      */               
/* 2719 */               recordLockedRead(e);
/* 2720 */               return entryValue;
/*      */             } 
/*      */             
/* 2723 */             this.modCount++;
/* 2724 */             enqueueNotification(key, hash, entryValue, RemovalCause.REPLACED);
/* 2725 */             setValue(e, value);
/* 2726 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2732 */         this.modCount++;
/* 2733 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = newEntry(key, hash, first);
/* 2734 */         setValue(newEntry, value);
/* 2735 */         table.set(index, newEntry);
/* 2736 */         if (evictEntries()) {
/* 2737 */           newCount = this.count + 1;
/*      */         }
/* 2739 */         this.count = newCount;
/* 2740 */         return null;
/*      */       } finally {
/* 2742 */         unlock();
/* 2743 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void expand() {
/* 2752 */       AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> oldTable = this.table;
/* 2753 */       int oldCapacity = oldTable.length();
/* 2754 */       if (oldCapacity >= 1073741824) {
/*      */         return;
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
/* 2768 */       int newCount = this.count;
/* 2769 */       AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> newTable = newEntryArray(oldCapacity << 1);
/* 2770 */       this.threshold = newTable.length() * 3 / 4;
/* 2771 */       int newMask = newTable.length() - 1;
/* 2772 */       for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
/*      */ 
/*      */         
/* 2775 */         CustomConcurrentHashMap.ReferenceEntry<K, V> head = oldTable.get(oldIndex);
/*      */         
/* 2777 */         if (head != null) {
/* 2778 */           CustomConcurrentHashMap.ReferenceEntry<K, V> next = head.getNext();
/* 2779 */           int headIndex = head.getHash() & newMask;
/*      */ 
/*      */           
/* 2782 */           if (next == null) {
/* 2783 */             newTable.set(headIndex, head);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2788 */             CustomConcurrentHashMap.ReferenceEntry<K, V> tail = head;
/* 2789 */             int tailIndex = headIndex; CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2790 */             for (e = next; e != null; e = e.getNext()) {
/* 2791 */               int newIndex = e.getHash() & newMask;
/* 2792 */               if (newIndex != tailIndex) {
/*      */                 
/* 2794 */                 tailIndex = newIndex;
/* 2795 */                 tail = e;
/*      */               } 
/*      */             } 
/* 2798 */             newTable.set(tailIndex, tail);
/*      */ 
/*      */             
/* 2801 */             for (e = head; e != tail; e = e.getNext()) {
/* 2802 */               if (isCollected(e)) {
/* 2803 */                 removeCollectedEntry(e);
/* 2804 */                 newCount--;
/*      */               } else {
/* 2806 */                 int newIndex = e.getHash() & newMask;
/* 2807 */                 CustomConcurrentHashMap.ReferenceEntry<K, V> newNext = newTable.get(newIndex);
/* 2808 */                 CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = copyEntry(e, newNext);
/* 2809 */                 newTable.set(newIndex, newFirst);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2815 */       this.table = newTable;
/* 2816 */       this.count = newCount;
/*      */     }
/*      */     
/*      */     boolean replace(K key, int hash, V oldValue, V newValue) {
/* 2820 */       lock();
/*      */       try {
/* 2822 */         preWriteCleanup();
/*      */         
/* 2824 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2825 */         int index = hash & table.length() - 1;
/* 2826 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 2828 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2829 */           K entryKey = e.getKey();
/* 2830 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */ 
/*      */ 
/*      */             
/* 2834 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2835 */             V entryValue = valueReference.get();
/* 2836 */             if (entryValue == null) {
/* 2837 */               if (isCollected(valueReference)) {
/* 2838 */                 int newCount = this.count - 1;
/* 2839 */                 this.modCount++;
/* 2840 */                 enqueueNotification(entryKey, hash, entryValue, RemovalCause.COLLECTED);
/* 2841 */                 CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2842 */                 newCount = this.count - 1;
/* 2843 */                 table.set(index, newFirst);
/* 2844 */                 this.count = newCount;
/*      */               } 
/* 2846 */               return false;
/*      */             } 
/*      */             
/* 2849 */             if (this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
/* 2850 */               this.modCount++;
/* 2851 */               enqueueNotification(key, hash, entryValue, RemovalCause.REPLACED);
/* 2852 */               setValue(e, newValue);
/* 2853 */               return true;
/*      */             } 
/*      */ 
/*      */             
/* 2857 */             recordLockedRead(e);
/* 2858 */             return false;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2863 */         return false;
/*      */       } finally {
/* 2865 */         unlock();
/* 2866 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     V replace(K key, int hash, V newValue) {
/* 2871 */       lock();
/*      */       
/* 2873 */       try { preWriteCleanup();
/*      */         
/* 2875 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2876 */         int index = hash & table.length() - 1;
/* 2877 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2879 */         for (e = first; e != null; e = e.getNext()) {
/* 2880 */           K entryKey = e.getKey();
/* 2881 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */ 
/*      */ 
/*      */             
/* 2885 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2886 */             V entryValue = valueReference.get();
/* 2887 */             if (entryValue == null) {
/* 2888 */               if (isCollected(valueReference)) {
/* 2889 */                 int newCount = this.count - 1;
/* 2890 */                 this.modCount++;
/* 2891 */                 enqueueNotification(entryKey, hash, entryValue, RemovalCause.COLLECTED);
/* 2892 */                 CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2893 */                 newCount = this.count - 1;
/* 2894 */                 table.set(index, newFirst);
/* 2895 */                 this.count = newCount;
/*      */               } 
/* 2897 */               return null;
/*      */             } 
/*      */             
/* 2900 */             this.modCount++;
/* 2901 */             enqueueNotification(key, hash, entryValue, RemovalCause.REPLACED);
/* 2902 */             setValue(e, newValue);
/* 2903 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */         
/* 2907 */         e = null;
/*      */ 
/*      */         
/* 2910 */         return (V)e; } finally { unlock(); postWriteCleanup(); }
/*      */     
/*      */     }
/*      */     
/*      */     V remove(Object key, int hash) {
/* 2915 */       lock();
/*      */       
/* 2917 */       try { preWriteCleanup();
/*      */         
/* 2919 */         int newCount = this.count - 1;
/* 2920 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2921 */         int index = hash & table.length() - 1;
/* 2922 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2924 */         for (e = first; e != null; e = e.getNext()) {
/* 2925 */           K entryKey = e.getKey();
/* 2926 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */             RemovalCause cause;
/* 2928 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2929 */             V entryValue = valueReference.get();
/*      */ 
/*      */             
/* 2932 */             if (entryValue != null) {
/* 2933 */               cause = RemovalCause.EXPLICIT;
/* 2934 */             } else if (isCollected(valueReference)) {
/* 2935 */               cause = RemovalCause.COLLECTED;
/*      */             } else {
/* 2937 */               return null;
/*      */             } 
/*      */             
/* 2940 */             this.modCount++;
/* 2941 */             enqueueNotification(entryKey, hash, entryValue, cause);
/* 2942 */             CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2943 */             newCount = this.count - 1;
/* 2944 */             table.set(index, newFirst);
/* 2945 */             this.count = newCount;
/* 2946 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */         
/* 2950 */         e = null;
/*      */ 
/*      */         
/* 2953 */         return (V)e; } finally { unlock(); postWriteCleanup(); }
/*      */     
/*      */     }
/*      */     
/*      */     boolean remove(Object key, int hash, Object value) {
/* 2958 */       lock();
/*      */       try {
/* 2960 */         preWriteCleanup();
/*      */         
/* 2962 */         int newCount = this.count - 1;
/* 2963 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2964 */         int index = hash & table.length() - 1;
/* 2965 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 2967 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2968 */           K entryKey = e.getKey();
/* 2969 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */             RemovalCause cause;
/* 2971 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2972 */             V entryValue = valueReference.get();
/*      */ 
/*      */             
/* 2975 */             if (this.map.valueEquivalence.equivalent(value, entryValue)) {
/* 2976 */               cause = RemovalCause.EXPLICIT;
/* 2977 */             } else if (isCollected(valueReference)) {
/* 2978 */               cause = RemovalCause.COLLECTED;
/*      */             } else {
/* 2980 */               return false;
/*      */             } 
/*      */             
/* 2983 */             this.modCount++;
/* 2984 */             enqueueNotification(entryKey, hash, entryValue, cause);
/* 2985 */             CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2986 */             newCount = this.count - 1;
/* 2987 */             table.set(index, newFirst);
/* 2988 */             this.count = newCount;
/* 2989 */             return (cause == RemovalCause.EXPLICIT);
/*      */           } 
/*      */         } 
/*      */         
/* 2993 */         return false;
/*      */       } finally {
/* 2995 */         unlock();
/* 2996 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     void clear() {
/* 3001 */       if (this.count != 0) {
/* 3002 */         lock();
/*      */         try {
/* 3004 */           AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 3005 */           if (this.map.removalNotificationQueue != CustomConcurrentHashMap.DISCARDING_QUEUE) {
/* 3006 */             for (int j = 0; j < table.length(); j++) {
/* 3007 */               for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = table.get(j); e != null; e = e.getNext()) {
/*      */                 
/* 3009 */                 if (!e.getValueReference().isComputingReference()) {
/* 3010 */                   enqueueNotification(e, RemovalCause.EXPLICIT);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           }
/* 3015 */           for (int i = 0; i < table.length(); i++) {
/* 3016 */             table.set(i, null);
/*      */           }
/* 3018 */           clearReferenceQueues();
/* 3019 */           this.evictionQueue.clear();
/* 3020 */           this.expirationQueue.clear();
/* 3021 */           this.readCount.set(0);
/*      */           
/* 3023 */           this.modCount++;
/* 3024 */           this.count = 0;
/*      */         } finally {
/* 3026 */           unlock();
/* 3027 */           postWriteCleanup();
/*      */         } 
/*      */       } 
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
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> removeFromChain(CustomConcurrentHashMap.ReferenceEntry<K, V> first, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3046 */       this.evictionQueue.remove(entry);
/* 3047 */       this.expirationQueue.remove(entry);
/*      */       
/* 3049 */       int newCount = this.count;
/* 3050 */       CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = entry.getNext();
/* 3051 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
/* 3052 */         if (isCollected(e)) {
/* 3053 */           removeCollectedEntry(e);
/* 3054 */           newCount--;
/*      */         } else {
/* 3056 */           newFirst = copyEntry(e, newFirst);
/*      */         } 
/*      */       } 
/* 3059 */       this.count = newCount;
/* 3060 */       return newFirst;
/*      */     }
/*      */     
/*      */     void removeCollectedEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3064 */       enqueueNotification(entry, RemovalCause.COLLECTED);
/* 3065 */       this.evictionQueue.remove(entry);
/* 3066 */       this.expirationQueue.remove(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean reclaimKey(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, int hash) {
/* 3073 */       lock();
/*      */       try {
/* 3075 */         int newCount = this.count - 1;
/* 3076 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 3077 */         int index = hash & table.length() - 1;
/* 3078 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3080 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3081 */           if (e == entry) {
/* 3082 */             this.modCount++;
/* 3083 */             enqueueNotification(e.getKey(), hash, (V)e.getValueReference().get(), RemovalCause.COLLECTED);
/*      */             
/* 3085 */             CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 3086 */             newCount = this.count - 1;
/* 3087 */             table.set(index, newFirst);
/* 3088 */             this.count = newCount;
/* 3089 */             return true;
/*      */           } 
/*      */         } 
/*      */         
/* 3093 */         return false;
/*      */       } finally {
/* 3095 */         unlock();
/* 3096 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean reclaimValue(K key, int hash, CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 3104 */       lock();
/*      */       try {
/* 3106 */         int newCount = this.count - 1;
/* 3107 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 3108 */         int index = hash & table.length() - 1;
/* 3109 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3111 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3112 */           K entryKey = e.getKey();
/* 3113 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */             
/* 3115 */             CustomConcurrentHashMap.ValueReference<K, V> v = e.getValueReference();
/* 3116 */             if (v == valueReference) {
/* 3117 */               this.modCount++;
/* 3118 */               enqueueNotification(key, hash, valueReference.get(), RemovalCause.COLLECTED);
/* 3119 */               CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 3120 */               newCount = this.count - 1;
/* 3121 */               table.set(index, newFirst);
/* 3122 */               this.count = newCount;
/* 3123 */               return true;
/*      */             } 
/* 3125 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/* 3129 */         return false;
/*      */       } finally {
/* 3131 */         unlock();
/* 3132 */         if (!isHeldByCurrentThread()) {
/* 3133 */           postWriteCleanup();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean clearValue(K key, int hash, CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 3142 */       lock();
/*      */       try {
/* 3144 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 3145 */         int index = hash & table.length() - 1;
/* 3146 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3148 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3149 */           K entryKey = e.getKey();
/* 3150 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */             
/* 3152 */             CustomConcurrentHashMap.ValueReference<K, V> v = e.getValueReference();
/* 3153 */             if (v == valueReference) {
/* 3154 */               CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 3155 */               table.set(index, newFirst);
/* 3156 */               return true;
/*      */             } 
/* 3158 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/* 3162 */         return false;
/*      */       } finally {
/* 3164 */         unlock();
/* 3165 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     boolean removeEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, int hash, RemovalCause cause) {
/* 3171 */       int newCount = this.count - 1;
/* 3172 */       AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 3173 */       int index = hash & table.length() - 1;
/* 3174 */       CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */       
/* 3176 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3177 */         if (e == entry) {
/* 3178 */           this.modCount++;
/* 3179 */           enqueueNotification(e.getKey(), hash, (V)e.getValueReference().get(), cause);
/* 3180 */           CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 3181 */           newCount = this.count - 1;
/* 3182 */           table.set(index, newFirst);
/* 3183 */           this.count = newCount;
/* 3184 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/* 3188 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isCollected(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3196 */       if (entry.getKey() == null) {
/* 3197 */         return true;
/*      */       }
/* 3199 */       return isCollected(entry.getValueReference());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isCollected(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 3207 */       if (valueReference.isComputingReference()) {
/* 3208 */         return false;
/*      */       }
/* 3210 */       return (valueReference.get() == null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     V getLiveValue(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3218 */       if (entry.getKey() == null) {
/* 3219 */         tryDrainReferenceQueues();
/* 3220 */         return null;
/*      */       } 
/* 3222 */       V value = (V)entry.getValueReference().get();
/* 3223 */       if (value == null) {
/* 3224 */         tryDrainReferenceQueues();
/* 3225 */         return null;
/*      */       } 
/*      */       
/* 3228 */       if (this.map.expires() && this.map.isExpired(entry)) {
/* 3229 */         tryExpireEntries();
/* 3230 */         return null;
/*      */       } 
/* 3232 */       return value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void postReadCleanup() {
/* 3240 */       if ((this.readCount.incrementAndGet() & 0x3F) == 0) {
/* 3241 */         cleanUp();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void preWriteCleanup() {
/* 3253 */       runLockedCleanup();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void postWriteCleanup() {
/* 3260 */       runUnlockedCleanup();
/*      */     }
/*      */     
/*      */     void cleanUp() {
/* 3264 */       runLockedCleanup();
/* 3265 */       runUnlockedCleanup();
/*      */     }
/*      */     
/*      */     void runLockedCleanup() {
/* 3269 */       if (tryLock()) {
/*      */         try {
/* 3271 */           drainReferenceQueues();
/* 3272 */           expireEntries();
/* 3273 */           this.readCount.set(0);
/*      */         } finally {
/* 3275 */           unlock();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void runUnlockedCleanup() {
/* 3282 */       if (!isHeldByCurrentThread()) {
/* 3283 */         this.map.processPendingNotifications();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ComputedUncheckedException<V>
/*      */     implements ComputedValue<V>
/*      */   {
/*      */     final RuntimeException e;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ComputedUncheckedException(RuntimeException e) {
/* 3300 */       this.e = e;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 3305 */       throw new UncheckedExecutionException(this.e);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class ComputedException<V>
/*      */     implements ComputedValue<V>
/*      */   {
/*      */     final Exception e;
/*      */     
/*      */     ComputedException(Exception e) {
/* 3316 */       this.e = e;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() throws ExecutionException {
/* 3321 */       throw new ExecutionException(this.e);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class ComputedError<V>
/*      */     implements ComputedValue<V>
/*      */   {
/*      */     final Error e;
/*      */     
/*      */     ComputedError(Error e) {
/* 3332 */       this.e = e;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 3337 */       throw new ExecutionError(this.e);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class ComputedReference<V>
/*      */     implements ComputedValue<V>
/*      */   {
/*      */     final V value;
/*      */     
/*      */     ComputedReference(V value) {
/* 3348 */       this.value = (V)Preconditions.checkNotNull(value);
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 3353 */       return this.value;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class ComputedNull<K, V>
/*      */     implements ComputedValue<V>
/*      */   {
/*      */     final String msg;
/*      */     
/*      */     public ComputedNull(CacheLoader<? super K, V> loader, K key) {
/* 3364 */       this.msg = loader + " returned null for key " + key + ".";
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 3369 */       throw new NullPointerException(this.msg);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ComputingValueReference<K, V> implements ValueReference<K, V> {
/*      */     final CacheLoader<? super K, V> loader;
/*      */     @GuardedBy("ComputingValueReference.this")
/* 3376 */     volatile CustomConcurrentHashMap.ComputedValue<V> computedValue = null;
/*      */ 
/*      */     
/*      */     public ComputingValueReference(CacheLoader<? super K, V> loader) {
/* 3380 */       this.loader = loader;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isComputingReference() {
/* 3385 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V waitForValue() throws ExecutionException {
/* 3393 */       if (this.computedValue == null) {
/* 3394 */         boolean interrupted = false;
/*      */         try {
/* 3396 */           synchronized (this) {
/* 3397 */             while (this.computedValue == null) {
/*      */               try {
/* 3399 */                 wait();
/* 3400 */               } catch (InterruptedException ie) {
/* 3401 */                 interrupted = true;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } finally {
/* 3406 */           if (interrupted) {
/* 3407 */             Thread.currentThread().interrupt();
/*      */           }
/*      */         } 
/*      */       } 
/* 3411 */       return this.computedValue.get();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void notifyNewValue(V newValue) {
/* 3418 */       setComputedValue(new CustomConcurrentHashMap.ComputedReference<V>(newValue));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     V compute(K key, int hash) throws ExecutionException {
/*      */       CustomConcurrentHashMap.ComputedValue<V> computedValue;
/*      */       try {
/* 3426 */         V value = this.loader.load(key);
/* 3427 */         if (value == null) {
/* 3428 */           computedValue = new CustomConcurrentHashMap.ComputedNull<K, V>(this.loader, key);
/*      */         } else {
/* 3430 */           computedValue = new CustomConcurrentHashMap.ComputedReference<V>(value);
/*      */         } 
/* 3432 */       } catch (RuntimeException e) {
/* 3433 */         computedValue = new CustomConcurrentHashMap.ComputedUncheckedException(e);
/* 3434 */       } catch (Exception e) {
/* 3435 */         computedValue = new CustomConcurrentHashMap.ComputedException(e);
/* 3436 */       } catch (Error e) {
/* 3437 */         computedValue = new CustomConcurrentHashMap.ComputedError(e);
/*      */       } 
/*      */       
/* 3440 */       setComputedValue(computedValue);
/* 3441 */       return computedValue.get();
/*      */     }
/*      */     
/*      */     void setComputedValue(CustomConcurrentHashMap.ComputedValue<V> newValue) {
/* 3445 */       synchronized (this) {
/* 3446 */         if (this.computedValue == null) {
/* 3447 */           this.computedValue = newValue;
/* 3448 */           notifyAll();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 3455 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 3460 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3465 */       return this;
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
/*      */   static final class EvictionQueue<K, V>
/*      */     extends AbstractQueue<ReferenceEntry<K, V>>
/*      */   {
/* 3483 */     final CustomConcurrentHashMap.ReferenceEntry<K, V> head = new CustomConcurrentHashMap.AbstractReferenceEntry<K, V>()
/*      */       {
/* 3485 */         CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = this;
/*      */ 
/*      */         
/*      */         public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/* 3489 */           return this.nextEvictable;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/* 3494 */           this.nextEvictable = next;
/*      */         }
/*      */         
/* 3497 */         CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = this;
/*      */ 
/*      */         
/*      */         public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/* 3501 */           return this.previousEvictable;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 3506 */           this.previousEvictable = previous;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean offer(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3515 */       CustomConcurrentHashMap.connectEvictables(entry.getPreviousEvictable(), entry.getNextEvictable());
/*      */ 
/*      */       
/* 3518 */       CustomConcurrentHashMap.connectEvictables(this.head.getPreviousEvictable(), entry);
/* 3519 */       CustomConcurrentHashMap.connectEvictables(entry, this.head);
/*      */       
/* 3521 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> peek() {
/* 3526 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = this.head.getNextEvictable();
/* 3527 */       return (next == this.head) ? null : next;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> poll() {
/* 3532 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = this.head.getNextEvictable();
/* 3533 */       if (next == this.head) {
/* 3534 */         return null;
/*      */       }
/*      */       
/* 3537 */       remove(next);
/* 3538 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 3544 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = (CustomConcurrentHashMap.ReferenceEntry<K, V>)o;
/* 3545 */       CustomConcurrentHashMap.ReferenceEntry<K, V> previous = e.getPreviousEvictable();
/* 3546 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = e.getNextEvictable();
/* 3547 */       CustomConcurrentHashMap.connectEvictables(previous, next);
/* 3548 */       CustomConcurrentHashMap.nullifyEvictable(e);
/*      */       
/* 3550 */       return (next != CustomConcurrentHashMap.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3556 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = (CustomConcurrentHashMap.ReferenceEntry<K, V>)o;
/* 3557 */       return (e.getNextEvictable() != CustomConcurrentHashMap.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3562 */       return (this.head.getNextEvictable() == this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3567 */       int size = 0;
/* 3568 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.head.getNextEvictable(); e != this.head; e = e.getNextEvictable()) {
/* 3569 */         size++;
/*      */       }
/* 3571 */       return size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3576 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.head.getNextEvictable();
/* 3577 */       while (e != this.head) {
/* 3578 */         CustomConcurrentHashMap.ReferenceEntry<K, V> next = e.getNextEvictable();
/* 3579 */         CustomConcurrentHashMap.nullifyEvictable(e);
/* 3580 */         e = next;
/*      */       } 
/*      */       
/* 3583 */       this.head.setNextEvictable(this.head);
/* 3584 */       this.head.setPreviousEvictable(this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<CustomConcurrentHashMap.ReferenceEntry<K, V>> iterator() {
/* 3589 */       return (Iterator<CustomConcurrentHashMap.ReferenceEntry<K, V>>)new AbstractLinkedIterator<CustomConcurrentHashMap.ReferenceEntry<K, V>>(peek())
/*      */         {
/*      */           protected CustomConcurrentHashMap.ReferenceEntry<K, V> computeNext(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 3592 */             CustomConcurrentHashMap.ReferenceEntry<K, V> next = previous.getNextEvictable();
/* 3593 */             return (next == CustomConcurrentHashMap.EvictionQueue.this.head) ? null : next;
/*      */           }
/*      */         };
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
/*      */   static final class ExpirationQueue<K, V>
/*      */     extends AbstractQueue<ReferenceEntry<K, V>>
/*      */   {
/* 3611 */     final CustomConcurrentHashMap.ReferenceEntry<K, V> head = new CustomConcurrentHashMap.AbstractReferenceEntry<K, V>()
/*      */       {
/*      */         public long getExpirationTime()
/*      */         {
/* 3615 */           return Long.MAX_VALUE;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setExpirationTime(long time) {}
/*      */         
/* 3621 */         CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = this;
/*      */ 
/*      */         
/*      */         public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() {
/* 3625 */           return this.nextExpirable;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/* 3630 */           this.nextExpirable = next;
/*      */         }
/*      */         
/* 3633 */         CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = this;
/*      */ 
/*      */         
/*      */         public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/* 3637 */           return this.previousExpirable;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 3642 */           this.previousExpirable = previous;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean offer(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3651 */       CustomConcurrentHashMap.connectExpirables(entry.getPreviousExpirable(), entry.getNextExpirable());
/*      */ 
/*      */       
/* 3654 */       CustomConcurrentHashMap.connectExpirables(this.head.getPreviousExpirable(), entry);
/* 3655 */       CustomConcurrentHashMap.connectExpirables(entry, this.head);
/*      */       
/* 3657 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> peek() {
/* 3662 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = this.head.getNextExpirable();
/* 3663 */       return (next == this.head) ? null : next;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> poll() {
/* 3668 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = this.head.getNextExpirable();
/* 3669 */       if (next == this.head) {
/* 3670 */         return null;
/*      */       }
/*      */       
/* 3673 */       remove(next);
/* 3674 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 3680 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = (CustomConcurrentHashMap.ReferenceEntry<K, V>)o;
/* 3681 */       CustomConcurrentHashMap.ReferenceEntry<K, V> previous = e.getPreviousExpirable();
/* 3682 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = e.getNextExpirable();
/* 3683 */       CustomConcurrentHashMap.connectExpirables(previous, next);
/* 3684 */       CustomConcurrentHashMap.nullifyExpirable(e);
/*      */       
/* 3686 */       return (next != CustomConcurrentHashMap.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3692 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = (CustomConcurrentHashMap.ReferenceEntry<K, V>)o;
/* 3693 */       return (e.getNextExpirable() != CustomConcurrentHashMap.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3698 */       return (this.head.getNextExpirable() == this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3703 */       int size = 0;
/* 3704 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.head.getNextExpirable(); e != this.head; e = e.getNextExpirable()) {
/* 3705 */         size++;
/*      */       }
/* 3707 */       return size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3712 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.head.getNextExpirable();
/* 3713 */       while (e != this.head) {
/* 3714 */         CustomConcurrentHashMap.ReferenceEntry<K, V> next = e.getNextExpirable();
/* 3715 */         CustomConcurrentHashMap.nullifyExpirable(e);
/* 3716 */         e = next;
/*      */       } 
/*      */       
/* 3719 */       this.head.setNextExpirable(this.head);
/* 3720 */       this.head.setPreviousExpirable(this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<CustomConcurrentHashMap.ReferenceEntry<K, V>> iterator() {
/* 3725 */       return (Iterator<CustomConcurrentHashMap.ReferenceEntry<K, V>>)new AbstractLinkedIterator<CustomConcurrentHashMap.ReferenceEntry<K, V>>(peek())
/*      */         {
/*      */           protected CustomConcurrentHashMap.ReferenceEntry<K, V> computeNext(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 3728 */             CustomConcurrentHashMap.ReferenceEntry<K, V> next = previous.getNextExpirable();
/* 3729 */             return (next == CustomConcurrentHashMap.ExpirationQueue.this.head) ? null : next;
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanUp() {
/* 3738 */     for (Segment<?, ?> segment : this.segments) {
/* 3739 */       segment.cleanUp();
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
/*      */   public boolean isEmpty() {
/* 3754 */     long sum = 0L;
/* 3755 */     Segment<K, V>[] segments = this.segments; int i;
/* 3756 */     for (i = 0; i < segments.length; i++) {
/* 3757 */       if ((segments[i]).count != 0) {
/* 3758 */         return false;
/*      */       }
/* 3760 */       sum += (segments[i]).modCount;
/*      */     } 
/*      */     
/* 3763 */     if (sum != 0L) {
/* 3764 */       for (i = 0; i < segments.length; i++) {
/* 3765 */         if ((segments[i]).count != 0) {
/* 3766 */           return false;
/*      */         }
/* 3768 */         sum -= (segments[i]).modCount;
/*      */       } 
/* 3770 */       if (sum != 0L) {
/* 3771 */         return false;
/*      */       }
/*      */     } 
/* 3774 */     return true;
/*      */   }
/*      */   
/*      */   long longSize() {
/* 3778 */     Segment<K, V>[] segments = this.segments;
/* 3779 */     long sum = 0L;
/* 3780 */     for (int i = 0; i < segments.length; i++) {
/* 3781 */       sum += (segments[i]).count;
/*      */     }
/* 3783 */     return sum;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/* 3788 */     return Ints.saturatedCast(longSize());
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(@Nullable Object key) {
/* 3793 */     if (key == null) {
/* 3794 */       return null;
/*      */     }
/* 3796 */     int hash = hash(key);
/* 3797 */     return segmentFor(hash).get(key, hash);
/*      */   }
/*      */   
/*      */   V getOrCompute(K key) throws ExecutionException {
/* 3801 */     int hash = hash(Preconditions.checkNotNull(key));
/* 3802 */     return segmentFor(hash).getOrCompute(key, hash, this.loader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ReferenceEntry<K, V> getEntry(@Nullable Object key) {
/* 3811 */     if (key == null) {
/* 3812 */       return null;
/*      */     }
/* 3814 */     int hash = hash(key);
/* 3815 */     return segmentFor(hash).getEntry(key, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ReferenceEntry<K, V> getLiveEntry(@Nullable Object key) {
/* 3823 */     if (key == null) {
/* 3824 */       return null;
/*      */     }
/* 3826 */     int hash = hash(key);
/* 3827 */     return segmentFor(hash).getLiveEntry(key, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(@Nullable Object key) {
/* 3833 */     if (key == null) {
/* 3834 */       return false;
/*      */     }
/* 3836 */     int hash = hash(key);
/* 3837 */     return segmentFor(hash).containsKey(key, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsValue(@Nullable Object value) {
/* 3843 */     if (value == null) {
/* 3844 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3852 */     Segment<K, V>[] segments = this.segments;
/* 3853 */     long last = -1L;
/* 3854 */     for (int i = 0; i < 3; i++) {
/* 3855 */       long sum = 0L;
/* 3856 */       for (Segment<K, V> segment : segments) {
/*      */ 
/*      */         
/* 3859 */         int c = segment.count;
/*      */         
/* 3861 */         AtomicReferenceArray<ReferenceEntry<K, V>> table = segment.table;
/* 3862 */         for (int j = 0; j < table.length(); j++) {
/* 3863 */           for (ReferenceEntry<K, V> e = table.get(j); e != null; e = e.getNext()) {
/* 3864 */             V v = segment.getLiveValue(e);
/* 3865 */             if (v != null && this.valueEquivalence.equivalent(value, v)) {
/* 3866 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/* 3870 */         sum += segment.modCount;
/*      */       } 
/* 3872 */       if (sum == last) {
/*      */         break;
/*      */       }
/* 3875 */       last = sum;
/*      */     } 
/* 3877 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K key, V value) {
/* 3882 */     Preconditions.checkNotNull(key);
/* 3883 */     Preconditions.checkNotNull(value);
/* 3884 */     int hash = hash(key);
/* 3885 */     return segmentFor(hash).put(key, hash, value, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(K key, V value) {
/* 3890 */     Preconditions.checkNotNull(key);
/* 3891 */     Preconditions.checkNotNull(value);
/* 3892 */     int hash = hash(key);
/* 3893 */     return segmentFor(hash).put(key, hash, value, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/* 3898 */     for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
/* 3899 */       put(e.getKey(), e.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(@Nullable Object key) {
/* 3905 */     if (key == null) {
/* 3906 */       return null;
/*      */     }
/* 3908 */     int hash = hash(key);
/* 3909 */     return segmentFor(hash).remove(key, hash);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(@Nullable Object key, @Nullable Object value) {
/* 3914 */     if (key == null || value == null) {
/* 3915 */       return false;
/*      */     }
/* 3917 */     int hash = hash(key);
/* 3918 */     return segmentFor(hash).remove(key, hash, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K key, @Nullable V oldValue, V newValue) {
/* 3923 */     Preconditions.checkNotNull(key);
/* 3924 */     Preconditions.checkNotNull(newValue);
/* 3925 */     if (oldValue == null) {
/* 3926 */       return false;
/*      */     }
/* 3928 */     int hash = hash(key);
/* 3929 */     return segmentFor(hash).replace(key, hash, oldValue, newValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(K key, V value) {
/* 3934 */     Preconditions.checkNotNull(key);
/* 3935 */     Preconditions.checkNotNull(value);
/* 3936 */     int hash = hash(key);
/* 3937 */     return segmentFor(hash).replace(key, hash, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/* 3942 */     for (Segment<K, V> segment : this.segments) {
/* 3943 */       segment.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<K> keySet() {
/* 3952 */     Set<K> ks = this.keySet;
/* 3953 */     return (ks != null) ? ks : (this.keySet = new KeySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> values() {
/* 3961 */     Collection<V> vs = this.values;
/* 3962 */     return (vs != null) ? vs : (this.values = new Values());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/* 3970 */     Set<Map.Entry<K, V>> es = this.entrySet;
/* 3971 */     return (es != null) ? es : (this.entrySet = new EntrySet());
/*      */   }
/*      */ 
/*      */   
/*      */   abstract class HashIterator
/*      */   {
/*      */     int nextSegmentIndex;
/*      */     
/*      */     int nextTableIndex;
/*      */     CustomConcurrentHashMap.Segment<K, V> currentSegment;
/*      */     AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> currentTable;
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEntry;
/*      */     CustomConcurrentHashMap<K, V>.WriteThroughEntry nextExternal;
/*      */     CustomConcurrentHashMap<K, V>.WriteThroughEntry lastReturned;
/*      */     
/*      */     HashIterator() {
/* 3987 */       this.nextSegmentIndex = CustomConcurrentHashMap.this.segments.length - 1;
/* 3988 */       this.nextTableIndex = -1;
/* 3989 */       advance();
/*      */     }
/*      */     
/*      */     final void advance() {
/* 3993 */       this.nextExternal = null;
/*      */       
/* 3995 */       if (nextInChain()) {
/*      */         return;
/*      */       }
/*      */       
/* 3999 */       if (nextInTable()) {
/*      */         return;
/*      */       }
/*      */       
/* 4003 */       while (this.nextSegmentIndex >= 0) {
/* 4004 */         this.currentSegment = CustomConcurrentHashMap.this.segments[this.nextSegmentIndex--];
/* 4005 */         if (this.currentSegment.count != 0) {
/* 4006 */           this.currentTable = this.currentSegment.table;
/* 4007 */           this.nextTableIndex = this.currentTable.length() - 1;
/* 4008 */           if (nextInTable()) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean nextInChain() {
/* 4019 */       if (this.nextEntry != null) {
/* 4020 */         for (this.nextEntry = this.nextEntry.getNext(); this.nextEntry != null; this.nextEntry = this.nextEntry.getNext()) {
/* 4021 */           if (advanceTo(this.nextEntry)) {
/* 4022 */             return true;
/*      */           }
/*      */         } 
/*      */       }
/* 4026 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean nextInTable() {
/* 4033 */       while (this.nextTableIndex >= 0) {
/* 4034 */         if ((this.nextEntry = this.currentTable.get(this.nextTableIndex--)) != null && (
/* 4035 */           advanceTo(this.nextEntry) || nextInChain())) {
/* 4036 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 4040 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean advanceTo(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/*      */       try {
/* 4049 */         K key = entry.getKey();
/* 4050 */         V value = CustomConcurrentHashMap.this.getLiveValue(entry);
/* 4051 */         if (value != null) {
/* 4052 */           this.nextExternal = new CustomConcurrentHashMap.WriteThroughEntry(key, value);
/* 4053 */           return true;
/*      */         } 
/*      */         
/* 4056 */         return false;
/*      */       } finally {
/*      */         
/* 4059 */         this.currentSegment.postReadCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 4064 */       return (this.nextExternal != null);
/*      */     }
/*      */     
/*      */     CustomConcurrentHashMap<K, V>.WriteThroughEntry nextEntry() {
/* 4068 */       if (this.nextExternal == null) {
/* 4069 */         throw new NoSuchElementException();
/*      */       }
/* 4071 */       this.lastReturned = this.nextExternal;
/* 4072 */       advance();
/* 4073 */       return this.lastReturned;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 4077 */       Preconditions.checkState((this.lastReturned != null));
/* 4078 */       CustomConcurrentHashMap.this.remove(this.lastReturned.getKey());
/* 4079 */       this.lastReturned = null;
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeyIterator
/*      */     extends HashIterator
/*      */     implements Iterator<K> {
/*      */     public K next() {
/* 4087 */       return nextEntry().getKey();
/*      */     }
/*      */   }
/*      */   
/*      */   final class ValueIterator
/*      */     extends HashIterator
/*      */     implements Iterator<V> {
/*      */     public V next() {
/* 4095 */       return nextEntry().getValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final class WriteThroughEntry
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final K key;
/*      */     
/*      */     V value;
/*      */     
/*      */     WriteThroughEntry(K key, V value) {
/* 4108 */       this.key = key;
/* 4109 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public K getKey() {
/* 4114 */       return this.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/* 4119 */       return this.value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/* 4125 */       if (object instanceof Map.Entry) {
/* 4126 */         Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
/* 4127 */         return (this.key.equals(that.getKey()) && this.value.equals(that.getValue()));
/*      */       } 
/* 4129 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 4135 */       return this.key.hashCode() ^ this.value.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V newValue) {
/* 4140 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 4147 */       return (new StringBuilder()).append(getKey()).append("=").append(getValue()).toString();
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntryIterator
/*      */     extends HashIterator
/*      */     implements Iterator<Map.Entry<K, V>> {
/*      */     public Map.Entry<K, V> next() {
/* 4155 */       return nextEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeySet
/*      */     extends AbstractSet<K>
/*      */   {
/*      */     public Iterator<K> iterator() {
/* 4163 */       return new CustomConcurrentHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 4168 */       return CustomConcurrentHashMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 4173 */       return CustomConcurrentHashMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 4178 */       return CustomConcurrentHashMap.this.containsKey(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 4183 */       return (CustomConcurrentHashMap.this.remove(o) != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 4188 */       CustomConcurrentHashMap.this.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   final class Values
/*      */     extends AbstractCollection<V>
/*      */   {
/*      */     public Iterator<V> iterator() {
/* 4196 */       return new CustomConcurrentHashMap.ValueIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 4201 */       return CustomConcurrentHashMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 4206 */       return CustomConcurrentHashMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 4211 */       return CustomConcurrentHashMap.this.containsValue(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 4216 */       CustomConcurrentHashMap.this.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntrySet
/*      */     extends AbstractSet<Map.Entry<K, V>>
/*      */   {
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/* 4224 */       return new CustomConcurrentHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 4229 */       if (!(o instanceof Map.Entry)) {
/* 4230 */         return false;
/*      */       }
/* 4232 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 4233 */       Object key = e.getKey();
/* 4234 */       if (key == null) {
/* 4235 */         return false;
/*      */       }
/* 4237 */       V v = (V)CustomConcurrentHashMap.this.get(key);
/*      */       
/* 4239 */       return (v != null && CustomConcurrentHashMap.this.valueEquivalence.equivalent(e.getValue(), v));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 4244 */       if (!(o instanceof Map.Entry)) {
/* 4245 */         return false;
/*      */       }
/* 4247 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 4248 */       Object key = e.getKey();
/* 4249 */       return (key != null && CustomConcurrentHashMap.this.remove(key, e.getValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 4254 */       return CustomConcurrentHashMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 4259 */       return CustomConcurrentHashMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 4264 */       CustomConcurrentHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Cache<K, V> cacheSerializationProxy() {
/* 4271 */     return new SerializationProxy<K, V>(this.loader, this.keyStrength, this.valueStrength, this.keyEquivalence, this.valueEquivalence, this.expireAfterWriteNanos, this.expireAfterAccessNanos, this.maximumSize, this.concurrencyLevel, this.removalListener, this.ticker);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class SerializationProxy<K, V>
/*      */     extends ForwardingCache<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     final CacheLoader<? super K, V> loader;
/*      */     
/*      */     final CustomConcurrentHashMap.Strength keyStrength;
/*      */     
/*      */     final CustomConcurrentHashMap.Strength valueStrength;
/*      */     
/*      */     final Equivalence<Object> keyEquivalence;
/*      */     
/*      */     final Equivalence<Object> valueEquivalence;
/*      */     
/*      */     final long expireAfterWriteNanos;
/*      */     
/*      */     final long expireAfterAccessNanos;
/*      */     
/*      */     final int maximumSize;
/*      */     
/*      */     final int concurrencyLevel;
/*      */     
/*      */     final RemovalListener<? super K, ? super V> removalListener;
/*      */     
/*      */     final Ticker ticker;
/*      */     
/*      */     transient Cache<K, V> delegate;
/*      */ 
/*      */     
/*      */     SerializationProxy(CacheLoader<? super K, V> loader, CustomConcurrentHashMap.Strength keyStrength, CustomConcurrentHashMap.Strength valueStrength, Equivalence<Object> keyEquivalence, Equivalence<Object> valueEquivalence, long expireAfterWriteNanos, long expireAfterAccessNanos, int maximumSize, int concurrencyLevel, RemovalListener<? super K, ? super V> removalListener, Ticker ticker) {
/* 4308 */       this.loader = loader;
/* 4309 */       this.keyStrength = keyStrength;
/* 4310 */       this.valueStrength = valueStrength;
/* 4311 */       this.keyEquivalence = keyEquivalence;
/* 4312 */       this.valueEquivalence = valueEquivalence;
/* 4313 */       this.expireAfterWriteNanos = expireAfterWriteNanos;
/* 4314 */       this.expireAfterAccessNanos = expireAfterAccessNanos;
/* 4315 */       this.maximumSize = maximumSize;
/* 4316 */       this.concurrencyLevel = concurrencyLevel;
/* 4317 */       this.removalListener = removalListener;
/* 4318 */       this.ticker = ticker;
/*      */     }
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 4322 */       in.defaultReadObject();
/* 4323 */       CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4329 */       builder.removalListener(this.removalListener);
/* 4330 */       if (this.expireAfterWriteNanos > 0L) {
/* 4331 */         builder.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
/*      */       }
/* 4333 */       if (this.expireAfterAccessNanos > 0L) {
/* 4334 */         builder.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
/*      */       }
/* 4336 */       if (this.maximumSize != -1) {
/* 4337 */         builder.maximumSize(this.maximumSize);
/*      */       }
/* 4339 */       if (this.ticker != Ticker.systemTicker()) {
/* 4340 */         builder.ticker(this.ticker);
/*      */       }
/* 4342 */       this.delegate = builder.build(this.loader);
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 4346 */       return this.delegate;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Cache<K, V> delegate() {
/* 4351 */       return this.delegate;
/*      */     }
/*      */   }
/*      */   
/*      */   private static interface ComputedValue<V> {
/*      */     V get() throws ExecutionException;
/*      */   }
/*      */   
/*      */   static interface ReferenceEntry<K, V> {
/*      */     CustomConcurrentHashMap.ValueReference<K, V> getValueReference();
/*      */     
/*      */     void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> param1ValueReference);
/*      */     
/*      */     ReferenceEntry<K, V> getNext();
/*      */     
/*      */     int getHash();
/*      */     
/*      */     K getKey();
/*      */     
/*      */     long getExpirationTime();
/*      */     
/*      */     void setExpirationTime(long param1Long);
/*      */     
/*      */     ReferenceEntry<K, V> getNextExpirable();
/*      */     
/*      */     void setNextExpirable(ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     ReferenceEntry<K, V> getPreviousExpirable();
/*      */     
/*      */     void setPreviousExpirable(ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     ReferenceEntry<K, V> getNextEvictable();
/*      */     
/*      */     void setNextEvictable(ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     ReferenceEntry<K, V> getPreviousEvictable();
/*      */     
/*      */     void setPreviousEvictable(ReferenceEntry<K, V> param1ReferenceEntry);
/*      */   }
/*      */   
/*      */   static interface ValueReference<K, V> {
/*      */     V get();
/*      */     
/*      */     V waitForValue() throws ExecutionException;
/*      */     
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry();
/*      */     
/*      */     ValueReference<K, V> copyFor(ReferenceQueue<V> param1ReferenceQueue, CustomConcurrentHashMap.ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     void notifyNewValue(V param1V);
/*      */     
/*      */     boolean isComputingReference();
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\cache\CustomConcurrentHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */