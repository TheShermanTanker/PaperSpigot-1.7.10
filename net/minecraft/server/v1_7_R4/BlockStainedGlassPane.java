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
/*    */ public class BlockStainedGlassPane
/*    */   extends BlockThin
/*    */ {
/* 14 */   private static final IIcon[] a = new IIcon[16];
/* 15 */   private static final IIcon[] b = new IIcon[16];
/*    */   
/*    */   public BlockStainedGlassPane() {
/* 18 */     super("glass", "glass_pane_top", Material.SHATTERABLE, false);
/* 19 */     a(CreativeModeTab.c);
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
/*    */   public int getDropData(int paramInt) {
/* 38 */     return paramInt;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockStainedGlassPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */