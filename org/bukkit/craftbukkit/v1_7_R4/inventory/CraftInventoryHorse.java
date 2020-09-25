/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import org.bukkit.inventory.HorseInventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryHorse
/*    */   extends CraftInventory implements HorseInventory {
/*    */   public CraftInventoryHorse(IInventory inventory) {
/* 10 */     super(inventory);
/*    */   }
/*    */   
/*    */   public ItemStack getSaddle() {
/* 14 */     return getItem(0);
/*    */   }
/*    */   
/*    */   public ItemStack getArmor() {
/* 18 */     return getItem(1);
/*    */   }
/*    */   
/*    */   public void setSaddle(ItemStack stack) {
/* 22 */     setItem(0, stack);
/*    */   }
/*    */   
/*    */   public void setArmor(ItemStack stack) {
/* 26 */     setItem(1, stack);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryHorse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */