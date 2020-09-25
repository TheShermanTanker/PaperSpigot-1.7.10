/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMinecartTrack
/*    */   extends BlockMinecartTrackAbstract
/*    */ {
/*    */   protected BlockMinecartTrack() {
/* 11 */     super(false);
/*    */   }
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
/*    */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Block paramBlock) {
/* 31 */     if (paramBlock.isPowerSource() && (
/* 32 */       new MinecartTrackLogic(this, paramWorld, paramInt1, paramInt2, paramInt3)).a() == 3)
/* 33 */       a(paramWorld, paramInt1, paramInt2, paramInt3, false); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockMinecartTrack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */