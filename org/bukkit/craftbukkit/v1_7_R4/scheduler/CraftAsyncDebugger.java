/*    */ package org.bukkit.craftbukkit.v1_7_R4.scheduler;
/*    */ 
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ class CraftAsyncDebugger
/*    */ {
/*  7 */   private CraftAsyncDebugger next = null;
/*    */   private final int expiry;
/*    */   private final Plugin plugin;
/*    */   private final Class<? extends Runnable> clazz;
/*    */   
/*    */   CraftAsyncDebugger(int expiry, Plugin plugin, Class<? extends Runnable> clazz) {
/* 13 */     this.expiry = expiry;
/* 14 */     this.plugin = plugin;
/* 15 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   
/*    */   final CraftAsyncDebugger getNextHead(int time) {
/* 20 */     CraftAsyncDebugger current = this; CraftAsyncDebugger next;
/* 21 */     while (time > current.expiry && (next = current.next) != null) {
/* 22 */       current = next;
/*    */     }
/* 24 */     return current;
/*    */   }
/*    */   
/*    */   final CraftAsyncDebugger setNext(CraftAsyncDebugger next) {
/* 28 */     return this.next = next;
/*    */   }
/*    */   
/*    */   StringBuilder debugTo(StringBuilder string) {
/* 32 */     for (CraftAsyncDebugger next = this; next != null; next = next.next) {
/* 33 */       string.append(next.plugin.getDescription().getName()).append(':').append(next.clazz.getName()).append('@').append(next.expiry).append(',');
/*    */     }
/* 35 */     return string;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scheduler\CraftAsyncDebugger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */