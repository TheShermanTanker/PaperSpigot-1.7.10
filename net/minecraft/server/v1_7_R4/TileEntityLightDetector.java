/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class TileEntityLightDetector
/*    */   extends TileEntity
/*    */ {
/*    */   public void h() {
/*  8 */     if (this.world != null && !this.world.isStatic) {
/*  9 */       this.h = q();
/* 10 */       if (this.h instanceof BlockDaylightDetector)
/* 11 */         ((BlockDaylightDetector)this.h).e(this.world, this.x, this.y, this.z); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityLightDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */