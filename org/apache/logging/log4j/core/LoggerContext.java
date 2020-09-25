/*     */ package org.apache.logging.log4j.core;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.File;
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationFactory;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationListener;
/*     */ import org.apache.logging.log4j.core.config.DefaultConfiguration;
/*     */ import org.apache.logging.log4j.core.config.NullConfiguration;
/*     */ import org.apache.logging.log4j.core.config.Reconfigurable;
/*     */ import org.apache.logging.log4j.core.helpers.Assert;
/*     */ import org.apache.logging.log4j.core.helpers.NetUtils;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
/*     */ import org.apache.logging.log4j.spi.LoggerContext;
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
/*     */ public class LoggerContext
/*     */   implements LoggerContext, ConfigurationListener, LifeCycle
/*     */ {
/*     */   public static final String PROPERTY_CONFIG = "config";
/*  54 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */   
/*  56 */   private final ConcurrentMap<String, Logger> loggers = new ConcurrentHashMap<String, Logger>();
/*  57 */   private final CopyOnWriteArrayList<PropertyChangeListener> propertyChangeListeners = new CopyOnWriteArrayList<PropertyChangeListener>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private volatile Configuration config = (Configuration)new DefaultConfiguration();
/*     */   
/*     */   private Object externalContext;
/*     */   private final String name;
/*     */   private URI configLocation;
/*  68 */   private ShutdownThread shutdownThread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Status
/*     */   {
/*  75 */     INITIALIZED,
/*     */     
/*  77 */     STARTING,
/*     */     
/*  79 */     STARTED,
/*     */     
/*  81 */     STOPPING,
/*     */     
/*  83 */     STOPPED;
/*     */   }
/*     */   
/*  86 */   private volatile Status status = Status.INITIALIZED;
/*     */   
/*  88 */   private final Lock configLock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerContext(String name) {
/*  95 */     this(name, (Object)null, (URI)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerContext(String name, Object externalContext) {
/* 104 */     this(name, externalContext, (URI)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerContext(String name, Object externalContext, URI configLocn) {
/* 114 */     this.name = name;
/* 115 */     this.externalContext = externalContext;
/* 116 */     this.configLocation = configLocn;
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
/*     */   public LoggerContext(String name, Object externalContext, String configLocn) {
/* 128 */     this.name = name;
/* 129 */     this.externalContext = externalContext;
/* 130 */     if (configLocn != null) {
/*     */       URI uRI;
/*     */       try {
/* 133 */         uRI = (new File(configLocn)).toURI();
/* 134 */       } catch (Exception ex) {
/* 135 */         uRI = null;
/*     */       } 
/* 137 */       this.configLocation = uRI;
/*     */     } else {
/* 139 */       this.configLocation = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 145 */     if (this.configLock.tryLock()) {
/*     */       try {
/* 147 */         if (this.status == Status.INITIALIZED || this.status == Status.STOPPED) {
/* 148 */           this.status = Status.STARTING;
/* 149 */           reconfigure();
/* 150 */           if (this.config.isShutdownHookEnabled()) {
/* 151 */             this.shutdownThread = new ShutdownThread(this);
/*     */             try {
/* 153 */               Runtime.getRuntime().addShutdownHook(this.shutdownThread);
/* 154 */             } catch (IllegalStateException ise) {
/* 155 */               LOGGER.warn("Unable to register shutdown hook due to JVM state");
/* 156 */               this.shutdownThread = null;
/* 157 */             } catch (SecurityException se) {
/* 158 */               LOGGER.warn("Unable to register shutdown hook due to security restrictions");
/* 159 */               this.shutdownThread = null;
/*     */             } 
/*     */           } 
/* 162 */           this.status = Status.STARTED;
/*     */         } 
/*     */       } finally {
/* 165 */         this.configLock.unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(Configuration config) {
/* 175 */     if (this.configLock.tryLock()) {
/*     */       try {
/* 177 */         if ((this.status == Status.INITIALIZED || this.status == Status.STOPPED) && config.isShutdownHookEnabled()) {
/* 178 */           this.shutdownThread = new ShutdownThread(this);
/*     */           try {
/* 180 */             Runtime.getRuntime().addShutdownHook(this.shutdownThread);
/* 181 */           } catch (IllegalStateException ise) {
/* 182 */             LOGGER.warn("Unable to register shutdown hook due to JVM state");
/* 183 */             this.shutdownThread = null;
/* 184 */           } catch (SecurityException se) {
/* 185 */             LOGGER.warn("Unable to register shutdown hook due to security restrictions");
/* 186 */             this.shutdownThread = null;
/*     */           } 
/* 188 */           this.status = Status.STARTED;
/*     */         } 
/*     */       } finally {
/* 191 */         this.configLock.unlock();
/*     */       } 
/*     */     }
/* 194 */     setConfiguration(config);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 199 */     this.configLock.lock();
/*     */     try {
/* 201 */       if (this.status == Status.STOPPED) {
/*     */         return;
/*     */       }
/* 204 */       this.status = Status.STOPPING;
/* 205 */       if (this.shutdownThread != null) {
/* 206 */         Runtime.getRuntime().removeShutdownHook(this.shutdownThread);
/* 207 */         this.shutdownThread = null;
/*     */       } 
/* 209 */       Configuration prev = this.config;
/* 210 */       this.config = (Configuration)new NullConfiguration();
/* 211 */       updateLoggers();
/* 212 */       prev.stop();
/* 213 */       this.externalContext = null;
/* 214 */       LogManager.getFactory().removeContext(this);
/* 215 */       this.status = Status.STOPPED;
/*     */     } finally {
/* 217 */       this.configLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 227 */     return this.name;
/*     */   }
/*     */   
/*     */   public Status getStatus() {
/* 231 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStarted() {
/* 236 */     return (this.status == Status.STARTED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExternalContext(Object context) {
/* 244 */     this.externalContext = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getExternalContext() {
/* 253 */     return this.externalContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Logger getLogger(String name) {
/* 263 */     return getLogger(name, (MessageFactory)null);
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
/*     */   public Logger getLogger(String name, MessageFactory messageFactory) {
/* 276 */     Logger logger = this.loggers.get(name);
/* 277 */     if (logger != null) {
/* 278 */       AbstractLogger.checkMessageFactory((Logger)logger, messageFactory);
/* 279 */       return logger;
/*     */     } 
/*     */     
/* 282 */     logger = newInstance(this, name, messageFactory);
/* 283 */     Logger prev = this.loggers.putIfAbsent(name, logger);
/* 284 */     return (prev == null) ? logger : prev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasLogger(String name) {
/* 294 */     return this.loggers.containsKey(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Configuration getConfiguration() {
/* 304 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFilter(Filter filter) {
/* 313 */     this.config.addFilter(filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFilter(Filter filter) {
/* 321 */     this.config.removeFilter(filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Configuration setConfiguration(Configuration config) {
/* 330 */     if (config == null) {
/* 331 */       throw new NullPointerException("No Configuration was provided");
/*     */     }
/* 333 */     Configuration prev = this.config;
/* 334 */     config.addListener(this);
/* 335 */     Map<String, String> map = new HashMap<String, String>();
/* 336 */     map.put("hostName", NetUtils.getLocalHostname());
/* 337 */     map.put("contextName", this.name);
/* 338 */     config.addComponent("ContextProperties", map);
/* 339 */     config.start();
/* 340 */     this.config = config;
/* 341 */     updateLoggers();
/* 342 */     if (prev != null) {
/* 343 */       prev.removeListener(this);
/* 344 */       prev.stop();
/*     */     } 
/*     */ 
/*     */     
/* 348 */     PropertyChangeEvent evt = new PropertyChangeEvent(this, "config", prev, config);
/* 349 */     for (PropertyChangeListener listener : this.propertyChangeListeners) {
/* 350 */       listener.propertyChange(evt);
/*     */     }
/* 352 */     return prev;
/*     */   }
/*     */   
/*     */   public void addPropertyChangeListener(PropertyChangeListener listener) {
/* 356 */     this.propertyChangeListeners.add(Assert.isNotNull(listener, "listener"));
/*     */   }
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener listener) {
/* 360 */     this.propertyChangeListeners.remove(listener);
/*     */   }
/*     */   
/*     */   public synchronized URI getConfigLocation() {
/* 364 */     return this.configLocation;
/*     */   }
/*     */   
/*     */   public synchronized void setConfigLocation(URI configLocation) {
/* 368 */     this.configLocation = configLocation;
/* 369 */     reconfigure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void reconfigure() {
/* 376 */     LOGGER.debug("Reconfiguration started for context " + this.name);
/* 377 */     Configuration instance = ConfigurationFactory.getInstance().getConfiguration(this.name, this.configLocation);
/* 378 */     setConfiguration(instance);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     LOGGER.debug("Reconfiguration completed");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLoggers() {
/* 390 */     updateLoggers(this.config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLoggers(Configuration config) {
/* 398 */     for (Logger logger : this.loggers.values()) {
/* 399 */       logger.updateConfiguration(config);
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
/*     */   public synchronized void onChange(Reconfigurable reconfigurable) {
/* 411 */     LOGGER.debug("Reconfiguration started for context " + this.name);
/* 412 */     Configuration config = reconfigurable.reconfigure();
/* 413 */     if (config != null) {
/* 414 */       setConfiguration(config);
/* 415 */       LOGGER.debug("Reconfiguration completed");
/*     */     } else {
/* 417 */       LOGGER.debug("Reconfiguration failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Logger newInstance(LoggerContext ctx, String name, MessageFactory messageFactory) {
/* 423 */     return new Logger(ctx, name, messageFactory);
/*     */   }
/*     */   
/*     */   private class ShutdownThread
/*     */     extends Thread {
/*     */     private final LoggerContext context;
/*     */     
/*     */     public ShutdownThread(LoggerContext context) {
/* 431 */       this.context = context;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 436 */       this.context.shutdownThread = null;
/* 437 */       this.context.stop();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\LoggerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */