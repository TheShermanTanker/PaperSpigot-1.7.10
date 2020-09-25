/*     */ package org.apache.logging.log4j.core.lookup;
/*     */ 
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "web", category = "Lookup")
/*     */ public class WebLookup
/*     */   implements StrLookup
/*     */ {
/*     */   private static final String ATTR_PREFIX = "attr.";
/*     */   private static final String INIT_PARAM_PREFIX = "initParam.";
/*     */   
/*     */   protected ServletContext getServletContext() {
/*  36 */     LoggerContext lc = ContextAnchor.THREAD_CONTEXT.get();
/*  37 */     if (lc == null) {
/*  38 */       lc = (LoggerContext)LogManager.getContext(false);
/*     */     }
/*  40 */     if (lc != null) {
/*  41 */       Object obj = lc.getExternalContext();
/*  42 */       return (obj != null && obj instanceof ServletContext) ? (ServletContext)obj : null;
/*     */     } 
/*  44 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String lookup(String key) {
/*  49 */     ServletContext ctx = getServletContext();
/*  50 */     if (ctx == null) {
/*  51 */       return null;
/*     */     }
/*     */     
/*  54 */     if (key.startsWith("attr.")) {
/*  55 */       String attrName = key.substring("attr.".length());
/*  56 */       Object attrValue = ctx.getAttribute(attrName);
/*  57 */       return (attrValue == null) ? null : attrValue.toString();
/*     */     } 
/*     */     
/*  60 */     if (key.startsWith("initParam.")) {
/*  61 */       String paramName = key.substring("initParam.".length());
/*  62 */       return ctx.getInitParameter(paramName);
/*     */     } 
/*     */     
/*  65 */     if ("rootDir".equals(key)) {
/*  66 */       String root = ctx.getRealPath("/");
/*  67 */       if (root == null) {
/*  68 */         String msg = "failed to resolve web:rootDir -- servlet container unable to translate virtual path  to real path (probably not deployed as exploded";
/*     */ 
/*     */         
/*  71 */         throw new RuntimeException(msg);
/*     */       } 
/*     */       
/*  74 */       return root;
/*     */     } 
/*     */     
/*  77 */     if ("contextPath".equals(key)) {
/*  78 */       return ctx.getContextPath();
/*     */     }
/*     */     
/*  81 */     if ("servletContextName".equals(key)) {
/*  82 */       return ctx.getServletContextName();
/*     */     }
/*     */     
/*  85 */     if ("serverInfo".equals(key)) {
/*  86 */       return ctx.getServerInfo();
/*     */     }
/*     */     
/*  89 */     if ("effectiveMajorVersion".equals(key)) {
/*  90 */       return String.valueOf(ctx.getEffectiveMajorVersion());
/*     */     }
/*     */     
/*  93 */     if ("effectiveMinorVersion".equals(key)) {
/*  94 */       return String.valueOf(ctx.getEffectiveMinorVersion());
/*     */     }
/*     */     
/*  97 */     if ("majorVersion".equals(key)) {
/*  98 */       return String.valueOf(ctx.getMajorVersion());
/*     */     }
/*     */     
/* 101 */     if ("minorVersion".equals(key)) {
/* 102 */       return String.valueOf(ctx.getMinorVersion());
/*     */     }
/*     */     
/* 105 */     if (ctx.getAttribute(key) != null) {
/* 106 */       return ctx.getAttribute(key).toString();
/*     */     }
/*     */     
/* 109 */     if (ctx.getInitParameter(key) != null) {
/* 110 */       return ctx.getInitParameter(key);
/*     */     }
/*     */     
/* 113 */     ctx.log(getClass().getName() + " unable to resolve key '" + key + "'");
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String lookup(LogEvent event, String key) {
/* 119 */     return lookup(key);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\WebLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */