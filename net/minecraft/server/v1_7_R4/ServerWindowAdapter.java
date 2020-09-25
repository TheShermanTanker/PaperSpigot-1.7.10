/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
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
/*    */ final class ServerWindowAdapter
/*    */   extends WindowAdapter
/*    */ {
/*    */   ServerWindowAdapter(DedicatedServer paramDedicatedServer) {}
/*    */   
/*    */   public void windowClosing(WindowEvent paramWindowEvent) {
/* 40 */     this.a.safeShutdown();
/* 41 */     while (!this.a.isStopped()) {
/*    */       try {
/* 43 */         Thread.sleep(100L);
/* 44 */       } catch (InterruptedException interruptedException) {
/* 45 */         interruptedException.printStackTrace();
/*    */       } 
/*    */     } 
/* 48 */     System.exit(0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerWindowAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */