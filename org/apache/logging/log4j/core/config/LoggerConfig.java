/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LifeCycle;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.filter.AbstractFilterable;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.impl.DefaultLogEventFactory;
/*     */ import org.apache.logging.log4j.core.impl.LogEventFactory;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @Plugin(name = "logger", category = "Core", printObject = true)
/*     */ public class LoggerConfig
/*     */   extends AbstractFilterable
/*     */ {
/*  62 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   private static final int MAX_RETRIES = 3;
/*     */   private static final long WAIT_TIME = 1000L;
/*  65 */   private static LogEventFactory LOG_EVENT_FACTORY = null;
/*     */   
/*  67 */   private List<AppenderRef> appenderRefs = new ArrayList<AppenderRef>();
/*  68 */   private final Map<String, AppenderControl> appenders = new ConcurrentHashMap<String, AppenderControl>();
/*     */   private final String name;
/*     */   private LogEventFactory logEventFactory;
/*     */   private Level level;
/*     */   private boolean additive = true;
/*     */   private boolean includeLocation = true;
/*     */   private LoggerConfig parent;
/*  75 */   private final AtomicInteger counter = new AtomicInteger();
/*     */   private boolean shutdown = false;
/*     */   private final Map<Property, Boolean> properties;
/*     */   private final Configuration config;
/*     */   
/*     */   static {
/*  81 */     String factory = PropertiesUtil.getProperties().getStringProperty("Log4jLogEventFactory");
/*  82 */     if (factory != null) {
/*     */       try {
/*  84 */         Class<?> clazz = Loader.loadClass(factory);
/*  85 */         if (clazz != null && LogEventFactory.class.isAssignableFrom(clazz)) {
/*  86 */           LOG_EVENT_FACTORY = (LogEventFactory)clazz.newInstance();
/*     */         }
/*  88 */       } catch (Exception ex) {
/*  89 */         LOGGER.error("Unable to create LogEventFactory " + factory, ex);
/*     */       } 
/*     */     }
/*  92 */     if (LOG_EVENT_FACTORY == null) {
/*  93 */       LOG_EVENT_FACTORY = (LogEventFactory)new DefaultLogEventFactory();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerConfig() {
/* 101 */     this.logEventFactory = LOG_EVENT_FACTORY;
/* 102 */     this.level = Level.ERROR;
/* 103 */     this.name = "";
/* 104 */     this.properties = null;
/* 105 */     this.config = null;
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
/*     */   public LoggerConfig(String name, Level level, boolean additive) {
/* 117 */     this.logEventFactory = LOG_EVENT_FACTORY;
/* 118 */     this.name = name;
/* 119 */     this.level = level;
/* 120 */     this.additive = additive;
/* 121 */     this.properties = null;
/* 122 */     this.config = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LoggerConfig(String name, List<AppenderRef> appenders, Filter filter, Level level, boolean additive, Property[] properties, Configuration config, boolean includeLocation) {
/* 130 */     super(filter);
/* 131 */     this.logEventFactory = LOG_EVENT_FACTORY;
/* 132 */     this.name = name;
/* 133 */     this.appenderRefs = appenders;
/* 134 */     this.level = level;
/* 135 */     this.additive = additive;
/* 136 */     this.includeLocation = includeLocation;
/* 137 */     this.config = config;
/* 138 */     if (properties != null && properties.length > 0) {
/* 139 */       this.properties = new HashMap<Property, Boolean>(properties.length);
/* 140 */       for (Property prop : properties) {
/* 141 */         boolean interpolate = prop.getValue().contains("${");
/* 142 */         this.properties.put(prop, Boolean.valueOf(interpolate));
/*     */       } 
/*     */     } else {
/* 145 */       this.properties = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter getFilter() {
/* 151 */     return super.getFilter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 160 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(LoggerConfig parent) {
/* 169 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerConfig getParent() {
/* 178 */     return this.parent;
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
/*     */   public void addAppender(Appender appender, Level level, Filter filter) {
/* 190 */     this.appenders.put(appender.getName(), new AppenderControl(appender, level, filter));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAppender(String name) {
/* 200 */     AppenderControl ctl = this.appenders.remove(name);
/* 201 */     if (ctl != null) {
/* 202 */       cleanupFilter(ctl);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Appender> getAppenders() {
/* 213 */     Map<String, Appender> map = new HashMap<String, Appender>();
/* 214 */     for (Map.Entry<String, AppenderControl> entry : this.appenders.entrySet())
/*     */     {
/* 216 */       map.put(entry.getKey(), ((AppenderControl)entry.getValue()).getAppender());
/*     */     }
/* 218 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearAppenders() {
/* 225 */     waitForCompletion();
/* 226 */     Collection<AppenderControl> controls = this.appenders.values();
/* 227 */     Iterator<AppenderControl> iterator = controls.iterator();
/* 228 */     while (iterator.hasNext()) {
/* 229 */       AppenderControl ctl = iterator.next();
/* 230 */       iterator.remove();
/* 231 */       cleanupFilter(ctl);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanupFilter(AppenderControl ctl) {
/* 236 */     Filter filter = ctl.getFilter();
/* 237 */     if (filter != null) {
/* 238 */       ctl.removeFilter(filter);
/* 239 */       if (filter instanceof LifeCycle) {
/* 240 */         ((LifeCycle)filter).stop();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AppenderRef> getAppenderRefs() {
/* 251 */     return this.appenderRefs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(Level level) {
/* 260 */     this.level = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level getLevel() {
/* 269 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogEventFactory getLogEventFactory() {
/* 278 */     return this.logEventFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLogEventFactory(LogEventFactory logEventFactory) {
/* 288 */     this.logEventFactory = logEventFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdditive() {
/* 297 */     return this.additive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAdditive(boolean additive) {
/* 307 */     this.additive = additive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIncludeLocation() {
/* 318 */     return this.includeLocation;
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
/*     */   public Map<Property, Boolean> getProperties() {
/* 336 */     return (this.properties == null) ? null : Collections.<Property, Boolean>unmodifiableMap(this.properties);
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
/*     */   public void log(String loggerName, Marker marker, String fqcn, Level level, Message data, Throwable t) {
/* 353 */     List<Property> props = null;
/* 354 */     if (this.properties != null) {
/* 355 */       props = new ArrayList<Property>(this.properties.size());
/*     */       
/* 357 */       for (Map.Entry<Property, Boolean> entry : this.properties.entrySet()) {
/*     */         
/* 359 */         Property prop = entry.getKey();
/* 360 */         String value = ((Boolean)entry.getValue()).booleanValue() ? this.config.getStrSubstitutor().replace(prop.getValue()) : prop.getValue();
/*     */         
/* 362 */         props.add(Property.createProperty(prop.getName(), value));
/*     */       } 
/*     */     } 
/* 365 */     LogEvent event = this.logEventFactory.createEvent(loggerName, marker, fqcn, level, data, props, t);
/*     */     
/* 367 */     log(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void waitForCompletion() {
/* 375 */     if (this.shutdown) {
/*     */       return;
/*     */     }
/* 378 */     this.shutdown = true;
/* 379 */     int retries = 0;
/* 380 */     while (this.counter.get() > 0) {
/*     */       try {
/* 382 */         wait(1000L * (retries + 1));
/* 383 */       } catch (InterruptedException ie) {
/* 384 */         if (++retries > 3) {
/*     */           break;
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
/*     */   public void log(LogEvent event) {
/* 398 */     this.counter.incrementAndGet();
/*     */     try {
/* 400 */       if (isFiltered(event)) {
/*     */         return;
/*     */       }
/*     */       
/* 404 */       event.setIncludeLocation(isIncludeLocation());
/*     */       
/* 406 */       callAppenders(event);
/*     */       
/* 408 */       if (this.additive && this.parent != null) {
/* 409 */         this.parent.log(event);
/*     */       }
/*     */     } finally {
/* 412 */       if (this.counter.decrementAndGet() == 0) {
/* 413 */         synchronized (this) {
/* 414 */           if (this.shutdown) {
/* 415 */             notifyAll();
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void callAppenders(LogEvent event) {
/* 424 */     for (AppenderControl control : this.appenders.values()) {
/* 425 */       control.callAppender(event);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 432 */     return Strings.isEmpty(this.name) ? "root" : this.name;
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
/*     */   @PluginFactory
/*     */   public static LoggerConfig createLogger(@PluginAttribute("additivity") String additivity, @PluginAttribute("level") String levelName, @PluginAttribute("name") String loggerName, @PluginAttribute("includeLocation") String includeLocation, @PluginElement("AppenderRef") AppenderRef[] refs, @PluginElement("Properties") Property[] properties, @PluginConfiguration Configuration config, @PluginElement("Filters") Filter filter) {
/*     */     Level level;
/* 458 */     if (loggerName == null) {
/* 459 */       LOGGER.error("Loggers cannot be configured without a name");
/* 460 */       return null;
/*     */     } 
/*     */     
/* 463 */     List<AppenderRef> appenderRefs = Arrays.asList(refs);
/*     */     
/*     */     try {
/* 466 */       level = Level.toLevel(levelName, Level.ERROR);
/* 467 */     } catch (Exception ex) {
/* 468 */       LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", new Object[] { levelName });
/*     */ 
/*     */       
/* 471 */       level = Level.ERROR;
/*     */     } 
/* 473 */     String name = loggerName.equals("root") ? "" : loggerName;
/* 474 */     boolean additive = Booleans.parseBoolean(additivity, true);
/*     */     
/* 476 */     return new LoggerConfig(name, appenderRefs, filter, level, additive, properties, config, includeLocation(includeLocation));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean includeLocation(String includeLocationConfigValue) {
/* 483 */     if (includeLocationConfigValue == null) {
/* 484 */       boolean sync = !AsyncLoggerContextSelector.class.getName().equals(System.getProperty("Log4jContextSelector"));
/*     */       
/* 486 */       return sync;
/*     */     } 
/* 488 */     return Boolean.parseBoolean(includeLocationConfigValue);
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
/*     */   @Plugin(name = "root", category = "Core", printObject = true)
/*     */   public static class RootLogger
/*     */     extends LoggerConfig
/*     */   {
/*     */     @PluginFactory
/*     */     public static LoggerConfig createLogger(@PluginAttribute("additivity") String additivity, @PluginAttribute("level") String levelName, @PluginAttribute("includeLocation") String includeLocation, @PluginElement("AppenderRef") AppenderRef[] refs, @PluginElement("Properties") Property[] properties, @PluginConfiguration Configuration config, @PluginElement("Filters") Filter filter) {
/*     */       Level level;
/* 506 */       List<AppenderRef> appenderRefs = Arrays.asList(refs);
/*     */       
/*     */       try {
/* 509 */         level = Level.toLevel(levelName, Level.ERROR);
/* 510 */       } catch (Exception ex) {
/* 511 */         LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", new Object[] { levelName });
/*     */ 
/*     */         
/* 514 */         level = Level.ERROR;
/*     */       } 
/* 516 */       boolean additive = Booleans.parseBoolean(additivity, true);
/*     */       
/* 518 */       return new LoggerConfig("", appenderRefs, filter, level, additive, properties, config, includeLocation(includeLocation));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\LoggerConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */