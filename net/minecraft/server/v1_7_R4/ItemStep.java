/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStep
/*     */   extends ItemBlock
/*     */ {
/*     */   private final boolean b;
/*     */   private final BlockStepAbstract c;
/*     */   private final BlockStepAbstract d;
/*     */   
/*     */   public ItemStep(Block paramBlock, BlockStepAbstract paramBlockStepAbstract1, BlockStepAbstract paramBlockStepAbstract2, boolean paramBoolean) {
/*  15 */     super(paramBlock);
/*  16 */     this.c = paramBlockStepAbstract1;
/*  17 */     this.d = paramBlockStepAbstract2;
/*     */     
/*  19 */     this.b = paramBoolean;
/*     */     
/*  21 */     setMaxDurability(0);
/*  22 */     a(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int filterData(int paramInt) {
/*  32 */     return paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public String a(ItemStack paramItemStack) {
/*  37 */     return this.c.b(paramItemStack.getData());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  42 */     if (this.b) return super.interactWith(paramItemStack, paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
/*     */     
/*  44 */     if (paramItemStack.count == 0) return false; 
/*  45 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false;
/*     */     
/*  47 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/*  48 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  49 */     int j = i & 0x7;
/*  50 */     boolean bool = ((i & 0x8) != 0) ? true : false;
/*     */     
/*  52 */     if (((paramInt4 == 1 && !bool) || (paramInt4 == 0 && bool)) && block == this.c && j == paramItemStack.getData()) {
/*  53 */       if (paramWorld.b(this.d.a(paramWorld, paramInt1, paramInt2, paramInt3)) && paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.d, j, 3)) {
/*  54 */         paramWorld.makeSound((paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), this.d.stepSound.getPlaceSound(), (this.d.stepSound.getVolume1() + 1.0F) / 2.0F, this.d.stepSound.getVolume2() * 0.8F);
/*  55 */         paramItemStack.count--;
/*     */       } 
/*  57 */       return true;
/*  58 */     }  if (a(paramItemStack, paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3, paramInt4)) {
/*  59 */       return true;
/*     */     }
/*  61 */     return super.interactWith(paramItemStack, paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  97 */     if (paramInt4 == 0) paramInt2--; 
/*  98 */     if (paramInt4 == 1) paramInt2++; 
/*  99 */     if (paramInt4 == 2) paramInt3--; 
/* 100 */     if (paramInt4 == 3) paramInt3++; 
/* 101 */     if (paramInt4 == 4) paramInt1--; 
/* 102 */     if (paramInt4 == 5) paramInt1++;
/*     */     
/* 104 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/* 105 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 106 */     int j = i & 0x7;
/*     */     
/* 108 */     if (block == this.c && j == paramItemStack.getData()) {
/* 109 */       if (paramWorld.b(this.d.a(paramWorld, paramInt1, paramInt2, paramInt3)) && paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.d, j, 3)) {
/* 110 */         paramWorld.makeSound((paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), this.d.stepSound.getPlaceSound(), (this.d.stepSound.getVolume1() + 1.0F) / 2.0F, this.d.stepSound.getVolume2() * 0.8F);
/* 111 */         paramItemStack.count--;
/*     */       } 
/* 113 */       return true;
/*     */     } 
/*     */     
/* 116 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */