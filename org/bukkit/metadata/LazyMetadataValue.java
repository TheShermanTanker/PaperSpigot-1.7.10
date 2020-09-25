/*     */ package org.bukkit.metadata;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LazyMetadataValue
/*     */   extends MetadataValueAdapter
/*     */   implements MetadataValue
/*     */ {
/*     */   private Callable<Object> lazyValue;
/*     */   private CacheStrategy cacheStrategy;
/*     */   private SoftReference<Object> internalValue;
/*  24 */   private static final Object ACTUALLY_NULL = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LazyMetadataValue(Plugin owningPlugin, Callable<Object> lazyValue) {
/*  35 */     this(owningPlugin, CacheStrategy.CACHE_AFTER_FIRST_EVAL, lazyValue);
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
/*     */   public LazyMetadataValue(Plugin owningPlugin, CacheStrategy cacheStrategy, Callable<Object> lazyValue) {
/*  48 */     super(owningPlugin);
/*  49 */     Validate.notNull(cacheStrategy, "cacheStrategy cannot be null");
/*  50 */     Validate.notNull(lazyValue, "lazyValue cannot be null");
/*  51 */     this.internalValue = new SoftReference(null);
/*  52 */     this.lazyValue = lazyValue;
/*  53 */     this.cacheStrategy = cacheStrategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LazyMetadataValue(Plugin owningPlugin) {
/*  61 */     super(owningPlugin);
/*     */   }
/*     */   
/*     */   public Object value() {
/*  65 */     eval();
/*  66 */     Object value = this.internalValue.get();
/*  67 */     if (value == ACTUALLY_NULL) {
/*  68 */       return null;
/*     */     }
/*  70 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void eval() throws MetadataEvaluationException {
/*  80 */     if (this.cacheStrategy == CacheStrategy.NEVER_CACHE || this.internalValue.get() == null) {
/*     */       try {
/*  82 */         Object value = this.lazyValue.call();
/*  83 */         if (value == null) {
/*  84 */           value = ACTUALLY_NULL;
/*     */         }
/*  86 */         this.internalValue = new SoftReference(value);
/*  87 */       } catch (Exception e) {
/*  88 */         throw new MetadataEvaluationException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void invalidate() {
/*  94 */     if (this.cacheStrategy != CacheStrategy.CACHE_ETERNALLY) {
/*  95 */       this.internalValue.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum CacheStrategy
/*     */   {
/* 107 */     CACHE_AFTER_FIRST_EVAL,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     NEVER_CACHE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     CACHE_ETERNALLY;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\LazyMetadataValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */