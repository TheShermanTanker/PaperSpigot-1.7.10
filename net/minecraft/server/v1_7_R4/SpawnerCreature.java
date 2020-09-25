/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.LongHash;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.LongObjectHashMap;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SpawnerCreature
/*     */ {
/*  17 */   private LongObjectHashMap<Boolean> a = new LongObjectHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ChunkPosition getRandomPosition(World world, int i, int j) {
/*  22 */     Chunk chunk = world.getChunkAt(i, j);
/*  23 */     int k = i * 16 + world.random.nextInt(16);
/*  24 */     int l = j * 16 + world.random.nextInt(16);
/*  25 */     int i1 = world.random.nextInt((chunk == null) ? world.S() : (chunk.h() + 16 - 1));
/*     */     
/*  27 */     return new ChunkPosition(k, i1, l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getEntityCount(WorldServer server, Class oClass) {
/*  33 */     int i = 0;
/*  34 */     for (Long coord : this.a.keySet()) {
/*     */       
/*  36 */       int x = LongHash.msw(coord.longValue());
/*  37 */       int z = LongHash.lsw(coord.longValue());
/*  38 */       if (!server.chunkProviderServer.unloadQueue.contains(coord.longValue()) && server.isChunkLoaded(x, z))
/*     */       {
/*  40 */         i += (server.getChunkAt(x, z)).entityCount.get(oClass);
/*     */       }
/*     */     } 
/*  43 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int spawnEntities(WorldServer worldserver, boolean flag, boolean flag1, boolean flag2) {
/*  48 */     if (!flag && !flag1) {
/*  49 */       return 0;
/*     */     }
/*  51 */     this.a.clear();
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/*  56 */     for (i = 0; i < worldserver.players.size(); i++) {
/*  57 */       EntityHuman entityhuman = worldserver.players.get(i);
/*     */       
/*  59 */       if (entityhuman.affectsSpawning) {
/*     */ 
/*     */         
/*  62 */         int k = MathHelper.floor(entityhuman.locX / 16.0D);
/*     */         
/*  64 */         int m = MathHelper.floor(entityhuman.locZ / 16.0D);
/*  65 */         byte b0 = 8;
/*     */         
/*  67 */         b0 = worldserver.spigotConfig.mobSpawnRange;
/*  68 */         b0 = (b0 > worldserver.spigotConfig.viewDistance) ? (byte)worldserver.spigotConfig.viewDistance : b0;
/*     */         
/*  70 */         if (entityhuman instanceof EntityPlayer) {
/*  71 */           int viewDistance = ((EntityPlayer)entityhuman).viewDistance;
/*  72 */           b0 = (b0 > viewDistance) ? (byte)viewDistance : b0;
/*     */         } 
/*     */         
/*  75 */         b0 = (b0 > 8) ? 8 : b0;
/*     */ 
/*     */         
/*  78 */         for (int l = -b0; l <= b0; l++) {
/*  79 */           for (int i1 = -b0; i1 <= b0; i1++) {
/*  80 */             boolean flag3 = (l == -b0 || l == b0 || i1 == -b0 || i1 == b0);
/*     */ 
/*     */             
/*  83 */             long chunkCoords = LongHash.toLong(l + k, i1 + m);
/*     */             
/*  85 */             if (!flag3) {
/*  86 */               this.a.put(chunkCoords, Boolean.valueOf(false));
/*  87 */             } else if (!this.a.containsKey(chunkCoords)) {
/*  88 */               this.a.put(chunkCoords, Boolean.valueOf(true));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     i = 0;
/*  96 */     ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
/*  97 */     EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
/*     */     
/*  99 */     int j = aenumcreaturetype.length;
/*     */     
/* 101 */     for (int j1 = 0; j1 < j; j1++) {
/* 102 */       EnumCreatureType enumcreaturetype = aenumcreaturetype[j1];
/*     */ 
/*     */       
/* 105 */       int limit = enumcreaturetype.b();
/* 106 */       switch (enumcreaturetype) {
/*     */         case MONSTER:
/* 108 */           limit = worldserver.getWorld().getMonsterSpawnLimit();
/*     */           break;
/*     */         case CREATURE:
/* 111 */           limit = worldserver.getWorld().getAnimalSpawnLimit();
/*     */           break;
/*     */         case WATER_CREATURE:
/* 114 */           limit = worldserver.getWorld().getWaterAnimalSpawnLimit();
/*     */           break;
/*     */         case AMBIENT:
/* 117 */           limit = worldserver.getWorld().getAmbientSpawnLimit();
/*     */           break;
/*     */       } 
/*     */       
/* 121 */       if (limit != 0) {
/*     */ 
/*     */         
/* 124 */         int mobcnt = 0;
/*     */ 
/*     */         
/* 127 */         if ((!enumcreaturetype.d() || flag1) && (enumcreaturetype.d() || flag) && (!enumcreaturetype.e() || flag2) && (mobcnt = getEntityCount(worldserver, enumcreaturetype.a())) <= limit * this.a.size() / 256) {
/* 128 */           Iterator<Long> iterator = this.a.keySet().iterator();
/*     */           
/* 130 */           int moblimit = limit * this.a.size() / 256 - mobcnt + 1;
/*     */           
/* 132 */           label115: while (iterator.hasNext() && moblimit > 0) {
/*     */             
/* 134 */             long key = ((Long)iterator.next()).longValue();
/*     */             
/* 136 */             if (!((Boolean)this.a.get(key)).booleanValue()) {
/* 137 */               ChunkPosition chunkposition = getRandomPosition(worldserver, LongHash.msw(key), LongHash.lsw(key));
/*     */               
/* 139 */               int k1 = chunkposition.x;
/* 140 */               int l1 = chunkposition.y;
/* 141 */               int i2 = chunkposition.z;
/*     */               
/* 143 */               if (!worldserver.getType(k1, l1, i2).r() && worldserver.getType(k1, l1, i2).getMaterial() == enumcreaturetype.c()) {
/* 144 */                 int j2 = 0;
/* 145 */                 int k2 = 0;
/*     */                 
/* 147 */                 while (k2 < 3) {
/* 148 */                   int l2 = k1;
/* 149 */                   int i3 = l1;
/* 150 */                   int j3 = i2;
/* 151 */                   byte b1 = 6;
/* 152 */                   BiomeMeta biomemeta = null;
/* 153 */                   GroupDataEntity groupdataentity = null;
/* 154 */                   int k3 = 0;
/*     */ 
/*     */                   
/* 157 */                   while (k3 < 4) {
/*     */                     
/* 159 */                     l2 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
/* 160 */                     i3 += worldserver.random.nextInt(1) - worldserver.random.nextInt(1);
/* 161 */                     j3 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
/* 162 */                     if (a(enumcreaturetype, worldserver, l2, i3, j3)) {
/* 163 */                       float f = l2 + 0.5F;
/* 164 */                       float f1 = i3;
/* 165 */                       float f2 = j3 + 0.5F;
/*     */                       
/* 167 */                       if (worldserver.findNearbyPlayerWhoAffectsSpawning(f, f1, f2, 24.0D) == null) {
/* 168 */                         float f3 = f - chunkcoordinates.x;
/* 169 */                         float f4 = f1 - chunkcoordinates.y;
/* 170 */                         float f5 = f2 - chunkcoordinates.z;
/* 171 */                         float f6 = f3 * f3 + f4 * f4 + f5 * f5;
/*     */                         
/* 173 */                         if (f6 >= 576.0F) {
/* 174 */                           EntityInsentient entityinsentient; if (biomemeta == null) {
/* 175 */                             biomemeta = worldserver.a(enumcreaturetype, l2, i3, j3);
/* 176 */                             if (biomemeta == null) {
/*     */                               break;
/*     */                             }
/*     */                           } 
/*     */ 
/*     */ 
/*     */                           
/*     */                           try {
/* 184 */                             entityinsentient = biomemeta.b.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldserver });
/* 185 */                           } catch (Exception exception) {
/* 186 */                             exception.printStackTrace();
/* 187 */                             return i;
/*     */                           } 
/*     */                           
/* 190 */                           entityinsentient.setPositionRotation(f, f1, f2, worldserver.random.nextFloat() * 360.0F, 0.0F);
/* 191 */                           if (entityinsentient.canSpawn()) {
/* 192 */                             j2++;
/*     */                             
/* 194 */                             groupdataentity = entityinsentient.prepare(groupdataentity);
/* 195 */                             worldserver.addEntity(entityinsentient, CreatureSpawnEvent.SpawnReason.NATURAL);
/*     */ 
/*     */                             
/* 198 */                             if (--moblimit <= 0) {
/*     */                               continue label115;
/*     */                             }
/*     */ 
/*     */ 
/*     */                             
/* 204 */                             if (j2 >= entityinsentient.bB()) {
/*     */                               continue label115;
/*     */                             }
/*     */                           } 
/*     */                           
/* 209 */                           i += j2;
/*     */                         } 
/*     */                       } 
/*     */                     } 
/*     */                     
/* 214 */                     k3++;
/*     */                   } 
/*     */ 
/*     */ 
/*     */                   
/* 219 */                   k2++;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 229 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean a(EnumCreatureType enumcreaturetype, World world, int i, int j, int k) {
/* 234 */     if (enumcreaturetype.c() == Material.WATER)
/* 235 */       return (world.getType(i, j, k).getMaterial().isLiquid() && world.getType(i, j - 1, k).getMaterial().isLiquid() && !world.getType(i, j + 1, k).r()); 
/* 236 */     if (!World.a(world, i, j - 1, k)) {
/* 237 */       return false;
/*     */     }
/* 239 */     Block block = world.getType(i, j - 1, k);
/*     */     
/* 241 */     return (block != Blocks.BEDROCK && !world.getType(i, j, k).r() && !world.getType(i, j, k).getMaterial().isLiquid() && !world.getType(i, j + 1, k).r());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void a(World world, BiomeBase biomebase, int i, int j, int k, int l, Random random) {
/* 246 */     List list = biomebase.getMobs(EnumCreatureType.CREATURE);
/*     */     
/* 248 */     if (!list.isEmpty())
/* 249 */       while (random.nextFloat() < biomebase.g()) {
/* 250 */         BiomeMeta biomemeta = (BiomeMeta)WeightedRandom.a(world.random, list);
/* 251 */         GroupDataEntity groupdataentity = null;
/* 252 */         int i1 = biomemeta.c + random.nextInt(1 + biomemeta.d - biomemeta.c);
/* 253 */         int j1 = i + random.nextInt(k);
/* 254 */         int k1 = j + random.nextInt(l);
/* 255 */         int l1 = j1;
/* 256 */         int i2 = k1;
/*     */         
/* 258 */         for (int j2 = 0; j2 < i1; j2++) {
/* 259 */           boolean flag = false;
/*     */           
/* 261 */           for (int k2 = 0; !flag && k2 < 4; k2++) {
/* 262 */             int l2 = world.i(j1, k1);
/*     */             
/* 264 */             if (a(EnumCreatureType.CREATURE, world, j1, l2, k1)) {
/* 265 */               EntityInsentient entityinsentient; float f = j1 + 0.5F;
/* 266 */               float f1 = l2;
/* 267 */               float f2 = k1 + 0.5F;
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 272 */                 entityinsentient = biomemeta.b.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
/* 273 */               } catch (Exception exception) {
/* 274 */                 exception.printStackTrace();
/*     */               } 
/*     */ 
/*     */               
/* 278 */               entityinsentient.setPositionRotation(f, f1, f2, random.nextFloat() * 360.0F, 0.0F);
/*     */               
/* 280 */               groupdataentity = entityinsentient.prepare(groupdataentity);
/* 281 */               world.addEntity(entityinsentient, CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
/*     */               
/* 283 */               flag = true;
/*     */             } 
/*     */             
/* 286 */             j1 += random.nextInt(5) - random.nextInt(5);
/*     */             
/* 288 */             for (k1 += random.nextInt(5) - random.nextInt(5); j1 < i || j1 >= i + k || k1 < j || k1 >= j + k; k1 = i2 + random.nextInt(5) - random.nextInt(5))
/* 289 */               j1 = l1 + random.nextInt(5) - random.nextInt(5); 
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SpawnerCreature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */