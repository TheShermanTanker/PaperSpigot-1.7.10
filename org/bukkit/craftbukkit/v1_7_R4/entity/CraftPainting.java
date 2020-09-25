/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityHanging;
/*    */ import net.minecraft.server.v1_7_R4.EntityPainting;
/*    */ import net.minecraft.server.v1_7_R4.EnumArt;
/*    */ import net.minecraft.server.v1_7_R4.World;
/*    */ import net.minecraft.server.v1_7_R4.WorldServer;
/*    */ import org.bukkit.Art;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftArt;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Painting;
/*    */ 
/*    */ public class CraftPainting extends CraftHanging implements Painting {
/*    */   public CraftPainting(CraftServer server, EntityPainting entity) {
/* 18 */     super(server, (EntityHanging)entity);
/*    */   }
/*    */   
/*    */   public Art getArt() {
/* 22 */     EnumArt art = (getHandle()).art;
/* 23 */     return CraftArt.NotchToBukkit(art);
/*    */   }
/*    */   
/*    */   public boolean setArt(Art art) {
/* 27 */     return setArt(art, false);
/*    */   }
/*    */   
/*    */   public boolean setArt(Art art, boolean force) {
/* 31 */     EntityPainting painting = getHandle();
/* 32 */     EnumArt oldArt = painting.art;
/* 33 */     painting.art = CraftArt.BukkitToNotch(art);
/* 34 */     painting.setDirection(painting.direction);
/* 35 */     if (!force && !painting.survives()) {
/*    */       
/* 37 */       painting.art = oldArt;
/* 38 */       painting.setDirection(painting.direction);
/* 39 */       return false;
/*    */     } 
/* 41 */     update();
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public boolean setFacingDirection(BlockFace face, boolean force) {
/* 46 */     if (super.setFacingDirection(face, force)) {
/* 47 */       update();
/* 48 */       return true;
/*    */     } 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   private void update() {
/* 55 */     WorldServer world = ((CraftWorld)getWorld()).getHandle();
/* 56 */     EntityPainting painting = new EntityPainting((World)world);
/* 57 */     painting.x = (getHandle()).x;
/* 58 */     painting.y = (getHandle()).y;
/* 59 */     painting.z = (getHandle()).z;
/* 60 */     painting.art = (getHandle()).art;
/* 61 */     painting.setDirection((getHandle()).direction);
/* 62 */     getHandle().die();
/* 63 */     (getHandle()).velocityChanged = true;
/* 64 */     world.addEntity((Entity)painting);
/* 65 */     this.entity = (Entity)painting;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPainting getHandle() {
/* 70 */     return (EntityPainting)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return "CraftPainting{art=" + getArt() + "}";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 79 */     return EntityType.PAINTING;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftPainting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */