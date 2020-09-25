/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Fish;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.player.PlayerFishEvent;
/*     */ 
/*     */ public class EntityFishingHook
/*     */   extends Entity
/*     */ {
/*  15 */   private static final List d = Arrays.asList(new PossibleFishingResult[] { (new PossibleFishingResult(new ItemStack(Items.LEATHER_BOOTS), 10)).a(0.9F), new PossibleFishingResult(new ItemStack(Items.LEATHER), 10), new PossibleFishingResult(new ItemStack(Items.BONE), 10), new PossibleFishingResult(new ItemStack(Items.POTION), 10), new PossibleFishingResult(new ItemStack(Items.STRING), 5), (new PossibleFishingResult(new ItemStack(Items.FISHING_ROD), 2)).a(0.9F), new PossibleFishingResult(new ItemStack(Items.BOWL), 10), new PossibleFishingResult(new ItemStack(Items.STICK), 5), new PossibleFishingResult(new ItemStack(Items.INK_SACK, 10, 0), 1), new PossibleFishingResult(new ItemStack(Blocks.TRIPWIRE_SOURCE), 10), new PossibleFishingResult(new ItemStack(Items.ROTTEN_FLESH), 10) });
/*  16 */   private static final List e = Arrays.asList(new PossibleFishingResult[] { new PossibleFishingResult(new ItemStack(Blocks.WATER_LILY), 1), new PossibleFishingResult(new ItemStack(Items.NAME_TAG), 1), new PossibleFishingResult(new ItemStack(Items.SADDLE), 1), (new PossibleFishingResult(new ItemStack(Items.BOW), 1)).a(0.25F).a(), (new PossibleFishingResult(new ItemStack(Items.FISHING_ROD), 1)).a(0.25F).a(), (new PossibleFishingResult(new ItemStack(Items.BOOK), 1)).a() });
/*  17 */   private static final List f = Arrays.asList(new PossibleFishingResult[] { new PossibleFishingResult(new ItemStack(Items.RAW_FISH, 1, EnumFish.COD.a()), 60), new PossibleFishingResult(new ItemStack(Items.RAW_FISH, 1, EnumFish.SALMON.a()), 25), new PossibleFishingResult(new ItemStack(Items.RAW_FISH, 1, EnumFish.CLOWNFISH.a()), 2), new PossibleFishingResult(new ItemStack(Items.RAW_FISH, 1, EnumFish.PUFFERFISH.a()), 13) });
/*  18 */   private int g = -1;
/*  19 */   private int h = -1;
/*  20 */   private int i = -1;
/*     */   private Block at;
/*     */   private boolean au;
/*     */   public int a;
/*     */   public EntityHuman owner;
/*     */   private int av;
/*     */   private int aw;
/*     */   private int ax;
/*     */   private int ay;
/*     */   private int az;
/*     */   private float aA;
/*     */   public Entity hooked;
/*     */   private int aB;
/*     */   private double aC;
/*     */   private double aD;
/*     */   private double aE;
/*     */   private double aF;
/*     */   private double aG;
/*     */   
/*     */   public EntityFishingHook(World world) {
/*  40 */     super(world);
/*  41 */     a(0.25F, 0.25F);
/*  42 */     this.ak = true;
/*     */   }
/*     */   
/*     */   public EntityFishingHook(World world, EntityHuman entityhuman) {
/*  46 */     super(world);
/*  47 */     this.ak = true;
/*  48 */     this.owner = entityhuman;
/*  49 */     this.owner.hookedFish = this;
/*  50 */     a(0.25F, 0.25F);
/*  51 */     setPositionRotation(entityhuman.locX, entityhuman.locY + 1.62D - entityhuman.height, entityhuman.locZ, entityhuman.yaw, entityhuman.pitch);
/*  52 */     this.locX -= (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  53 */     this.locY -= 0.10000000149011612D;
/*  54 */     this.locZ -= (MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  55 */     setPosition(this.locX, this.locY, this.locZ);
/*  56 */     this.height = 0.0F;
/*  57 */     float f = 0.4F;
/*     */     
/*  59 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  60 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  61 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f);
/*  62 */     c(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void c() {}
/*     */   
/*     */   public void c(double d0, double d1, double d2, float f, float f1) {
/*  68 */     float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  70 */     d0 /= f2;
/*  71 */     d1 /= f2;
/*  72 */     d2 /= f2;
/*  73 */     d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  74 */     d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  75 */     d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  76 */     d0 *= f;
/*  77 */     d1 *= f;
/*  78 */     d2 *= f;
/*  79 */     this.motX = d0;
/*  80 */     this.motY = d1;
/*  81 */     this.motZ = d2;
/*  82 */     float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*     */     
/*  84 */     this.lastYaw = this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
/*  85 */     this.lastPitch = this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
/*  86 */     this.av = 0;
/*     */   }
/*     */   
/*     */   public void h() {
/*  90 */     super.h();
/*  91 */     if (this.aB > 0) {
/*  92 */       double d0 = this.locX + (this.aC - this.locX) / this.aB;
/*  93 */       double d1 = this.locY + (this.aD - this.locY) / this.aB;
/*  94 */       double d2 = this.locZ + (this.aE - this.locZ) / this.aB;
/*  95 */       double d3 = MathHelper.g(this.aF - this.yaw);
/*     */       
/*  97 */       this.yaw = (float)(this.yaw + d3 / this.aB);
/*  98 */       this.pitch = (float)(this.pitch + (this.aG - this.pitch) / this.aB);
/*  99 */       this.aB--;
/* 100 */       setPosition(d0, d1, d2);
/* 101 */       b(this.yaw, this.pitch);
/*     */     } else {
/* 103 */       if (!this.world.isStatic) {
/* 104 */         ItemStack itemstack = this.owner.bF();
/*     */         
/* 106 */         if (this.owner.dead || !this.owner.isAlive() || itemstack == null || itemstack.getItem() != Items.FISHING_ROD || f(this.owner) > 1024.0D) {
/* 107 */           die();
/* 108 */           this.owner.hookedFish = null;
/*     */           
/*     */           return;
/*     */         } 
/* 112 */         if (this.hooked != null) {
/* 113 */           if (!this.hooked.dead) {
/* 114 */             this.locX = this.hooked.locX;
/* 115 */             this.locY = this.hooked.boundingBox.b + this.hooked.length * 0.8D;
/* 116 */             this.locZ = this.hooked.locZ;
/*     */             
/*     */             return;
/*     */           } 
/* 120 */           this.hooked = null;
/*     */         } 
/*     */       } 
/*     */       
/* 124 */       if (this.a > 0) {
/* 125 */         this.a--;
/*     */       }
/*     */       
/* 128 */       if (this.au) {
/* 129 */         if (this.world.getType(this.g, this.h, this.i) == this.at) {
/* 130 */           this.av++;
/* 131 */           if (this.av == 1200) {
/* 132 */             die();
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 138 */         this.au = false;
/* 139 */         this.motX *= (this.random.nextFloat() * 0.2F);
/* 140 */         this.motY *= (this.random.nextFloat() * 0.2F);
/* 141 */         this.motZ *= (this.random.nextFloat() * 0.2F);
/* 142 */         this.av = 0;
/* 143 */         this.aw = 0;
/*     */       } else {
/* 145 */         this.aw++;
/*     */       } 
/*     */       
/* 148 */       Vec3D vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
/* 149 */       Vec3D vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 150 */       MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);
/*     */       
/* 152 */       vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
/* 153 */       vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 154 */       if (movingobjectposition != null) {
/* 155 */         vec3d1 = Vec3D.a(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
/*     */       }
/*     */       
/* 158 */       Entity entity = null;
/* 159 */       List<Entity> list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 160 */       double d4 = 0.0D;
/*     */ 
/*     */ 
/*     */       
/* 164 */       for (int i = 0; i < list.size(); i++) {
/* 165 */         Entity entity1 = list.get(i);
/*     */         
/* 167 */         if (entity1.R() && (entity1 != this.owner || this.aw >= 5)) {
/* 168 */           float f = 0.3F;
/* 169 */           AxisAlignedBB axisalignedbb = entity1.boundingBox.grow(f, f, f);
/* 170 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */           
/* 172 */           if (movingobjectposition1 != null) {
/* 173 */             double d5 = vec3d.distanceSquared(movingobjectposition1.pos);
/* 174 */             if (d5 < d4 || d4 == 0.0D) {
/* 175 */               entity = entity1;
/* 176 */               d4 = d5;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 182 */       if (entity != null) {
/* 183 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 186 */       if (movingobjectposition != null) {
/* 187 */         CraftEventFactory.callProjectileHitEvent(this);
/* 188 */         if (movingobjectposition.entity != null) {
/* 189 */           if (movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.owner), 0.0F)) {
/* 190 */             this.hooked = movingobjectposition.entity;
/*     */           }
/*     */         } else {
/* 193 */           this.au = true;
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       if (!this.au) {
/* 198 */         move(this.motX, this.motY, this.motZ);
/* 199 */         float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */         
/* 201 */         this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */         
/* 203 */         for (this.pitch = (float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */         
/* 207 */         while (this.pitch - this.lastPitch >= 180.0F) {
/* 208 */           this.lastPitch += 360.0F;
/*     */         }
/*     */         
/* 211 */         while (this.yaw - this.lastYaw < -180.0F) {
/* 212 */           this.lastYaw -= 360.0F;
/*     */         }
/*     */         
/* 215 */         while (this.yaw - this.lastYaw >= 180.0F) {
/* 216 */           this.lastYaw += 360.0F;
/*     */         }
/*     */         
/* 219 */         this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 220 */         this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/* 221 */         float f2 = 0.92F;
/*     */         
/* 223 */         if (this.onGround || this.positionChanged) {
/* 224 */           f2 = 0.5F;
/*     */         }
/*     */         
/* 227 */         byte b0 = 5;
/* 228 */         double d6 = 0.0D;
/*     */         
/* 230 */         for (int j = 0; j < b0; j++) {
/* 231 */           double d7 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (j + 0) / b0 - 0.125D + 0.125D;
/* 232 */           double d8 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (j + 1) / b0 - 0.125D + 0.125D;
/* 233 */           AxisAlignedBB axisalignedbb1 = AxisAlignedBB.a(this.boundingBox.a, d7, this.boundingBox.c, this.boundingBox.d, d8, this.boundingBox.f);
/*     */           
/* 235 */           if (this.world.b(axisalignedbb1, Material.WATER)) {
/* 236 */             d6 += 1.0D / b0;
/*     */           }
/*     */         } 
/*     */         
/* 240 */         if (!this.world.isStatic && d6 > 0.0D) {
/* 241 */           WorldServer worldserver = (WorldServer)this.world;
/* 242 */           int k = 1;
/*     */           
/* 244 */           if (this.random.nextFloat() < 0.25F && this.world.isRainingAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY) + 1, MathHelper.floor(this.locZ))) {
/* 245 */             k = 2;
/*     */           }
/*     */           
/* 248 */           if (this.random.nextFloat() < 0.5F && !this.world.i(MathHelper.floor(this.locX), MathHelper.floor(this.locY) + 1, MathHelper.floor(this.locZ))) {
/* 249 */             k--;
/*     */           }
/*     */           
/* 252 */           if (this.ax > 0) {
/* 253 */             this.ax--;
/* 254 */             if (this.ax <= 0) {
/* 255 */               this.ay = 0;
/* 256 */               this.az = 0;
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 266 */           else if (this.az > 0) {
/* 267 */             this.az -= k;
/* 268 */             if (this.az <= 0) {
/* 269 */               this.motY -= 0.20000000298023224D;
/* 270 */               makeSound("random.splash", 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/* 271 */               float f4 = MathHelper.floor(this.boundingBox.b);
/* 272 */               worldserver.a("bubble", this.locX, (f4 + 1.0F), this.locZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D);
/* 273 */               worldserver.a("wake", this.locX, (f4 + 1.0F), this.locZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D);
/* 274 */               this.ax = MathHelper.nextInt(this.random, 10, 30);
/*     */             } else {
/* 276 */               this.aA = (float)(this.aA + this.random.nextGaussian() * 4.0D);
/* 277 */               float f4 = this.aA * 0.017453292F;
/* 278 */               float f5 = MathHelper.sin(f4);
/* 279 */               float f3 = MathHelper.cos(f4);
/* 280 */               double d9 = this.locX + (f5 * this.az * 0.1F);
/* 281 */               double d11 = (MathHelper.floor(this.boundingBox.b) + 1.0F);
/* 282 */               double d10 = this.locZ + (f3 * this.az * 0.1F);
/* 283 */               if (this.random.nextFloat() < 0.15F) {
/* 284 */                 worldserver.a("bubble", d9, d11 - 0.10000000149011612D, d10, 1, f5, 0.1D, f3, 0.0D);
/*     */               }
/*     */               
/* 287 */               float f6 = f5 * 0.04F;
/* 288 */               float f7 = f3 * 0.04F;
/*     */               
/* 290 */               worldserver.a("wake", d9, d11, d10, 0, f7, 0.01D, -f6, 1.0D);
/* 291 */               worldserver.a("wake", d9, d11, d10, 0, -f7, 0.01D, f6, 1.0D);
/*     */             } 
/* 293 */           } else if (this.ay > 0) {
/* 294 */             this.ay -= k;
/* 295 */             float f4 = 0.15F;
/* 296 */             if (this.ay < 20) {
/* 297 */               f4 = (float)(f4 + (20 - this.ay) * 0.05D);
/* 298 */             } else if (this.ay < 40) {
/* 299 */               f4 = (float)(f4 + (40 - this.ay) * 0.02D);
/* 300 */             } else if (this.ay < 60) {
/* 301 */               f4 = (float)(f4 + (60 - this.ay) * 0.01D);
/*     */             } 
/*     */             
/* 304 */             if (this.random.nextFloat() < f4) {
/* 305 */               float f5 = MathHelper.a(this.random, 0.0F, 360.0F) * 0.017453292F;
/* 306 */               float f3 = MathHelper.a(this.random, 25.0F, 60.0F);
/* 307 */               double d9 = this.locX + (MathHelper.sin(f5) * f3 * 0.1F);
/* 308 */               double d11 = (MathHelper.floor(this.boundingBox.b) + 1.0F);
/* 309 */               double d10 = this.locZ + (MathHelper.cos(f5) * f3 * 0.1F);
/* 310 */               worldserver.a("splash", d9, d11, d10, 2 + this.random.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
/*     */             } 
/*     */             
/* 313 */             if (this.ay <= 0) {
/* 314 */               this.aA = MathHelper.a(this.random, 0.0F, 360.0F);
/* 315 */               this.az = MathHelper.nextInt(this.random, 20, 80);
/*     */             } 
/*     */           } else {
/*     */             
/* 319 */             this.ay = MathHelper.nextInt(this.random, this.world.paperSpigotConfig.fishingMinTicks, this.world.paperSpigotConfig.fishingMaxTicks);
/* 320 */             this.ay -= EnchantmentManager.getLureEnchantmentLevel(this.owner) * 20 * 5;
/*     */           } 
/*     */ 
/*     */           
/* 324 */           if (this.ax > 0) {
/* 325 */             this.motY -= (this.random.nextFloat() * this.random.nextFloat() * this.random.nextFloat()) * 0.2D;
/*     */           }
/*     */         } 
/*     */         
/* 329 */         double d5 = d6 * 2.0D - 1.0D;
/* 330 */         this.motY += 0.03999999910593033D * d5;
/* 331 */         if (d6 > 0.0D) {
/* 332 */           f2 = (float)(f2 * 0.9D);
/* 333 */           this.motY *= 0.8D;
/*     */         } 
/*     */         
/* 336 */         this.motX *= f2;
/* 337 */         this.motY *= f2;
/* 338 */         this.motZ *= f2;
/* 339 */         setPosition(this.locX, this.locY, this.locZ);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 345 */     nbttagcompound.setShort("xTile", (short)this.g);
/* 346 */     nbttagcompound.setShort("yTile", (short)this.h);
/* 347 */     nbttagcompound.setShort("zTile", (short)this.i);
/* 348 */     nbttagcompound.setByte("inTile", (byte)Block.getId(this.at));
/* 349 */     nbttagcompound.setByte("shake", (byte)this.a);
/* 350 */     nbttagcompound.setByte("inGround", (byte)(this.au ? 1 : 0));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 354 */     this.g = nbttagcompound.getShort("xTile");
/* 355 */     this.h = nbttagcompound.getShort("yTile");
/* 356 */     this.i = nbttagcompound.getShort("zTile");
/* 357 */     this.at = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
/* 358 */     this.a = nbttagcompound.getByte("shake") & 0xFF;
/* 359 */     this.au = (nbttagcompound.getByte("inGround") == 1);
/*     */   }
/*     */   
/*     */   public int e() {
/* 363 */     if (this.world.isStatic) {
/* 364 */       return 0;
/*     */     }
/* 366 */     byte b0 = 0;
/*     */     
/* 368 */     if (this.hooked != null) {
/*     */       
/* 370 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), (Entity)this.hooked.getBukkitEntity(), (Fish)getBukkitEntity(), PlayerFishEvent.State.CAUGHT_ENTITY);
/* 371 */       this.world.getServer().getPluginManager().callEvent((Event)playerFishEvent);
/*     */       
/* 373 */       if (playerFishEvent.isCancelled()) {
/* 374 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 378 */       double d0 = this.owner.locX - this.locX;
/* 379 */       double d1 = this.owner.locY - this.locY;
/* 380 */       double d2 = this.owner.locZ - this.locZ;
/* 381 */       double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 382 */       double d4 = 0.1D;
/*     */       
/* 384 */       this.hooked.motX += d0 * d4;
/* 385 */       this.hooked.motY += d1 * d4 + MathHelper.sqrt(d3) * 0.08D;
/* 386 */       this.hooked.motZ += d2 * d4;
/* 387 */       b0 = 3;
/* 388 */     } else if (this.ax > 0) {
/* 389 */       EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY, this.locZ, f());
/*     */       
/* 391 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), (Entity)entityitem.getBukkitEntity(), (Fish)getBukkitEntity(), PlayerFishEvent.State.CAUGHT_FISH);
/* 392 */       playerFishEvent.setExpToDrop(this.random.nextInt(6) + 1);
/* 393 */       this.world.getServer().getPluginManager().callEvent((Event)playerFishEvent);
/*     */       
/* 395 */       if (playerFishEvent.isCancelled()) {
/* 396 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 400 */       double d5 = this.owner.locX - this.locX;
/* 401 */       double d6 = this.owner.locY - this.locY;
/* 402 */       double d7 = this.owner.locZ - this.locZ;
/* 403 */       double d8 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
/* 404 */       double d9 = 0.1D;
/*     */       
/* 406 */       entityitem.motX = d5 * d9;
/* 407 */       entityitem.motY = d6 * d9 + MathHelper.sqrt(d8) * 0.08D;
/* 408 */       entityitem.motZ = d7 * d9;
/* 409 */       this.world.addEntity(entityitem);
/*     */       
/* 411 */       this.owner.world.addEntity(new EntityExperienceOrb(this.owner.world, this.owner.locX, this.owner.locY + 0.5D, this.owner.locZ + 0.5D, playerFishEvent.getExpToDrop()));
/* 412 */       b0 = 1;
/*     */     } 
/*     */     
/* 415 */     if (this.au) {
/*     */       
/* 417 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, (Fish)getBukkitEntity(), PlayerFishEvent.State.IN_GROUND);
/* 418 */       this.world.getServer().getPluginManager().callEvent((Event)playerFishEvent);
/*     */       
/* 420 */       if (playerFishEvent.isCancelled()) {
/* 421 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 425 */       b0 = 2;
/*     */     } 
/*     */ 
/*     */     
/* 429 */     if (b0 == 0) {
/* 430 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, (Fish)getBukkitEntity(), PlayerFishEvent.State.FAILED_ATTEMPT);
/* 431 */       this.world.getServer().getPluginManager().callEvent((Event)playerFishEvent);
/* 432 */       if (playerFishEvent.isCancelled()) {
/* 433 */         return 0;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 438 */     die();
/* 439 */     this.owner.hookedFish = null;
/* 440 */     return b0;
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemStack f() {
/* 445 */     float f = this.world.random.nextFloat();
/* 446 */     int i = EnchantmentManager.getLuckEnchantmentLevel(this.owner);
/* 447 */     int j = EnchantmentManager.getLureEnchantmentLevel(this.owner);
/* 448 */     float f1 = 0.1F - i * 0.025F - j * 0.01F;
/* 449 */     float f2 = 0.05F + i * 0.01F - j * 0.01F;
/*     */     
/* 451 */     f1 = MathHelper.a(f1, 0.0F, 1.0F);
/* 452 */     f2 = MathHelper.a(f2, 0.0F, 1.0F);
/* 453 */     if (f < f1) {
/* 454 */       this.owner.a(StatisticList.A, 1);
/* 455 */       return ((PossibleFishingResult)WeightedRandom.a(this.random, d)).a(this.random);
/*     */     } 
/* 457 */     f -= f1;
/* 458 */     if (f < f2) {
/* 459 */       this.owner.a(StatisticList.B, 1);
/* 460 */       return ((PossibleFishingResult)WeightedRandom.a(this.random, e)).a(this.random);
/*     */     } 
/* 462 */     float f3 = f - f2;
/*     */     
/* 464 */     this.owner.a(StatisticList.z, 1);
/* 465 */     return ((PossibleFishingResult)WeightedRandom.a(this.random, EntityFishingHook.f)).a(this.random);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void die() {
/* 471 */     super.die();
/* 472 */     if (this.owner != null)
/* 473 */       this.owner.hookedFish = null; 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityFishingHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */