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
/*    */ public class BlockLeaves2
/*    */   extends BlockLeaves
/*    */ {
/* 17 */   public static final String[][] N = new String[][] { { "leaves_acacia", "leaves_big_oak" }, { "leaves_acacia_opaque", "leaves_big_oak_opaque" } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public static final String[] O = new String[] { "acacia", "big_oak" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 30 */     if ((paramInt4 & 0x3) == 1 && paramWorld.random.nextInt(paramInt5) == 0) {
/* 31 */       a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Items.APPLE, 1, 0));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDropData(int paramInt) {
/* 37 */     return super.getDropData(paramInt) + 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDropData(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 42 */     return paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x3;
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
/*    */   public String[] e() {
/* 72 */     return O;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockLeaves2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */