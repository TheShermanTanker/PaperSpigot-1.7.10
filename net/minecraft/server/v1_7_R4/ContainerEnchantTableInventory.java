/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.InventoryHolder;
/*    */ 
/*    */ public class ContainerEnchantTableInventory
/*    */   extends InventorySubcontainer
/*    */ {
/*    */   final ContainerEnchantTable enchantTable;
/* 14 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*    */   public Player player;
/* 16 */   private int maxStack = 64;
/*    */   
/*    */   public ItemStack[] getContents() {
/* 19 */     return this.items;
/*    */   }
/*    */   
/*    */   public void onOpen(CraftHumanEntity who) {
/* 23 */     this.transaction.add(who);
/*    */   }
/*    */   
/*    */   public void onClose(CraftHumanEntity who) {
/* 27 */     this.transaction.remove(who);
/*    */   }
/*    */   
/*    */   public List<HumanEntity> getViewers() {
/* 31 */     return this.transaction;
/*    */   }
/*    */   
/*    */   public InventoryHolder getOwner() {
/* 35 */     return (InventoryHolder)this.player;
/*    */   }
/*    */   
/*    */   public void setMaxStackSize(int size) {
/* 39 */     this.maxStack = size;
/*    */   }
/*    */ 
/*    */   
/*    */   ContainerEnchantTableInventory(ContainerEnchantTable containerenchanttable, String s, boolean flag, int i) {
/* 44 */     super(s, flag, i);
/* 45 */     this.enchantTable = containerenchanttable;
/* 46 */     setMaxStackSize(1);
/*    */   }
/*    */   
/*    */   public int getMaxStackSize() {
/* 50 */     return this.maxStack;
/*    */   }
/*    */   
/*    */   public void update() {
/* 54 */     super.update();
/* 55 */     this.enchantTable.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerEnchantTableInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */