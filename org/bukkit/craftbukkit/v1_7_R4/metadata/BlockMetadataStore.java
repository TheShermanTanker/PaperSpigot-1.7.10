/*    */ package org.bukkit.craftbukkit.v1_7_R4.metadata;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.metadata.MetadataStore;
/*    */ import org.bukkit.metadata.MetadataStoreBase;
/*    */ import org.bukkit.metadata.MetadataValue;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMetadataStore
/*    */   extends MetadataStoreBase<Block>
/*    */   implements MetadataStore<Block>
/*    */ {
/*    */   private final World owningWorld;
/*    */   
/*    */   public BlockMetadataStore(World owningWorld) {
/* 24 */     this.owningWorld = owningWorld;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String disambiguate(Block block, String metadataKey) {
/* 36 */     return Integer.toString(block.getX()) + ":" + Integer.toString(block.getY()) + ":" + Integer.toString(block.getZ()) + ":" + metadataKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<MetadataValue> getMetadata(Block block, String metadataKey) {
/* 46 */     if (block.getWorld() == this.owningWorld) {
/* 47 */       return super.getMetadata(block, metadataKey);
/*    */     }
/* 49 */     throw new IllegalArgumentException("Block does not belong to world " + this.owningWorld.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasMetadata(Block block, String metadataKey) {
/* 60 */     if (block.getWorld() == this.owningWorld) {
/* 61 */       return super.hasMetadata(block, metadataKey);
/*    */     }
/* 63 */     throw new IllegalArgumentException("Block does not belong to world " + this.owningWorld.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeMetadata(Block block, String metadataKey, Plugin owningPlugin) {
/* 74 */     if (block.getWorld() == this.owningWorld) {
/* 75 */       super.removeMetadata(block, metadataKey, owningPlugin);
/*    */     } else {
/* 77 */       throw new IllegalArgumentException("Block does not belong to world " + this.owningWorld.getName());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMetadata(Block block, String metadataKey, MetadataValue newMetadataValue) {
/* 88 */     if (block.getWorld() == this.owningWorld) {
/* 89 */       super.setMetadata(block, metadataKey, newMetadataValue);
/*    */     } else {
/* 91 */       throw new IllegalArgumentException("Block does not belong to world " + this.owningWorld.getName());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\metadata\BlockMetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */