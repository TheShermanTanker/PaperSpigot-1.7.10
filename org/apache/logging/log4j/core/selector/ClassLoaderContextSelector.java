/*     */ package org.apache.logging.log4j.core.selector;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
/*     */ import org.apache.logging.log4j.core.impl.ReflectiveCallerClassUtility;
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
/*     */ public class ClassLoaderContextSelector
/*     */   implements ContextSelector
/*     */ {
/*  50 */   private static final AtomicReference<LoggerContext> CONTEXT = new AtomicReference<LoggerContext>();
/*     */   
/*     */   private static final PrivateSecurityManager SECURITY_MANAGER;
/*     */   
/*  54 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */   
/*  56 */   private static final ConcurrentMap<String, AtomicReference<WeakReference<LoggerContext>>> CONTEXT_MAP = new ConcurrentHashMap<String, AtomicReference<WeakReference<LoggerContext>>>();
/*     */ 
/*     */   
/*     */   static {
/*  60 */     if (ReflectiveCallerClassUtility.isSupported()) {
/*  61 */       SECURITY_MANAGER = null;
/*     */     } else {
/*     */       PrivateSecurityManager privateSecurityManager;
/*     */       try {
/*  65 */         privateSecurityManager = new PrivateSecurityManager();
/*  66 */         if (privateSecurityManager.getCaller(ClassLoaderContextSelector.class.getName()) == null) {
/*     */           
/*  68 */           privateSecurityManager = null;
/*  69 */           LOGGER.error("Unable to obtain call stack from security manager.");
/*     */         } 
/*  71 */       } catch (Exception e) {
/*  72 */         privateSecurityManager = null;
/*  73 */         LOGGER.debug("Unable to install security manager", e);
/*     */       } 
/*  75 */       SECURITY_MANAGER = privateSecurityManager;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
/*  81 */     return getContext(fqcn, loader, currentContext, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
/*  87 */     if (currentContext) {
/*  88 */       LoggerContext ctx = ContextAnchor.THREAD_CONTEXT.get();
/*  89 */       if (ctx != null) {
/*  90 */         return ctx;
/*     */       }
/*  92 */       return getDefault();
/*  93 */     }  if (loader != null) {
/*  94 */       return locateContext(loader, configLocation);
/*     */     }
/*  96 */     if (ReflectiveCallerClassUtility.isSupported()) {
/*     */       try {
/*  98 */         Class<?> clazz = Class.class;
/*  99 */         boolean bool = false;
/* 100 */         for (int index = 2; clazz != null; index++) {
/* 101 */           clazz = ReflectiveCallerClassUtility.getCaller(index);
/* 102 */           if (clazz == null) {
/*     */             break;
/*     */           }
/* 105 */           if (clazz.getName().equals(fqcn)) {
/* 106 */             bool = true;
/*     */           
/*     */           }
/* 109 */           else if (bool) {
/*     */             break;
/*     */           } 
/*     */         } 
/* 113 */         if (clazz != null) {
/* 114 */           return locateContext(clazz.getClassLoader(), configLocation);
/*     */         }
/* 116 */       } catch (Exception ex) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (SECURITY_MANAGER != null) {
/* 122 */       Class<?> clazz = SECURITY_MANAGER.getCaller(fqcn);
/* 123 */       if (clazz != null) {
/* 124 */         ClassLoader ldr = (clazz.getClassLoader() != null) ? clazz.getClassLoader() : ClassLoader.getSystemClassLoader();
/*     */         
/* 126 */         return locateContext(ldr, configLocation);
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     Throwable t = new Throwable();
/* 131 */     boolean next = false;
/* 132 */     String name = null;
/* 133 */     for (StackTraceElement element : t.getStackTrace()) {
/* 134 */       if (element.getClassName().equals(fqcn)) {
/* 135 */         next = true;
/*     */       
/*     */       }
/* 138 */       else if (next) {
/* 139 */         name = element.getClassName();
/*     */         break;
/*     */       } 
/*     */     } 
/* 143 */     if (name != null) {
/*     */       try {
/* 145 */         return locateContext(Loader.loadClass(name).getClassLoader(), configLocation);
/* 146 */       } catch (ClassNotFoundException ignore) {}
/*     */     }
/*     */ 
/*     */     
/* 150 */     LoggerContext lc = ContextAnchor.THREAD_CONTEXT.get();
/* 151 */     if (lc != null) {
/* 152 */       return lc;
/*     */     }
/* 154 */     return getDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeContext(LoggerContext context) {
/* 160 */     for (Map.Entry<String, AtomicReference<WeakReference<LoggerContext>>> entry : CONTEXT_MAP.entrySet()) {
/* 161 */       LoggerContext ctx = ((WeakReference<LoggerContext>)((AtomicReference<WeakReference<LoggerContext>>)entry.getValue()).get()).get();
/* 162 */       if (ctx == context) {
/* 163 */         CONTEXT_MAP.remove(entry.getKey());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<LoggerContext> getLoggerContexts() {
/* 170 */     List<LoggerContext> list = new ArrayList<LoggerContext>();
/* 171 */     Collection<AtomicReference<WeakReference<LoggerContext>>> coll = CONTEXT_MAP.values();
/* 172 */     for (AtomicReference<WeakReference<LoggerContext>> ref : coll) {
/* 173 */       LoggerContext ctx = ((WeakReference<LoggerContext>)ref.get()).get();
/* 174 */       if (ctx != null) {
/* 175 */         list.add(ctx);
/*     */       }
/*     */     } 
/* 178 */     return Collections.unmodifiableList(list);
/*     */   }
/*     */   
/*     */   private LoggerContext locateContext(ClassLoader loader, URI configLocation) {
/* 182 */     String name = loader.toString();
/* 183 */     AtomicReference<WeakReference<LoggerContext>> ref = CONTEXT_MAP.get(name);
/* 184 */     if (ref == null) {
/* 185 */       if (configLocation == null) {
/* 186 */         ClassLoader parent = loader.getParent();
/* 187 */         while (parent != null) {
/*     */           
/* 189 */           ref = CONTEXT_MAP.get(parent.toString());
/* 190 */           if (ref != null) {
/* 191 */             WeakReference<LoggerContext> weakReference = ref.get();
/* 192 */             LoggerContext loggerContext1 = weakReference.get();
/* 193 */             if (loggerContext1 != null) {
/* 194 */               return loggerContext1;
/*     */             }
/*     */           } 
/* 197 */           parent = parent.getParent();
/*     */         } 
/*     */       } 
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
/* 217 */       LoggerContext loggerContext = new LoggerContext(name, null, configLocation);
/* 218 */       AtomicReference<WeakReference<LoggerContext>> atomicReference = new AtomicReference<WeakReference<LoggerContext>>();
/*     */       
/* 220 */       atomicReference.set(new WeakReference<LoggerContext>(loggerContext));
/* 221 */       CONTEXT_MAP.putIfAbsent(loader.toString(), atomicReference);
/* 222 */       loggerContext = ((WeakReference<LoggerContext>)((AtomicReference<WeakReference<LoggerContext>>)CONTEXT_MAP.get(name)).get()).get();
/* 223 */       return loggerContext;
/*     */     } 
/* 225 */     WeakReference<LoggerContext> r = ref.get();
/* 226 */     LoggerContext ctx = r.get();
/* 227 */     if (ctx != null) {
/* 228 */       if (ctx.getConfigLocation() == null && configLocation != null) {
/* 229 */         LOGGER.debug("Setting configuration to {}", new Object[] { configLocation });
/* 230 */         ctx.setConfigLocation(configLocation);
/* 231 */       } else if (ctx.getConfigLocation() != null && configLocation != null && !ctx.getConfigLocation().equals(configLocation)) {
/*     */         
/* 233 */         LOGGER.warn("locateContext called with URI {}. Existing LoggerContext has URI {}", new Object[] { configLocation, ctx.getConfigLocation() });
/*     */       } 
/*     */       
/* 236 */       return ctx;
/*     */     } 
/* 238 */     ctx = new LoggerContext(name, null, configLocation);
/* 239 */     ref.compareAndSet(r, new WeakReference<LoggerContext>(ctx));
/* 240 */     return ctx;
/*     */   }
/*     */   
/*     */   private LoggerContext getDefault() {
/* 244 */     LoggerContext ctx = CONTEXT.get();
/* 245 */     if (ctx != null) {
/* 246 */       return ctx;
/*     */     }
/* 248 */     CONTEXT.compareAndSet(null, new LoggerContext("Default"));
/* 249 */     return CONTEXT.get();
/*     */   }
/*     */   
/*     */   private static class PrivateSecurityManager
/*     */     extends SecurityManager
/*     */   {
/*     */     private PrivateSecurityManager() {}
/*     */     
/*     */     public Class<?> getCaller(String fqcn) {
/* 258 */       Class<?>[] classes = getClassContext();
/* 259 */       boolean next = false;
/* 260 */       for (Class<?> clazz : classes) {
/* 261 */         if (clazz.getName().equals(fqcn)) {
/* 262 */           next = true;
/*     */         
/*     */         }
/* 265 */         else if (next) {
/* 266 */           return clazz;
/*     */         } 
/*     */       } 
/* 269 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\selector\ClassLoaderContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */