/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityAnimal;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntitySheep;
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftSheep extends CraftAnimals implements Sheep {
/*    */   public CraftSheep(CraftServer server, EntitySheep entity) {
/* 12 */     super(server, (EntityAnimal)entity);
/*    */   }
/*    */   
/*    */   public DyeColor getColor() {
/* 16 */     return DyeColor.getByWoolData((byte)getHandle().getColor());
/*    */   }
/*    */   
/*    */   public void setColor(DyeColor color) {
/* 20 */     getHandle().setColor(color.getWoolData());
/*    */   }
/*    */   
/*    */   public boolean isSheared() {
/* 24 */     return getHandle().isSheared();
/*    */   }
/*    */   
/*    */   public void setSheared(boolean flag) {
/* 28 */     getHandle().setSheared(flag);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySheep getHandle() {
/* 33 */     return (EntitySheep)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "CraftSheep";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 42 */     return EntityType.SHEEP;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftSheep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */