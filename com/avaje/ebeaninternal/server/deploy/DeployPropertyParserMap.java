/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DeployPropertyParserMap
/*    */   extends DeployParser
/*    */ {
/*    */   private final Map<String, String> map;
/*    */   
/*    */   public DeployPropertyParserMap(Map<String, String> map) {
/* 14 */     this.map = map;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getIncludes() {
/* 21 */     return null;
/*    */   }
/*    */   
/*    */   public String convertWord() {
/* 25 */     String r = getDeployWord(this.word);
/* 26 */     return (r == null) ? this.word : r;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDeployWord(String expression) {
/* 32 */     String deployExpr = this.map.get(expression);
/* 33 */     if (deployExpr == null) {
/* 34 */       return null;
/*    */     }
/* 36 */     return deployExpr;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DeployPropertyParserMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */