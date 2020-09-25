/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ public class BlockEnchantmentTable
/*    */   extends BlockContainer
/*    */ {
/*    */   protected BlockEnchantmentTable() {
/* 25 */     super(Material.STONE);
/* 26 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/* 27 */     g(0);
/* 28 */     a(CreativeModeTab.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 33 */     return false;
/*    */   }
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
/*    */ 
/*    */   
/*    */   public boolean c() {
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntity a(World paramWorld, int paramInt) {
/* 71 */     return new TileEntityEnchantTable();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 76 */     if (paramWorld.isStatic) {
/* 77 */       return true;
/*    */     }
/* 79 */     TileEntityEnchantTable tileEntityEnchantTable = (TileEntityEnchantTable)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 80 */     paramEntityHuman.startEnchanting(paramInt1, paramInt2, paramInt3, tileEntityEnchantTable.b() ? tileEntityEnchantTable.a() : null);
/* 81 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack) {
/* 86 */     super.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLiving, paramItemStack);
/* 87 */     if (paramItemStack.hasName())
/* 88 */       ((TileEntityEnchantTable)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).a(paramItemStack.getName()); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockEnchantmentTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */