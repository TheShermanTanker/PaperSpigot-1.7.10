/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.primitives.Primitives;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ImmutableClassToInstanceMap<B>
/*     */   extends ForwardingMap<Class<? extends B>, B>
/*     */   implements ClassToInstanceMap<B>
/*     */ {
/*     */   private final ImmutableMap<Class<? extends B>, B> delegate;
/*     */   
/*     */   public static <B> Builder<B> builder() {
/*  37 */     return new Builder<B>();
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
/*     */   public static final class Builder<B>
/*     */   {
/*  58 */     private final ImmutableMap.Builder<Class<? extends B>, B> mapBuilder = ImmutableMap.builder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends B> Builder<B> put(Class<T> key, T value) {
/*  66 */       this.mapBuilder.put(key, (B)value);
/*  67 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends B> Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> map) {
/*  81 */       for (Map.Entry<? extends Class<? extends T>, ? extends T> entry : map.entrySet()) {
/*  82 */         Class<? extends T> type = entry.getKey();
/*  83 */         T value = entry.getValue();
/*  84 */         this.mapBuilder.put(type, cast((Class)type, value));
/*     */       } 
/*  86 */       return this;
/*     */     }
/*     */     
/*     */     private static <B, T extends B> T cast(Class<T> type, B value) {
/*  90 */       return Primitives.wrap(type).cast(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableClassToInstanceMap<B> build() {
/* 100 */       return new ImmutableClassToInstanceMap<B>(this.mapBuilder.build());
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
/*     */   public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> map) {
/* 119 */     if (map instanceof ImmutableClassToInstanceMap) {
/*     */ 
/*     */       
/* 122 */       ImmutableClassToInstanceMap<B> cast = (ImmutableClassToInstanceMap)map;
/* 123 */       return cast;
/*     */     } 
/* 125 */     return (new Builder<B>()).<S>putAll(map).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> delegate) {
/* 132 */     this.delegate = delegate;
/*     */   }
/*     */   
/*     */   protected Map<Class<? extends B>, B> delegate() {
/* 136 */     return this.delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends B> T getInstance(Class<T> type) {
/* 142 */     return (T)this.delegate.get(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends B> T putInstance(Class<T> type, T value) {
/* 152 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ImmutableClassToInstanceMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */