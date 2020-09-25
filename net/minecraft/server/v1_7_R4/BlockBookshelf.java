/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBookshelf
/*    */   extends Block
/*    */ {
/*    */   public BlockBookshelf() {
/* 12 */     super(Material.WOOD);
/* 13 */     a(CreativeModeTab.b);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int a(Random paramRandom) {
/* 24 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 29 */     return Items.BOOK;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockBookshelf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */