/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Ebean;
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.TxScope;
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
/*    */ public class HelpScopeTrans
/*    */ {
/*    */   public static ScopeTrans createScopeTrans(TxScope txScope) {
/* 36 */     EbeanServer server = Ebean.getServer(txScope.getServerName());
/* 37 */     SpiEbeanServer iserver = (SpiEbeanServer)server;
/* 38 */     return iserver.createScopeTrans(txScope);
/*    */   }
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
/*    */   public static void onExitScopeTrans(Object returnOrThrowable, int opCode, ScopeTrans scopeTrans) {
/* 53 */     scopeTrans.onExit(returnOrThrowable, opCode);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\HelpScopeTrans.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */