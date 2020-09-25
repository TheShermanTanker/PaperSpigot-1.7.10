/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.TrigMath;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.entity.EntityUnleashEvent;
/*     */ 
/*     */ public abstract class EntityCreature
/*     */   extends EntityInsentient {
/*  13 */   public static final UUID h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
/*  14 */   public static final AttributeModifier i = (new AttributeModifier(h, "Fleeing speed bonus", 2.0D, 2)).a(false);
/*     */   public PathEntity pathEntity;
/*     */   public Entity target;
/*     */   protected boolean bn;
/*     */   protected int bo;
/*  19 */   private ChunkCoordinates bq = new ChunkCoordinates(0, 0, 0);
/*  20 */   private float br = -1.0F;
/*  21 */   private PathfinderGoal bs = new PathfinderGoalMoveTowardsRestriction(this, 1.0D);
/*     */   private boolean bt;
/*     */   
/*     */   public EntityCreature(World world) {
/*  25 */     super(world);
/*     */   }
/*     */   
/*     */   protected boolean bP() {
/*  29 */     return false;
/*     */   }
/*     */   
/*     */   protected void bq() {
/*  33 */     this.world.methodProfiler.a("ai");
/*  34 */     if (this.bo > 0 && --this.bo == 0) {
/*  35 */       AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
/*     */       
/*  37 */       attributeinstance.b(EntityCreature.i);
/*     */     } 
/*     */     
/*  40 */     this.bn = bP();
/*  41 */     float f11 = 16.0F;
/*     */     
/*  43 */     if (this.target == null) {
/*     */       
/*  45 */       Entity target = findTarget();
/*  46 */       if (target != null) {
/*  47 */         EntityTargetEvent event = new EntityTargetEvent((Entity)getBukkitEntity(), (Entity)target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
/*  48 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/*  50 */         if (!event.isCancelled()) {
/*  51 */           if (event.getTarget() == null) {
/*  52 */             this.target = null;
/*     */           } else {
/*  54 */             this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  60 */       if (this.target != null) {
/*  61 */         this.pathEntity = this.world.findPath(this, this.target, f11, true, false, false, true);
/*     */       }
/*  63 */     } else if (this.target.isAlive()) {
/*  64 */       float f1 = this.target.e(this);
/*     */       
/*  66 */       if (hasLineOfSight(this.target)) {
/*  67 */         a(this.target, f1);
/*     */       }
/*     */     } else {
/*     */       
/*  71 */       EntityTargetEvent event = new EntityTargetEvent((Entity)getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
/*  72 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/*  74 */       if (!event.isCancelled()) {
/*  75 */         if (event.getTarget() == null) {
/*  76 */           this.target = null;
/*     */         } else {
/*  78 */           this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  84 */     if (this.target instanceof EntityPlayer && ((EntityPlayer)this.target).playerInteractManager.isCreative()) {
/*  85 */       this.target = null;
/*     */     }
/*     */     
/*  88 */     this.world.methodProfiler.b();
/*  89 */     if (!this.bn && this.target != null && (this.pathEntity == null || this.random.nextInt(20) == 0)) {
/*  90 */       this.pathEntity = this.world.findPath(this, this.target, f11, true, false, false, true);
/*  91 */     } else if (!this.bn && ((this.pathEntity == null && this.random.nextInt(180) == 0) || this.random.nextInt(120) == 0 || this.bo > 0) && this.aU < 100) {
/*  92 */       bQ();
/*     */     } 
/*     */     
/*  95 */     int i = MathHelper.floor(this.boundingBox.b + 0.5D);
/*  96 */     boolean flag = M();
/*  97 */     boolean flag1 = P();
/*     */     
/*  99 */     this.pitch = 0.0F;
/* 100 */     if (this.pathEntity != null && this.random.nextInt(100) != 0) {
/* 101 */       this.world.methodProfiler.a("followpath");
/* 102 */       Vec3D vec3d = this.pathEntity.a(this);
/* 103 */       double d0 = (this.width * 2.0F);
/*     */       
/* 105 */       while (vec3d != null && vec3d.d(this.locX, vec3d.b, this.locZ) < d0 * d0) {
/* 106 */         this.pathEntity.a();
/* 107 */         if (this.pathEntity.b()) {
/* 108 */           vec3d = null;
/* 109 */           this.pathEntity = null; continue;
/*     */         } 
/* 111 */         vec3d = this.pathEntity.a(this);
/*     */       } 
/*     */ 
/*     */       
/* 115 */       this.bc = false;
/* 116 */       if (vec3d != null) {
/* 117 */         double d1 = vec3d.a - this.locX;
/* 118 */         double d2 = vec3d.c - this.locZ;
/* 119 */         double d3 = vec3d.b - i;
/*     */         
/* 121 */         float f2 = (float)(TrigMath.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 122 */         float f3 = MathHelper.g(f2 - this.yaw);
/*     */         
/* 124 */         this.be = (float)getAttributeInstance(GenericAttributes.d).getValue();
/* 125 */         if (f3 > 30.0F) {
/* 126 */           f3 = 30.0F;
/*     */         }
/*     */         
/* 129 */         if (f3 < -30.0F) {
/* 130 */           f3 = -30.0F;
/*     */         }
/*     */         
/* 133 */         this.yaw += f3;
/* 134 */         if (this.bn && this.target != null) {
/* 135 */           double d4 = this.target.locX - this.locX;
/* 136 */           double d5 = this.target.locZ - this.locZ;
/* 137 */           float f4 = this.yaw;
/*     */           
/* 139 */           this.yaw = (float)(Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 140 */           f3 = (f4 - this.yaw + 90.0F) * 3.1415927F / 180.0F;
/* 141 */           this.bd = -MathHelper.sin(f3) * this.be * 1.0F;
/* 142 */           this.be = MathHelper.cos(f3) * this.be * 1.0F;
/*     */         } 
/*     */         
/* 145 */         if (d3 > 0.0D) {
/* 146 */           this.bc = true;
/*     */         }
/*     */       } 
/*     */       
/* 150 */       if (this.target != null) {
/* 151 */         a(this.target, 30.0F, 30.0F);
/*     */       }
/*     */       
/* 154 */       if (this.positionChanged && !bS()) {
/* 155 */         this.bc = true;
/*     */       }
/*     */       
/* 158 */       if (this.random.nextFloat() < 0.8F && (flag || flag1)) {
/* 159 */         this.bc = true;
/*     */       }
/*     */       
/* 162 */       this.world.methodProfiler.b();
/*     */     } else {
/* 164 */       super.bq();
/* 165 */       this.pathEntity = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void bQ() {
/* 170 */     this.world.methodProfiler.a("stroll");
/* 171 */     boolean flag = false;
/* 172 */     int i = -1;
/* 173 */     int j = -1;
/* 174 */     int k = -1;
/* 175 */     float f = -99999.0F;
/*     */     
/* 177 */     for (int l = 0; l < 10; l++) {
/* 178 */       int i1 = MathHelper.floor(this.locX + this.random.nextInt(13) - 6.0D);
/* 179 */       int j1 = MathHelper.floor(this.locY + this.random.nextInt(7) - 3.0D);
/* 180 */       int k1 = MathHelper.floor(this.locZ + this.random.nextInt(13) - 6.0D);
/* 181 */       float f1 = a(i1, j1, k1);
/*     */       
/* 183 */       if (f1 > f) {
/* 184 */         f = f1;
/* 185 */         i = i1;
/* 186 */         j = j1;
/* 187 */         k = k1;
/* 188 */         flag = true;
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     if (flag) {
/* 193 */       this.pathEntity = this.world.a(this, i, j, k, 10.0F, true, false, false, true);
/*     */     }
/*     */     
/* 196 */     this.world.methodProfiler.b();
/*     */   }
/*     */   
/*     */   protected void a(Entity entity, float f) {}
/*     */   
/*     */   public float a(int i, int j, int k) {
/* 202 */     return 0.0F;
/*     */   }
/*     */   
/*     */   protected Entity findTarget() {
/* 206 */     return null;
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/* 210 */     int i = MathHelper.floor(this.locX);
/* 211 */     int j = MathHelper.floor(this.boundingBox.b);
/* 212 */     int k = MathHelper.floor(this.locZ);
/*     */     
/* 214 */     return (super.canSpawn() && a(i, j, k) >= 0.0F);
/*     */   }
/*     */   
/*     */   public boolean bS() {
/* 218 */     return (this.pathEntity != null);
/*     */   }
/*     */   
/*     */   public void setPathEntity(PathEntity pathentity) {
/* 222 */     this.pathEntity = pathentity;
/*     */   }
/*     */   
/*     */   public Entity bT() {
/* 226 */     return this.target;
/*     */   }
/*     */   
/*     */   public void setTarget(Entity entity) {
/* 230 */     this.target = entity;
/*     */   }
/*     */   
/*     */   public boolean bU() {
/* 234 */     return b(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
/*     */   }
/*     */   
/*     */   public boolean b(int i, int j, int k) {
/* 238 */     return (this.br == -1.0F) ? true : ((this.bq.e(i, j, k) < this.br * this.br));
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k, int l) {
/* 242 */     this.bq.b(i, j, k);
/* 243 */     this.br = l;
/*     */   }
/*     */   
/*     */   public ChunkCoordinates bV() {
/* 247 */     return this.bq;
/*     */   }
/*     */   
/*     */   public float bW() {
/* 251 */     return this.br;
/*     */   }
/*     */   
/*     */   public void bX() {
/* 255 */     this.br = -1.0F;
/*     */   }
/*     */   
/*     */   public boolean bY() {
/* 259 */     return (this.br != -1.0F);
/*     */   }
/*     */   
/*     */   protected void bL() {
/* 263 */     super.bL();
/* 264 */     if (bN() && getLeashHolder() != null && (getLeashHolder()).world == this.world) {
/* 265 */       Entity entity = getLeashHolder();
/*     */       
/* 267 */       a((int)entity.locX, (int)entity.locY, (int)entity.locZ, 5);
/* 268 */       float f = e(entity);
/*     */       
/* 270 */       if (this instanceof EntityTameableAnimal && ((EntityTameableAnimal)this).isSitting()) {
/* 271 */         if (f > 10.0F) {
/* 272 */           this.world.getServer().getPluginManager().callEvent((Event)new EntityUnleashEvent((Entity)getBukkitEntity(), EntityUnleashEvent.UnleashReason.DISTANCE));
/* 273 */           unleash(true, true);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 279 */       if (!this.bt) {
/* 280 */         this.goalSelector.a(2, this.bs);
/* 281 */         getNavigation().a(false);
/* 282 */         this.bt = true;
/*     */       } 
/*     */       
/* 285 */       o(f);
/* 286 */       if (f > 4.0F) {
/* 287 */         getNavigation().a(entity, 1.0D);
/*     */       }
/*     */       
/* 290 */       if (f > 6.0F) {
/* 291 */         double d0 = (entity.locX - this.locX) / f;
/* 292 */         double d1 = (entity.locY - this.locY) / f;
/* 293 */         double d2 = (entity.locZ - this.locZ) / f;
/*     */         
/* 295 */         this.motX += d0 * Math.abs(d0) * 0.4D;
/* 296 */         this.motY += d1 * Math.abs(d1) * 0.4D;
/* 297 */         this.motZ += d2 * Math.abs(d2) * 0.4D;
/*     */       } 
/*     */       
/* 300 */       if (f > 10.0F) {
/* 301 */         this.world.getServer().getPluginManager().callEvent((Event)new EntityUnleashEvent((Entity)getBukkitEntity(), EntityUnleashEvent.UnleashReason.DISTANCE));
/* 302 */         unleash(true, true);
/*     */       } 
/* 304 */     } else if (!bN() && this.bt) {
/* 305 */       this.bt = false;
/* 306 */       this.goalSelector.a(this.bs);
/* 307 */       getNavigation().a(true);
/* 308 */       bX();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void o(float f) {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityCreature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */