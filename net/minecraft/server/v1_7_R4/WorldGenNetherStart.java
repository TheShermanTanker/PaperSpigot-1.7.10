/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenNetherStart
/*    */   extends StructureStart
/*    */ {
/*    */   public WorldGenNetherStart() {}
/*    */   
/*    */   public WorldGenNetherStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 70 */     super(paramInt1, paramInt2);
/*    */     
/* 72 */     WorldGenNetherPiece15 worldGenNetherPiece15 = new WorldGenNetherPiece15(paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
/* 73 */     this.a.add(worldGenNetherPiece15);
/* 74 */     worldGenNetherPiece15.a(worldGenNetherPiece15, this.a, paramRandom);
/*    */     
/* 76 */     ArrayList<StructurePiece> arrayList = worldGenNetherPiece15.e;
/* 77 */     while (!arrayList.isEmpty()) {
/* 78 */       int i = paramRandom.nextInt(arrayList.size());
/* 79 */       StructurePiece structurePiece = arrayList.remove(i);
/* 80 */       structurePiece.a(worldGenNetherPiece15, this.a, paramRandom);
/*    */     } 
/*    */     
/* 83 */     c();
/* 84 */     a(paramWorld, paramRandom, 48, 70);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNetherStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */