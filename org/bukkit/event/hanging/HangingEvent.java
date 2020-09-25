/*    */ package org.bukkit.event.hanging;
/*    */ 
/*    */ import org.bukkit.entity.Hanging;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public abstract class HangingEvent
/*    */   extends Event
/*    */ {
/*    */   protected Hanging hanging;
/*    */   
/*    */   protected HangingEvent(Hanging painting) {
/* 13 */     this.hanging = painting;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Hanging getEntity() {
/* 22 */     return this.hanging;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\hanging\HangingEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */