/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.bean.PersistenceContext;
/*    */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CopyContext
/*    */ {
/*    */   private final boolean vanillaMode;
/*    */   private final boolean sharing;
/*    */   private final PersistenceContext pc;
/*    */   
/*    */   public CopyContext(boolean vanillaMode, boolean sharing) {
/* 39 */     this.vanillaMode = vanillaMode;
/* 40 */     this.sharing = sharing;
/* 41 */     this.pc = (PersistenceContext)new DefaultPersistenceContext();
/*    */   }
/*    */   
/*    */   public CopyContext(boolean vanillaMode) {
/* 45 */     this(vanillaMode, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isVanillaMode() {
/* 52 */     return this.vanillaMode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSharing() {
/* 59 */     return this.sharing;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PersistenceContext getPersistenceContext() {
/* 66 */     return this.pc;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object putIfAbsent(Object id, Object bean) {
/* 73 */     return this.pc.putIfAbsent(id, bean);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get(Class<?> beanType, Object beanId) {
/* 80 */     return this.pc.get(beanType, beanId);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\CopyContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */