/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class Throwables
/*     */ {
/*     */   public static <X extends Throwable> void propagateIfInstanceOf(@Nullable Throwable throwable, Class<X> declaredType) throws X {
/*  59 */     if (throwable != null && declaredType.isInstance(throwable)) {
/*  60 */       throw (X)declaredType.cast(throwable);
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
/*     */   public static void propagateIfPossible(@Nullable Throwable throwable) {
/*  79 */     propagateIfInstanceOf(throwable, Error.class);
/*  80 */     propagateIfInstanceOf(throwable, RuntimeException.class);
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
/*     */   public static <X extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X> declaredType) throws X {
/* 104 */     propagateIfInstanceOf(throwable, declaredType);
/* 105 */     propagateIfPossible(throwable);
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
/*     */   public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X1> declaredType1, Class<X2> declaredType2) throws X1, X2 {
/* 125 */     Preconditions.checkNotNull(declaredType2);
/* 126 */     propagateIfInstanceOf(throwable, declaredType1);
/* 127 */     propagateIfPossible(throwable, declaredType2);
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
/*     */   public static RuntimeException propagate(Throwable throwable) {
/* 155 */     propagateIfPossible(Preconditions.<Throwable>checkNotNull(throwable));
/* 156 */     throw new RuntimeException(throwable);
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
/*     */   public static Throwable getRootCause(Throwable throwable) {
/*     */     Throwable cause;
/* 170 */     while ((cause = throwable.getCause()) != null) {
/* 171 */       throwable = cause;
/*     */     }
/* 173 */     return throwable;
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
/*     */   @Beta
/*     */   public static List<Throwable> getCausalChain(Throwable throwable) {
/* 195 */     Preconditions.checkNotNull(throwable);
/* 196 */     List<Throwable> causes = new ArrayList<Throwable>(4);
/* 197 */     while (throwable != null) {
/* 198 */       causes.add(throwable);
/* 199 */       throwable = throwable.getCause();
/*     */     } 
/* 201 */     return Collections.unmodifiableList(causes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getStackTraceAsString(Throwable throwable) {
/* 212 */     StringWriter stringWriter = new StringWriter();
/* 213 */     throwable.printStackTrace(new PrintWriter(stringWriter));
/* 214 */     return stringWriter.toString();
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
/*     */   @Deprecated
/*     */   @Beta
/*     */   public static Exception throwCause(Exception exception, boolean combineStackTraces) throws Exception {
/* 248 */     Throwable cause = exception.getCause();
/* 249 */     if (cause == null) {
/* 250 */       throw exception;
/*     */     }
/* 252 */     if (combineStackTraces) {
/* 253 */       StackTraceElement[] causeTrace = cause.getStackTrace();
/* 254 */       StackTraceElement[] outerTrace = exception.getStackTrace();
/* 255 */       StackTraceElement[] combined = new StackTraceElement[causeTrace.length + outerTrace.length];
/*     */       
/* 257 */       System.arraycopy(causeTrace, 0, combined, 0, causeTrace.length);
/* 258 */       System.arraycopy(outerTrace, 0, combined, causeTrace.length, outerTrace.length);
/*     */       
/* 260 */       cause.setStackTrace(combined);
/*     */     } 
/* 262 */     if (cause instanceof Exception) {
/* 263 */       throw (Exception)cause;
/*     */     }
/* 265 */     if (cause instanceof Error) {
/* 266 */       throw (Error)cause;
/*     */     }
/*     */     
/* 269 */     throw exception;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\Throwables.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */