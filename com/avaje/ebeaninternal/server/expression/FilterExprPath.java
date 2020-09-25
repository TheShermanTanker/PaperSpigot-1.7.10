/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class FilterExprPath
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6420905565372842018L;
/*    */   private String path;
/*    */   
/*    */   public FilterExprPath(String path) {
/* 43 */     this.path = path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void trimPath(int prefixTrim) {
/* 51 */     this.path = this.path.substring(prefixTrim);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 58 */     return this.path;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\expression\FilterExprPath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */