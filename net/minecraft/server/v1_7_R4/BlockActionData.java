/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class BlockActionData {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private Block d;
/*    */   private int e;
/*    */   private int f;
/*    */   
/*    */   public BlockActionData(int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4, int paramInt5) {
/* 12 */     this.a = paramInt1;
/* 13 */     this.b = paramInt2;
/* 14 */     this.c = paramInt3;
/* 15 */     this.e = paramInt4;
/* 16 */     this.f = paramInt5;
/* 17 */     this.d = paramBlock;
/*    */   }
/*    */   
/*    */   public int a() {
/* 21 */     return this.a;
/*    */   }
/*    */   
/*    */   public int b() {
/* 25 */     return this.b;
/*    */   }
/*    */   
/*    */   public int c() {
/* 29 */     return this.c;
/*    */   }
/*    */   
/*    */   public int d() {
/* 33 */     return this.e;
/*    */   }
/*    */   
/*    */   public int e() {
/* 37 */     return this.f;
/*    */   }
/*    */   
/*    */   public Block f() {
/* 41 */     return this.d;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 46 */     if (paramObject instanceof BlockActionData) {
/* 47 */       BlockActionData blockActionData = (BlockActionData)paramObject;
/* 48 */       return (this.a == blockActionData.a && this.b == blockActionData.b && this.c == blockActionData.c && this.e == blockActionData.e && this.f == blockActionData.f && this.d == blockActionData.d);
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     return "TE(" + this.a + "," + this.b + "," + this.c + ")," + this.e + "," + this.f + "," + this.d;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockActionData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */