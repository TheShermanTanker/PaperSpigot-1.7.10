/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class WorldGenFlatLayerInfo
/*    */ {
/*    */   private Block a;
/*  7 */   private int b = 1;
/*    */   private int c;
/*    */   private int d;
/*    */   
/*    */   public WorldGenFlatLayerInfo(int paramInt, Block paramBlock) {
/* 12 */     this.b = paramInt;
/* 13 */     this.a = paramBlock;
/*    */   }
/*    */   
/*    */   public WorldGenFlatLayerInfo(int paramInt1, Block paramBlock, int paramInt2) {
/* 17 */     this(paramInt1, paramBlock);
/* 18 */     this.c = paramInt2;
/*    */   }
/*    */   
/*    */   public int a() {
/* 22 */     return this.b;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block b() {
/* 30 */     return this.a;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int c() {
/* 38 */     return this.c;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int d() {
/* 46 */     return this.d;
/*    */   }
/*    */   
/*    */   public void c(int paramInt) {
/* 50 */     this.d = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     String str = Integer.toString(Block.getId(this.a));
/*    */     
/* 57 */     if (this.b > 1) {
/* 58 */       str = this.b + "x" + str;
/*    */     }
/* 60 */     if (this.c > 0) {
/* 61 */       str = str + ":" + this.c;
/*    */     }
/*    */     
/* 64 */     return str;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenFlatLayerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */