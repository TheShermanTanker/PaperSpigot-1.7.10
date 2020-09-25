/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
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
/*     */ public class Metrics
/*     */ {
/*     */   private static final int REVISION = 6;
/*     */   private static final String BASE_URL = "http://mcstats.org";
/*     */   private static final String REPORT_URL = "/report/%s";
/*     */   private static final String CUSTOM_DATA_SEPARATOR = "~~";
/*     */   private static final int PING_INTERVAL = 10;
/*  94 */   private final Set<Graph> graphs = Collections.synchronizedSet(new HashSet<Graph>());
/*     */ 
/*     */ 
/*     */   
/*  98 */   private final Graph defaultGraph = new Graph("Default");
/*     */ 
/*     */ 
/*     */   
/*     */   private final YamlConfiguration configuration;
/*     */ 
/*     */ 
/*     */   
/*     */   private final File configurationFile;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String guid;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean debug;
/*     */ 
/*     */ 
/*     */   
/* 118 */   private final Object optOutLock = new Object();
/*     */ 
/*     */ 
/*     */   
/* 122 */   private volatile Timer task = null;
/*     */ 
/*     */   
/*     */   public Metrics() throws IOException {
/* 126 */     this.configurationFile = getConfigFile();
/* 127 */     this.configuration = YamlConfiguration.loadConfiguration(this.configurationFile);
/*     */ 
/*     */     
/* 130 */     this.configuration.addDefault("opt-out", Boolean.valueOf(false));
/* 131 */     this.configuration.addDefault("guid", UUID.randomUUID().toString());
/* 132 */     this.configuration.addDefault("debug", Boolean.valueOf(false));
/*     */ 
/*     */     
/* 135 */     if (this.configuration.get("guid", null) == null) {
/* 136 */       this.configuration.options().header("http://mcstats.org").copyDefaults(true);
/* 137 */       this.configuration.save(this.configurationFile);
/*     */     } 
/*     */ 
/*     */     
/* 141 */     this.guid = this.configuration.getString("guid");
/* 142 */     this.debug = this.configuration.getBoolean("debug", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Graph createGraph(String name) {
/* 153 */     if (name == null) {
/* 154 */       throw new IllegalArgumentException("Graph name cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 158 */     Graph graph = new Graph(name);
/*     */ 
/*     */     
/* 161 */     this.graphs.add(graph);
/*     */ 
/*     */     
/* 164 */     return graph;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGraph(Graph graph) {
/* 173 */     if (graph == null) {
/* 174 */       throw new IllegalArgumentException("Graph cannot be null");
/*     */     }
/*     */     
/* 177 */     this.graphs.add(graph);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCustomData(Plotter plotter) {
/* 186 */     if (plotter == null) {
/* 187 */       throw new IllegalArgumentException("Plotter cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 191 */     this.defaultGraph.addPlotter(plotter);
/*     */ 
/*     */     
/* 194 */     this.graphs.add(this.defaultGraph);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean start() {
/* 205 */     synchronized (this.optOutLock) {
/*     */       
/* 207 */       if (isOptOut()) {
/* 208 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 212 */       if (this.task != null) {
/* 213 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 217 */       this.task = new Timer("Spigot Metrics Thread", true);
/*     */       
/* 219 */       this.task.scheduleAtFixedRate(new TimerTask()
/*     */           {
/*     */             private boolean firstPost = true;
/*     */             
/*     */             public void run() {
/*     */               try {
/* 225 */                 synchronized (Metrics.this.optOutLock) {
/*     */                   
/* 227 */                   if (Metrics.this.isOptOut() && Metrics.this.task != null) {
/* 228 */                     Metrics.this.task.cancel();
/* 229 */                     Metrics.this.task = null;
/*     */                     
/* 231 */                     for (Metrics.Graph graph : Metrics.this.graphs) {
/* 232 */                       graph.onOptOut();
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 240 */                 Metrics.this.postPlugin(!this.firstPost);
/*     */ 
/*     */ 
/*     */                 
/* 244 */                 this.firstPost = false;
/* 245 */               } catch (IOException e) {
/* 246 */                 if (Metrics.this.debug) {
/* 247 */                   Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage());
/*     */                 }
/*     */               } 
/*     */             }
/*     */           }0L, TimeUnit.MINUTES.toMillis(10L));
/*     */       
/* 253 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOptOut() {
/* 263 */     synchronized (this.optOutLock) {
/*     */       
/*     */       try {
/* 266 */         this.configuration.load(getConfigFile());
/* 267 */       } catch (IOException ex) {
/* 268 */         if (this.debug) {
/* 269 */           Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
/*     */         }
/* 271 */         return true;
/* 272 */       } catch (InvalidConfigurationException ex) {
/* 273 */         if (this.debug) {
/* 274 */           Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
/*     */         }
/* 276 */         return true;
/*     */       } 
/* 278 */       return this.configuration.getBoolean("opt-out", false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enable() throws IOException {
/* 289 */     synchronized (this.optOutLock) {
/*     */       
/* 291 */       if (isOptOut()) {
/* 292 */         this.configuration.set("opt-out", Boolean.valueOf(false));
/* 293 */         this.configuration.save(this.configurationFile);
/*     */       } 
/*     */ 
/*     */       
/* 297 */       if (this.task == null) {
/* 298 */         start();
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
/*     */   public void disable() throws IOException {
/* 310 */     synchronized (this.optOutLock) {
/*     */       
/* 312 */       if (!isOptOut()) {
/* 313 */         this.configuration.set("opt-out", Boolean.valueOf(true));
/* 314 */         this.configuration.save(this.configurationFile);
/*     */       } 
/*     */ 
/*     */       
/* 318 */       if (this.task != null) {
/* 319 */         this.task.cancel();
/* 320 */         this.task = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getConfigFile() {
/* 339 */     return new File(new File((File)(MinecraftServer.getServer()).options.valueOf("plugins"), "PluginMetrics"), "config.yml");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void postPlugin(boolean isPing) throws IOException {
/*     */     URLConnection connection;
/* 347 */     String pluginName = "PaperSpigot";
/* 348 */     boolean onlineMode = Bukkit.getServer().getOnlineMode();
/* 349 */     String pluginVersion = (Metrics.class.getPackage().getImplementationVersion() != null) ? Metrics.class.getPackage().getImplementationVersion() : "unknown";
/* 350 */     String serverVersion = Bukkit.getVersion();
/* 351 */     int playersOnline = Bukkit.getServer().getOnlinePlayers().size();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     StringBuilder data = new StringBuilder();
/*     */ 
/*     */     
/* 359 */     data.append(encode("guid")).append('=').append(encode(this.guid));
/* 360 */     encodeDataPair(data, "version", pluginVersion);
/* 361 */     encodeDataPair(data, "server", serverVersion);
/* 362 */     encodeDataPair(data, "players", Integer.toString(playersOnline));
/* 363 */     encodeDataPair(data, "revision", String.valueOf(6));
/*     */ 
/*     */     
/* 366 */     String osname = System.getProperty("os.name");
/* 367 */     String osarch = System.getProperty("os.arch");
/* 368 */     String osversion = System.getProperty("os.version");
/* 369 */     String java_version = System.getProperty("java.version");
/* 370 */     int coreCount = Runtime.getRuntime().availableProcessors();
/*     */ 
/*     */     
/* 373 */     if (osarch.equals("amd64")) {
/* 374 */       osarch = "x86_64";
/*     */     }
/*     */     
/* 377 */     encodeDataPair(data, "osname", osname);
/* 378 */     encodeDataPair(data, "osarch", osarch);
/* 379 */     encodeDataPair(data, "osversion", osversion);
/* 380 */     encodeDataPair(data, "cores", Integer.toString(coreCount));
/* 381 */     encodeDataPair(data, "online-mode", Boolean.toString(onlineMode));
/* 382 */     encodeDataPair(data, "java_version", java_version);
/*     */ 
/*     */     
/* 385 */     if (isPing) {
/* 386 */       encodeDataPair(data, "ping", "true");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 391 */     synchronized (this.graphs) {
/* 392 */       Iterator<Graph> iter = this.graphs.iterator();
/*     */       
/* 394 */       while (iter.hasNext()) {
/* 395 */         Graph graph = iter.next();
/*     */         
/* 397 */         for (Plotter plotter : graph.getPlotters()) {
/*     */ 
/*     */ 
/*     */           
/* 401 */           String key = String.format("C%s%s%s%s", new Object[] { "~~", graph.getName(), "~~", plotter.getColumnName() });
/*     */ 
/*     */ 
/*     */           
/* 405 */           String value = Integer.toString(plotter.getValue());
/*     */ 
/*     */           
/* 408 */           encodeDataPair(data, key, value);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 414 */     URL url = new URL("http://mcstats.org" + String.format("/report/%s", new Object[] { encode(pluginName) }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 421 */     if (isMineshafterPresent()) {
/* 422 */       connection = url.openConnection(Proxy.NO_PROXY);
/*     */     } else {
/* 424 */       connection = url.openConnection();
/*     */     } 
/*     */     
/* 427 */     connection.setDoOutput(true);
/*     */ 
/*     */     
/* 430 */     OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
/* 431 */     writer.write(data.toString());
/* 432 */     writer.flush();
/*     */ 
/*     */     
/* 435 */     BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 436 */     String response = reader.readLine();
/*     */ 
/*     */     
/* 439 */     writer.close();
/* 440 */     reader.close();
/*     */     
/* 442 */     if (response == null || response.startsWith("ERR")) {
/* 443 */       throw new IOException(response);
/*     */     }
/*     */     
/* 446 */     if (response.contains("OK This is your first update this hour")) {
/* 447 */       synchronized (this.graphs) {
/* 448 */         Iterator<Graph> iter = this.graphs.iterator();
/*     */         
/* 450 */         while (iter.hasNext()) {
/* 451 */           Graph graph = iter.next();
/*     */           
/* 453 */           for (Plotter plotter : graph.getPlotters()) {
/* 454 */             plotter.reset();
/*     */           }
/*     */         } 
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
/*     */   private boolean isMineshafterPresent() {
/*     */     try {
/* 469 */       Class.forName("mineshafter.MineServer");
/* 470 */       return true;
/* 471 */     } catch (Exception e) {
/* 472 */       return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void encodeDataPair(StringBuilder buffer, String key, String value) throws UnsupportedEncodingException {
/* 490 */     buffer.append('&').append(encode(key)).append('=').append(encode(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String encode(String text) throws UnsupportedEncodingException {
/* 500 */     return URLEncoder.encode(text, "UTF-8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Graph
/*     */   {
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 516 */     private final Set<Metrics.Plotter> plotters = new LinkedHashSet<Metrics.Plotter>();
/*     */     
/*     */     private Graph(String name) {
/* 519 */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 528 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addPlotter(Metrics.Plotter plotter) {
/* 537 */       this.plotters.add(plotter);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void removePlotter(Metrics.Plotter plotter) {
/* 546 */       this.plotters.remove(plotter);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Metrics.Plotter> getPlotters() {
/* 555 */       return Collections.unmodifiableSet(this.plotters);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 560 */       return this.name.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object object) {
/* 565 */       if (!(object instanceof Graph)) {
/* 566 */         return false;
/*     */       }
/*     */       
/* 569 */       Graph graph = (Graph)object;
/* 570 */       return graph.name.equals(this.name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onOptOut() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class Plotter
/*     */   {
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Plotter() {
/* 594 */       this("Default");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Plotter(String name) {
/* 603 */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract int getValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getColumnName() {
/* 621 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 632 */       return getColumnName().hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object object) {
/* 637 */       if (!(object instanceof Plotter)) {
/* 638 */         return false;
/*     */       }
/*     */       
/* 641 */       Plotter plotter = (Plotter)object;
/* 642 */       return (plotter.name.equals(this.name) && plotter.getValue() == getValue());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\Metrics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */