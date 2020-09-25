/*     */ package net.minecraft.util.com.google.common.reflect;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.collect.ForwardingMap;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class ImmutableTypeToInstanceMap<B>
/*     */   extends ForwardingMap<TypeToken<? extends B>, B>
/*     */   implements TypeToInstanceMap<B>
/*     */ {
/*     */   private final ImmutableMap<TypeToken<? extends B>, B> delegate;
/*     */   
/*     */   public static <B> ImmutableTypeToInstanceMap<B> of() {
/*  38 */     return new ImmutableTypeToInstanceMap<B>(ImmutableMap.of());
/*     */   }
/*     */ 
/*     */   
/*     */   public static <B> Builder<B> builder() {
/*  43 */     return new Builder<B>();
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
/*     */   @Beta
/*     */   public static final class Builder<B>
/*     */   {
/*  64 */     private final ImmutableMap.Builder<TypeToken<? extends B>, B> mapBuilder = ImmutableMap.builder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends B> Builder<B> put(Class<T> key, T value) {
/*  74 */       this.mapBuilder.put(TypeToken.of(key), value);
/*  75 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends B> Builder<B> put(TypeToken<T> key, T value) {
/*  83 */       this.mapBuilder.put(key.rejectTypeVariables(), value);
/*  84 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ImmutableTypeToInstanceMap<B> build() {
/*  94 */       return new ImmutableTypeToInstanceMap<B>(this.mapBuilder.build());
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */   
/*     */   private ImmutableTypeToInstanceMap(ImmutableMap<TypeToken<? extends B>, B> delegate) {
/* 101 */     this.delegate = delegate;
/*     */   }
/*     */   
/*     */   public <T extends B> T getInstance(TypeToken<T> type) {
/* 105 */     return trustedGet(type.rejectTypeVariables());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends B> T putInstance(TypeToken<T> type, T value) {
/* 114 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T extends B> T getInstance(Class<T> type) {
/* 118 */     return trustedGet(TypeToken.of(type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends B> T putInstance(Class<T> type, T value) {
/* 127 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected Map<TypeToken<? extends B>, B> delegate() {
/* 131 */     return (Map<TypeToken<? extends B>, B>)this.delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   private <T extends B> T trustedGet(TypeToken<T> type) {
/* 136 */     return (T)this.delegate.get(type);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\reflect\ImmutableTypeToInstanceMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */