/*     */ package net.minecraft.util.org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LazyInitializer<T>
/*     */   implements ConcurrentInitializer<T>
/*     */ {
/*     */   private volatile T object;
/*     */   
/*     */   public T get() throws ConcurrentException {
/*  95 */     T result = this.object;
/*     */     
/*  97 */     if (result == null) {
/*  98 */       synchronized (this) {
/*  99 */         result = this.object;
/* 100 */         if (result == null) {
/* 101 */           this.object = result = initialize();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 106 */     return result;
/*     */   }
/*     */   
/*     */   protected abstract T initialize() throws ConcurrentException;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\concurrent\LazyInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */