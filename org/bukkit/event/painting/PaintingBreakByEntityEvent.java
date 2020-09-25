/*    */ package org.bukkit.event.painting;
/*    */ 
/*    */ import org.bukkit.Warning;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Painting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ @Warning(reason = "This event has been replaced by HangingBreakByEntityEvent")
/*    */ public class PaintingBreakByEntityEvent
/*    */   extends PaintingBreakEvent
/*    */ {
/*    */   private final Entity remover;
/*    */   
/*    */   public PaintingBreakByEntityEvent(Painting painting, Entity remover) {
/* 19 */     super(painting, PaintingBreakEvent.RemoveCause.ENTITY);
/* 20 */     this.remover = remover;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity getRemover() {
/* 29 */     return this.remover;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\painting\PaintingBreakByEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */