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
/*     */ public class DamageSource
/*     */ {
/*  13 */   public static DamageSource FIRE = (new DamageSource("inFire")).setExplosion();
/*  14 */   public static DamageSource BURN = (new DamageSource("onFire")).setIgnoreArmor().setExplosion();
/*  15 */   public static DamageSource LAVA = (new DamageSource("lava")).setExplosion();
/*  16 */   public static DamageSource STUCK = (new DamageSource("inWall")).setIgnoreArmor();
/*  17 */   public static DamageSource DROWN = (new DamageSource("drown")).setIgnoreArmor();
/*  18 */   public static DamageSource STARVE = (new DamageSource("starve")).setIgnoreArmor().m();
/*  19 */   public static DamageSource CACTUS = new DamageSource("cactus");
/*  20 */   public static DamageSource FALL = (new DamageSource("fall")).setIgnoreArmor();
/*  21 */   public static DamageSource OUT_OF_WORLD = (new DamageSource("outOfWorld")).setIgnoreArmor().l();
/*  22 */   public static DamageSource GENERIC = (new DamageSource("generic")).setIgnoreArmor();
/*  23 */   public static DamageSource MAGIC = (new DamageSource("magic")).setIgnoreArmor().setMagic();
/*  24 */   public static DamageSource WITHER = (new DamageSource("wither")).setIgnoreArmor();
/*  25 */   public static DamageSource ANVIL = new DamageSource("anvil");
/*  26 */   public static DamageSource FALLING_BLOCK = new DamageSource("fallingBlock"); private boolean p;
/*     */   
/*     */   public static DamageSource mobAttack(EntityLiving paramEntityLiving) {
/*  29 */     return new EntityDamageSource("mob", paramEntityLiving);
/*     */   }
/*     */   private boolean q; private boolean r;
/*     */   public static DamageSource playerAttack(EntityHuman paramEntityHuman) {
/*  33 */     return new EntityDamageSource("player", paramEntityHuman);
/*     */   }
/*     */   
/*     */   public static DamageSource arrow(EntityArrow paramEntityArrow, Entity paramEntity) {
/*  37 */     return (new EntityDamageSourceIndirect("arrow", paramEntityArrow, paramEntity)).b();
/*     */   }
/*     */   
/*     */   public static DamageSource fireball(EntityFireball paramEntityFireball, Entity paramEntity) {
/*  41 */     if (paramEntity == null) {
/*  42 */       return (new EntityDamageSourceIndirect("onFire", paramEntityFireball, paramEntityFireball)).setExplosion().b();
/*     */     }
/*  44 */     return (new EntityDamageSourceIndirect("fireball", paramEntityFireball, paramEntity)).setExplosion().b();
/*     */   }
/*     */   
/*     */   public static DamageSource projectile(Entity paramEntity1, Entity paramEntity2) {
/*  48 */     return (new EntityDamageSourceIndirect("thrown", paramEntity1, paramEntity2)).b();
/*     */   }
/*     */   
/*     */   public static DamageSource b(Entity paramEntity1, Entity paramEntity2) {
/*  52 */     return (new EntityDamageSourceIndirect("indirectMagic", paramEntity1, paramEntity2)).setIgnoreArmor().setMagic();
/*     */   }
/*     */   
/*     */   public static DamageSource a(Entity paramEntity) {
/*  56 */     return (new EntityDamageSource("thorns", paramEntity)).setMagic();
/*     */   }
/*     */   
/*     */   public static DamageSource explosion(Explosion paramExplosion) {
/*  60 */     if (paramExplosion != null && paramExplosion.c() != null) {
/*  61 */       return (new EntityDamageSource("explosion.player", paramExplosion.c())).q().d();
/*     */     }
/*  63 */     return (new DamageSource("explosion")).q().d();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private float s = 0.3F; private boolean t;
/*     */   private boolean u;
/*     */   private boolean v;
/*     */   private boolean w;
/*     */   private boolean x;
/*     */   public String translationIndex;
/*     */   
/*     */   public boolean a() {
/*  79 */     return this.u;
/*     */   }
/*     */   
/*     */   public DamageSource b() {
/*  83 */     this.u = true;
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isExplosion() {
/*  88 */     return this.x;
/*     */   }
/*     */   
/*     */   public DamageSource d() {
/*  92 */     this.x = true;
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public boolean ignoresArmor() {
/*  97 */     return this.p;
/*     */   }
/*     */   
/*     */   public float getExhaustionCost() {
/* 101 */     return this.s;
/*     */   }
/*     */   
/*     */   public boolean ignoresInvulnerability() {
/* 105 */     return this.q;
/*     */   }
/*     */   
/*     */   public boolean isStarvation() {
/* 109 */     return this.r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DamageSource(String paramString) {
/* 115 */     this.translationIndex = paramString;
/*     */   }
/*     */   
/*     */   public Entity i() {
/* 119 */     return getEntity();
/*     */   }
/*     */   
/*     */   public Entity getEntity() {
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   protected DamageSource setIgnoreArmor() {
/* 127 */     this.p = true;
/*     */     
/* 129 */     this.s = 0.0F;
/* 130 */     return this;
/*     */   }
/*     */   
/*     */   protected DamageSource l() {
/* 134 */     this.q = true;
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   protected DamageSource m() {
/* 139 */     this.r = true;
/*     */     
/* 141 */     this.s = 0.0F;
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   protected DamageSource setExplosion() {
/* 146 */     this.t = true;
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getLocalizedDeathMessage(EntityLiving paramEntityLiving) {
/* 151 */     EntityLiving entityLiving = paramEntityLiving.aX();
/* 152 */     String str1 = "death.attack." + this.translationIndex;
/* 153 */     String str2 = str1 + ".player";
/*     */     
/* 155 */     if (entityLiving != null && LocaleI18n.c(str2)) {
/* 156 */       return new ChatMessage(str2, new Object[] { paramEntityLiving.getScoreboardDisplayName(), entityLiving.getScoreboardDisplayName() });
/*     */     }
/* 158 */     return new ChatMessage(str1, new Object[] { paramEntityLiving.getScoreboardDisplayName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean o() {
/* 163 */     return this.t;
/*     */   }
/*     */   
/*     */   public String p() {
/* 167 */     return this.translationIndex;
/*     */   }
/*     */   
/*     */   public DamageSource q() {
/* 171 */     this.v = true;
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public boolean r() {
/* 176 */     return this.v;
/*     */   }
/*     */   
/*     */   public boolean isMagic() {
/* 180 */     return this.w;
/*     */   }
/*     */   
/*     */   public DamageSource setMagic() {
/* 184 */     this.w = true;
/* 185 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DamageSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */