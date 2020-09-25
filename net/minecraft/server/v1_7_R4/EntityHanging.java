/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Hanging;
/*     */ import org.bukkit.entity.Painting;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.hanging.HangingBreakByEntityEvent;
/*     */ import org.bukkit.event.hanging.HangingBreakEvent;
/*     */ import org.bukkit.event.painting.PaintingBreakByEntityEvent;
/*     */ import org.bukkit.event.painting.PaintingBreakEvent;
/*     */ 
/*     */ public abstract class EntityHanging extends Entity {
/*     */   private int e;
/*     */   public int direction;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public EntityHanging(World world) {
/*  22 */     super(world);
/*  23 */     this.height = 0.0F;
/*  24 */     a(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityHanging(World world, int i, int j, int k, int l) {
/*  28 */     this(world);
/*  29 */     this.x = i;
/*  30 */     this.y = j;
/*  31 */     this.z = k;
/*     */   }
/*     */   
/*     */   protected void c() {}
/*     */   
/*     */   public void setDirection(int i) {
/*  37 */     this.direction = i;
/*  38 */     this.lastYaw = this.yaw = (i * 90);
/*  39 */     float f = f();
/*  40 */     float f1 = i();
/*  41 */     float f2 = f();
/*     */     
/*  43 */     if (i != 2 && i != 0) {
/*  44 */       f = 0.5F;
/*     */     } else {
/*  46 */       f2 = 0.5F;
/*  47 */       this.yaw = this.lastYaw = (Direction.f[i] * 90);
/*     */     } 
/*     */     
/*  50 */     f /= 32.0F;
/*  51 */     f1 /= 32.0F;
/*  52 */     f2 /= 32.0F;
/*  53 */     float f3 = this.x + 0.5F;
/*  54 */     float f4 = this.y + 0.5F;
/*  55 */     float f5 = this.z + 0.5F;
/*  56 */     float f6 = 0.5625F;
/*     */     
/*  58 */     if (i == 2) {
/*  59 */       f5 -= f6;
/*     */     }
/*     */     
/*  62 */     if (i == 1) {
/*  63 */       f3 -= f6;
/*     */     }
/*     */     
/*  66 */     if (i == 0) {
/*  67 */       f5 += f6;
/*     */     }
/*     */     
/*  70 */     if (i == 3) {
/*  71 */       f3 += f6;
/*     */     }
/*     */     
/*  74 */     if (i == 2) {
/*  75 */       f3 -= c(f());
/*     */     }
/*     */     
/*  78 */     if (i == 1) {
/*  79 */       f5 += c(f());
/*     */     }
/*     */     
/*  82 */     if (i == 0) {
/*  83 */       f3 += c(f());
/*     */     }
/*     */     
/*  86 */     if (i == 3) {
/*  87 */       f5 -= c(f());
/*     */     }
/*     */     
/*  90 */     f4 += c(i());
/*  91 */     setPosition(f3, f4, f5);
/*  92 */     float f7 = -0.03125F;
/*     */     
/*  94 */     this.boundingBox.b((f3 - f - f7), (f4 - f1 - f7), (f5 - f2 - f7), (f3 + f + f7), (f4 + f1 + f7), (f5 + f2 + f7));
/*     */   }
/*     */   
/*     */   private float c(int i) {
/*  98 */     return (i == 32) ? 0.5F : ((i == 64) ? 0.5F : 0.0F);
/*     */   }
/*     */   
/*     */   public void h() {
/* 102 */     this.lastX = this.locX;
/* 103 */     this.lastY = this.locY;
/* 104 */     this.lastZ = this.locZ;
/* 105 */     if (this.e++ == this.world.spigotConfig.hangingTickFrequency && !this.world.isStatic) {
/* 106 */       this.e = 0;
/* 107 */       if (!this.dead && !survives()) {
/*     */         HangingBreakEvent.RemoveCause cause;
/* 109 */         Material material = this.world.getType((int)this.locX, (int)this.locY, (int)this.locZ).getMaterial();
/*     */ 
/*     */         
/* 112 */         if (!material.equals(Material.AIR)) {
/*     */           
/* 114 */           cause = HangingBreakEvent.RemoveCause.OBSTRUCTION;
/*     */         } else {
/* 116 */           cause = HangingBreakEvent.RemoveCause.PHYSICS;
/*     */         } 
/*     */         
/* 119 */         HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), cause);
/* 120 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/* 122 */         PaintingBreakEvent paintingEvent = null;
/* 123 */         if (this instanceof EntityPainting) {
/*     */           
/* 125 */           paintingEvent = new PaintingBreakEvent((Painting)getBukkitEntity(), PaintingBreakEvent.RemoveCause.valueOf(cause.name()));
/* 126 */           paintingEvent.setCancelled(event.isCancelled());
/* 127 */           this.world.getServer().getPluginManager().callEvent((Event)paintingEvent);
/*     */         } 
/*     */         
/* 130 */         if (this.dead || event.isCancelled() || (paintingEvent != null && paintingEvent.isCancelled())) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 135 */         die();
/* 136 */         b((Entity)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public boolean survives() {
/*     */     Entity entity;
/* 142 */     if (!this.world.getCubes(this, this.boundingBox).isEmpty()) {
/* 143 */       return false;
/*     */     }
/* 145 */     int i = Math.max(1, f() / 16);
/* 146 */     int j = Math.max(1, i() / 16);
/* 147 */     int k = this.x;
/* 148 */     int l = this.y;
/* 149 */     int i1 = this.z;
/*     */     
/* 151 */     if (this.direction == 2) {
/* 152 */       k = MathHelper.floor(this.locX - (f() / 32.0F));
/*     */     }
/*     */     
/* 155 */     if (this.direction == 1) {
/* 156 */       i1 = MathHelper.floor(this.locZ - (f() / 32.0F));
/*     */     }
/*     */     
/* 159 */     if (this.direction == 0) {
/* 160 */       k = MathHelper.floor(this.locX - (f() / 32.0F));
/*     */     }
/*     */     
/* 163 */     if (this.direction == 3) {
/* 164 */       i1 = MathHelper.floor(this.locZ - (f() / 32.0F));
/*     */     }
/*     */     
/* 167 */     l = MathHelper.floor(this.locY - (i() / 32.0F));
/*     */     
/* 169 */     for (int j1 = 0; j1 < i; j1++) {
/* 170 */       for (int k1 = 0; k1 < j; k1++) {
/*     */         Material material;
/*     */         
/* 173 */         if (this.direction != 2 && this.direction != 0) {
/* 174 */           material = this.world.getType(this.x, l + k1, i1 + j1).getMaterial();
/*     */         } else {
/* 176 */           material = this.world.getType(k + j1, l + k1, this.z).getMaterial();
/*     */         } 
/*     */         
/* 179 */         if (!material.isBuildable()) {
/* 180 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     List list = this.world.getEntities(this, this.boundingBox);
/* 186 */     Iterator<Entity> iterator = list.iterator();
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 191 */       if (!iterator.hasNext()) {
/* 192 */         return true;
/*     */       }
/*     */       
/* 195 */       entity = iterator.next();
/* 196 */     } while (!(entity instanceof EntityHanging));
/*     */     
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean R() {
/* 203 */     return true;
/*     */   }
/*     */   
/*     */   public boolean j(Entity entity) {
/* 207 */     return (entity instanceof EntityHuman) ? damageEntity(DamageSource.playerAttack((EntityHuman)entity), 0.0F) : false;
/*     */   }
/*     */   
/*     */   public void i(int i) {
/* 211 */     this.world.X();
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 215 */     if (isInvulnerable()) {
/* 216 */       return false;
/*     */     }
/* 218 */     if (!this.dead && !this.world.isStatic) {
/*     */       PaintingBreakByEntityEvent paintingBreakByEntityEvent;
/* 220 */       HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.DEFAULT);
/* 221 */       PaintingBreakEvent paintingEvent = null;
/* 222 */       if (damagesource.getEntity() != null) {
/* 223 */         HangingBreakByEntityEvent hangingBreakByEntityEvent = new HangingBreakByEntityEvent((Hanging)getBukkitEntity(), (damagesource.getEntity() == null) ? null : (Entity)damagesource.getEntity().getBukkitEntity());
/*     */         
/* 225 */         if (this instanceof EntityPainting)
/*     */         {
/* 227 */           paintingBreakByEntityEvent = new PaintingBreakByEntityEvent((Painting)getBukkitEntity(), (damagesource.getEntity() == null) ? null : (Entity)damagesource.getEntity().getBukkitEntity());
/*     */         }
/* 229 */       } else if (damagesource.isExplosion()) {
/* 230 */         event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.EXPLOSION);
/*     */       } 
/*     */       
/* 233 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 235 */       if (paintingBreakByEntityEvent != null) {
/* 236 */         paintingBreakByEntityEvent.setCancelled(event.isCancelled());
/* 237 */         this.world.getServer().getPluginManager().callEvent((Event)paintingBreakByEntityEvent);
/*     */       } 
/*     */       
/* 240 */       if (this.dead || event.isCancelled() || (paintingBreakByEntityEvent != null && paintingBreakByEntityEvent.isCancelled())) {
/* 241 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 245 */       die();
/* 246 */       Q();
/* 247 */       b(damagesource.getEntity());
/*     */     } 
/*     */     
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(double d0, double d1, double d2) {
/* 255 */     if (!this.world.isStatic && !this.dead && d0 * d0 + d1 * d1 + d2 * d2 > 0.0D) {
/* 256 */       if (this.dead) {
/*     */         return;
/*     */       }
/*     */       
/* 260 */       HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.PHYSICS);
/* 261 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 263 */       if (this.dead || event.isCancelled()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 268 */       die();
/* 269 */       b((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void g(double d0, double d1, double d2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 281 */     nbttagcompound.setByte("Direction", (byte)this.direction);
/* 282 */     nbttagcompound.setInt("TileX", this.x);
/* 283 */     nbttagcompound.setInt("TileY", this.y);
/* 284 */     nbttagcompound.setInt("TileZ", this.z);
/* 285 */     switch (this.direction) {
/*     */       case 0:
/* 287 */         nbttagcompound.setByte("Dir", (byte)2);
/*     */         break;
/*     */       
/*     */       case 1:
/* 291 */         nbttagcompound.setByte("Dir", (byte)1);
/*     */         break;
/*     */       
/*     */       case 2:
/* 295 */         nbttagcompound.setByte("Dir", (byte)0);
/*     */         break;
/*     */       
/*     */       case 3:
/* 299 */         nbttagcompound.setByte("Dir", (byte)3);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 304 */     if (nbttagcompound.hasKeyOfType("Direction", 99)) {
/* 305 */       this.direction = nbttagcompound.getByte("Direction");
/*     */     } else {
/* 307 */       switch (nbttagcompound.getByte("Dir")) {
/*     */         case 0:
/* 309 */           this.direction = 2;
/*     */           break;
/*     */         
/*     */         case 1:
/* 313 */           this.direction = 1;
/*     */           break;
/*     */         
/*     */         case 2:
/* 317 */           this.direction = 0;
/*     */           break;
/*     */         
/*     */         case 3:
/* 321 */           this.direction = 3;
/*     */           break;
/*     */       } 
/*     */     } 
/* 325 */     this.x = nbttagcompound.getInt("TileX");
/* 326 */     this.y = nbttagcompound.getInt("TileY");
/* 327 */     this.z = nbttagcompound.getInt("TileZ");
/* 328 */     setDirection(this.direction);
/*     */   }
/*     */   
/*     */   public abstract int f();
/*     */   
/*     */   public abstract int i();
/*     */   
/*     */   public abstract void b(Entity paramEntity);
/*     */   
/*     */   protected boolean V() {
/* 338 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityHanging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */