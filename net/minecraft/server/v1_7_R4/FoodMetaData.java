/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ 
/*     */ public class FoodMetaData {
/*   6 */   public int foodLevel = 20;
/*   7 */   public float saturationLevel = 5.0F;
/*     */   
/*     */   public float exhaustionLevel;
/*     */   public int foodTickTimer;
/*     */   private EntityHuman entityhuman;
/*  12 */   private int e = 20;
/*     */   public FoodMetaData() {
/*  14 */     throw new AssertionError("Whoopsie, we missed the bukkit.");
/*     */   }
/*     */   
/*     */   public FoodMetaData(EntityHuman entityhuman) {
/*  18 */     Validate.notNull(entityhuman);
/*  19 */     this.entityhuman = entityhuman;
/*     */   }
/*     */ 
/*     */   
/*     */   public void eat(int i, float f) {
/*  24 */     this.foodLevel = Math.min(i + this.foodLevel, 20);
/*  25 */     this.saturationLevel = Math.min(this.saturationLevel + i * f * 2.0F, this.foodLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(ItemFood itemfood, ItemStack itemstack) {
/*  30 */     int oldFoodLevel = this.foodLevel;
/*     */     
/*  32 */     FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(this.entityhuman, itemfood.getNutrition(itemstack) + oldFoodLevel);
/*     */     
/*  34 */     if (!event.isCancelled()) {
/*  35 */       eat(event.getFoodLevel() - oldFoodLevel, itemfood.getSaturationModifier(itemstack));
/*     */     }
/*     */     
/*  38 */     ((EntityPlayer)this.entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)this.entityhuman).getBukkitEntity().getScaledHealth(), (this.entityhuman.getFoodData()).foodLevel, (this.entityhuman.getFoodData()).saturationLevel));
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(EntityHuman entityhuman) {
/*  43 */     EnumDifficulty enumdifficulty = entityhuman.world.difficulty;
/*     */     
/*  45 */     this.e = this.foodLevel;
/*  46 */     if (this.exhaustionLevel > 4.0F) {
/*  47 */       this.exhaustionLevel -= 4.0F;
/*  48 */       if (this.saturationLevel > 0.0F) {
/*  49 */         this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
/*  50 */       } else if (enumdifficulty != EnumDifficulty.PEACEFUL) {
/*     */         
/*  52 */         FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, Math.max(this.foodLevel - 1, 0));
/*     */         
/*  54 */         if (!event.isCancelled()) {
/*  55 */           this.foodLevel = event.getFoodLevel();
/*     */         }
/*     */         
/*  58 */         ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), this.foodLevel, this.saturationLevel));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  63 */     if (entityhuman.world.getGameRules().getBoolean("naturalRegeneration") && this.foodLevel >= 18 && entityhuman.bR()) {
/*  64 */       this.foodTickTimer++;
/*  65 */       if (this.foodTickTimer >= 80) {
/*     */         
/*  67 */         entityhuman.heal(1.0F, EntityRegainHealthEvent.RegainReason.SATIATED);
/*  68 */         a(entityhuman.world.spigotConfig.regenExhaustion);
/*  69 */         this.foodTickTimer = 0;
/*     */       } 
/*  71 */     } else if (this.foodLevel <= 0) {
/*  72 */       this.foodTickTimer++;
/*  73 */       if (this.foodTickTimer >= 80) {
/*  74 */         if (entityhuman.getHealth() > 10.0F || enumdifficulty == EnumDifficulty.HARD || (entityhuman.getHealth() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL)) {
/*  75 */           entityhuman.damageEntity(DamageSource.STARVE, 1.0F);
/*     */         }
/*     */         
/*  78 */         this.foodTickTimer = 0;
/*     */       } 
/*     */     } else {
/*  81 */       this.foodTickTimer = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  86 */     if (nbttagcompound.hasKeyOfType("foodLevel", 99)) {
/*  87 */       this.foodLevel = nbttagcompound.getInt("foodLevel");
/*  88 */       this.foodTickTimer = nbttagcompound.getInt("foodTickTimer");
/*  89 */       this.saturationLevel = nbttagcompound.getFloat("foodSaturationLevel");
/*  90 */       this.exhaustionLevel = nbttagcompound.getFloat("foodExhaustionLevel");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  95 */     nbttagcompound.setInt("foodLevel", this.foodLevel);
/*  96 */     nbttagcompound.setInt("foodTickTimer", this.foodTickTimer);
/*  97 */     nbttagcompound.setFloat("foodSaturationLevel", this.saturationLevel);
/*  98 */     nbttagcompound.setFloat("foodExhaustionLevel", this.exhaustionLevel);
/*     */   }
/*     */   
/*     */   public int getFoodLevel() {
/* 102 */     return this.foodLevel;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 106 */     return (this.foodLevel < 20);
/*     */   }
/*     */   
/*     */   public void a(float f) {
/* 110 */     this.exhaustionLevel = Math.min(this.exhaustionLevel + f, 40.0F);
/*     */   }
/*     */   
/*     */   public float getSaturationLevel() {
/* 114 */     return this.saturationLevel;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\FoodMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */