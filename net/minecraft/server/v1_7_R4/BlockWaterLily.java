/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWaterLily
/*    */   extends BlockPlant
/*    */ {
/*    */   protected BlockWaterLily() {
/* 16 */     float f1 = 0.5F;
/* 17 */     float f2 = 0.015625F;
/* 18 */     a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f2, 0.5F + f1);
/* 19 */     a(CreativeModeTab.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public int b() {
/* 24 */     return 23;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity) {
/* 29 */     if (paramEntity == null || !(paramEntity instanceof EntityBoat)) {
/* 30 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 36 */     return AxisAlignedBB.a(paramInt1 + this.minX, paramInt2 + this.minY, paramInt3 + this.minZ, paramInt1 + this.maxX, paramInt2 + this.maxY, paramInt3 + this.maxZ);
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
/*    */   protected boolean a(Block paramBlock) {
/* 56 */     return (paramBlock == Blocks.STATIONARY_WATER);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean j(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 61 */     if (paramInt2 < 0 || paramInt2 >= 256) return false; 
/* 62 */     return (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3).getMaterial() == Material.WATER && paramWorld.getData(paramInt1, paramInt2 - 1, paramInt3) == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockWaterLily.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */