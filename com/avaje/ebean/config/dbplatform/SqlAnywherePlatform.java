/*    */ package com.avaje.ebean.config.dbplatform;
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
/*    */ public class SqlAnywherePlatform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public SqlAnywherePlatform() {
/* 38 */     this.name = "sqlanywhere";
/* 39 */     this.dbIdentity.setIdType(IdType.IDENTITY);
/*    */     
/* 41 */     this.sqlLimiter = new SqlAnywhereLimiter();
/* 42 */     this.dbIdentity.setSupportsGetGeneratedKeys(false);
/* 43 */     this.dbIdentity.setSelectLastInsertedIdTemplate("select @@IDENTITY as X");
/* 44 */     this.dbIdentity.setSupportsIdentity(true);
/*    */     
/* 46 */     this.dbTypeMap.put(16, new DbType("bit default 0"));
/* 47 */     this.dbTypeMap.put(-5, new DbType("numeric", 19));
/* 48 */     this.dbTypeMap.put(7, new DbType("float(16)"));
/* 49 */     this.dbTypeMap.put(8, new DbType("float(32)"));
/* 50 */     this.dbTypeMap.put(-6, new DbType("smallint"));
/* 51 */     this.dbTypeMap.put(3, new DbType("numeric", 28));
/*    */     
/* 53 */     this.dbTypeMap.put(2004, new DbType("binary(4500)"));
/* 54 */     this.dbTypeMap.put(2005, new DbType("long varchar"));
/* 55 */     this.dbTypeMap.put(-4, new DbType("long binary"));
/* 56 */     this.dbTypeMap.put(-1, new DbType("long varchar"));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SqlAnywherePlatform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */