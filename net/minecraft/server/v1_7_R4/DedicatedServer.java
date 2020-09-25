/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.LoggerOutputStream;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.SpigotTimings;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.command.CraftRemoteConsoleCommandSender;
/*     */ import org.bukkit.event.server.ServerCommandEvent;
/*     */ import org.bukkit.plugin.PluginLoadOrder;
/*     */ import org.github.paperspigot.PaperSpigotConfig;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class DedicatedServer extends MinecraftServer implements IMinecraftServer {
/*  28 */   private static final Logger i = LogManager.getLogger();
/*  29 */   private final List j = Collections.synchronizedList(new ArrayList());
/*     */   
/*     */   private RemoteStatusListener k;
/*     */   private RemoteControlListener l;
/*     */   public PropertyManager propertyManager;
/*     */   private EULA n;
/*     */   private boolean generateStructures;
/*     */   private EnumGamemode p;
/*     */   private boolean q;
/*     */   
/*     */   public DedicatedServer(OptionSet options) {
/*  40 */     super(options, Proxy.NO_PROXY);
/*     */ 
/*     */     
/*  43 */     new ThreadSleepForever(this, "Server Infinisleeper");
/*     */   }
/*     */   
/*     */   protected boolean init() throws UnknownHostException {
/*  47 */     ThreadCommandReader threadcommandreader = new ThreadCommandReader(this, "Server console handler");
/*     */     
/*  49 */     threadcommandreader.setDaemon(true);
/*  50 */     threadcommandreader.start();
/*     */ 
/*     */     
/*  53 */     Logger global = Logger.getLogger("");
/*  54 */     global.setUseParentHandlers(false);
/*  55 */     for (Handler handler : global.getHandlers()) {
/*  56 */       global.removeHandler(handler);
/*     */     }
/*  58 */     global.addHandler((Handler)new ForwardLogHandler());
/*     */     
/*  60 */     Logger logger = (Logger)LogManager.getRootLogger();
/*  61 */     for (Appender appender : logger.getAppenders().values()) {
/*  62 */       if (appender instanceof org.apache.logging.log4j.core.appender.ConsoleAppender) {
/*  63 */         logger.removeAppender(appender);
/*     */       }
/*     */     } 
/*     */     
/*  67 */     (new Thread((Runnable)new TerminalConsoleWriterThread(System.out, this.reader))).start();
/*     */     
/*  69 */     System.setOut(new PrintStream((OutputStream)new LoggerOutputStream((Logger)logger, Level.INFO), true));
/*  70 */     System.setErr(new PrintStream((OutputStream)new LoggerOutputStream((Logger)logger, Level.WARN), true));
/*     */ 
/*     */     
/*  73 */     i.info("Starting minecraft server version 1.7.10");
/*  74 */     if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
/*  75 */       i.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
/*     */     }
/*     */     
/*  78 */     i.info("Loading properties");
/*  79 */     this.propertyManager = new PropertyManager(this.options);
/*     */     
/*  81 */     File EULALock = new File(".eula-lock");
/*     */     
/*  83 */     boolean eulaAgreed = Boolean.getBoolean("com.mojang.eula.agree");
/*  84 */     if (eulaAgreed) {
/*     */       
/*  86 */       System.err.println("You have used the Spigot command line EULA agreement flag.");
/*  87 */       System.err.println("By using this setting you are indicating your agreement to Mojang's EULA (https://account.mojang.com/documents/minecraft_eula).");
/*  88 */       System.err.println("If you do not agree to the above EULA please stop your server and remove this flag immediately.");
/*     */     } else {
/*     */       
/*  91 */       if (!EULALock.exists()) {
/*  92 */         System.err.println("WARNING: By using this server you are indicating your agreement to Mojang's EULA (https://account.mojang.com/documents/minecraft_eula)");
/*  93 */         System.err.println("If you do not agree to the above EULA please stop this server and remove it from your system immediately.");
/*  94 */         System.err.println("The server will start in 10 seconds, you will only see this message and have to wait this one time.");
/*     */         try {
/*  96 */           EULALock.createNewFile();
/*  97 */         } catch (IOException e1) {
/*  98 */           System.err.println("Unable to create EULA lock file");
/*  99 */           e1.printStackTrace();
/*     */         } 
/*     */         try {
/* 102 */           Thread.sleep(TimeUnit.SECONDS.toMillis(10L));
/* 103 */         } catch (InterruptedException ex) {}
/*     */       } 
/*     */ 
/*     */       
/* 107 */       if (N()) {
/* 108 */         c("127.0.0.1");
/*     */       } else {
/* 110 */         setOnlineMode(this.propertyManager.getBoolean("online-mode", true));
/* 111 */         c(this.propertyManager.getString("server-ip", ""));
/*     */       } 
/*     */       
/* 114 */       setSpawnAnimals(this.propertyManager.getBoolean("spawn-animals", true));
/* 115 */       setSpawnNPCs(this.propertyManager.getBoolean("spawn-npcs", true));
/* 116 */       setPvP(this.propertyManager.getBoolean("pvp", true));
/* 117 */       setAllowFlight(this.propertyManager.getBoolean("allow-flight", false));
/* 118 */       setTexturePack(this.propertyManager.getString("resource-pack", ""));
/* 119 */       setMotd(this.propertyManager.getString("motd", "A Minecraft Server"));
/* 120 */       setForceGamemode(this.propertyManager.getBoolean("force-gamemode", false));
/* 121 */       setIdleTimeout(this.propertyManager.getInt("player-idle-timeout", 0));
/* 122 */       if (this.propertyManager.getInt("difficulty", 1) < 0) {
/* 123 */         this.propertyManager.setProperty("difficulty", Integer.valueOf(0));
/* 124 */       } else if (this.propertyManager.getInt("difficulty", 1) > 3) {
/* 125 */         this.propertyManager.setProperty("difficulty", Integer.valueOf(3));
/*     */       } 
/*     */       
/* 128 */       this.generateStructures = this.propertyManager.getBoolean("generate-structures", true);
/* 129 */       int gamemode = this.propertyManager.getInt("gamemode", EnumGamemode.SURVIVAL.getId());
/*     */       
/* 131 */       this.p = WorldSettings.a(gamemode);
/* 132 */       i.info("Default game type: " + this.p);
/* 133 */       InetAddress inetaddress = null;
/*     */       
/* 135 */       if (getServerIp().length() > 0) {
/* 136 */         inetaddress = InetAddress.getByName(getServerIp());
/*     */       }
/*     */       
/* 139 */       if (L() < 0) {
/* 140 */         setPort(this.propertyManager.getInt("server-port", 25565));
/*     */       }
/*     */       
/* 143 */       a(new DedicatedPlayerList(this));
/* 144 */       SpigotConfig.init();
/* 145 */       SpigotConfig.registerCommands();
/*     */ 
/*     */       
/* 148 */       PaperSpigotConfig.init();
/* 149 */       PaperSpigotConfig.registerCommands();
/*     */ 
/*     */       
/* 152 */       i.info("Generating keypair");
/* 153 */       a(MinecraftEncryption.b());
/* 154 */       i.info("Starting Minecraft server on " + ((getServerIp().length() == 0) ? "*" : getServerIp()) + ":" + L());
/*     */       
/* 156 */       if (!SpigotConfig.lateBind) {
/*     */         try {
/* 158 */           ai().a(inetaddress, L());
/* 159 */         } catch (Throwable ioexception) {
/* 160 */           i.warn("**** FAILED TO BIND TO PORT!");
/* 161 */           i.warn("The exception was: {}", new Object[] { ioexception.toString() });
/* 162 */           i.warn("Perhaps a server is already running on that port?");
/* 163 */           return false;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 169 */       this.server.loadPlugins();
/* 170 */       this.server.enablePlugins(PluginLoadOrder.STARTUP);
/*     */ 
/*     */       
/* 173 */       if (!getOnlineMode()) {
/* 174 */         i.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
/* 175 */         i.warn("The server will make no attempt to authenticate usernames. Beware.");
/* 176 */         i.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
/* 177 */         i.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
/*     */       } 
/*     */       
/* 180 */       if (aE()) {
/* 181 */         getUserCache().c();
/*     */       }
/*     */       
/* 184 */       if (!NameReferencingFileConverter.a(this.propertyManager)) {
/* 185 */         return false;
/*     */       }
/*     */       
/* 188 */       this.convertable = new WorldLoaderServer(this.server.getWorldContainer());
/* 189 */       long j = System.nanoTime();
/*     */       
/* 191 */       if (O() == null) {
/* 192 */         k(this.propertyManager.getString("level-name", "world"));
/*     */       }
/*     */       
/* 195 */       String s = this.propertyManager.getString("level-seed", "");
/* 196 */       String s1 = this.propertyManager.getString("level-type", "DEFAULT");
/* 197 */       String s2 = this.propertyManager.getString("generator-settings", "");
/* 198 */       long k = (new Random()).nextLong();
/*     */       
/* 200 */       if (s.length() > 0) {
/*     */         try {
/* 202 */           long l = Long.parseLong(s);
/*     */           
/* 204 */           if (l != 0L) {
/* 205 */             k = l;
/*     */           }
/* 207 */         } catch (NumberFormatException numberformatexception) {
/* 208 */           k = s.hashCode();
/*     */         } 
/*     */       }
/*     */       
/* 212 */       WorldType worldtype = WorldType.getType(s1);
/*     */       
/* 214 */       if (worldtype == null) {
/* 215 */         worldtype = WorldType.NORMAL;
/*     */       }
/*     */       
/* 218 */       at();
/* 219 */       getEnableCommandBlock();
/* 220 */       l();
/* 221 */       getSnooperEnabled();
/* 222 */       c(this.propertyManager.getInt("max-build-height", 256));
/* 223 */       c((getMaxBuildHeight() + 8) / 16 * 16);
/* 224 */       c(MathHelper.a(getMaxBuildHeight(), 64, 256));
/* 225 */       this.propertyManager.setProperty("max-build-height", Integer.valueOf(getMaxBuildHeight()));
/* 226 */       i.info("Preparing level \"" + O() + "\"");
/* 227 */       a(O(), O(), k, worldtype, s2);
/* 228 */       long i1 = System.nanoTime() - j;
/* 229 */       String s3 = String.format("%.3fs", new Object[] { Double.valueOf(i1 / 1.0E9D) });
/*     */       
/* 231 */       i.info("Done (" + s3 + ")! For help, type \"help\" or \"?\"");
/* 232 */       if (this.propertyManager.getBoolean("enable-query", false)) {
/* 233 */         i.info("Starting GS4 status listener");
/* 234 */         this.k = new RemoteStatusListener(this);
/* 235 */         this.k.a();
/*     */       } 
/*     */       
/* 238 */       if (this.propertyManager.getBoolean("enable-rcon", false)) {
/* 239 */         i.info("Starting remote control listener");
/* 240 */         this.l = new RemoteControlListener(this);
/* 241 */         this.l.a();
/* 242 */         this.remoteConsole = (RemoteConsoleCommandSender)new CraftRemoteConsoleCommandSender();
/*     */       } 
/*     */ 
/*     */       
/* 246 */       if (this.server.getBukkitSpawnRadius() > -1) {
/* 247 */         i.info("'settings.spawn-radius' in bukkit.yml has been moved to 'spawn-protection' in server.properties. I will move your config for you.");
/* 248 */         this.propertyManager.properties.remove("spawn-protection");
/* 249 */         this.propertyManager.getInt("spawn-protection", this.server.getBukkitSpawnRadius());
/* 250 */         this.server.removeBukkitSpawnRadius();
/* 251 */         this.propertyManager.savePropertiesFile();
/*     */       } 
/*     */ 
/*     */       
/* 255 */       if (SpigotConfig.lateBind) {
/*     */         try {
/* 257 */           ai().a(inetaddress, L());
/* 258 */         } catch (Throwable ioexception) {
/* 259 */           i.warn("**** FAILED TO BIND TO PORT!");
/* 260 */           i.warn("The exception was: {}", new Object[] { ioexception.toString() });
/* 261 */           i.warn("Perhaps a server is already running on that port?");
/* 262 */           return false;
/*     */         } 
/*     */       }
/* 265 */       return true;
/*     */     } 
/*     */     
/* 268 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyManager getPropertyManager() {
/* 273 */     return this.propertyManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getGenerateStructures() {
/* 278 */     return this.generateStructures;
/*     */   }
/*     */   
/*     */   public EnumGamemode getGamemode() {
/* 282 */     return this.p;
/*     */   }
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 286 */     return EnumDifficulty.getById(this.propertyManager.getInt("difficulty", 1));
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/* 290 */     return this.propertyManager.getBoolean("hardcore", false);
/*     */   }
/*     */   
/*     */   protected void a(CrashReport crashreport) {}
/*     */   
/*     */   public CrashReport b(CrashReport crashreport) {
/* 296 */     crashreport = super.b(crashreport);
/* 297 */     crashreport.g().a("Is Modded", new CrashReportModded(this));
/* 298 */     crashreport.g().a("Type", new CrashReportType(this));
/* 299 */     return crashreport;
/*     */   }
/*     */   
/*     */   protected void t() {
/* 303 */     System.exit(0);
/*     */   }
/*     */   
/*     */   public void v() {
/* 307 */     super.v();
/* 308 */     aB();
/*     */   }
/*     */   
/*     */   public boolean getAllowNether() {
/* 312 */     return this.propertyManager.getBoolean("allow-nether", true);
/*     */   }
/*     */   
/*     */   public boolean getSpawnMonsters() {
/* 316 */     return this.propertyManager.getBoolean("spawn-monsters", true);
/*     */   }
/*     */   
/*     */   public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
/* 320 */     mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(aC().getHasWhitelist()));
/* 321 */     mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf((aC().getWhitelisted()).length));
/* 322 */     super.a(mojangstatisticsgenerator);
/*     */   }
/*     */   
/*     */   public boolean getSnooperEnabled() {
/* 326 */     return this.propertyManager.getBoolean("snooper-enabled", true);
/*     */   }
/*     */   
/*     */   public void issueCommand(String s, ICommandListener icommandlistener) {
/* 330 */     this.j.add(new ServerCommand(s, icommandlistener));
/*     */   }
/*     */   
/*     */   public void aB() {
/* 334 */     SpigotTimings.serverCommandTimer.startTiming();
/* 335 */     while (!this.j.isEmpty()) {
/* 336 */       ServerCommand servercommand = this.j.remove(0);
/*     */ 
/*     */       
/* 339 */       ServerCommandEvent event = new ServerCommandEvent((CommandSender)this.console, servercommand.command);
/* 340 */       this.server.getPluginManager().callEvent((Event)event);
/* 341 */       servercommand = new ServerCommand(event.getCommand(), servercommand.source);
/*     */ 
/*     */       
/* 344 */       this.server.dispatchServerCommand((CommandSender)this.console, servercommand);
/*     */     } 
/*     */     
/* 347 */     SpigotTimings.serverCommandTimer.stopTiming();
/*     */   }
/*     */   
/*     */   public boolean X() {
/* 351 */     return true;
/*     */   }
/*     */   
/*     */   public DedicatedPlayerList aC() {
/* 355 */     return (DedicatedPlayerList)super.getPlayerList();
/*     */   }
/*     */   
/*     */   public int a(String s, int i) {
/* 359 */     return this.propertyManager.getInt(s, i);
/*     */   }
/*     */   
/*     */   public String a(String s, String s1) {
/* 363 */     return this.propertyManager.getString(s, s1);
/*     */   }
/*     */   
/*     */   public boolean a(String s, boolean flag) {
/* 367 */     return this.propertyManager.getBoolean(s, flag);
/*     */   }
/*     */   
/*     */   public void a(String s, Object object) {
/* 371 */     this.propertyManager.setProperty(s, object);
/*     */   }
/*     */   
/*     */   public void a() {
/* 375 */     this.propertyManager.savePropertiesFile();
/*     */   }
/*     */   
/*     */   public String b() {
/* 379 */     File file1 = this.propertyManager.c();
/*     */     
/* 381 */     return (file1 != null) ? file1.getAbsolutePath() : "No settings file";
/*     */   }
/*     */   
/*     */   public void aD() {
/* 385 */     ServerGUI.a(this);
/* 386 */     this.q = true;
/*     */   }
/*     */   
/*     */   public boolean ak() {
/* 390 */     return this.q;
/*     */   }
/*     */   
/*     */   public String a(EnumGamemode enumgamemode, boolean flag) {
/* 394 */     return "";
/*     */   }
/*     */   
/*     */   public boolean getEnableCommandBlock() {
/* 398 */     return this.propertyManager.getBoolean("enable-command-block", false);
/*     */   }
/*     */   
/*     */   public int getSpawnProtection() {
/* 402 */     return this.propertyManager.getInt("spawn-protection", super.getSpawnProtection());
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, EntityHuman entityhuman) {
/* 406 */     if (world.worldProvider.dimension != 0)
/* 407 */       return false; 
/* 408 */     if (aC().getOPs().isEmpty())
/* 409 */       return false; 
/* 410 */     if (aC().isOp(entityhuman.getProfile()))
/* 411 */       return false; 
/* 412 */     if (getSpawnProtection() <= 0) {
/* 413 */       return false;
/*     */     }
/* 415 */     ChunkCoordinates chunkcoordinates = world.getSpawn();
/* 416 */     int l = MathHelper.a(i - chunkcoordinates.x);
/* 417 */     int i1 = MathHelper.a(k - chunkcoordinates.z);
/* 418 */     int j1 = Math.max(l, i1);
/*     */     
/* 420 */     return (j1 <= getSpawnProtection());
/*     */   }
/*     */ 
/*     */   
/*     */   public int l() {
/* 425 */     return this.propertyManager.getInt("op-permission-level", 4);
/*     */   }
/*     */   
/*     */   public void setIdleTimeout(int i) {
/* 429 */     super.setIdleTimeout(i);
/* 430 */     this.propertyManager.setProperty("player-idle-timeout", Integer.valueOf(i));
/* 431 */     a();
/*     */   }
/*     */   
/*     */   public boolean m() {
/* 435 */     return this.propertyManager.getBoolean("broadcast-rcon-to-ops", true);
/*     */   }
/*     */   
/*     */   public boolean at() {
/* 439 */     return this.propertyManager.getBoolean("announce-player-achievements", true);
/*     */   }
/*     */   
/*     */   protected boolean aE() {
/* 443 */     this.server.getLogger().info("**** Beginning UUID conversion, this may take A LONG time ****");
/* 444 */     boolean flag = false;
/*     */     
/*     */     int i;
/*     */     
/* 448 */     for (i = 0; !flag && i <= 2; i++) {
/* 449 */       if (i > 0) {
/*     */         
/* 451 */         DedicatedServer.i.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
/* 452 */         aG();
/*     */       } 
/*     */       
/* 455 */       flag = NameReferencingFileConverter.a(this);
/*     */     } 
/*     */     
/* 458 */     boolean flag1 = false;
/*     */     
/* 460 */     for (i = 0; !flag1 && i <= 2; i++) {
/* 461 */       if (i > 0) {
/*     */         
/* 463 */         DedicatedServer.i.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
/* 464 */         aG();
/*     */       } 
/*     */       
/* 467 */       flag1 = NameReferencingFileConverter.b(this);
/*     */     } 
/*     */     
/* 470 */     boolean flag2 = false;
/*     */     
/* 472 */     for (i = 0; !flag2 && i <= 2; i++) {
/* 473 */       if (i > 0) {
/*     */         
/* 475 */         DedicatedServer.i.warn("Encountered a problem while converting the op list, retrying in a few seconds");
/* 476 */         aG();
/*     */       } 
/*     */       
/* 479 */       flag2 = NameReferencingFileConverter.c(this);
/*     */     } 
/*     */     
/* 482 */     boolean flag3 = false;
/*     */     
/* 484 */     for (i = 0; !flag3 && i <= 2; i++) {
/* 485 */       if (i > 0) {
/*     */         
/* 487 */         DedicatedServer.i.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
/* 488 */         aG();
/*     */       } 
/*     */       
/* 491 */       flag3 = NameReferencingFileConverter.d(this);
/*     */     } 
/*     */     
/* 494 */     boolean flag4 = false;
/*     */     
/* 496 */     for (i = 0; !flag4 && i <= 2; i++) {
/* 497 */       if (i > 0) {
/*     */         
/* 499 */         DedicatedServer.i.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
/* 500 */         aG();
/*     */       } 
/*     */       
/* 503 */       flag4 = NameReferencingFileConverter.a(this, this.propertyManager);
/*     */     } 
/*     */     
/* 506 */     return (flag || flag1 || flag2 || flag3 || flag4);
/*     */   }
/*     */   
/*     */   private void aG() {
/*     */     try {
/* 511 */       Thread.sleep(5000L);
/* 512 */     } catch (InterruptedException interruptedexception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerList getPlayerList() {
/* 518 */     return aC();
/*     */   }
/*     */   
/*     */   static Logger aF() {
/* 522 */     return i;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DedicatedServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */