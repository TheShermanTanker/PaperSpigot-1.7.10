/*    */ package com.avaje.ebeaninternal.server.jdbc;
/*    */ 
/*    */ import com.avaje.ebean.config.PstmtDelegate;
/*    */ import com.avaje.ebeaninternal.server.lib.sql.ExtendedPreparedStatement;
/*    */ import java.sql.PreparedStatement;
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
/*    */ public class StandardPstmtDelegate
/*    */   implements PstmtDelegate
/*    */ {
/*    */   public PreparedStatement unwrap(PreparedStatement pstmt) {
/* 40 */     return ((ExtendedPreparedStatement)pstmt).getDelegate();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\jdbc\StandardPstmtDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */