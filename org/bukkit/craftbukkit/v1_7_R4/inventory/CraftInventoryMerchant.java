/*   */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*   */ import net.minecraft.server.v1_7_R4.IInventory;
/*   */ import net.minecraft.server.v1_7_R4.InventoryMerchant;
/*   */ import org.bukkit.inventory.MerchantInventory;
/*   */ 
/*   */ public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory {
/*   */   public CraftInventoryMerchant(InventoryMerchant merchant) {
/* 8 */     super((IInventory)merchant);
/*   */   }
/*   */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryMerchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */