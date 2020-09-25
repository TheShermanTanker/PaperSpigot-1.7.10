/*     */ package org.apache.logging.log4j.status;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.simple.SimpleLogger;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
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
/*     */ public final class StatusLogger
/*     */   extends AbstractLogger
/*     */ {
/*     */   public static final String MAX_STATUS_ENTRIES = "log4j2.status.entries";
/*     */   private static final String NOT_AVAIL = "?";
/*  48 */   private static final PropertiesUtil PROPS = new PropertiesUtil("log4j2.StatusLogger.properties");
/*     */   
/*  50 */   private static final int MAX_ENTRIES = PROPS.getIntegerProperty("log4j2.status.entries", 200);
/*     */   
/*  52 */   private static final String DEFAULT_STATUS_LEVEL = PROPS.getStringProperty("log4j2.StatusLogger.level");
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final StatusLogger STATUS_LOGGER = new StatusLogger();
/*     */   
/*     */   private final SimpleLogger logger;
/*     */   
/*  60 */   private final CopyOnWriteArrayList<StatusListener> listeners = new CopyOnWriteArrayList<StatusListener>();
/*  61 */   private final ReentrantReadWriteLock listenersLock = new ReentrantReadWriteLock();
/*     */   
/*  63 */   private final Queue<StatusData> messages = new BoundedQueue<StatusData>(MAX_ENTRIES);
/*  64 */   private final ReentrantLock msgLock = new ReentrantLock();
/*     */   
/*     */   private int listenersLevel;
/*     */   
/*     */   private StatusLogger() {
/*  69 */     this.logger = new SimpleLogger("StatusLogger", Level.ERROR, false, true, false, false, "", null, PROPS, System.err);
/*     */     
/*  71 */     this.listenersLevel = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StatusLogger getLogger() {
/*  79 */     return STATUS_LOGGER;
/*     */   }
/*     */   
/*     */   public Level getLevel() {
/*  83 */     return this.logger.getLevel();
/*     */   }
/*     */   
/*     */   public void setLevel(Level level) {
/*  87 */     this.logger.setLevel(level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerListener(StatusListener listener) {
/*  95 */     this.listenersLock.writeLock().lock();
/*     */     try {
/*  97 */       this.listeners.add(listener);
/*  98 */       Level lvl = listener.getStatusLevel();
/*  99 */       if (this.listenersLevel < lvl.intLevel()) {
/* 100 */         this.listenersLevel = lvl.intLevel();
/*     */       }
/*     */     } finally {
/* 103 */       this.listenersLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(StatusListener listener) {
/* 112 */     this.listenersLock.writeLock().lock();
/*     */     try {
/* 114 */       this.listeners.remove(listener);
/* 115 */       int lowest = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
/* 116 */       for (StatusListener l : this.listeners) {
/* 117 */         int level = l.getStatusLevel().intLevel();
/* 118 */         if (lowest < level) {
/* 119 */           lowest = level;
/*     */         }
/*     */       } 
/* 122 */       this.listenersLevel = lowest;
/*     */     } finally {
/* 124 */       this.listenersLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<StatusListener> getListeners() {
/* 133 */     return this.listeners.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 140 */     this.listeners.clear();
/* 141 */     clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<StatusData> getStatusData() {
/* 149 */     this.msgLock.lock();
/*     */     try {
/* 151 */       return new ArrayList<StatusData>(this.messages);
/*     */     } finally {
/* 153 */       this.msgLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 161 */     this.msgLock.lock();
/*     */     try {
/* 163 */       this.messages.clear();
/*     */     } finally {
/* 165 */       this.msgLock.unlock();
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
/*     */   public void log(Marker marker, String fqcn, Level level, Message msg, Throwable t) {
/* 180 */     StackTraceElement element = null;
/* 181 */     if (fqcn != null) {
/* 182 */       element = getStackTraceElement(fqcn, Thread.currentThread().getStackTrace());
/*     */     }
/* 184 */     StatusData data = new StatusData(element, level, msg, t);
/* 185 */     this.msgLock.lock();
/*     */     try {
/* 187 */       this.messages.add(data);
/*     */     } finally {
/* 189 */       this.msgLock.unlock();
/*     */     } 
/* 191 */     if (this.listeners.size() > 0) {
/* 192 */       for (StatusListener listener : this.listeners) {
/* 193 */         if (data.getLevel().isAtLeastAsSpecificAs(listener.getStatusLevel())) {
/* 194 */           listener.log(data);
/*     */         }
/*     */       } 
/*     */     } else {
/* 198 */       this.logger.log(marker, fqcn, level, msg, t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private StackTraceElement getStackTraceElement(String fqcn, StackTraceElement[] stackTrace) {
/* 203 */     if (fqcn == null) {
/* 204 */       return null;
/*     */     }
/* 206 */     boolean next = false;
/* 207 */     for (StackTraceElement element : stackTrace) {
/* 208 */       if (next) {
/* 209 */         return element;
/*     */       }
/* 211 */       String className = element.getClassName();
/* 212 */       if (fqcn.equals(className)) {
/* 213 */         next = true;
/* 214 */       } else if ("?".equals(className)) {
/*     */         break;
/*     */       } 
/*     */     } 
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, String data) {
/* 223 */     return isEnabled(level, marker);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, String data, Throwable t) {
/* 228 */     return isEnabled(level, marker);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, String data, Object... p1) {
/* 233 */     return isEnabled(level, marker);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, Object data, Throwable t) {
/* 238 */     return isEnabled(level, marker);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isEnabled(Level level, Marker marker, Message data, Throwable t) {
/* 243 */     return isEnabled(level, marker);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(Level level, Marker marker) {
/* 248 */     if (this.listeners.size() > 0) {
/* 249 */       return (this.listenersLevel >= level.intLevel());
/*     */     }
/* 251 */     switch (level) {
/*     */       case FATAL:
/* 253 */         return this.logger.isFatalEnabled(marker);
/*     */       case TRACE:
/* 255 */         return this.logger.isTraceEnabled(marker);
/*     */       case DEBUG:
/* 257 */         return this.logger.isDebugEnabled(marker);
/*     */       case INFO:
/* 259 */         return this.logger.isInfoEnabled(marker);
/*     */       case WARN:
/* 261 */         return this.logger.isWarnEnabled(marker);
/*     */       case ERROR:
/* 263 */         return this.logger.isErrorEnabled(marker);
/*     */     } 
/* 265 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class BoundedQueue<E>
/*     */     extends ConcurrentLinkedQueue<E>
/*     */   {
/*     */     private static final long serialVersionUID = -3945953719763255337L;
/*     */ 
/*     */     
/*     */     private final int size;
/*     */ 
/*     */     
/*     */     public BoundedQueue(int size) {
/* 280 */       this.size = size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(E object) {
/* 285 */       while (StatusLogger.this.messages.size() > this.size) {
/* 286 */         StatusLogger.this.messages.poll();
/*     */       }
/* 288 */       return super.add(object);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\status\StatusLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */