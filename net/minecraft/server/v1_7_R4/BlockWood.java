/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWood
/*    */   extends Block
/*    */ {
/* 11 */   public static final String[] a = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockWood() {
/* 18 */     super(Material.WOOD);
/* 19 */     a(CreativeModeTab.b);
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
/*    */   public int getDropData(int paramInt) {
/* 32 */     return paramInt;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockWood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */