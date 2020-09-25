/*     */ package org.bukkit.plugin.java;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Warning;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventException;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.server.PluginDisableEvent;
/*     */ import org.bukkit.event.server.PluginEnableEvent;
/*     */ import org.bukkit.plugin.AuthorNagException;
/*     */ import org.bukkit.plugin.EventExecutor;
/*     */ import org.bukkit.plugin.InvalidDescriptionException;
/*     */ import org.bukkit.plugin.InvalidPluginException;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginLoader;
/*     */ import org.bukkit.plugin.RegisteredListener;
/*     */ import org.bukkit.plugin.UnknownDependencyException;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JavaPluginLoader
/*     */   implements PluginLoader
/*     */ {
/*     */   final Server server;
/*  50 */   private final Pattern[] fileFilters = new Pattern[] { Pattern.compile("\\.jar$") };
/*  51 */   private final Map<String, Class<?>> classes = new ConcurrentHashMap<String, Class<?>>();
/*  52 */   private final Map<String, PluginClassLoader> loaders = new LinkedHashMap<String, PluginClassLoader>();
/*  53 */   public static final CustomTimingsHandler pluginParentTimer = new CustomTimingsHandler("** Plugins");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JavaPluginLoader(Server instance) {
/*  60 */     Validate.notNull(instance, "Server cannot be null");
/*  61 */     this.server = instance;
/*     */   } public Plugin loadPlugin(File file) throws InvalidPluginException {
/*     */     PluginDescriptionFile description;
/*     */     PluginClassLoader loader;
/*  65 */     Validate.notNull(file, "File cannot be null");
/*     */     
/*  67 */     if (!file.exists()) {
/*  68 */       throw new InvalidPluginException(new FileNotFoundException(file.getPath() + " does not exist"));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  73 */       description = getPluginDescription(file);
/*  74 */     } catch (InvalidDescriptionException ex) {
/*  75 */       throw new InvalidPluginException(ex);
/*     */     } 
/*     */     
/*  78 */     File parentFile = file.getParentFile();
/*  79 */     File dataFolder = new File(parentFile, description.getName());
/*     */     
/*  81 */     File oldDataFolder = new File(parentFile, description.getRawName());
/*     */ 
/*     */     
/*  84 */     if (!dataFolder.equals(oldDataFolder))
/*     */     {
/*  86 */       if (dataFolder.isDirectory() && oldDataFolder.isDirectory()) {
/*  87 */         this.server.getLogger().warning(String.format("While loading %s (%s) found old-data folder: `%s' next to the new one `%s'", new Object[] { description.getFullName(), file, oldDataFolder, dataFolder }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  94 */       else if (oldDataFolder.isDirectory() && !dataFolder.exists()) {
/*  95 */         if (!oldDataFolder.renameTo(dataFolder)) {
/*  96 */           throw new InvalidPluginException("Unable to rename old data folder: `" + oldDataFolder + "' to: `" + dataFolder + "'");
/*     */         }
/*  98 */         this.server.getLogger().log(Level.INFO, String.format("While loading %s (%s) renamed data folder: `%s' to `%s'", new Object[] { description.getFullName(), file, oldDataFolder, dataFolder }));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (dataFolder.exists() && !dataFolder.isDirectory()) {
/* 108 */       throw new InvalidPluginException(String.format("Projected datafolder: `%s' for %s (%s) exists and is not a directory", new Object[] { dataFolder, description.getFullName(), file }));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     for (String pluginName : description.getDepend()) {
/* 117 */       if (this.loaders == null) {
/* 118 */         throw new UnknownDependencyException(pluginName);
/*     */       }
/* 120 */       PluginClassLoader current = this.loaders.get(pluginName);
/*     */       
/* 122 */       if (current == null) {
/* 123 */         throw new UnknownDependencyException(pluginName);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 129 */       loader = new PluginClassLoader(this, getClass().getClassLoader(), description, dataFolder, file);
/* 130 */     } catch (InvalidPluginException ex) {
/* 131 */       throw ex;
/* 132 */     } catch (Throwable ex) {
/* 133 */       throw new InvalidPluginException(ex);
/*     */     } 
/*     */     
/* 136 */     this.loaders.put(description.getName(), loader);
/*     */     
/* 138 */     return (Plugin)loader.plugin;
/*     */   }
/*     */   
/*     */   public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
/* 142 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 144 */     JarFile jar = null;
/* 145 */     InputStream stream = null;
/*     */     
/*     */     try {
/* 148 */       jar = new JarFile(file);
/* 149 */       JarEntry entry = jar.getJarEntry("plugin.yml");
/*     */       
/* 151 */       if (entry == null) {
/* 152 */         throw new InvalidDescriptionException(new FileNotFoundException("Jar does not contain plugin.yml"));
/*     */       }
/*     */       
/* 155 */       stream = jar.getInputStream(entry);
/*     */       
/* 157 */       return new PluginDescriptionFile(stream);
/*     */     }
/* 159 */     catch (IOException ex) {
/* 160 */       throw new InvalidDescriptionException(ex);
/* 161 */     } catch (YAMLException ex) {
/* 162 */       throw new InvalidDescriptionException(ex);
/*     */     } finally {
/* 164 */       if (jar != null) {
/*     */         try {
/* 166 */           jar.close();
/* 167 */         } catch (IOException e) {}
/*     */       }
/*     */       
/* 170 */       if (stream != null) {
/*     */         try {
/* 172 */           stream.close();
/* 173 */         } catch (IOException e) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Pattern[] getPluginFileFilters() {
/* 180 */     return (Pattern[])this.fileFilters.clone();
/*     */   }
/*     */   
/*     */   Class<?> getClassByName(String name) {
/* 184 */     Class<?> cachedClass = this.classes.get(name);
/*     */     
/* 186 */     if (cachedClass != null) {
/* 187 */       return cachedClass;
/*     */     }
/* 189 */     for (String current : this.loaders.keySet()) {
/* 190 */       PluginClassLoader loader = this.loaders.get(current);
/*     */       
/*     */       try {
/* 193 */         cachedClass = loader.findClass(name, false);
/* 194 */       } catch (ClassNotFoundException cnfe) {}
/* 195 */       if (cachedClass != null) {
/* 196 */         return cachedClass;
/*     */       }
/*     */     } 
/*     */     
/* 200 */     return null;
/*     */   }
/*     */   
/*     */   void setClass(String name, Class<?> clazz) {
/* 204 */     if (!this.classes.containsKey(name)) {
/* 205 */       this.classes.put(name, clazz);
/*     */       
/* 207 */       if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
/* 208 */         Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
/* 209 */         ConfigurationSerialization.registerClass(serializable);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeClass(String name) {
/* 215 */     Class<?> clazz = this.classes.remove(name);
/*     */     
/*     */     try {
/* 218 */       if (clazz != null && ConfigurationSerializable.class.isAssignableFrom(clazz)) {
/* 219 */         Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
/* 220 */         ConfigurationSerialization.unregisterClass(serializable);
/*     */       } 
/* 222 */     } catch (NullPointerException ex) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin) {
/*     */     Set<Method> methods;
/* 229 */     Validate.notNull(plugin, "Plugin can not be null");
/* 230 */     Validate.notNull(listener, "Listener can not be null");
/*     */     
/* 232 */     boolean useTimings = this.server.getPluginManager().useTimings();
/* 233 */     Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<Class<? extends Event>, Set<RegisteredListener>>();
/*     */     
/*     */     try {
/* 236 */       Method[] publicMethods = listener.getClass().getMethods();
/* 237 */       methods = new HashSet<Method>(publicMethods.length, Float.MAX_VALUE);
/* 238 */       for (Method method : publicMethods) {
/* 239 */         methods.add(method);
/*     */       }
/* 241 */       for (Method method : listener.getClass().getDeclaredMethods()) {
/* 242 */         methods.add(method);
/*     */       }
/* 244 */     } catch (NoClassDefFoundError e) {
/* 245 */       plugin.getLogger().severe("Plugin " + plugin.getDescription().getFullName() + " has failed to register events for " + listener.getClass() + " because " + e.getMessage() + " does not exist.");
/* 246 */       return ret;
/*     */     } 
/*     */     
/* 249 */     for (Method method : methods) {
/* 250 */       EventHandler eh = method.<EventHandler>getAnnotation(EventHandler.class);
/* 251 */       if (eh == null)
/*     */         continue;  Class<?> checkClass;
/* 253 */       if ((method.getParameterTypes()).length != 1 || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
/* 254 */         plugin.getLogger().severe(plugin.getDescription().getFullName() + " attempted to register an invalid EventHandler method signature \"" + method.toGenericString() + "\" in " + listener.getClass());
/*     */         continue;
/*     */       } 
/* 257 */       final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
/* 258 */       method.setAccessible(true);
/* 259 */       Set<RegisteredListener> eventSet = ret.get(eventClass);
/* 260 */       if (eventSet == null) {
/* 261 */         eventSet = new HashSet<RegisteredListener>();
/* 262 */         ret.put(eventClass, eventSet);
/*     */       } 
/*     */       
/* 265 */       for (Class<?> clazz = eventClass; Event.class.isAssignableFrom(clazz); clazz = clazz.getSuperclass()) {
/*     */         
/* 267 */         if (clazz.getAnnotation(Deprecated.class) != null) {
/* 268 */           Warning warning = clazz.<Warning>getAnnotation(Warning.class);
/* 269 */           Warning.WarningState warningState = this.server.getWarningState();
/* 270 */           if (!warningState.printFor(warning)) {
/*     */             break;
/*     */           }
/* 273 */           plugin.getLogger().log(Level.WARNING, String.format("\"%s\" has registered a listener for %s on method \"%s\", but the event is Deprecated. \"%s\"; please notify the authors %s.", new Object[] { plugin.getDescription().getFullName(), clazz.getName(), method.toGenericString(), (warning != null && warning.reason().length() != 0) ? warning.reason() : "Server performance will be affected", Arrays.toString(plugin.getDescription().getAuthors().toArray()) }), (warningState == Warning.WarningState.ON) ? (Throwable)new AuthorNagException(null) : null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 288 */       final CustomTimingsHandler timings = new CustomTimingsHandler("Plugin: " + plugin.getDescription().getFullName() + " Event: " + listener.getClass().getName() + "::" + method.getName() + "(" + eventClass.getSimpleName() + ")", pluginParentTimer);
/* 289 */       EventExecutor executor = new EventExecutor() {
/*     */           public void execute(Listener listener, Event event) throws EventException {
/*     */             try {
/* 292 */               if (!eventClass.isAssignableFrom(event.getClass())) {
/*     */                 return;
/*     */               }
/*     */               
/* 296 */               boolean isAsync = event.isAsynchronous();
/* 297 */               if (!isAsync) timings.startTiming(); 
/* 298 */               method.invoke(listener, new Object[] { event });
/* 299 */               if (!isAsync) timings.stopTiming();
/*     */             
/* 301 */             } catch (InvocationTargetException ex) {
/* 302 */               throw new EventException(ex.getCause());
/* 303 */             } catch (Throwable t) {
/* 304 */               throw new EventException(t);
/*     */             } 
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */       
/* 311 */       eventSet.add(new RegisteredListener(listener, executor, eh.priority(), plugin, eh.ignoreCancelled()));
/*     */     } 
/*     */     
/* 314 */     return ret;
/*     */   }
/*     */   
/*     */   public void enablePlugin(Plugin plugin) {
/* 318 */     Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
/*     */     
/* 320 */     if (!plugin.isEnabled()) {
/* 321 */       plugin.getLogger().info("Enabling " + plugin.getDescription().getFullName());
/*     */       
/* 323 */       JavaPlugin jPlugin = (JavaPlugin)plugin;
/*     */       
/* 325 */       String pluginName = jPlugin.getDescription().getName();
/*     */       
/* 327 */       if (!this.loaders.containsKey(pluginName)) {
/* 328 */         this.loaders.put(pluginName, (PluginClassLoader)jPlugin.getClassLoader());
/*     */       }
/*     */       
/*     */       try {
/* 332 */         jPlugin.setEnabled(true);
/* 333 */       } catch (Throwable ex) {
/* 334 */         this.server.getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 339 */       this.server.getPluginManager().callEvent((Event)new PluginEnableEvent(plugin));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disablePlugin(Plugin plugin) {
/* 344 */     Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
/*     */     
/* 346 */     if (plugin.isEnabled()) {
/* 347 */       String message = String.format("Disabling %s", new Object[] { plugin.getDescription().getFullName() });
/* 348 */       plugin.getLogger().info(message);
/*     */       
/* 350 */       this.server.getPluginManager().callEvent((Event)new PluginDisableEvent(plugin));
/*     */       
/* 352 */       JavaPlugin jPlugin = (JavaPlugin)plugin;
/* 353 */       ClassLoader cloader = jPlugin.getClassLoader();
/*     */       
/*     */       try {
/* 356 */         jPlugin.setEnabled(false);
/* 357 */       } catch (Throwable ex) {
/* 358 */         this.server.getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       } 
/*     */       
/* 361 */       this.loaders.remove(jPlugin.getDescription().getName());
/*     */       
/* 363 */       if (cloader instanceof PluginClassLoader) {
/* 364 */         PluginClassLoader loader = (PluginClassLoader)cloader;
/* 365 */         Set<String> names = loader.getClasses();
/*     */         
/* 367 */         for (String name : names)
/* 368 */           removeClass(name); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\java\JavaPluginLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */