/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.InternString;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TableJoinColumn
/*    */ {
/*    */   private final String localDbColumn;
/*    */   private final String foreignDbColumn;
/*    */   private final boolean insertable;
/*    */   private final boolean updateable;
/*    */   
/*    */   public TableJoinColumn(DeployTableJoinColumn deploy) {
/* 48 */     this.localDbColumn = InternString.intern(deploy.getLocalDbColumn());
/* 49 */     this.foreignDbColumn = InternString.intern(deploy.getForeignDbColumn());
/* 50 */     this.insertable = deploy.isInsertable();
/* 51 */     this.updateable = deploy.isUpdateable();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 55 */     return this.localDbColumn + " = " + this.foreignDbColumn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getForeignDbColumn() {
/* 63 */     return this.foreignDbColumn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalDbColumn() {
/* 70 */     return this.localDbColumn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInsertable() {
/* 77 */     return this.insertable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUpdateable() {
/* 84 */     return this.updateable;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\TableJoinColumn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */