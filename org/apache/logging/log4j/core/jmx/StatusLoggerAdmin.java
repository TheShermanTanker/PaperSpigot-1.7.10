/*     */ package org.apache.logging.log4j.core.jmx;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationBroadcasterSupport;
/*     */ import javax.management.ObjectName;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.status.StatusData;
/*     */ import org.apache.logging.log4j.status.StatusListener;
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
/*     */ public class StatusLoggerAdmin
/*     */   extends NotificationBroadcasterSupport
/*     */   implements StatusListener, StatusLoggerAdminMBean
/*     */ {
/*  38 */   private final AtomicLong sequenceNo = new AtomicLong();
/*     */   private final ObjectName objectName;
/*  40 */   private Level level = Level.WARN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatusLoggerAdmin(Executor executor) {
/*  49 */     super(executor, new MBeanNotificationInfo[] { createNotificationInfo() });
/*     */     try {
/*  51 */       this.objectName = new ObjectName("org.apache.logging.log4j2:type=StatusLogger");
/*  52 */     } catch (Exception e) {
/*  53 */       throw new IllegalStateException(e);
/*     */     } 
/*  55 */     StatusLogger.getLogger().registerListener(this);
/*     */   }
/*     */   
/*     */   private static MBeanNotificationInfo createNotificationInfo() {
/*  59 */     String[] notifTypes = { "com.apache.logging.log4j.core.jmx.statuslogger.data", "com.apache.logging.log4j.core.jmx.statuslogger.message" };
/*     */     
/*  61 */     String name = Notification.class.getName();
/*  62 */     String description = "StatusLogger has logged an event";
/*  63 */     return new MBeanNotificationInfo(notifTypes, name, "StatusLogger has logged an event");
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getStatusDataHistory() {
/*  68 */     List<StatusData> data = getStatusData();
/*  69 */     String[] result = new String[data.size()];
/*  70 */     for (int i = 0; i < result.length; i++) {
/*  71 */       result[i] = ((StatusData)data.get(i)).getFormattedStatus();
/*     */     }
/*  73 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<StatusData> getStatusData() {
/*  78 */     return StatusLogger.getLogger().getStatusData();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLevel() {
/*  83 */     return this.level.name();
/*     */   }
/*     */ 
/*     */   
/*     */   public Level getStatusLevel() {
/*  88 */     return this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(String level) {
/*  93 */     this.level = Level.toLevel(level, Level.ERROR);
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
/*     */   public void log(StatusData data) {
/* 105 */     Notification notifMsg = new Notification("com.apache.logging.log4j.core.jmx.statuslogger.message", getObjectName(), nextSeqNo(), now(), data.getFormattedStatus());
/*     */     
/* 107 */     sendNotification(notifMsg);
/*     */     
/* 109 */     Notification notifData = new Notification("com.apache.logging.log4j.core.jmx.statuslogger.data", getObjectName(), nextSeqNo(), now());
/*     */     
/* 111 */     notifData.setUserData(data);
/* 112 */     sendNotification(notifData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName() {
/* 122 */     return this.objectName;
/*     */   }
/*     */   
/*     */   private long nextSeqNo() {
/* 126 */     return this.sequenceNo.getAndIncrement();
/*     */   }
/*     */   
/*     */   private long now() {
/* 130 */     return System.currentTimeMillis();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\StatusLoggerAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */