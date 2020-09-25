/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockOreBlock
/*    */   extends Block
/*    */ {
/*    */   private final MaterialMapColor a;
/*    */   
/*    */   public BlockOreBlock(MaterialMapColor paramMaterialMapColor) {
/* 11 */     super(Material.ORE);
/* 12 */     this.a = paramMaterialMapColor;
/* 13 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public MaterialMapColor f(int paramInt) {
/* 18 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockOreBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */