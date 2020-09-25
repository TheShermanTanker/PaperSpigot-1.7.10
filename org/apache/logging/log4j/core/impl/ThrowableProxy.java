/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.security.CodeSource;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public class ThrowableProxy
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2752771578252251910L;
/*  38 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private static final PrivateSecurityManager SECURITY_MANAGER;
/*     */   
/*     */   private static final Method GET_SUPPRESSED;
/*     */   
/*     */   private static final Method ADD_SUPPRESSED;
/*     */   
/*     */   private final ThrowableProxy proxyCause;
/*     */   
/*     */   private final Throwable throwable;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final StackTracePackageElement[] callerPackageData;
/*     */   
/*     */   private int commonElementCount;
/*     */   
/*     */   static {
/*  57 */     if (ReflectiveCallerClassUtility.isSupported()) {
/*  58 */       SECURITY_MANAGER = null;
/*     */     } else {
/*     */       PrivateSecurityManager privateSecurityManager;
/*     */       try {
/*  62 */         privateSecurityManager = new PrivateSecurityManager();
/*  63 */         if (privateSecurityManager.getClasses() == null) {
/*     */           
/*  65 */           privateSecurityManager = null;
/*  66 */           LOGGER.error("Unable to obtain call stack from security manager.");
/*     */         } 
/*  68 */       } catch (Exception e) {
/*  69 */         privateSecurityManager = null;
/*  70 */         LOGGER.debug("Unable to install security manager.", e);
/*     */       } 
/*  72 */       SECURITY_MANAGER = privateSecurityManager;
/*     */     } 
/*     */     
/*  75 */     Method getSuppressed = null, addSuppressed = null;
/*  76 */     Method[] methods = Throwable.class.getMethods();
/*  77 */     for (Method method : methods) {
/*  78 */       if (method.getName().equals("getSuppressed")) {
/*  79 */         getSuppressed = method;
/*  80 */       } else if (method.getName().equals("addSuppressed")) {
/*  81 */         addSuppressed = method;
/*     */       } 
/*     */     } 
/*  84 */     GET_SUPPRESSED = getSuppressed;
/*  85 */     ADD_SUPPRESSED = addSuppressed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThrowableProxy(Throwable throwable) {
/*  93 */     this.throwable = throwable;
/*  94 */     this.name = throwable.getClass().getName();
/*  95 */     Map<String, CacheEntry> map = new HashMap<String, CacheEntry>();
/*  96 */     Stack<Class<?>> stack = getCurrentStack();
/*  97 */     this.callerPackageData = resolvePackageData(stack, map, null, throwable.getStackTrace());
/*  98 */     this.proxyCause = (throwable.getCause() == null) ? null : new ThrowableProxy(throwable, stack, map, throwable.getCause());
/*     */     
/* 100 */     setSuppressed(throwable);
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
/*     */   private ThrowableProxy(Throwable parent, Stack<Class<?>> stack, Map<String, CacheEntry> map, Throwable cause) {
/* 113 */     this.throwable = cause;
/* 114 */     this.name = cause.getClass().getName();
/* 115 */     this.callerPackageData = resolvePackageData(stack, map, parent.getStackTrace(), cause.getStackTrace());
/* 116 */     this.proxyCause = (cause.getCause() == null) ? null : new ThrowableProxy(parent, stack, map, cause.getCause());
/*     */     
/* 118 */     setSuppressed(cause);
/*     */   }
/*     */   
/*     */   public Throwable getThrowable() {
/* 122 */     return this.throwable;
/*     */   }
/*     */   
/*     */   public ThrowableProxy getCause() {
/* 126 */     return this.proxyCause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 134 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCommonElementCount() {
/* 143 */     return this.commonElementCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackTracePackageElement[] getPackageData() {
/* 151 */     return this.callerPackageData;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 156 */     String msg = this.throwable.getMessage();
/* 157 */     return (msg != null) ? (this.name + ": " + msg) : this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRootCauseStackTrace() {
/* 165 */     return getRootCauseStackTrace(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRootCauseStackTrace(List<String> packages) {
/* 174 */     StringBuilder sb = new StringBuilder();
/* 175 */     if (this.proxyCause != null) {
/* 176 */       formatWrapper(sb, this.proxyCause);
/* 177 */       sb.append("Wrapped by: ");
/*     */     } 
/* 179 */     sb.append(toString());
/* 180 */     sb.append("\n");
/* 181 */     formatElements(sb, 0, this.throwable.getStackTrace(), this.callerPackageData, packages);
/* 182 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void formatWrapper(StringBuilder sb, ThrowableProxy cause) {
/* 191 */     formatWrapper(sb, cause, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void formatWrapper(StringBuilder sb, ThrowableProxy cause, List<String> packages) {
/* 202 */     Throwable caused = (cause.getCause() != null) ? cause.getCause().getThrowable() : null;
/* 203 */     if (caused != null) {
/* 204 */       formatWrapper(sb, cause.proxyCause);
/* 205 */       sb.append("Wrapped by: ");
/*     */     } 
/* 207 */     sb.append(cause).append("\n");
/* 208 */     formatElements(sb, cause.commonElementCount, cause.getThrowable().getStackTrace(), cause.callerPackageData, packages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtendedStackTrace() {
/* 217 */     return getExtendedStackTrace(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtendedStackTrace(List<String> packages) {
/* 226 */     StringBuilder sb = new StringBuilder(this.name);
/* 227 */     String msg = this.throwable.getMessage();
/* 228 */     if (msg != null) {
/* 229 */       sb.append(": ").append(this.throwable.getMessage());
/*     */     }
/* 231 */     sb.append("\n");
/* 232 */     formatElements(sb, 0, this.throwable.getStackTrace(), this.callerPackageData, packages);
/* 233 */     if (this.proxyCause != null) {
/* 234 */       formatCause(sb, this.proxyCause, packages);
/*     */     }
/* 236 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSuppressedStackTrace() {
/* 244 */     ThrowableProxy[] suppressed = getSuppressed();
/* 245 */     if (suppressed == null || suppressed.length == 0) {
/* 246 */       return "";
/*     */     }
/* 248 */     StringBuilder sb = new StringBuilder("Suppressed Stack Trace Elements:\n");
/* 249 */     for (ThrowableProxy proxy : suppressed) {
/* 250 */       sb.append(proxy.getExtendedStackTrace());
/*     */     }
/* 252 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void formatCause(StringBuilder sb, ThrowableProxy cause, List<String> packages) {
/* 257 */     sb.append("Caused by: ").append(cause).append("\n");
/* 258 */     formatElements(sb, cause.commonElementCount, cause.getThrowable().getStackTrace(), cause.callerPackageData, packages);
/*     */     
/* 260 */     if (cause.getCause() != null) {
/* 261 */       formatCause(sb, cause.proxyCause, packages);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void formatElements(StringBuilder sb, int commonCount, StackTraceElement[] causedTrace, StackTracePackageElement[] packageData, List<String> packages) {
/* 267 */     if (packages == null || packages.size() == 0) {
/* 268 */       for (int i = 0; i < packageData.length; i++) {
/* 269 */         formatEntry(causedTrace[i], packageData[i], sb);
/*     */       }
/*     */     } else {
/* 272 */       int count = 0;
/* 273 */       for (int i = 0; i < packageData.length; i++) {
/* 274 */         if (!isSuppressed(causedTrace[i], packages)) {
/* 275 */           if (count > 0) {
/* 276 */             if (count == 1) {
/* 277 */               sb.append("\t....\n");
/*     */             } else {
/* 279 */               sb.append("\t... suppressed ").append(count).append(" lines\n");
/*     */             } 
/* 281 */             count = 0;
/*     */           } 
/* 283 */           formatEntry(causedTrace[i], packageData[i], sb);
/*     */         } else {
/* 285 */           count++;
/*     */         } 
/*     */       } 
/* 288 */       if (count > 0) {
/* 289 */         if (count == 1) {
/* 290 */           sb.append("\t...\n");
/*     */         } else {
/* 292 */           sb.append("\t... suppressed ").append(count).append(" lines\n");
/*     */         } 
/*     */       }
/*     */     } 
/* 296 */     if (commonCount != 0) {
/* 297 */       sb.append("\t... ").append(commonCount).append(" more").append("\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void formatEntry(StackTraceElement element, StackTracePackageElement packageData, StringBuilder sb) {
/* 303 */     sb.append("\tat ");
/* 304 */     sb.append(element);
/* 305 */     sb.append(" ");
/* 306 */     sb.append(packageData);
/* 307 */     sb.append("\n");
/*     */   }
/*     */   
/*     */   private boolean isSuppressed(StackTraceElement element, List<String> packages) {
/* 311 */     String className = element.getClassName();
/* 312 */     for (String pkg : packages) {
/* 313 */       if (className.startsWith(pkg)) {
/* 314 */         return true;
/*     */       }
/*     */     } 
/* 317 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Stack<Class<?>> getCurrentStack() {
/* 327 */     if (ReflectiveCallerClassUtility.isSupported()) {
/* 328 */       Stack<Class<?>> classes = new Stack<Class<?>>();
/* 329 */       int index = 1;
/* 330 */       Class<?> clazz = ReflectiveCallerClassUtility.getCaller(index);
/* 331 */       while (clazz != null) {
/* 332 */         classes.push(clazz);
/* 333 */         clazz = ReflectiveCallerClassUtility.getCaller(++index);
/*     */       } 
/* 335 */       return classes;
/* 336 */     }  if (SECURITY_MANAGER != null) {
/* 337 */       Class<?>[] array = SECURITY_MANAGER.getClasses();
/* 338 */       Stack<Class<?>> classes = new Stack<Class<?>>();
/* 339 */       for (Class<?> clazz : array) {
/* 340 */         classes.push(clazz);
/*     */       }
/* 342 */       return classes;
/*     */     } 
/* 344 */     return new Stack<Class<?>>();
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
/*     */   StackTracePackageElement[] resolvePackageData(Stack<Class<?>> stack, Map<String, CacheEntry> map, StackTraceElement[] rootTrace, StackTraceElement[] stackTrace) {
/*     */     int stackLength;
/* 360 */     if (rootTrace != null) {
/* 361 */       int rootIndex = rootTrace.length - 1;
/* 362 */       int stackIndex = stackTrace.length - 1;
/* 363 */       while (rootIndex >= 0 && stackIndex >= 0 && rootTrace[rootIndex].equals(stackTrace[stackIndex])) {
/* 364 */         rootIndex--;
/* 365 */         stackIndex--;
/*     */       } 
/* 367 */       this.commonElementCount = stackTrace.length - 1 - stackIndex;
/* 368 */       stackLength = stackIndex + 1;
/*     */     } else {
/* 370 */       this.commonElementCount = 0;
/* 371 */       stackLength = stackTrace.length;
/*     */     } 
/* 373 */     StackTracePackageElement[] packageArray = new StackTracePackageElement[stackLength];
/* 374 */     Class<?> clazz = stack.isEmpty() ? null : stack.peek();
/* 375 */     ClassLoader lastLoader = null;
/* 376 */     for (int i = stackLength - 1; i >= 0; i--) {
/* 377 */       String className = stackTrace[i].getClassName();
/*     */ 
/*     */ 
/*     */       
/* 381 */       if (clazz != null && className.equals(clazz.getName())) {
/* 382 */         CacheEntry entry = resolvePackageElement(clazz, true);
/* 383 */         packageArray[i] = entry.element;
/* 384 */         lastLoader = entry.loader;
/* 385 */         stack.pop();
/* 386 */         clazz = stack.isEmpty() ? null : stack.peek();
/*     */       }
/* 388 */       else if (map.containsKey(className)) {
/* 389 */         CacheEntry entry = map.get(className);
/* 390 */         packageArray[i] = entry.element;
/* 391 */         if (entry.loader != null) {
/* 392 */           lastLoader = entry.loader;
/*     */         }
/*     */       } else {
/* 395 */         CacheEntry entry = resolvePackageElement(loadClass(lastLoader, className), false);
/* 396 */         packageArray[i] = entry.element;
/* 397 */         map.put(className, entry);
/* 398 */         if (entry.loader != null) {
/* 399 */           lastLoader = entry.loader;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 404 */     return packageArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CacheEntry resolvePackageElement(Class<?> callerClass, boolean exact) {
/* 415 */     String location = "?";
/* 416 */     String version = "?";
/* 417 */     ClassLoader lastLoader = null;
/* 418 */     if (callerClass != null) {
/*     */       try {
/* 420 */         CodeSource source = callerClass.getProtectionDomain().getCodeSource();
/* 421 */         if (source != null) {
/* 422 */           URL locationURL = source.getLocation();
/* 423 */           if (locationURL != null) {
/* 424 */             String str = locationURL.toString().replace('\\', '/');
/* 425 */             int index = str.lastIndexOf("/");
/* 426 */             if (index >= 0 && index == str.length() - 1) {
/* 427 */               index = str.lastIndexOf("/", index - 1);
/* 428 */               location = str.substring(index + 1);
/*     */             } else {
/* 430 */               location = str.substring(index + 1);
/*     */             } 
/*     */           } 
/*     */         } 
/* 434 */       } catch (Exception ex) {}
/*     */ 
/*     */       
/* 437 */       Package pkg = callerClass.getPackage();
/* 438 */       if (pkg != null) {
/* 439 */         String ver = pkg.getImplementationVersion();
/* 440 */         if (ver != null) {
/* 441 */           version = ver;
/*     */         }
/*     */       } 
/* 444 */       lastLoader = callerClass.getClassLoader();
/*     */     } 
/* 446 */     return new CacheEntry(new StackTracePackageElement(location, version, exact), lastLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> loadClass(ClassLoader lastLoader, String className) {
/*     */     Class<?> clazz;
/* 457 */     if (lastLoader != null) {
/*     */       try {
/* 459 */         clazz = lastLoader.loadClass(className);
/* 460 */         if (clazz != null) {
/* 461 */           return clazz;
/*     */         }
/* 463 */       } catch (Exception ex) {}
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 468 */       clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
/* 469 */     } catch (ClassNotFoundException e) {
/*     */       try {
/* 471 */         clazz = Class.forName(className);
/* 472 */       } catch (ClassNotFoundException e1) {
/*     */         try {
/* 474 */           clazz = getClass().getClassLoader().loadClass(className);
/* 475 */         } catch (ClassNotFoundException e2) {
/* 476 */           return null;
/*     */         } 
/*     */       } 
/*     */     } 
/* 480 */     return clazz;
/*     */   }
/*     */   
/*     */   public ThrowableProxy[] getSuppressed() {
/* 484 */     if (GET_SUPPRESSED != null) {
/*     */       try {
/* 486 */         return (ThrowableProxy[])GET_SUPPRESSED.invoke(this.throwable, new Object[0]);
/* 487 */       } catch (Exception ignore) {
/* 488 */         return null;
/*     */       } 
/*     */     }
/* 491 */     return null;
/*     */   }
/*     */   
/*     */   private void setSuppressed(Throwable throwable) {
/* 495 */     if (GET_SUPPRESSED != null && ADD_SUPPRESSED != null) {
/*     */       try {
/* 497 */         Throwable[] array = (Throwable[])GET_SUPPRESSED.invoke(throwable, new Object[0]);
/* 498 */         for (Throwable t : array) {
/* 499 */           ADD_SUPPRESSED.invoke(this, new Object[] { new ThrowableProxy(t) });
/*     */         } 
/* 501 */       } catch (Exception ignore) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class CacheEntry
/*     */   {
/*     */     private final StackTracePackageElement element;
/*     */     
/*     */     private final ClassLoader loader;
/*     */ 
/*     */     
/*     */     public CacheEntry(StackTracePackageElement element, ClassLoader loader) {
/* 515 */       this.element = element;
/* 516 */       this.loader = loader;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class PrivateSecurityManager
/*     */     extends SecurityManager {
/*     */     private PrivateSecurityManager() {}
/*     */     
/*     */     public Class<?>[] getClasses() {
/* 525 */       return getClassContext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\ThrowableProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */