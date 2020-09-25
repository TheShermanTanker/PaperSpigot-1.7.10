/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class Position implements IPosition {
/*    */   protected final double a;
/*    */   protected final double b;
/*    */   protected final double c;
/*    */   
/*    */   public Position(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  9 */     this.a = paramDouble1;
/* 10 */     this.b = paramDouble2;
/* 11 */     this.c = paramDouble3;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getX() {
/* 16 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getY() {
/* 21 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getZ() {
/* 26 */     return this.c;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Position.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */