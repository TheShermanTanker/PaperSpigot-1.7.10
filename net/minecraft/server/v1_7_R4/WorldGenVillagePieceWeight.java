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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenVillagePieceWeight
/*    */ {
/*    */   public Class a;
/*    */   public final int b;
/*    */   public int c;
/*    */   public int d;
/*    */   
/*    */   public WorldGenVillagePieceWeight(Class paramClass, int paramInt1, int paramInt2) {
/* 49 */     this.a = paramClass;
/* 50 */     this.b = paramInt1;
/* 51 */     this.d = paramInt2;
/*    */   }
/*    */   
/*    */   public boolean a(int paramInt) {
/* 55 */     return (this.d == 0 || this.c < this.d);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 59 */     return (this.d == 0 || this.c < this.d);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillagePieceWeight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */