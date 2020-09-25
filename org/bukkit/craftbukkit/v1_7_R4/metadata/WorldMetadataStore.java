/*    */ package org.bukkit.craftbukkit.v1_7_R4.metadata;
/*    */ 
/*    */ import org.bukkit.World;
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
/*    */ public class WorldMetadataStore
/*    */   extends MetadataStoreBase<World>
/*    */   implements MetadataStore<World>
/*    */ {
/*    */   protected String disambiguate(World world, String metadataKey) {
/* 20 */     return world.getUID().toString() + ":" + metadataKey;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\metadata\WorldMetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */