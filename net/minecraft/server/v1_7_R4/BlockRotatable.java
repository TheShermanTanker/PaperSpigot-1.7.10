/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ public abstract class BlockRotatable
/*    */   extends Block
/*    */ {
/*    */   protected BlockRotatable(Material paramMaterial) {
/* 21 */     super(paramMaterial);
/*    */   }
/*    */ 
/*    */   
/*    */   public int b() {
/* 26 */     return 31;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5) {
/* 31 */     int i = paramInt5 & 0x3;
/* 32 */     byte b = 0;
/*    */     
/* 34 */     switch (paramInt4) {
/*    */       case 2:
/*    */       case 3:
/* 37 */         b = 8;
/*    */         break;
/*    */       case 4:
/*    */       case 5:
/* 41 */         b = 4;
/*    */         break;
/*    */       case 0:
/*    */       case 1:
/* 45 */         b = 0;
/*    */         break;
/*    */     } 
/*    */     
/* 49 */     return i | b;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDropData(int paramInt) {
/* 76 */     return paramInt & 0x3;
/*    */   }
/*    */   
/*    */   public int k(int paramInt) {
/* 80 */     return paramInt & 0x3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack j(int paramInt) {
/* 85 */     return new ItemStack(Item.getItemOf(this), 1, k(paramInt));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockRotatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */