/*     */ package org.apache.logging.log4j.core.config.plugins;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.helpers.Closer;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ public class PluginManager
/*     */ {
/*     */   private static final long NANOS_PER_SECOND = 1000000000L;
/*  48 */   private static ConcurrentMap<String, ConcurrentMap<String, PluginType<?>>> pluginTypeMap = new ConcurrentHashMap<String, ConcurrentMap<String, PluginType<?>>>();
/*     */ 
/*     */   
/*  51 */   private static final CopyOnWriteArrayList<String> PACKAGES = new CopyOnWriteArrayList<String>();
/*     */   
/*     */   private static final String PATH = "org/apache/logging/log4j/core/config/plugins/";
/*     */   private static final String FILENAME = "Log4j2Plugins.dat";
/*     */   private static final String LOG4J_PACKAGES = "org.apache.logging.log4j.core";
/*  56 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private static String rootDir;
/*     */   
/*  60 */   private Map<String, PluginType<?>> plugins = new HashMap<String, PluginType<?>>();
/*     */ 
/*     */   
/*     */   private final String type;
/*     */   
/*     */   private final Class<?> clazz;
/*     */ 
/*     */   
/*     */   public PluginManager(String type) {
/*  69 */     this.type = type;
/*  70 */     this.clazz = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginManager(String type, Class<?> clazz) {
/*  79 */     this.type = type;
/*  80 */     this.clazz = clazz;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  84 */     if (args == null || args.length < 1) {
/*  85 */       System.err.println("A target directory must be specified");
/*  86 */       System.exit(-1);
/*     */     } 
/*  88 */     rootDir = (args[0].endsWith("/") || args[0].endsWith("\\")) ? args[0] : (args[0] + "/");
/*     */     
/*  90 */     PluginManager manager = new PluginManager("Core");
/*  91 */     String packages = (args.length == 2) ? args[1] : null;
/*     */     
/*  93 */     manager.collectPlugins(false, packages);
/*  94 */     encode(pluginTypeMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addPackage(String p) {
/* 102 */     if (PACKAGES.addIfAbsent(p))
/*     */     {
/*     */       
/* 105 */       pluginTypeMap.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginType<?> getPluginType(String name) {
/* 115 */     return this.plugins.get(name.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, PluginType<?>> getPlugins() {
/* 123 */     return this.plugins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void collectPlugins() {
/* 130 */     collectPlugins(true, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void collectPlugins(boolean preLoad, String pkgs) {
/* 141 */     if (pluginTypeMap.containsKey(this.type)) {
/* 142 */       this.plugins = pluginTypeMap.get(this.type);
/* 143 */       preLoad = false;
/*     */     } 
/* 145 */     long start = System.nanoTime();
/* 146 */     ResolverUtil resolver = new ResolverUtil();
/* 147 */     ClassLoader classLoader = Loader.getClassLoader();
/* 148 */     if (classLoader != null) {
/* 149 */       resolver.setClassLoader(classLoader);
/*     */     }
/* 151 */     if (preLoad) {
/* 152 */       ConcurrentMap<String, ConcurrentMap<String, PluginType<?>>> map = decode(classLoader);
/* 153 */       if (map != null) {
/* 154 */         pluginTypeMap = map;
/* 155 */         this.plugins = map.get(this.type);
/*     */       } else {
/* 157 */         LOGGER.warn("Plugin preloads not available from class loader {}", new Object[] { classLoader });
/*     */       } 
/*     */     } 
/* 160 */     if (this.plugins == null || this.plugins.size() == 0) {
/* 161 */       if (pkgs == null) {
/* 162 */         if (!PACKAGES.contains("org.apache.logging.log4j.core")) {
/* 163 */           PACKAGES.add("org.apache.logging.log4j.core");
/*     */         }
/*     */       } else {
/* 166 */         String[] names = pkgs.split(",");
/* 167 */         for (String name : names) {
/* 168 */           PACKAGES.add(name);
/*     */         }
/*     */       } 
/*     */     }
/* 172 */     ResolverUtil.Test test = new PluginTest(this.clazz);
/* 173 */     for (String pkg : PACKAGES) {
/* 174 */       resolver.findInPackage(test, pkg);
/*     */     }
/* 176 */     for (Class<?> clazz : resolver.getClasses()) {
/* 177 */       Plugin plugin = clazz.<Plugin>getAnnotation(Plugin.class);
/* 178 */       String pluginCategory = plugin.category();
/* 179 */       if (!pluginTypeMap.containsKey(pluginCategory)) {
/* 180 */         pluginTypeMap.putIfAbsent(pluginCategory, new ConcurrentHashMap<String, PluginType<?>>());
/*     */       }
/* 182 */       Map<String, PluginType<?>> map = pluginTypeMap.get(pluginCategory);
/* 183 */       String type = plugin.elementType().equals("") ? plugin.name() : plugin.elementType();
/* 184 */       PluginType<?> pluginType = new PluginType(clazz, type, plugin.printObject(), plugin.deferChildren());
/* 185 */       map.put(plugin.name().toLowerCase(), pluginType);
/* 186 */       PluginAliases pluginAliases = clazz.<PluginAliases>getAnnotation(PluginAliases.class);
/* 187 */       if (pluginAliases != null) {
/* 188 */         for (String alias : pluginAliases.value()) {
/* 189 */           type = plugin.elementType().equals("") ? alias : plugin.elementType();
/* 190 */           pluginType = new PluginType(clazz, type, plugin.printObject(), plugin.deferChildren());
/* 191 */           map.put(alias.trim().toLowerCase(), pluginType);
/*     */         } 
/*     */       }
/*     */     } 
/* 195 */     long elapsed = System.nanoTime() - start;
/* 196 */     this.plugins = pluginTypeMap.get(this.type);
/* 197 */     StringBuilder sb = new StringBuilder("Generated plugins");
/* 198 */     sb.append(" in ");
/* 199 */     DecimalFormat numFormat = new DecimalFormat("#0");
/* 200 */     long seconds = elapsed / 1000000000L;
/* 201 */     elapsed %= 1000000000L;
/* 202 */     sb.append(numFormat.format(seconds)).append('.');
/* 203 */     numFormat = new DecimalFormat("000000000");
/* 204 */     sb.append(numFormat.format(elapsed)).append(" seconds");
/* 205 */     LOGGER.debug(sb.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ConcurrentMap<String, ConcurrentMap<String, PluginType<?>>> decode(ClassLoader classLoader) {
/*     */     Enumeration<URL> resources;
/*     */     try {
/* 212 */       resources = classLoader.getResources("org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat");
/* 213 */     } catch (IOException ioe) {
/* 214 */       LOGGER.warn("Unable to preload plugins", ioe);
/* 215 */       return null;
/*     */     } 
/* 217 */     ConcurrentMap<String, ConcurrentMap<String, PluginType<?>>> map = new ConcurrentHashMap<String, ConcurrentMap<String, PluginType<?>>>();
/*     */     
/* 219 */     while (resources.hasMoreElements()) {
/* 220 */       DataInputStream dis = null;
/*     */       try {
/* 222 */         URL url = resources.nextElement();
/* 223 */         LOGGER.debug("Found Plugin Map at {}", new Object[] { url.toExternalForm() });
/* 224 */         InputStream is = url.openStream();
/* 225 */         BufferedInputStream bis = new BufferedInputStream(is);
/* 226 */         dis = new DataInputStream(bis);
/* 227 */         int count = dis.readInt();
/* 228 */         for (int j = 0; j < count; j++) {
/* 229 */           String type = dis.readUTF();
/* 230 */           int entries = dis.readInt();
/* 231 */           ConcurrentMap<String, PluginType<?>> types = map.get(type);
/* 232 */           if (types == null) {
/* 233 */             types = new ConcurrentHashMap<String, PluginType<?>>(count);
/*     */           }
/* 235 */           for (int i = 0; i < entries; i++) {
/* 236 */             String key = dis.readUTF();
/* 237 */             String className = dis.readUTF();
/* 238 */             String name = dis.readUTF();
/* 239 */             boolean printable = dis.readBoolean();
/* 240 */             boolean defer = dis.readBoolean();
/* 241 */             Class<?> clazz = Class.forName(className);
/* 242 */             types.put(key, new PluginType(clazz, name, printable, defer));
/*     */           } 
/* 244 */           map.putIfAbsent(type, types);
/*     */         } 
/* 246 */       } catch (Exception ex) {
/* 247 */         LOGGER.warn("Unable to preload plugins", ex);
/* 248 */         return null;
/*     */       } finally {
/* 250 */         Closer.closeSilent(dis);
/*     */       } 
/*     */     } 
/* 253 */     return (map.size() == 0) ? null : map;
/*     */   }
/*     */   
/*     */   private static void encode(ConcurrentMap<String, ConcurrentMap<String, PluginType<?>>> map) {
/* 257 */     String fileName = rootDir + "org/apache/logging/log4j/core/config/plugins/" + "Log4j2Plugins.dat";
/* 258 */     DataOutputStream dos = null;
/*     */     try {
/* 260 */       File file = new File(rootDir + "org/apache/logging/log4j/core/config/plugins/");
/* 261 */       file.mkdirs();
/* 262 */       FileOutputStream fos = new FileOutputStream(fileName);
/* 263 */       BufferedOutputStream bos = new BufferedOutputStream(fos);
/* 264 */       dos = new DataOutputStream(bos);
/* 265 */       dos.writeInt(map.size());
/* 266 */       for (Map.Entry<String, ConcurrentMap<String, PluginType<?>>> outer : map.entrySet()) {
/* 267 */         dos.writeUTF(outer.getKey());
/* 268 */         dos.writeInt(((ConcurrentMap)outer.getValue()).size());
/* 269 */         for (Map.Entry<String, PluginType<?>> entry : (Iterable<Map.Entry<String, PluginType<?>>>)((ConcurrentMap)outer.getValue()).entrySet()) {
/* 270 */           dos.writeUTF(entry.getKey());
/* 271 */           PluginType<?> pt = entry.getValue();
/* 272 */           dos.writeUTF(pt.getPluginClass().getName());
/* 273 */           dos.writeUTF(pt.getElementName());
/* 274 */           dos.writeBoolean(pt.isObjectPrintable());
/* 275 */           dos.writeBoolean(pt.isDeferChildren());
/*     */         } 
/*     */       } 
/* 278 */     } catch (Exception ex) {
/* 279 */       ex.printStackTrace();
/*     */     } finally {
/* 281 */       Closer.closeSilent(dos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PluginTest
/*     */     extends ResolverUtil.ClassTest
/*     */   {
/*     */     private final Class<?> isA;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PluginTest(Class<?> isA) {
/* 297 */       this.isA = isA;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(Class<?> type) {
/* 307 */       return (type != null && type.isAnnotationPresent((Class)Plugin.class) && (this.isA == null || this.isA.isAssignableFrom(type)));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 313 */       StringBuilder msg = new StringBuilder("annotated with @" + Plugin.class.getSimpleName());
/* 314 */       if (this.isA != null) {
/* 315 */         msg.append(" is assignable to " + this.isA.getSimpleName());
/*     */       }
/* 317 */       return msg.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\PluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */