/*     */ package org.bukkit.metadata;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public abstract class MetadataStoreBase<T> {
/*   9 */   private Map<String, Map<Plugin, MetadataValue>> metadataMap = new HashMap<String, Map<Plugin, MetadataValue>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMetadata(T subject, String metadataKey, MetadataValue newMetadataValue) {
/*  34 */     Validate.notNull(newMetadataValue, "Value cannot be null");
/*  35 */     Plugin owningPlugin = newMetadataValue.getOwningPlugin();
/*  36 */     Validate.notNull(owningPlugin, "Plugin cannot be null");
/*  37 */     String key = disambiguate(subject, metadataKey);
/*  38 */     Map<Plugin, MetadataValue> entry = this.metadataMap.get(key);
/*  39 */     if (entry == null) {
/*  40 */       entry = new WeakHashMap<Plugin, MetadataValue>(1);
/*  41 */       this.metadataMap.put(key, entry);
/*     */     } 
/*  43 */     entry.put(owningPlugin, newMetadataValue);
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
/*     */   public synchronized List<MetadataValue> getMetadata(T subject, String metadataKey) {
/*  57 */     String key = disambiguate(subject, metadataKey);
/*  58 */     if (this.metadataMap.containsKey(key)) {
/*  59 */       Collection<MetadataValue> values = ((Map)this.metadataMap.get(key)).values();
/*  60 */       return Collections.unmodifiableList(new ArrayList<MetadataValue>(values));
/*     */     } 
/*  62 */     return Collections.emptyList();
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
/*     */   public synchronized boolean hasMetadata(T subject, String metadataKey) {
/*  75 */     String key = disambiguate(subject, metadataKey);
/*  76 */     return this.metadataMap.containsKey(key);
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
/*     */   public synchronized void removeMetadata(T subject, String metadataKey, Plugin owningPlugin) {
/*  91 */     Validate.notNull(owningPlugin, "Plugin cannot be null");
/*  92 */     String key = disambiguate(subject, metadataKey);
/*  93 */     Map<Plugin, MetadataValue> entry = this.metadataMap.get(key);
/*  94 */     if (entry == null) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     entry.remove(owningPlugin);
/*  99 */     if (entry.isEmpty()) {
/* 100 */       this.metadataMap.remove(key);
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
/*     */   public synchronized void invalidateAll(Plugin owningPlugin) {
/* 114 */     Validate.notNull(owningPlugin, "Plugin cannot be null");
/* 115 */     for (Map<Plugin, MetadataValue> values : this.metadataMap.values()) {
/* 116 */       if (values.containsKey(owningPlugin))
/* 117 */         ((MetadataValue)values.get(owningPlugin)).invalidate(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract String disambiguate(T paramT, String paramString);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\MetadataStoreBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */