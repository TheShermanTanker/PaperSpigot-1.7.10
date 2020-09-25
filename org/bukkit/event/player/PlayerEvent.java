/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public abstract class PlayerEvent
/*    */   extends Event
/*    */ {
/*    */   protected Player player;
/*    */   
/*    */   public PlayerEvent(Player who) {
/* 13 */     this.player = who;
/*    */   }
/*    */   
/*    */   PlayerEvent(Player who, boolean async) {
/* 17 */     super(async);
/* 18 */     this.player = who;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final Player getPlayer() {
/* 28 */     return this.player;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */