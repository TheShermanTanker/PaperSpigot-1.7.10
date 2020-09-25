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
/*     */ public class EnchantmentProtection
/*     */   extends Enchantment
/*     */ {
/*  14 */   private static final String[] E = new String[] { "all", "fire", "fall", "explosion", "projectile" };
/*     */ 
/*     */ 
/*     */   
/*  18 */   private static final int[] F = new int[] { 1, 10, 5, 5, 3 };
/*     */ 
/*     */ 
/*     */   
/*  22 */   private static final int[] G = new int[] { 11, 8, 6, 8, 6 };
/*     */ 
/*     */ 
/*     */   
/*  26 */   private static final int[] H = new int[] { 20, 12, 10, 12, 15 };
/*     */ 
/*     */   
/*     */   public final int a;
/*     */ 
/*     */   
/*     */   public EnchantmentProtection(int paramInt1, int paramInt2, int paramInt3) {
/*  33 */     super(paramInt1, paramInt2, EnchantmentSlotType.ARMOR);
/*  34 */     this.a = paramInt3;
/*     */     
/*  36 */     if (paramInt3 == 2) {
/*  37 */       this.slot = EnchantmentSlotType.ARMOR_FEET;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int a(int paramInt) {
/*  43 */     return F[this.a] + (paramInt - 1) * G[this.a];
/*     */   }
/*     */ 
/*     */   
/*     */   public int b(int paramInt) {
/*  48 */     return a(paramInt) + H[this.a];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/*  53 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int a(int paramInt, DamageSource paramDamageSource) {
/*  58 */     if (paramDamageSource.ignoresInvulnerability()) return 0;
/*     */     
/*  60 */     float f = (6 + paramInt * paramInt) / 3.0F;
/*     */     
/*  62 */     if (this.a == 0) return MathHelper.d(f * 0.75F); 
/*  63 */     if (this.a == 1 && paramDamageSource.o()) return MathHelper.d(f * 1.25F); 
/*  64 */     if (this.a == 2 && paramDamageSource == DamageSource.FALL) return MathHelper.d(f * 2.5F); 
/*  65 */     if (this.a == 3 && paramDamageSource.isExplosion()) return MathHelper.d(f * 1.5F); 
/*  66 */     if (this.a == 4 && paramDamageSource.a()) return MathHelper.d(f * 1.5F); 
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String a() {
/*  72 */     return "enchantment.protect." + E[this.a];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(Enchantment paramEnchantment) {
/*  77 */     if (paramEnchantment instanceof EnchantmentProtection) {
/*  78 */       EnchantmentProtection enchantmentProtection = (EnchantmentProtection)paramEnchantment;
/*     */       
/*  80 */       if (enchantmentProtection.a == this.a) {
/*  81 */         return false;
/*     */       }
/*  83 */       if (this.a == 2 || enchantmentProtection.a == 2) {
/*  84 */         return true;
/*     */       }
/*  86 */       return false;
/*     */     } 
/*  88 */     return super.a(paramEnchantment);
/*     */   }
/*     */   
/*     */   public static int a(Entity paramEntity, int paramInt) {
/*  92 */     int i = EnchantmentManager.getEnchantmentLevel(Enchantment.PROTECTION_FIRE.id, paramEntity.getEquipment());
/*     */     
/*  94 */     if (i > 0) {
/*  95 */       paramInt -= MathHelper.d(paramInt * i * 0.15F);
/*     */     }
/*     */     
/*  98 */     return paramInt;
/*     */   }
/*     */   
/*     */   public static double a(Entity paramEntity, double paramDouble) {
/* 102 */     int i = EnchantmentManager.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS.id, paramEntity.getEquipment());
/*     */     
/* 104 */     if (i > 0) {
/* 105 */       paramDouble -= MathHelper.floor(paramDouble * (i * 0.15F));
/*     */     }
/*     */     
/* 108 */     return paramDouble;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentProtection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */