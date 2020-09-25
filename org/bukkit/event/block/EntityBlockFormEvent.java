/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityBlockFormEvent
/*    */   extends BlockFormEvent
/*    */ {
/*    */   private final Entity entity;
/*    */   
/*    */   public EntityBlockFormEvent(Entity entity, Block block, BlockState blockstate) {
/* 19 */     super(block, blockstate);
/*    */     
/* 21 */     this.entity = entity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity getEntity() {
/* 30 */     return this.entity;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\EntityBlockFormEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */