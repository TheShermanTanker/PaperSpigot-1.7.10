/*     */ package net.minecraft.util.io.netty.util;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultAttributeMap
/*     */   implements AttributeMap
/*     */ {
/*  30 */   private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, Map> updater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, Map.class, "map");
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Map<AttributeKey<?>, Attribute<?>> map;
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Attribute<T> attr(AttributeKey<T> key) {
/*  39 */     Map<AttributeKey<?>, Attribute<?>> map = this.map;
/*  40 */     if (map == null) {
/*     */       
/*  42 */       map = new IdentityHashMap<AttributeKey<?>, Attribute<?>>(2);
/*  43 */       if (!updater.compareAndSet(this, null, map)) {
/*  44 */         map = this.map;
/*     */       }
/*     */     } 
/*     */     
/*  48 */     synchronized (map) {
/*     */       
/*  50 */       Attribute<T> attr = (Attribute<T>)map.get(key);
/*  51 */       if (attr == null) {
/*  52 */         attr = new DefaultAttribute<T>(map, key);
/*  53 */         map.put(key, attr);
/*     */       } 
/*  55 */       return attr;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class DefaultAttribute<T>
/*     */     extends AtomicReference<T>
/*     */     implements Attribute<T> {
/*     */     private static final long serialVersionUID = -2661411462200283011L;
/*     */     private final Map<AttributeKey<?>, Attribute<?>> map;
/*     */     private final AttributeKey<T> key;
/*     */     
/*     */     DefaultAttribute(Map<AttributeKey<?>, Attribute<?>> map, AttributeKey<T> key) {
/*  67 */       this.map = map;
/*  68 */       this.key = key;
/*     */     }
/*     */ 
/*     */     
/*     */     public AttributeKey<T> key() {
/*  73 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public T setIfAbsent(T value) {
/*  78 */       while (!compareAndSet(null, value)) {
/*  79 */         T old = get();
/*  80 */         if (old != null) {
/*  81 */           return old;
/*     */         }
/*     */       } 
/*  84 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public T getAndRemove() {
/*  89 */       T oldValue = getAndSet(null);
/*  90 */       remove0();
/*  91 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/*  96 */       set(null);
/*  97 */       remove0();
/*     */     }
/*     */     
/*     */     private void remove0() {
/* 101 */       synchronized (this.map) {
/* 102 */         this.map.remove(this.key);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\DefaultAttributeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */