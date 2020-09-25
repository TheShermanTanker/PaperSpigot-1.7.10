/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ public class ItemBlock
/*     */   extends Item
/*     */ {
/*     */   protected final Block block;
/*     */   
/*     */   public ItemBlock(Block paramBlock) {
/*  19 */     this.block = paramBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemBlock b(String paramString) {
/*  24 */     super.c(paramString);
/*  25 */     return this;
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
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  46 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/*  47 */     if (block == Blocks.SNOW && (paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x7) < 1) {
/*  48 */       paramInt4 = 1;
/*  49 */     } else if (block != Blocks.VINE && block != Blocks.LONG_GRASS && block != Blocks.DEAD_BUSH) {
/*     */ 
/*     */       
/*  52 */       if (paramInt4 == 0) paramInt2--; 
/*  53 */       if (paramInt4 == 1) paramInt2++; 
/*  54 */       if (paramInt4 == 2) paramInt3--; 
/*  55 */       if (paramInt4 == 3) paramInt3++; 
/*  56 */       if (paramInt4 == 4) paramInt1--; 
/*  57 */       if (paramInt4 == 5) paramInt1++;
/*     */     
/*     */     } 
/*  60 */     if (paramItemStack.count == 0) return false; 
/*  61 */     if (!paramEntityHuman.a(paramInt1, paramInt2, paramInt3, paramInt4, paramItemStack)) return false;
/*     */     
/*  63 */     if (paramInt2 == 255 && this.block.getMaterial().isBuildable()) return false;
/*     */     
/*  65 */     if (paramWorld.mayPlace(this.block, paramInt1, paramInt2, paramInt3, false, paramInt4, paramEntityHuman, paramItemStack)) {
/*  66 */       int i = filterData(paramItemStack.getData());
/*  67 */       int j = this.block.getPlacedData(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3, i);
/*  68 */       if (paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.block, j, 3)) {
/*     */ 
/*     */ 
/*     */         
/*  72 */         if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this.block) {
/*  73 */           this.block.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman, paramItemStack);
/*  74 */           this.block.postPlace(paramWorld, paramInt1, paramInt2, paramInt3, j);
/*     */         } 
/*  76 */         paramWorld.makeSound((paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume1() + 1.0F) / 2.0F, this.block.stepSound.getVolume2() * 0.8F);
/*  77 */         paramItemStack.count--;
/*     */       } 
/*  79 */       return true;
/*     */     } 
/*  81 */     return false;
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
/*     */   public String a(ItemStack paramItemStack) {
/* 102 */     return this.block.a();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 107 */     return this.block.a();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */