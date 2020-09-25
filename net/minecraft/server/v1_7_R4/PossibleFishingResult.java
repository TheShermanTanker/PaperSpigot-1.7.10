/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PossibleFishingResult
/*    */   extends WeightedRandomChoice
/*    */ {
/*    */   private final ItemStack b;
/*    */   private float c;
/*    */   private boolean d;
/*    */   
/*    */   public PossibleFishingResult(ItemStack paramItemStack, int paramInt) {
/* 15 */     super(paramInt);
/* 16 */     this.b = paramItemStack;
/*    */   }
/*    */   
/*    */   public ItemStack a(Random paramRandom) {
/* 20 */     ItemStack itemStack = this.b.cloneItemStack();
/*    */     
/* 22 */     if (this.c > 0.0F) {
/* 23 */       int i = (int)(this.c * this.b.l());
/* 24 */       int j = itemStack.l() - paramRandom.nextInt(paramRandom.nextInt(i) + 1);
/* 25 */       if (j > i) j = i; 
/* 26 */       if (j < 1) j = 1; 
/* 27 */       itemStack.setData(j);
/*    */     } 
/*    */     
/* 30 */     if (this.d) {
/* 31 */       EnchantmentManager.a(paramRandom, itemStack, 30);
/*    */     }
/*    */     
/* 34 */     return itemStack;
/*    */   }
/*    */   
/*    */   public PossibleFishingResult a(float paramFloat) {
/* 38 */     this.c = paramFloat;
/* 39 */     return this;
/*    */   }
/*    */   
/*    */   public PossibleFishingResult a() {
/* 43 */     this.d = true;
/* 44 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PossibleFishingResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */