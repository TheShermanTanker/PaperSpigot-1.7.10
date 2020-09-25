/*    */ package com.avaje.ebeaninternal.server.deploy.meta;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class DeployBeanEmbedded
/*    */ {
/* 37 */   Map<String, String> propMap = new HashMap<String, String>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void put(String propertyName, String dbCoumn) {
/* 43 */     this.propMap.put(propertyName, dbCoumn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void putAll(Map<String, String> propertyColumnMap) {
/* 50 */     this.propMap.putAll(propertyColumnMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, String> getPropertyColumnMap() {
/* 57 */     return this.propMap;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */