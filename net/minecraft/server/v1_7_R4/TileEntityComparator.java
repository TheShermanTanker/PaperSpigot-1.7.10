/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class TileEntityComparator
/*    */   extends TileEntity
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 10 */     super.b(paramNBTTagCompound);
/* 11 */     paramNBTTagCompound.setInt("OutputSignal", this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 16 */     super.a(paramNBTTagCompound);
/* 17 */     this.a = paramNBTTagCompound.getInt("OutputSignal");
/*    */   }
/*    */   
/*    */   public int a() {
/* 21 */     return this.a;
/*    */   }
/*    */   
/*    */   public void a(int paramInt) {
/* 25 */     this.a = paramInt;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */