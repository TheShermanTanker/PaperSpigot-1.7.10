/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBeacon
/*    */   extends BlockContainer
/*    */ {
/*    */   public BlockBeacon() {
/* 14 */     super(Material.SHATTERABLE);
/* 15 */     c(3.0F);
/* 16 */     a(CreativeModeTab.f);
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity a(World paramWorld, int paramInt) {
/* 21 */     return new TileEntityBeacon();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 26 */     if (paramWorld.isStatic) return true;
/*    */     
/* 28 */     TileEntityBeacon tileEntityBeacon = (TileEntityBeacon)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 29 */     if (tileEntityBeacon != null) paramEntityHuman.openBeacon(tileEntityBeacon);
/*    */     
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean c() {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int b() {
/* 50 */     return 34;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack) {
/* 60 */     super.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityLiving, paramItemStack);
/* 61 */     if (paramItemStack.hasName())
/* 62 */       ((TileEntityBeacon)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3)).a(paramItemStack.getName()); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */