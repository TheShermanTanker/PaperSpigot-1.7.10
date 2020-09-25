/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ public class PlayerDistanceComparator
/*    */   implements Comparator
/*    */ {
/*    */   private final ChunkCoordinates a;
/*    */   
/*    */   public PlayerDistanceComparator(ChunkCoordinates paramChunkCoordinates) {
/* 12 */     this.a = paramChunkCoordinates;
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(EntityPlayer paramEntityPlayer1, EntityPlayer paramEntityPlayer2) {
/* 17 */     double d1 = paramEntityPlayer1.e(this.a.x, this.a.y, this.a.z);
/* 18 */     double d2 = paramEntityPlayer2.e(this.a.x, this.a.y, this.a.z);
/*    */     
/* 20 */     if (d1 < d2)
/* 21 */       return -1; 
/* 22 */     if (d1 > d2) {
/* 23 */       return 1;
/*    */     }
/* 25 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerDistanceComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */