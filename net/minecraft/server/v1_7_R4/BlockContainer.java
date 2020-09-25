/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public abstract class BlockContainer
/*    */   extends Block
/*    */   implements IContainer
/*    */ {
/*    */   protected BlockContainer(Material paramMaterial) {
/*  9 */     super(paramMaterial);
/* 10 */     this.isTileEntity = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 20 */     super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
/* 21 */     paramWorld.p(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 26 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 27 */     TileEntity tileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 28 */     if (tileEntity != null) {
/* 29 */       return tileEntity.c(paramInt4, paramInt5);
/*    */     }
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */