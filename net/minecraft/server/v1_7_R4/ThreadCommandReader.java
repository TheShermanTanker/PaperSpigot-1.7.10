/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.bukkit.craftbukkit.Main;
/*    */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*    */ 
/*    */ 
/*    */ class ThreadCommandReader
/*    */   extends Thread
/*    */ {
/*    */   final DedicatedServer server;
/*    */   
/*    */   ThreadCommandReader(DedicatedServer dedicatedserver, String s) {
/* 14 */     super(s);
/* 15 */     this.server = dedicatedserver;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 20 */     if (!Main.useConsole) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 25 */     ConsoleReader bufferedreader = this.server.reader;
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 30 */       while (!this.server.isStopped() && this.server.isRunning()) {
/* 31 */         String s; if (Main.useJline) {
/* 32 */           s = bufferedreader.readLine(">", null);
/*    */         } else {
/* 34 */           s = bufferedreader.readLine();
/*    */         } 
/* 36 */         if (s != null) {
/* 37 */           this.server.issueCommand(s, this.server);
/*    */         }
/*    */       }
/*    */     
/* 41 */     } catch (IOException ioexception) {
/* 42 */       DedicatedServer.aF().error("Exception handling console input", ioexception);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ThreadCommandReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */