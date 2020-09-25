/*      */ package net.minecraft.server.v1_7_R4;
/*      */ import java.io.File;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.SocketAddress;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import net.minecraft.util.com.google.common.base.Charsets;
/*      */ import net.minecraft.util.com.google.common.collect.Lists;
/*      */ import net.minecraft.util.com.google.common.collect.Maps;
/*      */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.TravelAgent;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftTravelAgent;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.chunkio.ChunkIOExecutor;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.player.PlayerChangedWorldEvent;
/*      */ import org.bukkit.event.player.PlayerJoinEvent;
/*      */ import org.bukkit.event.player.PlayerLoginEvent;
/*      */ import org.bukkit.event.player.PlayerPortalEvent;
/*      */ import org.bukkit.event.player.PlayerQuitEvent;
/*      */ import org.bukkit.event.player.PlayerRespawnEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.util.Vector;
/*      */ import org.spigotmc.SpigotConfig;
/*      */ import org.spigotmc.event.player.PlayerSpawnLocationEvent;
/*      */ 
/*      */ public abstract class PlayerList {
/*   44 */   public static final File a = new File("banned-players.json");
/*   45 */   public static final File b = new File("banned-ips.json");
/*   46 */   public static final File c = new File("ops.json");
/*   47 */   public static final File d = new File("whitelist.json");
/*   48 */   private static final Logger g = LogManager.getLogger();
/*   49 */   private static final SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
/*      */   private final MinecraftServer server;
/*   51 */   public final List players = new CopyOnWriteArrayList();
/*      */   
/*   53 */   public final Map<String, EntityPlayer> playerMap = new HashMap<String, EntityPlayer>()
/*      */     {
/*      */       public EntityPlayer put(String key, EntityPlayer value) {
/*   56 */         return super.put(key.toLowerCase(), value);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public EntityPlayer get(Object key) {
/*   62 */         EntityPlayer player = super.get((key instanceof String) ? ((String)key).toLowerCase() : key);
/*   63 */         return (player != null && player.playerConnection != null) ? player : null;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean containsKey(Object key) {
/*   68 */         return (get(key) != null);
/*      */       }
/*      */ 
/*      */       
/*      */       public EntityPlayer remove(Object key) {
/*   73 */         return super.remove((key instanceof String) ? ((String)key).toLowerCase() : key);
/*      */       }
/*      */     };
/*   76 */   public final Map<UUID, EntityPlayer> uuidMap = new HashMap<UUID, EntityPlayer>()
/*      */     {
/*      */       public EntityPlayer get(Object key)
/*      */       {
/*   80 */         EntityPlayer player = super.get((key instanceof String) ? ((String)key).toLowerCase() : key);
/*   81 */         return (player != null && player.playerConnection != null) ? player : null;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   private final GameProfileBanList j;
/*      */ 
/*      */   
/*      */   private final IpBanList k;
/*      */ 
/*      */   
/*      */   private final OpList operators;
/*      */ 
/*      */   
/*      */   private final WhiteList whitelist;
/*      */   
/*      */   private final Map n;
/*      */   
/*      */   public IPlayerFileData playerFileData;
/*      */   
/*      */   public boolean hasWhitelist;
/*      */   
/*      */   protected int maxPlayers;
/*      */   
/*      */   private int q;
/*      */   
/*      */   private EnumGamemode r;
/*      */   
/*      */   private boolean s;
/*      */   
/*      */   private int t;
/*      */   
/*      */   private CraftServer cserver;
/*      */   
/*      */   private int currentPing;
/*      */ 
/*      */   
/*      */   public void a(NetworkManager networkmanager, EntityPlayer entityplayer) {
/*  120 */     GameProfile gameprofile = entityplayer.getProfile();
/*  121 */     UserCache usercache = this.server.getUserCache();
/*  122 */     GameProfile gameprofile1 = usercache.a(gameprofile.getId());
/*  123 */     String s = (gameprofile1 == null) ? gameprofile.getName() : gameprofile1.getName();
/*      */     
/*  125 */     usercache.a(gameprofile);
/*  126 */     NBTTagCompound nbttagcompound = a(entityplayer);
/*      */     
/*  128 */     entityplayer.spawnIn(this.server.getWorldServer(entityplayer.dimension));
/*  129 */     entityplayer.playerInteractManager.a((WorldServer)entityplayer.world);
/*  130 */     String s1 = "local";
/*      */     
/*  132 */     if (networkmanager.getSocketAddress() != null) {
/*  133 */       s1 = networkmanager.getSocketAddress().toString();
/*      */     }
/*      */ 
/*      */     
/*  137 */     CraftPlayer craftPlayer = entityplayer.getBukkitEntity();
/*  138 */     PlayerSpawnLocationEvent ev = new PlayerSpawnLocationEvent((Player)craftPlayer, craftPlayer.getLocation());
/*  139 */     Bukkit.getPluginManager().callEvent((Event)ev);
/*      */     
/*  141 */     Location loc = ev.getSpawnLocation();
/*  142 */     WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
/*      */     
/*  144 */     entityplayer.spawnIn(world);
/*  145 */     entityplayer.setPosition(loc.getX(), loc.getY(), loc.getZ());
/*  146 */     entityplayer.b(loc.getYaw(), loc.getPitch());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  151 */     WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
/*  152 */     ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
/*      */     
/*  154 */     a(entityplayer, (EntityPlayer)null, worldserver);
/*  155 */     PlayerConnection playerconnection = new PlayerConnection(this.server, networkmanager, entityplayer);
/*      */ 
/*      */     
/*  158 */     int maxPlayers = getMaxPlayers();
/*  159 */     if (maxPlayers > 60) {
/*  160 */       maxPlayers = 60;
/*      */     }
/*  162 */     playerconnection.sendPacket(new PacketPlayOutLogin(entityplayer.getId(), entityplayer.playerInteractManager.getGameMode(), worldserver.getWorldData().isHardcore(), worldserver.worldProvider.dimension, worldserver.difficulty, maxPlayers, worldserver.getWorldData().getType()));
/*  163 */     entityplayer.getBukkitEntity().sendSupportedChannels();
/*      */     
/*  165 */     playerconnection.sendPacket(new PacketPlayOutCustomPayload("MC|Brand", getServer().getServerModName().getBytes(Charsets.UTF_8)));
/*  166 */     playerconnection.sendPacket(new PacketPlayOutSpawnPosition(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z));
/*  167 */     playerconnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
/*  168 */     playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
/*  169 */     entityplayer.getStatisticManager().d();
/*  170 */     entityplayer.getStatisticManager().updateStatistics(entityplayer);
/*  171 */     sendScoreboard((ScoreboardServer)worldserver.getScoreboard(), entityplayer);
/*  172 */     this.server.az();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  185 */     c(entityplayer);
/*  186 */     worldserver = this.server.getWorldServer(entityplayer.dimension);
/*  187 */     playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
/*  188 */     b(entityplayer, worldserver);
/*  189 */     if (this.server.getResourcePack().length() > 0) {
/*  190 */       entityplayer.setResourcePack(this.server.getResourcePack());
/*      */     }
/*      */     
/*  193 */     Iterator<MobEffect> iterator = entityplayer.getEffects().iterator();
/*      */     
/*  195 */     while (iterator.hasNext()) {
/*  196 */       MobEffect mobeffect = iterator.next();
/*      */       
/*  198 */       playerconnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
/*      */     } 
/*      */     
/*  201 */     entityplayer.syncInventory();
/*  202 */     if (nbttagcompound != null && nbttagcompound.hasKeyOfType("Riding", 10)) {
/*  203 */       Entity entity = EntityTypes.a(nbttagcompound.getCompound("Riding"), worldserver);
/*      */       
/*  205 */       if (entity != null) {
/*  206 */         entity.attachedToPlayer = true;
/*  207 */         worldserver.addEntity(entity);
/*  208 */         entityplayer.mount(entity);
/*  209 */         entity.attachedToPlayer = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  214 */     g.info(entityplayer.getName() + "[" + s1 + "] logged in with entity id " + entityplayer.getId() + " at ([" + entityplayer.world.worldData.getName() + "] " + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
/*      */   }
/*      */   
/*      */   public void sendScoreboard(ScoreboardServer scoreboardserver, EntityPlayer entityplayer) {
/*  218 */     HashSet<ScoreboardObjective> hashset = new HashSet();
/*  219 */     Iterator<ScoreboardTeam> iterator = scoreboardserver.getTeams().iterator();
/*      */     
/*  221 */     while (iterator.hasNext()) {
/*  222 */       ScoreboardTeam scoreboardteam = iterator.next();
/*      */       
/*  224 */       entityplayer.playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardteam, 0));
/*      */     } 
/*      */     
/*  227 */     for (int i = 0; i < 3; i++) {
/*  228 */       ScoreboardObjective scoreboardobjective = scoreboardserver.getObjectiveForSlot(i);
/*      */       
/*  230 */       if (scoreboardobjective != null && !hashset.contains(scoreboardobjective)) {
/*  231 */         List list = scoreboardserver.getScoreboardScorePacketsForObjective(scoreboardobjective);
/*  232 */         Iterator<Packet> iterator1 = list.iterator();
/*      */         
/*  234 */         while (iterator1.hasNext()) {
/*  235 */           Packet packet = iterator1.next();
/*      */           
/*  237 */           entityplayer.playerConnection.sendPacket(packet);
/*      */         } 
/*      */         
/*  240 */         hashset.add(scoreboardobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setPlayerFileData(WorldServer[] aworldserver) {
/*  246 */     if (this.playerFileData != null)
/*  247 */       return;  this.playerFileData = aworldserver[0].getDataManager().getPlayerFileData();
/*      */   }
/*      */   
/*      */   public void a(EntityPlayer entityplayer, WorldServer worldserver) {
/*  251 */     WorldServer worldserver1 = entityplayer.r();
/*      */     
/*  253 */     if (worldserver != null) {
/*  254 */       worldserver.getPlayerChunkMap().removePlayer(entityplayer);
/*      */     }
/*      */     
/*  257 */     worldserver1.getPlayerChunkMap().addPlayer(entityplayer);
/*  258 */     worldserver1.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
/*      */   }
/*      */   
/*      */   public int d() {
/*  262 */     return PlayerChunkMap.getFurthestViewableBlock(s());
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagCompound a(EntityPlayer entityplayer) {
/*  267 */     NBTTagCompound nbttagcompound1, nbttagcompound = ((WorldServer)this.server.worlds.get(0)).getWorldData().i();
/*      */ 
/*      */     
/*  270 */     if (entityplayer.getName().equals(this.server.M()) && nbttagcompound != null) {
/*  271 */       entityplayer.f(nbttagcompound);
/*  272 */       nbttagcompound1 = nbttagcompound;
/*  273 */       g.debug("loading single player");
/*      */     } else {
/*  275 */       nbttagcompound1 = this.playerFileData.load(entityplayer);
/*      */     } 
/*      */     
/*  278 */     return nbttagcompound1;
/*      */   }
/*      */   
/*      */   protected void b(EntityPlayer entityplayer) {
/*  282 */     this.playerFileData.save(entityplayer);
/*  283 */     ServerStatisticManager serverstatisticmanager = (ServerStatisticManager)this.n.get(entityplayer.getUniqueID());
/*      */     
/*  285 */     if (serverstatisticmanager != null) {
/*  286 */       serverstatisticmanager.b();
/*      */     }
/*      */   }
/*      */   
/*      */   public void c(EntityPlayer entityplayer) {
/*  291 */     this.cserver.detectListNameConflict(entityplayer);
/*      */     
/*  293 */     this.players.add(entityplayer);
/*  294 */     this.playerMap.put(entityplayer.getName(), entityplayer);
/*  295 */     this.uuidMap.put(entityplayer.getUniqueID(), entityplayer);
/*  296 */     WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
/*      */ 
/*      */     
/*  299 */     PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.getName() + " joined the game.");
/*  300 */     this.cserver.getPluginManager().callEvent((Event)playerJoinEvent);
/*      */     
/*  302 */     String joinMessage = playerJoinEvent.getJoinMessage();
/*      */     
/*  304 */     if (joinMessage != null && joinMessage.length() > 0) {
/*  305 */       for (IChatBaseComponent line : CraftChatMessage.fromString(joinMessage)) {
/*  306 */         this.server.getPlayerList().sendAll(new PacketPlayOutChat(line));
/*      */       }
/*      */     }
/*  309 */     this.cserver.onPlayerJoin(playerJoinEvent.getPlayer());
/*      */     
/*  311 */     ChunkIOExecutor.adjustPoolSize(getPlayerCount());
/*      */ 
/*      */ 
/*      */     
/*  315 */     if (entityplayer.world == worldserver && !worldserver.players.contains(entityplayer)) {
/*  316 */       worldserver.addEntity(entityplayer);
/*  317 */       a(entityplayer, (WorldServer)null);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  322 */     PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.addPlayer(entityplayer);
/*  323 */     PacketPlayOutPlayerInfo displayPacket = PacketPlayOutPlayerInfo.updateDisplayName(entityplayer); int i;
/*  324 */     for (i = 0; i < this.players.size(); i++) {
/*  325 */       EntityPlayer entityplayer1 = this.players.get(i);
/*      */       
/*  327 */       if (entityplayer1.getBukkitEntity().canSee((Player)entityplayer.getBukkitEntity())) {
/*  328 */         entityplayer1.playerConnection.sendPacket(packet);
/*      */         
/*  330 */         if (!entityplayer.getName().equals(entityplayer.listName) && entityplayer1.playerConnection.networkManager.getVersion() > 28)
/*      */         {
/*  332 */           entityplayer1.playerConnection.sendPacket(displayPacket);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  339 */     for (i = 0; i < this.players.size(); i++) {
/*  340 */       EntityPlayer entityplayer1 = this.players.get(i);
/*      */ 
/*      */       
/*  343 */       if (entityplayer.getBukkitEntity().canSee((Player)entityplayer1.getBukkitEntity())) {
/*      */ 
/*      */ 
/*      */         
/*  347 */         entityplayer.playerConnection.sendPacket(PacketPlayOutPlayerInfo.addPlayer(entityplayer1));
/*      */         
/*  349 */         if (!entityplayer.getName().equals(entityplayer.listName) && entityplayer.playerConnection.networkManager.getVersion() > 28)
/*      */         {
/*  351 */           entityplayer.playerConnection.sendPacket(PacketPlayOutPlayerInfo.updateDisplayName(entityplayer1));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void d(EntityPlayer entityplayer) {
/*  359 */     entityplayer.r().getPlayerChunkMap().movePlayer(entityplayer);
/*      */   }
/*      */   
/*      */   public String disconnect(EntityPlayer entityplayer) {
/*  363 */     entityplayer.a(StatisticList.f);
/*      */ 
/*      */     
/*  366 */     CraftEventFactory.handleInventoryCloseEvent(entityplayer);
/*      */     
/*  368 */     PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.getName() + " left the game.");
/*  369 */     this.cserver.getPluginManager().callEvent((Event)playerQuitEvent);
/*  370 */     entityplayer.getBukkitEntity().disconnect(playerQuitEvent.getQuitMessage());
/*      */ 
/*      */     
/*  373 */     b(entityplayer);
/*  374 */     WorldServer worldserver = entityplayer.r();
/*      */     
/*  376 */     if (entityplayer.vehicle != null && !(entityplayer.vehicle instanceof EntityPlayer)) {
/*  377 */       worldserver.removeEntity(entityplayer.vehicle);
/*  378 */       g.debug("removing player mount");
/*      */     } 
/*      */     
/*  381 */     worldserver.kill(entityplayer);
/*  382 */     worldserver.getPlayerChunkMap().removePlayer(entityplayer);
/*  383 */     this.players.remove(entityplayer);
/*  384 */     this.uuidMap.remove(entityplayer.getUniqueID());
/*  385 */     this.playerMap.remove(entityplayer.getName());
/*  386 */     this.n.remove(entityplayer.getUniqueID());
/*  387 */     ChunkIOExecutor.adjustPoolSize(getPlayerCount());
/*      */ 
/*      */ 
/*      */     
/*  391 */     PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.removePlayer(entityplayer);
/*  392 */     for (int i = 0; i < this.players.size(); i++) {
/*  393 */       EntityPlayer entityplayer1 = this.players.get(i);
/*      */       
/*  395 */       if (entityplayer1.getBukkitEntity().canSee((Player)entityplayer.getBukkitEntity())) {
/*  396 */         entityplayer1.playerConnection.sendPacket(packet);
/*      */       } else {
/*  398 */         entityplayer1.getBukkitEntity().removeDisconnectingPlayer((Player)entityplayer.getBukkitEntity());
/*      */       } 
/*      */     } 
/*      */     
/*  402 */     this.cserver.getScoreboardManager().removePlayer((Player)entityplayer.getBukkitEntity());
/*      */     
/*  404 */     return playerQuitEvent.getQuitMessage();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer attemptLogin(LoginListener loginlistener, GameProfile gameprofile, String hostname) {
/*  413 */     SocketAddress socketaddress = loginlistener.networkManager.getSocketAddress();
/*      */     
/*  415 */     EntityPlayer entity = new EntityPlayer(this.server, this.server.getWorldServer(0), gameprofile, new PlayerInteractManager(this.server.getWorldServer(0)));
/*  416 */     CraftPlayer craftPlayer = entity.getBukkitEntity();
/*  417 */     PlayerLoginEvent event = new PlayerLoginEvent((Player)craftPlayer, hostname, ((InetSocketAddress)socketaddress).getAddress(), ((InetSocketAddress)loginlistener.networkManager.getRawAddress()).getAddress());
/*      */ 
/*      */     
/*  420 */     if (this.j.isBanned(gameprofile) && !this.j.get(gameprofile).hasExpired()) {
/*  421 */       GameProfileBanEntry gameprofilebanentry = (GameProfileBanEntry)this.j.get(gameprofile);
/*      */       
/*  423 */       String s = "You are banned from this server!\nReason: " + gameprofilebanentry.getReason();
/*  424 */       if (gameprofilebanentry.getExpires() != null) {
/*  425 */         s = s + "\nYour ban will be removed on " + h.format(gameprofilebanentry.getExpires());
/*      */       }
/*      */ 
/*      */       
/*  429 */       if (!gameprofilebanentry.hasExpired()) event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s); 
/*  430 */     } else if (!isWhitelisted(gameprofile)) {
/*      */       
/*  432 */       event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, SpigotConfig.whitelistMessage);
/*  433 */     } else if (this.k.isBanned(socketaddress) && !this.k.get(socketaddress).hasExpired()) {
/*  434 */       IpBanEntry ipbanentry = this.k.get(socketaddress);
/*      */       
/*  436 */       String s = "Your IP address is banned from this server!\nReason: " + ipbanentry.getReason();
/*  437 */       if (ipbanentry.getExpires() != null) {
/*  438 */         s = s + "\nYour ban will be removed on " + h.format(ipbanentry.getExpires());
/*      */       }
/*      */ 
/*      */       
/*  442 */       event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s);
/*      */     
/*      */     }
/*  445 */     else if (this.players.size() >= this.maxPlayers) {
/*  446 */       event.disallow(PlayerLoginEvent.Result.KICK_FULL, SpigotConfig.serverFullMessage);
/*      */     } 
/*      */ 
/*      */     
/*  450 */     this.cserver.getPluginManager().callEvent((Event)event);
/*  451 */     if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
/*  452 */       loginlistener.disconnect(event.getKickMessage());
/*  453 */       return null;
/*      */     } 
/*      */     
/*  456 */     return entity;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer processLogin(GameProfile gameprofile, EntityPlayer player) {
/*  461 */     UUID uuid = EntityHuman.a(gameprofile);
/*  462 */     ArrayList arraylist = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EntityPlayer entityplayer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  479 */     if ((entityplayer = this.uuidMap.get(uuid)) != null)
/*      */     {
/*  481 */       entityplayer.playerConnection.disconnect("You logged in from another location");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  495 */     return player;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag) {
/*  501 */     return moveToWorld(entityplayer, i, flag, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location, boolean avoidSuffocation) {
/*  506 */     entityplayer.r().getTracker().untrackPlayer(entityplayer);
/*      */     
/*  508 */     entityplayer.r().getPlayerChunkMap().removePlayer(entityplayer);
/*  509 */     this.players.remove(entityplayer);
/*  510 */     this.server.getWorldServer(entityplayer.dimension).removeEntity(entityplayer);
/*  511 */     ChunkCoordinates chunkcoordinates = entityplayer.getBed();
/*  512 */     boolean flag1 = entityplayer.isRespawnForced();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  526 */     EntityPlayer entityplayer1 = entityplayer;
/*  527 */     World fromWorld = entityplayer1.getBukkitEntity().getWorld();
/*  528 */     entityplayer1.viewingCredits = false;
/*      */ 
/*      */     
/*  531 */     entityplayer1.playerConnection = entityplayer.playerConnection;
/*  532 */     entityplayer1.copyTo(entityplayer, flag);
/*  533 */     entityplayer1.d(entityplayer.getId());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  540 */     if (location == null) {
/*  541 */       boolean isBedSpawn = false;
/*  542 */       CraftWorld cworld = (CraftWorld)this.server.server.getWorld(entityplayer.spawnWorld);
/*  543 */       if (cworld != null && chunkcoordinates != null) {
/*  544 */         ChunkCoordinates chunkCoordinates = EntityHuman.getBed(cworld.getHandle(), chunkcoordinates, flag1);
/*  545 */         if (chunkCoordinates != null) {
/*  546 */           isBedSpawn = true;
/*  547 */           location = new Location((World)cworld, chunkCoordinates.x + 0.5D, chunkCoordinates.y, chunkCoordinates.z + 0.5D);
/*      */         } else {
/*  549 */           entityplayer1.setRespawnPosition((ChunkCoordinates)null, true);
/*  550 */           entityplayer1.playerConnection.sendPacket(new PacketPlayOutGameStateChange(0, 0.0F));
/*      */         } 
/*      */       } 
/*      */       
/*  554 */       if (location == null) {
/*  555 */         cworld = this.server.server.getWorlds().get(0);
/*  556 */         chunkcoordinates = cworld.getHandle().getSpawn();
/*  557 */         location = new Location((World)cworld, chunkcoordinates.x + 0.5D, chunkcoordinates.y, chunkcoordinates.z + 0.5D);
/*      */       } 
/*      */       
/*  560 */       Player respawnPlayer = this.cserver.getPlayer(entityplayer1);
/*  561 */       PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn);
/*  562 */       this.cserver.getPluginManager().callEvent((Event)respawnEvent);
/*      */       
/*  564 */       if (entityplayer.playerConnection.isDisconnected()) {
/*  565 */         return entityplayer;
/*      */       }
/*      */ 
/*      */       
/*  569 */       location = respawnEvent.getRespawnLocation();
/*  570 */       entityplayer.reset();
/*      */     } else {
/*  572 */       location.setWorld((World)this.server.getWorldServer(i).getWorld());
/*      */     } 
/*  574 */     WorldServer worldserver = ((CraftWorld)location.getWorld()).getHandle();
/*  575 */     entityplayer1.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*      */ 
/*      */     
/*  578 */     worldserver.chunkProviderServer.getChunkAt((int)entityplayer1.locX >> 4, (int)entityplayer1.locZ >> 4);
/*      */     
/*  580 */     while (avoidSuffocation && !worldserver.getCubes(entityplayer1, entityplayer1.boundingBox).isEmpty()) {
/*  581 */       entityplayer1.setPosition(entityplayer1.locX, entityplayer1.locY + 1.0D, entityplayer1.locZ);
/*      */     }
/*      */ 
/*      */     
/*  585 */     byte actualDimension = (byte)worldserver.getWorld().getEnvironment().getId();
/*      */     
/*  587 */     entityplayer1.playerConnection.sendPacket(new PacketPlayOutRespawn((byte)((actualDimension >= 0) ? -1 : 0), worldserver.difficulty, worldserver.getWorldData().getType(), entityplayer.playerInteractManager.getGameMode()));
/*  588 */     entityplayer1.playerConnection.sendPacket(new PacketPlayOutRespawn(actualDimension, worldserver.difficulty, worldserver.getWorldData().getType(), entityplayer1.playerInteractManager.getGameMode()));
/*  589 */     entityplayer1.spawnIn(worldserver);
/*  590 */     entityplayer1.dead = false;
/*  591 */     entityplayer1.playerConnection.teleport(new Location((World)worldserver.getWorld(), entityplayer1.locX, entityplayer1.locY, entityplayer1.locZ, entityplayer1.yaw, entityplayer1.pitch));
/*  592 */     entityplayer1.setSneaking(false);
/*  593 */     ChunkCoordinates chunkcoordinates1 = worldserver.getSpawn();
/*      */ 
/*      */     
/*  596 */     entityplayer1.playerConnection.sendPacket(new PacketPlayOutSpawnPosition(chunkcoordinates1.x, chunkcoordinates1.y, chunkcoordinates1.z));
/*  597 */     entityplayer1.playerConnection.sendPacket(new PacketPlayOutExperience(entityplayer1.exp, entityplayer1.expTotal, entityplayer1.expLevel));
/*  598 */     b(entityplayer1, worldserver);
/*      */ 
/*      */     
/*  601 */     if (!entityplayer.playerConnection.isDisconnected()) {
/*  602 */       worldserver.getPlayerChunkMap().addPlayer(entityplayer1);
/*  603 */       worldserver.addEntity(entityplayer1);
/*  604 */       this.players.add(entityplayer1);
/*      */     } 
/*      */     
/*  607 */     updateClient(entityplayer1);
/*  608 */     entityplayer1.updateAbilities();
/*  609 */     Iterator<MobEffect> iterator = entityplayer1.getEffects().iterator();
/*      */     
/*  611 */     while (iterator.hasNext()) {
/*  612 */       MobEffect mobeffect = iterator.next();
/*      */       
/*  614 */       entityplayer1.playerConnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer1.getId(), mobeffect));
/*      */     } 
/*      */ 
/*      */     
/*  618 */     entityplayer1.setHealth(entityplayer1.getHealth());
/*      */ 
/*      */ 
/*      */     
/*  622 */     if (fromWorld != location.getWorld()) {
/*  623 */       PlayerChangedWorldEvent event = new PlayerChangedWorldEvent((Player)entityplayer1.getBukkitEntity(), fromWorld);
/*  624 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*      */     } 
/*      */ 
/*      */     
/*  628 */     if (entityplayer.playerConnection.isDisconnected()) {
/*  629 */       b(entityplayer1);
/*      */     }
/*      */ 
/*      */     
/*  633 */     return entityplayer1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void changeDimension(EntityPlayer entityplayer, int i, PlayerTeleportEvent.TeleportCause cause) {
/*  638 */     WorldServer exitWorld = null;
/*  639 */     if (entityplayer.dimension < 10)
/*      */     {
/*  641 */       for (WorldServer world : this.server.worlds) {
/*  642 */         if (world.dimension == i) {
/*  643 */           exitWorld = world;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  648 */     Location enter = entityplayer.getBukkitEntity().getLocation();
/*  649 */     Location exit = null;
/*  650 */     boolean useTravelAgent = false;
/*  651 */     if (exitWorld != null) {
/*  652 */       if (cause == PlayerTeleportEvent.TeleportCause.END_PORTAL && i == 0) {
/*      */         
/*  654 */         exit = entityplayer.getBukkitEntity().getBedSpawnLocation();
/*  655 */         if (exit == null || (((CraftWorld)exit.getWorld()).getHandle()).dimension != 0) {
/*  656 */           exit = exitWorld.getWorld().getSpawnLocation();
/*      */         }
/*      */       } else {
/*      */         
/*  660 */         exit = calculateTarget(enter, exitWorld);
/*  661 */         useTravelAgent = true;
/*      */       } 
/*      */     }
/*      */     
/*  665 */     TravelAgent agent = (exit != null) ? (TravelAgent)((CraftWorld)exit.getWorld()).getHandle().getTravelAgent() : CraftTravelAgent.DEFAULT;
/*  666 */     agent.setCanCreatePortal((cause != PlayerTeleportEvent.TeleportCause.END_PORTAL));
/*      */     
/*  668 */     PlayerPortalEvent event = new PlayerPortalEvent((Player)entityplayer.getBukkitEntity(), enter, exit, agent, cause);
/*  669 */     event.useTravelAgent(useTravelAgent);
/*  670 */     Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*  671 */     if (event.isCancelled() || event.getTo() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  676 */     exit = (cause != PlayerTeleportEvent.TeleportCause.END_PORTAL && event.useTravelAgent()) ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo();
/*  677 */     if (exit == null) {
/*      */       return;
/*      */     }
/*  680 */     exitWorld = ((CraftWorld)exit.getWorld()).getHandle();
/*      */     
/*  682 */     Vector velocity = entityplayer.getBukkitEntity().getVelocity();
/*  683 */     boolean before = exitWorld.chunkProviderServer.forceChunkLoad;
/*  684 */     exitWorld.chunkProviderServer.forceChunkLoad = true;
/*  685 */     exitWorld.getTravelAgent().adjustExit(entityplayer, exit, velocity);
/*  686 */     exitWorld.chunkProviderServer.forceChunkLoad = before;
/*      */     
/*  688 */     moveToWorld(entityplayer, exitWorld.dimension, true, exit, false);
/*  689 */     if (entityplayer.motX != velocity.getX() || entityplayer.motY != velocity.getY() || entityplayer.motZ != velocity.getZ()) {
/*  690 */       entityplayer.getBukkitEntity().setVelocity(velocity);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(Entity entity, int i, WorldServer worldserver, WorldServer worldserver1) {
/*  697 */     Location exit = calculateTarget(entity.getBukkitEntity().getLocation(), worldserver1);
/*  698 */     repositionEntity(entity, exit, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Location calculateTarget(Location enter, World target) {
/*  703 */     WorldServer worldserver = ((CraftWorld)enter.getWorld()).getHandle();
/*  704 */     WorldServer worldserver1 = target.getWorld().getHandle();
/*  705 */     int i = worldserver.dimension;
/*      */     
/*  707 */     double y = enter.getY();
/*  708 */     float yaw = enter.getYaw();
/*  709 */     float pitch = enter.getPitch();
/*  710 */     double d0 = enter.getX();
/*  711 */     double d1 = enter.getZ();
/*  712 */     double d2 = 8.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  721 */     if (worldserver1.dimension == -1) {
/*  722 */       d0 /= d2;
/*  723 */       d1 /= d2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  730 */     else if (worldserver1.dimension == 0) {
/*  731 */       d0 *= d2;
/*  732 */       d1 *= d2;
/*      */     } else {
/*      */       ChunkCoordinates chunkcoordinates;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  742 */       if (i == 1) {
/*      */         
/*  744 */         worldserver1 = this.server.worlds.get(0);
/*  745 */         chunkcoordinates = worldserver1.getSpawn();
/*      */       } else {
/*  747 */         chunkcoordinates = worldserver1.getDimensionSpawn();
/*      */       } 
/*      */       
/*  750 */       d0 = chunkcoordinates.x;
/*  751 */       y = chunkcoordinates.y;
/*  752 */       d1 = chunkcoordinates.z;
/*  753 */       yaw = 90.0F;
/*  754 */       pitch = 0.0F;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  764 */     if (i != 1) {
/*      */       
/*  766 */       d0 = MathHelper.a((int)d0, -29999872, 29999872);
/*  767 */       d1 = MathHelper.a((int)d1, -29999872, 29999872);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  781 */     return new Location((World)worldserver1.getWorld(), d0, y, d1, yaw, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public void repositionEntity(Entity entity, Location exit, boolean portal) {
/*  786 */     int i = entity.dimension;
/*  787 */     WorldServer worldserver = (WorldServer)entity.world;
/*  788 */     WorldServer worldserver1 = ((CraftWorld)exit.getWorld()).getHandle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  799 */     worldserver.methodProfiler.a("moving");
/*  800 */     entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
/*  801 */     if (entity.isAlive()) {
/*  802 */       worldserver.entityJoinedWorld(entity, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  838 */     worldserver.methodProfiler.b();
/*  839 */     if (i != 1) {
/*  840 */       worldserver.methodProfiler.a("placing");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  845 */       if (entity.isAlive()) {
/*      */ 
/*      */         
/*  848 */         if (portal) {
/*  849 */           Vector velocity = entity.getBukkitEntity().getVelocity();
/*  850 */           worldserver1.getTravelAgent().adjustExit(entity, exit, velocity);
/*  851 */           entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
/*  852 */           if (entity.motX != velocity.getX() || entity.motY != velocity.getY() || entity.motZ != velocity.getZ()) {
/*  853 */             entity.getBukkitEntity().setVelocity(velocity);
/*      */           }
/*      */         } 
/*  856 */         worldserver1.addEntity(entity);
/*  857 */         worldserver1.entityJoinedWorld(entity, false);
/*      */       } 
/*      */       
/*  860 */       worldserver.methodProfiler.b();
/*      */     } 
/*      */     
/*  863 */     entity.spawnIn(worldserver1);
/*      */   }
/*      */   
/*      */   public PlayerList(MinecraftServer minecraftserver) {
/*  867 */     this.currentPing = 0; minecraftserver.server = new CraftServer(minecraftserver, this); minecraftserver.console = ColouredConsoleSender.getInstance(); minecraftserver.reader.addCompleter((Completer)new ConsoleCommandCompleter(minecraftserver.server)); this.cserver = minecraftserver.server; this.j = new GameProfileBanList(a); this.k = new IpBanList(b); this.operators = new OpList(c); this.whitelist = new WhiteList(d); this.n = Maps.newHashMap(); this.server = minecraftserver;
/*      */     this.j.a(false);
/*      */     this.k.a(false);
/*  870 */     this.maxPlayers = 8; } public void tick() { if (++this.t > 600) {
/*  871 */       this.t = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  884 */       if (!this.players.isEmpty()) {
/*      */         
/*  886 */         this.currentPing = (this.currentPing + 1) % this.players.size();
/*  887 */         EntityPlayer player = this.players.get(this.currentPing);
/*  888 */         if (player.lastPing == -1 || Math.abs(player.ping - player.lastPing) > 20) {
/*      */           
/*  890 */           Packet packet = PacketPlayOutPlayerInfo.updatePing(player);
/*  891 */           for (EntityPlayer splayer : this.players) {
/*      */             
/*  893 */             if (splayer.getBukkitEntity().canSee((Player)player.getBukkitEntity()))
/*      */             {
/*  895 */               splayer.playerConnection.sendPacket(packet);
/*      */             }
/*      */           } 
/*  898 */           player.lastPing = player.ping;
/*      */         } 
/*      */       } 
/*  901 */     } catch (Exception e) {} }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAll(Packet packet) {
/*  908 */     for (int i = 0; i < this.players.size(); i++) {
/*  909 */       ((EntityPlayer)this.players.get(i)).playerConnection.sendPacket(packet);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(Packet packet, int i) {
/*  914 */     for (int j = 0; j < this.players.size(); j++) {
/*  915 */       EntityPlayer entityplayer = this.players.get(j);
/*      */       
/*  917 */       if (entityplayer.dimension == i) {
/*  918 */         entityplayer.playerConnection.sendPacket(packet);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public String b(boolean flag) {
/*  924 */     String s = "";
/*  925 */     ArrayList<EntityPlayer> arraylist = Lists.newArrayList(this.players);
/*      */     
/*  927 */     for (int i = 0; i < arraylist.size(); i++) {
/*  928 */       if (i > 0) {
/*  929 */         s = s + ", ";
/*      */       }
/*      */       
/*  932 */       s = s + ((EntityPlayer)arraylist.get(i)).getName();
/*  933 */       if (flag) {
/*  934 */         s = s + " (" + ((EntityPlayer)arraylist.get(i)).getUniqueID().toString() + ")";
/*      */       }
/*      */     } 
/*      */     
/*  938 */     return s;
/*      */   }
/*      */   
/*      */   public String[] f() {
/*  942 */     String[] astring = new String[this.players.size()];
/*      */     
/*  944 */     for (int i = 0; i < this.players.size(); i++) {
/*  945 */       astring[i] = ((EntityPlayer)this.players.get(i)).getName();
/*      */     }
/*      */     
/*  948 */     return astring;
/*      */   }
/*      */   
/*      */   public GameProfile[] g() {
/*  952 */     GameProfile[] agameprofile = new GameProfile[this.players.size()];
/*      */     
/*  954 */     for (int i = 0; i < this.players.size(); i++) {
/*  955 */       agameprofile[i] = ((EntityPlayer)this.players.get(i)).getProfile();
/*      */     }
/*      */     
/*  958 */     return agameprofile;
/*      */   }
/*      */   
/*      */   public GameProfileBanList getProfileBans() {
/*  962 */     return this.j;
/*      */   }
/*      */   
/*      */   public IpBanList getIPBans() {
/*  966 */     return this.k;
/*      */   }
/*      */   
/*      */   public void addOp(GameProfile gameprofile) {
/*  970 */     this.operators.add(new OpListEntry(gameprofile, this.server.l()));
/*      */ 
/*      */     
/*  973 */     Player player = this.server.server.getPlayer(gameprofile.getId());
/*  974 */     if (player != null) {
/*  975 */       player.recalculatePermissions();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeOp(GameProfile gameprofile) {
/*  981 */     this.operators.remove(gameprofile);
/*      */ 
/*      */     
/*  984 */     Player player = this.server.server.getPlayer(gameprofile.getId());
/*  985 */     if (player != null) {
/*  986 */       player.recalculatePermissions();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWhitelisted(GameProfile gameprofile) {
/*  992 */     return (!this.hasWhitelist || this.operators.d(gameprofile) || this.whitelist.d(gameprofile));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOp(GameProfile gameprofile) {
/*  997 */     return (this.operators.d(gameprofile) || (this.server.N() && ((WorldServer)this.server.worlds.get(0)).getWorldData().allowCommands() && this.server.M().equalsIgnoreCase(gameprofile.getName())) || this.s);
/*      */   }
/*      */   
/*      */   public EntityPlayer getPlayer(String s) {
/* 1001 */     return this.playerMap.get(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List a(ChunkCoordinates chunkcoordinates, int i, int j, int k, int l, int i1, int j1, Map map, String s, String s1, World world) {
/* 1018 */     if (this.players.isEmpty()) {
/* 1019 */       return Collections.emptyList();
/*      */     }
/* 1021 */     Object object = new ArrayList();
/* 1022 */     boolean flag = (k < 0);
/* 1023 */     boolean flag1 = (s != null && s.startsWith("!"));
/* 1024 */     boolean flag2 = (s1 != null && s1.startsWith("!"));
/* 1025 */     int k1 = i * i;
/* 1026 */     int l1 = j * j;
/*      */     
/* 1028 */     k = MathHelper.a(k);
/* 1029 */     if (flag1) {
/* 1030 */       s = s.substring(1);
/*      */     }
/*      */     
/* 1033 */     if (flag2) {
/* 1034 */       s1 = s1.substring(1);
/*      */     }
/*      */     
/* 1037 */     for (int i2 = 0; i2 < this.players.size(); i2++) {
/* 1038 */       EntityPlayer entityplayer = this.players.get(i2);
/*      */       
/* 1040 */       if ((world == null || entityplayer.world == world) && (s == null || flag1 != s.equalsIgnoreCase(entityplayer.getName()))) {
/* 1041 */         if (s1 != null) {
/* 1042 */           ScoreboardTeamBase scoreboardteambase = entityplayer.getScoreboardTeam();
/* 1043 */           String s2 = (scoreboardteambase == null) ? "" : scoreboardteambase.getName();
/*      */           
/* 1045 */           if (flag2 == s1.equalsIgnoreCase(s2)) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */         
/* 1050 */         if (chunkcoordinates != null && (i > 0 || j > 0)) {
/* 1051 */           float f = chunkcoordinates.e(entityplayer.getChunkCoordinates());
/*      */           
/* 1053 */           if ((i > 0 && f < k1) || (j > 0 && f > l1)) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */         
/* 1058 */         if (a(entityplayer, map) && (l == EnumGamemode.NONE.getId() || l == entityplayer.playerInteractManager.getGameMode().getId()) && (i1 <= 0 || entityplayer.expLevel >= i1) && entityplayer.expLevel <= j1) {
/* 1059 */           ((List<EntityPlayer>)object).add(entityplayer);
/*      */         }
/*      */       } 
/*      */       continue;
/*      */     } 
/* 1064 */     if (chunkcoordinates != null) {
/* 1065 */       Collections.sort((List)object, new PlayerDistanceComparator(chunkcoordinates));
/*      */     }
/*      */     
/* 1068 */     if (flag) {
/* 1069 */       Collections.reverse((List)object);
/*      */     }
/*      */     
/* 1072 */     if (k > 0) {
/* 1073 */       object = ((List)object).subList(0, Math.min(k, ((List)object).size()));
/*      */     }
/*      */     
/* 1076 */     return (List)object;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean a(EntityHuman entityhuman, Map map) {
/* 1081 */     if (map != null && map.size() != 0) {
/* 1082 */       Map.Entry entry; boolean flag; int i; Iterator<Map.Entry> iterator = map.entrySet().iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       do {
/* 1089 */         if (!iterator.hasNext()) {
/* 1090 */           return true;
/*      */         }
/*      */         
/* 1093 */         entry = iterator.next();
/* 1094 */         String s = (String)entry.getKey();
/*      */         
/* 1096 */         flag = false;
/* 1097 */         if (s.endsWith("_min") && s.length() > 4) {
/* 1098 */           flag = true;
/* 1099 */           s = s.substring(0, s.length() - 4);
/*      */         } 
/*      */         
/* 1102 */         Scoreboard scoreboard = entityhuman.getScoreboard();
/* 1103 */         ScoreboardObjective scoreboardobjective = scoreboard.getObjective(s);
/*      */         
/* 1105 */         if (scoreboardobjective == null) {
/* 1106 */           return false;
/*      */         }
/*      */         
/* 1109 */         ScoreboardScore scoreboardscore = entityhuman.getScoreboard().getPlayerScoreForObjective(entityhuman.getName(), scoreboardobjective);
/*      */         
/* 1111 */         i = scoreboardscore.getScore();
/* 1112 */         if (i < ((Integer)entry.getValue()).intValue() && flag) {
/* 1113 */           return false;
/*      */         }
/* 1115 */       } while (i <= ((Integer)entry.getValue()).intValue() || flag);
/*      */       
/* 1117 */       return false;
/*      */     } 
/* 1119 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketNearby(double d0, double d1, double d2, double d3, int i, Packet packet) {
/* 1124 */     sendPacketNearby((EntityHuman)null, d0, d1, d2, d3, i, packet);
/*      */   }
/*      */   
/*      */   public void sendPacketNearby(EntityHuman entityhuman, double d0, double d1, double d2, double d3, int i, Packet packet) {
/* 1128 */     for (int j = 0; j < this.players.size(); j++) {
/* 1129 */       EntityPlayer entityplayer = this.players.get(j);
/*      */ 
/*      */       
/* 1132 */       if (entityhuman == null || !(entityhuman instanceof EntityPlayer) || entityplayer.getBukkitEntity().canSee((Player)((EntityPlayer)entityhuman).getBukkitEntity()))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1137 */         if (entityplayer != entityhuman && entityplayer.dimension == i) {
/* 1138 */           double d4 = d0 - entityplayer.locX;
/* 1139 */           double d5 = d1 - entityplayer.locY;
/* 1140 */           double d6 = d2 - entityplayer.locZ;
/*      */           
/* 1142 */           if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3)
/* 1143 */             entityplayer.playerConnection.sendPacket(packet); 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void savePlayers() {
/* 1150 */     for (int i = 0; i < this.players.size(); i++) {
/* 1151 */       b(this.players.get(i));
/*      */     }
/*      */   }
/*      */   
/*      */   public void addWhitelist(GameProfile gameprofile) {
/* 1156 */     this.whitelist.add(new WhiteListEntry(gameprofile));
/*      */   }
/*      */   
/*      */   public void removeWhitelist(GameProfile gameprofile) {
/* 1160 */     this.whitelist.remove(gameprofile);
/*      */   }
/*      */   
/*      */   public WhiteList getWhitelist() {
/* 1164 */     return this.whitelist;
/*      */   }
/*      */   
/*      */   public String[] getWhitelisted() {
/* 1168 */     return this.whitelist.getEntries();
/*      */   }
/*      */   
/*      */   public OpList getOPs() {
/* 1172 */     return this.operators;
/*      */   }
/*      */   
/*      */   public String[] n() {
/* 1176 */     return this.operators.getEntries();
/*      */   }
/*      */   
/*      */   public void reloadWhitelist() {}
/*      */   
/*      */   public void b(EntityPlayer entityplayer, WorldServer worldserver) {
/* 1182 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")));
/* 1183 */     if (worldserver.Q())
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1188 */       entityplayer.setPlayerWeather(WeatherType.DOWNFALL, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateClient(EntityPlayer entityplayer) {
/* 1194 */     entityplayer.updateInventory(entityplayer.defaultContainer);
/* 1195 */     entityplayer.getBukkitEntity().updateScaledHealth();
/* 1196 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
/*      */   }
/*      */   
/*      */   public int getPlayerCount() {
/* 1200 */     return this.players.size();
/*      */   }
/*      */   
/*      */   public int getMaxPlayers() {
/* 1204 */     return this.maxPlayers;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getSeenPlayers() {
/* 1209 */     return ((WorldServer)this.server.worlds.get(0)).getDataManager().getPlayerFileData().getSeenPlayers();
/*      */   }
/*      */   
/*      */   public boolean getHasWhitelist() {
/* 1213 */     return this.hasWhitelist;
/*      */   }
/*      */   
/*      */   public void setHasWhitelist(boolean flag) {
/* 1217 */     this.hasWhitelist = flag;
/*      */   }
/*      */   
/*      */   public List b(String s) {
/* 1221 */     ArrayList<EntityPlayer> arraylist = new ArrayList();
/* 1222 */     Iterator<EntityPlayer> iterator = this.players.iterator();
/*      */     
/* 1224 */     while (iterator.hasNext()) {
/* 1225 */       EntityPlayer entityplayer = iterator.next();
/*      */       
/* 1227 */       if (entityplayer.s().equals(s)) {
/* 1228 */         arraylist.add(entityplayer);
/*      */       }
/*      */     } 
/*      */     
/* 1232 */     return arraylist;
/*      */   }
/*      */   
/*      */   public int s() {
/* 1236 */     return this.q;
/*      */   }
/*      */   
/*      */   public MinecraftServer getServer() {
/* 1240 */     return this.server;
/*      */   }
/*      */   
/*      */   public NBTTagCompound t() {
/* 1244 */     return null;
/*      */   }
/*      */   
/*      */   private void a(EntityPlayer entityplayer, EntityPlayer entityplayer1, World world) {
/* 1248 */     if (entityplayer1 != null) {
/* 1249 */       entityplayer.playerInteractManager.setGameMode(entityplayer1.playerInteractManager.getGameMode());
/* 1250 */     } else if (this.r != null) {
/* 1251 */       entityplayer.playerInteractManager.setGameMode(this.r);
/*      */     } 
/*      */     
/* 1254 */     entityplayer.playerInteractManager.b(world.getWorldData().getGameType());
/*      */   }
/*      */   
/*      */   public void u() {
/* 1258 */     while (!this.players.isEmpty()) {
/*      */       
/* 1260 */       EntityPlayer p = this.players.get(0);
/* 1261 */       p.playerConnection.disconnect(this.server.server.getShutdownMessage());
/* 1262 */       if (!this.players.isEmpty() && this.players.get(0) == p)
/*      */       {
/* 1264 */         this.players.remove(0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendMessage(IChatBaseComponent[] ichatbasecomponent) {
/* 1272 */     for (IChatBaseComponent component : ichatbasecomponent) {
/* 1273 */       sendMessage(component, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessage(IChatBaseComponent ichatbasecomponent, boolean flag) {
/* 1279 */     this.server.sendMessage(ichatbasecomponent);
/* 1280 */     sendAll(new PacketPlayOutChat(ichatbasecomponent, flag));
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent ichatbasecomponent) {
/* 1284 */     sendMessage(ichatbasecomponent, true);
/*      */   }
/*      */   
/*      */   public ServerStatisticManager a(EntityHuman entityhuman) {
/* 1288 */     UUID uuid = entityhuman.getUniqueID();
/* 1289 */     ServerStatisticManager serverstatisticmanager = (uuid == null) ? null : (ServerStatisticManager)this.n.get(uuid);
/*      */     
/* 1291 */     if (serverstatisticmanager == null) {
/* 1292 */       File file1 = new File(this.server.getWorldServer(0).getDataManager().getDirectory(), "stats");
/* 1293 */       File file2 = new File(file1, uuid.toString() + ".json");
/*      */       
/* 1295 */       if (!file2.exists()) {
/* 1296 */         File file3 = new File(file1, entityhuman.getName() + ".json");
/*      */         
/* 1298 */         if (file3.exists() && file3.isFile()) {
/* 1299 */           file3.renameTo(file2);
/*      */         }
/*      */       } 
/*      */       
/* 1303 */       serverstatisticmanager = new ServerStatisticManager(this.server, file2);
/* 1304 */       serverstatisticmanager.a();
/* 1305 */       this.n.put(uuid, serverstatisticmanager);
/*      */     } 
/*      */     
/* 1308 */     return serverstatisticmanager;
/*      */   }
/*      */   
/*      */   public void a(int i) {
/* 1312 */     this.q = i;
/* 1313 */     if (this.server.worldServer != null) {
/* 1314 */       WorldServer[] aworldserver = this.server.worldServer;
/* 1315 */       int j = aworldserver.length;
/*      */       
/* 1317 */       for (int k = 0; k < j; k++) {
/* 1318 */         WorldServer worldserver = aworldserver[k];
/*      */         
/* 1320 */         if (worldserver != null)
/* 1321 */           worldserver.getPlayerChunkMap().a(i); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */