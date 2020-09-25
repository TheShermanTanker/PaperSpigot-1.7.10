/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.EntityMinecartAbstract;
/*    */ import net.minecraft.server.v1_7_R4.EntityMinecartChest;
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.StorageMinecart;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftMinecartChest extends CraftMinecart implements StorageMinecart {
/*    */   private final CraftInventory inventory;
/*    */   
/*    */   public CraftMinecartChest(CraftServer server, EntityMinecartChest entity) {
/* 16 */     super(server, (EntityMinecartAbstract)entity);
/* 17 */     this.inventory = new CraftInventory((IInventory)entity);
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 21 */     return (Inventory)this.inventory;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "CraftMinecartChest{inventory=" + this.inventory + '}';
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 30 */     return EntityType.MINECART_CHEST;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftMinecartChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */