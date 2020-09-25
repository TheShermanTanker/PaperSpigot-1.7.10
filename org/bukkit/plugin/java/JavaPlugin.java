/*     */ package org.bukkit.plugin.java;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.EbeanServerFactory;
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.io.ByteStreams;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Warning;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.configuration.Configuration;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.plugin.AuthorNagException;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginAwareness;
/*     */ import org.bukkit.plugin.PluginBase;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginLoader;
/*     */ import org.bukkit.plugin.PluginLogger;
/*     */ 
/*     */ 
/*     */ public abstract class JavaPlugin
/*     */   extends PluginBase
/*     */ {
/*     */   private boolean isEnabled = false;
/*  49 */   private PluginLoader loader = null;
/*  50 */   private Server server = null;
/*  51 */   private File file = null;
/*  52 */   private PluginDescriptionFile description = null;
/*  53 */   private File dataFolder = null;
/*  54 */   private ClassLoader classLoader = null;
/*     */   private boolean naggable = true;
/*  56 */   private EbeanServer ebean = null;
/*  57 */   private FileConfiguration newConfig = null;
/*  58 */   private File configFile = null;
/*  59 */   private PluginLogger logger = null;
/*     */   
/*     */   public JavaPlugin() {
/*  62 */     ClassLoader classLoader = getClass().getClassLoader();
/*  63 */     if (!(classLoader instanceof PluginClassLoader)) {
/*  64 */       throw new IllegalStateException("JavaPlugin requires " + PluginClassLoader.class.getName());
/*     */     }
/*  66 */     ((PluginClassLoader)classLoader).initialize(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected JavaPlugin(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file) {
/*  78 */     ClassLoader classLoader = getClass().getClassLoader();
/*  79 */     if (classLoader instanceof PluginClassLoader) {
/*  80 */       throw new IllegalStateException("Cannot use initialization constructor at runtime");
/*     */     }
/*  82 */     init(loader, server, description, dataFolder, file, classLoader);
/*     */   }
/*     */   
/*     */   protected JavaPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
/*  86 */     ClassLoader classLoader = getClass().getClassLoader();
/*  87 */     if (classLoader instanceof PluginClassLoader) {
/*  88 */       throw new IllegalStateException("Cannot use initialization constructor at runtime");
/*     */     }
/*  90 */     init(loader, loader.server, description, dataFolder, file, classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final File getDataFolder() {
/* 101 */     return this.dataFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PluginLoader getPluginLoader() {
/* 111 */     return this.loader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Server getServer() {
/* 121 */     return this.server;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isEnabled() {
/* 132 */     return this.isEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected File getFile() {
/* 141 */     return this.file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PluginDescriptionFile getDescription() {
/* 151 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileConfiguration getConfig() {
/* 156 */     if (this.newConfig == null) {
/* 157 */       reloadConfig();
/*     */     }
/* 159 */     return this.newConfig;
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
/*     */   
/*     */   protected final Reader getTextResource(String file) {
/* 175 */     InputStream in = getResource(file);
/*     */     
/* 177 */     return (in == null) ? null : new InputStreamReader(in, (isStrictlyUTF8() || FileConfiguration.UTF8_OVERRIDE) ? Charsets.UTF_8 : Charset.defaultCharset());
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadConfig() {
/*     */     YamlConfiguration defConfig;
/* 183 */     this.newConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
/*     */     
/* 185 */     InputStream defConfigStream = getResource("config.yml");
/* 186 */     if (defConfigStream == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 191 */     if (isStrictlyUTF8() || FileConfiguration.UTF8_OVERRIDE) {
/* 192 */       defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
/*     */     } else {
/*     */       byte[] contents;
/* 195 */       defConfig = new YamlConfiguration();
/*     */       try {
/* 197 */         contents = ByteStreams.toByteArray(defConfigStream);
/* 198 */       } catch (IOException e) {
/* 199 */         getLogger().log(Level.SEVERE, "Unexpected failure reading config.yml", e);
/*     */         
/*     */         return;
/*     */       } 
/* 203 */       String text = new String(contents, Charset.defaultCharset());
/* 204 */       if (!text.equals(new String(contents, Charsets.UTF_8))) {
/* 205 */         getLogger().warning("Default system encoding may have misread config.yml from plugin jar");
/*     */       }
/*     */       
/*     */       try {
/* 209 */         defConfig.loadFromString(text);
/* 210 */       } catch (InvalidConfigurationException e) {
/* 211 */         getLogger().log(Level.SEVERE, "Cannot load configuration from jar", (Throwable)e);
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     this.newConfig.setDefaults((Configuration)defConfig);
/*     */   }
/*     */   
/*     */   private boolean isStrictlyUTF8() {
/* 219 */     return getDescription().getAwareness().contains(PluginAwareness.Flags.UTF8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveConfig() {
/*     */     try {
/* 225 */       getConfig().save(this.configFile);
/* 226 */     } catch (IOException ex) {
/* 227 */       this.logger.log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveDefaultConfig() {
/* 233 */     if (!this.configFile.exists()) {
/* 234 */       saveResource("config.yml", false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveResource(String resourcePath, boolean replace) {
/* 240 */     if (resourcePath == null || resourcePath.equals("")) {
/* 241 */       throw new IllegalArgumentException("ResourcePath cannot be null or empty");
/*     */     }
/*     */     
/* 244 */     resourcePath = resourcePath.replace('\\', '/');
/* 245 */     InputStream in = getResource(resourcePath);
/* 246 */     if (in == null) {
/* 247 */       throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);
/*     */     }
/*     */     
/* 250 */     File outFile = new File(this.dataFolder, resourcePath);
/* 251 */     int lastIndex = resourcePath.lastIndexOf('/');
/* 252 */     File outDir = new File(this.dataFolder, resourcePath.substring(0, (lastIndex >= 0) ? lastIndex : 0));
/*     */     
/* 254 */     if (!outDir.exists()) {
/* 255 */       outDir.mkdirs();
/*     */     }
/*     */     
/*     */     try {
/* 259 */       if (!outFile.exists() || replace) {
/* 260 */         OutputStream out = new FileOutputStream(outFile);
/* 261 */         byte[] buf = new byte[1024];
/*     */         int len;
/* 263 */         while ((len = in.read(buf)) > 0) {
/* 264 */           out.write(buf, 0, len);
/*     */         }
/* 266 */         out.close();
/* 267 */         in.close();
/*     */       } else {
/* 269 */         this.logger.log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
/*     */       } 
/* 271 */     } catch (IOException ex) {
/* 272 */       this.logger.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getResource(String filename) {
/* 278 */     if (filename == null) {
/* 279 */       throw new IllegalArgumentException("Filename cannot be null");
/*     */     }
/*     */     
/*     */     try {
/* 283 */       URL url = getClassLoader().getResource(filename);
/*     */       
/* 285 */       if (url == null) {
/* 286 */         return null;
/*     */       }
/*     */       
/* 289 */       URLConnection connection = url.openConnection();
/* 290 */       connection.setUseCaches(false);
/* 291 */       return connection.getInputStream();
/* 292 */     } catch (IOException ex) {
/* 293 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ClassLoader getClassLoader() {
/* 303 */     return this.classLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setEnabled(boolean enabled) {
/* 312 */     if (this.isEnabled != enabled) {
/* 313 */       this.isEnabled = enabled;
/*     */       
/* 315 */       if (this.isEnabled) {
/* 316 */         onEnable();
/*     */       } else {
/* 318 */         onDisable();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected final void initialize(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader) {
/* 329 */     if (server.getWarningState() == Warning.WarningState.OFF) {
/*     */       return;
/*     */     }
/* 332 */     getLogger().log(Level.WARNING, getClass().getName() + " is already initialized", (server.getWarningState() == Warning.WarningState.DEFAULT) ? null : (Throwable)new AuthorNagException("Explicit initialization"));
/*     */   }
/*     */   
/*     */   final void init(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader) {
/* 336 */     this.loader = loader;
/* 337 */     this.server = server;
/* 338 */     this.file = file;
/* 339 */     this.description = description;
/* 340 */     this.dataFolder = dataFolder;
/* 341 */     this.classLoader = classLoader;
/* 342 */     this.configFile = new File(dataFolder, "config.yml");
/* 343 */     this.logger = new PluginLogger((Plugin)this);
/*     */     
/* 345 */     if (description.isDatabaseEnabled()) {
/* 346 */       ServerConfig db = new ServerConfig();
/*     */       
/* 348 */       db.setDefaultServer(false);
/* 349 */       db.setRegister(false);
/* 350 */       db.setClasses(getDatabaseClasses());
/* 351 */       db.setName(description.getName());
/* 352 */       server.configureDbConfig(db);
/*     */       
/* 354 */       DataSourceConfig ds = db.getDataSourceConfig();
/*     */       
/* 356 */       ds.setUrl(replaceDatabaseString(ds.getUrl()));
/* 357 */       dataFolder.mkdirs();
/*     */       
/* 359 */       ClassLoader previous = Thread.currentThread().getContextClassLoader();
/*     */       
/* 361 */       Thread.currentThread().setContextClassLoader(classLoader);
/* 362 */       this.ebean = EbeanServerFactory.create(db);
/* 363 */       Thread.currentThread().setContextClassLoader(previous);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Class<?>> getDatabaseClasses() {
/* 373 */     return new ArrayList<Class<?>>();
/*     */   }
/*     */   
/*     */   private String replaceDatabaseString(String input) {
/* 377 */     input = input.replaceAll("\\{DIR\\}", this.dataFolder.getPath().replaceAll("\\\\", "/") + "/");
/* 378 */     input = input.replaceAll("\\{NAME\\}", this.description.getName().replaceAll("[^\\w_-]", ""));
/* 379 */     return input;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final boolean isInitialized() {
/* 391 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 399 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
/* 407 */     return null;
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
/*     */   public PluginCommand getCommand(String name) {
/* 419 */     String alias = name.toLowerCase();
/* 420 */     PluginCommand command = getServer().getPluginCommand(alias);
/*     */     
/* 422 */     if (command == null || command.getPlugin() != this) {
/* 423 */       command = getServer().getPluginCommand(this.description.getName().toLowerCase() + ":" + alias);
/*     */     }
/*     */     
/* 426 */     if (command != null && command.getPlugin() == this) {
/* 427 */       return command;
/*     */     }
/* 429 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoad() {}
/*     */ 
/*     */   
/*     */   public void onDisable() {}
/*     */ 
/*     */   
/*     */   public void onEnable() {}
/*     */ 
/*     */   
/*     */   public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
/* 444 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isNaggable() {
/* 449 */     return this.naggable;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setNaggable(boolean canNag) {
/* 454 */     this.naggable = canNag;
/*     */   }
/*     */ 
/*     */   
/*     */   public EbeanServer getDatabase() {
/* 459 */     return this.ebean;
/*     */   }
/*     */   
/*     */   protected void installDDL() {
/* 463 */     SpiEbeanServer serv = (SpiEbeanServer)getDatabase();
/* 464 */     DdlGenerator gen = serv.getDdlGenerator();
/*     */     
/* 466 */     gen.runScript(false, gen.generateCreateDdl());
/*     */   }
/*     */   
/*     */   protected void removeDDL() {
/* 470 */     SpiEbeanServer serv = (SpiEbeanServer)getDatabase();
/* 471 */     DdlGenerator gen = serv.getDdlGenerator();
/*     */     
/* 473 */     gen.runScript(true, gen.generateDropDdl());
/*     */   }
/*     */ 
/*     */   
/*     */   public final Logger getLogger() {
/* 478 */     return (Logger)this.logger;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 483 */     return this.description.getFullName();
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
/*     */   public static <T extends JavaPlugin> T getPlugin(Class<T> clazz) {
/* 509 */     Validate.notNull(clazz, "Null class cannot have a plugin");
/* 510 */     if (!JavaPlugin.class.isAssignableFrom(clazz)) {
/* 511 */       throw new IllegalArgumentException(clazz + " does not extend " + JavaPlugin.class);
/*     */     }
/* 513 */     ClassLoader cl = clazz.getClassLoader();
/* 514 */     if (!(cl instanceof PluginClassLoader)) {
/* 515 */       throw new IllegalArgumentException(clazz + " is not initialized by " + PluginClassLoader.class);
/*     */     }
/* 517 */     JavaPlugin plugin = ((PluginClassLoader)cl).plugin;
/* 518 */     if (plugin == null) {
/* 519 */       throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
/*     */     }
/* 521 */     return clazz.cast(plugin);
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
/*     */   public static JavaPlugin getProvidingPlugin(Class<?> clazz) {
/* 535 */     Validate.notNull(clazz, "Null class cannot have a plugin");
/* 536 */     ClassLoader cl = clazz.getClassLoader();
/* 537 */     if (!(cl instanceof PluginClassLoader)) {
/* 538 */       throw new IllegalArgumentException(clazz + " is not provided by " + PluginClassLoader.class);
/*     */     }
/* 540 */     JavaPlugin plugin = ((PluginClassLoader)cl).plugin;
/* 541 */     if (plugin == null) {
/* 542 */       throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
/*     */     }
/* 544 */     return plugin;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\java\JavaPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */