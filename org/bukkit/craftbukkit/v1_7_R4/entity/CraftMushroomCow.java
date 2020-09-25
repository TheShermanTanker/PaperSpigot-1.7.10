/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCow;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityMushroomCow;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftMushroomCow extends CraftCow implements MushroomCow {
/*    */   public CraftMushroomCow(CraftServer server, EntityMushroomCow entity) {
/* 11 */     super(server, (EntityCow)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMushroomCow getHandle() {
/* 16 */     return (EntityMushroomCow)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return "CraftMushroomCow";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.MUSHROOM_COW;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftMushroomCow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */