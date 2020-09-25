/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.helpers.FileUtils;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.core.lookup.Interpolator;
/*     */ import org.apache.logging.log4j.core.lookup.StrLookup;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public abstract class ConfigurationFactory
/*     */ {
/*     */   public static final String CONFIGURATION_FACTORY_PROPERTY = "log4j.configurationFactory";
/*     */   public static final String CONFIGURATION_FILE_PROPERTY = "log4j.configurationFile";
/*  81 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String TEST_PREFIX = "log4j2-test";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String DEFAULT_PREFIX = "log4j2";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CLASS_LOADER_SCHEME = "classloader";
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final int CLASS_LOADER_SCHEME_LENGTH = "classloader".length() + 1;
/*     */ 
/*     */   
/*     */   private static final String CLASS_PATH_SCHEME = "classpath";
/*     */ 
/*     */   
/* 103 */   private static final int CLASS_PATH_SCHEME_LENGTH = "classpath".length() + 1;
/*     */   
/* 105 */   private static volatile List<ConfigurationFactory> factories = null;
/*     */   
/* 107 */   private static ConfigurationFactory configFactory = new Factory();
/*     */   
/* 109 */   protected final StrSubstitutor substitutor = new StrSubstitutor((StrLookup)new Interpolator());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurationFactory getInstance() {
/* 116 */     if (factories == null) {
/* 117 */       synchronized ("log4j2-test") {
/* 118 */         if (factories == null) {
/* 119 */           List<ConfigurationFactory> list = new ArrayList<ConfigurationFactory>();
/* 120 */           String factoryClass = PropertiesUtil.getProperties().getStringProperty("log4j.configurationFactory");
/* 121 */           if (factoryClass != null) {
/* 122 */             addFactory(list, factoryClass);
/*     */           }
/* 124 */           PluginManager manager = new PluginManager("ConfigurationFactory");
/* 125 */           manager.collectPlugins();
/* 126 */           Map<String, PluginType<?>> plugins = manager.getPlugins();
/* 127 */           Set<WeightedFactory> ordered = new TreeSet<WeightedFactory>();
/* 128 */           for (PluginType<?> type : plugins.values()) {
/*     */             
/*     */             try {
/* 131 */               Class<ConfigurationFactory> clazz = type.getPluginClass();
/* 132 */               Order order = clazz.<Order>getAnnotation(Order.class);
/* 133 */               if (order != null) {
/* 134 */                 int weight = order.value();
/* 135 */                 ordered.add(new WeightedFactory(weight, clazz));
/*     */               } 
/* 137 */             } catch (Exception ex) {
/* 138 */               LOGGER.warn("Unable to add class " + type.getPluginClass());
/*     */             } 
/*     */           } 
/* 141 */           for (WeightedFactory wf : ordered) {
/* 142 */             addFactory(list, wf.factoryClass);
/*     */           }
/* 144 */           factories = Collections.unmodifiableList(list);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 149 */     return configFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addFactory(List<ConfigurationFactory> list, String factoryClass) {
/*     */     try {
/* 155 */       addFactory(list, (Class)Class.forName(factoryClass));
/* 156 */     } catch (ClassNotFoundException ex) {
/* 157 */       LOGGER.error("Unable to load class " + factoryClass, ex);
/* 158 */     } catch (Exception ex) {
/* 159 */       LOGGER.error("Unable to load class " + factoryClass, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addFactory(List<ConfigurationFactory> list, Class<ConfigurationFactory> factoryClass) {
/*     */     try {
/* 166 */       list.add(factoryClass.newInstance());
/* 167 */     } catch (Exception ex) {
/* 168 */       LOGGER.error("Unable to create instance of " + factoryClass.getName(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setConfigurationFactory(ConfigurationFactory factory) {
/* 177 */     configFactory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resetConfigurationFactory() {
/* 185 */     configFactory = new Factory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeConfigurationFactory(ConfigurationFactory factory) {
/* 193 */     if (configFactory == factory) {
/* 194 */       configFactory = new Factory();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isActive() {
/* 201 */     return true;
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
/*     */   public Configuration getConfiguration(String name, URI configLocation) {
/* 213 */     if (!isActive()) {
/* 214 */       return null;
/*     */     }
/* 216 */     if (configLocation != null) {
/* 217 */       ConfigurationSource source = getInputFromURI(configLocation);
/* 218 */       if (source != null) {
/* 219 */         return getConfiguration(source);
/*     */       }
/*     */     } 
/* 222 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ConfigurationSource getInputFromURI(URI configLocation) {
/* 231 */     File configFile = FileUtils.fileFromURI(configLocation);
/* 232 */     if (configFile != null && configFile.exists() && configFile.canRead()) {
/*     */       try {
/* 234 */         return new ConfigurationSource(new FileInputStream(configFile), configFile);
/* 235 */       } catch (FileNotFoundException ex) {
/* 236 */         LOGGER.error("Cannot locate file " + configLocation.getPath(), ex);
/*     */       } 
/*     */     }
/* 239 */     String scheme = configLocation.getScheme();
/* 240 */     boolean isClassLoaderScheme = (scheme != null && scheme.equals("classloader"));
/* 241 */     boolean isClassPathScheme = (scheme != null && !isClassLoaderScheme && scheme.equals("classpath"));
/* 242 */     if (scheme == null || isClassLoaderScheme || isClassPathScheme) {
/* 243 */       String path; ClassLoader loader = getClass().getClassLoader();
/*     */       
/* 245 */       if (isClassLoaderScheme) {
/* 246 */         path = configLocation.toString().substring(CLASS_LOADER_SCHEME_LENGTH);
/* 247 */       } else if (isClassPathScheme) {
/* 248 */         path = configLocation.toString().substring(CLASS_PATH_SCHEME_LENGTH);
/*     */       } else {
/* 250 */         path = configLocation.getPath();
/*     */       } 
/* 252 */       ConfigurationSource source = getInputFromResource(path, loader);
/* 253 */       if (source != null) {
/* 254 */         return source;
/*     */       }
/*     */     } 
/*     */     try {
/* 258 */       return new ConfigurationSource(configLocation.toURL().openStream(), configLocation.getPath());
/* 259 */     } catch (MalformedURLException ex) {
/* 260 */       LOGGER.error("Invalid URL " + configLocation.toString(), ex);
/* 261 */     } catch (IOException ex) {
/* 262 */       LOGGER.error("Unable to access " + configLocation.toString(), ex);
/* 263 */     } catch (Exception ex) {
/* 264 */       LOGGER.error("Unable to access " + configLocation.toString(), ex);
/*     */     } 
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ConfigurationSource getInputFromString(String config, ClassLoader loader) {
/*     */     try {
/* 277 */       URL url = new URL(config);
/* 278 */       return new ConfigurationSource(url.openStream(), FileUtils.fileFromURI(url.toURI()));
/* 279 */     } catch (Exception ex) {
/* 280 */       ConfigurationSource source = getInputFromResource(config, loader);
/* 281 */       if (source == null) {
/*     */         try {
/* 283 */           File file = new File(config);
/* 284 */           return new ConfigurationSource(new FileInputStream(file), file);
/* 285 */         } catch (FileNotFoundException fnfe) {}
/*     */       }
/*     */ 
/*     */       
/* 289 */       return source;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ConfigurationSource getInputFromResource(String resource, ClassLoader loader) {
/* 300 */     URL url = Loader.getResource(resource, loader);
/* 301 */     if (url == null) {
/* 302 */       return null;
/*     */     }
/* 304 */     InputStream is = null;
/*     */     try {
/* 306 */       is = url.openStream();
/* 307 */     } catch (IOException ioe) {
/* 308 */       return null;
/*     */     } 
/* 310 */     if (is == null) {
/* 311 */       return null;
/*     */     }
/*     */     
/* 314 */     if (FileUtils.isFile(url)) {
/*     */       try {
/* 316 */         return new ConfigurationSource(is, FileUtils.fileFromURI(url.toURI()));
/* 317 */       } catch (URISyntaxException ex) {}
/*     */     }
/*     */ 
/*     */     
/* 321 */     return new ConfigurationSource(is, resource);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract String[] getSupportedTypes();
/*     */ 
/*     */   
/*     */   public abstract Configuration getConfiguration(ConfigurationSource paramConfigurationSource);
/*     */   
/*     */   private static class WeightedFactory
/*     */     implements Comparable<WeightedFactory>
/*     */   {
/*     */     private final int weight;
/*     */     private final Class<ConfigurationFactory> factoryClass;
/*     */     
/*     */     public WeightedFactory(int weight, Class<ConfigurationFactory> clazz) {
/* 337 */       this.weight = weight;
/* 338 */       this.factoryClass = clazz;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(WeightedFactory wf) {
/* 343 */       int w = wf.weight;
/* 344 */       if (this.weight == w)
/* 345 */         return 0; 
/* 346 */       if (this.weight > w) {
/* 347 */         return -1;
/*     */       }
/* 349 */       return 1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Factory
/*     */     extends ConfigurationFactory
/*     */   {
/*     */     private Factory() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Configuration getConfiguration(String name, URI configLocation) {
/* 368 */       if (configLocation == null) {
/* 369 */         String str = this.substitutor.replace(PropertiesUtil.getProperties().getStringProperty("log4j.configurationFile"));
/*     */         
/* 371 */         if (str != null) {
/* 372 */           ConfigurationFactory.ConfigurationSource source = null;
/*     */           try {
/* 374 */             source = getInputFromURI(new URI(str));
/* 375 */           } catch (Exception ex) {}
/*     */ 
/*     */           
/* 378 */           if (source == null) {
/* 379 */             ClassLoader loader = getClass().getClassLoader();
/* 380 */             source = getInputFromString(str, loader);
/*     */           } 
/* 382 */           if (source != null) {
/* 383 */             for (ConfigurationFactory factory : ConfigurationFactory.factories) {
/* 384 */               String[] types = factory.getSupportedTypes();
/* 385 */               if (types != null) {
/* 386 */                 for (String type : types) {
/* 387 */                   if (type.equals("*") || str.endsWith(type)) {
/* 388 */                     Configuration c = factory.getConfiguration(source);
/* 389 */                     if (c != null) {
/* 390 */                       return c;
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } else {
/* 399 */         for (ConfigurationFactory factory : ConfigurationFactory.factories) {
/* 400 */           String[] types = factory.getSupportedTypes();
/* 401 */           if (types != null) {
/* 402 */             for (String type : types) {
/* 403 */               if (type.equals("*") || configLocation.toString().endsWith(type)) {
/* 404 */                 Configuration configuration = factory.getConfiguration(name, configLocation);
/* 405 */                 if (configuration != null) {
/* 406 */                   return configuration;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 414 */       Configuration config = getConfiguration(true, name);
/* 415 */       if (config == null) {
/* 416 */         config = getConfiguration(true, (String)null);
/* 417 */         if (config == null) {
/* 418 */           config = getConfiguration(false, name);
/* 419 */           if (config == null) {
/* 420 */             config = getConfiguration(false, (String)null);
/*     */           }
/*     */         } 
/*     */       } 
/* 424 */       return (config != null) ? config : new DefaultConfiguration();
/*     */     }
/*     */     
/*     */     private Configuration getConfiguration(boolean isTest, String name) {
/* 428 */       boolean named = (name != null && name.length() > 0);
/* 429 */       ClassLoader loader = getClass().getClassLoader();
/* 430 */       for (ConfigurationFactory factory : ConfigurationFactory.factories) {
/*     */         
/* 432 */         String prefix = isTest ? "log4j2-test" : "log4j2";
/* 433 */         String[] types = factory.getSupportedTypes();
/* 434 */         if (types == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 438 */         for (String suffix : types) {
/* 439 */           if (!suffix.equals("*")) {
/*     */ 
/*     */             
/* 442 */             String configName = named ? (prefix + name + suffix) : (prefix + suffix);
/*     */             
/* 444 */             ConfigurationFactory.ConfigurationSource source = getInputFromResource(configName, loader);
/* 445 */             if (source != null)
/* 446 */               return factory.getConfiguration(source); 
/*     */           } 
/*     */         } 
/*     */       } 
/* 450 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String[] getSupportedTypes() {
/* 455 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Configuration getConfiguration(ConfigurationFactory.ConfigurationSource source) {
/* 460 */       if (source != null) {
/* 461 */         String config = source.getLocation();
/* 462 */         for (ConfigurationFactory factory : ConfigurationFactory.factories) {
/* 463 */           String[] types = factory.getSupportedTypes();
/* 464 */           if (types != null) {
/* 465 */             for (String type : types) {
/* 466 */               if (type.equals("*") || (config != null && config.endsWith(type))) {
/* 467 */                 Configuration c = factory.getConfiguration(source);
/* 468 */                 if (c != null) {
/* 469 */                   return c;
/*     */                 }
/* 471 */                 LOGGER.error("Cannot determine the ConfigurationFactory to use for {}", new Object[] { config });
/* 472 */                 return null;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 478 */       LOGGER.error("Cannot process configuration, input source is null");
/* 479 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ConfigurationSource
/*     */   {
/*     */     private File file;
/*     */     
/*     */     private String location;
/*     */     
/*     */     private InputStream stream;
/*     */ 
/*     */     
/*     */     public ConfigurationSource() {}
/*     */ 
/*     */     
/*     */     public ConfigurationSource(InputStream stream) {
/* 498 */       this.stream = stream;
/* 499 */       this.file = null;
/* 500 */       this.location = null;
/*     */     }
/*     */     
/*     */     public ConfigurationSource(InputStream stream, File file) {
/* 504 */       this.stream = stream;
/* 505 */       this.file = file;
/* 506 */       this.location = file.getAbsolutePath();
/*     */     }
/*     */     
/*     */     public ConfigurationSource(InputStream stream, String location) {
/* 510 */       this.stream = stream;
/* 511 */       this.location = location;
/* 512 */       this.file = null;
/*     */     }
/*     */     
/*     */     public File getFile() {
/* 516 */       return this.file;
/*     */     }
/*     */     
/*     */     public void setFile(File file) {
/* 520 */       this.file = file;
/*     */     }
/*     */     
/*     */     public String getLocation() {
/* 524 */       return this.location;
/*     */     }
/*     */     
/*     */     public void setLocation(String location) {
/* 528 */       this.location = location;
/*     */     }
/*     */     
/*     */     public InputStream getInputStream() {
/* 532 */       return this.stream;
/*     */     }
/*     */     
/*     */     public void setInputStream(InputStream stream) {
/* 536 */       this.stream = stream;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\ConfigurationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */