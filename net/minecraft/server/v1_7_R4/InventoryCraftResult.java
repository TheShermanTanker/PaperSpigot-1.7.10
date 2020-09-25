/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.inventory.InventoryHolder;
/*    */ 
/*    */ public class InventoryCraftResult implements IInventory {
/* 10 */   private ItemStack[] items = new ItemStack[1];
/*    */ 
/*    */   
/* 13 */   private int maxStack = 64;
/*    */   
/*    */   public ItemStack[] getContents() {
/* 16 */     return this.items;
/*    */   }
/*    */   
/*    */   public InventoryHolder getOwner() {
/* 20 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onOpen(CraftHumanEntity who) {}
/*    */   
/*    */   public List<HumanEntity> getViewers() {
/* 27 */     return new ArrayList<HumanEntity>();
/*    */   }
/*    */   public void onClose(CraftHumanEntity who) {}
/*    */   public void setMaxStackSize(int size) {
/* 31 */     this.maxStack = size;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 38 */     return 1;
/*    */   }
/*    */   
/*    */   public ItemStack getItem(int i) {
/* 42 */     return this.items[0];
/*    */   }
/*    */   
/*    */   public String getInventoryName() {
/* 46 */     return "Result";
/*    */   }
/*    */   
/*    */   public boolean k_() {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public ItemStack splitStack(int i, int j) {
/* 54 */     if (this.items[0] != null) {
/* 55 */       ItemStack itemstack = this.items[0];
/*    */       
/* 57 */       this.items[0] = null;
/* 58 */       return itemstack;
/*    */     } 
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack splitWithoutUpdate(int i) {
/* 65 */     if (this.items[0] != null) {
/* 66 */       ItemStack itemstack = this.items[0];
/*    */       
/* 68 */       this.items[0] = null;
/* 69 */       return itemstack;
/*    */     } 
/* 71 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setItem(int i, ItemStack itemstack) {
/* 76 */     this.items[0] = itemstack;
/*    */   }
/*    */   
/*    */   public int getMaxStackSize() {
/* 80 */     return this.maxStack;
/*    */   }
/*    */   
/*    */   public void update() {}
/*    */   
/*    */   public boolean a(EntityHuman entityhuman) {
/* 86 */     return true;
/*    */   }
/*    */   
/*    */   public void startOpen() {}
/*    */   
/*    */   public void closeContainer() {}
/*    */   
/*    */   public boolean b(int i, ItemStack itemstack) {
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InventoryCraftResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */