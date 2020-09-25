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
/*    */ class WorldGenNetherPieceWeight
/*    */ {
/*    */   public Class a;
/*    */   public final int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public boolean e;
/*    */   
/*    */   public WorldGenNetherPieceWeight(Class paramClass, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 46 */     this.a = paramClass;
/* 47 */     this.b = paramInt1;
/* 48 */     this.d = paramInt2;
/* 49 */     this.e = paramBoolean;
/*    */   }
/*    */   
/*    */   public WorldGenNetherPieceWeight(Class paramClass, int paramInt1, int paramInt2) {
/* 53 */     this(paramClass, paramInt1, paramInt2, false);
/*    */   }
/*    */   
/*    */   public boolean a(int paramInt) {
/* 57 */     return (this.d == 0 || this.c < this.d);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 61 */     return (this.d == 0 || this.c < this.d);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNetherPieceWeight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */