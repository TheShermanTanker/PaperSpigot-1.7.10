/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCaveSpider;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntitySpider;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftCaveSpider extends CraftSpider implements CaveSpider {
/*    */   public CraftCaveSpider(CraftServer server, EntityCaveSpider entity) {
/* 11 */     super(server, (EntitySpider)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityCaveSpider getHandle() {
/* 16 */     return (EntityCaveSpider)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return "CraftCaveSpider";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.CAVE_SPIDER;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftCaveSpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */