/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.AsyncCatcher;
/*     */ import org.spigotmc.TrackingRange;
/*     */ 
/*     */ public class EntityTracker
/*     */ {
/*  14 */   private static final Logger a = LogManager.getLogger();
/*     */   private final WorldServer world;
/*  16 */   private Set c = new HashSet();
/*  17 */   public IntHashMap trackedEntities = new IntHashMap();
/*     */   private int e;
/*     */   
/*     */   public EntityTracker(WorldServer worldserver) {
/*  21 */     this.world = worldserver;
/*  22 */     this.e = worldserver.getMinecraftServer().getPlayerList().d();
/*     */   }
/*     */   
/*     */   public void track(Entity entity) {
/*  26 */     if (entity instanceof EntityPlayer) {
/*  27 */       addEntity(entity, 512, 2);
/*  28 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/*  29 */       Iterator<EntityTrackerEntry> iterator = this.c.iterator();
/*     */       
/*  31 */       while (iterator.hasNext()) {
/*  32 */         EntityTrackerEntry entitytrackerentry = iterator.next();
/*     */         
/*  34 */         if (entitytrackerentry.tracker != entityplayer) {
/*  35 */           entitytrackerentry.updatePlayer(entityplayer);
/*     */         }
/*     */       } 
/*  38 */     } else if (entity instanceof EntityFishingHook) {
/*  39 */       addEntity(entity, 64, 5, true);
/*  40 */     } else if (entity instanceof EntityArrow) {
/*  41 */       addEntity(entity, 64, 20, false);
/*  42 */     } else if (entity instanceof EntitySmallFireball) {
/*  43 */       addEntity(entity, 64, 10, false);
/*  44 */     } else if (entity instanceof EntityFireball) {
/*  45 */       addEntity(entity, 64, 10, false);
/*  46 */     } else if (entity instanceof EntitySnowball) {
/*  47 */       addEntity(entity, 64, 10, true);
/*  48 */     } else if (entity instanceof EntityEnderPearl) {
/*  49 */       addEntity(entity, 64, 10, true);
/*  50 */     } else if (entity instanceof EntityEnderSignal) {
/*  51 */       addEntity(entity, 64, 4, true);
/*  52 */     } else if (entity instanceof EntityEgg) {
/*  53 */       addEntity(entity, 64, 10, true);
/*  54 */     } else if (entity instanceof EntityPotion) {
/*  55 */       addEntity(entity, 64, 10, true);
/*  56 */     } else if (entity instanceof EntityThrownExpBottle) {
/*  57 */       addEntity(entity, 64, 10, true);
/*  58 */     } else if (entity instanceof EntityFireworks) {
/*  59 */       addEntity(entity, 64, 10, true);
/*  60 */     } else if (entity instanceof EntityItem) {
/*  61 */       addEntity(entity, 64, 20, true);
/*  62 */     } else if (entity instanceof EntityMinecartAbstract) {
/*  63 */       addEntity(entity, 80, 3, true);
/*  64 */     } else if (entity instanceof EntityBoat) {
/*  65 */       addEntity(entity, 80, 3, true);
/*  66 */     } else if (entity instanceof EntitySquid) {
/*  67 */       addEntity(entity, 64, 3, true);
/*  68 */     } else if (entity instanceof EntityWither) {
/*  69 */       addEntity(entity, 80, 3, false);
/*  70 */     } else if (entity instanceof EntityBat) {
/*  71 */       addEntity(entity, 80, 3, false);
/*  72 */     } else if (entity instanceof IAnimal) {
/*  73 */       addEntity(entity, 80, 3, true);
/*  74 */     } else if (entity instanceof EntityEnderDragon) {
/*  75 */       addEntity(entity, 160, 3, true);
/*  76 */     } else if (entity instanceof EntityTNTPrimed) {
/*  77 */       addEntity(entity, 160, 10, true);
/*  78 */     } else if (entity instanceof EntityFallingBlock) {
/*  79 */       addEntity(entity, 160, 20, true);
/*  80 */     } else if (entity instanceof EntityHanging) {
/*  81 */       addEntity(entity, 160, 2147483647, false);
/*  82 */     } else if (entity instanceof EntityExperienceOrb) {
/*  83 */       addEntity(entity, 160, 20, true);
/*  84 */     } else if (entity instanceof EntityEnderCrystal) {
/*  85 */       addEntity(entity, 256, 2147483647, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addEntity(Entity entity, int i, int j) {
/*  90 */     addEntity(entity, i, j, false);
/*     */   }
/*     */   
/*     */   public void addEntity(Entity entity, int i, int j, boolean flag) {
/*  94 */     AsyncCatcher.catchOp("entity track");
/*  95 */     i = TrackingRange.getEntityTrackingRange(entity, i);
/*  96 */     if (i > this.e) {
/*  97 */       i = this.e;
/*     */     }
/*     */     
/*     */     try {
/* 101 */       if (this.trackedEntities.b(entity.getId())) {
/* 102 */         throw new IllegalStateException("Entity is already tracked!");
/*     */       }
/*     */       
/* 105 */       EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, i, j, flag);
/*     */       
/* 107 */       this.c.add(entitytrackerentry);
/* 108 */       this.trackedEntities.a(entity.getId(), entitytrackerentry);
/* 109 */       entitytrackerentry.scanPlayers(this.world.players);
/* 110 */     } catch (Throwable throwable) {
/* 111 */       CrashReport crashreport = CrashReport.a(throwable, "Adding entity to track");
/* 112 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity To Track");
/*     */       
/* 114 */       crashreportsystemdetails.a("Tracking range", i + " blocks");
/* 115 */       crashreportsystemdetails.a("Update interval", new CrashReportEntityTrackerUpdateInterval(this, j));
/* 116 */       entity.a(crashreportsystemdetails);
/* 117 */       CrashReportSystemDetails crashreportsystemdetails1 = crashreport.a("Entity That Is Already Tracked");
/*     */       
/* 119 */       ((EntityTrackerEntry)this.trackedEntities.get(entity.getId())).tracker.a(crashreportsystemdetails1);
/*     */       
/*     */       try {
/* 122 */         throw new ReportedException(crashreport);
/* 123 */       } catch (ReportedException reportedexception) {
/* 124 */         a.error("\"Silently\" catching entity tracking error.", reportedexception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void untrackEntity(Entity entity) {
/* 130 */     AsyncCatcher.catchOp("entity untrack");
/* 131 */     if (entity instanceof EntityPlayer) {
/* 132 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/* 133 */       Iterator<EntityTrackerEntry> iterator = this.c.iterator();
/*     */       
/* 135 */       while (iterator.hasNext()) {
/* 136 */         EntityTrackerEntry entitytrackerentry = iterator.next();
/*     */         
/* 138 */         entitytrackerentry.a(entityplayer);
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)this.trackedEntities.d(entity.getId());
/*     */     
/* 144 */     if (entitytrackerentry1 != null) {
/* 145 */       this.c.remove(entitytrackerentry1);
/* 146 */       entitytrackerentry1.a();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updatePlayers() {
/* 151 */     ArrayList<EntityPlayer> arraylist = new ArrayList();
/* 152 */     Iterator<EntityTrackerEntry> iterator = this.c.iterator();
/*     */     
/* 154 */     while (iterator.hasNext()) {
/* 155 */       EntityTrackerEntry entitytrackerentry = iterator.next();
/*     */       
/* 157 */       entitytrackerentry.track(this.world.players);
/* 158 */       if (entitytrackerentry.n && entitytrackerentry.tracker instanceof EntityPlayer) {
/* 159 */         arraylist.add((EntityPlayer)entitytrackerentry.tracker);
/*     */       }
/*     */     } 
/*     */     
/* 163 */     for (int i = 0; i < arraylist.size(); i++) {
/* 164 */       EntityPlayer entityplayer = arraylist.get(i);
/* 165 */       Iterator<EntityTrackerEntry> iterator1 = this.c.iterator();
/*     */       
/* 167 */       while (iterator1.hasNext()) {
/* 168 */         EntityTrackerEntry entitytrackerentry1 = iterator1.next();
/*     */         
/* 170 */         if (entitytrackerentry1.tracker != entityplayer) {
/* 171 */           entitytrackerentry1.updatePlayer(entityplayer);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(Entity entity, Packet packet) {
/* 178 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntities.get(entity.getId());
/*     */     
/* 180 */     if (entitytrackerentry != null) {
/* 181 */       entitytrackerentry.broadcast(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendPacketToEntity(Entity entity, Packet packet) {
/* 186 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntities.get(entity.getId());
/*     */     
/* 188 */     if (entitytrackerentry != null) {
/* 189 */       entitytrackerentry.broadcastIncludingSelf(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void untrackPlayer(EntityPlayer entityplayer) {
/* 194 */     Iterator<EntityTrackerEntry> iterator = this.c.iterator();
/*     */     
/* 196 */     while (iterator.hasNext()) {
/* 197 */       EntityTrackerEntry entitytrackerentry = iterator.next();
/*     */       
/* 199 */       entitytrackerentry.clear(entityplayer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(EntityPlayer entityplayer, Chunk chunk) {
/* 204 */     Iterator<EntityTrackerEntry> iterator = this.c.iterator();
/*     */     
/* 206 */     while (iterator.hasNext()) {
/* 207 */       EntityTrackerEntry entitytrackerentry = iterator.next();
/*     */       
/* 209 */       if (entitytrackerentry.tracker != entityplayer && entitytrackerentry.tracker.ah == chunk.locX && entitytrackerentry.tracker.aj == chunk.locZ)
/* 210 */         entitytrackerentry.updatePlayer(entityplayer); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */