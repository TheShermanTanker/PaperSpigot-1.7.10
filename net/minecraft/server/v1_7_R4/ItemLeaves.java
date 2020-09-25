/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemLeaves
/*    */   extends ItemBlock
/*    */ {
/*    */   private final BlockLeaves b;
/*    */   
/*    */   public ItemLeaves(BlockLeaves paramBlockLeaves) {
/* 11 */     super(paramBlockLeaves);
/* 12 */     this.b = paramBlockLeaves;
/*    */     
/* 14 */     setMaxDurability(0);
/* 15 */     a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public int filterData(int paramInt) {
/* 20 */     return paramInt | 0x4;
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
/*    */   public String a(ItemStack paramItemStack) {
/* 35 */     int i = paramItemStack.getData();
/* 36 */     if (i < 0 || i >= (this.b.e()).length) {
/* 37 */       i = 0;
/*    */     }
/* 39 */     return getName() + "." + this.b.e()[i];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemLeaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */