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
/*     */ 
/*     */ 
/*     */ public class ItemFish
/*     */   extends ItemFood
/*     */ {
/*     */   private final boolean b;
/*     */   
/*     */   public ItemFish(boolean paramBoolean) {
/*  21 */     super(0, 0.0F, false);
/*     */     
/*  23 */     this.b = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNutrition(ItemStack paramItemStack) {
/*  28 */     EnumFish enumFish = EnumFish.a(paramItemStack);
/*     */     
/*  30 */     if (this.b && enumFish.i()) {
/*  31 */       return enumFish.e();
/*     */     }
/*  33 */     return enumFish.c();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSaturationModifier(ItemStack paramItemStack) {
/*  39 */     EnumFish enumFish = EnumFish.a(paramItemStack);
/*     */     
/*  41 */     if (this.b && enumFish.i()) {
/*  42 */       return enumFish.f();
/*     */     }
/*  44 */     return enumFish.d();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String i(ItemStack paramItemStack) {
/*  50 */     if (EnumFish.a(paramItemStack) == EnumFish.PUFFERFISH) {
/*  51 */       return PotionBrewer.m;
/*     */     }
/*  53 */     return null;
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
/*     */   protected void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/*  66 */     EnumFish enumFish = EnumFish.a(paramItemStack);
/*     */     
/*  68 */     if (enumFish == EnumFish.PUFFERFISH) {
/*  69 */       paramEntityHuman.addEffect(new MobEffect(MobEffectList.POISON.id, 1200, 3));
/*  70 */       paramEntityHuman.addEffect(new MobEffect(MobEffectList.HUNGER.id, 300, 2));
/*  71 */       paramEntityHuman.addEffect(new MobEffect(MobEffectList.CONFUSION.id, 300, 1));
/*     */     } 
/*     */     
/*  74 */     super.c(paramItemStack, paramWorld, paramEntityHuman);
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
/*     */   public String a(ItemStack paramItemStack) {
/*  99 */     EnumFish enumFish = EnumFish.a(paramItemStack);
/* 100 */     return getName() + "." + enumFish.b() + "." + ((this.b && enumFish.i()) ? "cooked" : "raw");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemFish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */