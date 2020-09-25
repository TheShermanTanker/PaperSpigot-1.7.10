/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.base.Equivalence;
/*      */ import com.google.common.base.Equivalences;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Ticker;
/*      */ import com.google.common.primitives.Ints;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
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
/*      */ import java.util.concurrent.CancellationException;
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
/*      */ 
/*      */ class CustomConcurrentHashMap<K, V>
/*      */   extends AbstractMap<K, V>
/*      */   implements ConcurrentMap<K, V>, Serializable
/*      */ {
/*      */   static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   static final int MAX_SEGMENTS = 65536;
/*      */   static final int CONTAINS_VALUE_RETRIES = 3;
/*      */   static final int DRAIN_THRESHOLD = 63;
/*      */   static final int DRAIN_MAX = 16;
/*      */   static final long CLEANUP_EXECUTOR_DELAY_SECS = 60L;
/*  136 */   private static final Logger logger = Logger.getLogger(CustomConcurrentHashMap.class.getName());
/*      */ 
/*      */ 
/*      */   
/*      */   final transient int segmentMask;
/*      */ 
/*      */ 
/*      */   
/*      */   final transient int segmentShift;
/*      */ 
/*      */ 
/*      */   
/*      */   final transient Segment<K, V>[] segments;
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
/*      */   final Queue<MapMaker.RemovalNotification<K, V>> removalNotificationQueue;
/*      */ 
/*      */   
/*      */   final MapMaker.RemovalListener<K, V> removalListener;
/*      */ 
/*      */   
/*      */   final transient EntryFactory entryFactory;
/*      */ 
/*      */   
/*      */   final Ticker ticker;
/*      */ 
/*      */ 
/*      */   
/*      */   CustomConcurrentHashMap(MapMaker builder) {
/*  197 */     this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
/*      */     
/*  199 */     this.keyStrength = builder.getKeyStrength();
/*  200 */     this.valueStrength = builder.getValueStrength();
/*      */     
/*  202 */     this.keyEquivalence = builder.getKeyEquivalence();
/*  203 */     this.valueEquivalence = builder.getValueEquivalence();
/*      */     
/*  205 */     this.maximumSize = builder.maximumSize;
/*  206 */     this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
/*  207 */     this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
/*      */     
/*  209 */     this.entryFactory = EntryFactory.getFactory(this.keyStrength, expires(), evictsBySize());
/*  210 */     this.ticker = builder.getTicker();
/*      */     
/*  212 */     this.removalListener = builder.getRemovalListener();
/*  213 */     this.removalNotificationQueue = (this.removalListener == GenericMapMaker.NullListener.INSTANCE) ? discardingQueue() : new ConcurrentLinkedQueue<MapMaker.RemovalNotification<K, V>>();
/*      */ 
/*      */ 
/*      */     
/*  217 */     int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
/*  218 */     if (evictsBySize()) {
/*  219 */       initialCapacity = Math.min(initialCapacity, this.maximumSize);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  225 */     int segmentShift = 0;
/*  226 */     int segmentCount = 1;
/*      */     
/*  228 */     while (segmentCount < this.concurrencyLevel && (!evictsBySize() || segmentCount * 2 <= this.maximumSize)) {
/*  229 */       segmentShift++;
/*  230 */       segmentCount <<= 1;
/*      */     } 
/*  232 */     this.segmentShift = 32 - segmentShift;
/*  233 */     this.segmentMask = segmentCount - 1;
/*      */     
/*  235 */     this.segments = newSegmentArray(segmentCount);
/*      */     
/*  237 */     int segmentCapacity = initialCapacity / segmentCount;
/*  238 */     if (segmentCapacity * segmentCount < initialCapacity) {
/*  239 */       segmentCapacity++;
/*      */     }
/*      */     
/*  242 */     int segmentSize = 1;
/*  243 */     while (segmentSize < segmentCapacity) {
/*  244 */       segmentSize <<= 1;
/*      */     }
/*      */     
/*  247 */     if (evictsBySize()) {
/*      */       
/*  249 */       int maximumSegmentSize = this.maximumSize / segmentCount + 1;
/*  250 */       int remainder = this.maximumSize % segmentCount;
/*  251 */       for (int i = 0; i < this.segments.length; i++) {
/*  252 */         if (i == remainder) {
/*  253 */           maximumSegmentSize--;
/*      */         }
/*  255 */         this.segments[i] = createSegment(segmentSize, maximumSegmentSize);
/*      */       } 
/*      */     } else {
/*      */       
/*  259 */       for (int i = 0; i < this.segments.length; i++) {
/*  260 */         this.segments[i] = createSegment(segmentSize, -1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean evictsBySize() {
/*  267 */     return (this.maximumSize != -1);
/*      */   }
/*      */   
/*      */   boolean expires() {
/*  271 */     return (expiresAfterWrite() || expiresAfterAccess());
/*      */   }
/*      */   
/*      */   boolean expiresAfterWrite() {
/*  275 */     return (this.expireAfterWriteNanos > 0L);
/*      */   }
/*      */   
/*      */   boolean expiresAfterAccess() {
/*  279 */     return (this.expireAfterAccessNanos > 0L);
/*      */   }
/*      */   
/*      */   boolean usesKeyReferences() {
/*  283 */     return (this.keyStrength != Strength.STRONG);
/*      */   }
/*      */   
/*      */   boolean usesValueReferences() {
/*  287 */     return (this.valueStrength != Strength.STRONG);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum Strength
/*      */   {
/*  296 */     STRONG
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> entry, V value)
/*      */       {
/*  300 */         return new CustomConcurrentHashMap.StrongValueReference<K, V>(value);
/*      */       }
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  305 */         return Equivalences.equals();
/*      */       }
/*      */     },
/*      */     
/*  309 */     SOFT
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> entry, V value)
/*      */       {
/*  313 */         return new CustomConcurrentHashMap.SoftValueReference<K, V>(segment.valueReferenceQueue, value, entry);
/*      */       }
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  318 */         return Equivalences.identity();
/*      */       }
/*      */     },
/*      */     
/*  322 */     WEAK
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ValueReference<K, V> referenceValue(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> entry, V value)
/*      */       {
/*  326 */         return new CustomConcurrentHashMap.WeakValueReference<K, V>(segment.valueReferenceQueue, value, entry);
/*      */       }
/*      */ 
/*      */       
/*      */       Equivalence<Object> defaultEquivalence() {
/*  331 */         return Equivalences.identity();
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
/*  353 */     STRONG
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  357 */         return new CustomConcurrentHashMap.StrongEntry<K, V>(key, hash, next);
/*      */       }
/*      */     },
/*  360 */     STRONG_EXPIRABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  364 */         return new CustomConcurrentHashMap.StrongExpirableEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  370 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  371 */         copyExpirableEntry(original, newEntry);
/*  372 */         return newEntry;
/*      */       }
/*      */     },
/*  375 */     STRONG_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  379 */         return new CustomConcurrentHashMap.StrongEvictableEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  385 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  386 */         copyEvictableEntry(original, newEntry);
/*  387 */         return newEntry;
/*      */       }
/*      */     },
/*  390 */     STRONG_EXPIRABLE_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  394 */         return new CustomConcurrentHashMap.StrongExpirableEvictableEntry<K, V>(key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  400 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  401 */         copyExpirableEntry(original, newEntry);
/*  402 */         copyEvictableEntry(original, newEntry);
/*  403 */         return newEntry;
/*      */       }
/*      */     },
/*      */     
/*  407 */     SOFT
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  411 */         return new CustomConcurrentHashMap.SoftEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */     },
/*  414 */     SOFT_EXPIRABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  418 */         return new CustomConcurrentHashMap.SoftExpirableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  424 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  425 */         copyExpirableEntry(original, newEntry);
/*  426 */         return newEntry;
/*      */       }
/*      */     },
/*  429 */     SOFT_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  433 */         return new CustomConcurrentHashMap.SoftEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  439 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  440 */         copyEvictableEntry(original, newEntry);
/*  441 */         return newEntry;
/*      */       }
/*      */     },
/*  444 */     SOFT_EXPIRABLE_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  448 */         return new CustomConcurrentHashMap.SoftExpirableEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  454 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  455 */         copyExpirableEntry(original, newEntry);
/*  456 */         copyEvictableEntry(original, newEntry);
/*  457 */         return newEntry;
/*      */       }
/*      */     },
/*      */     
/*  461 */     WEAK
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  465 */         return new CustomConcurrentHashMap.WeakEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */     },
/*  468 */     WEAK_EXPIRABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  472 */         return new CustomConcurrentHashMap.WeakExpirableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  478 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  479 */         copyExpirableEntry(original, newEntry);
/*  480 */         return newEntry;
/*      */       }
/*      */     },
/*  483 */     WEAK_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  487 */         return new CustomConcurrentHashMap.WeakEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  493 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  494 */         copyEvictableEntry(original, newEntry);
/*  495 */         return newEntry;
/*      */       }
/*      */     },
/*  498 */     WEAK_EXPIRABLE_EVICTABLE
/*      */     {
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Segment<K, V> segment, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next)
/*      */       {
/*  502 */         return new CustomConcurrentHashMap.WeakExpirableEvictableEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       <K, V> CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.Segment<K, V> segment, CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/*  508 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
/*  509 */         copyExpirableEntry(original, newEntry);
/*  510 */         copyEvictableEntry(original, newEntry);
/*  511 */         return newEntry;
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
/*  525 */     static final EntryFactory[][] factories = new EntryFactory[][] { { STRONG, STRONG_EXPIRABLE, STRONG_EVICTABLE, STRONG_EXPIRABLE_EVICTABLE }, { SOFT, SOFT_EXPIRABLE, SOFT_EVICTABLE, SOFT_EXPIRABLE_EVICTABLE }, { WEAK, WEAK_EXPIRABLE, WEAK_EVICTABLE, WEAK_EXPIRABLE_EVICTABLE } };
/*      */ 
/*      */     
/*      */     static {
/*      */     
/*      */     }
/*      */     
/*      */     static EntryFactory getFactory(CustomConcurrentHashMap.Strength keyStrength, boolean expireAfterWrite, boolean evictsBySize) {
/*  533 */       int flags = (expireAfterWrite ? 1 : 0) | (evictsBySize ? 2 : 0);
/*  534 */       return factories[keyStrength.ordinal()][flags];
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
/*  557 */       return newEntry(segment, original.getKey(), original.getHash(), newNext);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     <K, V> void copyExpirableEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry) {
/*  564 */       newEntry.setExpirationTime(original.getExpirationTime());
/*      */       
/*  566 */       CustomConcurrentHashMap.connectExpirables(original.getPreviousExpirable(), newEntry);
/*  567 */       CustomConcurrentHashMap.connectExpirables(newEntry, original.getNextExpirable());
/*      */       
/*  569 */       CustomConcurrentHashMap.nullifyExpirable(original);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     <K, V> void copyEvictableEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry) {
/*  576 */       CustomConcurrentHashMap.connectEvictables(original.getPreviousEvictable(), newEntry);
/*  577 */       CustomConcurrentHashMap.connectEvictables(newEntry, original.getNextEvictable());
/*      */       
/*  579 */       CustomConcurrentHashMap.nullifyEvictable(original);
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
/*  630 */   static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>()
/*      */     {
/*      */       public Object get() {
/*  633 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getEntry() {
/*  638 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public CustomConcurrentHashMap.ValueReference<Object, Object> copyFor(ReferenceQueue<Object> queue, CustomConcurrentHashMap.ReferenceEntry<Object, Object> entry) {
/*  644 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isComputingReference() {
/*  649 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object waitForValue() {
/*  654 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void clear(CustomConcurrentHashMap.ValueReference<Object, Object> newValue) {}
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   static <K, V> ValueReference<K, V> unset() {
/*  666 */     return (ValueReference)UNSET;
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
/*      */   private enum NullEntry
/*      */     implements ReferenceEntry<Object, Object>
/*      */   {
/*  772 */     INSTANCE;
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<Object, Object> getValueReference() {
/*  776 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValueReference(CustomConcurrentHashMap.ValueReference<Object, Object> valueReference) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNext() {
/*  784 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/*  789 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getKey() {
/*  794 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getExpirationTime() {
/*  799 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setExpirationTime(long time) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNextExpirable() {
/*  807 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> next) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getPreviousExpirable() {
/*  815 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> previous) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getNextEvictable() {
/*  823 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> next) {}
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<Object, Object> getPreviousEvictable() {
/*  831 */       return this;
/*      */     }
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<Object, Object> previous) {}
/*      */   }
/*      */   
/*      */   static abstract class AbstractReferenceEntry<K, V>
/*      */     implements ReferenceEntry<K, V>
/*      */   {
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> getValueReference() {
/*  841 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/*  846 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext() {
/*  851 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/*  856 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  861 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long getExpirationTime() {
/*  866 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setExpirationTime(long time) {
/*  871 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() {
/*  876 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*  881 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/*  886 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/*  891 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/*  896 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*  901 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/*  906 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/*  911 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static <K, V> ReferenceEntry<K, V> nullEntry() {
/*  917 */     return NullEntry.INSTANCE;
/*      */   }
/*      */   
/*  920 */   static final Queue<? extends Object> DISCARDING_QUEUE = new AbstractQueue()
/*      */     {
/*      */       public boolean offer(Object o) {
/*  923 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object peek() {
/*  928 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Object poll() {
/*  933 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public int size() {
/*  938 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Object> iterator() {
/*  943 */         return Iterators.emptyIterator();
/*      */       }
/*      */     };
/*      */   Set<K> keySet;
/*      */   Collection<V> values;
/*      */   Set<Map.Entry<K, V>> entrySet;
/*      */   private static final long serialVersionUID = 5L;
/*      */   
/*      */   static <E> Queue<E> discardingQueue() {
/*  952 */     return (Queue)DISCARDING_QUEUE;
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
/* 1038 */       this.valueReference = CustomConcurrentHashMap.unset(); this.key = key; this.hash = hash; this.next = next;
/*      */     }
/*      */     public K getKey() { return this.key; }
/*      */     public long getExpirationTime() { throw new UnsupportedOperationException(); }
/* 1042 */     public void setExpirationTime(long time) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { throw new UnsupportedOperationException(); } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { throw new UnsupportedOperationException(); }
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); }
/* 1047 */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { throw new UnsupportedOperationException(); } public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); } public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) { CustomConcurrentHashMap.ValueReference<K, V> previous = this.valueReference;
/* 1048 */       this.valueReference = valueReference;
/* 1049 */       previous.clear(valueReference); }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getHash() {
/* 1054 */       return this.hash;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext() {
/* 1059 */       return this.next;
/*      */     } }
/*      */   static final class StrongExpirableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V> { volatile long time; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable;
/*      */     
/* 1066 */     StrongExpirableEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1071 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1083 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1096 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; }
/*      */     public void setExpirationTime(long time) { this.time = time; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; }
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/* 1101 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1106 */       this.previousExpirable = previous;
/*      */     } }
/*      */   static final class StrongEvictableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V> { @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1113 */     StrongEvictableEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1118 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1131 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/*      */       return this.nextEvictable;
/*      */     } public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*      */       this.nextEvictable = next;
/*      */     } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/* 1136 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1141 */       this.previousEvictable = previous;
/*      */     } }
/*      */   static final class StrongExpirableEvictableEntry<K, V> extends StrongEntry<K, V> implements ReferenceEntry<K, V> { volatile long time; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/* 1148 */     StrongExpirableEvictableEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1153 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1165 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1178 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1193 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1206 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; } public void setExpirationTime(long time) { this.time = time; } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { return this.previousExpirable; }
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { this.previousExpirable = previous; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { return this.nextEvictable; }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextEvictable = next; }
/* 1211 */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { return this.previousEvictable; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1216 */       this.previousEvictable = previous;
/*      */     } }
/*      */ 
/*      */   
/*      */   static class SoftEntry<K, V> extends SoftReference<K> implements ReferenceEntry<K, V> {
/*      */     final int hash;
/*      */     final CustomConcurrentHashMap.ReferenceEntry<K, V> next;
/*      */     volatile CustomConcurrentHashMap.ValueReference<K, V> valueReference;
/*      */     
/* 1225 */     SoftEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, queue);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1292 */       this.valueReference = CustomConcurrentHashMap.unset(); this.hash = hash; this.next = next; } public K getKey() { return get(); } public long getExpirationTime() { throw new UnsupportedOperationException(); } public void setExpirationTime(long time) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { throw new UnsupportedOperationException(); } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { throw new UnsupportedOperationException(); } public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); }
/* 1296 */     public CustomConcurrentHashMap.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 1301 */       CustomConcurrentHashMap.ValueReference<K, V> previous = this.valueReference;
/* 1302 */       this.valueReference = valueReference;
/* 1303 */       previous.clear(valueReference);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/* 1308 */       return this.hash;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext() {
/* 1313 */       return this.next;
/*      */     } }
/*      */   static final class SoftExpirableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V> { volatile long time;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable;
/*      */     
/* 1321 */     SoftExpirableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1326 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1338 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1351 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; }
/*      */     public void setExpirationTime(long time) { this.time = time; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; }
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/* 1356 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1361 */       this.previousExpirable = previous;
/*      */     } }
/*      */   
/*      */   static final class SoftEvictableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V> { @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1369 */     SoftEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1374 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1387 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/*      */       return this.nextEvictable;
/*      */     } public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*      */       this.nextEvictable = next;
/*      */     } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/* 1392 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1397 */       this.previousEvictable = previous;
/*      */     } }
/*      */   static final class SoftExpirableEvictableEntry<K, V> extends SoftEntry<K, V> implements ReferenceEntry<K, V> { volatile long time; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1405 */     SoftExpirableEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1410 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1422 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1435 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1450 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1463 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; } public void setExpirationTime(long time) { this.time = time; } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { return this.previousExpirable; }
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { this.previousExpirable = previous; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { return this.nextEvictable; }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextEvictable = next; }
/* 1468 */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { return this.previousEvictable; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1473 */       this.previousEvictable = previous;
/*      */     } }
/*      */ 
/*      */   
/*      */   static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
/*      */     final int hash;
/*      */     final CustomConcurrentHashMap.ReferenceEntry<K, V> next;
/*      */     volatile CustomConcurrentHashMap.ValueReference<K, V> valueReference;
/*      */     
/* 1482 */     WeakEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(key, queue);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1550 */       this.valueReference = CustomConcurrentHashMap.unset(); this.hash = hash; this.next = next; } public K getKey() { return get(); } public long getExpirationTime() { throw new UnsupportedOperationException(); } public void setExpirationTime(long time) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { throw new UnsupportedOperationException(); } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { throw new UnsupportedOperationException(); } public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { throw new UnsupportedOperationException(); }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { throw new UnsupportedOperationException(); }
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { throw new UnsupportedOperationException(); }
/* 1554 */     public CustomConcurrentHashMap.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueReference(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 1559 */       CustomConcurrentHashMap.ValueReference<K, V> previous = this.valueReference;
/* 1560 */       this.valueReference = valueReference;
/* 1561 */       previous.clear(valueReference);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHash() {
/* 1566 */       return this.hash;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNext() {
/* 1571 */       return this.next;
/*      */     } }
/*      */   static final class WeakExpirableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V> { volatile long time;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable;
/*      */     
/* 1579 */     WeakExpirableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1584 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1596 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1609 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; }
/*      */     public void setExpirationTime(long time) { this.time = time; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; }
/*      */     public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/* 1614 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1619 */       this.previousExpirable = previous;
/*      */     } }
/*      */   
/*      */   static final class WeakEvictableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V> { @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable;
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1627 */     WeakEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1632 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1645 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/*      */       return this.nextEvictable;
/*      */     } public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/*      */       this.nextEvictable = next;
/*      */     } public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/* 1650 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1655 */       this.previousEvictable = previous;
/*      */     } }
/*      */   static final class WeakExpirableEvictableEntry<K, V> extends WeakEntry<K, V> implements ReferenceEntry<K, V> { volatile long time; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable; @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable;
/*      */     
/* 1663 */     WeakExpirableEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) { super(queue, key, hash, next);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1668 */       this.time = Long.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1680 */       this.nextExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1693 */       this.previousExpirable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1708 */       this.nextEvictable = CustomConcurrentHashMap.nullEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1721 */       this.previousEvictable = CustomConcurrentHashMap.nullEntry(); } public long getExpirationTime() { return this.time; } public void setExpirationTime(long time) { this.time = time; } public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() { return this.nextExpirable; } public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextExpirable = next; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() { return this.previousExpirable; }
/*      */     public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) { this.previousExpirable = previous; }
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() { return this.nextEvictable; }
/*      */     public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) { this.nextEvictable = next; }
/* 1726 */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() { return this.previousEvictable; }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 1731 */       this.previousEvictable = previous;
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
/* 1743 */       super(referent, queue);
/* 1744 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 1749 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear(CustomConcurrentHashMap.ValueReference<K, V> newValue) {
/* 1754 */       clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 1760 */       return new WeakValueReference(queue, get(), entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isComputingReference() {
/* 1765 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1770 */       return get();
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
/* 1782 */       super(referent, queue);
/* 1783 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 1788 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear(CustomConcurrentHashMap.ValueReference<K, V> newValue) {
/* 1793 */       clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 1798 */       return new SoftValueReference(queue, get(), entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isComputingReference() {
/* 1803 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1808 */       return get();
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
/* 1819 */       this.referent = referent;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/* 1824 */       return this.referent;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry() {
/* 1829 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 1834 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isComputingReference() {
/* 1839 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public V waitForValue() {
/* 1844 */       return get();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear(CustomConcurrentHashMap.ValueReference<K, V> newValue) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int rehash(int h) {
/* 1863 */     h += h << 15 ^ 0xFFFFCD7D;
/* 1864 */     h ^= h >>> 10;
/* 1865 */     h += h << 3;
/* 1866 */     h ^= h >>> 6;
/* 1867 */     h += (h << 2) + (h << 14);
/* 1868 */     return h ^ h >>> 16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   @VisibleForTesting
/*      */   ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
/* 1877 */     return segmentFor(hash).newEntry(key, hash, next);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   @VisibleForTesting
/*      */   ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
/* 1886 */     int hash = original.getHash();
/* 1887 */     return segmentFor(hash).copyEntry(original, newNext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   @VisibleForTesting
/*      */   ValueReference<K, V> newValueReference(ReferenceEntry<K, V> entry, V value) {
/* 1896 */     int hash = entry.getHash();
/* 1897 */     return this.valueStrength.referenceValue(segmentFor(hash), entry, value);
/*      */   }
/*      */   
/*      */   int hash(Object key) {
/* 1901 */     int h = this.keyEquivalence.hash(key);
/* 1902 */     return rehash(h);
/*      */   }
/*      */   
/*      */   void reclaimValue(ValueReference<K, V> valueReference) {
/* 1906 */     ReferenceEntry<K, V> entry = valueReference.getEntry();
/* 1907 */     int hash = entry.getHash();
/* 1908 */     segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
/*      */   }
/*      */   
/*      */   void reclaimKey(ReferenceEntry<K, V> entry) {
/* 1912 */     int hash = entry.getHash();
/* 1913 */     segmentFor(hash).reclaimKey(entry, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   boolean isLive(ReferenceEntry<K, V> entry) {
/* 1922 */     return (segmentFor(entry.getHash()).getLiveValue(entry) != null);
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
/* 1933 */     return this.segments[hash >>> this.segmentShift & this.segmentMask];
/*      */   }
/*      */   
/*      */   Segment<K, V> createSegment(int initialCapacity, int maxSegmentSize) {
/* 1937 */     return new Segment<K, V>(this, initialCapacity, maxSegmentSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   V getLiveValue(ReferenceEntry<K, V> entry) {
/* 1946 */     if (entry.getKey() == null) {
/* 1947 */       return null;
/*      */     }
/* 1949 */     V value = (V)entry.getValueReference().get();
/* 1950 */     if (value == null) {
/* 1951 */       return null;
/*      */     }
/*      */     
/* 1954 */     if (expires() && isExpired(entry)) {
/* 1955 */       return null;
/*      */     }
/* 1957 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isExpired(ReferenceEntry<K, V> entry) {
/* 1966 */     return isExpired(entry, this.ticker.read());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isExpired(ReferenceEntry<K, V> entry, long now) {
/* 1974 */     return (now - entry.getExpirationTime() > 0L);
/*      */   }
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   static <K, V> void connectExpirables(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
/* 1979 */     previous.setNextExpirable(next);
/* 1980 */     next.setPreviousExpirable(previous);
/*      */   }
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   static <K, V> void nullifyExpirable(ReferenceEntry<K, V> nulled) {
/* 1985 */     ReferenceEntry<K, V> nullEntry = nullEntry();
/* 1986 */     nulled.setNextExpirable(nullEntry);
/* 1987 */     nulled.setPreviousExpirable(nullEntry);
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
/*      */     MapMaker.RemovalNotification<K, V> notification;
/* 1999 */     while ((notification = this.removalNotificationQueue.poll()) != null) {
/*      */       try {
/* 2001 */         this.removalListener.onRemoval(notification);
/* 2002 */       } catch (Exception e) {
/* 2003 */         logger.log(Level.WARNING, "Exception thrown by removal listener", e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   static <K, V> void connectEvictables(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
/* 2011 */     previous.setNextEvictable(next);
/* 2012 */     next.setPreviousEvictable(previous);
/*      */   }
/*      */   
/*      */   @GuardedBy("Segment.this")
/*      */   static <K, V> void nullifyEvictable(ReferenceEntry<K, V> nulled) {
/* 2017 */     ReferenceEntry<K, V> nullEntry = nullEntry();
/* 2018 */     nulled.setNextEvictable(nullEntry);
/* 2019 */     nulled.setPreviousEvictable(nullEntry);
/*      */   }
/*      */ 
/*      */   
/*      */   final Segment<K, V>[] newSegmentArray(int ssize) {
/* 2024 */     return (Segment<K, V>[])new Segment[ssize];
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
/* 2124 */     final AtomicInteger readCount = new AtomicInteger();
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
/*      */     
/*      */     Segment(CustomConcurrentHashMap<K, V> map, int initialCapacity, int maxSegmentSize) {
/* 2141 */       this.map = map;
/* 2142 */       this.maxSegmentSize = maxSegmentSize;
/* 2143 */       initTable(newEntryArray(initialCapacity));
/*      */       
/* 2145 */       this.keyReferenceQueue = map.usesKeyReferences() ? new ReferenceQueue<K>() : null;
/*      */ 
/*      */       
/* 2148 */       this.valueReferenceQueue = map.usesValueReferences() ? new ReferenceQueue<V>() : null;
/*      */ 
/*      */       
/* 2151 */       this.recencyQueue = (map.evictsBySize() || map.expiresAfterAccess()) ? new ConcurrentLinkedQueue<CustomConcurrentHashMap.ReferenceEntry<K, V>>() : CustomConcurrentHashMap.<CustomConcurrentHashMap.ReferenceEntry<K, V>>discardingQueue();
/*      */ 
/*      */ 
/*      */       
/* 2155 */       this.evictionQueue = map.evictsBySize() ? new CustomConcurrentHashMap.EvictionQueue<K, V>() : CustomConcurrentHashMap.<CustomConcurrentHashMap.ReferenceEntry<K, V>>discardingQueue();
/*      */ 
/*      */ 
/*      */       
/* 2159 */       this.expirationQueue = map.expires() ? new CustomConcurrentHashMap.ExpirationQueue<K, V>() : CustomConcurrentHashMap.<CustomConcurrentHashMap.ReferenceEntry<K, V>>discardingQueue();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> newEntryArray(int size) {
/* 2165 */       return new AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>>(size);
/*      */     }
/*      */     
/*      */     void initTable(AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> newTable) {
/* 2169 */       this.threshold = newTable.length() * 3 / 4;
/* 2170 */       if (this.threshold == this.maxSegmentSize)
/*      */       {
/* 2172 */         this.threshold++;
/*      */       }
/* 2174 */       this.table = newTable;
/*      */     }
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/* 2179 */       return this.map.entryFactory.newEntry(this, key, hash, next);
/*      */     }
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> copyEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> original, CustomConcurrentHashMap.ReferenceEntry<K, V> newNext) {
/* 2184 */       CustomConcurrentHashMap.ValueReference<K, V> valueReference = original.getValueReference();
/* 2185 */       CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
/* 2186 */       newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, newEntry));
/* 2187 */       return newEntry;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void setValue(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, V value) {
/* 2195 */       CustomConcurrentHashMap.ValueReference<K, V> valueReference = this.map.valueStrength.referenceValue(this, entry, value);
/* 2196 */       entry.setValueReference(valueReference);
/* 2197 */       recordWrite(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void tryDrainReferenceQueues() {
/* 2206 */       if (tryLock()) {
/*      */         try {
/* 2208 */           drainReferenceQueues();
/*      */         } finally {
/* 2210 */           unlock();
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
/* 2221 */       if (this.map.usesKeyReferences()) {
/* 2222 */         drainKeyReferenceQueue();
/*      */       }
/* 2224 */       if (this.map.usesValueReferences()) {
/* 2225 */         drainValueReferenceQueue();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void drainKeyReferenceQueue() {
/* 2232 */       int i = 0; Reference<? extends K> ref;
/* 2233 */       while ((ref = this.keyReferenceQueue.poll()) != null) {
/*      */         
/* 2235 */         CustomConcurrentHashMap.ReferenceEntry<K, V> entry = (CustomConcurrentHashMap.ReferenceEntry)ref;
/* 2236 */         this.map.reclaimKey(entry);
/* 2237 */         if (++i == 16) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void drainValueReferenceQueue() {
/* 2246 */       int i = 0; Reference<? extends V> ref;
/* 2247 */       while ((ref = this.valueReferenceQueue.poll()) != null) {
/*      */         
/* 2249 */         CustomConcurrentHashMap.ValueReference<K, V> valueReference = (CustomConcurrentHashMap.ValueReference)ref;
/* 2250 */         this.map.reclaimValue(valueReference);
/* 2251 */         if (++i == 16) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clearReferenceQueues() {
/* 2261 */       if (this.map.usesKeyReferences()) {
/* 2262 */         clearKeyReferenceQueue();
/*      */       }
/* 2264 */       if (this.map.usesValueReferences()) {
/* 2265 */         clearValueReferenceQueue();
/*      */       }
/*      */     }
/*      */     
/*      */     void clearKeyReferenceQueue() {
/* 2270 */       while (this.keyReferenceQueue.poll() != null);
/*      */     }
/*      */     
/*      */     void clearValueReferenceQueue() {
/* 2274 */       while (this.valueReferenceQueue.poll() != null);
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
/* 2287 */       if (this.map.expiresAfterAccess()) {
/* 2288 */         recordExpirationTime(entry, this.map.expireAfterAccessNanos);
/*      */       }
/* 2290 */       this.recencyQueue.add(entry);
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
/* 2302 */       this.evictionQueue.add(entry);
/* 2303 */       if (this.map.expiresAfterAccess()) {
/* 2304 */         recordExpirationTime(entry, this.map.expireAfterAccessNanos);
/* 2305 */         this.expirationQueue.add(entry);
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
/* 2316 */       drainRecencyQueue();
/* 2317 */       this.evictionQueue.add(entry);
/* 2318 */       if (this.map.expires()) {
/*      */ 
/*      */         
/* 2321 */         long expiration = this.map.expiresAfterAccess() ? this.map.expireAfterAccessNanos : this.map.expireAfterWriteNanos;
/*      */ 
/*      */         
/* 2324 */         recordExpirationTime(entry, expiration);
/* 2325 */         this.expirationQueue.add(entry);
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
/* 2338 */       while ((e = this.recencyQueue.poll()) != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2343 */         if (this.evictionQueue.contains(e)) {
/* 2344 */           this.evictionQueue.add(e);
/*      */         }
/* 2346 */         if (this.map.expiresAfterAccess() && this.expirationQueue.contains(e)) {
/* 2347 */           this.expirationQueue.add(e);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void recordExpirationTime(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, long expirationNanos) {
/* 2356 */       entry.setExpirationTime(this.map.ticker.read() + expirationNanos);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void tryExpireEntries() {
/* 2363 */       if (tryLock()) {
/*      */         try {
/* 2365 */           expireEntries();
/*      */         } finally {
/* 2367 */           unlock();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void expireEntries() {
/* 2375 */       drainRecencyQueue();
/*      */       
/* 2377 */       if (this.expirationQueue.isEmpty()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 2382 */       long now = this.map.ticker.read();
/*      */       CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2384 */       while ((e = this.expirationQueue.peek()) != null && this.map.isExpired(e, now)) {
/* 2385 */         if (!removeEntry(e, e.getHash(), MapMaker.RemovalCause.EXPIRED)) {
/* 2386 */           throw new AssertionError();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void enqueueNotification(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, MapMaker.RemovalCause cause) {
/* 2394 */       enqueueNotification(entry.getKey(), entry.getHash(), (V)entry.getValueReference().get(), cause);
/*      */     }
/*      */     
/*      */     void enqueueNotification(@Nullable K key, int hash, @Nullable V value, MapMaker.RemovalCause cause) {
/* 2398 */       if (this.map.removalNotificationQueue != CustomConcurrentHashMap.DISCARDING_QUEUE) {
/* 2399 */         MapMaker.RemovalNotification<K, V> notification = new MapMaker.RemovalNotification<K, V>(key, value, cause);
/* 2400 */         this.map.removalNotificationQueue.offer(notification);
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
/* 2412 */       if (this.map.evictsBySize() && this.count >= this.maxSegmentSize) {
/* 2413 */         drainRecencyQueue();
/*      */         
/* 2415 */         CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.evictionQueue.remove();
/* 2416 */         if (!removeEntry(e, e.getHash(), MapMaker.RemovalCause.SIZE)) {
/* 2417 */           throw new AssertionError();
/*      */         }
/* 2419 */         return true;
/*      */       } 
/* 2421 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> getFirst(int hash) {
/* 2429 */       AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2430 */       return table.get(hash & table.length() - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> getEntry(Object key, int hash) {
/* 2436 */       if (this.count != 0) {
/* 2437 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = getFirst(hash); e != null; e = e.getNext()) {
/* 2438 */           if (e.getHash() == hash) {
/*      */ 
/*      */ 
/*      */             
/* 2442 */             K entryKey = e.getKey();
/* 2443 */             if (entryKey == null) {
/* 2444 */               tryDrainReferenceQueues();
/*      */ 
/*      */             
/*      */             }
/* 2448 */             else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
/* 2449 */               return e;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 2454 */       return null;
/*      */     }
/*      */     
/*      */     CustomConcurrentHashMap.ReferenceEntry<K, V> getLiveEntry(Object key, int hash) {
/* 2458 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = getEntry(key, hash);
/* 2459 */       if (e == null)
/* 2460 */         return null; 
/* 2461 */       if (this.map.expires() && this.map.isExpired(e)) {
/* 2462 */         tryExpireEntries();
/* 2463 */         return null;
/*      */       } 
/* 2465 */       return e;
/*      */     }
/*      */     
/*      */     V get(Object key, int hash) {
/*      */       try {
/* 2470 */         CustomConcurrentHashMap.ReferenceEntry<K, V> e = getLiveEntry(key, hash);
/* 2471 */         if (e == null) {
/* 2472 */           return null;
/*      */         }
/*      */         
/* 2475 */         V value = (V)e.getValueReference().get();
/* 2476 */         if (value != null) {
/* 2477 */           recordRead(e);
/*      */         } else {
/* 2479 */           tryDrainReferenceQueues();
/*      */         } 
/* 2481 */         return value;
/*      */       } finally {
/* 2483 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean containsKey(Object key, int hash) {
/*      */       try {
/* 2489 */         if (this.count != 0) {
/* 2490 */           CustomConcurrentHashMap.ReferenceEntry<K, V> e = getLiveEntry(key, hash);
/* 2491 */           if (e == null) {
/* 2492 */             return false;
/*      */           }
/* 2494 */           return (e.getValueReference().get() != null);
/*      */         } 
/*      */         
/* 2497 */         return false;
/*      */       } finally {
/* 2499 */         postReadCleanup();
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
/* 2510 */         if (this.count != 0) {
/* 2511 */           AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2512 */           int length = table.length();
/* 2513 */           for (int i = 0; i < length; i++) {
/* 2514 */             for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
/* 2515 */               V entryValue = getLiveValue(e);
/* 2516 */               if (entryValue != null)
/*      */               {
/*      */                 
/* 2519 */                 if (this.map.valueEquivalence.equivalent(value, entryValue)) {
/* 2520 */                   return true;
/*      */                 }
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 2526 */         return false;
/*      */       } finally {
/* 2528 */         postReadCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     V put(K key, int hash, V value, boolean onlyIfAbsent) {
/* 2533 */       lock();
/*      */       try {
/* 2535 */         preWriteCleanup();
/*      */         
/* 2537 */         int newCount = this.count + 1;
/* 2538 */         if (newCount > this.threshold) {
/* 2539 */           expand();
/* 2540 */           newCount = this.count + 1;
/*      */         } 
/*      */         
/* 2543 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2544 */         int index = hash & table.length() - 1;
/* 2545 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */ 
/*      */         
/* 2548 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2549 */           K entryKey = e.getKey();
/* 2550 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */ 
/*      */ 
/*      */             
/* 2554 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2555 */             V entryValue = valueReference.get();
/*      */             
/* 2557 */             if (entryValue == null) {
/* 2558 */               this.modCount++;
/* 2559 */               setValue(e, value);
/* 2560 */               if (!valueReference.isComputingReference()) {
/* 2561 */                 enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
/* 2562 */                 newCount = this.count;
/* 2563 */               } else if (evictEntries()) {
/* 2564 */                 newCount = this.count + 1;
/*      */               } 
/* 2566 */               this.count = newCount;
/* 2567 */               return null;
/* 2568 */             }  if (onlyIfAbsent) {
/*      */ 
/*      */ 
/*      */               
/* 2572 */               recordLockedRead(e);
/* 2573 */               return entryValue;
/*      */             } 
/*      */             
/* 2576 */             this.modCount++;
/* 2577 */             enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
/* 2578 */             setValue(e, value);
/* 2579 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2585 */         this.modCount++;
/* 2586 */         CustomConcurrentHashMap.ReferenceEntry<K, V> newEntry = newEntry(key, hash, first);
/* 2587 */         setValue(newEntry, value);
/* 2588 */         table.set(index, newEntry);
/* 2589 */         if (evictEntries()) {
/* 2590 */           newCount = this.count + 1;
/*      */         }
/* 2592 */         this.count = newCount;
/* 2593 */         return null;
/*      */       } finally {
/* 2595 */         unlock();
/* 2596 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     void expand() {
/* 2605 */       AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> oldTable = this.table;
/* 2606 */       int oldCapacity = oldTable.length();
/* 2607 */       if (oldCapacity >= 1073741824) {
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
/* 2621 */       int newCount = this.count;
/* 2622 */       AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> newTable = newEntryArray(oldCapacity << 1);
/* 2623 */       this.threshold = newTable.length() * 3 / 4;
/* 2624 */       int newMask = newTable.length() - 1;
/* 2625 */       for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
/*      */ 
/*      */         
/* 2628 */         CustomConcurrentHashMap.ReferenceEntry<K, V> head = oldTable.get(oldIndex);
/*      */         
/* 2630 */         if (head != null) {
/* 2631 */           CustomConcurrentHashMap.ReferenceEntry<K, V> next = head.getNext();
/* 2632 */           int headIndex = head.getHash() & newMask;
/*      */ 
/*      */           
/* 2635 */           if (next == null) {
/* 2636 */             newTable.set(headIndex, head);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2641 */             CustomConcurrentHashMap.ReferenceEntry<K, V> tail = head;
/* 2642 */             int tailIndex = headIndex; CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2643 */             for (e = next; e != null; e = e.getNext()) {
/* 2644 */               int newIndex = e.getHash() & newMask;
/* 2645 */               if (newIndex != tailIndex) {
/*      */                 
/* 2647 */                 tailIndex = newIndex;
/* 2648 */                 tail = e;
/*      */               } 
/*      */             } 
/* 2651 */             newTable.set(tailIndex, tail);
/*      */ 
/*      */             
/* 2654 */             for (e = head; e != tail; e = e.getNext()) {
/* 2655 */               if (isCollected(e)) {
/* 2656 */                 removeCollectedEntry(e);
/* 2657 */                 newCount--;
/*      */               } else {
/* 2659 */                 int newIndex = e.getHash() & newMask;
/* 2660 */                 CustomConcurrentHashMap.ReferenceEntry<K, V> newNext = newTable.get(newIndex);
/* 2661 */                 CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = copyEntry(e, newNext);
/* 2662 */                 newTable.set(newIndex, newFirst);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2668 */       this.table = newTable;
/* 2669 */       this.count = newCount;
/*      */     }
/*      */     
/*      */     boolean replace(K key, int hash, V oldValue, V newValue) {
/* 2673 */       lock();
/*      */       try {
/* 2675 */         preWriteCleanup();
/*      */         
/* 2677 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2678 */         int index = hash & table.length() - 1;
/* 2679 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 2681 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2682 */           K entryKey = e.getKey();
/* 2683 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */ 
/*      */ 
/*      */             
/* 2687 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2688 */             V entryValue = valueReference.get();
/* 2689 */             if (entryValue == null) {
/* 2690 */               if (isCollected(valueReference)) {
/* 2691 */                 int newCount = this.count - 1;
/* 2692 */                 this.modCount++;
/* 2693 */                 enqueueNotification(entryKey, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
/* 2694 */                 CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2695 */                 newCount = this.count - 1;
/* 2696 */                 table.set(index, newFirst);
/* 2697 */                 this.count = newCount;
/*      */               } 
/* 2699 */               return false;
/*      */             } 
/*      */             
/* 2702 */             if (this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
/* 2703 */               this.modCount++;
/* 2704 */               enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
/* 2705 */               setValue(e, newValue);
/* 2706 */               return true;
/*      */             } 
/*      */ 
/*      */             
/* 2710 */             recordLockedRead(e);
/* 2711 */             return false;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2716 */         return false;
/*      */       } finally {
/* 2718 */         unlock();
/* 2719 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     V replace(K key, int hash, V newValue) {
/* 2724 */       lock();
/*      */       
/* 2726 */       try { preWriteCleanup();
/*      */         
/* 2728 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2729 */         int index = hash & table.length() - 1;
/* 2730 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2732 */         for (e = first; e != null; e = e.getNext()) {
/* 2733 */           K entryKey = e.getKey();
/* 2734 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */ 
/*      */ 
/*      */             
/* 2738 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2739 */             V entryValue = valueReference.get();
/* 2740 */             if (entryValue == null) {
/* 2741 */               if (isCollected(valueReference)) {
/* 2742 */                 int newCount = this.count - 1;
/* 2743 */                 this.modCount++;
/* 2744 */                 enqueueNotification(entryKey, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
/* 2745 */                 CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2746 */                 newCount = this.count - 1;
/* 2747 */                 table.set(index, newFirst);
/* 2748 */                 this.count = newCount;
/*      */               } 
/* 2750 */               return null;
/*      */             } 
/*      */             
/* 2753 */             this.modCount++;
/* 2754 */             enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
/* 2755 */             setValue(e, newValue);
/* 2756 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */         
/* 2760 */         e = null;
/*      */ 
/*      */         
/* 2763 */         return (V)e; } finally { unlock(); postWriteCleanup(); }
/*      */     
/*      */     }
/*      */     
/*      */     V remove(Object key, int hash) {
/* 2768 */       lock();
/*      */       
/* 2770 */       try { preWriteCleanup();
/*      */         
/* 2772 */         int newCount = this.count - 1;
/* 2773 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2774 */         int index = hash & table.length() - 1;
/* 2775 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         CustomConcurrentHashMap.ReferenceEntry<K, V> e;
/* 2777 */         for (e = first; e != null; e = e.getNext()) {
/* 2778 */           K entryKey = e.getKey();
/* 2779 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */             MapMaker.RemovalCause cause;
/* 2781 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2782 */             V entryValue = valueReference.get();
/*      */ 
/*      */             
/* 2785 */             if (entryValue != null) {
/* 2786 */               cause = MapMaker.RemovalCause.EXPLICIT;
/* 2787 */             } else if (isCollected(valueReference)) {
/* 2788 */               cause = MapMaker.RemovalCause.COLLECTED;
/*      */             } else {
/* 2790 */               return null;
/*      */             } 
/*      */             
/* 2793 */             this.modCount++;
/* 2794 */             enqueueNotification(entryKey, hash, entryValue, cause);
/* 2795 */             CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2796 */             newCount = this.count - 1;
/* 2797 */             table.set(index, newFirst);
/* 2798 */             this.count = newCount;
/* 2799 */             return entryValue;
/*      */           } 
/*      */         } 
/*      */         
/* 2803 */         e = null;
/*      */ 
/*      */         
/* 2806 */         return (V)e; } finally { unlock(); postWriteCleanup(); }
/*      */     
/*      */     }
/*      */     
/*      */     boolean remove(Object key, int hash, Object value) {
/* 2811 */       lock();
/*      */       try {
/* 2813 */         preWriteCleanup();
/*      */         
/* 2815 */         int newCount = this.count - 1;
/* 2816 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2817 */         int index = hash & table.length() - 1;
/* 2818 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 2820 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2821 */           K entryKey = e.getKey();
/* 2822 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */             MapMaker.RemovalCause cause;
/* 2824 */             CustomConcurrentHashMap.ValueReference<K, V> valueReference = e.getValueReference();
/* 2825 */             V entryValue = valueReference.get();
/*      */ 
/*      */             
/* 2828 */             if (this.map.valueEquivalence.equivalent(value, entryValue)) {
/* 2829 */               cause = MapMaker.RemovalCause.EXPLICIT;
/* 2830 */             } else if (isCollected(valueReference)) {
/* 2831 */               cause = MapMaker.RemovalCause.COLLECTED;
/*      */             } else {
/* 2833 */               return false;
/*      */             } 
/*      */             
/* 2836 */             this.modCount++;
/* 2837 */             enqueueNotification(entryKey, hash, entryValue, cause);
/* 2838 */             CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2839 */             newCount = this.count - 1;
/* 2840 */             table.set(index, newFirst);
/* 2841 */             this.count = newCount;
/* 2842 */             return (cause == MapMaker.RemovalCause.EXPLICIT);
/*      */           } 
/*      */         } 
/*      */         
/* 2846 */         return false;
/*      */       } finally {
/* 2848 */         unlock();
/* 2849 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     void clear() {
/* 2854 */       if (this.count != 0) {
/* 2855 */         lock();
/*      */         try {
/* 2857 */           AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2858 */           if (this.map.removalNotificationQueue != CustomConcurrentHashMap.DISCARDING_QUEUE) {
/* 2859 */             for (int j = 0; j < table.length(); j++) {
/* 2860 */               for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = table.get(j); e != null; e = e.getNext()) {
/*      */                 
/* 2862 */                 if (!e.getValueReference().isComputingReference()) {
/* 2863 */                   enqueueNotification(e, MapMaker.RemovalCause.EXPLICIT);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           }
/* 2868 */           for (int i = 0; i < table.length(); i++) {
/* 2869 */             table.set(i, null);
/*      */           }
/* 2871 */           clearReferenceQueues();
/* 2872 */           this.evictionQueue.clear();
/* 2873 */           this.expirationQueue.clear();
/* 2874 */           this.readCount.set(0);
/*      */           
/* 2876 */           this.modCount++;
/* 2877 */           this.count = 0;
/*      */         } finally {
/* 2879 */           unlock();
/* 2880 */           postWriteCleanup();
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
/* 2899 */       this.evictionQueue.remove(entry);
/* 2900 */       this.expirationQueue.remove(entry);
/*      */       
/* 2902 */       int newCount = this.count;
/* 2903 */       CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = entry.getNext();
/* 2904 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
/* 2905 */         if (isCollected(e)) {
/* 2906 */           removeCollectedEntry(e);
/* 2907 */           newCount--;
/*      */         } else {
/* 2909 */           newFirst = copyEntry(e, newFirst);
/*      */         } 
/*      */       } 
/* 2912 */       this.count = newCount;
/* 2913 */       return newFirst;
/*      */     }
/*      */     
/*      */     void removeCollectedEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 2917 */       enqueueNotification(entry, MapMaker.RemovalCause.COLLECTED);
/* 2918 */       this.evictionQueue.remove(entry);
/* 2919 */       this.expirationQueue.remove(entry);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean reclaimKey(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, int hash) {
/* 2926 */       lock();
/*      */       try {
/* 2928 */         int newCount = this.count - 1;
/* 2929 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2930 */         int index = hash & table.length() - 1;
/* 2931 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 2933 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2934 */           if (e == entry) {
/* 2935 */             this.modCount++;
/* 2936 */             enqueueNotification(e.getKey(), hash, (V)e.getValueReference().get(), MapMaker.RemovalCause.COLLECTED);
/*      */             
/* 2938 */             CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2939 */             newCount = this.count - 1;
/* 2940 */             table.set(index, newFirst);
/* 2941 */             this.count = newCount;
/* 2942 */             return true;
/*      */           } 
/*      */         } 
/*      */         
/* 2946 */         return false;
/*      */       } finally {
/* 2948 */         unlock();
/* 2949 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean reclaimValue(K key, int hash, CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 2957 */       lock();
/*      */       try {
/* 2959 */         int newCount = this.count - 1;
/* 2960 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2961 */         int index = hash & table.length() - 1;
/* 2962 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 2964 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 2965 */           K entryKey = e.getKey();
/* 2966 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */             
/* 2968 */             CustomConcurrentHashMap.ValueReference<K, V> v = e.getValueReference();
/* 2969 */             if (v == valueReference) {
/* 2970 */               this.modCount++;
/* 2971 */               enqueueNotification(key, hash, valueReference.get(), MapMaker.RemovalCause.COLLECTED);
/* 2972 */               CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 2973 */               newCount = this.count - 1;
/* 2974 */               table.set(index, newFirst);
/* 2975 */               this.count = newCount;
/* 2976 */               return true;
/*      */             } 
/* 2978 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/* 2982 */         return false;
/*      */       } finally {
/* 2984 */         unlock();
/* 2985 */         if (!isHeldByCurrentThread()) {
/* 2986 */           postWriteCleanup();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean clearValue(K key, int hash, CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 2995 */       lock();
/*      */       try {
/* 2997 */         AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 2998 */         int index = hash & table.length() - 1;
/* 2999 */         CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */         
/* 3001 */         for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3002 */           K entryKey = e.getKey();
/* 3003 */           if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
/*      */             
/* 3005 */             CustomConcurrentHashMap.ValueReference<K, V> v = e.getValueReference();
/* 3006 */             if (v == valueReference) {
/* 3007 */               CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 3008 */               table.set(index, newFirst);
/* 3009 */               return true;
/*      */             } 
/* 3011 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/* 3015 */         return false;
/*      */       } finally {
/* 3017 */         unlock();
/* 3018 */         postWriteCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     @GuardedBy("Segment.this")
/*      */     boolean removeEntry(CustomConcurrentHashMap.ReferenceEntry<K, V> entry, int hash, MapMaker.RemovalCause cause) {
/* 3024 */       int newCount = this.count - 1;
/* 3025 */       AtomicReferenceArray<CustomConcurrentHashMap.ReferenceEntry<K, V>> table = this.table;
/* 3026 */       int index = hash & table.length() - 1;
/* 3027 */       CustomConcurrentHashMap.ReferenceEntry<K, V> first = table.get(index);
/*      */       
/* 3029 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
/* 3030 */         if (e == entry) {
/* 3031 */           this.modCount++;
/* 3032 */           enqueueNotification(e.getKey(), hash, (V)e.getValueReference().get(), cause);
/* 3033 */           CustomConcurrentHashMap.ReferenceEntry<K, V> newFirst = removeFromChain(first, e);
/* 3034 */           newCount = this.count - 1;
/* 3035 */           table.set(index, newFirst);
/* 3036 */           this.count = newCount;
/* 3037 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/* 3041 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isCollected(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3049 */       if (entry.getKey() == null) {
/* 3050 */         return true;
/*      */       }
/* 3052 */       return isCollected(entry.getValueReference());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isCollected(CustomConcurrentHashMap.ValueReference<K, V> valueReference) {
/* 3060 */       if (valueReference.isComputingReference()) {
/* 3061 */         return false;
/*      */       }
/* 3063 */       return (valueReference.get() == null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     V getLiveValue(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3071 */       if (entry.getKey() == null) {
/* 3072 */         tryDrainReferenceQueues();
/* 3073 */         return null;
/*      */       } 
/* 3075 */       V value = (V)entry.getValueReference().get();
/* 3076 */       if (value == null) {
/* 3077 */         tryDrainReferenceQueues();
/* 3078 */         return null;
/*      */       } 
/*      */       
/* 3081 */       if (this.map.expires() && this.map.isExpired(entry)) {
/* 3082 */         tryExpireEntries();
/* 3083 */         return null;
/*      */       } 
/* 3085 */       return value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void postReadCleanup() {
/* 3094 */       if ((this.readCount.incrementAndGet() & 0x3F) == 0) {
/* 3095 */         runCleanup();
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
/* 3107 */       runLockedCleanup();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void postWriteCleanup() {
/* 3114 */       runUnlockedCleanup();
/*      */     }
/*      */     
/*      */     void runCleanup() {
/* 3118 */       runLockedCleanup();
/* 3119 */       runUnlockedCleanup();
/*      */     }
/*      */     
/*      */     void runLockedCleanup() {
/* 3123 */       if (tryLock()) {
/*      */         try {
/* 3125 */           drainReferenceQueues();
/* 3126 */           expireEntries();
/* 3127 */           this.readCount.set(0);
/*      */         } finally {
/* 3129 */           unlock();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void runUnlockedCleanup() {
/* 3136 */       if (!isHeldByCurrentThread()) {
/* 3137 */         this.map.processPendingNotifications();
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
/*      */   static final class EvictionQueue<K, V>
/*      */     extends AbstractQueue<ReferenceEntry<K, V>>
/*      */   {
/* 3157 */     final CustomConcurrentHashMap.ReferenceEntry<K, V> head = new CustomConcurrentHashMap.AbstractReferenceEntry<K, V>()
/*      */       {
/* 3159 */         CustomConcurrentHashMap.ReferenceEntry<K, V> nextEvictable = this;
/*      */ 
/*      */         
/*      */         public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextEvictable() {
/* 3163 */           return this.nextEvictable;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setNextEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/* 3168 */           this.nextEvictable = next;
/*      */         }
/*      */         
/* 3171 */         CustomConcurrentHashMap.ReferenceEntry<K, V> previousEvictable = this;
/*      */ 
/*      */         
/*      */         public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousEvictable() {
/* 3175 */           return this.previousEvictable;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setPreviousEvictable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 3180 */           this.previousEvictable = previous;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean offer(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3189 */       CustomConcurrentHashMap.connectEvictables(entry.getPreviousEvictable(), entry.getNextEvictable());
/*      */ 
/*      */       
/* 3192 */       CustomConcurrentHashMap.connectEvictables(this.head.getPreviousEvictable(), entry);
/* 3193 */       CustomConcurrentHashMap.connectEvictables(entry, this.head);
/*      */       
/* 3195 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> peek() {
/* 3200 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = this.head.getNextEvictable();
/* 3201 */       return (next == this.head) ? null : next;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> poll() {
/* 3206 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = this.head.getNextEvictable();
/* 3207 */       if (next == this.head) {
/* 3208 */         return null;
/*      */       }
/*      */       
/* 3211 */       remove(next);
/* 3212 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 3218 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = (CustomConcurrentHashMap.ReferenceEntry<K, V>)o;
/* 3219 */       CustomConcurrentHashMap.ReferenceEntry<K, V> previous = e.getPreviousEvictable();
/* 3220 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = e.getNextEvictable();
/* 3221 */       CustomConcurrentHashMap.connectEvictables(previous, next);
/* 3222 */       CustomConcurrentHashMap.nullifyEvictable(e);
/*      */       
/* 3224 */       return (next != CustomConcurrentHashMap.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3230 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = (CustomConcurrentHashMap.ReferenceEntry<K, V>)o;
/* 3231 */       return (e.getNextEvictable() != CustomConcurrentHashMap.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3236 */       return (this.head.getNextEvictable() == this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3241 */       int size = 0;
/* 3242 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.head.getNextEvictable(); e != this.head; e = e.getNextEvictable()) {
/* 3243 */         size++;
/*      */       }
/* 3245 */       return size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3250 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.head.getNextEvictable();
/* 3251 */       while (e != this.head) {
/* 3252 */         CustomConcurrentHashMap.ReferenceEntry<K, V> next = e.getNextEvictable();
/* 3253 */         CustomConcurrentHashMap.nullifyEvictable(e);
/* 3254 */         e = next;
/*      */       } 
/*      */       
/* 3257 */       this.head.setNextEvictable(this.head);
/* 3258 */       this.head.setPreviousEvictable(this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<CustomConcurrentHashMap.ReferenceEntry<K, V>> iterator() {
/* 3263 */       return new AbstractLinkedIterator<CustomConcurrentHashMap.ReferenceEntry<K, V>>(peek())
/*      */         {
/*      */           protected CustomConcurrentHashMap.ReferenceEntry<K, V> computeNext(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 3266 */             CustomConcurrentHashMap.ReferenceEntry<K, V> next = previous.getNextEvictable();
/* 3267 */             return (next == CustomConcurrentHashMap.EvictionQueue.this.head) ? null : next;
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
/* 3285 */     final CustomConcurrentHashMap.ReferenceEntry<K, V> head = new CustomConcurrentHashMap.AbstractReferenceEntry<K, V>()
/*      */       {
/*      */         public long getExpirationTime()
/*      */         {
/* 3289 */           return Long.MAX_VALUE;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setExpirationTime(long time) {}
/*      */         
/* 3295 */         CustomConcurrentHashMap.ReferenceEntry<K, V> nextExpirable = this;
/*      */ 
/*      */         
/*      */         public CustomConcurrentHashMap.ReferenceEntry<K, V> getNextExpirable() {
/* 3299 */           return this.nextExpirable;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setNextExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> next) {
/* 3304 */           this.nextExpirable = next;
/*      */         }
/*      */         
/* 3307 */         CustomConcurrentHashMap.ReferenceEntry<K, V> previousExpirable = this;
/*      */ 
/*      */         
/*      */         public CustomConcurrentHashMap.ReferenceEntry<K, V> getPreviousExpirable() {
/* 3311 */           return this.previousExpirable;
/*      */         }
/*      */ 
/*      */         
/*      */         public void setPreviousExpirable(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 3316 */           this.previousExpirable = previous;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean offer(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/* 3325 */       CustomConcurrentHashMap.connectExpirables(entry.getPreviousExpirable(), entry.getNextExpirable());
/*      */ 
/*      */       
/* 3328 */       CustomConcurrentHashMap.connectExpirables(this.head.getPreviousExpirable(), entry);
/* 3329 */       CustomConcurrentHashMap.connectExpirables(entry, this.head);
/*      */       
/* 3331 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> peek() {
/* 3336 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = this.head.getNextExpirable();
/* 3337 */       return (next == this.head) ? null : next;
/*      */     }
/*      */ 
/*      */     
/*      */     public CustomConcurrentHashMap.ReferenceEntry<K, V> poll() {
/* 3342 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = this.head.getNextExpirable();
/* 3343 */       if (next == this.head) {
/* 3344 */         return null;
/*      */       }
/*      */       
/* 3347 */       remove(next);
/* 3348 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 3354 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = (CustomConcurrentHashMap.ReferenceEntry<K, V>)o;
/* 3355 */       CustomConcurrentHashMap.ReferenceEntry<K, V> previous = e.getPreviousExpirable();
/* 3356 */       CustomConcurrentHashMap.ReferenceEntry<K, V> next = e.getNextExpirable();
/* 3357 */       CustomConcurrentHashMap.connectExpirables(previous, next);
/* 3358 */       CustomConcurrentHashMap.nullifyExpirable(e);
/*      */       
/* 3360 */       return (next != CustomConcurrentHashMap.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3366 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = (CustomConcurrentHashMap.ReferenceEntry<K, V>)o;
/* 3367 */       return (e.getNextExpirable() != CustomConcurrentHashMap.NullEntry.INSTANCE);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3372 */       return (this.head.getNextExpirable() == this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3377 */       int size = 0;
/* 3378 */       for (CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.head.getNextExpirable(); e != this.head; e = e.getNextExpirable()) {
/* 3379 */         size++;
/*      */       }
/* 3381 */       return size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3386 */       CustomConcurrentHashMap.ReferenceEntry<K, V> e = this.head.getNextExpirable();
/* 3387 */       while (e != this.head) {
/* 3388 */         CustomConcurrentHashMap.ReferenceEntry<K, V> next = e.getNextExpirable();
/* 3389 */         CustomConcurrentHashMap.nullifyExpirable(e);
/* 3390 */         e = next;
/*      */       } 
/*      */       
/* 3393 */       this.head.setNextExpirable(this.head);
/* 3394 */       this.head.setPreviousExpirable(this.head);
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<CustomConcurrentHashMap.ReferenceEntry<K, V>> iterator() {
/* 3399 */       return new AbstractLinkedIterator<CustomConcurrentHashMap.ReferenceEntry<K, V>>(peek())
/*      */         {
/*      */           protected CustomConcurrentHashMap.ReferenceEntry<K, V> computeNext(CustomConcurrentHashMap.ReferenceEntry<K, V> previous) {
/* 3402 */             CustomConcurrentHashMap.ReferenceEntry<K, V> next = previous.getNextExpirable();
/* 3403 */             return (next == CustomConcurrentHashMap.ExpirationQueue.this.head) ? null : next;
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */   
/*      */   static final class CleanupMapTask implements Runnable {
/*      */     final WeakReference<CustomConcurrentHashMap<?, ?>> mapReference;
/*      */     
/*      */     public CleanupMapTask(CustomConcurrentHashMap<?, ?> map) {
/* 3413 */       this.mapReference = new WeakReference<CustomConcurrentHashMap<?, ?>>(map);
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/* 3418 */       CustomConcurrentHashMap<?, ?> map = this.mapReference.get();
/* 3419 */       if (map == null) {
/* 3420 */         throw new CancellationException();
/*      */       }
/*      */       
/* 3423 */       for (CustomConcurrentHashMap.Segment<?, ?> segment : map.segments) {
/* 3424 */         segment.runCleanup();
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
/*      */   public boolean isEmpty() {
/* 3440 */     long sum = 0L;
/* 3441 */     Segment<K, V>[] segments = this.segments; int i;
/* 3442 */     for (i = 0; i < segments.length; i++) {
/* 3443 */       if ((segments[i]).count != 0) {
/* 3444 */         return false;
/*      */       }
/* 3446 */       sum += (segments[i]).modCount;
/*      */     } 
/*      */     
/* 3449 */     if (sum != 0L) {
/* 3450 */       for (i = 0; i < segments.length; i++) {
/* 3451 */         if ((segments[i]).count != 0) {
/* 3452 */           return false;
/*      */         }
/* 3454 */         sum -= (segments[i]).modCount;
/*      */       } 
/* 3456 */       if (sum != 0L) {
/* 3457 */         return false;
/*      */       }
/*      */     } 
/* 3460 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/* 3465 */     Segment<K, V>[] segments = this.segments;
/* 3466 */     long sum = 0L;
/* 3467 */     for (int i = 0; i < segments.length; i++) {
/* 3468 */       sum += (segments[i]).count;
/*      */     }
/* 3470 */     return Ints.saturatedCast(sum);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(@Nullable Object key) {
/* 3475 */     if (key == null) {
/* 3476 */       return null;
/*      */     }
/* 3478 */     int hash = hash(key);
/* 3479 */     return segmentFor(hash).get(key, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ReferenceEntry<K, V> getEntry(@Nullable Object key) {
/* 3487 */     if (key == null) {
/* 3488 */       return null;
/*      */     }
/* 3490 */     int hash = hash(key);
/* 3491 */     return segmentFor(hash).getEntry(key, hash);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ReferenceEntry<K, V> getLiveEntry(@Nullable Object key) {
/* 3498 */     if (key == null) {
/* 3499 */       return null;
/*      */     }
/* 3501 */     int hash = hash(key);
/* 3502 */     return segmentFor(hash).getLiveEntry(key, hash);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(@Nullable Object key) {
/* 3507 */     if (key == null) {
/* 3508 */       return false;
/*      */     }
/* 3510 */     int hash = hash(key);
/* 3511 */     return segmentFor(hash).containsKey(key, hash);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(@Nullable Object value) {
/* 3516 */     if (value == null) {
/* 3517 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3525 */     Segment<K, V>[] segments = this.segments;
/* 3526 */     long last = -1L;
/* 3527 */     for (int i = 0; i < 3; i++) {
/* 3528 */       long sum = 0L;
/* 3529 */       for (Segment<K, V> segment : segments) {
/*      */ 
/*      */         
/* 3532 */         int c = segment.count;
/*      */         
/* 3534 */         AtomicReferenceArray<ReferenceEntry<K, V>> table = segment.table;
/* 3535 */         for (int j = 0; j < table.length(); j++) {
/* 3536 */           for (ReferenceEntry<K, V> e = table.get(j); e != null; e = e.getNext()) {
/* 3537 */             V v = segment.getLiveValue(e);
/* 3538 */             if (v != null && this.valueEquivalence.equivalent(value, v)) {
/* 3539 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/* 3543 */         sum += segment.modCount;
/*      */       } 
/* 3545 */       if (sum == last) {
/*      */         break;
/*      */       }
/* 3548 */       last = sum;
/*      */     } 
/* 3550 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K key, V value) {
/* 3555 */     Preconditions.checkNotNull(key);
/* 3556 */     Preconditions.checkNotNull(value);
/* 3557 */     int hash = hash(key);
/* 3558 */     return segmentFor(hash).put(key, hash, value, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(K key, V value) {
/* 3563 */     Preconditions.checkNotNull(key);
/* 3564 */     Preconditions.checkNotNull(value);
/* 3565 */     int hash = hash(key);
/* 3566 */     return segmentFor(hash).put(key, hash, value, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/* 3571 */     for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
/* 3572 */       put(e.getKey(), e.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(@Nullable Object key) {
/* 3578 */     if (key == null) {
/* 3579 */       return null;
/*      */     }
/* 3581 */     int hash = hash(key);
/* 3582 */     return segmentFor(hash).remove(key, hash);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(@Nullable Object key, @Nullable Object value) {
/* 3587 */     if (key == null || value == null) {
/* 3588 */       return false;
/*      */     }
/* 3590 */     int hash = hash(key);
/* 3591 */     return segmentFor(hash).remove(key, hash, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K key, @Nullable V oldValue, V newValue) {
/* 3596 */     Preconditions.checkNotNull(key);
/* 3597 */     Preconditions.checkNotNull(newValue);
/* 3598 */     if (oldValue == null) {
/* 3599 */       return false;
/*      */     }
/* 3601 */     int hash = hash(key);
/* 3602 */     return segmentFor(hash).replace(key, hash, oldValue, newValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(K key, V value) {
/* 3607 */     Preconditions.checkNotNull(key);
/* 3608 */     Preconditions.checkNotNull(value);
/* 3609 */     int hash = hash(key);
/* 3610 */     return segmentFor(hash).replace(key, hash, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/* 3615 */     for (Segment<K, V> segment : this.segments) {
/* 3616 */       segment.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<K> keySet() {
/* 3624 */     Set<K> ks = this.keySet;
/* 3625 */     return (ks != null) ? ks : (this.keySet = new KeySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<V> values() {
/* 3632 */     Collection<V> vs = this.values;
/* 3633 */     return (vs != null) ? vs : (this.values = new Values());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Map.Entry<K, V>> entrySet() {
/* 3640 */     Set<Map.Entry<K, V>> es = this.entrySet;
/* 3641 */     return (es != null) ? es : (this.entrySet = new EntrySet());
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
/* 3657 */       this.nextSegmentIndex = CustomConcurrentHashMap.this.segments.length - 1;
/* 3658 */       this.nextTableIndex = -1;
/* 3659 */       advance();
/*      */     }
/*      */     
/*      */     final void advance() {
/* 3663 */       this.nextExternal = null;
/*      */       
/* 3665 */       if (nextInChain()) {
/*      */         return;
/*      */       }
/*      */       
/* 3669 */       if (nextInTable()) {
/*      */         return;
/*      */       }
/*      */       
/* 3673 */       while (this.nextSegmentIndex >= 0) {
/* 3674 */         this.currentSegment = CustomConcurrentHashMap.this.segments[this.nextSegmentIndex--];
/* 3675 */         if (this.currentSegment.count != 0) {
/* 3676 */           this.currentTable = this.currentSegment.table;
/* 3677 */           this.nextTableIndex = this.currentTable.length() - 1;
/* 3678 */           if (nextInTable()) {
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
/* 3689 */       if (this.nextEntry != null) {
/* 3690 */         for (this.nextEntry = this.nextEntry.getNext(); this.nextEntry != null; this.nextEntry = this.nextEntry.getNext()) {
/* 3691 */           if (advanceTo(this.nextEntry)) {
/* 3692 */             return true;
/*      */           }
/*      */         } 
/*      */       }
/* 3696 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean nextInTable() {
/* 3703 */       while (this.nextTableIndex >= 0) {
/* 3704 */         if ((this.nextEntry = this.currentTable.get(this.nextTableIndex--)) != null && (
/* 3705 */           advanceTo(this.nextEntry) || nextInChain())) {
/* 3706 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 3710 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean advanceTo(CustomConcurrentHashMap.ReferenceEntry<K, V> entry) {
/*      */       try {
/* 3719 */         K key = entry.getKey();
/* 3720 */         V value = CustomConcurrentHashMap.this.getLiveValue(entry);
/* 3721 */         if (value != null) {
/* 3722 */           this.nextExternal = new CustomConcurrentHashMap.WriteThroughEntry(key, value);
/* 3723 */           return true;
/*      */         } 
/*      */         
/* 3726 */         return false;
/*      */       } finally {
/*      */         
/* 3729 */         this.currentSegment.postReadCleanup();
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 3734 */       return (this.nextExternal != null);
/*      */     }
/*      */     
/*      */     CustomConcurrentHashMap<K, V>.WriteThroughEntry nextEntry() {
/* 3738 */       if (this.nextExternal == null) {
/* 3739 */         throw new NoSuchElementException();
/*      */       }
/* 3741 */       this.lastReturned = this.nextExternal;
/* 3742 */       advance();
/* 3743 */       return this.lastReturned;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 3747 */       Preconditions.checkState((this.lastReturned != null));
/* 3748 */       CustomConcurrentHashMap.this.remove(this.lastReturned.getKey());
/* 3749 */       this.lastReturned = null;
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeyIterator
/*      */     extends HashIterator
/*      */     implements Iterator<K> {
/*      */     public K next() {
/* 3757 */       return nextEntry().getKey();
/*      */     }
/*      */   }
/*      */   
/*      */   final class ValueIterator
/*      */     extends HashIterator
/*      */     implements Iterator<V> {
/*      */     public V next() {
/* 3765 */       return nextEntry().getValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final class WriteThroughEntry
/*      */     extends AbstractMapEntry<K, V>
/*      */   {
/*      */     final K key;
/*      */     
/*      */     V value;
/*      */     
/*      */     WriteThroughEntry(K key, V value) {
/* 3778 */       this.key = key;
/* 3779 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public K getKey() {
/* 3784 */       return this.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/* 3789 */       return this.value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object object) {
/* 3795 */       if (object instanceof Map.Entry) {
/* 3796 */         Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
/* 3797 */         return (this.key.equals(that.getKey()) && this.value.equals(that.getValue()));
/*      */       } 
/* 3799 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 3805 */       return this.key.hashCode() ^ this.value.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V newValue) {
/* 3810 */       V oldValue = CustomConcurrentHashMap.this.put(this.key, newValue);
/* 3811 */       this.value = newValue;
/* 3812 */       return oldValue;
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntryIterator
/*      */     extends HashIterator
/*      */     implements Iterator<Map.Entry<K, V>> {
/*      */     public Map.Entry<K, V> next() {
/* 3820 */       return nextEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   final class KeySet
/*      */     extends AbstractSet<K>
/*      */   {
/*      */     public Iterator<K> iterator() {
/* 3828 */       return new CustomConcurrentHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3833 */       return CustomConcurrentHashMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3838 */       return CustomConcurrentHashMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3843 */       return CustomConcurrentHashMap.this.containsKey(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 3848 */       return (CustomConcurrentHashMap.this.remove(o) != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3853 */       CustomConcurrentHashMap.this.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   final class Values
/*      */     extends AbstractCollection<V>
/*      */   {
/*      */     public Iterator<V> iterator() {
/* 3861 */       return new CustomConcurrentHashMap.ValueIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3866 */       return CustomConcurrentHashMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3871 */       return CustomConcurrentHashMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3876 */       return CustomConcurrentHashMap.this.containsValue(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3881 */       CustomConcurrentHashMap.this.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   final class EntrySet
/*      */     extends AbstractSet<Map.Entry<K, V>>
/*      */   {
/*      */     public Iterator<Map.Entry<K, V>> iterator() {
/* 3889 */       return new CustomConcurrentHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 3894 */       if (!(o instanceof Map.Entry)) {
/* 3895 */         return false;
/*      */       }
/* 3897 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 3898 */       Object key = e.getKey();
/* 3899 */       if (key == null) {
/* 3900 */         return false;
/*      */       }
/* 3902 */       V v = (V)CustomConcurrentHashMap.this.get(key);
/*      */       
/* 3904 */       return (v != null && CustomConcurrentHashMap.this.valueEquivalence.equivalent(e.getValue(), v));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 3909 */       if (!(o instanceof Map.Entry)) {
/* 3910 */         return false;
/*      */       }
/* 3912 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 3913 */       Object key = e.getKey();
/* 3914 */       return (key != null && CustomConcurrentHashMap.this.remove(key, e.getValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 3919 */       return CustomConcurrentHashMap.this.size();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3924 */       return CustomConcurrentHashMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 3929 */       CustomConcurrentHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object writeReplace() {
/* 3938 */     return new SerializationProxy<K, V>(this.keyStrength, this.valueStrength, this.keyEquivalence, this.valueEquivalence, this.expireAfterWriteNanos, this.expireAfterAccessNanos, this.maximumSize, this.concurrencyLevel, this.removalListener, this);
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class AbstractSerializationProxy<K, V>
/*      */     extends ForwardingConcurrentMap<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 3L;
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
/*      */     final MapMaker.RemovalListener<? super K, ? super V> removalListener;
/*      */     transient ConcurrentMap<K, V> delegate;
/*      */     
/*      */     AbstractSerializationProxy(CustomConcurrentHashMap.Strength keyStrength, CustomConcurrentHashMap.Strength valueStrength, Equivalence<Object> keyEquivalence, Equivalence<Object> valueEquivalence, long expireAfterWriteNanos, long expireAfterAccessNanos, int maximumSize, int concurrencyLevel, MapMaker.RemovalListener<? super K, ? super V> removalListener, ConcurrentMap<K, V> delegate) {
/* 3968 */       this.keyStrength = keyStrength;
/* 3969 */       this.valueStrength = valueStrength;
/* 3970 */       this.keyEquivalence = keyEquivalence;
/* 3971 */       this.valueEquivalence = valueEquivalence;
/* 3972 */       this.expireAfterWriteNanos = expireAfterWriteNanos;
/* 3973 */       this.expireAfterAccessNanos = expireAfterAccessNanos;
/* 3974 */       this.maximumSize = maximumSize;
/* 3975 */       this.concurrencyLevel = concurrencyLevel;
/* 3976 */       this.removalListener = removalListener;
/* 3977 */       this.delegate = delegate;
/*      */     }
/*      */ 
/*      */     
/*      */     protected ConcurrentMap<K, V> delegate() {
/* 3982 */       return this.delegate;
/*      */     }
/*      */     
/*      */     void writeMapTo(ObjectOutputStream out) throws IOException {
/* 3986 */       out.writeInt(this.delegate.size());
/* 3987 */       for (Map.Entry<K, V> entry : this.delegate.entrySet()) {
/* 3988 */         out.writeObject(entry.getKey());
/* 3989 */         out.writeObject(entry.getValue());
/*      */       } 
/* 3991 */       out.writeObject(null);
/*      */     }
/*      */ 
/*      */     
/*      */     MapMaker readMapMaker(ObjectInputStream in) throws IOException {
/* 3996 */       int size = in.readInt();
/* 3997 */       MapMaker mapMaker = (new MapMaker()).initialCapacity(size).setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4004 */       mapMaker.removalListener(this.removalListener);
/* 4005 */       if (this.expireAfterWriteNanos > 0L) {
/* 4006 */         mapMaker.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
/*      */       }
/* 4008 */       if (this.expireAfterAccessNanos > 0L) {
/* 4009 */         mapMaker.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
/*      */       }
/* 4011 */       if (this.maximumSize != -1) {
/* 4012 */         mapMaker.maximumSize(this.maximumSize);
/*      */       }
/* 4014 */       return mapMaker;
/*      */     }
/*      */ 
/*      */     
/*      */     void readEntries(ObjectInputStream in) throws IOException, ClassNotFoundException {
/*      */       while (true) {
/* 4020 */         K key = (K)in.readObject();
/* 4021 */         if (key == null) {
/*      */           break;
/*      */         }
/* 4024 */         V value = (V)in.readObject();
/* 4025 */         this.delegate.put(key, value);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class SerializationProxy<K, V>
/*      */     extends AbstractSerializationProxy<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = 3L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SerializationProxy(CustomConcurrentHashMap.Strength keyStrength, CustomConcurrentHashMap.Strength valueStrength, Equivalence<Object> keyEquivalence, Equivalence<Object> valueEquivalence, long expireAfterWriteNanos, long expireAfterAccessNanos, int maximumSize, int concurrencyLevel, MapMaker.RemovalListener<? super K, ? super V> removalListener, ConcurrentMap<K, V> delegate) {
/* 4042 */       super(keyStrength, valueStrength, keyEquivalence, valueEquivalence, expireAfterWriteNanos, expireAfterAccessNanos, maximumSize, concurrencyLevel, removalListener, delegate);
/*      */     }
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream out) throws IOException {
/* 4047 */       out.defaultWriteObject();
/* 4048 */       writeMapTo(out);
/*      */     }
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 4052 */       in.defaultReadObject();
/* 4053 */       MapMaker mapMaker = readMapMaker(in);
/* 4054 */       this.delegate = mapMaker.makeMap();
/* 4055 */       readEntries(in);
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 4059 */       return this.delegate;
/*      */     }
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
/*      */     void clear(@Nullable ValueReference<K, V> param1ValueReference);
/*      */     
/*      */     boolean isComputingReference();
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\CustomConcurrentHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */