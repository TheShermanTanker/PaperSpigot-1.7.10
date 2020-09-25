/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ThreadSleepForever
/*    */   extends Thread
/*    */ {
/*    */   ThreadSleepForever(DedicatedServer paramDedicatedServer, String paramString) {
/* 53 */     super(paramString);
/*    */     
/* 55 */     setDaemon(true);
/* 56 */     start();
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     while (true) {
/*    */       try {
/*    */         while (true)
/* 63 */           Thread.sleep(2147483647L);  break;
/* 64 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ThreadSleepForever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */