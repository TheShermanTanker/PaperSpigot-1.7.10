/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class TileEntityRecordPlayer
/*    */   extends TileEntity
/*    */ {
/*    */   private ItemStack record;
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 10 */     super.a(nbttagcompound);
/* 11 */     if (nbttagcompound.hasKeyOfType("RecordItem", 10)) {
/* 12 */       setRecord(ItemStack.createStack(nbttagcompound.getCompound("RecordItem")));
/* 13 */     } else if (nbttagcompound.getInt("Record") > 0) {
/* 14 */       setRecord(new ItemStack(Item.getById(nbttagcompound.getInt("Record")), 1, 0));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 19 */     super.b(nbttagcompound);
/* 20 */     if (getRecord() != null) {
/* 21 */       nbttagcompound.set("RecordItem", getRecord().save(new NBTTagCompound()));
/* 22 */       nbttagcompound.setInt("Record", Item.getId(getRecord().getItem()));
/*    */     } 
/*    */   }
/*    */   
/*    */   public ItemStack getRecord() {
/* 27 */     return this.record;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRecord(ItemStack itemstack) {
/* 32 */     if (itemstack != null) {
/* 33 */       itemstack.count = 1;
/*    */     }
/*    */ 
/*    */     
/* 37 */     this.record = itemstack;
/* 38 */     update();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityRecordPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */