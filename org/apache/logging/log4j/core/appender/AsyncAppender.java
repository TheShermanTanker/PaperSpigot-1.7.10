/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.AppenderControl;
/*     */ import org.apache.logging.log4j.core.config.AppenderRef;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationException;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAliases;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
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
/*     */ @Plugin(name = "Async", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class AsyncAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private static final int DEFAULT_QUEUE_SIZE = 128;
/*     */   private static final String SHUTDOWN = "Shutdown";
/*     */   private final BlockingQueue<Serializable> queue;
/*     */   private final boolean blocking;
/*     */   private final Configuration config;
/*     */   private final AppenderRef[] appenderRefs;
/*     */   private final String errorRef;
/*     */   private final boolean includeLocation;
/*     */   private AppenderControl errorAppender;
/*     */   private AsyncThread thread;
/*  63 */   private static final AtomicLong threadSequence = new AtomicLong(1L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AsyncAppender(String name, Filter filter, AppenderRef[] appenderRefs, String errorRef, int queueSize, boolean blocking, boolean ignoreExceptions, Configuration config, boolean includeLocation) {
/*  70 */     super(name, filter, (Layout<? extends Serializable>)null, ignoreExceptions);
/*  71 */     this.queue = new ArrayBlockingQueue<Serializable>(queueSize);
/*  72 */     this.blocking = blocking;
/*  73 */     this.config = config;
/*  74 */     this.appenderRefs = appenderRefs;
/*  75 */     this.errorRef = errorRef;
/*  76 */     this.includeLocation = includeLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  81 */     Map<String, Appender> map = this.config.getAppenders();
/*  82 */     List<AppenderControl> appenders = new ArrayList<AppenderControl>();
/*  83 */     for (AppenderRef appenderRef : this.appenderRefs) {
/*  84 */       if (map.containsKey(appenderRef.getRef())) {
/*  85 */         appenders.add(new AppenderControl(map.get(appenderRef.getRef()), appenderRef.getLevel(), appenderRef.getFilter()));
/*     */       } else {
/*     */         
/*  88 */         LOGGER.error("No appender named {} was configured", new Object[] { appenderRef });
/*     */       } 
/*     */     } 
/*  91 */     if (this.errorRef != null) {
/*  92 */       if (map.containsKey(this.errorRef)) {
/*  93 */         this.errorAppender = new AppenderControl(map.get(this.errorRef), null, null);
/*     */       } else {
/*  95 */         LOGGER.error("Unable to set up error Appender. No appender named {} was configured", new Object[] { this.errorRef });
/*     */       } 
/*     */     }
/*  98 */     if (appenders.size() > 0) {
/*  99 */       this.thread = new AsyncThread(appenders, this.queue);
/* 100 */       this.thread.setName("AsyncAppender-" + getName());
/* 101 */     } else if (this.errorRef == null) {
/* 102 */       throw new ConfigurationException("No appenders are available for AsyncAppender " + getName());
/*     */     } 
/*     */     
/* 105 */     this.thread.start();
/* 106 */     super.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 111 */     super.stop();
/* 112 */     this.thread.shutdown();
/*     */     try {
/* 114 */       this.thread.join();
/* 115 */     } catch (InterruptedException ex) {
/* 116 */       LOGGER.warn("Interrupted while stopping AsyncAppender {}", new Object[] { getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(LogEvent event) {
/* 127 */     if (!isStarted()) {
/* 128 */       throw new IllegalStateException("AsyncAppender " + getName() + " is not active");
/*     */     }
/* 130 */     if (event instanceof Log4jLogEvent) {
/* 131 */       boolean appendSuccessful = false;
/* 132 */       if (this.blocking) {
/*     */         
/*     */         try {
/* 135 */           this.queue.put(Log4jLogEvent.serialize((Log4jLogEvent)event, this.includeLocation));
/* 136 */           appendSuccessful = true;
/* 137 */         } catch (InterruptedException e) {
/* 138 */           LOGGER.warn("Interrupted while waiting for a free slot in the AsyncAppender LogEvent-queue {}", new Object[] { getName() });
/*     */         } 
/*     */       } else {
/*     */         
/* 142 */         appendSuccessful = this.queue.offer(Log4jLogEvent.serialize((Log4jLogEvent)event, this.includeLocation));
/* 143 */         if (!appendSuccessful) {
/* 144 */           error("Appender " + getName() + " is unable to write primary appenders. queue is full");
/*     */         }
/*     */       } 
/* 147 */       if (!appendSuccessful && this.errorAppender != null) {
/* 148 */         this.errorAppender.callAppender(event);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static AsyncAppender createAppender(@PluginElement("AppenderRef") AppenderRef[] appenderRefs, @PluginAttribute("errorRef") @PluginAliases({"error-ref"}) String errorRef, @PluginAttribute("blocking") String blocking, @PluginAttribute("bufferSize") String size, @PluginAttribute("name") String name, @PluginAttribute("includeLocation") String includeLocation, @PluginElement("Filter") Filter filter, @PluginConfiguration Configuration config, @PluginAttribute("ignoreExceptions") String ignore) {
/* 177 */     if (name == null) {
/* 178 */       LOGGER.error("No name provided for AsyncAppender");
/* 179 */       return null;
/*     */     } 
/* 181 */     if (appenderRefs == null) {
/* 182 */       LOGGER.error("No appender references provided to AsyncAppender {}", new Object[] { name });
/*     */     }
/*     */     
/* 185 */     boolean isBlocking = Booleans.parseBoolean(blocking, true);
/* 186 */     int queueSize = AbstractAppender.parseInt(size, 128);
/* 187 */     boolean isIncludeLocation = Boolean.parseBoolean(includeLocation);
/* 188 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/*     */     
/* 190 */     return new AsyncAppender(name, filter, appenderRefs, errorRef, queueSize, isBlocking, ignoreExceptions, config, isIncludeLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   private class AsyncThread
/*     */     extends Thread
/*     */   {
/*     */     private volatile boolean shutdown = false;
/*     */     
/*     */     private final List<AppenderControl> appenders;
/*     */     
/*     */     private final BlockingQueue<Serializable> queue;
/*     */     
/*     */     public AsyncThread(List<AppenderControl> appenders, BlockingQueue<Serializable> queue) {
/* 204 */       this.appenders = appenders;
/* 205 */       this.queue = queue;
/* 206 */       setDaemon(true);
/* 207 */       setName("AsyncAppenderThread" + AsyncAppender.threadSequence.getAndIncrement());
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 212 */       while (!this.shutdown) {
/*     */         Serializable s;
/*     */         try {
/* 215 */           s = this.queue.take();
/* 216 */           if (s != null && s instanceof String && "Shutdown".equals(s.toString())) {
/* 217 */             this.shutdown = true;
/*     */             continue;
/*     */           } 
/* 220 */         } catch (InterruptedException ex) {
/*     */           continue;
/*     */         } 
/*     */         
/* 224 */         Log4jLogEvent event = Log4jLogEvent.deserialize(s);
/* 225 */         event.setEndOfBatch(this.queue.isEmpty());
/* 226 */         boolean success = false;
/* 227 */         for (AppenderControl control : this.appenders) {
/*     */           try {
/* 229 */             control.callAppender((LogEvent)event);
/* 230 */             success = true;
/* 231 */           } catch (Exception ex) {}
/*     */         } 
/*     */ 
/*     */         
/* 235 */         if (!success && AsyncAppender.this.errorAppender != null) {
/*     */           try {
/* 237 */             AsyncAppender.this.errorAppender.callAppender((LogEvent)event);
/* 238 */           } catch (Exception ex) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 244 */       while (!this.queue.isEmpty()) {
/*     */         try {
/* 246 */           Serializable s = this.queue.take();
/* 247 */           if (s instanceof Log4jLogEvent) {
/* 248 */             Log4jLogEvent event = Log4jLogEvent.deserialize(s);
/* 249 */             event.setEndOfBatch(this.queue.isEmpty());
/* 250 */             for (AppenderControl control : this.appenders) {
/* 251 */               control.callAppender((LogEvent)event);
/*     */             }
/*     */           } 
/* 254 */         } catch (InterruptedException ex) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void shutdown() {
/* 261 */       this.shutdown = true;
/* 262 */       if (this.queue.isEmpty())
/* 263 */         this.queue.offer("Shutdown"); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\AsyncAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */