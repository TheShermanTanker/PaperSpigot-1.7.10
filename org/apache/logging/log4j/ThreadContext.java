/*     */ package org.apache.logging.log4j;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.message.ParameterizedMessage;
/*     */ import org.apache.logging.log4j.spi.DefaultThreadContextMap;
/*     */ import org.apache.logging.log4j.spi.DefaultThreadContextStack;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
/*     */ import org.apache.logging.log4j.spi.MutableThreadContextStack;
/*     */ import org.apache.logging.log4j.spi.Provider;
/*     */ import org.apache.logging.log4j.spi.ThreadContextMap;
/*     */ import org.apache.logging.log4j.spi.ThreadContextStack;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
/*     */ import org.apache.logging.log4j.util.ProviderUtil;
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
/*     */ public final class ThreadContext
/*     */ {
/*  52 */   public static final Map<String, String> EMPTY_MAP = Collections.emptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public static final ThreadContextStack EMPTY_STACK = (ThreadContextStack)new MutableThreadContextStack(new ArrayList());
/*     */   
/*     */   private static final String DISABLE_MAP = "disableThreadContextMap";
/*     */   
/*     */   private static final String DISABLE_STACK = "disableThreadContextStack";
/*     */   private static final String DISABLE_ALL = "disableThreadContext";
/*     */   private static final String THREAD_CONTEXT_KEY = "log4j2.threadContextMap";
/*     */   private static boolean all;
/*     */   private static boolean useMap;
/*     */   private static boolean useStack;
/*     */   private static ThreadContextMap contextMap;
/*     */   private static ThreadContextStack contextStack;
/*  69 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   static {
/*  72 */     PropertiesUtil managerProps = PropertiesUtil.getProperties();
/*  73 */     all = managerProps.getBooleanProperty("disableThreadContext");
/*  74 */     useStack = (!managerProps.getBooleanProperty("disableThreadContextStack") && !all);
/*  75 */     contextStack = (ThreadContextStack)new DefaultThreadContextStack(useStack);
/*     */     
/*  77 */     useMap = (!managerProps.getBooleanProperty("disableThreadContextMap") && !all);
/*  78 */     String threadContextMapName = managerProps.getStringProperty("log4j2.threadContextMap");
/*  79 */     ClassLoader cl = ProviderUtil.findClassLoader();
/*  80 */     if (threadContextMapName != null) {
/*     */       try {
/*  82 */         Class<?> clazz = cl.loadClass(threadContextMapName);
/*  83 */         if (ThreadContextMap.class.isAssignableFrom(clazz)) {
/*  84 */           contextMap = (ThreadContextMap)clazz.newInstance();
/*     */         }
/*  86 */       } catch (ClassNotFoundException cnfe) {
/*  87 */         LOGGER.error("Unable to locate configured LoggerContextFactory {}", new Object[] { threadContextMapName });
/*  88 */       } catch (Exception ex) {
/*  89 */         LOGGER.error("Unable to create configured LoggerContextFactory {}", new Object[] { threadContextMapName, ex });
/*     */       } 
/*     */     }
/*  92 */     if (contextMap == null && ProviderUtil.hasProviders()) {
/*  93 */       LoggerContextFactory factory = LogManager.getFactory();
/*  94 */       Iterator<Provider> providers = ProviderUtil.getProviders();
/*  95 */       while (providers.hasNext()) {
/*  96 */         Provider provider = providers.next();
/*  97 */         threadContextMapName = provider.getThreadContextMap();
/*  98 */         String factoryClassName = provider.getClassName();
/*  99 */         if (threadContextMapName != null && factory.getClass().getName().equals(factoryClassName)) {
/*     */           try {
/* 101 */             Class<?> clazz = cl.loadClass(threadContextMapName);
/* 102 */             if (ThreadContextMap.class.isAssignableFrom(clazz)) {
/* 103 */               contextMap = (ThreadContextMap)clazz.newInstance();
/*     */               break;
/*     */             } 
/* 106 */           } catch (ClassNotFoundException cnfe) {
/* 107 */             LOGGER.error("Unable to locate configured LoggerContextFactory {}", new Object[] { threadContextMapName });
/* 108 */             contextMap = (ThreadContextMap)new DefaultThreadContextMap(useMap);
/* 109 */           } catch (Exception ex) {
/* 110 */             LOGGER.error("Unable to create configured LoggerContextFactory {}", new Object[] { threadContextMapName, ex });
/* 111 */             contextMap = (ThreadContextMap)new DefaultThreadContextMap(useMap);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 116 */     if (contextMap == null) {
/* 117 */       contextMap = (ThreadContextMap)new DefaultThreadContextMap(useMap);
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
/*     */   public static void put(String key, String value) {
/* 136 */     contextMap.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(String key) {
/* 147 */     return contextMap.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void remove(String key) {
/* 155 */     contextMap.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clear() {
/* 162 */     contextMap.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean containsKey(String key) {
/* 171 */     return contextMap.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> getContext() {
/* 179 */     return contextMap.getCopy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, String> getImmutableContext() {
/* 187 */     Map<String, String> map = contextMap.getImmutableMapOrNull();
/* 188 */     return (map == null) ? EMPTY_MAP : map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEmpty() {
/* 196 */     return contextMap.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearStack() {
/* 203 */     contextStack.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ContextStack cloneStack() {
/* 211 */     return contextStack.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ContextStack getImmutableStack() {
/* 219 */     return (ContextStack)contextStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setStack(Collection<String> stack) {
/* 227 */     if (stack.size() == 0 || !useStack) {
/*     */       return;
/*     */     }
/* 230 */     contextStack.clear();
/* 231 */     contextStack.addAll(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDepth() {
/* 241 */     return contextStack.getDepth();
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
/*     */   public static String pop() {
/* 253 */     return contextStack.pop();
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
/*     */   public static String peek() {
/* 266 */     return contextStack.peek();
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
/*     */   public static void push(String message) {
/* 278 */     contextStack.push(message);
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
/*     */   public static void push(String message, Object... args) {
/* 292 */     contextStack.push(ParameterizedMessage.format(message, args));
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
/*     */   public static void removeStack() {
/* 314 */     contextStack.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void trim(int depth) {
/* 347 */     contextStack.trim(depth);
/*     */   }
/*     */   
/*     */   public static interface ContextStack extends Serializable {
/*     */     void clear();
/*     */     
/*     */     String pop();
/*     */     
/*     */     String peek();
/*     */     
/*     */     void push(String param1String);
/*     */     
/*     */     int getDepth();
/*     */     
/*     */     List<String> asList();
/*     */     
/*     */     void trim(int param1Int);
/*     */     
/*     */     ContextStack copy();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\ThreadContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */