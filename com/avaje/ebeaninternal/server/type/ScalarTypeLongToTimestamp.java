/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.sql.Timestamp;
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
/*    */ public class ScalarTypeLongToTimestamp
/*    */   extends ScalarTypeWrapper<Long, Timestamp>
/*    */ {
/*    */   public ScalarTypeLongToTimestamp() {
/* 27 */     super(Long.class, new ScalarTypeTimestamp(), new LongToTimestampConverter());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLongToTimestamp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */