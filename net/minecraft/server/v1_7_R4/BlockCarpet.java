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
/*    */ public class BlockCarpet
/*    */   extends Block
/*    */ {
/*    */   protected BlockCarpet() {
/* 18 */     super(Material.WOOL);
/* 19 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/* 20 */     a(true);
/* 21 */     a(CreativeModeTab.c);
/* 22 */     b(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 32 */     boolean bool = false;
/* 33 */     float f = 0.0625F;
/* 34 */     return AxisAlignedBB.a(paramInt1 + this.minX, paramInt2 + this.minY, paramInt3 + this.minZ, paramInt1 + this.maxX, (paramInt2 + bool * f), paramInt3 + this.maxZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean c() {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void g() {
/* 53 */     b(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 58 */     b(paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3));
/*    */   }
/*    */   
/*    */   protected void b(int paramInt) {
/* 62 */     byte b = 0;
/* 63 */     float f = (1 * (1 + b)) / 16.0F;
/* 64 */     a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 69 */     return (super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3) && j(paramWorld, paramInt1, paramInt2, paramInt3));
/*    */   }
/*    */ 
/*    */   
/*    */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 74 */     e(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   private boolean e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 78 */     if (!j(paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 79 */       b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
/* 80 */       paramWorld.setAir(paramInt1, paramInt2, paramInt3);
/* 81 */       return false;
/*    */     } 
/* 83 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean j(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 88 */     return !paramWorld.isEmpty(paramInt1, paramInt2 - 1, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDropData(int paramInt) {
/* 99 */     return paramInt;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockCarpet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */