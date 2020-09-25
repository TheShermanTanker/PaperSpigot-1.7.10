/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class EntityExperienceOrb
/*     */   extends Entity
/*     */ {
/*     */   public int a;
/*     */   public int b;
/*     */   public int c;
/*  13 */   private int d = 5;
/*     */   public int value;
/*     */   private EntityHuman targetPlayer;
/*     */   private int targetTime;
/*     */   
/*     */   public EntityExperienceOrb(World world, double d0, double d1, double d2, int i) {
/*  19 */     super(world);
/*  20 */     a(0.5F, 0.5F);
/*  21 */     this.height = this.length / 2.0F;
/*  22 */     setPosition(d0, d1, d2);
/*  23 */     this.yaw = (float)(Math.random() * 360.0D);
/*  24 */     this.motX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
/*  25 */     this.motY = ((float)(Math.random() * 0.2D) * 2.0F);
/*  26 */     this.motZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
/*  27 */     this.value = i;
/*     */   }
/*     */   
/*     */   protected boolean g_() {
/*  31 */     return false;
/*     */   }
/*     */   
/*     */   public EntityExperienceOrb(World world) {
/*  35 */     super(world);
/*  36 */     a(0.25F, 0.25F);
/*  37 */     this.height = this.length / 2.0F;
/*     */   }
/*     */   
/*     */   protected void c() {}
/*     */   
/*     */   public void h() {
/*  43 */     super.h();
/*  44 */     if (this.c > 0) {
/*  45 */       this.c--;
/*     */     }
/*     */     
/*  48 */     this.lastX = this.locX;
/*  49 */     this.lastY = this.locY;
/*  50 */     this.lastZ = this.locZ;
/*  51 */     this.motY -= 0.029999999329447746D;
/*  52 */     if (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)).getMaterial() == Material.LAVA) {
/*  53 */       this.motY = 0.20000000298023224D;
/*  54 */       this.motX = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  55 */       this.motZ = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  56 */       makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
/*     */     } 
/*     */     
/*  59 */     j(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
/*  60 */     double d0 = 8.0D;
/*     */     
/*  62 */     if (this.targetTime < this.a - 20 + getId() % 100) {
/*  63 */       if (this.targetPlayer == null || this.targetPlayer.f(this) > d0 * d0) {
/*  64 */         this.targetPlayer = this.world.findNearbyPlayer(this, d0);
/*     */       }
/*     */       
/*  67 */       this.targetTime = this.a;
/*     */     } 
/*     */     
/*  70 */     if (this.targetPlayer != null) {
/*     */       
/*  72 */       EntityTargetEvent event = CraftEventFactory.callEntityTargetEvent(this, this.targetPlayer, EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
/*  73 */       Entity target = (event.getTarget() == null) ? null : ((CraftEntity)event.getTarget()).getHandle();
/*     */       
/*  75 */       if (!event.isCancelled() && target != null) {
/*  76 */         double d1 = (target.locX - this.locX) / d0;
/*  77 */         double d2 = (target.locY + target.getHeadHeight() - this.locY) / d0;
/*  78 */         double d3 = (target.locZ - this.locZ) / d0;
/*  79 */         double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*  80 */         double d5 = 1.0D - d4;
/*  81 */         if (d5 > 0.0D) {
/*  82 */           d5 *= d5;
/*  83 */           this.motX += d1 / d4 * d5 * 0.1D;
/*  84 */           this.motY += d2 / d4 * d5 * 0.1D;
/*  85 */           this.motZ += d3 / d4 * d5 * 0.1D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  91 */     move(this.motX, this.motY, this.motZ);
/*  92 */     float f = 0.98F;
/*     */     
/*  94 */     if (this.onGround) {
/*  95 */       f = (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ))).frictionFactor * 0.98F;
/*     */     }
/*     */     
/*  98 */     this.motX *= f;
/*  99 */     this.motY *= 0.9800000190734863D;
/* 100 */     this.motZ *= f;
/* 101 */     if (this.onGround) {
/* 102 */       this.motY *= -0.8999999761581421D;
/*     */     }
/*     */     
/* 105 */     this.a++;
/* 106 */     this.b++;
/* 107 */     if (this.b >= 6000) {
/* 108 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean N() {
/* 113 */     return this.world.a(this.boundingBox, Material.WATER, this);
/*     */   }
/*     */   
/*     */   protected void burn(int i) {
/* 117 */     damageEntity(DamageSource.FIRE, i);
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 121 */     if (isInvulnerable()) {
/* 122 */       return false;
/*     */     }
/* 124 */     Q();
/* 125 */     this.d = (int)(this.d - f);
/* 126 */     if (this.d <= 0) {
/* 127 */       die();
/*     */     }
/*     */     
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 135 */     nbttagcompound.setShort("Health", (short)(byte)this.d);
/* 136 */     nbttagcompound.setShort("Age", (short)this.b);
/* 137 */     nbttagcompound.setShort("Value", (short)this.value);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 141 */     this.d = nbttagcompound.getShort("Health") & 0xFF;
/* 142 */     this.b = nbttagcompound.getShort("Age");
/* 143 */     this.value = nbttagcompound.getShort("Value");
/*     */   }
/*     */   
/*     */   public void b_(EntityHuman entityhuman) {
/* 147 */     if (!this.world.isStatic && 
/* 148 */       this.c == 0 && entityhuman.bt == 0) {
/* 149 */       entityhuman.bt = 2;
/* 150 */       this.world.makeSound(entityhuman, "random.orb", 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
/* 151 */       entityhuman.receive(this, 1);
/* 152 */       entityhuman.giveExp(CraftEventFactory.callPlayerExpChangeEvent(entityhuman, this.value).getAmount());
/* 153 */       die();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int e() {
/* 159 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getOrbValue(int i) {
/* 164 */     if (i > 162670129) return i - 100000; 
/* 165 */     if (i > 81335063) return 81335063; 
/* 166 */     if (i > 40667527) return 40667527; 
/* 167 */     if (i > 20333759) return 20333759; 
/* 168 */     if (i > 10166857) return 10166857; 
/* 169 */     if (i > 5083423) return 5083423; 
/* 170 */     if (i > 2541701) return 2541701; 
/* 171 */     if (i > 1270849) return 1270849; 
/* 172 */     if (i > 635413) return 635413; 
/* 173 */     if (i > 317701) return 317701; 
/* 174 */     if (i > 158849) return 158849; 
/* 175 */     if (i > 79423) return 79423; 
/* 176 */     if (i > 39709) return 39709; 
/* 177 */     if (i > 19853) return 19853; 
/* 178 */     if (i > 9923) return 9923; 
/* 179 */     if (i > 4957) return 4957;
/*     */ 
/*     */     
/* 182 */     return (i >= 2477) ? 2477 : ((i >= 1237) ? 1237 : ((i >= 617) ? 617 : ((i >= 307) ? 307 : ((i >= 149) ? 149 : ((i >= 73) ? 73 : ((i >= 37) ? 37 : ((i >= 17) ? 17 : ((i >= 7) ? 7 : ((i >= 3) ? 3 : 1)))))))));
/*     */   }
/*     */   
/*     */   public boolean av() {
/* 186 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityExperienceOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */