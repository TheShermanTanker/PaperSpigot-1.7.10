/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityItem;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftItem
/*    */   extends CraftEntity implements Item {
/*    */   private final EntityItem item;
/*    */   
/*    */   public CraftItem(CraftServer server, Entity entity, EntityItem item) {
/* 16 */     super(server, entity);
/* 17 */     this.item = item;
/*    */   }
/*    */   
/*    */   public CraftItem(CraftServer server, EntityItem entity) {
/* 21 */     this(server, (Entity)entity, entity);
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 25 */     return (ItemStack)CraftItemStack.asCraftMirror(this.item.getItemStack());
/*    */   }
/*    */   
/*    */   public void setItemStack(ItemStack stack) {
/* 29 */     this.item.setItemStack(CraftItemStack.asNMSCopy(stack));
/*    */   }
/*    */   
/*    */   public int getPickupDelay() {
/* 33 */     return this.item.pickupDelay;
/*    */   }
/*    */   
/*    */   public void setPickupDelay(int delay) {
/* 37 */     this.item.pickupDelay = delay;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "CraftItem";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 46 */     return EntityType.DROPPED_ITEM;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */