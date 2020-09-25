/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class ChunkPosition
/*    */ {
/*    */   public final int x;
/*    */   public final int y;
/*    */   public final int z;
/*    */   
/*    */   public ChunkPosition(int paramInt1, int paramInt2, int paramInt3) {
/* 10 */     this.x = paramInt1;
/* 11 */     this.y = paramInt2;
/* 12 */     this.z = paramInt3;
/*    */   }
/*    */   
/*    */   public ChunkPosition(Vec3D paramVec3D) {
/* 16 */     this(MathHelper.floor(paramVec3D.a), MathHelper.floor(paramVec3D.b), MathHelper.floor(paramVec3D.c));
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 20 */     if (paramObject instanceof ChunkPosition) {
/*    */       
/* 22 */       ChunkPosition chunkPosition = (ChunkPosition)paramObject;
/* 23 */       return (chunkPosition.x == this.x && chunkPosition.y == this.y && chunkPosition.z == this.z);
/*    */     } 
/*    */     
/* 26 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 30 */     return this.x * 8976890 + this.y * 981131 + this.z;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */