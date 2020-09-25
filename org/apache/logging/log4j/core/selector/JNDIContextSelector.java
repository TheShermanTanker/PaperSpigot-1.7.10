/*     */ package org.apache.logging.log4j.core.selector;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingException;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
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
/*     */ public class JNDIContextSelector
/*     */   implements NamedContextSelector
/*     */ {
/*  90 */   private static final LoggerContext CONTEXT = new LoggerContext("Default");
/*     */   
/*  92 */   private static final ConcurrentMap<String, LoggerContext> CONTEXT_MAP = new ConcurrentHashMap<String, LoggerContext>();
/*     */ 
/*     */   
/*  95 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */ 
/*     */   
/*     */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
/*  99 */     return getContext(fqcn, loader, currentContext, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
/* 106 */     LoggerContext lc = ContextAnchor.THREAD_CONTEXT.get();
/* 107 */     if (lc != null) {
/* 108 */       return lc;
/*     */     }
/*     */     
/* 111 */     String loggingContextName = null;
/*     */     
/*     */     try {
/* 114 */       Context ctx = new InitialContext();
/* 115 */       loggingContextName = (String)lookup(ctx, "java:comp/env/log4j/context-name");
/* 116 */     } catch (NamingException ne) {
/* 117 */       LOGGER.error("Unable to lookup java:comp/env/log4j/context-name", ne);
/*     */     } 
/*     */     
/* 120 */     return (loggingContextName == null) ? CONTEXT : locateContext(loggingContextName, null, configLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public LoggerContext locateContext(String name, Object externalContext, URI configLocation) {
/* 125 */     if (name == null) {
/* 126 */       LOGGER.error("A context name is required to locate a LoggerContext");
/* 127 */       return null;
/*     */     } 
/* 129 */     if (!CONTEXT_MAP.containsKey(name)) {
/* 130 */       LoggerContext ctx = new LoggerContext(name, externalContext, configLocation);
/* 131 */       CONTEXT_MAP.putIfAbsent(name, ctx);
/*     */     } 
/* 133 */     return CONTEXT_MAP.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeContext(LoggerContext context) {
/* 139 */     for (Map.Entry<String, LoggerContext> entry : CONTEXT_MAP.entrySet()) {
/* 140 */       if (((LoggerContext)entry.getValue()).equals(context)) {
/* 141 */         CONTEXT_MAP.remove(entry.getKey());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LoggerContext removeContext(String name) {
/* 148 */     return CONTEXT_MAP.remove(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<LoggerContext> getLoggerContexts() {
/* 153 */     List<LoggerContext> list = new ArrayList<LoggerContext>(CONTEXT_MAP.values());
/* 154 */     return Collections.unmodifiableList(list);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Object lookup(Context ctx, String name) throws NamingException {
/* 159 */     if (ctx == null) {
/* 160 */       return null;
/*     */     }
/*     */     try {
/* 163 */       return ctx.lookup(name);
/* 164 */     } catch (NameNotFoundException e) {
/* 165 */       LOGGER.error("Could not find name [" + name + "].");
/* 166 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\selector\JNDIContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */