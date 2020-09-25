/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class ItemMultiTexture
/*    */   extends ItemBlock
/*    */ {
/*    */   protected final Block b;
/*    */   protected final String[] c;
/*    */   
/*    */   public ItemMultiTexture(Block paramBlock1, Block paramBlock2, String[] paramArrayOfString) {
/* 11 */     super(paramBlock1);
/*    */     
/* 13 */     this.b = paramBlock2;
/* 14 */     this.c = paramArrayOfString;
/*    */     
/* 16 */     setMaxDurability(0);
/* 17 */     a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int filterData(int paramInt) {
/* 27 */     return paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public String a(ItemStack paramItemStack) {
/* 32 */     int i = paramItemStack.getData();
/* 33 */     if (i < 0 || i >= this.c.length) {
/* 34 */       i = 0;
/*    */     }
/* 36 */     return getName() + "." + this.c[i];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemMultiTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */