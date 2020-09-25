/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.appender.ConsoleAppender;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAliases;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginValue;
/*     */ import org.apache.logging.log4j.core.filter.AbstractFilterable;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*     */ import org.apache.logging.log4j.core.lookup.Interpolator;
/*     */ import org.apache.logging.log4j.core.lookup.MapLookup;
/*     */ import org.apache.logging.log4j.core.lookup.StrLookup;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.core.net.Advertiser;
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
/*     */ public class BaseConfiguration
/*     */   extends AbstractFilterable
/*     */   implements Configuration
/*     */ {
/*  68 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Node rootNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   protected final List<ConfigurationListener> listeners = new CopyOnWriteArrayList<ConfigurationListener>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   protected ConfigurationMonitor monitor = new DefaultConfigurationMonitor();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private Advertiser advertiser = new DefaultAdvertiser();
/*     */   
/*     */   protected Map<String, String> advertisedConfiguration;
/*     */   
/*  93 */   private Node advertiserNode = null;
/*     */ 
/*     */   
/*     */   private Object advertisement;
/*     */ 
/*     */   
/*     */   protected boolean isShutdownHookEnabled = true;
/*     */ 
/*     */   
/*     */   private String name;
/*     */   
/* 104 */   private ConcurrentMap<String, Appender> appenders = new ConcurrentHashMap<String, Appender>();
/*     */   
/* 106 */   private ConcurrentMap<String, LoggerConfig> loggers = new ConcurrentHashMap<String, LoggerConfig>();
/*     */   
/* 108 */   private final StrLookup tempLookup = (StrLookup)new Interpolator();
/*     */   
/* 110 */   private final StrSubstitutor subst = new StrSubstitutor(this.tempLookup);
/*     */   
/* 112 */   private LoggerConfig root = new LoggerConfig();
/*     */   
/*     */   private final boolean started = false;
/*     */   
/* 116 */   private final ConcurrentMap<String, Object> componentMap = new ConcurrentHashMap<String, Object>();
/*     */ 
/*     */   
/*     */   protected PluginManager pluginManager;
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseConfiguration() {
/* 124 */     this.pluginManager = new PluginManager("Core");
/* 125 */     this.rootNode = new Node();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getProperties() {
/* 131 */     return (Map<String, String>)this.componentMap.get("ContextProperties");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 139 */     this.pluginManager.collectPlugins();
/* 140 */     setup();
/* 141 */     setupAdvertisement();
/* 142 */     doConfigure();
/* 143 */     for (LoggerConfig logger : this.loggers.values()) {
/* 144 */       logger.startFilter();
/*     */     }
/* 146 */     for (Appender appender : this.appenders.values()) {
/* 147 */       appender.start();
/*     */     }
/* 149 */     this.root.startFilter();
/* 150 */     startFilter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 159 */     Appender[] array = (Appender[])this.appenders.values().toArray((Object[])new Appender[this.appenders.size()]);
/* 160 */     for (int i = array.length - 1; i >= 0; i--) {
/* 161 */       array[i].stop();
/*     */     }
/* 163 */     for (LoggerConfig logger : this.loggers.values()) {
/* 164 */       logger.clearAppenders();
/* 165 */       logger.stopFilter();
/*     */     } 
/* 167 */     this.root.stopFilter();
/* 168 */     stopFilter();
/* 169 */     if (this.advertiser != null && this.advertisement != null)
/*     */     {
/* 171 */       this.advertiser.unadvertise(this.advertisement);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdownHookEnabled() {
/* 177 */     return this.isShutdownHookEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {}
/*     */   
/*     */   protected Level getDefaultStatus() {
/* 184 */     String statusLevel = PropertiesUtil.getProperties().getStringProperty("Log4jDefaultStatusLevel", Level.ERROR.name());
/*     */     
/*     */     try {
/* 187 */       return Level.toLevel(statusLevel);
/* 188 */     } catch (Exception ex) {
/* 189 */       return Level.ERROR;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createAdvertiser(String advertiserString, ConfigurationFactory.ConfigurationSource configSource, byte[] buffer, String contentType) {
/* 195 */     if (advertiserString != null) {
/* 196 */       Node node = new Node(null, advertiserString, null);
/* 197 */       Map<String, String> attributes = node.getAttributes();
/* 198 */       attributes.put("content", new String(buffer));
/* 199 */       attributes.put("contentType", contentType);
/* 200 */       attributes.put("name", "configuration");
/* 201 */       if (configSource.getLocation() != null) {
/* 202 */         attributes.put("location", configSource.getLocation());
/*     */       }
/* 204 */       this.advertiserNode = node;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setupAdvertisement() {
/* 209 */     if (this.advertiserNode != null) {
/*     */       
/* 211 */       String name = this.advertiserNode.getName();
/*     */       
/* 213 */       PluginType<Advertiser> type = this.pluginManager.getPluginType(name);
/* 214 */       if (type != null) {
/*     */         
/* 216 */         Class<Advertiser> clazz = type.getPluginClass();
/*     */         try {
/* 218 */           this.advertiser = clazz.newInstance();
/* 219 */           this.advertisement = this.advertiser.advertise(this.advertiserNode.getAttributes());
/* 220 */         } catch (InstantiationException e) {
/* 221 */           System.err.println("InstantiationException attempting to instantiate advertiser: " + name);
/* 222 */         } catch (IllegalAccessException e) {
/* 223 */           System.err.println("IllegalAccessException attempting to instantiate advertiser: " + name);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getComponent(String name) {
/* 231 */     return this.componentMap.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addComponent(String name, Object obj) {
/* 236 */     this.componentMap.putIfAbsent(name, obj);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doConfigure() {
/* 241 */     boolean setRoot = false;
/* 242 */     boolean setLoggers = false;
/* 243 */     for (Node child : this.rootNode.getChildren()) {
/* 244 */       createConfiguration(child, (LogEvent)null);
/* 245 */       if (child.getObject() == null) {
/*     */         continue;
/*     */       }
/* 248 */       if (child.getName().equalsIgnoreCase("Properties")) {
/* 249 */         if (this.tempLookup == this.subst.getVariableResolver()) {
/* 250 */           this.subst.setVariableResolver((StrLookup)child.getObject()); continue;
/*     */         } 
/* 252 */         LOGGER.error("Properties declaration must be the first element in the configuration");
/*     */         continue;
/*     */       } 
/* 255 */       if (this.tempLookup == this.subst.getVariableResolver()) {
/* 256 */         Map<String, String> map = (Map<String, String>)this.componentMap.get("ContextProperties");
/* 257 */         MapLookup mapLookup = (map == null) ? null : new MapLookup(map);
/* 258 */         this.subst.setVariableResolver((StrLookup)new Interpolator((StrLookup)mapLookup));
/*     */       } 
/* 260 */       if (child.getName().equalsIgnoreCase("Appenders")) {
/* 261 */         this.appenders = (ConcurrentMap<String, Appender>)child.getObject(); continue;
/* 262 */       }  if (child.getObject() instanceof Filter) {
/* 263 */         addFilter((Filter)child.getObject()); continue;
/* 264 */       }  if (child.getName().equalsIgnoreCase("Loggers")) {
/* 265 */         Loggers l = (Loggers)child.getObject();
/* 266 */         this.loggers = l.getMap();
/* 267 */         setLoggers = true;
/* 268 */         if (l.getRoot() != null) {
/* 269 */           this.root = l.getRoot();
/* 270 */           setRoot = true;
/*     */         }  continue;
/*     */       } 
/* 273 */       LOGGER.error("Unknown object \"" + child.getName() + "\" of type " + child.getObject().getClass().getName() + " is ignored");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 278 */     if (!setLoggers) {
/* 279 */       LOGGER.warn("No Loggers were configured, using default. Is the Loggers element missing?");
/* 280 */       setToDefault(); return;
/*     */     } 
/* 282 */     if (!setRoot) {
/* 283 */       LOGGER.warn("No Root logger was configured, creating default ERROR-level Root logger with Console appender");
/* 284 */       setToDefault();
/*     */     } 
/*     */ 
/*     */     
/* 288 */     for (Map.Entry<String, LoggerConfig> entry : this.loggers.entrySet()) {
/* 289 */       LoggerConfig l = entry.getValue();
/* 290 */       for (AppenderRef ref : l.getAppenderRefs()) {
/* 291 */         Appender app = this.appenders.get(ref.getRef());
/* 292 */         if (app != null) {
/* 293 */           l.addAppender(app, ref.getLevel(), ref.getFilter()); continue;
/*     */         } 
/* 295 */         LOGGER.error("Unable to locate appender " + ref.getRef() + " for logger " + l.getName());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 301 */     setParents();
/*     */   }
/*     */   
/*     */   private void setToDefault() {
/* 305 */     setName("Default");
/* 306 */     PatternLayout patternLayout = PatternLayout.createLayout("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", null, null, null, null);
/*     */ 
/*     */     
/* 309 */     ConsoleAppender consoleAppender = ConsoleAppender.createAppender((Layout)patternLayout, null, "SYSTEM_OUT", "Console", "false", "true");
/*     */     
/* 311 */     consoleAppender.start();
/* 312 */     addAppender((Appender)consoleAppender);
/* 313 */     LoggerConfig root = getRootLogger();
/* 314 */     root.addAppender((Appender)consoleAppender, (Level)null, (Filter)null);
/*     */     
/* 316 */     String levelName = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.level");
/* 317 */     Level level = (levelName != null && Level.valueOf(levelName) != null) ? Level.valueOf(levelName) : Level.ERROR;
/*     */     
/* 319 */     root.setLevel(level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 327 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 336 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(ConfigurationListener listener) {
/* 345 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(ConfigurationListener listener) {
/* 354 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Appender getAppender(String name) {
/* 363 */     return this.appenders.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Appender> getAppenders() {
/* 372 */     return this.appenders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAppender(Appender appender) {
/* 380 */     this.appenders.put(appender.getName(), appender);
/*     */   }
/*     */ 
/*     */   
/*     */   public StrSubstitutor getStrSubstitutor() {
/* 385 */     return this.subst;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setConfigurationMonitor(ConfigurationMonitor monitor) {
/* 390 */     this.monitor = monitor;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfigurationMonitor getConfigurationMonitor() {
/* 395 */     return this.monitor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAdvertiser(Advertiser advertiser) {
/* 400 */     this.advertiser = advertiser;
/*     */   }
/*     */ 
/*     */   
/*     */   public Advertiser getAdvertiser() {
/* 405 */     return this.advertiser;
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
/*     */   public synchronized void addLoggerAppender(Logger logger, Appender appender) {
/* 420 */     String name = logger.getName();
/* 421 */     this.appenders.putIfAbsent(appender.getName(), appender);
/* 422 */     LoggerConfig lc = getLoggerConfig(name);
/* 423 */     if (lc.getName().equals(name)) {
/* 424 */       lc.addAppender(appender, (Level)null, (Filter)null);
/*     */     } else {
/* 426 */       LoggerConfig nlc = new LoggerConfig(name, lc.getLevel(), lc.isAdditive());
/* 427 */       nlc.addAppender(appender, (Level)null, (Filter)null);
/* 428 */       nlc.setParent(lc);
/* 429 */       this.loggers.putIfAbsent(name, nlc);
/* 430 */       setParents();
/* 431 */       logger.getContext().updateLoggers();
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
/*     */   public synchronized void addLoggerFilter(Logger logger, Filter filter) {
/* 445 */     String name = logger.getName();
/* 446 */     LoggerConfig lc = getLoggerConfig(name);
/* 447 */     if (lc.getName().equals(name)) {
/*     */       
/* 449 */       lc.addFilter(filter);
/*     */     } else {
/* 451 */       LoggerConfig nlc = new LoggerConfig(name, lc.getLevel(), lc.isAdditive());
/* 452 */       nlc.addFilter(filter);
/* 453 */       nlc.setParent(lc);
/* 454 */       this.loggers.putIfAbsent(name, nlc);
/* 455 */       setParents();
/* 456 */       logger.getContext().updateLoggers();
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
/*     */   public synchronized void setLoggerAdditive(Logger logger, boolean additive) {
/* 471 */     String name = logger.getName();
/* 472 */     LoggerConfig lc = getLoggerConfig(name);
/* 473 */     if (lc.getName().equals(name)) {
/* 474 */       lc.setAdditive(additive);
/*     */     } else {
/* 476 */       LoggerConfig nlc = new LoggerConfig(name, lc.getLevel(), additive);
/* 477 */       nlc.setParent(lc);
/* 478 */       this.loggers.putIfAbsent(name, nlc);
/* 479 */       setParents();
/* 480 */       logger.getContext().updateLoggers();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeAppender(String name) {
/* 491 */     for (LoggerConfig logger : this.loggers.values()) {
/* 492 */       logger.removeAppender(name);
/*     */     }
/* 494 */     Appender app = this.appenders.remove(name);
/*     */     
/* 496 */     if (app != null) {
/* 497 */       app.stop();
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
/*     */   public LoggerConfig getLoggerConfig(String name) {
/* 509 */     if (this.loggers.containsKey(name)) {
/* 510 */       return this.loggers.get(name);
/*     */     }
/* 512 */     String substr = name;
/* 513 */     while ((substr = NameUtil.getSubName(substr)) != null) {
/* 514 */       if (this.loggers.containsKey(substr)) {
/* 515 */         return this.loggers.get(substr);
/*     */       }
/*     */     } 
/* 518 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerConfig getRootLogger() {
/* 526 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, LoggerConfig> getLoggers() {
/* 535 */     return Collections.unmodifiableMap(this.loggers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerConfig getLogger(String name) {
/* 544 */     return this.loggers.get(name);
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
/*     */   public void addLogger(String name, LoggerConfig loggerConfig) {
/* 560 */     this.loggers.put(name, loggerConfig);
/* 561 */     setParents();
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
/*     */   public void removeLogger(String name) {
/* 576 */     this.loggers.remove(name);
/* 577 */     setParents();
/*     */   }
/*     */ 
/*     */   
/*     */   public void createConfiguration(Node node, LogEvent event) {
/* 582 */     PluginType<?> type = node.getType();
/* 583 */     if (type != null && type.isDeferChildren()) {
/* 584 */       node.setObject(createPluginObject(type, node, event));
/*     */     } else {
/* 586 */       for (Node child : node.getChildren()) {
/* 587 */         createConfiguration(child, event);
/*     */       }
/*     */       
/* 590 */       if (type == null) {
/* 591 */         if (node.getParent() != null) {
/* 592 */           LOGGER.error("Unable to locate plugin for " + node.getName());
/*     */         }
/*     */       } else {
/* 595 */         node.setObject(createPluginObject(type, node, event));
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
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> Object createPluginObject(PluginType<T> type, Node node, LogEvent event) {
/* 617 */     Class<T> clazz = type.getPluginClass();
/*     */     
/* 619 */     if (Map.class.isAssignableFrom(clazz)) {
/*     */       
/*     */       try {
/* 622 */         Map<String, Object> map = (Map<String, Object>)clazz.newInstance();
/* 623 */         for (Node child : node.getChildren()) {
/* 624 */           map.put(child.getName(), child.getObject());
/*     */         }
/* 626 */         return map;
/* 627 */       } catch (Exception ex) {
/* 628 */         LOGGER.warn("Unable to create Map for " + type.getElementName() + " of class " + clazz);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 633 */     if (List.class.isAssignableFrom(clazz)) {
/*     */       
/*     */       try {
/* 636 */         List<Object> list = (List<Object>)clazz.newInstance();
/* 637 */         for (Node child : node.getChildren()) {
/* 638 */           list.add(child.getObject());
/*     */         }
/* 640 */         return list;
/* 641 */       } catch (Exception ex) {
/* 642 */         LOGGER.warn("Unable to create List for " + type.getElementName() + " of class " + clazz);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 647 */     Method factoryMethod = null;
/*     */     
/* 649 */     for (Method method : clazz.getMethods()) {
/* 650 */       if (method.isAnnotationPresent((Class)PluginFactory.class)) {
/* 651 */         factoryMethod = method;
/*     */         break;
/*     */       } 
/*     */     } 
/* 655 */     if (factoryMethod == null) {
/* 656 */       return null;
/*     */     }
/*     */     
/* 659 */     Annotation[][] parmArray = factoryMethod.getParameterAnnotations();
/* 660 */     Class<?>[] parmClasses = factoryMethod.getParameterTypes();
/* 661 */     if (parmArray.length != parmClasses.length) {
/* 662 */       LOGGER.error("Number of parameter annotations does not equal the number of paramters");
/*     */     }
/* 664 */     Object[] parms = new Object[parmClasses.length];
/*     */     
/* 666 */     int index = 0;
/* 667 */     Map<String, String> attrs = node.getAttributes();
/* 668 */     List<Node> children = node.getChildren();
/* 669 */     StringBuilder sb = new StringBuilder();
/* 670 */     List<Node> used = new ArrayList<Node>();
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
/* 682 */     for (Annotation[] parmTypes : parmArray) {
/* 683 */       String[] aliases = null;
/* 684 */       for (Annotation a : parmTypes) {
/* 685 */         if (a instanceof PluginAliases) {
/* 686 */           aliases = ((PluginAliases)a).value();
/*     */         }
/*     */       } 
/* 689 */       for (Annotation a : parmTypes) {
/* 690 */         if (!(a instanceof PluginAliases)) {
/*     */ 
/*     */           
/* 693 */           if (sb.length() == 0) {
/* 694 */             sb.append(" with params(");
/*     */           } else {
/* 696 */             sb.append(", ");
/*     */           } 
/* 698 */           if (a instanceof org.apache.logging.log4j.core.config.plugins.PluginNode) {
/* 699 */             parms[index] = node;
/* 700 */             sb.append("Node=").append(node.getName());
/* 701 */           } else if (a instanceof org.apache.logging.log4j.core.config.plugins.PluginConfiguration) {
/* 702 */             parms[index] = this;
/* 703 */             if (this.name != null) {
/* 704 */               sb.append("Configuration(").append(this.name).append(")");
/*     */             } else {
/* 706 */               sb.append("Configuration");
/*     */             } 
/* 708 */           } else if (a instanceof PluginValue) {
/* 709 */             String name = ((PluginValue)a).value();
/* 710 */             String v = node.getValue();
/* 711 */             if (v == null) {
/* 712 */               v = getAttrValue("value", (String[])null, attrs);
/*     */             }
/* 714 */             String value = this.subst.replace(event, v);
/* 715 */             sb.append(name).append("=\"").append(value).append("\"");
/* 716 */             parms[index] = value;
/* 717 */           } else if (a instanceof PluginAttribute) {
/* 718 */             PluginAttribute attr = (PluginAttribute)a;
/* 719 */             String name = attr.value();
/* 720 */             String value = this.subst.replace(event, getAttrValue(name, aliases, attrs));
/* 721 */             sb.append(name).append("=\"").append(value).append("\"");
/* 722 */             parms[index] = value;
/* 723 */           } else if (a instanceof PluginElement) {
/* 724 */             PluginElement elem = (PluginElement)a;
/* 725 */             String name = elem.value();
/* 726 */             if (parmClasses[index].isArray()) {
/* 727 */               Class<?> parmClass = parmClasses[index].getComponentType();
/* 728 */               List<Object> list = new ArrayList();
/* 729 */               sb.append(name).append("={");
/* 730 */               boolean first = true;
/* 731 */               for (Node child : children) {
/* 732 */                 PluginType<?> childType = child.getType();
/* 733 */                 if (elem.value().equalsIgnoreCase(childType.getElementName()) || parmClass.isAssignableFrom(childType.getPluginClass())) {
/*     */                   
/* 735 */                   used.add(child);
/* 736 */                   if (!first) {
/* 737 */                     sb.append(", ");
/*     */                   }
/* 739 */                   first = false;
/* 740 */                   Object obj = child.getObject();
/* 741 */                   if (obj == null) {
/* 742 */                     LOGGER.error("Null object returned for " + child.getName() + " in " + node.getName());
/*     */                     
/*     */                     continue;
/*     */                   } 
/* 746 */                   if (obj.getClass().isArray()) {
/* 747 */                     printArray(sb, (Object[])obj);
/* 748 */                     parms[index] = obj;
/*     */                     break;
/*     */                   } 
/* 751 */                   sb.append(child.toString());
/* 752 */                   list.add(obj);
/*     */                 } 
/*     */               } 
/* 755 */               sb.append("}");
/* 756 */               if (parms[index] != null) {
/*     */                 break;
/*     */               }
/* 759 */               if (list.size() > 0 && !parmClass.isAssignableFrom(list.get(0).getClass())) {
/* 760 */                 LOGGER.error("Attempted to assign List containing class " + list.get(0).getClass().getName() + " to array of type " + parmClass + " for attribute " + name);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 765 */               Object[] array = (Object[])Array.newInstance(parmClass, list.size());
/* 766 */               int i = 0;
/* 767 */               for (Object obj : list) {
/* 768 */                 array[i] = obj;
/* 769 */                 i++;
/*     */               } 
/* 771 */               parms[index] = array;
/*     */             } else {
/* 773 */               Class<?> parmClass = parmClasses[index];
/* 774 */               boolean present = false;
/* 775 */               for (Node child : children) {
/* 776 */                 PluginType<?> childType = child.getType();
/* 777 */                 if (elem.value().equals(childType.getElementName()) || parmClass.isAssignableFrom(childType.getPluginClass())) {
/*     */                   
/* 779 */                   sb.append(child.getName()).append("(").append(child.toString()).append(")");
/* 780 */                   present = true;
/* 781 */                   used.add(child);
/* 782 */                   parms[index] = child.getObject();
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 786 */               if (!present)
/* 787 */                 sb.append("null"); 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 792 */       index++;
/*     */     } 
/* 794 */     if (sb.length() > 0) {
/* 795 */       sb.append(")");
/*     */     }
/*     */     
/* 798 */     if (attrs.size() > 0) {
/* 799 */       StringBuilder eb = new StringBuilder();
/* 800 */       for (String key : attrs.keySet()) {
/* 801 */         if (eb.length() == 0) {
/* 802 */           eb.append(node.getName());
/* 803 */           eb.append(" contains ");
/* 804 */           if (attrs.size() == 1) {
/* 805 */             eb.append("an invalid element or attribute ");
/*     */           } else {
/* 807 */             eb.append("invalid attributes ");
/*     */           } 
/*     */         } else {
/* 810 */           eb.append(", ");
/*     */         } 
/* 812 */         eb.append("\"");
/* 813 */         eb.append(key);
/* 814 */         eb.append("\"");
/*     */       } 
/*     */       
/* 817 */       LOGGER.error(eb.toString());
/*     */     } 
/*     */     
/* 820 */     if (!type.isDeferChildren() && used.size() != children.size()) {
/* 821 */       for (Node child : children) {
/* 822 */         if (used.contains(child)) {
/*     */           continue;
/*     */         }
/* 825 */         String nodeType = node.getType().getElementName();
/* 826 */         String start = nodeType.equals(node.getName()) ? node.getName() : (nodeType + " " + node.getName());
/* 827 */         LOGGER.error(start + " has no parameter that matches element " + child.getName());
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 832 */       int mod = factoryMethod.getModifiers();
/* 833 */       if (!Modifier.isStatic(mod)) {
/* 834 */         LOGGER.error(factoryMethod.getName() + " method is not static on class " + clazz.getName() + " for element " + node.getName());
/*     */         
/* 836 */         return null;
/*     */       } 
/* 838 */       LOGGER.debug("Calling {} on class {} for element {}{}", new Object[] { factoryMethod.getName(), clazz.getName(), node.getName(), sb.toString() });
/*     */ 
/*     */       
/* 841 */       return factoryMethod.invoke((Object)null, parms);
/*     */     
/*     */     }
/* 844 */     catch (Exception e) {
/* 845 */       LOGGER.error("Unable to invoke method " + factoryMethod.getName() + " in class " + clazz.getName() + " for element " + node.getName(), e);
/*     */ 
/*     */       
/* 848 */       return null;
/*     */     } 
/*     */   }
/*     */   private void printArray(StringBuilder sb, Object... array) {
/* 852 */     boolean first = true;
/* 853 */     for (Object obj : array) {
/* 854 */       if (!first) {
/* 855 */         sb.append(", ");
/*     */       }
/* 857 */       sb.append(obj.toString());
/* 858 */       first = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getAttrValue(String name, String[] aliases, Map<String, String> attrs) {
/* 863 */     for (String key : attrs.keySet()) {
/* 864 */       if (key.equalsIgnoreCase(name)) {
/* 865 */         String attr = attrs.get(key);
/* 866 */         attrs.remove(key);
/* 867 */         return attr;
/*     */       } 
/* 869 */       if (aliases != null) {
/* 870 */         for (String alias : aliases) {
/* 871 */           if (key.equalsIgnoreCase(alias)) {
/* 872 */             String attr = attrs.get(key);
/* 873 */             attrs.remove(key);
/* 874 */             return attr;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 879 */     return null;
/*     */   }
/*     */   
/*     */   private void setParents() {
/* 883 */     for (Map.Entry<String, LoggerConfig> entry : this.loggers.entrySet()) {
/* 884 */       LoggerConfig logger = entry.getValue();
/* 885 */       String name = entry.getKey();
/* 886 */       if (!name.equals("")) {
/* 887 */         int i = name.lastIndexOf('.');
/* 888 */         if (i > 0) {
/* 889 */           name = name.substring(0, i);
/* 890 */           LoggerConfig parent = getLoggerConfig(name);
/* 891 */           if (parent == null) {
/* 892 */             parent = this.root;
/*     */           }
/* 894 */           logger.setParent(parent); continue;
/*     */         } 
/* 896 */         logger.setParent(this.root);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\BaseConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */