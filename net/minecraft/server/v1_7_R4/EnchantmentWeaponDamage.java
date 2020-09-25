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
/*    */ public class EnchantmentWeaponDamage
/*    */   extends Enchantment
/*    */ {
/* 14 */   private static final String[] E = new String[] { "all", "undead", "arthropods" };
/*    */ 
/*    */ 
/*    */   
/* 18 */   private static final int[] F = new int[] { 1, 5, 5 };
/*    */ 
/*    */ 
/*    */   
/* 22 */   private static final int[] G = new int[] { 11, 8, 8 };
/*    */ 
/*    */ 
/*    */   
/* 26 */   private static final int[] H = new int[] { 20, 20, 20 };
/*    */ 
/*    */   
/*    */   public final int a;
/*    */ 
/*    */   
/*    */   public EnchantmentWeaponDamage(int paramInt1, int paramInt2, int paramInt3) {
/* 33 */     super(paramInt1, paramInt2, EnchantmentSlotType.WEAPON);
/* 34 */     this.a = paramInt3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 39 */     return F[this.a] + (paramInt - 1) * G[this.a];
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 44 */     return a(paramInt) + H[this.a];
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 49 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public float a(int paramInt, EnumMonsterType paramEnumMonsterType) {
/* 54 */     if (this.a == 0) {
/* 55 */       return paramInt * 1.25F;
/*    */     }
/* 57 */     if (this.a == 1 && paramEnumMonsterType == EnumMonsterType.UNDEAD) {
/* 58 */       return paramInt * 2.5F;
/*    */     }
/* 60 */     if (this.a == 2 && paramEnumMonsterType == EnumMonsterType.ARTHROPOD) {
/* 61 */       return paramInt * 2.5F;
/*    */     }
/* 63 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public String a() {
/* 68 */     return "enchantment.damage." + E[this.a];
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(Enchantment paramEnchantment) {
/* 73 */     return !(paramEnchantment instanceof EnchantmentWeaponDamage);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack) {
/* 78 */     if (paramItemStack.getItem() instanceof ItemAxe) return true; 
/* 79 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(EntityLiving paramEntityLiving, Entity paramEntity, int paramInt) {
/* 84 */     if (paramEntity instanceof EntityLiving) {
/* 85 */       EntityLiving entityLiving = (EntityLiving)paramEntity;
/*    */       
/* 87 */       if (this.a == 2 && entityLiving.getMonsterType() == EnumMonsterType.ARTHROPOD) {
/* 88 */         int i = 20 + paramEntityLiving.aI().nextInt(10 * paramInt);
/* 89 */         entityLiving.addEffect(new MobEffect(MobEffectList.SLOWER_MOVEMENT.id, i, 3));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentWeaponDamage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */