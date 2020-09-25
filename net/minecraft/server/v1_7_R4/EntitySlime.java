/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Slime;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.entity.SlimeSplitEvent;
/*     */ 
/*     */ public class EntitySlime extends EntityInsentient implements IMonster {
/*     */   public float h;
/*     */   public float i;
/*     */   public float bm;
/*     */   private int jumpDelay;
/*     */   private Entity lastTarget;
/*     */   
/*     */   public EntitySlime(World world) {
/*  19 */     super(world);
/*  20 */     int i = 1 << this.random.nextInt(3);
/*     */     
/*  22 */     this.height = 0.0F;
/*  23 */     this.jumpDelay = this.random.nextInt(20) + 10;
/*  24 */     setSize(i);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  28 */     super.c();
/*  29 */     this.datawatcher.a(16, new Byte((byte)1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(int i) {
/*  34 */     this.datawatcher.watch(16, new Byte((byte)i));
/*  35 */     a(0.6F * i, 0.6F * i);
/*  36 */     setPosition(this.locX, this.locY, this.locZ);
/*  37 */     getAttributeInstance(GenericAttributes.maxHealth).setValue((i * i));
/*  38 */     setHealth(getMaxHealth());
/*  39 */     this.b = i;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  43 */     return this.datawatcher.getByte(16);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  47 */     super.b(nbttagcompound);
/*  48 */     nbttagcompound.setInt("Size", getSize() - 1);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  52 */     super.a(nbttagcompound);
/*  53 */     int i = nbttagcompound.getInt("Size");
/*     */     
/*  55 */     if (i < 0) {
/*  56 */       i = 0;
/*     */     }
/*     */     
/*  59 */     setSize(i + 1);
/*     */   }
/*     */   
/*     */   protected String bP() {
/*  63 */     return "slime";
/*     */   }
/*     */   
/*     */   protected String bV() {
/*  67 */     return "mob.slime." + ((getSize() > 1) ? "big" : "small");
/*     */   }
/*     */   
/*     */   public void h() {
/*  71 */     if (!this.world.isStatic && this.world.difficulty == EnumDifficulty.PEACEFUL && getSize() > 0) {
/*  72 */       this.dead = true;
/*     */     }
/*     */     
/*  75 */     this.i += (this.h - this.i) * 0.5F;
/*  76 */     this.bm = this.i;
/*  77 */     boolean flag = this.onGround;
/*     */     
/*  79 */     super.h();
/*     */ 
/*     */     
/*  82 */     if (this.onGround && !flag) {
/*  83 */       int i = getSize();
/*     */       
/*  85 */       for (int j = 0; j < i * 8; j++) {
/*  86 */         float f = this.random.nextFloat() * 3.1415927F * 2.0F;
/*  87 */         float f1 = this.random.nextFloat() * 0.5F + 0.5F;
/*  88 */         float f2 = MathHelper.sin(f) * i * 0.5F * f1;
/*  89 */         float f3 = MathHelper.cos(f) * i * 0.5F * f1;
/*     */         
/*  91 */         this.world.addParticle(bP(), this.locX + f2, this.boundingBox.b, this.locZ + f3, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */       
/*  94 */       if (bW()) {
/*  95 */         makeSound(bV(), bf(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/*     */       }
/*     */       
/*  98 */       this.h = -0.5F;
/*  99 */     } else if (!this.onGround && flag) {
/* 100 */       this.h = 1.0F;
/*     */     } 
/*     */     
/* 103 */     bS();
/* 104 */     if (this.world.isStatic) {
/* 105 */       int i = getSize();
/* 106 */       a(0.6F * i, 0.6F * i);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void bq() {
/* 111 */     w();
/*     */     
/* 113 */     Entity entityhuman = this.world.findNearbyVulnerablePlayer(this, 16.0D);
/* 114 */     EntityTargetEvent event = null;
/*     */     
/* 116 */     if (entityhuman != null && !entityhuman.equals(this.lastTarget)) {
/* 117 */       event = CraftEventFactory.callEntityTargetEvent(this, entityhuman, EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
/* 118 */     } else if (this.lastTarget != null && entityhuman == null) {
/* 119 */       event = CraftEventFactory.callEntityTargetEvent(this, entityhuman, EntityTargetEvent.TargetReason.FORGOT_TARGET);
/*     */     } 
/*     */     
/* 122 */     if (event != null && !event.isCancelled()) {
/* 123 */       entityhuman = (event.getTarget() == null) ? null : ((CraftEntity)event.getTarget()).getHandle();
/*     */     }
/*     */     
/* 126 */     this.lastTarget = entityhuman;
/*     */ 
/*     */     
/* 129 */     if (entityhuman != null) {
/* 130 */       a(entityhuman, 10.0F, 20.0F);
/*     */     }
/*     */     
/* 133 */     if (this.onGround && this.jumpDelay-- <= 0) {
/* 134 */       this.jumpDelay = bR();
/* 135 */       if (entityhuman != null) {
/* 136 */         this.jumpDelay /= 3;
/*     */       }
/*     */       
/* 139 */       this.bc = true;
/* 140 */       if (bY()) {
/* 141 */         makeSound(bV(), bf(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */       }
/*     */       
/* 144 */       this.bd = 1.0F - this.random.nextFloat() * 2.0F;
/* 145 */       this.be = (1 * getSize());
/*     */     } else {
/* 147 */       this.bc = false;
/* 148 */       if (this.onGround) {
/* 149 */         this.bd = this.be = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void bS() {
/* 155 */     this.h *= 0.6F;
/*     */   }
/*     */   
/*     */   protected int bR() {
/* 159 */     return this.random.nextInt(20) + 10;
/*     */   }
/*     */   
/*     */   protected EntitySlime bQ() {
/* 163 */     return new EntitySlime(this.world);
/*     */   }
/*     */   
/*     */   public void die() {
/* 167 */     int i = getSize();
/*     */     
/* 169 */     if (!this.world.isStatic && i > 1 && getHealth() <= 0.0F) {
/* 170 */       int j = 2 + this.random.nextInt(3);
/*     */ 
/*     */       
/* 173 */       SlimeSplitEvent event = new SlimeSplitEvent((Slime)getBukkitEntity(), j);
/* 174 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 176 */       if (!event.isCancelled() && event.getCount() > 0) {
/* 177 */         j = event.getCount();
/*     */       } else {
/* 179 */         super.die();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 184 */       for (int k = 0; k < j; k++) {
/* 185 */         float f = ((k % 2) - 0.5F) * i / 4.0F;
/* 186 */         float f1 = ((k / 2) - 0.5F) * i / 4.0F;
/* 187 */         EntitySlime entityslime = bQ();
/*     */         
/* 189 */         entityslime.setSize(i / 2);
/* 190 */         entityslime.setPositionRotation(this.locX + f, this.locY + 0.5D, this.locZ + f1, this.random.nextFloat() * 360.0F, 0.0F);
/* 191 */         this.world.addEntity(entityslime, CreatureSpawnEvent.SpawnReason.SLIME_SPLIT);
/*     */       } 
/*     */     } 
/*     */     
/* 195 */     super.die();
/*     */   }
/*     */   
/*     */   public void b_(EntityHuman entityhuman) {
/* 199 */     if (bT()) {
/* 200 */       int i = getSize();
/*     */       
/* 202 */       if (hasLineOfSight(entityhuman) && f(entityhuman) < 0.6D * i * 0.6D * i && entityhuman.damageEntity(DamageSource.mobAttack(this), bU())) {
/* 203 */         makeSound("mob.attack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean bT() {
/* 209 */     return (getSize() > 1);
/*     */   }
/*     */   
/*     */   protected int bU() {
/* 213 */     return getSize();
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 217 */     return "mob.slime." + ((getSize() > 1) ? "big" : "small");
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 221 */     return "mob.slime." + ((getSize() > 1) ? "big" : "small");
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 225 */     return (getSize() == 1) ? Items.SLIME_BALL : Item.getById(0);
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/* 229 */     Chunk chunk = this.world.getChunkAtWorldCoords(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
/*     */     
/* 231 */     if (this.world.getWorldData().getType() == WorldType.FLAT && this.random.nextInt(4) != 1) {
/* 232 */       return false;
/*     */     }
/* 234 */     if (getSize() == 1 || this.world.difficulty != EnumDifficulty.PEACEFUL) {
/* 235 */       BiomeBase biomebase = this.world.getBiome(MathHelper.floor(this.locX), MathHelper.floor(this.locZ));
/*     */       
/* 237 */       if (biomebase == BiomeBase.SWAMPLAND && this.locY > 50.0D && this.locY < 70.0D && this.random.nextFloat() < 0.5F && this.random.nextFloat() < this.world.y() && this.world.getLightLevel(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) <= this.random.nextInt(8)) {
/* 238 */         return super.canSpawn();
/*     */       }
/*     */       
/* 241 */       if (this.random.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && this.locY < 40.0D) {
/* 242 */         return super.canSpawn();
/*     */       }
/*     */     } 
/*     */     
/* 246 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float bf() {
/* 251 */     return 0.4F * getSize();
/*     */   }
/*     */   
/*     */   public int x() {
/* 255 */     return 0;
/*     */   }
/*     */   
/*     */   protected boolean bY() {
/* 259 */     return (getSize() > 0);
/*     */   }
/*     */   
/*     */   protected boolean bW() {
/* 263 */     return (getSize() > 2);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySlime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */