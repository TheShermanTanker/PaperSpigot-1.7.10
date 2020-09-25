/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockRedstone
/*    */   extends BlockOreBlock
/*    */ {
/*    */   public BlockRedstone(MaterialMapColor paramMaterialMapColor) {
/* 10 */     super(paramMaterialMapColor);
/* 11 */     a(CreativeModeTab.d);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPowerSource() {
/* 16 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 21 */     return 15;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockRedstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */