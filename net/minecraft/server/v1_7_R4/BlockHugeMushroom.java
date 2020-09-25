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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockHugeMushroom
/*    */   extends Block
/*    */ {
/* 18 */   private static final String[] a = new String[] { "skin_brown", "skin_red" };
/*    */ 
/*    */ 
/*    */   
/*    */   private final int b;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockHugeMushroom(Material paramMaterial, int paramInt) {
/* 28 */     super(paramMaterial);
/* 29 */     this.b = paramInt;
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
/*    */   public int a(Random paramRandom) {
/* 58 */     int i = paramRandom.nextInt(10) - 7;
/* 59 */     if (i < 0) i = 0; 
/* 60 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 66 */     return Item.getById(Block.getId(Blocks.BROWN_MUSHROOM) + this.b);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockHugeMushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */