/*    */ package org.bukkit.craftbukkit.v1_7_R4.metadata;
/*    */ 
/*    */ import org.bukkit.OfflinePlayer;
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
/*    */ public class PlayerMetadataStore
/*    */   extends MetadataStoreBase<OfflinePlayer>
/*    */   implements MetadataStore<OfflinePlayer>
/*    */ {
/*    */   protected String disambiguate(OfflinePlayer player, String metadataKey) {
/* 21 */     return player.getName().toLowerCase() + ":" + metadataKey;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\metadata\PlayerMetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */