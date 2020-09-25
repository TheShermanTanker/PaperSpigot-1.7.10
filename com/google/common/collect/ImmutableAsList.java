/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.InvalidObjectException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(serializable = true, emulated = true)
/*    */ final class ImmutableAsList<E>
/*    */   extends RegularImmutableList<E>
/*    */ {
/*    */   private final transient ImmutableCollection<E> collection;
/*    */   
/*    */   ImmutableAsList(Object[] array, ImmutableCollection<E> collection) {
/* 37 */     super(array, 0, array.length);
/* 38 */     this.collection = collection;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean contains(Object target) {
/* 44 */     return this.collection.contains(target);
/*    */   }
/*    */   
/*    */   static class SerializedForm
/*    */     implements Serializable {
/*    */     final ImmutableCollection<?> collection;
/*    */     private static final long serialVersionUID = 0L;
/*    */     
/*    */     SerializedForm(ImmutableCollection<?> collection) {
/* 53 */       this.collection = collection;
/*    */     }
/*    */     Object readResolve() {
/* 56 */       return this.collection.asList();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 63 */     throw new InvalidObjectException("Use SerializedForm");
/*    */   }
/*    */   
/*    */   Object writeReplace() {
/* 67 */     return new SerializedForm(this.collection);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableAsList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */