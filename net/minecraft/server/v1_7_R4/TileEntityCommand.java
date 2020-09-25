/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityCommand
/*    */   extends TileEntity
/*    */ {
/* 14 */   private final CommandBlockListenerAbstract a = new TileEntityCommandListener(this);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 54 */     super.b(paramNBTTagCompound);
/* 55 */     this.a.a(paramNBTTagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 60 */     super.a(paramNBTTagCompound);
/* 61 */     this.a.b(paramNBTTagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet getUpdatePacket() {
/* 66 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 67 */     b(nBTTagCompound);
/* 68 */     return new PacketPlayOutTileEntityData(this.x, this.y, this.z, 2, nBTTagCompound);
/*    */   }
/*    */   
/*    */   public CommandBlockListenerAbstract getCommandBlock() {
/* 72 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */