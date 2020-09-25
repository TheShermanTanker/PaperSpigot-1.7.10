/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.entity.Explosive;
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ 
/*     */ public class EntityTNTPrimed extends Entity {
/*   9 */   public float yield = 4.0F; public int fuseTicks;
/*     */   private EntityLiving source;
/*     */   public boolean isIncendiary = false;
/*     */   public Location sourceLoc;
/*     */   
/*     */   public EntityTNTPrimed(World world) {
/*  15 */     this((Location)null, world);
/*     */   }
/*     */   
/*     */   public EntityTNTPrimed(Location loc, World world) {
/*  19 */     super(world);
/*  20 */     this.sourceLoc = loc;
/*     */     
/*  22 */     this.k = true;
/*  23 */     a(0.98F, 0.98F);
/*  24 */     this.height = this.length / 2.0F;
/*  25 */     this.loadChunks = world.paperSpigotConfig.loadUnloadedTNTEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityTNTPrimed(Location loc, World world, double d0, double d1, double d2, EntityLiving entityliving) {
/*  30 */     this(loc, world);
/*     */     
/*  32 */     setPosition(d0, d1, d2);
/*     */ 
/*     */     
/*  35 */     this.motX = 0.0D;
/*  36 */     this.motY = 0.20000000298023224D;
/*  37 */     this.motZ = 0.0D;
/*  38 */     this.fuseTicks = 80;
/*  39 */     this.lastX = d0;
/*  40 */     this.lastY = d1;
/*  41 */     this.lastZ = d2;
/*  42 */     this.source = entityliving;
/*     */   }
/*     */   
/*     */   protected void c() {}
/*     */   
/*     */   protected boolean g_() {
/*  48 */     return false;
/*     */   }
/*     */   
/*     */   public boolean R() {
/*  52 */     return !this.dead;
/*     */   }
/*     */   
/*     */   public void h() {
/*  56 */     if (this.world.spigotConfig.currentPrimedTnt++ > this.world.spigotConfig.maxTntTicksPerTick)
/*  57 */       return;  this.lastX = this.locX;
/*  58 */     this.lastY = this.locY;
/*  59 */     this.lastZ = this.locZ;
/*  60 */     this.motY -= 0.03999999910593033D;
/*  61 */     move(this.motX, this.motY, this.motZ);
/*     */     
/*  63 */     if (this.inUnloadedChunk && this.world.paperSpigotConfig.removeUnloadedTNTEntities) {
/*  64 */       die();
/*  65 */       this.fuseTicks = 2;
/*     */     } 
/*     */     
/*  68 */     this.motX *= 0.9800000190734863D;
/*  69 */     this.motY *= 0.9800000190734863D;
/*  70 */     this.motZ *= 0.9800000190734863D;
/*  71 */     if (this.onGround) {
/*  72 */       this.motX *= 0.699999988079071D;
/*  73 */       this.motZ *= 0.699999988079071D;
/*  74 */       this.motY *= -0.5D;
/*     */     } 
/*     */     
/*  77 */     if (this.fuseTicks-- <= 0) {
/*     */       
/*  79 */       if (!this.world.isStatic) {
/*  80 */         explode();
/*     */       }
/*  82 */       die();
/*     */     } else {
/*     */       
/*  85 */       this.world.addParticle("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void explode() {
/*  93 */     ChunkProviderServer chunkProviderServer = (ChunkProviderServer)this.world.chunkProvider;
/*  94 */     boolean forceChunkLoad = chunkProviderServer.forceChunkLoad;
/*  95 */     if (this.world.paperSpigotConfig.loadUnloadedTNTEntities) {
/*  96 */       chunkProviderServer.forceChunkLoad = true;
/*     */     }
/*     */     
/*  99 */     CraftServer server = this.world.getServer();
/*     */     
/* 101 */     ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive)CraftEntity.getEntity(server, this));
/* 102 */     server.getPluginManager().callEvent((Event)event);
/*     */     
/* 104 */     if (!event.isCancelled())
/*     */     {
/* 106 */       this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), true);
/*     */     }
/*     */ 
/*     */     
/* 110 */     if (this.world.paperSpigotConfig.loadUnloadedTNTEntities) {
/* 111 */       chunkProviderServer.forceChunkLoad = forceChunkLoad;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {
/* 117 */     nbttagcompound.setByte("Fuse", (byte)this.fuseTicks);
/*     */     
/* 119 */     if (this.sourceLoc != null) {
/* 120 */       nbttagcompound.setInt("SourceLoc_x", this.sourceLoc.getBlockX());
/* 121 */       nbttagcompound.setInt("SourceLoc_y", this.sourceLoc.getBlockY());
/* 122 */       nbttagcompound.setInt("SourceLoc_z", this.sourceLoc.getBlockZ());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {
/* 128 */     this.fuseTicks = nbttagcompound.getByte("Fuse");
/*     */     
/* 130 */     if (nbttagcompound.hasKey("SourceLoc_x")) {
/* 131 */       int srcX = nbttagcompound.getInt("SourceLoc_x");
/* 132 */       int srcY = nbttagcompound.getInt("SourceLoc_y");
/* 133 */       int srcZ = nbttagcompound.getInt("SourceLoc_z");
/* 134 */       this.sourceLoc = new Location((World)this.world.getWorld(), srcX, srcY, srcZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLiving getSource() {
/* 140 */     return this.source;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityTNTPrimed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */