/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ @GwtCompatible
/*     */ public final class Collections2
/*     */ {
/*     */   public static <E> Collection<E> filter(Collection<E> unfiltered, Predicate<? super E> predicate) {
/*  80 */     if (unfiltered instanceof FilteredCollection)
/*     */     {
/*     */       
/*  83 */       return ((FilteredCollection<E>)unfiltered).createCombined(predicate);
/*     */     }
/*     */     
/*  86 */     return new FilteredCollection<E>((Collection<E>)Preconditions.checkNotNull(unfiltered), (Predicate<? super E>)Preconditions.checkNotNull(predicate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean safeContains(Collection<?> collection, Object object) {
/*     */     try {
/*  96 */       return collection.contains(object);
/*  97 */     } catch (ClassCastException e) {
/*  98 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   static class FilteredCollection<E>
/*     */     implements Collection<E> {
/*     */     final Collection<E> unfiltered;
/*     */     final Predicate<? super E> predicate;
/*     */     
/*     */     FilteredCollection(Collection<E> unfiltered, Predicate<? super E> predicate) {
/* 108 */       this.unfiltered = unfiltered;
/* 109 */       this.predicate = predicate;
/*     */     }
/*     */     
/*     */     FilteredCollection<E> createCombined(Predicate<? super E> newPredicate) {
/* 113 */       return new FilteredCollection(this.unfiltered, Predicates.and(this.predicate, newPredicate));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean add(E element) {
/* 120 */       Preconditions.checkArgument(this.predicate.apply(element));
/* 121 */       return this.unfiltered.add(element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends E> collection) {
/* 126 */       for (E element : collection) {
/* 127 */         Preconditions.checkArgument(this.predicate.apply(element));
/*     */       }
/* 129 */       return this.unfiltered.addAll(collection);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 134 */       Iterables.removeIf(this.unfiltered, this.predicate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object element) {
/*     */       try {
/* 143 */         E e = (E)element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 150 */         return (this.predicate.apply(e) && this.unfiltered.contains(element));
/* 151 */       } catch (NullPointerException e) {
/* 152 */         return false;
/* 153 */       } catch (ClassCastException e) {
/* 154 */         return false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> collection) {
/* 160 */       for (Object element : collection) {
/* 161 */         if (!contains(element)) {
/* 162 */           return false;
/*     */         }
/*     */       } 
/* 165 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 170 */       return !Iterators.any(this.unfiltered.iterator(), this.predicate);
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<E> iterator() {
/* 175 */       return Iterators.filter(this.unfiltered.iterator(), this.predicate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object element) {
/*     */       try {
/* 184 */         E e = (E)element;
/*     */ 
/*     */         
/* 187 */         return (this.predicate.apply(e) && this.unfiltered.remove(element));
/* 188 */       } catch (NullPointerException e) {
/* 189 */         return false;
/* 190 */       } catch (ClassCastException e) {
/* 191 */         return false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(final Collection<?> collection) {
/* 197 */       Preconditions.checkNotNull(collection);
/* 198 */       Predicate<E> combinedPredicate = new Predicate<E>()
/*     */         {
/*     */           public boolean apply(E input) {
/* 201 */             return (Collections2.FilteredCollection.this.predicate.apply(input) && collection.contains(input));
/*     */           }
/*     */         };
/* 204 */       return Iterables.removeIf(this.unfiltered, combinedPredicate);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(final Collection<?> collection) {
/* 209 */       Preconditions.checkNotNull(collection);
/* 210 */       Predicate<E> combinedPredicate = new Predicate<E>()
/*     */         {
/*     */           public boolean apply(E input)
/*     */           {
/* 214 */             return (Collections2.FilteredCollection.this.predicate.apply(input) && !collection.contains(input));
/*     */           }
/*     */         };
/* 217 */       return Iterables.removeIf(this.unfiltered, combinedPredicate);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 222 */       return Iterators.size(iterator());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 228 */       return Lists.<E>newArrayList(iterator()).toArray();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] array) {
/* 233 */       return (T[])Lists.<E>newArrayList(iterator()).toArray((Object[])array);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 237 */       return Iterators.toString(iterator());
/*     */     }
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
/*     */   
/*     */   public static <F, T> Collection<T> transform(Collection<F> fromCollection, Function<? super F, T> function) {
/* 262 */     return new TransformedCollection<F, T>(fromCollection, function);
/*     */   }
/*     */   
/*     */   static class TransformedCollection<F, T>
/*     */     extends AbstractCollection<T> {
/*     */     final Collection<F> fromCollection;
/*     */     final Function<? super F, ? extends T> function;
/*     */     
/*     */     TransformedCollection(Collection<F> fromCollection, Function<? super F, ? extends T> function) {
/* 271 */       this.fromCollection = (Collection<F>)Preconditions.checkNotNull(fromCollection);
/* 272 */       this.function = (Function<? super F, ? extends T>)Preconditions.checkNotNull(function);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 276 */       this.fromCollection.clear();
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 280 */       return this.fromCollection.isEmpty();
/*     */     }
/*     */     
/*     */     public Iterator<T> iterator() {
/* 284 */       return Iterators.transform(this.fromCollection.iterator(), this.function);
/*     */     }
/*     */     
/*     */     public int size() {
/* 288 */       return this.fromCollection.size();
/*     */     }
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
/*     */   static boolean containsAllImpl(Collection<?> self, Collection<?> c) {
/* 305 */     Preconditions.checkNotNull(self);
/* 306 */     for (Object o : c) {
/* 307 */       if (!self.contains(o)) {
/* 308 */         return false;
/*     */       }
/*     */     } 
/* 311 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String toStringImpl(final Collection<?> collection) {
/* 318 */     StringBuilder sb = newStringBuilderForCollection(collection.size()).append('[');
/*     */     
/* 320 */     STANDARD_JOINER.appendTo(sb, Iterables.transform(collection, new Function<Object, Object>()
/*     */           {
/*     */             public Object apply(Object input) {
/* 323 */               return (input == collection) ? "(this Collection)" : input;
/*     */             }
/*     */           }));
/* 326 */     return sb.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static StringBuilder newStringBuilderForCollection(int size) {
/* 333 */     Preconditions.checkArgument((size >= 0), "size must be non-negative");
/* 334 */     return new StringBuilder((int)Math.min(size * 8L, 1073741824L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> Collection<T> cast(Iterable<T> iterable) {
/* 341 */     return (Collection<T>)iterable;
/*     */   }
/*     */   
/* 344 */   static final Joiner STANDARD_JOINER = Joiner.on(", ");
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Collections2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */