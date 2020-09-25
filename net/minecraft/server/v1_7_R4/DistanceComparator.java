/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public class DistanceComparator
/*    */   implements Comparator
/*    */ {
/*    */   private final Entity a;
/*    */   
/*    */   public DistanceComparator(Entity paramEntity) {
/* 65 */     this.a = paramEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(Entity paramEntity1, Entity paramEntity2) {
/* 70 */     double d1 = this.a.f(paramEntity1);
/* 71 */     double d2 = this.a.f(paramEntity2);
/* 72 */     if (d1 < d2) return -1; 
/* 73 */     if (d1 > d2) return 1; 
/* 74 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DistanceComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */