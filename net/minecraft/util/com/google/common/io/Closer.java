/*     */ package net.minecraft.util.com.google.common.io;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Throwables;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public final class Closer
/*     */   implements Closeable
/*     */ {
/*  96 */   private static final Suppressor SUPPRESSOR = SuppressingSuppressor.isAvailable() ? SuppressingSuppressor.INSTANCE : LoggingSuppressor.INSTANCE;
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   final Suppressor suppressor;
/*     */ 
/*     */   
/*     */   public static Closer create() {
/* 104 */     return new Closer(SUPPRESSOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private final Deque<Closeable> stack = new ArrayDeque<Closeable>(4);
/*     */   
/*     */   @VisibleForTesting
/*     */   Closer(Suppressor suppressor) {
/* 114 */     this.suppressor = (Suppressor)Preconditions.checkNotNull(suppressor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Throwable thrown;
/*     */ 
/*     */ 
/*     */   
/*     */   public <C extends Closeable> C register(@Nullable C closeable) {
/* 125 */     if (closeable != null) {
/* 126 */       this.stack.push((Closeable)closeable);
/*     */     }
/*     */     
/* 129 */     return closeable;
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
/*     */   public RuntimeException rethrow(Throwable e) throws IOException {
/* 146 */     Preconditions.checkNotNull(e);
/* 147 */     this.thrown = e;
/* 148 */     Throwables.propagateIfPossible(e, IOException.class);
/* 149 */     throw new RuntimeException(e);
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
/*     */   public <X extends Exception> RuntimeException rethrow(Throwable e, Class<X> declaredType) throws IOException, X {
/* 168 */     Preconditions.checkNotNull(e);
/* 169 */     this.thrown = e;
/* 170 */     Throwables.propagateIfPossible(e, IOException.class);
/* 171 */     Throwables.propagateIfPossible(e, declaredType);
/* 172 */     throw new RuntimeException(e);
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
/*     */   public <X1 extends Exception, X2 extends Exception> RuntimeException rethrow(Throwable e, Class<X1> declaredType1, Class<X2> declaredType2) throws IOException, X1, X2 {
/* 192 */     Preconditions.checkNotNull(e);
/* 193 */     this.thrown = e;
/* 194 */     Throwables.propagateIfPossible(e, IOException.class);
/* 195 */     Throwables.propagateIfPossible(e, declaredType1, declaredType2);
/* 196 */     throw new RuntimeException(e);
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
/*     */   public void close() throws IOException {
/* 208 */     Throwable throwable = this.thrown;
/*     */ 
/*     */     
/* 211 */     while (!this.stack.isEmpty()) {
/* 212 */       Closeable closeable = this.stack.pop();
/*     */       try {
/* 214 */         closeable.close();
/* 215 */       } catch (Throwable e) {
/* 216 */         if (throwable == null) {
/* 217 */           throwable = e; continue;
/*     */         } 
/* 219 */         this.suppressor.suppress(closeable, throwable, e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 224 */     if (this.thrown == null && throwable != null) {
/* 225 */       Throwables.propagateIfPossible(throwable, IOException.class);
/* 226 */       throw new AssertionError(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static interface Suppressor
/*     */   {
/*     */     void suppress(Closeable param1Closeable, Throwable param1Throwable1, Throwable param1Throwable2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static final class LoggingSuppressor
/*     */     implements Suppressor
/*     */   {
/* 247 */     static final LoggingSuppressor INSTANCE = new LoggingSuppressor();
/*     */ 
/*     */ 
/*     */     
/*     */     public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed) {
/* 252 */       Closeables.logger.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, suppressed);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static final class SuppressingSuppressor
/*     */     implements Suppressor
/*     */   {
/* 263 */     static final SuppressingSuppressor INSTANCE = new SuppressingSuppressor();
/*     */     
/*     */     static boolean isAvailable() {
/* 266 */       return (addSuppressed != null);
/*     */     }
/*     */     
/* 269 */     static final Method addSuppressed = getAddSuppressed();
/*     */     
/*     */     private static Method getAddSuppressed() {
/*     */       try {
/* 273 */         return Throwable.class.getMethod("addSuppressed", new Class[] { Throwable.class });
/* 274 */       } catch (Throwable e) {
/* 275 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed) {
/* 282 */       if (thrown == suppressed) {
/*     */         return;
/*     */       }
/*     */       try {
/* 286 */         addSuppressed.invoke(thrown, new Object[] { suppressed });
/* 287 */       } catch (Throwable e) {
/*     */         
/* 289 */         Closer.LoggingSuppressor.INSTANCE.suppress(closeable, thrown, suppressed);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\io\Closer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */