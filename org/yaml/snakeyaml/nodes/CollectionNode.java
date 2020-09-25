/*    */ package org.yaml.snakeyaml.nodes;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
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
/*    */ public abstract class CollectionNode
/*    */   extends Node
/*    */ {
/*    */   private Boolean flowStyle;
/*    */   
/*    */   public CollectionNode(Tag tag, Mark startMark, Mark endMark, Boolean flowStyle) {
/* 28 */     super(tag, startMark, endMark);
/* 29 */     this.flowStyle = flowStyle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Boolean getFlowStyle() {
/* 39 */     return this.flowStyle;
/*    */   }
/*    */   
/*    */   public void setFlowStyle(Boolean flowStyle) {
/* 43 */     this.flowStyle = flowStyle;
/*    */   }
/*    */   
/*    */   public void setEndMark(Mark endMark) {
/* 47 */     this.endMark = endMark;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\nodes\CollectionNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */