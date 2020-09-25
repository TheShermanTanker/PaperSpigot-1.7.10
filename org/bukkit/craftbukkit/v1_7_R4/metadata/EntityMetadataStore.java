/*    */ package org.bukkit.craftbukkit.v1_7_R4.metadata;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.metadata.MetadataStore;
/*    */ import org.bukkit.metadata.MetadataStoreBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMetadataStore
/*    */   extends MetadataStoreBase<Entity>
/*    */   implements MetadataStore<Entity>
/*    */ {
/*    */   protected String disambiguate(Entity entity, String metadataKey) {
/* 21 */     return entity.getUniqueId().toString() + ":" + metadataKey;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\metadata\EntityMetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */