/*     */ package net.minecraft.util.io.netty.util.internal.logging;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInternalLogger
/*     */   implements InternalLogger, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6382972526573193470L;
/*     */   private final String name;
/*     */   
/*     */   protected AbstractInternalLogger(String name) {
/*  36 */     if (name == null) {
/*  37 */       throw new NullPointerException("name");
/*     */     }
/*  39 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  44 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(InternalLogLevel level) {
/*  49 */     switch (level) {
/*     */       case TRACE:
/*  51 */         return isTraceEnabled();
/*     */       case DEBUG:
/*  53 */         return isDebugEnabled();
/*     */       case INFO:
/*  55 */         return isInfoEnabled();
/*     */       case WARN:
/*  57 */         return isWarnEnabled();
/*     */       case ERROR:
/*  59 */         return isErrorEnabled();
/*     */     } 
/*  61 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String msg, Throwable cause) {
/*  67 */     switch (level) {
/*     */       case TRACE:
/*  69 */         trace(msg, cause);
/*     */         return;
/*     */       case DEBUG:
/*  72 */         debug(msg, cause);
/*     */         return;
/*     */       case INFO:
/*  75 */         info(msg, cause);
/*     */         return;
/*     */       case WARN:
/*  78 */         warn(msg, cause);
/*     */         return;
/*     */       case ERROR:
/*  81 */         error(msg, cause);
/*     */         return;
/*     */     } 
/*  84 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String msg) {
/*  90 */     switch (level) {
/*     */       case TRACE:
/*  92 */         trace(msg);
/*     */         return;
/*     */       case DEBUG:
/*  95 */         debug(msg);
/*     */         return;
/*     */       case INFO:
/*  98 */         info(msg);
/*     */         return;
/*     */       case WARN:
/* 101 */         warn(msg);
/*     */         return;
/*     */       case ERROR:
/* 104 */         error(msg);
/*     */         return;
/*     */     } 
/* 107 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object arg) {
/* 113 */     switch (level) {
/*     */       case TRACE:
/* 115 */         trace(format, arg);
/*     */         return;
/*     */       case DEBUG:
/* 118 */         debug(format, arg);
/*     */         return;
/*     */       case INFO:
/* 121 */         info(format, arg);
/*     */         return;
/*     */       case WARN:
/* 124 */         warn(format, arg);
/*     */         return;
/*     */       case ERROR:
/* 127 */         error(format, arg);
/*     */         return;
/*     */     } 
/* 130 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object argA, Object argB) {
/* 136 */     switch (level) {
/*     */       case TRACE:
/* 138 */         trace(format, argA, argB);
/*     */         return;
/*     */       case DEBUG:
/* 141 */         debug(format, argA, argB);
/*     */         return;
/*     */       case INFO:
/* 144 */         info(format, argA, argB);
/*     */         return;
/*     */       case WARN:
/* 147 */         warn(format, argA, argB);
/*     */         return;
/*     */       case ERROR:
/* 150 */         error(format, argA, argB);
/*     */         return;
/*     */     } 
/* 153 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object... arguments) {
/* 159 */     switch (level) {
/*     */       case TRACE:
/* 161 */         trace(format, arguments);
/*     */         return;
/*     */       case DEBUG:
/* 164 */         debug(format, arguments);
/*     */         return;
/*     */       case INFO:
/* 167 */         info(format, arguments);
/*     */         return;
/*     */       case WARN:
/* 170 */         warn(format, arguments);
/*     */         return;
/*     */       case ERROR:
/* 173 */         error(format, arguments);
/*     */         return;
/*     */     } 
/* 176 */     throw new Error();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object readResolve() throws ObjectStreamException {
/* 181 */     return InternalLoggerFactory.getInstance(name());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 186 */     return getClass().getSimpleName() + '(' + name() + ')';
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\logging\AbstractInternalLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */