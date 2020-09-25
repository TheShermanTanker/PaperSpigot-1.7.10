/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityHanging;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Hanging;
/*    */ 
/*    */ public class CraftHanging extends CraftEntity implements Hanging {
/*    */   public CraftHanging(CraftServer server, EntityHanging entity) {
/* 12 */     super(server, (Entity)entity);
/*    */   }
/*    */   
/*    */   public BlockFace getAttachedFace() {
/* 16 */     return getFacing().getOppositeFace();
/*    */   }
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/* 20 */     setFacingDirection(face, false);
/*    */   }
/*    */   
/*    */   public boolean setFacingDirection(BlockFace face, boolean force) {
/* 24 */     Block block = getLocation().getBlock().getRelative(getAttachedFace()).getRelative(face.getOppositeFace()).getRelative(getFacing());
/* 25 */     EntityHanging hanging = getHandle();
/* 26 */     int x = hanging.x, y = hanging.y, z = hanging.z, dir = hanging.direction;
/* 27 */     hanging.x = block.getX();
/* 28 */     hanging.y = block.getY();
/* 29 */     hanging.z = block.getZ();
/* 30 */     switch (face) {
/*    */       
/*    */       default:
/* 33 */         getHandle().setDirection(0);
/*    */         break;
/*    */       case WEST:
/* 36 */         getHandle().setDirection(1);
/*    */         break;
/*    */       case NORTH:
/* 39 */         getHandle().setDirection(2);
/*    */         break;
/*    */       case EAST:
/* 42 */         getHandle().setDirection(3);
/*    */         break;
/*    */     } 
/* 45 */     if (!force && !hanging.survives()) {
/*    */       
/* 47 */       hanging.x = x;
/* 48 */       hanging.y = y;
/* 49 */       hanging.z = z;
/* 50 */       hanging.setDirection(dir);
/* 51 */       return false;
/*    */     } 
/* 53 */     return true;
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 57 */     switch ((getHandle()).direction)
/*    */     
/*    */     { default:
/* 60 */         return BlockFace.SOUTH;
/*    */       case 1:
/* 62 */         return BlockFace.WEST;
/*    */       case 2:
/* 64 */         return BlockFace.NORTH;
/*    */       case 3:
/* 66 */         break; }  return BlockFace.EAST;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityHanging getHandle() {
/* 72 */     return (EntityHanging)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 77 */     return "CraftHanging";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 81 */     return EntityType.UNKNOWN;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftHanging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */