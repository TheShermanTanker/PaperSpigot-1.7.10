/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.primitives.Booleans;
/*     */ import java.io.Serializable;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.annotation.Nullable;
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
/*     */ @GwtCompatible
/*     */ abstract class Cut<C extends Comparable>
/*     */   implements Comparable<Cut<C>>, Serializable
/*     */ {
/*     */   final C endpoint;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   Cut(@Nullable C endpoint) {
/*  41 */     this.endpoint = endpoint;
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
/*     */   Cut<C> canonical(DiscreteDomain<C> domain) {
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Cut<C> that) {
/*  69 */     if (that == belowAll()) {
/*  70 */       return 1;
/*     */     }
/*  72 */     if (that == aboveAll()) {
/*  73 */       return -1;
/*     */     }
/*  75 */     int result = Range.compareOrThrow((Comparable)this.endpoint, (Comparable)that.endpoint);
/*  76 */     if (result != 0) {
/*  77 */       return result;
/*     */     }
/*     */     
/*  80 */     return Booleans.compare(this instanceof AboveValue, that instanceof AboveValue);
/*     */   }
/*     */ 
/*     */   
/*     */   C endpoint() {
/*  85 */     return this.endpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  90 */     if (obj instanceof Cut) {
/*     */       
/*  92 */       Cut<C> that = (Cut<C>)obj;
/*     */       try {
/*  94 */         int compareResult = compareTo(that);
/*  95 */         return (compareResult == 0);
/*  96 */       } catch (ClassCastException ignored) {}
/*     */     } 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <C extends Comparable> Cut<C> belowAll() {
/* 108 */     return BelowAll.INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class BelowAll
/*     */     extends Cut<Comparable<?>>
/*     */   {
/* 114 */     private static final BelowAll INSTANCE = new BelowAll(); private static final long serialVersionUID = 0L;
/*     */     
/*     */     private BelowAll() {
/* 117 */       super(null);
/*     */     }
/*     */     Comparable<?> endpoint() {
/* 120 */       throw new IllegalStateException("range unbounded on this side");
/*     */     }
/*     */     boolean isLessThan(Comparable<?> value) {
/* 123 */       return true;
/*     */     }
/*     */     BoundType typeAsLowerBound() {
/* 126 */       throw new IllegalStateException();
/*     */     }
/*     */     BoundType typeAsUpperBound() {
/* 129 */       throw new AssertionError("this statement should be unreachable");
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
/* 133 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
/* 137 */       throw new AssertionError("this statement should be unreachable");
/*     */     }
/*     */     void describeAsLowerBound(StringBuilder sb) {
/* 140 */       sb.append("(-∞");
/*     */     }
/*     */     void describeAsUpperBound(StringBuilder sb) {
/* 143 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
/* 147 */       return domain.minValue();
/*     */     }
/*     */     
/*     */     Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
/* 151 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> domain) {
/*     */       try {
/* 156 */         return Cut.belowValue(domain.minValue());
/* 157 */       } catch (NoSuchElementException e) {
/* 158 */         return this;
/*     */       } 
/*     */     }
/*     */     public int compareTo(Cut<Comparable<?>> o) {
/* 162 */       return (o == this) ? 0 : -1;
/*     */     }
/*     */     private Object readResolve() {
/* 165 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <C extends Comparable> Cut<C> aboveAll() {
/* 176 */     return AboveAll.INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class AboveAll extends Cut<Comparable<?>> {
/* 180 */     private static final AboveAll INSTANCE = new AboveAll(); private static final long serialVersionUID = 0L;
/*     */     
/*     */     private AboveAll() {
/* 183 */       super(null);
/*     */     }
/*     */     Comparable<?> endpoint() {
/* 186 */       throw new IllegalStateException("range unbounded on this side");
/*     */     }
/*     */     boolean isLessThan(Comparable<?> value) {
/* 189 */       return false;
/*     */     }
/*     */     BoundType typeAsLowerBound() {
/* 192 */       throw new AssertionError("this statement should be unreachable");
/*     */     }
/*     */     BoundType typeAsUpperBound() {
/* 195 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
/* 199 */       throw new AssertionError("this statement should be unreachable");
/*     */     }
/*     */     
/*     */     Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
/* 203 */       throw new IllegalStateException();
/*     */     }
/*     */     void describeAsLowerBound(StringBuilder sb) {
/* 206 */       throw new AssertionError();
/*     */     }
/*     */     void describeAsUpperBound(StringBuilder sb) {
/* 209 */       sb.append("+∞)");
/*     */     }
/*     */     
/*     */     Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
/* 213 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
/* 217 */       return domain.maxValue();
/*     */     }
/*     */     public int compareTo(Cut<Comparable<?>> o) {
/* 220 */       return (o == this) ? 0 : 1;
/*     */     }
/*     */     private Object readResolve() {
/* 223 */       return INSTANCE;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static <C extends Comparable> Cut<C> belowValue(C endpoint) {
/* 229 */     return new BelowValue<C>(endpoint);
/*     */   }
/*     */   private static final class BelowValue<C extends Comparable> extends Cut<C> { private static final long serialVersionUID = 0L;
/*     */     
/*     */     BelowValue(C endpoint) {
/* 234 */       super((C)Preconditions.checkNotNull(endpoint));
/*     */     }
/*     */     
/*     */     boolean isLessThan(C value) {
/* 238 */       return (Range.compareOrThrow((Comparable)this.endpoint, (Comparable)value) <= 0);
/*     */     }
/*     */     BoundType typeAsLowerBound() {
/* 241 */       return BoundType.CLOSED;
/*     */     }
/*     */     BoundType typeAsUpperBound() {
/* 244 */       return BoundType.OPEN;
/*     */     } Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
/*     */       C previous;
/* 247 */       switch (boundType) {
/*     */         case CLOSED:
/* 249 */           return this;
/*     */         case OPEN:
/* 251 */           previous = domain.previous(this.endpoint);
/* 252 */           return (previous == null) ? Cut.<C>belowAll() : new Cut.AboveValue<C>(previous);
/*     */       } 
/* 254 */       throw new AssertionError();
/*     */     }
/*     */     Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
/*     */       C previous;
/* 258 */       switch (boundType) {
/*     */         case CLOSED:
/* 260 */           previous = domain.previous(this.endpoint);
/* 261 */           return (previous == null) ? Cut.<C>aboveAll() : new Cut.AboveValue<C>(previous);
/*     */         case OPEN:
/* 263 */           return this;
/*     */       } 
/* 265 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     void describeAsLowerBound(StringBuilder sb) {
/* 269 */       sb.append('[').append(this.endpoint);
/*     */     }
/*     */     void describeAsUpperBound(StringBuilder sb) {
/* 272 */       sb.append(this.endpoint).append(')');
/*     */     }
/*     */     C leastValueAbove(DiscreteDomain<C> domain) {
/* 275 */       return this.endpoint;
/*     */     }
/*     */     C greatestValueBelow(DiscreteDomain<C> domain) {
/* 278 */       return domain.previous(this.endpoint);
/*     */     }
/*     */     public int hashCode() {
/* 281 */       return this.endpoint.hashCode();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   static <C extends Comparable> Cut<C> aboveValue(C endpoint) {
/* 287 */     return new AboveValue<C>(endpoint);
/*     */   } abstract boolean isLessThan(C paramC); abstract BoundType typeAsLowerBound(); abstract BoundType typeAsUpperBound(); abstract Cut<C> withLowerBoundType(BoundType paramBoundType, DiscreteDomain<C> paramDiscreteDomain); abstract Cut<C> withUpperBoundType(BoundType paramBoundType, DiscreteDomain<C> paramDiscreteDomain); abstract void describeAsLowerBound(StringBuilder paramStringBuilder); abstract void describeAsUpperBound(StringBuilder paramStringBuilder);
/*     */   abstract C leastValueAbove(DiscreteDomain<C> paramDiscreteDomain);
/*     */   abstract C greatestValueBelow(DiscreteDomain<C> paramDiscreteDomain);
/*     */   private static final class AboveValue<C extends Comparable> extends Cut<C> { AboveValue(C endpoint) {
/* 292 */       super((C)Preconditions.checkNotNull(endpoint));
/*     */     }
/*     */     private static final long serialVersionUID = 0L;
/*     */     boolean isLessThan(C value) {
/* 296 */       return (Range.compareOrThrow((Comparable)this.endpoint, (Comparable)value) < 0);
/*     */     }
/*     */     BoundType typeAsLowerBound() {
/* 299 */       return BoundType.OPEN;
/*     */     }
/*     */     BoundType typeAsUpperBound() {
/* 302 */       return BoundType.CLOSED;
/*     */     } Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
/*     */       C next;
/* 305 */       switch (boundType) {
/*     */         case OPEN:
/* 307 */           return this;
/*     */         case CLOSED:
/* 309 */           next = domain.next(this.endpoint);
/* 310 */           return (next == null) ? Cut.<C>belowAll() : belowValue(next);
/*     */       } 
/* 312 */       throw new AssertionError();
/*     */     }
/*     */     Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
/*     */       C next;
/* 316 */       switch (boundType) {
/*     */         case OPEN:
/* 318 */           next = domain.next(this.endpoint);
/* 319 */           return (next == null) ? Cut.<C>aboveAll() : belowValue(next);
/*     */         case CLOSED:
/* 321 */           return this;
/*     */       } 
/* 323 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     void describeAsLowerBound(StringBuilder sb) {
/* 327 */       sb.append('(').append(this.endpoint);
/*     */     }
/*     */     void describeAsUpperBound(StringBuilder sb) {
/* 330 */       sb.append(this.endpoint).append(']');
/*     */     }
/*     */     C leastValueAbove(DiscreteDomain<C> domain) {
/* 333 */       return domain.next(this.endpoint);
/*     */     }
/*     */     C greatestValueBelow(DiscreteDomain<C> domain) {
/* 336 */       return this.endpoint;
/*     */     }
/*     */     Cut<C> canonical(DiscreteDomain<C> domain) {
/* 339 */       C next = leastValueAbove(domain);
/* 340 */       return (next != null) ? belowValue(next) : Cut.<C>aboveAll();
/*     */     }
/*     */     public int hashCode() {
/* 343 */       return this.endpoint.hashCode() ^ 0xFFFFFFFF;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Cut.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */