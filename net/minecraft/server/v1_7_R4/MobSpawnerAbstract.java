/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.SpawnerSpawnEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MobSpawnerAbstract
/*     */ {
/*  16 */   public int spawnDelay = 20;
/*  17 */   private String mobName = "Pig";
/*     */   private List mobs;
/*     */   private TileEntityMobSpawnerData spawnData;
/*     */   public double c;
/*     */   public double d;
/*  22 */   private int minSpawnDelay = 200;
/*  23 */   private int maxSpawnDelay = 800;
/*  24 */   private int spawnCount = 4;
/*     */   private Entity j;
/*  26 */   private int maxNearbyEntities = 6;
/*  27 */   private int requiredPlayerRange = 16;
/*  28 */   private int spawnRange = 4;
/*  29 */   private int tickDelay = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMobName() {
/*  34 */     if (i() == null) {
/*  35 */       if (this.mobName.equals("Minecart")) {
/*  36 */         this.mobName = "MinecartRideable";
/*     */       }
/*     */       
/*  39 */       return this.mobName;
/*     */     } 
/*  41 */     return (i()).c;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMobName(String s) {
/*  46 */     this.mobName = s;
/*     */   }
/*     */   
/*     */   public boolean f() {
/*  50 */     return (a().findNearbyPlayerWhoAffectsSpawning(b() + 0.5D, c() + 0.5D, d() + 0.5D, this.requiredPlayerRange) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void g() {
/*  55 */     if (this.spawnDelay > 0 && --this.tickDelay > 0)
/*  56 */       return;  this.tickDelay = (a()).paperSpigotConfig.mobSpawnerTickRate;
/*     */     
/*  58 */     if (f())
/*     */     {
/*     */       
/*  61 */       if ((a()).isStatic) {
/*  62 */         double d1 = (b() + (a()).random.nextFloat());
/*  63 */         double d2 = (c() + (a()).random.nextFloat());
/*     */         
/*  65 */         double d0 = (d() + (a()).random.nextFloat());
/*  66 */         a().addParticle("smoke", d1, d2, d0, 0.0D, 0.0D, 0.0D);
/*  67 */         a().addParticle("flame", d1, d2, d0, 0.0D, 0.0D, 0.0D);
/*  68 */         if (this.spawnDelay > 0) {
/*  69 */           this.spawnDelay -= this.tickDelay;
/*     */         }
/*     */         
/*  72 */         this.d = this.c;
/*  73 */         this.c = (this.c + (1000.0F / (this.spawnDelay + 200.0F))) % 360.0D;
/*     */       } else {
/*  75 */         if (this.spawnDelay < -this.tickDelay) {
/*  76 */           j();
/*     */         }
/*     */         
/*  79 */         if (this.spawnDelay > 0) {
/*  80 */           this.spawnDelay -= this.tickDelay;
/*     */           
/*     */           return;
/*     */         } 
/*  84 */         boolean flag = false;
/*  85 */         for (int i = 0; i < this.spawnCount; i++) {
/*  86 */           Entity entity = EntityTypes.createEntityByName(getMobName(), a());
/*     */           
/*  88 */           if (entity == null) {
/*     */             return;
/*     */           }
/*     */           
/*  92 */           int j = a().a(entity.getClass(), AxisAlignedBB.a(b(), c(), d(), (b() + 1), (c() + 1), (d() + 1)).grow((this.spawnRange * 2), 4.0D, (this.spawnRange * 2))).size();
/*     */           
/*  94 */           if (j >= this.maxNearbyEntities) {
/*  95 */             j();
/*     */             
/*     */             return;
/*     */           } 
/*  99 */           double d0 = b() + ((a()).random.nextDouble() - (a()).random.nextDouble()) * this.spawnRange;
/* 100 */           double d3 = (c() + (a()).random.nextInt(3) - 1);
/* 101 */           double d4 = d() + ((a()).random.nextDouble() - (a()).random.nextDouble()) * this.spawnRange;
/* 102 */           EntityInsentient entityinsentient = (entity instanceof EntityInsentient) ? (EntityInsentient)entity : null;
/*     */           
/* 104 */           entity.setPositionRotation(d0, d3, d4, (a()).random.nextFloat() * 360.0F, 0.0F);
/* 105 */           if (entityinsentient == null || entityinsentient.canSpawn()) {
/* 106 */             a(entity);
/* 107 */             a().triggerEffect(2004, b(), c(), d(), 0);
/* 108 */             if (entityinsentient != null) {
/* 109 */               entityinsentient.s();
/*     */             }
/*     */             
/* 112 */             flag = true;
/*     */           } 
/*     */         } 
/*     */         
/* 116 */         if (flag) {
/* 117 */           j();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public Entity a(Entity entity) {
/* 124 */     if (i() != null) {
/* 125 */       SpawnerSpawnEvent event; NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 127 */       entity.d(nbttagcompound);
/* 128 */       Iterator<String> iterator = (i()).b.c().iterator();
/*     */       
/* 130 */       while (iterator.hasNext()) {
/* 131 */         String s = iterator.next();
/* 132 */         NBTBase nbtbase = (i()).b.get(s);
/*     */         
/* 134 */         nbttagcompound.set(s, nbtbase.clone());
/*     */       } 
/*     */       
/* 137 */       entity.f(nbttagcompound);
/* 138 */       if (entity.world != null) {
/*     */         
/* 140 */         event = CraftEventFactory.callSpawnerSpawnEvent(entity, b(), c(), d());
/* 141 */         if (!event.isCancelled()) {
/* 142 */           entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
/*     */           
/* 144 */           if (entity.world.spigotConfig.nerfSpawnerMobs)
/*     */           {
/* 146 */             entity.fromMobSpawner = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*     */       SpawnerSpawnEvent spawnerSpawnEvent1;
/*     */ 
/*     */       
/* 155 */       for (Entity entity1 = entity; spawnerSpawnEvent1.hasKeyOfType("Riding", 10); spawnerSpawnEvent1 = event) {
/* 156 */         NBTTagCompound nbttagcompound1 = spawnerSpawnEvent1.getCompound("Riding");
/* 157 */         Entity entity2 = EntityTypes.createEntityByName(nbttagcompound1.getString("id"), entity.world);
/*     */         
/* 159 */         if (entity2 != null) {
/* 160 */           NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*     */           
/* 162 */           entity2.d(nbttagcompound2);
/* 163 */           Iterator<String> iterator1 = nbttagcompound1.c().iterator();
/*     */           
/* 165 */           while (iterator1.hasNext()) {
/* 166 */             String s1 = iterator1.next();
/* 167 */             NBTBase nbtbase1 = nbttagcompound1.get(s1);
/*     */             
/* 169 */             nbttagcompound2.set(s1, nbtbase1.clone());
/*     */           } 
/*     */           
/* 172 */           entity2.f(nbttagcompound2);
/* 173 */           entity2.setPositionRotation(entity1.locX, entity1.locY, entity1.locZ, entity1.yaw, entity1.pitch);
/*     */           
/* 175 */           SpawnerSpawnEvent spawnerSpawnEvent = CraftEventFactory.callSpawnerSpawnEvent(entity2, b(), c(), d());
/* 176 */           if (spawnerSpawnEvent.isCancelled()) {
/*     */             continue;
/*     */           }
/* 179 */           if (entity.world != null) {
/* 180 */             entity.world.addEntity(entity2, CreatureSpawnEvent.SpawnReason.SPAWNER);
/*     */           }
/*     */           
/* 183 */           entity1.mount(entity2);
/*     */         } 
/*     */         
/* 186 */         entity1 = entity2; continue;
/*     */       } 
/* 188 */     } else if (entity instanceof EntityLiving && entity.world != null) {
/* 189 */       ((EntityInsentient)entity).prepare((GroupDataEntity)null);
/*     */       
/* 191 */       SpawnerSpawnEvent event = CraftEventFactory.callSpawnerSpawnEvent(entity, b(), c(), d());
/* 192 */       if (!event.isCancelled()) {
/* 193 */         a().addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
/*     */         
/* 195 */         if (entity.world.spigotConfig.nerfSpawnerMobs)
/*     */         {
/* 197 */           entity.fromMobSpawner = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 204 */     return entity;
/*     */   }
/*     */   
/*     */   private void j() {
/* 208 */     if (this.maxSpawnDelay <= this.minSpawnDelay) {
/* 209 */       this.spawnDelay = this.minSpawnDelay;
/*     */     } else {
/* 211 */       int i = this.maxSpawnDelay - this.minSpawnDelay;
/*     */       
/* 213 */       this.spawnDelay = this.minSpawnDelay + (a()).random.nextInt(i);
/*     */     } 
/*     */     
/* 216 */     if (this.mobs != null && this.mobs.size() > 0) {
/* 217 */       a((TileEntityMobSpawnerData)WeightedRandom.a((a()).random, this.mobs));
/*     */     }
/*     */     
/* 220 */     a(1);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 224 */     this.mobName = nbttagcompound.getString("EntityId");
/* 225 */     this.spawnDelay = nbttagcompound.getShort("Delay");
/* 226 */     if (nbttagcompound.hasKeyOfType("SpawnPotentials", 9)) {
/* 227 */       this.mobs = new ArrayList();
/* 228 */       NBTTagList nbttaglist = nbttagcompound.getList("SpawnPotentials", 10);
/*     */       
/* 230 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 231 */         this.mobs.add(new TileEntityMobSpawnerData(this, nbttaglist.get(i)));
/*     */       }
/*     */     } else {
/* 234 */       this.mobs = null;
/*     */     } 
/*     */     
/* 237 */     if (nbttagcompound.hasKeyOfType("SpawnData", 10)) {
/* 238 */       a(new TileEntityMobSpawnerData(this, nbttagcompound.getCompound("SpawnData"), this.mobName));
/*     */     } else {
/* 240 */       a((TileEntityMobSpawnerData)null);
/*     */     } 
/*     */     
/* 243 */     if (nbttagcompound.hasKeyOfType("MinSpawnDelay", 99)) {
/* 244 */       this.minSpawnDelay = nbttagcompound.getShort("MinSpawnDelay");
/* 245 */       this.maxSpawnDelay = nbttagcompound.getShort("MaxSpawnDelay");
/* 246 */       this.spawnCount = nbttagcompound.getShort("SpawnCount");
/*     */     } 
/*     */     
/* 249 */     if (nbttagcompound.hasKeyOfType("MaxNearbyEntities", 99)) {
/* 250 */       this.maxNearbyEntities = nbttagcompound.getShort("MaxNearbyEntities");
/* 251 */       this.requiredPlayerRange = nbttagcompound.getShort("RequiredPlayerRange");
/*     */     } 
/*     */     
/* 254 */     if (nbttagcompound.hasKeyOfType("SpawnRange", 99)) {
/* 255 */       this.spawnRange = nbttagcompound.getShort("SpawnRange");
/*     */     }
/*     */     
/* 258 */     if (a() != null && (a()).isStatic) {
/* 259 */       this.j = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 264 */     nbttagcompound.setString("EntityId", getMobName());
/* 265 */     nbttagcompound.setShort("Delay", (short)this.spawnDelay);
/* 266 */     nbttagcompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
/* 267 */     nbttagcompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
/* 268 */     nbttagcompound.setShort("SpawnCount", (short)this.spawnCount);
/* 269 */     nbttagcompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
/* 270 */     nbttagcompound.setShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
/* 271 */     nbttagcompound.setShort("SpawnRange", (short)this.spawnRange);
/* 272 */     if (i() != null) {
/* 273 */       nbttagcompound.set("SpawnData", (i()).b.clone());
/*     */     }
/*     */     
/* 276 */     if (i() != null || (this.mobs != null && this.mobs.size() > 0)) {
/* 277 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 279 */       if (this.mobs != null && this.mobs.size() > 0) {
/* 280 */         Iterator<TileEntityMobSpawnerData> iterator = this.mobs.iterator();
/*     */         
/* 282 */         while (iterator.hasNext()) {
/* 283 */           TileEntityMobSpawnerData tileentitymobspawnerdata = iterator.next();
/*     */           
/* 285 */           nbttaglist.add(tileentitymobspawnerdata.a());
/*     */         } 
/*     */       } else {
/* 288 */         nbttaglist.add(i().a());
/*     */       } 
/*     */       
/* 291 */       nbttagcompound.set("SpawnPotentials", nbttaglist);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean b(int i) {
/* 296 */     if (i == 1 && (a()).isStatic) {
/* 297 */       this.spawnDelay = this.minSpawnDelay;
/* 298 */       return true;
/*     */     } 
/* 300 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityMobSpawnerData i() {
/* 305 */     return this.spawnData;
/*     */   }
/*     */   
/*     */   public void a(TileEntityMobSpawnerData tileentitymobspawnerdata) {
/* 309 */     this.spawnData = tileentitymobspawnerdata;
/*     */   }
/*     */   
/*     */   public abstract void a(int paramInt);
/*     */   
/*     */   public abstract World a();
/*     */   
/*     */   public abstract int b();
/*     */   
/*     */   public abstract int c();
/*     */   
/*     */   public abstract int d();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MobSpawnerAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */