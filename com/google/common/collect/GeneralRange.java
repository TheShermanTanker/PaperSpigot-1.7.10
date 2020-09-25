/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Optional;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
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
/*     */ @GwtCompatible(serializable = true)
/*     */ final class GeneralRange<T>
/*     */   implements Serializable
/*     */ {
/*     */   private final Comparator<? super T> comparator;
/*     */   private final Optional<T> lowerEndpoint;
/*     */   private final BoundType lowerBoundType;
/*     */   private final Optional<T> upperEndpoint;
/*     */   private final BoundType upperBoundType;
/*     */   private transient GeneralRange<T> reverse;
/*     */   
/*     */   static <T extends Comparable> GeneralRange<T> from(Range<T> range) {
/*  46 */     Optional<T> lowerEndpoint = range.hasLowerBound() ? Optional.of(range.lowerEndpoint()) : Optional.absent();
/*     */     
/*  48 */     BoundType lowerBoundType = range.hasLowerBound() ? range.lowerBoundType() : BoundType.OPEN;
/*  49 */     Optional<T> upperEndpoint = range.hasUpperBound() ? Optional.of(range.upperEndpoint()) : Optional.absent();
/*     */     
/*  51 */     BoundType upperBoundType = range.hasUpperBound() ? range.upperBoundType() : BoundType.OPEN;
/*  52 */     return new GeneralRange<T>(Ordering.natural(), lowerEndpoint, lowerBoundType, upperEndpoint, upperBoundType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> GeneralRange<T> all(Comparator<? super T> comparator) {
/*  60 */     return new GeneralRange<T>(comparator, Optional.absent(), BoundType.OPEN, Optional.absent(), BoundType.OPEN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> GeneralRange<T> downTo(Comparator<? super T> comparator, T endpoint, BoundType boundType) {
/*  69 */     return new GeneralRange<T>(comparator, Optional.of(endpoint), boundType, Optional.absent(), BoundType.OPEN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> GeneralRange<T> upTo(Comparator<? super T> comparator, T endpoint, BoundType boundType) {
/*  79 */     return new GeneralRange<T>(comparator, Optional.absent(), BoundType.OPEN, Optional.of(endpoint), boundType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> GeneralRange<T> range(Comparator<? super T> comparator, T lower, BoundType lowerType, T upper, BoundType upperType) {
/*  89 */     return new GeneralRange<T>(comparator, Optional.of(lower), lowerType, Optional.of(upper), upperType);
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
/*     */   private GeneralRange(Comparator<? super T> comparator, Optional<T> lowerEndpoint, BoundType lowerBoundType, Optional<T> upperEndpoint, BoundType upperBoundType) {
/* 101 */     this.comparator = (Comparator<? super T>)Preconditions.checkNotNull(comparator);
/* 102 */     this.lowerEndpoint = (Optional<T>)Preconditions.checkNotNull(lowerEndpoint);
/* 103 */     this.lowerBoundType = (BoundType)Preconditions.checkNotNull(lowerBoundType);
/* 104 */     this.upperEndpoint = (Optional<T>)Preconditions.checkNotNull(upperEndpoint);
/* 105 */     this.upperBoundType = (BoundType)Preconditions.checkNotNull(upperBoundType);
/* 106 */     if (lowerEndpoint.isPresent() && upperEndpoint.isPresent()) {
/* 107 */       int cmp = comparator.compare((T)lowerEndpoint.get(), (T)upperEndpoint.get());
/*     */       
/* 109 */       Preconditions.checkArgument((cmp <= 0), "lowerEndpoint (%s) > upperEndpoint (%s)", new Object[] { lowerEndpoint, upperEndpoint });
/*     */       
/* 111 */       if (cmp == 0) {
/* 112 */         Preconditions.checkArgument(((lowerBoundType != BoundType.OPEN)) | ((upperBoundType != BoundType.OPEN)));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   Comparator<? super T> comparator() {
/* 118 */     return this.comparator;
/*     */   }
/*     */   
/*     */   boolean hasLowerBound() {
/* 122 */     return this.lowerEndpoint.isPresent();
/*     */   }
/*     */   
/*     */   boolean hasUpperBound() {
/* 126 */     return this.upperEndpoint.isPresent();
/*     */   }
/*     */   
/*     */   boolean isEmpty() {
/* 130 */     return ((hasUpperBound() && tooLow((T)this.upperEndpoint.get())) || (hasLowerBound() && tooHigh((T)this.lowerEndpoint.get())));
/*     */   }
/*     */ 
/*     */   
/*     */   boolean tooLow(T t) {
/* 135 */     if (!hasLowerBound()) {
/* 136 */       return false;
/*     */     }
/* 138 */     T lbound = (T)this.lowerEndpoint.get();
/* 139 */     int cmp = this.comparator.compare(t, lbound);
/* 140 */     return ((cmp < 0) ? 1 : 0) | ((cmp == 0)) & ((this.lowerBoundType == BoundType.OPEN));
/*     */   }
/*     */   
/*     */   boolean tooHigh(T t) {
/* 144 */     if (!hasUpperBound()) {
/* 145 */       return false;
/*     */     }
/* 147 */     T ubound = (T)this.upperEndpoint.get();
/* 148 */     int cmp = this.comparator.compare(t, ubound);
/* 149 */     return ((cmp > 0) ? 1 : 0) | ((cmp == 0)) & ((this.upperBoundType == BoundType.OPEN));
/*     */   }
/*     */   
/*     */   boolean contains(T t) {
/* 153 */     Preconditions.checkNotNull(t);
/* 154 */     return (!tooLow(t) && !tooHigh(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GeneralRange<T> intersect(GeneralRange<T> other) {
/* 161 */     Preconditions.checkNotNull(other);
/* 162 */     Preconditions.checkArgument(this.comparator.equals(other.comparator));
/*     */     
/* 164 */     Optional<T> lowEnd = this.lowerEndpoint;
/* 165 */     BoundType lowType = this.lowerBoundType;
/* 166 */     if (!hasLowerBound()) {
/* 167 */       lowEnd = other.lowerEndpoint;
/* 168 */       lowType = other.lowerBoundType;
/* 169 */     } else if (other.hasLowerBound()) {
/* 170 */       int cmp = this.comparator.compare((T)this.lowerEndpoint.get(), (T)other.lowerEndpoint.get());
/* 171 */       if (cmp < 0 || (cmp == 0 && other.lowerBoundType == BoundType.OPEN)) {
/* 172 */         lowEnd = other.lowerEndpoint;
/* 173 */         lowType = other.lowerBoundType;
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     Optional<T> upEnd = this.upperEndpoint;
/* 178 */     BoundType upType = this.upperBoundType;
/* 179 */     if (!hasUpperBound()) {
/* 180 */       upEnd = other.upperEndpoint;
/* 181 */       upType = other.upperBoundType;
/* 182 */     } else if (other.hasUpperBound()) {
/* 183 */       int cmp = this.comparator.compare((T)this.upperEndpoint.get(), (T)other.upperEndpoint.get());
/* 184 */       if (cmp > 0 || (cmp == 0 && other.upperBoundType == BoundType.OPEN)) {
/* 185 */         upEnd = other.upperEndpoint;
/* 186 */         upType = other.upperBoundType;
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     if (lowEnd.isPresent() && upEnd.isPresent()) {
/* 191 */       int cmp = this.comparator.compare((T)lowEnd.get(), (T)upEnd.get());
/* 192 */       if (cmp > 0 || (cmp == 0 && lowType == BoundType.OPEN && upType == BoundType.OPEN)) {
/*     */         
/* 194 */         lowEnd = upEnd;
/* 195 */         lowType = BoundType.OPEN;
/* 196 */         upType = BoundType.CLOSED;
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     return new GeneralRange(this.comparator, lowEnd, lowType, upEnd, upType);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/* 205 */     if (obj instanceof GeneralRange) {
/* 206 */       GeneralRange<?> r = (GeneralRange)obj;
/* 207 */       return (this.comparator.equals(r.comparator) && this.lowerEndpoint.equals(r.lowerEndpoint) && this.lowerBoundType.equals(r.lowerBoundType) && this.upperEndpoint.equals(r.upperEndpoint) && this.upperBoundType.equals(r.upperBoundType));
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 216 */     return Objects.hashCode(new Object[] { this.comparator, this.lowerEndpoint, this.lowerBoundType, this.upperEndpoint, this.upperBoundType });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralRange<T> reverse() {
/* 226 */     GeneralRange<T> result = this.reverse;
/* 227 */     if (result == null) {
/* 228 */       result = new GeneralRange(Ordering.<T>from(this.comparator).reverse(), this.upperEndpoint, this.upperBoundType, this.lowerEndpoint, this.lowerBoundType);
/*     */ 
/*     */       
/* 231 */       result.reverse = this;
/* 232 */       return this.reverse = result;
/*     */     } 
/* 234 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 239 */     StringBuilder builder = new StringBuilder();
/* 240 */     builder.append(this.comparator).append(":");
/* 241 */     switch (this.lowerBoundType) {
/*     */       case CLOSED:
/* 243 */         builder.append('[');
/*     */         break;
/*     */       case OPEN:
/* 246 */         builder.append('(');
/*     */         break;
/*     */     } 
/* 249 */     if (hasLowerBound()) {
/* 250 */       builder.append(this.lowerEndpoint.get());
/*     */     } else {
/* 252 */       builder.append("-∞");
/*     */     } 
/* 254 */     builder.append(',');
/* 255 */     if (hasUpperBound()) {
/* 256 */       builder.append(this.upperEndpoint.get());
/*     */     } else {
/* 258 */       builder.append("∞");
/*     */     } 
/* 260 */     switch (this.upperBoundType) {
/*     */       case CLOSED:
/* 262 */         builder.append(']');
/*     */         break;
/*     */       case OPEN:
/* 265 */         builder.append(')');
/*     */         break;
/*     */     } 
/* 268 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\GeneralRange.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */