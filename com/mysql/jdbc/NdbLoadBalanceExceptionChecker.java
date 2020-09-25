/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class NdbLoadBalanceExceptionChecker
/*    */   extends StandardLoadBalanceExceptionChecker
/*    */ {
/*    */   public boolean shouldExceptionTriggerFailover(SQLException ex) {
/*  9 */     return (super.shouldExceptionTriggerFailover(ex) || checkNdbException(ex));
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean checkNdbException(SQLException ex) {
/* 14 */     return (ex.getMessage().startsWith("Lock wait timeout exceeded") || (ex.getMessage().startsWith("Got temporary error") && ex.getMessage().endsWith("from NDB")));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\NdbLoadBalanceExceptionChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */