/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Direction
/*    */ {
/* 12 */   public static final int[] a = new int[] { 0, -1, 0, 1 };
/*    */ 
/*    */   
/* 15 */   public static final int[] b = new int[] { 1, 0, -1, 0 };
/*    */ 
/*    */ 
/*    */   
/* 19 */   public static final String[] c = new String[] { "SOUTH", "WEST", "NORTH", "EAST" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public static final int[] d = new int[] { 3, 4, 2, 5 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   public static final int[] e = new int[] { -1, -1, 2, 0, 1, 3 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public static final int[] f = new int[] { 2, 3, 0, 1 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public static final int[] g = new int[] { 1, 2, 3, 0 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public static final int[] h = new int[] { 3, 0, 1, 2 };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public static final int[][] i = new int[][] { { 1, 0, 3, 2, 5, 4 }, { 1, 0, 5, 4, 2, 3 }, { 1, 0, 2, 3, 4, 5 }, { 1, 0, 4, 5, 3, 2 } };
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
/*    */   public static int a(double paramDouble1, double paramDouble2) {
/* 69 */     if (MathHelper.abs((float)paramDouble1) > MathHelper.abs((float)paramDouble2)) {
/* 70 */       if (paramDouble1 > 0.0D) {
/* 71 */         return 1;
/*    */       }
/* 73 */       return 3;
/*    */     } 
/*    */     
/* 76 */     if (paramDouble2 > 0.0D) {
/* 77 */       return 2;
/*    */     }
/* 79 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Direction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */