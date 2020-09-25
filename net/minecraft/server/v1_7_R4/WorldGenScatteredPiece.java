/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
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
/*    */ abstract class WorldGenScatteredPiece
/*    */   extends StructurePiece
/*    */ {
/*    */   protected int a;
/*    */   protected int b;
/*    */   protected int c;
/* 27 */   protected int d = -1;
/*    */ 
/*    */   
/*    */   public WorldGenScatteredPiece() {}
/*    */ 
/*    */   
/*    */   protected WorldGenScatteredPiece(Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 34 */     super(0);
/*    */     
/* 36 */     this.a = paramInt4;
/* 37 */     this.b = paramInt5;
/* 38 */     this.c = paramInt6;
/*    */     
/* 40 */     this.g = paramRandom.nextInt(4);
/*    */     
/* 42 */     switch (this.g) {
/*    */       case 0:
/*    */       case 2:
/* 45 */         this.f = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1 + paramInt4 - 1, paramInt2 + paramInt5 - 1, paramInt3 + paramInt6 - 1);
/*    */         return;
/*    */     } 
/* 48 */     this.f = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1 + paramInt6 - 1, paramInt2 + paramInt5 - 1, paramInt3 + paramInt4 - 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 55 */     paramNBTTagCompound.setInt("Width", this.a);
/* 56 */     paramNBTTagCompound.setInt("Height", this.b);
/* 57 */     paramNBTTagCompound.setInt("Depth", this.c);
/* 58 */     paramNBTTagCompound.setInt("HPos", this.d);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 63 */     this.a = paramNBTTagCompound.getInt("Width");
/* 64 */     this.b = paramNBTTagCompound.getInt("Height");
/* 65 */     this.c = paramNBTTagCompound.getInt("Depth");
/* 66 */     this.d = paramNBTTagCompound.getInt("HPos");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt) {
/* 71 */     if (this.d >= 0) {
/* 72 */       return true;
/*    */     }
/*    */     
/* 75 */     int i = 0;
/* 76 */     byte b = 0;
/* 77 */     for (int j = this.f.c; j <= this.f.f; j++) {
/* 78 */       for (int k = this.f.a; k <= this.f.d; k++) {
/* 79 */         if (paramStructureBoundingBox.b(k, 64, j)) {
/* 80 */           i += Math.max(paramWorld.i(k, j), paramWorld.worldProvider.getSeaLevel());
/* 81 */           b++;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 86 */     if (b == 0) {
/* 87 */       return false;
/*    */     }
/* 89 */     this.d = i / b;
/* 90 */     this.f.a(0, this.d - this.f.b + paramInt, 0);
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenScatteredPiece.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */