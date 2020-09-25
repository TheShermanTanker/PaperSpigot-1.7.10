/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class ItemAnvil
/*    */   extends ItemMultiTexture
/*    */ {
/*    */   public ItemAnvil(Block paramBlock) {
/*  8 */     super(paramBlock, paramBlock, BlockAnvil.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public int filterData(int paramInt) {
/* 13 */     return paramInt << 2;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemAnvil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */