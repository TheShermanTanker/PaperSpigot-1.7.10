/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityHanging;
/*    */ import net.minecraft.server.v1_7_R4.EntityItemFrame;
/*    */ import net.minecraft.server.v1_7_R4.WorldServer;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Rotation;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.ItemFrame;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftItemFrame extends CraftHanging implements ItemFrame {
/*    */   public CraftItemFrame(CraftServer server, EntityItemFrame entity) {
/* 18 */     super(server, (EntityHanging)entity);
/*    */   }
/*    */   
/*    */   public boolean setFacingDirection(BlockFace face, boolean force) {
/* 22 */     if (!super.setFacingDirection(face, force)) {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     WorldServer world = ((CraftWorld)getWorld()).getHandle();
/* 27 */     world.getTracker().untrackEntity((Entity)getHandle());
/* 28 */     world.getTracker().track((Entity)getHandle());
/* 29 */     return true;
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack item) {
/* 33 */     if (item == null || item.getTypeId() == 0) {
/* 34 */       getHandle().getDataWatcher().add(2, 5);
/* 35 */       getHandle().getDataWatcher().update(2);
/*    */     } else {
/* 37 */       getHandle().setItem(CraftItemStack.asNMSCopy(item));
/*    */     } 
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 42 */     return CraftItemStack.asBukkitCopy(getHandle().getItem());
/*    */   }
/*    */   
/*    */   public Rotation getRotation() {
/* 46 */     return toBukkitRotation(getHandle().getRotation());
/*    */   }
/*    */ 
/*    */   
/*    */   Rotation toBukkitRotation(int value) {
/* 51 */     switch (value) {
/*    */       case 0:
/* 53 */         return Rotation.NONE;
/*    */       case 1:
/* 55 */         return Rotation.CLOCKWISE;
/*    */       case 2:
/* 57 */         return Rotation.FLIPPED;
/*    */       case 3:
/* 59 */         return Rotation.COUNTER_CLOCKWISE;
/*    */     } 
/* 61 */     throw new AssertionError("Unknown rotation " + value + " for " + getHandle());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRotation(Rotation rotation) {
/* 66 */     Validate.notNull(rotation, "Rotation cannot be null");
/* 67 */     getHandle().setRotation(toInteger(rotation));
/*    */   }
/*    */ 
/*    */   
/*    */   static int toInteger(Rotation rotation) {
/* 72 */     switch (rotation) {
/*    */       case NONE:
/* 74 */         return 0;
/*    */       case CLOCKWISE:
/* 76 */         return 1;
/*    */       case FLIPPED:
/* 78 */         return 2;
/*    */       case COUNTER_CLOCKWISE:
/* 80 */         return 3;
/*    */     } 
/* 82 */     throw new IllegalArgumentException(rotation + " is not applicable to an ItemFrame");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityItemFrame getHandle() {
/* 88 */     return (EntityItemFrame)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 93 */     return "CraftItemFrame{item=" + getItem() + ", rotation=" + getRotation() + "}";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 97 */     return EntityType.ITEM_FRAME;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftItemFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */