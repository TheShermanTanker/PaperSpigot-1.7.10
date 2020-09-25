/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.projectiles.ProjectileSource;
/*     */ 
/*     */ public class EntityArrow extends Entity implements IProjectile {
/*  13 */   private int d = -1;
/*  14 */   private int e = -1;
/*  15 */   private int f = -1;
/*     */   private Block g;
/*     */   private int h;
/*     */   public boolean inGround = false;
/*     */   public int fromPlayer;
/*     */   public int shake;
/*     */   public Entity shooter;
/*     */   private int at;
/*     */   private int au;
/*  24 */   private double damage = 2.0D;
/*     */ 
/*     */   
/*     */   public int knockbackStrength;
/*     */ 
/*     */   
/*     */   public void inactiveTick() {
/*  31 */     if (this.inGround)
/*     */     {
/*  33 */       this.at += 19;
/*     */     }
/*  35 */     super.inactiveTick();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World world) {
/*  40 */     super(world);
/*  41 */     this.j = 10.0D;
/*  42 */     a(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityArrow(World world, double d0, double d1, double d2) {
/*  46 */     super(world);
/*  47 */     this.j = 10.0D;
/*  48 */     a(0.5F, 0.5F);
/*  49 */     setPosition(d0, d1, d2);
/*  50 */     this.height = 0.0F;
/*     */   }
/*     */   
/*     */   public EntityArrow(World world, EntityLiving entityliving, EntityLiving entityliving1, float f, float f1) {
/*  54 */     super(world);
/*  55 */     this.j = 10.0D;
/*  56 */     this.shooter = entityliving;
/*  57 */     this.projectileSource = (ProjectileSource)entityliving.getBukkitEntity();
/*  58 */     if (entityliving instanceof EntityHuman) {
/*  59 */       this.fromPlayer = 1;
/*     */     }
/*     */     
/*  62 */     this.locY = entityliving.locY + entityliving.getHeadHeight() - 0.10000000149011612D;
/*  63 */     double d0 = entityliving1.locX - entityliving.locX;
/*  64 */     double d1 = entityliving1.boundingBox.b + (entityliving1.length / 3.0F) - this.locY;
/*  65 */     double d2 = entityliving1.locZ - entityliving.locZ;
/*  66 */     double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*     */     
/*  68 */     if (d3 >= 1.0E-7D) {
/*  69 */       float f2 = (float)(Math.atan2(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
/*  70 */       float f3 = (float)-(Math.atan2(d1, d3) * 180.0D / 3.1415927410125732D);
/*  71 */       double d4 = d0 / d3;
/*  72 */       double d5 = d2 / d3;
/*     */       
/*  74 */       setPositionRotation(entityliving.locX + d4, this.locY, entityliving.locZ + d5, f2, f3);
/*  75 */       this.height = 0.0F;
/*  76 */       float f4 = (float)d3 * 0.2F;
/*     */       
/*  78 */       shoot(d0, d1 + f4, d2, f, f1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EntityArrow(World world, EntityLiving entityliving, float f) {
/*  83 */     super(world);
/*  84 */     this.j = 10.0D;
/*  85 */     this.shooter = entityliving;
/*  86 */     this.projectileSource = (ProjectileSource)entityliving.getBukkitEntity();
/*  87 */     if (entityliving instanceof EntityHuman) {
/*  88 */       this.fromPlayer = 1;
/*     */     }
/*     */     
/*  91 */     a(0.5F, 0.5F);
/*  92 */     setPositionRotation(entityliving.locX, entityliving.locY + entityliving.getHeadHeight(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  93 */     this.locX -= (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  94 */     this.locY -= 0.10000000149011612D;
/*  95 */     this.locZ -= (MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  96 */     setPosition(this.locX, this.locY, this.locZ);
/*  97 */     this.height = 0.0F;
/*  98 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
/*  99 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
/* 100 */     this.motY = -MathHelper.sin(this.pitch / 180.0F * 3.1415927F);
/* 101 */     shoot(this.motX, this.motY, this.motZ, f * 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void c() {
/* 105 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void shoot(double d0, double d1, double d2, float f, float f1) {
/* 109 */     float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/* 111 */     d0 /= f2;
/* 112 */     d1 /= f2;
/* 113 */     d2 /= f2;
/* 114 */     d0 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : true) * 0.007499999832361937D * f1;
/* 115 */     d1 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : true) * 0.007499999832361937D * f1;
/* 116 */     d2 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : true) * 0.007499999832361937D * f1;
/* 117 */     d0 *= f;
/* 118 */     d1 *= f;
/* 119 */     d2 *= f;
/* 120 */     this.motX = d0;
/* 121 */     this.motY = d1;
/* 122 */     this.motZ = d2;
/* 123 */     float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*     */     
/* 125 */     this.lastYaw = this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
/* 126 */     this.lastPitch = this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
/* 127 */     this.at = 0;
/*     */   }
/*     */   
/*     */   public void h() {
/* 131 */     super.h();
/* 132 */     if (this.lastPitch == 0.0F && this.lastYaw == 0.0F) {
/* 133 */       float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */       
/* 135 */       this.lastYaw = this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/* 136 */       this.lastPitch = this.pitch = (float)(Math.atan2(this.motY, f) * 180.0D / 3.1415927410125732D);
/*     */     } 
/*     */     
/* 139 */     Block block = this.world.getType(this.d, this.e, this.f);
/*     */     
/* 141 */     if (block.getMaterial() != Material.AIR) {
/* 142 */       block.updateShape(this.world, this.d, this.e, this.f);
/* 143 */       AxisAlignedBB axisalignedbb = block.a(this.world, this.d, this.e, this.f);
/*     */       
/* 145 */       if (axisalignedbb != null && axisalignedbb.a(Vec3D.a(this.locX, this.locY, this.locZ))) {
/* 146 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     if (this.shake > 0) {
/* 151 */       this.shake--;
/*     */     }
/*     */     
/* 154 */     if (this.inGround) {
/* 155 */       int i = this.world.getData(this.d, this.e, this.f);
/*     */       
/* 157 */       if (block == this.g && i == this.h) {
/* 158 */         this.at++;
/* 159 */         if (this.at >= this.world.spigotConfig.arrowDespawnRate) {
/* 160 */           die();
/*     */         }
/*     */       } else {
/* 163 */         this.inGround = false;
/* 164 */         this.motX *= (this.random.nextFloat() * 0.2F);
/* 165 */         this.motY *= (this.random.nextFloat() * 0.2F);
/* 166 */         this.motZ *= (this.random.nextFloat() * 0.2F);
/* 167 */         this.at = 0;
/* 168 */         this.au = 0;
/*     */       } 
/*     */     } else {
/* 171 */       this.au++;
/* 172 */       Vec3D vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
/* 173 */       Vec3D vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 174 */       MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1, false, true, false);
/*     */       
/* 176 */       vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
/* 177 */       vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 178 */       if (movingobjectposition != null) {
/* 179 */         vec3d1 = Vec3D.a(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
/*     */       }
/*     */       
/* 182 */       Entity entity = null;
/* 183 */       List<Entity> list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 184 */       double d0 = 0.0D;
/*     */ 
/*     */       
/*     */       int j;
/*     */       
/* 189 */       for (j = 0; j < list.size(); j++) {
/* 190 */         Entity entity1 = list.get(j);
/*     */         
/* 192 */         if (entity1.R() && (entity1 != this.shooter || this.au >= 5)) {
/* 193 */           float f = 0.3F;
/* 194 */           AxisAlignedBB axisalignedbb1 = entity1.boundingBox.grow(f, f, f);
/* 195 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);
/*     */           
/* 197 */           if (movingobjectposition1 != null) {
/* 198 */             double d1 = vec3d.distanceSquared(movingobjectposition1.pos);
/*     */             
/* 200 */             if (d1 < d0 || d0 == 0.0D) {
/* 201 */               entity = entity1;
/* 202 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 208 */       if (entity != null) {
/* 209 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 212 */       if (movingobjectposition != null && movingobjectposition.entity != null && movingobjectposition.entity instanceof EntityHuman) {
/* 213 */         EntityHuman entityhuman = (EntityHuman)movingobjectposition.entity;
/*     */         
/* 215 */         if (entityhuman.abilities.isInvulnerable || (this.shooter instanceof EntityHuman && !((EntityHuman)this.shooter).a(entityhuman))) {
/* 216 */           movingobjectposition = null;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       if (movingobjectposition != null && movingobjectposition.entity instanceof EntityPlayer && this.shooter != null && this.shooter instanceof EntityPlayer && 
/* 225 */         !((EntityPlayer)this.shooter).getBukkitEntity().canSee((Player)((EntityPlayer)movingobjectposition.entity).getBukkitEntity())) {
/* 226 */         movingobjectposition = null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 231 */       if (movingobjectposition != null) {
/* 232 */         CraftEventFactory.callProjectileHitEvent(this);
/*     */         
/* 234 */         if (movingobjectposition.entity != null) {
/* 235 */           float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
/* 236 */           int k = MathHelper.f(f * this.damage);
/*     */           
/* 238 */           if (isCritical()) {
/* 239 */             k += this.random.nextInt(k / 2 + 2);
/*     */           }
/*     */           
/* 242 */           DamageSource damagesource = null;
/*     */           
/* 244 */           if (this.shooter == null) {
/* 245 */             damagesource = DamageSource.arrow(this, this);
/*     */           } else {
/* 247 */             damagesource = DamageSource.arrow(this, this.shooter);
/*     */           } 
/*     */ 
/*     */           
/* 251 */           if (movingobjectposition.entity.damageEntity(damagesource, k)) {
/* 252 */             if (isBurning() && !(movingobjectposition.entity instanceof EntityEnderman) && (!(movingobjectposition.entity instanceof EntityPlayer) || !(this.shooter instanceof EntityPlayer) || this.world.pvpMode)) {
/* 253 */               EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent((Entity)getBukkitEntity(), (Entity)entity.getBukkitEntity(), 5);
/* 254 */               Bukkit.getPluginManager().callEvent((Event)combustEvent);
/*     */               
/* 256 */               if (!combustEvent.isCancelled()) {
/* 257 */                 movingobjectposition.entity.setOnFire(combustEvent.getDuration());
/*     */               }
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 263 */             if (movingobjectposition.entity instanceof EntityLiving) {
/* 264 */               EntityLiving entityliving = (EntityLiving)movingobjectposition.entity;
/*     */               
/* 266 */               if (!this.world.isStatic) {
/* 267 */                 entityliving.p(entityliving.aZ() + 1);
/*     */               }
/*     */               
/* 270 */               if (this.knockbackStrength > 0) {
/* 271 */                 float f3 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 272 */                 if (f3 > 0.0F) {
/* 273 */                   movingobjectposition.entity.g(this.motX * this.knockbackStrength * 0.6000000238418579D / f3, 0.1D, this.motZ * this.knockbackStrength * 0.6000000238418579D / f3);
/*     */                 }
/*     */               } 
/*     */               
/* 277 */               if (this.shooter != null && this.shooter instanceof EntityLiving) {
/* 278 */                 EnchantmentManager.a(entityliving, this.shooter);
/* 279 */                 EnchantmentManager.b((EntityLiving)this.shooter, entityliving);
/*     */               } 
/*     */               
/* 282 */               if (this.shooter != null && movingobjectposition.entity != this.shooter && movingobjectposition.entity instanceof EntityHuman && this.shooter instanceof EntityPlayer) {
/* 283 */                 ((EntityPlayer)this.shooter).playerConnection.sendPacket(new PacketPlayOutGameStateChange(6, 0.0F));
/*     */               }
/*     */             } 
/*     */             
/* 287 */             makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 288 */             if (!(movingobjectposition.entity instanceof EntityEnderman)) {
/* 289 */               die();
/*     */             }
/*     */           } else {
/* 292 */             this.motX *= -0.10000000149011612D;
/* 293 */             this.motY *= -0.10000000149011612D;
/* 294 */             this.motZ *= -0.10000000149011612D;
/* 295 */             this.yaw += 180.0F;
/* 296 */             this.lastYaw += 180.0F;
/* 297 */             this.au = 0;
/*     */           } 
/*     */         } else {
/* 300 */           this.d = movingobjectposition.b;
/* 301 */           this.e = movingobjectposition.c;
/* 302 */           this.f = movingobjectposition.d;
/* 303 */           this.g = this.world.getType(this.d, this.e, this.f);
/* 304 */           this.h = this.world.getData(this.d, this.e, this.f);
/* 305 */           this.motX = (float)(movingobjectposition.pos.a - this.locX);
/* 306 */           this.motY = (float)(movingobjectposition.pos.b - this.locY);
/* 307 */           this.motZ = (float)(movingobjectposition.pos.c - this.locZ);
/* 308 */           float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
/* 309 */           this.locX -= this.motX / f * 0.05000000074505806D;
/* 310 */           this.locY -= this.motY / f * 0.05000000074505806D;
/* 311 */           this.locZ -= this.motZ / f * 0.05000000074505806D;
/* 312 */           makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 313 */           this.inGround = true;
/* 314 */           this.shake = 7;
/* 315 */           setCritical(false);
/* 316 */           if (this.g.getMaterial() != Material.AIR) {
/* 317 */             this.g.a(this.world, this.d, this.e, this.f, this);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 322 */       if (isCritical()) {
/* 323 */         for (j = 0; j < 4; j++) {
/* 324 */           this.world.addParticle("crit", this.locX + this.motX * j / 4.0D, this.locY + this.motY * j / 4.0D, this.locZ + this.motZ * j / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ);
/*     */         }
/*     */       }
/*     */       
/* 328 */       this.locX += this.motX;
/* 329 */       this.locY += this.motY;
/* 330 */       this.locZ += this.motZ;
/* 331 */       float f2 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 332 */       this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */       
/* 334 */       for (this.pitch = (float)(Math.atan2(this.motY, f2) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */       
/* 338 */       while (this.pitch - this.lastPitch >= 180.0F) {
/* 339 */         this.lastPitch += 360.0F;
/*     */       }
/*     */       
/* 342 */       while (this.yaw - this.lastYaw < -180.0F) {
/* 343 */         this.lastYaw -= 360.0F;
/*     */       }
/*     */       
/* 346 */       while (this.yaw - this.lastYaw >= 180.0F) {
/* 347 */         this.lastYaw += 360.0F;
/*     */       }
/*     */       
/* 350 */       this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 351 */       this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/* 352 */       float f4 = 0.99F;
/*     */       
/* 354 */       float f1 = 0.05F;
/* 355 */       if (M()) {
/* 356 */         for (int l = 0; l < 4; l++) {
/* 357 */           float f3 = 0.25F;
/* 358 */           this.world.addParticle("bubble", this.locX - this.motX * f3, this.locY - this.motY * f3, this.locZ - this.motZ * f3, this.motX, this.motY, this.motZ);
/*     */         } 
/*     */         
/* 361 */         f4 = 0.8F;
/*     */       } 
/*     */       
/* 364 */       if (L()) {
/* 365 */         extinguish();
/*     */       }
/*     */       
/* 368 */       this.motX *= f4;
/* 369 */       this.motY *= f4;
/* 370 */       this.motZ *= f4;
/* 371 */       this.motY -= f1;
/* 372 */       setPosition(this.locX, this.locY, this.locZ);
/* 373 */       I();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 378 */     nbttagcompound.setShort("xTile", (short)this.d);
/* 379 */     nbttagcompound.setShort("yTile", (short)this.e);
/* 380 */     nbttagcompound.setShort("zTile", (short)this.f);
/* 381 */     nbttagcompound.setShort("life", (short)this.at);
/* 382 */     nbttagcompound.setByte("inTile", (byte)Block.getId(this.g));
/* 383 */     nbttagcompound.setByte("inData", (byte)this.h);
/* 384 */     nbttagcompound.setByte("shake", (byte)this.shake);
/* 385 */     nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 386 */     nbttagcompound.setByte("pickup", (byte)this.fromPlayer);
/* 387 */     nbttagcompound.setDouble("damage", this.damage);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 391 */     this.d = nbttagcompound.getShort("xTile");
/* 392 */     this.e = nbttagcompound.getShort("yTile");
/* 393 */     this.f = nbttagcompound.getShort("zTile");
/* 394 */     this.at = nbttagcompound.getShort("life");
/* 395 */     this.g = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
/* 396 */     this.h = nbttagcompound.getByte("inData") & 0xFF;
/* 397 */     this.shake = nbttagcompound.getByte("shake") & 0xFF;
/* 398 */     this.inGround = (nbttagcompound.getByte("inGround") == 1);
/* 399 */     if (nbttagcompound.hasKeyOfType("damage", 99)) {
/* 400 */       this.damage = nbttagcompound.getDouble("damage");
/*     */     }
/*     */     
/* 403 */     if (nbttagcompound.hasKeyOfType("pickup", 99)) {
/* 404 */       this.fromPlayer = nbttagcompound.getByte("pickup");
/* 405 */     } else if (nbttagcompound.hasKeyOfType("player", 99)) {
/* 406 */       this.fromPlayer = nbttagcompound.getBoolean("player") ? 1 : 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b_(EntityHuman entityhuman) {
/* 411 */     if (!this.world.isStatic && this.inGround && this.shake <= 0) {
/*     */       
/* 413 */       ItemStack itemstack = new ItemStack(Items.ARROW);
/* 414 */       if (this.fromPlayer == 1 && entityhuman.inventory.canHold(itemstack) > 0) {
/* 415 */         EntityItem item = new EntityItem(this.world, this.locX, this.locY, this.locZ, itemstack);
/*     */         
/* 417 */         PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), (Item)new CraftItem(this.world.getServer(), this, item), 0);
/*     */         
/* 419 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/* 421 */         if (event.isCancelled()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 427 */       boolean flag = (this.fromPlayer == 1 || (this.fromPlayer == 2 && entityhuman.abilities.canInstantlyBuild));
/*     */       
/* 429 */       if (this.fromPlayer == 1 && !entityhuman.inventory.pickup(new ItemStack(Items.ARROW, 1))) {
/* 430 */         flag = false;
/*     */       }
/*     */       
/* 433 */       if (flag) {
/* 434 */         makeSound("random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 435 */         entityhuman.receive(this, 1);
/* 436 */         die();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean g_() {
/* 442 */     return false;
/*     */   }
/*     */   
/*     */   public void b(double d0) {
/* 446 */     this.damage = d0;
/*     */   }
/*     */   
/*     */   public double e() {
/* 450 */     return this.damage;
/*     */   }
/*     */   
/*     */   public void setKnockbackStrength(int i) {
/* 454 */     this.knockbackStrength = i;
/*     */   }
/*     */   
/*     */   public boolean av() {
/* 458 */     return false;
/*     */   }
/*     */   
/*     */   public void setCritical(boolean flag) {
/* 462 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 464 */     if (flag) {
/* 465 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     } else {
/* 467 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isCritical() {
/* 472 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 474 */     return ((b0 & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInGround() {
/* 479 */     return this.inGround;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */