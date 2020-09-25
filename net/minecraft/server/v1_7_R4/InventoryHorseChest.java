/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.inventory.InventoryHolder;
/*    */ 
/*    */ public class InventoryHorseChest extends InventorySubcontainer {
/*    */   public List<HumanEntity> transaction;
/*    */   
/*    */   public InventoryHorseChest(String s, int i) {
/* 12 */     super(s, false, i);
/*    */ 
/*    */ 
/*    */     
/* 16 */     this.transaction = new ArrayList<HumanEntity>();
/*    */     
/* 18 */     this.maxStack = 64;
/*    */   } private EntityHorse horse; private int maxStack;
/*    */   public InventoryHorseChest(String s, int i, EntityHorse horse) {
/* 21 */     this(s, i);
/* 22 */     this.horse = horse;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack[] getContents() {
/* 27 */     return this.items;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onOpen(CraftHumanEntity who) {
/* 32 */     this.transaction.add(who);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose(CraftHumanEntity who) {
/* 37 */     this.transaction.remove(who);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<HumanEntity> getViewers() {
/* 42 */     return this.transaction;
/*    */   }
/*    */ 
/*    */   
/*    */   public InventoryHolder getOwner() {
/* 47 */     return (InventoryHolder)this.horse.getBukkitEntity();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setMaxStackSize(int size) {
/* 52 */     this.maxStack = size;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 57 */     return this.maxStack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InventoryHorseChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */