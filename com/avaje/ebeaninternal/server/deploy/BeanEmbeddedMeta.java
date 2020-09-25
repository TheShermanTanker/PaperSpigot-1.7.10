/*    */ package com.avaje.ebeaninternal.server.deploy;
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
/*    */ public class BeanEmbeddedMeta
/*    */ {
/*    */   final BeanProperty[] properties;
/*    */   
/*    */   public BeanEmbeddedMeta(BeanProperty[] properties) {
/* 28 */     this.properties = properties;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BeanProperty[] getProperties() {
/* 35 */     return this.properties;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmbeddedVersion() {
/* 42 */     for (int i = 0; i < this.properties.length; i++) {
/* 43 */       if (this.properties[i].isVersion()) {
/* 44 */         return true;
/*    */       }
/*    */     } 
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanEmbeddedMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */