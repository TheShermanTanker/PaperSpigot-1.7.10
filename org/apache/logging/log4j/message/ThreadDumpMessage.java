/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class ThreadDumpMessage
/*     */   implements Message
/*     */ {
/*     */   private static final long serialVersionUID = -1103400781608841088L;
/*     */   private static final ThreadInfoFactory FACTORY;
/*     */   private volatile Map<ThreadInformation, StackTraceElement[]> threads;
/*     */   private final String title;
/*     */   private String formattedMessage;
/*     */   
/*     */   static {
/*  45 */     Method[] methods = ThreadInfo.class.getMethods();
/*  46 */     boolean basic = true;
/*  47 */     for (Method method : methods) {
/*  48 */       if (method.getName().equals("getLockInfo")) {
/*  49 */         basic = false;
/*     */         break;
/*     */       } 
/*     */     } 
/*  53 */     FACTORY = basic ? new BasicThreadInfoFactory() : new ExtendedThreadInfoFactory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadDumpMessage(String title) {
/*  61 */     this.title = (title == null) ? "" : title;
/*  62 */     this.threads = FACTORY.createThreadInfo();
/*     */   }
/*     */   
/*     */   private ThreadDumpMessage(String formattedMsg, String title) {
/*  66 */     this.formattedMessage = formattedMsg;
/*  67 */     this.title = (title == null) ? "" : title;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  72 */     StringBuilder sb = new StringBuilder("ThreadDumpMessage[");
/*  73 */     if (this.title.length() > 0) {
/*  74 */       sb.append("Title=\"").append(this.title).append("\"");
/*     */     }
/*  76 */     sb.append("]");
/*  77 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedMessage() {
/*  86 */     if (this.formattedMessage != null) {
/*  87 */       return this.formattedMessage;
/*     */     }
/*  89 */     StringBuilder sb = new StringBuilder(this.title);
/*  90 */     if (this.title.length() > 0) {
/*  91 */       sb.append("\n");
/*     */     }
/*  93 */     for (Map.Entry<ThreadInformation, StackTraceElement[]> entry : this.threads.entrySet()) {
/*  94 */       ThreadInformation info = entry.getKey();
/*  95 */       info.printThreadInfo(sb);
/*  96 */       info.printStack(sb, entry.getValue());
/*  97 */       sb.append("\n");
/*     */     } 
/*  99 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 108 */     return (this.title == null) ? "" : this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getParameters() {
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object writeReplace() {
/* 126 */     return new ThreadDumpMessageProxy(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 131 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ThreadDumpMessageProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -3476620450287648269L;
/*     */     
/*     */     private final String formattedMsg;
/*     */     private final String title;
/*     */     
/*     */     public ThreadDumpMessageProxy(ThreadDumpMessage msg) {
/* 144 */       this.formattedMsg = msg.getFormattedMessage();
/* 145 */       this.title = msg.title;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object readResolve() {
/* 153 */       return new ThreadDumpMessage(this.formattedMsg, this.title);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static interface ThreadInfoFactory
/*     */   {
/*     */     Map<ThreadInformation, StackTraceElement[]> createThreadInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class BasicThreadInfoFactory
/*     */     implements ThreadInfoFactory
/*     */   {
/*     */     private BasicThreadInfoFactory() {}
/*     */     
/*     */     public Map<ThreadInformation, StackTraceElement[]> createThreadInfo() {
/* 170 */       Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
/* 171 */       Map<ThreadInformation, StackTraceElement[]> threads = (Map)new HashMap<ThreadInformation, StackTraceElement>(map.size());
/*     */       
/* 173 */       for (Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
/* 174 */         threads.put(new BasicThreadInformation(entry.getKey()), entry.getValue());
/*     */       }
/* 176 */       return threads;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ExtendedThreadInfoFactory
/*     */     implements ThreadInfoFactory
/*     */   {
/*     */     private ExtendedThreadInfoFactory() {}
/*     */     
/*     */     public Map<ThreadInformation, StackTraceElement[]> createThreadInfo() {
/* 186 */       ThreadMXBean bean = ManagementFactory.getThreadMXBean();
/* 187 */       ThreadInfo[] array = bean.dumpAllThreads(true, true);
/*     */       
/* 189 */       Map<ThreadInformation, StackTraceElement[]> threads = (Map)new HashMap<ThreadInformation, StackTraceElement>(array.length);
/*     */       
/* 191 */       for (ThreadInfo info : array) {
/* 192 */         threads.put(new ExtendedThreadInformation(info), info.getStackTrace());
/*     */       }
/* 194 */       return threads;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getThrowable() {
/* 205 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\ThreadDumpMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */