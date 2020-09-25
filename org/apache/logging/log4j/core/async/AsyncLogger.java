/*     */ package org.apache.logging.log4j.core.async;
/*     */ 
/*     */ import com.lmax.disruptor.BlockingWaitStrategy;
/*     */ import com.lmax.disruptor.EventHandler;
/*     */ import com.lmax.disruptor.ExceptionHandler;
/*     */ import com.lmax.disruptor.RingBuffer;
/*     */ import com.lmax.disruptor.SleepingWaitStrategy;
/*     */ import com.lmax.disruptor.WaitStrategy;
/*     */ import com.lmax.disruptor.YieldingWaitStrategy;
/*     */ import com.lmax.disruptor.dsl.Disruptor;
/*     */ import com.lmax.disruptor.dsl.ProducerType;
/*     */ import com.lmax.disruptor.util.Util;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.Property;
/*     */ import org.apache.logging.log4j.core.helpers.Clock;
/*     */ import org.apache.logging.log4j.core.helpers.ClockFactory;
/*     */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsyncLogger
/*     */   extends Logger
/*     */ {
/*     */   private static final int HALF_A_SECOND = 500;
/*     */   private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 20;
/*     */   private static final int RINGBUFFER_MIN_SIZE = 128;
/*     */   private static final int RINGBUFFER_DEFAULT_SIZE = 262144;
/*  79 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */   
/*     */   private static volatile Disruptor<RingBufferLogEvent> disruptor;
/*  82 */   private static Clock clock = ClockFactory.getClock();
/*     */   
/*  84 */   private static ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory("AsyncLogger-"));
/*     */   
/*  86 */   private final ThreadLocal<Info> threadlocalInfo = new ThreadLocal<Info>();
/*     */   
/*     */   static {
/*  89 */     int ringBufferSize = calculateRingBufferSize();
/*     */     
/*  91 */     WaitStrategy waitStrategy = createWaitStrategy();
/*  92 */     disruptor = new Disruptor(RingBufferLogEvent.FACTORY, ringBufferSize, executor, ProducerType.MULTI, waitStrategy);
/*     */ 
/*     */     
/*  95 */     RingBufferLogEventHandler[] arrayOfRingBufferLogEventHandler = { new RingBufferLogEventHandler() };
/*     */     
/*  97 */     disruptor.handleExceptionsWith(getExceptionHandler());
/*  98 */     disruptor.handleEventsWith((EventHandler[])arrayOfRingBufferLogEventHandler);
/*     */     
/* 100 */     LOGGER.debug("Starting AsyncLogger disruptor with ringbuffer size {}...", new Object[] { Integer.valueOf(disruptor.getRingBuffer().getBufferSize()) });
/*     */ 
/*     */     
/* 103 */     disruptor.start();
/*     */   }
/*     */   
/*     */   private static int calculateRingBufferSize() {
/* 107 */     int ringBufferSize = 262144;
/* 108 */     String userPreferredRBSize = System.getProperty("AsyncLogger.RingBufferSize", String.valueOf(ringBufferSize));
/*     */     
/*     */     try {
/* 111 */       int size = Integer.parseInt(userPreferredRBSize);
/* 112 */       if (size < 128) {
/* 113 */         size = 128;
/* 114 */         LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", new Object[] { userPreferredRBSize, Integer.valueOf(128) });
/*     */       } 
/*     */ 
/*     */       
/* 118 */       ringBufferSize = size;
/* 119 */     } catch (Exception ex) {
/* 120 */       LOGGER.warn("Invalid RingBufferSize {}, using default size {}.", new Object[] { userPreferredRBSize, Integer.valueOf(ringBufferSize) });
/*     */     } 
/*     */     
/* 123 */     return Util.ceilingNextPowerOfTwo(ringBufferSize);
/*     */   }
/*     */   
/*     */   private static WaitStrategy createWaitStrategy() {
/* 127 */     String strategy = System.getProperty("AsyncLogger.WaitStrategy");
/* 128 */     LOGGER.debug("property AsyncLogger.WaitStrategy={}", new Object[] { strategy });
/* 129 */     if ("Sleep".equals(strategy)) {
/* 130 */       LOGGER.debug("disruptor event handler uses SleepingWaitStrategy");
/* 131 */       return (WaitStrategy)new SleepingWaitStrategy();
/* 132 */     }  if ("Yield".equals(strategy)) {
/* 133 */       LOGGER.debug("disruptor event handler uses YieldingWaitStrategy");
/* 134 */       return (WaitStrategy)new YieldingWaitStrategy();
/* 135 */     }  if ("Block".equals(strategy)) {
/* 136 */       LOGGER.debug("disruptor event handler uses BlockingWaitStrategy");
/* 137 */       return (WaitStrategy)new BlockingWaitStrategy();
/*     */     } 
/* 139 */     LOGGER.debug("disruptor event handler uses SleepingWaitStrategy");
/* 140 */     return (WaitStrategy)new SleepingWaitStrategy();
/*     */   }
/*     */   
/*     */   private static ExceptionHandler getExceptionHandler() {
/* 144 */     String cls = System.getProperty("AsyncLogger.ExceptionHandler");
/* 145 */     if (cls == null) {
/* 146 */       LOGGER.debug("No AsyncLogger.ExceptionHandler specified");
/* 147 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 152 */       Class<? extends ExceptionHandler> klass = (Class)Class.forName(cls);
/*     */       
/* 154 */       ExceptionHandler result = klass.newInstance();
/* 155 */       LOGGER.debug("AsyncLogger.ExceptionHandler=" + result);
/* 156 */       return result;
/* 157 */     } catch (Exception ignored) {
/* 158 */       LOGGER.debug("AsyncLogger.ExceptionHandler not set: error creating " + cls + ": ", ignored);
/*     */ 
/*     */       
/* 161 */       return null;
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
/*     */   public AsyncLogger(LoggerContext context, String name, MessageFactory messageFactory) {
/* 175 */     super(context, name, messageFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Info
/*     */   {
/*     */     private RingBufferLogEventTranslator translator;
/*     */     
/*     */     private String cachedThreadName;
/*     */     
/*     */     private Info() {}
/*     */   }
/*     */   
/*     */   public void log(Marker marker, String fqcn, Level level, Message data, Throwable t) {
/* 189 */     Info info = this.threadlocalInfo.get();
/* 190 */     if (info == null) {
/* 191 */       info = new Info();
/* 192 */       info.translator = new RingBufferLogEventTranslator();
/* 193 */       info.cachedThreadName = Thread.currentThread().getName();
/* 194 */       this.threadlocalInfo.set(info);
/*     */     } 
/*     */     
/* 197 */     boolean includeLocation = this.config.loggerConfig.isIncludeLocation();
/* 198 */     info.translator.setValues(this, getName(), marker, fqcn, level, data, t, ThreadContext.getImmutableContext(), ThreadContext.getImmutableStack(), info.cachedThreadName, includeLocation ? location(fqcn) : null, clock.currentTimeMillis());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     disruptor.publishEvent(info.translator);
/*     */   }
/*     */   
/*     */   private StackTraceElement location(String fqcnOfLogger) {
/* 227 */     return Log4jLogEvent.calcLocation(fqcnOfLogger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actualAsyncLog(RingBufferLogEvent event) {
/* 237 */     Map<Property, Boolean> properties = this.config.loggerConfig.getProperties();
/* 238 */     event.mergePropertiesIntoContextMap(properties, this.config.config.getStrSubstitutor());
/*     */     
/* 240 */     this.config.logEvent(event);
/*     */   }
/*     */   
/*     */   public static void stop() {
/* 244 */     Disruptor<RingBufferLogEvent> temp = disruptor;
/*     */ 
/*     */ 
/*     */     
/* 248 */     disruptor = null;
/* 249 */     temp.shutdown();
/*     */ 
/*     */     
/* 252 */     RingBuffer<RingBufferLogEvent> ringBuffer = temp.getRingBuffer();
/* 253 */     for (int i = 0; i < 20 && 
/* 254 */       !ringBuffer.hasAvailableCapacity(ringBuffer.getBufferSize()); i++) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 259 */         Thread.sleep(500L);
/* 260 */       } catch (InterruptedException e) {}
/*     */     } 
/*     */ 
/*     */     
/* 264 */     executor.shutdown();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */