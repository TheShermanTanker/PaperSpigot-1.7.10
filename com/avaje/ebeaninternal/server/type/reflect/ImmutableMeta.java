/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImmutableMeta
/*    */ {
/*    */   private final Constructor<?> constructor;
/*    */   private final Method[] readers;
/*    */   
/*    */   public ImmutableMeta(Constructor<?> constructor, Method[] readers) {
/* 14 */     this.constructor = constructor;
/* 15 */     this.readers = readers;
/*    */   }
/*    */ 
/*    */   
/*    */   public Constructor<?> getConstructor() {
/* 20 */     return this.constructor;
/*    */   }
/*    */   
/*    */   public Method[] getReaders() {
/* 24 */     return this.readers;
/*    */   }
/*    */   
/*    */   public boolean isCompoundType() {
/* 28 */     return (this.readers.length > 1);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\reflect\ImmutableMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */