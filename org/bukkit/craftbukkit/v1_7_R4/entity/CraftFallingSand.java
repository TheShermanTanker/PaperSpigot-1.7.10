/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityFallingBlock;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.FallingSand;
/*    */ 
/*    */ public class CraftFallingSand extends CraftEntity implements FallingSand {
/*    */   public CraftFallingSand(CraftServer server, EntityFallingBlock entity) {
/* 14 */     super(server, (Entity)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityFallingBlock getHandle() {
/* 19 */     return (EntityFallingBlock)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 24 */     return "CraftFallingSand";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 28 */     return EntityType.FALLING_BLOCK;
/*    */   }
/*    */   
/*    */   public Material getMaterial() {
/* 32 */     return Material.getMaterial(getBlockId());
/*    */   }
/*    */   
/*    */   public int getBlockId() {
/* 36 */     return CraftMagicNumbers.getId((getHandle()).id);
/*    */   }
/*    */   
/*    */   public byte getBlockData() {
/* 40 */     return (byte)(getHandle()).data;
/*    */   }
/*    */   
/*    */   public boolean getDropItem() {
/* 44 */     return (getHandle()).dropItem;
/*    */   }
/*    */   
/*    */   public void setDropItem(boolean drop) {
/* 48 */     (getHandle()).dropItem = drop;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Location getSourceLoc() {
/* 54 */     return (getHandle()).sourceLoc;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftFallingSand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */