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
/*    */ public class BlockDirt
/*    */   extends Block
/*    */ {
/* 19 */   public static final String[] a = new String[] { "default", "default", "podzol" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected BlockDirt() {
/* 27 */     super(Material.EARTH);
/* 28 */     a(CreativeModeTab.b);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDropData(int paramInt) {
/* 65 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack j(int paramInt) {
/* 70 */     if (paramInt == 1) paramInt = 0; 
/* 71 */     return super.j(paramInt);
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
/*    */   public int getDropData(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 90 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 91 */     if (i == 1) {
/* 92 */       i = 0;
/*    */     }
/* 94 */     return i;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockDirt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */