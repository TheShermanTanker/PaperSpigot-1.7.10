/*     */ package net.minecraft.util.com.google.common.collect;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ abstract class AbstractListMultimap<K, V>
/*     */   extends AbstractMapBasedMultimap<K, V>
/*     */   implements ListMultimap<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = 6588350623831699109L;
/*     */   
/*     */   protected AbstractListMultimap(Map<K, Collection<V>> map) {
/*  46 */     super(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<V> createUnmodifiableEmptyCollection() {
/*  53 */     return ImmutableList.of();
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
/*     */   public List<V> get(@Nullable K key) {
/*  66 */     return (List<V>)super.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<V> removeAll(@Nullable Object key) {
/*  77 */     return (List<V>)super.removeAll(key);
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
/*     */   public List<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
/*  89 */     return (List<V>)super.replaceValues(key, values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean put(@Nullable K key, @Nullable V value) {
/* 100 */     return super.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<K, Collection<V>> asMap() {
/* 110 */     return super.asMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 121 */     return super.equals(object);
/*     */   }
/*     */   
/*     */   abstract List<V> createCollection();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\AbstractListMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */