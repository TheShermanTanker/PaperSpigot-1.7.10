/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.projectiles.ProjectileSource;
/*     */ 
/*     */ public abstract class EntityProjectile extends Entity implements IProjectile {
/*   7 */   private int blockX = -1;
/*   8 */   private int blockY = -1;
/*   9 */   private int blockZ = -1;
/*     */   private Block inBlockId;
/*     */   protected boolean inGround;
/*     */   public int shake;
/*     */   public EntityLiving shooter;
/*     */   public String shooterName;
/*     */   private int i;
/*     */   private int at;
/*     */   
/*     */   public EntityProjectile(World world) {
/*  19 */     super(world);
/*  20 */     a(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */   protected void c() {}
/*     */   
/*     */   public EntityProjectile(World world, EntityLiving entityliving) {
/*  26 */     super(world);
/*  27 */     this.shooter = entityliving;
/*  28 */     this.projectileSource = (ProjectileSource)entityliving.getBukkitEntity();
/*  29 */     a(0.25F, 0.25F);
/*  30 */     setPositionRotation(entityliving.locX, entityliving.locY + entityliving.getHeadHeight(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  31 */     this.locX -= (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  32 */     this.locY -= 0.10000000149011612D;
/*  33 */     this.locZ -= (MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
/*  34 */     setPosition(this.locX, this.locY, this.locZ);
/*  35 */     this.height = 0.0F;
/*  36 */     float f = 0.4F;
/*     */     
/*  38 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  39 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  40 */     this.motY = (-MathHelper.sin((this.pitch + f()) / 180.0F * 3.1415927F) * f);
/*  41 */     shoot(this.motX, this.motY, this.motZ, e(), 1.0F);
/*     */   }
/*     */   
/*     */   public EntityProjectile(World world, double d0, double d1, double d2) {
/*  45 */     super(world);
/*  46 */     this.i = 0;
/*  47 */     a(0.25F, 0.25F);
/*  48 */     setPosition(d0, d1, d2);
/*  49 */     this.height = 0.0F;
/*     */   }
/*     */   
/*     */   protected float e() {
/*  53 */     return 1.5F;
/*     */   }
/*     */   
/*     */   protected float f() {
/*  57 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void shoot(double d0, double d1, double d2, float f, float f1) {
/*  61 */     float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  63 */     d0 /= f2;
/*  64 */     d1 /= f2;
/*  65 */     d2 /= f2;
/*  66 */     d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  67 */     d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  68 */     d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  69 */     d0 *= f;
/*  70 */     d1 *= f;
/*  71 */     d2 *= f;
/*  72 */     this.motX = d0;
/*  73 */     this.motY = d1;
/*  74 */     this.motZ = d2;
/*  75 */     float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*     */     
/*  77 */     this.lastYaw = this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
/*  78 */     this.lastPitch = this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
/*  79 */     this.i = 0;
/*     */   }
/*     */   
/*     */   public void h() {
/*  83 */     this.S = this.locX;
/*  84 */     this.T = this.locY;
/*  85 */     this.U = this.locZ;
/*  86 */     super.h();
/*  87 */     if (this.shake > 0) {
/*  88 */       this.shake--;
/*     */     }
/*     */     
/*  91 */     if (this.inGround) {
/*  92 */       if (this.world.getType(this.blockX, this.blockY, this.blockZ) == this.inBlockId) {
/*  93 */         this.i++;
/*  94 */         if (this.i == 1200) {
/*  95 */           die();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 101 */       this.inGround = false;
/* 102 */       this.motX *= (this.random.nextFloat() * 0.2F);
/* 103 */       this.motY *= (this.random.nextFloat() * 0.2F);
/* 104 */       this.motZ *= (this.random.nextFloat() * 0.2F);
/* 105 */       this.i = 0;
/* 106 */       this.at = 0;
/*     */     } else {
/* 108 */       this.at++;
/*     */     } 
/*     */     
/* 111 */     Vec3D vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
/* 112 */     Vec3D vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 113 */     MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);
/*     */     
/* 115 */     vec3d = Vec3D.a(this.locX, this.locY, this.locZ);
/* 116 */     vec3d1 = Vec3D.a(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 117 */     if (movingobjectposition != null) {
/* 118 */       vec3d1 = Vec3D.a(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
/*     */     }
/*     */     
/* 121 */     if (!this.world.isStatic) {
/* 122 */       Entity entity = null;
/* 123 */       List<Entity> list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 124 */       double d0 = 0.0D;
/* 125 */       EntityLiving entityliving = getShooter();
/*     */       
/* 127 */       for (int i = 0; i < list.size(); i++) {
/* 128 */         Entity entity1 = list.get(i);
/*     */         
/* 130 */         if (entity1.R() && (entity1 != entityliving || this.at >= 5)) {
/* 131 */           float f = 0.3F;
/* 132 */           AxisAlignedBB axisalignedbb = entity1.boundingBox.grow(f, f, f);
/* 133 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */           
/* 135 */           if (movingobjectposition1 != null) {
/* 136 */             double d1 = vec3d.distanceSquared(movingobjectposition1.pos);
/*     */             
/* 138 */             if (d1 < d0 || d0 == 0.0D) {
/* 139 */               entity = entity1;
/* 140 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 146 */       if (entity != null) {
/* 147 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 152 */     if (movingobjectposition != null && movingobjectposition.entity instanceof EntityPlayer && this.shooter != null && this.shooter instanceof EntityPlayer && 
/* 153 */       !((EntityPlayer)this.shooter).getBukkitEntity().canSee((Player)((EntityPlayer)movingobjectposition.entity).getBukkitEntity())) {
/* 154 */       movingobjectposition = null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (movingobjectposition != null) {
/* 160 */       if (movingobjectposition.type == EnumMovingObjectType.BLOCK && this.world.getType(movingobjectposition.b, movingobjectposition.c, movingobjectposition.d) == Blocks.PORTAL) {
/* 161 */         ah();
/*     */       } else {
/* 163 */         a(movingobjectposition);
/*     */         
/* 165 */         if (this.dead) {
/* 166 */           CraftEventFactory.callProjectileHitEvent(this);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 172 */     this.locX += this.motX;
/* 173 */     this.locY += this.motY;
/* 174 */     this.locZ += this.motZ;
/* 175 */     float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/* 177 */     this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */     
/* 179 */     for (this.pitch = (float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 183 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 184 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/* 187 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 188 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */     
/* 191 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 192 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/* 195 */     this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 196 */     this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/* 197 */     float f2 = 0.99F;
/* 198 */     float f3 = i();
/*     */     
/* 200 */     if (M()) {
/* 201 */       for (int j = 0; j < 4; j++) {
/* 202 */         float f4 = 0.25F;
/*     */         
/* 204 */         this.world.addParticle("bubble", this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ);
/*     */       } 
/*     */       
/* 207 */       f2 = 0.8F;
/*     */     } 
/*     */     
/* 210 */     this.motX *= f2;
/* 211 */     this.motY *= f2;
/* 212 */     this.motZ *= f2;
/* 213 */     this.motY -= f3;
/* 214 */     setPosition(this.locX, this.locY, this.locZ);
/*     */   }
/*     */   
/*     */   protected float i() {
/* 218 */     return 0.03F;
/*     */   }
/*     */   
/*     */   protected abstract void a(MovingObjectPosition paramMovingObjectPosition);
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 224 */     nbttagcompound.setShort("xTile", (short)this.blockX);
/* 225 */     nbttagcompound.setShort("yTile", (short)this.blockY);
/* 226 */     nbttagcompound.setShort("zTile", (short)this.blockZ);
/* 227 */     nbttagcompound.setByte("inTile", (byte)Block.getId(this.inBlockId));
/* 228 */     nbttagcompound.setByte("shake", (byte)this.shake);
/* 229 */     nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 230 */     if ((this.shooterName == null || this.shooterName.length() == 0) && this.shooter != null && this.shooter instanceof EntityHuman) {
/* 231 */       this.shooterName = this.shooter.getName();
/*     */     }
/*     */     
/* 234 */     nbttagcompound.setString("ownerName", (this.shooterName == null) ? "" : this.shooterName);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 238 */     this.blockX = nbttagcompound.getShort("xTile");
/* 239 */     this.blockY = nbttagcompound.getShort("yTile");
/* 240 */     this.blockZ = nbttagcompound.getShort("zTile");
/* 241 */     this.inBlockId = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
/* 242 */     this.shake = nbttagcompound.getByte("shake") & 0xFF;
/* 243 */     this.inGround = (nbttagcompound.getByte("inGround") == 1);
/* 244 */     this.shooterName = nbttagcompound.getString("ownerName");
/* 245 */     if (this.shooterName != null && this.shooterName.length() == 0) {
/* 246 */       this.shooterName = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityLiving getShooter() {
/* 251 */     if (this.shooter == null && this.shooterName != null && this.shooterName.length() > 0) {
/* 252 */       this.shooter = this.world.a(this.shooterName);
/*     */     }
/*     */     
/* 255 */     return this.shooter;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityProjectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */