/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ 
/*     */ public class Explosion {
/*     */   public boolean a;
/*     */   public boolean b = true;
/*  21 */   private int i = 16;
/*  22 */   private Random j = new Random();
/*     */   private World world;
/*     */   public double posX;
/*     */   public double posY;
/*     */   public double posZ;
/*     */   public Entity source;
/*     */   public float size;
/*  29 */   public List blocks = new ArrayList();
/*  30 */   private Map l = new HashMap<Object, Object>();
/*     */   public boolean wasCanceled = false;
/*     */   
/*     */   public Explosion(World world, Entity entity, double d0, double d1, double d2, float f) {
/*  34 */     this.world = world;
/*  35 */     this.source = entity;
/*  36 */     this.size = (float)Math.max(f, 0.0D);
/*  37 */     this.posX = d0;
/*  38 */     this.posY = d1;
/*  39 */     this.posZ = d2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/*  44 */     if (this.size < 0.1F) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  49 */     float f = this.size;
/*  50 */     HashSet<ChunkPosition> hashset = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */ 
/*     */     
/*  59 */     for (i = 0; i < this.i; i++) {
/*  60 */       for (int m = 0; m < this.i; m++) {
/*  61 */         for (int n = 0; n < this.i; n++) {
/*  62 */           if (i == 0 || i == this.i - 1 || m == 0 || m == this.i - 1 || n == 0 || n == this.i - 1) {
/*  63 */             double d3 = (i / (this.i - 1.0F) * 2.0F - 1.0F);
/*  64 */             double d4 = (m / (this.i - 1.0F) * 2.0F - 1.0F);
/*  65 */             double d5 = (n / (this.i - 1.0F) * 2.0F - 1.0F);
/*  66 */             double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */             
/*  68 */             d3 /= d6;
/*  69 */             d4 /= d6;
/*  70 */             d5 /= d6;
/*  71 */             float f1 = this.size * (0.7F + this.world.random.nextFloat() * 0.6F);
/*     */             
/*  73 */             double d0 = this.posX;
/*  74 */             double d1 = this.posY;
/*  75 */             double d2 = this.posZ;
/*     */             
/*  77 */             for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
/*  78 */               int l = MathHelper.floor(d0);
/*  79 */               int i1 = MathHelper.floor(d1);
/*  80 */               int j1 = MathHelper.floor(d2);
/*  81 */               Block block = this.world.getType(l, i1, j1);
/*     */               
/*  83 */               if (block.getMaterial() != Material.AIR) {
/*  84 */                 float f3 = (this.source != null) ? this.source.a(this, this.world, l, i1, j1, block) : block.a(this.source);
/*     */                 
/*  86 */                 f1 -= (f3 + 0.3F) * f2;
/*     */               } 
/*     */               
/*  89 */               if (f1 > 0.0F && (this.source == null || this.source.a(this, this.world, l, i1, j1, block, f1)) && i1 < 256 && i1 >= 0) {
/*  90 */                 hashset.add(new ChunkPosition(l, i1, j1));
/*     */               }
/*     */               
/*  93 */               d0 += d3 * f2;
/*  94 */               d1 += d4 * f2;
/*  95 */               d2 += d5 * f2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     this.blocks.addAll(hashset);
/* 103 */     this.size *= 2.0F;
/* 104 */     i = MathHelper.floor(this.posX - this.size - 1.0D);
/* 105 */     int j = MathHelper.floor(this.posX + this.size + 1.0D);
/* 106 */     int k = MathHelper.floor(this.posY - this.size - 1.0D);
/* 107 */     int k1 = MathHelper.floor(this.posY + this.size + 1.0D);
/* 108 */     int l1 = MathHelper.floor(this.posZ - this.size - 1.0D);
/* 109 */     int i2 = MathHelper.floor(this.posZ + this.size + 1.0D);
/*     */     
/* 111 */     List<Entity> list = this.world.getEntities(this.source, AxisAlignedBB.a(i, k, l1, j, k1, i2), new IEntitySelector()
/*     */         {
/*     */           public boolean a(Entity entity) {
/* 114 */             return !entity.dead;
/*     */           }
/*     */         });
/*     */     
/* 118 */     Vec3D vec3d = Vec3D.a(this.posX, this.posY, this.posZ);
/*     */     
/* 120 */     for (int j2 = 0; j2 < list.size(); j2++) {
/* 121 */       Entity entity = list.get(j2);
/* 122 */       double d7 = entity.f(this.posX, this.posY, this.posZ) / this.size;
/*     */       
/* 124 */       if (d7 <= 1.0D) {
/* 125 */         double d0 = entity.locX - this.posX;
/* 126 */         double d1 = entity.locY + entity.getHeadHeight() - this.posY;
/* 127 */         double d2 = entity.locZ - this.posZ;
/* 128 */         double d8 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */         
/* 130 */         if (d8 != 0.0D) {
/* 131 */           d0 /= d8;
/* 132 */           d1 /= d8;
/* 133 */           d2 /= d8;
/* 134 */           double d9 = getBlockDensity(vec3d, entity.boundingBox);
/* 135 */           double d10 = (1.0D - d7) * d9;
/*     */ 
/*     */           
/* 138 */           CraftEventFactory.entityDamage = this.source;
/* 139 */           if (!entity.damageEntity(DamageSource.explosion(this), (int)((d10 * d10 + d10) / 2.0D * 8.0D * this.size + 1.0D)));
/*     */ 
/*     */           
/* 142 */           CraftEventFactory.entityDamage = null;
/*     */           
/* 144 */           double d11 = (entity instanceof EntityHuman && this.world.paperSpigotConfig.disableExplosionKnockback) ? 0.0D : EnchantmentProtection.a(entity, d10);
/*     */           
/* 146 */           entity.motX += d0 * d11;
/* 147 */           entity.motY += d1 * d11;
/* 148 */           entity.motZ += d2 * d11;
/* 149 */           if (entity instanceof EntityHuman && !this.world.paperSpigotConfig.disableExplosionKnockback) {
/* 150 */             this.l.put((EntityHuman)entity, Vec3D.a(d0 * d10, d1 * d10, d2 * d10));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     this.size = f;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(boolean flag) {
/* 161 */     float volume = (this.source instanceof EntityTNTPrimed) ? this.world.paperSpigotConfig.tntExplosionVolume : 4.0F;
/* 162 */     this.world.makeSound(this.posX, this.posY, this.posZ, "random.explode", volume, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
/*     */     
/* 164 */     if (this.size >= 2.0F && this.b) {
/* 165 */       this.world.addParticle("hugeexplosion", this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
/*     */     } else {
/* 167 */       this.world.addParticle("largeexplode", this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     if (this.b) {
/*     */       
/* 179 */       CraftWorld craftWorld = this.world.getWorld();
/* 180 */       CraftEntity craftEntity = (this.source == null) ? null : this.source.getBukkitEntity();
/* 181 */       Location location = new Location((World)craftWorld, this.posX, this.posY, this.posZ);
/*     */       
/* 183 */       List<Block> blockList = new ArrayList<Block>();
/* 184 */       for (int i1 = this.blocks.size() - 1; i1 >= 0; i1--) {
/* 185 */         ChunkPosition cpos = this.blocks.get(i1);
/* 186 */         Block bblock = craftWorld.getBlockAt(cpos.x, cpos.y, cpos.z);
/* 187 */         if (bblock.getType() != Material.AIR) {
/* 188 */           blockList.add(bblock);
/*     */         }
/*     */       } 
/*     */       
/* 192 */       EntityExplodeEvent event = new EntityExplodeEvent((Entity)craftEntity, location, blockList, 0.3F);
/* 193 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 195 */       this.blocks.clear();
/*     */       
/* 197 */       for (Block bblock : event.blockList()) {
/* 198 */         ChunkPosition coords = new ChunkPosition(bblock.getX(), bblock.getY(), bblock.getZ());
/* 199 */         this.blocks.add(coords);
/*     */       } 
/*     */       
/* 202 */       if (event.isCancelled()) {
/* 203 */         this.wasCanceled = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 208 */       Iterator<ChunkPosition> iterator = this.blocks.iterator();
/*     */       
/* 210 */       while (iterator.hasNext()) {
/* 211 */         ChunkPosition chunkposition = iterator.next();
/* 212 */         int i = chunkposition.x;
/* 213 */         int j = chunkposition.y;
/* 214 */         int k = chunkposition.z;
/* 215 */         Block block = this.world.getType(i, j, k);
/* 216 */         this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, i, j, k);
/* 217 */         if (flag) {
/* 218 */           double d0 = (i + this.world.random.nextFloat());
/* 219 */           double d1 = (j + this.world.random.nextFloat());
/* 220 */           double d2 = (k + this.world.random.nextFloat());
/* 221 */           double d3 = d0 - this.posX;
/* 222 */           double d4 = d1 - this.posY;
/* 223 */           double d5 = d2 - this.posZ;
/* 224 */           double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */           
/* 226 */           d3 /= d6;
/* 227 */           d4 /= d6;
/* 228 */           d5 /= d6;
/* 229 */           double d7 = 0.5D / (d6 / this.size + 0.1D);
/*     */           
/* 231 */           d7 *= (this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F);
/* 232 */           d3 *= d7;
/* 233 */           d4 *= d7;
/* 234 */           d5 *= d7;
/* 235 */           this.world.addParticle("explode", (d0 + this.posX * 1.0D) / 2.0D, (d1 + this.posY * 1.0D) / 2.0D, (d2 + this.posZ * 1.0D) / 2.0D, d3, d4, d5);
/* 236 */           this.world.addParticle("smoke", d0, d1, d2, d3, d4, d5);
/*     */         } 
/*     */         
/* 239 */         if (block.getMaterial() != Material.AIR) {
/* 240 */           if (block.a(this))
/*     */           {
/* 242 */             block.dropNaturally(this.world, i, j, k, this.world.getData(i, j, k), event.getYield(), 0);
/*     */           }
/*     */           
/* 245 */           this.world.setTypeAndData(i, j, k, Blocks.AIR, 0, 3);
/* 246 */           block.wasExploded(this.world, i, j, k, this);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     if (this.a) {
/* 252 */       Iterator<ChunkPosition> iterator = this.blocks.iterator();
/*     */       
/* 254 */       while (iterator.hasNext()) {
/* 255 */         ChunkPosition chunkposition = iterator.next();
/* 256 */         int i = chunkposition.x;
/* 257 */         int j = chunkposition.y;
/* 258 */         int k = chunkposition.z;
/* 259 */         Block block = this.world.getType(i, j, k);
/* 260 */         Block block1 = this.world.getType(i, j - 1, k);
/*     */         
/* 262 */         if (block.getMaterial() == Material.AIR && block1.j() && this.j.nextInt(3) == 0)
/*     */         {
/* 264 */           if (!CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
/* 265 */             this.world.setTypeUpdate(i, j, k, Blocks.FIRE);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map b() {
/* 274 */     return this.l;
/*     */   }
/*     */   
/*     */   public EntityLiving c() {
/* 278 */     return (this.source == null) ? null : ((this.source instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.source).getSource() : ((this.source instanceof EntityLiving) ? (EntityLiving)this.source : null));
/*     */   }
/*     */ 
/*     */   
/*     */   private float getBlockDensity(Vec3D vec3d, AxisAlignedBB aabb) {
/* 283 */     if (!this.world.paperSpigotConfig.optimizeExplosions) {
/* 284 */       return this.world.a(vec3d, aabb);
/*     */     }
/*     */     
/* 287 */     CacheKey key = new CacheKey(this, aabb);
/* 288 */     Float blockDensity = this.world.explosionDensityCache.get(key);
/* 289 */     if (blockDensity == null) {
/* 290 */       blockDensity = Float.valueOf(this.world.a(vec3d, aabb));
/* 291 */       this.world.explosionDensityCache.put(key, blockDensity);
/*     */     } 
/*     */     
/* 294 */     return blockDensity.floatValue();
/*     */   }
/*     */   
/*     */   static class CacheKey { private final World world;
/*     */     private final double posX;
/*     */     private final double posY;
/*     */     private final double posZ;
/*     */     private final double minX;
/*     */     
/*     */     public CacheKey(Explosion explosion, AxisAlignedBB aabb) {
/* 304 */       this.world = explosion.world;
/* 305 */       this.posX = explosion.posX;
/* 306 */       this.posY = explosion.posY;
/* 307 */       this.posZ = explosion.posZ;
/* 308 */       this.minX = aabb.a;
/* 309 */       this.minY = aabb.b;
/* 310 */       this.minZ = aabb.c;
/* 311 */       this.maxX = aabb.d;
/* 312 */       this.maxY = aabb.e;
/* 313 */       this.maxZ = aabb.f;
/*     */     }
/*     */     private final double minY; private final double minZ; private final double maxX; private final double maxY; private final double maxZ;
/*     */     
/*     */     public boolean equals(Object o) {
/* 318 */       if (this == o) return true; 
/* 319 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 321 */       CacheKey cacheKey = (CacheKey)o;
/*     */       
/* 323 */       if (Double.compare(cacheKey.posX, this.posX) != 0) return false; 
/* 324 */       if (Double.compare(cacheKey.posY, this.posY) != 0) return false; 
/* 325 */       if (Double.compare(cacheKey.posZ, this.posZ) != 0) return false; 
/* 326 */       if (Double.compare(cacheKey.minX, this.minX) != 0) return false; 
/* 327 */       if (Double.compare(cacheKey.minY, this.minY) != 0) return false; 
/* 328 */       if (Double.compare(cacheKey.minZ, this.minZ) != 0) return false; 
/* 329 */       if (Double.compare(cacheKey.maxX, this.maxX) != 0) return false; 
/* 330 */       if (Double.compare(cacheKey.maxY, this.maxY) != 0) return false; 
/* 331 */       if (Double.compare(cacheKey.maxZ, this.maxZ) != 0) return false; 
/* 332 */       return this.world.equals(cacheKey.world);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 339 */       int result = this.world.hashCode();
/* 340 */       long temp = Double.doubleToLongBits(this.posX);
/* 341 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 342 */       temp = Double.doubleToLongBits(this.posY);
/* 343 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 344 */       temp = Double.doubleToLongBits(this.posZ);
/* 345 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 346 */       temp = Double.doubleToLongBits(this.minX);
/* 347 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 348 */       temp = Double.doubleToLongBits(this.minY);
/* 349 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 350 */       temp = Double.doubleToLongBits(this.minZ);
/* 351 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 352 */       temp = Double.doubleToLongBits(this.maxX);
/* 353 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 354 */       temp = Double.doubleToLongBits(this.maxY);
/* 355 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 356 */       temp = Double.doubleToLongBits(this.maxZ);
/* 357 */       result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 358 */       return result;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Explosion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */