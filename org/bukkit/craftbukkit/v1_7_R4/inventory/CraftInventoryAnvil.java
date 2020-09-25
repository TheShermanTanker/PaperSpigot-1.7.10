/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.IInventory;
/*    */ import net.minecraft.server.v1_7_R4.ItemStack;
/*    */ import org.bukkit.inventory.AnvilInventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryAnvil extends CraftInventory implements AnvilInventory {
/*    */   private final IInventory resultInventory;
/*    */   
/*    */   public CraftInventoryAnvil(IInventory inventory, IInventory resultInventory) {
/* 12 */     super(inventory);
/* 13 */     this.resultInventory = resultInventory;
/*    */   }
/*    */   
/*    */   public IInventory getResultInventory() {
/* 17 */     return this.resultInventory;
/*    */   }
/*    */   
/*    */   public IInventory getIngredientsInventory() {
/* 21 */     return this.inventory;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItem(int slot) {
/* 26 */     if (slot < getIngredientsInventory().getSize()) {
/* 27 */       ItemStack itemStack = getIngredientsInventory().getItem(slot);
/* 28 */       return (itemStack == null) ? null : CraftItemStack.asCraftMirror(itemStack);
/*    */     } 
/* 30 */     ItemStack item = getResultInventory().getItem(slot - getIngredientsInventory().getSize());
/* 31 */     return (item == null) ? null : CraftItemStack.asCraftMirror(item);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItem(int index, ItemStack item) {
/* 37 */     if (index < getIngredientsInventory().getSize()) {
/* 38 */       getIngredientsInventory().setItem(index, (item == null) ? null : CraftItemStack.asNMSCopy(item));
/*    */     } else {
/* 40 */       getResultInventory().setItem(index - getIngredientsInventory().getSize(), (item == null) ? null : CraftItemStack.asNMSCopy(item));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 46 */     return getResultInventory().getSize() + getIngredientsInventory().getSize();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryAnvil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */