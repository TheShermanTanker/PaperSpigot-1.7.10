/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.annotation.NamedUpdate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeployNamedUpdate
/*    */ {
/*    */   private final String name;
/*    */   private final String updateStatement;
/*    */   private final boolean notifyCache;
/*    */   private String sqlUpdateStatement;
/*    */   
/*    */   public DeployNamedUpdate(NamedUpdate update) {
/* 19 */     this.name = update.name();
/* 20 */     this.updateStatement = update.update();
/* 21 */     this.notifyCache = update.notifyCache();
/*    */   }
/*    */   
/*    */   public void initialise(DeployUpdateParser parser) {
/* 25 */     this.sqlUpdateStatement = parser.parse(this.updateStatement);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 29 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getSqlUpdateStatement() {
/* 33 */     return this.sqlUpdateStatement;
/*    */   }
/*    */   
/*    */   public boolean isNotifyCache() {
/* 37 */     return this.notifyCache;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DeployNamedUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */