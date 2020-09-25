/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityEnderSignal;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EnderSignal;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftEnderSignal extends CraftEntity implements EnderSignal {
/*    */   public CraftEnderSignal(CraftServer server, EntityEnderSignal entity) {
/* 10 */     super(server, (Entity)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityEnderSignal getHandle() {
/* 15 */     return (EntityEnderSignal)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftEnderSignal";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.ENDER_SIGNAL;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftEnderSignal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */