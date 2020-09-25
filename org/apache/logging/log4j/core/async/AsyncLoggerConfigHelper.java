/*     */ package org.apache.logging.log4j.core.async;
/*     */ 
/*     */ import com.lmax.disruptor.BlockingWaitStrategy;
/*     */ import com.lmax.disruptor.EventFactory;
/*     */ import com.lmax.disruptor.EventHandler;
/*     */ import com.lmax.disruptor.EventTranslator;
/*     */ import com.lmax.disruptor.ExceptionHandler;
/*     */ import com.lmax.disruptor.RingBuffer;
/*     */ import com.lmax.disruptor.Sequence;
/*     */ import com.lmax.disruptor.SequenceReportingEventHandler;
/*     */ import com.lmax.disruptor.SleepingWaitStrategy;
/*     */ import com.lmax.disruptor.WaitStrategy;
/*     */ import com.lmax.disruptor.YieldingWaitStrategy;
/*     */ import com.lmax.disruptor.dsl.Disruptor;
/*     */ import com.lmax.disruptor.dsl.ProducerType;
/*     */ import com.lmax.disruptor.util.Util;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
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
/*     */ class AsyncLoggerConfigHelper
/*     */ {
/*     */   private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 20;
/*     */   private static final int HALF_A_SECOND = 500;
/*     */   private static final int RINGBUFFER_MIN_SIZE = 128;
/*     */   private static final int RINGBUFFER_DEFAULT_SIZE = 262144;
/*  64 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*  66 */   private static ThreadFactory threadFactory = new DaemonThreadFactory("AsyncLoggerConfig-");
/*     */   
/*     */   private static volatile Disruptor<Log4jEventWrapper> disruptor;
/*     */   
/*     */   private static ExecutorService executor;
/*  71 */   private static volatile int count = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final EventFactory<Log4jEventWrapper> FACTORY = new EventFactory<Log4jEventWrapper>()
/*     */     {
/*     */       public AsyncLoggerConfigHelper.Log4jEventWrapper newInstance() {
/*  80 */         return new AsyncLoggerConfigHelper.Log4jEventWrapper();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   private final EventTranslator<Log4jEventWrapper> translator = new EventTranslator<Log4jEventWrapper>()
/*     */     {
/*     */       public void translateTo(AsyncLoggerConfigHelper.Log4jEventWrapper event, long sequence)
/*     */       {
/*  91 */         event.event = AsyncLoggerConfigHelper.this.currentLogEvent.get();
/*  92 */         event.loggerConfig = AsyncLoggerConfigHelper.this.asyncLoggerConfig;
/*     */       }
/*     */     };
/*     */   
/*  96 */   private final ThreadLocal<LogEvent> currentLogEvent = new ThreadLocal<LogEvent>();
/*     */   private final AsyncLoggerConfig asyncLoggerConfig;
/*     */   
/*     */   public AsyncLoggerConfigHelper(AsyncLoggerConfig asyncLoggerConfig) {
/* 100 */     this.asyncLoggerConfig = asyncLoggerConfig;
/* 101 */     claim();
/*     */   }
/*     */   
/*     */   private static synchronized void initDisruptor() {
/* 105 */     if (disruptor != null) {
/* 106 */       LOGGER.trace("AsyncLoggerConfigHelper not starting new disruptor, using existing object. Ref count is {}.", new Object[] { Integer.valueOf(count) });
/*     */       return;
/*     */     } 
/* 109 */     LOGGER.trace("AsyncLoggerConfigHelper creating new disruptor. Ref count is {}.", new Object[] { Integer.valueOf(count) });
/* 110 */     int ringBufferSize = calculateRingBufferSize();
/* 111 */     WaitStrategy waitStrategy = createWaitStrategy();
/* 112 */     executor = Executors.newSingleThreadExecutor(threadFactory);
/* 113 */     disruptor = new Disruptor(FACTORY, ringBufferSize, executor, ProducerType.MULTI, waitStrategy);
/*     */     
/* 115 */     Log4jEventWrapperHandler[] arrayOfLog4jEventWrapperHandler = { new Log4jEventWrapperHandler() };
/*     */     
/* 117 */     ExceptionHandler errorHandler = getExceptionHandler();
/* 118 */     disruptor.handleExceptionsWith(errorHandler);
/* 119 */     disruptor.handleEventsWith((EventHandler[])arrayOfLog4jEventWrapperHandler);
/*     */     
/* 121 */     LOGGER.debug("Starting AsyncLoggerConfig disruptor with ringbuffer size={}, waitStrategy={}, exceptionHandler={}...", new Object[] { Integer.valueOf(disruptor.getRingBuffer().getBufferSize()), waitStrategy.getClass().getSimpleName(), errorHandler });
/*     */ 
/*     */     
/* 124 */     disruptor.start();
/*     */   }
/*     */   
/*     */   private static WaitStrategy createWaitStrategy() {
/* 128 */     String strategy = System.getProperty("AsyncLoggerConfig.WaitStrategy");
/*     */     
/* 130 */     LOGGER.debug("property AsyncLoggerConfig.WaitStrategy={}", new Object[] { strategy });
/* 131 */     if ("Sleep".equals(strategy))
/* 132 */       return (WaitStrategy)new SleepingWaitStrategy(); 
/* 133 */     if ("Yield".equals(strategy))
/* 134 */       return (WaitStrategy)new YieldingWaitStrategy(); 
/* 135 */     if ("Block".equals(strategy)) {
/* 136 */       return (WaitStrategy)new BlockingWaitStrategy();
/*     */     }
/* 138 */     return (WaitStrategy)new SleepingWaitStrategy();
/*     */   }
/*     */   
/*     */   private static int calculateRingBufferSize() {
/* 142 */     int ringBufferSize = 262144;
/* 143 */     String userPreferredRBSize = System.getProperty("AsyncLoggerConfig.RingBufferSize", String.valueOf(ringBufferSize));
/*     */ 
/*     */     
/*     */     try {
/* 147 */       int size = Integer.parseInt(userPreferredRBSize);
/* 148 */       if (size < 128) {
/* 149 */         size = 128;
/* 150 */         LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", new Object[] { userPreferredRBSize, Integer.valueOf(128) });
/*     */       } 
/*     */ 
/*     */       
/* 154 */       ringBufferSize = size;
/* 155 */     } catch (Exception ex) {
/* 156 */       LOGGER.warn("Invalid RingBufferSize {}, using default size {}.", new Object[] { userPreferredRBSize, Integer.valueOf(ringBufferSize) });
/*     */     } 
/*     */     
/* 159 */     return Util.ceilingNextPowerOfTwo(ringBufferSize);
/*     */   }
/*     */   
/*     */   private static ExceptionHandler getExceptionHandler() {
/* 163 */     String cls = System.getProperty("AsyncLoggerConfig.ExceptionHandler");
/*     */     
/* 165 */     if (cls == null) {
/* 166 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 170 */       Class<? extends ExceptionHandler> klass = (Class)Class.forName(cls);
/*     */       
/* 172 */       ExceptionHandler result = klass.newInstance();
/* 173 */       return result;
/* 174 */     } catch (Exception ignored) {
/* 175 */       LOGGER.debug("AsyncLoggerConfig.ExceptionHandler not set: error creating " + cls + ": ", ignored);
/*     */ 
/*     */       
/* 178 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Log4jEventWrapper
/*     */   {
/*     */     private AsyncLoggerConfig loggerConfig;
/*     */     
/*     */     private LogEvent event;
/*     */ 
/*     */     
/*     */     private Log4jEventWrapper() {}
/*     */ 
/*     */     
/*     */     public void clear() {
/* 195 */       this.loggerConfig = null;
/* 196 */       this.event = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Log4jEventWrapperHandler
/*     */     implements SequenceReportingEventHandler<Log4jEventWrapper>
/*     */   {
/*     */     private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
/*     */     private Sequence sequenceCallback;
/*     */     private int counter;
/*     */     
/*     */     private Log4jEventWrapperHandler() {}
/*     */     
/*     */     public void setSequenceCallback(Sequence sequenceCallback) {
/* 211 */       this.sequenceCallback = sequenceCallback;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEvent(AsyncLoggerConfigHelper.Log4jEventWrapper event, long sequence, boolean endOfBatch) throws Exception {
/* 217 */       event.event.setEndOfBatch(endOfBatch);
/* 218 */       event.loggerConfig.asyncCallAppenders(event.event);
/* 219 */       event.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       if (++this.counter > 50) {
/* 225 */         this.sequenceCallback.set(sequence);
/* 226 */         this.counter = 0;
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
/*     */   static synchronized void claim() {
/* 238 */     count++;
/* 239 */     initDisruptor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void release() {
/* 248 */     if (--count > 0) {
/* 249 */       LOGGER.trace("AsyncLoggerConfigHelper: not shutting down disruptor: ref count is {}.", new Object[] { Integer.valueOf(count) });
/*     */       return;
/*     */     } 
/* 252 */     Disruptor<Log4jEventWrapper> temp = disruptor;
/* 253 */     if (temp == null) {
/* 254 */       LOGGER.trace("AsyncLoggerConfigHelper: disruptor already shut down: ref count is {}.", new Object[] { Integer.valueOf(count) });
/*     */       return;
/*     */     } 
/* 257 */     LOGGER.trace("AsyncLoggerConfigHelper: shutting down disruptor: ref count is {}.", new Object[] { Integer.valueOf(count) });
/*     */ 
/*     */ 
/*     */     
/* 261 */     disruptor = null;
/* 262 */     temp.shutdown();
/*     */ 
/*     */     
/* 265 */     RingBuffer<Log4jEventWrapper> ringBuffer = temp.getRingBuffer();
/* 266 */     for (int i = 0; i < 20 && 
/* 267 */       !ringBuffer.hasAvailableCapacity(ringBuffer.getBufferSize()); i++) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 272 */         Thread.sleep(500L);
/* 273 */       } catch (InterruptedException e) {}
/*     */     } 
/*     */ 
/*     */     
/* 277 */     executor.shutdown();
/* 278 */     executor = null;
/*     */   }
/*     */   
/*     */   public void callAppendersFromAnotherThread(LogEvent event) {
/* 282 */     this.currentLogEvent.set(event);
/* 283 */     disruptor.publishEvent(this.translator);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLoggerConfigHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */