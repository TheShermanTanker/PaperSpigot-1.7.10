/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.InventoryHolder;
/*    */ 
/*    */ public class ContainerAnvilInventory
/*    */   extends InventorySubcontainer
/*    */ {
/*    */   final ContainerAnvil a;
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
/*    */   ContainerAnvilInventory(ContainerAnvil containeranvil, String s, boolean flag, int i) {
/* 44 */     super(s, flag, i);
/* 45 */     this.a = containeranvil;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 50 */     return this.maxStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 55 */     super.update();
/* 56 */     this.a.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerAnvilInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */