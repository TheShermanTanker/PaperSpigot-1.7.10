/*     */ package org.spigotmc;
/*     */ 
/*     */ import com.google.common.base.Throwables;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import net.minecraft.server.v1_7_R4.AttributeRanged;
/*     */ import net.minecraft.server.v1_7_R4.GenericAttributes;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ public class SpigotConfig
/*     */ {
/*  34 */   private static final File CONFIG_FILE = new File("spigot.yml");
/*     */   
/*     */   private static final String HEADER = "This is the main configuration file for Spigot.\nAs you can see, there's tons to configure. Some options may impact gameplay, so use\nwith caution, and make sure you know what each option does before configuring.\nFor a reference for any variable inside this file, check out the Spigot wiki at\nhttp://www.spigotmc.org/wiki/spigot-configuration/\n\nIf you need help with the configuration or have any questions related to Spigot,\njoin us at the IRC or drop by our forums and leave a post.\n\nIRC: #spigot @ irc.spi.gt ( http://www.spigotmc.org/pages/irc/ )\nForums: http://www.spigotmc.org/\n";
/*     */   
/*     */   public static YamlConfiguration config;
/*     */   
/*     */   static int version;
/*     */   
/*     */   static Map<String, Command> commands;
/*     */   
/*     */   private static Metrics metrics;
/*     */   
/*     */   public static boolean logCommands;
/*     */   
/*     */   public static int tabComplete;
/*     */   
/*     */   public static String whitelistMessage;
/*     */   public static String unknownCommandMessage;
/*     */   public static String serverFullMessage;
/*     */   
/*     */   public static void init() {
/*  55 */     config = new YamlConfiguration();
/*     */     
/*     */     try {
/*  58 */       config.load(CONFIG_FILE);
/*  59 */     } catch (IOException ex) {
/*     */     
/*  61 */     } catch (InvalidConfigurationException ex) {
/*     */       
/*  63 */       Bukkit.getLogger().log(Level.SEVERE, "Could not load spigot.yml, please correct your syntax errors", (Throwable)ex);
/*  64 */       throw Throwables.propagate(ex);
/*     */     } 
/*     */     
/*  67 */     config.options().header("This is the main configuration file for Spigot.\nAs you can see, there's tons to configure. Some options may impact gameplay, so use\nwith caution, and make sure you know what each option does before configuring.\nFor a reference for any variable inside this file, check out the Spigot wiki at\nhttp://www.spigotmc.org/wiki/spigot-configuration/\n\nIf you need help with the configuration or have any questions related to Spigot,\njoin us at the IRC or drop by our forums and leave a post.\n\nIRC: #spigot @ irc.spi.gt ( http://www.spigotmc.org/pages/irc/ )\nForums: http://www.spigotmc.org/\n");
/*  68 */     config.options().copyDefaults(true);
/*     */     
/*  70 */     commands = new HashMap<String, Command>();
/*     */     
/*  72 */     version = getInt("config-version", 8);
/*  73 */     set("config-version", Integer.valueOf(8));
/*  74 */     readConfig(SpigotConfig.class, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerCommands() {
/*  79 */     for (Map.Entry<String, Command> entry : commands.entrySet())
/*     */     {
/*  81 */       (MinecraftServer.getServer()).server.getCommandMap().register(entry.getKey(), "Spigot", entry.getValue());
/*     */     }
/*     */     
/*  84 */     if (metrics == null) {
/*     */       
/*     */       try {
/*     */         
/*  88 */         metrics = new Metrics();
/*  89 */         metrics.start();
/*  90 */       } catch (IOException ex) {
/*     */         
/*  92 */         Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not start metrics service", ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void readConfig(Class<?> clazz, Object instance) {
/*  99 */     for (Method method : clazz.getDeclaredMethods()) {
/*     */       
/* 101 */       if (Modifier.isPrivate(method.getModifiers()))
/*     */       {
/* 103 */         if ((method.getParameterTypes()).length == 0 && method.getReturnType() == void.class) {
/*     */           
/*     */           try {
/*     */             
/* 107 */             method.setAccessible(true);
/* 108 */             method.invoke(instance, new Object[0]);
/* 109 */           } catch (InvocationTargetException ex) {
/*     */             
/* 111 */             throw Throwables.propagate(ex.getCause());
/* 112 */           } catch (Exception ex) {
/*     */             
/* 114 */             Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
/*     */           } 
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 122 */       config.save(CONFIG_FILE);
/* 123 */     } catch (IOException ex) {
/*     */       
/* 125 */       Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void set(String path, Object val) {
/* 131 */     config.set(path, val);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(String path, boolean def) {
/* 136 */     config.addDefault(path, Boolean.valueOf(def));
/* 137 */     return config.getBoolean(path, config.getBoolean(path));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getInt(String path, int def) {
/* 142 */     config.addDefault(path, Integer.valueOf(def));
/* 143 */     return config.getInt(path, config.getInt(path));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> List getList(String path, T def) {
/* 148 */     config.addDefault(path, def);
/* 149 */     return config.getList(path, config.getList(path));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getString(String path, String def) {
/* 154 */     config.addDefault(path, def);
/* 155 */     return config.getString(path, config.getString(path));
/*     */   }
/*     */ 
/*     */   
/*     */   private static double getDouble(String path, double def) {
/* 160 */     config.addDefault(path, Double.valueOf(def));
/* 161 */     return config.getDouble(path, config.getDouble(path));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void logCommands() {
/* 167 */     logCommands = getBoolean("commands.log", true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void tabComplete() {
/* 173 */     if (version < 6) {
/*     */       
/* 175 */       boolean oldValue = getBoolean("commands.tab-complete", true);
/* 176 */       if (oldValue) {
/*     */         
/* 178 */         set("commands.tab-complete", Integer.valueOf(0));
/*     */       } else {
/*     */         
/* 181 */         set("commands.tab-complete", Integer.valueOf(-1));
/*     */       } 
/*     */     } 
/* 184 */     tabComplete = getInt("commands.tab-complete", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public static String outdatedClientMessage = "Outdated client! Please use {0}";
/* 191 */   public static String outdatedServerMessage = "Outdated server! I'm still on {0}";
/*     */   
/*     */   private static String transform(String s) {
/* 194 */     return ChatColor.translateAlternateColorCodes('&', s).replaceAll("\\n", "\n");
/*     */   }
/*     */   
/*     */   private static void messages() {
/* 198 */     if (version < 8) {
/*     */       
/* 200 */       set("messages.outdated-client", outdatedClientMessage);
/* 201 */       set("messages.outdated-server", outdatedServerMessage);
/*     */     } 
/*     */     
/* 204 */     whitelistMessage = transform(getString("messages.whitelist", "You are not whitelisted on this server!"));
/* 205 */     unknownCommandMessage = transform(getString("messages.unknown-command", "Unknown command. Type \"/help\" for help."));
/* 206 */     serverFullMessage = transform(getString("messages.server-full", "The server is full!"));
/* 207 */     outdatedClientMessage = transform(getString("messages.outdated-client", outdatedClientMessage));
/* 208 */     outdatedServerMessage = transform(getString("messages.outdated-server", outdatedServerMessage));
/*     */   }
/*     */   
/* 211 */   public static int timeoutTime = 60;
/*     */   public static boolean restartOnCrash = true;
/* 213 */   public static String restartScript = "./start.sh"; public static String restartMessage;
/*     */   public static boolean bungee;
/*     */   
/*     */   private static void watchdog() {
/* 217 */     timeoutTime = getInt("settings.timeout-time", timeoutTime);
/* 218 */     restartOnCrash = getBoolean("settings.restart-on-crash", restartOnCrash);
/* 219 */     restartScript = getString("settings.restart-script", restartScript);
/* 220 */     restartMessage = transform(getString("messages.restart", "Server is restarting"));
/* 221 */     commands.put("restart", new RestartCommand("restart"));
/* 222 */     WatchdogThread.doStart(timeoutTime, restartOnCrash);
/*     */   }
/*     */   public static boolean lateBind; public static boolean disableStatSaving;
/*     */   
/*     */   private static void bungee() {
/* 227 */     if (version < 4) {
/*     */       
/* 229 */       set("settings.bungeecord", Boolean.valueOf(false));
/* 230 */       System.out.println("Oudated config, disabling BungeeCord support!");
/*     */     } 
/* 232 */     bungee = getBoolean("settings.bungeecord", false);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void nettyThreads() {
/* 237 */     int count = getInt("settings.netty-threads", 4);
/* 238 */     System.setProperty("io.netty.eventLoopThreads", Integer.toString(count));
/* 239 */     Bukkit.getLogger().log(Level.INFO, "Using {0} threads for Netty based IO", Integer.valueOf(count));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void lateBind() {
/* 244 */     lateBind = getBoolean("settings.late-bind", false);
/*     */   }
/*     */   public static int playerSample; public static int playerShuffle;
/*     */   public static List<String> spamExclusions;
/* 248 */   public static TObjectIntHashMap<String> forcedStats = new TObjectIntHashMap(); public static boolean silentCommandBlocks; public static boolean filterCreativeItems;
/*     */   
/*     */   private static void stats() {
/* 251 */     disableStatSaving = getBoolean("stats.disable-saving", false);
/*     */     
/* 253 */     if (!config.contains("stats.forced-stats")) {
/* 254 */       config.createSection("stats.forced-stats");
/*     */     }
/*     */     
/* 257 */     ConfigurationSection section = config.getConfigurationSection("stats.forced-stats");
/* 258 */     for (String name : section.getKeys(true)) {
/*     */       
/* 260 */       if (section.isInt(name))
/*     */       {
/* 262 */         forcedStats.put(name, section.getInt(name));
/*     */       }
/*     */     } 
/*     */     
/* 266 */     if (disableStatSaving && section.getInt("achievement.openInventory", 0) < 1)
/*     */     {
/* 268 */       Bukkit.getLogger().warning("*** WARNING *** stats.disable-saving is true but stats.forced-stats.achievement.openInventory isn't set to 1. Disabling stat saving without forcing the achievement may cause it to get stuck on the player's screen."); } 
/*     */   }
/*     */   public static Set<String> replaceCommands; public static int userCacheCap; public static boolean saveUserCacheOnStopOnly;
/*     */   public static int intCacheLimit;
/*     */   public static double movedWronglyThreshold;
/*     */   public static double movedTooQuicklyThreshold;
/*     */   
/*     */   private static void tpsCommand() {
/* 276 */     commands.put("tps", new TicksPerSecondCommand("tps"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void playerSample() {
/* 282 */     playerSample = getInt("settings.sample-count", 12);
/* 283 */     System.out.println("Server Ping Player Sample Count: " + playerSample);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void playerShuffle() {
/* 289 */     playerShuffle = getInt("settings.player-shuffle", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void spamExclusions() {
/* 295 */     spamExclusions = getList("commands.spam-exclusions", Arrays.asList(new String[] { "/skill" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void silentCommandBlocks() {
/* 304 */     silentCommandBlocks = getBoolean("commands.silent-commandblock-console", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void filterCreativeItems() {
/* 310 */     filterCreativeItems = getBoolean("settings.filter-creative-items", true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void replaceCommands() {
/* 316 */     if (config.contains("replace-commands")) {
/*     */       
/* 318 */       set("commands.replace-commands", config.getStringList("replace-commands"));
/* 319 */       config.set("replace-commands", null);
/*     */     } 
/* 321 */     replaceCommands = new HashSet<String>(getList("commands.replace-commands", Arrays.asList(new String[] { "setblock", "summon", "testforblock", "tellraw" })));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void userCacheCap() {
/* 328 */     userCacheCap = getInt("settings.user-cache-size", 1000);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void saveUserCacheOnStopOnly() {
/* 334 */     saveUserCacheOnStopOnly = getBoolean("settings.save-user-cache-on-stop-only", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void intCacheLimit() {
/* 340 */     intCacheLimit = getInt("settings.int-cache-limit", 1024);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void movedWronglyThreshold() {
/* 346 */     movedWronglyThreshold = getDouble("settings.moved-wrongly-threshold", 0.0625D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void movedTooQuicklyThreshold() {
/* 352 */     movedTooQuicklyThreshold = getDouble("settings.moved-too-quickly-threshold", 100.0D);
/*     */   }
/*     */   
/* 355 */   public static double maxHealth = 2048.0D;
/* 356 */   public static double movementSpeed = 2048.0D;
/* 357 */   public static double attackDamage = 2048.0D; public static boolean debug;
/*     */   
/*     */   private static void attributeMaxes() {
/* 360 */     maxHealth = getDouble("settings.attribute.maxHealth.max", maxHealth);
/* 361 */     ((AttributeRanged)GenericAttributes.maxHealth).b = maxHealth;
/* 362 */     movementSpeed = getDouble("settings.attribute.movementSpeed.max", movementSpeed);
/* 363 */     ((AttributeRanged)GenericAttributes.d).b = movementSpeed;
/* 364 */     attackDamage = getDouble("settings.attribute.attackDamage.max", attackDamage);
/* 365 */     ((AttributeRanged)GenericAttributes.e).b = attackDamage;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void globalAPICache() {
/* 370 */     if (getBoolean("settings.global-api-cache", false) && !CachedStreamHandlerFactory.isSet) {
/*     */       
/* 372 */       Bukkit.getLogger().info("Global API cache enabled - All requests to Mojang's API will be handled by Spigot");
/*     */       
/* 374 */       CachedStreamHandlerFactory.isSet = true;
/* 375 */       URL.setURLStreamHandlerFactory(new CachedStreamHandlerFactory());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void debug() {
/* 382 */     debug = getBoolean("settings.debug", false);
/*     */     
/* 384 */     if (debug && !LogManager.getRootLogger().isTraceEnabled()) {
/*     */ 
/*     */       
/* 387 */       LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
/* 388 */       Configuration conf = ctx.getConfiguration();
/* 389 */       conf.getLoggerConfig("").setLevel(Level.ALL);
/* 390 */       ctx.updateLoggers(conf);
/*     */     } 
/*     */     
/* 393 */     if (LogManager.getRootLogger().isTraceEnabled()) {
/*     */       
/* 395 */       Bukkit.getLogger().info("Debug logging is enabled");
/*     */     } else {
/*     */       
/* 398 */       Bukkit.getLogger().info("Debug logging is disabled");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\SpigotConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */