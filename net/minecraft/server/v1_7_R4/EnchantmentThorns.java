/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnchantmentThorns
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentThorns(int paramInt1, int paramInt2) {
/* 15 */     super(paramInt1, paramInt2, EnchantmentSlotType.ARMOR_TORSO);
/*    */     
/* 17 */     b("thorns");
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(int paramInt) {
/* 22 */     return 10 + 20 * (paramInt - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int b(int paramInt) {
/* 27 */     return super.a(paramInt) + 50;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 32 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack) {
/* 37 */     if (paramItemStack.getItem() instanceof ItemArmor) return true; 
/* 38 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(EntityLiving paramEntityLiving, Entity paramEntity, int paramInt) {
/* 43 */     Random random = paramEntityLiving.aI();
/* 44 */     ItemStack itemStack = EnchantmentManager.a(Enchantment.THORNS, paramEntityLiving);
/*    */     
/* 46 */     if (a(paramInt, random)) {
/* 47 */       paramEntity.damageEntity(DamageSource.a(paramEntityLiving), b(paramInt, random));
/* 48 */       paramEntity.makeSound("damage.thorns", 0.5F, 1.0F);
/*    */       
/* 50 */       if (itemStack != null) {
/* 51 */         itemStack.damage(3, paramEntityLiving);
/*    */       }
/*    */     }
/* 54 */     else if (itemStack != null) {
/* 55 */       itemStack.damage(1, paramEntityLiving);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean a(int paramInt, Random paramRandom) {
/* 61 */     if (paramInt <= 0) return false; 
/* 62 */     return (paramRandom.nextFloat() < 0.15F * paramInt);
/*    */   }
/*    */   
/*    */   public static int b(int paramInt, Random paramRandom) {
/* 66 */     if (paramInt > 10) {
/* 67 */       return paramInt - 10;
/*    */     }
/* 69 */     return 1 + paramRandom.nextInt(4);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentThorns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */