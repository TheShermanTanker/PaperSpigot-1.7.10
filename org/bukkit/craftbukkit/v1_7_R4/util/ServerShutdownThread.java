/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.ExceptionWorldConflict;
/*    */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*    */ 
/*    */ public class ServerShutdownThread extends Thread {
/*    */   private final MinecraftServer server;
/*    */   
/*    */   public ServerShutdownThread(MinecraftServer server) {
/* 10 */     this.server = server;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 16 */       this.server.stop();
/* 17 */     } catch (ExceptionWorldConflict ex) {
/* 18 */       ex.printStackTrace();
/*    */     } finally {
/*    */       try {
/* 21 */         this.server.reader.getTerminal().restore();
/* 22 */       } catch (Exception e) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\ServerShutdownThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */