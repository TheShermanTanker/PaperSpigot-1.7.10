/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SourceBlock
/*    */   implements ISourceBlock
/*    */ {
/*    */   private final World a;
/*    */   private final int b;
/*    */   private final int c;
/*    */   private final int d;
/*    */   
/*    */   public SourceBlock(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     this.a = paramWorld;
/* 16 */     this.b = paramInt1;
/* 17 */     this.c = paramInt2;
/* 18 */     this.d = paramInt3;
/*    */   }
/*    */ 
/*    */   
/*    */   public World k() {
/* 23 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getX() {
/* 28 */     return this.b + 0.5D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getY() {
/* 33 */     return this.c + 0.5D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getZ() {
/* 38 */     return this.d + 0.5D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockX() {
/* 43 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockY() {
/* 48 */     return this.c;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockZ() {
/* 53 */     return this.d;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int h() {
/* 63 */     return this.a.getData(this.b, this.c, this.d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntity getTileEntity() {
/* 73 */     return this.a.getTileEntity(this.b, this.c, this.d);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SourceBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */