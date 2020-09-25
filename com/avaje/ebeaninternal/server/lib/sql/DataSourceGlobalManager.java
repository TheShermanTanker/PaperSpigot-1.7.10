/*    */ package com.avaje.ebeaninternal.server.lib.sql;
/*    */ 
/*    */ import com.avaje.ebean.config.DataSourceConfig;
/*    */ import java.util.List;
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
/*    */ public final class DataSourceGlobalManager
/*    */ {
/* 29 */   private static final DataSourceManager manager = new DataSourceManager();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isShuttingDown() {
/* 38 */     return manager.isShuttingDown();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void shutdown() {
/* 45 */     manager.shutdown();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<DataSourcePool> getPools() {
/* 52 */     return manager.getPools();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataSourcePool getDataSource(String name) {
/* 59 */     return manager.getDataSource(name);
/*    */   }
/*    */   
/*    */   public static DataSourcePool getDataSource(String name, DataSourceConfig dsConfig) {
/* 63 */     return manager.getDataSource(name, dsConfig);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceGlobalManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */