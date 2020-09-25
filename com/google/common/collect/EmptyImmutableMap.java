/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
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
/*    */ final class EmptyImmutableMap
/*    */   extends ImmutableMap<Object, Object>
/*    */ {
/* 33 */   static final EmptyImmutableMap INSTANCE = new EmptyImmutableMap();
/*    */   
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   public Object get(@Nullable Object key) {
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 43 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   public boolean containsKey(@Nullable Object key) {
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   public boolean containsValue(@Nullable Object value) {
/* 55 */     return false;
/*    */   }
/*    */   
/*    */   public ImmutableSet<Map.Entry<Object, Object>> entrySet() {
/* 59 */     return ImmutableSet.of();
/*    */   }
/*    */   
/*    */   public ImmutableSet<Object> keySet() {
/* 63 */     return ImmutableSet.of();
/*    */   }
/*    */   
/*    */   public ImmutableCollection<Object> values() {
/* 67 */     return ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION;
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 71 */     if (object instanceof Map) {
/* 72 */       Map<?, ?> that = (Map<?, ?>)object;
/* 73 */       return that.isEmpty();
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   boolean isPartialView() {
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 83 */     return 0;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     return "{}";
/*    */   }
/*    */   
/*    */   Object readResolve() {
/* 91 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\EmptyImmutableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */