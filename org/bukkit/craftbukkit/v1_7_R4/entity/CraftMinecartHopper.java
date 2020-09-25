/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.EntityMinecartAbstract;
/*    */ import net.minecraft.server.v1_7_R4.EntityMinecartHopper;
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.minecart.HopperMinecart;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ final class CraftMinecartHopper
/*    */   extends CraftMinecart implements HopperMinecart {
/*    */   CraftMinecartHopper(CraftServer server, EntityMinecartHopper entity) {
/* 15 */     super(server, (EntityMinecartAbstract)entity);
/* 16 */     this.inventory = new CraftInventory((IInventory)entity);
/*    */   }
/*    */   private final CraftInventory inventory;
/*    */   
/*    */   public String toString() {
/* 21 */     return "CraftMinecartHopper{inventory=" + this.inventory + '}';
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.MINECART_HOPPER;
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 29 */     return (Inventory)this.inventory;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftMinecartHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */