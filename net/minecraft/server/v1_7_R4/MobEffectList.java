/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.potion.CraftPotionEffectType;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MobEffectList
/*     */ {
/*  17 */   public static final MobEffectList[] byId = new MobEffectList[32];
/*  18 */   public static final MobEffectList b = null;
/*  19 */   public static final MobEffectList FASTER_MOVEMENT = (new MobEffectList(1, false, 8171462)).b("potion.moveSpeed").b(0, 0).a(GenericAttributes.d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2);
/*  20 */   public static final MobEffectList SLOWER_MOVEMENT = (new MobEffectList(2, true, 5926017)).b("potion.moveSlowdown").b(1, 0).a(GenericAttributes.d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2);
/*  21 */   public static final MobEffectList FASTER_DIG = (new MobEffectList(3, false, 14270531)).b("potion.digSpeed").b(2, 0).a(1.5D);
/*  22 */   public static final MobEffectList SLOWER_DIG = (new MobEffectList(4, true, 4866583)).b("potion.digSlowDown").b(3, 0);
/*  23 */   public static final MobEffectList INCREASE_DAMAGE = (new MobEffectAttackDamage(5, false, 9643043)).b("potion.damageBoost").b(4, 0).a(GenericAttributes.e, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0D, 2);
/*  24 */   public static final MobEffectList HEAL = (new InstantMobEffect(6, false, 16262179)).b("potion.heal");
/*  25 */   public static final MobEffectList HARM = (new InstantMobEffect(7, true, 4393481)).b("potion.harm");
/*  26 */   public static final MobEffectList JUMP = (new MobEffectList(8, false, 7889559)).b("potion.jump").b(2, 1);
/*  27 */   public static final MobEffectList CONFUSION = (new MobEffectList(9, true, 5578058)).b("potion.confusion").b(3, 1).a(0.25D);
/*  28 */   public static final MobEffectList REGENERATION = (new MobEffectList(10, false, 13458603)).b("potion.regeneration").b(7, 0).a(0.25D);
/*  29 */   public static final MobEffectList RESISTANCE = (new MobEffectList(11, false, 10044730)).b("potion.resistance").b(6, 1);
/*  30 */   public static final MobEffectList FIRE_RESISTANCE = (new MobEffectList(12, false, 14981690)).b("potion.fireResistance").b(7, 1);
/*  31 */   public static final MobEffectList WATER_BREATHING = (new MobEffectList(13, false, 3035801)).b("potion.waterBreathing").b(0, 2);
/*  32 */   public static final MobEffectList INVISIBILITY = (new MobEffectList(14, false, 8356754)).b("potion.invisibility").b(0, 1);
/*  33 */   public static final MobEffectList BLINDNESS = (new MobEffectList(15, true, 2039587)).b("potion.blindness").b(5, 1).a(0.25D);
/*  34 */   public static final MobEffectList NIGHT_VISION = (new MobEffectList(16, false, 2039713)).b("potion.nightVision").b(4, 1);
/*  35 */   public static final MobEffectList HUNGER = (new MobEffectList(17, true, 5797459)).b("potion.hunger").b(1, 1);
/*  36 */   public static final MobEffectList WEAKNESS = (new MobEffectAttackDamage(18, true, 4738376)).b("potion.weakness").b(5, 0).a(GenericAttributes.e, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0D, 0);
/*  37 */   public static final MobEffectList POISON = (new MobEffectList(19, true, 5149489)).b("potion.poison").b(6, 0).a(0.25D);
/*  38 */   public static final MobEffectList WITHER = (new MobEffectList(20, true, 3484199)).b("potion.wither").b(1, 2).a(0.25D);
/*  39 */   public static final MobEffectList HEALTH_BOOST = (new MobEffectHealthBoost(21, false, 16284963)).b("potion.healthBoost").b(2, 2).a(GenericAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0);
/*  40 */   public static final MobEffectList ABSORPTION = (new MobEffectAbsorption(22, false, 2445989)).b("potion.absorption").b(2, 2);
/*  41 */   public static final MobEffectList SATURATION = (new InstantMobEffect(23, false, 16262179)).b("potion.saturation");
/*  42 */   public static final MobEffectList z = null;
/*  43 */   public static final MobEffectList A = null;
/*  44 */   public static final MobEffectList B = null;
/*  45 */   public static final MobEffectList C = null;
/*  46 */   public static final MobEffectList D = null;
/*  47 */   public static final MobEffectList E = null;
/*  48 */   public static final MobEffectList F = null;
/*  49 */   public static final MobEffectList G = null;
/*     */   public final int id;
/*  51 */   private final Map I = Maps.newHashMap();
/*     */   private final boolean J;
/*     */   private final int K;
/*  54 */   private String L = "";
/*  55 */   private int M = -1;
/*     */   private double N;
/*     */   private boolean O;
/*     */   
/*     */   protected MobEffectList(int i, boolean flag, int j) {
/*  60 */     this.id = i;
/*  61 */     byId[i] = this;
/*  62 */     this.J = flag;
/*  63 */     if (flag) {
/*  64 */       this.N = 0.5D;
/*     */     } else {
/*  66 */       this.N = 1.0D;
/*     */     } 
/*     */     
/*  69 */     this.K = j;
/*     */     
/*  71 */     PotionEffectType.registerPotionEffectType((PotionEffectType)new CraftPotionEffectType(this));
/*     */   }
/*     */   
/*     */   protected MobEffectList b(int i, int j) {
/*  75 */     this.M = i + j * 8;
/*  76 */     return this;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  80 */     return this.id;
/*     */   }
/*     */   
/*     */   public void tick(EntityLiving entityliving, int i) {
/*  84 */     if (this.id == REGENERATION.id) {
/*  85 */       if (entityliving.getHealth() < entityliving.getMaxHealth()) {
/*  86 */         entityliving.heal(1.0F, EntityRegainHealthEvent.RegainReason.MAGIC_REGEN);
/*     */       }
/*  88 */     } else if (this.id == POISON.id) {
/*  89 */       if (entityliving.getHealth() > 1.0F) {
/*  90 */         entityliving.damageEntity(CraftEventFactory.POISON, 1.0F);
/*     */       }
/*  92 */     } else if (this.id == WITHER.id) {
/*  93 */       entityliving.damageEntity(DamageSource.WITHER, 1.0F);
/*  94 */     } else if (this.id == HUNGER.id && entityliving instanceof EntityHuman) {
/*  95 */       ((EntityHuman)entityliving).applyExhaustion(0.025F * (i + 1));
/*  96 */     } else if (this.id == SATURATION.id && entityliving instanceof EntityHuman) {
/*  97 */       if (!entityliving.world.isStatic)
/*     */       {
/*  99 */         EntityHuman entityhuman = (EntityHuman)entityliving;
/* 100 */         int oldFoodLevel = (entityhuman.getFoodData()).foodLevel;
/*     */         
/* 102 */         FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, i + 1 + oldFoodLevel);
/*     */         
/* 104 */         if (!event.isCancelled()) {
/* 105 */           entityhuman.getFoodData().eat(event.getFoodLevel() - oldFoodLevel, 1.0F);
/*     */         }
/*     */         
/* 108 */         ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), (entityhuman.getFoodData()).foodLevel, (entityhuman.getFoodData()).saturationLevel));
/*     */       }
/*     */     
/* 111 */     } else if ((this.id != HEAL.id || entityliving.aR()) && (this.id != HARM.id || !entityliving.aR())) {
/* 112 */       if ((this.id == HARM.id && !entityliving.aR()) || (this.id == HEAL.id && entityliving.aR())) {
/* 113 */         entityliving.damageEntity(DamageSource.MAGIC, (6 << i));
/*     */       }
/*     */     } else {
/* 116 */       entityliving.heal(Math.max(4 << i, 0), EntityRegainHealthEvent.RegainReason.MAGIC);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyInstantEffect(EntityLiving entityliving, EntityLiving entityliving1, int i, double d0) {
/* 122 */     applyInstantEffect(entityliving, entityliving1, i, d0, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInstantEffect(EntityLiving entityliving, EntityLiving entityliving1, int i, double d0, EntityPotion potion) {
/* 129 */     if ((this.id != HEAL.id || entityliving1.aR()) && (this.id != HARM.id || !entityliving1.aR())) {
/* 130 */       if ((this.id == HARM.id && !entityliving1.aR()) || (this.id == HEAL.id && entityliving1.aR())) {
/* 131 */         int j = (int)(d0 * (6 << i) + 0.5D);
/* 132 */         if (entityliving == null) {
/* 133 */           entityliving1.damageEntity(DamageSource.MAGIC, j);
/*     */         } else {
/*     */           
/* 136 */           entityliving1.damageEntity(DamageSource.b((potion != null) ? potion : entityliving1, entityliving), j);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 140 */       int j = (int)(d0 * (4 << i) + 0.5D);
/* 141 */       entityliving1.heal(j, EntityRegainHealthEvent.RegainReason.MAGIC);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isInstant() {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(int i, int j) {
/* 152 */     if (this.id == REGENERATION.id) {
/* 153 */       int k = 50 >> j;
/* 154 */       return (k > 0) ? ((i % k == 0)) : true;
/* 155 */     }  if (this.id == POISON.id) {
/* 156 */       int k = 25 >> j;
/* 157 */       return (k > 0) ? ((i % k == 0)) : true;
/* 158 */     }  if (this.id == WITHER.id) {
/* 159 */       int k = 40 >> j;
/* 160 */       return (k > 0) ? ((i % k == 0)) : true;
/*     */     } 
/* 162 */     return (this.id == HUNGER.id);
/*     */   }
/*     */ 
/*     */   
/*     */   public MobEffectList b(String s) {
/* 167 */     this.L = s;
/* 168 */     return this;
/*     */   }
/*     */   
/*     */   public String a() {
/* 172 */     return this.L;
/*     */   }
/*     */   
/*     */   protected MobEffectList a(double d0) {
/* 176 */     this.N = d0;
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   public double getDurationModifier() {
/* 181 */     return this.N;
/*     */   }
/*     */   
/*     */   public boolean i() {
/* 185 */     return this.O;
/*     */   }
/*     */   
/*     */   public int j() {
/* 189 */     return this.K;
/*     */   }
/*     */   
/*     */   public MobEffectList a(IAttribute iattribute, String s, double d0, int i) {
/* 193 */     AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(s), a(), d0, i);
/*     */     
/* 195 */     this.I.put(iattribute, attributemodifier);
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
/* 200 */     Iterator<Map.Entry> iterator = this.I.entrySet().iterator();
/*     */     
/* 202 */     while (iterator.hasNext()) {
/* 203 */       Map.Entry entry = iterator.next();
/* 204 */       AttributeInstance attributeinstance = attributemapbase.a((IAttribute)entry.getKey());
/*     */       
/* 206 */       if (attributeinstance != null) {
/* 207 */         attributeinstance.b((AttributeModifier)entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
/* 213 */     Iterator<Map.Entry> iterator = this.I.entrySet().iterator();
/*     */     
/* 215 */     while (iterator.hasNext()) {
/* 216 */       Map.Entry entry = iterator.next();
/* 217 */       AttributeInstance attributeinstance = attributemapbase.a((IAttribute)entry.getKey());
/*     */       
/* 219 */       if (attributeinstance != null) {
/* 220 */         AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
/*     */         
/* 222 */         attributeinstance.b(attributemodifier);
/* 223 */         attributeinstance.a(new AttributeModifier(attributemodifier.a(), a() + " " + i, a(i, attributemodifier), attributemodifier.c()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public double a(int i, AttributeModifier attributemodifier) {
/* 229 */     return attributemodifier.d() * (i + 1);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MobEffectList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */