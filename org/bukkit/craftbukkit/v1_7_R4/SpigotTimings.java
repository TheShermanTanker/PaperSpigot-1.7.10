/*     */ package org.bukkit.craftbukkit.v1_7_R4;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.TileEntity;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.scheduler.CraftTask;
/*     */ import org.bukkit.plugin.java.JavaPluginLoader;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpigotTimings
/*     */ {
/*  16 */   public static final CustomTimingsHandler serverTickTimer = new CustomTimingsHandler("** Full Server Tick");
/*  17 */   public static final CustomTimingsHandler playerListTimer = new CustomTimingsHandler("Player List");
/*  18 */   public static final CustomTimingsHandler connectionTimer = new CustomTimingsHandler("Connection Handler");
/*  19 */   public static final CustomTimingsHandler tickablesTimer = new CustomTimingsHandler("Tickables");
/*  20 */   public static final CustomTimingsHandler schedulerTimer = new CustomTimingsHandler("Scheduler");
/*  21 */   public static final CustomTimingsHandler chunkIOTickTimer = new CustomTimingsHandler("ChunkIOTick");
/*  22 */   public static final CustomTimingsHandler timeUpdateTimer = new CustomTimingsHandler("Time Update");
/*  23 */   public static final CustomTimingsHandler serverCommandTimer = new CustomTimingsHandler("Server Command");
/*  24 */   public static final CustomTimingsHandler worldSaveTimer = new CustomTimingsHandler("World Save");
/*     */   
/*  26 */   public static final CustomTimingsHandler entityMoveTimer = new CustomTimingsHandler("** entityMove");
/*  27 */   public static final CustomTimingsHandler tickEntityTimer = new CustomTimingsHandler("** tickEntity");
/*  28 */   public static final CustomTimingsHandler activatedEntityTimer = new CustomTimingsHandler("** activatedTickEntity");
/*  29 */   public static final CustomTimingsHandler tickTileEntityTimer = new CustomTimingsHandler("** tickTileEntity");
/*     */   
/*  31 */   public static final CustomTimingsHandler timerEntityBaseTick = new CustomTimingsHandler("** livingEntityBaseTick");
/*  32 */   public static final CustomTimingsHandler timerEntityAI = new CustomTimingsHandler("** livingEntityAI");
/*  33 */   public static final CustomTimingsHandler timerEntityAICollision = new CustomTimingsHandler("** livingEntityAICollision");
/*  34 */   public static final CustomTimingsHandler timerEntityAIMove = new CustomTimingsHandler("** livingEntityAIMove");
/*  35 */   public static final CustomTimingsHandler timerEntityTickRest = new CustomTimingsHandler("** livingEntityTickRest");
/*     */   
/*  37 */   public static final CustomTimingsHandler processQueueTimer = new CustomTimingsHandler("processQueue");
/*  38 */   public static final CustomTimingsHandler schedulerSyncTimer = new CustomTimingsHandler("** Scheduler - Sync Tasks", JavaPluginLoader.pluginParentTimer);
/*     */   
/*  40 */   public static final CustomTimingsHandler playerCommandTimer = new CustomTimingsHandler("** playerCommand");
/*     */   
/*  42 */   public static final CustomTimingsHandler entityActivationCheckTimer = new CustomTimingsHandler("entityActivationCheck");
/*  43 */   public static final CustomTimingsHandler checkIfActiveTimer = new CustomTimingsHandler("** checkIfActive");
/*     */   
/*  45 */   public static final HashMap<String, CustomTimingsHandler> entityTypeTimingMap = new HashMap<String, CustomTimingsHandler>();
/*  46 */   public static final HashMap<String, CustomTimingsHandler> tileEntityTypeTimingMap = new HashMap<String, CustomTimingsHandler>();
/*  47 */   public static final HashMap<String, CustomTimingsHandler> pluginTaskTimingMap = new HashMap<String, CustomTimingsHandler>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CustomTimingsHandler getPluginTaskTimings(BukkitTask task, long period) {
/*     */     String plugin;
/*  56 */     if (!task.isSync()) {
/*  57 */       return null;
/*     */     }
/*     */     
/*  60 */     CraftTask ctask = (CraftTask)task;
/*     */     
/*  62 */     if (task.getOwner() != null) {
/*  63 */       plugin = task.getOwner().getDescription().getFullName();
/*  64 */     } else if (ctask.timingName != null) {
/*  65 */       plugin = "CraftScheduler";
/*     */     } else {
/*  67 */       plugin = "Unknown";
/*     */     } 
/*  69 */     String taskname = ctask.getTaskName();
/*     */     
/*  71 */     String name = "Task: " + plugin + " Runnable: " + taskname;
/*  72 */     if (period > 0L) {
/*  73 */       name = name + "(interval:" + period + ")";
/*     */     } else {
/*  75 */       name = name + "(Single)";
/*     */     } 
/*  77 */     CustomTimingsHandler result = pluginTaskTimingMap.get(name);
/*  78 */     if (result == null) {
/*  79 */       result = new CustomTimingsHandler(name, schedulerSyncTimer);
/*  80 */       pluginTaskTimingMap.put(name, result);
/*     */     } 
/*  82 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CustomTimingsHandler getEntityTimings(Entity entity) {
/*  91 */     String entityType = entity.getClass().getSimpleName();
/*  92 */     CustomTimingsHandler result = entityTypeTimingMap.get(entityType);
/*  93 */     if (result == null) {
/*  94 */       result = new CustomTimingsHandler("** tickEntity - " + entityType, activatedEntityTimer);
/*  95 */       entityTypeTimingMap.put(entityType, result);
/*     */     } 
/*  97 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CustomTimingsHandler getTileEntityTimings(TileEntity entity) {
/* 106 */     String entityType = entity.getClass().getSimpleName();
/* 107 */     CustomTimingsHandler result = tileEntityTypeTimingMap.get(entityType);
/* 108 */     if (result == null) {
/* 109 */       result = new CustomTimingsHandler("** tickTileEntity - " + entityType, tickTileEntityTimer);
/* 110 */       tileEntityTypeTimingMap.put(entityType, result);
/*     */     } 
/* 112 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class WorldTimingsHandler
/*     */   {
/*     */     public final CustomTimingsHandler mobSpawn;
/*     */     
/*     */     public final CustomTimingsHandler doChunkUnload;
/*     */     
/*     */     public final CustomTimingsHandler doPortalForcer;
/*     */     public final CustomTimingsHandler doTickPending;
/*     */     public final CustomTimingsHandler doTickTiles;
/*     */     public final CustomTimingsHandler doVillages;
/*     */     public final CustomTimingsHandler doChunkMap;
/*     */     public final CustomTimingsHandler doChunkGC;
/*     */     public final CustomTimingsHandler doSounds;
/*     */     public final CustomTimingsHandler entityTick;
/*     */     public final CustomTimingsHandler tileEntityTick;
/*     */     public final CustomTimingsHandler tileEntityPending;
/*     */     public final CustomTimingsHandler tracker;
/*     */     public final CustomTimingsHandler doTick;
/*     */     public final CustomTimingsHandler tickEntities;
/*     */     public final CustomTimingsHandler syncChunkLoadTimer;
/*     */     public final CustomTimingsHandler syncChunkLoadDataTimer;
/*     */     public final CustomTimingsHandler syncChunkLoadStructuresTimer;
/*     */     public final CustomTimingsHandler syncChunkLoadEntitiesTimer;
/*     */     public final CustomTimingsHandler syncChunkLoadTileEntitiesTimer;
/*     */     public final CustomTimingsHandler syncChunkLoadTileTicksTimer;
/*     */     public final CustomTimingsHandler syncChunkLoadPostTimer;
/*     */     
/*     */     public WorldTimingsHandler(World server) {
/* 144 */       String name = server.worldData.getName() + " - ";
/*     */       
/* 146 */       this.mobSpawn = new CustomTimingsHandler("** " + name + "mobSpawn");
/* 147 */       this.doChunkUnload = new CustomTimingsHandler("** " + name + "doChunkUnload");
/* 148 */       this.doTickPending = new CustomTimingsHandler("** " + name + "doTickPending");
/* 149 */       this.doTickTiles = new CustomTimingsHandler("** " + name + "doTickTiles");
/* 150 */       this.doVillages = new CustomTimingsHandler("** " + name + "doVillages");
/* 151 */       this.doChunkMap = new CustomTimingsHandler("** " + name + "doChunkMap");
/* 152 */       this.doSounds = new CustomTimingsHandler("** " + name + "doSounds");
/* 153 */       this.doChunkGC = new CustomTimingsHandler("** " + name + "doChunkGC");
/* 154 */       this.doPortalForcer = new CustomTimingsHandler("** " + name + "doPortalForcer");
/* 155 */       this.entityTick = new CustomTimingsHandler("** " + name + "entityTick");
/* 156 */       this.tileEntityTick = new CustomTimingsHandler("** " + name + "tileEntityTick");
/* 157 */       this.tileEntityPending = new CustomTimingsHandler("** " + name + "tileEntityPending");
/*     */       
/* 159 */       this.syncChunkLoadTimer = new CustomTimingsHandler("** " + name + "syncChunkLoad");
/* 160 */       this.syncChunkLoadDataTimer = new CustomTimingsHandler("** " + name + "syncChunkLoad - Data");
/* 161 */       this.syncChunkLoadStructuresTimer = new CustomTimingsHandler("** " + name + "chunkLoad - Structures");
/* 162 */       this.syncChunkLoadEntitiesTimer = new CustomTimingsHandler("** " + name + "chunkLoad - Entities");
/* 163 */       this.syncChunkLoadTileEntitiesTimer = new CustomTimingsHandler("** " + name + "chunkLoad - TileEntities");
/* 164 */       this.syncChunkLoadTileTicksTimer = new CustomTimingsHandler("** " + name + "chunkLoad - TileTicks");
/* 165 */       this.syncChunkLoadPostTimer = new CustomTimingsHandler("** " + name + "chunkLoad - Post");
/*     */ 
/*     */       
/* 168 */       this.tracker = new CustomTimingsHandler(name + "tracker");
/* 169 */       this.doTick = new CustomTimingsHandler(name + "doTick");
/* 170 */       this.tickEntities = new CustomTimingsHandler(name + "tickEntities");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\SpigotTimings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */