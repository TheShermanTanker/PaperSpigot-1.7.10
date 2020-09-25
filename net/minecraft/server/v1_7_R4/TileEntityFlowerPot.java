/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityFlowerPot
/*    */   extends TileEntity
/*    */ {
/*    */   private Item a;
/*    */   private int i;
/*    */   
/*    */   public TileEntityFlowerPot() {}
/*    */   
/*    */   public TileEntityFlowerPot(Item paramItem, int paramInt) {
/* 18 */     this.a = paramItem;
/* 19 */     this.i = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 25 */     super.b(paramNBTTagCompound);
/* 26 */     paramNBTTagCompound.setInt("Item", Item.getId(this.a));
/* 27 */     paramNBTTagCompound.setInt("Data", this.i);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 32 */     super.a(paramNBTTagCompound);
/* 33 */     this.a = Item.getById(paramNBTTagCompound.getInt("Item"));
/* 34 */     this.i = paramNBTTagCompound.getInt("Data");
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet getUpdatePacket() {
/* 39 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 40 */     b(nBTTagCompound);
/* 41 */     return new PacketPlayOutTileEntityData(this.x, this.y, this.z, 5, nBTTagCompound);
/*    */   }
/*    */   
/*    */   public void a(Item paramItem, int paramInt) {
/* 45 */     this.a = paramItem;
/* 46 */     this.i = paramInt;
/*    */   }
/*    */   
/*    */   public Item a() {
/* 50 */     return this.a;
/*    */   }
/*    */   
/*    */   public int b() {
/* 54 */     return this.i;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityFlowerPot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */