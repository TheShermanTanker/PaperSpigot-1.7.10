/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.Warning;
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ @Warning(reason = "This event has been replaced by HangingEvent")
/*    */ public abstract class PaintingEvent
/*    */   extends Event
/*    */ {
/*    */   protected Painting painting;
/*    */   
/*    */   protected PaintingEvent(Painting painting) {
/* 18 */     this.painting = painting;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Painting getPainting() {
/* 27 */     return this.painting;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\painting\PaintingEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */