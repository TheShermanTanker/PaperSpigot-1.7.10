/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.lang.reflect.Array;
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
/*    */ public class CyclicBuffer<T>
/*    */ {
/*    */   private final T[] ring;
/* 28 */   private int first = 0;
/* 29 */   private int last = 0;
/* 30 */   private int numElems = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   private final Class<T> clazz;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CyclicBuffer(Class<T> clazz, int size) throws IllegalArgumentException {
/* 40 */     if (size < 1) {
/* 41 */       throw new IllegalArgumentException("The maxSize argument (" + size + ") is not a positive integer.");
/*    */     }
/* 43 */     this.ring = makeArray(clazz, size);
/* 44 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   
/*    */   private T[] makeArray(Class<T> clazz, int size) {
/* 49 */     return (T[])Array.newInstance(clazz, size);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void add(T item) {
/* 57 */     this.ring[this.last] = item;
/* 58 */     if (++this.last == this.ring.length) {
/* 59 */       this.last = 0;
/*    */     }
/*    */     
/* 62 */     if (this.numElems < this.ring.length) {
/* 63 */       this.numElems++;
/* 64 */     } else if (++this.first == this.ring.length) {
/* 65 */       this.first = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized T[] removeAll() {
/* 74 */     T[] array = makeArray(this.clazz, this.numElems);
/* 75 */     int index = 0;
/* 76 */     while (this.numElems > 0) {
/* 77 */       this.numElems--;
/* 78 */       array[index++] = this.ring[this.first];
/* 79 */       this.ring[this.first] = null;
/* 80 */       if (++this.first == this.ring.length) {
/* 81 */         this.first = 0;
/*    */       }
/*    */     } 
/* 84 */     return array;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 92 */     return (0 == this.numElems);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\CyclicBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */