/*      */ package net.minecraft.server.v1_7_R4;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.TrigMath;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.entity.EntityDamageEvent;
/*      */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ 
/*      */ public abstract class EntityLiving
/*      */   extends Entity {
/*   23 */   private static final UUID b = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
/*   24 */   private static final AttributeModifier c = (new AttributeModifier(b, "Sprinting speed boost", 0.30000001192092896D, 2)).a(false);
/*      */   private AttributeMapBase d;
/*   26 */   public CombatTracker combatTracker = new CombatTracker(this);
/*   27 */   public final HashMap effects = new HashMap<Object, Object>();
/*   28 */   private final ItemStack[] g = new ItemStack[5];
/*      */   public boolean at;
/*      */   public int au;
/*      */   public int av;
/*      */   public float aw;
/*      */   public int hurtTicks;
/*      */   public int ay;
/*      */   public float az;
/*      */   public int deathTicks;
/*      */   public int attackTicks;
/*      */   public float aC;
/*      */   public float aD;
/*      */   public float aE;
/*      */   public float aF;
/*      */   public float aG;
/*   43 */   public int maxNoDamageTicks = 20;
/*      */   public float aI;
/*      */   public float aJ;
/*      */   public float aK;
/*      */   public float aL;
/*      */   public float aM;
/*      */   public float aN;
/*      */   public float aO;
/*      */   public float aP;
/*   52 */   public float aQ = 0.02F;
/*      */   
/*      */   public EntityHuman killer;
/*      */   protected int lastDamageByPlayerTime;
/*      */   protected boolean aT;
/*      */   protected int aU;
/*      */   protected float aV;
/*      */   protected float aW;
/*      */   protected float aX;
/*      */   protected float aY;
/*      */   protected float aZ;
/*      */   protected int ba;
/*      */   public float lastDamage;
/*      */   protected boolean bc;
/*      */   public float bd;
/*      */   public float be;
/*      */   protected float bf;
/*      */   protected int bg;
/*      */   protected double bh;
/*      */   protected double bi;
/*      */   protected double bj;
/*      */   protected double bk;
/*      */   protected double bl;
/*      */   public boolean updateEffects = true;
/*      */   public EntityLiving lastDamager;
/*      */   private int bm;
/*      */   private EntityLiving bn;
/*      */   private int bo;
/*      */   private float bp;
/*      */   private int bq;
/*      */   private float br;
/*      */   public int expToDrop;
/*   84 */   public int maxAirTicks = 300;
/*   85 */   ArrayList<ItemStack> drops = null;
/*      */ 
/*      */ 
/*      */   
/*      */   public void inactiveTick() {
/*   90 */     super.inactiveTick();
/*   91 */     this.aU++;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLiving(World world) {
/*   96 */     super(world);
/*   97 */     aD();
/*      */     
/*   99 */     this.datawatcher.watch(6, Float.valueOf((float)getAttributeInstance(GenericAttributes.maxHealth).getValue()));
/*  100 */     this.k = true;
/*  101 */     this.aL = (float)(Math.random() + 1.0D) * 0.01F;
/*  102 */     setPosition(this.locX, this.locY, this.locZ);
/*  103 */     this.aK = (float)Math.random() * 12398.0F;
/*  104 */     this.yaw = (float)(Math.random() * 3.1415927410125732D * 2.0D);
/*  105 */     this.aO = this.yaw;
/*  106 */     this.W = 0.5F;
/*      */   }
/*      */   
/*      */   protected void c() {
/*  110 */     this.datawatcher.a(7, Integer.valueOf(0));
/*  111 */     this.datawatcher.a(8, Byte.valueOf((byte)0));
/*  112 */     this.datawatcher.a(9, Byte.valueOf((byte)0));
/*  113 */     this.datawatcher.a(6, Float.valueOf(1.0F));
/*      */   }
/*      */   
/*      */   protected void aD() {
/*  117 */     getAttributeMap().b(GenericAttributes.maxHealth);
/*  118 */     getAttributeMap().b(GenericAttributes.c);
/*  119 */     getAttributeMap().b(GenericAttributes.d);
/*  120 */     if (!bk()) {
/*  121 */       getAttributeInstance(GenericAttributes.d).setValue(0.10000000149011612D);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a(double d0, boolean flag) {
/*  126 */     if (!M()) {
/*  127 */       N();
/*      */     }
/*      */     
/*  130 */     if (flag && this.fallDistance > 0.0F) {
/*  131 */       int i = MathHelper.floor(this.locX);
/*  132 */       int j = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
/*  133 */       int k = MathHelper.floor(this.locZ);
/*  134 */       Block block = this.world.getType(i, j, k);
/*      */       
/*  136 */       if (block.getMaterial() == Material.AIR) {
/*  137 */         int l = this.world.getType(i, j - 1, k).b();
/*      */         
/*  139 */         if (l == 11 || l == 32 || l == 21) {
/*  140 */           block = this.world.getType(i, j - 1, k);
/*      */         }
/*  142 */       } else if (!this.world.isStatic && this.fallDistance > 3.0F) {
/*      */         
/*  144 */         if (this instanceof EntityPlayer) {
/*  145 */           this.world.a((EntityHuman)this, 2006, i, j, k, MathHelper.f(this.fallDistance - 3.0F));
/*  146 */           ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutWorldEvent(2006, i, j, k, MathHelper.f(this.fallDistance - 3.0F), false));
/*      */         } else {
/*  148 */           this.world.triggerEffect(2006, i, j, k, MathHelper.f(this.fallDistance - 3.0F));
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  153 */       block.a(this.world, i, j, k, this, this.fallDistance);
/*      */     } 
/*      */     
/*  156 */     super.a(d0, flag);
/*      */   }
/*      */   
/*      */   public boolean aE() {
/*  160 */     return false;
/*      */   }
/*      */   
/*      */   public void C() {
/*  164 */     this.aC = this.aD;
/*  165 */     super.C();
/*  166 */     this.world.methodProfiler.a("livingEntityBaseTick");
/*  167 */     if (isAlive() && inBlock()) {
/*  168 */       damageEntity(DamageSource.STUCK, 1.0F);
/*      */     }
/*      */     
/*  171 */     if (isFireproof() || this.world.isStatic) {
/*  172 */       extinguish();
/*      */     }
/*      */     
/*  175 */     boolean flag = (this instanceof EntityHuman && ((EntityHuman)this).abilities.isInvulnerable);
/*      */     
/*  177 */     if (isAlive() && a(Material.WATER)) {
/*  178 */       if (!aE() && !hasEffect(MobEffectList.WATER_BREATHING.id) && !flag) {
/*  179 */         setAirTicks(j(getAirTicks()));
/*  180 */         if (getAirTicks() == -20) {
/*  181 */           setAirTicks(0);
/*      */           
/*  183 */           for (int i = 0; i < 8; i++) {
/*  184 */             float f = this.random.nextFloat() - this.random.nextFloat();
/*  185 */             float f1 = this.random.nextFloat() - this.random.nextFloat();
/*  186 */             float f2 = this.random.nextFloat() - this.random.nextFloat();
/*      */             
/*  188 */             this.world.addParticle("bubble", this.locX + f, this.locY + f1, this.locZ + f2, this.motX, this.motY, this.motZ);
/*      */           } 
/*      */           
/*  191 */           damageEntity(DamageSource.DROWN, 2.0F);
/*      */         } 
/*      */       } 
/*      */       
/*  195 */       if (!this.world.isStatic && am() && this.vehicle instanceof EntityLiving) {
/*  196 */         mount((Entity)null);
/*      */       
/*      */       }
/*      */     }
/*  200 */     else if (getAirTicks() != 300) {
/*  201 */       setAirTicks(this.maxAirTicks);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  206 */     if (isAlive() && L()) {
/*  207 */       extinguish();
/*      */     }
/*      */     
/*  210 */     this.aI = this.aJ;
/*  211 */     if (this.attackTicks > 0) {
/*  212 */       this.attackTicks--;
/*      */     }
/*      */     
/*  215 */     if (this.hurtTicks > 0) {
/*  216 */       this.hurtTicks--;
/*      */     }
/*      */     
/*  219 */     if (this.noDamageTicks > 0 && !(this instanceof EntityPlayer)) {
/*  220 */       this.noDamageTicks--;
/*      */     }
/*      */     
/*  223 */     if (getHealth() <= 0.0F) {
/*  224 */       aF();
/*      */     }
/*      */     
/*  227 */     if (this.lastDamageByPlayerTime > 0) {
/*  228 */       this.lastDamageByPlayerTime--;
/*      */     } else {
/*  230 */       this.killer = null;
/*      */     } 
/*      */     
/*  233 */     if (this.bn != null && !this.bn.isAlive()) {
/*  234 */       this.bn = null;
/*      */     }
/*      */     
/*  237 */     if (this.lastDamager != null) {
/*  238 */       if (!this.lastDamager.isAlive()) {
/*  239 */         b((EntityLiving)null);
/*  240 */       } else if (this.ticksLived - this.bm > 100) {
/*  241 */         b((EntityLiving)null);
/*      */       } 
/*      */     }
/*      */     
/*  245 */     aO();
/*  246 */     this.aY = this.aX;
/*  247 */     this.aN = this.aM;
/*  248 */     this.aP = this.aO;
/*  249 */     this.lastYaw = this.yaw;
/*  250 */     this.lastPitch = this.pitch;
/*  251 */     this.world.methodProfiler.b();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getExpReward() {
/*  256 */     int exp = getExpValue(this.killer);
/*      */     
/*  258 */     if (!this.world.isStatic && (this.lastDamageByPlayerTime > 0 || alwaysGivesExp()) && aG() && this.world.getGameRules().getBoolean("doMobLoot")) {
/*  259 */       return exp;
/*      */     }
/*  261 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBaby() {
/*  267 */     return false;
/*      */   }
/*      */   
/*      */   protected void aF() {
/*  271 */     this.deathTicks++;
/*  272 */     if (this.deathTicks >= 20 && !this.dead) {
/*      */ 
/*      */ 
/*      */       
/*  276 */       int i = this.expToDrop;
/*  277 */       while (i > 0) {
/*  278 */         int j = EntityExperienceOrb.getOrbValue(i);
/*      */         
/*  280 */         i -= j;
/*  281 */         this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
/*      */       } 
/*  283 */       this.expToDrop = 0;
/*      */ 
/*      */       
/*  286 */       die();
/*      */       
/*  288 */       for (i = 0; i < 20; i++) {
/*  289 */         double d0 = this.random.nextGaussian() * 0.02D;
/*  290 */         double d1 = this.random.nextGaussian() * 0.02D;
/*  291 */         double d2 = this.random.nextGaussian() * 0.02D;
/*      */         
/*  293 */         this.world.addParticle("explode", this.locX + (this.random.nextFloat() * this.width * 2.0F) - this.width, this.locY + (this.random.nextFloat() * this.length), this.locZ + (this.random.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean aG() {
/*  299 */     return !isBaby();
/*      */   }
/*      */   
/*      */   protected int j(int i) {
/*  303 */     int j = EnchantmentManager.getOxygenEnchantmentLevel(this);
/*      */     
/*  305 */     return (j > 0 && this.random.nextInt(j + 1) > 0) ? i : (i - 1);
/*      */   }
/*      */   
/*      */   protected int getExpValue(EntityHuman entityhuman) {
/*  309 */     return 0;
/*      */   }
/*      */   
/*      */   protected boolean alwaysGivesExp() {
/*  313 */     return false;
/*      */   }
/*      */   
/*      */   public Random aI() {
/*  317 */     return this.random;
/*      */   }
/*      */   
/*      */   public EntityLiving getLastDamager() {
/*  321 */     return this.lastDamager;
/*      */   }
/*      */   
/*      */   public int aK() {
/*  325 */     return this.bm;
/*      */   }
/*      */   
/*      */   public void b(EntityLiving entityliving) {
/*  329 */     this.lastDamager = entityliving;
/*  330 */     this.bm = this.ticksLived;
/*      */   }
/*      */   
/*      */   public EntityLiving aL() {
/*  334 */     return this.bn;
/*      */   }
/*      */   
/*      */   public int aM() {
/*  338 */     return this.bo;
/*      */   }
/*      */   
/*      */   public void l(Entity entity) {
/*  342 */     if (entity instanceof EntityLiving) {
/*  343 */       this.bn = (EntityLiving)entity;
/*      */     } else {
/*  345 */       this.bn = null;
/*      */     } 
/*      */     
/*  348 */     this.bo = this.ticksLived;
/*      */   }
/*      */   
/*      */   public int aN() {
/*  352 */     return this.aU;
/*      */   }
/*      */   
/*      */   public void b(NBTTagCompound nbttagcompound) {
/*  356 */     nbttagcompound.setFloat("HealF", getHealth());
/*  357 */     nbttagcompound.setShort("Health", (short)(int)Math.ceil(getHealth()));
/*  358 */     nbttagcompound.setShort("HurtTime", (short)this.hurtTicks);
/*  359 */     nbttagcompound.setShort("DeathTime", (short)this.deathTicks);
/*  360 */     nbttagcompound.setShort("AttackTime", (short)this.attackTicks);
/*  361 */     nbttagcompound.setFloat("AbsorptionAmount", getAbsorptionHearts());
/*  362 */     ItemStack[] aitemstack = getEquipment();
/*  363 */     int i = aitemstack.length;
/*      */ 
/*      */     
/*      */     int j;
/*      */     
/*  368 */     for (j = 0; j < i; j++) {
/*  369 */       ItemStack itemstack = aitemstack[j];
/*  370 */       if (itemstack != null) {
/*  371 */         this.d.a(itemstack.D());
/*      */       }
/*      */     } 
/*      */     
/*  375 */     nbttagcompound.set("Attributes", GenericAttributes.a(getAttributeMap()));
/*  376 */     aitemstack = getEquipment();
/*  377 */     i = aitemstack.length;
/*      */     
/*  379 */     for (j = 0; j < i; j++) {
/*  380 */       ItemStack itemstack = aitemstack[j];
/*  381 */       if (itemstack != null) {
/*  382 */         this.d.b(itemstack.D());
/*      */       }
/*      */     } 
/*      */     
/*  386 */     if (!this.effects.isEmpty()) {
/*  387 */       NBTTagList nbttaglist = new NBTTagList();
/*  388 */       Iterator<MobEffect> iterator = this.effects.values().iterator();
/*      */       
/*  390 */       while (iterator.hasNext()) {
/*  391 */         MobEffect mobeffect = iterator.next();
/*      */         
/*  393 */         nbttaglist.add(mobeffect.a(new NBTTagCompound()));
/*      */       } 
/*      */       
/*  396 */       nbttagcompound.set("ActiveEffects", nbttaglist);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(NBTTagCompound nbttagcompound) {
/*  401 */     setAbsorptionHearts(nbttagcompound.getFloat("AbsorptionAmount"));
/*  402 */     if (nbttagcompound.hasKeyOfType("Attributes", 9) && this.world != null && !this.world.isStatic) {
/*  403 */       GenericAttributes.a(getAttributeMap(), nbttagcompound.getList("Attributes", 10));
/*      */     }
/*      */     
/*  406 */     if (nbttagcompound.hasKeyOfType("ActiveEffects", 9)) {
/*  407 */       NBTTagList nbttaglist = nbttagcompound.getList("ActiveEffects", 10);
/*      */       
/*  409 */       for (int i = 0; i < nbttaglist.size(); i++) {
/*  410 */         NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/*  411 */         MobEffect mobeffect = MobEffect.b(nbttagcompound1);
/*      */         
/*  413 */         if (mobeffect != null) {
/*  414 */           this.effects.put(Integer.valueOf(mobeffect.getEffectId()), mobeffect);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  420 */     if (nbttagcompound.hasKey("Bukkit.MaxHealth")) {
/*  421 */       NBTBase nbtbase = nbttagcompound.get("Bukkit.MaxHealth");
/*  422 */       if (nbtbase.getTypeId() == 5) {
/*  423 */         getAttributeInstance(GenericAttributes.maxHealth).setValue(((NBTTagFloat)nbtbase).c());
/*  424 */       } else if (nbtbase.getTypeId() == 3) {
/*  425 */         getAttributeInstance(GenericAttributes.maxHealth).setValue(((NBTTagInt)nbtbase).d());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  430 */     if (nbttagcompound.hasKeyOfType("HealF", 99)) {
/*  431 */       setHealth(nbttagcompound.getFloat("HealF"));
/*      */     } else {
/*  433 */       NBTBase nbtbase = nbttagcompound.get("Health");
/*      */       
/*  435 */       if (nbtbase == null) {
/*  436 */         setHealth(getMaxHealth());
/*  437 */       } else if (nbtbase.getTypeId() == 5) {
/*  438 */         setHealth(((NBTTagFloat)nbtbase).h());
/*  439 */       } else if (nbtbase.getTypeId() == 2) {
/*  440 */         setHealth(((NBTTagShort)nbtbase).e());
/*      */       } 
/*      */     } 
/*      */     
/*  444 */     this.hurtTicks = nbttagcompound.getShort("HurtTime");
/*  445 */     this.deathTicks = nbttagcompound.getShort("DeathTime");
/*  446 */     this.attackTicks = nbttagcompound.getShort("AttackTime");
/*      */   }
/*      */   
/*      */   protected void aO() {
/*  450 */     Iterator<Integer> iterator = this.effects.keySet().iterator();
/*      */     
/*  452 */     while (iterator.hasNext()) {
/*  453 */       Integer integer = iterator.next();
/*  454 */       MobEffect mobeffect = (MobEffect)this.effects.get(integer);
/*      */       
/*  456 */       if (!mobeffect.tick(this)) {
/*  457 */         if (!this.world.isStatic) {
/*  458 */           iterator.remove();
/*  459 */           b(mobeffect);
/*      */         }  continue;
/*  461 */       }  if (mobeffect.getDuration() % 600 == 0) {
/*  462 */         a(mobeffect, false);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  468 */     if (this.updateEffects) {
/*  469 */       if (!this.world.isStatic) {
/*  470 */         if (this.effects.isEmpty()) {
/*  471 */           this.datawatcher.watch(8, Byte.valueOf((byte)0));
/*  472 */           this.datawatcher.watch(7, Integer.valueOf(0));
/*  473 */           setInvisible(false);
/*      */         } else {
/*  475 */           int j = PotionBrewer.a(this.effects.values());
/*  476 */           this.datawatcher.watch(8, Byte.valueOf((byte)(PotionBrewer.b(this.effects.values()) ? 1 : 0)));
/*  477 */           this.datawatcher.watch(7, Integer.valueOf(j));
/*  478 */           setInvisible(hasEffect(MobEffectList.INVISIBILITY.id));
/*      */         } 
/*      */       }
/*      */       
/*  482 */       this.updateEffects = false;
/*      */     } 
/*      */     
/*  485 */     int i = this.datawatcher.getInt(7);
/*  486 */     boolean flag = (this.datawatcher.getByte(8) > 0);
/*      */     
/*  488 */     if (i > 0) {
/*  489 */       int j; boolean flag1 = false;
/*      */       
/*  491 */       if (!isInvisible()) {
/*  492 */         flag1 = this.random.nextBoolean();
/*      */       } else {
/*  494 */         flag1 = (this.random.nextInt(15) == 0);
/*      */       } 
/*      */       
/*  497 */       if (flag) {
/*  498 */         j = flag1 & ((this.random.nextInt(5) == 0) ? 1 : 0);
/*      */       }
/*      */       
/*  501 */       if (j != 0 && i > 0) {
/*  502 */         double d0 = (i >> 16 & 0xFF) / 255.0D;
/*  503 */         double d1 = (i >> 8 & 0xFF) / 255.0D;
/*  504 */         double d2 = (i >> 0 & 0xFF) / 255.0D;
/*      */         
/*  506 */         this.world.addParticle(flag ? "mobSpellAmbient" : "mobSpell", this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length - this.height, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, d0, d1, d2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeAllEffects() {
/*  512 */     Iterator<Integer> iterator = this.effects.keySet().iterator();
/*      */     
/*  514 */     while (iterator.hasNext()) {
/*  515 */       Integer integer = iterator.next();
/*  516 */       MobEffect mobeffect = (MobEffect)this.effects.get(integer);
/*      */       
/*  518 */       if (!this.world.isStatic) {
/*  519 */         iterator.remove();
/*  520 */         b(mobeffect);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Collection getEffects() {
/*  526 */     return this.effects.values();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect(int i) {
/*  531 */     return (this.effects.size() != 0 && this.effects.containsKey(Integer.valueOf(i)));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect(MobEffectList mobeffectlist) {
/*  536 */     return (this.effects.size() != 0 && this.effects.containsKey(Integer.valueOf(mobeffectlist.id)));
/*      */   }
/*      */   
/*      */   public MobEffect getEffect(MobEffectList mobeffectlist) {
/*  540 */     return (MobEffect)this.effects.get(Integer.valueOf(mobeffectlist.id));
/*      */   }
/*      */   
/*      */   public void addEffect(MobEffect mobeffect) {
/*  544 */     if (d(mobeffect)) {
/*  545 */       if (this.effects.containsKey(Integer.valueOf(mobeffect.getEffectId()))) {
/*  546 */         ((MobEffect)this.effects.get(Integer.valueOf(mobeffect.getEffectId()))).a(mobeffect);
/*  547 */         a((MobEffect)this.effects.get(Integer.valueOf(mobeffect.getEffectId())), true);
/*      */       } else {
/*  549 */         this.effects.put(Integer.valueOf(mobeffect.getEffectId()), mobeffect);
/*  550 */         a(mobeffect);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean d(MobEffect mobeffect) {
/*  556 */     if (getMonsterType() == EnumMonsterType.UNDEAD) {
/*  557 */       int i = mobeffect.getEffectId();
/*      */       
/*  559 */       if (i == MobEffectList.REGENERATION.id || i == MobEffectList.POISON.id) {
/*  560 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  564 */     return true;
/*      */   }
/*      */   
/*      */   public boolean aR() {
/*  568 */     return (getMonsterType() == EnumMonsterType.UNDEAD);
/*      */   }
/*      */   
/*      */   public void removeEffect(int i) {
/*  572 */     MobEffect mobeffect = (MobEffect)this.effects.remove(Integer.valueOf(i));
/*      */     
/*  574 */     if (mobeffect != null) {
/*  575 */       b(mobeffect);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a(MobEffect mobeffect) {
/*  580 */     this.updateEffects = true;
/*  581 */     if (!this.world.isStatic) {
/*  582 */       MobEffectList.byId[mobeffect.getEffectId()].b(this, getAttributeMap(), mobeffect.getAmplifier());
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a(MobEffect mobeffect, boolean flag) {
/*  587 */     this.updateEffects = true;
/*  588 */     if (flag && !this.world.isStatic) {
/*  589 */       MobEffectList.byId[mobeffect.getEffectId()].a(this, getAttributeMap(), mobeffect.getAmplifier());
/*  590 */       MobEffectList.byId[mobeffect.getEffectId()].b(this, getAttributeMap(), mobeffect.getAmplifier());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void b(MobEffect mobeffect) {
/*  595 */     this.updateEffects = true;
/*  596 */     if (!this.world.isStatic) {
/*  597 */       MobEffectList.byId[mobeffect.getEffectId()].a(this, getAttributeMap(), mobeffect.getAmplifier());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void heal(float f) {
/*  603 */     heal(f, EntityRegainHealthEvent.RegainReason.CUSTOM);
/*      */   }
/*      */   
/*      */   public void heal(float f, EntityRegainHealthEvent.RegainReason regainReason) {
/*  607 */     float f1 = getHealth();
/*      */     
/*  609 */     if (f1 > 0.0F) {
/*  610 */       EntityRegainHealthEvent event = new EntityRegainHealthEvent((Entity)getBukkitEntity(), f, regainReason);
/*  611 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*      */       
/*  613 */       if (!event.isCancelled()) {
/*  614 */         setHealth((float)(getHealth() + event.getAmount()));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final float getHealth() {
/*  621 */     if (this instanceof EntityPlayer) {
/*  622 */       return (float)((EntityPlayer)this).getBukkitEntity().getHealth();
/*      */     }
/*      */     
/*  625 */     return this.datawatcher.getFloat(6);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHealth(float f) {
/*  630 */     if (this instanceof EntityPlayer) {
/*  631 */       CraftPlayer player = ((EntityPlayer)this).getBukkitEntity();
/*      */       
/*  633 */       if (f < 0.0F) {
/*  634 */         player.setRealHealth(0.0D);
/*  635 */       } else if (f > player.getMaxHealth()) {
/*  636 */         player.setRealHealth(player.getMaxHealth());
/*      */       } else {
/*  638 */         player.setRealHealth(f);
/*      */       } 
/*      */       
/*  641 */       this.datawatcher.watch(6, Float.valueOf(player.getScaledHealth()));
/*      */       
/*      */       return;
/*      */     } 
/*  645 */     this.datawatcher.watch(6, Float.valueOf(MathHelper.a(f, 0.0F, getMaxHealth())));
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  649 */     if (isInvulnerable())
/*  650 */       return false; 
/*  651 */     if (this.world.isStatic) {
/*  652 */       return false;
/*      */     }
/*  654 */     this.aU = 0;
/*  655 */     if (getHealth() <= 0.0F)
/*  656 */       return false; 
/*  657 */     if (damagesource.o() && hasEffect(MobEffectList.FIRE_RESISTANCE)) {
/*  658 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  666 */     this.aF = 1.5F;
/*  667 */     boolean flag = true;
/*      */     
/*  669 */     if (this.noDamageTicks > this.maxNoDamageTicks / 2.0F) {
/*  670 */       if (f <= this.lastDamage) {
/*  671 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  675 */       if (!d(damagesource, f - this.lastDamage)) {
/*  676 */         return false;
/*      */       }
/*      */       
/*  679 */       this.lastDamage = f;
/*  680 */       flag = false;
/*      */     } else {
/*      */       
/*  683 */       float previousHealth = getHealth();
/*  684 */       if (!d(damagesource, f)) {
/*  685 */         return false;
/*      */       }
/*  687 */       this.lastDamage = f;
/*  688 */       this.aw = previousHealth;
/*  689 */       this.noDamageTicks = this.maxNoDamageTicks;
/*      */       
/*  691 */       this.hurtTicks = this.ay = 10;
/*      */     } 
/*      */     
/*  694 */     this.az = 0.0F;
/*  695 */     Entity entity = damagesource.getEntity();
/*      */     
/*  697 */     if (entity != null) {
/*  698 */       if (entity instanceof EntityLiving) {
/*  699 */         b((EntityLiving)entity);
/*      */       }
/*      */       
/*  702 */       if (entity instanceof EntityHuman) {
/*  703 */         this.lastDamageByPlayerTime = 100;
/*  704 */         this.killer = (EntityHuman)entity;
/*  705 */       } else if (entity instanceof EntityWolf) {
/*  706 */         EntityWolf entitywolf = (EntityWolf)entity;
/*      */         
/*  708 */         if (entitywolf.isTamed()) {
/*  709 */           this.lastDamageByPlayerTime = 100;
/*  710 */           this.killer = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  716 */     boolean knockbackCancelled = false;
/*  717 */     if (flag) if (!(knockbackCancelled = (this.world.paperSpigotConfig.disableExplosionKnockback && damagesource.isExplosion() && this instanceof EntityHuman))) {
/*      */         
/*  719 */         this.world.broadcastEntityEffect(this, (byte)2);
/*  720 */         if (damagesource != DamageSource.DROWN) {
/*  721 */           Q();
/*      */         }
/*      */         
/*  724 */         if (entity != null) {
/*  725 */           double d0 = entity.locX - this.locX;
/*      */           
/*      */           double d1;
/*      */           
/*  729 */           for (d1 = entity.locZ - this.locZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
/*  730 */             d0 = (Math.random() - Math.random()) * 0.01D;
/*      */           }
/*      */           
/*  733 */           this.az = (float)(Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - this.yaw;
/*  734 */           a(entity, f, d0, d1);
/*      */         } else {
/*  736 */           this.az = ((int)(Math.random() * 2.0D) * 180);
/*      */         } 
/*      */       } 
/*      */     
/*  740 */     if (knockbackCancelled) this.world.broadcastEntityEffect(this, (byte)2);
/*      */ 
/*      */ 
/*      */     
/*  744 */     if (getHealth() <= 0.0F) {
/*  745 */       String s = aU();
/*  746 */       if (flag && s != null) {
/*  747 */         makeSound(s, bf(), bg());
/*      */       }
/*      */       
/*  750 */       die(damagesource);
/*      */     } else {
/*  752 */       String s = aT();
/*  753 */       if (flag && s != null) {
/*  754 */         makeSound(s, bf(), bg());
/*      */       }
/*      */     } 
/*      */     
/*  758 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(ItemStack itemstack) {
/*  764 */     makeSound("random.break", 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);
/*      */     
/*  766 */     for (int i = 0; i < 5; i++) {
/*  767 */       Vec3D vec3d = Vec3D.a((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*      */       
/*  769 */       vec3d.a(-this.pitch * 3.1415927F / 180.0F);
/*  770 */       vec3d.b(-this.yaw * 3.1415927F / 180.0F);
/*  771 */       Vec3D vec3d1 = Vec3D.a((this.random.nextFloat() - 0.5D) * 0.3D, -this.random.nextFloat() * 0.6D - 0.3D, 0.6D);
/*      */       
/*  773 */       vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
/*  774 */       vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
/*  775 */       vec3d1 = vec3d1.add(this.locX, this.locY + getHeadHeight(), this.locZ);
/*  776 */       this.world.addParticle("iconcrack_" + Item.getId(itemstack.getItem()), vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void die(DamageSource damagesource) {
/*  781 */     Entity entity = damagesource.getEntity();
/*  782 */     EntityLiving entityliving = aX();
/*      */     
/*  784 */     if (this.ba >= 0 && entityliving != null) {
/*  785 */       entityliving.b(this, this.ba);
/*      */     }
/*      */     
/*  788 */     if (entity != null) {
/*  789 */       entity.a(this);
/*      */     }
/*      */     
/*  792 */     this.aT = true;
/*  793 */     aW().g();
/*  794 */     if (!this.world.isStatic) {
/*  795 */       int i = 0;
/*      */       
/*  797 */       if (entity instanceof EntityHuman) {
/*  798 */         i = EnchantmentManager.getBonusMonsterLootEnchantmentLevel((EntityLiving)entity);
/*      */       }
/*      */       
/*  801 */       if (aG() && this.world.getGameRules().getBoolean("doMobLoot")) {
/*  802 */         this.drops = new ArrayList<ItemStack>();
/*      */         
/*  804 */         dropDeathLoot((this.lastDamageByPlayerTime > 0), i);
/*  805 */         dropEquipment((this.lastDamageByPlayerTime > 0), i);
/*  806 */         if (this.lastDamageByPlayerTime > 0) {
/*  807 */           int j = this.random.nextInt(200) - i;
/*      */           
/*  809 */           if (j < 5) {
/*  810 */             getRareDrop((j <= 0) ? 1 : 0);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  815 */         CraftEventFactory.callEntityDeathEvent(this, this.drops);
/*  816 */         this.drops = null;
/*      */       } else {
/*  818 */         CraftEventFactory.callEntityDeathEvent(this);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  823 */     this.world.broadcastEntityEffect(this, (byte)3);
/*      */   }
/*      */   
/*      */   protected void dropEquipment(boolean flag, int i) {}
/*      */   
/*      */   public void a(Entity entity, float f, double d0, double d1) {
/*  829 */     if (this.random.nextDouble() >= getAttributeInstance(GenericAttributes.c).getValue()) {
/*  830 */       this.al = true;
/*  831 */       float f1 = MathHelper.sqrt(d0 * d0 + d1 * d1);
/*  832 */       float f2 = 0.4F;
/*      */       
/*  834 */       this.motX /= 2.0D;
/*  835 */       this.motY /= 2.0D;
/*  836 */       this.motZ /= 2.0D;
/*  837 */       this.motX -= d0 / f1 * f2;
/*  838 */       this.motY += f2;
/*  839 */       this.motZ -= d1 / f1 * f2;
/*  840 */       if (this.motY > 0.4000000059604645D) {
/*  841 */         this.motY = 0.4000000059604645D;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String aT() {
/*  847 */     return "game.neutral.hurt";
/*      */   }
/*      */   
/*      */   protected String aU() {
/*  851 */     return "game.neutral.die";
/*      */   }
/*      */   
/*      */   protected void getRareDrop(int i) {}
/*      */   
/*      */   protected void dropDeathLoot(boolean flag, int i) {}
/*      */   
/*      */   public boolean h_() {
/*  859 */     int i = MathHelper.floor(this.locX);
/*  860 */     int j = MathHelper.floor(this.boundingBox.b);
/*  861 */     int k = MathHelper.floor(this.locZ);
/*  862 */     Block block = this.world.getType(i, j, k);
/*      */     
/*  864 */     return (block == Blocks.LADDER || block == Blocks.VINE);
/*      */   }
/*      */   
/*      */   public boolean isAlive() {
/*  868 */     return (!this.dead && getHealth() > 0.0F);
/*      */   }
/*      */   
/*      */   protected void b(float f) {
/*  872 */     super.b(f);
/*  873 */     MobEffect mobeffect = getEffect(MobEffectList.JUMP);
/*  874 */     float f1 = (mobeffect != null) ? (mobeffect.getAmplifier() + 1) : 0.0F;
/*  875 */     int i = MathHelper.f(f - 3.0F - f1);
/*      */     
/*  877 */     if (i > 0) {
/*      */       
/*  879 */       if (!damageEntity(DamageSource.FALL, i)) {
/*      */         return;
/*      */       }
/*      */       
/*  883 */       makeSound(o(i), 1.0F, 1.0F);
/*      */       
/*  885 */       int j = MathHelper.floor(this.locX);
/*  886 */       int k = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
/*  887 */       int l = MathHelper.floor(this.locZ);
/*  888 */       Block block = this.world.getType(j, k, l);
/*      */       
/*  890 */       if (block.getMaterial() != Material.AIR) {
/*  891 */         StepSound stepsound = block.stepSound;
/*      */         
/*  893 */         makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.5F, stepsound.getVolume2() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String o(int i) {
/*  899 */     return (i > 4) ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
/*      */   }
/*      */   
/*      */   public int aV() {
/*  903 */     int i = 0;
/*  904 */     ItemStack[] aitemstack = getEquipment();
/*  905 */     int j = aitemstack.length;
/*      */     
/*  907 */     for (int k = 0; k < j; k++) {
/*  908 */       ItemStack itemstack = aitemstack[k];
/*      */       
/*  910 */       if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
/*  911 */         int l = ((ItemArmor)itemstack.getItem()).c;
/*      */         
/*  913 */         i += l;
/*      */       } 
/*      */     } 
/*      */     
/*  917 */     return i;
/*      */   }
/*      */   
/*      */   protected void damageArmor(float f) {}
/*      */   
/*      */   protected float applyArmorModifier(DamageSource damagesource, float f) {
/*  923 */     if (!damagesource.ignoresArmor()) {
/*  924 */       int i = 25 - aV();
/*  925 */       float f1 = f * i;
/*      */ 
/*      */       
/*  928 */       f = f1 / 25.0F;
/*      */     } 
/*      */     
/*  931 */     return f;
/*      */   }
/*      */   
/*      */   protected float applyMagicModifier(DamageSource damagesource, float f) {
/*  935 */     if (damagesource.isStarvation()) {
/*  936 */       return f;
/*      */     }
/*  938 */     if (this instanceof EntityZombie) {
/*  939 */       f = f;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  954 */     if (f <= 0.0F) {
/*  955 */       return 0.0F;
/*      */     }
/*  957 */     int i = EnchantmentManager.a(getEquipment(), damagesource);
/*  958 */     if (i > 20) {
/*  959 */       i = 20;
/*      */     }
/*      */     
/*  962 */     if (i > 0 && i <= 20) {
/*  963 */       int j = 25 - i;
/*  964 */       float f1 = f * j;
/*  965 */       f = f1 / 25.0F;
/*      */     } 
/*      */     
/*  968 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean d(final DamageSource damagesource, float f) {
/*  975 */     if (!isInvulnerable()) {
/*  976 */       final boolean human = this instanceof EntityHuman;
/*  977 */       float originalDamage = f;
/*  978 */       Function<Double, Double> hardHat = new Function<Double, Double>()
/*      */         {
/*      */           public Double apply(Double f) {
/*  981 */             if ((damagesource == DamageSource.ANVIL || damagesource == DamageSource.FALLING_BLOCK) && EntityLiving.this.getEquipment(4) != null) {
/*  982 */               return Double.valueOf(-(f.doubleValue() - f.doubleValue() * 0.75D));
/*      */             }
/*  984 */             return Double.valueOf(-0.0D);
/*      */           }
/*      */         };
/*  987 */       float hardHatModifier = ((Double)hardHat.apply(Double.valueOf(f))).floatValue();
/*  988 */       f += hardHatModifier;
/*      */       
/*  990 */       Function<Double, Double> blocking = new Function<Double, Double>()
/*      */         {
/*      */           public Double apply(Double f) {
/*  993 */             if (human && 
/*  994 */               !damagesource.ignoresArmor() && ((EntityHuman)EntityLiving.this).isBlocking() && f.doubleValue() > 0.0D) {
/*  995 */               return Double.valueOf(-(f.doubleValue() - (1.0D + f.doubleValue()) * 0.5D));
/*      */             }
/*      */             
/*  998 */             return Double.valueOf(-0.0D);
/*      */           }
/*      */         };
/* 1001 */       float blockingModifier = ((Double)blocking.apply(Double.valueOf(f))).floatValue();
/* 1002 */       f += blockingModifier;
/*      */       
/* 1004 */       Function<Double, Double> armor = new Function<Double, Double>()
/*      */         {
/*      */           public Double apply(Double f) {
/* 1007 */             return Double.valueOf(-(f.doubleValue() - EntityLiving.this.applyArmorModifier(damagesource, f.floatValue())));
/*      */           }
/*      */         };
/* 1010 */       float armorModifier = ((Double)armor.apply(Double.valueOf(f))).floatValue();
/* 1011 */       f += armorModifier;
/*      */       
/* 1013 */       Function<Double, Double> resistance = new Function<Double, Double>()
/*      */         {
/*      */           public Double apply(Double f) {
/* 1016 */             if (!damagesource.isStarvation() && EntityLiving.this.hasEffect(MobEffectList.RESISTANCE) && damagesource != DamageSource.OUT_OF_WORLD) {
/* 1017 */               int i = (EntityLiving.this.getEffect(MobEffectList.RESISTANCE).getAmplifier() + 1) * 5;
/* 1018 */               int j = 25 - i;
/* 1019 */               float f1 = f.floatValue() * j;
/* 1020 */               return Double.valueOf(-(f.doubleValue() - (f1 / 25.0F)));
/*      */             } 
/* 1022 */             return Double.valueOf(-0.0D);
/*      */           }
/*      */         };
/* 1025 */       float resistanceModifier = ((Double)resistance.apply(Double.valueOf(f))).floatValue();
/* 1026 */       f += resistanceModifier;
/*      */       
/* 1028 */       Function<Double, Double> magic = new Function<Double, Double>()
/*      */         {
/*      */           public Double apply(Double f) {
/* 1031 */             return Double.valueOf(-(f.doubleValue() - EntityLiving.this.applyMagicModifier(damagesource, f.floatValue())));
/*      */           }
/*      */         };
/* 1034 */       float magicModifier = ((Double)magic.apply(Double.valueOf(f))).floatValue();
/* 1035 */       f += magicModifier;
/*      */       
/* 1037 */       Function<Double, Double> absorption = new Function<Double, Double>()
/*      */         {
/*      */           public Double apply(Double f) {
/* 1040 */             return Double.valueOf(-Math.max(f.doubleValue() - Math.max(f.doubleValue() - EntityLiving.this.getAbsorptionHearts(), 0.0D), 0.0D));
/*      */           }
/*      */         };
/* 1043 */       float absorptionModifier = ((Double)absorption.apply(Double.valueOf(f))).floatValue();
/*      */       
/* 1045 */       EntityDamageEvent event = CraftEventFactory.handleLivingEntityDamageEvent(this, damagesource, originalDamage, hardHatModifier, blockingModifier, armorModifier, resistanceModifier, magicModifier, absorptionModifier, hardHat, blocking, armor, resistance, magic, absorption);
/* 1046 */       if (event.isCancelled()) {
/* 1047 */         return false;
/*      */       }
/*      */       
/* 1050 */       f = (float)event.getFinalDamage();
/*      */ 
/*      */       
/* 1053 */       if ((damagesource == DamageSource.ANVIL || damagesource == DamageSource.FALLING_BLOCK) && getEquipment(4) != null) {
/* 1054 */         getEquipment(4).damage((int)(event.getDamage() * 4.0D + this.random.nextFloat() * event.getDamage() * 2.0D), this);
/*      */       }
/*      */ 
/*      */       
/* 1058 */       if (!damagesource.ignoresArmor()) {
/* 1059 */         float armorDamage = (float)(event.getDamage() + event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) + event.getDamage(EntityDamageEvent.DamageModifier.HARD_HAT));
/* 1060 */         damageArmor(armorDamage);
/*      */       } 
/*      */       
/* 1063 */       absorptionModifier = (float)-event.getDamage(EntityDamageEvent.DamageModifier.ABSORPTION);
/* 1064 */       setAbsorptionHearts(Math.max(getAbsorptionHearts() - absorptionModifier, 0.0F));
/* 1065 */       if (f != 0.0F) {
/* 1066 */         if (human) {
/* 1067 */           ((EntityHuman)this).applyExhaustion(damagesource.getExhaustionCost());
/*      */         }
/*      */         
/* 1070 */         float f2 = getHealth();
/*      */         
/* 1072 */         setHealth(f2 - f);
/* 1073 */         aW().a(damagesource, f2, f);
/*      */         
/* 1075 */         if (human) {
/* 1076 */           return true;
/*      */         }
/*      */         
/* 1079 */         setAbsorptionHearts(getAbsorptionHearts() - f);
/*      */       } 
/* 1081 */       return true;
/*      */     } 
/* 1083 */     return false;
/*      */   }
/*      */   
/*      */   public CombatTracker aW() {
/* 1087 */     return this.combatTracker;
/*      */   }
/*      */   
/*      */   public EntityLiving aX() {
/* 1091 */     return (this.combatTracker.c() != null) ? this.combatTracker.c() : ((this.killer != null) ? this.killer : ((this.lastDamager != null) ? this.lastDamager : null));
/*      */   }
/*      */   
/*      */   public final float getMaxHealth() {
/* 1095 */     return (float)getAttributeInstance(GenericAttributes.maxHealth).getValue();
/*      */   }
/*      */   
/*      */   public final int aZ() {
/* 1099 */     return this.datawatcher.getByte(9);
/*      */   }
/*      */   
/*      */   public final void p(int i) {
/* 1103 */     this.datawatcher.watch(9, Byte.valueOf((byte)i));
/*      */   }
/*      */   
/*      */   private int j() {
/* 1107 */     return hasEffect(MobEffectList.FASTER_DIG) ? (6 - (1 + getEffect(MobEffectList.FASTER_DIG).getAmplifier()) * 1) : (hasEffect(MobEffectList.SLOWER_DIG) ? (6 + (1 + getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) * 2) : 6);
/*      */   }
/*      */   
/*      */   public void ba() {
/* 1111 */     if (!this.at || this.au >= j() / 2 || this.au < 0) {
/* 1112 */       this.au = -1;
/* 1113 */       this.at = true;
/* 1114 */       if (this.world instanceof WorldServer) {
/* 1115 */         ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutAnimation(this, 0));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void G() {
/* 1121 */     damageEntity(DamageSource.OUT_OF_WORLD, 4.0F);
/*      */   }
/*      */   
/*      */   protected void bb() {
/* 1125 */     int i = j();
/*      */     
/* 1127 */     if (this.at) {
/* 1128 */       this.au++;
/* 1129 */       if (this.au >= i) {
/* 1130 */         this.au = 0;
/* 1131 */         this.at = false;
/*      */       } 
/*      */     } else {
/* 1134 */       this.au = 0;
/*      */     } 
/*      */     
/* 1137 */     this.aD = this.au / i;
/*      */   }
/*      */   
/*      */   public AttributeInstance getAttributeInstance(IAttribute iattribute) {
/* 1141 */     return getAttributeMap().a(iattribute);
/*      */   }
/*      */   
/*      */   public AttributeMapBase getAttributeMap() {
/* 1145 */     if (this.d == null) {
/* 1146 */       this.d = new AttributeMapServer();
/*      */     }
/*      */     
/* 1149 */     return this.d;
/*      */   }
/*      */   
/*      */   public EnumMonsterType getMonsterType() {
/* 1153 */     return EnumMonsterType.UNDEFINED;
/*      */   }
/*      */   
/*      */   public abstract ItemStack be();
/*      */   
/*      */   public abstract ItemStack getEquipment(int paramInt);
/*      */   
/*      */   public abstract void setEquipment(int paramInt, ItemStack paramItemStack);
/*      */   
/*      */   public void setSprinting(boolean flag) {
/* 1163 */     super.setSprinting(flag);
/* 1164 */     AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
/*      */     
/* 1166 */     if (attributeinstance.a(b) != null) {
/* 1167 */       attributeinstance.b(c);
/*      */     }
/*      */     
/* 1170 */     if (flag) {
/* 1171 */       attributeinstance.a(c);
/*      */     }
/*      */   }
/*      */   
/*      */   public abstract ItemStack[] getEquipment();
/*      */   
/*      */   protected float bf() {
/* 1178 */     return 1.0F;
/*      */   }
/*      */   
/*      */   protected float bg() {
/* 1182 */     return isBaby() ? ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F) : ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*      */   }
/*      */   
/*      */   protected boolean bh() {
/* 1186 */     return (getHealth() <= 0.0F);
/*      */   }
/*      */   
/*      */   public void enderTeleportTo(double d0, double d1, double d2) {
/* 1190 */     setPositionRotation(d0, d1, d2, this.yaw, this.pitch);
/*      */   }
/*      */   
/*      */   public void m(Entity entity) {
/* 1194 */     double d0 = entity.locX;
/* 1195 */     double d1 = entity.boundingBox.b + entity.length;
/* 1196 */     double d2 = entity.locZ;
/* 1197 */     byte b0 = 1;
/*      */     
/* 1199 */     for (int i = -b0; i <= b0; i++) {
/* 1200 */       for (int j = -b0; j < b0; j++) {
/* 1201 */         if (i != 0 || j != 0) {
/* 1202 */           int k = (int)(this.locX + i);
/* 1203 */           int l = (int)(this.locZ + j);
/* 1204 */           AxisAlignedBB axisalignedbb = this.boundingBox.c(i, 1.0D, j);
/*      */           
/* 1206 */           if (this.world.a(axisalignedbb).isEmpty()) {
/* 1207 */             if (World.a(this.world, k, (int)this.locY, l)) {
/* 1208 */               enderTeleportTo(this.locX + i, this.locY + 1.0D, this.locZ + j);
/*      */               
/*      */               return;
/*      */             } 
/* 1212 */             if (World.a(this.world, k, (int)this.locY - 1, l) || this.world.getType(k, (int)this.locY - 1, l).getMaterial() == Material.WATER) {
/* 1213 */               d0 = this.locX + i;
/* 1214 */               d1 = this.locY + 1.0D;
/* 1215 */               d2 = this.locZ + j;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1222 */     enderTeleportTo(d0, d1, d2);
/*      */   }
/*      */   
/*      */   protected void bj() {
/* 1226 */     this.motY = 0.41999998688697815D;
/* 1227 */     if (hasEffect(MobEffectList.JUMP)) {
/* 1228 */       this.motY += ((getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F);
/*      */     }
/*      */     
/* 1231 */     if (isSprinting()) {
/* 1232 */       float f = this.yaw * 0.017453292F;
/*      */       
/* 1234 */       this.motX -= (MathHelper.sin(f) * 0.2F);
/* 1235 */       this.motZ += (MathHelper.cos(f) * 0.2F);
/*      */     } 
/*      */     
/* 1238 */     this.al = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void e(float f, float f1) {
/* 1244 */     if (M() && (!(this instanceof EntityHuman) || !((EntityHuman)this).abilities.isFlying)) {
/* 1245 */       double d = this.locY;
/* 1246 */       a(f, f1, bk() ? 0.04F : 0.02F);
/* 1247 */       move(this.motX, this.motY, this.motZ);
/* 1248 */       this.motX *= 0.800000011920929D;
/* 1249 */       this.motY *= 0.800000011920929D;
/* 1250 */       this.motZ *= 0.800000011920929D;
/* 1251 */       this.motY -= 0.02D;
/* 1252 */       if (this.positionChanged && c(this.motX, this.motY + 0.6000000238418579D - this.locY + d, this.motZ)) {
/* 1253 */         this.motY = 0.30000001192092896D;
/*      */       }
/* 1255 */     } else if (P() && (!(this instanceof EntityHuman) || !((EntityHuman)this).abilities.isFlying)) {
/* 1256 */       double d = this.locY;
/* 1257 */       a(f, f1, 0.02F);
/* 1258 */       move(this.motX, this.motY, this.motZ);
/* 1259 */       this.motX *= 0.5D;
/* 1260 */       this.motY *= 0.5D;
/* 1261 */       this.motZ *= 0.5D;
/* 1262 */       this.motY -= 0.02D;
/* 1263 */       if (this.positionChanged && c(this.motX, this.motY + 0.6000000238418579D - this.locY + d, this.motZ)) {
/* 1264 */         this.motY = 0.30000001192092896D;
/*      */       }
/*      */     } else {
/* 1267 */       float f4, f2 = 0.91F;
/*      */       
/* 1269 */       if (this.onGround) {
/* 1270 */         f2 = (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ))).frictionFactor * 0.91F;
/*      */       }
/*      */       
/* 1273 */       float f3 = 0.16277136F / f2 * f2 * f2;
/*      */ 
/*      */       
/* 1276 */       if (this.onGround) {
/* 1277 */         f4 = bl() * f3;
/*      */       } else {
/* 1279 */         f4 = this.aQ;
/*      */       } 
/*      */       
/* 1282 */       a(f, f1, f4);
/* 1283 */       f2 = 0.91F;
/* 1284 */       if (this.onGround) {
/* 1285 */         f2 = (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ))).frictionFactor * 0.91F;
/*      */       }
/*      */       
/* 1288 */       if (h_()) {
/* 1289 */         float f5 = 0.15F;
/*      */         
/* 1291 */         if (this.motX < -f5) {
/* 1292 */           this.motX = -f5;
/*      */         }
/*      */         
/* 1295 */         if (this.motX > f5) {
/* 1296 */           this.motX = f5;
/*      */         }
/*      */         
/* 1299 */         if (this.motZ < -f5) {
/* 1300 */           this.motZ = -f5;
/*      */         }
/*      */         
/* 1303 */         if (this.motZ > f5) {
/* 1304 */           this.motZ = f5;
/*      */         }
/*      */         
/* 1307 */         this.fallDistance = 0.0F;
/* 1308 */         if (this.motY < -0.15D) {
/* 1309 */           this.motY = -0.15D;
/*      */         }
/*      */         
/* 1312 */         boolean flag = (isSneaking() && this instanceof EntityHuman);
/*      */         
/* 1314 */         if (flag && this.motY < 0.0D) {
/* 1315 */           this.motY = 0.0D;
/*      */         }
/*      */       } 
/*      */       
/* 1319 */       move(this.motX, this.motY, this.motZ);
/* 1320 */       if (this.positionChanged && h_()) {
/* 1321 */         this.motY = 0.2D;
/*      */       }
/*      */       
/* 1324 */       if (this.world.isStatic && (!this.world.isLoaded((int)this.locX, 0, (int)this.locZ) || !(this.world.getChunkAtWorldCoords((int)this.locX, (int)this.locZ)).d)) {
/* 1325 */         if (this.locY > 0.0D) {
/* 1326 */           this.motY = -0.1D;
/*      */         } else {
/* 1328 */           this.motY = 0.0D;
/*      */         } 
/*      */       } else {
/* 1331 */         this.motY -= 0.08D;
/*      */       } 
/*      */       
/* 1334 */       this.motY *= 0.9800000190734863D;
/* 1335 */       this.motX *= f2;
/* 1336 */       this.motZ *= f2;
/*      */     } 
/*      */     
/* 1339 */     this.aE = this.aF;
/* 1340 */     double d0 = this.locX - this.lastX;
/* 1341 */     double d1 = this.locZ - this.lastZ;
/* 1342 */     float f6 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
/*      */     
/* 1344 */     if (f6 > 1.0F) {
/* 1345 */       f6 = 1.0F;
/*      */     }
/*      */     
/* 1348 */     this.aF += (f6 - this.aF) * 0.4F;
/* 1349 */     this.aG += this.aF;
/*      */   }
/*      */   
/*      */   protected boolean bk() {
/* 1353 */     return false;
/*      */   }
/*      */   
/*      */   public float bl() {
/* 1357 */     return bk() ? this.bp : 0.1F;
/*      */   }
/*      */   
/*      */   public void i(float f) {
/* 1361 */     this.bp = f;
/*      */   }
/*      */   
/*      */   public boolean n(Entity entity) {
/* 1365 */     l(entity);
/* 1366 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSleeping() {
/* 1370 */     return false;
/*      */   }
/*      */   
/*      */   public void h() {
/* 1374 */     SpigotTimings.timerEntityBaseTick.startTiming();
/* 1375 */     super.h();
/* 1376 */     if (!this.world.isStatic) {
/* 1377 */       int i = aZ();
/*      */       
/* 1379 */       if (i > 0) {
/* 1380 */         if (this.av <= 0) {
/* 1381 */           this.av = 20 * (30 - i);
/*      */         }
/*      */         
/* 1384 */         this.av--;
/* 1385 */         if (this.av <= 0) {
/* 1386 */           p(i - 1);
/*      */         }
/*      */       } 
/*      */       
/* 1390 */       for (int j = 0; j < 5; j++) {
/* 1391 */         ItemStack itemstack = this.g[j];
/* 1392 */         ItemStack itemstack1 = getEquipment(j);
/*      */         
/* 1394 */         if (!ItemStack.matches(itemstack1, itemstack)) {
/* 1395 */           ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutEntityEquipment(getId(), j, itemstack1));
/* 1396 */           if (itemstack != null) {
/* 1397 */             this.d.a(itemstack.D());
/*      */           }
/*      */           
/* 1400 */           if (itemstack1 != null) {
/* 1401 */             this.d.b(itemstack1.D());
/*      */           }
/*      */           
/* 1404 */           this.g[j] = (itemstack1 == null) ? null : itemstack1.cloneItemStack();
/*      */         } 
/*      */       } 
/*      */       
/* 1408 */       if (this.ticksLived % 20 == 0) {
/* 1409 */         aW().g();
/*      */       }
/*      */     } 
/*      */     
/* 1413 */     SpigotTimings.timerEntityBaseTick.stopTiming();
/* 1414 */     e();
/* 1415 */     SpigotTimings.timerEntityTickRest.startTiming();
/* 1416 */     double d0 = this.locX - this.lastX;
/* 1417 */     double d1 = this.locZ - this.lastZ;
/* 1418 */     float f = (float)(d0 * d0 + d1 * d1);
/* 1419 */     float f1 = this.aM;
/* 1420 */     float f2 = 0.0F;
/*      */     
/* 1422 */     this.aV = this.aW;
/* 1423 */     float f3 = 0.0F;
/*      */     
/* 1425 */     if (f > 0.0025000002F) {
/* 1426 */       f3 = 1.0F;
/* 1427 */       f2 = (float)Math.sqrt(f) * 3.0F;
/*      */       
/* 1429 */       f1 = (float)TrigMath.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
/*      */     } 
/*      */     
/* 1432 */     if (this.aD > 0.0F) {
/* 1433 */       f1 = this.yaw;
/*      */     }
/*      */     
/* 1436 */     if (!this.onGround) {
/* 1437 */       f3 = 0.0F;
/*      */     }
/*      */     
/* 1440 */     this.aW += (f3 - this.aW) * 0.3F;
/* 1441 */     this.world.methodProfiler.a("headTurn");
/* 1442 */     f2 = f(f1, f2);
/* 1443 */     this.world.methodProfiler.b();
/* 1444 */     this.world.methodProfiler.a("rangeChecks");
/*      */     
/* 1446 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 1447 */       this.lastYaw -= 360.0F;
/*      */     }
/*      */     
/* 1450 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 1451 */       this.lastYaw += 360.0F;
/*      */     }
/*      */     
/* 1454 */     while (this.aM - this.aN < -180.0F) {
/* 1455 */       this.aN -= 360.0F;
/*      */     }
/*      */     
/* 1458 */     while (this.aM - this.aN >= 180.0F) {
/* 1459 */       this.aN += 360.0F;
/*      */     }
/*      */     
/* 1462 */     while (this.pitch - this.lastPitch < -180.0F) {
/* 1463 */       this.lastPitch -= 360.0F;
/*      */     }
/*      */     
/* 1466 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 1467 */       this.lastPitch += 360.0F;
/*      */     }
/*      */     
/* 1470 */     while (this.aO - this.aP < -180.0F) {
/* 1471 */       this.aP -= 360.0F;
/*      */     }
/*      */     
/* 1474 */     while (this.aO - this.aP >= 180.0F) {
/* 1475 */       this.aP += 360.0F;
/*      */     }
/*      */     
/* 1478 */     this.world.methodProfiler.b();
/* 1479 */     this.aX += f2;
/* 1480 */     SpigotTimings.timerEntityTickRest.stopTiming();
/*      */   }
/*      */   
/*      */   protected float f(float f, float f1) {
/* 1484 */     float f2 = MathHelper.g(f - this.aM);
/*      */     
/* 1486 */     this.aM += f2 * 0.3F;
/* 1487 */     float f3 = MathHelper.g(this.yaw - this.aM);
/* 1488 */     boolean flag = (f3 < -90.0F || f3 >= 90.0F);
/*      */     
/* 1490 */     if (f3 < -75.0F) {
/* 1491 */       f3 = -75.0F;
/*      */     }
/*      */     
/* 1494 */     if (f3 >= 75.0F) {
/* 1495 */       f3 = 75.0F;
/*      */     }
/*      */     
/* 1498 */     this.aM = this.yaw - f3;
/* 1499 */     if (f3 * f3 > 2500.0F) {
/* 1500 */       this.aM += f3 * 0.2F;
/*      */     }
/*      */     
/* 1503 */     if (flag) {
/* 1504 */       f1 *= -1.0F;
/*      */     }
/*      */     
/* 1507 */     return f1;
/*      */   }
/*      */   
/*      */   public void e() {
/* 1511 */     if (this.bq > 0) {
/* 1512 */       this.bq--;
/*      */     }
/*      */     
/* 1515 */     if (this.bg > 0) {
/* 1516 */       double d0 = this.locX + (this.bh - this.locX) / this.bg;
/* 1517 */       double d1 = this.locY + (this.bi - this.locY) / this.bg;
/* 1518 */       double d2 = this.locZ + (this.bj - this.locZ) / this.bg;
/* 1519 */       double d3 = MathHelper.g(this.bk - this.yaw);
/*      */       
/* 1521 */       this.yaw = (float)(this.yaw + d3 / this.bg);
/* 1522 */       this.pitch = (float)(this.pitch + (this.bl - this.pitch) / this.bg);
/* 1523 */       this.bg--;
/* 1524 */       setPosition(d0, d1, d2);
/* 1525 */       b(this.yaw, this.pitch);
/* 1526 */     } else if (!br()) {
/* 1527 */       this.motX *= 0.98D;
/* 1528 */       this.motY *= 0.98D;
/* 1529 */       this.motZ *= 0.98D;
/*      */     } 
/*      */     
/* 1532 */     if (Math.abs(this.motX) < 0.005D) {
/* 1533 */       this.motX = 0.0D;
/*      */     }
/*      */     
/* 1536 */     if (Math.abs(this.motY) < 0.005D) {
/* 1537 */       this.motY = 0.0D;
/*      */     }
/*      */     
/* 1540 */     if (Math.abs(this.motZ) < 0.005D) {
/* 1541 */       this.motZ = 0.0D;
/*      */     }
/*      */     
/* 1544 */     this.world.methodProfiler.a("ai");
/* 1545 */     SpigotTimings.timerEntityAI.startTiming();
/* 1546 */     if (bh()) {
/* 1547 */       this.bc = false;
/* 1548 */       this.bd = 0.0F;
/* 1549 */       this.be = 0.0F;
/* 1550 */       this.bf = 0.0F;
/* 1551 */     } else if (br()) {
/* 1552 */       if (bk()) {
/* 1553 */         this.world.methodProfiler.a("newAi");
/* 1554 */         bn();
/* 1555 */         this.world.methodProfiler.b();
/*      */       } else {
/* 1557 */         this.world.methodProfiler.a("oldAi");
/* 1558 */         bq();
/* 1559 */         this.world.methodProfiler.b();
/* 1560 */         this.aO = this.yaw;
/*      */       } 
/*      */     } 
/* 1563 */     SpigotTimings.timerEntityAI.stopTiming();
/*      */     
/* 1565 */     this.world.methodProfiler.b();
/* 1566 */     this.world.methodProfiler.a("jump");
/* 1567 */     if (this.bc) {
/* 1568 */       if (!M() && !P()) {
/* 1569 */         if (this.onGround && this.bq == 0) {
/* 1570 */           bj();
/* 1571 */           this.bq = 10;
/*      */         } 
/*      */       } else {
/* 1574 */         this.motY += 0.03999999910593033D;
/*      */       } 
/*      */     } else {
/* 1577 */       this.bq = 0;
/*      */     } 
/*      */     
/* 1580 */     this.world.methodProfiler.b();
/* 1581 */     this.world.methodProfiler.a("travel");
/* 1582 */     this.bd *= 0.98F;
/* 1583 */     this.be *= 0.98F;
/* 1584 */     this.bf *= 0.9F;
/* 1585 */     SpigotTimings.timerEntityAIMove.startTiming();
/* 1586 */     e(this.bd, this.be);
/* 1587 */     SpigotTimings.timerEntityAIMove.stopTiming();
/* 1588 */     this.world.methodProfiler.b();
/* 1589 */     this.world.methodProfiler.a("push");
/* 1590 */     if (!this.world.isStatic) {
/* 1591 */       SpigotTimings.timerEntityAICollision.startTiming();
/* 1592 */       bo();
/* 1593 */       SpigotTimings.timerEntityAICollision.stopTiming();
/*      */     } 
/*      */     
/* 1596 */     this.world.methodProfiler.b();
/*      */   }
/*      */   
/*      */   protected void bn() {}
/*      */   
/*      */   protected void bo() {
/* 1602 */     List<Entity> list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*      */     
/* 1604 */     if (R() && list != null && !list.isEmpty()) {
/* 1605 */       this.numCollisions -= this.world.spigotConfig.maxCollisionsPerEntity;
/* 1606 */       for (int i = 0; i < list.size() && 
/* 1607 */         this.numCollisions <= this.world.spigotConfig.maxCollisionsPerEntity; i++) {
/* 1608 */         Entity entity = list.get(i);
/*      */ 
/*      */ 
/*      */         
/* 1612 */         if (!(entity instanceof EntityLiving) || this instanceof EntityPlayer || this.ticksLived % 2 != 0)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1617 */           if (entity.S()) {
/* 1618 */             entity.numCollisions++;
/* 1619 */             this.numCollisions++;
/* 1620 */             o(entity);
/*      */           }  } 
/*      */       } 
/* 1623 */       this.numCollisions = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void o(Entity entity) {
/* 1628 */     entity.collide(this);
/*      */   }
/*      */   
/*      */   public void ab() {
/* 1632 */     super.ab();
/* 1633 */     this.aV = this.aW;
/* 1634 */     this.aW = 0.0F;
/* 1635 */     this.fallDistance = 0.0F;
/*      */   }
/*      */   
/*      */   protected void bp() {}
/*      */   
/*      */   protected void bq() {
/* 1641 */     this.aU++;
/*      */   }
/*      */   
/*      */   public void f(boolean flag) {
/* 1645 */     this.bc = flag;
/*      */   }
/*      */   
/*      */   public void receive(Entity entity, int i) {
/* 1649 */     if (!entity.dead && !this.world.isStatic) {
/* 1650 */       EntityTracker entitytracker = ((WorldServer)this.world).getTracker();
/*      */       
/* 1652 */       if (entity instanceof EntityItem) {
/* 1653 */         entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
/*      */       }
/*      */       
/* 1656 */       if (entity instanceof EntityArrow) {
/* 1657 */         entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
/*      */       }
/*      */       
/* 1660 */       if (entity instanceof EntityExperienceOrb) {
/* 1661 */         entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean hasLineOfSight(Entity entity) {
/* 1667 */     return (this.world.a(Vec3D.a(this.locX, this.locY + getHeadHeight(), this.locZ), Vec3D.a(entity.locX, entity.locY + entity.getHeadHeight(), entity.locZ)) == null);
/*      */   }
/*      */   
/*      */   public Vec3D ag() {
/* 1671 */     return j(1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3D j(float f) {
/* 1680 */     if (f == 1.0F) {
/* 1681 */       float f7 = MathHelper.cos(-this.yaw * 0.017453292F - 3.1415927F);
/* 1682 */       float f8 = MathHelper.sin(-this.yaw * 0.017453292F - 3.1415927F);
/* 1683 */       float f9 = -MathHelper.cos(-this.pitch * 0.017453292F);
/* 1684 */       float f10 = MathHelper.sin(-this.pitch * 0.017453292F);
/* 1685 */       return Vec3D.a((f8 * f9), f10, (f7 * f9));
/*      */     } 
/* 1687 */     float f1 = this.lastPitch + (this.pitch - this.lastPitch) * f;
/* 1688 */     float f2 = this.lastYaw + (this.yaw - this.lastYaw) * f;
/* 1689 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 1690 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 1691 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 1692 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*      */     
/* 1694 */     return Vec3D.a((f4 * f5), f6, (f3 * f5));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean br() {
/* 1699 */     return !this.world.isStatic;
/*      */   }
/*      */   
/*      */   public boolean R() {
/* 1703 */     return !this.dead;
/*      */   }
/*      */   
/*      */   public boolean S() {
/* 1707 */     return !this.dead;
/*      */   }
/*      */   
/*      */   public float getHeadHeight() {
/* 1711 */     return this.length * 0.85F;
/*      */   }
/*      */   
/*      */   protected void Q() {
/* 1715 */     this.velocityChanged = (this.random.nextDouble() >= getAttributeInstance(GenericAttributes.c).getValue());
/*      */   }
/*      */   
/*      */   public float getHeadRotation() {
/* 1719 */     return this.aO;
/*      */   }
/*      */   
/*      */   public float getAbsorptionHearts() {
/* 1723 */     return this.br;
/*      */   }
/*      */   
/*      */   public void setAbsorptionHearts(float f) {
/* 1727 */     if (f < 0.0F) {
/* 1728 */       f = 0.0F;
/*      */     }
/*      */     
/* 1731 */     this.br = f;
/*      */   }
/*      */   
/*      */   public ScoreboardTeamBase getScoreboardTeam() {
/* 1735 */     return null;
/*      */   }
/*      */   
/*      */   public boolean c(EntityLiving entityliving) {
/* 1739 */     return a(entityliving.getScoreboardTeam());
/*      */   }
/*      */   
/*      */   public boolean a(ScoreboardTeamBase scoreboardteambase) {
/* 1743 */     return (getScoreboardTeam() != null) ? getScoreboardTeam().isAlly(scoreboardteambase) : false;
/*      */   }
/*      */   
/*      */   public void bu() {}
/*      */   
/*      */   public void bv() {}
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityLiving.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */