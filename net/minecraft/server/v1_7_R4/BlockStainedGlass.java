/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockStainedGlass
/*    */   extends BlockHalfTransparent
/*    */ {
/* 11 */   private static final IIcon[] a = new IIcon[16];
/*    */   
/*    */   public BlockStainedGlass(Material paramMaterial) {
/* 14 */     super("glass", paramMaterial, false);
/* 15 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDropData(int paramInt) {
/* 25 */     return paramInt;
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
/*    */   public int a(Random paramRandom) {
/* 53 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean E() {
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 63 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockStainedGlass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */