/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BinaryMessageList
/*    */ {
/* 32 */   ArrayList<BinaryMessage> list = new ArrayList<BinaryMessage>();
/*    */   
/*    */   public void add(BinaryMessage msg) {
/* 35 */     this.list.add(msg);
/*    */   }
/*    */   
/*    */   public List<BinaryMessage> getList() {
/* 39 */     return this.list;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\BinaryMessageList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */