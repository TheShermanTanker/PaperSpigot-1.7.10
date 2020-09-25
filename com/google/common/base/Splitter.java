/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.annotations.GwtIncompatible;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.CheckReturnValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible(emulated = true)
/*     */ public final class Splitter
/*     */ {
/*     */   private final CharMatcher trimmer;
/*     */   private final boolean omitEmptyStrings;
/*     */   private final Strategy strategy;
/*     */   private final int limit;
/*     */   
/*     */   private Splitter(Strategy strategy) {
/* 107 */     this(strategy, false, CharMatcher.NONE, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   private Splitter(Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer, int limit) {
/* 112 */     this.strategy = strategy;
/* 113 */     this.omitEmptyStrings = omitEmptyStrings;
/* 114 */     this.trimmer = trimmer;
/* 115 */     this.limit = limit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Splitter on(char separator) {
/* 127 */     return on(CharMatcher.is(separator));
/*     */   }
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
/*     */   public static Splitter on(final CharMatcher separatorMatcher) {
/* 141 */     Preconditions.checkNotNull(separatorMatcher);
/*     */     
/* 143 */     return new Splitter(new Strategy()
/*     */         {
/*     */           public Splitter.SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
/* 146 */             return new Splitter.SplittingIterator(splitter, toSplit) {
/*     */                 int separatorStart(int start) {
/* 148 */                   return separatorMatcher.indexIn(this.toSplit, start);
/*     */                 }
/*     */                 
/*     */                 int separatorEnd(int separatorPosition) {
/* 152 */                   return separatorPosition + 1;
/*     */                 }
/*     */               };
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Splitter on(final String separator) {
/* 168 */     Preconditions.checkArgument((separator.length() != 0), "The separator may not be the empty string.");
/*     */ 
/*     */     
/* 171 */     return new Splitter(new Strategy()
/*     */         {
/*     */           public Splitter.SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
/* 174 */             return new Splitter.SplittingIterator(splitter, toSplit) {
/*     */                 public int separatorStart(int start) {
/* 176 */                   int delimeterLength = separator.length();
/*     */ 
/*     */                   
/* 179 */                   int p = start, last = this.toSplit.length() - delimeterLength;
/* 180 */                   for (; p <= last; p++) {
/* 181 */                     int i = 0; while (true) { if (i < delimeterLength) {
/* 182 */                         if (this.toSplit.charAt(i + p) != separator.charAt(i))
/*     */                           break;  i++;
/*     */                         continue;
/*     */                       } 
/* 186 */                       return p; }
/*     */                   
/* 188 */                   }  return -1;
/*     */                 }
/*     */                 
/*     */                 public int separatorEnd(int separatorPosition) {
/* 192 */                   return separatorPosition + separator.length();
/*     */                 }
/*     */               };
/*     */           }
/*     */         });
/*     */   }
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
/*     */   @GwtIncompatible("java.util.regex")
/*     */   public static Splitter on(final Pattern separatorPattern) {
/* 213 */     Preconditions.checkNotNull(separatorPattern);
/* 214 */     Preconditions.checkArgument(!separatorPattern.matcher("").matches(), "The pattern may not match the empty string: %s", new Object[] { separatorPattern });
/*     */ 
/*     */     
/* 217 */     return new Splitter(new Strategy()
/*     */         {
/*     */           public Splitter.SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
/* 220 */             final Matcher matcher = separatorPattern.matcher(toSplit);
/* 221 */             return new Splitter.SplittingIterator(splitter, toSplit) {
/*     */                 public int separatorStart(int start) {
/* 223 */                   return matcher.find(start) ? matcher.start() : -1;
/*     */                 }
/*     */                 
/*     */                 public int separatorEnd(int separatorPosition) {
/* 227 */                   return matcher.end();
/*     */                 }
/*     */               };
/*     */           }
/*     */         });
/*     */   }
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
/*     */   @GwtIncompatible("java.util.regex")
/*     */   public static Splitter onPattern(String separatorPattern) {
/* 251 */     return on(Pattern.compile(separatorPattern));
/*     */   }
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
/*     */   public static Splitter fixedLength(final int length) {
/* 265 */     Preconditions.checkArgument((length > 0), "The length may not be less than 1");
/*     */     
/* 267 */     return new Splitter(new Strategy()
/*     */         {
/*     */           public Splitter.SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
/* 270 */             return new Splitter.SplittingIterator(splitter, toSplit) {
/*     */                 public int separatorStart(int start) {
/* 272 */                   int nextChunkStart = start + length;
/* 273 */                   return (nextChunkStart < this.toSplit.length()) ? nextChunkStart : -1;
/*     */                 }
/*     */                 
/*     */                 public int separatorEnd(int separatorPosition) {
/* 277 */                   return separatorPosition;
/*     */                 }
/*     */               };
/*     */           }
/*     */         });
/*     */   }
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
/*     */   @CheckReturnValue
/*     */   public Splitter omitEmptyStrings() {
/* 304 */     return new Splitter(this.strategy, true, this.trimmer, this.limit);
/*     */   }
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
/*     */   @CheckReturnValue
/*     */   public Splitter limit(int limit) {
/* 328 */     Preconditions.checkArgument((limit > 0), "must be greater then zero: %s", new Object[] { Integer.valueOf(limit) });
/* 329 */     return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, limit);
/*     */   }
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
/*     */   @CheckReturnValue
/*     */   public Splitter trimResults() {
/* 344 */     return trimResults(CharMatcher.WHITESPACE);
/*     */   }
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
/*     */   @CheckReturnValue
/*     */   public Splitter trimResults(CharMatcher trimmer) {
/* 361 */     Preconditions.checkNotNull(trimmer);
/* 362 */     return new Splitter(this.strategy, this.omitEmptyStrings, trimmer, this.limit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<String> split(final CharSequence sequence) {
/* 373 */     Preconditions.checkNotNull(sequence);
/*     */     
/* 375 */     return new Iterable<String>() {
/*     */         public Iterator<String> iterator() {
/* 377 */           return Splitter.this.spliterator(sequence);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private Iterator<String> spliterator(CharSequence sequence) {
/* 383 */     return this.strategy.iterator(this, sequence);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CheckReturnValue
/*     */   @Beta
/*     */   public MapSplitter withKeyValueSeparator(String separator) {
/* 395 */     return withKeyValueSeparator(on(separator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CheckReturnValue
/*     */   @Beta
/*     */   public MapSplitter withKeyValueSeparator(Splitter keyValueSplitter) {
/* 408 */     return new MapSplitter(this, keyValueSplitter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   public static final class MapSplitter
/*     */   {
/*     */     private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
/*     */ 
/*     */     
/*     */     private final Splitter outerSplitter;
/*     */ 
/*     */     
/*     */     private final Splitter entrySplitter;
/*     */ 
/*     */     
/*     */     private MapSplitter(Splitter outerSplitter, Splitter entrySplitter) {
/* 426 */       this.outerSplitter = outerSplitter;
/* 427 */       this.entrySplitter = Preconditions.<Splitter>checkNotNull(entrySplitter);
/*     */     }
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
/*     */     public Map<String, String> split(CharSequence sequence) {
/* 446 */       Map<String, String> map = new LinkedHashMap<String, String>();
/* 447 */       for (String entry : this.outerSplitter.split(sequence)) {
/* 448 */         Iterator<String> entryFields = this.entrySplitter.spliterator(entry);
/*     */         
/* 450 */         Preconditions.checkArgument(entryFields.hasNext(), "Chunk [%s] is not a valid entry", new Object[] { entry });
/* 451 */         String key = entryFields.next();
/* 452 */         Preconditions.checkArgument(!map.containsKey(key), "Duplicate key [%s] found.", new Object[] { key });
/*     */         
/* 454 */         Preconditions.checkArgument(entryFields.hasNext(), "Chunk [%s] is not a valid entry", new Object[] { entry });
/* 455 */         String value = entryFields.next();
/* 456 */         map.put(key, value);
/*     */         
/* 458 */         Preconditions.checkArgument(!entryFields.hasNext(), "Chunk [%s] is not a valid entry", new Object[] { entry });
/*     */       } 
/* 460 */       return Collections.unmodifiableMap(map);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class SplittingIterator
/*     */     extends AbstractIterator<String>
/*     */   {
/*     */     final CharSequence toSplit;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final CharMatcher trimmer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final boolean omitEmptyStrings;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 487 */     int offset = 0;
/*     */     int limit;
/*     */     
/*     */     protected SplittingIterator(Splitter splitter, CharSequence toSplit) {
/* 491 */       this.trimmer = splitter.trimmer;
/* 492 */       this.omitEmptyStrings = splitter.omitEmptyStrings;
/* 493 */       this.limit = splitter.limit;
/* 494 */       this.toSplit = toSplit;
/*     */     }
/*     */     
/*     */     protected String computeNext() {
/* 498 */       while (this.offset != -1) {
/* 499 */         int end, start = this.offset;
/*     */ 
/*     */         
/* 502 */         int separatorPosition = separatorStart(this.offset);
/* 503 */         if (separatorPosition == -1) {
/* 504 */           end = this.toSplit.length();
/* 505 */           this.offset = -1;
/*     */         } else {
/* 507 */           end = separatorPosition;
/* 508 */           this.offset = separatorEnd(separatorPosition);
/*     */         } 
/*     */         
/* 511 */         while (start < end && this.trimmer.matches(this.toSplit.charAt(start))) {
/* 512 */           start++;
/*     */         }
/* 514 */         while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
/* 515 */           end--;
/*     */         }
/*     */         
/* 518 */         if (this.omitEmptyStrings && start == end) {
/*     */           continue;
/*     */         }
/*     */         
/* 522 */         if (this.limit == 1) {
/*     */ 
/*     */ 
/*     */           
/* 526 */           end = this.toSplit.length();
/* 527 */           this.offset = -1;
/*     */           
/* 529 */           while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
/* 530 */             end--;
/*     */           }
/*     */         } else {
/* 533 */           this.limit--;
/*     */         } 
/*     */         
/* 536 */         return this.toSplit.subSequence(start, end).toString();
/*     */       } 
/* 538 */       return endOfData();
/*     */     }
/*     */     
/*     */     abstract int separatorStart(int param1Int);
/*     */     
/*     */     abstract int separatorEnd(int param1Int);
/*     */   }
/*     */   
/*     */   private static abstract class AbstractIterator<T> implements Iterator<T> {
/* 547 */     State state = State.NOT_READY;
/*     */     T next;
/*     */     
/* 550 */     enum State { READY, NOT_READY, DONE, FAILED; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final T endOfData() {
/* 558 */       this.state = State.DONE;
/* 559 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean hasNext() {
/* 564 */       Preconditions.checkState((this.state != State.FAILED));
/* 565 */       switch (this.state) {
/*     */         case DONE:
/* 567 */           return false;
/*     */         case READY:
/* 569 */           return true;
/*     */       } 
/*     */       
/* 572 */       return tryToComputeNext();
/*     */     }
/*     */     
/*     */     boolean tryToComputeNext() {
/* 576 */       this.state = State.FAILED;
/* 577 */       this.next = computeNext();
/* 578 */       if (this.state != State.DONE) {
/* 579 */         this.state = State.READY;
/* 580 */         return true;
/*     */       } 
/* 582 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public final T next() {
/* 587 */       if (!hasNext()) {
/* 588 */         throw new NoSuchElementException();
/*     */       }
/* 590 */       this.state = State.NOT_READY;
/* 591 */       return this.next;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 595 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     private AbstractIterator() {}
/*     */     
/*     */     protected abstract T computeNext();
/*     */   }
/*     */   
/*     */   private static interface Strategy {
/*     */     Iterator<String> iterator(Splitter param1Splitter, CharSequence param1CharSequence);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Splitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */