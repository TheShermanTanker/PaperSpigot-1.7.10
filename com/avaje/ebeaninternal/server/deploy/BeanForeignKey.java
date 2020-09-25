/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.InternString;
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
/*    */ public class BeanForeignKey
/*    */ {
/*    */   private final String dbColumn;
/*    */   private final int dbType;
/*    */   
/*    */   public BeanForeignKey(String dbColumn, int dbType) {
/* 37 */     this.dbColumn = InternString.intern(dbColumn);
/* 38 */     this.dbType = dbType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDbColumn() {
/* 45 */     return this.dbColumn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDbType() {
/* 52 */     return this.dbType;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 56 */     if (obj == null) {
/* 57 */       return false;
/*    */     }
/* 59 */     if (obj instanceof BeanForeignKey) {
/* 60 */       return (obj.hashCode() == hashCode());
/*    */     }
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 66 */     int hc = getClass().hashCode();
/* 67 */     hc = hc * 31 + ((this.dbColumn != null) ? this.dbColumn.hashCode() : 0);
/* 68 */     return hc;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     return this.dbColumn;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanForeignKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */