/*     */ package org.bukkit.plugin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.PluginCommandYamlParser;
/*     */ import org.bukkit.command.SimpleCommandMap;
/*     */ import org.bukkit.command.defaults.TimingsCommand;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionDefault;
/*     */ import org.bukkit.util.FileUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SimplePluginManager
/*     */   implements PluginManager
/*     */ {
/*     */   private final Server server;
/*  42 */   private final Map<Pattern, PluginLoader> fileAssociations = new HashMap<Pattern, PluginLoader>();
/*  43 */   private final List<Plugin> plugins = new ArrayList<Plugin>();
/*  44 */   private final Map<String, Plugin> lookupNames = new HashMap<String, Plugin>();
/*  45 */   private static File updateDirectory = null;
/*     */   private final SimpleCommandMap commandMap;
/*  47 */   private final Map<String, Permission> permissions = new HashMap<String, Permission>();
/*  48 */   private final Map<Boolean, Set<Permission>> defaultPerms = new LinkedHashMap<Boolean, Set<Permission>>();
/*  49 */   private final Map<String, Map<Permissible, Boolean>> permSubs = new HashMap<String, Map<Permissible, Boolean>>();
/*  50 */   private final Map<Boolean, Map<Permissible, Boolean>> defSubs = new HashMap<Boolean, Map<Permissible, Boolean>>();
/*     */   private boolean useTimings = false;
/*     */   
/*     */   public SimplePluginManager(Server instance, SimpleCommandMap commandMap) {
/*  54 */     this.server = instance;
/*  55 */     this.commandMap = commandMap;
/*     */     
/*  57 */     this.defaultPerms.put(Boolean.valueOf(true), new HashSet<Permission>());
/*  58 */     this.defaultPerms.put(Boolean.valueOf(false), new HashSet<Permission>());
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
/*     */   public void registerInterface(Class<? extends PluginLoader> loader) throws IllegalArgumentException {
/*     */     PluginLoader instance;
/*  71 */     if (PluginLoader.class.isAssignableFrom(loader)) {
/*     */ 
/*     */       
/*     */       try {
/*  75 */         Constructor<? extends PluginLoader> constructor = loader.getConstructor(new Class[] { Server.class });
/*  76 */         instance = constructor.newInstance(new Object[] { this.server });
/*  77 */       } catch (NoSuchMethodException ex) {
/*  78 */         String className = loader.getName();
/*     */         
/*  80 */         throw new IllegalArgumentException(String.format("Class %s does not have a public %s(Server) constructor", new Object[] { className, className }), ex);
/*  81 */       } catch (Exception ex) {
/*  82 */         throw new IllegalArgumentException(String.format("Unexpected exception %s while attempting to construct a new instance of %s", new Object[] { ex.getClass().getName(), loader.getName() }), ex);
/*     */       } 
/*     */     } else {
/*  85 */       throw new IllegalArgumentException(String.format("Class %s does not implement interface PluginLoader", new Object[] { loader.getName() }));
/*     */     } 
/*     */     
/*  88 */     Pattern[] patterns = instance.getPluginFileFilters();
/*     */     
/*  90 */     synchronized (this) {
/*  91 */       for (Pattern pattern : patterns) {
/*  92 */         this.fileAssociations.put(pattern, instance);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plugin[] loadPlugins(File directory) {
/* 104 */     Validate.notNull(directory, "Directory cannot be null");
/* 105 */     Validate.isTrue(directory.isDirectory(), "Directory must be a directory");
/*     */     
/* 107 */     List<Plugin> result = new ArrayList<Plugin>();
/* 108 */     Set<Pattern> filters = this.fileAssociations.keySet();
/*     */     
/* 110 */     if (!this.server.getUpdateFolder().equals("")) {
/* 111 */       updateDirectory = new File(directory, this.server.getUpdateFolder());
/*     */     }
/*     */     
/* 114 */     Map<String, File> plugins = new HashMap<String, File>();
/* 115 */     Set<String> loadedPlugins = new HashSet<String>();
/* 116 */     Map<String, Collection<String>> dependencies = new HashMap<String, Collection<String>>();
/* 117 */     Map<String, Collection<String>> softDependencies = new HashMap<String, Collection<String>>();
/*     */ 
/*     */     
/* 120 */     for (File file : directory.listFiles()) {
/* 121 */       PluginLoader loader = null;
/* 122 */       for (Pattern filter : filters) {
/* 123 */         Matcher match = filter.matcher(file.getName());
/* 124 */         if (match.find()) {
/* 125 */           loader = this.fileAssociations.get(filter);
/*     */         }
/*     */       } 
/*     */       
/* 129 */       if (loader != null) {
/*     */         
/* 131 */         PluginDescriptionFile description = null;
/*     */         
/* 133 */         try { description = loader.getPluginDescription(file);
/* 134 */           String name = description.getName();
/*     */           
/* 136 */           if (name.equalsIgnoreCase("Orebfuscator")) {
/*     */             
/* 138 */             this.server.getLogger().log(Level.WARNING, "Skipping loading of Orebfuscator as it does not work with Spigot 1.8 builds!");
/*     */ 
/*     */           
/*     */           }
/* 142 */           else if (name.equalsIgnoreCase("bukkit") || name.equalsIgnoreCase("minecraft") || name.equalsIgnoreCase("mojang")) {
/* 143 */             this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': Restricted Name");
/*     */           } else {
/* 145 */             if (description.rawName.indexOf(' ') != -1) {
/* 146 */               this.server.getLogger().warning(String.format("Plugin `%s' uses the space-character (0x20) in its name `%s' - this is discouraged", new Object[] { description.getFullName(), description.rawName }));
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 157 */             File replacedFile = plugins.put(description.getName(), file);
/* 158 */             if (replacedFile != null) {
/* 159 */               this.server.getLogger().severe(String.format("Ambiguous plugin name `%s' for files `%s' and `%s' in `%s'", new Object[] { description.getName(), file.getPath(), replacedFile.getPath(), directory.getPath() }));
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 168 */             Collection<String> softDependencySet = description.getSoftDepend();
/* 169 */             if (softDependencySet != null && !softDependencySet.isEmpty()) {
/* 170 */               if (softDependencies.containsKey(description.getName())) {
/*     */                 
/* 172 */                 ((Collection<String>)softDependencies.get(description.getName())).addAll(softDependencySet);
/*     */               } else {
/* 174 */                 softDependencies.put(description.getName(), new LinkedList<String>(softDependencySet));
/*     */               } 
/*     */             }
/*     */             
/* 178 */             Collection<String> dependencySet = description.getDepend();
/* 179 */             if (dependencySet != null && !dependencySet.isEmpty()) {
/* 180 */               dependencies.put(description.getName(), new LinkedList<String>(dependencySet));
/*     */             }
/*     */             
/* 183 */             Collection<String> loadBeforeSet = description.getLoadBefore();
/* 184 */             if (loadBeforeSet != null && !loadBeforeSet.isEmpty())
/* 185 */               for (String loadBeforeTarget : loadBeforeSet) {
/* 186 */                 if (softDependencies.containsKey(loadBeforeTarget)) {
/* 187 */                   ((Collection<String>)softDependencies.get(loadBeforeTarget)).add(description.getName());
/*     */                   continue;
/*     */                 } 
/* 190 */                 Collection<String> shortSoftDependency = new LinkedList<String>();
/* 191 */                 shortSoftDependency.add(description.getName());
/* 192 */                 softDependencies.put(loadBeforeTarget, shortSoftDependency);
/*     */               }  
/*     */           }  }
/*     */         catch (InvalidDescriptionException ex) { this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex); }
/*     */       
/*     */       } 
/* 198 */     }  while (!plugins.isEmpty()) {
/* 199 */       boolean missingDependency = true;
/* 200 */       Iterator<String> pluginIterator = plugins.keySet().iterator();
/*     */       
/* 202 */       while (pluginIterator.hasNext()) {
/* 203 */         String plugin = pluginIterator.next();
/*     */         
/* 205 */         if (dependencies.containsKey(plugin)) {
/* 206 */           Iterator<String> dependencyIterator = ((Collection<String>)dependencies.get(plugin)).iterator();
/*     */           
/* 208 */           while (dependencyIterator.hasNext()) {
/* 209 */             String dependency = dependencyIterator.next();
/*     */ 
/*     */             
/* 212 */             if (loadedPlugins.contains(dependency)) {
/* 213 */               dependencyIterator.remove();
/*     */               continue;
/*     */             } 
/* 216 */             if (!plugins.containsKey(dependency)) {
/* 217 */               missingDependency = false;
/* 218 */               File file = plugins.get(plugin);
/* 219 */               pluginIterator.remove();
/* 220 */               softDependencies.remove(plugin);
/* 221 */               dependencies.remove(plugin);
/*     */               
/* 223 */               this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", new UnknownDependencyException(dependency));
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 231 */           if (dependencies.containsKey(plugin) && ((Collection)dependencies.get(plugin)).isEmpty()) {
/* 232 */             dependencies.remove(plugin);
/*     */           }
/*     */         } 
/* 235 */         if (softDependencies.containsKey(plugin)) {
/* 236 */           Iterator<String> softDependencyIterator = ((Collection<String>)softDependencies.get(plugin)).iterator();
/*     */           
/* 238 */           while (softDependencyIterator.hasNext()) {
/* 239 */             String softDependency = softDependencyIterator.next();
/*     */ 
/*     */             
/* 242 */             if (!plugins.containsKey(softDependency)) {
/* 243 */               softDependencyIterator.remove();
/*     */             }
/*     */           } 
/*     */           
/* 247 */           if (((Collection)softDependencies.get(plugin)).isEmpty()) {
/* 248 */             softDependencies.remove(plugin);
/*     */           }
/*     */         } 
/* 251 */         if (!dependencies.containsKey(plugin) && !softDependencies.containsKey(plugin) && plugins.containsKey(plugin)) {
/*     */           
/* 253 */           File file = plugins.get(plugin);
/* 254 */           pluginIterator.remove();
/* 255 */           missingDependency = false;
/*     */           
/*     */           try {
/* 258 */             result.add(loadPlugin(file));
/* 259 */             loadedPlugins.add(plugin);
/*     */           }
/* 261 */           catch (InvalidPluginException ex) {
/* 262 */             this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 267 */       if (missingDependency) {
/*     */ 
/*     */         
/* 270 */         pluginIterator = plugins.keySet().iterator();
/*     */         
/* 272 */         while (pluginIterator.hasNext()) {
/* 273 */           String plugin = pluginIterator.next();
/*     */           
/* 275 */           if (!dependencies.containsKey(plugin)) {
/* 276 */             softDependencies.remove(plugin);
/* 277 */             missingDependency = false;
/* 278 */             File file = plugins.get(plugin);
/* 279 */             pluginIterator.remove();
/*     */             
/*     */             try {
/* 282 */               result.add(loadPlugin(file));
/* 283 */               loadedPlugins.add(plugin);
/*     */               break;
/* 285 */             } catch (InvalidPluginException ex) {
/* 286 */               this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 291 */         if (missingDependency) {
/* 292 */           softDependencies.clear();
/* 293 */           dependencies.clear();
/* 294 */           Iterator<File> failedPluginIterator = plugins.values().iterator();
/*     */           
/* 296 */           while (failedPluginIterator.hasNext()) {
/* 297 */             File file = failedPluginIterator.next();
/* 298 */             failedPluginIterator.remove();
/* 299 */             this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': circular dependency detected");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     TimingsCommand.timingStart = System.nanoTime();
/* 306 */     return result.<Plugin>toArray(new Plugin[result.size()]);
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
/*     */   public synchronized Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException {
/* 322 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 324 */     checkUpdate(file);
/*     */     
/* 326 */     Set<Pattern> filters = this.fileAssociations.keySet();
/* 327 */     Plugin result = null;
/*     */     
/* 329 */     for (Pattern filter : filters) {
/* 330 */       String name = file.getName();
/* 331 */       Matcher match = filter.matcher(name);
/*     */       
/* 333 */       if (match.find()) {
/* 334 */         PluginLoader loader = this.fileAssociations.get(filter);
/*     */         
/* 336 */         result = loader.loadPlugin(file);
/*     */       } 
/*     */     } 
/*     */     
/* 340 */     if (result != null) {
/* 341 */       this.plugins.add(result);
/* 342 */       this.lookupNames.put(result.getDescription().getName(), result);
/*     */     } 
/*     */     
/* 345 */     return result;
/*     */   }
/*     */   
/*     */   private void checkUpdate(File file) {
/* 349 */     if (updateDirectory == null || !updateDirectory.isDirectory()) {
/*     */       return;
/*     */     }
/*     */     
/* 353 */     File updateFile = new File(updateDirectory, file.getName());
/* 354 */     if (updateFile.isFile() && FileUtil.copy(updateFile, file)) {
/* 355 */       updateFile.delete();
/*     */     }
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
/*     */   public synchronized Plugin getPlugin(String name) {
/* 368 */     return this.lookupNames.get(name.replace(' ', '_'));
/*     */   }
/*     */   
/*     */   public synchronized Plugin[] getPlugins() {
/* 372 */     return this.plugins.<Plugin>toArray(new Plugin[0]);
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
/*     */   public boolean isPluginEnabled(String name) {
/* 384 */     Plugin plugin = getPlugin(name);
/*     */     
/* 386 */     return isPluginEnabled(plugin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPluginEnabled(Plugin plugin) {
/* 396 */     if (plugin != null && this.plugins.contains(plugin)) {
/* 397 */       return plugin.isEnabled();
/*     */     }
/* 399 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enablePlugin(Plugin plugin) {
/* 404 */     if (!plugin.isEnabled()) {
/* 405 */       List<Command> pluginCommands = PluginCommandYamlParser.parse(plugin);
/*     */       
/* 407 */       if (!pluginCommands.isEmpty()) {
/* 408 */         this.commandMap.registerAll(plugin.getDescription().getName(), pluginCommands);
/*     */       }
/*     */       
/*     */       try {
/* 412 */         plugin.getPluginLoader().enablePlugin(plugin);
/* 413 */       } catch (Throwable ex) {
/* 414 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       } 
/*     */       
/* 417 */       HandlerList.bakeAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disablePlugins() {
/* 422 */     Plugin[] plugins = getPlugins();
/* 423 */     for (int i = plugins.length - 1; i >= 0; i--) {
/* 424 */       disablePlugin(plugins[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void disablePlugin(Plugin plugin) {
/* 429 */     if (plugin.isEnabled()) {
/*     */       try {
/* 431 */         plugin.getPluginLoader().disablePlugin(plugin);
/* 432 */       } catch (Throwable ex) {
/* 433 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       } 
/*     */       
/*     */       try {
/* 437 */         this.server.getScheduler().cancelTasks(plugin);
/* 438 */       } catch (Throwable ex) {
/* 439 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while cancelling tasks for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       } 
/*     */       
/*     */       try {
/* 443 */         this.server.getServicesManager().unregisterAll(plugin);
/* 444 */       } catch (Throwable ex) {
/* 445 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering services for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       } 
/*     */       
/*     */       try {
/* 449 */         HandlerList.unregisterAll(plugin);
/* 450 */       } catch (Throwable ex) {
/* 451 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering events for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       } 
/*     */       
/*     */       try {
/* 455 */         this.server.getMessenger().unregisterIncomingPluginChannel(plugin);
/* 456 */         this.server.getMessenger().unregisterOutgoingPluginChannel(plugin);
/* 457 */       } catch (Throwable ex) {
/* 458 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering plugin channels for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearPlugins() {
/* 464 */     synchronized (this) {
/* 465 */       disablePlugins();
/* 466 */       this.plugins.clear();
/* 467 */       this.lookupNames.clear();
/* 468 */       HandlerList.unregisterAll();
/* 469 */       this.fileAssociations.clear();
/* 470 */       this.permissions.clear();
/* 471 */       ((Set)this.defaultPerms.get(Boolean.valueOf(true))).clear();
/* 472 */       ((Set)this.defaultPerms.get(Boolean.valueOf(false))).clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void callEvent(Event event) {
/* 484 */     if (event.isAsynchronous()) {
/* 485 */       if (Thread.holdsLock(this)) {
/* 486 */         throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from inside synchronized code.");
/*     */       }
/* 488 */       if (this.server.isPrimaryThread()) {
/* 489 */         throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from primary server thread.");
/*     */       }
/* 491 */       fireEvent(event);
/*     */     } else {
/* 493 */       synchronized (this) {
/* 494 */         fireEvent(event);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireEvent(Event event) {
/* 500 */     HandlerList handlers = event.getHandlers();
/* 501 */     RegisteredListener[] listeners = handlers.getRegisteredListeners();
/*     */     
/* 503 */     for (RegisteredListener registration : listeners) {
/* 504 */       if (registration.getPlugin().isEnabled())
/*     */         
/*     */         try {
/*     */ 
/*     */           
/* 509 */           registration.callEvent(event);
/* 510 */         } catch (AuthorNagException ex) {
/* 511 */           Plugin plugin = registration.getPlugin();
/*     */           
/* 513 */           if (plugin.isNaggable()) {
/* 514 */             plugin.setNaggable(false);
/*     */             
/* 516 */             this.server.getLogger().log(Level.SEVERE, String.format("Nag author(s): '%s' of '%s' about the following: %s", new Object[] { plugin.getDescription().getAuthors(), plugin.getDescription().getFullName(), ex.getMessage() }));
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 523 */         catch (Throwable ex) {
/* 524 */           this.server.getLogger().log(Level.SEVERE, "Could not pass event " + event.getEventName() + " to " + registration.getPlugin().getDescription().getFullName(), ex);
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerEvents(Listener listener, Plugin plugin) {
/* 530 */     if (!plugin.isEnabled()) {
/* 531 */       throw new IllegalPluginAccessException("Plugin attempted to register " + listener + " while not enabled");
/*     */     }
/*     */     
/* 534 */     for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader().createRegisteredListeners(listener, plugin).entrySet()) {
/* 535 */       getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {
/* 541 */     registerEvent(event, listener, priority, executor, plugin, false);
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
/*     */   public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {
/* 557 */     Validate.notNull(listener, "Listener cannot be null");
/* 558 */     Validate.notNull(priority, "Priority cannot be null");
/* 559 */     Validate.notNull(executor, "Executor cannot be null");
/* 560 */     Validate.notNull(plugin, "Plugin cannot be null");
/*     */     
/* 562 */     if (!plugin.isEnabled()) {
/* 563 */       throw new IllegalPluginAccessException("Plugin attempted to register " + event + " while not enabled");
/*     */     }
/*     */     
/* 566 */     if (this.useTimings) {
/* 567 */       getEventListeners(event).register(new TimedRegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
/*     */     } else {
/* 569 */       getEventListeners(event).register(new RegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
/*     */     } 
/*     */   }
/*     */   
/*     */   private HandlerList getEventListeners(Class<? extends Event> type) {
/*     */     try {
/* 575 */       Method method = getRegistrationClass(type).getDeclaredMethod("getHandlerList", new Class[0]);
/* 576 */       method.setAccessible(true);
/* 577 */       return (HandlerList)method.invoke(null, new Object[0]);
/* 578 */     } catch (Exception e) {
/* 579 */       throw new IllegalPluginAccessException(e.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
/*     */     try {
/* 585 */       clazz.getDeclaredMethod("getHandlerList", new Class[0]);
/* 586 */       return clazz;
/* 587 */     } catch (NoSuchMethodException e) {
/* 588 */       if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class) && Event.class.isAssignableFrom(clazz.getSuperclass()))
/*     */       {
/*     */         
/* 591 */         return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
/*     */       }
/* 593 */       throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Permission getPermission(String name) {
/* 599 */     return this.permissions.get(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public void addPermission(Permission perm) {
/* 603 */     String name = perm.getName().toLowerCase();
/*     */     
/* 605 */     if (this.permissions.containsKey(name)) {
/* 606 */       throw new IllegalArgumentException("The permission " + name + " is already defined!");
/*     */     }
/*     */     
/* 609 */     this.permissions.put(name, perm);
/* 610 */     calculatePermissionDefault(perm);
/*     */   }
/*     */   
/*     */   public Set<Permission> getDefaultPermissions(boolean op) {
/* 614 */     return (Set<Permission>)ImmutableSet.copyOf(this.defaultPerms.get(Boolean.valueOf(op)));
/*     */   }
/*     */   
/*     */   public void removePermission(Permission perm) {
/* 618 */     removePermission(perm.getName());
/*     */   }
/*     */   
/*     */   public void removePermission(String name) {
/* 622 */     this.permissions.remove(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public void recalculatePermissionDefaults(Permission perm) {
/* 626 */     if (this.permissions.containsValue(perm)) {
/* 627 */       ((Set)this.defaultPerms.get(Boolean.valueOf(true))).remove(perm);
/* 628 */       ((Set)this.defaultPerms.get(Boolean.valueOf(false))).remove(perm);
/*     */       
/* 630 */       calculatePermissionDefault(perm);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void calculatePermissionDefault(Permission perm) {
/* 635 */     if (perm.getDefault() == PermissionDefault.OP || perm.getDefault() == PermissionDefault.TRUE) {
/* 636 */       ((Set<Permission>)this.defaultPerms.get(Boolean.valueOf(true))).add(perm);
/* 637 */       dirtyPermissibles(true);
/*     */     } 
/* 639 */     if (perm.getDefault() == PermissionDefault.NOT_OP || perm.getDefault() == PermissionDefault.TRUE) {
/* 640 */       ((Set<Permission>)this.defaultPerms.get(Boolean.valueOf(false))).add(perm);
/* 641 */       dirtyPermissibles(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dirtyPermissibles(boolean op) {
/* 646 */     Set<Permissible> permissibles = getDefaultPermSubscriptions(op);
/*     */     
/* 648 */     for (Permissible p : permissibles) {
/* 649 */       p.recalculatePermissions();
/*     */     }
/*     */   }
/*     */   
/*     */   public void subscribeToPermission(String permission, Permissible permissible) {
/* 654 */     String name = permission.toLowerCase();
/* 655 */     Map<Permissible, Boolean> map = this.permSubs.get(name);
/*     */     
/* 657 */     if (map == null) {
/* 658 */       map = new WeakHashMap<Permissible, Boolean>();
/* 659 */       this.permSubs.put(name, map);
/*     */     } 
/*     */     
/* 662 */     map.put(permissible, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void unsubscribeFromPermission(String permission, Permissible permissible) {
/* 666 */     String name = permission.toLowerCase();
/* 667 */     Map<Permissible, Boolean> map = this.permSubs.get(name);
/*     */     
/* 669 */     if (map != null) {
/* 670 */       map.remove(permissible);
/*     */       
/* 672 */       if (map.isEmpty()) {
/* 673 */         this.permSubs.remove(name);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<Permissible> getPermissionSubscriptions(String permission) {
/* 679 */     String name = permission.toLowerCase();
/* 680 */     Map<Permissible, Boolean> map = this.permSubs.get(name);
/*     */     
/* 682 */     if (map == null) {
/* 683 */       return (Set<Permissible>)ImmutableSet.of();
/*     */     }
/* 685 */     return (Set<Permissible>)ImmutableSet.copyOf(map.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public void subscribeToDefaultPerms(boolean op, Permissible permissible) {
/* 690 */     Map<Permissible, Boolean> map = this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 692 */     if (map == null) {
/* 693 */       map = new WeakHashMap<Permissible, Boolean>();
/* 694 */       this.defSubs.put(Boolean.valueOf(op), map);
/*     */     } 
/*     */     
/* 697 */     map.put(permissible, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible) {
/* 701 */     Map<Permissible, Boolean> map = this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 703 */     if (map != null) {
/* 704 */       map.remove(permissible);
/*     */       
/* 706 */       if (map.isEmpty()) {
/* 707 */         this.defSubs.remove(Boolean.valueOf(op));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<Permissible> getDefaultPermSubscriptions(boolean op) {
/* 713 */     Map<Permissible, Boolean> map = this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 715 */     if (map == null) {
/* 716 */       return (Set<Permissible>)ImmutableSet.of();
/*     */     }
/* 718 */     return (Set<Permissible>)ImmutableSet.copyOf(map.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Permission> getPermissions() {
/* 723 */     return new HashSet<Permission>(this.permissions.values());
/*     */   }
/*     */   
/*     */   public boolean useTimings() {
/* 727 */     return this.useTimings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void useTimings(boolean use) {
/* 736 */     this.useTimings = use;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\SimplePluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */