/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.annotations.GwtIncompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.util.Collection;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(emulated = true)
/*    */ public final class EnumMultiset<E extends Enum<E>>
/*    */   extends AbstractMapBasedMultiset<E>
/*    */ {
/*    */   private transient Class<E> type;
/*    */   @GwtIncompatible("Not needed in emulated source")
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> type) {
/* 38 */     return new EnumMultiset<E>(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> elements) {
/* 51 */     Iterator<E> iterator = elements.iterator();
/* 52 */     Preconditions.checkArgument(iterator.hasNext(), "EnumMultiset constructor passed empty Iterable");
/* 53 */     EnumMultiset<E> multiset = new EnumMultiset<E>(((Enum<E>)iterator.next()).getDeclaringClass());
/* 54 */     Iterables.addAll(multiset, elements);
/* 55 */     return multiset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private EnumMultiset(Class<E> type) {
/* 62 */     super(WellBehavedMap.wrap(new EnumMap<E, Count>(type)));
/* 63 */     this.type = type;
/*    */   }
/*    */   
/*    */   @GwtIncompatible("java.io.ObjectOutputStream")
/*    */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 68 */     stream.defaultWriteObject();
/* 69 */     stream.writeObject(this.type);
/* 70 */     Serialization.writeMultiset(this, stream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @GwtIncompatible("java.io.ObjectInputStream")
/*    */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 80 */     stream.defaultReadObject();
/*    */     
/* 82 */     Class<E> localType = (Class<E>)stream.readObject();
/* 83 */     this.type = localType;
/* 84 */     setBackingMap(WellBehavedMap.wrap(new EnumMap<E, Count>(this.type)));
/* 85 */     Serialization.populateMultiset(this, stream);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\EnumMultiset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */