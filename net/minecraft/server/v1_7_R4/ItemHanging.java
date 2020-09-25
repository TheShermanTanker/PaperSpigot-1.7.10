/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;
/*    */ import org.bukkit.entity.Hanging;
/*    */ import org.bukkit.entity.Painting;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.hanging.HangingPlaceEvent;
/*    */ import org.bukkit.event.painting.PaintingPlaceEvent;
/*    */ 
/*    */ public class ItemHanging extends Item {
/*    */   public ItemHanging(Class oclass) {
/* 14 */     this.a = oclass;
/* 15 */     a(CreativeModeTab.c);
/*    */   }
/*    */   private final Class a;
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/* 19 */     if (l == 0)
/* 20 */       return false; 
/* 21 */     if (l == 1) {
/* 22 */       return false;
/*    */     }
/* 24 */     int i1 = Direction.e[l];
/* 25 */     EntityHanging entityhanging = a(world, i, j, k, i1);
/*    */     
/* 27 */     if (!entityhuman.a(i, j, k, l, itemstack)) {
/* 28 */       return false;
/*    */     }
/* 30 */     if (entityhanging != null && entityhanging.survives()) {
/* 31 */       if (!world.isStatic) {
/*    */         
/* 33 */         Player who = (entityhuman == null) ? null : (Player)entityhuman.getBukkitEntity();
/* 34 */         Block blockClicked = world.getWorld().getBlockAt(i, j, k);
/* 35 */         BlockFace blockFace = CraftBlock.notchToBlockFace(l);
/*    */         
/* 37 */         HangingPlaceEvent event = new HangingPlaceEvent((Hanging)entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
/* 38 */         world.getServer().getPluginManager().callEvent((Event)event);
/*    */         
/* 40 */         PaintingPlaceEvent paintingEvent = null;
/* 41 */         if (entityhanging instanceof EntityPainting) {
/*    */           
/* 43 */           paintingEvent = new PaintingPlaceEvent((Painting)entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
/* 44 */           paintingEvent.setCancelled(event.isCancelled());
/* 45 */           world.getServer().getPluginManager().callEvent((Event)paintingEvent);
/*    */         } 
/*    */         
/* 48 */         if (event.isCancelled() || (paintingEvent != null && paintingEvent.isCancelled())) {
/* 49 */           return false;
/*    */         }
/*    */ 
/*    */         
/* 53 */         world.addEntity(entityhanging);
/*    */       } 
/*    */       
/* 56 */       itemstack.count--;
/*    */     } 
/*    */     
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private EntityHanging a(World world, int i, int j, int k, int l) {
/* 65 */     return (this.a == EntityPainting.class) ? new EntityPainting(world, i, j, k, l) : ((this.a == EntityItemFrame.class) ? new EntityItemFrame(world, i, j, k, l) : null);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemHanging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */