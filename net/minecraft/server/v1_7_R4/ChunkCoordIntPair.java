/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class ChunkCoordIntPair
/*    */ {
/*    */   public final int x;
/*    */   public final int z;
/*    */   
/*    */   public ChunkCoordIntPair(int paramInt1, int paramInt2) {
/*  9 */     this.x = paramInt1;
/* 10 */     this.z = paramInt2;
/*    */   }
/*    */   
/*    */   public static long a(int paramInt1, int paramInt2) {
/* 14 */     return paramInt1 & 0xFFFFFFFFL | (paramInt2 & 0xFFFFFFFFL) << 32L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 23 */     int i = 1664525 * this.x + 1013904223;
/* 24 */     int j = 1664525 * (this.z ^ 0xDEADBEEF) + 1013904223;
/* 25 */     return i ^ j;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 30 */     if (this == paramObject) {
/* 31 */       return true;
/*    */     }
/*    */     
/* 34 */     if (paramObject instanceof ChunkCoordIntPair) {
/* 35 */       ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair)paramObject;
/*    */       
/* 37 */       return (this.x == chunkCoordIntPair.x && this.z == chunkCoordIntPair.z);
/*    */     } 
/*    */     
/* 40 */     return false;
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
/*    */   public int a() {
/* 54 */     return (this.x << 4) + 8;
/*    */   }
/*    */   
/*    */   public int b() {
/* 58 */     return (this.z << 4) + 8;
/*    */   }
/*    */   
/*    */   public ChunkPosition a(int paramInt) {
/* 62 */     return new ChunkPosition(a(), paramInt, b());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 66 */     return "[" + this.x + ", " + this.z + "]";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkCoordIntPair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */