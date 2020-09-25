/*    */ package com.avaje.ebean.config.ldap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LdapConfig
/*    */ {
/*    */   private LdapContextFactory contextFactory;
/*    */   private boolean vanillaMode;
/*    */   
/*    */   public LdapContextFactory getContextFactory() {
/* 38 */     return this.contextFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setContextFactory(LdapContextFactory contextFactory) {
/* 45 */     this.contextFactory = contextFactory;
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
/*    */   public void setVanillaMode(boolean vanillaMode) {
/* 59 */     this.vanillaMode = vanillaMode;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ldap\LdapConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */