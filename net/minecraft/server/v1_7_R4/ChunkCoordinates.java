/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ public class ChunkCoordinates
/*     */   implements Comparable
/*     */ {
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public ChunkCoordinates() {}
/*     */   
/*     */   public ChunkCoordinates(int paramInt1, int paramInt2, int paramInt3) {
/*  14 */     this.x = paramInt1;
/*  15 */     this.y = paramInt2;
/*  16 */     this.z = paramInt3;
/*     */   }
/*     */   
/*     */   public ChunkCoordinates(ChunkCoordinates paramChunkCoordinates) {
/*  20 */     this.x = paramChunkCoordinates.x;
/*  21 */     this.y = paramChunkCoordinates.y;
/*  22 */     this.z = paramChunkCoordinates.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  27 */     if (!(paramObject instanceof ChunkCoordinates)) {
/*  28 */       return false;
/*     */     }
/*     */     
/*  31 */     ChunkCoordinates chunkCoordinates = (ChunkCoordinates)paramObject;
/*  32 */     return (this.x == chunkCoordinates.x && this.y == chunkCoordinates.y && this.z == chunkCoordinates.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  37 */     return this.x + this.z << 8 + this.y << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ChunkCoordinates paramChunkCoordinates) {
/*  42 */     if (this.y == paramChunkCoordinates.y) {
/*  43 */       if (this.z == paramChunkCoordinates.z) {
/*  44 */         return this.x - paramChunkCoordinates.x;
/*     */       }
/*  46 */       return this.z - paramChunkCoordinates.z;
/*     */     } 
/*  48 */     return this.y - paramChunkCoordinates.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(int paramInt1, int paramInt2, int paramInt3) {
/*  56 */     this.x = paramInt1;
/*  57 */     this.y = paramInt2;
/*  58 */     this.z = paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float e(int paramInt1, int paramInt2, int paramInt3) {
/* 200 */     float f1 = (this.x - paramInt1);
/* 201 */     float f2 = (this.y - paramInt2);
/* 202 */     float f3 = (this.z - paramInt3);
/* 203 */     return f1 * f1 + f2 * f2 + f3 * f3;
/*     */   }
/*     */   
/*     */   public float e(ChunkCoordinates paramChunkCoordinates) {
/* 207 */     return e(paramChunkCoordinates.x, paramChunkCoordinates.y, paramChunkCoordinates.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 212 */     return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkCoordinates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */