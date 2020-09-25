/*     */ package org.bukkit.plugin.java;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.InvalidPluginException;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ final class PluginClassLoader
/*     */   extends URLClassLoader {
/*     */   private final JavaPluginLoader loader;
/*  20 */   private final Map<String, Class<?>> classes = new ConcurrentHashMap<String, Class<?>>();
/*     */   
/*     */   private final PluginDescriptionFile description;
/*     */   
/*     */   private final File dataFolder;
/*     */   
/*     */   private final File file;
/*     */   final JavaPlugin plugin;
/*     */   private JavaPlugin pluginInit;
/*     */   private IllegalStateException pluginState;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       Method method = ClassLoader.class.getDeclaredMethod("registerAsParallelCapable", new Class[0]);
/*  34 */       if (method != null) {
/*     */         
/*  36 */         boolean oldAccessible = method.isAccessible();
/*  37 */         method.setAccessible(true);
/*  38 */         method.invoke(null, new Object[0]);
/*  39 */         method.setAccessible(oldAccessible);
/*  40 */         Bukkit.getLogger().log(Level.INFO, "Set PluginClassLoader as parallel capable");
/*     */       } 
/*  42 */     } catch (NoSuchMethodException ex) {
/*     */ 
/*     */     
/*  45 */     } catch (Exception ex) {
/*     */       
/*  47 */       Bukkit.getLogger().log(Level.WARNING, "Error setting PluginClassLoader as parallel capable", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   PluginClassLoader(JavaPluginLoader loader, ClassLoader parent, PluginDescriptionFile description, File dataFolder, File file) throws InvalidPluginException, MalformedURLException {
/*  53 */     super(new URL[] { file.toURI().toURL() }, parent);
/*  54 */     Validate.notNull(loader, "Loader cannot be null");
/*     */     
/*  56 */     this.loader = loader;
/*  57 */     this.description = description;
/*  58 */     this.dataFolder = dataFolder;
/*  59 */     this.file = file;
/*     */     try {
/*     */       Class<?> jarClass;
/*     */       Class<? extends JavaPlugin> pluginClass;
/*     */       try {
/*  64 */         jarClass = Class.forName(description.getMain(), true, this);
/*  65 */       } catch (ClassNotFoundException ex) {
/*  66 */         throw new InvalidPluginException("Cannot find main class `" + description.getMain() + "'", ex);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/*  71 */         pluginClass = jarClass.asSubclass(JavaPlugin.class);
/*  72 */       } catch (ClassCastException ex) {
/*  73 */         throw new InvalidPluginException("main class `" + description.getMain() + "' does not extend JavaPlugin", ex);
/*     */       } 
/*     */       
/*  76 */       this.plugin = pluginClass.newInstance();
/*  77 */     } catch (IllegalAccessException ex) {
/*  78 */       throw new InvalidPluginException("No public constructor", ex);
/*  79 */     } catch (InstantiationException ex) {
/*  80 */       throw new InvalidPluginException("Abnormal plugin type", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class<?> findClass(String name) throws ClassNotFoundException {
/*  86 */     return findClass(name, true);
/*     */   }
/*     */   
/*     */   Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
/*  90 */     if (name.startsWith("org.bukkit.") || name.startsWith("net.minecraft.")) {
/*  91 */       throw new ClassNotFoundException(name);
/*     */     }
/*  93 */     Class<?> result = this.classes.get(name);
/*     */     
/*  95 */     if (result == null) {
/*  96 */       if (checkGlobal) {
/*  97 */         result = this.loader.getClassByName(name);
/*     */       }
/*     */       
/* 100 */       if (result == null) {
/* 101 */         result = super.findClass(name);
/*     */         
/* 103 */         if (result != null) {
/* 104 */           this.loader.setClass(name, result);
/*     */         }
/*     */       } 
/*     */       
/* 108 */       this.classes.put(name, result);
/*     */     } 
/*     */     
/* 111 */     return result;
/*     */   }
/*     */   
/*     */   Set<String> getClasses() {
/* 115 */     return this.classes.keySet();
/*     */   }
/*     */   
/*     */   synchronized void initialize(JavaPlugin javaPlugin) {
/* 119 */     Validate.notNull(javaPlugin, "Initializing plugin cannot be null");
/* 120 */     Validate.isTrue((javaPlugin.getClass().getClassLoader() == this), "Cannot initialize plugin outside of this class loader");
/* 121 */     if (this.plugin != null || this.pluginInit != null) {
/* 122 */       throw new IllegalArgumentException("Plugin already initialized!", this.pluginState);
/*     */     }
/*     */     
/* 125 */     this.pluginState = new IllegalStateException("Initial initialization");
/* 126 */     this.pluginInit = javaPlugin;
/*     */     
/* 128 */     javaPlugin.init(this.loader, this.loader.server, this.description, this.dataFolder, this.file, this);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\java\PluginClassLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */