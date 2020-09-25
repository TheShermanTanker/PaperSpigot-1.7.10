/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityEnderman;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityMonster;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ public class CraftEnderman extends CraftMonster implements Enderman {
/*    */   public CraftEnderman(CraftServer server, EntityEnderman entity) {
/* 13 */     super(server, (EntityMonster)entity);
/*    */   }
/*    */   
/*    */   public MaterialData getCarriedMaterial() {
/* 17 */     return CraftMagicNumbers.getMaterial(getHandle().getCarried()).getNewData((byte)getHandle().getCarriedData());
/*    */   }
/*    */   
/*    */   public void setCarriedMaterial(MaterialData data) {
/* 21 */     getHandle().setCarried(CraftMagicNumbers.getBlock(data.getItemTypeId()));
/* 22 */     getHandle().setCarriedData(data.getData());
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityEnderman getHandle() {
/* 27 */     return (EntityEnderman)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return "CraftEnderman";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 36 */     return EntityType.ENDERMAN;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftEnderman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */