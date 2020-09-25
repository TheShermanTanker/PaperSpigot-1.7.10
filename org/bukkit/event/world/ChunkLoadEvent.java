/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class ChunkLoadEvent
/*    */   extends ChunkEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final boolean newChunk;
/*    */   
/*    */   public ChunkLoadEvent(Chunk chunk, boolean newChunk) {
/* 14 */     super(chunk);
/* 15 */     this.newChunk = newChunk;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isNewChunk() {
/* 26 */     return this.newChunk;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 31 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 35 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\ChunkLoadEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */