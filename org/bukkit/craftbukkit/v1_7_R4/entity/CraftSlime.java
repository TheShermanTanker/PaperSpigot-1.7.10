/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntitySlime;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Slime;
/*    */ 
/*    */ public class CraftSlime extends CraftLivingEntity implements Slime {
/*    */   public CraftSlime(CraftServer server, EntitySlime entity) {
/* 12 */     super(server, (EntityLiving)entity);
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 16 */     return getHandle().getSize();
/*    */   }
/*    */   
/*    */   public void setSize(int size) {
/* 20 */     getHandle().setSize(size);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySlime getHandle() {
/* 25 */     return (EntitySlime)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 30 */     return "CraftSlime";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 34 */     return EntityType.SLIME;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftSlime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */