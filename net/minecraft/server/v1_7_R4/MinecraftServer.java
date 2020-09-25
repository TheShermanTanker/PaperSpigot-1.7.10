/*      */ package net.minecraft.server.v1_7_R4;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.net.Proxy;
/*      */ import java.net.UnknownHostException;
/*      */ import java.security.KeyPair;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.util.com.google.common.base.Charsets;
/*      */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*      */ import net.minecraft.util.com.mojang.authlib.GameProfileRepository;
/*      */ import net.minecraft.util.com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import net.minecraft.util.com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*      */ import net.minecraft.util.io.netty.handler.codec.base64.Base64;
/*      */ import net.minecraft.util.io.netty.util.ResourceLeakDetector;
/*      */ import net.minecraft.util.org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.craftbukkit.Main;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*      */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.util.Waitable;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.server.RemoteServerCommandEvent;
/*      */ import org.bukkit.event.world.WorldLoadEvent;
/*      */ import org.bukkit.event.world.WorldSaveEvent;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.spigotmc.CustomTimingsHandler;
/*      */ import org.spigotmc.WatchdogThread;
/*      */ 
/*      */ public abstract class MinecraftServer implements ICommandListener, Runnable, IMojangStatistics {
/*   48 */   private static final Logger i = LogManager.getLogger();
/*   49 */   private static final File a = new File("usercache.json");
/*      */   private static MinecraftServer j;
/*      */   public Convertable convertable;
/*   52 */   private final MojangStatisticsGenerator l = new MojangStatisticsGenerator("server", this, ar());
/*      */   public File universe;
/*   54 */   private final List n = new ArrayList();
/*      */   private final ICommandHandler o;
/*   56 */   public final MethodProfiler methodProfiler = new MethodProfiler();
/*      */   private ServerConnection p;
/*   58 */   private final ServerPing q = new ServerPing();
/*   59 */   private final Random r = new Random();
/*      */   private String serverIp;
/*   61 */   private int t = -1;
/*      */   public WorldServer[] worldServer;
/*      */   private PlayerList u;
/*      */   private boolean isRunning = true;
/*      */   private boolean isStopped;
/*      */   private int ticks;
/*      */   protected final Proxy d;
/*      */   public String e;
/*      */   public int f;
/*      */   private boolean onlineMode;
/*      */   private boolean spawnAnimals;
/*      */   private boolean spawnNPCs;
/*      */   private boolean pvpMode;
/*      */   private boolean allowFlight;
/*      */   private String motd;
/*      */   private int E;
/*   77 */   private int F = 0;
/*   78 */   public final long[] g = new long[100];
/*      */   public long[][] h;
/*      */   private KeyPair G;
/*      */   private String H;
/*      */   private String I;
/*      */   private boolean demoMode;
/*      */   private boolean L;
/*      */   private boolean M;
/*   86 */   private String N = "";
/*      */   private boolean O;
/*      */   private long P;
/*      */   private String Q;
/*      */   private boolean R;
/*      */   private boolean S;
/*      */   private final YggdrasilAuthenticationService T;
/*      */   private final MinecraftSessionService U;
/*   94 */   private long V = 0L;
/*      */   
/*      */   private final GameProfileRepository W;
/*      */   
/*      */   private final UserCache X;
/*   99 */   public List<WorldServer> worlds = new ArrayList<WorldServer>();
/*      */   public CraftServer server;
/*      */   public OptionSet options;
/*      */   public ConsoleCommandSender console;
/*      */   public RemoteConsoleCommandSender remoteConsole;
/*      */   public ConsoleReader reader;
/*  105 */   public static int currentTick = 0;
/*      */   public final Thread primaryThread;
/*  107 */   public Queue<Runnable> processQueue = new ConcurrentLinkedQueue<Runnable>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int autosavePeriod;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int TPS = 20;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long SEC_IN_NANO = 1000000000L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long TICK_TIME = 50000000L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MAX_CATCHUP_BUFFER = 60000000000L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SAMPLE_INTERVAL = 20;
/*      */ 
/*      */ 
/*      */   
/*      */   public final RollingAverage tps1;
/*      */ 
/*      */ 
/*      */   
/*      */   public final RollingAverage tps5;
/*      */ 
/*      */ 
/*      */   
/*      */   public final RollingAverage tps15;
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] recentTps;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void a(String s) {
/*  157 */     if (getConvertable().isConvertable(s)) {
/*  158 */       i.info("Converting map!");
/*  159 */       b("menu.convertingLevel");
/*  160 */       getConvertable().convert(s, new ConvertProgressUpdater(this));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected synchronized void b(String s) {
/*  165 */     this.Q = s;
/*      */   }
/*      */   
/*      */   protected void a(String s, String s1, long i, WorldType worldtype, String s2) {
/*  169 */     a(s);
/*  170 */     b("menu.loadingLevel");
/*  171 */     this.worldServer = new WorldServer[3];
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
/*  189 */     int worldCount = 3;
/*      */     
/*  191 */     for (int j = 0; j < worldCount; j++) {
/*      */       WorldServer world;
/*  193 */       int dimension = 0;
/*      */       
/*  195 */       if (j == 1) {
/*  196 */         if (getAllowNether()) {
/*  197 */           dimension = -1;
/*      */         } else {
/*      */           continue;
/*      */         } 
/*      */       }
/*      */       
/*  203 */       if (j == 2) {
/*  204 */         if (this.server.getAllowEnd()) {
/*  205 */           dimension = 1;
/*      */         } else {
/*      */           continue;
/*      */         } 
/*      */       }
/*      */       
/*  211 */       String worldType = World.Environment.getEnvironment(dimension).toString().toLowerCase();
/*  212 */       String name = (dimension == 0) ? s : (s + "_" + worldType);
/*      */       
/*  214 */       ChunkGenerator gen = this.server.getGenerator(name);
/*  215 */       WorldSettings worldsettings = new WorldSettings(i, getGamemode(), getGenerateStructures(), isHardcore(), worldtype);
/*  216 */       worldsettings.a(s2);
/*      */       
/*  218 */       if (j == 0) {
/*  219 */         IDataManager idatamanager = new ServerNBTManager(this.server.getWorldContainer(), s1, true);
/*  220 */         if (R()) {
/*  221 */           world = new DemoWorldServer(this, idatamanager, s1, dimension, this.methodProfiler);
/*      */         } else {
/*      */           
/*  224 */           world = new WorldServer(this, idatamanager, s1, dimension, worldsettings, this.methodProfiler, World.Environment.getEnvironment(dimension), gen);
/*      */         } 
/*  226 */         this.server.scoreboardManager = new CraftScoreboardManager(this, world.getScoreboard());
/*      */       } else {
/*  228 */         String dim = "DIM" + dimension;
/*      */         
/*  230 */         File newWorld = new File(new File(name), dim);
/*  231 */         File oldWorld = new File(new File(s), dim);
/*      */         
/*  233 */         if (!newWorld.isDirectory() && oldWorld.isDirectory()) {
/*  234 */           MinecraftServer.i.info("---- Migration of old " + worldType + " folder required ----");
/*  235 */           MinecraftServer.i.info("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + worldType + " folder to a new location in order to operate correctly.");
/*  236 */           MinecraftServer.i.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
/*  237 */           MinecraftServer.i.info("Attempting to move " + oldWorld + " to " + newWorld + "...");
/*      */           
/*  239 */           if (newWorld.exists()) {
/*  240 */             MinecraftServer.i.warn("A file or folder already exists at " + newWorld + "!");
/*  241 */             MinecraftServer.i.info("---- Migration of old " + worldType + " folder failed ----");
/*  242 */           } else if (newWorld.getParentFile().mkdirs()) {
/*  243 */             if (oldWorld.renameTo(newWorld)) {
/*  244 */               MinecraftServer.i.info("Success! To restore " + worldType + " in the future, simply move " + newWorld + " to " + oldWorld);
/*      */               
/*      */               try {
/*  247 */                 Files.copy(new File(new File(s), "level.dat"), new File(new File(name), "level.dat"));
/*  248 */               } catch (IOException exception) {
/*  249 */                 MinecraftServer.i.warn("Unable to migrate world data.");
/*      */               } 
/*  251 */               MinecraftServer.i.info("---- Migration of old " + worldType + " folder complete ----");
/*      */             } else {
/*  253 */               MinecraftServer.i.warn("Could not move folder " + oldWorld + " to " + newWorld + "!");
/*  254 */               MinecraftServer.i.info("---- Migration of old " + worldType + " folder failed ----");
/*      */             } 
/*      */           } else {
/*  257 */             MinecraftServer.i.warn("Could not create path for " + newWorld + "!");
/*  258 */             MinecraftServer.i.info("---- Migration of old " + worldType + " folder failed ----");
/*      */           } 
/*      */         } 
/*      */         
/*  262 */         IDataManager idatamanager = new ServerNBTManager(this.server.getWorldContainer(), name, true);
/*      */         
/*  264 */         world = new SecondaryWorldServer(this, idatamanager, name, dimension, worldsettings, this.worlds.get(0), this.methodProfiler, World.Environment.getEnvironment(dimension), gen);
/*      */       } 
/*      */       
/*  267 */       if (gen != null) {
/*  268 */         world.getWorld().getPopulators().addAll(gen.getDefaultPopulators((World)world.getWorld()));
/*      */       }
/*      */       
/*  271 */       this.server.getPluginManager().callEvent((Event)new WorldInitEvent((World)world.getWorld()));
/*      */       
/*  273 */       world.addIWorldAccess(new WorldManager(this, world));
/*  274 */       if (!N()) {
/*  275 */         world.getWorldData().setGameType(getGamemode());
/*      */       }
/*      */       
/*  278 */       this.worlds.add(world);
/*  279 */       this.u.setPlayerFileData(this.worlds.<WorldServer>toArray(new WorldServer[this.worlds.size()]));
/*      */       
/*      */       continue;
/*      */     } 
/*  283 */     a(getDifficulty());
/*  284 */     g();
/*      */   }
/*      */   
/*      */   protected void g() {
/*  288 */     boolean flag = true;
/*  289 */     boolean flag1 = true;
/*  290 */     boolean flag2 = true;
/*  291 */     boolean flag3 = true;
/*  292 */     int i = 0;
/*      */     
/*  294 */     b("menu.generatingTerrain");
/*  295 */     byte b0 = 0;
/*      */ 
/*      */     
/*  298 */     for (int m = 0; m < this.worlds.size(); m++) {
/*  299 */       WorldServer worldserver = this.worlds.get(m);
/*  300 */       MinecraftServer.i.info("Preparing start region for level " + m + " (Seed: " + worldserver.getSeed() + ")");
/*  301 */       if (worldserver.getWorld().getKeepSpawnInMemory()) {
/*      */ 
/*      */ 
/*      */         
/*  305 */         ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
/*  306 */         long j = ar();
/*  307 */         i = 0;
/*      */         
/*  309 */         for (int k = -192; k <= 192 && isRunning(); k += 16) {
/*  310 */           for (int l = -192; l <= 192 && isRunning(); l += 16) {
/*  311 */             long i1 = ar();
/*      */             
/*  313 */             if (i1 - j > 1000L) {
/*  314 */               a_("Preparing spawn area", i * 100 / 625);
/*  315 */               j = i1;
/*      */             } 
/*      */             
/*  318 */             i++;
/*  319 */             worldserver.chunkProviderServer.getChunkAt(chunkcoordinates.x + k >> 4, chunkcoordinates.z + l >> 4);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  324 */     for (WorldServer world : this.worlds) {
/*  325 */       this.server.getPluginManager().callEvent((Event)new WorldLoadEvent((World)world.getWorld()));
/*      */     }
/*      */     
/*  328 */     n();
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
/*      */   protected void a_(String s, int i) {
/*  344 */     this.e = s;
/*  345 */     this.f = i;
/*      */     
/*  347 */     MinecraftServer.i.info(s + ": " + i + "%");
/*      */   }
/*      */   
/*      */   protected void n() {
/*  351 */     this.e = null;
/*  352 */     this.f = 0;
/*      */     
/*  354 */     this.server.enablePlugins(PluginLoadOrder.POSTWORLD);
/*      */   }
/*      */   
/*      */   protected void saveChunks(boolean flag) throws ExceptionWorldConflict {
/*  358 */     if (!this.M) {
/*      */ 
/*      */       
/*  361 */       int i = this.worlds.size();
/*      */       
/*  363 */       for (int j = 0; j < i; j++) {
/*  364 */         WorldServer worldserver = this.worlds.get(j);
/*      */         
/*  366 */         if (worldserver != null) {
/*  367 */           if (!flag) {
/*  368 */             MinecraftServer.i.info("Saving chunks for level '" + worldserver.getWorldData().getName() + "'/" + worldserver.worldProvider.getName());
/*      */           }
/*      */           
/*  371 */           worldserver.save(true, (IProgressUpdate)null);
/*  372 */           worldserver.saveLevel();
/*      */           
/*  374 */           WorldSaveEvent event = new WorldSaveEvent((World)worldserver.getWorld());
/*  375 */           this.server.getPluginManager().callEvent((Event)event);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void stop() throws ExceptionWorldConflict {
/*  383 */     if (!this.M) {
/*  384 */       i.info("Stopping server");
/*      */       
/*  386 */       if (this.server != null) {
/*  387 */         this.server.disablePlugins();
/*      */       }
/*      */ 
/*      */       
/*  391 */       if (ai() != null) {
/*  392 */         ai().b();
/*      */       }
/*      */       
/*  395 */       if (this.u != null) {
/*  396 */         i.info("Saving players");
/*  397 */         this.u.savePlayers();
/*  398 */         this.u.u();
/*      */       } 
/*      */       
/*  401 */       if (this.worldServer != null) {
/*  402 */         i.info("Saving worlds");
/*  403 */         saveChunks(false);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  414 */       if (this.l.d()) {
/*  415 */         this.l.e();
/*      */       }
/*      */       
/*  418 */       if (SpigotConfig.saveUserCacheOnStopOnly) {
/*      */         
/*  420 */         i.info("Saving usercache.json");
/*  421 */         this.X.c();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerIp() {
/*  428 */     return this.serverIp;
/*      */   }
/*      */   
/*      */   public void c(String s) {
/*  432 */     this.serverIp = s;
/*      */   }
/*      */   
/*      */   public boolean isRunning() {
/*  436 */     return this.isRunning;
/*      */   }
/*      */   
/*      */   public void safeShutdown() {
/*  440 */     this.isRunning = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MinecraftServer(OptionSet options, Proxy proxy)
/*      */   {
/*  449 */     this.tps1 = new RollingAverage(60);
/*  450 */     this.tps5 = new RollingAverage(300);
/*  451 */     this.tps15 = new RollingAverage(900);
/*  452 */     this.recentTps = new double[3]; ResourceLeakDetector.setEnabled(false); this.X = new UserCache(this, a); j = this; this.d = proxy; this.o = new CommandDispatcher(); this.T = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString()); this.U = this.T.createMinecraftSessionService(); this.W = this.T.createProfileRepository(); this.options = options; if (System.console() == null) { System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal"); Main.useJline = false; }
/*      */      try { this.reader = new ConsoleReader(System.in, System.out); this.reader.setExpandEvents(false); }
/*      */     catch (Throwable e) { try { System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal"); System.setProperty("user.language", "en"); Main.useJline = false; this.reader = new ConsoleReader(System.in, System.out); this.reader.setExpandEvents(false); }
/*      */       catch (IOException ex)
/*      */       { i.warn((String)null, ex); }
/*      */        }
/*  458 */      Runtime.getRuntime().addShutdownHook((Thread)new ServerShutdownThread(this)); this.primaryThread = new ThreadServerApplication(this, "Server thread"); } public static class RollingAverage { private final int size; private int index = 0; private long time; private double total;
/*      */     private final double[] samples;
/*      */     private final long[] times;
/*      */     
/*      */     RollingAverage(int size) {
/*  463 */       this.size = size;
/*  464 */       this.time = size * 1000000000L;
/*  465 */       this.total = (20000000000L * size);
/*  466 */       this.samples = new double[size];
/*  467 */       this.times = new long[size];
/*  468 */       for (int i = 0; i < size; i++) {
/*  469 */         this.samples[i] = 20.0D;
/*  470 */         this.times[i] = 1000000000L;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(double x, long t) {
/*  475 */       this.time -= this.times[this.index];
/*  476 */       this.total -= this.samples[this.index] * this.times[this.index];
/*  477 */       this.samples[this.index] = x;
/*  478 */       this.times[this.index] = t;
/*  479 */       this.time += t;
/*  480 */       this.total += x * t;
/*  481 */       if (++this.index == this.size) {
/*  482 */         this.index = 0;
/*      */       }
/*      */     }
/*      */     
/*      */     public double getAverage() {
/*  487 */       return this.total / this.time;
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   public void run() {
/*      */     try {
/*  494 */       if (init()) {
/*  495 */         long i = ar();
/*  496 */         long j = 0L;
/*      */         
/*  498 */         this.q.setMOTD(new ChatComponentText(this.motd));
/*  499 */         this.q.setServerInfo(new ServerPingServerData("1.7.10", 5));
/*  500 */         a(this.q);
/*      */ 
/*      */ 
/*      */         
/*  504 */         Arrays.fill(this.recentTps, 20.0D);
/*      */         
/*  506 */         long start = System.nanoTime(), lastTick = start - 50000000L, catchupTime = 0L, tickSection = start;
/*      */         
/*  508 */         while (this.isRunning) {
/*  509 */           long curTime = System.nanoTime();
/*      */           
/*  511 */           long wait = 50000000L - curTime - lastTick;
/*  512 */           if (wait > 0L) {
/*  513 */             if (catchupTime < 2000000.0D) {
/*  514 */               wait += Math.abs(catchupTime);
/*      */             }
/*  516 */             if (wait < catchupTime) {
/*  517 */               catchupTime -= wait;
/*  518 */               wait = 0L;
/*  519 */             } else if (catchupTime > 2000000.0D) {
/*  520 */               wait -= catchupTime;
/*  521 */               catchupTime -= catchupTime;
/*      */             } 
/*      */           } 
/*  524 */           if (wait > 0L) {
/*  525 */             Thread.sleep(wait / 1000000L);
/*  526 */             wait = 50000000L - curTime - lastTick;
/*      */           } 
/*      */           
/*  529 */           catchupTime = Math.min(60000000000L, catchupTime - wait);
/*      */ 
/*      */           
/*  532 */           if (++currentTick % 20 == 0) {
/*      */ 
/*      */             
/*  535 */             long diff = curTime - tickSection;
/*  536 */             double currentTps = 1.0E9D / diff * 20.0D;
/*  537 */             this.tps1.add(currentTps, diff);
/*  538 */             this.tps5.add(currentTps, diff);
/*  539 */             this.tps15.add(currentTps, diff);
/*      */             
/*  541 */             this.recentTps[0] = this.tps1.getAverage();
/*  542 */             this.recentTps[1] = this.tps5.getAverage();
/*  543 */             this.recentTps[2] = this.tps15.getAverage();
/*  544 */             tickSection = curTime;
/*      */           } 
/*      */           
/*  547 */           lastTick = curTime;
/*      */           
/*  549 */           u();
/*  550 */           this.O = true;
/*      */         } 
/*      */       } else {
/*      */         
/*  554 */         a((CrashReport)null);
/*      */       } 
/*  556 */     } catch (Throwable throwable) {
/*  557 */       MinecraftServer.i.error("Encountered an unexpected exception", throwable);
/*      */       
/*  559 */       if (throwable.getCause() != null)
/*      */       {
/*  561 */         MinecraftServer.i.error("\tCause of unexpected exception was", throwable.getCause());
/*      */       }
/*      */       
/*  564 */       CrashReport crashreport = null;
/*      */       
/*  566 */       if (throwable instanceof ReportedException) {
/*  567 */         crashreport = b(((ReportedException)throwable).a());
/*      */       } else {
/*  569 */         crashreport = b(new CrashReport("Exception in server tick loop", throwable));
/*      */       } 
/*      */       
/*  572 */       File file1 = new File(new File(s(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
/*      */       
/*  574 */       if (crashreport.a(file1)) {
/*  575 */         MinecraftServer.i.error("This crash report has been saved to: " + file1.getAbsolutePath());
/*      */       } else {
/*  577 */         MinecraftServer.i.error("We were unable to save this crash report to disk.");
/*      */       } 
/*      */       
/*  580 */       a(crashreport);
/*      */     } finally {
/*      */       try {
/*  583 */         WatchdogThread.doStop();
/*  584 */         stop();
/*  585 */         this.isStopped = true;
/*  586 */       } catch (Throwable throwable1) {
/*  587 */         MinecraftServer.i.error("Exception stopping the server", throwable1);
/*      */       } finally {
/*      */         
/*      */         try {
/*  591 */           this.reader.getTerminal().restore();
/*  592 */         } catch (Exception e) {}
/*      */ 
/*      */         
/*  595 */         t();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void a(ServerPing serverping) {
/*  601 */     File file1 = d("server-icon.png");
/*      */     
/*  603 */     if (file1.isFile()) {
/*  604 */       ByteBuf bytebuf = Unpooled.buffer();
/*      */       
/*      */       try {
/*  607 */         BufferedImage bufferedimage = ImageIO.read(file1);
/*      */         
/*  609 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/*  610 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*  611 */         ImageIO.write(bufferedimage, "PNG", (OutputStream)new ByteBufOutputStream(bytebuf));
/*  612 */         ByteBuf bytebuf1 = Base64.encode(bytebuf);
/*      */         
/*  614 */         serverping.setFavicon("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
/*  615 */       } catch (Exception exception) {
/*  616 */         i.error("Couldn't load server icon", exception);
/*      */       } finally {
/*  618 */         bytebuf.release();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected File s() {
/*  624 */     return new File(".");
/*      */   }
/*      */   
/*      */   protected void a(CrashReport crashreport) {}
/*      */   
/*      */   protected void t() {}
/*      */   
/*      */   protected void u() throws ExceptionWorldConflict {
/*  632 */     SpigotTimings.serverTickTimer.startTiming();
/*  633 */     long i = System.nanoTime();
/*      */     
/*  635 */     this.ticks++;
/*  636 */     if (this.R) {
/*  637 */       this.R = false;
/*  638 */       this.methodProfiler.a = true;
/*  639 */       this.methodProfiler.a();
/*      */     } 
/*      */     
/*  642 */     this.methodProfiler.a("root");
/*  643 */     v();
/*  644 */     if (i - this.V >= 5000000000L) {
/*  645 */       this.V = i;
/*  646 */       this.q.setPlayerSample(new ServerPingPlayerSample(D(), C()));
/*  647 */       GameProfile[] agameprofile = new GameProfile[Math.min(C(), 12)];
/*  648 */       int j = MathHelper.nextInt(this.r, 0, C() - agameprofile.length);
/*      */       
/*  650 */       for (int k = 0; k < agameprofile.length; k++) {
/*  651 */         agameprofile[k] = ((EntityPlayer)this.u.players.get(j + k)).getProfile();
/*      */       }
/*      */       
/*  654 */       Collections.shuffle(Arrays.asList((Object[])agameprofile));
/*  655 */       this.q.b().a(agameprofile);
/*      */     } 
/*      */     
/*  658 */     if (this.autosavePeriod > 0 && this.ticks % this.autosavePeriod == 0) {
/*  659 */       SpigotTimings.worldSaveTimer.startTiming();
/*  660 */       this.methodProfiler.a("save");
/*  661 */       this.u.savePlayers();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  666 */       this.server.playerCommandState = true;
/*  667 */       for (World world : this.worlds) {
/*  668 */         world.getWorld().save(true);
/*      */       }
/*  670 */       this.server.playerCommandState = false;
/*      */ 
/*      */       
/*  673 */       this.methodProfiler.b();
/*  674 */       SpigotTimings.worldSaveTimer.stopTiming();
/*      */     } 
/*      */     
/*  677 */     this.methodProfiler.a("tallying");
/*  678 */     this.g[this.ticks % 100] = System.nanoTime() - i;
/*  679 */     this.methodProfiler.b();
/*  680 */     this.methodProfiler.a("snooper");
/*  681 */     if (getSnooperEnabled() && !this.l.d() && this.ticks > 100) {
/*  682 */       this.l.a();
/*      */     }
/*      */     
/*  685 */     if (getSnooperEnabled() && this.ticks % 6000 == 0) {
/*  686 */       this.l.b();
/*      */     }
/*      */     
/*  689 */     this.methodProfiler.b();
/*  690 */     this.methodProfiler.b();
/*  691 */     WatchdogThread.tick();
/*  692 */     SpigotTimings.serverTickTimer.stopTiming();
/*  693 */     CustomTimingsHandler.tick();
/*      */   }
/*      */   
/*      */   public void v() {
/*  697 */     this.methodProfiler.a("levels");
/*      */     
/*  699 */     SpigotTimings.schedulerTimer.startTiming();
/*      */     
/*  701 */     this.server.getScheduler().mainThreadHeartbeat(this.ticks);
/*  702 */     SpigotTimings.schedulerTimer.stopTiming();
/*      */ 
/*      */     
/*  705 */     SpigotTimings.processQueueTimer.startTiming();
/*  706 */     while (!this.processQueue.isEmpty()) {
/*  707 */       ((Runnable)this.processQueue.remove()).run();
/*      */     }
/*  709 */     SpigotTimings.processQueueTimer.stopTiming();
/*      */     
/*  711 */     SpigotTimings.chunkIOTickTimer.startTiming();
/*  712 */     ChunkIOExecutor.tick();
/*  713 */     SpigotTimings.chunkIOTickTimer.stopTiming();
/*      */     
/*  715 */     SpigotTimings.timeUpdateTimer.startTiming();
/*      */     
/*  717 */     if (this.ticks % 20 == 0) {
/*  718 */       for (int j = 0; j < (getPlayerList()).players.size(); j++) {
/*  719 */         EntityPlayer entityplayer = (getPlayerList()).players.get(j);
/*  720 */         entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(entityplayer.world.getTime(), entityplayer.getPlayerTime(), entityplayer.world.getGameRules().getBoolean("doDaylightCycle")));
/*      */       } 
/*      */     }
/*  723 */     SpigotTimings.timeUpdateTimer.stopTiming();
/*      */     
/*      */     int i;
/*      */     
/*  727 */     for (i = 0; i < this.worlds.size(); i++) {
/*  728 */       long j = System.nanoTime();
/*      */ 
/*      */       
/*  731 */       WorldServer worldserver = this.worlds.get(i);
/*      */       
/*  733 */       this.methodProfiler.a(worldserver.getWorldData().getName());
/*  734 */       this.methodProfiler.a("pools");
/*  735 */       this.methodProfiler.b();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  744 */       this.methodProfiler.a("tick");
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  749 */         worldserver.timings.doTick.startTiming();
/*  750 */         worldserver.doTick();
/*  751 */         worldserver.timings.doTick.stopTiming();
/*  752 */       } catch (Throwable throwable) {
/*      */         CrashReport crashreport;
/*      */         try {
/*  755 */           crashreport = CrashReport.a(throwable, "Exception ticking world");
/*  756 */         } catch (Throwable t) {
/*  757 */           throw new RuntimeException("Error generating crash report", t);
/*      */         } 
/*      */         
/*  760 */         worldserver.a(crashreport);
/*  761 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*      */       try {
/*  765 */         worldserver.timings.tickEntities.startTiming();
/*  766 */         worldserver.tickEntities();
/*  767 */         worldserver.timings.tickEntities.stopTiming();
/*  768 */       } catch (Throwable throwable1) {
/*      */         CrashReport crashreport;
/*      */         try {
/*  771 */           crashreport = CrashReport.a(throwable1, "Exception ticking world entities");
/*  772 */         } catch (Throwable t) {
/*  773 */           throw new RuntimeException("Error generating crash report", t);
/*      */         } 
/*      */         
/*  776 */         worldserver.a(crashreport);
/*  777 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  780 */       this.methodProfiler.b();
/*  781 */       this.methodProfiler.a("tracker");
/*  782 */       worldserver.timings.tracker.startTiming();
/*  783 */       worldserver.getTracker().updatePlayers();
/*  784 */       worldserver.timings.tracker.stopTiming();
/*  785 */       this.methodProfiler.b();
/*  786 */       this.methodProfiler.b();
/*  787 */       worldserver.explosionDensityCache.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  793 */     this.methodProfiler.c("connection");
/*  794 */     SpigotTimings.connectionTimer.startTiming();
/*  795 */     ai().c();
/*  796 */     SpigotTimings.connectionTimer.stopTiming();
/*  797 */     this.methodProfiler.c("players");
/*  798 */     SpigotTimings.playerListTimer.startTiming();
/*  799 */     this.u.tick();
/*  800 */     SpigotTimings.playerListTimer.stopTiming();
/*  801 */     this.methodProfiler.c("tickables");
/*      */     
/*  803 */     SpigotTimings.tickablesTimer.startTiming();
/*  804 */     for (i = 0; i < this.n.size(); i++) {
/*  805 */       ((IUpdatePlayerListBox)this.n.get(i)).a();
/*      */     }
/*  807 */     SpigotTimings.tickablesTimer.stopTiming();
/*      */     
/*  809 */     this.methodProfiler.b();
/*      */   }
/*      */   
/*      */   public boolean getAllowNether() {
/*  813 */     return true;
/*      */   }
/*      */   
/*      */   public void a(IUpdatePlayerListBox iupdateplayerlistbox) {
/*  817 */     this.n.add(iupdateplayerlistbox);
/*      */   }
/*      */   
/*      */   public static void main(OptionSet options) {
/*  821 */     DispenserRegistry.b();
/*  822 */     ProtocolInjector.inject();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  898 */       DedicatedServer dedicatedserver = new DedicatedServer(options);
/*      */       
/*  900 */       if (options.has("port")) {
/*  901 */         int port = ((Integer)options.valueOf("port")).intValue();
/*  902 */         if (port > 0) {
/*  903 */           dedicatedserver.setPort(port);
/*      */         }
/*      */       } 
/*      */       
/*  907 */       if (options.has("universe")) {
/*  908 */         dedicatedserver.universe = (File)options.valueOf("universe");
/*      */       }
/*      */       
/*  911 */       if (options.has("world")) {
/*  912 */         dedicatedserver.k((String)options.valueOf("world"));
/*      */       }
/*      */       
/*  915 */       dedicatedserver.primaryThread.start();
/*      */     
/*      */     }
/*  918 */     catch (Exception exception) {
/*  919 */       i.fatal("Failed to start the minecraft server", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void x() {}
/*      */ 
/*      */   
/*      */   public File d(String s) {
/*  928 */     return new File(s(), s);
/*      */   }
/*      */   
/*      */   public void info(String s) {
/*  932 */     i.info(s);
/*      */   }
/*      */   
/*      */   public void warning(String s) {
/*  936 */     i.warn(s);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldServer getWorldServer(int i) {
/*  941 */     for (WorldServer world : this.worlds) {
/*  942 */       if (world.dimension == i) {
/*  943 */         return world;
/*      */       }
/*      */     } 
/*      */     
/*  947 */     return this.worlds.get(0);
/*      */   }
/*      */ 
/*      */   
/*      */   public String y() {
/*  952 */     return this.serverIp;
/*      */   }
/*      */   
/*      */   public int z() {
/*  956 */     return this.t;
/*      */   }
/*      */   
/*      */   public String A() {
/*  960 */     return this.motd;
/*      */   }
/*      */   
/*      */   public String getVersion() {
/*  964 */     return "1.7.10";
/*      */   }
/*      */   
/*      */   public int C() {
/*  968 */     return this.u.getPlayerCount();
/*      */   }
/*      */   
/*      */   public int D() {
/*  972 */     return this.u.getMaxPlayers();
/*      */   }
/*      */   
/*      */   public String[] getPlayers() {
/*  976 */     return this.u.f();
/*      */   }
/*      */   
/*      */   public GameProfile[] F() {
/*  980 */     return this.u.g();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPlugins() {
/*  985 */     StringBuilder result = new StringBuilder();
/*  986 */     Plugin[] plugins = this.server.getPluginManager().getPlugins();
/*      */     
/*  988 */     result.append(this.server.getName());
/*  989 */     result.append(" on Bukkit ");
/*  990 */     result.append(this.server.getBukkitVersion());
/*      */     
/*  992 */     if (plugins.length > 0 && this.server.getQueryPlugins()) {
/*  993 */       result.append(": ");
/*      */       
/*  995 */       for (int i = 0; i < plugins.length; i++) {
/*  996 */         if (i > 0) {
/*  997 */           result.append("; ");
/*      */         }
/*      */         
/* 1000 */         result.append(plugins[i].getDescription().getName());
/* 1001 */         result.append(" ");
/* 1002 */         result.append(plugins[i].getDescription().getVersion().replaceAll(";", ","));
/*      */       } 
/*      */     } 
/*      */     
/* 1006 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String g(final String s) {
/* 1012 */     Waitable<String> waitable = new Waitable<String>()
/*      */       {
/*      */         protected String evaluate() {
/* 1015 */           RemoteControlCommandListener.instance.e();
/*      */           
/* 1017 */           RemoteServerCommandEvent event = new RemoteServerCommandEvent((CommandSender)MinecraftServer.this.remoteConsole, s);
/* 1018 */           MinecraftServer.this.server.getPluginManager().callEvent((Event)event);
/*      */           
/* 1020 */           ServerCommand servercommand = new ServerCommand(event.getCommand(), RemoteControlCommandListener.instance);
/* 1021 */           MinecraftServer.this.server.dispatchServerCommand((CommandSender)MinecraftServer.this.remoteConsole, servercommand);
/*      */           
/* 1023 */           return RemoteControlCommandListener.instance.f(); }
/*      */       };
/* 1025 */     this.processQueue.add(waitable);
/*      */     try {
/* 1027 */       return (String)waitable.get();
/* 1028 */     } catch (ExecutionException e) {
/* 1029 */       throw new RuntimeException("Exception processing rcon command " + s, e.getCause());
/* 1030 */     } catch (InterruptedException e) {
/* 1031 */       Thread.currentThread().interrupt();
/* 1032 */       throw new RuntimeException("Interrupted processing rcon command " + s, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDebugging() {
/* 1038 */     return getPropertyManager().getBoolean("debug", false);
/*      */   }
/*      */   
/*      */   public void h(String s) {
/* 1042 */     i.error(s);
/*      */   }
/*      */   
/*      */   public void i(String s) {
/* 1046 */     if (isDebugging()) {
/* 1047 */       i.info(s);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getServerModName() {
/* 1052 */     return "PaperSpigot";
/*      */   }
/*      */   
/*      */   public CrashReport b(CrashReport crashreport) {
/* 1056 */     crashreport.g().a("Profiler Position", new CrashReportProfilerPosition(this));
/* 1057 */     if (this.worlds != null && this.worlds.size() > 0 && this.worlds.get(0) != null) {
/* 1058 */       crashreport.g().a("Vec3 Pool Size", new CrashReportVec3DPoolSize(this));
/*      */     }
/*      */     
/* 1061 */     if (this.u != null) {
/* 1062 */       crashreport.g().a("Player Count", new CrashReportPlayerCount(this));
/*      */     }
/*      */     
/* 1065 */     return crashreport;
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
/*      */   public List a(ICommandListener icommandlistener, String s) {
/* 1110 */     return this.server.tabComplete(icommandlistener, s);
/*      */   }
/*      */ 
/*      */   
/*      */   public static MinecraftServer getServer() {
/* 1115 */     return j;
/*      */   }
/*      */   
/*      */   public String getName() {
/* 1119 */     return "Server";
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent ichatbasecomponent) {
/* 1123 */     i.info(ichatbasecomponent.c());
/*      */   }
/*      */   
/*      */   public boolean a(int i, String s) {
/* 1127 */     return true;
/*      */   }
/*      */   
/*      */   public ICommandHandler getCommandHandler() {
/* 1131 */     return this.o;
/*      */   }
/*      */   
/*      */   public KeyPair K() {
/* 1135 */     return this.G;
/*      */   }
/*      */   
/*      */   public int L() {
/* 1139 */     return this.t;
/*      */   }
/*      */   
/*      */   public void setPort(int i) {
/* 1143 */     this.t = i;
/*      */   }
/*      */   
/*      */   public String M() {
/* 1147 */     return this.H;
/*      */   }
/*      */   
/*      */   public void j(String s) {
/* 1151 */     this.H = s;
/*      */   }
/*      */   
/*      */   public boolean N() {
/* 1155 */     return (this.H != null);
/*      */   }
/*      */   
/*      */   public String O() {
/* 1159 */     return this.I;
/*      */   }
/*      */   
/*      */   public void k(String s) {
/* 1163 */     this.I = s;
/*      */   }
/*      */   
/*      */   public void a(KeyPair keypair) {
/* 1167 */     this.G = keypair;
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(EnumDifficulty enumdifficulty) {
/* 1172 */     for (int j = 0; j < this.worlds.size(); j++) {
/* 1173 */       WorldServer worldserver = this.worlds.get(j);
/*      */ 
/*      */       
/* 1176 */       if (worldserver != null) {
/* 1177 */         if (worldserver.getWorldData().isHardcore()) {
/* 1178 */           worldserver.difficulty = EnumDifficulty.HARD;
/* 1179 */           worldserver.setSpawnFlags(true, true);
/* 1180 */         } else if (N()) {
/* 1181 */           worldserver.difficulty = enumdifficulty;
/* 1182 */           worldserver.setSpawnFlags((worldserver.difficulty != EnumDifficulty.PEACEFUL), true);
/*      */         } else {
/* 1184 */           worldserver.difficulty = enumdifficulty;
/* 1185 */           worldserver.setSpawnFlags(getSpawnMonsters(), this.spawnAnimals);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean getSpawnMonsters() {
/* 1192 */     return true;
/*      */   }
/*      */   
/*      */   public boolean R() {
/* 1196 */     return this.demoMode;
/*      */   }
/*      */   
/*      */   public void b(boolean flag) {
/* 1200 */     this.demoMode = flag;
/*      */   }
/*      */   
/*      */   public void c(boolean flag) {
/* 1204 */     this.L = flag;
/*      */   }
/*      */   
/*      */   public Convertable getConvertable() {
/* 1208 */     return this.convertable;
/*      */   }
/*      */   
/*      */   public void U() {
/* 1212 */     this.M = true;
/* 1213 */     getConvertable().d();
/*      */ 
/*      */     
/* 1216 */     for (int i = 0; i < this.worlds.size(); i++) {
/* 1217 */       WorldServer worldserver = this.worlds.get(i);
/*      */ 
/*      */       
/* 1220 */       if (worldserver != null) {
/* 1221 */         worldserver.saveLevel();
/*      */       }
/*      */     } 
/*      */     
/* 1225 */     getConvertable().e(((WorldServer)this.worlds.get(0)).getDataManager().g());
/* 1226 */     safeShutdown();
/*      */   }
/*      */   
/*      */   public String getResourcePack() {
/* 1230 */     return this.N;
/*      */   }
/*      */   
/*      */   public void setTexturePack(String s) {
/* 1234 */     this.N = s;
/*      */   }
/*      */   
/*      */   public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
/* 1238 */     mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(false));
/* 1239 */     mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf(0));
/* 1240 */     mojangstatisticsgenerator.a("players_current", Integer.valueOf(C()));
/* 1241 */     mojangstatisticsgenerator.a("players_max", Integer.valueOf(D()));
/* 1242 */     mojangstatisticsgenerator.a("players_seen", Integer.valueOf((this.u.getSeenPlayers()).length));
/* 1243 */     mojangstatisticsgenerator.a("uses_auth", Boolean.valueOf(this.onlineMode));
/* 1244 */     mojangstatisticsgenerator.a("gui_state", ak() ? "enabled" : "disabled");
/* 1245 */     mojangstatisticsgenerator.a("run_time", Long.valueOf((ar() - mojangstatisticsgenerator.g()) / 60L * 1000L));
/* 1246 */     mojangstatisticsgenerator.a("avg_tick_ms", Integer.valueOf((int)(MathHelper.a(this.g) * 1.0E-6D)));
/* 1247 */     int i = 0;
/*      */ 
/*      */     
/* 1250 */     for (int j = 0; j < this.worlds.size(); j++) {
/* 1251 */       WorldServer worldserver = this.worlds.get(j);
/* 1252 */       if (this.worldServer != null) {
/*      */         
/* 1254 */         WorldData worlddata = worldserver.getWorldData();
/*      */         
/* 1256 */         mojangstatisticsgenerator.a("world[" + i + "][dimension]", Integer.valueOf(worldserver.worldProvider.dimension));
/* 1257 */         mojangstatisticsgenerator.a("world[" + i + "][mode]", worlddata.getGameType());
/* 1258 */         mojangstatisticsgenerator.a("world[" + i + "][difficulty]", worldserver.difficulty);
/* 1259 */         mojangstatisticsgenerator.a("world[" + i + "][hardcore]", Boolean.valueOf(worlddata.isHardcore()));
/* 1260 */         mojangstatisticsgenerator.a("world[" + i + "][generator_name]", worlddata.getType().name());
/* 1261 */         mojangstatisticsgenerator.a("world[" + i + "][generator_version]", Integer.valueOf(worlddata.getType().getVersion()));
/* 1262 */         mojangstatisticsgenerator.a("world[" + i + "][height]", Integer.valueOf(this.E));
/* 1263 */         mojangstatisticsgenerator.a("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.L().getLoadedChunks()));
/* 1264 */         i++;
/*      */       } 
/*      */     } 
/*      */     
/* 1268 */     mojangstatisticsgenerator.a("worlds", Integer.valueOf(i));
/*      */   }
/*      */   
/*      */   public void b(MojangStatisticsGenerator mojangstatisticsgenerator) {
/* 1272 */     mojangstatisticsgenerator.b("singleplayer", Boolean.valueOf(N()));
/* 1273 */     mojangstatisticsgenerator.b("server_brand", getServerModName());
/* 1274 */     mojangstatisticsgenerator.b("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
/* 1275 */     mojangstatisticsgenerator.b("dedicated", Boolean.valueOf(X()));
/*      */   }
/*      */   
/*      */   public boolean getSnooperEnabled() {
/* 1279 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOnlineMode() {
/* 1285 */     return this.server.getOnlineMode();
/*      */   }
/*      */   
/*      */   public void setOnlineMode(boolean flag) {
/* 1289 */     this.onlineMode = flag;
/*      */   }
/*      */   
/*      */   public boolean getSpawnAnimals() {
/* 1293 */     return this.spawnAnimals;
/*      */   }
/*      */   
/*      */   public void setSpawnAnimals(boolean flag) {
/* 1297 */     this.spawnAnimals = flag;
/*      */   }
/*      */   
/*      */   public boolean getSpawnNPCs() {
/* 1301 */     return this.spawnNPCs;
/*      */   }
/*      */   
/*      */   public void setSpawnNPCs(boolean flag) {
/* 1305 */     this.spawnNPCs = flag;
/*      */   }
/*      */   
/*      */   public boolean getPvP() {
/* 1309 */     return this.pvpMode;
/*      */   }
/*      */   
/*      */   public void setPvP(boolean flag) {
/* 1313 */     this.pvpMode = flag;
/*      */   }
/*      */   
/*      */   public boolean getAllowFlight() {
/* 1317 */     return this.allowFlight;
/*      */   }
/*      */   
/*      */   public void setAllowFlight(boolean flag) {
/* 1321 */     this.allowFlight = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMotd() {
/* 1327 */     return this.motd;
/*      */   }
/*      */   
/*      */   public void setMotd(String s) {
/* 1331 */     this.motd = s;
/*      */   }
/*      */   
/*      */   public int getMaxBuildHeight() {
/* 1335 */     return this.E;
/*      */   }
/*      */   
/*      */   public void c(int i) {
/* 1339 */     this.E = i;
/*      */   }
/*      */   
/*      */   public boolean isStopped() {
/* 1343 */     return this.isStopped;
/*      */   }
/*      */   
/*      */   public PlayerList getPlayerList() {
/* 1347 */     return this.u;
/*      */   }
/*      */   
/*      */   public void a(PlayerList playerlist) {
/* 1351 */     this.u = playerlist;
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(EnumGamemode enumgamemode) {
/* 1356 */     for (int i = 0; i < this.worlds.size(); i++) {
/* 1357 */       ((WorldServer)(getServer()).worlds.get(i)).getWorldData().setGameType(enumgamemode);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServerConnection getServerConnection() {
/* 1365 */     return this.p;
/*      */   }
/*      */   
/*      */   public ServerConnection ai() {
/* 1369 */     return (this.p == null) ? (this.p = new ServerConnection(this)) : this.p;
/*      */   }
/*      */   
/*      */   public boolean ak() {
/* 1373 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int al() {
/* 1379 */     return this.ticks;
/*      */   }
/*      */   
/*      */   public void am() {
/* 1383 */     this.R = true;
/*      */   }
/*      */   
/*      */   public ChunkCoordinates getChunkCoordinates() {
/* 1387 */     return new ChunkCoordinates(0, 0, 0);
/*      */   }
/*      */   
/*      */   public World getWorld() {
/* 1391 */     return this.worlds.get(0);
/*      */   }
/*      */   
/*      */   public int getSpawnProtection() {
/* 1395 */     return 16;
/*      */   }
/*      */   
/*      */   public boolean a(World world, int i, int j, int k, EntityHuman entityhuman) {
/* 1399 */     return false;
/*      */   }
/*      */   
/*      */   public void setForceGamemode(boolean flag) {
/* 1403 */     this.S = flag;
/*      */   }
/*      */   
/*      */   public boolean getForceGamemode() {
/* 1407 */     return this.S;
/*      */   }
/*      */   
/*      */   public Proxy aq() {
/* 1411 */     return this.d;
/*      */   }
/*      */   
/*      */   public static long ar() {
/* 1415 */     return System.currentTimeMillis();
/*      */   }
/*      */   
/*      */   public int getIdleTimeout() {
/* 1419 */     return this.F;
/*      */   }
/*      */   
/*      */   public void setIdleTimeout(int i) {
/* 1423 */     this.F = i;
/*      */   }
/*      */   
/*      */   public IChatBaseComponent getScoreboardDisplayName() {
/* 1427 */     return new ChatComponentText(getName());
/*      */   }
/*      */   
/*      */   public boolean at() {
/* 1431 */     return true;
/*      */   }
/*      */   
/*      */   public MinecraftSessionService av() {
/* 1435 */     return this.U;
/*      */   }
/*      */   
/*      */   public GameProfileRepository getGameProfileRepository() {
/* 1439 */     return this.W;
/*      */   }
/*      */   
/*      */   public UserCache getUserCache() {
/* 1443 */     return this.X;
/*      */   }
/*      */   
/*      */   public ServerPing ay() {
/* 1447 */     return this.q;
/*      */   }
/*      */   
/*      */   public void az() {
/* 1451 */     this.V = 0L;
/*      */   }
/*      */   
/*      */   public static Logger getLogger() {
/* 1455 */     return i;
/*      */   }
/*      */   
/*      */   public static PlayerList a(MinecraftServer minecraftserver) {
/* 1459 */     return minecraftserver.u;
/*      */   }
/*      */   
/*      */   public abstract PropertyManager getPropertyManager();
/*      */   
/*      */   protected abstract boolean init() throws UnknownHostException;
/*      */   
/*      */   public abstract boolean getGenerateStructures();
/*      */   
/*      */   public abstract EnumGamemode getGamemode();
/*      */   
/*      */   public abstract EnumDifficulty getDifficulty();
/*      */   
/*      */   public abstract boolean isHardcore();
/*      */   
/*      */   public abstract int l();
/*      */   
/*      */   public abstract boolean m();
/*      */   
/*      */   public abstract boolean X();
/*      */   
/*      */   public abstract boolean getEnableCommandBlock();
/*      */   
/*      */   public abstract String a(EnumGamemode paramEnumGamemode, boolean paramBoolean);
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MinecraftServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */