/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class ItemMapEmpty extends ItemWorldMapBase {
/*    */   protected ItemMapEmpty() {
/*  6 */     a(CreativeModeTab.f);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 10 */     World worldMain = (world.getServer().getServer()).worlds.get(0);
/* 11 */     ItemStack itemstack1 = new ItemStack(Items.MAP, 1, worldMain.b("map"));
/* 12 */     String s = "map_" + itemstack1.getData();
/* 13 */     WorldMap worldmap = new WorldMap(s);
/*    */     
/* 15 */     worldMain.a(s, worldmap);
/* 16 */     worldmap.scale = 0;
/* 17 */     int i = 128 * (1 << worldmap.scale);
/*    */     
/* 19 */     worldmap.centerX = (int)(Math.round(entityhuman.locX / i) * i);
/* 20 */     worldmap.centerZ = (int)(Math.round(entityhuman.locZ / i) * i);
/* 21 */     worldmap.map = (byte)((WorldServer)world).dimension;
/* 22 */     worldmap.c();
/*    */     
/* 24 */     CraftEventFactory.callEvent((Event)new MapInitializeEvent((MapView)worldmap.mapView));
/*    */     
/* 26 */     itemstack.count--;
/* 27 */     if (itemstack.count <= 0) {
/* 28 */       return itemstack1;
/*    */     }
/* 30 */     if (!entityhuman.inventory.pickup(itemstack1.cloneItemStack())) {
/* 31 */       entityhuman.drop(itemstack1, false);
/*    */     }
/*    */     
/* 34 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemMapEmpty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */