/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockEnderChest
/*    */   extends BlockContainer
/*    */ {
/*    */   protected BlockEnderChest() {
/* 29 */     super(Material.STONE);
/* 30 */     a(CreativeModeTab.c);
/*    */     
/* 32 */     a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean c() {
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b() {
/* 47 */     return 22;
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 52 */     return Item.getItemOf(Blocks.OBSIDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 57 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean E() {
/* 62 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack) {
/* 68 */     byte b = 0;
/* 69 */     int i = MathHelper.floor((paramEntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/*    */     
/* 71 */     if (i == 0) b = 2; 
/* 72 */     if (i == 1) b = 5; 
/* 73 */     if (i == 2) b = 3; 
/* 74 */     if (i == 3) b = 4;
/*    */     
/* 76 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, b, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 81 */     InventoryEnderChest inventoryEnderChest = paramEntityHuman.getEnderChest();
/* 82 */     TileEntityEnderChest tileEntityEnderChest = (TileEntityEnderChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 83 */     if (inventoryEnderChest == null || tileEntityEnderChest == null) return true;
/*    */     
/* 85 */     if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3).r()) return true;
/*    */     
/* 87 */     if (paramWorld.isStatic) {
/* 88 */       return true;
/*    */     }
/*    */     
/* 91 */     inventoryEnderChest.a(tileEntityEnderChest);
/* 92 */     paramEntityHuman.openContainer(inventoryEnderChest);
/*    */     
/* 94 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity a(World paramWorld, int paramInt) {
/* 99 */     return new TileEntityEnderChest();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockEnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */