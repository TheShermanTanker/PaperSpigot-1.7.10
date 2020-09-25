/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.com.google.common.base.Predicates;
/*    */ import net.minecraft.util.com.google.common.collect.Iterators;
/*    */ import net.minecraft.util.com.google.common.collect.Lists;
/*    */ 
/*    */ 
/*    */ public class RegistryID
/*    */   implements Registry
/*    */ {
/* 14 */   private IdentityHashMap a = new IdentityHashMap<Object, Object>(512);
/* 15 */   private List b = Lists.newArrayList();
/*    */   
/*    */   public void a(Object paramObject, int paramInt) {
/* 18 */     this.a.put(paramObject, Integer.valueOf(paramInt));
/*    */ 
/*    */     
/* 21 */     while (this.b.size() <= paramInt) {
/* 22 */       this.b.add(null);
/*    */     }
/*    */     
/* 25 */     this.b.set(paramInt, paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(Object paramObject) {
/* 30 */     Integer integer = (Integer)this.a.get(paramObject);
/* 31 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object a(int paramInt) {
/* 36 */     if (paramInt >= 0 && paramInt < this.b.size()) {
/* 37 */       return this.b.get(paramInt);
/*    */     }
/*    */     
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator iterator() {
/* 45 */     return (Iterator)Iterators.filter(this.b.iterator(), Predicates.notNull());
/*    */   }
/*    */   
/*    */   public boolean b(int paramInt) {
/* 49 */     return (a(paramInt) != null);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RegistryID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */