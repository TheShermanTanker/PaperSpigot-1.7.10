/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMelon
/*    */   extends Block
/*    */ {
/*    */   protected BlockMelon() {
/* 15 */     super(Material.PUMPKIN);
/* 16 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 27 */     return Items.MELON;
/*    */   }
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 32 */     return 3 + paramRandom.nextInt(5);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDropCount(int paramInt, Random paramRandom) {
/* 37 */     int i = a(paramRandom) + paramRandom.nextInt(1 + paramInt);
/* 38 */     if (i > 9) {
/* 39 */       i = 9;
/*    */     }
/* 41 */     return i;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockMelon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */