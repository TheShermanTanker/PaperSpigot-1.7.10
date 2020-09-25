/*    */ package net.minecraft.util.com.mojang.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.BlockingQueue;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ import java.util.concurrent.locks.ReadWriteLock;
/*    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*    */ import org.apache.logging.log4j.core.Filter;
/*    */ import org.apache.logging.log4j.core.Layout;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*    */ 
/*    */ @Plugin(name = "Queue", category = "Core", elementType = "appender", printObject = true)
/*    */ public class QueueLogAppender
/*    */   extends AbstractAppender {
/*    */   private static final int MAX_CAPACITY = 250;
/* 24 */   private static final Map<String, BlockingQueue<String>> QUEUES = new HashMap<String, BlockingQueue<String>>();
/* 25 */   private static final ReadWriteLock QUEUE_LOCK = new ReentrantReadWriteLock();
/*    */   
/*    */   private final BlockingQueue<String> queue;
/*    */   
/*    */   public QueueLogAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, BlockingQueue<String> queue) {
/* 30 */     super(name, filter, layout, ignoreExceptions);
/* 31 */     this.queue = queue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void append(LogEvent event) {
/* 36 */     if (this.queue.size() >= 250) {
/* 37 */       this.queue.clear();
/*    */     }
/* 39 */     this.queue.add(getLayout().toSerializable(event).toString());
/*    */   }
/*    */   @PluginFactory
/*    */   public static QueueLogAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("ignoreExceptions") String ignore, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filters") Filter filter) {
/*    */     PatternLayout patternLayout;
/* 44 */     boolean ignoreExceptions = Boolean.parseBoolean(ignore);
/*    */     
/* 46 */     if (name == null) {
/* 47 */       LOGGER.error("No name provided for QueueLogAppender");
/* 48 */       return null;
/*    */     } 
/*    */     
/* 51 */     QUEUE_LOCK.writeLock().lock();
/* 52 */     BlockingQueue<String> queue = QUEUES.get(name);
/* 53 */     if (queue == null) {
/* 54 */       queue = new LinkedBlockingQueue<String>();
/* 55 */       QUEUES.put(name, queue);
/*    */     } 
/* 57 */     QUEUE_LOCK.writeLock().unlock();
/*    */     
/* 59 */     if (layout == null) {
/* 60 */       patternLayout = PatternLayout.createLayout(null, null, null, null, null);
/*    */     }
/*    */     
/* 63 */     return new QueueLogAppender(name, filter, (Layout<? extends Serializable>)patternLayout, ignoreExceptions, queue);
/*    */   }
/*    */   
/*    */   public static String getNextLogEvent(String queueName) {
/* 67 */     QUEUE_LOCK.readLock().lock();
/* 68 */     BlockingQueue<String> queue = QUEUES.get(queueName);
/* 69 */     QUEUE_LOCK.readLock().unlock();
/*    */     
/* 71 */     if (queue != null) {
/*    */       try {
/* 73 */         return queue.take();
/* 74 */       } catch (InterruptedException ignored) {}
/*    */     }
/*    */     
/* 77 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojan\\util\QueueLogAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */