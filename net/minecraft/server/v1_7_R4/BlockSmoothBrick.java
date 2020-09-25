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
/*    */ public class BlockSmoothBrick
/*    */   extends Block
/*    */ {
/* 16 */   public static final String[] a = new String[] { "default", "mossy", "cracked", "chiseled" };
/*    */ 
/*    */ 
/*    */   
/* 20 */   public static final String[] b = new String[] { null, "mossy", "cracked", "carved" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockSmoothBrick() {
/* 27 */     super(Material.STONE);
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
/*    */   public int getDropData(int paramInt) {
/* 39 */     return paramInt;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSmoothBrick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */