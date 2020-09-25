/*    */ package com.avaje.ebean.bean;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ObjectGraphNode
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2087081778650228996L;
/*    */   private final ObjectGraphOrigin originQueryPoint;
/*    */   private final String path;
/*    */   
/*    */   public ObjectGraphNode(ObjectGraphNode parent, String path) {
/* 50 */     this.originQueryPoint = parent.getOriginQueryPoint();
/* 51 */     this.path = parent.getChildPath(path);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectGraphNode(ObjectGraphOrigin originQueryPoint, String path) {
/* 58 */     this.originQueryPoint = originQueryPoint;
/* 59 */     this.path = path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectGraphOrigin getOriginQueryPoint() {
/* 66 */     return this.originQueryPoint;
/*    */   }
/*    */   
/*    */   private String getChildPath(String childPath) {
/* 70 */     if (this.path == null)
/* 71 */       return childPath; 
/* 72 */     if (childPath == null) {
/* 73 */       return this.path;
/*    */     }
/* 75 */     return this.path + "." + childPath;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 83 */     return this.path;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     return "origin:" + this.originQueryPoint + " " + ":" + this.path + ":" + this.path;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\ObjectGraphNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */