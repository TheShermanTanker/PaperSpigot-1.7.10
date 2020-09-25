/*     */ package org.apache.logging.log4j.core;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.filter.CompositeFilter;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.message.SimpleMessage;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
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
/*     */ public class Logger
/*     */   extends AbstractLogger
/*     */ {
/*     */   protected volatile PrivateConfig config;
/*     */   private final LoggerContext context;
/*     */   
/*     */   protected Logger(LoggerContext context, String name, MessageFactory messageFactory) {
/*  56 */     super(name, messageFactory);
/*  57 */     this.context = context;
/*  58 */     this.config = new PrivateConfig(context.getConfiguration(), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Logger getParent() {
/*  67 */     LoggerConfig lc = this.config.loggerConfig.getName().equals(getName()) ? this.config.loggerConfig.getParent() : this.config.loggerConfig;
/*     */     
/*  69 */     if (lc == null) {
/*  70 */       return null;
/*     */     }
/*  72 */     if (this.context.hasLogger(lc.getName())) {
/*  73 */       return this.context.getLogger(lc.getName(), getMessageFactory());
/*     */     }
/*  75 */     return new Logger(this.context, lc.getName(), getMessageFactory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerContext getContext() {
/*  83 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setLevel(Level level) {
/*  91 */     if (level != null) {
/*  92 */       this.config = new PrivateConfig(this.config, level);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level getLevel() {
/* 101 */     return this.config.level;
/*     */   }
/*     */   
/*     */   public void log(Marker marker, String fqcn, Level level, Message data, Throwable t) {
/*     */     SimpleMessage simpleMessage;
/* 106 */     if (data == null) {
/* 107 */       simpleMessage = new SimpleMessage("");
/*     */     }
/* 109 */     this.config.config.getConfigurationMonitor().checkConfiguration();
/* 110 */     this.config.loggerConfig.log(getName(), marker, fqcn, level, (Message)simpleMessage, t);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(Level level, Marker marker, String msg) {
/* 115 */     return this.config.filter(level, marker, msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(Level level, Marker marker, String msg, Throwable t) {
/* 120 */     return this.config.filter(level, marker, msg, t);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(Level level, Marker marker, String msg, Object... p1) {
/* 125 */     return this.config.filter(level, marker, msg, p1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(Level level, Marker marker, Object msg, Throwable t) {
/* 130 */     return this.config.filter(level, marker, msg, t);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(Level level, Marker marker, Message msg, Throwable t) {
/* 135 */     return this.config.filter(level, marker, msg, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAppender(Appender appender) {
/* 143 */     this.config.config.addLoggerAppender(this, appender);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAppender(Appender appender) {
/* 151 */     this.config.loggerConfig.removeAppender(appender.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Appender> getAppenders() {
/* 159 */     return this.config.loggerConfig.getAppenders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Filter> getFilters() {
/* 167 */     Filter filter = this.config.loggerConfig.getFilter();
/* 168 */     if (filter == null)
/* 169 */       return (new ArrayList<Filter>()).iterator(); 
/* 170 */     if (filter instanceof CompositeFilter) {
/* 171 */       return ((CompositeFilter)filter).iterator();
/*     */     }
/* 173 */     List<Filter> filters = new ArrayList<Filter>();
/* 174 */     filters.add(filter);
/* 175 */     return filters.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int filterCount() {
/* 184 */     Filter filter = this.config.loggerConfig.getFilter();
/* 185 */     if (filter == null)
/* 186 */       return 0; 
/* 187 */     if (filter instanceof CompositeFilter) {
/* 188 */       return ((CompositeFilter)filter).size();
/*     */     }
/* 190 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFilter(Filter filter) {
/* 198 */     this.config.config.addLoggerFilter(this, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdditive() {
/* 207 */     return this.config.loggerConfig.isAdditive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAdditive(boolean additive) {
/* 216 */     this.config.config.setLoggerAdditive(this, additive);
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
/*     */   void updateConfiguration(Configuration config) {
/* 232 */     this.config = new PrivateConfig(config, this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected class PrivateConfig
/*     */   {
/*     */     public final LoggerConfig loggerConfig;
/*     */     
/*     */     public final Configuration config;
/*     */     
/*     */     private final Level level;
/*     */     private final int intLevel;
/*     */     private final Logger logger;
/*     */     
/*     */     public PrivateConfig(Configuration config, Logger logger) {
/* 247 */       this.config = config;
/* 248 */       this.loggerConfig = config.getLoggerConfig(Logger.this.getName());
/* 249 */       this.level = this.loggerConfig.getLevel();
/* 250 */       this.intLevel = this.level.intLevel();
/* 251 */       this.logger = logger;
/*     */     }
/*     */     
/*     */     public PrivateConfig(PrivateConfig pc, Level level) {
/* 255 */       this.config = pc.config;
/* 256 */       this.loggerConfig = pc.loggerConfig;
/* 257 */       this.level = level;
/* 258 */       this.intLevel = this.level.intLevel();
/* 259 */       this.logger = pc.logger;
/*     */     }
/*     */     
/*     */     public PrivateConfig(PrivateConfig pc, LoggerConfig lc) {
/* 263 */       this.config = pc.config;
/* 264 */       this.loggerConfig = lc;
/* 265 */       this.level = lc.getLevel();
/* 266 */       this.intLevel = this.level.intLevel();
/* 267 */       this.logger = pc.logger;
/*     */     }
/*     */ 
/*     */     
/*     */     public void logEvent(LogEvent event) {
/* 272 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 273 */       this.loggerConfig.log(event);
/*     */     }
/*     */     
/*     */     boolean filter(Level level, Marker marker, String msg) {
/* 277 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 278 */       Filter filter = this.config.getFilter();
/* 279 */       if (filter != null) {
/* 280 */         Filter.Result r = filter.filter(this.logger, level, marker, msg, new Object[0]);
/* 281 */         if (r != Filter.Result.NEUTRAL) {
/* 282 */           return (r == Filter.Result.ACCEPT);
/*     */         }
/*     */       } 
/*     */       
/* 286 */       return (this.intLevel >= level.intLevel());
/*     */     }
/*     */     
/*     */     boolean filter(Level level, Marker marker, String msg, Throwable t) {
/* 290 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 291 */       Filter filter = this.config.getFilter();
/* 292 */       if (filter != null) {
/* 293 */         Filter.Result r = filter.filter(this.logger, level, marker, msg, t);
/* 294 */         if (r != Filter.Result.NEUTRAL) {
/* 295 */           return (r == Filter.Result.ACCEPT);
/*     */         }
/*     */       } 
/*     */       
/* 299 */       return (this.intLevel >= level.intLevel());
/*     */     }
/*     */     
/*     */     boolean filter(Level level, Marker marker, String msg, Object... p1) {
/* 303 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 304 */       Filter filter = this.config.getFilter();
/* 305 */       if (filter != null) {
/* 306 */         Filter.Result r = filter.filter(this.logger, level, marker, msg, p1);
/* 307 */         if (r != Filter.Result.NEUTRAL) {
/* 308 */           return (r == Filter.Result.ACCEPT);
/*     */         }
/*     */       } 
/*     */       
/* 312 */       return (this.intLevel >= level.intLevel());
/*     */     }
/*     */     
/*     */     boolean filter(Level level, Marker marker, Object msg, Throwable t) {
/* 316 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 317 */       Filter filter = this.config.getFilter();
/* 318 */       if (filter != null) {
/* 319 */         Filter.Result r = filter.filter(this.logger, level, marker, msg, t);
/* 320 */         if (r != Filter.Result.NEUTRAL) {
/* 321 */           return (r == Filter.Result.ACCEPT);
/*     */         }
/*     */       } 
/*     */       
/* 325 */       return (this.intLevel >= level.intLevel());
/*     */     }
/*     */     
/*     */     boolean filter(Level level, Marker marker, Message msg, Throwable t) {
/* 329 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 330 */       Filter filter = this.config.getFilter();
/* 331 */       if (filter != null) {
/* 332 */         Filter.Result r = filter.filter(this.logger, level, marker, msg, t);
/* 333 */         if (r != Filter.Result.NEUTRAL) {
/* 334 */           return (r == Filter.Result.ACCEPT);
/*     */         }
/*     */       } 
/*     */       
/* 338 */       return (this.intLevel >= level.intLevel());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 348 */     String nameLevel = "" + getName() + ":" + getLevel();
/* 349 */     if (this.context == null) {
/* 350 */       return nameLevel;
/*     */     }
/* 352 */     String contextName = this.context.getName();
/* 353 */     return (contextName == null) ? nameLevel : (nameLevel + " in " + contextName);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\Logger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */