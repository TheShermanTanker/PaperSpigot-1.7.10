/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class ItemDoor
/*    */   extends Item {
/*    */   private Material a;
/*    */   
/*    */   public ItemDoor(Material material) {
/*  8 */     this.a = material;
/*  9 */     this.maxStackSize = 1;
/* 10 */     a(CreativeModeTab.d);
/*    */   }
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/*    */     Block block;
/* 14 */     if (l != 1) {
/* 15 */       return false;
/*    */     }
/* 17 */     j++;
/*    */ 
/*    */     
/* 20 */     if (this.a == Material.WOOD) {
/* 21 */       block = Blocks.WOODEN_DOOR;
/*    */     } else {
/* 23 */       block = Blocks.IRON_DOOR_BLOCK;
/*    */     } 
/*    */     
/* 26 */     if (entityhuman.a(i, j, k, l, itemstack) && entityhuman.a(i, j + 1, k, l, itemstack)) {
/* 27 */       if (!block.canPlace(world, i, j, k)) {
/* 28 */         return false;
/*    */       }
/* 30 */       int i1 = MathHelper.floor(((entityhuman.yaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 0x3;
/*    */       
/* 32 */       place(world, i, j, k, i1, block);
/* 33 */       itemstack.count--;
/* 34 */       return true;
/*    */     } 
/*    */     
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void place(World world, int i, int j, int k, int l, Block block) {
/* 43 */     byte b0 = 0;
/* 44 */     byte b1 = 0;
/*    */     
/* 46 */     if (l == 0) {
/* 47 */       b1 = 1;
/*    */     }
/*    */     
/* 50 */     if (l == 1) {
/* 51 */       b0 = -1;
/*    */     }
/*    */     
/* 54 */     if (l == 2) {
/* 55 */       b1 = -1;
/*    */     }
/*    */     
/* 58 */     if (l == 3) {
/* 59 */       b0 = 1;
/*    */     }
/*    */     
/* 62 */     int i1 = (world.getType(i - b0, j, k - b1).r() ? 1 : 0) + (world.getType(i - b0, j + 1, k - b1).r() ? 1 : 0);
/* 63 */     int j1 = (world.getType(i + b0, j, k + b1).r() ? 1 : 0) + (world.getType(i + b0, j + 1, k + b1).r() ? 1 : 0);
/* 64 */     boolean flag = (world.getType(i - b0, j, k - b1) == block || world.getType(i - b0, j + 1, k - b1) == block);
/* 65 */     boolean flag1 = (world.getType(i + b0, j, k + b1) == block || world.getType(i + b0, j + 1, k + b1) == block);
/* 66 */     boolean flag2 = false;
/*    */     
/* 68 */     if (flag && !flag1) {
/* 69 */       flag2 = true;
/* 70 */     } else if (j1 > i1) {
/* 71 */       flag2 = true;
/*    */     } 
/*    */ 
/*    */     
/* 75 */     world.setTypeAndData(i, j, k, block, l, 3);
/* 76 */     world.setTypeAndData(i, j + 1, k, block, 0x8 | (flag2 ? 1 : 0), 3);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */