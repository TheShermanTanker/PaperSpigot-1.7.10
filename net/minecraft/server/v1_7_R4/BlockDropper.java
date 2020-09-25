/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryDoubleChest;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.inventory.InventoryMoveItemEvent;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class BlockDropper extends BlockDispenser {
/* 10 */   private final IDispenseBehavior P = new DispenseBehaviorItem();
/*    */ 
/*    */ 
/*    */   
/*    */   protected IDispenseBehavior a(ItemStack itemstack) {
/* 15 */     return this.P;
/*    */   }
/*    */   
/*    */   public TileEntity a(World world, int i) {
/* 19 */     return new TileEntityDropper();
/*    */   }
/*    */   
/*    */   public void dispense(World world, int i, int j, int k) {
/* 23 */     SourceBlock sourceblock = new SourceBlock(world, i, j, k);
/* 24 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
/*    */     
/* 26 */     if (tileentitydispenser != null) {
/* 27 */       int l = tileentitydispenser.i();
/*    */       
/* 29 */       if (l < 0) {
/* 30 */         world.triggerEffect(1001, i, j, k, 0);
/*    */       } else {
/* 32 */         ItemStack itemstack1, itemstack = tileentitydispenser.getItem(l);
/* 33 */         int i1 = world.getData(i, j, k) & 0x7;
/* 34 */         IInventory iinventory = TileEntityHopper.getInventoryAt(world, (i + Facing.b[i1]), (j + Facing.c[i1]), (k + Facing.d[i1]));
/*    */ 
/*    */         
/* 37 */         if (iinventory != null) {
/*    */           Inventory destinationInventory;
/* 39 */           CraftItemStack oitemstack = CraftItemStack.asCraftMirror(itemstack.cloneItemStack().a(1));
/*    */ 
/*    */ 
/*    */           
/* 43 */           if (iinventory instanceof InventoryLargeChest) {
/* 44 */             CraftInventoryDoubleChest craftInventoryDoubleChest = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
/*    */           } else {
/* 46 */             destinationInventory = iinventory.getOwner().getInventory();
/*    */           } 
/*    */           
/* 49 */           InventoryMoveItemEvent event = new InventoryMoveItemEvent(tileentitydispenser.getOwner().getInventory(), (ItemStack)oitemstack.clone(), destinationInventory, true);
/* 50 */           world.getServer().getPluginManager().callEvent((Event)event);
/* 51 */           if (event.isCancelled()) {
/*    */             return;
/*    */           }
/* 54 */           itemstack1 = TileEntityHopper.addItem(iinventory, CraftItemStack.asNMSCopy(event.getItem()), Facing.OPPOSITE_FACING[i1]);
/* 55 */           if (event.getItem().equals(oitemstack) && itemstack1 == null) {
/*    */             
/* 57 */             itemstack1 = itemstack.cloneItemStack();
/* 58 */             if (--itemstack1.count == 0) {
/* 59 */               itemstack1 = null;
/*    */             }
/*    */           } else {
/* 62 */             itemstack1 = itemstack.cloneItemStack();
/*    */           } 
/*    */         } else {
/* 65 */           itemstack1 = this.P.a(sourceblock, itemstack);
/* 66 */           if (itemstack1 != null && itemstack1.count == 0) {
/* 67 */             itemstack1 = null;
/*    */           }
/*    */         } 
/*    */         
/* 71 */         tileentitydispenser.setItem(l, itemstack1);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockDropper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */