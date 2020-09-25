/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import net.minecraft.util.com.google.common.collect.Multimap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemSword
/*     */   extends Item
/*     */ {
/*     */   private float damage;
/*     */   private final EnumToolMaterial b;
/*     */   
/*     */   public ItemSword(EnumToolMaterial paramEnumToolMaterial) {
/*  18 */     this.b = paramEnumToolMaterial;
/*  19 */     this.maxStackSize = 1;
/*  20 */     setMaxDurability(paramEnumToolMaterial.a());
/*  21 */     a(CreativeModeTab.j);
/*     */     
/*  23 */     this.damage = 4.0F + paramEnumToolMaterial.c();
/*     */   }
/*     */   
/*     */   public float i() {
/*  27 */     return this.b.c();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock) {
/*  32 */     if (paramBlock == Blocks.WEB)
/*     */     {
/*  34 */       return 15.0F;
/*     */     }
/*     */ 
/*     */     
/*  38 */     Material material = paramBlock.getMaterial();
/*  39 */     if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT || material == Material.CORAL || material == Material.LEAVES || material == Material.PUMPKIN) {
/*  40 */       return 1.5F;
/*     */     }
/*  42 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
/*  47 */     paramItemStack.damage(1, paramEntityLiving2);
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving) {
/*  54 */     if (paramBlock.f(paramWorld, paramInt1, paramInt2, paramInt3) != 0.0D) paramItemStack.damage(2, paramEntityLiving); 
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAnimation d(ItemStack paramItemStack) {
/*  65 */     return EnumAnimation.BLOCK;
/*     */   }
/*     */ 
/*     */   
/*     */   public int d_(ItemStack paramItemStack) {
/*  70 */     return 72000;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/*  75 */     paramEntityHuman.a(paramItemStack, d_(paramItemStack));
/*  76 */     return paramItemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDestroySpecialBlock(Block paramBlock) {
/*  81 */     return (paramBlock == Blocks.WEB);
/*     */   }
/*     */ 
/*     */   
/*     */   public int c() {
/*  86 */     return this.b.e();
/*     */   }
/*     */   
/*     */   public String j() {
/*  90 */     return this.b.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2) {
/*  95 */     if (this.b.f() == paramItemStack2.getItem()) {
/*  96 */       return true;
/*     */     }
/*  98 */     return super.a(paramItemStack1, paramItemStack2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap k() {
/* 103 */     Multimap multimap = super.k();
/*     */     
/* 105 */     multimap.put(GenericAttributes.e.getName(), new AttributeModifier(f, "Weapon modifier", this.damage, 0));
/*     */     
/* 107 */     return multimap;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSword.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */