/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityFireworks
/*     */   extends Entity
/*     */ {
/*     */   private int ticksFlown;
/*     */   public int expectedLifespan;
/*     */   
/*     */   public void inactiveTick() {
/*  12 */     this.ticksFlown += 19;
/*  13 */     super.inactiveTick();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFireworks(World world) {
/*  18 */     super(world);
/*  19 */     a(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  23 */     this.datawatcher.add(8, 5);
/*     */   }
/*     */   
/*     */   public EntityFireworks(World world, double d0, double d1, double d2, ItemStack itemstack) {
/*  27 */     super(world);
/*  28 */     this.ticksFlown = 0;
/*  29 */     a(0.25F, 0.25F);
/*  30 */     setPosition(d0, d1, d2);
/*  31 */     this.height = 0.0F;
/*  32 */     int i = 1;
/*     */     
/*  34 */     if (itemstack != null && itemstack.hasTag()) {
/*  35 */       this.datawatcher.watch(8, itemstack);
/*  36 */       NBTTagCompound nbttagcompound = itemstack.getTag();
/*  37 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Fireworks");
/*     */       
/*  39 */       if (nbttagcompound1 != null) {
/*  40 */         i += nbttagcompound1.getByte("Flight");
/*     */       }
/*     */     } 
/*     */     
/*  44 */     this.motX = this.random.nextGaussian() * 0.001D;
/*  45 */     this.motZ = this.random.nextGaussian() * 0.001D;
/*  46 */     this.motY = 0.05D;
/*  47 */     this.expectedLifespan = 10 * i + this.random.nextInt(6) + this.random.nextInt(7);
/*     */   }
/*     */   
/*     */   public void h() {
/*  51 */     this.S = this.locX;
/*  52 */     this.T = this.locY;
/*  53 */     this.U = this.locZ;
/*  54 */     super.h();
/*  55 */     this.motX *= 1.15D;
/*  56 */     this.motZ *= 1.15D;
/*  57 */     this.motY += 0.04D;
/*  58 */     move(this.motX, this.motY, this.motZ);
/*  59 */     float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/*  61 */     this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*     */     
/*  63 */     for (this.pitch = (float)(Math.atan2(this.motY, f) * 180.0D / 3.1415927410125732D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/*  67 */     while (this.pitch - this.lastPitch >= 180.0F) {
/*  68 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/*  71 */     while (this.yaw - this.lastYaw < -180.0F) {
/*  72 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */     
/*  75 */     while (this.yaw - this.lastYaw >= 180.0F) {
/*  76 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/*  79 */     this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/*  80 */     this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/*  81 */     if (this.ticksFlown == 0) {
/*  82 */       this.world.makeSound(this, "fireworks.launch", 3.0F, 1.0F);
/*     */     }
/*     */     
/*  85 */     this.ticksFlown++;
/*  86 */     if (this.world.isStatic && this.ticksFlown % 2 < 2) {
/*  87 */       this.world.addParticle("fireworksSpark", this.locX, this.locY - 0.3D, this.locZ, this.random.nextGaussian() * 0.05D, -this.motY * 0.5D, this.random.nextGaussian() * 0.05D);
/*     */     }
/*     */     
/*  90 */     if (!this.world.isStatic && this.ticksFlown > this.expectedLifespan) {
/*  91 */       this.world.broadcastEntityEffect(this, (byte)17);
/*  92 */       die();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  97 */     nbttagcompound.setInt("Life", this.ticksFlown);
/*  98 */     nbttagcompound.setInt("LifeTime", this.expectedLifespan);
/*  99 */     ItemStack itemstack = this.datawatcher.getItemStack(8);
/*     */     
/* 101 */     if (itemstack != null) {
/* 102 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/* 104 */       itemstack.save(nbttagcompound1);
/* 105 */       nbttagcompound.set("FireworksItem", nbttagcompound1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 110 */     this.ticksFlown = nbttagcompound.getInt("Life");
/* 111 */     this.expectedLifespan = nbttagcompound.getInt("LifeTime");
/* 112 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("FireworksItem");
/*     */     
/* 114 */     if (nbttagcompound1 != null) {
/* 115 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound1);
/*     */       
/* 117 */       if (itemstack != null) {
/* 118 */         this.datawatcher.watch(8, itemstack);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public float d(float f) {
/* 124 */     return super.d(f);
/*     */   }
/*     */   
/*     */   public boolean au() {
/* 128 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityFireworks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */