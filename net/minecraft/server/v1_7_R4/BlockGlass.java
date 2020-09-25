/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockGlass
/*    */   extends BlockHalfTransparent
/*    */ {
/*    */   public BlockGlass(Material paramMaterial, boolean paramBoolean) {
/* 11 */     super("glass", paramMaterial, paramBoolean);
/* 12 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 17 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean E() {
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockGlass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */