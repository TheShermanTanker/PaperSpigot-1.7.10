/*    */ package org.spigotmc;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*    */ import org.github.paperspigot.PaperSpigotConfig;
/*    */ 
/*    */ public class AsyncCatcher
/*    */ {
/*  8 */   public static boolean enabled = PaperSpigotConfig.asyncCatcherFeature;
/*    */ 
/*    */   
/*    */   public static void catchOp(String reason) {
/* 12 */     if (enabled && Thread.currentThread() != (MinecraftServer.getServer()).primaryThread)
/*    */     {
/* 14 */       throw new IllegalStateException("Asynchronous " + reason + "!");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\AsyncCatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */