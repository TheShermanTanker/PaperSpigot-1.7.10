/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityTeleportEvent;
/*     */ import org.spigotmc.ProtocolData;
/*     */ 
/*     */ public class EntityEnderman extends EntityMonster {
/*  14 */   private static final UUID bp = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
/*  15 */   private static final AttributeModifier bq = (new AttributeModifier(bp, "Attacking speed boost", 6.199999809265137D, 0)).a(false);
/*  16 */   private static boolean[] br = new boolean[256];
/*     */   private int bs;
/*     */   private int bt;
/*     */   private Entity bu;
/*     */   private boolean bv;
/*     */   
/*     */   public EntityEnderman(World world) {
/*  23 */     super(world);
/*  24 */     a(0.6F, 2.9F);
/*  25 */     this.W = 1.0F;
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  29 */     super.aD();
/*  30 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(40.0D);
/*  31 */     getAttributeInstance(GenericAttributes.d).setValue(0.30000001192092896D);
/*  32 */     getAttributeInstance(GenericAttributes.e).setValue(7.0D);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  36 */     super.c();
/*  37 */     this.datawatcher.a(16, new ProtocolData.ByteShort((short)0));
/*  38 */     this.datawatcher.a(17, new Byte((byte)0));
/*  39 */     this.datawatcher.a(18, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  43 */     super.b(nbttagcompound);
/*  44 */     nbttagcompound.setShort("carried", (short)Block.getId(getCarried()));
/*  45 */     nbttagcompound.setShort("carriedData", (short)getCarriedData());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  49 */     super.a(nbttagcompound);
/*  50 */     setCarried(Block.getById(nbttagcompound.getShort("carried")));
/*  51 */     setCarriedData(nbttagcompound.getShort("carriedData"));
/*     */   }
/*     */   
/*     */   protected Entity findTarget() {
/*  55 */     EntityHuman entityhuman = this.world.findNearbyVulnerablePlayer(this, 64.0D);
/*     */     
/*  57 */     if (entityhuman != null) {
/*  58 */       if (f(entityhuman)) {
/*  59 */         this.bv = true;
/*  60 */         if (this.bt == 0) {
/*  61 */           this.world.makeSound(entityhuman.locX, entityhuman.locY, entityhuman.locZ, "mob.endermen.stare", 1.0F, 1.0F);
/*     */         }
/*     */         
/*  64 */         if (this.bt++ == 5) {
/*  65 */           this.bt = 0;
/*  66 */           a(true);
/*  67 */           return entityhuman;
/*     */         } 
/*     */       } else {
/*  70 */         this.bt = 0;
/*     */       } 
/*     */     }
/*     */     
/*  74 */     return null;
/*     */   }
/*     */   
/*     */   private boolean f(EntityHuman entityhuman) {
/*  78 */     ItemStack itemstack = entityhuman.inventory.armor[3];
/*     */     
/*  80 */     if (itemstack != null && itemstack.getItem() == Item.getItemOf(Blocks.PUMPKIN)) {
/*  81 */       return false;
/*     */     }
/*  83 */     Vec3D vec3d = entityhuman.j(1.0F).a();
/*  84 */     Vec3D vec3d1 = Vec3D.a(this.locX - entityhuman.locX, this.boundingBox.b + (this.length / 2.0F) - entityhuman.locY + entityhuman.getHeadHeight(), this.locZ - entityhuman.locZ);
/*  85 */     double d0 = vec3d1.b();
/*     */     
/*  87 */     vec3d1 = vec3d1.a();
/*  88 */     double d1 = vec3d.b(vec3d1);
/*     */     
/*  90 */     return (d1 > 1.0D - 0.025D / d0 && entityhuman.hasLineOfSight(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void e() {
/*  95 */     if (L()) {
/*  96 */       damageEntity(DamageSource.DROWN, 1.0F);
/*     */     }
/*     */     
/*  99 */     if (this.bu != this.target) {
/* 100 */       AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
/*     */       
/* 102 */       attributeinstance.b(bq);
/* 103 */       if (this.target != null) {
/* 104 */         attributeinstance.a(bq);
/*     */       }
/*     */     } 
/*     */     
/* 108 */     this.bu = this.target;
/*     */ 
/*     */     
/* 111 */     if (!this.world.isStatic && this.world.getGameRules().getBoolean("mobGriefing"))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 116 */       if (getCarried().getMaterial() == Material.AIR) {
/* 117 */         if (this.random.nextInt(20) == 0) {
/* 118 */           int m = MathHelper.floor(this.locX - 2.0D + this.random.nextDouble() * 4.0D);
/* 119 */           int j = MathHelper.floor(this.locY + this.random.nextDouble() * 3.0D);
/* 120 */           int k = MathHelper.floor(this.locZ - 2.0D + this.random.nextDouble() * 4.0D);
/* 121 */           Block block = this.world.getType(m, j, k);
/* 122 */           if (br[Block.getId(block)])
/*     */           {
/* 124 */             if (!CraftEventFactory.callEntityChangeBlockEvent(this, this.world.getWorld().getBlockAt(m, j, k), Material.AIR).isCancelled()) {
/* 125 */               setCarried(block);
/* 126 */               setCarriedData(this.world.getData(m, j, k));
/* 127 */               this.world.setTypeUpdate(m, j, k, Blocks.AIR);
/*     */             }
/*     */           
/*     */           }
/*     */         } 
/* 132 */       } else if (this.random.nextInt(2000) == 0) {
/* 133 */         int m = MathHelper.floor(this.locX - 1.0D + this.random.nextDouble() * 2.0D);
/* 134 */         int j = MathHelper.floor(this.locY + this.random.nextDouble() * 2.0D);
/* 135 */         int k = MathHelper.floor(this.locZ - 1.0D + this.random.nextDouble() * 2.0D);
/* 136 */         Block block = this.world.getType(m, j, k);
/* 137 */         Block block1 = this.world.getType(m, j - 1, k);
/*     */         
/* 139 */         if (block.getMaterial() == Material.AIR && block1.getMaterial() != Material.AIR && block1.d())
/*     */         {
/* 141 */           if (!CraftEventFactory.callEntityChangeBlockEvent(this, m, j, k, getCarried(), getCarriedData()).isCancelled()) {
/* 142 */             this.world.setTypeAndData(m, j, k, getCarried(), getCarriedData(), 3);
/* 143 */             setCarried(Blocks.AIR);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 150 */     for (int i = 0; i < 2; i++) {
/* 151 */       this.world.addParticle("portal", this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length - 0.25D, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
/*     */     }
/*     */     
/* 154 */     if (this.world.w() && !this.world.isStatic) {
/* 155 */       float f = d(1.0F);
/*     */       
/* 157 */       if (f > 0.5F && this.world.i(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
/* 158 */         this.target = null;
/* 159 */         a(false);
/* 160 */         this.bv = false;
/* 161 */         bZ();
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     if (L() || isBurning()) {
/* 166 */       this.target = null;
/* 167 */       a(false);
/* 168 */       this.bv = false;
/* 169 */       bZ();
/*     */     } 
/*     */     
/* 172 */     if (cd() && !this.bv && this.random.nextInt(100) == 0) {
/* 173 */       a(false);
/*     */     }
/*     */     
/* 176 */     this.bc = false;
/* 177 */     if (this.target != null) {
/* 178 */       a(this.target, 100.0F, 100.0F);
/*     */     }
/*     */     
/* 181 */     if (!this.world.isStatic && isAlive()) {
/* 182 */       if (this.target != null) {
/* 183 */         if (this.target instanceof EntityHuman && f((EntityHuman)this.target)) {
/* 184 */           if (this.target.f(this) < 16.0D) {
/* 185 */             bZ();
/*     */           }
/*     */           
/* 188 */           this.bs = 0;
/* 189 */         } else if (this.target.f(this) > 256.0D && this.bs++ >= 30 && c(this.target)) {
/* 190 */           this.bs = 0;
/*     */         } 
/*     */       } else {
/* 193 */         a(false);
/* 194 */         this.bs = 0;
/*     */       } 
/*     */     }
/*     */     
/* 198 */     super.e();
/*     */   }
/*     */   
/*     */   protected boolean bZ() {
/* 202 */     double d0 = this.locX + (this.random.nextDouble() - 0.5D) * 64.0D;
/* 203 */     double d1 = this.locY + (this.random.nextInt(64) - 32);
/* 204 */     double d2 = this.locZ + (this.random.nextDouble() - 0.5D) * 64.0D;
/*     */     
/* 206 */     return k(d0, d1, d2);
/*     */   }
/*     */   
/*     */   protected boolean c(Entity entity) {
/* 210 */     Vec3D vec3d = Vec3D.a(this.locX - entity.locX, this.boundingBox.b + (this.length / 2.0F) - entity.locY + entity.getHeadHeight(), this.locZ - entity.locZ);
/*     */     
/* 212 */     vec3d = vec3d.a();
/* 213 */     double d0 = 16.0D;
/* 214 */     double d1 = this.locX + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.a * d0;
/* 215 */     double d2 = this.locY + (this.random.nextInt(16) - 8) - vec3d.b * d0;
/* 216 */     double d3 = this.locZ + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.c * d0;
/*     */     
/* 218 */     return k(d1, d2, d3);
/*     */   }
/*     */   
/*     */   protected boolean k(double d0, double d1, double d2) {
/* 222 */     double d3 = this.locX;
/* 223 */     double d4 = this.locY;
/* 224 */     double d5 = this.locZ;
/*     */     
/* 226 */     this.locX = d0;
/* 227 */     this.locY = d1;
/* 228 */     this.locZ = d2;
/* 229 */     boolean flag = false;
/* 230 */     int i = MathHelper.floor(this.locX);
/* 231 */     int j = MathHelper.floor(this.locY);
/* 232 */     int k = MathHelper.floor(this.locZ);
/*     */     
/* 234 */     if (this.world.isLoaded(i, j, k)) {
/* 235 */       boolean flag1 = false;
/*     */       
/* 237 */       while (!flag1 && j > 0) {
/* 238 */         Block block = this.world.getType(i, j - 1, k);
/*     */         
/* 240 */         if (block.getMaterial().isSolid()) {
/* 241 */           flag1 = true; continue;
/*     */         } 
/* 243 */         this.locY--;
/* 244 */         j--;
/*     */       } 
/*     */ 
/*     */       
/* 248 */       if (flag1) {
/*     */         
/* 250 */         EntityTeleportEvent teleport = new EntityTeleportEvent((Entity)getBukkitEntity(), new Location((World)this.world.getWorld(), d3, d4, d5), new Location((World)this.world.getWorld(), this.locX, this.locY, this.locZ));
/* 251 */         this.world.getServer().getPluginManager().callEvent((Event)teleport);
/* 252 */         if (teleport.isCancelled()) {
/* 253 */           return false;
/*     */         }
/*     */         
/* 256 */         Location to = teleport.getTo();
/* 257 */         setPosition(to.getX(), to.getY(), to.getZ());
/*     */ 
/*     */         
/* 260 */         if (this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox)) {
/* 261 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     if (!flag) {
/* 267 */       setPosition(d3, d4, d5);
/* 268 */       return false;
/*     */     } 
/* 270 */     short short1 = 128;
/*     */     
/* 272 */     for (int l = 0; l < short1; l++) {
/* 273 */       double d6 = l / (short1 - 1.0D);
/* 274 */       float f = (this.random.nextFloat() - 0.5F) * 0.2F;
/* 275 */       float f1 = (this.random.nextFloat() - 0.5F) * 0.2F;
/* 276 */       float f2 = (this.random.nextFloat() - 0.5F) * 0.2F;
/* 277 */       double d7 = d3 + (this.locX - d3) * d6 + (this.random.nextDouble() - 0.5D) * this.width * 2.0D;
/* 278 */       double d8 = d4 + (this.locY - d4) * d6 + this.random.nextDouble() * this.length;
/* 279 */       double d9 = d5 + (this.locZ - d5) * d6 + (this.random.nextDouble() - 0.5D) * this.width * 2.0D;
/*     */       
/* 281 */       this.world.addParticle("portal", d7, d8, d9, f, f1, f2);
/*     */     } 
/*     */     
/* 284 */     this.world.makeSound(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
/* 285 */     makeSound("mob.endermen.portal", 1.0F, 1.0F);
/* 286 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String t() {
/* 291 */     return cd() ? "mob.endermen.scream" : "mob.endermen.idle";
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 295 */     return "mob.endermen.hit";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 299 */     return "mob.endermen.death";
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 303 */     return Items.ENDER_PEARL;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 307 */     Item item = getLoot();
/*     */     
/* 309 */     if (item != null) {
/* 310 */       int j = this.random.nextInt(2 + i);
/*     */       
/* 312 */       for (int k = 0; k < j; k++) {
/* 313 */         a(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCarried(Block block) {
/* 319 */     this.datawatcher.watch(16, new ProtocolData.ByteShort((short)Block.getId(block)));
/*     */   }
/*     */   
/*     */   public Block getCarried() {
/* 323 */     return Block.getById(this.datawatcher.getShort(16));
/*     */   }
/*     */   
/*     */   public void setCarriedData(int i) {
/* 327 */     this.datawatcher.watch(17, Byte.valueOf((byte)(i & 0xFF)));
/*     */   }
/*     */   
/*     */   public int getCarriedData() {
/* 331 */     return this.datawatcher.getByte(17);
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 335 */     if (isInvulnerable()) {
/* 336 */       return false;
/*     */     }
/* 338 */     a(true);
/* 339 */     if (damagesource instanceof EntityDamageSource && damagesource.getEntity() instanceof EntityHuman) {
/* 340 */       this.bv = true;
/*     */     }
/*     */     
/* 343 */     if (damagesource instanceof EntityDamageSourceIndirect) {
/* 344 */       this.bv = false;
/*     */       
/* 346 */       for (int i = 0; i < 64; i++) {
/* 347 */         if (bZ()) {
/* 348 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 352 */       return false;
/*     */     } 
/* 354 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean cd() {
/* 360 */     return (this.datawatcher.getByte(18) > 0);
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 364 */     this.datawatcher.watch(18, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   static {
/* 368 */     br[Block.getId(Blocks.GRASS)] = true;
/* 369 */     br[Block.getId(Blocks.DIRT)] = true;
/* 370 */     br[Block.getId(Blocks.SAND)] = true;
/* 371 */     br[Block.getId(Blocks.GRAVEL)] = true;
/* 372 */     br[Block.getId(Blocks.YELLOW_FLOWER)] = true;
/* 373 */     br[Block.getId(Blocks.RED_ROSE)] = true;
/* 374 */     br[Block.getId(Blocks.BROWN_MUSHROOM)] = true;
/* 375 */     br[Block.getId(Blocks.RED_MUSHROOM)] = true;
/* 376 */     br[Block.getId(Blocks.TNT)] = true;
/* 377 */     br[Block.getId(Blocks.CACTUS)] = true;
/* 378 */     br[Block.getId(Blocks.CLAY)] = true;
/* 379 */     br[Block.getId(Blocks.PUMPKIN)] = true;
/* 380 */     br[Block.getId(Blocks.MELON)] = true;
/* 381 */     br[Block.getId(Blocks.MYCEL)] = true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityEnderman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */