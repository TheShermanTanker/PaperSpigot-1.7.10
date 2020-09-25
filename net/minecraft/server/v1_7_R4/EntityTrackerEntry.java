/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.player.PlayerVelocityEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ import org.spigotmc.AsyncCatcher;
/*     */ 
/*     */ public class EntityTrackerEntry {
/*  20 */   private static final Logger p = LogManager.getLogger();
/*     */   public Entity tracker;
/*     */   public int b;
/*     */   public int c;
/*     */   public int xLoc;
/*     */   public int yLoc;
/*     */   public int zLoc;
/*     */   public int yRot;
/*     */   public int xRot;
/*     */   public int i;
/*     */   public double j;
/*     */   public double k;
/*     */   public double l;
/*     */   public int m;
/*     */   private double q;
/*     */   private double r;
/*     */   private double s;
/*     */   private boolean isMoving;
/*     */   private boolean u;
/*     */   private int v;
/*     */   private Entity w;
/*     */   private boolean x;
/*     */   public boolean n;
/*  43 */   public Set trackedPlayers = new HashSet();
/*  44 */   public Set<EntityPlayer> freshViewers = Sets.newHashSet();
/*     */   
/*     */   public EntityTrackerEntry(Entity entity, int i, int j, boolean flag) {
/*  47 */     this.tracker = entity;
/*  48 */     this.b = i;
/*  49 */     this.c = j;
/*  50 */     this.u = flag;
/*  51 */     this.xLoc = MathHelper.floor(entity.locX * 32.0D);
/*  52 */     this.yLoc = MathHelper.floor(entity.locY * 32.0D);
/*  53 */     this.zLoc = MathHelper.floor(entity.locZ * 32.0D);
/*  54 */     this.yRot = MathHelper.d(entity.yaw * 256.0F / 360.0F);
/*  55 */     this.xRot = MathHelper.d(entity.pitch * 256.0F / 360.0F);
/*  56 */     this.i = MathHelper.d(entity.getHeadRotation() * 256.0F / 360.0F);
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/*  60 */     return (object instanceof EntityTrackerEntry) ? ((((EntityTrackerEntry)object).tracker.getId() == this.tracker.getId())) : false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  64 */     return this.tracker.getId();
/*     */   }
/*     */   
/*     */   public void track(List list) {
/*  68 */     this.n = false;
/*  69 */     if (!this.isMoving || this.tracker.e(this.q, this.r, this.s) > 16.0D) {
/*  70 */       this.q = this.tracker.locX;
/*  71 */       this.r = this.tracker.locY;
/*  72 */       this.s = this.tracker.locZ;
/*  73 */       this.isMoving = true;
/*  74 */       this.n = true;
/*  75 */       scanPlayers(list);
/*     */     } 
/*     */     
/*  78 */     if (this.w != this.tracker.vehicle || (this.tracker.vehicle != null && this.m % 60 == 0)) {
/*  79 */       this.w = this.tracker.vehicle;
/*  80 */       broadcast(new PacketPlayOutAttachEntity(0, this.tracker, this.tracker.vehicle));
/*     */     } 
/*     */     
/*  83 */     if (this.tracker instanceof EntityItemFrame) {
/*  84 */       EntityItemFrame i3 = (EntityItemFrame)this.tracker;
/*  85 */       ItemStack i4 = i3.getItem();
/*     */       
/*  87 */       if (this.m % 10 == 0 && i4 != null && i4.getItem() instanceof ItemWorldMap) {
/*  88 */         WorldMap i6 = Items.MAP.getSavedMap(i4, this.tracker.world);
/*  89 */         Iterator<EntityHuman> i7 = this.trackedPlayers.iterator();
/*     */         
/*  91 */         while (i7.hasNext()) {
/*  92 */           EntityHuman i8 = i7.next();
/*  93 */           EntityPlayer i9 = (EntityPlayer)i8;
/*     */           
/*  95 */           i6.a(i9, i4);
/*  96 */           Packet j0 = Items.MAP.c(i4, this.tracker.world, i9);
/*     */           
/*  98 */           if (j0 != null) {
/*  99 */             i9.playerConnection.sendPacket(j0);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 104 */       b();
/* 105 */     } else if (this.m % this.c == 0 || this.tracker.al || this.tracker.getDataWatcher().a()) {
/*     */ 
/*     */ 
/*     */       
/* 109 */       if (this.tracker.vehicle == null) {
/* 110 */         this.v++;
/* 111 */         int m = this.tracker.as.a(this.tracker.locX);
/* 112 */         int j = MathHelper.floor(this.tracker.locY * 32.0D);
/* 113 */         int k = this.tracker.as.a(this.tracker.locZ);
/* 114 */         int l = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
/* 115 */         int i1 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
/* 116 */         int j1 = m - this.xLoc;
/* 117 */         int k1 = j - this.yLoc;
/* 118 */         int l1 = k - this.zLoc;
/* 119 */         Object object = null;
/* 120 */         boolean flag = (Math.abs(j1) >= 4 || Math.abs(k1) >= 4 || Math.abs(l1) >= 4 || this.m % 60 == 0);
/* 121 */         boolean flag1 = (Math.abs(l - this.yRot) >= 4 || Math.abs(i1 - this.xRot) >= 4);
/*     */         
/* 123 */         if (this.m > 0 || this.tracker instanceof EntityArrow) {
/*     */           
/* 125 */           if (flag) {
/* 126 */             this.xLoc = m;
/* 127 */             this.yLoc = j;
/* 128 */             this.zLoc = k;
/*     */           } 
/*     */           
/* 131 */           if (flag1) {
/* 132 */             this.yRot = 1;
/* 133 */             this.xRot = i1;
/*     */           } 
/*     */ 
/*     */           
/* 137 */           if (j1 >= -128 && j1 < 128 && k1 >= -128 && k1 < 128 && l1 >= -128 && l1 < 128 && this.v <= 400 && !this.x) {
/* 138 */             if (flag && flag1) {
/* 139 */               object = new PacketPlayOutRelEntityMoveLook(this.tracker.getId(), (byte)j1, (byte)k1, (byte)l1, (byte)l, (byte)i1, this.tracker.onGround);
/* 140 */             } else if (flag) {
/* 141 */               object = new PacketPlayOutRelEntityMove(this.tracker.getId(), (byte)j1, (byte)k1, (byte)l1, this.tracker.onGround);
/* 142 */             } else if (flag1) {
/* 143 */               object = new PacketPlayOutEntityLook(this.tracker.getId(), (byte)l, (byte)i1, this.tracker.onGround);
/*     */             } 
/*     */           } else {
/* 146 */             this.v = 0;
/*     */             
/* 148 */             if (this.tracker instanceof EntityPlayer) {
/* 149 */               scanPlayers(new ArrayList(this.trackedPlayers));
/*     */             }
/*     */             
/* 152 */             object = new PacketPlayOutEntityTeleport(this.tracker.getId(), m, j, k, (byte)l, (byte)i1, this.tracker.onGround, (this.tracker instanceof EntityFallingBlock || this.tracker instanceof EntityTNTPrimed));
/*     */           } 
/*     */         } 
/*     */         
/* 156 */         if (this.u) {
/* 157 */           double d0 = this.tracker.motX - this.j;
/* 158 */           double d1 = this.tracker.motY - this.k;
/* 159 */           double d2 = this.tracker.motZ - this.l;
/* 160 */           double d3 = 0.02D;
/* 161 */           double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */           
/* 163 */           if (d4 > d3 * d3 || (d4 > 0.0D && this.tracker.motX == 0.0D && this.tracker.motY == 0.0D && this.tracker.motZ == 0.0D)) {
/* 164 */             this.j = this.tracker.motX;
/* 165 */             this.k = this.tracker.motY;
/* 166 */             this.l = this.tracker.motZ;
/* 167 */             broadcast(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.j, this.k, this.l));
/*     */           } 
/*     */         } 
/*     */         
/* 171 */         if (object != null) {
/*     */ 
/*     */           
/* 174 */           if (object instanceof PacketPlayOutEntityTeleport) {
/* 175 */             broadcast((Packet)object);
/*     */           } else {
/* 177 */             PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(this.tracker.getId(), m, j, k, (byte)l, (byte)i1, this.tracker.onGround, (this.tracker instanceof EntityFallingBlock || this.tracker instanceof EntityTNTPrimed));
/*     */             
/* 179 */             for (EntityPlayer viewer : this.trackedPlayers) {
/* 180 */               if (this.freshViewers.contains(viewer)) {
/* 181 */                 viewer.playerConnection.sendPacket(teleportPacket); continue;
/*     */               } 
/* 183 */               viewer.playerConnection.sendPacket((Packet)object);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 188 */           this.freshViewers.clear();
/*     */         } 
/*     */ 
/*     */         
/* 192 */         b();
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
/* 206 */         this.x = false;
/*     */       } else {
/* 208 */         int k = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
/* 209 */         int j = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
/* 210 */         boolean flag2 = (Math.abs(k - this.yRot) >= 4 || Math.abs(j - this.xRot) >= 4);
/*     */         
/* 212 */         if (flag2) {
/* 213 */           broadcast(new PacketPlayOutEntityLook(this.tracker.getId(), (byte)k, (byte)j, this.tracker.onGround));
/* 214 */           this.yRot = k;
/* 215 */           this.xRot = j;
/*     */         } 
/*     */         
/* 218 */         this.xLoc = this.tracker.as.a(this.tracker.locX);
/* 219 */         this.yLoc = MathHelper.floor(this.tracker.locY * 32.0D);
/* 220 */         this.zLoc = this.tracker.as.a(this.tracker.locZ);
/* 221 */         b();
/* 222 */         this.x = true;
/*     */       } 
/*     */       
/* 225 */       int i = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
/* 226 */       if (Math.abs(i - this.i) >= 4) {
/* 227 */         broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte)i));
/* 228 */         this.i = i;
/*     */       } 
/*     */       
/* 231 */       this.tracker.al = false;
/*     */     } 
/*     */     
/* 234 */     this.m++;
/* 235 */     if (this.tracker.velocityChanged) {
/*     */       
/* 237 */       boolean cancelled = false;
/*     */       
/* 239 */       if (this.tracker instanceof EntityPlayer) {
/* 240 */         Player player = (Player)this.tracker.getBukkitEntity();
/* 241 */         Vector velocity = player.getVelocity();
/*     */         
/* 243 */         PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity);
/* 244 */         this.tracker.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/* 246 */         if (event.isCancelled()) {
/* 247 */           cancelled = true;
/* 248 */         } else if (!velocity.equals(event.getVelocity())) {
/* 249 */           player.setVelocity(velocity);
/*     */         } 
/*     */       } 
/*     */       
/* 253 */       if (!cancelled) {
/* 254 */         broadcastIncludingSelf(new PacketPlayOutEntityVelocity(this.tracker));
/*     */       }
/*     */ 
/*     */       
/* 258 */       this.tracker.velocityChanged = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b() {
/* 263 */     DataWatcher datawatcher = this.tracker.getDataWatcher();
/*     */     
/* 265 */     if (datawatcher.a()) {
/* 266 */       broadcastIncludingSelf(new PacketPlayOutEntityMetadata(this.tracker.getId(), datawatcher, false));
/*     */     }
/*     */     
/* 269 */     if (this.tracker instanceof EntityLiving) {
/* 270 */       AttributeMapServer attributemapserver = (AttributeMapServer)((EntityLiving)this.tracker).getAttributeMap();
/* 271 */       Set set = attributemapserver.getAttributes();
/*     */       
/* 273 */       if (!set.isEmpty()) {
/*     */         
/* 275 */         if (this.tracker instanceof EntityPlayer) {
/* 276 */           ((EntityPlayer)this.tracker).getBukkitEntity().injectScaledMaxHealth(set, false);
/*     */         }
/*     */         
/* 279 */         broadcastIncludingSelf(new PacketPlayOutUpdateAttributes(this.tracker.getId(), set));
/*     */       } 
/*     */       
/* 282 */       set.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcast(Packet packet) {
/* 287 */     Iterator<EntityPlayer> iterator = this.trackedPlayers.iterator();
/*     */     
/* 289 */     while (iterator.hasNext()) {
/* 290 */       EntityPlayer entityplayer = iterator.next();
/*     */       
/* 292 */       entityplayer.playerConnection.sendPacket(packet);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcastIncludingSelf(Packet packet) {
/* 297 */     broadcast(packet);
/* 298 */     if (this.tracker instanceof EntityPlayer) {
/* 299 */       ((EntityPlayer)this.tracker).playerConnection.sendPacket(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a() {
/* 304 */     Iterator<EntityPlayer> iterator = this.trackedPlayers.iterator();
/*     */     
/* 306 */     while (iterator.hasNext()) {
/* 307 */       EntityPlayer entityplayer = iterator.next();
/*     */       
/* 309 */       entityplayer.d(this.tracker);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(EntityPlayer entityplayer) {
/* 314 */     if (this.trackedPlayers.contains(entityplayer)) {
/* 315 */       entityplayer.d(this.tracker);
/* 316 */       this.trackedPlayers.remove(entityplayer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updatePlayer(EntityPlayer entityplayer) {
/* 321 */     AsyncCatcher.catchOp("player tracker update");
/* 322 */     if (entityplayer != this.tracker) {
/* 323 */       double d0 = entityplayer.locX - (this.xLoc / 32);
/* 324 */       double d1 = entityplayer.locZ - (this.zLoc / 32);
/*     */       
/* 326 */       if (d0 >= -this.b && d0 <= this.b && d1 >= -this.b && d1 <= this.b) {
/* 327 */         if (!this.trackedPlayers.contains(entityplayer) && (d(entityplayer) || this.tracker.attachedToPlayer)) {
/*     */           
/* 329 */           if (this.tracker instanceof EntityPlayer) {
/* 330 */             CraftPlayer craftPlayer = ((EntityPlayer)this.tracker).getBukkitEntity();
/* 331 */             if (!entityplayer.getBukkitEntity().canSee((Player)craftPlayer)) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */           
/* 336 */           entityplayer.removeQueue.remove(Integer.valueOf(this.tracker.getId()));
/*     */ 
/*     */           
/* 339 */           this.freshViewers.add(entityplayer);
/* 340 */           this.trackedPlayers.add(entityplayer);
/* 341 */           Packet packet = c();
/*     */ 
/*     */           
/* 344 */           if (this.tracker instanceof EntityPlayer) {
/*     */             
/* 346 */             entityplayer.playerConnection.sendPacket(PacketPlayOutPlayerInfo.addPlayer((EntityPlayer)this.tracker));
/* 347 */             if (!entityplayer.getName().equals(entityplayer.listName) && entityplayer.playerConnection.networkManager.getVersion() > 28)
/*     */             {
/* 349 */               entityplayer.playerConnection.sendPacket(PacketPlayOutPlayerInfo.updateDisplayName((EntityPlayer)this.tracker));
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 354 */           entityplayer.playerConnection.sendPacket(packet);
/* 355 */           if (!this.tracker.getDataWatcher().d()) {
/* 356 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(this.tracker.getId(), this.tracker.getDataWatcher(), true));
/*     */           }
/*     */           
/* 359 */           if (this.tracker instanceof EntityLiving) {
/* 360 */             AttributeMapServer attributemapserver = (AttributeMapServer)((EntityLiving)this.tracker).getAttributeMap();
/* 361 */             Collection collection = attributemapserver.c();
/*     */ 
/*     */             
/* 364 */             if (this.tracker.getId() == entityplayer.getId()) {
/* 365 */               ((EntityPlayer)this.tracker).getBukkitEntity().injectScaledMaxHealth(collection, false);
/*     */             }
/*     */             
/* 368 */             if (!collection.isEmpty()) {
/* 369 */               entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateAttributes(this.tracker.getId(), collection));
/*     */             }
/*     */           } 
/*     */           
/* 373 */           this.j = this.tracker.motX;
/* 374 */           this.k = this.tracker.motY;
/* 375 */           this.l = this.tracker.motZ;
/* 376 */           if (this.u && !(packet instanceof PacketPlayOutSpawnEntityLiving)) {
/* 377 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.tracker.motX, this.tracker.motY, this.tracker.motZ));
/*     */           }
/*     */           
/* 380 */           if (this.tracker.vehicle != null) {
/* 381 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, this.tracker, this.tracker.vehicle));
/*     */           }
/*     */ 
/*     */           
/* 385 */           if (this.tracker.passenger != null) {
/* 386 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, this.tracker.passenger, this.tracker));
/*     */           }
/*     */ 
/*     */           
/* 390 */           if (this.tracker instanceof EntityInsentient && ((EntityInsentient)this.tracker).getLeashHolder() != null) {
/* 391 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this.tracker, ((EntityInsentient)this.tracker).getLeashHolder()));
/*     */           }
/*     */           
/* 394 */           if (this.tracker instanceof EntityLiving) {
/* 395 */             for (int i = 0; i < 5; i++) {
/* 396 */               ItemStack itemstack = ((EntityLiving)this.tracker).getEquipment(i);
/*     */               
/* 398 */               if (itemstack != null) {
/* 399 */                 entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEquipment(this.tracker.getId(), i, itemstack));
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/* 404 */           if (this.tracker instanceof EntityHuman) {
/* 405 */             EntityHuman entityhuman = (EntityHuman)this.tracker;
/*     */             
/* 407 */             if (entityhuman.isSleeping()) {
/* 408 */               entityplayer.playerConnection.sendPacket(new PacketPlayOutBed(entityhuman, MathHelper.floor(this.tracker.locX), MathHelper.floor(this.tracker.locY), MathHelper.floor(this.tracker.locZ)));
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 413 */           this.i = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
/* 414 */           broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte)this.i));
/*     */ 
/*     */           
/* 417 */           if (this.tracker instanceof EntityLiving) {
/* 418 */             EntityLiving entityliving = (EntityLiving)this.tracker;
/* 419 */             Iterator<MobEffect> iterator = entityliving.getEffects().iterator();
/*     */             
/* 421 */             while (iterator.hasNext()) {
/* 422 */               MobEffect mobeffect = iterator.next();
/*     */               
/* 424 */               entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.tracker.getId(), mobeffect));
/*     */             } 
/*     */           } 
/*     */         } 
/* 428 */       } else if (this.trackedPlayers.contains(entityplayer)) {
/* 429 */         this.trackedPlayers.remove(entityplayer);
/* 430 */         entityplayer.d(this.tracker);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean d(EntityPlayer entityplayer) {
/* 436 */     return entityplayer.r().getPlayerChunkMap().a(entityplayer, this.tracker.ah, this.tracker.aj);
/*     */   }
/*     */   
/*     */   public void scanPlayers(List<EntityPlayer> list) {
/* 440 */     for (int i = 0; i < list.size(); i++) {
/* 441 */       updatePlayer(list.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */   private Packet c() {
/* 446 */     if (this.tracker.dead)
/*     */     {
/*     */       
/* 449 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 453 */     if (this.tracker instanceof EntityItem)
/* 454 */       return new PacketPlayOutSpawnEntity(this.tracker, 2, 1); 
/* 455 */     if (this.tracker instanceof EntityPlayer)
/* 456 */       return new PacketPlayOutNamedEntitySpawn((EntityHuman)this.tracker); 
/* 457 */     if (this.tracker instanceof EntityMinecartAbstract) {
/* 458 */       EntityMinecartAbstract entityminecartabstract = (EntityMinecartAbstract)this.tracker;
/*     */       
/* 460 */       return new PacketPlayOutSpawnEntity(this.tracker, 10, entityminecartabstract.m());
/* 461 */     }  if (this.tracker instanceof EntityBoat)
/* 462 */       return new PacketPlayOutSpawnEntity(this.tracker, 1); 
/* 463 */     if (!(this.tracker instanceof IAnimal) && !(this.tracker instanceof EntityEnderDragon)) {
/* 464 */       if (this.tracker instanceof EntityFishingHook) {
/* 465 */         EntityHuman entityhuman = ((EntityFishingHook)this.tracker).owner;
/*     */         
/* 467 */         return new PacketPlayOutSpawnEntity(this.tracker, 90, (entityhuman != null) ? entityhuman.getId() : this.tracker.getId());
/* 468 */       }  if (this.tracker instanceof EntityArrow) {
/* 469 */         Entity entity = ((EntityArrow)this.tracker).shooter;
/*     */         
/* 471 */         return new PacketPlayOutSpawnEntity(this.tracker, 60, (entity != null) ? entity.getId() : this.tracker.getId());
/* 472 */       }  if (this.tracker instanceof EntitySnowball)
/* 473 */         return new PacketPlayOutSpawnEntity(this.tracker, 61); 
/* 474 */       if (this.tracker instanceof EntityPotion)
/* 475 */         return new PacketPlayOutSpawnEntity(this.tracker, 73, ((EntityPotion)this.tracker).getPotionValue()); 
/* 476 */       if (this.tracker instanceof EntityThrownExpBottle)
/* 477 */         return new PacketPlayOutSpawnEntity(this.tracker, 75); 
/* 478 */       if (this.tracker instanceof EntityEnderPearl)
/* 479 */         return new PacketPlayOutSpawnEntity(this.tracker, 65); 
/* 480 */       if (this.tracker instanceof EntityEnderSignal)
/* 481 */         return new PacketPlayOutSpawnEntity(this.tracker, 72); 
/* 482 */       if (this.tracker instanceof EntityFireworks) {
/* 483 */         return new PacketPlayOutSpawnEntity(this.tracker, 76);
/*     */       }
/*     */ 
/*     */       
/* 487 */       if (this.tracker instanceof EntityFireball) {
/* 488 */         EntityFireball entityfireball = (EntityFireball)this.tracker;
/*     */         
/* 490 */         PacketPlayOutSpawnEntity packetplayoutspawnentity = null;
/* 491 */         byte b0 = 63;
/*     */         
/* 493 */         if (this.tracker instanceof EntitySmallFireball) {
/* 494 */           b0 = 64;
/* 495 */         } else if (this.tracker instanceof EntityWitherSkull) {
/* 496 */           b0 = 66;
/*     */         } 
/*     */         
/* 499 */         if (entityfireball.shooter != null) {
/* 500 */           packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, b0, ((EntityFireball)this.tracker).shooter.getId());
/*     */         } else {
/* 502 */           packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, b0, 0);
/*     */         } 
/*     */         
/* 505 */         packetplayoutspawnentity.d((int)(entityfireball.dirX * 8000.0D));
/* 506 */         packetplayoutspawnentity.e((int)(entityfireball.dirY * 8000.0D));
/* 507 */         packetplayoutspawnentity.f((int)(entityfireball.dirZ * 8000.0D));
/* 508 */         return packetplayoutspawnentity;
/* 509 */       }  if (this.tracker instanceof EntityEgg)
/* 510 */         return new PacketPlayOutSpawnEntity(this.tracker, 62); 
/* 511 */       if (this.tracker instanceof EntityTNTPrimed)
/* 512 */         return new PacketPlayOutSpawnEntity(this.tracker, 50); 
/* 513 */       if (this.tracker instanceof EntityEnderCrystal)
/* 514 */         return new PacketPlayOutSpawnEntity(this.tracker, 51); 
/* 515 */       if (this.tracker instanceof EntityFallingBlock) {
/* 516 */         EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.tracker;
/*     */         
/* 518 */         return new PacketPlayOutSpawnEntity(this.tracker, 70, Block.getId(entityfallingblock.f()) | entityfallingblock.data << 16);
/* 519 */       }  if (this.tracker instanceof EntityPainting)
/* 520 */         return new PacketPlayOutSpawnEntityPainting((EntityPainting)this.tracker); 
/* 521 */       if (this.tracker instanceof EntityItemFrame) {
/* 522 */         EntityItemFrame entityitemframe = (EntityItemFrame)this.tracker;
/*     */         
/* 524 */         PacketPlayOutSpawnEntity packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, 71, entityitemframe.direction);
/* 525 */         packetplayoutspawnentity.a(MathHelper.d((entityitemframe.x * 32)));
/* 526 */         packetplayoutspawnentity.b(MathHelper.d((entityitemframe.y * 32)));
/* 527 */         packetplayoutspawnentity.c(MathHelper.d((entityitemframe.z * 32)));
/* 528 */         return packetplayoutspawnentity;
/* 529 */       }  if (this.tracker instanceof EntityLeash) {
/* 530 */         EntityLeash entityleash = (EntityLeash)this.tracker;
/*     */         
/* 532 */         PacketPlayOutSpawnEntity packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, 77);
/* 533 */         packetplayoutspawnentity.a(MathHelper.d((entityleash.x * 32)));
/* 534 */         packetplayoutspawnentity.b(MathHelper.d((entityleash.y * 32)));
/* 535 */         packetplayoutspawnentity.c(MathHelper.d((entityleash.z * 32)));
/* 536 */         return packetplayoutspawnentity;
/* 537 */       }  if (this.tracker instanceof EntityExperienceOrb) {
/* 538 */         return new PacketPlayOutSpawnEntityExperienceOrb((EntityExperienceOrb)this.tracker);
/*     */       }
/* 540 */       throw new IllegalArgumentException("Don't know how to add " + this.tracker.getClass() + "!");
/*     */     } 
/*     */ 
/*     */     
/* 544 */     this.i = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
/* 545 */     return new PacketPlayOutSpawnEntityLiving((EntityLiving)this.tracker);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear(EntityPlayer entityplayer) {
/* 550 */     AsyncCatcher.catchOp("player tracker clear");
/* 551 */     if (this.trackedPlayers.contains(entityplayer)) {
/* 552 */       this.trackedPlayers.remove(entityplayer);
/* 553 */       entityplayer.d(this.tracker);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityTrackerEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */