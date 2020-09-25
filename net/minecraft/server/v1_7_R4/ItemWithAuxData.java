/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemWithAuxData
/*    */   extends ItemBlock
/*    */ {
/*    */   private final Block b;
/*    */   private String[] c;
/*    */   
/*    */   public ItemWithAuxData(Block paramBlock, boolean paramBoolean) {
/* 12 */     super(paramBlock);
/* 13 */     this.b = paramBlock;
/*    */     
/* 15 */     if (paramBoolean) {
/* 16 */       setMaxDurability(0);
/* 17 */       a(true);
/*    */     } 
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
/*    */   public int filterData(int paramInt) {
/* 33 */     return paramInt;
/*    */   }
/*    */   
/*    */   public ItemWithAuxData a(String[] paramArrayOfString) {
/* 37 */     this.c = paramArrayOfString;
/* 38 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String a(ItemStack paramItemStack) {
/* 43 */     if (this.c == null) {
/* 44 */       return super.a(paramItemStack);
/*    */     }
/* 46 */     int i = paramItemStack.getData();
/* 47 */     if (i >= 0 && i < this.c.length) {
/* 48 */       return super.a(paramItemStack) + "." + this.c[i];
/*    */     }
/* 50 */     return super.a(paramItemStack);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemWithAuxData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */