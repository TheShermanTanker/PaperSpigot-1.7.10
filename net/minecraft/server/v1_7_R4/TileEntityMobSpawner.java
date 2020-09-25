/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityMobSpawner
/*    */   extends TileEntity
/*    */ {
/* 11 */   private final MobSpawnerAbstract a = new MobSpawner(this);
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
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 49 */     super.a(paramNBTTagCompound);
/* 50 */     this.a.a(paramNBTTagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 55 */     super.b(paramNBTTagCompound);
/* 56 */     this.a.b(paramNBTTagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public void h() {
/* 61 */     this.a.g();
/* 62 */     super.h();
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet getUpdatePacket() {
/* 67 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 68 */     b(nBTTagCompound);
/* 69 */     nBTTagCompound.remove("SpawnPotentials");
/* 70 */     return new PacketPlayOutTileEntityData(this.x, this.y, this.z, 1, nBTTagCompound);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean c(int paramInt1, int paramInt2) {
/* 75 */     if (this.a.b(paramInt1)) return true; 
/* 76 */     return super.c(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public MobSpawnerAbstract getSpawner() {
/* 80 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityMobSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */