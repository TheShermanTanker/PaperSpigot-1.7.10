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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MsSqlServer2005Platform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public MsSqlServer2005Platform() {
/* 41 */     this.name = "mssqlserver2005";
/* 42 */     this.sqlLimiter = new MsSqlServer2005SqlLimiter();
/* 43 */     this.dbDdlSyntax.setIdentity("identity(1,1)");
/* 44 */     this.dbIdentity.setIdType(IdType.IDENTITY);
/* 45 */     this.dbIdentity.setSupportsGetGeneratedKeys(true);
/* 46 */     this.dbIdentity.setSupportsIdentity(true);
/*    */     
/* 48 */     this.openQuote = "[";
/* 49 */     this.closeQuote = "]";
/*    */ 
/*    */     
/* 52 */     this.dbTypeMap.put(16, new DbType("bit default 0"));
/*    */     
/* 54 */     this.dbTypeMap.put(-5, new DbType("numeric", 19));
/* 55 */     this.dbTypeMap.put(7, new DbType("float(16)"));
/* 56 */     this.dbTypeMap.put(8, new DbType("float(32)"));
/* 57 */     this.dbTypeMap.put(-6, new DbType("smallint"));
/* 58 */     this.dbTypeMap.put(3, new DbType("numeric", 28));
/*    */     
/* 60 */     this.dbTypeMap.put(2004, new DbType("image"));
/* 61 */     this.dbTypeMap.put(2005, new DbType("text"));
/* 62 */     this.dbTypeMap.put(-4, new DbType("image"));
/* 63 */     this.dbTypeMap.put(-1, new DbType("text"));
/*    */     
/* 65 */     this.dbTypeMap.put(91, new DbType("datetime"));
/* 66 */     this.dbTypeMap.put(92, new DbType("datetime"));
/* 67 */     this.dbTypeMap.put(93, new DbType("datetime"));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\MsSqlServer2005Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */