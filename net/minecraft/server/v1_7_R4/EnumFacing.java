/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public enum EnumFacing {
/*  4 */   DOWN(0, 1, 0, -1, 0),
/*  5 */   UP(1, 0, 0, 1, 0),
/*  6 */   NORTH(2, 3, 0, 0, -1),
/*  7 */   SOUTH(3, 2, 0, 0, 1),
/*  8 */   EAST(4, 5, -1, 0, 0),
/*  9 */   WEST(5, 4, 1, 0, 0);
/*    */   
/*    */   private final int g;
/*    */   
/*    */   private final int h;
/*    */   private final int i;
/*    */   
/*    */   static {
/* 17 */     l = new EnumFacing[6];
/*    */     
/* 19 */     for (EnumFacing enumFacing : values())
/* 20 */       l[enumFacing.g] = enumFacing; 
/*    */   }
/*    */   private final int j; private final int k; private static final EnumFacing[] l;
/*    */   
/*    */   EnumFacing(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 25 */     this.g = paramInt1;
/* 26 */     this.h = paramInt2;
/* 27 */     this.i = paramInt3;
/* 28 */     this.j = paramInt4;
/* 29 */     this.k = paramInt5;
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
/*    */   public int getAdjacentX() {
/* 41 */     return this.i;
/*    */   }
/*    */   
/*    */   public int getAdjacentY() {
/* 45 */     return this.j;
/*    */   }
/*    */   
/*    */   public int getAdjacentZ() {
/* 49 */     return this.k;
/*    */   }
/*    */   
/*    */   public static EnumFacing a(int paramInt) {
/* 53 */     return l[paramInt % l.length];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumFacing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */