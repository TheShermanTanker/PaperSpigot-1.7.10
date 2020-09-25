/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class EntityGhast
/*     */   extends EntityFlying implements IMonster {
/*     */   public int h;
/*     */   public double i;
/*     */   public double bm;
/*     */   public double bn;
/*     */   private Entity target;
/*     */   private int br;
/*     */   public int bo;
/*     */   public int bp;
/*  18 */   private int explosionPower = 1;
/*     */   
/*     */   public EntityGhast(World world) {
/*  21 */     super(world);
/*  22 */     a(4.0F, 4.0F);
/*  23 */     this.fireProof = true;
/*  24 */     this.b = 5;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  28 */     if (isInvulnerable())
/*  29 */       return false; 
/*  30 */     if ("fireball".equals(damagesource.p()) && damagesource.getEntity() instanceof EntityHuman) {
/*  31 */       super.damageEntity(damagesource, 1000.0F);
/*  32 */       ((EntityHuman)damagesource.getEntity()).a(AchievementList.z);
/*  33 */       return true;
/*     */     } 
/*  35 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  40 */     super.c();
/*  41 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  45 */     super.aD();
/*  46 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*     */   }
/*     */   
/*     */   protected void bq() {
/*  50 */     if (!this.world.isStatic && this.world.difficulty == EnumDifficulty.PEACEFUL) {
/*  51 */       die();
/*     */     }
/*     */     
/*  54 */     w();
/*  55 */     this.bo = this.bp;
/*  56 */     double d0 = this.i - this.locX;
/*  57 */     double d1 = this.bm - this.locY;
/*  58 */     double d2 = this.bn - this.locZ;
/*  59 */     double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */     
/*  61 */     if (d3 < 1.0D || d3 > 3600.0D) {
/*  62 */       this.i = this.locX + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*  63 */       this.bm = this.locY + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*  64 */       this.bn = this.locZ + ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*     */     } 
/*     */     
/*  67 */     if (this.h-- <= 0) {
/*  68 */       this.h += this.random.nextInt(5) + 2;
/*  69 */       d3 = MathHelper.sqrt(d3);
/*  70 */       if (a(this.i, this.bm, this.bn, d3)) {
/*  71 */         this.motX += d0 / d3 * 0.1D;
/*  72 */         this.motY += d1 / d3 * 0.1D;
/*  73 */         this.motZ += d2 / d3 * 0.1D;
/*     */       } else {
/*  75 */         this.i = this.locX;
/*  76 */         this.bm = this.locY;
/*  77 */         this.bn = this.locZ;
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     if (this.target != null && this.target.dead) {
/*     */       
/*  83 */       EntityTargetEvent event = new EntityTargetEvent((Entity)getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
/*  84 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/*  86 */       if (!event.isCancelled()) {
/*  87 */         if (event.getTarget() == null) {
/*  88 */           this.target = null;
/*     */         } else {
/*  90 */           this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  96 */     if (this.target == null || this.br-- <= 0) {
/*     */       
/*  98 */       Entity target = this.world.findNearbyVulnerablePlayer(this, 100.0D);
/*  99 */       if (target != null) {
/* 100 */         EntityTargetEvent event = new EntityTargetEvent((Entity)getBukkitEntity(), (Entity)target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
/* 101 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/* 103 */         if (!event.isCancelled()) {
/* 104 */           if (event.getTarget() == null) {
/* 105 */             this.target = null;
/*     */           } else {
/* 107 */             this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 113 */       if (this.target != null) {
/* 114 */         this.br = 20;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     double d4 = 64.0D;
/*     */     
/* 120 */     if (this.target != null && this.target.f(this) < d4 * d4) {
/* 121 */       double d5 = this.target.locX - this.locX;
/* 122 */       double d6 = this.target.boundingBox.b + (this.target.length / 2.0F) - this.locY + (this.length / 2.0F);
/* 123 */       double d7 = this.target.locZ - this.locZ;
/*     */       
/* 125 */       this.aM = this.yaw = -((float)Math.atan2(d5, d7)) * 180.0F / 3.1415927F;
/* 126 */       if (hasLineOfSight(this.target)) {
/* 127 */         if (this.bp == 10) {
/* 128 */           this.world.a((EntityHuman)null, 1007, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
/*     */         }
/*     */         
/* 131 */         this.bp++;
/* 132 */         if (this.bp == 20) {
/* 133 */           this.world.a((EntityHuman)null, 1008, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
/* 134 */           EntityLargeFireball entitylargefireball = new EntityLargeFireball(this.world, this, d5, d6, d7);
/*     */ 
/*     */           
/* 137 */           entitylargefireball.bukkitYield = (entitylargefireball.yield = this.explosionPower);
/* 138 */           double d8 = 4.0D;
/* 139 */           Vec3D vec3d = j(1.0F);
/*     */           
/* 141 */           entitylargefireball.locX = this.locX + vec3d.a * d8;
/* 142 */           entitylargefireball.locY = this.locY + (this.length / 2.0F) + 0.5D;
/* 143 */           entitylargefireball.locZ = this.locZ + vec3d.c * d8;
/* 144 */           this.world.addEntity(entitylargefireball);
/* 145 */           this.bp = -40;
/*     */         } 
/* 147 */       } else if (this.bp > 0) {
/* 148 */         this.bp--;
/*     */       } 
/*     */     } else {
/* 151 */       this.aM = this.yaw = -((float)Math.atan2(this.motX, this.motZ)) * 180.0F / 3.1415927F;
/* 152 */       if (this.bp > 0) {
/* 153 */         this.bp--;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     if (!this.world.isStatic) {
/* 158 */       byte b0 = this.datawatcher.getByte(16);
/* 159 */       byte b1 = (byte)((this.bp > 10) ? 1 : 0);
/*     */       
/* 161 */       if (b0 != b1) {
/* 162 */         this.datawatcher.watch(16, Byte.valueOf(b1));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean a(double d0, double d1, double d2, double d3) {
/* 168 */     double d4 = (this.i - this.locX) / d3;
/* 169 */     double d5 = (this.bm - this.locY) / d3;
/* 170 */     double d6 = (this.bn - this.locZ) / d3;
/* 171 */     AxisAlignedBB axisalignedbb = this.boundingBox.clone();
/*     */     
/* 173 */     for (int i = 1; i < d3; i++) {
/* 174 */       axisalignedbb.d(d4, d5, d6);
/* 175 */       if (!this.world.getCubes(this, axisalignedbb).isEmpty()) {
/* 176 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 180 */     return true;
/*     */   }
/*     */   
/*     */   protected String t() {
/* 184 */     return "mob.ghast.moan";
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 188 */     return "mob.ghast.scream";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 192 */     return "mob.ghast.death";
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 196 */     return Items.SULPHUR;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 200 */     int j = this.random.nextInt(2) + this.random.nextInt(1 + i);
/*     */     
/*     */     int k;
/*     */     
/* 204 */     for (k = 0; k < j; k++) {
/* 205 */       a(Items.GHAST_TEAR, 1);
/*     */     }
/*     */     
/* 208 */     j = this.random.nextInt(3) + this.random.nextInt(1 + i);
/*     */     
/* 210 */     for (k = 0; k < j; k++) {
/* 211 */       a(Items.SULPHUR, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected float bf() {
/* 216 */     return 10.0F;
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/* 220 */     return (this.random.nextInt(20) == 0 && super.canSpawn() && this.world.difficulty != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */   
/*     */   public int bB() {
/* 224 */     return 1;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 228 */     super.b(nbttagcompound);
/* 229 */     nbttagcompound.setInt("ExplosionPower", this.explosionPower);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 233 */     super.a(nbttagcompound);
/* 234 */     if (nbttagcompound.hasKeyOfType("ExplosionPower", 99))
/* 235 */       this.explosionPower = nbttagcompound.getInt("ExplosionPower"); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityGhast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */