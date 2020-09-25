/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_7_R4.AxisAlignedBB;
/*     */ import net.minecraft.server.v1_7_R4.Chunk;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.EntityAnimal;
/*     */ import net.minecraft.server.v1_7_R4.EntityArrow;
/*     */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*     */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.EntitySheep;
/*     */ import net.minecraft.server.v1_7_R4.EntityVillager;
/*     */ import net.minecraft.server.v1_7_R4.MathHelper;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivationRange
/*     */ {
/*  38 */   static AxisAlignedBB maxBB = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  39 */   static AxisAlignedBB miscBB = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  40 */   static AxisAlignedBB animalBB = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  41 */   static AxisAlignedBB monsterBB = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte initializeEntityActivationType(Entity entity) {
/*  52 */     if (entity instanceof net.minecraft.server.v1_7_R4.EntityMonster || entity instanceof net.minecraft.server.v1_7_R4.EntitySlime)
/*     */     {
/*  54 */       return 1; } 
/*  55 */     if (entity instanceof EntityCreature || entity instanceof net.minecraft.server.v1_7_R4.EntityAmbient)
/*     */     {
/*  57 */       return 2;
/*     */     }
/*     */     
/*  60 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean initializeEntityActivationState(Entity entity, SpigotWorldConfig config) {
/*  73 */     if ((entity.activationType == 3 && config.miscActivationRange == 0) || (entity.activationType == 2 && config.animalActivationRange == 0) || (entity.activationType == 1 && config.monsterActivationRange == 0) || entity instanceof net.minecraft.server.v1_7_R4.EntityHuman || entity instanceof net.minecraft.server.v1_7_R4.EntityProjectile || entity instanceof net.minecraft.server.v1_7_R4.EntityEnderDragon || entity instanceof net.minecraft.server.v1_7_R4.EntityComplexPart || entity instanceof net.minecraft.server.v1_7_R4.EntityWither || entity instanceof net.minecraft.server.v1_7_R4.EntityFireball || entity instanceof net.minecraft.server.v1_7_R4.EntityWeather || entity instanceof net.minecraft.server.v1_7_R4.EntityTNTPrimed || entity instanceof net.minecraft.server.v1_7_R4.EntityEnderCrystal || entity instanceof net.minecraft.server.v1_7_R4.EntityFireworks)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  87 */       return true;
/*     */     }
/*     */     
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void growBB(AxisAlignedBB target, AxisAlignedBB source, int x, int y, int z) {
/* 105 */     source.a -= x;
/* 106 */     source.b -= y;
/* 107 */     source.c -= z;
/* 108 */     source.d += x;
/* 109 */     source.e += y;
/* 110 */     source.f += z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void activateEntities(World world) {
/* 121 */     SpigotTimings.entityActivationCheckTimer.startTiming();
/* 122 */     int miscActivationRange = world.spigotConfig.miscActivationRange;
/* 123 */     int animalActivationRange = world.spigotConfig.animalActivationRange;
/* 124 */     int monsterActivationRange = world.spigotConfig.monsterActivationRange;
/*     */     
/* 126 */     int maxRange = Math.max(monsterActivationRange, animalActivationRange);
/* 127 */     maxRange = Math.max(maxRange, miscActivationRange);
/* 128 */     maxRange = Math.min((world.spigotConfig.viewDistance << 4) - 8, maxRange);
/*     */     
/* 130 */     for (Entity player : world.players) {
/*     */ 
/*     */ 
/*     */       
/* 134 */       int maxRange0 = maxRange;
/* 135 */       if (player instanceof EntityPlayer) {
/* 136 */         maxRange0 = Math.min((((EntityPlayer)player).viewDistance << 4) - 8, maxRange);
/*     */       }
/*     */       
/* 139 */       player.activatedTick = MinecraftServer.currentTick;
/* 140 */       growBB(maxBB, player.boundingBox, maxRange0, 256, maxRange0);
/* 141 */       growBB(miscBB, player.boundingBox, miscActivationRange, 256, miscActivationRange);
/* 142 */       growBB(animalBB, player.boundingBox, animalActivationRange, 256, animalActivationRange);
/* 143 */       growBB(monsterBB, player.boundingBox, monsterActivationRange, 256, monsterActivationRange);
/*     */       
/* 145 */       int i = MathHelper.floor(maxBB.a / 16.0D);
/* 146 */       int j = MathHelper.floor(maxBB.d / 16.0D);
/* 147 */       int k = MathHelper.floor(maxBB.c / 16.0D);
/* 148 */       int l = MathHelper.floor(maxBB.f / 16.0D);
/*     */       
/* 150 */       for (int i1 = i; i1 <= j; i1++) {
/*     */         
/* 152 */         for (int j1 = k; j1 <= l; j1++) {
/*     */           
/* 154 */           if (world.getWorld().isChunkLoaded(i1, j1))
/*     */           {
/* 156 */             activateChunkEntities(world.getChunkAt(i1, j1));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 161 */     SpigotTimings.entityActivationCheckTimer.stopTiming();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void activateChunkEntities(Chunk chunk) {
/* 171 */     for (List<Entity> slice : chunk.entitySlices) {
/*     */       
/* 173 */       for (Entity entity : slice) {
/*     */         
/* 175 */         if (MinecraftServer.currentTick > entity.activatedTick) {
/*     */           
/* 177 */           if (entity.defaultActivationState) {
/*     */             
/* 179 */             entity.activatedTick = MinecraftServer.currentTick;
/*     */             continue;
/*     */           } 
/* 182 */           switch (entity.activationType) {
/*     */             
/*     */             case 1:
/* 185 */               if (monsterBB.b(entity.boundingBox))
/*     */               {
/* 187 */                 entity.activatedTick = MinecraftServer.currentTick;
/*     */               }
/*     */               continue;
/*     */             case 2:
/* 191 */               if (animalBB.b(entity.boundingBox))
/*     */               {
/* 193 */                 entity.activatedTick = MinecraftServer.currentTick;
/*     */               }
/*     */               continue;
/*     */           } 
/*     */           
/* 198 */           if (miscBB.b(entity.boundingBox))
/*     */           {
/* 200 */             entity.activatedTick = MinecraftServer.currentTick;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkEntityImmunities(Entity entity) {
/* 218 */     if (entity.inWater || entity.fireTicks > 0)
/*     */     {
/* 220 */       return true;
/*     */     }
/* 222 */     if (!(entity instanceof EntityArrow)) {
/*     */       
/* 224 */       if (!entity.onGround || entity.passenger != null || entity.vehicle != null)
/*     */       {
/*     */         
/* 227 */         return true;
/*     */       }
/* 229 */     } else if (!((EntityArrow)entity).inGround) {
/*     */       
/* 231 */       return true;
/*     */     } 
/*     */     
/* 234 */     if (entity instanceof EntityLiving) {
/*     */       
/* 236 */       EntityLiving living = (EntityLiving)entity;
/* 237 */       if (living.attackTicks > 0 || living.hurtTicks > 0 || living.effects.size() > 0)
/*     */       {
/* 239 */         return true;
/*     */       }
/* 241 */       if (entity instanceof EntityCreature && ((EntityCreature)entity).target != null)
/*     */       {
/* 243 */         return true;
/*     */       }
/* 245 */       if (entity instanceof EntityVillager && ((EntityVillager)entity).bY())
/*     */       {
/* 247 */         return true;
/*     */       }
/* 249 */       if (entity instanceof EntityAnimal) {
/*     */         
/* 251 */         EntityAnimal animal = (EntityAnimal)entity;
/* 252 */         if (animal.isBaby() || animal.ce())
/*     */         {
/* 254 */           return true;
/*     */         }
/* 256 */         if (entity instanceof EntitySheep && ((EntitySheep)entity).isSheared())
/*     */         {
/* 258 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkIfActive(Entity entity) {
/* 273 */     SpigotTimings.checkIfActiveTimer.startTiming();
/*     */ 
/*     */ 
/*     */     
/* 277 */     if (!entity.isAddedToChunk() || entity instanceof net.minecraft.server.v1_7_R4.EntityFireworks || entity instanceof net.minecraft.server.v1_7_R4.EntityFallingBlock || entity.loadChunks) {
/* 278 */       SpigotTimings.checkIfActiveTimer.stopTiming();
/* 279 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 283 */     boolean isActive = (entity.activatedTick >= MinecraftServer.currentTick || entity.defaultActivationState);
/*     */ 
/*     */     
/* 286 */     if (!isActive) {
/*     */       
/* 288 */       if ((MinecraftServer.currentTick - entity.activatedTick - 1L) % 20L == 0L)
/*     */       {
/*     */         
/* 291 */         if (checkEntityImmunities(entity))
/*     */         {
/*     */           
/* 294 */           entity.activatedTick = (MinecraftServer.currentTick + 20);
/*     */         }
/* 296 */         isActive = true;
/*     */       }
/*     */     
/* 299 */     } else if (!entity.defaultActivationState && entity.ticksLived % 4 == 0 && !checkEntityImmunities(entity)) {
/*     */       
/* 301 */       isActive = false;
/*     */     } 
/* 303 */     int x = MathHelper.floor(entity.locX);
/* 304 */     int z = MathHelper.floor(entity.locZ);
/*     */     
/* 306 */     Chunk chunk = entity.world.getChunkIfLoaded(x >> 4, z >> 4);
/* 307 */     if (isActive && (chunk == null || !chunk.areNeighborsLoaded(1)))
/*     */     {
/* 309 */       isActive = false;
/*     */     }
/* 311 */     SpigotTimings.checkIfActiveTimer.stopTiming();
/* 312 */     return isActive;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\ActivationRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */