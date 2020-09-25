/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemFood
/*    */   extends Item
/*    */ {
/* 10 */   public final int a = 32;
/*    */   
/*    */   private final int b;
/*    */   
/*    */   private final float c;
/*    */   private final boolean d;
/*    */   private boolean m;
/*    */   private int n;
/*    */   private int o;
/*    */   private int p;
/*    */   private float q;
/*    */   
/*    */   public ItemFood(int paramInt, float paramFloat, boolean paramBoolean) {
/* 23 */     this.b = paramInt;
/* 24 */     this.d = paramBoolean;
/* 25 */     this.c = paramFloat;
/* 26 */     a(CreativeModeTab.h);
/*    */   }
/*    */   
/*    */   public ItemFood(int paramInt, boolean paramBoolean) {
/* 30 */     this(paramInt, 0.6F, paramBoolean);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 35 */     paramItemStack.count--;
/* 36 */     paramEntityHuman.getFoodData().a(this, paramItemStack);
/* 37 */     paramWorld.makeSound(paramEntityHuman, "random.burp", 0.5F, paramWorld.random.nextFloat() * 0.1F + 0.9F);
/*    */     
/* 39 */     c(paramItemStack, paramWorld, paramEntityHuman);
/*    */     
/* 41 */     return paramItemStack;
/*    */   }
/*    */   
/*    */   protected void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 45 */     if (!paramWorld.isStatic && this.n > 0 && paramWorld.random.nextFloat() < this.q) {
/* 46 */       paramEntityHuman.addEffect(new MobEffect(this.n, this.o * 20, this.p));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int d_(ItemStack paramItemStack) {
/* 53 */     return 32;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumAnimation d(ItemStack paramItemStack) {
/* 58 */     return EnumAnimation.EAT;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 63 */     if (paramEntityHuman.g(this.m)) {
/* 64 */       paramEntityHuman.a(paramItemStack, d_(paramItemStack));
/*    */     }
/* 66 */     return paramItemStack;
/*    */   }
/*    */   
/*    */   public int getNutrition(ItemStack paramItemStack) {
/* 70 */     return this.b;
/*    */   }
/*    */   
/*    */   public float getSaturationModifier(ItemStack paramItemStack) {
/* 74 */     return this.c;
/*    */   }
/*    */   
/*    */   public boolean i() {
/* 78 */     return this.d;
/*    */   }
/*    */   
/*    */   public ItemFood a(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
/* 82 */     this.n = paramInt1;
/* 83 */     this.o = paramInt2;
/* 84 */     this.p = paramInt3;
/* 85 */     this.q = paramFloat;
/* 86 */     return this;
/*    */   }
/*    */   
/*    */   public ItemFood j() {
/* 90 */     this.m = true;
/* 91 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemFood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */