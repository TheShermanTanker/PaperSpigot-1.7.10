/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.PortalType;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.BlockStateListPopulator;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityCreatePortalEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class EntityEnderDragon extends EntityInsentient implements IComplex, IMonster {
/*     */   public double h;
/*  22 */   public double[][] bn = new double[64][3]; public double i; public double bm;
/*  23 */   public int bo = -1;
/*     */   public EntityComplexPart[] children;
/*     */   public EntityComplexPart bq;
/*     */   public EntityComplexPart br;
/*     */   public EntityComplexPart bs;
/*     */   public EntityComplexPart bt;
/*     */   public EntityComplexPart bu;
/*     */   public EntityComplexPart bv;
/*     */   public EntityComplexPart bw;
/*     */   public float bx;
/*     */   public float by;
/*     */   public boolean bz;
/*     */   public boolean bA;
/*     */   private Entity bD;
/*     */   public int bB;
/*     */   public EntityEnderCrystal bC;
/*  39 */   private Explosion explosionSource = new Explosion(null, this, Double.NaN, Double.NaN, Double.NaN, Float.NaN);
/*     */   
/*     */   public EntityEnderDragon(World world) {
/*  42 */     super(world);
/*  43 */     this.children = new EntityComplexPart[] { this.bq = new EntityComplexPart(this, "head", 6.0F, 6.0F), this.br = new EntityComplexPart(this, "body", 8.0F, 8.0F), this.bs = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bt = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bu = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bv = new EntityComplexPart(this, "wing", 4.0F, 4.0F), this.bw = new EntityComplexPart(this, "wing", 4.0F, 4.0F) };
/*  44 */     setHealth(getMaxHealth());
/*  45 */     a(16.0F, 8.0F);
/*  46 */     this.X = true;
/*  47 */     this.fireProof = true;
/*  48 */     this.i = 100.0D;
/*  49 */     this.ak = true;
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  53 */     super.aD();
/*  54 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(200.0D);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  58 */     super.c();
/*     */   }
/*     */   
/*     */   public double[] b(int i, float f) {
/*  62 */     if (getHealth() <= 0.0F) {
/*  63 */       f = 0.0F;
/*     */     }
/*     */     
/*  66 */     f = 1.0F - f;
/*  67 */     int j = this.bo - i * 1 & 0x3F;
/*  68 */     int k = this.bo - i * 1 - 1 & 0x3F;
/*  69 */     double[] adouble = new double[3];
/*  70 */     double d0 = this.bn[j][0];
/*  71 */     double d1 = MathHelper.g(this.bn[k][0] - d0);
/*     */     
/*  73 */     adouble[0] = d0 + d1 * f;
/*  74 */     d0 = this.bn[j][1];
/*  75 */     d1 = this.bn[k][1] - d0;
/*  76 */     adouble[1] = d0 + d1 * f;
/*  77 */     adouble[2] = this.bn[j][2] + (this.bn[k][2] - this.bn[j][2]) * f;
/*  78 */     return adouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  85 */     if (this.world.isStatic) {
/*  86 */       float f = MathHelper.cos(this.by * 3.1415927F * 2.0F);
/*  87 */       float f1 = MathHelper.cos(this.bx * 3.1415927F * 2.0F);
/*  88 */       if (f1 <= -0.3F && f >= -0.3F) {
/*  89 */         this.world.a(this.locX, this.locY, this.locZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
/*     */       }
/*     */     } 
/*     */     
/*  93 */     this.bx = this.by;
/*     */ 
/*     */     
/*  96 */     if (getHealth() <= 0.0F) {
/*  97 */       float f = (this.random.nextFloat() - 0.5F) * 8.0F;
/*  98 */       float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
/*  99 */       float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 100 */       this.world.addParticle("largeexplode", this.locX + f, this.locY + 2.0D + f1, this.locZ + f2, 0.0D, 0.0D, 0.0D);
/*     */     } else {
/* 102 */       bP();
/* 103 */       float f = 0.2F / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0F + 1.0F);
/* 104 */       f *= (float)Math.pow(2.0D, this.motY);
/* 105 */       if (this.bA) {
/* 106 */         this.by += f * 0.5F;
/*     */       } else {
/* 108 */         this.by += f;
/*     */       } 
/*     */       
/* 111 */       this.yaw = MathHelper.g(this.yaw);
/* 112 */       if (this.bo < 0) {
/* 113 */         for (int d05 = 0; d05 < this.bn.length; d05++) {
/* 114 */           this.bn[d05][0] = this.yaw;
/* 115 */           this.bn[d05][1] = this.locY;
/*     */         } 
/*     */       }
/*     */       
/* 119 */       if (++this.bo == this.bn.length) {
/* 120 */         this.bo = 0;
/*     */       }
/*     */       
/* 123 */       this.bn[this.bo][0] = this.yaw;
/* 124 */       this.bn[this.bo][1] = this.locY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       if (this.world.isStatic) {
/* 132 */         if (this.bg > 0) {
/* 133 */           double d0 = this.locX + (this.bh - this.locX) / this.bg;
/* 134 */           double d1 = this.locY + (this.bi - this.locY) / this.bg;
/* 135 */           double d2 = this.locZ + (this.bj - this.locZ) / this.bg;
/* 136 */           double d3 = MathHelper.g(this.bk - this.yaw);
/* 137 */           this.yaw = (float)(this.yaw + d3 / this.bg);
/* 138 */           this.pitch = (float)(this.pitch + (this.bl - this.pitch) / this.bg);
/* 139 */           this.bg--;
/* 140 */           setPosition(d0, d1, d2);
/* 141 */           b(this.yaw, this.pitch);
/*     */         } 
/*     */       } else {
/* 144 */         double d0 = this.h - this.locX;
/* 145 */         double d1 = this.i - this.locY;
/* 146 */         double d2 = this.bm - this.locZ;
/* 147 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 148 */         if (this.bD != null) {
/* 149 */           this.h = this.bD.locX;
/* 150 */           this.bm = this.bD.locZ;
/* 151 */           double d4 = this.h - this.locX;
/* 152 */           double d5 = this.bm - this.locZ;
/* 153 */           double d6 = Math.sqrt(d4 * d4 + d5 * d5);
/* 154 */           double d7 = 0.4000000059604645D + d6 / 80.0D - 1.0D;
/*     */           
/* 156 */           if (d7 > 10.0D) {
/* 157 */             d7 = 10.0D;
/*     */           }
/*     */           
/* 160 */           this.i = this.bD.boundingBox.b + d7;
/*     */         } else {
/* 162 */           this.h += this.random.nextGaussian() * 2.0D;
/* 163 */           this.bm += this.random.nextGaussian() * 2.0D;
/*     */         } 
/*     */         
/* 166 */         if (this.bz || d3 < 100.0D || d3 > 22500.0D || this.positionChanged || this.F) {
/* 167 */           bQ();
/*     */         }
/*     */         
/* 170 */         d1 /= MathHelper.sqrt(d0 * d0 + d2 * d2);
/* 171 */         float f14 = 0.6F;
/* 172 */         if (d1 < -f14) {
/* 173 */           d1 = -f14;
/*     */         }
/*     */         
/* 176 */         if (d1 > f14) {
/* 177 */           d1 = f14;
/*     */         }
/*     */         
/* 180 */         this.motY += d1 * 0.10000000149011612D;
/* 181 */         this.yaw = MathHelper.g(this.yaw);
/* 182 */         double d8 = 180.0D - Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D;
/* 183 */         double d9 = MathHelper.g(d8 - this.yaw);
/*     */         
/* 185 */         if (d9 > 50.0D) {
/* 186 */           d9 = 50.0D;
/*     */         }
/*     */         
/* 189 */         if (d9 < -50.0D) {
/* 190 */           d9 = -50.0D;
/*     */         }
/*     */         
/* 193 */         Vec3D vec3d = Vec3D.a(this.h - this.locX, this.i - this.locY, this.bm - this.locZ).a();
/* 194 */         Vec3D vec3d1 = Vec3D.a(MathHelper.sin(this.yaw * 3.1415927F / 180.0F), this.motY, -MathHelper.cos(this.yaw * 3.1415927F / 180.0F)).a();
/* 195 */         float f4 = (float)(vec3d1.b(vec3d) + 0.5D) / 1.5F;
/*     */         
/* 197 */         if (f4 < 0.0F) {
/* 198 */           f4 = 0.0F;
/*     */         }
/*     */         
/* 201 */         this.bf *= 0.8F;
/* 202 */         float f5 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0F + 1.0F;
/* 203 */         double d10 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0D + 1.0D;
/*     */         
/* 205 */         if (d10 > 40.0D) {
/* 206 */           d10 = 40.0D;
/*     */         }
/*     */         
/* 209 */         this.bf = (float)(this.bf + d9 * 0.699999988079071D / d10 / f5);
/* 210 */         this.yaw += this.bf * 0.1F;
/* 211 */         float f6 = (float)(2.0D / (d10 + 1.0D));
/* 212 */         float f7 = 0.06F;
/*     */         
/* 214 */         a(0.0F, -1.0F, f7 * (f4 * f6 + 1.0F - f6));
/* 215 */         if (this.bA) {
/* 216 */           move(this.motX * 0.800000011920929D, this.motY * 0.800000011920929D, this.motZ * 0.800000011920929D);
/*     */         } else {
/* 218 */           move(this.motX, this.motY, this.motZ);
/*     */         } 
/*     */         
/* 221 */         Vec3D vec3d2 = Vec3D.a(this.motX, this.motY, this.motZ).a();
/* 222 */         float f8 = (float)(vec3d2.b(vec3d1) + 1.0D) / 2.0F;
/*     */         
/* 224 */         f8 = 0.8F + 0.15F * f8;
/* 225 */         this.motX *= f8;
/* 226 */         this.motZ *= f8;
/* 227 */         this.motY *= 0.9100000262260437D;
/*     */       } 
/*     */       
/* 230 */       this.aM = this.yaw;
/* 231 */       this.bq.width = this.bq.length = 3.0F;
/* 232 */       this.bs.width = this.bs.length = 2.0F;
/* 233 */       this.bt.width = this.bt.length = 2.0F;
/* 234 */       this.bu.width = this.bu.length = 2.0F;
/* 235 */       this.br.length = 3.0F;
/* 236 */       this.br.width = 5.0F;
/* 237 */       this.bv.length = 2.0F;
/* 238 */       this.bv.width = 4.0F;
/* 239 */       this.bw.length = 3.0F;
/* 240 */       this.bw.width = 4.0F;
/* 241 */       float f1 = (float)(b(5, 1.0F)[1] - b(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
/* 242 */       float f2 = MathHelper.cos(f1);
/* 243 */       float f9 = -MathHelper.sin(f1);
/* 244 */       float f10 = this.yaw * 3.1415927F / 180.0F;
/* 245 */       float f11 = MathHelper.sin(f10);
/* 246 */       float f12 = MathHelper.cos(f10);
/*     */       
/* 248 */       this.br.h();
/* 249 */       this.br.setPositionRotation(this.locX + (f11 * 0.5F), this.locY, this.locZ - (f12 * 0.5F), 0.0F, 0.0F);
/* 250 */       this.bv.h();
/* 251 */       this.bv.setPositionRotation(this.locX + (f12 * 4.5F), this.locY + 2.0D, this.locZ + (f11 * 4.5F), 0.0F, 0.0F);
/* 252 */       this.bw.h();
/* 253 */       this.bw.setPositionRotation(this.locX - (f12 * 4.5F), this.locY + 2.0D, this.locZ - (f11 * 4.5F), 0.0F, 0.0F);
/* 254 */       if (!this.world.isStatic && this.hurtTicks == 0) {
/* 255 */         a(this.world.getEntities(this, this.bv.boundingBox.grow(4.0D, 2.0D, 4.0D).d(0.0D, -2.0D, 0.0D)));
/* 256 */         a(this.world.getEntities(this, this.bw.boundingBox.grow(4.0D, 2.0D, 4.0D).d(0.0D, -2.0D, 0.0D)));
/* 257 */         b(this.world.getEntities(this, this.bq.boundingBox.grow(1.0D, 1.0D, 1.0D)));
/*     */       } 
/*     */       
/* 260 */       double[] adouble = b(5, 1.0F);
/* 261 */       double[] adouble1 = b(0, 1.0F);
/*     */       
/* 263 */       float f3 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F - this.bf * 0.01F);
/* 264 */       float f13 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F - this.bf * 0.01F);
/*     */       
/* 266 */       this.bq.h();
/* 267 */       this.bq.setPositionRotation(this.locX + (f3 * 5.5F * f2), this.locY + (adouble1[1] - adouble[1]) * 1.0D + (f9 * 5.5F), this.locZ - (f13 * 5.5F * f2), 0.0F, 0.0F);
/*     */       
/* 269 */       for (int j = 0; j < 3; j++) {
/* 270 */         EntityComplexPart entitycomplexpart = null;
/*     */         
/* 272 */         if (j == 0) {
/* 273 */           entitycomplexpart = this.bs;
/*     */         }
/*     */         
/* 276 */         if (j == 1) {
/* 277 */           entitycomplexpart = this.bt;
/*     */         }
/*     */         
/* 280 */         if (j == 2) {
/* 281 */           entitycomplexpart = this.bu;
/*     */         }
/*     */         
/* 284 */         double[] adouble2 = b(12 + j * 2, 1.0F);
/* 285 */         float f14 = this.yaw * 3.1415927F / 180.0F + b(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
/* 286 */         float f15 = MathHelper.sin(f14);
/* 287 */         float f16 = MathHelper.cos(f14);
/* 288 */         float f17 = 1.5F;
/* 289 */         float f18 = (j + 1) * 2.0F;
/*     */         
/* 291 */         entitycomplexpart.h();
/* 292 */         entitycomplexpart.setPositionRotation(this.locX - ((f11 * f17 + f15 * f18) * f2), this.locY + (adouble2[1] - adouble[1]) * 1.0D - ((f18 + f17) * f9) + 1.5D, this.locZ + ((f12 * f17 + f16 * f18) * f2), 0.0F, 0.0F);
/*     */       } 
/*     */       
/* 295 */       if (!this.world.isStatic) {
/* 296 */         this.bA = a(this.bq.boundingBox) | a(this.br.boundingBox);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void bP() {
/* 302 */     if (this.bC != null) {
/* 303 */       if (this.bC.dead) {
/* 304 */         if (!this.world.isStatic) {
/* 305 */           CraftEventFactory.entityDamage = this.bC;
/* 306 */           a(this.bq, DamageSource.explosion((Explosion)null), 10.0F);
/* 307 */           CraftEventFactory.entityDamage = null;
/*     */         } 
/*     */         
/* 310 */         this.bC = null;
/* 311 */       } else if (this.ticksLived % 10 == 0 && getHealth() < getMaxHealth()) {
/*     */         
/* 313 */         EntityRegainHealthEvent event = new EntityRegainHealthEvent((Entity)getBukkitEntity(), 1.0D, EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL);
/* 314 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/* 316 */         if (!event.isCancelled()) {
/* 317 */           setHealth((float)(getHealth() + event.getAmount()));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 323 */     if (this.random.nextInt(10) == 0) {
/* 324 */       float f = 32.0F;
/* 325 */       List list = this.world.a(EntityEnderCrystal.class, this.boundingBox.grow(f, f, f));
/* 326 */       EntityEnderCrystal entityendercrystal = null;
/* 327 */       double d0 = Double.MAX_VALUE;
/* 328 */       Iterator<EntityEnderCrystal> iterator = list.iterator();
/*     */       
/* 330 */       while (iterator.hasNext()) {
/* 331 */         EntityEnderCrystal entityendercrystal1 = iterator.next();
/* 332 */         double d1 = entityendercrystal1.f(this);
/*     */         
/* 334 */         if (d1 < d0) {
/* 335 */           d0 = d1;
/* 336 */           entityendercrystal = entityendercrystal1;
/*     */         } 
/*     */       } 
/*     */       
/* 340 */       this.bC = entityendercrystal;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(List list) {
/* 345 */     double d0 = (this.br.boundingBox.a + this.br.boundingBox.d) / 2.0D;
/* 346 */     double d1 = (this.br.boundingBox.c + this.br.boundingBox.f) / 2.0D;
/* 347 */     Iterator<Entity> iterator = list.iterator();
/*     */     
/* 349 */     while (iterator.hasNext()) {
/* 350 */       Entity entity = iterator.next();
/*     */       
/* 352 */       if (entity instanceof EntityLiving) {
/* 353 */         double d2 = entity.locX - d0;
/* 354 */         double d3 = entity.locZ - d1;
/* 355 */         double d4 = d2 * d2 + d3 * d3;
/*     */         
/* 357 */         entity.g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b(List<Entity> list) {
/* 363 */     for (int i = 0; i < list.size(); i++) {
/* 364 */       Entity entity = list.get(i);
/*     */       
/* 366 */       if (entity instanceof EntityLiving) {
/* 367 */         entity.damageEntity(DamageSource.mobAttack(this), 10.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void bQ() {
/* 373 */     this.bz = false;
/* 374 */     if (this.random.nextInt(2) == 0 && !this.world.players.isEmpty()) {
/*     */       
/* 376 */       Entity target = this.world.players.get(this.random.nextInt(this.world.players.size()));
/* 377 */       EntityTargetEvent event = new EntityTargetEvent((Entity)getBukkitEntity(), (Entity)target.getBukkitEntity(), EntityTargetEvent.TargetReason.RANDOM_TARGET);
/* 378 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 380 */       if (!event.isCancelled()) {
/* 381 */         if (event.getTarget() == null) {
/* 382 */           this.bD = null;
/*     */         } else {
/* 384 */           this.bD = ((CraftEntity)event.getTarget()).getHandle();
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 389 */       boolean flag = false;
/*     */       
/*     */       do {
/* 392 */         this.h = 0.0D;
/* 393 */         this.i = (70.0F + this.random.nextFloat() * 50.0F);
/* 394 */         this.bm = 0.0D;
/* 395 */         this.h += (this.random.nextFloat() * 120.0F - 60.0F);
/* 396 */         this.bm += (this.random.nextFloat() * 120.0F - 60.0F);
/* 397 */         double d0 = this.locX - this.h;
/* 398 */         double d1 = this.locY - this.i;
/* 399 */         double d2 = this.locZ - this.bm;
/*     */         
/* 401 */         flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
/* 402 */       } while (!flag);
/*     */       
/* 404 */       this.bD = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private float b(double d0) {
/* 409 */     return (float)MathHelper.g(d0);
/*     */   }
/*     */   
/*     */   private boolean a(AxisAlignedBB axisalignedbb) {
/* 413 */     int i = MathHelper.floor(axisalignedbb.a);
/* 414 */     int j = MathHelper.floor(axisalignedbb.b);
/* 415 */     int k = MathHelper.floor(axisalignedbb.c);
/* 416 */     int l = MathHelper.floor(axisalignedbb.d);
/* 417 */     int i1 = MathHelper.floor(axisalignedbb.e);
/* 418 */     int j1 = MathHelper.floor(axisalignedbb.f);
/* 419 */     boolean flag = false;
/* 420 */     boolean flag1 = false;
/*     */ 
/*     */     
/* 423 */     List<Block> destroyedBlocks = new ArrayList<Block>();
/* 424 */     CraftWorld craftWorld = this.world.getWorld();
/*     */ 
/*     */     
/* 427 */     for (int k1 = i; k1 <= l; k1++) {
/* 428 */       for (int l1 = j; l1 <= i1; l1++) {
/* 429 */         for (int i2 = k; i2 <= j1; i2++) {
/* 430 */           Block block = this.world.getType(k1, l1, i2);
/*     */           
/* 432 */           if (block.getMaterial() != Material.AIR) {
/* 433 */             if (block != Blocks.OBSIDIAN && block != Blocks.WHITESTONE && block != Blocks.BEDROCK && this.world.getGameRules().getBoolean("mobGriefing")) {
/*     */ 
/*     */               
/* 436 */               flag1 = true;
/* 437 */               destroyedBlocks.add(craftWorld.getBlockAt(k1, l1, i2));
/*     */             } else {
/*     */               
/* 440 */               flag = true;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 447 */     if (flag1) {
/*     */       
/* 449 */       CraftEntity craftEntity = getBukkitEntity();
/* 450 */       EntityExplodeEvent event = new EntityExplodeEvent((Entity)craftEntity, craftEntity.getLocation(), destroyedBlocks, 0.0F);
/* 451 */       Bukkit.getPluginManager().callEvent((Event)event);
/* 452 */       if (event.isCancelled())
/*     */       {
/*     */         
/* 455 */         return flag; } 
/* 456 */       if (event.getYield() == 0.0F) {
/*     */         
/* 458 */         for (Block block : event.blockList()) {
/* 459 */           this.world.setAir(block.getX(), block.getY(), block.getZ());
/*     */         }
/*     */       } else {
/* 462 */         for (Block block : event.blockList()) {
/* 463 */           Material blockId = block.getType();
/* 464 */           if (blockId == Material.AIR) {
/*     */             continue;
/*     */           }
/*     */           
/* 468 */           int blockX = block.getX();
/* 469 */           int blockY = block.getY();
/* 470 */           int blockZ = block.getZ();
/*     */           
/* 472 */           Block nmsBlock = CraftMagicNumbers.getBlock(blockId);
/* 473 */           if (nmsBlock.a(this.explosionSource)) {
/* 474 */             nmsBlock.dropNaturally(this.world, blockX, blockY, blockZ, block.getData(), event.getYield(), 0);
/*     */           }
/* 476 */           nmsBlock.wasExploded(this.world, blockX, blockY, blockZ, this.explosionSource);
/*     */           
/* 478 */           this.world.setAir(blockX, blockY, blockZ);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 483 */       double d0 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * this.random.nextFloat();
/* 484 */       double d1 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * this.random.nextFloat();
/* 485 */       double d2 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * this.random.nextFloat();
/*     */       
/* 487 */       this.world.addParticle("largeexplode", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */     
/* 490 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
/* 494 */     if (entitycomplexpart != this.bq) {
/* 495 */       f = f / 4.0F + 1.0F;
/*     */     }
/*     */     
/* 498 */     float f1 = this.yaw * 3.1415927F / 180.0F;
/* 499 */     float f2 = MathHelper.sin(f1);
/* 500 */     float f3 = MathHelper.cos(f1);
/*     */     
/* 502 */     this.h = this.locX + (f2 * 5.0F) + ((this.random.nextFloat() - 0.5F) * 2.0F);
/* 503 */     this.i = this.locY + (this.random.nextFloat() * 3.0F) + 1.0D;
/* 504 */     this.bm = this.locZ - (f3 * 5.0F) + ((this.random.nextFloat() - 0.5F) * 2.0F);
/* 505 */     this.bD = null;
/* 506 */     if (damagesource.getEntity() instanceof EntityHuman || damagesource.isExplosion()) {
/* 507 */       dealDamage(damagesource, f);
/*     */     }
/*     */     
/* 510 */     return true;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 514 */     return false;
/*     */   }
/*     */   
/*     */   public boolean dealDamage(DamageSource damagesource, float f) {
/* 518 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   protected void aF() {
/* 522 */     if (this.dead)
/* 523 */       return;  this.bB++;
/* 524 */     if (this.bB >= 180 && this.bB <= 200) {
/* 525 */       float f = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 526 */       float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
/* 527 */       float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
/*     */       
/* 529 */       this.world.addParticle("hugeexplosion", this.locX + f, this.locY + 2.0D + f1, this.locZ + f2, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     if (!this.world.isStatic) {
/* 536 */       if (this.bB > 150 && this.bB % 5 == 0) {
/* 537 */         int i = this.expToDrop / 12;
/*     */         
/* 539 */         while (i > 0) {
/* 540 */           int j = EntityExperienceOrb.getOrbValue(i);
/* 541 */           i -= j;
/* 542 */           this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
/*     */         } 
/*     */       } 
/*     */       
/* 546 */       if (this.bB == 1)
/*     */       {
/*     */         
/* 549 */         for (EntityPlayer player : this.world.players) {
/* 550 */           int viewDistance = player.viewDistance * 16;
/* 551 */           double deltaX = this.locX - player.locX;
/* 552 */           double deltaZ = this.locZ - player.locZ;
/* 553 */           double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
/* 554 */           if (this.world.spigotConfig.dragonDeathSoundRadius > 0 && distanceSquared > (this.world.spigotConfig.dragonDeathSoundRadius * this.world.spigotConfig.dragonDeathSoundRadius))
/* 555 */             continue;  if (distanceSquared > (viewDistance * viewDistance)) {
/* 556 */             double deltaLength = Math.sqrt(distanceSquared);
/* 557 */             double relativeX = player.locX + deltaX / deltaLength * viewDistance;
/* 558 */             double relativeZ = player.locZ + deltaZ / deltaLength * viewDistance;
/* 559 */             player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1018, (int)relativeX, (int)this.locY, (int)relativeZ, 0, true)); continue;
/*     */           } 
/* 561 */           player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1018, (int)this.locX, (int)this.locY, (int)this.locZ, 0, true));
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 568 */     move(0.0D, 0.10000000149011612D, 0.0D);
/* 569 */     this.aM = this.yaw += 20.0F;
/* 570 */     if (this.bB == 200 && !this.world.isStatic) {
/* 571 */       int i = this.expToDrop - 10 * this.expToDrop / 12;
/*     */       
/* 573 */       while (i > 0) {
/* 574 */         int j = EntityExperienceOrb.getOrbValue(i);
/* 575 */         i -= j;
/* 576 */         this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
/*     */       } 
/*     */       
/* 579 */       b(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
/* 580 */       die();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b(int i, int j) {
/* 585 */     byte b0 = 64;
/*     */     
/* 587 */     BlockEnderPortal.a = true;
/* 588 */     byte b1 = 4;
/*     */ 
/*     */     
/* 591 */     BlockStateListPopulator world = new BlockStateListPopulator((World)this.world.getWorld());
/*     */     
/* 593 */     for (int k = b0 - 1; k <= b0 + 32; k++) {
/* 594 */       for (int l = i - b1; l <= i + b1; l++) {
/* 595 */         for (int i1 = j - b1; i1 <= j + b1; i1++) {
/* 596 */           double d0 = (l - i);
/* 597 */           double d1 = (i1 - j);
/* 598 */           double d2 = d0 * d0 + d1 * d1;
/*     */           
/* 600 */           if (d2 <= (b1 - 0.5D) * (b1 - 0.5D)) {
/* 601 */             if (k < b0) {
/* 602 */               if (d2 <= ((b1 - 1) - 0.5D) * ((b1 - 1) - 0.5D)) {
/* 603 */                 world.setTypeUpdate(l, k, i1, Blocks.BEDROCK);
/*     */               }
/* 605 */             } else if (k > b0) {
/* 606 */               world.setTypeUpdate(l, k, i1, Blocks.AIR);
/* 607 */             } else if (d2 > ((b1 - 1) - 0.5D) * ((b1 - 1) - 0.5D)) {
/* 608 */               world.setTypeUpdate(l, k, i1, Blocks.BEDROCK);
/*     */             } else {
/* 610 */               world.setTypeUpdate(l, k, i1, Blocks.ENDER_PORTAL);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 617 */     world.setType(i, b0 + 0, j, Blocks.BEDROCK);
/* 618 */     world.setType(i, b0 + 1, j, Blocks.BEDROCK);
/* 619 */     world.setType(i, b0 + 2, j, Blocks.BEDROCK);
/* 620 */     world.setTypeAndData(i - 1, b0 + 2, j, Blocks.TORCH, 2, 0);
/* 621 */     world.setTypeAndData(i + 1, b0 + 2, j, Blocks.TORCH, 1, 0);
/* 622 */     world.setTypeAndData(i, b0 + 2, j - 1, Blocks.TORCH, 4, 0);
/* 623 */     world.setTypeAndData(i, b0 + 2, j + 1, Blocks.TORCH, 3, 0);
/* 624 */     world.setType(i, b0 + 3, j, Blocks.BEDROCK);
/* 625 */     world.setType(i, b0 + 4, j, Blocks.DRAGON_EGG);
/*     */     
/* 627 */     EntityCreatePortalEvent event = new EntityCreatePortalEvent((LivingEntity)getBukkitEntity(), Collections.unmodifiableList(world.getList()), PortalType.ENDER);
/* 628 */     this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/* 630 */     if (!event.isCancelled()) {
/* 631 */       for (BlockState state : event.getBlocks()) {
/* 632 */         state.update(true);
/*     */       }
/*     */     } else {
/* 635 */       for (BlockState state : event.getBlocks()) {
/* 636 */         PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(state.getX(), state.getY(), state.getZ(), this.world);
/* 637 */         for (Iterator<EntityHuman> it = this.world.players.iterator(); it.hasNext(); ) {
/* 638 */           EntityHuman entity = it.next();
/* 639 */           if (entity instanceof EntityPlayer) {
/* 640 */             ((EntityPlayer)entity).playerConnection.sendPacket(packet);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 647 */     BlockEnderPortal.a = false;
/*     */   }
/*     */   
/*     */   protected void w() {}
/*     */   
/*     */   public Entity[] at() {
/* 653 */     return (Entity[])this.children;
/*     */   }
/*     */   
/*     */   public boolean R() {
/* 657 */     return false;
/*     */   }
/*     */   
/*     */   public World a() {
/* 661 */     return this.world;
/*     */   }
/*     */   
/*     */   protected String t() {
/* 665 */     return "mob.enderdragon.growl";
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 669 */     return "mob.enderdragon.hit";
/*     */   }
/*     */   
/*     */   protected float bf() {
/* 673 */     return 5.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExpReward() {
/* 680 */     return 12000;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityEnderDragon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */