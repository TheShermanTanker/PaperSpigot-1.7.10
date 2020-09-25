/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class ItemBlockWithAuxData
/*    */   extends ItemBlock
/*    */ {
/*    */   private Block b;
/*    */   
/*    */   public ItemBlockWithAuxData(Block paramBlock1, Block paramBlock2) {
/* 10 */     super(paramBlock1);
/*    */     
/* 12 */     this.b = paramBlock2;
/*    */     
/* 14 */     setMaxDurability(0);
/* 15 */     a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int filterData(int paramInt) {
/* 25 */     return paramInt;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemBlockWithAuxData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */