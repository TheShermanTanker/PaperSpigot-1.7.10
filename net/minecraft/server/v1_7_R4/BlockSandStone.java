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
/*    */ public class BlockSandStone
/*    */   extends Block
/*    */ {
/* 16 */   public static final String[] a = new String[] { "default", "chiseled", "smooth" };
/*    */ 
/*    */ 
/*    */   
/* 20 */   private static final String[] b = new String[] { "normal", "carved", "smooth" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockSandStone() {
/* 29 */     super(Material.STONE);
/* 30 */     a(CreativeModeTab.b);
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
/*    */   public int getDropData(int paramInt) {
/* 47 */     return paramInt;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSandStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */