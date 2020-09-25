/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ 
/*    */ public class ItemBoat extends Item {
/*    */   public ItemBoat() {
/*  8 */     this.maxStackSize = 1;
/*  9 */     a(CreativeModeTab.e);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 13 */     float f = 1.0F;
/* 14 */     float f1 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
/* 15 */     float f2 = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * f;
/* 16 */     double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * f;
/* 17 */     double d1 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * f + 1.62D - entityhuman.height;
/* 18 */     double d2 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * f;
/* 19 */     Vec3D vec3d = Vec3D.a(d0, d1, d2);
/* 20 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 21 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 22 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 23 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 24 */     float f7 = f4 * f5;
/* 25 */     float f8 = f3 * f5;
/* 26 */     double d3 = 5.0D;
/* 27 */     Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/* 28 */     MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, true);
/*    */     
/* 30 */     if (movingobjectposition == null) {
/* 31 */       return itemstack;
/*    */     }
/* 33 */     Vec3D vec3d2 = entityhuman.j(f);
/* 34 */     boolean flag = false;
/* 35 */     float f9 = 1.0F;
/* 36 */     List<Entity> list = world.getEntities(entityhuman, entityhuman.boundingBox.a(vec3d2.a * d3, vec3d2.b * d3, vec3d2.c * d3).grow(f9, f9, f9));
/*    */     
/*    */     int i;
/*    */     
/* 40 */     for (i = 0; i < list.size(); i++) {
/* 41 */       Entity entity = list.get(i);
/*    */       
/* 43 */       if (entity.R()) {
/* 44 */         float f10 = entity.af();
/* 45 */         AxisAlignedBB axisalignedbb = entity.boundingBox.grow(f10, f10, f10);
/*    */         
/* 47 */         if (axisalignedbb.a(vec3d)) {
/* 48 */           flag = true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     if (flag) {
/* 54 */       return itemstack;
/*    */     }
/* 56 */     if (movingobjectposition.type == EnumMovingObjectType.BLOCK) {
/* 57 */       i = movingobjectposition.b;
/* 58 */       int j = movingobjectposition.c;
/* 59 */       int k = movingobjectposition.d;
/*    */ 
/*    */       
/* 62 */       PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, movingobjectposition.face, itemstack);
/*    */       
/* 64 */       if (event.isCancelled()) {
/* 65 */         return itemstack;
/*    */       }
/*    */ 
/*    */       
/* 69 */       if (world.getType(i, j, k) == Blocks.SNOW) {
/* 70 */         j--;
/*    */       }
/*    */       
/* 73 */       EntityBoat entityboat = new EntityBoat(world, (i + 0.5F), (j + 1.0F), (k + 0.5F));
/*    */       
/* 75 */       entityboat.yaw = (((MathHelper.floor((entityhuman.yaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/* 76 */       if (!world.getCubes(entityboat, entityboat.boundingBox.grow(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 77 */         return itemstack;
/*    */       }
/*    */       
/* 80 */       if (!world.isStatic) {
/* 81 */         world.addEntity(entityboat);
/*    */       }
/*    */       
/* 84 */       if (!entityhuman.abilities.canInstantlyBuild) {
/* 85 */         itemstack.count--;
/*    */       }
/*    */     } 
/*    */     
/* 89 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemBoat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */