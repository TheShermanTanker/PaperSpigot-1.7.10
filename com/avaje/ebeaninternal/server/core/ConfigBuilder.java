/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigBuilder
/*    */ {
/*    */   public ServerConfig build(String serverName) {
/* 16 */     ServerConfig config = new ServerConfig();
/* 17 */     config.setName(serverName);
/*    */     
/* 19 */     config.loadFromProperties();
/*    */     
/* 21 */     return config;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\ConfigBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */