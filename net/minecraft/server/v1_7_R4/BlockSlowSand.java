/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSlowSand
/*    */   extends Block
/*    */ {
/*    */   public BlockSlowSand() {
/* 11 */     super(Material.SAND);
/* 12 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 17 */     float f = 0.125F;
/* 18 */     return AxisAlignedBB.a(paramInt1, paramInt2, paramInt3, (paramInt1 + 1), ((paramInt2 + 1) - f), (paramInt3 + 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) {
/* 23 */     paramEntity.motX *= 0.4D;
/* 24 */     paramEntity.motZ *= 0.4D;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSlowSand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */