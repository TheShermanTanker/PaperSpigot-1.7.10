/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityShootBowEvent;
/*     */ 
/*     */ public class ItemBow extends Item {
/*   7 */   public static final String[] a = new String[] { "pulling_0", "pulling_1", "pulling_2" };
/*     */   
/*     */   public ItemBow() {
/*  10 */     this.maxStackSize = 1;
/*  11 */     setMaxDurability(384);
/*  12 */     a(CreativeModeTab.j);
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, World world, EntityHuman entityhuman, int i) {
/*  16 */     boolean flag = (entityhuman.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, itemstack) > 0);
/*     */     
/*  18 */     if (flag || entityhuman.inventory.b(Items.ARROW)) {
/*  19 */       int j = d_(itemstack) - i;
/*  20 */       float f = j / 20.0F;
/*     */       
/*  22 */       f = (f * f + f * 2.0F) / 3.0F;
/*  23 */       if (f < 0.1D) {
/*     */         return;
/*     */       }
/*     */       
/*  27 */       if (f > 1.0F) {
/*  28 */         f = 1.0F;
/*     */       }
/*     */       
/*  31 */       EntityArrow entityarrow = new EntityArrow(world, entityhuman, f * 2.0F);
/*     */       
/*  33 */       if (f == 1.0F) {
/*  34 */         entityarrow.setCritical(true);
/*     */       }
/*     */       
/*  37 */       int k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, itemstack);
/*     */       
/*  39 */       if (k > 0) {
/*  40 */         entityarrow.b(entityarrow.e() + k * 0.5D + 0.5D);
/*     */       }
/*     */       
/*  43 */       int l = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, itemstack);
/*     */       
/*  45 */       if (l > 0) {
/*  46 */         entityarrow.setKnockbackStrength(l);
/*     */       }
/*     */       
/*  49 */       if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, itemstack) > 0) {
/*     */         
/*  51 */         EntityCombustEvent entityCombustEvent = new EntityCombustEvent((Entity)entityarrow.getBukkitEntity(), 100);
/*  52 */         entityarrow.world.getServer().getPluginManager().callEvent((Event)entityCombustEvent);
/*     */         
/*  54 */         if (!entityCombustEvent.isCancelled()) {
/*  55 */           entityarrow.setOnFire(entityCombustEvent.getDuration());
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  61 */       EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(entityhuman, itemstack, entityarrow, f);
/*  62 */       if (event.isCancelled()) {
/*  63 */         event.getProjectile().remove();
/*     */         
/*     */         return;
/*     */       } 
/*  67 */       if (event.getProjectile() == entityarrow.getBukkitEntity()) {
/*  68 */         world.addEntity(entityarrow);
/*     */       }
/*     */ 
/*     */       
/*  72 */       itemstack.damage(1, entityhuman);
/*  73 */       world.makeSound(entityhuman, "random.bow", 1.0F, 1.0F / (g.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
/*  74 */       if (flag) {
/*  75 */         entityarrow.fromPlayer = 2;
/*     */       } else {
/*  77 */         entityhuman.inventory.a(Items.ARROW);
/*     */       } 
/*     */       
/*  80 */       if (!world.isStatic);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack b(ItemStack itemstack, World world, EntityHuman entityhuman) {
/*  87 */     return itemstack;
/*     */   }
/*     */   
/*     */   public int d_(ItemStack itemstack) {
/*  91 */     return 72000;
/*     */   }
/*     */   
/*     */   public EnumAnimation d(ItemStack itemstack) {
/*  95 */     return EnumAnimation.BOW;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/*  99 */     if (entityhuman.abilities.canInstantlyBuild || entityhuman.inventory.b(Items.ARROW)) {
/* 100 */       entityhuman.a(itemstack, d_(itemstack));
/*     */     }
/*     */     
/* 103 */     return itemstack;
/*     */   }
/*     */   
/*     */   public int c() {
/* 107 */     return 1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemBow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */