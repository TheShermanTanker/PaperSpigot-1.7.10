/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ public class Loggers
/*    */ {
/*    */   private final ConcurrentMap<String, LoggerConfig> map;
/*    */   private final LoggerConfig root;
/*    */   
/*    */   public Loggers(ConcurrentMap<String, LoggerConfig> map, LoggerConfig root) {
/* 29 */     this.map = map;
/* 30 */     this.root = root;
/*    */   }
/*    */   
/*    */   public ConcurrentMap<String, LoggerConfig> getMap() {
/* 34 */     return this.map;
/*    */   }
/*    */   
/*    */   public LoggerConfig getRoot() {
/* 38 */     return this.root;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\Loggers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */